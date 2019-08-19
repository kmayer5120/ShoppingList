package com.kmayer5120.shoppinglist;

import android.annotation.SuppressLint;

import androidx.room.TypeConverter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimestampConverter
{

    @SuppressLint("SimpleDateFormat")
    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static TimeZone timeZone = TimeZone.getTimeZone("America/Denver");

    @TypeConverter
    public static Date fromTimestamp(String value)
    {
        if (value != null)
        {
            try
            {
                df.setTimeZone(timeZone);
                return df.parse(value);
            } catch (ParseException e)
            {
                e.printStackTrace();
            }
            return null;
        } else
        {
            return null;
        }
    }


    @TypeConverter
    public static String dateToTimestamp(Date value)
    {
        df.setTimeZone(timeZone);
        return value == null ? null : df.format(value);
    }
}

