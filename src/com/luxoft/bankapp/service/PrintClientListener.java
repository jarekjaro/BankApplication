package com.luxoft.bankapp.service;

import com.luxoft.bankapp.model.Client;

public class PrintClientListener implements ClientRegistrationListener {
    @Override
    public void onClientAdded(Client client) {
        System.out.printf("Client %11s added.\n",client);
    }
}
