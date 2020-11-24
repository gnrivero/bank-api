package com.integracion.bankapi.model.security;

import com.integracion.bankapi.model.Client;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    private String username;
    private String password;
    private Boolean enabled;
    private String type;
    @OneToOne
    @JoinColumn(name = "client_id")
    private Client client;
    /*@OneToOne(mappedBy = "user" ,
              fetch = FetchType.LAZY,
              cascade= CascadeType.ALL,
              orphanRemoval = true)
    private Authority authority;*/

}