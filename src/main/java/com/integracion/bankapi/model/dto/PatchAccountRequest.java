package com.integracion.bankapi.model.dto;

import java.math.BigDecimal;

public class PatchAccountRequest {

    private BigDecimal overdraft;

    public BigDecimal getOverdraft() {
        return overdraft;
    }

    public void setOverdraft(BigDecimal overdraft) {
        this.overdraft = overdraft;
    }

}