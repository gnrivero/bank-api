package com.integracion.bankapi.service;

import com.integracion.bankapi.model.Account;
import com.integracion.bankapi.model.AccountDTO;
import com.integracion.bankapi.model.Client;
import com.integracion.bankapi.repository.AccountRepository;
import com.integracion.bankapi.repository.ClientRepository;
import org.springframework.stereotype.Service;

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


    public Account create(AccountDTO accountDTO)
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
        return account;
    }


    private void mapping (AccountDTO accountOrigin, Account account){
        account.setId(accountOrigin.getId());
        account.setCBU(accountOrigin.getCBU());
    }

    private void mapping (Account accountOrigin, AccountDTO account){
        account.setId(accountOrigin.getId());
        account.setCBU(accountOrigin.getCBU());
    }
}
