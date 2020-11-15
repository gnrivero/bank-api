package com.integracion.bankapi.model;

import java.math.BigDecimal;

public enum TransactionType {

    DEPOSIT("DEPOSIT","Deposito", BigDecimal.ZERO),
    WITHDRAW("WITHDRAW","Extraccion",BigDecimal.ZERO),
    SERVICE_PAYMENT("PAYMENT","Cobranza servicio e impuestos", BigDecimal.valueOf(0.01)),
    FEE("FEE","Comision",BigDecimal.ZERO);

    private String transactionTypeName;
    private String shortName;
    private BigDecimal percent;

    private TransactionType(String shortName, String transactionTypeName, BigDecimal percent){
        this.transactionTypeName = transactionTypeName;
        this.percent = percent;
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }

    public String getTransactionTypeName() { return transactionTypeName; }

    public BigDecimal getPercent() { return percent; }

}