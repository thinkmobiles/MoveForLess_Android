package com.miami.moveforless.utils;

import android.text.format.DateFormat;


/**
 * Created by Sergbek on 17.11.2015.
 */
public class TimeUtil {

    public static int getUnixTime() {
        return (int) (System.currentTimeMillis() / 1000L);
    }

    public static String getDate(String unixTime, String format) {

        Integer intUnixTime = Integer.valueOf(unixTime);

        return DateFormat.format(format, intUnixTime * 1000L).toString();
    }

}
