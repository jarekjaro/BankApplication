package com.luxoft.bankapp.service;

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

    @Override
    public Client findClientByName(Bank bankToSearch, String nameOfTheClientToSearch)
            throws DAOException {
        Client foundClient = new Client(nameOfTheClientToSearch);
        String sqlQuery = "SELECT c.ID,  c.NAME,  c.SURNAME FROM CLIENT c " +
                "INNER JOIN BANKS_CLIENTS bc ON c.ID = bc.CLIENT_ID " +
                "WHERE c.NAME =? AND bc.BANK_ID=?;";
        PreparedStatement preparedStatement;
        try {
            openConnection();
            preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setString(1, nameOfTheClientToSearch);
            preparedStatement.setInt(2, bankToSearch.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("ID");
                foundClient.setId(id);
                String surname = resultSet.getString("SURNAME");
                foundClient.setSurname(surname);
            } else {
                throw new ClientDoesNotExistException();
            }
        } catch (DAOException | SQLException | ClientDoesNotExistException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return foundClient;
    }

    @Override
    public List<Client> getAllClients(Bank bank) throws DAOException {
        List<Client> clientsOfTheGivenBank = new ArrayList<>();
        String sqlQuery = "SELECT c.ID, c.NAME, c.SURNAME FROM CLIENT c " +
                "INNER JOIN BANKS_CLIENTS bc ON c.ID=bc.CLIENT_ID " +
                "WHERE bc.BANK_ID=?";
        PreparedStatement preparedStatement;
        try {
            openConnection();
            preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, bank.getId());
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

    @Override
    public void save(Client clientToSave) throws DAOException {
        String sqlUpdateQuery = "UPDATE CLIENT SET NAME=?, SURNAME=? WHERE ID=?";
        PreparedStatement preparedStatement;
        try {
            openConnection();
            preparedStatement = conn.prepareStatement(sqlUpdateQuery);
            preparedStatement.setString(1, clientToSave.getName());
            preparedStatement.setString(2, clientToSave.getSurname());
            preparedStatement.setInt(3, clientToSave.getId());
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
        }
    }

    @Override
    public void remove(Client clientToRemove) throws DAOException {
        openConnection();
        String sqlRemoveClientQuery = "DELETE FROM CLIENT WHERE ID=?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sqlRemoveClientQuery);
            preparedStatement.setInt(1, clientToRemove.getId());
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
}
