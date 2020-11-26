package com.integracion.bankapi.repository;

import com.integracion.bankapi.model.security.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {

    @Query(value = "select * from users u where u.username =  ?1",
            nativeQuery = true)
    Optional<User> findByUsername(String username);

    @Modifying
    @Transactional
    @Query(value = "update users SET password = ?1, password_reset = 1 where client_id = ?2", nativeQuery = true)
    Integer updateUserPassword(String password, Integer id);

}