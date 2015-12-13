package com.luxoft.bankapp.model;
import com.luxoft.bankapp.service.Gender;
import com.luxoft.bankapp.service.Report;

import java.util.ArrayList;
import java.util.List;

public class Client implements Report {

    //FIELDS
    private String name;
    private Gender gender;
    private List<Account> accounts;
    private Account activeAccount;
    private float initialOverdrat;

    //CONSTRUCTORS
    public Client(String name) {
        this.name = name;
        this.accounts = new ArrayList<>(2);
    }


    public Client(String name, Gender gender) {
        this.name = name;
        this.gender = gender;
        this.accounts = new ArrayList<>(2);
    }

    public Client(float initialOverdrat) {
        this.initialOverdrat = initialOverdrat;
    }

    //METHODS
    public void setActiveAccount(Account a) {
        activeAccount=a;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public String getClientSalutation() {
        return gender.getPrefix()+" ";
    }
    public void printReport() {
        System.out.printf("%s%s\n", getClientSalutation(), name);
        accounts.forEach(account -> System.out.printf("%20s%6.2f\n", account, account.getBalance()));
    }
}
