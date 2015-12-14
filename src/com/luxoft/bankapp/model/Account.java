package com.luxoft.bankapp.model;

import com.luxoft.bankapp.service.NotEnoughFundsException;
import com.luxoft.bankapp.service.Report;

public interface Account extends Report {
    float getBalance();

    void deposit(float amount);

    void withdraw(float amount) throws NotEnoughFundsException;

}
