package com.luxoft.bankapp.model;

import com.luxoft.bankapp.exceptions.NotEnoughFundsException;

public class SavingAccount extends AbstractAccount {
    public SavingAccount(float balance) {
        super(balance);
    }

    @Override
    public String toString() {
        return String.format("Saving account   %,10.2f", this.getBalance());
    }

    public void withdraw(float amount) throws NotEnoughFundsException {
        if (getBalance() >= amount) {
            balance -= amount;
        } else {
            throw new NotEnoughFundsException(this,amount);
        }
    }

    @Override
    public void printReport() {
        this.toString();
    }
}
