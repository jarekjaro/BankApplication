package com.luxoft.bankapp.commands;

import com.luxoft.bankapp.exceptions.ClientDoesNotExistException;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.BankCommander;

import java.util.Scanner;


public class FindClientCommand implements Command {
    Scanner scanner = new Scanner(System.in);

    @Override
    public void execute() {
        System.out.println("");
        System.out.println("Insert Clients Name:");
        System.out.printf("%s", findClient(scanner.nextLine()));
        scanner.nextLine();
    }

    public Client findClient(String clientName) {
        try {
            return BankCommander.currentBank.getClientByName(clientName);
        } catch (ClientDoesNotExistException e) {
            System.out.println("CLIENT NOT IN DATABASE!!");
        }
        return null;
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Find Client Command");
    }
}

