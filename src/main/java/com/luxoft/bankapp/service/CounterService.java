package com.luxoft.bankapp.service;

public interface CounterService {
    void incrementCounter();

    void decrementCounter();

    int getCounter();

    void setCounter(int atomicIntegerCounterValue);
}
