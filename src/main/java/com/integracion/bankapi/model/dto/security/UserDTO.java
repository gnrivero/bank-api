package com.integracion.bankapi.model.dto.security;


import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class UserDTO {

    private String username;

}
