package com.luxoft.bankapp.service;

import com.luxoft.bankapp.model.Client;

public interface EventNotificationListener {
    void onClientAdded(Client client);
}
