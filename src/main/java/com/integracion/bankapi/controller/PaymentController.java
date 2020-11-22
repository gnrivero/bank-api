package com.integracion.bankapi.controller;

import com.integracion.bankapi.model.dto.PaymentDTO;
import com.integracion.bankapi.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return ResponseEntity.ok(payment);

    }

    @GetMapping("/search/electronicCode/{electronicCode}/paid")
    public ResponseEntity<List<?>> getPaymentPaidByElectronicCode(@PathVariable String electronicCode){

        List<PaymentDTO> payments = service.getPaymentPaidByElectronicCode(electronicCode);

        return new ResponseEntity(payments,HttpStatus.OK);
    }


    @PostMapping("/generatePayments/{providerCode}")
    public ResponseEntity generatePayments(@PathVariable String providerCode,@RequestBody List<PaymentDTO> listPayment){

        service.generatePayments(providerCode,listPayment);
        return ResponseEntity.ok("ok");
    }

    @PostMapping("/paymentsTest")
    public ResponseEntity<?> createPaymentTest(@RequestBody PaymentDTO payment){

        PaymentDTO createdPayment = service.createTest(payment);

        if (createdPayment == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<PaymentDTO>(createdPayment, HttpStatus.CREATED);
        }
    }

}
