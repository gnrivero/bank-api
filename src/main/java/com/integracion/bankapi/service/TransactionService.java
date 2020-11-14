package com.integracion.bankapi.service;

import com.integracion.bankapi.model.*;
import com.integracion.bankapi.model.dto.AccountDTO;
import com.integracion.bankapi.model.dto.TransactionAccountDTO;
import com.integracion.bankapi.model.dto.TransactionDTO;
import com.integracion.bankapi.repository.AccountRepository;
import com.integracion.bankapi.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class TransactionService {

    private TransactionRepository repo;
    private AccountRepository repoAccount;

    public TransactionService (TransactionRepository repo, AccountRepository repoAccount){
        this.repo = repo;
        this.repoAccount = repoAccount;
    }


    public TransactionDTO create(TransactionDTO transactionDTO)
    {
        var type = Arrays.stream(TransactionType.values()).filter(x->x.getShortName().equals(transactionDTO.getTransactionType())).toArray();
        if(type.length !=0) {
            Optional<Account> accountRepo = repoAccount.findById(transactionDTO.getAccountId());
            Transaction transaction = new Transaction();
            mapping(transactionDTO, transaction);
            if (accountRepo.isPresent()) {
                Account account = accountRepo.get();
                transaction.setAccount(account);
                transaction.setDate(new Date());
                switch(transaction.getTransactionType())
                {
                    //DEPOSITO
                    case "DEP":
                    {
                        if(transaction.getCash()){
                            //en efectivo
                            account.setBalance(account.getBalance().add(transaction.getAmount()));
                        }else{
                            //desde una cuenta
                            BigDecimal newBalance = account.getBalance().add(transaction.getAmount());
                            account.setBalance(newBalance);
                            //Ver si buscar por CBU
                            Optional<Account> accountOriginRepo = repoAccount.findById(transactionDTO.getAccountOriginId());
                            if(accountOriginRepo.isPresent()){
                                Account accountOrigin =accountOriginRepo.get();
                                Transaction transactionOrigin = new Transaction();
                                mapping(transactionDTO,transactionOrigin);
                                transactionOrigin.setTypeOperation("E");
                                transactionOrigin.setAccount(accountOrigin);
                                transactionOrigin.setDate(new Date());
                                newBalance = accountOrigin.getBalance().subtract(transactionOrigin.getAmount());
                                //Si es una CA el origen no puede quedar con saldo negativo
                                if (accountOrigin.getAccountType().equals("CA")
                                        && newBalance.compareTo(BigDecimal.ZERO) == -1) {
                                    return null;
                                }
                                accountOrigin.setBalance(newBalance);
                                repo.save(transactionOrigin);
                                repoAccount.save(accountOrigin);
                            }
                            else {
                                return null;
                            }
                        }
                        break;
                    }
                    //EXTRACCION
                    case "EXT":
                    {
                        BigDecimal newBalance = account.getBalance().subtract(transaction.getAmount());
                        //Si es una CA el destino de la operacion no puede quedar con saldo negativo
                        if(account.getAccountType().equals("CA")
                                && newBalance.compareTo(BigDecimal.ZERO) == -1) {
                            return null;
                        }
                        account.setBalance(newBalance);
                        //desde una cuenta
                        if(!transaction.getCash()){
                            //Ver si buscar por CBU
                            Optional<Account> accountOriginRepo = repoAccount.findById(transactionDTO.getAccountOriginId());
                            if(accountOriginRepo.isPresent()){
                                Account accountOrigin =accountOriginRepo.get();
                                Transaction transactionOrigin = new Transaction();
                                mapping(transactionDTO,transactionOrigin);
                                transactionOrigin.setTypeOperation("I");
                                transactionOrigin.setAccount(accountOrigin);
                                transactionOrigin.setDate(new Date());
                                newBalance = accountOrigin.getBalance().subtract(transactionOrigin.getAmount());
                                accountOrigin.setBalance(newBalance);
                                repo.save(transactionOrigin);
                                repoAccount.save(accountOrigin);
                            }
                            else {
                                return null;
                            }
                        }
                        break;
                    }
                    case "COB":
                    {
                        break;
                    }
                    case "COM":
                    {
                        break;
                    }
                    default:{
                        return null;
                    }
                }
                transaction = repo.save(transaction);
                repoAccount.save(account);
                mapping(transaction, transactionDTO);
                return transactionDTO;

            }else{
                return null;
            }
        }
        else{
            return null;
        }
    }

    public TransactionAccountDTO getTransactionsByIdAccount(Integer idAccount){
        Optional<Account> accountRepo = repoAccount.findById(idAccount);
        if (accountRepo.isPresent()) {
            Account account = accountRepo.get();
            TransactionAccountDTO transactionAccountDTO = new TransactionAccountDTO();
            AccountDTO accountDTO = new AccountDTO();
            mapping(account, accountDTO);
            transactionAccountDTO.setAccountDetail(accountDTO);
            List<Transaction> transactionsRepo = repo.getTransactionByAccount(idAccount);
            List<TransactionDTO> transactionsDTO;
            if (transactionsRepo != null) {
                transactionsDTO = new ArrayList<TransactionDTO>();
                for (Transaction transaction : transactionsRepo) {
                    TransactionDTO transactionDTO = new TransactionDTO();
                    mapping(transaction, transactionDTO);
                    transactionsDTO.add(transactionDTO);
                }
                transactionAccountDTO.setTransactions(transactionsDTO);
            } else {
                transactionAccountDTO = null;
            }
            return transactionAccountDTO;
        }else{
            return null;
        }
    }

    private void mapping (TransactionDTO transactionOrigin, Transaction transaction){
        transaction.setId(transactionOrigin.getId());
        transaction.setAmount(transactionOrigin.getAmount());
        transaction.setDetail(transactionOrigin.getDetail());
        transaction.setTransactionType(transactionOrigin.getTransactionType());
        transaction.setCash(transactionOrigin.getCash());
        transaction.setTypeOperation(transactionOrigin.getTypeOperation());
    }

    private void mapping (Transaction transactionOrigin, TransactionDTO transaction){
        transaction.setId(transactionOrigin.getId());
        transaction.setAmount(transactionOrigin.getAmount());
        transaction.setDetail(transactionOrigin.getDetail());
        transaction.setTransactionType(transactionOrigin.getTransactionType());
        transaction.setDate(transactionOrigin.getDate());
        transaction.setCash(transactionOrigin.getCash());
        transaction.setTypeOperation(transactionOrigin.getTypeOperation());
    }

    private void mapping (AccountDTO accountOrigin, Account account){
        account.setId(accountOrigin.getId());
        account.setIdentificationNumber(accountOrigin.getIdentificationNumber());
        account.setBalance(accountOrigin.getBalance());
        account.setName(accountOrigin.getName());
        account.setAccountType(accountOrigin.getAccountType());
        account.setOverdraft(accountOrigin.getOverdraft());
        account.setActive(accountOrigin.getActive());
    }

    private void mapping (Account accountOrigin, AccountDTO account){
        account.setId(accountOrigin.getId());
        account.setIdentificationNumber(accountOrigin.getIdentificationNumber());
        account.setBalance(accountOrigin.getBalance());
        account.setName(accountOrigin.getName());
        account.setAccountType(accountOrigin.getAccountType());
        if(accountOrigin.getAccountType() =="CC"){
            account.setAccountTypeDescription(AccountType.CC.getAccountTypeName());
        }else if (accountOrigin.getAccountType() =="CA"){
            account.setAccountTypeDescription(AccountType.CA.getAccountTypeName());
        }
        account.setOverdraft(accountOrigin.getOverdraft());
        account.setActive(accountOrigin.getActive());
    }

}
