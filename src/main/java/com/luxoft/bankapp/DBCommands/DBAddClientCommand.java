package com.luxoft.bankapp.DBCommands;

import com.luxoft.bankapp.DAO.ClientDAOImpl;
import com.luxoft.bankapp.commands.Command;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.DBBankCommander;

import java.util.Scanner;

public class DBAddClientCommand implements Command {
    Scanner scanner = new Scanner(System.in);

    @Override
    public void execute() {
        if (isBankSelected()) {
            ClientDAOImpl clientDAO = new ClientDAOImpl();
            String name = getName();
            String surname = getSurname();
            Client clientToAdd = new Client(name, surname);
            try {
                clientDAO.addClient(clientToAdd);
            } catch (DAOException e) {
                e.getMessage();
            }
        } else {
            System.err.println("Select Bank first!");
        }
    }

    private String getSurname() {
        System.out.println("Give client surname:");
        return getUserInput();
    }

    private String getName() {
        System.out.println("Give client name:");
        return getUserInput();
    }

    private boolean isBankSelected() {
        return DBBankCommander.activeBank != null;
    }

    private String getUserInput() {
        return scanner.nextLine();
    }

    @Override
    public void printCommandInfo() {
        System.out.println("DBAddClientCommand");
    }
}
