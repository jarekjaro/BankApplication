package com.luxoft.bankapp.model;

import com.luxoft.bankapp.exceptions.NotEnoughFundsException;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public interface Account extends Report {
    float getBalance();

    void deposit(float amount);

    void withdraw(float amount) throws NotEnoughFundsException;

    default void decimalValue(){
        int temp = (int)Math.round((this.getBalance() * Math.pow(10 , 2)));
        double rounded = ((double)temp)/Math.pow(10 , 2);
        System.out.printf("%,10.2f\n",rounded);
    }

    void parseFeed(Map<String,String> propertiesMap);
}
