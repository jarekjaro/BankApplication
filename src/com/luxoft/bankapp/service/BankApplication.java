package com.luxoft.bankapp.service;

import com.luxoft.bankapp.exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.model.*;

import java.util.*;

public class BankApplication {

    public static void main(String[] args) {
        Bank bank1 = new Bank();
        initialize(bank1);
        bank1.printReport();
        modifyBank(bank1);
        bank1.printReport();
        bank1.getClients().forEach(System.out::println);
//
//        List<Client> clients = bank1.getClients();
//        for (int i = 0;i<bank1.getClients().size();i++) {
//            System.out.println(clients.get(i));
//        }
        bank1.getClients().forEach(client ->{
            client.getAccounts().forEach(account -> account.decimalValue());
        });
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
            bank1.addAccount(client, new CheckingAccount(rand.nextFloat() * 100000, rand.nextFloat()*1000));
            bank1.addAccount(client, new SavingAccount(rand.nextFloat() * 100000));
        }

        for (Client client : clientInit) {
                bank1.addClient(client);
        }
    }

    private static void modifyBank(Bank bankToModify) {
        Set<Client> listOfClients = bankToModify.getClients();
        Set<Account> listToModify = new TreeSet<>();
        listOfClients.forEach(client -> {
            Set<Account> listOfClientAccounts = client.getAccounts();
            listOfClientAccounts.forEach(listToModify::add);
        });

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
