package com.fox.david.ATM.model.dao;

import com.fox.david.ATM.model.dto.AccountDTO;
import com.fox.david.ATM.model.repository.AccountRepository;
import org.springframework.stereotype.Component;

@Component
public class AccountDAO {
    AccountRepository accountRepository;

    public AccountDAO(AccountRepository accountRepository){this.accountRepository = accountRepository;}



    public AccountDTO getBalance(long id){
        return null;
    }

    public boolean checkPin(long id,int pin) {
        return pin == accountRepository.findById(id).get().getPin();
    }
}
