package com.luxoft.bankapp.DAO;

import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

import java.util.List;

public interface ClientDAO {
    /**
       * Return client by its name, initialize client accounts.
       * @param bankToSearch
       * @param nameOfTheClientToSearch
       * @return
       */
    Client findClientByName(Bank bankToSearch, String nameOfTheClientToSearch) throws DAOException;
    /**
     * Returns the list of all clients of the Bank
     * And their accounts
     * @param bank
     * @return
     */
    List<Client> getAllClients(Bank bank) throws DAOException;
    /**
    * Method should insert new Client (if id == null)
    * Or update client in database
    * @param clientToSave
    */
    void save(Client clientToSave) throws DAOException;
    /**
            * Method removes client from Database
    * @param clientToRemove
    */
    void remove(Client clientToRemove) throws DAOException;
}
