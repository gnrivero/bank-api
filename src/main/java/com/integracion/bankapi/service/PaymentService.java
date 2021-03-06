package com.integracion.bankapi.service;

import com.integracion.bankapi.model.*;
import com.integracion.bankapi.model.dto.AccountDTO;
import com.integracion.bankapi.model.dto.PaymentDTO;
import com.integracion.bankapi.model.exception.*;
import com.integracion.bankapi.repository.AccountRepository;
import com.integracion.bankapi.repository.PaymentRepository;
import com.integracion.bankapi.repository.ProviderRepository;
import com.integracion.bankapi.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PaymentService {

    private PaymentRepository repo;
    private TransactionRepository repoTransaction;
    private AccountRepository repoAccount;
    private ProviderRepository repoProvider;

    public PaymentService(PaymentRepository repo, AccountRepository repoAccount, TransactionRepository repoTransaction, ProviderRepository repoProvider){
        this.repo = repo;
        this.repoAccount = repoAccount;
        this.repoTransaction = repoTransaction;
        this.repoProvider = repoProvider;
    }


    public PaymentDTO create(PaymentDTO paymentDTO)
    {
        Optional<Payment> paymentRepo = repo.findById(paymentDTO.getId());
        if(paymentRepo.isEmpty()){
            throw new PaymentNotFoundException("No se encontro el comprobante de pago electronico");
        }
        Payment payment = paymentRepo.get();
        //si no esta pago y no esta vencida Continua
        if(payment.getPaid() || payment.getDate().isBefore(LocalDate.now().plusDays(-1))){
            throw new PaymentExpireException();
        }
        Account accountProvider = payment.getProvider().getAccount();
        //TODO ver de donde sacamos el id de la cuenta del banco (en el server el cliente id 1 tiene la cuenta id:10)
        Optional<Account> accountBankRepo = repoAccount.findById(10);
        Account accountBank = accountBankRepo.get();

        BigDecimal amountBank = payment.getAmount().multiply(TransactionType.SERVICE_PAYMENT.getPercent());
        BigDecimal amountProvider = payment.getAmount().subtract(amountBank);
        Transaction transactionBank = new Transaction();
        transactionBank.setCash(paymentDTO.getCash());
        transactionBank.setDate(new Date());
        transactionBank.setDetail("Comision Cobro Servicio - "+ payment.getProvider().getName()+ " - "+ payment.getElectronicCode());
        transactionBank.setTransactionType(TransactionType.SERVICE_PAYMENT.getShortName());
        transactionBank.setOperationType("INCOME");
        transactionBank.setAccount(accountBank);
        transactionBank.setAmount(amountBank);

        Transaction transactionProvider = new Transaction();
        transactionProvider.setCash(paymentDTO.getCash());
        transactionProvider.setDate(new Date());
        transactionProvider.setDetail("Cobro Servicio - "+ payment.getProvider().getName() + " - "+ payment.getElectronicCode() );
        transactionProvider.setTransactionType(TransactionType.SERVICE_PAYMENT.getShortName());
        transactionProvider.setOperationType("INCOME");
        transactionProvider.setAccount(accountProvider);
        transactionProvider.setAmount(amountProvider);

        payment.setPaid(true);

        BigDecimal newBalanceBank = accountBank.getBalance().add(transactionBank.getAmount());
        accountBank.setBalance(newBalanceBank);
        BigDecimal newBalanceProvider = accountProvider.getBalance().add(transactionProvider.getAmount());
        accountProvider.setBalance(newBalanceProvider);

        //Si es pago por cuentas
        if (!paymentDTO.getCash()){
            //Cuenta desde donde se pagan los servicios
            Optional<Account> accountRepo = repoAccount.findById(paymentDTO.getAccountId());

            if(accountRepo.isEmpty()){
                throw new AccountNotFoundException();
            }
            Account accountClient = accountRepo.get();

            BigDecimal newBalance = accountClient.getBalance().subtract(payment.getAmount());
            //Si se paga desde una CA esta no puede quedar con saldo negativo
            if(accountClient.getAccountType().equals("CA")
                    && newBalance.compareTo(BigDecimal.ZERO) == -1){
                throw new AccountLimitSurpassedException();
            }
            accountClient.setBalance(newBalance);

            Transaction transactionClient = new Transaction();
            transactionClient.setCash(false);
            transactionClient.setDate(new Date());
            transactionClient.setDetail("Cobro Servicio - "+ payment.getProvider().getName()+ " - "+ payment.getElectronicCode());
            transactionClient.setTransactionType(TransactionType.SERVICE_PAYMENT.getShortName());
            transactionClient.setOperationType("EXPENDITURE");
            transactionClient.setAccount(accountClient);
            transactionClient.setAmount(payment.getAmount());

            repoTransaction.save(transactionClient);
            repoAccount.save(accountClient);


        }
        repoTransaction.save(transactionBank);
        repoTransaction.save(transactionProvider);

        repoAccount.save(accountBank);
        repoAccount.save(accountProvider);
        repo.save(payment);

        return toDTO(payment);

    }


    public PaymentDTO getPaymentByElectronicCode(String electronicCode){
        LocalDate expiration = LocalDate.now().plusDays(-1);

        Optional<Payment> paymentRepo = repo.findByElectronicCodeAndPaidAndDateAfter(electronicCode,false,expiration);

        if (paymentRepo.isEmpty())
            throw new PaymentNotFoundException("No se encontro el comprobante de pago electronico");

        PaymentDTO payment  = toDTO(paymentRepo.get());
        return payment;
    }


    public List<PaymentDTO> getPaymentPaidByElectronicCode(String electronicCode){

        List<Payment> payments = repo.findByElectronicCodeAndPaid(electronicCode,true);

        List<PaymentDTO> paymentDTOs = new ArrayList<PaymentDTO>();
        for (Payment p: payments){
            PaymentDTO pDTO = toDTO(p);
            paymentDTOs.add(pDTO);

        }
        return paymentDTOs;
    }

    public List<PaymentDTO> getLastMonthsPayments(String providerCode){

        Optional<Provider> provider = repoProvider.findByProviderCode(providerCode);
        if (provider.isEmpty()){
            throw new ProviderNotFoundException();
        }
        List<Payment> payments = repo.getLastMonthsPayments(provider.get().getId());

        List<PaymentDTO> paymentDTOs = new ArrayList<PaymentDTO>();
        for (Payment p: payments){
            PaymentDTO pDTO = toDTO(p);
            paymentDTOs.add(pDTO);

        }
        return paymentDTOs;
    }


    public void generatePayments(String providerCode, List<PaymentDTO> listPayment ) {
        Optional<Provider> provider = repoProvider.findByProviderCode(providerCode);
        if (provider.isEmpty())
            throw new ProviderNotFoundException();

        List<Payment> payments = new ArrayList<Payment>();

        for (PaymentDTO l : listPayment) {
            Payment p = new Payment();

            p.setElectronicCode(l.getElectronicCode());
            p.setAmount(l.getAmount());
            p.setDate(l.getDate());
            p.setPaid(false);
            p.setProvider(provider.get());

            payments.add(p);
        }
        if (!listPayment.isEmpty()){
            repo.removeExpired(provider.get().getId());
            repo.saveAll(payments);
        }
    }

    public PaymentDTO createTest(PaymentDTO paymentDTO)
    {
        Payment p = new Payment();
        p.setDate(LocalDate.now());
        p.setPaid(false);
        Optional<Provider> prov = repoProvider.findById(paymentDTO.getProviderId());
        p.setProvider(prov.get());
        p.setAmount(paymentDTO.getAmount());
        p.setElectronicCode(paymentDTO.getElectronicCode());
        repo.save(p);
        return toDTO(p);
    }


    private Payment toPayment(PaymentDTO paymentOrigin){
        Payment payment = new Payment();
        payment.setId(paymentOrigin.getId());
        payment.setAmount(paymentOrigin.getAmount());
        payment.setDate(paymentOrigin.getDate());
        payment.setElectronicCode(paymentOrigin.getElectronicCode());
        payment.setPaid(paymentOrigin.getPaid());
        return payment;
    }

    private PaymentDTO toDTO(Payment paymentOrigin){
        PaymentDTO payment = new  PaymentDTO();
        payment.setId(paymentOrigin.getId());
        payment.setAmount(paymentOrigin.getAmount());
        payment.setDate(paymentOrigin.getDate());
        payment.setElectronicCode(paymentOrigin.getElectronicCode());
        payment.setPaid(paymentOrigin.getPaid());
        payment.setProviderName(paymentOrigin.getProvider().getName());
        return payment;
    }



}
