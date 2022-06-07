package com.xiaomi.accountsdk.utils;

import com.xiaomi.micolauncher.skills.music.model.lrc.LrcRow;
import java.lang.ref.SoftReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/* loaded from: classes2.dex */
public final class DateUtils {
    private static final Date DEFAULT_TWO_DIGIT_YEAR_START;
    public static final String PATTERN_RFC1036 = "EEEE, dd-MMM-yy HH:mm:ss zzz";
    public static final String PATTERN_RFC1123 = "EEE, dd MMM yyyy HH:mm:ss zzz";
    public static final String PATTERN_ASCTIME = "EEE MMM d HH:mm:ss yyyy";
    private static final String[] DEFAULT_PATTERNS = {PATTERN_RFC1036, PATTERN_RFC1123, PATTERN_ASCTIME};
    public static final TimeZone GMT = TimeZone.getTimeZone("GMT");

    static {
        Calendar instance = Calendar.getInstance();
        instance.setTimeZone(GMT);
        instance.set(2000, 0, 1, 0, 0, 0);
        instance.set(14, 0);
        DEFAULT_TWO_DIGIT_YEAR_START = instance.getTime();
    }

    public static Date parseDate(String str) throws ParseException {
        return parseDate(str, null, null);
    }

    public static Date parseDate(String str, String[] strArr) throws ParseException {
        return parseDate(str, strArr, null);
    }

    public static Date parseDate(String str, String[] strArr, Date date) throws ParseException {
        if (str != null) {
            if (strArr == null) {
                strArr = DEFAULT_PATTERNS;
            }
            if (date == null) {
                date = DEFAULT_TWO_DIGIT_YEAR_START;
            }
            if (str.length() > 1 && str.startsWith(LrcRow.SINGLE_QUOTE) && str.endsWith(LrcRow.SINGLE_QUOTE)) {
                str = str.substring(1, str.length() - 1);
            }
            for (String str2 : strArr) {
                SimpleDateFormat formatFor = DateFormatHolder.formatFor(str2);
                formatFor.set2DigitYearStart(date);
                try {
                    return formatFor.parse(str);
                } catch (ParseException unused) {
                }
            }
            throw new ParseException("Unable to parse the date " + str, 0);
        }
        throw new IllegalArgumentException("dateValue is null");
    }

    public static String formatDate(Date date) {
        return formatDate(date, PATTERN_RFC1123);
    }

    public static String formatDate(Date date, String str) {
        if (date == null) {
            throw new IllegalArgumentException("date is null");
        } else if (str != null) {
            return DateFormatHolder.formatFor(str).format(date);
        } else {
            throw new IllegalArgumentException("pattern is null");
        }
    }

    private DateUtils() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static final class DateFormatHolder {
        private static final ThreadLocal<SoftReference<Map<String, SimpleDateFormat>>> THREADLOCAL_FORMATS = new ThreadLocal<SoftReference<Map<String, SimpleDateFormat>>>() { // from class: com.xiaomi.accountsdk.utils.DateUtils.DateFormatHolder.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // java.lang.ThreadLocal
            public SoftReference<Map<String, SimpleDateFormat>> initialValue() {
                return new SoftReference<>(new HashMap());
            }
        };

        DateFormatHolder() {
        }

        public static SimpleDateFormat formatFor(String str) {
            Map<String, SimpleDateFormat> map = THREADLOCAL_FORMATS.get().get();
            if (map == null) {
                map = new HashMap<>();
                THREADLOCAL_FORMATS.set(new SoftReference<>(map));
            }
            SimpleDateFormat simpleDateFormat = map.get(str);
            if (simpleDateFormat != null) {
                return simpleDateFormat;
            }
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(str, Locale.US);
            simpleDateFormat2.setTimeZone(TimeZone.getTimeZone("GMT"));
            map.put(str, simpleDateFormat2);
            return simpleDateFormat2;
        }
    }
}
