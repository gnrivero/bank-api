package com.integracion.bankapi.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExternalPaymentDTO {

    private String cbu;
    private String providerCode;
    private BigDecimal amount;
    private String detail;

}