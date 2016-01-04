package com.luxoft.bankapp.service;

import java.io.IOException;
import java.net.ServerSocket;

public class BankServer {
    public static void main(String[] args) {
        ServerSocket bankSocket = null;
        try {
            bankSocket = new ServerSocket(5432);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
