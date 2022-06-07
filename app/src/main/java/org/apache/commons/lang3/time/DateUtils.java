package org.apache.commons.lang3.time;

import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/* loaded from: classes5.dex */
public class DateUtils {
    public static final long MILLIS_PER_DAY = 86400000;
    public static final long MILLIS_PER_HOUR = 3600000;
    public static final long MILLIS_PER_MINUTE = 60000;
    public static final long MILLIS_PER_SECOND = 1000;
    public static final int RANGE_MONTH_MONDAY = 6;
    public static final int RANGE_MONTH_SUNDAY = 5;
    public static final int RANGE_WEEK_CENTER = 4;
    public static final int RANGE_WEEK_MONDAY = 2;
    public static final int RANGE_WEEK_RELATIVE = 3;
    public static final int RANGE_WEEK_SUNDAY = 1;
    public static final int SEMI_MONTH = 1001;
    private static final int[][] a = {new int[]{14}, new int[]{13}, new int[]{12}, new int[]{11, 10}, new int[]{5, 5, 9}, new int[]{2, 1001}, new int[]{1}, new int[]{0}};

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public enum b {
        TRUNCATE,
        ROUND,
        CEILING
    }

    public static boolean isSameDay(Date date, Date date2) {
        if (date == null || date2 == null) {
            throw new IllegalArgumentException("The date must not be null");
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
        throw new IllegalArgumentException("The date must not be null");
    }

    public static boolean isSameInstant(Date date, Date date2) {
        if (date != null && date2 != null) {
            return date.getTime() == date2.getTime();
        }
        throw new IllegalArgumentException("The date must not be null");
    }

    public static boolean isSameInstant(Calendar calendar, Calendar calendar2) {
        if (calendar != null && calendar2 != null) {
            return calendar.getTime().getTime() == calendar2.getTime().getTime();
        }
        throw new IllegalArgumentException("The date must not be null");
    }

    public static boolean isSameLocalTime(Calendar calendar, Calendar calendar2) {
        if (calendar != null && calendar2 != null) {
            return calendar.get(14) == calendar2.get(14) && calendar.get(13) == calendar2.get(13) && calendar.get(12) == calendar2.get(12) && calendar.get(11) == calendar2.get(11) && calendar.get(6) == calendar2.get(6) && calendar.get(1) == calendar2.get(1) && calendar.get(0) == calendar2.get(0) && calendar.getClass() == calendar2.getClass();
        }
        throw new IllegalArgumentException("The date must not be null");
    }

    public static Date parseDate(String str, String... strArr) throws ParseException {
        return parseDate(str, null, strArr);
    }

    public static Date parseDate(String str, Locale locale, String... strArr) throws ParseException {
        return a(str, locale, strArr, true);
    }

    public static Date parseDateStrictly(String str, String... strArr) throws ParseException {
        return parseDateStrictly(str, null, strArr);
    }

    public static Date parseDateStrictly(String str, Locale locale, String... strArr) throws ParseException {
        return a(str, locale, strArr, false);
    }

    private static Date a(String str, Locale locale, String[] strArr, boolean z) throws ParseException {
        if (str == null || strArr == null) {
            throw new IllegalArgumentException("Date and Patterns must not be null");
        }
        TimeZone timeZone = TimeZone.getDefault();
        if (locale == null) {
            locale = Locale.getDefault();
        }
        ParsePosition parsePosition = new ParsePosition(0);
        Calendar instance = Calendar.getInstance(timeZone, locale);
        instance.setLenient(z);
        for (String str2 : strArr) {
            FastDateParser fastDateParser = new FastDateParser(str2, timeZone, locale);
            instance.clear();
            try {
                if (fastDateParser.parse(str, parsePosition, instance) && parsePosition.getIndex() == str.length()) {
                    return instance.getTime();
                }
            } catch (IllegalArgumentException unused) {
            }
            parsePosition.setIndex(0);
        }
        throw new ParseException("Unable to parse the date: " + str, -1);
    }

    public static Date addYears(Date date, int i) {
        return a(date, 1, i);
    }

    public static Date addMonths(Date date, int i) {
        return a(date, 2, i);
    }

    public static Date addWeeks(Date date, int i) {
        return a(date, 3, i);
    }

    public static Date addDays(Date date, int i) {
        return a(date, 5, i);
    }

    public static Date addHours(Date date, int i) {
        return a(date, 11, i);
    }

    public static Date addMinutes(Date date, int i) {
        return a(date, 12, i);
    }

    public static Date addSeconds(Date date, int i) {
        return a(date, 13, i);
    }

    public static Date addMilliseconds(Date date, int i) {
        return a(date, 14, i);
    }

    private static Date a(Date date, int i, int i2) {
        if (date != null) {
            Calendar instance = Calendar.getInstance();
            instance.setTime(date);
            instance.add(i, i2);
            return instance.getTime();
        }
        throw new IllegalArgumentException("The date must not be null");
    }

    public static Date setYears(Date date, int i) {
        return b(date, 1, i);
    }

    public static Date setMonths(Date date, int i) {
        return b(date, 2, i);
    }

    public static Date setDays(Date date, int i) {
        return b(date, 5, i);
    }

    public static Date setHours(Date date, int i) {
        return b(date, 11, i);
    }

    public static Date setMinutes(Date date, int i) {
        return b(date, 12, i);
    }

    public static Date setSeconds(Date date, int i) {
        return b(date, 13, i);
    }

    public static Date setMilliseconds(Date date, int i) {
        return b(date, 14, i);
    }

    private static Date b(Date date, int i, int i2) {
        if (date != null) {
            Calendar instance = Calendar.getInstance();
            instance.setLenient(false);
            instance.setTime(date);
            instance.set(i, i2);
            return instance.getTime();
        }
        throw new IllegalArgumentException("The date must not be null");
    }

    public static Calendar toCalendar(Date date) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        return instance;
    }

