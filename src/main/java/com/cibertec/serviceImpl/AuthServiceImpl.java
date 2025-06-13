package com.cibertec.serviceImpl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cibertec.dto.LoginRequestDTO;
import com.cibertec.dto.LoginResponseDTO;
import com.cibertec.model.Usuario;
import com.cibertec.repository.UsuarioRepository;
import com.cibertec.service.AuthService;
import com.cibertec.util.JwtUtil;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public ResponseEntity<Map<String, Object>> login(LoginRequestDTO loginRequest) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreoAndEstadoTrue(loginRequest.getCorreo());
            
            if (!usuarioOpt.isPresent()) {
                response.put("mensaje", "Credenciales incorrectas");
                response.put("status", HttpStatus.UNAUTHORIZED);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
            Usuario usuario = usuarioOpt.get();
            
            String contraseñaHasheada = hashSHA256(loginRequest.getContrasena());
            
            if (!usuario.getContrasena().equals(contraseñaHasheada)) {
                response.put("mensaje", "Credenciales incorrectas");
                response.put("status", HttpStatus.UNAUTHORIZED);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
            String token = jwtUtil.generateToken(
                usuario.getCorreo(), 
                usuario.getTipoUsuario(), 
                usuario.getCodigoUsuario()
            );
            
            LoginResponseDTO loginResponse = new LoginResponseDTO(
                token,
                usuario.getTipoUsuario(),
                usuario.getNombreCompleto(),
                usuario.getCorreo(),
                usuario.getCodigoUsuario()
            );
            
            response.put("mensaje", "Login exitoso");
            response.put("status", HttpStatus.OK);
            response.put("data", loginResponse);
            
            return ResponseEntity.status(HttpStatus.OK).body(response);
            
        } catch (Exception e) {
            response.put("mensaje", "Error interno del servidor");
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> validateToken(String token) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            
            String correo = jwtUtil.getCorreoFromToken(token);
            
            if (jwtUtil.isTokenExpired(token)) {
                response.put("mensaje", "Token expirado");
                response.put("status", HttpStatus.UNAUTHORIZED);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
            Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreoAndEstadoTrue(correo);
            
            if (!usuarioOpt.isPresent()) {
                response.put("mensaje", "Usuario no encontrado o inactivo");
                response.put("status", HttpStatus.UNAUTHORIZED);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
            Usuario usuario = usuarioOpt.get();
            
            Map<String, Object> userData = new HashMap<>();
            userData.put("codigoUsuario", usuario.getCodigoUsuario());
            userData.put("nombreCompleto", usuario.getNombreCompleto());
            userData.put("correo", usuario.getCorreo());
            userData.put("tipoUsuario", usuario.getTipoUsuario());
            
            response.put("mensaje", "Token válido");
            response.put("status", HttpStatus.OK);
            response.put("data", userData);
            
            return ResponseEntity.status(HttpStatus.OK).body(response);
            
        } catch (Exception e) {
            response.put("mensaje", "Token inválido");
            response.put("status", HttpStatus.UNAUTHORIZED);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
    
    private String hashSHA256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al hashear la contraseña", e);
        }
    }
}
