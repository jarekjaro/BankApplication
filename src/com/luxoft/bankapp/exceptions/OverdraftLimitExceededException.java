package com.luxoft.bankapp.exceptions;

import com.luxoft.bankapp.model.CheckingAccount;

public class OverdraftLimitExceededException extends NotEnoughFundsException {
    private static final long serialVersionUID = 8461325912285706860L;
    private final String message;

    public OverdraftLimitExceededException() {
        message = "Overdraft limit exceeded!";
    }

    @Override
    public String getMessage() {
        return message;
    }

    public OverdraftLimitExceededException(CheckingAccount checkingAccount, float amountToWithdraw) {
        message = String.format("For account %-17s cannot withdraw %,10.2f.",
                                checkingAccount, amountToWithdraw);
    }
}
