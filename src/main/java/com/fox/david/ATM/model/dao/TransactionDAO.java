package com.fox.david.ATM.model.dao;

import com.fox.david.ATM.model.repository.TransactionRepository;
import org.springframework.stereotype.Component;

@Component
public class TransactionDAO {
    TransactionRepository transactionRepository;

    public TransactionDAO(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
}
