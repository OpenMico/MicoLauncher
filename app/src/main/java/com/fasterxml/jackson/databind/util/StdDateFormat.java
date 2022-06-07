package com.fasterxml.jackson.databind.util;

import com.blankj.utilcode.constant.CacheConstants;
import com.fasterxml.jackson.core.io.NumberInput;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class StdDateFormat extends DateFormat {
    protected static final String[] ALL_FORMATS;
    protected static final Calendar CALENDAR;
    protected static final DateFormat DATE_FORMAT_ISO8601;
    protected static final DateFormat DATE_FORMAT_RFC1123;
    public static final String DATE_FORMAT_STR_ISO8601 = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    protected static final String DATE_FORMAT_STR_PLAIN = "yyyy-MM-dd";
    protected static final String DATE_FORMAT_STR_RFC1123 = "EEE, dd MMM yyyy HH:mm:ss zzz";
    protected static final Locale DEFAULT_LOCALE;
    protected static final TimeZone DEFAULT_TIMEZONE;
    protected static final Pattern PATTERN_ISO8601;
    public static final StdDateFormat instance;
    protected Boolean _lenient;
    protected final Locale _locale;
    protected transient TimeZone _timezone;
    private boolean _tzSerializedWithColon;
    private transient Calendar a;
    private transient DateFormat b;
    protected static final String PATTERN_PLAIN_STR = "\\d\\d\\d\\d[-]\\d\\d[-]\\d\\d";
    protected static final Pattern PATTERN_PLAIN = Pattern.compile(PATTERN_PLAIN_STR);

    @Override // java.text.DateFormat
    public boolean equals(Object obj) {
        return obj == this;
    }

    static {
        try {
            PATTERN_ISO8601 = Pattern.compile("\\d\\d\\d\\d[-]\\d\\d[-]\\d\\d[T]\\d\\d[:]\\d\\d(?:[:]\\d\\d)?(\\.\\d+)?(Z|[+-]\\d\\d(?:[:]?\\d\\d)?)?");
            ALL_FORMATS = new String[]{DATE_FORMAT_STR_ISO8601, "yyyy-MM-dd'T'HH:mm:ss.SSS", "EEE, dd MMM yyyy HH:mm:ss zzz", "yyyy-MM-dd"};
            DEFAULT_TIMEZONE = TimeZone.getTimeZone("UTC");
            DEFAULT_LOCALE = Locale.US;
            DATE_FORMAT_RFC1123 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", DEFAULT_LOCALE);
            DATE_FORMAT_RFC1123.setTimeZone(DEFAULT_TIMEZONE);
            DATE_FORMAT_ISO8601 = new SimpleDateFormat(DATE_FORMAT_STR_ISO8601, DEFAULT_LOCALE);
            DATE_FORMAT_ISO8601.setTimeZone(DEFAULT_TIMEZONE);
            instance = new StdDateFormat();
            CALENDAR = new GregorianCalendar(DEFAULT_TIMEZONE, DEFAULT_LOCALE);
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }

    public StdDateFormat() {
        this._tzSerializedWithColon = false;
        this._locale = DEFAULT_LOCALE;
    }

    @Deprecated
    public StdDateFormat(TimeZone timeZone, Locale locale) {
        this._tzSerializedWithColon = false;
        this._timezone = timeZone;
        this._locale = locale;
    }

    protected StdDateFormat(TimeZone timeZone, Locale locale, Boolean bool) {
        this(timeZone, locale, bool, false);
    }

    protected StdDateFormat(TimeZone timeZone, Locale locale, Boolean bool, boolean z) {
        this._tzSerializedWithColon = false;
        this._timezone = timeZone;
        this._locale = locale;
        this._lenient = bool;
        this._tzSerializedWithColon = z;
    }

    public static TimeZone getDefaultTimeZone() {
        return DEFAULT_TIMEZONE;
    }

    public StdDateFormat withTimeZone(TimeZone timeZone) {
        if (timeZone == null) {
            timeZone = DEFAULT_TIMEZONE;
        }
        TimeZone timeZone2 = this._timezone;
        return (timeZone == timeZone2 || timeZone.equals(timeZone2)) ? this : new StdDateFormat(timeZone, this._locale, this._lenient, this._tzSerializedWithColon);
    }

    public StdDateFormat withLocale(Locale locale) {
        return locale.equals(this._locale) ? this : new StdDateFormat(this._timezone, locale, this._lenient, this._tzSerializedWithColon);
    }

    public StdDateFormat withLenient(Boolean bool) {
        return _equals(bool, this._lenient) ? this : new StdDateFormat(this._timezone, this._locale, bool, this._tzSerializedWithColon);
    }

    public StdDateFormat withColonInTimeZone(boolean z) {
        return this._tzSerializedWithColon == z ? this : new StdDateFormat(this._timezone, this._locale, this._lenient, z);
    }

    @Override // java.text.DateFormat, java.text.Format
    public StdDateFormat clone() {
        return new StdDateFormat(this._timezone, this._locale, this._lenient, this._tzSerializedWithColon);
    }

    @Deprecated
    public static DateFormat getISO8601Format(TimeZone timeZone, Locale locale) {
        return a(DATE_FORMAT_ISO8601, DATE_FORMAT_STR_ISO8601, timeZone, locale, null);
    }

    @Deprecated
    public static DateFormat getRFC1123Format(TimeZone timeZone, Locale locale) {
        return a(DATE_FORMAT_RFC1123, "EEE, dd MMM yyyy HH:mm:ss zzz", timeZone, locale, null);
    }

    @Override // java.text.DateFormat
    public TimeZone getTimeZone() {
        return this._timezone;
    }

    @Override // java.text.DateFormat
    public void setTimeZone(TimeZone timeZone) {
        if (!timeZone.equals(this._timezone)) {
            _clearFormats();
            this._timezone = timeZone;
        }
    }

    @Override // java.text.DateFormat
    public void setLenient(boolean z) {
        Boolean valueOf = Boolean.valueOf(z);
        if (!_equals(valueOf, this._lenient)) {
            this._lenient = valueOf;
            _clearFormats();
        }
    }

    @Override // java.text.DateFormat
    public boolean isLenient() {
        Boolean bool = this._lenient;
        return bool == null || bool.booleanValue();
    }

    public boolean isColonIncludedInTimeZone() {
        return this._tzSerializedWithColon;
    }

    @Override // java.text.DateFormat
    public Date parse(String str) throws ParseException {
        String trim = str.trim();
        ParsePosition parsePosition = new ParsePosition(0);
        Date _parseDate = _parseDate(trim, parsePosition);
        if (_parseDate != null) {
            return _parseDate;
        }
        StringBuilder sb = new StringBuilder();
        String[] strArr = ALL_FORMATS;
        for (String str2 : strArr) {
            if (sb.length() > 0) {
                sb.append("\", \"");
            } else {
                sb.append('\"');
            }
            sb.append(str2);
        }
        sb.append('\"');
        throw new ParseException(String.format("Cannot parse date \"%s\": not compatible with any of standard forms (%s)", trim, sb.toString()), parsePosition.getErrorIndex());
    }

    @Override // java.text.DateFormat
    public Date parse(String str, ParsePosition parsePosition) {
        try {
            return _parseDate(str, parsePosition);
        } catch (ParseException unused) {
            return null;
        }
    }

    protected Date _parseDate(String str, ParsePosition parsePosition) throws ParseException {
        if (looksLikeISO8601(str)) {
            return parseAsISO8601(str, parsePosition);
        }
        int length = str.length();
        while (true) {
            length--;
            if (length < 0) {
                break;
            }
            char charAt = str.charAt(length);
            if (charAt < '0' || charAt > '9') {
                if (length > 0 || charAt != '-') {
                    break;
                }
            }
        }
        if (length >= 0 || (str.charAt(0) != '-' && !NumberInput.inLongRange(str, false))) {
            return parseAsRFC1123(str, parsePosition);
        }
        return a(str, parsePosition);
    }

    @Override // java.text.DateFormat
    public StringBuffer format(Date date, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        TimeZone timeZone = this._timezone;
        if (timeZone == null) {
            timeZone = DEFAULT_TIMEZONE;
        }
        _format(timeZone, this._locale, date, stringBuffer);
        return stringBuffer;
    }

    protected void _format(TimeZone timeZone, Locale locale, Date date, StringBuffer stringBuffer) {
        Calendar _getCalendar = _getCalendar(timeZone);
        _getCalendar.setTime(date);
        int i = _getCalendar.get(1);
        if (_getCalendar.get(0) == 0) {
            _formatBCEYear(stringBuffer, i);
        } else {
            if (i > 9999) {
                stringBuffer.append('+');
            }
            c(stringBuffer, i);
        }
        char c = '-';
        stringBuffer.append('-');
        a(stringBuffer, _getCalendar.get(2) + 1);
        stringBuffer.append('-');
        a(stringBuffer, _getCalendar.get(5));
        stringBuffer.append('T');
        a(stringBuffer, _getCalendar.get(11));
        stringBuffer.append(':');
        a(stringBuffer, _getCalendar.get(12));
        stringBuffer.append(':');
        a(stringBuffer, _getCalendar.get(13));
        stringBuffer.append('.');
        b(stringBuffer, _getCalendar.get(14));
        int offset = timeZone.getOffset(_getCalendar.getTimeInMillis());
        if (offset != 0) {
            int i2 = offset / 60000;
            int abs = Math.abs(i2 / 60);
            int abs2 = Math.abs(i2 % 60);
            if (offset >= 0) {
                c = '+';
            }
            stringBuffer.append(c);
            a(stringBuffer, abs);
            if (this._tzSerializedWithColon) {
                stringBuffer.append(':');
            }
            a(stringBuffer, abs2);
        } else if (this._tzSerializedWithColon) {
            stringBuffer.append("+00:00");
        } else {
            stringBuffer.append("+0000");
        }
    }

    protected void _formatBCEYear(StringBuffer stringBuffer, int i) {
        if (i == 1) {
            stringBuffer.append("+0000");
            return;
        }
        stringBuffer.append('-');
        c(stringBuffer, i - 1);
    }

    private static void a(StringBuffer stringBuffer, int i) {
        int i2 = i / 10;
        if (i2 == 0) {
            stringBuffer.append('0');
        } else {
            stringBuffer.append((char) (i2 + 48));
            i -= i2 * 10;
        }
        stringBuffer.append((char) (i + 48));
    }

    private static void b(StringBuffer stringBuffer, int i) {
        int i2 = i / 100;
        if (i2 == 0) {
            stringBuffer.append('0');
        } else {
            stringBuffer.append((char) (i2 + 48));
            i -= i2 * 100;
        }
        a(stringBuffer, i);
    }

    private static void c(StringBuffer stringBuffer, int i) {
        int i2 = i / 100;
        if (i2 == 0) {
            stringBuffer.append('0');
            stringBuffer.append('0');
        } else {
            if (i2 > 99) {
                stringBuffer.append(i2);
            } else {
                a(stringBuffer, i2);
            }
            i -= i2 * 100;
        }
        a(stringBuffer, i);
    }

    public String toString() {
        return String.format("DateFormat %s: (timezone: %s, locale: %s, lenient: %s)", getClass().getName(), this._timezone, this._locale, this._lenient);
    }

    public String toPattern() {
        StringBuilder sb = new StringBuilder(100);
        sb.append("[one of: '");
        sb.append(DATE_FORMAT_STR_ISO8601);
        sb.append("', '");
        sb.append("EEE, dd MMM yyyy HH:mm:ss zzz");
        sb.append("' (");
        sb.append(Boolean.FALSE.equals(this._lenient) ? "strict" : "lenient");
        sb.append(")]");
        return sb.toString();
    }

    @Override // java.text.DateFormat
    public int hashCode() {
        return System.identityHashCode(this);
    }

    protected boolean looksLikeISO8601(String str) {
        return str.length() >= 7 && Character.isDigit(str.charAt(0)) && Character.isDigit(str.charAt(3)) && str.charAt(4) == '-' && Character.isDigit(str.charAt(5));
    }

    private Date a(String str, ParsePosition parsePosition) throws ParseException {
        try {
            return new Date(NumberInput.parseLong(str));
        } catch (NumberFormatException unused) {
            throw new ParseException(String.format("Timestamp value %s out of 64-bit value range", str), parsePosition.getErrorIndex());
        }
    }

    protected Date parseAsISO8601(String str, ParsePosition parsePosition) throws ParseException {
        try {
            return _parseAsISO8601(str, parsePosition);
        } catch (IllegalArgumentException e) {
            throw new ParseException(String.format("Cannot parse date \"%s\", problem: %s", str, e.getMessage()), parsePosition.getErrorIndex());
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    protected Date _parseAsISO8601(String str, ParsePosition parsePosition) throws IllegalArgumentException, ParseException {
        String str2;
        int length = str.length();
        TimeZone timeZone = DEFAULT_TIMEZONE;
        if (!(this._timezone == null || 'Z' == str.charAt(length - 1))) {
            timeZone = this._timezone;
        }
        Calendar _getCalendar = _getCalendar(timeZone);
        _getCalendar.clear();
        int i = 0;
        if (length > 10) {
            Matcher matcher = PATTERN_ISO8601.matcher(str);
            if (matcher.matches()) {
                int start = matcher.start(2);
                int end = matcher.end(2);
                int i2 = end - start;
                if (i2 > 1) {
                    int b = b(str, start + 1) * CacheConstants.HOUR;
                    if (i2 >= 5) {
                        b += b(str, end - 2) * 60;
                    }
                    _getCalendar.set(15, str.charAt(start) == '-' ? b * (-1000) : b * 1000);
                    _getCalendar.set(16, 0);
                }
                _getCalendar.set(a(str, 0), b(str, 5) - 1, b(str, 8), b(str, 11), b(str, 14), (length <= 16 || str.charAt(16) != ':') ? 0 : b(str, 17));
                int start2 = matcher.start(1) + 1;
                int end2 = matcher.end(1);
                if (start2 >= end2) {
                    _getCalendar.set(14, 0);
                } else {
                    int i3 = end2 - start2;
                    switch (i3) {
                        case 0:
                            break;
                        case 1:
                            i += (str.charAt(start2) - '0') * 100;
                            break;
                        case 2:
                            i += (str.charAt(start2 + 1) - '0') * 10;
                            i += (str.charAt(start2) - '0') * 100;
                            break;
                        case 3:
                            i = 0 + (str.charAt(start2 + 2) - '0');
                            i += (str.charAt(start2 + 1) - '0') * 10;
                            i += (str.charAt(start2) - '0') * 100;
                            break;
                        default:
                            if (i3 > 9) {
                                throw new ParseException(String.format("Cannot parse date \"%s\": invalid fractional seconds '%s'; can use at most 9 digits", str, matcher.group(1).substring(1)), start2);
                            }
                            i = 0 + (str.charAt(start2 + 2) - '0');
                            i += (str.charAt(start2 + 1) - '0') * 10;
                            i += (str.charAt(start2) - '0') * 100;
                            break;
                    }
                    _getCalendar.set(14, i);
                }
                return _getCalendar.getTime();
            }
            str2 = DATE_FORMAT_STR_ISO8601;
        } else if (PATTERN_PLAIN.matcher(str).matches()) {
            _getCalendar.set(a(str, 0), b(str, 5) - 1, b(str, 8), 0, 0, 0);
            _getCalendar.set(14, 0);
            return _getCalendar.getTime();
        } else {
            str2 = "yyyy-MM-dd";
        }
        throw new ParseException(String.format("Cannot parse date \"%s\": while it seems to fit format '%s', parsing fails (leniency? %s)", str, str2, this._lenient), 0);
    }

    private static int a(String str, int i) {
        return ((str.charAt(i) - '0') * 1000) + ((str.charAt(i + 1) - '0') * 100) + ((str.charAt(i + 2) - '0') * 10) + (str.charAt(i + 3) - '0');
    }

    private static int b(String str, int i) {
        return ((str.charAt(i) - '0') * 10) + (str.charAt(i + 1) - '0');
    }

    protected Date parseAsRFC1123(String str, ParsePosition parsePosition) {
        if (this.b == null) {
            this.b = a(DATE_FORMAT_RFC1123, "EEE, dd MMM yyyy HH:mm:ss zzz", this._timezone, this._locale, this._lenient);
        }
        return this.b.parse(str, parsePosition);
    }

    private static final DateFormat a(DateFormat dateFormat, String str, TimeZone timeZone, Locale locale, Boolean bool) {
        DateFormat dateFormat2;
        if (!locale.equals(DEFAULT_LOCALE)) {
            dateFormat2 = new SimpleDateFormat(str, locale);
            if (timeZone == null) {
                timeZone = DEFAULT_TIMEZONE;
            }
            dateFormat2.setTimeZone(timeZone);
        } else {
            dateFormat2 = (DateFormat) dateFormat.clone();
            if (timeZone != null) {
                dateFormat2.setTimeZone(timeZone);
            }
        }
        if (bool != null) {
            dateFormat2.setLenient(bool.booleanValue());
        }
        return dateFormat2;
    }

    protected void _clearFormats() {
        this.b = null;
    }

    protected Calendar _getCalendar(TimeZone timeZone) {
        Calendar calendar = this.a;
        if (calendar == null) {
            calendar = (Calendar) CALENDAR.clone();
            this.a = calendar;
        }
        if (!calendar.getTimeZone().equals(timeZone)) {
            calendar.setTimeZone(timeZone);
        }
        calendar.setLenient(isLenient());
        return calendar;
    }

    protected static <T> boolean _equals(T t, T t2) {
        if (t == t2) {
            return true;
        }
        return t != null && t.equals(t2);
    }
}
