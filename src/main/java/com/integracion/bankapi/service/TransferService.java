package com.integracion.bankapi.service;

import com.integracion.bankapi.model.Account;
import com.integracion.bankapi.model.dto.AccountDTO;
import com.integracion.bankapi.model.dto.TransactionDTO;
import com.integracion.bankapi.model.dto.TransferDTO;
import org.springframework.stereotype.Service;

@Service
public class TransferService {

    private TransactionService txService;
    private AccountService accountService;

    public TransferService(TransactionService txService, AccountService accountService) {
        this.txService = txService;
        this.accountService = accountService;
    }

    public TransferDTO createTransfer(TransferDTO transferDTO) {

        TransactionDTO withdraw = new TransactionDTO();
        withdraw.setCash(false);
        withdraw.setTransactionType("TRANSFER");
        withdraw.setAmount(transferDTO.getAmount());
        /*
         Si no generalizamos en el origen no tendriamos que validar que sea de nuestro banco
        */
        if(transferDTO.getSourceAccount().startsWith("456")){
        //Cuenta interna
            AccountDTO account = accountService.getAccountByIdentificationNumber(transferDTO.getSourceAccount());
            withdraw.setAccountId(account.getId());
            withdraw.setDetail(String.format("Transferenca a cuenta CBU %s", account.getIdentificationNumber()));

        }else{
        /*
         TODO: validar con el otro banco. Creo que no haria falta
        */
        }

        TransactionDTO deposit = null;
        if(transferDTO.getSourceAccount().startsWith("456")){
            //Cuenta interna
            deposit = new TransactionDTO();
            AccountDTO account = accountService.getAccountByIdentificationNumber(transferDTO.getDestinationAccount());
            deposit.setCash(false);
            deposit.setTransactionType("TRANSFER");
            deposit.setAmount(transferDTO.getAmount());
            deposit.setAccountId(account.getId());
            deposit.setDetail(String.format("Transferenca desde cuenta CBU %s", account.getIdentificationNumber()));


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

}