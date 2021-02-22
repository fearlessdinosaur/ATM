package com.fox.david.ATM.model.data;

import javax.persistence.*;

@Entity
public class Transaction {

    @Id
    @GeneratedValue
    Long id;

    int value;

    @ManyToOne()
    @JoinColumn(name = "ACCOUNT_ID")
    Account account;

    public Transaction(int value, Account account) {
        this.value = value;
        this.account = account;
    }

    public Transaction(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
