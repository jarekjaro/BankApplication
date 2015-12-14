package com.luxoft.bankapp.model;

import com.luxoft.bankapp.service.Report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bank implements Report {
    private List<Client> clients;

    public Bank() {
        clients = new ArrayList<>();
    }

    public List<Client> getClients() {
        return (clients); //Collections.unmodifiableList ??
    }

    @Override
    public void printReport() {
        clients.forEach(Client::printReport);
        System.out.println("----------------------------------------");
    }
}
