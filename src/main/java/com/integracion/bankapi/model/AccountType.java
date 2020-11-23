package com.integracion.bankapi.model;

import java.math.BigDecimal;

public enum AccountType
{
    CA("CA","Caja de Ahorro",new BigDecimal(0.03),new BigDecimal(0)),
    CC("CC","Cuenta Corriente",new BigDecimal(0.08), new BigDecimal(200));

    private String accountTypeName;
    private String shortName;
    private BigDecimal interest;
    private BigDecimal fee;

    private AccountType (String shortName, String accountTypeName, BigDecimal interest, BigDecimal fee){
        this.accountTypeName = accountTypeName;
        this.interest = interest;
        this.shortName = shortName;
        this.fee = fee;
    }

    public String getAccountTypeName() {
        return accountTypeName;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public String getShortName() {
        return shortName;
    }

    public BigDecimal getFee() {
        return fee;
    }

}