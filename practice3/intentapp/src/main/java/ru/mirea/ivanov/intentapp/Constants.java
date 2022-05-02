package ru.mirea.ivanov.intentapp;

public enum Constants {

    EXTRA_MESS_NAME("msgDateString");

    private final String value;

    Constants(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
