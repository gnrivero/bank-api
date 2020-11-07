package com.integracion.bankapi.model;

public class AccountDTO {

    private Integer id;
    private String CBU;
    private Integer clientId;


    public AccountDTO() {
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getCBU() { return CBU; }

    public void setCBU(String CBU) { this.CBU = CBU; }

    public Integer getClientId() { return clientId; }

    public void setClientId(Integer clientId) { this.clientId = clientId; }
}
