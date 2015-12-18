package com.luxoft.bankapp.model;

import com.luxoft.bankapp.exceptions.ClientDoesNotExistException;
import com.luxoft.bankapp.service.BankService;
import com.luxoft.bankapp.exceptions.ClientExistsException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Bank implements Report {
    private final List<Client> clients;
    private final List<ClientRegistrationListener> listeners;

    public Bank() {
        clients = new ArrayList<>();
        listeners = new ArrayList<>();
        registerEvent(new PrintClientListener());
        registerEvent(new EmailNotificationListener());
    }



    class EmailNotificationListener implements ClientRegistrationListener {

        @Override
        public void onClientAdded(Client client) {
            System.out.printf("Notification email for a client %11s to be sent.\n", client);
        }
    }

    class PrintClientListener implements ClientRegistrationListener {
        @Override
        public void onClientAdded(Client client) {
            System.out.printf("Client %11s added.\n", client);
        }
    }

    private void registerEvent(ClientRegistrationListener actionListener) {
        listeners.add(actionListener);
    }

    public List<Client> getClients() {
        return Collections.unmodifiableList(clients); //Collections.unmodifiableList ??
    }

    @Override
    public void printReport() {
        clients.forEach(Client::printReport);
        System.out.println("----------------------------------------");
    }

    private List<ClientRegistrationListener> getListeners() {
        return listeners;
    }

    public void addClient(Bank bank, Client client) {
        try {
            if (bank.getClients().indexOf(client) != -1) {
                throw new ClientExistsException(client);
            } else {
                clients.add(client);
            }
        } catch (ClientExistsException e) {
            e.getMessage();
        } finally {
            bank.getListeners().forEach(eventNotificationListener -> eventNotificationListener.onClientAdded(client));
        }
    }

    public boolean removeClient(Client client) {
        return clients.remove(client);
    }

    public Client getClientByName(String name) throws ClientDoesNotExistException {
        List<Client> tempList = this.getClients();
        for (Iterator<Client> iterator = tempList.iterator(); iterator.hasNext(); ) {
            Client client = iterator.next();
            if(client.getName().equals(name)) return client;
            else throw new ClientDoesNotExistException();
        }
        return null;
    }

    public void addAccount(Client client, Account account) {
        client.getAccounts().add(account);
    }

    public void setActiveAccount(Client client, Account account) {
        client.setActiveAccount(account);
    }

    public Client getClient(Bank bank, String clientName) {
        return null;
    }
}

