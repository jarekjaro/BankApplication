package com.luxoft.bankapp.service;

import com.luxoft.bankapp.model.Bank;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class BankServerThreaded {
    private final int PORT = 2004;
    private final int MAX_POOL_SIZE = 3;
    private final int CORE_POOL_SIZE = 2;
    protected ThreadPoolExecutor pool = null;
    private ServerSocket serverSocket = null;
    private Bank currentBank;
    private CounterService threadedClients;
    private CounterService connectedClients;
    private BlockingQueue<Runnable> clientsToExecuteQue;
    private BankServerMonitor bankServerMonitor;

    public BankServerThreaded(Bank bank) throws IOException {
        clientsToExecuteQue = new ArrayBlockingQueue<>(11,true);
        serverSocket = new ServerSocket(PORT, 10);
        pool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, 1L, TimeUnit.DAYS, clientsToExecuteQue);
        currentBank = bank;
        threadedClients = new CounterServiceImpl();
        connectedClients = new CounterServiceImpl();
        bankServerMonitor = new BankServerMonitor(connectedClients, threadedClients);
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
            Thread bankMonitor = new Thread(bankServerMonitor);
            pool.submit(bankMonitor);
            while (true) {
                System.out.println("Waiting for connection");
                Socket clientSocket = serverSocket.accept();
                connectedClients.incrementCounter();
                System.out.println("Connection received from " + clientSocket.getInetAddress().getHostName());
                System.out.println("Adding client to the threads que...");
                ServerThread currentThread = new ServerThread(clientSocket, currentBank, threadedClients, connectedClients);
                pool.submit(currentThread);
                threadedClients.setCounter(pool.getActiveCount());
                System.out.println(threadedClients);
                System.gc();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
