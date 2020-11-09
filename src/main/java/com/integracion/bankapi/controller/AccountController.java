package com.integracion.bankapi.controller;


import com.integracion.bankapi.model.Account;
import com.integracion.bankapi.model.AccountDTO;
import com.integracion.bankapi.model.Client;
import com.integracion.bankapi.model.ClientDTO;
import com.integracion.bankapi.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {


    private AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody AccountDTO account){

        //En la creacion por las dudas le seteo el balance en 0
        account.setBalance(0);
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

        AccountDTO account = service.getAccountByIdentificationNumber(identificationNumber);
        if (account == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return ResponseEntity.ok(account);
        }
    }

    @GetMapping("/search/client/{idClient}")
    public ResponseEntity<List<?>> getAccountByIdClient(@PathVariable Integer idClient){

        List<AccountDTO> accounts = service.getAccountByIdClient(idClient);
        if (accounts == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity(accounts,HttpStatus.OK);

        }
    }
}
