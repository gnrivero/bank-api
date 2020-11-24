package com.integracion.bankapi.model.exception;

public class InvalidUserException extends RuntimeException {

    public InvalidUserException() {
        super("Invalid User");
    }

}