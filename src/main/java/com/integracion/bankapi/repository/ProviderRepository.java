package com.integracion.bankapi.repository;


import com.integracion.bankapi.model.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Integer> {


    Optional<Provider> findByProviderCode(String providerCode);
/*
    @Query(value = "select * from transactions t where t.account_id =  ?1 ORDER BY date DESC ",
            nativeQuery = true)
    List<Transaction> getTransactionByAccount(Integer id);
*/
}

