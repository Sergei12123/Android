package ru.mirea.ivanov.mireaproject.ui.history.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface HistoryDao {
    @Query("SELECT * FROM history")
    List<History> getAll();

    @Query("SELECT * FROM history WHERE id = :id")
    History getById(long id);

    @Insert
    void insert(History history);

    @Update
    void update(History history);

    @Delete
    void delete(History history);
}
