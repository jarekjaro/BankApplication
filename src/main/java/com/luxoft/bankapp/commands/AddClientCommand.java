package com.luxoft.bankapp.commands;

import com.luxoft.bankapp.service.BankCommander;

import java.util.Scanner;

public class AddClientCommand implements Command {
    @Override
    public void execute() {
        Scanner sc = new Scanner(System.in);
        String name = "";
        String surname = "";
        String phone = "";
        String email = "";
        float initialOverdraft = 0.0f;
        boolean nameFlag = true;
        boolean surnameFlag = true;
        boolean phoneFlag = true;
        boolean emailFlag = true;
        boolean overdraftFlag = true;
        do {
            String regex = "[a-zA-Z]*";
            System.out.println("Please provide a name for the new client:");
            String testName = sc.nextLine();
            if (testName.matches(regex)) {
                name = testName;
                nameFlag = false;
            } else System.out.println("The name should only contain letters!");
        } while (nameFlag);
        do {
            String regex = "[a-zA-Z]*";
            System.out.println("Please provide a surname for the new client:");
            String testSurname = sc.nextLine();
            if (testSurname.matches(regex)) {
                surname = testSurname;
                surnameFlag = false;
            } else System.out.println("The name should only contain letters!");
        } while (surnameFlag);
        do {
            String regex = "[+]?[0-9]{7,}";
            System.out.println("Please provide a phone number for the new client without spaces:");
            String testPhone = sc.nextLine();
            if (testPhone.matches(regex)) {
                phone = testPhone;
                phoneFlag = false;
            } else System.out.println("The name should only contain letters!");
        } while (phoneFlag);
        do {
            String regex = "[a-zA-Z0-9\\.]*[@][a-zA-Z0-9]*\\.[a-zA-Z]{2,}";
            System.out.println("Please provide an email for the new client:");
            String testEmail = sc.nextLine();
            if (testEmail.matches(regex)) {
                email = testEmail;
                emailFlag = false;
            } else System.out.println("The email should be in format similar to this: name@email.com");
        } while (emailFlag);
        do {
            String regex = "[0-9]*\\.?[0-9]{0,2}";
            System.out.println("Please provide the initial overdraft amount for the new client:");
            String testOverdraft = sc.nextLine();
            if (testOverdraft.matches(regex)) {
                initialOverdraft = Float.parseFloat(testOverdraft);
                overdraftFlag = false;
            } else System.out.println("The overdraft should be a number!");
        } while (overdraftFlag);
        BankCommander.activeBank.addNewClient(name, surname, phone, email, initialOverdraft);
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Add Client Command");
    }
}
