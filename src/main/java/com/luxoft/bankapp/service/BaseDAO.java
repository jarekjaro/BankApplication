package com.luxoft.bankapp.service;

import com.luxoft.bankapp.exceptions.DAOException;

import java.sql.Connection;

public interface BaseDAO {
    Connection openConnection() throws DAOException;

    Connection closeConnection();
}

