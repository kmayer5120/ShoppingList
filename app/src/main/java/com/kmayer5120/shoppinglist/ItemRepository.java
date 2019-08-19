package com.kmayer5120.shoppinglist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import java.util.Date;
import java.util.List;

public class ItemRepository
{

    private String DB_NAME;

    private ItemDatabase itemDatabase;

    public ItemRepository(Context context)
    {
        DB_NAME = "DB_Items";
        itemDatabase = Room.databaseBuilder(context, ItemDatabase.class, DB_NAME).build();
    }


    public void insertTask(String title, String description, double price, double totalPrice, int quantity, boolean isTaxed)
    {

        Item item = new Item();
        Date date = new Date();
        item.setDescription(description);
        item.setCreatedAt(date);
        item.setModifiedAt(date);
        item.setPrice(price);
        item.setTotalPrice(totalPrice);
        item.setQuantity(quantity);
        item.setIsTaxed(isTaxed);

        insertTask(item);
    }

    @SuppressLint("StaticFieldLeak")
    public void insertTask(final Item item)
    {
        new AsyncTask<Void, Void, Void>()
        {
            @Override
            protected Void doInBackground(Void... voids)
            {
                itemDatabase.daoAccess().insertTask(item);
                return null;
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void updateTask(final Item item)
    {
        Date date = new Date();
        item.setModifiedAt(date);

        new AsyncTask<Void, Void, Void>()
        {
            @Override
            protected Void doInBackground(Void... voids)
            {
                itemDatabase.daoAccess().updateTask(item);
                return null;
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void deleteTask(final int id)
    {
        final LiveData<Item> task = getTask(id);
        if (task != null)
        {
            new AsyncTask<Void, Void, Void>()
            {
                @Override
                protected Void doInBackground(Void... voids)
                {
                    itemDatabase.daoAccess().delete(task.getValue());
                    return null;
                }
            }.execute();
        }
    }

    @SuppressLint("StaticFieldLeak")
    public void delete(final Item item)
    {
        new AsyncTask<Void, Void, Void>()
        {
            @Override
            protected Void doInBackground(Void... voids)
            {
                itemDatabase.daoAccess().delete(item);
                return null;
            }
        }.execute();
    }


    public LiveData<Item> getTask(int id)
    {
        return itemDatabase.daoAccess().getTask(id);
    }

    public LiveData<List<Item>> getTasks()
    {
        return itemDatabase.daoAccess().fetchAllTasks();
    }
}
