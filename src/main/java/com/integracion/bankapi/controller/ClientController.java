package com.integracion.bankapi.controller;

import com.integracion.bankapi.model.dto.AccountDTO;
import com.integracion.bankapi.model.Client;
import com.integracion.bankapi.model.dto.ClientDTO;
import com.integracion.bankapi.model.dto.security.ClientPasswordUpdateDTO;
import com.integracion.bankapi.service.AccountService;
import com.integracion.bankapi.service.ClientService;
import com.integracion.bankapi.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clients")
@AllArgsConstructor
public class ClientController {

    private ClientService service;
    private AccountService accountService;
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createClient(@RequestBody Client client){

        Client createdClient = service.create(client);

        return new ResponseEntity<Client>(createdClient, HttpStatus.CREATED);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchClient(
            @RequestParam(required = false) String dni,
            @RequestParam(required = false) String cuil
    ) {

        ClientDTO client = service.getClientByDniOrCuil(dni, cuil);

        return ResponseEntity.ok(client);
    }

    @GetMapping("/{idClient}/accounts")
    public ResponseEntity<List<?>> getAccountByIdClient(@PathVariable Integer idClient){

        List<AccountDTO> accounts = accountService.getAccountByIdClient(idClient);

        return new ResponseEntity(accounts,HttpStatus.OK);
    }


    @GetMapping("/{idClient}")
    public ResponseEntity<?> getClientById(@PathVariable Integer idClient){

        ClientDTO client = service.getClientById(idClient);

        return ResponseEntity.ok(client);
    }

    @GetMapping("/search/cbu/{identificationNumber}")
    public ResponseEntity<?> searchClientByIdentificationNumber(@PathVariable String identificationNumber) {

        ClientDTO client = service.getClientByIdentificationNumber(identificationNumber);

        return ResponseEntity.ok(client);
    }


    @PatchMapping("/{clientId}")
    public ResponseEntity<?> updatePassword(
            @PathVariable Integer clientId,
            @RequestBody ClientPasswordUpdateDTO clientPasswordUpdateDTO
    ) {
        userService.updateUserPassword(clientId, clientPasswordUpdateDTO.getPassword());

        return ResponseEntity.ok().build();
    }

}