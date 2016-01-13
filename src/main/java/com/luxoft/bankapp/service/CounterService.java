package com.luxoft.bankapp.service;

import java.util.concurrent.atomic.AtomicInteger;

public interface CounterService {
    void incrementCounter();
    void decrementCounter();
    int getCounter();
    void setCounter(int atomicIntegerCounterValue);
}
