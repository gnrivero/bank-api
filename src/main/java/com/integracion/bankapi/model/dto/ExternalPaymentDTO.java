package com.integracion.bankapi.model.dto;

import java.math.BigDecimal;

public class ExternalPaymentDTO {

    private String account;
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

    public String getAccount() { return account; }

    public void setAccount(String account) { this.account = account; }
}