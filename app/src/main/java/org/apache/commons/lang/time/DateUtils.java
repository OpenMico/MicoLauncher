package org.apache.commons.lang.time;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.TimeZone;
import org.apache.commons.lang.StringUtils;

/* loaded from: classes5.dex */
public class DateUtils {
    public static final int MILLIS_IN_DAY = 86400000;
    public static final int MILLIS_IN_HOUR = 3600000;
    public static final int MILLIS_IN_MINUTE = 60000;
    public static final int MILLIS_IN_SECOND = 1000;
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
    public static final TimeZone UTC_TIME_ZONE = TimeZone.getTimeZone("GMT");
    private static final int[][] a = {new int[]{14}, new int[]{13}, new int[]{12}, new int[]{11, 10}, new int[]{5, 5, 9}, new int[]{2, 1001}, new int[]{1}, new int[]{0}};

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
            return calendar.get(14) == calendar2.get(14) && calendar.get(13) == calendar2.get(13) && calendar.get(12) == calendar2.get(12) && calendar.get(10) == calendar2.get(10) && calendar.get(6) == calendar2.get(6) && calendar.get(1) == calendar2.get(1) && calendar.get(0) == calendar2.get(0) && calendar.getClass() == calendar2.getClass();
        }
        throw new IllegalArgumentException("The date must not be null");
    }

    public static Date parseDate(String str, String[] strArr) throws ParseException {
        return a(str, strArr, true);
    }

    public static Date parseDateStrictly(String str, String[] strArr) throws ParseException {
        return a(str, strArr, false);
    }

    private static Date a(String str, String[] strArr, boolean z) throws ParseException {
        String str2;
        if (str == null || strArr == null) {
            throw new IllegalArgumentException("Date and Patterns must not be null");
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.setLenient(z);
        ParsePosition parsePosition = new ParsePosition(0);
        for (int i = 0; i < strArr.length; i++) {
            String str3 = strArr[i];
            if (strArr[i].endsWith("ZZ")) {
                str3 = str3.substring(0, str3.length() - 1);
            }
            simpleDateFormat.applyPattern(str3);
            parsePosition.setIndex(0);
            if (strArr[i].endsWith("ZZ")) {
                int a2 = a(str, 0);
                str2 = str;
                while (a2 >= 0) {
                    str2 = b(str2, a2);
                    a2 = a(str2, a2 + 1);
                }
            } else {
                str2 = str;
            }
            Date parse = simpleDateFormat.parse(str2, parsePosition);
            if (parse != null && parsePosition.getIndex() == str2.length()) {
                return parse;
            }
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Unable to parse the date: ");
        stringBuffer.append(str);
        throw new ParseException(stringBuffer.toString(), -1);
    }

    private static int a(String str, int i) {
        int indexOf = StringUtils.indexOf(str, '+', i);
        return indexOf < 0 ? StringUtils.indexOf(str, '-', i) : indexOf;
    }

    private static String b(String str, int i) {
        int i2;
        if (i < 0 || (i2 = i + 5) >= str.length() || !Character.isDigit(str.charAt(i + 1)) || !Character.isDigit(str.charAt(i + 2))) {
            return str;
        }
        int i3 = i + 3;
        if (str.charAt(i3) != ':') {
            return str;
        }
        int i4 = i + 4;
        if (!Character.isDigit(str.charAt(i4)) || !Character.isDigit(str.charAt(i2))) {
            return str;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str.substring(0, i3));
        stringBuffer.append(str.substring(i4));
        return stringBuffer.toString();
    }

    public static Date addYears(Date date, int i) {
        return add(date, 1, i);
    }

    public static Date addMonths(Date date, int i) {
        return add(date, 2, i);
    }

    public static Date addWeeks(Date date, int i) {
        return add(date, 3, i);
    }

    public static Date addDays(Date date, int i) {
        return add(date, 5, i);
    }

    public static Date addHours(Date date, int i) {
        return add(date, 11, i);
    }

    public static Date addMinutes(Date date, int i) {
        return add(date, 12, i);
    }

    public static Date addSeconds(Date date, int i) {
        return add(date, 13, i);
    }

    public static Date addMilliseconds(Date date, int i) {
        return add(date, 14, i);
    }

    public static Date add(Date date, int i, int i2) {
        if (date != null) {
            Calendar instance = Calendar.getInstance();
            instance.setTime(date);
            instance.add(i, i2);
            return instance.getTime();
        }
        throw new IllegalArgumentException("The date must not be null");
    }

    public static Date setYears(Date date, int i) {
        return a(date, 1, i);
    }

    public static Date setMonths(Date date, int i) {
        return a(date, 2, i);
    }

    public static Date setDays(Date date, int i) {
        return a(date, 5, i);
    }

    public static Date setHours(Date date, int i) {
        return a(date, 11, i);
    }

    public static Date setMinutes(Date date, int i) {
        return a(date, 12, i);
    }

    public static Date setSeconds(Date date, int i) {
        return a(date, 13, i);
    }

    public static Date setMilliseconds(Date date, int i) {
        return a(date, 14, i);
    }

    private static Date a(Date date, int i, int i2) {
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

    public static Date round(Date date, int i) {
        if (date != null) {
            Calendar instance = Calendar.getInstance();
            instance.setTime(date);
            a(instance, i, 1);
            return instance.getTime();
        }
        throw new IllegalArgumentException("The date must not be null");
    }

    public static Calendar round(Calendar calendar, int i) {
        if (calendar != null) {
            Calendar calendar2 = (Calendar) calendar.clone();
            a(calendar2, i, 1);
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
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Could not round ");
            stringBuffer.append(obj);
            throw new ClassCastException(stringBuffer.toString());
        }
    }

    public static Date truncate(Date date, int i) {
        if (date != null) {
            Calendar instance = Calendar.getInstance();
            instance.setTime(date);
            a(instance, i, 0);
            return instance.getTime();
        }
        throw new IllegalArgumentException("The date must not be null");
    }

    public static Calendar truncate(Calendar calendar, int i) {
        if (calendar != null) {
            Calendar calendar2 = (Calendar) calendar.clone();
            a(calendar2, i, 0);
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
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Could not truncate ");
            stringBuffer.append(obj);
            throw new ClassCastException(stringBuffer.toString());
        }
    }

    public static Date ceiling(Date date, int i) {
        if (date != null) {
            Calendar instance = Calendar.getInstance();
            instance.setTime(date);
            a(instance, i, 2);
            return instance.getTime();
        }
        throw new IllegalArgumentException("The date must not be null");
    }

    public static Calendar ceiling(Calendar calendar, int i) {
        if (calendar != null) {
            Calendar calendar2 = (Calendar) calendar.clone();
            a(calendar2, i, 2);
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
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Could not find ceiling of for type: ");
            stringBuffer.append(obj.getClass());
            throw new ClassCastException(stringBuffer.toString());
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:56:0x00c1, code lost:
        if (r17 == 9) goto L_0x00e2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x00c3, code lost:
        if (r17 == 1001) goto L_0x00c6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x00cb, code lost:
        if (r9[r5][0] != 5) goto L_0x00fd;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x00cd, code lost:
        r6 = r16.get(5) - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x00d2, code lost:
        if (r6 < 15) goto L_0x00d8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x00d4, code lost:
        r8 = r6 - 15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x00d8, code lost:
        r8 = r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x00da, code lost:
        if (r8 <= 7) goto L_0x00de;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x00dc, code lost:
        r6 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x00de, code lost:
        r6 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x00df, code lost:
        r6 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x00e7, code lost:
        if (r9[r5][0] != 11) goto L_0x00fd;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x00e9, code lost:
        r6 = r16.get(11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x00ed, code lost:
        if (r6 < 12) goto L_0x00f3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x00ef, code lost:
        r8 = r6 - 12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x00f3, code lost:
        r8 = r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x00f5, code lost:
        if (r8 < 6) goto L_0x00f9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x00f7, code lost:
        r6 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x00f9, code lost:
        r6 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x00fa, code lost:
        r6 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x00fd, code lost:
        r7 = r6;
        r6 = false;
        r8 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x0100, code lost:
        if (r6 != false) goto L_0x012a;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x0102, code lost:
        r6 = r16.getActualMinimum(org.apache.commons.lang.time.DateUtils.a[r5][0]);
        r8 = r16.getActualMaximum(org.apache.commons.lang.time.DateUtils.a[r5][0]);
        r7 = r16.get(org.apache.commons.lang.time.DateUtils.a[r5][0]) - r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x0124, code lost:
        if (r7 <= ((r8 - r6) / 2)) goto L_0x0128;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x0126, code lost:
        r6 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x0128, code lost:
        r6 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x012c, code lost:
        if (r7 == 0) goto L_0x0142;
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x012e, code lost:
        r8 = org.apache.commons.lang.time.DateUtils.a;
        r16.set(r8[r5][0], r16.get(r8[r5][0]) - r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x0143, code lost:
        r5 = r5 + 1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void a(java.util.Calendar r16, int r17, int r18) {
        /*
            Method dump skipped, instructions count: 363
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.lang.time.DateUtils.a(java.util.Calendar, int, int):void");
    }

    public static Iterator iterator(Date date, int i) {
        if (date != null) {
            Calendar instance = Calendar.getInstance();
            instance.setTime(date);
            return iterator(instance, i);
        }
        throw new IllegalArgumentException("The date must not be null");
    }

    public static Iterator iterator(Calendar calendar, int i) {
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
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("The range style ");
                    stringBuffer.append(i);
                    stringBuffer.append(" is not valid.");
                    throw new IllegalArgumentException(stringBuffer.toString());
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

    public static Iterator iterator(Object obj, int i) {
        if (obj == null) {
            throw new IllegalArgumentException("The date must not be null");
        } else if (obj instanceof Date) {
            return iterator((Date) obj, i);
        } else {
            if (obj instanceof Calendar) {
                return iterator((Calendar) obj, i);
            }
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Could not iterate based on ");
            stringBuffer.append(obj);
            throw new ClassCastException(stringBuffer.toString());
        }
    }

    public static long getFragmentInMilliseconds(Date date, int i) {
        return b(date, i, 14);
    }

    public static long getFragmentInSeconds(Date date, int i) {
        return b(date, i, 13);
    }

    public static long getFragmentInMinutes(Date date, int i) {
        return b(date, i, 12);
    }

    public static long getFragmentInHours(Date date, int i) {
        return b(date, i, 11);
    }

    public static long getFragmentInDays(Date date, int i) {
        return b(date, i, 6);
    }

    public static long getFragmentInMilliseconds(Calendar calendar, int i) {
        return b(calendar, i, 14);
    }

    public static long getFragmentInSeconds(Calendar calendar, int i) {
        return b(calendar, i, 13);
    }

    public static long getFragmentInMinutes(Calendar calendar, int i) {
        return b(calendar, i, 12);
    }

    public static long getFragmentInHours(Calendar calendar, int i) {
        return b(calendar, i, 11);
    }

    public static long getFragmentInDays(Calendar calendar, int i) {
        return b(calendar, i, 6);
    }

    private static long b(Date date, int i, int i2) {
        if (date != null) {
            Calendar instance = Calendar.getInstance();
            instance.setTime(date);
            return b(instance, i, i2);
        }
        throw new IllegalArgumentException("The date must not be null");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static long b(Calendar calendar, int i, int i2) {
        if (calendar != null) {
            long a2 = a(i2);
            long j = 0;
            switch (i) {
                case 1:
                    j = 0 + ((calendar.get(6) * 86400000) / a2);
                    break;
                case 2:
                    j = 0 + ((calendar.get(5) * 86400000) / a2);
                    break;
            }
            switch (i) {
                case 1:
                case 2:
                case 5:
                case 6:
                    j += (calendar.get(11) * 3600000) / a2;
                    j += (calendar.get(12) * 60000) / a2;
                    j += (calendar.get(13) * 1000) / a2;
                    break;
                case 3:
                case 4:
                case 7:
                case 8:
                case 9:
                case 10:
                default:
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("The fragment ");
                    stringBuffer.append(i);
                    stringBuffer.append(" is not supported");
                    throw new IllegalArgumentException(stringBuffer.toString());
                case 11:
                    j += (calendar.get(12) * 60000) / a2;
                    j += (calendar.get(13) * 1000) / a2;
                    break;
                case 12:
                    j += (calendar.get(13) * 1000) / a2;
                    break;
                case 13:
                    break;
                case 14:
                    return j;
            }
            return j + ((calendar.get(14) * 1) / a2);
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
        return truncate(calendar, i).getTime().compareTo(truncate(calendar2, i).getTime());
    }

    public static int truncatedCompareTo(Date date, Date date2, int i) {
        return truncate(date, i).compareTo(truncate(date2, i));
    }

    private static long a(int i) {
        switch (i) {
            case 5:
            case 6:
                return 86400000L;
            default:
                switch (i) {
                    case 11:
                        return 3600000L;
                    case 12:
                        return 60000L;
                    case 13:
                        return 1000L;
                    case 14:
                        return 1L;
                    default:
                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append("The unit ");
                        stringBuffer.append(i);
                        stringBuffer.append(" cannot be represented is milleseconds");
                        throw new IllegalArgumentException(stringBuffer.toString());
                }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static class a implements Iterator {
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

        @Override // java.util.Iterator
        public Object next() {
            if (!this.b.equals(this.a)) {
                this.b.add(5, 1);
                return this.b.clone();
            }
            throw new NoSuchElementException();
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
