package org.eclipse.jetty.util;

import com.xiaomi.micolauncher.skills.music.model.lrc.LrcRow;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/* loaded from: classes5.dex */
public class DateCache {
    public static String DEFAULT_FORMAT = "EEE MMM dd HH:mm:ss zzz yyyy";
    private static long __hitWindow = 3600;
    private DateFormatSymbols _dfs;
    private String _formatString;
    private long _lastMinutes;
    private int _lastMs;
    private String _lastResult;
    private long _lastSeconds;
    private Locale _locale;
    private SimpleDateFormat _minFormat;
    private String _minFormatString;
    private String _secFormatString;
    private String _secFormatString0;
    private String _secFormatString1;
    private SimpleDateFormat _tzFormat;
    private String _tzFormatString;

    public DateCache() {
        this(DEFAULT_FORMAT);
        getFormat().setTimeZone(TimeZone.getDefault());
    }

    public DateCache(String str) {
        this._lastMinutes = -1L;
        this._lastSeconds = -1L;
        this._lastMs = -1;
        this._lastResult = null;
        this._locale = null;
        this._dfs = null;
        this._formatString = str;
        setTimeZone(TimeZone.getDefault());
    }

    public DateCache(String str, Locale locale) {
        this._lastMinutes = -1L;
        this._lastSeconds = -1L;
        this._lastMs = -1;
        this._lastResult = null;
        this._locale = null;
        this._dfs = null;
        this._formatString = str;
        this._locale = locale;
        setTimeZone(TimeZone.getDefault());
    }

    public DateCache(String str, DateFormatSymbols dateFormatSymbols) {
        this._lastMinutes = -1L;
        this._lastSeconds = -1L;
        this._lastMs = -1;
        this._lastResult = null;
        this._locale = null;
        this._dfs = null;
        this._formatString = str;
        this._dfs = dateFormatSymbols;
        setTimeZone(TimeZone.getDefault());
    }

    public synchronized void setTimeZone(TimeZone timeZone) {
        setTzFormatString(timeZone);
        if (this._locale != null) {
            this._tzFormat = new SimpleDateFormat(this._tzFormatString, this._locale);
            this._minFormat = new SimpleDateFormat(this._minFormatString, this._locale);
        } else if (this._dfs != null) {
            this._tzFormat = new SimpleDateFormat(this._tzFormatString, this._dfs);
            this._minFormat = new SimpleDateFormat(this._minFormatString, this._dfs);
        } else {
            this._tzFormat = new SimpleDateFormat(this._tzFormatString);
            this._minFormat = new SimpleDateFormat(this._minFormatString);
        }
        this._tzFormat.setTimeZone(timeZone);
        this._minFormat.setTimeZone(timeZone);
        this._lastSeconds = -1L;
        this._lastMinutes = -1L;
    }

    public TimeZone getTimeZone() {
        return this._tzFormat.getTimeZone();
    }

    public void setTimeZoneID(String str) {
        setTimeZone(TimeZone.getTimeZone(str));
    }

    private synchronized void setTzFormatString(TimeZone timeZone) {
        int indexOf = this._formatString.indexOf("ZZZ");
        if (indexOf >= 0) {
            String substring = this._formatString.substring(0, indexOf);
            String substring2 = this._formatString.substring(indexOf + 3);
            int rawOffset = timeZone.getRawOffset();
            StringBuilder sb = new StringBuilder(this._formatString.length() + 10);
            sb.append(substring);
            sb.append(LrcRow.SINGLE_QUOTE);
            if (rawOffset >= 0) {
                sb.append('+');
            } else {
                rawOffset = -rawOffset;
                sb.append('-');
            }
            int i = rawOffset / 60000;
            int i2 = i / 60;
            int i3 = i % 60;
            if (i2 < 10) {
                sb.append('0');
            }
            sb.append(i2);
            if (i3 < 10) {
                sb.append('0');
            }
            sb.append(i3);
            sb.append('\'');
            sb.append(substring2);
            this._tzFormatString = sb.toString();
        } else {
            this._tzFormatString = this._formatString;
        }
        setMinFormatString();
    }

    private void setMinFormatString() {
        if (this._tzFormatString.indexOf("ss.SSS") < 0) {
            int indexOf = this._tzFormatString.indexOf("ss");
            String substring = this._tzFormatString.substring(0, indexOf);
            String substring2 = this._tzFormatString.substring(indexOf + 2);
            this._minFormatString = substring + "'ss'" + substring2;
            return;
        }
        throw new IllegalStateException("ms not supported");
    }

    public synchronized String format(Date date) {
        return format(date.getTime());
    }

    public synchronized String format(long j) {
        long j2 = j / 1000;
        if (j2 >= this._lastSeconds && (this._lastSeconds <= 0 || j2 <= this._lastSeconds + __hitWindow)) {
            if (this._lastSeconds == j2) {
                return this._lastResult;
            }
            Date date = new Date(j);
            long j3 = j2 / 60;
            if (this._lastMinutes != j3) {
                this._lastMinutes = j3;
                this._secFormatString = this._minFormat.format(date);
                int indexOf = this._secFormatString.indexOf("ss");
                this._secFormatString0 = this._secFormatString.substring(0, indexOf);
                this._secFormatString1 = this._secFormatString.substring(indexOf + 2);
            }
            this._lastSeconds = j2;
            StringBuilder sb = new StringBuilder(this._secFormatString.length());
            sb.append(this._secFormatString0);
            int i = (int) (j2 % 60);
            if (i < 10) {
                sb.append('0');
            }
            sb.append(i);
            sb.append(this._secFormatString1);
            this._lastResult = sb.toString();
            return this._lastResult;
        }
        return this._tzFormat.format(new Date(j));
    }

    public void format(long j, StringBuilder sb) {
        sb.append(format(j));
    }

    public SimpleDateFormat getFormat() {
        return this._minFormat;
    }

    public String getFormatString() {
        return this._formatString;
    }

    public String now() {
        long currentTimeMillis = System.currentTimeMillis();
        this._lastMs = (int) (currentTimeMillis % 1000);
        return format(currentTimeMillis);
    }

    public int lastMs() {
        return this._lastMs;
    }
}
