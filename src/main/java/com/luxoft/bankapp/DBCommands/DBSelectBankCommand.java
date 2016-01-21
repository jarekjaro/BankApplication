package com.luxoft.bankapp.DBCommands;

import com.luxoft.bankapp.DAO.AccountDAOImpl;
import com.luxoft.bankapp.DAO.BankDAOImpl;
import com.luxoft.bankapp.DAO.ClientDAOImpl;
import com.luxoft.bankapp.commands.Command;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.DBBankCommander;

import java.util.List;
import java.util.Scanner;

public class DBSelectBankCommand implements Command {
    Scanner scanner = new Scanner(System.in);
    Bank bankFromDB;

    @Override
    public void execute() {
        String nameOfTheBankToSelectFromDB;
        BankDAOImpl bankDAO = new BankDAOImpl();
        printSelectBankMenu();
        nameOfTheBankToSelectFromDB = readTheBankNameFromClient();
        try {
            bankFromDB = bankDAO.getBankByName(nameOfTheBankToSelectFromDB);
            DBBankCommander.activeBank = bankFromDB;
            loadAllBankClientsData();
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    private void loadAllBankClientsData() {
        ClientDAOImpl clientDAO = new ClientDAOImpl();
        List<Client> listOfClientsReturnedFromDB;
        try {
            listOfClientsReturnedFromDB =
                    clientDAO.getAllClients(DBBankCommander.activeBank);
            listOfClientsReturnedFromDB.forEach(client -> loadAllAccounts(client));
            listOfClientsReturnedFromDB.forEach(client -> DBBankCommander.activeBank.addClientToMap(client));
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    private void loadAllAccounts(Client client) {
        List<Account> listOfAccountsReturnedFromDB;
        AccountDAOImpl accountDAO = new AccountDAOImpl();
        try {
            listOfAccountsReturnedFromDB = accountDAO.getClientAccounts(client.getId());
            listOfAccountsReturnedFromDB.forEach(account -> client.addAccount(account));
            client.setActiveAccount(); // Exemplary Active Account Setting
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    private String readTheBankNameFromClient() {
        return scanner.nextLine();
    }

    private void printSelectBankMenu() {
        System.out.println("Currently our database has 3 banks:\n" +
                "Deutche\n" +
                "ING\n" +
                "Nordea");
        System.out.println("Please write the name of the bank you want to work with and press ENTER: ");
    }

    @Override
    public void printCommandInfo() {
        System.out.println("DBSelectBankCommand");
    }
}
