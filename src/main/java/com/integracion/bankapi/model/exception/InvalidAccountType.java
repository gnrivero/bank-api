package com.integracion.bankapi.model.exception;

public class InvalidAccountType extends RuntimeException {

    public InvalidAccountType() {
        super("Tipo de cuenta inválido");
    }

    public InvalidAccountType(String message) {
        super(message);
    }

}