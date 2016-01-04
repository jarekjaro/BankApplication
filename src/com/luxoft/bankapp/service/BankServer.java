package com.luxoft.bankapp.service;

import com.luxoft.bankapp.model.Bank;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class BankServer {
    private Bank bank = null;
    ServerSocket providerSocket = null;
    Socket connection = null;
    ObjectOutputStream out;
    ObjectInputStream in;
    String message;

    public BankServer(Bank bank) {
        this.bank=bank;
    }

    void run() {
        try {
            //1. Creating a server socket
            providerSocket = new ServerSocket(2004, 10);
            //2. waiting for connection
            System.out.println("Waiting for connection");
            connection = providerSocket.accept();
            System.out.println("Connection received from " + connection.getInetAddress().getHostName());
            //3. get Input and Output Streams
            out = new ObjectOutputStream(connection.getOutputStream());
            out.flush();
            in = new ObjectInputStream(connection.getInputStream());
            sendMessage("Connection successful");
            //4. the two parts communicate via the input and output streams
            do {
                try {
                    message = (String) in.readObject();
                    System.out.println("client>" + message);
                    if (message.equals("bye")) {
                        sendMessage("bye");
                    }
                } catch (ClassNotFoundException e) {
                    System.err.println("Data received in unknown format");
                }
            } while (!message.equals("bye"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //5. closing a connection
            try {
                in.close();
                out.close();
                providerSocket.close();
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

    public static void main(String[] args) {
        Bank bank1 = new Bank();
        BankApplication.initialize(bank1);
        BankServer server = new BankServer(bank1);
        while (true) {
            server.run();
        }
    }
}
