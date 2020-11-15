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
@RequestMapping("/api/v1/withdraws")
public class WithdrawController {

    private TransactionService service;

    public WithdrawController(TransactionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> createWithdraw(@RequestBody TransactionDTO transaction){

        TransactionDTO createdTransaction = service.createWithdraw(transaction);

        return new ResponseEntity<TransactionDTO>(createdTransaction, HttpStatus.CREATED);

    }

}