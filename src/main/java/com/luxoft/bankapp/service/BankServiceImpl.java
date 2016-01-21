package com.luxoft.bankapp.service;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

import java.io.*;

public class BankServiceImpl implements BankService {

    public void addClient(Bank bank, Client client) {
        // bank.getClientsMap().add(client);
    }

    public void removeClient(Bank bank, Client client) {
        //bank.getClientsMap().remove(client);
    }

    public void addAccount(Client client, Account account) {
        //client.getAccounts().add(account);
    }

    public void setActiveAccount(Client client, Account account) {
        //client.setActiveAccount(account);
    }

    @Override
    public void saveClient(Client client) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(client.getName() + ".object")))) {
            oos.writeObject(client);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Client loadClient(String objectPath) {
        File clientFile = new File(objectPath);
        Client client = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(clientFile))) {
            client = (Client) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        } catch (IOException | ClassNotFoundException f) {
            f.printStackTrace();
        }
        return client;
    }
}