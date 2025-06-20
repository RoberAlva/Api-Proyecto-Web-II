package com.cibertec.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cibertec.model.Libro;
import com.cibertec.model.Prestamo;
import com.cibertec.repository.LibroRepository;
import com.cibertec.repository.PrestamoRepository;
import com.cibertec.service.PrestamoService;

@Service
@Transactional
public class PrestamoServiceImpl implements PrestamoService {
    
    @Autowired
    private PrestamoRepository prestamoRepository;
    
    @Autowired
    private LibroRepository libroRepository;
    
    @Override
    public ResponseEntity<Map<String, Object>> listarPrestamos() {
        Map<String, Object> response = new HashMap<>();
        List<Prestamo> lista = prestamoRepository.findAll();
        response.put("listaPrestamos", lista);
        return ResponseEntity.ok(response);
    }
    
    @Override
    public ResponseEntity<Map<String, Object>> buscarPrestamoPorCodigo(Long codigoPrestamo) {
        Map<String, Object> response = new HashMap<>();
        Optional<Prestamo> option = prestamoRepository.findById(codigoPrestamo);
        if (option.isPresent()) {
            response.put("listaPrestamos", option.get());
            return ResponseEntity.ok(response);
        } else {
            response.put("mensaje", "El Prestamo no fue encontrado");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
    
    @Override
    public ResponseEntity<Map<String, Object>> grabarPrestamo(Prestamo prestamo) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Verificar disponibilidad del libro
            Libro libro = prestamo.getLibro();
            if (libro == null) {
                response.put("mensaje", "Libro no especificado");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            
            // Obtener el libro actualizado de la BD
            Optional<Libro> libroOpt = libroRepository.findById(libro.getCodigoLibro());
            if (!libroOpt.isPresent()) {
                response.put("mensaje", "Libro no encontrado");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            
            Libro libroActual = libroOpt.get();
            
            // Verificar si hay stock disponible
            if (libroActual.getStock() <= 0) {
                response.put("mensaje", "No hay stock disponible para este libro");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            
            // Verificar si el libro está activo
            if (!libroActual.getEstado()) {
                response.put("mensaje", "El libro no está disponible");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            
            // REDUCIR STOCK AL CREAR PRÉSTAMO (reserva)
            libroActual.setStock(libroActual.getStock() - 1);
            libroRepository.save(libroActual);
            
            // Actualizar la referencia del libro en el préstamo
            prestamo.setLibro(libroActual);
            
            // Guardar el préstamo
            Prestamo nuevo = prestamoRepository.save(prestamo);
            
            response.put("mensaje", "El Prestamo se Registro Exitosamente");
            response.put("listaPrestamos", nuevo);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
            
        } catch (Exception e) {
            response.put("mensaje", "Error al crear el préstamo: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @Override
    public ResponseEntity<Map<String, Object>> actualizarPrestamo(Long codigoPrestamo, Prestamo prestamo) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<Prestamo> option = prestamoRepository.findById(codigoPrestamo);
            
            if (!option.isPresent()) {
                response.put("mensaje", "Préstamo no encontrado");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            
            Prestamo prestamoExistente = option.get();
            String estadoAnterior = prestamoExistente.getEstadoPres();
            String estadoNuevo = prestamo.getEstadoPres();
            
            // Manejar cambios de stock según el cambio de estado
            if (!estadoAnterior.equals(estadoNuevo)) {
                Libro libro = prestamoExistente.getLibro();
                
                // Si cambia de cualquier estado a "Devuelto" -> INCREMENTAR STOCK
                if ("Devuelto".equals(estadoNuevo) && !"Devuelto".equals(estadoAnterior)) {
                    libro.setStock(libro.getStock() + 1);
                    libroRepository.save(libro);
                }
                // Si cambia de "Devuelto" a cualquier otro estado -> REDUCIR STOCK
                else if ("Devuelto".equals(estadoAnterior) && !"Devuelto".equals(estadoNuevo)) {
                    if (libro.getStock() <= 0) {
                        response.put("mensaje", "No hay stock disponible para reactivar este préstamo");
                        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
                    }
                    libro.setStock(libro.getStock() - 1);
                    libroRepository.save(libro);
                }
                // Para cambios entre "Por entregar" y "Alquilado" -> NO CAMBIAR STOCK
                // (ambos estados mantienen el libro fuera de disponibilidad)
            }
            
            // Actualizar los campos del préstamo
            prestamo.setCodigoPrestamo(codigoPrestamo);
            Prestamo actualizado = prestamoRepository.save(prestamo);
            
            response.put("mensaje", "Prestamo actualizado correctamente");
            response.put("listaPrestamos", actualizado);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("mensaje", "Error al actualizar el préstamo: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @Override
    public ResponseEntity<Map<String, Object>> eliminarPrestamo(Long codigoPrestamo) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<Prestamo> option = prestamoRepository.findById(codigoPrestamo);
            
            if (!option.isPresent()) {
                response.put("mensaje", "Préstamo no encontrado");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            
            Prestamo prestamo = option.get();
            
            // INCREMENTAR STOCK AL ELIMINAR PRÉSTAMO (solo si no estaba devuelto)
            if (!"Devuelto".equals(prestamo.getEstadoPres())) {
                Libro libro = prestamo.getLibro();
                libro.setStock(libro.getStock() + 1);
                libroRepository.save(libro);
            }
            
            // Eliminar el préstamo
            prestamoRepository.deleteById(codigoPrestamo);
            
            response.put("mensaje", "Prestamo eliminado");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("mensaje", "Error al eliminar el préstamo: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //contar pretamos bibliotecario
    @Override
    public ResponseEntity<Map<String, Long>> contarPrestamos() {
        long total = prestamoRepository.count();
        Map<String, Long> response = Map.of("total", total);
        return ResponseEntity.ok(response);
    }
    
    //contar prestamos por usuario
    
    @Override
    public ResponseEntity<Map<String, Long>> contarPrestamosPorCorreo(String correo) {
        long total = prestamoRepository.countByUsuarioCorreo(correo);
        return ResponseEntity.ok(Map.of("total", total));
    }
}
