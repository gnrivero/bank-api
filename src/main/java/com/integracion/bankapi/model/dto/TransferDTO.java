package com.integracion.bankapi.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferDTO {

    private String sourceAccount;
    private String destinationAccount;
    private BigDecimal amount;
    private String detailSourceAccount;
    private String detailDestinationAccount;
    private Integer sourceReferenceNumber;
    private Integer destinationReferenceNumber;

}