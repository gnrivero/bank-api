package com.integracion.bankapi.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;


@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String electronicCode;
    private BigDecimal amount;
    private LocalDate date;
    private Boolean paid;

    @ManyToOne
    @JoinColumn(name = "provider_id")
    private Provider provider;

    public Payment() {
    }

    public static double withMathRound(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getElectronicCode() { return electronicCode; }

    public void setElectronicCode(String electronicCode) { this.electronicCode = electronicCode; }

    public BigDecimal getAmount() { return amount; }

    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public LocalDate getDate() { return date; }

    public void setDate(LocalDate date) { this.date = date; }

    public Boolean getPaid() { return paid; }

    public void setPaid(Boolean paid) { this.paid = paid; }

    public Provider getProvider() { return provider; }

    public void setProvider(Provider provider) { this.provider = provider; }

}