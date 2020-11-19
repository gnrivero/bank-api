package com.integracion.bankapi.service;

import com.integracion.bankapi.model.*;
import com.integracion.bankapi.model.dto.ProviderDTO;
import com.integracion.bankapi.model.exception.ClientNotFoundException;
import com.integracion.bankapi.model.exception.ProviderNotFoundException;
import com.integracion.bankapi.repository.AccountRepository;
import com.integracion.bankapi.repository.ProviderRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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

    public void delete(Integer idProvider)
    {
        Optional<Provider> provider = repo.findById(idProvider);
        if(provider.isPresent()){
            repo.delete(provider.get());
        }
    }

    public void saveFile(MultipartFile file, String providerFile){
        String a = providerFile;


        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                // Creating the directory to store file
                String rootPath = System.getProperty(providerFile);
                File dir = new File("proveedores" + File.separator);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath() + File.separator + providerFile+"-disponible.txt");
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

            } catch (Exception e) {
                new RuntimeException(e.getMessage());
            }
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
        if (pRepo.isEmpty())
            throw new ProviderNotFoundException(
                    String.format("No se encontro el Proveedor")
            );
        p = new ProviderDTO();
        mapping(pRepo.get(),p);

        return p;
    }

    public ProviderDTO getProviderByProviderCode(String providerCode){
        Optional<Provider> pRepo = repo.findByProviderCode(providerCode);
        ProviderDTO p;
        if (pRepo.isEmpty())
            throw new ProviderNotFoundException(
                    String.format("No se encontro el Proveedor")
            );
        p = new ProviderDTO();
        mapping(pRepo.get(),p);

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
        provider.setAccountId(pOrigin.getAccount().getId());
    }

}
