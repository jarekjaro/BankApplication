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

    public NotEnoughFundsException(SavingAccount accountToThrow, float toWithdraw) {
        message = "For account "+ accountToThrow + "there is: "
                + accountToThrow.getBalance() + " funds"
                +" so cannot withdraw " +toWithdraw+" !";
    }
}
