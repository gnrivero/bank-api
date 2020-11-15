package com.integracion.bankapi.controller;

import com.integracion.bankapi.model.dto.AccountDTO;
import com.integracion.bankapi.model.dto.PatchAccountRequest;
import com.integracion.bankapi.model.dto.TransactionAccountDTO;
import com.integracion.bankapi.service.AccountService;
import com.integracion.bankapi.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private AccountService service;
    private TransactionService transactionService;

    public AccountController(AccountService service, TransactionService transactionService) {
        this.service = service;
        this.transactionService = transactionService;

    }

    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody AccountDTO account){

        AccountDTO createdAccount = service.create(account);

        return new ResponseEntity<AccountDTO>(createdAccount, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccountById(@PathVariable Integer id){

        AccountDTO account = service.getAccountById(id);

        return ResponseEntity.ok(account);
    }

    @GetMapping("/search/cbu/{identificationNumber}")
    public ResponseEntity<?> getAccountByIdentificationNumber(@PathVariable String identificationNumber){

        AccountDTO account = service.getAccountByIdentificationNumber(identificationNumber);

        return ResponseEntity.ok(account);
    }

    @GetMapping("/{id}/transactions")
    public ResponseEntity<?> getTransactionByIdAccount(@PathVariable Integer id){

        TransactionAccountDTO transactionsAccount = transactionService.getTransactionsByIdAccount(id);
        if (transactionsAccount == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity(transactionsAccount,HttpStatus.OK);

        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateAccountOverdraft(
            @PathVariable Integer id,
            @RequestBody PatchAccountRequest body
    ){

        service.patchAccount(body.getOverdraft(), id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}