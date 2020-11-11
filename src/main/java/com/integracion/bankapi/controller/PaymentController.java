package com.integracion.bankapi.controller;

import com.integracion.bankapi.model.PaymentDTO;
import com.integracion.bankapi.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> createPayment(@RequestBody PaymentDTO payment){

        PaymentDTO createdPayment = service.create(payment);

        if (createdPayment == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<PaymentDTO>(createdPayment, HttpStatus.CREATED);
        }
    }

    @GetMapping("/search/electronicCode/{electronicCode}")
    public ResponseEntity<?> getPaymentByElectronicCode(@PathVariable String electronicCode){

        PaymentDTO payment = service.getPaymentByElectronicCode(electronicCode);
        if (payment == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return ResponseEntity.ok(payment);
        }
    }
}
