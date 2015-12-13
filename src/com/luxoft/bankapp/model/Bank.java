package com.luxoft.bankapp.model;

import com.luxoft.bankapp.service.Report;

import java.util.ArrayList;
import java.util.List;

public class Bank implements Report {
    //FIELDS
    private List<Client> clients;

    //CONSTRUCTORS
    public Bank() {
        clients = new ArrayList<>();
    }

    //METHODS
    public List<Client> getClients(){
        return clients;
    }

    @Override
    public void printReport() {
        clients.forEach(Client::printReport);
        System.out.println("----------------------------------------");
    }
}
