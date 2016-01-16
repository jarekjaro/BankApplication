package com.luxoft.bankapp.commands;

/**
 * Created by jaro on 12/16/15.
 */
public interface Command {
    void execute();

    void printCommandInfo();
}
