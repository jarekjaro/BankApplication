package com.luxoft.bankapp.service;

public class BankServerMonitor implements Runnable {
    private CounterService connectedClients;
    private CounterService threadedClients;
    private CounterService queuedClients;

    public BankServerMonitor(CounterService connectedClients, CounterService threadedClients) {
        queuedClients = new CounterServiceImpl();
        this.connectedClients = connectedClients;
        this.threadedClients = threadedClients;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            queuedClients.setCounter(connectedClients.getCounter() - threadedClients.getCounter()+1);
            System.out.println("Que size is currently: " + queuedClients.getCounter());
        }
    }
}
