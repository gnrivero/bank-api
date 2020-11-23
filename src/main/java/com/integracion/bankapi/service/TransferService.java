package com.integracion.bankapi.service;

import com.integracion.bankapi.bankb.webclient.BankWebClient;
import com.integracion.bankapi.model.dto.AccountDTO;
import com.integracion.bankapi.model.dto.ExternalPaymentDTO;
import com.integracion.bankapi.model.dto.ProviderDTO;
import com.integracion.bankapi.model.dto.TransactionDTO;
import com.integracion.bankapi.model.dto.TransferDTO;
import com.integracion.bankapi.model.mapper.TransactionMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TransferService {

    private TransactionService txService;
    private AccountService accountService;
    private ProviderService providerService;
    private BankWebClient webClient;
    private TransactionMapper mapper;

    public TransferDTO createTransfer(TransferDTO transferDTO) {

        TransactionDTO withdraw = new TransactionDTO();
        withdraw.setCash(false);
        withdraw.setTransactionType("TRANSFER");
        withdraw.setAmount(transferDTO.getAmount());
        withdraw.setCbu(transferDTO.getSourceAccount());

        if (transferDTO.getDetailSourceAccount() == null) {
            withdraw.setDetail(String.format("Transferenca a cuenta CBU %s", transferDTO.getDestinationAccount()));
        } else {
            withdraw.setDetail(transferDTO.getDetailSourceAccount());
        }


        TransactionDTO deposit = new TransactionDTO();
        deposit.setCash(false);
        deposit.setTransactionType("TRANSFER");
        deposit.setAmount(transferDTO.getAmount());
        deposit.setCbu(transferDTO.getDestinationAccount());

        if(transferDTO.getDetailDestinationAccount() == null) {
            deposit.setDetail(String.format("Transferenca desde cuenta CBU %s", transferDTO.getSourceAccount()));
        } else {
            deposit.setDetail(transferDTO.getDetailDestinationAccount());
        }

        if (isInternalAccount(transferDTO.getSourceAccount())) {
            AccountDTO account = accountService.getAccountByIdentificationNumber(transferDTO.getSourceAccount());
            withdraw.setAccountId(account.getId());

            withdraw = txService.createWithdraw(withdraw);
            transferDTO.setSourceReferenceNumber(withdraw.getId());

        } else {
            webClient.makeTransfer(mapper.toExternalTransferRequest(withdraw));
        }

        if(isInternalAccount(transferDTO.getDestinationAccount())){

            AccountDTO account = accountService.getAccountByIdentificationNumber(transferDTO.getDestinationAccount());
            deposit.setAccountId(account.getId());

            deposit = txService.createDeposit(deposit);
            transferDTO.setDestinationReferenceNumber((deposit.getId()));

        } else {
            webClient.makeTransfer(mapper.toExternalTransferRequest(deposit));
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

        t.setDetailSourceAccount(String.format("Pago a CBU %s", salaryPaymentDTO.getCbu()));

        if (isInternalAccount(salaryPaymentDTO.getCbu())){
            if(salaryPaymentDTO.getDetail().isEmpty())
                t.setDetailDestinationAccount(String.format("Acreditacion desde CBU %s", accountProvider.getIdentificationNumber()));

            t.setDetailDestinationAccount(salaryPaymentDTO.getDetail());
        } else {
            t.setDetailDestinationAccount("pago_de_sueldo");
        }

        return this.createTransfer(t);

    }

    public TransferDTO createPaymentTransfer(ExternalPaymentDTO externalPaymentDTO) {
        ProviderDTO provider = providerService.getProviderByProviderCode(externalPaymentDTO.getProviderCode());
        AccountDTO accountProvider = accountService.getAccountById(provider.getAccountId());
        TransferDTO t = new TransferDTO();
        t.setDestinationAccount(accountProvider.getIdentificationNumber());
        t.setSourceAccount(externalPaymentDTO.getCbu());
        t.setAmount(externalPaymentDTO.getAmount());

        if (isInternalAccount(externalPaymentDTO.getCbu())) {
            if(externalPaymentDTO.getDetail().isEmpty()){
                t.setDetailSourceAccount(String.format("Pago a CBU %s", accountProvider.getIdentificationNumber()));
            }
            t.setDetailSourceAccount(externalPaymentDTO.getDetail());
        } else {
            t.setDetailSourceAccount("compra_en_establecimiento");
        }

        t.setDetailDestinationAccount(String.format("Acreditacion pago desde CBU %s", externalPaymentDTO.getCbu()));

        return this.createTransfer(t);
    }

    public TransferDTO createSupplierTransfer(ExternalPaymentDTO salaryPaymentDTO) {
        ProviderDTO provider = providerService.getProviderByProviderCode(salaryPaymentDTO.getProviderCode());
        AccountDTO accountProvider = accountService.getAccountById(provider.getAccountId());
        TransferDTO t = new TransferDTO();
        t.setSourceAccount(accountProvider.getIdentificationNumber());
        t.setDestinationAccount(salaryPaymentDTO.getCbu());
        t.setAmount(salaryPaymentDTO.getAmount());
        t.setDetailSourceAccount(String.format("Pago a CBU %s", salaryPaymentDTO.getCbu()));

        if (isInternalAccount(salaryPaymentDTO.getCbu())){
            if(salaryPaymentDTO.getDetail().isEmpty()) {
                t.setDetailDestinationAccount(String.format("Acreditacion desde CBU %s", accountProvider.getIdentificationNumber()));
            }
            t.setDetailDestinationAccount(salaryPaymentDTO.getDetail());
        } else {
            //TODO falta definir por el banco B
            t.setDetailDestinationAccount("pago_de_sueldo");
        }

        return this.createTransfer(t);

    }

    private Boolean isInternalAccount(String cbu) {
        return cbu.startsWith("456");
    }

}