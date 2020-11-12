package com.integracion.bankapi.model;

public class AccountDTO {

    private Integer id;
    private String identificationNumber;
    private String name;
    private Integer clientId;
    private String ClientCuil;
    private double balance;
    private String accountType;
    private String accountTypeDescription;
    private double overdraft;
    private Boolean status;

    public AccountDTO() {
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getIdentificationNumber() { return identificationNumber; }

    public void setIdentificationNumber(String identificationNumber) { this.identificationNumber = identificationNumber; }

    public Integer getClientId() { return clientId; }

    public void setClientId(Integer clientId) { this.clientId = clientId; }

    public double getBalance() { return balance; }

    public void setBalance(double balance) { this.balance = balance; }

    public String getAccountType() { return accountType; }

    public void setAccountType(String accountType) { this.accountType = accountType; }

    public String getAccountTypeDescription() { return accountTypeDescription; }

    public void setAccountTypeDescription(String accountTypeDescription) {
        this.accountTypeDescription = accountTypeDescription;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Boolean getStatus() { return status; }

    public void setStatus(Boolean status) { this.status = status; }

    public double getOverdraft() { return overdraft; }

    public void setOverdraft(double overdraft) { this.overdraft = overdraft; }

    public String getClientCuil() { return ClientCuil; }

    public void setClientCuil(String clientCuil) { ClientCuil = clientCuil; }
}
