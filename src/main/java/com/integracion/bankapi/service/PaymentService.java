package com.integracion.bankapi.service;

import com.integracion.bankapi.model.*;
import com.integracion.bankapi.repository.AccountRepository;
import com.integracion.bankapi.repository.PaymentRepository;
import com.integracion.bankapi.repository.ProviderRepository;
import com.integracion.bankapi.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
        if(paymentRepo.isPresent()){
            Payment payment = paymentRepo.get();
            //si no esta pago y no esta vencida Continua
            if(payment.getPaid() || payment.getDate().isBefore(LocalDate.now().plusDays(-1))){
                return null;
            }
            Account accountProvider = payment.getProvider().getAccount();
            //TODO ver de donde sacamos el id de la cuenta del banco
            Optional<Account> accountBankRepo = repoAccount.findById(1);
            Account accountBank = accountBankRepo.get();


            double amountBank = payment.getAmount()*TransactionType.COBRANZA.getPercent()/100;
            double amountProvider = payment.getAmount() - amountBank;
            Transaction transactionBank = new Transaction();
            transactionBank.setCash(true);
            transactionBank.setDate(new Date());
            transactionBank.setDetail("Cobro Servicio - "+ payment.getProvider().getName());
            transactionBank.setTransactionType("COB");
            transactionBank.setTypeOperation("I");
            transactionBank.setAccount(accountBank);
            transactionBank.setAmount(amountBank);

            Transaction transactionProvider = new Transaction();
            transactionProvider.setCash(true);
            transactionProvider.setDate(new Date());
            transactionProvider.setDetail("Cobro Servicio - "+ payment.getProvider().getName());
            transactionProvider.setTransactionType("COB");
            transactionProvider.setTypeOperation("I");
            transactionProvider.setAccount(accountProvider);
            transactionProvider.setAmount(amountProvider);

            payment.setPaid(true);

            double newBalanceBank =accountBank.getBalance()+transactionBank.getAmount();
            accountBank.setBalance(newBalanceBank);
            double newBalanceProvider =accountProvider.getBalance()+transactionProvider.getAmount();
            accountProvider.setBalance(newBalanceProvider);

            //Si es pago por cuentas
            if (!paymentDTO.getCash()){
                //Cuenta desde donde se pagan los servicios
                Optional<Account> accountRepo = repoAccount.findById(paymentDTO.getAccountId());

                if (accountRepo.isPresent()) {
                    Account accountClient = accountRepo.get();


                    double newBalance = accountClient.getBalance()-payment.getAmount();
                    //Si se paga desde una CA esta no puede quedar con saldo negativo
                    if(accountClient.getAccountType().equals("CA") && newBalance<0){
                        return null;
                    }
                    accountClient.setBalance(newBalance);

                    Transaction transactionClient = new Transaction();
                    transactionClient.setCash(true);
                    transactionClient.setDate(new Date());
                    transactionClient.setDetail("Cobro Servicio - "+ payment.getProvider().getName());
                    transactionClient.setTransactionType("COB");
                    transactionClient.setTypeOperation("E");
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

            mapping(payment,paymentDTO);
            return paymentDTO;
        }else{
            return null;
        }
    }


    public PaymentDTO getPaymentByElectronicCode(String electronicCode){
        LocalDate expiration = LocalDate.now().plusDays(-1);

        Payment paymentRepo = repo.findByElectronicCodeAndPaidAndDateAfter(electronicCode,false,expiration);
        PaymentDTO payment;
        if(paymentRepo != null){
            payment = new PaymentDTO();
            mapping(paymentRepo,payment);
        }
        else{
            payment = null;
        }
        return payment;
    }

    public void generatePayments(){
        List<Provider> providers = repoProvider.findAll();
        for (Provider provider: providers){
            /////0002-disponible
            Path path = Paths.get(provider.getProviderCode()+"-disponible.txt");

            List<Payment> payments = new ArrayList<Payment>();
            List<String> list = new ArrayList<>();

            try (Stream<String> stream = Files.lines(path)) {

                list = stream.collect(Collectors.toList());

            } catch (IOException e) {
                e.printStackTrace();
            }
            for (String l:list) {
                Payment p = new Payment();

                String[] values = l.split(",");
                p.setElectronicCode(String.format("%6s",values[0]).replace(' ','0'));
                p.setAmount(Double.parseDouble(values[1]));
                p.setDate(LocalDate.parse(values[2]));
                p.setPaid(false);
                p.setProvider(provider);

                payments.add(p);
            }/*
            list.forEach(x-> {
                Payment p = new Payment();

                String[] values = x.split(",");
                p.setElectronicCode(String.format("%06d", values[0]) );
                p.setAmount(Double.parseDouble(values[1]));
                p.setDate(LocalDate.parse(values[2]));
                p.setProvider(provider);

                payments.add(p);
            });*/
            if(list.toArray().length !=0) {
                repo.removeExpired(provider.getId());
                repo.saveAll(payments);
                try{
                    // rename a file in the same directory
                    Files.move(path, Paths.get(provider.getProviderCode() + "-disponible" + LocalDate.now() + ".txt"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // rename a file in the same directory
            }

        }


    }


    private void mapping (PaymentDTO paymentOrigin, Payment payment){
        payment.setId(paymentOrigin.getId());
        payment.setAmount(paymentOrigin.getAmount());
        payment.setDate(paymentOrigin.getDate());
        payment.setElectronicCode(paymentOrigin.getElectronicCode());
        payment.setPaid(paymentOrigin.getPaid());
    }

    private void mapping (Payment paymentOrigin, PaymentDTO payment){
        payment.setId(paymentOrigin.getId());
        payment.setAmount(paymentOrigin.getAmount());
        payment.setDate(paymentOrigin.getDate());
        payment.setElectronicCode(paymentOrigin.getElectronicCode());
        payment.setPaid(paymentOrigin.getPaid());
    }



}
