package com.cibertec.serviceImpl;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cibertec.model.Libro;
import com.cibertec.repository.LibroRepository;
import com.cibertec.service.LibroService;

@Service
public class LibroServiceImpl implements LibroService {
    
    @Autowired
    private LibroRepository libroRepository;
    
    @Override
    public ResponseEntity<Map<String, Object>> listarLibros() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Libro> libros = libroRepository.findAll();

            if (libros.isEmpty()) {
                response.put("mensaje", "No existen registros para la consulta");
                response.put("status", HttpStatus.NOT_FOUND.value());
                response.put("libros", libros);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                response.put("mensaje", "Lista de libros completa");
                response.put("status", HttpStatus.OK.value());
                response.put("libros", libros);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
        } catch (Exception e) {
            response.put("mensaje", "Error interno del servidor");
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    @Override
    public ResponseEntity<Map<String, Object>> buscarPorCodigo(Long codigoLibro) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<Libro> libroExistente = libroRepository.findById(codigoLibro);

            if (!libroExistente.isPresent()) {
                response.put("mensaje", "Libro con código " + codigoLibro + " no encontrado");
                response.put("status", HttpStatus.NOT_FOUND.value());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            } else {
                response.put("mensaje", "Libro con código " + codigoLibro + " encontrado");
                response.put("libro", libroExistente.get());
                response.put("status", HttpStatus.OK.value());
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
        } catch (Exception e) {
            response.put("mensaje", "Error interno del servidor");
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    @Override
    public ResponseEntity<Map<String, Object>> grabarLibro(Libro libro) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Establecer fecha de creación si no existe
            if (libro.getFechaCreacion() == null) {
                libro.setFechaCreacion(LocalDate.now());
            }
            
            Libro nuevoLibro = libroRepository.save(libro);

            response.put("mensaje", "Libro agregado exitosamente");
            response.put("status", HttpStatus.CREATED.value());
            response.put("libro", nuevoLibro);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.put("mensaje", "Error al crear el libro");
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    @Override
    public ResponseEntity<Map<String, Object>> actualizarLibro(Long codigoLibro, Libro libro) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<Libro> libroExistente = libroRepository.findById(codigoLibro);

            if (!libroExistente.isPresent()) {
                response.put("mensaje", "Libro con código " + codigoLibro + " no encontrado");
                response.put("status", HttpStatus.NOT_FOUND.value());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            } else {
                Libro libroActualizado = libroExistente.get();
                libroActualizado.setNombreLibro(libro.getNombreLibro());
                libroActualizado.setGenero(libro.getGenero());
                libroActualizado.setStock(libro.getStock());
                libroActualizado.setAutor(libro.getAutor());
                libroActualizado.setAnioPublicacion(libro.getAnioPublicacion());
                libroActualizado.setEditorial(libro.getEditorial());
                libroActualizado.setEstado(libro.getEstado());
                libroActualizado.setDescripcion(libro.getDescripcion());
                // No actualizar fechaCreacion

                libroRepository.save(libroActualizado);
                response.put("mensaje", "Libro actualizado exitosamente");
                response.put("status", HttpStatus.OK.value());
                response.put("libro", libroActualizado);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
        } catch (Exception e) {
            response.put("mensaje", "Error al actualizar el libro");
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    @Override
    public ResponseEntity<Map<String, Object>> eliminarLibro(Long codigoLibro) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<Libro> libroExistente = libroRepository.findById(codigoLibro);

            if (!libroExistente.isPresent()) {
                response.put("mensaje", "Libro con código " + codigoLibro + " no encontrado");
                response.put("status", HttpStatus.NOT_FOUND.value());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            } else {
                // Baja lógica - cambiar estado a false en lugar de eliminar físicamente
                Libro libro = libroExistente.get();
                libro.setEstado(false);
                libroRepository.save(libro);
                
                response.put("mensaje", "Libro con código " + codigoLibro + " eliminado exitosamente");
                response.put("status", HttpStatus.OK.value());
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
        } catch (Exception e) {
            response.put("mensaje", "Error al eliminar el libro");
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}