package com.integracion.bankapi.service;

import com.integracion.bankapi.model.Client;
import com.integracion.bankapi.model.dto.ClientDTO;
import com.integracion.bankapi.model.exception.ClientNotFoundException;
import com.integracion.bankapi.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {

    private ClientRepository repo;

    public ClientService (ClientRepository repo){
        this.repo = repo;
    }

    public Client create(Client client) {
        return repo.save(client);
    }

    public ClientDTO edit(ClientDTO client) {
        Optional<Client> clientRepo = repo.findById(client.getId());
        if(clientRepo.isPresent()){
            Client clientEdit = new Client();
            clientEdit.setAccounts(clientRepo.get().getAccounts());
            clientEdit = mapping(client);
            clientEdit = repo.save(clientEdit);
            client = mapping(clientEdit);
        } else{
            client= null;
        }

        return client;
    }

    public ClientDTO getClientByDniOrCuil(String dni, String cuil){

        Optional<Client> client = repo.findByDniOrCuil(dni, cuil);

        if (client.isEmpty()){
            throw new ClientNotFoundException(
                    String.format("El cliente DNI: %s, CUIL: %s no existe", dni, cuil)
            );
        }

        return mapping(client.get());
    }


    public ClientDTO getClientById(Integer id) {
        Optional<Client> clientRepo = repo.findById(id);

        if(clientRepo.isEmpty()){
            throw new ClientNotFoundException(String.format("El cliente ID: %d no existe", id));
        }

        ClientDTO client = mapping(clientRepo.get());

        return client;
    }

    private Client mapping (ClientDTO clientOrigin){
        Client client = new Client();
        client.setId(clientOrigin.getId());
        client.setName(clientOrigin.getName());
        client.setBusinessName(clientOrigin.getBusinessName());
        client.setCuil(clientOrigin.getCuil());
        client.setDni(clientOrigin.getDni());
        client.setLastName(clientOrigin.getLastName());
        client.setEmail(clientOrigin.getEmail());
        client.setStatus(clientOrigin.getStatus());
        return client;
    }

    private ClientDTO mapping (Client clientOrigin){
        ClientDTO client = new ClientDTO();
        client.setId(clientOrigin.getId());
        client.setName(clientOrigin.getName());
        client.setBusinessName(clientOrigin.getBusinessName());
        client.setCuil(clientOrigin.getCuil());
        client.setDni(clientOrigin.getDni());
        client.setLastName(clientOrigin.getLastName());
        client.setEmail(clientOrigin.getEmail());
        client.setStatus(clientOrigin.getStatus());

        return client;
    }
}
