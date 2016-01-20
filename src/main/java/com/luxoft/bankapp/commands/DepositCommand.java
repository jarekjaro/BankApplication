package com.luxoft.bankapp.commands;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.CheckingAccount;
import com.luxoft.bankapp.model.SavingAccount;
import com.luxoft.bankapp.service.BankCommander;

import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class DepositCommand implements Command {
    @Override
    public void execute() {
        Scanner sc = new Scanner(System.in);
        System.out.println("To (C)hecking account or to (S)aving account?");
        if (sc.nextLine().equals("C")) {
            Set<Account> accountSet = BankCommander.activeClient.getAccounts();
            for (Iterator<Account> iterator = accountSet.iterator(); iterator.hasNext(); ) {
                Account next = iterator.next();
                if (next instanceof CheckingAccount) BankCommander.activeClient.setActiveAccount(next);
            }
        } else {
            Set<Account> accountSet = BankCommander.activeClient.getAccounts();
            for (Iterator<Account> iterator = accountSet.iterator(); iterator.hasNext(); ) {
                Account next = iterator.next();
                if (next instanceof SavingAccount) BankCommander.activeClient.setActiveAccount(next);
            }
        }
        boolean amountOfDepositFlag = true;
        do {
            System.out.println("How much would you like to deposit?");
            float toDeposit = sc.nextFloat();
            if (toDeposit >= 0) {
                BankCommander.activeClient.getActiveAccount().deposit(toDeposit);
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
