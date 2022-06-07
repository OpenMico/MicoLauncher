package com.xiaomi.smarthome.library.common.util;

import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.accountsdk.account.XMPassport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import org.xml.sax.SAXException;

/* loaded from: classes4.dex */
public class DateTimeHelper {
    public static final TimeZone sBeijingTimeZone = TimeZone.getTimeZone("Asia/Shanghai");
    public static final long sDayInMilliseconds = 86400000;
    public static final long sDayInMinutes = 1440;
    public static final long sHourInMilliseconds = 3600000;
    public static final long sHourInMinutes = 60;
    public static final long sMinuteInMilliseconds = 60000;

    public static long getCurrentTiemstamp() {
        return Calendar.getInstance(sBeijingTimeZone).getTimeInMillis();
    }

    public static long getTodayStartTimestamp() {
        return getTodayStartTimestamp(getCurrentTiemstamp());
    }

    public static long getTodayStartTimestamp(long j) {
        return j - (j % 86400000);
    }

    public static long getTomorrowStartTimestamp(long j) {
        return (j - (j % 86400000)) + 86400000;
    }

    public static long getElapsedMinutesFromToday() {
        return getElapsedMinutesFromToday(getCurrentTiemstamp());
    }

    public static long getElapsedMinutesFromToday(long j) {
        return (j - getTodayStartTimestamp(j)) / 60000;
    }

    public static long getElapsedMinutesFromHour() {
        return getElapsedMinutesFromHour(getCurrentTiemstamp());
    }

    public static long getElapsedMinutesFromHour(long j) {
        return getElapsedMinutesFromToday(j) % 60;
    }

    public static long parseDate(String str) throws SAXException {
        if (TextUtils.isEmpty(str)) {
            return -1L;
        }
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        try {
            gregorianCalendar.setTime(new SimpleDateFormat(XMPassport.SIMPLE_DATE_FORMAT, Locale.US).parse(str));
            gregorianCalendar.setTimeZone(sBeijingTimeZone);
            return gregorianCalendar.getTimeInMillis();
        } catch (ParseException e) {
            Log.e("common/DateTimeHelper", "Failed to parse date", e);
            return -1L;
        }
    }

    public static String getWeekday(Date date) {
        switch (date.getDay()) {
            case 0:
                return "周日";
            case 1:
                return "周一";
            case 2:
                return "周二";
            case 3:
                return "周三";
            case 4:
                return "周四";
            case 5:
                return "周五";
            default:
                return "周六";
        }
    }

    public static String getCurrentString(String str) {
        return new SimpleDateFormat(str).format(new Date(System.currentTimeMillis()));
    }
}
