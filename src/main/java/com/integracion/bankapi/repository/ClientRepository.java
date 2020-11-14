package com.integracion.bankapi.repository;

import com.integracion.bankapi.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

     Optional<Client> findByDni(String dni);

     @Query(value = "select * from clients c where c.dni = ?1 OR c.cuil = ?2",nativeQuery = true)
     Optional<Client> findByDniOrCuil(String dni, String cuil);

     /*
     https://docs.spring.io/spring-data/jpa/docs/1.5.0.RELEASE/reference/html/jpa.repositories.html
         @Query(value = "select * from clients c where c.dni =  ?1",
                 nativeQuery = true)
         Client getClientByDni(Integer dni);
      */
}