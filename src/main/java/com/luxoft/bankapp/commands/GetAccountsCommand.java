package com.luxoft.bankapp.commands;

import com.luxoft.bankapp.service.BankCommander;

public class GetAccountsCommand implements Command {
    @Override
    public void execute() {
        if (BankCommander.activeClient != null) {
            BankCommander.activeClient.getAccounts();
            BankCommander.activeClient.printReport();
        } else {
            System.out.println("You need to provide a client first.");
        }
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Get Accounts Command");
    }
}
