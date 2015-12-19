package com.luxoft.bankapp.commands;

import com.luxoft.bankapp.service.BankCommander;

import java.util.Scanner;

public class DepositCommand implements Command {
    @Override
    public void execute() {
        Scanner sc = new Scanner(System.in);
        System.out.println("To (C)hecking account or to (S)aving account?");
        if (sc.nextLine().equals("C")) {
            BankCommander.currentClient.setActiveAccount(BankCommander.currentClient.getAccounts().get(0));
        } else {
            BankCommander.currentClient.setActiveAccount(BankCommander.currentClient.getAccounts().get(1));
        }
        boolean amountOfDepositFlag = true;
        do {
            System.out.println("How much would you like to deposit?");
            float toDeposit = sc.nextFloat();
            if (toDeposit >= 0) {
                BankCommander.currentClient.getActiveAccount().deposit(toDeposit);
                amountOfDepositFlag = false;
            } else {
                System.out.println("Please provide a positive number or 0 to exit.\n");
            }
        } while (amountOfDepositFlag);
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Deposit Command");
    }
}
