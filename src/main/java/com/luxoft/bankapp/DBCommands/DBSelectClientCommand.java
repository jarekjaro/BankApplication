package com.luxoft.bankapp.DBCommands;

import com.luxoft.bankapp.commands.Command;
import com.luxoft.bankapp.exceptions.ClientDoesNotExistException;
import com.luxoft.bankapp.service.DBBankCommander;

import java.util.Scanner;

public class DBSelectClientCommand implements Command {
    Scanner scanner = new Scanner(System.in);

    @Override
    public void execute() {
        if (isBankSelected()) {
            printSelectClientMenu();
            String nameOfTheClientToSearch = readClientNameFromUser();
            try {
                DBBankCommander.activeClient = DBBankCommander.activeBank.getClientByName(nameOfTheClientToSearch);
            } catch (ClientDoesNotExistException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("No Bank was selected, try to select bank firstly!");
        }
    }

    private boolean isBankSelected() {
        return DBBankCommander.activeBank != null;
    }

    private void printSelectClientMenu() {
        System.out.println("Write the name of the client to select:");
    }

    private String readClientNameFromUser() {
        return scanner.nextLine();
    }

    @Override
    public void printCommandInfo() {
        System.out.println("DBSelectClientCommand");
    }
}
