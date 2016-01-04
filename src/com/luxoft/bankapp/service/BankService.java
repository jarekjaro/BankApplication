package com.luxoft.bankapp.service;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

import java.io.IOException;

public interface BankService {
    void addClient(Bank bank, Client client);
    void removeClient(Bank bank,Client client);
    void addAccount(Client client, Account account);
    void setActiveAccount(Client client, Account account);
    void saveClient(Client clientToSave);
    public Client loadClient(String objectPath) throws IOException, ClassNotFoundException;
}
