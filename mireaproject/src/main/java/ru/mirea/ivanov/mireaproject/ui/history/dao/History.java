package ru.mirea.ivanov.mireaproject.ui.history.dao;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class History {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public String story;
}
