package com.integracion.bankapi.model.exception;

public class PaymentExpireException extends RuntimeException {

    public PaymentExpireException() {
        super("El comprobante no es puede cobrar. Esta pago o vencido");
    }


}