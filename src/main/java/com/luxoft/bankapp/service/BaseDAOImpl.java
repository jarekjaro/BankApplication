package com.luxoft.bankapp.service;

import com.luxoft.bankapp.exceptions.DAOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseDAOImpl {
    Connection conn;

    public Connection openConnection() throws DAOException {
        try {
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection("jdbc:h2:/home/jaro/IdeaProjects" +
                    "/BankApplication/BankApplicationDB", "sa", "");
            return conn;
        } catch (ClassNotFoundException | SQLException e) {
            throw new DAOException();
        }
    }

    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
