package com.cibertec.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cibertec.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    Optional<Usuario> findByCorreo(String correo);
    
    @Query("SELECT u FROM Usuario u WHERE u.correo = :correo AND u.estado = true")
    Optional<Usuario> findByCorreoAndEstadoTrue(@Param("correo") String correo);
    
    boolean existsByCorreo(String correo);
    
    boolean existsByNumeroDocumento(String numeroDocumento);
}
