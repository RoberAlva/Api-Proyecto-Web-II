package com.cibertec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cibertec.model.Prestamo;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long>{
	long countByUsuarioCorreo(String correo);
}
