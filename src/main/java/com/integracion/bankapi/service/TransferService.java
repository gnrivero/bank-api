package com.integracion.bankapi.service;

import com.integracion.bankapi.model.dto.*;
import org.springframework.stereotype.Service;

@Service
public class TransferService {

    private TransactionService txService;
    private AccountService accountService;
    private ProviderService providerService;

    public TransferService(TransactionService txService, AccountService accountService, ProviderService providerService) {
        this.txService = txService;
        this.accountService = accountService;
        this.providerService = providerService;
    }

    public TransferDTO createTransfer(TransferDTO transferDTO) {

        TransactionDTO withdraw = new TransactionDTO();
        withdraw.setCash(false);
        withdraw.setTransactionType("TRANSFER");
        withdraw.setAmount(transferDTO.getAmount());

        if(transferDTO.getSourceAccount().startsWith("456")){
        //Cuenta origen interna
            AccountDTO account = accountService.getAccountByIdentificationNumber(transferDTO.getSourceAccount());
            withdraw.setAccountId(account.getId());
            if (transferDTO.getDetailSourceAccount() == null)
                withdraw.setDetail(String.format("Transferenca a cuenta CBU %s", transferDTO.getDestinationAccount()));
            else
                withdraw.setDetail(transferDTO.getDetailSourceAccount());
        }else{
        /*
         TODO: validar con el otro banco. Creo que no haria falta
        */
        }

        TransactionDTO deposit = null;
        if(transferDTO.getDestinationAccount().startsWith("456")){
            //Cuenta destion interna
            deposit = new TransactionDTO();
            AccountDTO account = accountService.getAccountByIdentificationNumber(transferDTO.getDestinationAccount());
            deposit.setCash(false);
            deposit.setTransactionType("TRANSFER");
            deposit.setAmount(transferDTO.getAmount());
            deposit.setAccountId(account.getId());
            if(transferDTO.getDetailDestinationAccount() == null)
                deposit.setDetail(String.format("Transferenca desde cuenta CBU %s", transferDTO.getSourceAccount()));
            else
                deposit.setDetail(transferDTO.getDetailDestinationAccount());
        }else{
          /*
         TODO: validar con el otro banco. Si falla devolver una exception
        */
        }
        txService.createWithdraw(withdraw);
        if(deposit != null)
            txService.createDeposit(deposit);

        return transferDTO;
    }


    public TransferDTO createSalaryTransfer(ExternalPaymentDTO salaryPaymentDTO) {
        ProviderDTO provider = providerService.getProviderByProviderCode(salaryPaymentDTO.getProviderCode());
        AccountDTO accountProvider = accountService.getAccountById(provider.getAccountId());
        TransferDTO t = new TransferDTO();
        t.setSourceAccount(accountProvider.getIdentificationNumber());
        t.setDestinationAccount(salaryPaymentDTO.getCbu());
        t.setAmount(salaryPaymentDTO.getAmount());
        t.setDetailSourceAccount(String.format("Pago sueldo a CBU %s", salaryPaymentDTO.getCbu()));
        t.setDetailDestinationAccount(String.format("Acreditacion sueldo a cuenta de %s", provider.getName()));

        return this.createTransfer(t);

    }

    public TransferDTO createPaymentTransfer(ExternalPaymentDTO salaryPaymentDTO) {
        ProviderDTO provider = providerService.getProviderByProviderCode(salaryPaymentDTO.getProviderCode());
        AccountDTO accountProvider = accountService.getAccountById(provider.getAccountId());
        TransferDTO t = new TransferDTO();
        t.setDestinationAccount(accountProvider.getIdentificationNumber());
        t.setSourceAccount(salaryPaymentDTO.getCbu());
        t.setAmount(salaryPaymentDTO.getAmount());
        t.setDetailSourceAccount(String.format("Compra realizada en %s", provider.getName()));
        t.setDetailDestinationAccount(String.format("Acreditacion pago desde CBU %s", salaryPaymentDTO.getCbu()));

        return this.createTransfer(t);
    }

}