package com.miami.moveforless.utils;

import java.util.Calendar;
import java.util.GregorianCalendar;


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
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static boolean isFuture(int jobDay) {
        return jobDay != getCurrentDay() && jobDay !=getNextDay();
    }
}
