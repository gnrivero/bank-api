package com.integracion.bankapi.model.exception;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException() {
        super("No se encontro la cuenta");
    }

}