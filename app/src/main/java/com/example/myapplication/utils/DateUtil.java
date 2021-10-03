package com.example.myapplication.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    // TODO: Combine TimeUtil with this class
    private DateUtil() {
        // Disallows instantiation
    }

    public static Date getDateFromString(String source, String pattern) {
        try {
            return new SimpleDateFormat(pattern, Locale.getDefault()).parse(source);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String convertDateToString(Date source, String pattern) {
        return new SimpleDateFormat(pattern, Locale.getDefault()).format(source);
    }
}
