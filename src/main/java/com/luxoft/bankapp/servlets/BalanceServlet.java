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
            clientsFromDB.forEach(bank::addClientToMap);
            ArrayList<Account> accountArrayList;
            Client currentClient = clientDAO.findClientByName(bank, clientName);
            accountArrayList = (ArrayList<Account>) accountDAO.getClientAccounts(currentClient.getId());
            Account currentAccount = accountArrayList.get(0);
            req.getSession().setAttribute("balance", currentAccount.getBalance());
            resp.sendRedirect("balance.jsp");
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
