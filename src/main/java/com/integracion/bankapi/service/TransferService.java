package com.integracion.bankapi.service;

import com.integracion.bankapi.model.dto.TransactionDTO;
import com.integracion.bankapi.model.dto.TransferDTO;
import org.springframework.stereotype.Service;

@Service
public class TransferService {

    private TransactionService txService;

    public TransferService(TransactionService txService) {
        this.txService = txService;
    }

    public TransferDTO createTransfer(TransferDTO transferDTO) {

        TransactionDTO withdraw = new TransactionDTO();
        withdraw.setCash(false);
        withdraw.setTransactionType("TRANSFER");
        withdraw.setAmount(transferDTO.getAmount());
        withdraw.setAccountId(transferDTO.getSourceAccount());
        withdraw.setDetail(String.format("Transferenca a cuenta %d", transferDTO.getDestinationAccount()));

        txService.createWithdraw(withdraw);

        TransactionDTO deposit = new TransactionDTO();
        deposit.setCash(false);
        deposit.setTransactionType("TRANSFER");
        deposit.setAmount(transferDTO.getAmount());
        deposit.setAccountId(transferDTO.getDestinationAccount());
        withdraw.setDetail(String.format("Transferenca desde cuenta %d", transferDTO.getSourceAccount()));

        txService.createDeposit(deposit);

        return transferDTO;
    }

}