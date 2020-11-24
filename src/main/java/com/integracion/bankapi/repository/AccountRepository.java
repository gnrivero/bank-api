package com.integracion.bankapi.repository;


import com.integracion.bankapi.model.Account;
import com.integracion.bankapi.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {


    @Query(value = "select * from accounts a where a.identification_number = ?1", nativeQuery = true)
    Optional<Account> findByIdentificationNumber(String identificationNumber);

    @Query(value = "select * from accounts a where a.client_id =  ?1", nativeQuery = true)
    List<Account> getAccountByClient(Integer id);

    @Query(value = "select * from accounts  where account_type= 'CC' and balance < 0 and id != 10", nativeQuery = true)
    List<Account> getAccountByAccountTypeAndOverdraft();

    @Query(value = "select sum(if(operation_type='EXPENDITURE',amount,(-1)*amount)) from transactions WHERE date > CURRENT_DATE - INTERVAL 30 DAY and account_id= ?1", nativeQuery = true)
    BigDecimal getBalanceStartMonth(Integer id);

    @Modifying
    @Transactional
    @Query(value = "update accounts SET overdraft = ?1 where id = ?2", nativeQuery = true)
    Integer updateAccountOverdraft(BigDecimal overdraft, Integer id);

}