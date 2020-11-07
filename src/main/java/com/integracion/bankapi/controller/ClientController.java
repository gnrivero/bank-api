package com.integracion.bankapi.controller;

import com.integracion.bankapi.model.Client;
import com.integracion.bankapi.model.ClientDTO;
import com.integracion.bankapi.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/search/dni/{dni}")
    public ResponseEntity<?> getClientByDni(@PathVariable Integer dni){

        ClientDTO client = service.getClientByDni(dni);

        return ResponseEntity.ok(client);
    }

    @PostMapping("/edit")
    public ResponseEntity<?> edit(@RequestBody ClientDTO client){

        ClientDTO clientEdit = service.edit(client);

        return ResponseEntity.ok(clientEdit);
    }


}