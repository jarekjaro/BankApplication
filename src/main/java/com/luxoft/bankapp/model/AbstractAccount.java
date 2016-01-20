package com.luxoft.bankapp.model;

import com.luxoft.bankapp.exceptions.NotEnoughFundsException;

public abstract class AbstractAccount implements Account {
    float balance;
    private int type;
    private int accNo;
    private float overdraft;

    AbstractAccount(float balance) {
        this.balance = balance;
    }

    public AbstractAccount(int accNo) {
        this.accNo = accNo;
    }

    public AbstractAccount(int accNo, float balance, float overdraft, int type) {
        this.accNo = accNo;
        this.balance = balance;
        this.overdraft = overdraft;
        this.type = type;
    }

    public AbstractAccount(int accNo, float balance, int type) {
        this.accNo = accNo;
        this.balance = balance;
        this.type = type;
    }

    public int getAccNo() {
        return accNo;
    }

    public void setAccNo(int accNo) {
        this.accNo = accNo;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public float getOverdraft() {
        return overdraft;
    }

    public void setOverdraft(float overdraft) {
        this.overdraft = overdraft;
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

    public void setBalance(float balance) {
        this.balance = balance;
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
}
