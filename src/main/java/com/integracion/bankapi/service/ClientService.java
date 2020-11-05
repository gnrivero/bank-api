package com.integracion.bankapi.service;

import com.integracion.bankapi.model.Client;
import com.integracion.bankapi.repository.ClientRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private ClientRepository repo;

    public ClientService (ClientRepository repo){
        this.repo = repo;
    }

    public Client create(Client client) {
        return repo.save(client);
    }

}
