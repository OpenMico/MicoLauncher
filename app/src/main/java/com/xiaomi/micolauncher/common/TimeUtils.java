package com.xiaomi.micolauncher.common;

import android.text.TextUtils;
import com.blankj.utilcode.constant.CacheConstants;
import com.xiaomi.mico.base.utils.TimeCalculator;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.micolauncher.module.main.util.RandomTimeUtils;
import com.xiaomi.micolauncher.skills.alarm.model.AlarmModel;
import com.xiaomi.mipush.sdk.Constants;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class TimeUtils {
    public static final String PATTERN_8601 = "yyyy-MM-dd'T'HH:mm:ssXXX";
    private static int a = 6;
    private static int b = 9;

    public static String getCurrentTimeInHourAndMinute() {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        return String.format(Locale.getDefault(), "%02d:%02d", Integer.valueOf(gregorianCalendar.get(11)), Integer.valueOf(gregorianCalendar.get(12)));
    }

    public static String getDateTimeInHourAndMinute(Date date) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        return String.format(Locale.getDefault(), "%02d:%02d", Integer.valueOf(instance.get(11)), Integer.valueOf(instance.get(12)));
    }

    public static String getCurrentDateFormatYMD() {
        return getCurrentTimeInFormat("yyyy.MM.dd");
    }

    public static String getCurrentTimeInFormat(String str) {
        return new SimpleDateFormat(str).format(new Date());
    }

    public static boolean isHalfOfHour() {
        int currentMinuteOfHour = getCurrentMinuteOfHour();
        return currentMinuteOfHour == 0 || currentMinuteOfHour == 30;
    }

    public static int getCurrentHourOfDay() {
        return new GregorianCalendar().get(11);
    }

    public static int getCurrentMinuteOfHour() {
        return new GregorianCalendar().get(12);
    }

    public static int getCurrentDayOfWeek() {
        return (new GregorianCalendar().get(7) + 5) % 7;
    }

    public static int getCurrentDayOfMonth() {
        return new GregorianCalendar().get(5);
    }

    public static boolean isInnerTwoHours(long j) {
        return System.currentTimeMillis() - j < 7200000;
    }

    public static int getCurrentMonth() {
        return new GregorianCalendar().get(2) + 1;
    }

    public static int getCurrentYear() {
        return new GregorianCalendar().get(1);
    }

    public static boolean checkSameDay(long j, long j2) {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(j);
        Calendar instance2 = Calendar.getInstance();
        instance2.setTimeInMillis(j2);
        return checkSameDay(instance, instance2);
    }

    public static boolean checkSameDay(Calendar calendar, Calendar calendar2) {
        return calendar.get(1) == calendar2.get(1) && calendar.get(2) == calendar2.get(2) && calendar.get(5) == calendar2.get(5);
    }

    public static int getSeconds(String str, String str2, String str3) {
        int i = 0;
        if (str3 != null && str3.length() > 0) {
            i = 0 + Integer.parseInt(str3);
        }
        if (str2 != null && str2.length() > 0) {
            i += Integer.parseInt(str2) * 60;
        }
        return (str == null || str.length() <= 0) ? i : i + (Integer.parseInt(str) * CacheConstants.HOUR);
    }

    public static long getMilliSecondsFromMidnight() {
        return System.currentTimeMillis() - getMidnightTimeStamp();
    }

    public static long getMidnightTimeStamp() {
        return getMidnightTimeStamp(System.currentTimeMillis());
    }

    public static long getMidnightTimeStamp(long j) {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(j);
        instance.set(11, 0);
        instance.set(12, 0);
        instance.set(13, 0);
        instance.set(14, 0);
        return instance.getTimeInMillis();
    }

    public static int getTimeZoneOffsetMillis() {
        return Calendar.getInstance().get(15);
    }

    public static String timestampToStr(long j, String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        Date date = new Date();
        date.setTime(j);
        return new SimpleDateFormat(str).format(date);
    }

    public static boolean isTomorrow(long j) {
        if (j <= 0) {
            return false;
        }
        long midnightTimeStamp = getMidnightTimeStamp();
        long millis = TimeUnit.HOURS.toMillis(24L);
        long j2 = j - (midnightTimeStamp + millis);
        return j2 > 0 && j2 < millis;
    }

    public static boolean isMidNight() {
        Calendar instance = Calendar.getInstance();
        return instance.get(11) == 0 && instance.get(12) == 0;
    }

    public static void setFlowPeakRange(int i, int i2) {
        if (i > i2) {
            L.util.e("setFlowPeakRange time is invalid");
            if (DebugHelper.isDebugVersion()) {
                ToastUtil.showToast("设置的时间无效");
                return;
            }
            return;
        }
        a = i;
        b = i2;
        if (DebugHelper.isDebugVersion()) {
            ToastUtil.showToast(String.format("已设置流量尖峰时间段 %s - %s", Integer.valueOf(i), Integer.valueOf(i2)));
        }
    }

    public static boolean isMorningPeak(long j) {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(j);
        int i = instance.get(11);
        return i >= a && i <= b;
    }

    public static boolean isMorningPeak(String str) {
        String[] split = str.split(Constants.COLON_SEPARATOR);
        if (split.length > 1) {
            try {
                int parseInt = Integer.parseInt(split[0]);
                if (parseInt >= a) {
                    if (parseInt <= b) {
                        return true;
                    }
                }
            } catch (NumberFormatException e) {
                L.alarm.e("TimeUtils isMorningPeak parse error", e);
            }
        }
        return false;
    }

    public static long getAheadOfCacheJobTime(long j, long j2) {
        long currentTimeMillis = System.currentTimeMillis();
        int i = (currentTimeMillis > j ? 1 : (currentTimeMillis == j ? 0 : -1));
        if (i < 0) {
            return (j - currentTimeMillis) - j2;
        }
        if (i == 0) {
            return -1L;
        }
        return ((getMidnightTimeStamp(AlarmModel.DAY_MILLISECONDS + currentTimeMillis) - currentTimeMillis) + (j - getMidnightTimeStamp(j))) - j2;
    }

    public static int getNowDiffDay(long j) {
        double currentTimeMillis = (j - System.currentTimeMillis()) / 8.64E7d;
        int ceil = (int) Math.ceil(currentTimeMillis);
        L.childContent.i("child vip dueTime %f", Double.valueOf(currentTimeMillis));
        return ceil;
    }

    public static String getMusicPlayTime(long j) {
        if (j < 1) {
            return "00:00";
        }
        long j2 = j / 1000;
        long j3 = j2 % 60;
        long j4 = j2 / 60;
        if (j4 > 60) {
            j4 %= 60;
        }
        return TimeCalculator.completedString(j4) + Constants.COLON_SEPARATOR + TimeCalculator.completedString(j3);
    }

    public static long transformTime(String str, String str2) {
        try {
            if (!TextUtils.isEmpty(str2)) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str2);
                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                return simpleDateFormat.parse(str).getTime();
            }
            throw new IllegalArgumentException("pattern is empty");
        } catch (ParseException e) {
            L.base.e("TimeUtils.transformTime error", e);
            return 0L;
        }
    }

    public static String transformTimestamp(long j, String str) {
        try {
            if (!TextUtils.isEmpty(str)) {
                return new SimpleDateFormat(str).format(new Date(j));
            }
            throw new IllegalArgumentException("pattern is empty");
        } catch (Exception e) {
            L.base.e("TimeUtils.transformTimestamp error", e);
            return "";
        }
    }

    public static boolean isMidnight() {
        Date date = new Date();
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        if (instance.get(11) != 0 || instance.get(12) != 0) {
            return false;
        }
        int i = instance.get(13);
        L.alarm.d("check midnight, second:%d", Integer.valueOf(i));
        return i <= 3;
    }

    public static int isHourly(long j) {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(j);
        int i = instance.get(11);
        int i2 = instance.get(12);
        int i3 = instance.get(13);
        if (i2 == 0 && i3 == 0) {
            return i;
        }
        return -1;
    }

    public static boolean hasExpired(int i) {
        long currentTimeMillis = System.currentTimeMillis();
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(currentTimeMillis);
        instance.set(11, i);
        instance.set(12, 0);
        instance.set(13, 0);
        instance.set(14, 0);
        return currentTimeMillis > instance.getTimeInMillis();
    }

    public static long getHourlyRandomTimeDuration(int i, int i2) {
        long currentTimeMillis = System.currentTimeMillis();
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(currentTimeMillis);
        int i3 = instance.get(11);
        int i4 = instance.get(12);
        int i5 = i3 > i ? (23 - i3) * 60 * 60 * 1000 : ((i - 1) - i3) * 60 * 60 * 1000;
        L.hourlychime.d("getHourlyRandomTimeDuration hourDistance: %d", Integer.valueOf(i5));
        L.hourlychime.d("getHourlyRandomTimeDuration hour: %d, hourOfDayC :%d， minuteC :%d", Integer.valueOf(i), Integer.valueOf(i3), Integer.valueOf(i4));
        if (i4 < 30) {
            long randomMillisInNextMin = RandomTimeUtils.getRandomMillisInNextMin(i2 - 1);
            L.hourlychime.d("getHourlyRandomTimeDuration < 30 delay: %d", Long.valueOf(randomMillisInNextMin));
            return currentTimeMillis + ((30 - i4) * 60 * 1000) + randomMillisInNextMin + i5;
        } else if (i4 < 59) {
            long randomMillisInNextMin2 = RandomTimeUtils.getRandomMillisInNextMin(59 - i4);
            L.hourlychime.d("getHourlyRandomTimeDuration > 30 delay: %d", Long.valueOf(randomMillisInNextMin2));
            return currentTimeMillis + randomMillisInNextMin2 + i5;
        } else {
            L.hourlychime.d("getHourlyRandomTimeDuration ==50 delay:0");
            return currentTimeMillis;
        }
    }

    public static long getHourlyDuration(int i) {
        long currentTimeMillis = System.currentTimeMillis();
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(currentTimeMillis);
        instance.set(11, i);
        instance.set(12, 0);
        instance.set(13, 0);
        instance.set(14, 0);
        long timeInMillis = instance.getTimeInMillis();
        L.hourlychime.d("getHourlyDuration hour: %d, hTime :%s, currentTime: %s ", Integer.valueOf(i), Long.valueOf(timeInMillis), Long.valueOf(currentTimeMillis));
        return timeInMillis;
    }
}
