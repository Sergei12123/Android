package ru.mirea.ivanov.resultactivity;

public enum Constants {

    EXTRA_MESS_NAME("name");

    private final String value;

    Constants(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
