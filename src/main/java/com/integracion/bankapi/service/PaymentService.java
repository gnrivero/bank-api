package com.integracion.bankapi.service;

import com.integracion.bankapi.model.*;
import com.integracion.bankapi.model.dto.PaymentDTO;
import com.integracion.bankapi.model.exception.PaymentExpireException;
import com.integracion.bankapi.model.exception.PaymentNotFoundException;
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
            new PaymentNotFoundException("No se encontro el comprobante de pago electronico");
        }
        Payment payment = paymentRepo.get();
        //si no esta pago y no esta vencida Continua
        if(payment.getPaid() || payment.getDate().isBefore(LocalDate.now().plusDays(-1))){
            new PaymentExpireException();
        }
        Account accountProvider = payment.getProvider().getAccount();
        //TODO ver de donde sacamos el id de la cuenta del banco (en el server el cliente id 1 tiene la cuenta id:10)
        Optional<Account> accountBankRepo = repoAccount.findById(10);
        Account accountBank = accountBankRepo.get();

        BigDecimal amountBank = payment.getAmount().multiply(TransactionType.SERVICE_PAYMENT.getPercent());
        BigDecimal amountProvider = payment.getAmount().subtract(amountBank);
        Transaction transactionBank = new Transaction();
        transactionBank.setCash(true);
        transactionBank.setDate(new Date());
        transactionBank.setDetail("Cobro Servicio - "+ payment.getProvider().getName());
        transactionBank.setTransactionType("COB");
        transactionBank.setOperationType("I");
        transactionBank.setAccount(accountBank);
        transactionBank.setAmount(amountBank);

        Transaction transactionProvider = new Transaction();
        transactionProvider.setCash(true);
        transactionProvider.setDate(new Date());
        transactionProvider.setDetail("Cobro Servicio - "+ payment.getProvider().getName());
        transactionProvider.setTransactionType("COB");
        transactionProvider.setOperationType("I");
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

            if (accountRepo.isPresent()) {
                Account accountClient = accountRepo.get();

                BigDecimal newBalance = accountClient.getBalance().subtract(payment.getAmount());
                //Si se paga desde una CA esta no puede quedar con saldo negativo
                if(accountClient.getAccountType().equals("CA")
                        && newBalance.compareTo(BigDecimal.ZERO) == -1){
                    return null;
                }
                accountClient.setBalance(newBalance);

                Transaction transactionClient = new Transaction();
                transactionClient.setCash(true);
                transactionClient.setDate(new Date());
                transactionClient.setDetail("Cobro Servicio - "+ payment.getProvider().getName());
                transactionClient.setTransactionType("COB");
                transactionClient.setOperationType("E");
                transactionClient.setAccount(accountClient);
                transactionClient.setAmount(payment.getAmount());

                repoTransaction.save(transactionClient);
                repoAccount.save(accountClient);

            }else{
                return null;
            }
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

        Payment paymentRepo = repo.findByElectronicCodeAndPaidAndDateAfter(electronicCode,false,expiration);
        PaymentDTO payment;
        if(paymentRepo != null){
            payment = toDTO(paymentRepo);
        } else{
            payment = null;
        }
        return payment;
    }

    public void generatePayments(){
        List<Provider> providers = repoProvider.findAll();
        for (Provider provider: providers){
            /////0002-disponible
            Path path = Paths.get("proveedores"+ File.separator+ provider.getProviderCode()+"-disponible.txt");

            List<Payment> payments = new ArrayList<Payment>();
            List<String> list = new ArrayList<>();

            try (Stream<String> stream = Files.lines(path)) {

                list = stream.collect(Collectors.toList());

            } catch (IOException e) {
                e.printStackTrace();
                new RuntimeException(e.getMessage());
            }
            for (String l:list) {
                Payment p = new Payment();

                String[] values = l.split(",");
                ///p.setElectronicCode(String.format("%6s",values[0]).replace(' ','0'));
                p.setElectronicCode(values[0]);
                p.setAmount(BigDecimal.valueOf(Double.parseDouble(values[1])));
                p.setDate(LocalDate.parse(values[2]));
                p.setPaid(false);
                p.setProvider(provider);

                payments.add(p);
            }
            if(list.toArray().length !=0) {
                repo.removeExpired(provider.getId(),false);
                repo.saveAll(payments);
                try{
                    // rename a file in the same directory
                    Files.move(path, Paths.get("proveedores"+ File.separator+ provider.getProviderCode() + "-disponible" + LocalDate.now() + ".txt"));
                } catch (IOException e) {
                    e.printStackTrace();
                    new RuntimeException(e.getMessage());
                }
            }
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
