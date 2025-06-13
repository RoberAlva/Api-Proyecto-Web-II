package com.cibertec.model;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "prestamo")
public class Prestamo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "Codigo_Prestamo")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigoPrestamo;
	
	@Column(name = "Estado")
	private String estadoPres;

	@Column(name = "Fecha_Inicio")
	private LocalDate fecInicio;
	
	@Column(name = "Fecha_Fin")
	private LocalDate fecFinal;
	
	@ManyToOne
	@JoinColumn(name = "Codigo_Libro")
	private Libro libro;
	
	@ManyToOne
	@JoinColumn(name = "Codigo_Usuario")
	private Usuario usuario;

	
	public Prestamo() {
		
	}

	public Prestamo(Long codigoPrestamo, String estadoPres, LocalDate fecInicio, LocalDate fecFinal, Libro libro,
			Usuario usuario) {
		this.codigoPrestamo = codigoPrestamo;
		this.estadoPres = estadoPres;
		this.fecInicio = fecInicio;
		this.fecFinal = fecFinal;
		this.libro = libro;
		this.usuario = usuario;
	}
	
	
	
}

