package com.miami.moveforless.utils;

import java.util.Calendar;


/**
 * Created by Sergbek on 17.11.2015.
 */
public class TimeUtil {

    public static long getUnixTime() {
        return System.currentTimeMillis() / 1000L;
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
