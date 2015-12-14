package com.luxoft.bankapp.model;

import com.luxoft.bankapp.service.NotEnoughFundsException;

abstract class AbstractAccount implements Account {
    float balance;

    AbstractAccount(float balance) {
        this.balance = balance;
    }

    public float getBalance() {
        return balance;
    }

    public void deposit(float x) {
        balance += x;
    }

    public void withdraw(float x) throws NotEnoughFundsException {
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
