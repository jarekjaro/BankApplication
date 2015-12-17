package com.luxoft.bankapp.exceptions;

public class ClientDoesNotExistException extends BankException {

    private static final long serialVersionUID = 6708904060068336777L;
    private final String message;

    public ClientDoesNotExistException() {
        message = "Required client does not exist!";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
