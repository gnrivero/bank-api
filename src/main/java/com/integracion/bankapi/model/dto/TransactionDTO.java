package com.integracion.bankapi.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TransactionDTO {

    private Integer id;
    private String detail;
    private BigDecimal amount;
    private Date date;
    private String transactionType;
    private Boolean cash;
    private String operationType;
    private Integer accountId;
    private String cbu;

}