package com.integracion.bankapi.model;

import com.integracion.bankapi.model.exception.AccountLimitSurpassedException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class LimitValidator {

    public void validateAccountLimit(Account account, BigDecimal amount) {
        if (isSavingAccount(account) && amount.compareTo(account.getBalance()) == 1){
            throw new AccountLimitSurpassedException();
        }
    }

    private boolean isSavingAccount(Account account) {
        return AccountType.CA.getShortName().compareTo(account.getAccountType()) == 0;
    }

}
