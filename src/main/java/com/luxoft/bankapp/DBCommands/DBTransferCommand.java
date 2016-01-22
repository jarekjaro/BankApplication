package com.luxoft.bankapp.DBCommands;

import com.luxoft.bankapp.DAO.AccountDAOImpl;
import com.luxoft.bankapp.commands.Command;
import com.luxoft.bankapp.exceptions.ClientDoesNotExistException;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.service.DBBankCommander;

import java.util.Scanner;

public class DBTransferCommand implements Command {
    Scanner scanner = new Scanner(System.in);

    @Override
    public void execute() {
        if (isActiveClientSet()) {
            AccountDAOImpl accountDAO = new AccountDAOImpl();
            float transferAmount = getAmountToTransferFromUser();
            withdrawFromActiveAccount(transferAmount);
            try {
                String nameOfTheSendingClient = DBBankCommander.activeClient.getName();
                accountDAO.save(DBBankCommander.activeClient.getActiveAccount());
                printSelectClientMenu();
                String nameOfTheReceivingClient = readClientNameFromUser();
                setActiveClient(nameOfTheReceivingClient);
                depositToActiveAccount(transferAmount);
                accountDAO.save(DBBankCommander.activeClient.getActiveAccount());
                setActiveClient(nameOfTheSendingClient);
            } catch (DAOException | ClientDoesNotExistException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Select client first!");
        }
    }

    private boolean isActiveClientSet() {
        return DBBankCommander.activeClient != null;
    }

    private float getAmountToTransferFromUser() {
        System.out.println("How much would you like to transfer?");
        String amountToWithdraw = scanner.nextLine();
        while (!amountToWithdraw.matches("[0-9]*")) {
            System.out.println("Only Positive Integers!");
            scanner.nextLine();
        }
        return Float.valueOf(amountToWithdraw);
    }

    private void withdrawFromActiveAccount(float withdrawAmount) {
        DBBankCommander.activeClient.getActiveAccount().setBalance(
                calculateBalanceAfterWithdraw(withdrawAmount));
    }

    private void printSelectClientMenu() {
        System.out.println("Write the name of the client to transfer to:");
    }

    private String readClientNameFromUser() {
        return scanner.nextLine();
    }

    private void setActiveClient(String nameOfTheReceivingClient) throws ClientDoesNotExistException {
        DBBankCommander.activeClient = DBBankCommander.activeBank.getClientByName(nameOfTheReceivingClient);
    }

    private void depositToActiveAccount(float depositAmount) {
        DBBankCommander.activeClient.getActiveAccount().setBalance(
                calculateBalanceAfterDeposit(depositAmount));
    }

    private float calculateBalanceAfterWithdraw(float withdrawAmount) {
        return DBBankCommander.activeClient.getActiveAccount().getBalance() - withdrawAmount;
    }

    private float calculateBalanceAfterDeposit(float depositAmount) {
        return DBBankCommander.activeClient.getActiveAccount().getBalance() + depositAmount;
    }

    @Override
    public void printCommandInfo() {
        System.out.println("DBTransferCommand");
    }
}
