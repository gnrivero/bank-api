package com.integracion.bankapi.model.exception;

public class ProblemDetail {

    private String message;

    public ProblemDetail(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}