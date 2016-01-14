package com.luxoft.bankapp.model;

import java.io.Serializable;
import java.util.*;

public class Client implements Report, Comparable<Client>, Serializable {
    private static final long serialVersionUID = -6733881985047382792L;
    private String name;
    private String surname;
    private Gender gender;
    Set<Account> accounts;
    private String phone;
    private String email;
    private Account activeAccount;
    private float initialOverdraft;
    private String city;

    public Client(float initialOverdraft) {
        this.initialOverdraft = initialOverdraft;
    }

    public Client(String name, String surname, String phone, String email, float initialOverdraft) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
        this.initialOverdraft = initialOverdraft;
        this.gender = Gender.MALE;
    }

    public Client(String name) {
        this.name = name;
        this.accounts = new HashSet<>(2);
    }

    public Client(String name, Gender gender) {
        this.name = name;
        this.gender = gender;
        this.accounts = new HashSet<>(2);
        this.activeAccount = null;
    }

    public void parseFeed(Map<String, String> propertiesMap) {
        String accountType = null, name = null;
        Gender gender = null;
        Set<Map.Entry<String, String>> parsedMap = new TreeSet<>();
        parsedMap = propertiesMap.entrySet();
        for (Iterator<Map.Entry<String, String>> parsedMapIterator = parsedMap.iterator(); parsedMapIterator.hasNext(); ) {
            Map.Entry<String, String> mapEntry = parsedMapIterator.next();
            switch (mapEntry.getKey()) {
                case "name":
                    name = mapEntry.getValue();
                    break;
                case "accounttype":
                    accountType = mapEntry.getValue();
                    break;
                case "gender":
                    if (mapEntry.getValue().equalsIgnoreCase("f")) {
                        gender = Gender.FEMALE;
                    } else {
                        gender = Gender.MALE;
                    }
                    break;
            }
        }
        Account accountToParse;
        if (accountType.equalsIgnoreCase("c")) {
            accountToParse = new CheckingAccount(0, 0);
            accountToParse.parseFeed(propertiesMap);
        } else {
            accountToParse = new SavingAccount(0);
            accountToParse.parseFeed(propertiesMap);
        }

        accounts.add(accountToParse);
    }

    public Account getActiveAccount() {
        return activeAccount;
    }

    public Gender getGender() {
        return gender;
    }

    public void setActiveAccount(Account activeAccount) {
        this.activeAccount = activeAccount;
    }

    public Set<Account> getAccounts() {
        return Collections.unmodifiableSet(accounts);
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

    public String getCity() {
        return city;
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
        for (Account account : accounts) {
            sb
                    .append(account)
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

    @Override
    public int compareTo(Client o) {
        return name.compareTo(o.name);
    }
}
