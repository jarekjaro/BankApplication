package com.luxoft.bankapp.commands;

import com.luxoft.bankapp.service.BankCommander;

import java.util.Scanner;

public class AddClientCommand implements Command {
    @Override
    public void execute() {
        Scanner sc = new Scanner(System.in);
        String name;
        String surname;
        String phone;
        String email;
        float initialOverdraft;
        boolean nameFlag = true;
        boolean surnnameFlag = true;
        boolean phoneFlag = true;
        boolean emailFlag = true;
        do {
            System.out.println("Please provide a name for the new client:");
            sc.nextLine();
        } while (nameFlag);
        BankCommander.currentBank.addNewClient(name, surname, phone, email, initialOverdraft);
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Add Client Command");
    }
}
