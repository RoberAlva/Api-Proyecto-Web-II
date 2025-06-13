package com.cibertec.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.cibertec.model.Libro;

public interface LibroService {
	
	public ResponseEntity<Map<String, Object>> listarLibros();
	public ResponseEntity<Map<String, Object>> buscarPorCodigo(Long codigoLibro);
	public ResponseEntity<Map<String, Object>> grabarLibro(Libro libro);
	public ResponseEntity<Map<String, Object>> actualizarLibro(Long codigoLibro, Libro libro);
	public ResponseEntity<Map<String, Object>> eliminarLibro(Long codigoLibro);
}
