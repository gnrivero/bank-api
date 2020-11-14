package com.integracion.bankapi.model.dto;

public class ProviderDTO {

    private Integer id;
    private String providerCode;
    private String name;
    private Integer accountId;

    public ProviderDTO() {
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getProviderCode() { return providerCode; }

    public void setProviderCode(String providerCode) { this.providerCode = providerCode; }

    public Integer getAccountId() { return accountId; }
}
