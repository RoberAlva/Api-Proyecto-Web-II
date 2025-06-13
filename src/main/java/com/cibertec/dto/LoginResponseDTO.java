package com.cibertec.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDTO {
    private String token;
    private String tipoUsuario;
    private String nombreCompleto;
    private String correo;
    private Long codigoUsuario;
}
