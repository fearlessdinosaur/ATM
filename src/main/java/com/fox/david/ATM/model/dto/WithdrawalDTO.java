package com.fox.david.ATM.model.dto;

public class WithdrawalDTO {
    int amount;
    int pin;

    public WithdrawalDTO(String accountId, int amount, int pin) {
        this.amount = amount;
        this.pin = pin;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }
}
