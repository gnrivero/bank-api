package com.integracion.bankapi.service;

import com.integracion.bankapi.model.Client;
import com.integracion.bankapi.model.ClientDTO;
import com.integracion.bankapi.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
        Client clientRepo = new Client();
        mapping(client,clientRepo);
        clientRepo =repo.save(clientRepo);
        mapping(clientRepo,client);
        return client;

    }

    public ClientDTO getClientByDni(Integer dni){
        Client clientRepo = repo.findByDni(dni);
        //Client clientRepo = repo.getClientByDni(dni);
        ClientDTO client = new ClientDTO();
        if(clientRepo != null){
            mapping(clientRepo,client);
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
        client.setValid(clientOrigin.getValid());

    }

    private void mapping (Client clientOrigin, ClientDTO client){
        client.setId(clientOrigin.getId());
        client.setName(clientOrigin.getName());
        client.setBusinessName(clientOrigin.getBusinessName());
        client.setCuil(clientOrigin.getCuil());
        client.setDni(clientOrigin.getDni());
        client.setLastName(clientOrigin.getLastName());
        client.setEmail(clientOrigin.getEmail());
        client.setValid(clientOrigin.getValid());
    }
}