    public static Calendar toCalendar(Date date, TimeZone timeZone) {
        Calendar instance = Calendar.getInstance(timeZone);
        instance.setTime(date);
        return instance;
    }

    public static Date round(Date date, int i) {
        if (date != null) {
            Calendar instance = Calendar.getInstance();
            instance.setTime(date);
            a(instance, i, b.ROUND);
            return instance.getTime();
        }
        throw new IllegalArgumentException("The date must not be null");
    }

    public static Calendar round(Calendar calendar, int i) {
        if (calendar != null) {
            Calendar calendar2 = (Calendar) calendar.clone();
            a(calendar2, i, b.ROUND);
            return calendar2;
        }
        throw new IllegalArgumentException("The date must not be null");
    }

    public static Date round(Object obj, int i) {
        if (obj == null) {
            throw new IllegalArgumentException("The date must not be null");
        } else if (obj instanceof Date) {
            return round((Date) obj, i);
        } else {
            if (obj instanceof Calendar) {
                return round((Calendar) obj, i).getTime();
            }
            throw new ClassCastException("Could not round " + obj);
        }
    }

    public static Date truncate(Date date, int i) {
        if (date != null) {
            Calendar instance = Calendar.getInstance();
            instance.setTime(date);
            a(instance, i, b.TRUNCATE);
            return instance.getTime();
        }
        throw new IllegalArgumentException("The date must not be null");
    }

    public static Calendar truncate(Calendar calendar, int i) {
        if (calendar != null) {
            Calendar calendar2 = (Calendar) calendar.clone();
            a(calendar2, i, b.TRUNCATE);
            return calendar2;
        }
        throw new IllegalArgumentException("The date must not be null");
    }

    public static Date truncate(Object obj, int i) {
        if (obj == null) {
            throw new IllegalArgumentException("The date must not be null");
        } else if (obj instanceof Date) {
            return truncate((Date) obj, i);
        } else {
            if (obj instanceof Calendar) {
                return truncate((Calendar) obj, i).getTime();
            }
            throw new ClassCastException("Could not truncate " + obj);
        }
    }

