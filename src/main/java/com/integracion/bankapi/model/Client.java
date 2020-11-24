package com.integracion.bankapi.model;

import com.integracion.bankapi.model.security.User;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String lastName;
    private String businessName;
    private String dni;
    private String cuil;
    private String email;
    private Boolean active;

    @OneToOne(mappedBy = "client" ,
              fetch = FetchType.LAZY,
              cascade= CascadeType.ALL,
              orphanRemoval = true)
    private User user;

    @OneToMany( mappedBy = "client" , fetch = FetchType.LAZY,
            cascade= CascadeType.ALL, orphanRemoval = true)
    private List<Account> accounts = new ArrayList<>();

    public Client() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getCuil() {
        return cuil;
    }

    public void setCuil(String cuil) {
        this.cuil = cuil;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts.addAll(accounts);
        for (Account account: accounts)
            account.setClient(this);
    }

    public Boolean getActive() { return active; }

    public void setActive(Boolean active) { this.active = active; }
}