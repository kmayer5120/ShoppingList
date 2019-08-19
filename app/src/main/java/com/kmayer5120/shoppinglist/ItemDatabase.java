package com.kmayer5120.shoppinglist;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Item.class}, version = 1, exportSchema = false)
public abstract class ItemDatabase extends RoomDatabase
{

    public abstract DaoAccess daoAccess();
}
