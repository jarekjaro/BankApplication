package com.luxoft.bankapp.service;

import com.luxoft.bankapp.model.Client;

public class EmailNotificationListener implements ClientRegistrationListener {
    @Override
    public void onClientAdded(Client client) {
        System.out.println("Notification email for a client " + client + " to be sent.");
    }
}
