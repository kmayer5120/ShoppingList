package com.kmayer5120.shoppinglist;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DaoAccess
{

    @Insert
    Long insertTask(Item item);


    @Query("SELECT * FROM Item ORDER BY created_at desc")
    LiveData<List<Item>> fetchAllTasks();

    @Query("SELECT COUNT(id) FROM Item")
    int getDataCount();

    @Query("SELECT * FROM Item WHERE id =:taskId")
    LiveData<Item> getTask(int taskId);

    @Update
    void updateTask(Item item);

    @Delete
    void delete(Item item);
}
