package com.luxoft.bankapp.service;

public class BankException extends Exception {
    private String message;

    public BankException() {
        message="Bank Exception.";
    }

    @Override
    public String getMessage(){
        return message;
    }
}
