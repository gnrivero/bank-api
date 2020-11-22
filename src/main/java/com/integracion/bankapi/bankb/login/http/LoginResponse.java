package com.integracion.bankapi.bankb.login.http;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.integracion.bankapi.bankb.login.model.User;
import lombok.Data;


@Data
public class LoginResponse {
    @JsonProperty(value = "message")
    private String message;
    @JsonProperty(value = "user")
    private User user;
}