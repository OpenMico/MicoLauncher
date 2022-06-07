package org.apache.commons.lang3.time;

import com.xiaomi.mipush.sdk.Constants;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/* loaded from: classes5.dex */
public class FastDateFormat extends Format implements DateParser, DatePrinter {
    public static final int FULL = 0;
    public static final int LONG = 1;
    public static final int MEDIUM = 2;
    public static final int SHORT = 3;
    private static final b<FastDateFormat> a = new b<FastDateFormat>() { // from class: org.apache.commons.lang3.time.FastDateFormat.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public FastDateFormat b(String str, TimeZone timeZone, Locale locale) {
            return new FastDateFormat(str, timeZone, locale);
        }
    };
    private static final long serialVersionUID = 2;
    private final FastDateParser parser;
    private final FastDatePrinter printer;

    public static FastDateFormat getInstance() {
        return a.a();
    }

    public static FastDateFormat getInstance(String str) {
        return a.c(str, null, null);
    }

    public static FastDateFormat getInstance(String str, TimeZone timeZone) {
        return a.c(str, timeZone, null);
    }

    public static FastDateFormat getInstance(String str, Locale locale) {
        return a.c(str, null, locale);
    }

    public static FastDateFormat getInstance(String str, TimeZone timeZone, Locale locale) {
        return a.c(str, timeZone, locale);
    }

    public static FastDateFormat getDateInstance(int i) {
        return a.a(i, (TimeZone) null, (Locale) null);
    }

    public static FastDateFormat getDateInstance(int i, Locale locale) {
        return a.a(i, (TimeZone) null, locale);
    }

    public static FastDateFormat getDateInstance(int i, TimeZone timeZone) {
        return a.a(i, timeZone, (Locale) null);
    }

    public static FastDateFormat getDateInstance(int i, TimeZone timeZone, Locale locale) {
        return a.a(i, timeZone, locale);
    }

    public static FastDateFormat getTimeInstance(int i) {
        return a.b(i, (TimeZone) null, (Locale) null);
    }

    public static FastDateFormat getTimeInstance(int i, Locale locale) {
        return a.b(i, (TimeZone) null, locale);
    }

    public static FastDateFormat getTimeInstance(int i, TimeZone timeZone) {
        return a.b(i, timeZone, (Locale) null);
    }

    public static FastDateFormat getTimeInstance(int i, TimeZone timeZone, Locale locale) {
        return a.b(i, timeZone, locale);
    }

    public static FastDateFormat getDateTimeInstance(int i, int i2) {
        return a.a(i, i2, (TimeZone) null, (Locale) null);
    }

    public static FastDateFormat getDateTimeInstance(int i, int i2, Locale locale) {
        return a.a(i, i2, (TimeZone) null, locale);
    }

    public static FastDateFormat getDateTimeInstance(int i, int i2, TimeZone timeZone) {
        return getDateTimeInstance(i, i2, timeZone, null);
    }

    public static FastDateFormat getDateTimeInstance(int i, int i2, TimeZone timeZone, Locale locale) {
        return a.a(i, i2, timeZone, locale);
    }

    protected FastDateFormat(String str, TimeZone timeZone, Locale locale) {
        this(str, timeZone, locale, null);
    }

    protected FastDateFormat(String str, TimeZone timeZone, Locale locale, Date date) {
        this.printer = new FastDatePrinter(str, timeZone, locale);
        this.parser = new FastDateParser(str, timeZone, locale, date);
    }

    @Override // java.text.Format, org.apache.commons.lang3.time.DatePrinter
    public StringBuffer format(Object obj, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        stringBuffer.append(this.printer.a(obj));
        return stringBuffer;
    }

    @Override // org.apache.commons.lang3.time.DatePrinter
    public String format(long j) {
        return this.printer.format(j);
    }

    @Override // org.apache.commons.lang3.time.DatePrinter
    public String format(Date date) {
        return this.printer.format(date);
    }

    @Override // org.apache.commons.lang3.time.DatePrinter
    public String format(Calendar calendar) {
        return this.printer.format(calendar);
    }

    @Override // org.apache.commons.lang3.time.DatePrinter
    @Deprecated
    public StringBuffer format(long j, StringBuffer stringBuffer) {
        return this.printer.format(j, stringBuffer);
    }

    @Override // org.apache.commons.lang3.time.DatePrinter
    @Deprecated
    public StringBuffer format(Date date, StringBuffer stringBuffer) {
        return this.printer.format(date, stringBuffer);
    }

    @Override // org.apache.commons.lang3.time.DatePrinter
    @Deprecated
    public StringBuffer format(Calendar calendar, StringBuffer stringBuffer) {
        return this.printer.format(calendar, stringBuffer);
    }

    @Override // org.apache.commons.lang3.time.DatePrinter
    public <B extends Appendable> B format(long j, B b) {
        return (B) this.printer.format(j, (long) b);
    }

    @Override // org.apache.commons.lang3.time.DatePrinter
    public <B extends Appendable> B format(Date date, B b) {
        return (B) this.printer.format(date, (Date) b);
    }

    @Override // org.apache.commons.lang3.time.DatePrinter
    public <B extends Appendable> B format(Calendar calendar, B b) {
        return (B) this.printer.format(calendar, (Calendar) b);
    }

    @Override // org.apache.commons.lang3.time.DateParser
    public Date parse(String str) throws ParseException {
        return this.parser.parse(str);
    }

    @Override // org.apache.commons.lang3.time.DateParser
    public Date parse(String str, ParsePosition parsePosition) {
        return this.parser.parse(str, parsePosition);
    }

    @Override // org.apache.commons.lang3.time.DateParser
    public boolean parse(String str, ParsePosition parsePosition, Calendar calendar) {
        return this.parser.parse(str, parsePosition, calendar);
    }

    @Override // java.text.Format, org.apache.commons.lang3.time.DateParser
    public Object parseObject(String str, ParsePosition parsePosition) {
        return this.parser.parseObject(str, parsePosition);
    }

    @Override // org.apache.commons.lang3.time.DateParser, org.apache.commons.lang3.time.DatePrinter
    public String getPattern() {
        return this.printer.getPattern();
    }

    @Override // org.apache.commons.lang3.time.DateParser, org.apache.commons.lang3.time.DatePrinter
    public TimeZone getTimeZone() {
        return this.printer.getTimeZone();
    }

    @Override // org.apache.commons.lang3.time.DateParser, org.apache.commons.lang3.time.DatePrinter
    public Locale getLocale() {
        return this.printer.getLocale();
    }

    public int getMaxLengthEstimate() {
        return this.printer.getMaxLengthEstimate();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof FastDateFormat)) {
            return false;
        }
        return this.printer.equals(((FastDateFormat) obj).printer);
    }

    public int hashCode() {
        return this.printer.hashCode();
    }

    public String toString() {
        return "FastDateFormat[" + this.printer.getPattern() + Constants.ACCEPT_TIME_SEPARATOR_SP + this.printer.getLocale() + Constants.ACCEPT_TIME_SEPARATOR_SP + this.printer.getTimeZone().getID() + "]";
    }

    @Deprecated
    protected StringBuffer applyRules(Calendar calendar, StringBuffer stringBuffer) {
        return this.printer.applyRules(calendar, stringBuffer);
    }
}
