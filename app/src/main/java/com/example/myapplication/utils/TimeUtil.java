package com.example.myapplication.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A utility class for time formatting and parsing
 */
public class TimeUtil {

    /**
     * SimpleDateFormat formatting
     * @param time time formatting in {@link Date} to be convert to {@link String}
     * @param pattern targeted format
     */
    public static String timeToString(long time, String pattern) {
        @SuppressLint("SimpleDateFormat")SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
        Date mDate = new Date(time);
        return sdf.format(mDate);
    }

    /**
     * SimpleDateFormat parsing
     * @param timeString time formatting in {@link String} to be parsed to {@link Date}
     * @param pattern targeted format
     */
    public static long stringToTime(String timeString, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
        try {
            Date mDate = sdf.parse(timeString);
            assert mDate != null;   // In case of NullPointer
            return mDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Convert seconds into hours, minutes, seconds
     * @param date targeted time
     * @return time formatting in hour, minute, second
     */
    public static String getRecordDate(Integer date) {
        int hour = date / 3600;
        int minute = (date % 3600) / 60;
        int second = (date % 3600) % 60;
        return String.format("%02d:%02d:%02d", hour, minute, second); // An implicit method can be refactored
    }

    /**
     * Get current time
     * @param pattern targeted format
     * @return current time formatting in String
     */
    public static String getCurrTime(String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }
}
