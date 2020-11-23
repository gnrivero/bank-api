package com.integracion.bankapi.controller;

import com.integracion.bankapi.model.dto.ExternalPaymentDTO;
import com.integracion.bankapi.model.dto.TransferDTO;
import com.integracion.bankapi.service.FeeService;
import com.integracion.bankapi.service.TransferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/fee")
public class FeeController {

    private FeeService service;

    public FeeController(FeeService service) {
        this.service = service;
    }

    @GetMapping("/monthly")
    public ResponseEntity<?> chargeMonthly(){

        service.chargeMonthly();
        return  new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("/daily")
    public ResponseEntity<?> chargeDaily(){

        service.chargeDaily();
        return  new ResponseEntity<>(HttpStatus.OK);
    }

}