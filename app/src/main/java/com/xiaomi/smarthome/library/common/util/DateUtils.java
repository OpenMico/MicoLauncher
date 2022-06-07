package com.xiaomi.smarthome.library.common.util;

import java.util.Calendar;
import java.util.Date;

/* loaded from: classes4.dex */
public class DateUtils {
    public static Date MAX_DATE = new Date(Long.MAX_VALUE);
    public static final long MILLISECONDS_DAY = 86400000;
    public static final long MILLISECONDS_HOUR = 3600000;
    public static final long MILLISECONDS_MINUTE = 60000;
    public static final long MILLISECONDS_WEEK = 604800000;

    public static boolean isSameDay(Date date, Date date2) {
        if (date == null || date2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        Calendar instance2 = Calendar.getInstance();
        instance2.setTime(date2);
        return isSameDay(instance, instance2);
    }

    public static boolean isSameDay(Calendar calendar, Calendar calendar2) {
        if (calendar != null && calendar2 != null) {
            return calendar.get(0) == calendar2.get(0) && calendar.get(1) == calendar2.get(1) && calendar.get(6) == calendar2.get(6);
        }
        throw new IllegalArgumentException("The dates must not be null");
    }

    public static boolean isSameYear(Date date, Date date2) {
        if (date == null || date2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        Calendar instance2 = Calendar.getInstance();
        instance2.setTime(date2);
        return instance.get(0) == instance2.get(0) && instance.get(1) == instance2.get(1);
    }

    public static boolean isSameMonth(Date date, Date date2) {
        if (date == null || date2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        Calendar instance2 = Calendar.getInstance();
        instance2.setTime(date2);
        return instance.get(0) == instance2.get(0) && instance.get(1) == instance2.get(1) && instance.get(2) == instance2.get(2);
    }

    public static boolean isSameWeek(Date date, Date date2) {
        if (date == null || date2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        Calendar instance2 = Calendar.getInstance();
        instance2.setTime(date2);
        return instance.get(0) == instance2.get(0) && instance.get(1) == instance2.get(1) && instance.get(3) == instance2.get(3);
    }

    public static boolean isToday(Date date) {
        return isSameDay(date, Calendar.getInstance().getTime());
    }

    public static boolean isToday(Calendar calendar) {
        return isSameDay(calendar, Calendar.getInstance());
    }

    public static boolean isLastDay(Date date, Date date2) {
        if (date == null || date2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        Calendar instance2 = Calendar.getInstance();
        instance2.setTime(date2);
        return instance2.get(6) - instance.get(6) == 1;
    }

    public static boolean isBeforeDay(Date date, Date date2) {
        if (date == null || date2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        Calendar instance2 = Calendar.getInstance();
        instance2.setTime(date2);
        return isBeforeDay(instance, instance2);
    }

    public static boolean isBeforeDay(Calendar calendar, Calendar calendar2) {
        if (calendar == null || calendar2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        } else if (calendar.get(0) < calendar2.get(0)) {
            return true;
        } else {
            if (calendar.get(0) > calendar2.get(0)) {
                return false;
            }
            if (calendar.get(1) < calendar2.get(1)) {
                return true;
            }
            return calendar.get(1) <= calendar2.get(1) && calendar.get(6) < calendar2.get(6);
        }
    }

    public static boolean isAfterDay(Date date, Date date2) {
        if (date == null || date2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        Calendar instance2 = Calendar.getInstance();
        instance2.setTime(date2);
        return isAfterDay(instance, instance2);
    }

    public static boolean isAfterDay(Calendar calendar, Calendar calendar2) {
        if (calendar == null || calendar2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        } else if (calendar.get(0) < calendar2.get(0)) {
            return false;
        } else {
            if (calendar.get(0) > calendar2.get(0)) {
                return true;
            }
            if (calendar.get(1) < calendar2.get(1)) {
                return false;
            }
            return calendar.get(1) > calendar2.get(1) || calendar.get(6) > calendar2.get(6);
        }
    }

    public static boolean isWithinDaysFuture(Date date, int i) {
        if (date != null) {
            Calendar instance = Calendar.getInstance();
            instance.setTime(date);
            return isWithinDaysFuture(instance, i);
        }
        throw new IllegalArgumentException("The date must not be null");
    }

    public static boolean isWithinDaysFuture(Calendar calendar, int i) {
        if (calendar != null) {
            Calendar instance = Calendar.getInstance();
            Calendar instance2 = Calendar.getInstance();
            instance2.add(6, i);
            return isAfterDay(calendar, instance) && !isAfterDay(calendar, instance2);
        }
        throw new IllegalArgumentException("The date must not be null");
    }

    public static boolean isWithinDaysPast(Date date, int i) {
        if (date != null) {
            Calendar instance = Calendar.getInstance();
            instance.setTime(date);
            return isWithinDaysPast(instance, i);
        }
        throw new IllegalArgumentException("The date must not be null");
    }

    public static boolean isWithinDaysPast(Calendar calendar, int i) {
        if (calendar != null) {
            Calendar instance = Calendar.getInstance();
            Calendar instance2 = Calendar.getInstance();
            instance2.add(6, -i);
            return isBeforeDay(calendar, instance) && !isBeforeDay(calendar, instance2);
        }
        throw new IllegalArgumentException("The date must not be null");
    }

    public static Date getStart(Date date) {
        return clearTime(date);
    }

    public static Date clearTime(Date date) {
        if (date == null) {
            return null;
        }
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.set(11, 0);
        instance.set(12, 0);
        instance.set(13, 0);
        instance.set(14, 0);
        return instance.getTime();
    }

    public static Date clearMinutes(Date date) {
        if (date == null) {
            return null;
        }
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.set(12, 0);
        instance.set(13, 0);
        instance.set(14, 0);
        return instance.getTime();
    }

    public static boolean hasTime(Date date) {
        if (date == null) {
            return false;
        }
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        return instance.get(11) > 0 || instance.get(12) > 0 || instance.get(13) > 0 || instance.get(14) > 0;
    }

    public static Date getEnd(Date date) {
        if (date == null) {
            return null;
        }
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.set(11, 23);
        instance.set(12, 59);
        instance.set(13, 59);
        instance.set(14, 999);
        return instance.getTime();
    }

    public static Date max(Date date, Date date2) {
        if (date == null && date2 == null) {
            return null;
        }
        return date == null ? date2 : (date2 != null && !date.after(date2)) ? date2 : date;
    }

    public static Date min(Date date, Date date2) {
        if (date == null && date2 == null) {
            return null;
        }
        return date == null ? date2 : (date2 != null && !date.before(date2)) ? date2 : date;
    }

    public static int getIntervalDays(Date date, Date date2) {
        int i;
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        Calendar instance2 = Calendar.getInstance();
        instance2.setTime(date2);
        int i2 = instance.get(6);
        int i3 = instance2.get(6);
        boolean z = true;
        int i4 = instance.get(1);
        int i5 = instance2.get(1);
        if (i4 == i5) {
            return i3 - i2;
        }
        z = false;
        if (i4 > i5) {
            i = i2 - i3;
        } else {
            i = i3 - i2;
            i4 = i5;
            i5 = i4;
        }
        while (i5 < i4) {
            i = ((i5 % 4 != 0 || i5 % 100 == 0) && i5 % 400 != 0) ? i + 365 : i + 366;
            i5++;
        }
        return z ? -i : i;
    }
}
