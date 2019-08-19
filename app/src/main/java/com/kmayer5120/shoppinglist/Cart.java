package com.kmayer5120.shoppinglist;

import java.text.NumberFormat;
import java.util.ArrayList;

public class Cart
{
    private ArrayList<Item> items;
    private double total;

    public Cart()
    {
       this(new ArrayList<Item>());
       total = 0.0;
       calculateTotal();
    }

    public Cart(ArrayList<Item> items)
    {
       this.items = items;
       total = 0.0;
       calculateTotal();
    }

    public ArrayList<Item> getItems()
    {
        return items;
    }

    public void setItems(ArrayList<Item> items)
    {
        this.items = items;
        calculateTotal();
    }

    public void addItem(Item item)
    {
        items.add(item);
        calculateTotal();
    }

    public void removeItem(Item item)
    {
        items.remove(item);
        calculateTotal();
    }

    public void removeItemAtIndex(int index)
    {
        items.remove(index);
        calculateTotal();
    }

    public void editItemAtIndex(Item editedItem, int index)
    {
        if(index > -1)
        {
            removeItemAtIndex(index);
            items.add(index, editedItem);
        }
        calculateTotal();
    }


    public void clearItems()
    {
        items.clear();
        calculateTotal();
    }

    private void calculateTotal()
    {
        //Calculates the total cart price for each item in cart
        total = 0.0;
        for(Item item : items)
        {
            total += item.getTotalPrice();
        }
    }

    @Override
    public String toString()
    {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(total);
    }
}
