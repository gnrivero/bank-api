package com.integracion.bankapi.model.exception;

public class ProviderNotFoundException extends RuntimeException {

    public ProviderNotFoundException(String message) {
        super("No se encontro el Proveedor");
    }
}
