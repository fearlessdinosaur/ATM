package com.fox.david.ATM.model.dao;

import com.fox.david.ATM.model.data.Account;
import com.fox.david.ATM.model.dto.AccountDTO;
import com.fox.david.ATM.model.repository.AccountRepository;
import com.fox.david.ATM.utils.AccountToDTOMapper;
import org.springframework.stereotype.Component;

@Component
public class AccountDAO {
    AccountRepository accountRepository;

    public AccountDAO(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public boolean fundsAvailable(int amount, long id) throws Exception {
        if (accountRepository.findById(id).isPresent()) {
            Account account = accountRepository.findById(id).get();
            return (account.getBalance() + account.getOverdraft()) > amount;
        }
        throw new Exception("No Account Found");
    }


    public AccountDTO getBalance(long id) throws Exception {
        if (accountRepository.findById(id).isPresent()) {
            Account account = accountRepository.findById(id).get();
            return AccountToDTOMapper.mapAccountToAccountDTO(account);
        }
        throw new Exception("No Account Found");
    }

    public boolean checkPin(long id, int pin) throws Exception {
        if (accountRepository.findById(id).isPresent()) {
            return pin == accountRepository.findById(id).get().getPin();
        }
        throw new Exception("No Account Found");
    }
}
