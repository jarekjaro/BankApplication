package com.luxoft.bankapp.service;

import com.luxoft.bankapp.model.Client;

public class EmailNotificationListener implements ClientRegistrationListener {

    @Override
    public void onClientAdded(Client client) {
        System.out.printf("Notification email for a client %11s to be sent.\n", client);
    }
}
