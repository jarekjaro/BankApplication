package com.luxoft.bankapp.service;

import com.luxoft.bankapp.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BankApplication {

    public static void main(String[] args) {
        Bank bank1 = new Bank();
        initialize(bank1);
        bank1.printReport();
        modifyBank(bank1);
        bank1.printReport();
    }

    //METHODS
    public static void initialize(Bank bank1) {
        BankServiceImpl bs = new BankServiceImpl();

        Client[] clientInit = new Client[]{
                new Client("Janusz", Gender.MALE),
                new Client("Zosia", Gender.FEMALE),
                new Client("Maryla", Gender.FEMALE),
                new Client("Pawel", Gender.MALE),
                new Client("Stefan", Gender.MALE),
                new Client("Filip", Gender.MALE)
        };

        for (Client aClientInit : clientInit) {
            try {
                bs.addClient(bank1, aClientInit);
            } catch (ClientExistsException e) {
                System.out.println(e.getMessage());
            }
        }

        Random rand = new Random();
        //for each client add a fixed number of accounts with random starting balance
        int fix = 2;
        for (Client aClientInit : clientInit) {
            for (int g = 0; g < fix; g++) {
                if (rand.nextFloat() > 0.5) {
                    bs.addAccount(aClientInit, new CheckingAccount(rand.nextFloat() * 100000));
                } else {
                    bs.addAccount(aClientInit, new SavingAccount(rand.nextFloat() * 100000));
                }
            }
        }
    }

    public static void modifyBank(Bank bankToModify) {
        List<Client> listOfClients = bankToModify.getClients();
        List<Account> listToModify = new ArrayList<>();
        listOfClients.forEach(client -> {
            List<Account> listOfClientAccounts = client.getAccounts();
            listOfClientAccounts.forEach(listToModify::add);
        });

        Random rand = new Random();
        listToModify.forEach(account -> account.deposit(rand.nextFloat() * 10000));
        listToModify.forEach(account -> {
            float withdrawAmount = rand.nextFloat() * 10000000;
            try {
                account.withdraw(withdrawAmount);
            } catch (OverdraftLimitExceededException e) {
                System.out.println(e.getMessage());
            } catch (NotEnoughFundsException f) {
                System.out.println(f.getMessage());
            }
        });
    }
}
