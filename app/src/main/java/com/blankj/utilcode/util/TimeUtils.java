package com.blankj.utilcode.util;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import com.xiaomi.mipush.sdk.Constants;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes.dex */
public final class TimeUtils {
    private static final ThreadLocal<Map<String, SimpleDateFormat>> a = new ThreadLocal<Map<String, SimpleDateFormat>>() { // from class: com.blankj.utilcode.util.TimeUtils.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public Map<String, SimpleDateFormat> initialValue() {
            return new HashMap();
        }
    };
    private static final String[] b = {"猴", "鸡", "狗", "猪", "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊"};
    private static final int[] c = {20, 19, 21, 21, 21, 22, 23, 23, 23, 24, 23, 22};
    private static final String[] d = {"水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座"};

    private static long b(long j, int i) {
        return j * i;
    }

    private static SimpleDateFormat a() {
        return getSafeDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @SuppressLint({"SimpleDateFormat"})
    public static SimpleDateFormat getSafeDateFormat(String str) {
        Map<String, SimpleDateFormat> map = a.get();
        SimpleDateFormat simpleDateFormat = map.get(str);
        if (simpleDateFormat != null) {
            return simpleDateFormat;
        }
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(str);
        map.put(str, simpleDateFormat2);
        return simpleDateFormat2;
    }

    private TimeUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static String millis2String(long j) {
        return millis2String(j, a());
    }

    public static String millis2String(long j, @NonNull String str) {
        if (str != null) {
            return millis2String(j, getSafeDateFormat(str));
        }
        throw new NullPointerException("Argument 'pattern' of type String (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static String millis2String(long j, @NonNull DateFormat dateFormat) {
        if (dateFormat != null) {
            return dateFormat.format(new Date(j));
        }
        throw new NullPointerException("Argument 'format' of type DateFormat (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static long string2Millis(String str) {
        return string2Millis(str, a());
    }

    public static long string2Millis(String str, @NonNull String str2) {
        if (str2 != null) {
            return string2Millis(str, getSafeDateFormat(str2));
        }
        throw new NullPointerException("Argument 'pattern' of type String (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static long string2Millis(String str, @NonNull DateFormat dateFormat) {
        if (dateFormat != null) {
            try {
                return dateFormat.parse(str).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
                return -1L;
            }
        } else {
            throw new NullPointerException("Argument 'format' of type DateFormat (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static Date string2Date(String str) {
        return string2Date(str, a());
    }

    public static Date string2Date(String str, @NonNull String str2) {
        if (str2 != null) {
            return string2Date(str, getSafeDateFormat(str2));
        }
        throw new NullPointerException("Argument 'pattern' of type String (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static Date string2Date(String str, @NonNull DateFormat dateFormat) {
        if (dateFormat != null) {
            try {
                return dateFormat.parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            throw new NullPointerException("Argument 'format' of type DateFormat (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static String date2String(Date date) {
        return date2String(date, a());
    }

    public static String date2String(Date date, @NonNull String str) {
        if (str != null) {
            return getSafeDateFormat(str).format(date);
        }
        throw new NullPointerException("Argument 'pattern' of type String (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static String date2String(Date date, @NonNull DateFormat dateFormat) {
        if (dateFormat != null) {
            return dateFormat.format(date);
        }
        throw new NullPointerException("Argument 'format' of type DateFormat (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static long date2Millis(Date date) {
        return date.getTime();
    }

    public static Date millis2Date(long j) {
        return new Date(j);
    }

    public static long getTimeSpan(String str, String str2, int i) {
        return getTimeSpan(str, str2, a(), i);
    }

    public static long getTimeSpan(String str, String str2, @NonNull DateFormat dateFormat, int i) {
        if (dateFormat != null) {
            return c(string2Millis(str, dateFormat) - string2Millis(str2, dateFormat), i);
        }
        throw new NullPointerException("Argument 'format' of type DateFormat (#2 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static long getTimeSpan(Date date, Date date2, int i) {
        return c(date2Millis(date) - date2Millis(date2), i);
    }

    public static long getTimeSpan(long j, long j2, int i) {
        return c(j - j2, i);
    }

    public static String getFitTimeSpan(String str, String str2, int i) {
        return a(string2Millis(str, a()) - string2Millis(str2, a()), i);
    }

    public static String getFitTimeSpan(String str, String str2, @NonNull DateFormat dateFormat, int i) {
        if (dateFormat != null) {
            return a(string2Millis(str, dateFormat) - string2Millis(str2, dateFormat), i);
        }
        throw new NullPointerException("Argument 'format' of type DateFormat (#2 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static String getFitTimeSpan(Date date, Date date2, int i) {
        return a(date2Millis(date) - date2Millis(date2), i);
    }

    public static String getFitTimeSpan(long j, long j2, int i) {
        return a(j - j2, i);
    }

    public static long getNowMills() {
        return System.currentTimeMillis();
    }

    public static String getNowString() {
        return millis2String(System.currentTimeMillis(), a());
    }

    public static String getNowString(@NonNull DateFormat dateFormat) {
        if (dateFormat != null) {
            return millis2String(System.currentTimeMillis(), dateFormat);
        }
        throw new NullPointerException("Argument 'format' of type DateFormat (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static Date getNowDate() {
        return new Date();
    }

    public static long getTimeSpanByNow(String str, int i) {
        return getTimeSpan(str, getNowString(), a(), i);
    }

    public static long getTimeSpanByNow(String str, @NonNull DateFormat dateFormat, int i) {
        if (dateFormat != null) {
            return getTimeSpan(str, getNowString(dateFormat), dateFormat, i);
        }
        throw new NullPointerException("Argument 'format' of type DateFormat (#1 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static long getTimeSpanByNow(Date date, int i) {
        return getTimeSpan(date, new Date(), i);
    }

    public static long getTimeSpanByNow(long j, int i) {
        return getTimeSpan(j, System.currentTimeMillis(), i);
    }

    public static String getFitTimeSpanByNow(String str, int i) {
        return getFitTimeSpan(str, getNowString(), a(), i);
    }

    public static String getFitTimeSpanByNow(String str, @NonNull DateFormat dateFormat, int i) {
        if (dateFormat != null) {
            return getFitTimeSpan(str, getNowString(dateFormat), dateFormat, i);
        }
        throw new NullPointerException("Argument 'format' of type DateFormat (#1 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static String getFitTimeSpanByNow(Date date, int i) {
        return getFitTimeSpan(date, getNowDate(), i);
    }

    public static String getFitTimeSpanByNow(long j, int i) {
        return getFitTimeSpan(j, System.currentTimeMillis(), i);
    }

    public static String getFriendlyTimeSpanByNow(String str) {
        return getFriendlyTimeSpanByNow(str, a());
    }

    public static String getFriendlyTimeSpanByNow(String str, @NonNull DateFormat dateFormat) {
        if (dateFormat != null) {
            return getFriendlyTimeSpanByNow(string2Millis(str, dateFormat));
        }
        throw new NullPointerException("Argument 'format' of type DateFormat (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static String getFriendlyTimeSpanByNow(Date date) {
        return getFriendlyTimeSpanByNow(date.getTime());
    }

    public static String getFriendlyTimeSpanByNow(long j) {
        long currentTimeMillis = System.currentTimeMillis() - j;
        if (currentTimeMillis < 0) {
            return String.format("%tc", Long.valueOf(j));
        }
        if (currentTimeMillis < 1000) {
            return "刚刚";
        }
        if (currentTimeMillis < 60000) {
            return String.format(Locale.getDefault(), "%d秒前", Long.valueOf(currentTimeMillis / 1000));
        }
        if (currentTimeMillis < 3600000) {
            return String.format(Locale.getDefault(), "%d分钟前", Long.valueOf(currentTimeMillis / 60000));
        }
        long b2 = b();
        return j >= b2 ? String.format("今天%tR", Long.valueOf(j)) : j >= b2 - 86400000 ? String.format("昨天%tR", Long.valueOf(j)) : String.format("%tF", Long.valueOf(j));
    }

    private static long b() {
        Calendar instance = Calendar.getInstance();
        instance.set(11, 0);
        instance.set(13, 0);
        instance.set(12, 0);
        instance.set(14, 0);
        return instance.getTimeInMillis();
    }

    public static long getMillis(long j, long j2, int i) {
        return j + b(j2, i);
    }

    public static long getMillis(String str, long j, int i) {
        return getMillis(str, a(), j, i);
    }

    public static long getMillis(String str, @NonNull DateFormat dateFormat, long j, int i) {
        if (dateFormat != null) {
            return string2Millis(str, dateFormat) + b(j, i);
        }
        throw new NullPointerException("Argument 'format' of type DateFormat (#1 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static long getMillis(Date date, long j, int i) {
        return date2Millis(date) + b(j, i);
    }

    public static String getString(long j, long j2, int i) {
        return getString(j, a(), j2, i);
    }

    public static String getString(long j, @NonNull DateFormat dateFormat, long j2, int i) {
        if (dateFormat != null) {
            return millis2String(j + b(j2, i), dateFormat);
        }
        throw new NullPointerException("Argument 'format' of type DateFormat (#1 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static String getString(String str, long j, int i) {
        return getString(str, a(), j, i);
    }

    public static String getString(String str, @NonNull DateFormat dateFormat, long j, int i) {
        if (dateFormat != null) {
            return millis2String(string2Millis(str, dateFormat) + b(j, i), dateFormat);
        }
        throw new NullPointerException("Argument 'format' of type DateFormat (#1 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static String getString(Date date, long j, int i) {
        return getString(date, a(), j, i);
    }

    public static String getString(Date date, @NonNull DateFormat dateFormat, long j, int i) {
        if (dateFormat != null) {
            return millis2String(date2Millis(date) + b(j, i), dateFormat);
        }
        throw new NullPointerException("Argument 'format' of type DateFormat (#1 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static Date getDate(long j, long j2, int i) {
        return millis2Date(j + b(j2, i));
    }

    public static Date getDate(String str, long j, int i) {
        return getDate(str, a(), j, i);
    }

    public static Date getDate(String str, @NonNull DateFormat dateFormat, long j, int i) {
        if (dateFormat != null) {
            return millis2Date(string2Millis(str, dateFormat) + b(j, i));
        }
        throw new NullPointerException("Argument 'format' of type DateFormat (#1 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static Date getDate(Date date, long j, int i) {
        return millis2Date(date2Millis(date) + b(j, i));
    }

    public static long getMillisByNow(long j, int i) {
        return getMillis(getNowMills(), j, i);
    }

    public static String getStringByNow(long j, int i) {
        return getStringByNow(j, a(), i);
    }

    public static String getStringByNow(long j, @NonNull DateFormat dateFormat, int i) {
        if (dateFormat != null) {
            return getString(getNowMills(), dateFormat, j, i);
        }
        throw new NullPointerException("Argument 'format' of type DateFormat (#1 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static Date getDateByNow(long j, int i) {
        return getDate(getNowMills(), j, i);
    }

    public static boolean isToday(String str) {
        return isToday(string2Millis(str, a()));
    }

    public static boolean isToday(String str, @NonNull DateFormat dateFormat) {
        if (dateFormat != null) {
            return isToday(string2Millis(str, dateFormat));
        }
        throw new NullPointerException("Argument 'format' of type DateFormat (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static boolean isToday(Date date) {
        return isToday(date.getTime());
    }

    public static boolean isToday(long j) {
        long b2 = b();
        return j >= b2 && j < b2 + 86400000;
    }

    public static boolean isLeapYear(String str) {
        return isLeapYear(string2Date(str, a()));
    }

    public static boolean isLeapYear(String str, @NonNull DateFormat dateFormat) {
        if (dateFormat != null) {
            return isLeapYear(string2Date(str, dateFormat));
        }
        throw new NullPointerException("Argument 'format' of type DateFormat (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static boolean isLeapYear(Date date) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        return isLeapYear(instance.get(1));
    }

    public static boolean isLeapYear(long j) {
        return isLeapYear(millis2Date(j));
    }

    public static boolean isLeapYear(int i) {
        return (i % 4 == 0 && i % 100 != 0) || i % 400 == 0;
    }

    public static String getChineseWeek(String str) {
        return getChineseWeek(string2Date(str, a()));
    }

    public static String getChineseWeek(String str, @NonNull DateFormat dateFormat) {
        if (dateFormat != null) {
            return getChineseWeek(string2Date(str, dateFormat));
        }
        throw new NullPointerException("Argument 'format' of type DateFormat (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static String getChineseWeek(Date date) {
        return new SimpleDateFormat("E", Locale.CHINA).format(date);
    }

    public static String getChineseWeek(long j) {
        return getChineseWeek(new Date(j));
    }

    public static String getUSWeek(String str) {
        return getUSWeek(string2Date(str, a()));
    }

    public static String getUSWeek(String str, @NonNull DateFormat dateFormat) {
        if (dateFormat != null) {
            return getUSWeek(string2Date(str, dateFormat));
        }
        throw new NullPointerException("Argument 'format' of type DateFormat (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static String getUSWeek(Date date) {
        return new SimpleDateFormat("EEEE", Locale.US).format(date);
    }

    public static String getUSWeek(long j) {
        return getUSWeek(new Date(j));
    }

    public static boolean isAm() {
        return Calendar.getInstance().get(9) == 0;
    }

    public static boolean isAm(String str) {
        return getValueByCalendarField(str, a(), 9) == 0;
    }

    public static boolean isAm(String str, @NonNull DateFormat dateFormat) {
        if (dateFormat != null) {
            return getValueByCalendarField(str, dateFormat, 9) == 0;
        }
        throw new NullPointerException("Argument 'format' of type DateFormat (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static boolean isAm(Date date) {
        return getValueByCalendarField(date, 9) == 0;
    }

    public static boolean isAm(long j) {
        return getValueByCalendarField(j, 9) == 0;
    }

    public static boolean isPm() {
        return !isAm();
    }

    public static boolean isPm(String str) {
        return !isAm(str);
    }

    public static boolean isPm(String str, @NonNull DateFormat dateFormat) {
        if (dateFormat != null) {
            return !isAm(str, dateFormat);
        }
        throw new NullPointerException("Argument 'format' of type DateFormat (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static boolean isPm(Date date) {
        return !isAm(date);
    }

    public static boolean isPm(long j) {
        return !isAm(j);
    }

    public static int getValueByCalendarField(int i) {
        return Calendar.getInstance().get(i);
    }

    public static int getValueByCalendarField(String str, int i) {
        return getValueByCalendarField(string2Date(str, a()), i);
    }

    public static int getValueByCalendarField(String str, @NonNull DateFormat dateFormat, int i) {
        if (dateFormat != null) {
            return getValueByCalendarField(string2Date(str, dateFormat), i);
        }
        throw new NullPointerException("Argument 'format' of type DateFormat (#1 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static int getValueByCalendarField(Date date, int i) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        return instance.get(i);
    }

    public static int getValueByCalendarField(long j, int i) {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(j);
        return instance.get(i);
    }

    public static String getChineseZodiac(String str) {
        return getChineseZodiac(string2Date(str, a()));
    }

    public static String getChineseZodiac(String str, @NonNull DateFormat dateFormat) {
        if (dateFormat != null) {
            return getChineseZodiac(string2Date(str, dateFormat));
        }
        throw new NullPointerException("Argument 'format' of type DateFormat (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static String getChineseZodiac(Date date) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        return b[instance.get(1) % 12];
    }

    public static String getChineseZodiac(long j) {
        return getChineseZodiac(millis2Date(j));
    }

    public static String getChineseZodiac(int i) {
        return b[i % 12];
    }

    public static String getZodiac(String str) {
        return getZodiac(string2Date(str, a()));
    }

    public static String getZodiac(String str, @NonNull DateFormat dateFormat) {
        if (dateFormat != null) {
            return getZodiac(string2Date(str, dateFormat));
        }
        throw new NullPointerException("Argument 'format' of type DateFormat (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static String getZodiac(Date date) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        return getZodiac(instance.get(2) + 1, instance.get(5));
    }

    public static String getZodiac(long j) {
        return getZodiac(millis2Date(j));
    }

    public static String getZodiac(int i, int i2) {
        String[] strArr = d;
        int i3 = i - 1;
        if (i2 < c[i3]) {
            i3 = (i + 10) % 12;
        }
        return strArr[i3];
    }

    private static long c(long j, int i) {
        return j / i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String a(long j, int i) {
        if (i <= 0) {
            return null;
        }
        int min = Math.min(i, 5);
        String[] strArr = {"天", "小时", "分钟", "秒", "毫秒"};
        int i2 = (j > 0L ? 1 : (j == 0L ? 0 : -1));
        if (i2 == 0) {
            return 0 + strArr[min - 1];
        }
        StringBuilder sb = new StringBuilder();
        if (i2 < 0) {
            sb.append(Constants.ACCEPT_TIME_SEPARATOR_SERVER);
            j = -j;
        }
        int[] iArr = {86400000, 3600000, 60000, 1000, 1};
        for (int i3 = 0; i3 < min; i3++) {
            if (j >= iArr[i3]) {
                long j2 = j / iArr[i3];
                j -= iArr[i3] * j2;
                sb.append(j2);
                sb.append(strArr[i3]);
            }
        }
        return sb.toString();
    }
}
