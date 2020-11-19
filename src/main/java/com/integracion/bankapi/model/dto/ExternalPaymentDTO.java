package com.integracion.bankapi.model.dto;

import java.math.BigDecimal;

public class ExternalPaymentDTO {

    private String cbu;
    private String providerCode;
    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getProviderCode() { return providerCode; }

    public void setProviderCode(String providerCode) { this.providerCode = providerCode; }

    public String getCbu() { return cbu; }

    public void setCbu(String cbu) { this.cbu = cbu; }
}