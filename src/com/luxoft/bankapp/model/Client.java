package com.luxoft.bankapp.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Client implements Report {

    private String name;
    private String surname;
    private Gender gender;
    List<Account> accounts;
    private String phone;
    private String email;
    private Account activeAccount;
    private float initialOverdraft;
    public Client(String name, String surname, String phone, String email, float initialOverdraft) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
        this.initialOverdraft = initialOverdraft;
    }
    public Client(String name) {
        this.name = name;
        this.accounts = new ArrayList<>(2);
    }
    public Client(String name, Gender gender) {
        this.name = name;
        this.gender = gender;
        this.accounts = new ArrayList<>(2);
    }
    public Account getActiveAccount() {
        return activeAccount;
    }

    public Client(float initialOverdraft) {
        this.initialOverdraft = initialOverdraft;
        //TODO
    }

    public void setActiveAccount(Account activeAccount) {
        this.activeAccount = activeAccount;
    }

    public List<Account> getAccounts() {
        return Collections.unmodifiableList(accounts);
    }

    public String getClientSalutation() {
        return gender.getSalutation() + " ";
    }

    public void printReport() {
        System.out.printf("%s%s\n", getClientSalutation(), name);
        accounts.forEach(System.out::println);
    }
    public String getName() {
        return name;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb
                .append(getClientSalutation())
                .append(name)
                .append(" (")
                .append(gender.name())
                .append(")")
                .append(" has ");
        for (int i = 0; i < accounts.size(); i++) {
            sb
                    .append(accounts.get(i))
                    .append("; ");
        }
        return sb.toString();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (!name.equals(client.name)) return false;
        if (gender != client.gender) return false;
        return accounts.equals(client.accounts);

    }
    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + gender.hashCode();
        result = 31 * result + accounts.hashCode();
        return result;
    }
}
