package com.integracion.bankapi.controller;

import com.integracion.bankapi.model.dto.AccountDTO;
import com.integracion.bankapi.model.dto.TransactionAccountDTO;
import com.integracion.bankapi.service.AccountService;
import com.integracion.bankapi.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        if (account == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return ResponseEntity.ok(account);
        }
    }

    @GetMapping("/search/cbu/{identificationNumber}")
    public ResponseEntity<?> getAccountByIdentificationNumber(@PathVariable String identificationNumber){

        AccountDTO account = null;//service.getAccountByIdentificationNumber(identificationNumber);
        if (account == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(account);
        }
    }

    @GetMapping("/{idAccount}/transactions")
    public ResponseEntity<?> getTransactionByIdAccount(@PathVariable Integer idAccount){

        TransactionAccountDTO transactionsAccount = transactionService.getTransactionsByIdAccount(idAccount);
        if (transactionsAccount == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity(transactionsAccount,HttpStatus.OK);

        }
    }


}
