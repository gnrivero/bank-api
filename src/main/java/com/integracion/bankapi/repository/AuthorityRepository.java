package com.integracion.bankapi.repository;

import com.integracion.bankapi.model.security.Authority;
import org.springframework.data.repository.CrudRepository;

public interface AuthorityRepository extends CrudRepository<Authority, Integer> {

}