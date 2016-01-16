package com.luxoft.bankapp.service;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class BankClientMock implements Runnable {
    static final String SERVER = "localhost";
    Socket requestSocket;
    ObjectOutputStream out;
    ObjectInputStream in;
    String message;
    float amountOnTheAccount;

    public static void main(final String args[]) {
        BankClientMock client = new BankClientMock();
        client.run();
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
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
                    if (message.equals("Connection successful")) {
                        int z = 0;
                        while (true) {
//                            printMainMenu();
                            int firstRequest = 4;
                            int secondRequest = 2;
                            int thirdRequest = 1;
                            int fourthRequest = 3;
                            int clientRequest = 0;
                            if (z == 0) {
                                clientRequest = firstRequest;
                            } else if (z == 1) {
                                clientRequest = secondRequest;
                            } else if (z == 2) {
                                clientRequest = thirdRequest;
                            } else if (z == 3) {
                                clientRequest = fourthRequest;
                            }
                            if (clientRequest == 1) sendMessage("check_balance");
                            else if (clientRequest == 2) sendMessage("withdraw");
                            else if (clientRequest == 4) {
                                String name = "Janusz";
                                sendMessage(name);
                            } else {
                                message = "disconnect";
                                sendMessage(message);
                                break;
                            }
                            try {
                                message = (String) in.readObject();
                                System.out.println("server>" + message);
                                if (message.equals("how much")) {
//                                    printAmountMenu();
                                    int amount = 1000;
                                    if (amount > 9) {
                                        sendMessage(String.valueOf(amount));
                                        message = (String) in.readObject();
                                        if (message.equals("Not enough funds!")) {
                                            System.err.println(message);
                                            System.err.println("Please check your balance!");
                                            sendMessage("disconnect");
                                            break;
                                        } else {
                                            System.out.println("server>" + message);
                                            System.out.println(message);
                                        }
                                    }
                                } else if (message.matches("Balance is:.*")) {
                                    String subString = message.substring(11);
                                    amountOnTheAccount = Float.parseFloat(subString);
                                    System.out.println(message);
                                } else if (message.equals("login_successful")) {

                                } else {
                                    System.err.println(message);
                                }
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                            z++;
                        }
                    }
                } catch (ClassNotFoundException classNot) {
                    System.err.println("data received in unknown format");
                }
            } while (!message.equals("disconnect"));
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
        sc.close();
    }

    private void sendMessage(final String msg) {
        try {
            out.writeObject(msg);
            out.flush();
            System.out.println("client>" + msg);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private static void printMainMenu() {
        System.out.println("Choose your operation:");
        System.out.println("(1) for checking balance");
        System.out.println("(2) to withdraw");
        System.out.println("(3) to disconnect");
        System.out.println("(4) to login");
    }

    private static int readClientRequest(Scanner scanner) {
        String parseCommand;
        while (true) {
            parseCommand = scanner.nextLine();
            if (parseCommand.matches("[1-4]")) {
                break;
            } else {
                System.err.println("You can only pick option (1-3)");
            }
        }
        return Integer.parseInt(parseCommand);
    }

    private static void printAmountMenu() {
        System.out.println("Choose the amount to withdraw");
        System.out.println("(1) 20");
        System.out.println("(2) 50");
        System.out.println("(3) 100");
        System.out.println("(4) 200");
        System.out.println("(5) 300");
        System.out.println("(6) 400");
        System.out.println("(7) other");
        System.out.println("(8) to go back");
    }

    private static int readAmount(Scanner scanner) {
        String parseCommand;
        Map<Integer, Integer> amountsToWithdrawMap = new TreeMap<>();
        amountsToWithdrawMap.put(1, 20);
        amountsToWithdrawMap.put(2, 50);
        amountsToWithdrawMap.put(3, 100);
        amountsToWithdrawMap.put(4, 200);
        amountsToWithdrawMap.put(5, 300);
        amountsToWithdrawMap.put(6, 400);
        while (true) {
            parseCommand = scanner.nextLine();
            if (parseCommand.matches("[1-8]")) {
                if (parseCommand.equals(String.valueOf(7))) {
                    parseCommand = readCustomAmount(scanner);
                    break;
                } else if (parseCommand.matches("[1-6]")) {
                    parseCommand = String.valueOf(amountsToWithdrawMap.get(Integer.parseInt(parseCommand)));
                    break;
                } else if (parseCommand.matches("[8]")) {
                    break;
                } else {
                    System.err.println("You can only pick option (1-8)");
                }
            }
        }
        return Integer.parseInt(parseCommand);
    }

    private static String readCustomAmount(Scanner scanner) {
        String parseCommand = "";
        while (true) {
            System.out.println("Enter custom amount: ");
            parseCommand = scanner.nextLine();
            if (parseCommand.matches("[0-9]{2,4}")) {
                break;
            } else {
                System.err.println("You should provide positive integer value between (10-9999)");
            }
        }
        return parseCommand;
    }

    public float getAmountOnTheAccount() {
        return amountOnTheAccount;
    }

    private String getLoginName(Scanner scanner) {
        String parseCommand;
        while (true) {
            System.out.println("Provide a login name:");
            parseCommand = scanner.nextLine();
            if (parseCommand.matches("[A-Z][a-z]*")) {
                return parseCommand;
            } else {
                System.err.println("Provide a name starting with Capital letter!");
            }
        }
    }
}
