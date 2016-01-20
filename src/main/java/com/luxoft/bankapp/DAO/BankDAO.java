package com.luxoft.bankapp.DAO;

import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.Bank;

public interface BankDAO {
    /**
       * Finds Bank by its name.
       * Do not load the list of the clients.
       * @ Param name
       * @ Return
     */
    Bank getBankByName(String name) throws DAOException;

    void save(Bank bank) throws DAOException;

    void remove(Bank bank) throws DAOException;
}
