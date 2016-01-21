package com.luxoft.bankapp.DBCommands;

import com.luxoft.bankapp.DAO.AccountDAOImpl;
import com.luxoft.bankapp.commands.Command;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.service.DBBankCommander;

import java.util.Scanner;

public class DBDepositCommand implements Command {
    Scanner scanner = new Scanner(System.in);

    @Override
    public void execute() {
        if (isActiveClientSet()) {
            AccountDAOImpl accountDAO = new AccountDAOImpl();
            float depositAmount = getAmountFromUser();
            depositToActiveAccount(depositAmount);
            try {
                accountDAO.save(DBBankCommander.activeClient.getActiveAccount());
            } catch (DAOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Select client first!");
        }
    }

    private void depositToActiveAccount(float depositAmount) {
        DBBankCommander.activeClient.getActiveAccount().setBalance(
                calculateBalanceAfterDeposit(depositAmount));
    }

    private float calculateBalanceAfterDeposit(float depositAmount) {
        return DBBankCommander.activeClient.getActiveAccount().getBalance() + depositAmount;
    }

    private float getAmountFromUser() {
        System.out.println("How much would you like to deposit?");
        String amountToWithdraw = scanner.nextLine();
        while (!amountToWithdraw.matches("[0-9]*")) {
            System.out.println("Only Positive Integers!");
            scanner.nextLine();
        }
        return Float.valueOf(amountToWithdraw);
    }

    private boolean isActiveClientSet() {
        return DBBankCommander.activeClient != null;
    }

    @Override
    public void printCommandInfo() {
        System.out.println("DBDepositCommand");
    }
}
