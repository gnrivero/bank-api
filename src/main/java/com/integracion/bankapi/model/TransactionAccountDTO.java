package com.integracion.bankapi.model;

import java.util.Date;
import java.util.List;

public class TransactionAccountDTO {

    private List<TransactionDTO> transactions;
    private AccountDTO accountDetail;

    public List<TransactionDTO> getTransactions() { return transactions; }

    public void setTransactions(List<TransactionDTO> transactions) { this.transactions = transactions; }

    public AccountDTO getAccountDetail() { return accountDetail; }

    public void setAccountDetail(AccountDTO accountDetail) { this.accountDetail = accountDetail; }
}
