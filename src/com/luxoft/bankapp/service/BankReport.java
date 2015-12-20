package com.luxoft.bankapp.service;

import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

import java.util.Set;
import java.util.TreeSet;

public class BankReport {
    public void getNumberOfClients(Bank bank) {
        System.out.println("The bank has: " + bank.getClients().size() + " clients.");
    }

    public void getAccountsNumber(Bank bank) {
        int accountsNo = 0;
        for (Client client : bank.getClients()) {
            accountsNo += client.getAccounts().size();
        }
        System.out.println("The bank has: " + accountsNo + " accounts.");
    }

    public void getClientsSorted(Bank bank) {
        Set<Client> sortedSet = new TreeSet<>(bank.getClients());
        for (Client client : sortedSet) {
            System.out.println(client);
        }
    }
}
