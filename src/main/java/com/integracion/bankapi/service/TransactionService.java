package com.integracion.bankapi.service;

import com.integracion.bankapi.model.*;
import com.integracion.bankapi.model.dto.AccountDTO;
import com.integracion.bankapi.model.dto.TransactionAccountDTO;
import com.integracion.bankapi.model.dto.TransactionDTO;
import com.integracion.bankapi.model.exception.AccountNotFoundException;
import com.integracion.bankapi.model.mapper.TransactionMapper;
import com.integracion.bankapi.repository.AccountRepository;
import com.integracion.bankapi.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class TransactionService {

    private LimitValidator limitValidator;
    private TransactionRepository repo;
    private AccountRepository accountRepo;
    private TransactionMapper mapper;

    public TransactionService (
            TransactionRepository repo,
            AccountRepository accountRepo,
            LimitValidator limitValidator,
            TransactionMapper mapper
    ){
        this.repo = repo;
        this.accountRepo = accountRepo;
        this.limitValidator = limitValidator;
        this.mapper = mapper;
    }

    public TransactionDTO createWithdraw(TransactionDTO transactionDTO) {

        Account account = getAccountFromTransaction(transactionDTO);

        transactionDTO.setTransactionType(TransactionType.WITHDRAW.getShortName());
        transactionDTO.setOperationType("EXPENDITURE");
        transactionDTO.setDate(new Date());

        Transaction transaction = mapper.toTransaction(transactionDTO);
        transaction.setAccount(account);

        limitValidator.validateAccountLimit(account, transaction.getAmount());

        BigDecimal newBalance = account.getBalance().subtract(transaction.getAmount());
        account.setBalance(newBalance);

        accountRepo.save(account);
        transaction=repo.save(transaction);

        return mapper.toDTO(transaction);
    }

    public TransactionDTO createDeposit(TransactionDTO transactionDTO) {

        Account account = getAccountFromTransaction(transactionDTO);

        transactionDTO.setTransactionType(TransactionType.DEPOSIT.getShortName());
        transactionDTO.setOperationType("INCOME");
        transactionDTO.setDate(new Date());

        Transaction transaction = mapper.toTransaction(transactionDTO);
        transaction.setAccount(account);

        BigDecimal newBalance = account.getBalance().add(transaction.getAmount());
        account.setBalance(newBalance);

        accountRepo.save(account);
        transaction=repo.save(transaction);

        return mapper.toDTO(transaction);
    }

    public TransactionAccountDTO getTransactionsByIdAccount(Integer idAccount){
        Optional<Account> accountRepo = this.accountRepo.findById(idAccount);
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
                    TransactionDTO transactionDTO = mapper.toDTO(transaction);
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


    private Account getAccountFromTransaction(TransactionDTO transactionDTO) {

        Optional<Account> accountFromRepo;

        if (transactionDTO.getAccountId() == null && transactionDTO.getCbu() != null) {
            accountFromRepo = accountRepo.findByIdentificationNumber(transactionDTO.getCbu());
        } else {
            accountFromRepo = accountRepo.findById(transactionDTO.getAccountId());
        }

        if (accountFromRepo.isEmpty())
            throw new AccountNotFoundException();

        return accountFromRepo.get();
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