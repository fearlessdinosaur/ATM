package com.fox.david.ATM.model.dto;

public class AccountDTO {
    int balance;
    int overdraft;

    public AccountDTO(int balance,int overdraft) {
        this.balance = balance;
        this.overdraft = overdraft;
    }
}
