package com.fox.david.ATM.model.repository;


import com.fox.david.ATM.model.data.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {

}
