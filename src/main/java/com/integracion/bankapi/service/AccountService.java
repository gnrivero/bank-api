package com.integracion.bankapi.service;

import com.integracion.bankapi.model.*;
import com.integracion.bankapi.model.cbu.Cbu;
import com.integracion.bankapi.model.dto.AccountDTO;
import com.integracion.bankapi.model.exception.AccountNotFoundException;
import com.integracion.bankapi.model.exception.ClientNotFoundException;
import com.integracion.bankapi.model.exception.InvalidAccountType;
import com.integracion.bankapi.repository.AccountRepository;
import com.integracion.bankapi.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private AccountRepository accountRepo;
    private ClientRepository clientRepo;

    public AccountService (AccountRepository accountRepo, ClientRepository clientRepo){
        this.accountRepo = accountRepo;
        this.clientRepo = clientRepo;
    }

    public AccountDTO create(AccountDTO accountDTO) {

        //Account Type Validtion
        AccountType validAccountType = Arrays.stream(AccountType.values())
                                            .filter(at -> at.getShortName()
                                                            .equals(accountDTO.getAccountType()))
                                            .findFirst()
                                            .orElseThrow(InvalidAccountType::new);

        //Account Config
        accountDTO.setBalance(BigDecimal.ZERO);
        accountDTO.setName(validAccountType.getAccountTypeName());
        accountDTO.setActive(true);
        //Account Config

        Account account = toAccount(accountDTO);

        Optional<Client> client = clientRepo.findById(accountDTO.getClientId());

        if (client.isEmpty())
            throw new ClientNotFoundException("Cliente no encontrado");

        account.setClient(client.get());
        account = accountRepo.save(account);

        String cbu = new Cbu().setAccountId(account.getId()).getGeneratedValue();
        account.setIdentificationNumber(cbu);
        account = accountRepo.save(account);

        return toDTO(account);
    }

    public AccountDTO getAccountById(Integer id){

        Optional<Account> account = this.accountRepo.findById(id);

        if (account.isEmpty())
            throw new AccountNotFoundException();

        return toDTO(account.get());
    }

    public AccountDTO getAccountByIdentificationNumber(String identificationNumber){
        Optional<Account> account = this.accountRepo.findByIdentificationNumber(identificationNumber);

        if (account.isEmpty())
            throw new AccountNotFoundException();

        return toDTO(account.get());
    }

    public List<AccountDTO> getAccountByIdClient(Integer idClient) {

        List<Account> accounts = accountRepo.getAccountByClient(idClient);

        List<AccountDTO> accountDTOs = new ArrayList<AccountDTO>();

        accounts.forEach(a -> accountDTOs.add(toDTO(a)));

        return accountDTOs;
    }


    private Account toAccount(AccountDTO accountOrigin) {
        Account account = new Account();
        account.setId(accountOrigin.getId());
        account.setIdentificationNumber(accountOrigin.getIdentificationNumber());
        account.setBalance(accountOrigin.getBalance());
        account.setName(accountOrigin.getName());
        account.setAccountType(accountOrigin.getAccountType());
        account.setOverdraft(accountOrigin.getOverdraft());
        account.setActive(accountOrigin.getActive());
        return account;
    }

    private AccountDTO toDTO(Account accountOrigin) {

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(accountOrigin.getId());
        accountDTO.setIdentificationNumber(accountOrigin.getIdentificationNumber());
        accountDTO.setBalance(accountOrigin.getBalance());
        accountDTO.setName(accountOrigin.getName());
        accountDTO.setAccountType(accountOrigin.getAccountType());
        if("CC".compareTo(accountOrigin.getAccountType()) == 0) {
            accountDTO.setAccountTypeDescription(AccountType.CC.getAccountTypeName());
        } else if ("CA".compareTo(accountOrigin.getAccountType()) == 0){
            accountDTO.setAccountTypeDescription(AccountType.CA.getAccountTypeName());
        }
        accountDTO.setOverdraft(accountOrigin.getOverdraft());
        accountDTO.setActive(accountOrigin.getActive());

        return accountDTO;
    }


}
