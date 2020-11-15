package com.integracion.bankapi.repository;


import com.integracion.bankapi.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    Payment findByElectronicCodeAndPaidAndDateAfter(String electronicCode, Boolean paid, LocalDate date);
    
    @Modifying
    @Transactional
    @Query(value = "delete from payments p where p.paid = ?2 and p.provider_id = ?1", nativeQuery = true)
    void removeExpired(Integer id,Boolean paid);
/*
    @Query(value = "select * from transactions t where t.account_id =  ?1 ORDER BY date DESC ",
            nativeQuery = true)
    List<Transaction> getTransactionByAccount(Integer id);
*/
}

