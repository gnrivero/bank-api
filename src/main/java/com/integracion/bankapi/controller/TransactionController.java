package com.integracion.bankapi.controller;

import com.integracion.bankapi.model.dto.TransactionDTO;
import com.integracion.bankapi.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> createTransaction(@RequestBody TransactionDTO transaction){

        TransactionDTO createdTransaction = service.create(transaction);

        if (createdTransaction == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<TransactionDTO>(createdTransaction, HttpStatus.CREATED);
        }
    }

}
