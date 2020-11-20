package com.integracion.bankapi.model.mapper;

import com.integracion.bankapi.model.Transaction;
import com.integracion.bankapi.model.dto.TransactionDTO;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public Transaction toTransaction(TransactionDTO transactionDTO) {
        Transaction transaction = new Transaction();
        transaction.setId(transactionDTO.getId());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setDetail(transactionDTO.getDetail());
        transaction.setTransactionType(transactionDTO.getTransactionType());
        transaction.setCash(transactionDTO.getCash());
        transaction.setOperationType(transactionDTO.getOperationType());
        transaction.setDate(transactionDTO.getDate());
        return transaction;
    }

    public TransactionDTO toDTO(Transaction transaction) {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(transaction.getId());
        transactionDTO.setAmount(transaction.getAmount());
        transactionDTO.setDetail(transaction.getDetail());
        transactionDTO.setTransactionType(transaction.getTransactionType());
        transactionDTO.setOperationType(transaction.getOperationType());
        transactionDTO.setDate(transaction.getDate());
        return transactionDTO;
    }

}