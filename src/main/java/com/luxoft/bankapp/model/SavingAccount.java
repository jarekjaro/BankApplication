package com.luxoft.bankapp.model;

import com.luxoft.bankapp.exceptions.NotEnoughFundsException;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class SavingAccount extends AbstractAccount {
    public SavingAccount(float balance) {
        super(balance);
    }

    @Override
    public String toString() {
        return String.format("Saving account   %,10.2f", this.getBalance());
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
            }
        }
        this.balance=Float.parseFloat(balance);
    }
    public void withdraw(float amount) throws NotEnoughFundsException {
        if (getBalance() >= amount) {
            balance -= amount;
        } else {
            throw new NotEnoughFundsException(this,amount);
        }
    }

    @Override
    public void printReport() {
        System.out.println(this.toString());
    }
}
