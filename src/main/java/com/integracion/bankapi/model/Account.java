package com.integracion.bankapi.model;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private BigDecimal balance;
    private String accountType;
    private String identificationNumber;
    private BigDecimal overdraft;
    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany( mappedBy = "account" , fetch = FetchType.LAZY,
            cascade= CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions = new ArrayList<>();

    public Account() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getBalance() { return  balance; }

    public void setBalance(BigDecimal balance) { this.balance = balance; }

    public String getAccountType() { return accountType; }

    public void setAccountType(String accountType) { this.accountType = accountType; }

    public String getIdentificationNumber() { return identificationNumber; }

    public void setIdentificationNumber(String identificationNumber) { this.identificationNumber = identificationNumber; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public BigDecimal getOverdraft() { return overdraft; }

    public void setOverdraft(BigDecimal overdraft) { this.overdraft = overdraft; }

    public Boolean getActive() { return active; }

    public void setActive(Boolean active) { this.active = active; }

    public Client getClient() { return client; }

    public void setClient(Client client) { this.client = client; }

    public List<Transaction> getTransactions() { return transactions; }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions.addAll(transactions);

        for (Transaction transaction: transactions)
            transaction.setAccount(this);

        this.transactions = transactions;
    }

}