package com.luxoft.bankapp.service;

import com.luxoft.bankapp.DBCommands.*;
import com.luxoft.bankapp.commands.Command;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

import java.io.*;
import java.util.*;
import java.util.logging.*;

public class DBBankCommander {
    public static Bank activeBank;
    public static Client activeClient;
    public static Map<String, Command> commands = new TreeMap<>();
    private static Scanner sc = new Scanner(System.in);
    private static Logger logger = Logger.getLogger(DBBankCommander.class.getCanonicalName());
    private static long millisStart;
    private static long millisEnd;
    public static final String logger_all_path = "/home/jaro/IdeaProjects/BankApplication/src/main/resources/logger_all.properties";
    public static final String logger_clients_path = "/home/jaro/IdeaProjects/BankApplication/src/main/resources/logger_all.properties";
    public static final String logger_exceptions_path = "/home/jaro/IdeaProjects/BankApplication/src/main/resources/logger_all.properties";
    public static final File allConfigFile = new File(logger_all_path);
    public static final File clientConfigFile = new File(logger_clients_path);
    public static final File exceptionConfigFile = new File(logger_exceptions_path);

    public static Logger getLogger() {
        return logger;
    }

    public static void main(String[] args) throws IOException {
        InputStream fromFile = new FileInputStream(allConfigFile);
        LogManager.getLogManager().readConfiguration(fromFile);
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
        millisStart = System.currentTimeMillis();
        while (true) {
            printCommandsMenu();
            int optionPicked = readUserOptionPick();
            executePickedOption(optionPicked);
        }
    }

    private static void executePickedOption(int optionPicked) {
        switch (optionPicked) {
            case 1:
                logger.fine("DBAddClientCommand chosen and executing!");
                commands.get("DBAddClientCommand").execute();
                break;
            case 2:
                logger.fine("DBDepositCommand chosen and executing!");
                commands.get("DBDepositCommand").execute();
                break;
            case 3:
                logger.fine("DBRemoveClientCommand chosen and executing!");
                commands.get("DBRemoveClientCommand").execute();
                break;
            case 4:
                logger.fine("DBSelectBankCommand chosen and executing!");
                commands.get("DBSelectBankCommand").execute();
                break;
            case 5:
                logger.fine("DBSelectClientCommand chosen and executing!");
                commands.get("DBSelectClientCommand").execute();
                break;
            case 6:
                logger.fine("DBTransferCommand chosen and executing!");
                commands.get("DBTransferCommand").execute();
                break;
            case 7:
                logger.fine("DBWithdrawCommand chosen and executing!");
                commands.get("DBWithdrawCommand").execute();
                break;
            case 8:
                millisEnd = System.currentTimeMillis();
                logger.info("Exit chosen and executing!" + " Time spent in bank: " + ((millisEnd - millisStart) / 1000) + " seconds.");
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
