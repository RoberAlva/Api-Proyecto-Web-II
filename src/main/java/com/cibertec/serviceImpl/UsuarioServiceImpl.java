package com.cibertec.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.cibertec.model.Usuario;
import com.cibertec.repository.UsuarioRepository;
import com.cibertec.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	@Autowired
	private UsuarioRepository usuariorepository;
	
	@Override
	public ResponseEntity<Map<String, Object>> listarUsuarios() {
		Map<String, Object>response = new HashMap<>();
		List<Usuario> usuarios = usuariorepository.findAll();
		
		if(usuarios.isEmpty() ) {
			response.put("mensaje", "no hay usuarios registrados");
			response.put("status", HttpStatus.NOT_FOUND);
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}else {
			response.put("mensaje", "Lista de Usuarios complea");
			response.put("status", HttpStatus.OK);
			response.put("usuarios", usuarios);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> obtenerUsuarioPorCodigo(Long codigoUsuario) {

		Map<String, Object>response =new HashMap<>();
		Optional<Usuario> usuarioExistente = usuariorepository.findById(codigoUsuario);
		  
		if(!usuarioExistente.isPresent()) {
			
			response.put("mensaje", "Usuario con codigo "+ codigoUsuario +" no encontrado");
			response.put("status", HttpStatus.NOT_FOUND);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}else {
			usuariorepository.findById(codigoUsuario);
			response.put("mensaje", "Usuario con codigo "+ codigoUsuario + " Encontrado");
			response.put("productos", usuarioExistente);
			response.put("status", HttpStatus.OK);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
	}

/*	@Override
	public ResponseEntity<Map<String, Object>> crearUsuario(Usuario usuario) {
		Map<String, Object>response =new HashMap<>();
		Usuario nuevoUsuario = usuariorepository.save(usuario);
		
		response.put("mensaje", "Usuario Agregado Exitosamente");
		response.put("status", HttpStatus.CREATED);
		response.put("usuario", nuevoUsuario);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
		
	}*/
	
	@Override
	public ResponseEntity<Map<String, Object>> crearUsuario(Usuario usuario) {
	    Map<String, Object> response = new HashMap<>();
	    
	 
	    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	    usuario.setContrasena(encoder.encode(usuario.getContrasena()));
	    
	    Usuario nuevoUsuario = usuariorepository.save(usuario);
	    
	    response.put("mensaje", "Usuario Agregado Exitosamente");
	    response.put("status", HttpStatus.CREATED);
	    response.put("usuario", nuevoUsuario);
	    
	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}


	@Override
	public ResponseEntity<Map<String, Object>> actualizarUsuario(Long codigoUsuario, Usuario usuario) {
		Map<String, Object>response =new HashMap<>();
		Optional<Usuario> usuarioExistente = usuariorepository.findById(codigoUsuario);
		
		if(!usuarioExistente.isPresent()) {
			
			response.put("mensaje", "usuario con codigo "+ codigoUsuario +" no encontrado");
			response.put("status", HttpStatus.NOT_FOUND);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}else {
			Usuario usuarioActualizado = usuarioExistente.get();
			usuarioActualizado.setTipoDocumento(usuario.getTipoDocumento());
			usuarioActualizado.setNumeroDocumento(usuario.getNumeroDocumento());
			usuarioActualizado.setNombreCompleto(usuario.getNombreCompleto());
			usuarioActualizado.setCorreo(usuario.getCorreo());
			usuarioActualizado.setContrasena(usuario.getContrasena());
			usuarioActualizado.setTipoUsuario(usuario.getTipoUsuario());
			usuarioActualizado.setNumeroDocumento(usuario.getNumeroDocumento());
			usuarioActualizado.setEstado(usuario.getEstado());
			usuarioActualizado.setFechaCreacion(usuario.getFechaCreacion());
			
			usuariorepository.save(usuarioActualizado);
			response.put("mensaje", "Usuario Actualizado Exitosamente");
			response.put("status", HttpStatus.OK);
			response.put("usuario", usuarioActualizado);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> eliminarUsuario(Long codigoUsuario) {
		Map<String, Object>response =new HashMap<>();
		Optional<Usuario> usuarioExistente = usuariorepository.findById(codigoUsuario);
		  
		if(!usuarioExistente.isPresent()) {
			
			response.put("mensaje", "Usuario con codigo "+ codigoUsuario +" no encontrado");
			response.put("status", HttpStatus.NOT_FOUND);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}else {
			usuariorepository.deleteById(codigoUsuario);
			response.put("mensaje", "Usuario con codigo "+ codigoUsuario + " Eliminado Exitosamente");
			response.put("status", HttpStatus.OK);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}	
	}

	

}
