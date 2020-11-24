package com.integracion.bankapi.repository;

import com.integracion.bankapi.model.security.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {

    @Query(value = "select * from users u where u.username =  ?1",
            nativeQuery = true)
    Optional<User> findByUsername(String username);

}