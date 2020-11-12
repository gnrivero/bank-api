package com.integracion.bankapi.service;

import com.integracion.bankapi.model.*;
import com.integracion.bankapi.repository.AccountRepository;
import com.integracion.bankapi.repository.ClientRepository;
import com.integracion.bankapi.repository.ProviderRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ProviderService {

    private AccountRepository repoAccount;
    private ProviderRepository repo;

    public ProviderService(AccountRepository repoAccount, ProviderRepository repo){
        this.repo = repo;
        this.repoAccount = repoAccount;
    }


    public ProviderDTO create(ProviderDTO providerDTO)
    {
        Optional<Account> accountRepo;
        Account a = null;
        accountRepo = repoAccount.findById(providerDTO.getAccountId());
        if(accountRepo.isPresent()){
            a =accountRepo.get();

            Provider p = new Provider();
            mapping(providerDTO,p);
            if(a  != null){
                p.setAccount(a);
                p = repo.save(p);
            }
            mapping(p,providerDTO);
            return providerDTO;
        }
        else{
            return null;
        }
    }

/*
    public ProviderDTO edit(ProviderDTO p) {

        Optional<Provider> pRepo = repo.findById(p.getId());
        if(pRepo.isPresent()){
            Provider pEdit = new Provider();
            pEdit.setAccount((pRepo.get().getAccounts());
            mapping(client,clientEdit);
            clientEdit =repo.save(clientEdit);
            mapping(clientEdit,client);
        }else{
            client= null;
        }

        return client;
    }
    */

    public ProviderDTO getProviderById(Integer id){
        Optional<Provider> pRepo = repo.findById(id);
        ProviderDTO p;
        if(pRepo.isPresent()){
            p = new ProviderDTO();
            mapping(pRepo.get(),p);
        }
        else{
            p = null;
        }
        return p;
    }

    private void mapping (ProviderDTO pOrigin, Provider provider){
        provider.setId(pOrigin.getId());
        provider.setName(pOrigin.getName());
        provider.setProviderCode(pOrigin.getProviderCode());
    }

    private void mapping (Provider pOrigin, ProviderDTO provider){
        provider.setId(pOrigin.getId());
        provider.setName(pOrigin.getName());
        provider.setProviderCode(pOrigin.getProviderCode());
    }

}
