package ru.mirea.ivanov.intentapp;

import android.support.annotation.NonNull;

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
