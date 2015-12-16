package com.luxoft.bankapp.service;

class BankException extends Exception {
    private static final long serialVersionUID = 6414969375178932557L;
    private final String message;

    public BankException() {
        message="Bank Exception.";
    }

    @Override
    public String getMessage(){
        return message;
    }
}
