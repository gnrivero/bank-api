package com.integracion.bankapi.model;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "providers")
public class Provider {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;
        private String providerCode;
        private String name;
        @OneToOne
        @JoinColumn(name = "account_id")
        private Account account;

        public Integer getId() { return id; }

        public void setId(Integer id) { this.id = id; }

        public String getProviderCode() {
                return providerCode;
        }

        public void setProviderCode(String providerCode) {
                this.providerCode = providerCode;
        }

        public Account getAccount() {
                return account;
        }

        public void setAccount(Account account) {
                this.account = account;
        }

        public String getName() { return name; }

        public void setName(String name) { this.name = name; }

}
