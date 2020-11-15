package com.integracion.bankapi.model.exception;

public class AccountLimitSurpassedException extends RuntimeException {

    public AccountLimitSurpassedException() {
        super("El monto de la transaccion supera el saldo de la cuenta");
    }


}