package com.luxoft.bankapp.service;

public enum Gender {
    MALE("Mr."), FEMALE("Ms.");

    private final String salutation;

    Gender(String salutation) {
        this.salutation = salutation;
    }

    public String getSalutation() {
        return salutation;
    }
}
