package com.cibertec.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.cibertec.model.Prestamo;

public interface PrestamoService {
	public ResponseEntity<Map<String, Object>> listarPrestamos();
	public ResponseEntity<Map<String, Object>> buscarPrestamoPorCodigo(Long codigoPrestamo);
	public ResponseEntity<Map<String, Object>> grabarPrestamo(Prestamo prestamo);
	public ResponseEntity<Map<String, Object>> actualizarPrestamo(Long codigoPrestamo, Prestamo prestamo);
	public ResponseEntity<Map<String, Object>> eliminarPrestamo(Long codigoPrestamo);

}
