package com.template.basealarm;

import android.annotation.SuppressLint;
import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Utils {

    public static final int MAX_CALENDAR_DAYS = 31;

    public static final String DAILY = "Repeat Daily";
    public static final String WEEKLY = "Repeat Weekly";
    public static final String MONTHLY = "Repeat Monthly";
    public static final String YEARLY = "Repeat Yearly";

    public static String CURRENT_FILTER = "Today";
    public static final String TODAY = "Today";
    public static final String NEXT_7_DAYS = "Next 7 days";
    public static final String NEXT_30_DAYS = "Next 30 days";
    public static final String THIS_YEAR = "This Year";

    public enum NotificationPreference {
        TEN_MINUTES_BEFORE,
        ONE_HOUR_BEFORE,
        ONE_DAY_BEFORE,
        AT_THE_TIME_OF_EVENT
    }

    public enum AppTheme {
        INDIGO,
        DARK,
    }

    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);

    public static final SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM", Locale.ENGLISH);
    public static final SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.ENGLISH);
    public static final SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.ENGLISH);
    public static final SimpleDateFormat eventDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

    public static Date convertStringToDate(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        Date aDate = null;
        try {
            aDate = simpleDateFormat.parse(date);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return aDate;
    }

    public static Date convertStringToTime(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("kk:mm", Locale.ENGLISH);
        Date aDate = null;
        try {
            aDate = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return aDate;
    }


}
