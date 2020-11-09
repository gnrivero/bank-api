package com.integracion.bankapi.model;

import java.util.Date;

public class TransactionDTO {

    private Integer id;
    private String detail;
    private double amount;
    private Date date;
    private String transactionType;
    private Integer accountId;

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
}
