package com.luxoft.bankapp.model;

import com.luxoft.bankapp.exceptions.NotEnoughFundsException;

public abstract class AbstractAccount implements Account {
    float balance;
    private int id;
    private float overdraft;

    AbstractAccount(float balance) {
        this.balance = balance;
    }

    public AbstractAccount(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return (balance != +0.0f ? Float.floatToIntBits(balance) : 0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractAccount that = (AbstractAccount) o;

        return Float.compare(that.balance, balance) == 0;

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

    public float getBalance() {
        return balance;
    }

    public float getOverdraft() {
        return overdraft;
    }
}
