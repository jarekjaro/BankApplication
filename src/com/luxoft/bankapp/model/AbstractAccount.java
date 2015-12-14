package com.luxoft.bankapp.model;

import com.luxoft.bankapp.service.NotEnoughFundsException;

abstract class AbstractAccount implements Account {
    //FIELDS
    float balance;

    //CONSTRUCTORS
    AbstractAccount(float balance) {
        this.balance = balance;
    }

    //METHODS
    public float getBalance() {
        return balance;
    }

    public void deposit(float x) {
        balance += x;
    }

    public void withdraw(float x) throws NotEnoughFundsException{
        if (getBalance() >= x) {
            balance -= x;
        } else {
            throw new NotEnoughFundsException();
        }
    }

    public void printReport() {
        StringBuilder sb = new StringBuilder();
        sb
                .append("This ")
                .append(this.getClass().toString())
                .append(" balance is ")
                .append(getBalance());
        System.out.println(sb);
    }
}
