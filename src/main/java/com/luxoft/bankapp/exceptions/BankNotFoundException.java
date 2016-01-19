package com.luxoft.bankapp.exceptions;

public class BankNotFoundException extends Throwable {
    public BankNotFoundException(String name) {
        super(name);
    }
}
