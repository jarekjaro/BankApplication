package com.luxoft.bankapp.service;

import com.luxoft.bankapp.exceptions.ClientDoesNotExistException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

import java.util.Iterator;
import java.util.List;

public class BankServiceImpl implements BankService {
    public void addClient(Bank bank, Client client) {
        bank.getClients().add(client);
    }

    public void removeClient(Bank bank, Client client) {
        bank.getClients().remove(client);
    }

    public void addAccount(Client client, Account account) {
        client.getAccounts().add(account);
    }

    public void setActiveAccount(Client client, Account account) {
        client.setActiveAccount(account);
    }

    public Client getClient(Bank bank, String clientName) {
//        return bank.getClient(clientName);
        return new Client(clientName);//just for suppressing warnings

    }

    public Client findClient(Bank bank, String clientName) throws ClientDoesNotExistException {
        List<Client> clientsList = bank.getClients();
        Iterator it = clientsList.iterator();
        while (it.hasNext()) {
            Client client = (Client) it.next();
            if (client.getName().equals(clientName)) {
                return client;
            } else throw new ClientDoesNotExistException();
        }
        return null;
    }
}