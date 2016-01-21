package com.luxoft.bankapp.service;

import com.luxoft.bankapp.DBCommands.DBRemoveClientCommand;
import com.luxoft.bankapp.DBCommands.DBSelectBankCommand;
import com.luxoft.bankapp.DBCommands.DBSelectClientCommand;
import com.luxoft.bankapp.commands.*;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class BankCommander {
    public static Bank activeBank;
    public static Client activeClient;
    public static Client currentReceivingClient;
    public static BankServiceImpl bs = new BankServiceImpl();
    public static Map<String, Command> commands = new TreeMap<>();

    public static void removeCommand(String name) {
        commands.remove(name);
    }

    public static void main(String args[]) {

        registerCommand("FindClientCommand", new FindClientCommand());
        registerCommand("AddClientCommand", new AddClientCommand());
        registerCommand("GetAccountsCommand", new GetAccountsCommand());
        registerCommand("Deposit Command", new DepositCommand());
        registerCommand("Withdraw Command", new WithdrawCommand());
        registerCommand("TransferCommand", new TransferCommand());
        registerCommand("Exit", new Command() {
            public void execute() {
                System.exit(0);
            }

            public void printCommandInfo() {
                System.out.println("Exit");
            }
        });

        Scanner sc = new Scanner(System.in);
        boolean flagOfClient = false;
        while (true) {
            if (flagOfClient) System.out.println(activeClient.getClientSalutation() + activeClient.getName());
            int i = 1;
            for (Iterator iterator = commands.entrySet().iterator(); iterator.hasNext(); ) {
                Map.Entry<String, Command> next = (Map.Entry<String, Command>) iterator.next();
                System.out.print(i + ") ");
                System.out.println(next.getKey());
                i++;
            }
            i = 1;
            System.out.println();
            int commandNumber;
            boolean commandFlag = true;
            do {
                String commandNumberStr = sc.nextLine(); // initialize command with commandString
                try {
                    commandNumber = Integer.parseInt(commandNumberStr);
                    if ((commandNumber >= 1) && commandNumber <= (commands.size())) {
                        commandFlag = false;
                        if (commandNumber == 4) flagOfClient = true;
                        switch (commandNumber) {
                            case 1:
                                commands.get("AddClientCommand").execute();
                                break;
                            case 2: {
                                if (flagOfClient)
                                    commands.get("Deposit Command").execute();
                                else System.err.println("Find client first!");
                                break;
                            }
                            case 3:
                                commands.get("Exit").execute();
                                break;
                            case 4:
                                commands.get("FindClientCommand").execute();
                                break;
                            case 5:
                                if (flagOfClient)
                                    commands.get("GetAccountsCommand").execute();
                                else System.err.println("Find client first!");
                                break;
                            case 6:
                                if (flagOfClient)
                                    commands.get("TransferCommand").execute();
                                else System.err.println("Find client first!");
                                break;
                            case 7:
                                if (flagOfClient)
                                    commands.get("Withdraw Command").execute();
                                else System.err.println("Find client first!");
                                break;
                        }
                    } else {
                        System.out.println("Please provide a positive integer value between (1 - " + (commands.size()) + ")");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please provide a positive integer value between (1 - " + (commands.size()) + ")");
                }
            } while (commandFlag);
        }
    }

    public static void registerCommand(String name, Command command) {
        commands.put(name, command);
    }
}
