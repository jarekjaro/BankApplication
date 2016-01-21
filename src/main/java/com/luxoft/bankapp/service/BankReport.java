package com.luxoft.bankapp.service;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.CheckingAccount;
import com.luxoft.bankapp.model.Client;

import java.util.*;

public class BankReport {
    public void getNumberOfClients(Bank bank) {
        System.out.println("The bank has: " + bank.getClientsMap().size() + " clients.");
    }

    public void getAccountsNumber(Bank bank) {
        int accountsNo = 0;
        for (Client client : bank.getClientsMap()) {
            accountsNo += client.getAccounts().size();
        }
        System.out.println("The bank has: " + accountsNo + " accounts.");
    }

    public void getClientsSorted(Bank bank) {
        Set<Client> sortedSet = new TreeSet<>(bank.getClientsMap());
        for (Client client : sortedSet) {
            System.out.println(client);
        }
    }

    public void getBankCreditSum(Bank bank) {
        Set<Client> tempSet = bank.getClientsMap();
        float creditSum = 0;
        for (Iterator<Client> iterator = tempSet.iterator(); iterator.hasNext(); ) {
            Client next = iterator.next();
            Set<Account> acocuntsTmp = next.getAccounts();
            for (Iterator<Account> accountIterator = acocuntsTmp.iterator(); accountIterator.hasNext(); ) {
                try {
                    CheckingAccount account = (CheckingAccount) accountIterator.next();
                    creditSum += account.getOverdraft();
                } catch (ClassCastException ex) {

                }

            }
        }
        System.out.println(String.format("Sum of all Credit is: %,.2f", creditSum));
    }

    public void getClientsByCity(Bank bank) {
        Map<String, Set<Client>> mapByCity = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        Set<String> city = new TreeSet<>();
        Set<Client> bankClients = bank.getClientsMap();
        for (Iterator iterator = bankClients.iterator(); iterator.hasNext(); ) {
            Client next = (Client) iterator.next();
            city.add(next.getCity());
        }

        for (Iterator<String> iterator = city.iterator(); iterator.hasNext(); ) {
            Set<Client> clientsWithSameCity = new TreeSet<>();
            String nextCity = iterator.next();
            for (Iterator<Client> clientIterator = bankClients.iterator(); clientIterator.hasNext(); ) {
                Client nextClient = clientIterator.next();
                if (nextCity.equals(nextClient.getCity())) {
                    clientsWithSameCity.add(nextClient);
                }
            }
            mapByCity.put(nextCity, clientsWithSameCity);
        }
        city.forEach(diffCity -> System.out.println(mapByCity.get(city)));
    }
}