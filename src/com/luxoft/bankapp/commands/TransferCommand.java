package com.luxoft.bankapp.commands;

import com.luxoft.bankapp.exceptions.ClientDoesNotExistException;
import com.luxoft.bankapp.exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.BankCommander;

import java.util.Scanner;

public class TransferCommand implements Command {
    @Override
    public void execute() {
        Scanner sc = new Scanner(System.in);
        float toTransfer;
        System.out.println("From which: (C)hecking account or (S)aving account would you like to transfer?");
        if (sc.nextLine().equals("C")) {
            BankCommander.currentClient.setActiveAccount(BankCommander.currentClient.getAccounts().get(0));
        } else {
            BankCommander.currentClient.setActiveAccount(BankCommander.currentClient.getAccounts().get(1));
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
            BankCommander.currentReceivingClient.setActiveAccount(BankCommander.currentReceivingClient.getAccounts().get(0));
        } else {
            BankCommander.currentReceivingClient.setActiveAccount(BankCommander.currentReceivingClient.getAccounts().get(1));
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
