package com.integracion.bankapi.model;

import java.math.BigDecimal;

public enum TransactionType {
    DEPOSITO("DEP","Deposito", BigDecimal.ZERO),
    EXTRACCION("EXT","Extraccion",BigDecimal.ZERO),
    COBRANZA("COB","Cobranza servicio e impuestos",BigDecimal.valueOf(0.01)),
    COMISION("COM","Comision",BigDecimal.ZERO);

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
/*
public enum Type
{
    INGRESO("I","Ingreso"),
    EGRESO("E","Egreso");


    private String typeName;
    private String shortName;

    private Type(String shortName, String typeName){
        this.typeName = typeName;
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }

    public String getTypeName() { return typeName; }


}*/