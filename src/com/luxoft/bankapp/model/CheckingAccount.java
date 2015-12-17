package com.luxoft.bankapp.model;

import com.luxoft.bankapp.exceptions.OverdraftLimitExceededException;

public class CheckingAccount extends AbstractAccount {

    private float overdraft;

    public CheckingAccount(float balance) {
        super(balance);
    }

    public CheckingAccount(float balance, float overdraft) {
        super(balance);
        this.overdraft = overdraft;
    }

    public void setOverdraft(float overdraft) {
        this.overdraft = overdraft;
    }

    public float getOverdraft() {
        return overdraft;
    }

    @Override
    public void withdraw(float amount) throws OverdraftLimitExceededException {
        if (getBalance() >= amount - overdraft) {
            balance -= amount;
        } else {
            throw new OverdraftLimitExceededException(this, amount);
        }
    }

    @Override
    public String toString() {
        return String.format("Checking account %,10.2f with overdraft %,10.2f", this.getBalance(), this.getOverdraft());
    }

    @Override
    public void printReport() {
        System.out.println(this.toString());
    }
}
