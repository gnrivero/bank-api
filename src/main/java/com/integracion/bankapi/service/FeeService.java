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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class FeeService {

    private LimitValidator limitValidator;
    private TransactionRepository repo;
    private AccountRepository accountRepo;
    private TransactionMapper mapper;

    public FeeService(
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

    public void chargeMonthly() {

        List<Account> accounts = accountRepo.findAll();
        //TODO ver de donde sacamos el id de la cuenta del banco (en el server el cliente id 1 tiene la cuenta id:10)
        Optional<Account> accountBankRepo = accountRepo.findById(10);
        Account accountBank = accountBankRepo.get();

        for (Account account : accounts) {
            //Sacar la cuenta del banco
            if(account.getId()!=10){
                if(AccountType.CA.getShortName().compareTo(account.getAccountType()) == 0){
                    //Pagar intereses
                    // Client
                    BigDecimal balanceStartMonth = accountRepo.getBalanceStartMonth(account.getId());
                    if (balanceStartMonth == null)
                        balanceStartMonth=new BigDecimal(0);
                    BigDecimal interest = (((account.getBalance().add(balanceStartMonth)).add(account.getBalance())).divide( new BigDecimal(2))).multiply(AccountType.CA.getInterest());

                    TransactionDTO transactionDTO = new TransactionDTO();
                    transactionDTO.setTransactionType(TransactionType.FEE.getShortName());
                    transactionDTO.setOperationType("INCOME");
                    transactionDTO.setDate(new Date());
                    transactionDTO.setAmount(interest);
                    transactionDTO.setCash(false);
                    transactionDTO.setDetail("Acreditacion intereses");
                    Transaction transaction = mapper.toTransaction(transactionDTO);
                    transaction.setAccount(account);
                    //TODO ver si validamos para el cobro de comisiones
                    //limitValidator.validateAccountLimit(account, transaction.getAmount());

                    BigDecimal newBalance = account.getBalance().add(interest);
                    account.setBalance(newBalance);
                    // Bank
                    TransactionDTO transactionBankDTO = new TransactionDTO();
                    transactionBankDTO.setTransactionType(TransactionType.FEE.getShortName());
                    transactionBankDTO.setOperationType("EXPENDITURE");
                    transactionBankDTO.setDate(new Date());
                    transactionBankDTO.setAmount(interest);
                    transactionBankDTO.setCash(false);
                    transactionBankDTO.setDetail(String.format("Pago de intereses a CBU: %s",account.getIdentificationNumber()));
                    Transaction transactionBank = mapper.toTransaction(transactionBankDTO);
                    transactionBank.setAccount(accountBank);
                    //TODO ver si validamos para el cobro de comisiones
                    //limitValidator.validateAccountLimit(account, transaction.getAmount());

                    BigDecimal newBalanceBank = accountBank.getBalance().subtract(interest);
                    accountBank.setBalance(newBalanceBank);

                    accountRepo.save(account);
                    accountRepo.save(accountBank);
                    repo.save(transaction);
                    repo.save(transactionBank);

                }else if(AccountType.CC.getShortName().compareTo(account.getAccountType()) == 0){
                    //Cobrar comision
                    // Client
                    TransactionDTO transactionDTO = new TransactionDTO();
                    transactionDTO.setTransactionType(TransactionType.FEE.getShortName());
                    transactionDTO.setOperationType("EXPENDITURE");
                    transactionDTO.setDate(new Date());
                    transactionDTO.setAmount(AccountType.CC.getFee());
                    transactionDTO.setCash(false);
                    transactionDTO.setDetail("Pago de comision mensual");
                    Transaction transaction = mapper.toTransaction(transactionDTO);
                    transaction.setAccount(account);
                    //TODO ver si validamos para el cobro de comisiones
                    //limitValidator.validateAccountLimit(account, transaction.getAmount());

                    BigDecimal newBalance = account.getBalance().subtract(transaction.getAmount());
                    account.setBalance(newBalance);

                    // Bank
                    TransactionDTO transactionBankDTO = new TransactionDTO();
                    transactionBankDTO.setTransactionType(TransactionType.FEE.getShortName());
                    transactionBankDTO.setOperationType("INCOME");
                    transactionBankDTO.setDate(new Date());
                    transactionBankDTO.setAmount(AccountType.CC.getFee());
                    transactionBankDTO.setCash(false);
                    transactionBankDTO.setDetail(String.format("Cobro de comision mensual de CBU: %s",account.getIdentificationNumber()));
                    Transaction transactionBank = mapper.toTransaction(transactionBankDTO);
                    transactionBank.setAccount(accountBank);
                    //TODO ver si validamos para el cobro de comisiones
                    //limitValidator.validateAccountLimit(account, transaction.getAmount());

                    BigDecimal newBalanceBank = accountBank.getBalance().add(transactionBank.getAmount());
                    accountBank.setBalance(newBalanceBank);

                    accountRepo.save(account);
                    accountRepo.save(accountBank);
                    repo.save(transaction);
                    repo.save(transactionBank);
                }

            }
        }
    }

    public void chargeDaily() {

        List<Account> accounts = accountRepo.getAccountByAccountTypeAndOverdraft();
        //TODO ver de donde sacamos el id de la cuenta del banco (en el server el cliente id 1 tiene la cuenta id:10)
        Optional<Account> accountBankRepo = accountRepo.findById(10);
        Account accountBank = accountBankRepo.get();

        for (Account account : accounts) {
            //Sacar la cuenta del banco
            if(account.getId()!=10){
                //Cobro intereses
                // Client
                BigDecimal interest = ((account.getBalance()).multiply(AccountType.CC.getInterest())).multiply(new BigDecimal(-1));

                TransactionDTO transactionDTO = new TransactionDTO();
                transactionDTO.setTransactionType(TransactionType.FEE.getShortName());
                transactionDTO.setOperationType("EXPENDITURE");
                transactionDTO.setDate(new Date());
                transactionDTO.setAmount(interest);
                transactionDTO.setCash(false);
                transactionDTO.setDetail("cobro intereses por giro en descubierto");
                Transaction transaction = mapper.toTransaction(transactionDTO);
                transaction.setAccount(account);
                //TODO ver si validamos para el cobro de comisiones
                //limitValidator.validateAccountLimit(account, transaction.getAmount());

                BigDecimal newBalance = account.getBalance().subtract(interest);
                account.setBalance(newBalance);
                // Bank
                TransactionDTO transactionBankDTO = new TransactionDTO();
                transactionBankDTO.setTransactionType(TransactionType.FEE.getShortName());
                transactionBankDTO.setOperationType("INCOME");
                transactionBankDTO.setDate(new Date());
                transactionBankDTO.setAmount(interest);
                transactionBankDTO.setCash(false);
                transactionBankDTO.setDetail(String.format("Cobro de intereses por descubierto de CBU: %s",account.getIdentificationNumber()));
                Transaction transactionBank = mapper.toTransaction(transactionBankDTO);
                transactionBank.setAccount(accountBank);
                //TODO ver si validamos para el cobro de comisiones
                //limitValidator.validateAccountLimit(account, transaction.getAmount());

                BigDecimal newBalanceBank = accountBank.getBalance().add(transactionBank.getAmount());
                accountBank.setBalance(newBalanceBank);

                accountRepo.save(account);
                accountRepo.save(accountBank);
                repo.save(transaction);
                repo.save(transactionBank);
            }
        }
    }
}