package com.luxoft.bankapp.service;

import com.luxoft.bankapp.DBCommands.*;
import com.luxoft.bankapp.commands.Command;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.sun.org.apache.bcel.internal.util.ByteSequence;

import java.io.*;
import java.util.*;
import java.util.logging.*;
import java.util.logging.Formatter;

public class DBBankCommander {
    public static Bank activeBank;
    public static Client activeClient;
    public static Map<String, Command> commands = new TreeMap<>();
    private static Scanner sc = new Scanner(System.in);
    private static Logger logger = Logger.getLogger(DBBankCommander.class.getCanonicalName());
    private static Formatter simpleFormatter = new SimpleFormatter();
    private static Handler defaultHandler = new StreamHandler(System.out, simpleFormatter);
    private static long millisStart;
    private static long millisEnd;
    public static final String logger_all_path = "C:\\Users\\JARO\\IdeaProjects\\BankApplication\\logger_all.properties";
    public static final String logger_clients_path = "C:\\Users\\JARO\\IdeaProjects\\BankApplication\\logger_clients.properties";
    public static final String logger_exceptions_path = "C:\\Users\\JARO\\IdeaProjects\\BankApplication\\logger_exceptions.properties";
    public static final File allConfigFile = new File(logger_all_path);
    public static final File clientConfigFile = new File(logger_clients_path);
    public static final File exceptionConfigFile = new File(logger_exceptions_path);

    public static Logger getLogger() {
        return logger;
    }

    public static void main(String[] args) throws IOException {
        logger.addHandler(defaultHandler);
//        Scanner fileScanner = new Scanner(allConfigFile);
//        StringBuilder sb = new StringBuilder();
//        while (fileScanner.hasNextLine()) {
//            sb.append(fileScanner.nextLine());
//        }
//        String propertiesFromFile = sb.toString();
//        InputStream fromFile = new ByteArrayInputStream(propertiesFromFile.getBytes("UTF-8"));
//        LogManager.getLogManager().readConfiguration(fromFile);
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
                logger.info("DBAddClientCommand chosen and executing!");
                commands.get("DBAddClientCommand").execute();
                break;
            case 2:
                logger.info("DBDepositCommand chosen and executing!");
                commands.get("DBDepositCommand").execute();
                break;
            case 3:
                logger.info("DBRemoveClientCommand chosen and executing!");
                commands.get("DBRemoveClientCommand").execute();
                break;
            case 4:
                logger.info("DBSelectBankCommand chosen and executing!");
                commands.get("DBSelectBankCommand").execute();
                break;
            case 5:
                logger.info("DBSelectClientCommand chosen and executing!");
                commands.get("DBSelectClientCommand").execute();
                millisStart = System.currentTimeMillis();
                break;
            case 6:
                logger.info("DBTransferCommand chosen and executing!");
                commands.get("DBTransferCommand").execute();
                break;
            case 7:
                logger.info("DBWithdrawCommand chosen and executing!");
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
