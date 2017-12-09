package com.example.chad.smstrialapp;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Test if the setdate function is setting the function for tomorrow, Jan 01, and the beginning of the next week.
 */
public class ExampleUnitTest {
    @Test
    public void DateSetCorrectly() throws Exception {
        setDate testDate = new setDate();
        Calendar c = Calendar.getInstance();
        String tomorrow = testDate.getTomorrowsDate();
        // replace the expected with tomorrows date
        assertEquals("2017-12-09", tomorrow);
        //replace expected with the first month and day of next year
        String monthOrYearChange = testDate.getNewYearNewMonth();
        assertEquals("2018-01-01", monthOrYearChange);
        //replace expected with Monday of next week
        String weekChange = testDate.getFirstDayOfWeek();
        assertEquals("2017-12-10", weekChange);
    }

}