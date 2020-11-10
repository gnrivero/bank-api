package com.integracion.bankapi.repository;


import com.integracion.bankapi.model.Account;
import com.integracion.bankapi.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {


    @Query(value = "select * from transactions t where t.account_id =  ?1 ORDER BY date DESC ",
            nativeQuery = true)
    List<Transaction> getTransactionByAccount(Integer id);
}

