package com.integracion.bankapi.controller;

import com.integracion.bankapi.bankb.login.http.LoginResponse;
import com.integracion.bankapi.model.dto.TransferDTO;
import com.integracion.bankapi.service.TransferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transfers")
public class TransferController {

    private TransferService service;

    public TransferController(TransferService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> createTransfer(@RequestBody TransferDTO transfer){

        TransferDTO createdTransaction = service.createTransfer(transfer);

        return new ResponseEntity<TransferDTO>(createdTransaction, HttpStatus.CREATED);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> createTransfer(){

        String createdTransaction = service.dummyConnection();

        return new ResponseEntity<String>(createdTransaction, HttpStatus.CREATED);
    }

}