    public static Date ceiling(Date date, int i) {
        if (date != null) {
            Calendar instance = Calendar.getInstance();
            instance.setTime(date);
            a(instance, i, b.CEILING);
            return instance.getTime();
        }
        throw new IllegalArgumentException("The date must not be null");
    }

    public static Calendar ceiling(Calendar calendar, int i) {
        if (calendar != null) {
            Calendar calendar2 = (Calendar) calendar.clone();
            a(calendar2, i, b.CEILING);
            return calendar2;
        }
        throw new IllegalArgumentException("The date must not be null");
    }

    public static Date ceiling(Object obj, int i) {
        if (obj == null) {
            throw new IllegalArgumentException("The date must not be null");
        } else if (obj instanceof Date) {
            return ceiling((Date) obj, i);
        } else {
            if (obj instanceof Calendar) {
                return ceiling((Calendar) obj, i).getTime();
            }
            throw new ClassCastException("Could not find ceiling of for type: " + obj.getClass());
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:105:0x0140 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0113  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0131  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0134  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void a(java.util.Calendar r16, int r17, org.apache.commons.lang3.time.DateUtils.b r18) {
        /*
            Method dump skipped, instructions count: 362
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.lang3.time.DateUtils.a(java.util.Calendar, int, org.apache.commons.lang3.time.DateUtils$b):void");
    }

    public static Iterator<Calendar> iterator(Date date, int i) {
        if (date != null) {
            Calendar instance = Calendar.getInstance();
            instance.setTime(date);
            return iterator(instance, i);
        }
        throw new IllegalArgumentException("The date must not be null");
    }

    public static Iterator<Calendar> iterator(Calendar calendar, int i) {
        int i2;
        Calendar calendar2;
        Calendar calendar3;
        if (calendar != null) {
            int i3 = 2;
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 4:
                    calendar3 = truncate(calendar, 5);
                    calendar2 = truncate(calendar, 5);
                    switch (i) {
                        case 1:
                        default:
                            i3 = 1;
                            i2 = 7;
                            break;
                        case 2:
                            i2 = 1;
                            break;
                        case 3:
                            i3 = calendar.get(7);
                            i2 = i3 - 1;
                            break;
                        case 4:
                            i3 = calendar.get(7) - 3;
                            i2 = calendar.get(7) + 3;
                            break;
                    }
                case 5:
                case 6:
                    Calendar truncate = truncate(calendar, 2);
                    Calendar calendar4 = (Calendar) truncate.clone();
                    calendar4.add(2, 1);
                    calendar4.add(5, -1);
                    if (i != 6) {
                        i3 = 1;
                        calendar2 = calendar4;
                        calendar3 = truncate;
                        i2 = 7;
                        break;
                    } else {
                        calendar2 = calendar4;
                        calendar3 = truncate;
                        i2 = 1;
                        break;
                    }
                default:
                    throw new IllegalArgumentException("The range style " + i + " is not valid.");
            }
            if (i3 < 1) {
                i3 += 7;
            }
            if (i3 > 7) {
                i3 -= 7;
            }
            if (i2 < 1) {
                i2 += 7;
            }
            if (i2 > 7) {
                i2 -= 7;
            }
            while (calendar3.get(7) != i3) {
                calendar3.add(5, -1);
            }
            while (calendar2.get(7) != i2) {
                calendar2.add(5, 1);
            }
            return new a(calendar3, calendar2);
        }
        throw new IllegalArgumentException("The date must not be null");
    }

    public static Iterator<?> iterator(Object obj, int i) {
        if (obj == null) {
            throw new IllegalArgumentException("The date must not be null");
        } else if (obj instanceof Date) {
            return iterator((Date) obj, i);
        } else {
            if (obj instanceof Calendar) {
                return iterator((Calendar) obj, i);
            }
            throw new ClassCastException("Could not iterate based on " + obj);
        }
    }

    public static long getFragmentInMilliseconds(Date date, int i) {
        return a(date, i, TimeUnit.MILLISECONDS);
    }

    public static long getFragmentInSeconds(Date date, int i) {
        return a(date, i, TimeUnit.SECONDS);
    }

    public static long getFragmentInMinutes(Date date, int i) {
        return a(date, i, TimeUnit.MINUTES);
    }

    public static long getFragmentInHours(Date date, int i) {
        return a(date, i, TimeUnit.HOURS);
    }

    public static long getFragmentInDays(Date date, int i) {
        return a(date, i, TimeUnit.DAYS);
    }

    public static long getFragmentInMilliseconds(Calendar calendar, int i) {
        return a(calendar, i, TimeUnit.MILLISECONDS);
    }

    public static long getFragmentInSeconds(Calendar calendar, int i) {
        return a(calendar, i, TimeUnit.SECONDS);
    }

    public static long getFragmentInMinutes(Calendar calendar, int i) {
        return a(calendar, i, TimeUnit.MINUTES);
    }

    public static long getFragmentInHours(Calendar calendar, int i) {
        return a(calendar, i, TimeUnit.HOURS);
    }

    public static long getFragmentInDays(Calendar calendar, int i) {
        return a(calendar, i, TimeUnit.DAYS);
    }

    private static long a(Date date, int i, TimeUnit timeUnit) {
        if (date != null) {
            Calendar instance = Calendar.getInstance();
            instance.setTime(date);
            return a(instance, i, timeUnit);
        }
        throw new IllegalArgumentException("The date must not be null");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static long a(Calendar calendar, int i, TimeUnit timeUnit) {
        if (calendar != null) {
            long j = 0;
            int i2 = timeUnit == TimeUnit.DAYS ? 0 : 1;
            switch (i) {
                case 1:
                    j = 0 + timeUnit.convert(calendar.get(6) - i2, TimeUnit.DAYS);
                    break;
                case 2:
                    j = 0 + timeUnit.convert(calendar.get(5) - i2, TimeUnit.DAYS);
                    break;
            }
            switch (i) {
                case 1:
                case 2:
                case 5:
                case 6:
                    j += timeUnit.convert(calendar.get(11), TimeUnit.HOURS);
                    j += timeUnit.convert(calendar.get(12), TimeUnit.MINUTES);
                    j += timeUnit.convert(calendar.get(13), TimeUnit.SECONDS);
                    break;
                case 3:
                case 4:
                case 7:
                case 8:
                case 9:
                case 10:
                default:
                    throw new IllegalArgumentException("The fragment " + i + " is not supported");
                case 11:
                    j += timeUnit.convert(calendar.get(12), TimeUnit.MINUTES);
                    j += timeUnit.convert(calendar.get(13), TimeUnit.SECONDS);
                    break;
                case 12:
                    j += timeUnit.convert(calendar.get(13), TimeUnit.SECONDS);
                    break;
                case 13:
                    break;
                case 14:
                    return j;
            }
            return j + timeUnit.convert(calendar.get(14), TimeUnit.MILLISECONDS);
        }
        throw new IllegalArgumentException("The date must not be null");
    }

    public static boolean truncatedEquals(Calendar calendar, Calendar calendar2, int i) {
        return truncatedCompareTo(calendar, calendar2, i) == 0;
    }

    public static boolean truncatedEquals(Date date, Date date2, int i) {
        return truncatedCompareTo(date, date2, i) == 0;
    }

    public static int truncatedCompareTo(Calendar calendar, Calendar calendar2, int i) {
        return truncate(calendar, i).compareTo(truncate(calendar2, i));
    }

    public static int truncatedCompareTo(Date date, Date date2, int i) {
        return truncate(date, i).compareTo(truncate(date2, i));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static class a implements Iterator<Calendar> {
        private final Calendar a;
        private final Calendar b;

        a(Calendar calendar, Calendar calendar2) {
            this.a = calendar2;
            this.b = calendar;
            this.b.add(5, -1);
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.b.before(this.a);
        }

        /* renamed from: a */
        public Calendar next() {
            if (!this.b.equals(this.a)) {
                this.b.add(5, 1);
                return (Calendar) this.b.clone();
            }
            throw new NoSuchElementException();
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
