package com.integracion.bankapi.bankb.login.http;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class LoginRequest {

    @JsonProperty("nombre_usuario")
    private final String nombreUsuario;
    private final String clave;

}