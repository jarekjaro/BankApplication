package com.luxoft.bankapp.DAO;

import com.luxoft.bankapp.exceptions.BankNotFoundException;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Bank;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BankDAOImpl extends BaseDAOImpl implements BankDAO {
    private static final String REMOVE_BANK = "DELETE FROM BANK WHERE BANK.NAME=?";
    private static final String GET_BANK_BY_NAME = "SELECT ID, NAME FROM BANK WHERE NAME=?";
    private static final String UPDATE_BANK_NAME = "UPDATE BANK SET NAME=?";
    private PreparedStatement preparedStatement;

    @Override
    public Bank getBankByName(String name) throws DAOException {
        Bank bank = new Bank(name);
        openConnection();
        try {
            preparedStatement = conn.prepareStatement(GET_BANK_BY_NAME);
            prepareBankQuery(name);
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

    private void prepareBankQuery(String name) throws SQLException {
        preparedStatement.setString(1, name);
    }

    @Override
    public void save(Bank bank) throws DAOException {
        openConnection();
        try {
            preparedStatement = conn.prepareStatement(UPDATE_BANK_NAME);
            prepareBankQuery(bank.getBankName());
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
        try {
            preparedStatement = conn.prepareStatement(REMOVE_BANK);
            prepareBankQuery(bank.getBankName());
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
