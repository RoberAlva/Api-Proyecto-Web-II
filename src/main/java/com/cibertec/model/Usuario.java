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
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigoUsuario;

    @Column(nullable = false)
    private String tipoDocumento;

    @Column(nullable = false, unique = true, length = 50)
    private String numeroDocumento;

    @Column(nullable = false, length = 255)
    private String nombreCompleto;

    @Column(nullable = false, unique = true, length = 255)
    private String correo;

    @Column(nullable = false, length = 255)
    private String contrasena;

    @Column(nullable = false)
    private String tipoUsuario;

    @Column(nullable = false)
    private Boolean estado = true;

    @Column(nullable = false)
    private LocalDate fechaCreacion;
    
}