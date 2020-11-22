package com.integracion.bankapi.model.exception;

public class ProviderNotFoundException extends RuntimeException {

    public ProviderNotFoundException() {
        super("No se encontro el Proveedor");
    }
}
