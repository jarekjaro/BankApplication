package com.luxoft.bankapp.model;

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

    @Override
    public void withdraw(float x) {
        if (getBalance() >= x-overdraft) {
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
    @Override
    public String toString() {
        return "Checking Account: ";
    }
}
