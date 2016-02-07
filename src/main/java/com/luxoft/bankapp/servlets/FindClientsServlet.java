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

public class FindClientsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BankDAOImpl bankDAO = new BankDAOImpl();
        ClientDAOImpl clientDAO = new ClientDAOImpl();
        AccountDAOImpl accountDAO = new AccountDAOImpl();
        Bank bank;
        String clientName = request.getParameter("inputName");
        try {
            bank = bankDAO.getBankByName("Deutche");
            ArrayList<Client> clientsFromDB = (ArrayList<Client>) clientDAO.getAllClients(bank);
            request.getSession().setAttribute("clients", clientsFromDB);
            clientsFromDB.forEach(bank::addClientToMap);
            ArrayList<Account> accountArrayList;
            Client currentClient = clientDAO.findClientByName(bank, clientName);
            accountArrayList = (ArrayList<Account>) accountDAO.getClientAccounts(currentClient.getId());
            Account currentAccount = accountArrayList.get(0);
            request.getSession().setAttribute("balance", currentAccount.getBalance());
            response.sendRedirect("findclients.jsp");
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
