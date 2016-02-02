package com.luxoft.bankapp.servlets;

import com.luxoft.bankapp.DAO.AccountDAOImpl;
import com.luxoft.bankapp.DAO.BankDAOImpl;
import com.luxoft.bankapp.DAO.ClientDAOImpl;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class BalanceServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BankDAOImpl bankDAO = new BankDAOImpl();
        ClientDAOImpl clientDAO = new ClientDAOImpl();
        AccountDAOImpl accountDAO = new AccountDAOImpl();
        Bank bank = null;
        String clientName = (String) req.getSession().getAttribute("clientName");
        try {
            bank = bankDAO.getBankByName("Deutche");
            ArrayList<Client> clientsFromDB = (ArrayList<Client>) clientDAO.getAllClients(bank);
            HashSet<Account> accountHashSet = new HashSet<>();
            for (int i = 0; i < clientsFromDB.size(); i++) {
                List accountsList = accountDAO.getClientAccounts(i);
                accountsList.forEach(account -> accountHashSet.add((Account) account));
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
