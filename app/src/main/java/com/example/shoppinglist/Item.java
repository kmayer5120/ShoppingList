package com.example.shoppinglist;

public class Item
{
    private String description;



    private double price;

    public Item()
    {
        this("Item",0.00);
    }

    public Item(String description, double price)
    {
        this.description = description;
        this.price = price;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return this.description;
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

}
