package com.luxoft.bankapp.commands;

import com.luxoft.bankapp.commands.Command;
import com.luxoft.bankapp.exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.exceptions.OverdraftLimitExceededException;
import com.luxoft.bankapp.service.BankCommander;

import java.util.Scanner;

/**
 * Created by jaro on 12/16/15.
 */
public class WithdrawCommand implements Command {
    @Override
    public void execute() {
        Scanner sc = new Scanner(System.in);
        System.out.println("From which: (C)hecking account or (S)aving account would you like to withdraw?");
        if (sc.nextLine().equals("C")) {
            BankCommander.currentClient.setActiveAccount(BankCommander.currentClient.getAccounts().get(0));
        } else {
            BankCommander.currentClient.setActiveAccount(BankCommander.currentClient.getAccounts().get(1));
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
