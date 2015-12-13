package com.luxoft.bankapp.model;

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

    public void withdraw(float x) {
        if (getBalance() >= x) {
            balance -= x;
        } else {
            StringBuilder sb = new StringBuilder();
            sb
                    .append("The balance is ")
                    .append(getBalance())
                    .append(" so no withdraw of ")
                    .append(x)
                    .append(" is available.");
            System.out.println(sb);
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
