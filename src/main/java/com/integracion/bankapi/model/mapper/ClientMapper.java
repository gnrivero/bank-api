package com.integracion.bankapi.model.mapper;

import com.integracion.bankapi.model.Client;
import com.integracion.bankapi.model.dto.ClientDTO;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    public ClientDTO toDto(Client clientOrigin){
        ClientDTO client = new ClientDTO();
        client.setId(clientOrigin.getId());
        client.setName(clientOrigin.getName());
        client.setBusinessName(clientOrigin.getBusinessName());
        client.setCuil(clientOrigin.getCuil());
        client.setDni(clientOrigin.getDni());
        client.setLastName(clientOrigin.getLastName());
        client.setEmail(clientOrigin.getEmail());
        client.setActive(clientOrigin.getActive());

        return client;
    }

    public Client toClient(ClientDTO clientOrigin){
        Client client = new Client();
        client.setId(clientOrigin.getId());
        client.setName(clientOrigin.getName());
        client.setBusinessName(clientOrigin.getBusinessName());
        client.setCuil(clientOrigin.getCuil());
        client.setDni(clientOrigin.getDni());
        client.setLastName(clientOrigin.getLastName());
        client.setEmail(clientOrigin.getEmail());
        client.setActive(clientOrigin.getActive());
        return client;
    }

}