package com.luxoft.bankapp.service;

import com.luxoft.bankapp.model.Bank;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class BankServerThreaded {
    private static AtomicInteger connectionsCount = new AtomicInteger(0);
    private final int PORT = 2004;
    private final int POOL_SIZE = 10;
    private ServerSocket serverSocket = null;
    private ExecutorService pool = null;
    private Socket clientSocket = null;
    private Bank currentBank;

    public BankServerThreaded(Bank bank) throws IOException {
        serverSocket = new ServerSocket(PORT, 10);
        pool = Executors.newFixedThreadPool(POOL_SIZE);
        currentBank = bank;
    }

    private static void decrementConnectionsAmount() {
        connectionsCount.getAndDecrement();
    }

    public static void main(String[] args) throws IOException {
        Bank bank1 = new Bank();
        BankApplication.initialize(bank1);
        BankServerThreaded bankServerThreaded = new BankServerThreaded(bank1);
        while (true) {
            bankServerThreaded.run();
        }
    }

    public void run() {
        try {
            while (true) {
                System.out.println("Waiting for connection");
                clientSocket = serverSocket.accept();
                incrementConnectionsAmount();
                pool.execute(new ServerThread(clientSocket, currentBank));
                System.out.println("Connection received from " + clientSocket.getInetAddress().getHostName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void incrementConnectionsAmount() {
        connectionsCount.getAndIncrement();
    }
}
