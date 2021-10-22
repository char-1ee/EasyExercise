package com.example.myapplication.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.Date;

/**
 * Date utility test.
 *
 * @author Zhong Ruoyu
 */
public class DateUtilTest {
    @Test
    public void test() {
        final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
        String dateString = "2021-01-02T03:04:05";
        Date date = DateUtil.getDateFromString(dateString, DATE_FORMAT);
        assertEquals(dateString, DateUtil.convertDateToString(date, DATE_FORMAT));
    }
}
