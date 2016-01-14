package com.luxoft.bankapp.commands;

import com.luxoft.bankapp.service.BankCommander;

/**
 * Created by jaro on 12/16/15.
 */
public class GetAccountsCommand implements Command {
    @Override
    public void execute() {
        if(BankCommander.currentClient!=null){
            BankCommander.currentClient.getAccounts();
            BankCommander.currentClient.printReport();
        } else {
            System.out.println("You need to provide a client first.");
        }
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Get Accounts Command");
    }
}
