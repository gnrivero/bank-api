package com.integracion.bankapi.controller;

import com.integracion.bankapi.model.dto.ProviderDTO;
import com.integracion.bankapi.service.ProviderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/providers")
public class ProviderController {

    private ProviderService service;

    public ProviderController(ProviderService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> createProvider(@RequestBody ProviderDTO provider){

        ProviderDTO createdProvider = service.create(provider);

        return new ResponseEntity<ProviderDTO>(createdProvider, HttpStatus.CREATED);
    }
/*
    @PostMapping("/edit")
    public ResponseEntity<?> edit(@RequestBody ProviderDTO providerEdit){

        ProviderDTO editProvider = service.edit(providerEdit);
        if (editProvider == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return ResponseEntity.ok(editProvider);
        }
    }
*/
    @GetMapping("/{idProvider}")
    public ResponseEntity<?> getClientById(@PathVariable Integer idProvider){

        ProviderDTO provider = service.getProviderById(idProvider);
        if (provider == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return ResponseEntity.ok(provider);
        }
    }
}