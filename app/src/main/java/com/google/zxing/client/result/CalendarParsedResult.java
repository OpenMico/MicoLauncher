package com.google.zxing.client.result;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes2.dex */
public final class CalendarParsedResult extends ParsedResult {
    private static final Pattern a = Pattern.compile("P(?:(\\d+)W)?(?:(\\d+)D)?(?:T(?:(\\d+)H)?(?:(\\d+)M)?(?:(\\d+)S)?)?");
    private static final long[] b = {604800000, 86400000, 3600000, 60000, 1000};
    private static final Pattern c = Pattern.compile("[0-9]{8}(T[0-9]{6}Z?)?");
    private final String d;
    private final long e;
    private final boolean f;
    private final long g;
    private final boolean h;
    private final String i;
    private final String j;
    private final String[] k;
    private final String l;
    private final double m;
    private final double n;

    public CalendarParsedResult(String str, String str2, String str3, String str4, String str5, String str6, String[] strArr, String str7, double d, double d2) {
        super(ParsedResultType.CALENDAR);
        this.d = str;
        try {
            this.e = a(str2);
            if (str3 == null) {
                long a2 = a((CharSequence) str4);
                this.g = a2 < 0 ? -1L : a2 + this.e;
            } else {
                try {
                    this.g = a(str3);
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e.toString());
                }
            }
            boolean z = false;
            this.f = str2.length() == 8;
            if (str3 != null && str3.length() == 8) {
                z = true;
            }
            this.h = z;
            this.i = str5;
            this.j = str6;
            this.k = strArr;
            this.l = str7;
            this.m = d;
            this.n = d2;
        } catch (ParseException e2) {
            throw new IllegalArgumentException(e2.toString());
        }
    }

    public String getSummary() {
        return this.d;
    }

    @Deprecated
    public Date getStart() {
        return new Date(this.e);
    }

    public long getStartTimestamp() {
        return this.e;
    }

    public boolean isStartAllDay() {
        return this.f;
    }

    @Deprecated
    public Date getEnd() {
        long j = this.g;
        if (j < 0) {
            return null;
        }
        return new Date(j);
    }

    public long getEndTimestamp() {
        return this.g;
    }

    public boolean isEndAllDay() {
        return this.h;
    }

    public String getLocation() {
        return this.i;
    }

    public String getOrganizer() {
        return this.j;
    }

    public String[] getAttendees() {
        return this.k;
    }

    public String getDescription() {
        return this.l;
    }

    public double getLatitude() {
        return this.m;
    }

    public double getLongitude() {
        return this.n;
    }

    @Override // com.google.zxing.client.result.ParsedResult
    public String getDisplayResult() {
        StringBuilder sb = new StringBuilder(100);
        maybeAppend(this.d, sb);
        maybeAppend(a(this.f, this.e), sb);
        maybeAppend(a(this.h, this.g), sb);
        maybeAppend(this.i, sb);
        maybeAppend(this.j, sb);
        maybeAppend(this.k, sb);
        maybeAppend(this.l, sb);
        return sb.toString();
    }

    private static long a(String str) throws ParseException {
        if (!c.matcher(str).matches()) {
            throw new ParseException(str, 0);
        } else if (str.length() == 8) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            return simpleDateFormat.parse(str).getTime();
        } else if (str.length() != 16 || str.charAt(15) != 'Z') {
            return b(str);
        } else {
            long b2 = b(str.substring(0, 15));
            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            long j = b2 + gregorianCalendar.get(15);
            gregorianCalendar.setTime(new Date(j));
            return j + gregorianCalendar.get(16);
        }
    }

    private static String a(boolean z, long j) {
        DateFormat dateFormat;
        if (j < 0) {
            return null;
        }
        if (z) {
            dateFormat = DateFormat.getDateInstance(2);
        } else {
            dateFormat = DateFormat.getDateTimeInstance(2, 2);
        }
        return dateFormat.format(Long.valueOf(j));
    }

    private static long a(CharSequence charSequence) {
        if (charSequence == null) {
            return -1L;
        }
        Matcher matcher = a.matcher(charSequence);
        if (!matcher.matches()) {
            return -1L;
        }
        long j = 0;
        int i = 0;
        while (i < b.length) {
            int i2 = i + 1;
            String group = matcher.group(i2);
            if (group != null) {
                j += b[i] * Integer.parseInt(group);
            }
            i = i2;
        }
        return j;
    }

    private static long b(String str) throws ParseException {
        return new SimpleDateFormat("yyyyMMdd'T'HHmmss", Locale.ENGLISH).parse(str).getTime();
    }
}
