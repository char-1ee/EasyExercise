package com.example.myapplication.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Time utilities for time formatting and parsing.
 *
 * @author Li Xingjian
 */
public class TimeUtil {
    public static String pattern1 = "yyyy-MM-dd HH:mm:ss:SSS";
    public static String pattern2 = "yyyy-MM-dd HH:mm";
    public static String pattern3 = "yyyy-MM-dd";
    public static String pattern4 = "yyyy_MM_dd";
    public static String pattern5 = "yyyy-MM-dd-HH-mm";
    public static String pattern6 = "HH:mm";
    public static String pattern7 = "HH:mm:ss";

    /**
     * SimpleDateFormat formatting.
     *
     * @param time    time formatting in {@link Date}
     * @param pattern targeted format
     * @return time formatting in {@link String}
     */
    public static String timeToString(long time, String pattern) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
        Date mDate = new Date(time);
        return sdf.format(mDate);
    }

    /**
     * SimpleDateFormat parsing.
     *
     * @param timeString time formatting in {@link String}
     * @param pattern    targeted format
     * @return time formatting in {@link Date}
     * @throws ParseException when parsing error
     */
    public static long stringToTime(String timeString, String pattern) throws ParseException {
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
     * Convert seconds into hours, minutes, seconds.
     *
     * @param date targeted time
     * @return time formatting in hour, minute, second
     */
    public static String getRecordDate(Integer date) {
        int hour = date / 3600;
        int minute = (date % 3600) / 60;
        int second = (date % 3600) % 60;
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", hour, minute, second);
    }

    /**
     * Get current time
     *
     * @param pattern targeted format
     * @return current time formatting in String
     */
    public static String getCurrTime(String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }
}
