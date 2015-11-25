package com.miami.moveforless.utils;

import android.text.format.DateFormat;

import java.util.Calendar;


/**
 * Created by Sergbek on 17.11.2015.
 */
public class TimeUtil {

    public static long getUnixTime() {
        return (int) (System.currentTimeMillis());
    }

    public static String getDate(String unixTime, String format) {

        Long intUnixTime = Long.valueOf(unixTime);

        return DateFormat.format(format, intUnixTime).toString();
    }

    public static int getCurrentDay() {
        Calendar c = Calendar.getInstance();

        return c.get(Calendar.DAY_OF_MONTH);
    }

    public static int getNextDay() {
        Calendar c = Calendar.getInstance();

        int maxDayInMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);

        return getCurrentDay() + 1 <= maxDayInMonth ? getCurrentDay() + 1 : 1;

    }

}
