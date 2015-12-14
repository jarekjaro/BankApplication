package com.luxoft.bankapp.model;

import com.luxoft.bankapp.service.EventNotificationListener;
import com.luxoft.bankapp.service.Report;

import java.util.ArrayList;
import java.util.List;

public class Bank implements Report {
    private List<Client> clients;
    private List<EventNotificationListener> listeners;

    public Bank() {
        clients = new ArrayList<>();
        listeners = new ArrayList<>();
    }

    public List<Client> getClients() {
        return (clients); //Collections.unmodifiableList ??
    }

    @Override
    public void printReport() {
        clients.forEach(Client::printReport);
        System.out.println("----------------------------------------");
    }

    public List<EventNotificationListener> getListeners() {
        return listeners;
    }
}
