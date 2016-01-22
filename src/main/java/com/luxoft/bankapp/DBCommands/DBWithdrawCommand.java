package com.luxoft.bankapp.DBCommands;

import com.luxoft.bankapp.DAO.AccountDAOImpl;
import com.luxoft.bankapp.commands.Command;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.service.DBBankCommander;

import java.util.Scanner;

public class DBWithdrawCommand implements Command {
    Scanner scanner = new Scanner(System.in);

    @Override
    public void execute() {
        if (isActiveClientSet()) {
            AccountDAOImpl accountDAO = new AccountDAOImpl();
            float withdrawAmount = getAmountToWithdrawFromUser();
            withdrawFromActiveAccount(withdrawAmount);
            try {
                accountDAO.save(DBBankCommander.activeClient.getActiveAccount());
            } catch (DAOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Select client first!");
        }
    }

    @Override
    public void printCommandInfo() {
        System.out.println("DBWithdrawCommand");
    }

    private boolean isActiveClientSet() {
        return DBBankCommander.activeClient != null;
    }

    private float getAmountToWithdrawFromUser() {
        System.out.println("How much would you like to withdraw?");
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

    private float calculateBalanceAfterWithdraw(float withdrawAmount) {
        return DBBankCommander.activeClient.getActiveAccount().getBalance() - withdrawAmount;
    }
}
