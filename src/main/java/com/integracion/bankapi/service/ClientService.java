package com.integracion.bankapi.service;

import com.integracion.bankapi.model.Account;
import com.integracion.bankapi.model.Client;
import com.integracion.bankapi.model.security.User;
import com.integracion.bankapi.model.dto.ClientDTO;
import com.integracion.bankapi.model.exception.AccountNotFoundException;
import com.integracion.bankapi.model.exception.ClientNotFoundException;
import com.integracion.bankapi.model.mapper.ClientMapper;
import com.integracion.bankapi.repository.AccountRepository;
import com.integracion.bankapi.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {

    private ClientRepository repo;
    private AccountRepository accountRepo;
    private ClientMapper mapper;
    private UserService userService;


    public ClientService (ClientRepository repo,
                          AccountRepository accountRepo,
                          ClientMapper mapper,
                          UserService userService
    ){
        this.repo = repo;
        this.accountRepo = accountRepo;
        this.mapper = mapper;
        this.userService = userService;
    }

    public Client create(Client client) {
        try {
            ClientDTO clientDTO = getClientByDniOrCuil(client.getDni(), client.getCuil());
        } catch (ClientNotFoundException e) {
            client.setActive(true);
            client = repo.save(client);
            userService.createNewUser(client.getEmail(), client);
            return client;
        }

        throw new ClientNotFoundException(
                String.format("Ya existe un cliente con DNI: %s, CUIL: %s", client.getDni(), client.getCuil())
        );
    }

    public ClientDTO getClientByDniOrCuil(String dni, String cuil){

        Optional<Client> client = Optional.empty();

        if (cuil != null)
            client = repo.findByCuil(cuil);

        if (client.isEmpty() && dni != null)
            client = repo.findByDni(dni);

        if (client.isEmpty()){
            throw new ClientNotFoundException(
                    String.format("El cliente DNI: %s, CUIL: %s no existe", dni, cuil)
            );
        }

        return mapper.toDto(client.get());
    }

    public ClientDTO getClientByIdentificationNumber(String identificationNumber){
        Optional<Account> account = accountRepo.findByIdentificationNumber(identificationNumber);
        if (account.isEmpty())
            throw new AccountNotFoundException();

        return  mapper.toDto(account.get().getClient());

    }


    public ClientDTO getClientById(Integer id) {
        Optional<Client> clientRepo = repo.findById(id);

        if(clientRepo.isEmpty()){
            throw new ClientNotFoundException(String.format("El cliente ID: %d no existe", id));
        }

        ClientDTO client = mapper.toDto(clientRepo.get());

        return client;
    }

}