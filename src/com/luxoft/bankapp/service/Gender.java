package com.luxoft.bankapp.service;

public enum Gender {
    MALE("Mr."), FEMALE("Ms.");

    private String prefix;

    Gender(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }
}
