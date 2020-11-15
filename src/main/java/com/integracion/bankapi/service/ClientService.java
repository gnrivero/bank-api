package com.integracion.bankapi.service;

import com.integracion.bankapi.model.Client;
import com.integracion.bankapi.model.dto.ClientDTO;
import com.integracion.bankapi.model.exception.ClientNotFoundException;
import com.integracion.bankapi.model.mapper.ClientMapper;
import com.integracion.bankapi.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {

    private ClientRepository repo;
    private ClientMapper mapper;

    public ClientService (ClientRepository repo, ClientMapper mapper){
        this.repo = repo;
        this.mapper = mapper;
    }

    public Client create(Client client) {
        client.setActive(true);
        return repo.save(client);
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


    public ClientDTO getClientById(Integer id) {
        Optional<Client> clientRepo = repo.findById(id);

        if(clientRepo.isEmpty()){
            throw new ClientNotFoundException(String.format("El cliente ID: %d no existe", id));
        }

        ClientDTO client = mapper.toDto(clientRepo.get());

        return client;
    }

}