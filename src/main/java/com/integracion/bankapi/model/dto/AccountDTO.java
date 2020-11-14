package com.integracion.bankapi.model.dto;

import java.math.BigDecimal;

public class AccountDTO {

    private Integer id;
    private String identificationNumber;
    private String name;
    private Integer clientId;
    private String ClientCuil;
    private BigDecimal balance;
    private String accountType;
    private String accountTypeDescription;
    private BigDecimal overdraft;
    private Boolean active;

    public AccountDTO() {
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getIdentificationNumber() { return identificationNumber; }

    public void setIdentificationNumber(String identificationNumber) { this.identificationNumber = identificationNumber; }

    public Integer getClientId() { return clientId; }

    public void setClientId(Integer clientId) { this.clientId = clientId; }

    public BigDecimal getBalance() { return balance; }

    public void setBalance(BigDecimal balance) { this.balance = balance; }

    public String getAccountType() { return accountType; }

    public void setAccountType(String accountType) { this.accountType = accountType; }

    public String getAccountTypeDescription() { return accountTypeDescription; }

    public void setAccountTypeDescription(String accountTypeDescription) {
        this.accountTypeDescription = accountTypeDescription;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Boolean getActive() { return active; }

    public void setActive(Boolean active) { this.active = active; }

    public BigDecimal getOverdraft() { return overdraft; }

    public void setOverdraft(BigDecimal overdraft) { this.overdraft = overdraft; }

    public String getClientCuil() { return ClientCuil; }

    public void setClientCuil(String clientCuil) { ClientCuil = clientCuil; }
}
