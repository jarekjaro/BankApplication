package com.luxoft.bankapp.model;

import com.luxoft.bankapp.service.NotEnoughFundsException;

public class SavingAccount extends AbstractAccount {
    //CONSTRUCTORS
    public SavingAccount(float balance) {
        super(balance);
    }

    @Override
    public String toString() {
        return "Saving Account: ";
    }

    public void withdraw(float x) throws NotEnoughFundsException {
        if (getBalance() >= x) {
            balance -= x;
        } else {
            throw new NotEnoughFundsException(this,x);
        }
    }
}
