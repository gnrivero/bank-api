package com.integracion.bankapi.model;

public class AccountDTO {

    private Integer id;
    private String cbu;
    private Integer clientId;


    public AccountDTO() {
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getCbu() { return cbu; }

    public void setCbu(String cbu) { this.cbu = cbu; }

    public Integer getClientId() { return clientId; }

    public void setClientId(Integer clientId) { this.clientId = clientId; }
}
