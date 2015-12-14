package com.luxoft.bankapp.service;

import com.luxoft.bankapp.model.SavingAccount;

public class NotEnoughFundsException extends BankException {
    private String message;

    public NotEnoughFundsException(){
        message = "Not enough funds exception.";
    }

    @Override
    public String getMessage() {
        return message;
    }

    public NotEnoughFundsException(SavingAccount accountToThrow, float amountToWithdraw) {
        message = String.format("For account %-17s balance is: %,10.2f so cannot withdraw %,10.2f.",
                accountToThrow, accountToThrow.getBalance(), amountToWithdraw);
    }
}
