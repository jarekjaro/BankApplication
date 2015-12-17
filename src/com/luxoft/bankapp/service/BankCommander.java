package com.luxoft.bankapp.service;

import com.luxoft.bankapp.commands.*;
import com.luxoft.bankapp.model.*;

import java.util.Scanner;

public class BankCommander {
    public static Bank currentBank = new Bank();
    public static Client currentClient;
    public static BankServiceImpl bs = new BankServiceImpl();

    static Command[] commands = {
            new FindClientCommand(),
            new AddClientCommand(),
            new GetAccountsCommand(),
            new DepositCommand(),
            new TransferCommand(),
            new Command() {
                public void execute() {
                    System.exit(0);
                }

                public void printCommandInfo() {
                    System.out.println("Exit");
                }
            }
    };

    public static void main(String args[]) {
        BankApplication.initialize(currentBank);

        Scanner sc = new Scanner(System.in);
        boolean flag =true;
        while (flag) {
            for (int i = 0; i < commands.length; i++) { // show menu
                System.out.print(i + ") ");
                commands[i].printCommandInfo();
            }

            int commandNumber = sc.nextInt(); // initialize command with commandString
            commands[commandNumber].execute();
        }
    }
}
