package com.cibertec.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cibertec.model.Prestamo;
import com.cibertec.service.PrestamoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/prestamo")
@RequiredArgsConstructor
public class PrestamoController {
	private final PrestamoService prestamoService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> listar() {
        return prestamoService.listarPrestamos();
    }

    @GetMapping("buscar/{id}")
    public ResponseEntity<Map<String, Object>> buscar(@PathVariable Long id) {
        return prestamoService.buscarPrestamoPorCodigo(id);
    }

    @PostMapping("/grabar")
    public ResponseEntity<Map<String, Object>> registrar(@RequestBody Prestamo prestamo) {
        return prestamoService.grabarPrestamo(prestamo);
    }

    @PutMapping("actualizar/{id}")
    public ResponseEntity<Map<String, Object>> actualizar(@PathVariable Long id, @RequestBody Prestamo prestamo) {
        return prestamoService.actualizarPrestamo(id, prestamo);
    }

    @DeleteMapping("eliminar/{id}")
    public ResponseEntity<Map<String, Object>> eliminar(@PathVariable Long id) {
        return prestamoService.eliminarPrestamo(id);
    }
    
    @GetMapping("/contar")
    public ResponseEntity<Map<String, Long>> contarPrestamos() {
        return prestamoService.contarPrestamos();
    }
    
    @GetMapping("/contar/por-correo")
    public ResponseEntity<Map<String, Long>> contarPorCorreo(@RequestParam String correo) {
        return prestamoService.contarPrestamosPorCorreo(correo);
    }
	
}
