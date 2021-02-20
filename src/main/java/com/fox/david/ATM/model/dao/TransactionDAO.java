package com.fox.david.ATM.model.dao;

import com.fox.david.ATM.model.repository.TransactionRepository;

public class TransactionDAO {
    TransactionRepository transactionRepository;

    public TransactionDAO(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
}
