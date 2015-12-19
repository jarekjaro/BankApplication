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
            new WithdrawCommand(),
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
        boolean flagOfClient = false;
        while (true) {
            if (flagOfClient) System.out.println(currentClient.getClientSalutation() + currentClient.getName());
            for (int i = 0; i < commands.length; i++) { // show menu
                System.out.print(i + ") ");
                commands[i].printCommandInfo();
            }
            System.out.println();
            int commandNumber;
            boolean commandFlag = true;
            do {
                String commandNumberStr = sc.nextLine(); // initialize command with commandString
                try {
                    commandNumber = Integer.parseInt(commandNumberStr);
                    if ((commandNumber >= 0) && commandNumber <= (commands.length - 1)) {
                        commandFlag = false;
                        if (commandNumber == 0) flagOfClient = true;
                        commands[commandNumber].printCommandInfo();
                        commands[commandNumber].execute();
                    } else{
                        System.out.println("Please provide a positive integer value between (0-" + (commands.length - 1) + ")");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please provide a positive integer value between (0-" + (commands.length - 1) + ")");
                }
            } while (commandFlag);
        }
    }
}
