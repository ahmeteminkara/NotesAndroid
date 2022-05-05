package com.aek.notes.core.util;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SuppressLint("SimpleDateFormat")

public class DateUtils {

    /**
     * yyyy.MM.dd HH:mm:ss -> 2001.07.04 12:08:56
     */
    private DateUtils() {
    }

    /**
     * For today
     */
    public static String dateToString(String format) {
        return dateToString(format, Calendar.getInstance().getTime());
    }

    public static String dateToString(String format, Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * @param format  ex.: yyyy-MM-dd HH:mm:ss
     * @param dateStr ex.: 2021-03-25 15:56:02
     * @return new Date();
     */
    @Nullable
    public static Date stringToDate(String format, String dateStr) {
        try {
            return new SimpleDateFormat(format).parse(dateStr);
        } catch (ParseException e) {
            Log.e(DateUtils.class.getSimpleName(), "error: " + e.toString());
            return null;
        }
    }
}
