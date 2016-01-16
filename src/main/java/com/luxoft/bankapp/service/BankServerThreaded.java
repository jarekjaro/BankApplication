package com.luxoft.bankapp.service;

import com.luxoft.bankapp.exceptions.ClientDoesNotExistException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BankServerThreaded {
    public static Client currentClient;
    private final int PORT = 2004;
    private final int MAX_POOL_SIZE = 11;
    private final int CORE_POOL_SIZE = 11;
    protected ThreadPoolExecutor pool = null;
    private ServerSocket serverSocket = null;
    private Bank currentBank;
    private CounterService threadedClients;
    private CounterService connectedClients;
    private BlockingQueue<Runnable> clientsToExecuteQue;
    private BankServerMonitor bankServerMonitor;

    public BankServerThreaded(Bank bank) throws IOException {
        clientsToExecuteQue = new ArrayBlockingQueue<>(10, true);
        serverSocket = new ServerSocket(PORT, 10);
        pool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, 1L, TimeUnit.DAYS, clientsToExecuteQue);
        currentBank = bank;
        threadedClients = new CounterServiceImpl();
        connectedClients = new CounterServiceImpl();
        bankServerMonitor = new BankServerMonitor(connectedClients, threadedClients);
    }

    public static Client getCurrentClient() {
        return currentClient;
    }

    public static void main(String[] args) throws IOException, ClientDoesNotExistException {
        Bank bank1 = new Bank();
        BankApplication.initialize(bank1);
        currentClient = bank1.getClientByName("Janusz");
        bank1.getClientByName("Janusz").getActiveAccount().deposit(1000000);

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
                ServerThread currentThread = new ServerThread(clientSocket, currentBank, threadedClients,
                        connectedClients, currentClient);
                pool.submit(currentThread);
                threadedClients.setCounter(pool.getActiveCount() - 1);
                System.out.println(threadedClients);
                System.gc();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
