package com.integracion.bankapi.model;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private double balance;
    private String accountType;
    private String identificationNumber;
    private double overdraft;
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    public Account() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getBalance() { return  withMathRound(balance,2); }

    public void setBalance(double balance) { this.balance = withMathRound(balance,2); }


    public static double withMathRound(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

    public String getAccountType() { return accountType; }

    public void setAccountType(String accountType) { this.accountType = accountType; }

    public String getIdentificationNumber() { return identificationNumber; }

    public void setIdentificationNumber(String identificationNumber) { this.identificationNumber = identificationNumber; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public double getOverdraft() { return overdraft; }

    public void setOverdraft(double overdraft) { this.overdraft = overdraft; }

    public Boolean getStatus() { return status; }

    public void setStatus(Boolean status) { this.status = status; }

    public Client getClient() { return client; }

    public void setClient(Client client) { this.client = client; }
}
