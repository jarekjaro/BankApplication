package com.luxoft.bankapp.exceptions;

public class DAOException extends Exception {
    private final String message;

    public DAOException() {
        message = "Could not connect to Database, some other processes might be using it.";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
