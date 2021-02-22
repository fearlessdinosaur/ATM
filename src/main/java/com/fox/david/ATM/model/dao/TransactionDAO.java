package com.fox.david.ATM.model.dao;

import com.fox.david.ATM.model.data.Account;
import com.fox.david.ATM.model.data.Transaction;
import com.fox.david.ATM.model.repository.TransactionRepository;
import org.springframework.stereotype.Component;

@Component
public class TransactionDAO {
    TransactionRepository transactionRepository;

    public TransactionDAO(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void createTransaction(int amount, Account account) {
        transactionRepository.save(new Transaction(amount, account));
    }
}
