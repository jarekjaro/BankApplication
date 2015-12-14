package com.luxoft.bankapp.model;

import com.luxoft.bankapp.service.NotEnoughFundsException;

public class SavingAccount extends AbstractAccount {
    public SavingAccount(float balance) {
        super(balance);
    }

    @Override
    public String toString() {
        return "Saving Account ";
    }

    public void withdraw(float amount) throws NotEnoughFundsException {
        if (getBalance() >= amount) {
            balance -= amount;
        } else {
            throw new NotEnoughFundsException(this,amount);
        }
    }
}
