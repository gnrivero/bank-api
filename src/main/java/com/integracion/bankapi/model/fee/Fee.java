package com.integracion.bankapi.model.fee;

import com.integracion.bankapi.model.AccountType;

public class Fee {

    private String name;
    private AccountType accountType;
    private Double percentage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

}