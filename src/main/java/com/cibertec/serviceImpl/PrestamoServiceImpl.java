package com.cibertec.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cibertec.model.Prestamo;
import com.cibertec.repository.PrestamoRepository;
import com.cibertec.service.PrestamoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrestamoServiceImpl implements PrestamoService{
	
	@Autowired
	PrestamoRepository prestamoRepository;

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
		}else {
			response.put("mensaje", "El Prestamo no fue encontrado");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> grabarPrestamo(Prestamo prestamo) {
		Map<String, Object> response = new HashMap<>();
		Prestamo nuevo = prestamoRepository.save(prestamo);
		response.put("mensaje", "El Prestamo se Regsitro Exitosamente");
		response.put("listaPrestamos", nuevo);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<Map<String, Object>> actualizarPrestamo(Long codigoPrestamo, Prestamo prestamo) {
		Map<String, Object> response = new HashMap<>();
		Optional<Prestamo> option = prestamoRepository.findById(codigoPrestamo);
		if(option.isPresent()) {
			prestamo.setCodigoPrestamo(codigoPrestamo);
			Prestamo actualizado = prestamoRepository.save(prestamo);
			response.put("mensaje", "Prestamo actualizado correctamente");
			response.put("listaPrestamos", actualizado);
			return ResponseEntity.ok(response);
		}else {
			response.put("mensaje", "Error al actualizar");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> eliminarPrestamo(Long codigoPrestamo) {
		Map<String, Object> response = new HashMap<>();
		Optional<Prestamo> option = prestamoRepository.findById(codigoPrestamo);
		
		if(option.isPresent()) {
			prestamoRepository.deleteById(codigoPrestamo);
			response.put("mensaje", "Prestamo eliminado");
			return ResponseEntity.ok(response);
		}else {
			response.put("mensaje", "Error de eliminación");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		
	}
	
	

}
