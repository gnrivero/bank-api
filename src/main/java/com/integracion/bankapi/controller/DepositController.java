package com.integracion.bankapi.controller;

import com.integracion.bankapi.model.dto.TransactionDTO;
import com.integracion.bankapi.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/deposits")
public class DepositController {

    private TransactionService service;

    public DepositController(TransactionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> createDeposit(@RequestBody TransactionDTO transaction){

        TransactionDTO createdTransaction = service.createDeposit(transaction);

        return new ResponseEntity<TransactionDTO>(createdTransaction, HttpStatus.CREATED);

    }

}