package com.luxoft.bankapp.DAO;

import com.luxoft.bankapp.exceptions.ClientDoesNotExistException;
import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientDAOImpl extends BaseDAOImpl implements ClientDAO {

    private static final String DELETE_CLIENT_BY_ID = "DELETE FROM CLIENT WHERE ID=?";
    private static final String UPDATE_CLIENT = "UPDATE CLIENT SET NAME=?, SURNAME=? WHERE ID=?";
    private static final String GET_CLIENT_BY_NAME_AND_BANK_ID = "SELECT c.ID,  c.NAME,  c.SURNAME FROM CLIENT c " +
            "INNER JOIN BANKS_CLIENTS bc ON c.ID = bc.CLIENT_ID " +
            "WHERE c.NAME =? AND bc.BANK_ID=?;";
    private static final String GET_BANK_CLIENTS = "SELECT c.ID, c.NAME, c.SURNAME FROM CLIENT c " +
            "INNER JOIN BANKS_CLIENTS bc ON c.ID=bc.CLIENT_ID " +
            "WHERE bc.BANK_ID=?";
    private PreparedStatement preparedStatement;

    @Override
    public Client findClientByName(Bank bankToSearch, String nameOfTheClientToSearch)
            throws DAOException {
        Client foundClient = new Client(nameOfTheClientToSearch);
        openConnection();
        try {
            preparedStatement = conn.prepareStatement(GET_CLIENT_BY_NAME_AND_BANK_ID);
            prepareFindClientStatement(bankToSearch, nameOfTheClientToSearch);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("ID");
                foundClient.setId(id);
                String surname = resultSet.getString("SURNAME");
                foundClient.setSurname(surname);
            } else {
                throw new ClientDoesNotExistException();
            }
        } catch (SQLException | ClientDoesNotExistException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return foundClient;
    }

    private void prepareFindClientStatement(Bank bankToSearch, String nameOfTheClientToSearch) throws SQLException {
        preparedStatement.setString(1, nameOfTheClientToSearch);
        preparedStatement.setInt(2, bankToSearch.getId());
    }

    @Override
    public List<Client> getAllClients(Bank bank) throws DAOException {
        List<Client> clientsOfTheGivenBank = new ArrayList<>();
        openConnection();
        try {
            preparedStatement = conn.prepareStatement(GET_BANK_CLIENTS);
            prepareBankStatement(bank);
            ResultSet allClientsOfTheGivenBankResultSet = preparedStatement.executeQuery();
            while (allClientsOfTheGivenBankResultSet.next()) {
                Client clientToAdd = new Client(allClientsOfTheGivenBankResultSet.getString("NAME"));
                clientToAdd.setId(allClientsOfTheGivenBankResultSet.getInt("ID"));
                clientToAdd.setSurname(allClientsOfTheGivenBankResultSet.getString("SURNAME"));
                clientsOfTheGivenBank.add(clientToAdd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return clientsOfTheGivenBank;
    }

    private void prepareBankStatement(Bank bank) throws SQLException {
        preparedStatement.setInt(1, bank.getId());
    }

    @Override
    public void save(Client clientToSave) throws DAOException {
        openConnection();
        try {
            preparedStatement = conn.prepareStatement(UPDATE_CLIENT);
            prepareSaveClientStatement(clientToSave);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Client " + clientToSave.getName() + " " +
                        clientToSave.getSurname() +
                        " " + " updated, " + affectedRows + " rows affected");
            } else {
                System.err.println("Could not update the client " + clientToSave.getName()
                        + " " + clientToSave.getSurname() + ".");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    private void prepareSaveClientStatement(Client clientToSave) throws SQLException {
        preparedStatement.setString(1, clientToSave.getName());
        preparedStatement.setString(2, clientToSave.getSurname());
        preparedStatement.setInt(3, clientToSave.getId());
    }

    @Override
    public void remove(Client clientToRemove) throws DAOException {
        openConnection();
        try {
            preparedStatement = conn.prepareStatement(DELETE_CLIENT_BY_ID);
            prepareRemoveClientStatement(clientToRemove);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Client " + clientToRemove.getName() + " was deleted.");
            } else {
                System.err.println("Nothing to delete.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    private void prepareRemoveClientStatement(Client clientToRemove) throws SQLException {
        preparedStatement.setInt(1, clientToRemove.getId());
    }
}
