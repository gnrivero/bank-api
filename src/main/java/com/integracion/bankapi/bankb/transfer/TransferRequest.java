package com.integracion.bankapi.bankb.transfer;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder(toBuilder = true)
public class TransferRequest {
    private String cbu;
    private BigDecimal cantidad;
    private String concepto;
    private String descripcion;
}