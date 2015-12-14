package com.luxoft.bankapp.service;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

public interface BankService {
    void addClient(Bank bank, Client client) throws ClientExistsException;
    void removeClient(Bank bank,Client client);
    void addAccount(Client client, Account account);
    void setActiveAccount(Client client, Account account);
}
