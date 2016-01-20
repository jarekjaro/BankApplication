package com.luxoft.bankapp.DAO;

import com.luxoft.bankapp.exceptions.DAOException;
import com.luxoft.bankapp.model.AbstractAccount;
import com.luxoft.bankapp.model.Account;

import java.util.List;

interface AccountDAO {
    void save(Account account) throws DAOException;
    void add(Account account) throws DAOException;
    void removeByClientId(int idClient) throws DAOException;
    List<Account> getClientAccounts(int idClient) throws DAOException;
}

