package com.luxoft.bankapp.DBCommands;

import com.luxoft.bankapp.DAO.ClientDAOImpl;
import com.luxoft.bankapp.commands.Command;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.service.DBBankCommander;

import java.util.Scanner;

public class DBRemoveClientCommand implements Command {
    Scanner scanner = new Scanner(System.in);

    @Override
    public void execute() {
        if (isActiveClientSet()) {
            ClientDAOImpl clientDAO = new ClientDAOImpl();
            printRemoveClientMenu();
            String confirmation = getUserConfirmationOfRemoval();
            removeClientIfConfirmed(clientDAO, confirmation);
        } else {
            System.err.println("No Client selected, try to select client firstly!");
        }
    }

    private boolean isActiveClientSet() {
        return DBBankCommander.activeClient != null;
    }

    private void printRemoveClientMenu() {
        System.out.println("Do you really want to delete " + DBBankCommander.activeClient.getName() +
                " " + DBBankCommander.activeClient.getSurname() + "? (Y/N)");
    }

    public String getUserConfirmationOfRemoval() {
        return scanner.nextLine();
    }

    private void removeClientIfConfirmed(ClientDAOImpl clientDAO, String confirmation) {
        if (removalConfirmed(confirmation)) {
            try {
                clientDAO.remove(DBBankCommander.activeClient);
                DBBankCommander.getLogger().info("Client removal occured.");
            } catch (DAOException e) {
                DBBankCommander.getLogger().warning("Problems with connection to DB!");
                e.printStackTrace();
            }
        } else {
            removalNotConfirmed();
        }
    }

    private boolean removalConfirmed(String confirmation) {
        return confirmation.matches("[yY]");
    }

    private void removalNotConfirmed() {
        System.out.println("Not removing.");
    }

    @Override
    public void printCommandInfo() {
        System.out.println("DBRemoveClientCommand");
    }
}
