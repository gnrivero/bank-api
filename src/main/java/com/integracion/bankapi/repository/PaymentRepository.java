package com.integracion.bankapi.repository;


import com.integracion.bankapi.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    Optional<Payment> findByElectronicCodeAndPaidAndDateAfter(String electronicCode, Boolean paid, LocalDate date);

    List<Payment> findByElectronicCodeAndPaid(String electronicCode, Boolean paid);
    
    @Modifying
    @Transactional
    @Query(value = "delete from payments where paid = false and provider_id = ?1", nativeQuery = true)
    void removeExpired(Integer id);


    @Query(value = "select * from payments WHERE paid = true and provider_id = ?1 and date > CURRENT_DATE - INTERVAL 30 DAY ", nativeQuery = true)
    List<Payment> getLastMonthsPayments(Integer id);





/*
    @Query(value = "select * from transactions t where t.account_id =  ?1 ORDER BY date DESC ",
            nativeQuery = true)
    List<Transaction> getTransactionByAccount(Integer id);
*/
}

