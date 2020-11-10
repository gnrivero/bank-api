package com.integracion.bankapi.service;

import com.integracion.bankapi.model.*;
import com.integracion.bankapi.repository.AccountRepository;
import com.integracion.bankapi.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

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
                            account.setBalance(account.getBalance()+transaction.getAmount());
                        }else{
                            //desde una cuenta
                            account.setBalance(account.getBalance()+transaction.getAmount());
                            //Ver si buscar por CBU
                            Optional<Account> accountOriginRepo = repoAccount.findById(transactionDTO.getAccountOriginId());
                            if(accountOriginRepo.isPresent()){
                                Account accountOrigin =accountOriginRepo.get();
                                Transaction transactionOrigin = new Transaction();
                                mapping(transactionDTO,transactionOrigin);
                                transactionOrigin.setTypeOperation("E");
                                transactionOrigin.setAccount(accountOrigin);
                                transactionOrigin.setDate(new Date());
                                double newBalance=accountOrigin.getBalance()-transactionOrigin.getAmount();
                                //Si es una CA el origen no puede quedar con saldo negativo
                                if(accountOrigin.getAccountType().equals("CA") && newBalance<0){
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
                        if(transaction.getCash()){
                            //en efectivo
                            account.setBalance(account.getBalance()-transaction.getAmount());
                        }else{
                            //desde una cuenta
                            double newBalance = account.getBalance()-transaction.getAmount();
                            //Si es una CA el destino de la operacion no puede quedar con saldo negativo
                            if(account.getAccountType().equals("CA") && newBalance<0){
                                return null;
                            }
                            account.setBalance(newBalance);
                            //Ver si buscar por CBU
                            Optional<Account> accountOriginRepo = repoAccount.findById(transactionDTO.getAccountOriginId());
                            if(accountOriginRepo.isPresent()){
                                Account accountOrigin =accountOriginRepo.get();
                                Transaction transactionOrigin = new Transaction();
                                mapping(transactionDTO,transactionOrigin);
                                transactionOrigin.setTypeOperation("I");
                                transactionOrigin.setAccount(accountOrigin);
                                transactionOrigin.setDate(new Date());
                                accountOrigin.setBalance(accountOrigin.getBalance()+transactionOrigin.getAmount());
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

}
