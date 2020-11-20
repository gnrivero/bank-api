package com.integracion.bankapi.model.dto;

import java.math.BigDecimal;

public class TransferDTO {

    private String sourceAccount;
    private String destinationAccount;
    private BigDecimal amount;
    private String detailSourceAccount;
    private String detailDestinationAccount;

    public String getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(String sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public String getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(String destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDetailSourceAccount() { return detailSourceAccount; }

    public void setDetailSourceAccount(String detailSourceAccount) { this.detailSourceAccount = detailSourceAccount; }

    public String getDetailDestinationAccount() { return detailDestinationAccount; }

    public void setDetailDestinationAccount(String detailDestinationAccount) { this.detailDestinationAccount = detailDestinationAccount; }
}