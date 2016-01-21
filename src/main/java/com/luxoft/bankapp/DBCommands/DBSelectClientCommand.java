package com.luxoft.bankapp.DBCommands;

import com.luxoft.bankapp.DAO.ClientDAOImpl;
import com.luxoft.bankapp.commands.Command;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.DBBankCommander;

import java.util.Scanner;

public class DBSelectClientCommand implements Command {
    Scanner scanner = new Scanner(System.in);

    @Override
    public void execute() {
        if (isBankSelected()) {
            ClientDAOImpl clientDAO = new ClientDAOImpl();
            printSelectClientMenu();
            String nameOfTheClientToSearch = readClientNameFromUser();
            try {
                Client clientFromDB = clientDAO.findClientByName(DBBankCommander.activeBank, nameOfTheClientToSearch);
                DBBankCommander.activeClient = clientFromDB;
            } catch (DAOException e) {
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
