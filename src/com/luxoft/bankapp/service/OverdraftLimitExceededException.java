package com.luxoft.bankapp.service;

import com.luxoft.bankapp.model.CheckingAccount;

public class OverdraftLimitExceededException extends NotEnoughFundsException {
    private String message;

    public OverdraftLimitExceededException() {
        message = "Overdraft limit exceeded!";
    }

    @Override
    public String getMessage() {
        return message;
    }

    public OverdraftLimitExceededException(CheckingAccount checkingAccount, float amountToWithdraw) {
        message = String.format("For account %-17s balance is: %,10.2f and overdraft limit is: %8.2f so cannot withdraw %,10.2f.",
                checkingAccount, checkingAccount.getBalance(),
                checkingAccount.getOverdraft(), amountToWithdraw);
    }
}
