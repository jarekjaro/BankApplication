package com.luxoft.bankapp.service;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InterfaceAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class BankClient {
    Socket requestSocket;
    ObjectOutputStream out;
    ObjectInputStream in;
    String message;
    static final String SERVER = "localhost";

    void run() {
        try {
            // 1. creating a socket to connect to the server
            requestSocket = new Socket(SERVER, 2004);
            System.out.println("Connected to localhost in port 2004");
            // 2. get Input and Output streams
            out = new ObjectOutputStream(requestSocket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(requestSocket.getInputStream());
            // 3. Communicating with the server
            do {
                try {
                    message = (String) in.readObject();
                    System.out.println("server>" + message);
                    if (readClientRequest() == 1) {
                        sendMessage("check_balance");
                    } else {
                        sendMessage("withdraw_"+readAmount());
                    }
                    sendMessage("Hi my server");
                    message = "bye";
                    sendMessage(message);
                } catch (ClassNotFoundException classNot) {
                    System.err.println("data received in unknown format");
                }
            } while (!message.equals("bye"));
        } catch (UnknownHostException unknownHost) {
            System.err.println("You are trying to connect to an unknown host!");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            // 4. Closing connection
            try {
                in.close();
                out.close();
                requestSocket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    void sendMessage(final String msg) {
        try {
            out.writeObject(msg);
            out.flush();
            System.out.println("client>" + msg);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static void main(final String args[]) {
        BankClient client = new BankClient();
        client.run();
    }

    public static int readClientRequest() {
        Scanner sc = new Scanner(System.in);
        String parseCommand = "";
        boolean flag = true;
        while (flag) {
            System.out.println("Choose your operation:");
            System.out.println("(1) for checking balance");
            System.out.println("(2) to withdraw");
            parseCommand = sc.nextLine();
            if (parseCommand.matches("[1-2]")) {
                flag = false;
                sc.close();
            } else {
                System.err.println("You can only pick option (1) or option (2) !");
            }
        }
        return Integer.parseInt(parseCommand);
    }

    public static int readAmount() {
        Scanner sc = new Scanner(System.in);
        String parseCommand = "";
        boolean flag = true;
        while (flag) {
            System.out.println("Choose the amount to withdraw");
            System.out.println("(1) 20");
            System.out.println("(2) 50");
            System.out.println("(3) 100");
            System.out.println("(4) 200");
            System.out.println("(5) 300");
            System.out.println("(6) 400");
            System.out.println("(7) other");
            parseCommand = sc.nextLine();
            if (parseCommand.matches("[1-7]")) {
                flag = false;
            } else {
                System.err.println("You can only pick option (1-7)!");
            }
            if (parseCommand.equals("7")) {
                boolean customAmountFlag = true;
                while (customAmountFlag) {
                    System.out.println("Enter the custom amount: ");
                    sc.nextLine();
                    if (parseCommand.matches("[0-9]*")) {
                        customAmountFlag = false;
                    } else {
                        System.err.println("You should provide positive integer value!");
                    }
                }
            }
        }
        return Integer.parseInt(parseCommand);
    }
}
