package com.luxoft.bankapp.commands;

import com.luxoft.bankapp.commands.Command;

/**
 * Created by jaro on 12/16/15.
 */
public class TransferCommand implements Command {
    @Override
    public void execute() {

    }

    @Override
    public void printCommandInfo() {
        System.out.println("Transfer Command");
    }
}
