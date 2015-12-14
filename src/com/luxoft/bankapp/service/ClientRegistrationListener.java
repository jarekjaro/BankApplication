package com.luxoft.bankapp.service;

import com.luxoft.bankapp.model.Client;

public interface ClientRegistrationListener extends EventNotificationListener{
    @Override
    void onClientAdded(Client client);
}
