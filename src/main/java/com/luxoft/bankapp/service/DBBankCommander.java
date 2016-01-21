package com.luxoft.bankapp.service;

import com.luxoft.bankapp.DBCommands.*;
import com.luxoft.bankapp.commands.Command;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

import java.util.*;

public class DBBankCommander {
    public static Bank activeBank;
    public static Client activeClient;
    public static Client currentReceivingClient;
    public static BankServiceImpl bs = new BankServiceImpl();
    public static Map<String, Command> commands = new TreeMap<>();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        registerCommand("DBAddClientCommand", new DBAddClientCommand());
        registerCommand("DBDepositCommand", new DBDepositCommand());
        registerCommand("DBRemoveClientCommand", new DBRemoveClientCommand());
        registerCommand("DBSelectBankCommand", new DBSelectBankCommand());
        registerCommand("DBSelectClientCommand", new DBSelectClientCommand());
        registerCommand("DBTransferCommand", new DBTransferCommand());
        registerCommand("DBWithdrawCommand", new DBWithdrawCommand());
        registerCommand("Exit", new Command() {
            public void execute() {
                System.exit(0);
            }

            public void printCommandInfo() {
                System.out.println("Exit");
            }
        });

        while (true) {
            printCommandsMenu();
            int optionPicked = readUserOptionPick();
            executePickedOption(optionPicked);
        }
    }

    private static void executePickedOption(int optionPicked) {
        switch (optionPicked) {
            case 1:
                commands.get("DBAddClientCommand").execute();
                break;
            case 2:
                commands.get("DBDepositCommand").execute();
                break;
            case 3:
                commands.get("DBRemoveClientCommand").execute();
                break;
            case 4:
                commands.get("DBSelectBankCommand").execute();
                break;
            case 5:
                commands.get("DBSelectClientCommand").execute();
                break;
            case 6:
                commands.get("DBTransferCommand").execute();
                break;
            case 7:
                commands.get("DBWithdrawCommand").execute();
                break;
            case 8:
                commands.get("Exit").execute();
                break;
        }
    }

    private static int readUserOptionPick() {
        String optionPicked = sc.nextLine();
        while (!optionPicked.matches("[1-8]")) {
            System.err.println("Pick only options 1-8");
            optionPicked = sc.nextLine();
        }
        return Integer.valueOf(optionPicked);
    }

    private static void printCommandsMenu() {
        Set<String> commandsSet = commands.keySet();
        int i = 1;
        System.out.println("Pick your option by number:");
        for (Iterator<String> iterator = commandsSet.iterator(); iterator.hasNext(); ) {
            String next = iterator.next();
            System.out.println("(" + i + ") " + next);
            i++;
        }
    }

    public static void removeCommand(String name) {
        commands.remove(name);
    }

    public static void registerCommand(String name, Command command) {
        commands.put(name, command);
    }
}
