package com.integracion.bankapi.model;

import java.util.Date;

public class TransactionDTO {

    private Integer id;
    private String detail;
    private double amount;
    private Date date;
    private String transactionType;
    private Boolean cash;
    private String typeOperation;
    private Integer accountId;
    private Integer accountOriginId;

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getDetail() { return detail; }

    public void setDetail(String detail) { this.detail = detail; }

    public double getAmount() { return amount; }

    public void setAmount(double amount) { this.amount = amount; }

    public Date getDate() { return date; }

    public void setDate(Date date) { this.date = date; }

    public String getTransactionType() { return transactionType; }

    public void setTransactionType(String transactionType) { this.transactionType = transactionType; }

    public Integer getAccountId() { return accountId; }

    public void setAccountId(Integer accountId) { this.accountId = accountId; }

    public Integer getAccountOriginId() { return accountOriginId; }

    public void setAccountOriginId(Integer accountOriginId) { this.accountOriginId = accountOriginId; }

    public String getTypeOperation() { return typeOperation; }

    public void setTypeOperation(String typeOperation) { this.typeOperation = typeOperation; }

    public Boolean getCash() { return cash; }

    public void setCash(Boolean cash) { this.cash = cash; }

}
