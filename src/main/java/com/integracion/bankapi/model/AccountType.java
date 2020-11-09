package com.integracion.bankapi.model;

public enum AccountType
{
    CA("Caja de ahorro",30),
    CC("Cuenta corriente",80);

    private String accountTypeName;
    private double interest;

    private AccountType (String accountTypeName, double interest){
        this.accountTypeName = accountTypeName;
        this.interest = interest;
    }

    public String getAccountTypeName() {
        return accountTypeName;
    }

    public double getInterest() {
        return interest;
    }

}