package com.cibertec.model;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "libro")
public class Libro {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Codigo_Libro")
    private Long codigoLibro;
    
    @Column(name = "Nombre_Libro", nullable = false, length = 255)
    private String nombreLibro;
    
    @Column(name = "Genero", length = 100)
    private String genero;
    
    @Column(name = "Stock", nullable = false)
    private Integer stock;
    
    @Column(name = "Autor", length = 255)
    private String autor;
    
    @Column(name = "Anio_Publicacion")
    private LocalDate anioPublicacion;
    
    @Column(name = "Editorial", length = 255)
    private String editorial;
    
    @Column(name = "Estado", nullable = false)
    private Boolean estado = true;
    
    @Column(name = "Descripcion", columnDefinition = "TEXT")
    private String descripcion;
    
    @Column(name = "Fecha_Creacion", nullable = false)
    private LocalDate fechaCreacion;
    
    // Constructor por defecto
    public Libro() {
        this.fechaCreacion = LocalDate.now();
    }
}
