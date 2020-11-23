package com.integracion.bankapi.repository;

import com.integracion.bankapi.model.security.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {

}