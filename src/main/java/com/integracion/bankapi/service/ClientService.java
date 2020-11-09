package com.integracion.bankapi.service;

import com.integracion.bankapi.model.Client;
import com.integracion.bankapi.model.ClientDTO;
import com.integracion.bankapi.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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
            mapping(client,clientEdit);
            clientEdit =repo.save(clientEdit);
            mapping(clientEdit,client);
        }else{
            client= null;
        }

        return client;
    }

    public ClientDTO getClientByDni(Integer dni){
        Client clientRepo = repo.findByDni(dni);
        //Client clientRepo = repo.getClientByDni(dni);
        ClientDTO client = new ClientDTO();
        if(clientRepo != null){
            mapping(clientRepo,client);
        }
        else{
            client = null;
        }
        return client;
    }

    private void mapping (ClientDTO clientOrigin, Client client){
        client.setId(clientOrigin.getId());
        client.setName(clientOrigin.getName());
        client.setBusinessName(clientOrigin.getBusinessName());
        client.setCuil(clientOrigin.getCuil());
        client.setDni(clientOrigin.getDni());
        client.setLastName(clientOrigin.getLastName());
        client.setEmail(clientOrigin.getEmail());
        client.setStatus(clientOrigin.getStatus());

    }

    private void mapping (Client clientOrigin, ClientDTO client){
        client.setId(clientOrigin.getId());
        client.setName(clientOrigin.getName());
        client.setBusinessName(clientOrigin.getBusinessName());
        client.setCuil(clientOrigin.getCuil());
        client.setDni(clientOrigin.getDni());
        client.setLastName(clientOrigin.getLastName());
        client.setEmail(clientOrigin.getEmail());
        client.setStatus(clientOrigin.getStatus());
    }
}
