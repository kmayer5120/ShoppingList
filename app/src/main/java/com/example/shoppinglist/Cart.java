package com.example.shoppinglist;

import java.util.ArrayList;
import java.util.List;

public class Cart
{


    private List<Item> items;
    private double total;

    public Cart()
    {
       this(new ArrayList<Item>());
       total = 0.0;
    }

    public Cart(ArrayList<Item> items)
    {
       this.items = items;
       total = 0.0;
       calculateTotal();
    }

    public List<Item> getItems()
    {
        return items;
    }

    public void setItems(List<Item> items)
    {
        this.items = items;
        calculateTotal();
    }

    private void calculateTotal()
    {
        for(Item item : items)
        {
            total += item.getPrice();
        }
    }
}
