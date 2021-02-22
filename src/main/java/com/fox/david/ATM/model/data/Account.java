package com.fox.david.ATM.model.data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Account {

    @Id
    private Long accountId;

    private int pin;

    private int balance;

    private int overdraft;

    public Account(int pin, int balance, int overdraft) {
        this.pin = pin;
        this.balance = balance;
        this.overdraft = overdraft;
    }

    public Account(long id,int pin, int balance, int overdraft) {
        this.accountId = id;
        this.pin = pin;
        this.balance = balance;
        this.overdraft = overdraft;
    }

    public Account() {

    }

    public Long getId() {
        return accountId;
    }

    public void setId(Long id) {
        this.accountId = id;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getOverdraft() {
        return overdraft;
    }

    public void setOverdraft(int overdraft) {
        this.overdraft = overdraft;
    }
}
