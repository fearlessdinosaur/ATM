package com.fox.david.ATM.model.dao;

import com.fox.david.ATM.model.repository.AccountRepository;
import org.springframework.stereotype.Component;

@Component
public class AccountDAO {
    AccountRepository accountRepository;

    public AccountDAO(AccountRepository accountRepository){this.accountRepository = accountRepository;};
}
