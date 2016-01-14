package com.luxoft.bankapp.service;

import com.luxoft.bankapp.exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.model.*;

import java.io.FileNotFoundException;
import java.util.*;

public class BankApplication {

    public static void main(String[] args) {
        Bank bank1 = new Bank();
        initialize(bank1);
        BankFeedService bankService = new BankFeedService();
        String testFilePath = "test.txt";
        try {
            bankService.loadFeed(testFilePath);
            bank1.getClients().forEach(System.out::println);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


//        if (args[0].equals("report")) {
//            bank1.printReport();
//            System.exit(0);
//        }
//        bank1.printReport();
//        modifyBank(bank1);
//        bank1.printReport();
//        bank1.getClients().forEach(System.out::println);
//        bank1.getClients().forEach(client ->{
//            client.getAccounts().forEach(account -> account.decimalValue());
//        });
    }

    public static void initialize(Bank bank1) {

        Client[] clientInit = new Client[]{
                new Client("Janusz", Gender.MALE),
                new Client("Zosia", Gender.FEMALE),
                new Client("Maryla", Gender.FEMALE),
                new Client("Pawel", Gender.MALE),
                new Client("Stefan", Gender.MALE),
                new Client("Filip", Gender.MALE)
        };
        Random rand = new Random();
        //for each client add a fixed number of accounts with random starting balance and overdraft
        for (Client client : clientInit) {
            Account accountToAdd = new CheckingAccount(rand.nextFloat() * 100000, rand.nextFloat()*1000);
            //checking account is active by default
            client.setActiveAccount(accountToAdd);
            bank1.addAccount(client, accountToAdd);
            accountToAdd=new SavingAccount(rand.nextFloat() * 100000);
            bank1.addAccount(client, accountToAdd);
        }

        for (Client client : clientInit) {
                bank1.addClient(client);
        }
    }

    private static void modifyBank(Bank bankToModify) {
        Set<Client> listOfClients = bankToModify.getClients();
        Set<Account> listToModify = new TreeSet<>();
//        listOfClients.forEach(client -> {
//            Set<Account> listOfClientAccounts = client.getAccounts();
//            listOfClientAccounts.forEach(listToModify::add);
//        });

        Random rand = new Random();
        listToModify.forEach(account -> account.deposit(rand.nextFloat() * 10000));
        listToModify.forEach(account -> {
            float withdrawAmount = rand.nextFloat() * 100000;
            try {
                account.withdraw(withdrawAmount);
            } catch (NotEnoughFundsException f) {
                System.out.println(f.getMessage());
            }
        });
    }
}
