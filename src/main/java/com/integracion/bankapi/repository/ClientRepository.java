package com.integracion.bankapi.repository;

import com.integracion.bankapi.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

     Optional<Client> findByDni(String dni);

     Optional<Client> findByCuil(String cuil);

}