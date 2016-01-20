package com.luxoft.bankapp.model;

import java.io.Serializable;
import java.util.*;

public class Client implements Report, Comparable<Client>, Serializable {
    private static final long serialVersionUID = -6733881985047382792L;
    Set<Account> accounts;
    private String name;
    private String surname;
    private Gender gender;
    private String phone;
    private String email;
    private Account activeAccount;
    private float initialOverdraft;
    private String city;
    private int id;

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
    }

    public Client(String name, Gender gender) {
        this.name = name;
        this.gender = gender;
        this.accounts = new HashSet<>(2);
        this.activeAccount = null;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public float getInitialOverdraft() {
        return initialOverdraft;
    }

    public void setInitialOverdraft(float initialOverdraft) {
        this.initialOverdraft = initialOverdraft;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addAccount(Account accountToAdd) {
        accounts.add(accountToAdd);
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

    public void setActiveAccount(Account activeAccount) {
        this.activeAccount = activeAccount;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Set<Account> getAccounts() {
        return Collections.unmodifiableSet(accounts);
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    public void printReport() {
        System.out.printf("%s%s\n", getClientSalutation(), name);
        accounts.forEach(System.out::println);
    }

    public String getClientSalutation() {
        return gender.getSalutation() + " ";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + gender.hashCode();
        result = 31 * result + accounts.hashCode();
        return result;
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
    public int compareTo(Client o) {
        return name.compareTo(o.name);
    }
}
