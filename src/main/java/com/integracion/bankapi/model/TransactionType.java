package com.integracion.bankapi.model;

public enum TransactionType
{
    DEPOSITO("DEP","Deposito",0),
    EXTRACCION("EXT","Extraccion",0),
    COBRANZA("COB","Cobranza servicio e impuestos",1),
    COMISION("COM","Comision",0);


    private String transactionTypeName;
    private String shortName;
    private double percent;

    private TransactionType(String shortName, String transactionTypeName, double percent){
        this.transactionTypeName = transactionTypeName;
        this.percent = percent;
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }

    public String getTransactionTypeName() { return transactionTypeName; }

    public double getPercent() { return percent; }

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