package com.fox.david.ATM.model.dto;

public class AccountDTO {
    int balance;
    int overdraft;
    String message;

    public AccountDTO(int balance, int overdraft) {
        this.balance = balance;
        this.overdraft = overdraft;
    }

    public AccountDTO() {

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
