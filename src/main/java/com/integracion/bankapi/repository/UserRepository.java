package com.integracion.bankapi.repository;

import com.integracion.bankapi.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {

}