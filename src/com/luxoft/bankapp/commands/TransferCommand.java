package com.luxoft.bankapp.commands;

import com.luxoft.bankapp.exceptions.ClientDoesNotExistException;
import com.luxoft.bankapp.exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.CheckingAccount;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.SavingAccount;
import com.luxoft.bankapp.service.BankCommander;

import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class TransferCommand implements Command {
    @Override
    public void execute() {
        Scanner sc = new Scanner(System.in);
        float toTransfer;
        System.out.println("From which: (C)hecking account or (S)aving account would you like to transfer?");
        if (sc.nextLine().equals("C")) {
            Set<Account> accountSet = BankCommander.currentClient.getAccounts();
            for (Iterator<Account> iterator = accountSet.iterator(); iterator.hasNext(); ) {
                Account next = iterator.next();
                if(next instanceof CheckingAccount) BankCommander.currentClient.setActiveAccount(next);
            }
        } else {
            Set<Account> accountSet = BankCommander.currentClient.getAccounts();
            for (Iterator<Account> iterator = accountSet.iterator(); iterator.hasNext(); ) {
                Account next = iterator.next();
                if(next instanceof SavingAccount) BankCommander.currentClient.setActiveAccount(next);
            }
        }
        boolean amountToWithdrawFlag = true;
        do {
            System.out.println("How much would you like to transfer?");
            toTransfer = sc.nextFloat();
            sc.nextLine();
            if (toTransfer >= 0) {
                try {
                    BankCommander.currentClient.getActiveAccount().withdraw(toTransfer);
                    amountToWithdrawFlag = false;
                } catch (NotEnoughFundsException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("Please provide a positive number or 0 to exit.\n");
            }
        } while (amountToWithdrawFlag);

        boolean findCLientFlag = true;
        Client foundClient = null;
        do {
            System.out.println("To whom would you like to transfer?");
            try {
                foundClient = findClient(sc.nextLine());
                findCLientFlag = false;
            } catch (ClientDoesNotExistException e) {
                System.out.println(e.getMessage());
            }
        } while (findCLientFlag);
        System.out.println(foundClient);
        sc.nextLine();
        BankCommander.currentReceivingClient = foundClient;
        System.out.println("To (C)hecking account or to (S)aving account?");
        if (sc.nextLine().equals("C")) {
            Set<Account> accountSet = BankCommander.currentClient.getAccounts();
            for (Iterator<Account> iterator = accountSet.iterator(); iterator.hasNext(); ) {
                Account next = iterator.next();
                if(next instanceof CheckingAccount) BankCommander.currentClient.setActiveAccount(next);
            }
        } else {
            Set<Account> accountSet = BankCommander.currentClient.getAccounts();
            for (Iterator<Account> iterator = accountSet.iterator(); iterator.hasNext(); ) {
                Account next = iterator.next();
                if(next instanceof SavingAccount) BankCommander.currentClient.setActiveAccount(next);
            }
        }

        BankCommander.currentReceivingClient.getActiveAccount().deposit(toTransfer);
    }

    public Client findClient(String clientName) throws ClientDoesNotExistException {
        return BankCommander.currentBank.getClientByName(clientName);
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Transfer Command");
    }
}
