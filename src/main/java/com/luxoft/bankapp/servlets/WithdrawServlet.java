package com.luxoft.bankapp.servlets;

import com.luxoft.bankapp.DAO.AccountDAOImpl;
import com.luxoft.bankapp.DAO.BankDAOImpl;
import com.luxoft.bankapp.DAO.ClientDAOImpl;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.DBBankCommander;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class WithdrawServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BankDAOImpl bankDAO = new BankDAOImpl();
        ClientDAOImpl clientDAO = new ClientDAOImpl();
        AccountDAOImpl accountDAO = new AccountDAOImpl();
        Bank bank;
        ArrayList<Account> accountArrayList;
        Account currentAccount;
        String clientName = (String) request.getSession().getAttribute("clientName");
        try {
            bank = bankDAO.getBankByName("Deutche");
            ArrayList<Client> clientsFromDB = (ArrayList<Client>) clientDAO.getAllClients(bank);
            clientsFromDB.forEach(bank::addClientToMap);
            Client currentClient = clientDAO.findClientByName(bank, clientName);
            accountArrayList = (ArrayList<Account>) accountDAO.getClientAccounts(currentClient.getId());
            currentAccount = accountArrayList.get(0);
            float withdrawAmount = getAmountToWithdrawFromUser(request);
            withdrawFromActiveAccount(currentAccount, withdrawAmount);
            accountDAO.save(currentAccount);
            PrintWriter printWriter = response.getWriter();
            printWriter.println("Withdrawn: " + request.getParameter("amount"));
        } catch (DAOException e) {
            e.printStackTrace();
        }
        response.sendRedirect("menu.jsp");
    }


    private float getAmountToWithdrawFromUser(HttpServletRequest request) {
        return Float.valueOf(request.getParameter("amount"));
    }

    private void withdrawFromActiveAccount(Account account, float withdrawAmount) {
        account.setBalance(calculateBalanceAfterWithdraw(account, withdrawAmount));
    }

    private float calculateBalanceAfterWithdraw(Account account, float withdrawAmount) {
        return account.getBalance() - withdrawAmount;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
