package com.integracion.bankapi.model.dto.security;


import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class UserDTO {

    private String username;
    private String type;
    private String cuil;
    private Integer clientId;
    private String name;

}
