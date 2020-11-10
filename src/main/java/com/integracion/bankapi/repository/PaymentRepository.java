package com.integracion.bankapi.repository;


import com.integracion.bankapi.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

/*
    @Query(value = "select * from transactions t where t.account_id =  ?1 ORDER BY date DESC ",
            nativeQuery = true)
    List<Transaction> getTransactionByAccount(Integer id);
    */
 */
}

