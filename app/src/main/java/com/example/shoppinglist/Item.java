package com.example.shoppinglist;

import android.annotation.SuppressLint;

import java.text.NumberFormat;

public class Item
{
    private String description;
    private double price;
    private double totalPrice;
    private int quantity;
    private boolean isTaxed;
    private final double taxAmount = 0.0513;

    public Item()
    {
        this("Item",0.00, 1, false);
    }

    public Item(String description, double price, int quantity, boolean isTaxed)
    {
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.isTaxed = isTaxed;
        calculate();
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
        calculate();
        this.price = price;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
        calculate();
    }

    public void setIsTaxed(boolean isTaxed)
    {
        this.isTaxed = isTaxed;
        calculate();
    }

    public boolean isTaxed()
    {
        return this.isTaxed;
    }

    public final double getTaxAmount()
    {
        return this.taxAmount;
    }


    public int getQuantity() {return this.quantity;}

    @SuppressLint("DefaultLocale")
    public String getFormattedQuantity()
    {
            return String.format("Quantity: %d", this.quantity);
    }

    public String getTotalPrice()
    {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();

        if(quantity > 1) return "Items cost: " + formatter.format(this.totalPrice);

        return "Item cost: " + formatter.format(this.totalPrice);
    }

    private void calculate()
    {
        this.totalPrice = this.quantity * this.price;
        if (isTaxed)
        {
            this.totalPrice *= (1 + taxAmount);
        }
    }
}
