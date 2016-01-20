package com.luxoft.bankapp.model;

import com.luxoft.bankapp.exceptions.OverdraftLimitExceededException;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class CheckingAccount extends AbstractAccount {
    float balance;
    private int type;
    private int accNo;
    private float overdraft;

    public CheckingAccount(float balance) {
        super(balance);
    }

    public CheckingAccount(float balance, float overdraft) {
        super(balance);
        this.overdraft = overdraft;
    }

    public CheckingAccount(int accNo) {
        super(accNo);
    }

    public CheckingAccount(int accNo, float balance, float overdraft, int type) {
        super(accNo, balance, overdraft, type);
    }

    public void parseFeed(Map<String, String> propertiesMap) {
        String accountType = null, balance = null, overdraft = null;
        Set<Map.Entry<String, String>> parsedMap = new TreeSet<>();
        parsedMap = propertiesMap.entrySet();
        for (Iterator<Map.Entry<String, String>> parsedMapIterator = parsedMap.iterator(); parsedMapIterator.hasNext(); ) {
            Map.Entry<String, String> mapEntry = parsedMapIterator.next();
            switch (mapEntry.getKey()) {
                case "balance":
                    balance = mapEntry.getValue();
                    break;
                case "overdraft":
                    overdraft = mapEntry.getValue();
                    break;
            }
        }
        this.balance = Float.parseFloat(balance);
        this.overdraft = Float.parseFloat(overdraft);
    }

    @Override
    public void printReport() {
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        return String.format("Checking account %,10.2f with overdraft %,10.2f", this.getBalance(), this.getOverdraft());
    }

    public float getOverdraft() {
        return overdraft;
    }

    public void setOverdraft(float overdraft) {
        this.overdraft = overdraft;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (overdraft != +0.0f ? Float.floatToIntBits(overdraft) : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        CheckingAccount that = (CheckingAccount) o;

        return Float.compare(that.overdraft, overdraft) == 0;

    }

    @Override
    public void withdraw(float amount) throws OverdraftLimitExceededException {
        if (getBalance() >= amount - overdraft) {
            balance -= amount;
        } else {
            throw new OverdraftLimitExceededException(this, amount);
        }
    }
}
