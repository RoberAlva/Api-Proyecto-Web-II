package com.cibertec.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cibertec.model.Libro;
import com.cibertec.service.LibroService;

@RestController
@RequestMapping("/api/libros")
public class LibroController {
	
	@Autowired
	private LibroService service;
	
	@GetMapping
    public ResponseEntity<Map<String, Object>> listarLibros() {
        try {
            return service.listarLibros();
        } catch (Exception e) {
            System.out.println("Error encontrado: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/buscar/{codigo}")
    public ResponseEntity<Map<String, Object>> buscarPorCodigo(@PathVariable Long codigo) {
        try {
            return service.buscarPorCodigo(codigo);
        } catch (Exception e) {
            System.out.println("Error encontrado: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/grabar")
    public ResponseEntity<Map<String, Object>> grabarLibro(@RequestBody Libro libro) {
        try {
            return service.grabarLibro(libro);
        } catch (Exception e) {
            System.out.println("Error encontrado: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/actualizar/{codigo}")
    public ResponseEntity<Map<String, Object>> actualizarLibro(@PathVariable Long codigo, @RequestBody Libro libro) {
        try {
            return service.actualizarLibro(codigo, libro);
        } catch (Exception e) {
            System.out.println("Error encontrado: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/eliminar/{codigo}")
    public ResponseEntity<Map<String, Object>> eliminarLibro(@PathVariable Long codigo) {
        try {
            return service.eliminarLibro(codigo);
        } catch (Exception e) {
            System.out.println("Error encontrado: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
