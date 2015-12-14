package com.luxoft.bankapp.service;

import com.luxoft.bankapp.model.Client;

public class ClientExistsException extends Throwable {
    private String message;

    public ClientExistsException(Client client) {
        message = (client.getClientSalutation() + client.getName()
                + " is already our customer! NO ADDITION MADE!");
    }

    @Override
    public String getMessage(){
        return message;
    }
}
