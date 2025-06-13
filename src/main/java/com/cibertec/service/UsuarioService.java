package com.cibertec.service;


import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.cibertec.model.Usuario;

public interface UsuarioService {
	    ResponseEntity<Map<String, Object>> listarUsuarios();
	    ResponseEntity<Map<String, Object>>  obtenerUsuarioPorCodigo(Long codigoUsuario);
	    ResponseEntity<Map<String, Object>> crearUsuario(Usuario usuario);
	    ResponseEntity<Map<String, Object>>  actualizarUsuario(Long codigoUsuario, Usuario usuario);
	    ResponseEntity<Map<String, Object>>  eliminarUsuario(Long codigoUsuario);

}
