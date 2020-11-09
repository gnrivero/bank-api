package com.integracion.bankapi.service;

import com.integracion.bankapi.model.*;
import com.integracion.bankapi.repository.AccountRepository;
import com.integracion.bankapi.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private AccountRepository repo;
    private ClientRepository repoClient;

    public AccountService (AccountRepository repo, ClientRepository repoClient){
        this.repo = repo;
        this.repoClient = repoClient;
    }


    public AccountDTO create(AccountDTO accountDTO)
    {
        Optional<Client> clientRepo = repoClient.findById(accountDTO.getClientId());
        Account account = new Account();
        mapping(accountDTO,account);
        if(clientRepo.isPresent()){
            Client client =clientRepo.get();

            List<Account> accounts= client.getAccounts();
            accounts.add(account);
            client.setAccounts(accounts);
            client = repoClient.save(client);
        }
        mapping(account,accountDTO);
        return accountDTO;
    }

    public AccountDTO getAccountById(Integer id){
        Optional<Account> accountRepo = repo.findById(id);
        AccountDTO account;
        if(accountRepo.isPresent()){
            account = new AccountDTO();
            mapping(accountRepo.get(),account);
        }
        else{
            account = null;
        }
        return account;
    }

    public AccountDTO getAccountByIdentificationNumber(String identificationNumber){
        Account accountRepo = repo.findByIdentificationNumber(identificationNumber);
        AccountDTO account;
        if(accountRepo != null){
            account = new AccountDTO();
            mapping(accountRepo,account);
        }
        else{
            account = null;
        }
        return account;
    }

    public List<AccountDTO> getAccountByIdClient(Integer idClient){
        List<Account> accountsRepo = repo.findByClient(idClient);
        List<AccountDTO> accounts;
        if(accountsRepo != null){
            accounts = new ArrayList<AccountDTO>();
            for (Account account: accountsRepo){
                AccountDTO accountDTO = new AccountDTO();
                mapping(account,accountDTO);
                accounts.add(accountDTO);
            }
        }
        else{
            accounts = null;
        }
        return accounts;
    }


    private void mapping (AccountDTO accountOrigin, Account account){
        account.setId(accountOrigin.getId());
        account.setIdentificationNumber(accountOrigin.getIdentificationNumber());
        account.setBalance(accountOrigin.getBalance());
        account.setName(accountOrigin.getName());
        account.setAccountType(accountOrigin.getAccountType());
        account.setOverdraft(accountOrigin.getOverdraft());
        account.setStatus(accountOrigin.getStatus());
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
        account.setStatus(accountOrigin.getStatus());
    }


}
