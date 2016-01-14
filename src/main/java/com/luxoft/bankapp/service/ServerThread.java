package com.luxoft.bankapp.service;

import com.luxoft.bankapp.exceptions.ClientDoesNotExistException;
import com.luxoft.bankapp.exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.model.Bank;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread implements Runnable {
    Socket clientSocket = null;
    ObjectOutputStream out;
    ObjectInputStream in;
    String message;
    String currentClientName;
    private Bank bank = null;
    private CounterService threadedClients;
    private CounterService connectedClients;

    public ServerThread(Socket clientSocket, Bank currentBank, CounterService threadedClients, CounterService connectedClients) {
        this.clientSocket = clientSocket;
        this.bank = currentBank;
        this.threadedClients = threadedClients;
        this.connectedClients = connectedClients;
    }

    public void run() {
        System.out.println("Executing client...");
        //3. get Input and Output Streams
        try {
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(clientSocket.getInputStream());
            sendMessage("Connection successful");
            threadedClients.incrementCounter();
            System.out.println("Threads count is: " + threadedClients.getCounter());
            //4. the two parts communicate via the input and output streams
            do {
                try {
                    message = (String) in.readObject();
                    System.out.println("client>" + message);
                    if (message.matches("[A-Z][a-z]*")) {
                        try {
                            loginClient(message);
                            sendMessage("login_successful");
                        } catch (ClientDoesNotExistException e) {
                            sendMessage("Client does not exist!");
                        }
                    } else if (message.equals("check_balance")) {
                        try {
                            sendMessage("Balance is: " + String.valueOf(String.format("%,10.2f", checkBalance(currentClientName))));
                        } catch (ClientDoesNotExistException e) {
                            sendMessage("Client does not exist!");
                        }
                    } else if (message.equals("withdraw")) {
                        sendMessage("how much");
                    } else if (message.equals("disconnect")) {
                        threadedClients.decrementCounter();
                        connectedClients.decrementCounter();
                        System.out.println("Threads count is: " + threadedClients.getCounter());
                        break;
                    } else if (message.matches("[0-9]*?.[0-9]*")) {
                        try {
                            withdraw(currentClientName, Integer.parseInt(message));
                            sendMessage("Withdrawn: " + message);
                        } catch (ClientDoesNotExistException e) {
                            sendMessage("Client does not exist!");
                        } catch (NotEnoughFundsException e) {
                            sendMessage("Not enough funds!");
                        }
                    }
                } catch (ClassNotFoundException e) {
                    System.err.println("Data received in unknown format");
                }
            } while (!message.equals("disconnect"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //5. closing a connection
            try {
                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

    }

    private void sendMessage(String message) {
        try {
            out.writeObject(message);
            out.flush();
            System.out.println("server>" + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loginClient(String clientName) throws ClientDoesNotExistException {
        currentClientName = bank.getClientByName(clientName).getName();
    }

    private float checkBalance(String clientName) throws ClientDoesNotExistException {
        return bank.getClientByName(clientName).getActiveAccount().getBalance();
    }

    private void withdraw(String clientName, Integer amountToWithdraw) throws ClientDoesNotExistException, NotEnoughFundsException {
        bank.getClientByName(clientName).getActiveAccount().withdraw(amountToWithdraw);
    }
}
