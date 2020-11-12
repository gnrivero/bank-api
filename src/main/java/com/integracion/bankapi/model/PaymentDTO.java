package com.integracion.bankapi.model;

import java.time.LocalDate;
import java.util.Date;

public class PaymentDTO {

    private Integer id;
    private LocalDate date;
    private Boolean cash;
    private Integer accountId;
    private String electronicCode;
    private double amount;
    private Boolean paid;
    private Integer providerId;

    public PaymentDTO() {
    }


    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public LocalDate getDate() { return date; }

    public void setDate(LocalDate date) { this.date = date; }

    public Integer getAccountId() { return accountId; }

    public void setAccountId(Integer accountId) { this.accountId = accountId; }

    public Boolean getCash() { return cash; }

    public void setCash(Boolean cash) { this.cash = cash; }

    public String getElectronicCode() { return electronicCode; }

    public void setElectronicCode(String electronicCode) { this.electronicCode = electronicCode; }

    public double getAmount() { return amount; }

    public void setAmount(double amount) { this.amount = amount; }

    public Boolean getPaid() { return paid; }

    public void setPaid(Boolean paid) { this.paid = paid; }

    public Integer getProviderId() { return providerId; }

    public void setProviderId(Integer providerId) { this.providerId = providerId; }
}
