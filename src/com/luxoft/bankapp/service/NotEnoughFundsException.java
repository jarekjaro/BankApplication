package com.luxoft.bankapp.service;

import com.luxoft.bankapp.model.SavingAccount;

public class NotEnoughFundsException extends BankException {
    private static final long serialVersionUID = -7514992806614261531L;
    private final String message;

    public NotEnoughFundsException(){
        message = "Not enough funds exception.";
    }

    @Override
    public String getMessage() {
        return message;
    }

    public NotEnoughFundsException(SavingAccount accountToThrow, float amountToWithdraw) {
        message = String.format("For account %-17s cannot withdraw %,10.2f.",
                accountToThrow, amountToWithdraw);
    }
}
