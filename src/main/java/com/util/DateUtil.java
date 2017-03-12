package com.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yangliu on 18/12/2016.
 */
public class DateUtil {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static String dateToString(Date date) {
        return sdf.format(date);
    }

    public static Date stringToDate(String date) {
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean dateComparator(String date1, String date2) {
        try {
            return sdf.parse(date1).compareTo(sdf.parse(date2)) == 0;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
}
