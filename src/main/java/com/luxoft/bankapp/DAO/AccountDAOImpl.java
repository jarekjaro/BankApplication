package com.luxoft.bankapp.DAO;

import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.CheckingAccount;
import com.luxoft.bankapp.model.SavingAccount;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AccountDAOImpl extends BaseDAOImpl implements AccountDAO {
    private static final String UPDATE_ACCOUNT =
            "UPDATE ACCOUNT SET BALANCE=?, OVERDRAFT=? WHERE ACC_NO=?";
    private static final String INSERT_NEW_CHECKING_ACCOUNT =
            "INSERT INTO ACCOUNT (BALANCE, OVERDRAFT, TYPE) VALUES(?,?,?)";
    private static final String INSERT_NEW_SAVING_ACCOUNT =
            "INSERT INTO ACCOUNT (BALANCE, TYPE) VALUES(?,?)";
    private static final String GET_ACCS_OF_CLIENT =
            "SELECT ACC_NO, BALANCE, OVERDRAFT, TYPE FROM ACCOUNT " +
                    "JOIN ACCOUNT_OWNERS ON ACCOUNT.ACC_NO = ACCOUNT_OWNERS.ACCOUNT_ID " +
                    "WHERE OWNER_ID = ?;";
    private static final String DELETE_REFERENCES_TO_ALL_ACCOUNTS_OF_CLIENT =
            "DELETE FROM ACCOUNT_OWNERS WHERE ID " +
                    "IN(SELECT ID FROM ACCOUNT_OWNERS WHERE OWNER_ID=?);";
    private static final String GET_ACCOUNT_REFERENCES_TO_ANY_CLIENTS =
            "SELECT ID FROM ACCOUNT_OWNERS WHERE ACCOUNT_ID=?;";
    private static final String DELETE_ACCOUNTS_WITH_PARTICULAR_ACC_NO =
            "DELETE FROM ACCOUNT WHERE ACC_NO=?;";
    private PreparedStatement preparedStatement;

    @Override
    public void save(Account accountToSave) throws DAOException {
        openConnection();
        try {
            prepareSaveStatement(accountToSave);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Account " + accountToSave.getAccNo() + " " +
                        " updated, " + affectedRows + " rows affected.");
            } else {
                System.err.println("Could not update the account " +
                        accountToSave.getAccNo() + ".");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    private void prepareSaveStatement(Account accountToSave) throws SQLException {
        preparedStatement = conn.prepareStatement(UPDATE_ACCOUNT);
        preparedStatement.setFloat(1, accountToSave.getBalance());
        preparedStatement.setFloat(2, accountToSave.getOverdraft());
        preparedStatement.setInt(3, accountToSave.getAccNo());
    }

    @Override
    public void add(Account accountToAdd) throws DAOException {
        openConnection();
        try {
            if (isCheckingAccount(accountToAdd)) {
                prepareCheckingAccountQuery(accountToAdd);
            } else {
                prepareSavingAccountQuery(accountToAdd);
            }
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Account " + accountToAdd.getAccNo() + " " +
                        " added, " + affectedRows + " rows affected.");
            } else {
                System.err.println("Could not add the account " +
                        accountToAdd.getAccNo() + ".");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    private boolean isCheckingAccount(Account accountToAdd) {
        return accountToAdd.getType() == 1;
    }

    private void prepareCheckingAccountQuery(Account accountToAdd) throws SQLException {
        preparedStatement = conn.prepareStatement(INSERT_NEW_CHECKING_ACCOUNT);
        preparedStatement.setFloat(1, accountToAdd.getBalance());
        preparedStatement.setFloat(2, accountToAdd.getOverdraft());
        preparedStatement.setInt(3, accountToAdd.getType());
    }

    private void prepareSavingAccountQuery(Account accountToAdd) throws SQLException {
        preparedStatement = conn.prepareStatement(INSERT_NEW_SAVING_ACCOUNT);
        preparedStatement.setFloat(1, accountToAdd.getBalance());
        preparedStatement.setInt(2, accountToAdd.getType());
    }

    @Override
    public void removeByClientId(int clientId) throws DAOException {
        ResultSet resultSet;
        List<Integer> accountNumbers = new ArrayList<>();
        openConnection();
        try {
            prepareAccNosQuery(clientId);
            resultSet = preparedStatement.executeQuery();
            addAccNosToList(resultSet, accountNumbers);
            if (clientHasAccounts(accountNumbers)) {
                prepareDeleteReferencesQuery(clientId);
                int affectedRows = preparedStatement.executeUpdate();
                System.out.println(affectedRows + " references were deleted.");
            }
            deleteAccountsIfTheyHaveNoClientReferences(accountNumbers);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    private void prepareAccNosQuery(int clientId) throws SQLException {
        preparedStatement = conn.prepareStatement(GET_ACCS_OF_CLIENT);
        preparedStatement.setInt(1, clientId);
    }

    private void addAccNosToList(ResultSet resultSet, List<Integer> accountNumbers) throws SQLException {
        while (resultSet.next()) {
            accountNumbers.add(resultSet.getInt("ACC_NO"));
        }
    }

    private boolean clientHasAccounts(List<Integer> accountNumbers) {
        return accountNumbers.size() > 0;
    }

    private void prepareDeleteReferencesQuery(int clientId) throws SQLException {
        preparedStatement = conn
                .prepareStatement(
                        DELETE_REFERENCES_TO_ALL_ACCOUNTS_OF_CLIENT);
        preparedStatement.setInt(1, clientId);
    }

    private void deleteAccountsIfTheyHaveNoClientReferences(List<Integer> accountNumbers) throws SQLException {
        ResultSet resultSet;
        for (Iterator<Integer> iterator = accountNumbers.iterator(); iterator.hasNext(); ) {
            Integer nextAccNo = iterator.next();
            prepareAccountReferencesQuery(nextAccNo);
            resultSet = preparedStatement.executeQuery();
            List<Integer> referencesIDs = new ArrayList<>();
            addReferencesToList(resultSet, referencesIDs);
            if (hasNoClientReferences(referencesIDs)) {
                deleteAccount(nextAccNo);
            }
        }
    }

    private void prepareAccountReferencesQuery(Integer nextAccNo) throws SQLException {
        preparedStatement = conn
                .prepareStatement(GET_ACCOUNT_REFERENCES_TO_ANY_CLIENTS);
        preparedStatement.setInt(1, nextAccNo);
    }

    private void addReferencesToList(ResultSet resultSet, List<Integer> referencesIDs) throws SQLException {
        while (resultSet.next()) {
            referencesIDs.add(resultSet.getInt("ID"));
        }
    }

    private boolean hasNoClientReferences(List<Integer> referencesIDs) {
        return referencesIDs.size() <= 0;
    }

    private void deleteAccount(Integer accountNumber) throws SQLException {
        preparedStatement = conn
                .prepareStatement(DELETE_ACCOUNTS_WITH_PARTICULAR_ACC_NO);
        preparedStatement.setInt(1, accountNumber);
        int affectedRows = preparedStatement.executeUpdate();
        System.out.println("Account " + accountNumber +
                " had no more owners hence it was deleted, " + affectedRows +
                " rows were affected.");
    }

    @Override
    public List<Account> getClientAccounts(int idClient) throws DAOException {
        List<Account> accountsList = new ArrayList<>();
        openConnection();
        try {
            prepareAccNosQuery(idClient);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int acc_no = resultSet.getInt("ACC_NO");
                int type = resultSet.getInt("TYPE");
                float balance = resultSet.getFloat("BALANCE");
                Account accountToAdd;
                if (type == 1) {
                    float overdraft = resultSet.getFloat("OVERDRAFT");
                    accountToAdd =
                            new CheckingAccount(acc_no, balance, overdraft, type);
                } else {
                    accountToAdd =
                            new SavingAccount(acc_no, balance, type);
                }
                accountsList.add(accountToAdd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return accountsList;
    }
}
