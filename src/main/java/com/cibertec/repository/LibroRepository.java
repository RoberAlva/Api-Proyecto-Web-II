package com.cibertec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cibertec.model.Libro;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
	

}
