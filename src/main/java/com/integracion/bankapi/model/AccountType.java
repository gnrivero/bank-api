package com.integracion.bankapi.model;

public enum AccountType
{
    CA("CA","Caja de Ahorro",30),
    CC("CC","Cuenta Corriente",80);

    private String accountTypeName;
    private String shortName;
    private double interest;

    private AccountType (String shortName, String accountTypeName, double interest){
        this.accountTypeName = accountTypeName;
        this.interest = interest;
        this.shortName = shortName;
    }

    public String getAccountTypeName() {
        return accountTypeName;
    }

    public double getInterest() {
        return interest;
    }

    public String getShortName() {
        return shortName;
    }

}