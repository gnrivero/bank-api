package com.integracion.bankapi.controller;

import com.integracion.bankapi.model.Client;
import com.integracion.bankapi.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {

    private ClientService service;

    public ClientController(ClientService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> createClient(@RequestBody Client client){

        Client createdClient = service.create(client);

        return new ResponseEntity<Client>(client, HttpStatus.CREATED);
    }

}