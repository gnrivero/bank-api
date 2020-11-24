package com.integracion.bankapi.controller;

import com.integracion.bankapi.model.dto.ExternalPaymentDTO;
import com.integracion.bankapi.model.dto.TransferDTO;
import com.integracion.bankapi.service.TransferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/external")
public class ExternalController {

    private TransferService service;

    public ExternalController(TransferService service) {
        this.service = service;
    }

    @PostMapping("/salarypayment")
    public ResponseEntity<?> createSalaryTransfer(@RequestBody ExternalPaymentDTO salaryPaymentDTO){

        TransferDTO createdExternalPayment = service.createSalaryTransfer(salaryPaymentDTO);
        createdExternalPayment.setDestinationReferenceNumber(null);
        return new ResponseEntity<TransferDTO>(createdExternalPayment, HttpStatus.CREATED);
        ///return new ResponseEntity<String>(String.format("\"Pago de salario realizado a CBU: %s\"", salaryPaymentDTO.getCbu()), HttpStatus.CREATED);
    }


    @PostMapping("/supplierpayments")
    public ResponseEntity<?> createSupplierTransfer(@RequestBody ExternalPaymentDTO salaryPaymentDTO){

        TransferDTO createdExternalPayment = service.createSupplierTransfer(salaryPaymentDTO);
        createdExternalPayment.setDestinationReferenceNumber(null);
        return new ResponseEntity<TransferDTO>(createdExternalPayment, HttpStatus.CREATED);
        ///return new ResponseEntity<String>(String.format("\"Pago de salario realizado a CBU: %s\"", salaryPaymentDTO.getCbu()), HttpStatus.CREATED);
    }

    @PostMapping("/payment")
    public ResponseEntity<?> createPaymentTransfer(@RequestBody ExternalPaymentDTO paymentDTO){

        TransferDTO createdExternalPayment = service.createPaymentTransfer(paymentDTO);

        createdExternalPayment.setSourceReferenceNumber(null);
        return new ResponseEntity<TransferDTO>(createdExternalPayment, HttpStatus.CREATED);
        //return new ResponseEntity<String>(String.format("\"Pago realizado desde CBU: %s\"", paymentDTO.getCbu()), HttpStatus.CREATED);
    }

}