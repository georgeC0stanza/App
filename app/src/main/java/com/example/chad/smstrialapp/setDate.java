package com.example.chad.smstrialapp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Chad on 12/8/2017.
 */


/**
 * This class can set tomorrow's date, the first of next year, or the beginning of next week
 * into the format we need yyyy-MM-dd.
 */
public class setDate {
    /**
     * This class can set tomorrow's date. To do that, we get todays date and add 1.
     */
     public String getTomorrowsDate(){
        String dt = "2017-11-22";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 1);
        dt = df.format(c.getTime());
        return dt;
    }

    /**
     * This class can sets the first of next year. This is done by taking the current day from
     * the days in the month - the day of the month +1
     */
    public String getNewYearNewMonth(){
        String dt = "2017-11-22";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        int daysInMonth = c.getActualMaximum(Calendar.DATE);
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        c.add(Calendar.DATE, daysInMonth-dayOfMonth+1);
        dt = df.format(c.getTime());
        return dt;
    }

    /**
     * This class can sets the first of the next week. This is done by getting today day of the week,
     * and subtracting it from 7 and adding 1
     */
    public String getFirstDayOfWeek(){
        String dt = "2017-11-22";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        int daysInMonth = c.getActualMaximum(Calendar.DATE);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        c.add(Calendar.DATE, 7 - dayOfWeek+1);
        dt = df.format(c.getTime());
        return dt;
    }

}