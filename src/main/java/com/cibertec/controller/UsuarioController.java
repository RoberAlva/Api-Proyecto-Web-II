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

import com.cibertec.model.Usuario;
import com.cibertec.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> listarUsuarios() {
        try {
            return usuarioService.listarUsuarios();
        } catch (Exception e) {
            System.out.println("Error encontrado: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/buscar/{codigousuario}")
    public ResponseEntity<Map<String, Object>> buscarPorCodigo(@PathVariable Long codigousuario) {
        try {
            return usuarioService.obtenerUsuarioPorCodigo(codigousuario);
        } catch (Exception e) {
            System.out.println("Error encontrado: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/grabar")
    public ResponseEntity<Map<String, Object>> crearUsuario(@RequestBody Usuario usuario) {
        try {
            return usuarioService.crearUsuario(usuario);
        } catch (Exception e) {
            System.out.println("Error encontrado: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/actualizar/{codigousuario}")
    public ResponseEntity<Map<String, Object>> actualizarUsuario(@PathVariable Long codigousuario, @RequestBody Usuario usuario) {
        try {
            return usuarioService.actualizarUsuario(codigousuario, usuario);
        } catch (Exception e) {
            System.out.println("Error encontrado: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/eliminar/{codigousuario}")
    public ResponseEntity<Map<String, Object>> eliminarUsuario(@PathVariable Long codigousuario) {
        try {
            return usuarioService.eliminarUsuario(codigousuario);
        } catch (Exception e) {
            System.out.println("Error encontrado: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/contar")
    public ResponseEntity<Map<String, Long>> contarUsuarios() {
        return usuarioService.contarUsuarios();
    }
}






