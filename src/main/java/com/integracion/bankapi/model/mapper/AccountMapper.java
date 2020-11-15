package com.integracion.bankapi.model.mapper;

import com.integracion.bankapi.model.Account;
import com.integracion.bankapi.model.AccountType;
import com.integracion.bankapi.model.dto.AccountDTO;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public Account toAccount(AccountDTO accountOrigin) {
        Account account = new Account();
        account.setId(accountOrigin.getId());
        account.setIdentificationNumber(accountOrigin.getIdentificationNumber());
        account.setBalance(accountOrigin.getBalance());
        account.setName(accountOrigin.getName());
        account.setAccountType(accountOrigin.getAccountType());
        account.setOverdraft(accountOrigin.getOverdraft());
        account.setActive(accountOrigin.getActive());
        return account;
    }

    public AccountDTO toDTO(Account accountOrigin) {

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(accountOrigin.getId());
        accountDTO.setIdentificationNumber(accountOrigin.getIdentificationNumber());
        accountDTO.setBalance(accountOrigin.getBalance());
        accountDTO.setName(accountOrigin.getName());
        accountDTO.setAccountType(accountOrigin.getAccountType());
        if("CC".compareTo(accountOrigin.getAccountType()) == 0) {
            accountDTO.setAccountTypeDescription(AccountType.CC.getAccountTypeName());
        } else if ("CA".compareTo(accountOrigin.getAccountType()) == 0){
            accountDTO.setAccountTypeDescription(AccountType.CA.getAccountTypeName());
        }
        accountDTO.setOverdraft(accountOrigin.getOverdraft());
        accountDTO.setActive(accountOrigin.getActive());

        return accountDTO;
    }

}