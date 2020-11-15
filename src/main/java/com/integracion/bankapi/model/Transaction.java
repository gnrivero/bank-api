package com.integracion.bankapi.model;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String detail;
    private BigDecimal amount;
    private Date date;
    private String transactionType;
    private Boolean cash;
    private String operationType;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public Transaction() {
    }

    public static double withMathRound(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getDetail() { return detail; }

    public void setDetail(String detail) { this.detail = detail; }

    public BigDecimal getAmount() { return amount; }

    public void setAmount(BigDecimal amount) { this.amount = amount;}

    public Date getDate() {return date; }

    public void setDate(Date date) { this.date = date; }

    public String getTransactionType() { return transactionType; }

    public void setTransactionType(String transactionType) { this.transactionType = transactionType; }

    public Account getAccount() { return account; }

    public void setAccount(Account account) { this.account = account; }

    public Boolean getCash() { return cash; }

    public void setCash(Boolean cash) { this.cash = cash; }

    public String getOperationType() {return operationType; }

    public void setOperationType(String operationType) { this.operationType = operationType; }
}
