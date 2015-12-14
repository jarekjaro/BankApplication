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

    public OverdraftLimitExceededException(CheckingAccount checkingAccount, float toWithdraw) {
        message = "For account "+ checkingAccount + " overdraft limit is: "
        + checkingAccount.getOverdraft() + " so cannot withdraw " +toWithdraw+" !";
    }
}
