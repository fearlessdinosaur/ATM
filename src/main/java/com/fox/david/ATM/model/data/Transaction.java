package com.fox.david.ATM.model.data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Transaction {

    @Id
    String id;

    String value;

    @ManyToOne()
    @JoinColumn(name = "ACCOUNT_ID")
    Account account;

    public Transaction(String value, Account account){
        this.value = value;
        this.account = account;
    }

    public Transaction(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
