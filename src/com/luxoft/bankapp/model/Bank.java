package com.luxoft.bankapp.model;

import com.luxoft.bankapp.exceptions.ClientDoesNotExistException;
import com.luxoft.bankapp.exceptions.ClientExistsException;

import java.util.*;

public class Bank implements Report {
    private Set<Client> clients;
    private Set<ClientRegistrationListener> listeners;
    private Map<String, Client> clientMap;

    public Bank() {
        clients = new HashSet<>();
        listeners = new HashSet<>();
        clientMap = new TreeMap<>();
        registerEvent(new PrintClientListener());
        registerEvent(new EmailNotificationListener());
    }

    @Override
    public void printReport() {
        clients.forEach(Client::printReport);
        System.out.println("----------------------------------------");
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

    public Set<ClientRegistrationListener> getListeners() {
        return Collections.unmodifiableSet(listeners);
    }

    public Set<Client> getClients() {
        return Collections.unmodifiableSet(clients);
    }

    public void addClient(Client client) {
        try {
            if (this.getClients().contains(client)) {
                throw new ClientExistsException(client);
            } else {
                clients.add(client);
            }
        } catch (ClientExistsException e) {
            e.getMessage();
        } finally {
            this.getListeners().forEach(eventNotificationListener -> eventNotificationListener.onClientAdded(client));
        }
    }

    public void addNewClient(String name, String surname, String phone, String email, float initialOverdraft) {
        Client clientToAdd = new Client(name, surname, phone, email, initialOverdraft);
        addClient(clientToAdd);
    }

    public boolean removeClient(Client client) {
        return clients.remove(client);
    }

    public void parseFeed(Map<String, String> propertiesMap) {
        //TODO
        String accounttype = null, balance = null, overdraft = null, name = null;
        Gender gender = null;
        Set<Map.Entry<String, String>> parsedMap = new TreeSet<>();
        parsedMap = propertiesMap.entrySet();
        for (Iterator<Map.Entry<String, String>> parsedMapIterator = parsedMap.iterator(); parsedMapIterator.hasNext(); ) {
            Map.Entry<String, String> mapEntry = parsedMapIterator.next();
            switch (mapEntry.getKey()) {
                case "accounttype":
                    accounttype = mapEntry.getValue();
                case "balance":
                    balance = mapEntry.getValue();
                case "overdraft":
                    overdraft = mapEntry.getValue();
                case "name":
                    name = mapEntry.getValue();
                case "gender":
                    if (mapEntry.getValue().equalsIgnoreCase("f")) {
                        gender = Gender.FEMALE;
                    } else {
                        gender = Gender.MALE;
                    }
            }
        }
        Client newClient = new Client(name, gender);
        Account newAccount;
        if (accounttype.equalsIgnoreCase("c")) {
            newAccount = new CheckingAccount(Float.parseFloat(balance), Float.parseFloat(overdraft));
        } else {
            newAccount = new SavingAccount(Float.parseFloat(balance));
        }
        addAccount(newClient, newAccount);
        //accounttype=c;balance=100;overdraft=50;name=John;gender=f;
    }

    public Client getClientByName(String name) throws ClientDoesNotExistException {
        boolean clientFoundFlag = false;
        for (Client client : clients) {
            if (client.getName().equals(name)) {
                clientFoundFlag = true;
                return client;
            }
        }
        if (!clientFoundFlag) {
            throw new ClientDoesNotExistException();
        }
        return null;
    }

    public boolean addAccount(Client client, Account account) {
        return client.accounts.add(account);
    }

    public void setActiveAccount(Client client, Account account) {
        client.setActiveAccount(account);
    }

    public Client getClient(Bank bank, String clientName) {
        return null;
    }

    public void addClientToMap(Client clientToAdd) {
        clientMap.put(clientToAdd.getName(), clientToAdd);
    }
}