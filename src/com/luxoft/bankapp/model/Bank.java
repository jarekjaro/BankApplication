package com.luxoft.bankapp.model;

import com.luxoft.bankapp.service.Report;

import java.util.ArrayList;
import java.util.List;

public class Bank implements Report {
    private List<Client> clients;
    private List<ClientRegistrationListener> listeners;

    public Bank() {
        clients = new ArrayList<>();
        listeners = new ArrayList<>();
        listeners.add(new PrintClientListener());
        listeners.add(new EmailNotificationListener());
    }

    public List<Client> getClients() {
        return (clients); //Collections.unmodifiableList ??
    }

    @Override
    public void printReport() {
        clients.forEach(Client::printReport);
        System.out.println("----------------------------------------");
    }

    public List<ClientRegistrationListener> getListeners() {
        return listeners;
    }

    public interface ClientRegistrationListener {
        void onClientAdded(Client client);
    }

    public class EmailNotificationListener implements ClientRegistrationListener {

        @Override
        public void onClientAdded(Client client) {
            System.out.printf("Notification email for a client %11s to be sent.\n", client);
        }
    }

    public class PrintClientListener implements ClientRegistrationListener {
        @Override
        public void onClientAdded(Client client) {
            System.out.printf("Client %11s added.\n",client);
        }
    }
}
