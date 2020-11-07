package com.integracion.bankapi.controller;


import com.integracion.bankapi.model.Account;
import com.integracion.bankapi.model.AccountDTO;
import com.integracion.bankapi.model.Client;
import com.integracion.bankapi.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {


    private AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody AccountDTO account){

        Account createdAccount = service.create(account);

        return new ResponseEntity<Account>(createdAccount, HttpStatus.CREATED);
    }
}
