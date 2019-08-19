package com.kmayer5120.shoppinglist;

import android.annotation.SuppressLint;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;
import java.text.NumberFormat;

@Entity
public class Item
{
    private double taxAmount = 0.0513;
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "created_at")
    @TypeConverters({TimestampConverter.class})
    private Date createdAt;
    @ColumnInfo(name = "modified_at")
    @TypeConverters({TimestampConverter.class})
    private Date modifiedAt;
    private String description;
    private double price;
    private double totalPrice;
    private int quantity;
    private boolean isTaxed;

    public Item()
    {
        this("Item", 0.00, 1, false);
    }

    @Ignore
    public Item(String description, double price, int quantity, boolean isTaxed)
    {
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.isTaxed = isTaxed;
        calculate();
    }

    public int getId()
    {
        return this.id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public Date getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt)
    {
        this.createdAt = createdAt;
    }

    public Date getModifiedAt()
    {
        return modifiedAt;
    }

    public void setModifiedAt(Date modifiedAt)
    {
        this.modifiedAt = modifiedAt;
    }

    public String getDescription()
    {
        return this.description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public double getPrice()
    {
        return this.price;
    }

    public void setPrice(double price)
    {
        this.price = price;
        calculate();
    }

    public void setTotalPrice(double totalPrice)
    {
        this.totalPrice = totalPrice;
        calculate();
    }

    public double getTotalPrice()
    {
        return this.totalPrice;
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

    public void setTaxAmount(double taxAmount) {this.taxAmount = taxAmount;}

    public int getQuantity()
    {
        return this.quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
        calculate();
    }

    @SuppressLint("DefaultLocale")
    public String getFormattedQuantity()
    {
        return String.format("Quantity: %d", this.quantity);
    }

    public String getFormattedTotalPrice()
    {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();

        if (quantity > 1) return "Items cost: " + formatter.format(this.totalPrice);

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
