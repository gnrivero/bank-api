package com.integracion.bankapi.controller;

import com.integracion.bankapi.model.AccountDTO;
import com.integracion.bankapi.model.Client;
import com.integracion.bankapi.model.ClientDTO;
import com.integracion.bankapi.service.AccountService;
import com.integracion.bankapi.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {

    private ClientService service;
    private AccountService accountService;

    public ClientController(ClientService service, AccountService accountService) {
        this.service = service;
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<?> createClient(@RequestBody Client client){

        Client createdClient = service.create(client);

        return new ResponseEntity<Client>(client, HttpStatus.CREATED);
    }

    @GetMapping("/search/dni/{dni}")
    public ResponseEntity<?> getClientByDni(@PathVariable Integer dni){

        ClientDTO client = service.getClientByDni(dni);
        if (client == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return ResponseEntity.ok(client);
        }
    }

    @PostMapping("/edit")
    public ResponseEntity<?> edit(@RequestBody ClientDTO clientEdit){

        ClientDTO client = service.edit(clientEdit);
        if (client == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return ResponseEntity.ok(client);
        }
    }

    @GetMapping("/{idClient}/accounts")
    public ResponseEntity<List<?>> getAccountByIdClient(@PathVariable Integer idClient){

        List<AccountDTO> accounts = accountService.getAccountByIdClient(idClient);
        if (accounts == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity(accounts,HttpStatus.OK);

        }
    }
}