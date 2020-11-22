package com.integracion.bankapi.bankb.login.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class User {
    @JsonProperty("nombre_usuario")
    private String nombreUsuario;
    @JsonProperty("entidad")
    private Entidad entidad;
    @JsonProperty("rol")
    private String rol;
    @JsonProperty("x-access-token")
    private String token;
}