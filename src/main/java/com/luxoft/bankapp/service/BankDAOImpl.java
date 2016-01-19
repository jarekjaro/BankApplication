package com.luxoft.bankapp.service;

import com.luxoft.bankapp.exceptions.BankNotFoundException;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Bank;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BankDAOImpl extends BaseDAOImpl implements BankDAO {

    @Override
    public Bank getBankByName(String name) throws DAOException {
        Bank bank = new Bank(name);
        String sqlQuery = "SELECT ID, NAME FROM BANK WHERE NAME=?";
        PreparedStatement preparedStatement;
        try {
            openConnection();
            preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("ID");
                bank.setId(id);
            } else {
                throw new BankNotFoundException(name);
            }
        } catch (SQLException | BankNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return bank;
    }

    @Override
    public void save(Bank bank) throws DAOException {
        openConnection();
        String sqlUpdateQuery = "UPDATE BANK SET NAME=?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sqlUpdateQuery);
            preparedStatement.setString(1, bank.getBankName());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Bank " + bank.getBankName() + " was updated, "
                        + affectedRows + " rows affected");
            } else {
                System.err.println("No rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    @Override
    public void remove(Bank bank) throws DAOException {
        openConnection();
        String sqlRemoveBankQuery = "DELETE FROM BANK WHERE BANK.NAME=?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sqlRemoveBankQuery);
            preparedStatement.setString(1, bank.getBankName());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Bank " + bank.getBankName() + " was deleted.");
            } else {
                System.err.println("Nothing to delete.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }
}
