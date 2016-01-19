package com.luxoft.bankapp.service;

import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.AbstractAccount;
import com.luxoft.bankapp.model.Account;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class AccountDAOImpl extends BaseDAOImpl implements AccountDAO {

    @Override
    public void save(AbstractAccount accountToSave) throws DAOException {
        String sqlUpdateQuery = "UPDATE ACCOUNT SET BALANCE=?, OVERDRAFT=? WHERE ACC_NO=?";
        PreparedStatement preparedStatement;
        try {
            openConnection();
            preparedStatement = conn.prepareStatement(sqlUpdateQuery);
            preparedStatement.setFloat(1, accountToSave.getBalance());
            preparedStatement.setFloat(2, accountToSave.getOverdraft());
            preparedStatement.setInt(3, accountToSave.getId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Account " + accountToSave.getId() + " " +
                        " updated, " + affectedRows + " rows affected.");
            } else {
                System.err.println("Could not update the account " + accountToSave.getId() + ".");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(AbstractAccount account) throws DAOException {

    }

    @Override
    public void removeByClientId(int idClient) throws DAOException {

    }

    @Override
    public List<Account> getClientAccounts(int idClient) throws DAOException {
        return null;
    }
}
