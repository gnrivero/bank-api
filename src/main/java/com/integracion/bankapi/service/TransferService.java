package com.integracion.bankapi.service;

import com.integracion.bankapi.bankb.webclient.BankWebClient;
import com.integracion.bankapi.model.dto.AccountDTO;
import com.integracion.bankapi.model.dto.ExternalPaymentDTO;
import com.integracion.bankapi.model.dto.ProviderDTO;
import com.integracion.bankapi.model.dto.TransactionDTO;
import com.integracion.bankapi.model.dto.TransferDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TransferService {

    private TransactionService txService;
    private AccountService accountService;
    private ProviderService providerService;
    private BankWebClient webClient;

    public String dummyConnection(){
        return webClient.getToken();
    }

    public TransferDTO createTransfer(TransferDTO transferDTO) {

        TransactionDTO withdraw = null;
        if(transferDTO.getSourceAccount().startsWith("456")){
            withdraw = new TransactionDTO();
            withdraw.setCash(false);
            withdraw.setTransactionType("TRANSFER");
            withdraw.setAmount(transferDTO.getAmount());
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
        if (withdraw != null){
            withdraw = txService.createWithdraw(withdraw);
            transferDTO.setSourceReferenceNumber(withdraw.getId());
        }
        if(deposit != null) {
            deposit = txService.createDeposit(deposit);
            transferDTO.setDestinationReferenceNumber((deposit.getId()));
        }
        return transferDTO;
    }


    public TransferDTO createSalaryTransfer(ExternalPaymentDTO salaryPaymentDTO) {
        ProviderDTO provider = providerService.getProviderByProviderCode(salaryPaymentDTO.getProviderCode());
        AccountDTO accountProvider = accountService.getAccountById(provider.getAccountId());
        TransferDTO t = new TransferDTO();
        t.setSourceAccount(accountProvider.getIdentificationNumber());
        t.setDestinationAccount(salaryPaymentDTO.getCbu());
        t.setAmount(salaryPaymentDTO.getAmount());
        if(salaryPaymentDTO.getDetail().isEmpty())
        t.setDetailSourceAccount(String.format("Pago a CBU %s", salaryPaymentDTO.getCbu()));
        t.setDetailDestinationAccount(salaryPaymentDTO.getDetail());

        return this.createTransfer(t);

    }

    public TransferDTO createPaymentTransfer(ExternalPaymentDTO salaryPaymentDTO) {
        ProviderDTO provider = providerService.getProviderByProviderCode(salaryPaymentDTO.getProviderCode());
        AccountDTO accountProvider = accountService.getAccountById(provider.getAccountId());
        TransferDTO t = new TransferDTO();
        t.setDestinationAccount(accountProvider.getIdentificationNumber());
        t.setSourceAccount(salaryPaymentDTO.getCbu());
        t.setAmount(salaryPaymentDTO.getAmount());
        t.setDetailSourceAccount(salaryPaymentDTO.getDetail());
        t.setDetailDestinationAccount(String.format("Acreditacion pago desde CBU %s", salaryPaymentDTO.getCbu()));

        return this.createTransfer(t);
    }

}