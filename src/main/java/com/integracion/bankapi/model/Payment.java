package com.integracion.bankapi.model;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "payments")
public class Payment {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;
        private String electronicCode;
        private double amount;
        private Date date;
        private Boolean paid;
}
