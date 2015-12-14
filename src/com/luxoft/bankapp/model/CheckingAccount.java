package com.luxoft.bankapp.model;

import com.luxoft.bankapp.service.NotEnoughFundsException;
import com.luxoft.bankapp.service.OverdraftLimitExceededException;

public class CheckingAccount extends AbstractAccount {
    //FIELDS
    private float overdraft;

    //CONSTRUCTORS
    public CheckingAccount(float balance) {
        super(balance);
    }

    //METHODS
    public void setOverdraft(float newOverdraft) {
        this.overdraft = newOverdraft;
    }

    public float getOverdraft() {
        return overdraft;
    }

    @Override
    public void withdraw(float x) throws OverdraftLimitExceededException {
        if (getBalance() >= x - overdraft) {
            balance -= x;
        } else {
            throw new OverdraftLimitExceededException(this, x);
        }
    }

    @Override
    public String toString() {
        return "Checking Account: ";
    }
}
