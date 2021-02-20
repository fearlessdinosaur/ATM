package com.fox.david.ATM.model.repository;

import com.fox.david.ATM.model.data.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account,Long> {
}
