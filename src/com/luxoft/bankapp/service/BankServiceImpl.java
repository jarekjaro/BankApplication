package com.luxoft.bankapp.service;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

public class BankServiceImpl implements BankService {
    public void addClient(Bank bank, Client client) throws ClientExistsException {
        try{
            if(bank.getClients().indexOf(client)!=-1){
                throw new ClientExistsException(client);
            }else{
                bank.getClients().add(client);
            }
        } catch (ClientExistsException e) {
            e.getMessage();
        }
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
}
