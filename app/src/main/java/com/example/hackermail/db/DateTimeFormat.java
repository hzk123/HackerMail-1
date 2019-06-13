package com.example.hackermail.db;

import java.util.Calendar;

public class DateTimeFormat {
    public static String getYearString(Calendar cal) {
        return DateTimeFormat.lessThanTen(cal.get(Calendar.YEAR));
    }

    public static String getYearString(int year) {
        return DateTimeFormat.lessThanTen(year);
    }

    public static String getMonthString(Calendar cal) {
        return DateTimeFormat.lessThanTen(cal.get(Calendar.MONTH) + 1);
    }

    public static String getMonthString(int month) {
        return DateTimeFormat.lessThanTen(month + 1);
    }

    public static String getDayString(Calendar cal) {
        return DateTimeFormat.lessThanTen(cal.get(Calendar.DAY_OF_MONTH));
    }

    public static String getDayString(int day) {
        return DateTimeFormat.lessThanTen(day);
    }

    public static String getHourString(Calendar cal) {
        return DateTimeFormat.lessThanTen(cal.get(Calendar.HOUR_OF_DAY));
    }

    public static String getHourString(int hour) {
        return DateTimeFormat.lessThanTen(hour);
    }

    public static String getMinuteString(Calendar cal) {
        return DateTimeFormat.lessThanTen(cal.get(Calendar.MINUTE));
    }

    public static String getMinuteString(int minute) {
        return DateTimeFormat.lessThanTen(minute);
    }

    private static String lessThanTen(int value) {
        return value < 10 ? "0" + value : Integer.toString(value);
    }

    public static int getYearInteger(String year) {
        return Integer.valueOf(year);
    }

    public static int getMonthInteger(String month) {
        return Integer.valueOf(month) - 1;
    }

    public static int getDayInteger(String day) {
        return Integer.valueOf(day);
    }

    public static int getHourInteger(String hour) {
        return Integer.valueOf(hour);
    }

    public static int getMinuteInteger(String minute) {
        return Integer.valueOf(minute);
    }
}
