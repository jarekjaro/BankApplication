package com.luxoft.bankapp.service;

import java.util.concurrent.atomic.AtomicInteger;

public class CounterServiceImpl implements CounterService {

    private AtomicInteger atomicCounter = new AtomicInteger(0);

    @Override
    public void setCounter(int atomicCounter) {
        this.atomicCounter.getAndSet(atomicCounter);
    }

    @Override
    public void incrementCounter() {
        atomicCounter.getAndIncrement();

    }

    @Override
    public void decrementCounter() {
        atomicCounter.getAndDecrement();
    }

    @Override
    public int getCounter() {
       return atomicCounter.get();
    }
}
