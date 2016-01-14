package com.luxoft.bankapp.commands;

import com.luxoft.bankapp.exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.CheckingAccount;
import com.luxoft.bankapp.model.SavingAccount;
import com.luxoft.bankapp.service.BankCommander;

import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class WithdrawCommand implements Command {
    @Override
    public void execute() {
        Scanner sc = new Scanner(System.in);
        System.out.println("From which: (C)hecking account or (S)aving account would you like to withdraw?");
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
        boolean amountToWithdraw = true;
        do {
            System.out.println("How much would you like to withdraw?");
            float toWithdraw = sc.nextFloat();
            if (toWithdraw >= 0) {
                try {
                    BankCommander.currentClient.getActiveAccount().withdraw(toWithdraw);
                    amountToWithdraw = false;
                } catch (NotEnoughFundsException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("Please provide a positive number or 0 to exit.\n");
            }
        } while (amountToWithdraw);
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Withdraw Command");
    }
}
