package com.luxoft.bankapp.DAO;

import com.luxoft.bankapp.exceptions.DAOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseDAOImpl {
    Connection conn;

    public Connection openConnection() throws DAOException {
        try {
            Class.forName("org.h2.Driver");
            String relativePath = "jdbc:h2:~/IdeaProjects/BankApplication/BankApplicationDB"; //paths may differ so watchout!
            conn = DriverManager.getConnection(relativePath, "sa", "");
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
