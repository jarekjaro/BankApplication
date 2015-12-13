package com.luxoft.bankapp.model;

public class SavingAccount extends AbstractAccount {
    //CONSTRUCTORS
    public SavingAccount(float balance) {
        super(balance);
    }

    @Override
    public String toString() {
        return "Saving Account: ";
    }
}
