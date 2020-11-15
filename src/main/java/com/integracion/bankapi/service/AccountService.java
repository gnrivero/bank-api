package com.integracion.bankapi.service;

import com.integracion.bankapi.model.Account;
import com.integracion.bankapi.model.AccountType;
import com.integracion.bankapi.model.Client;
import com.integracion.bankapi.model.cbu.Cbu;
import com.integracion.bankapi.model.dto.AccountDTO;
import com.integracion.bankapi.model.exception.AccountNotFoundException;
import com.integracion.bankapi.model.exception.ClientNotFoundException;
import com.integracion.bankapi.model.exception.InvalidAccountType;
import com.integracion.bankapi.model.mapper.AccountMapper;
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
    private AccountMapper mapper;

    public AccountService (
            AccountRepository accountRepo,
            ClientRepository clientRepo,
            AccountMapper mapper) {
        this.accountRepo = accountRepo;
        this.clientRepo = clientRepo;
        this.mapper = mapper;
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

        Account account = mapper.toAccount(accountDTO);

        Optional<Client> client = this.clientRepo.findByCuil(accountDTO.getClientCuil());

        if (client.isEmpty())
            throw new ClientNotFoundException(
                    String.format("El Cliente con CUIL: %s no existe", accountDTO.getClientCuil())
            );

        account.setClient(client.get());
        account = accountRepo.save(account);

        String cbu = new Cbu().setAccountId(account.getId()).getGeneratedValue();
        account.setIdentificationNumber(cbu);
        account = accountRepo.save(account);

        return mapper.toDTO(account);
    }

    public AccountDTO getAccountById(Integer id){

        Optional<Account> account = this.accountRepo.findById(id);

        if (account.isEmpty())
            throw new AccountNotFoundException();

        return mapper.toDTO(account.get());
    }

    public AccountDTO getAccountByIdentificationNumber(String identificationNumber){
        Optional<Account> account = this.accountRepo.findByIdentificationNumber(identificationNumber);

        if (account.isEmpty())
            throw new AccountNotFoundException();

        return mapper.toDTO(account.get());
    }

    public List<AccountDTO> getAccountByIdClient(Integer idClient) {

        List<Account> accounts = accountRepo.getAccountByClient(idClient);

        List<AccountDTO> accountDTOs = new ArrayList<AccountDTO>();

        accounts.forEach(a -> accountDTOs.add(mapper.toDTO(a)));

        return accountDTOs;
    }

    public Integer patchAccount(BigDecimal overdraft, Integer id) {
        return accountRepo.updateAccountOverdraft(overdraft, id);
    }

}