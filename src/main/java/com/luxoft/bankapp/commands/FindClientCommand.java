package com.luxoft.bankapp.commands;

import com.luxoft.bankapp.exceptions.ClientDoesNotExistException;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.BankCommander;

import java.util.Scanner;

public class FindClientCommand implements Command {
    Scanner scanner = new Scanner(System.in);

    @Override
    public void execute() {
        Client foundClient = null;
        boolean clientNameFlag = true;
        do {
            System.out.println("Insert Client Name:");
            try {
                foundClient = findClient(scanner.nextLine());
                clientNameFlag = false;
            } catch (ClientDoesNotExistException e) {
                System.out.println(e.getMessage());
            }
        } while (clientNameFlag);
        System.out.println(foundClient);
        scanner.nextLine();
        BankCommander.activeClient = foundClient;
        BankCommander.activeBank.addClientToMap(BankCommander.activeClient);
    }

    public Client findClient(String clientName) throws ClientDoesNotExistException {
        return BankCommander.activeBank.getClientByName(clientName);
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Find Client Command");
    }
}

