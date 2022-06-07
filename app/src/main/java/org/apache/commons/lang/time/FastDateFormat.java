package org.apache.commons.lang.time;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.text.StrBuilder;

/* loaded from: classes5.dex */
public class FastDateFormat extends Format {
    public static final int FULL = 0;
    public static final int LONG = 1;
    public static final int MEDIUM = 2;
    public static final int SHORT = 3;
    private static String a = null;
    private static final Map b = new HashMap(7);
    private static final Map c = new HashMap(7);
    private static final Map d = new HashMap(7);
    private static final Map e = new HashMap(7);
    private static final Map f = new HashMap(7);
    private static final long serialVersionUID = 1;
    private transient e[] g;
    private transient int h;
    private final Locale mLocale;
    private final boolean mLocaleForced;
    private final String mPattern;
    private final TimeZone mTimeZone;
    private final boolean mTimeZoneForced;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public interface b extends e {
        void a(StringBuffer stringBuffer, int i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public interface e {
        int a();

        void a(StringBuffer stringBuffer, Calendar calendar);
    }

    public static FastDateFormat getInstance() {
        return getInstance(a(), null, null);
    }

    public static FastDateFormat getInstance(String str) {
        return getInstance(str, null, null);
    }

    public static FastDateFormat getInstance(String str, TimeZone timeZone) {
        return getInstance(str, timeZone, null);
    }

    public static FastDateFormat getInstance(String str, Locale locale) {
        return getInstance(str, null, locale);
    }

    public static synchronized FastDateFormat getInstance(String str, TimeZone timeZone, Locale locale) {
        FastDateFormat fastDateFormat;
        synchronized (FastDateFormat.class) {
            FastDateFormat fastDateFormat2 = new FastDateFormat(str, timeZone, locale);
            fastDateFormat = (FastDateFormat) b.get(fastDateFormat2);
            if (fastDateFormat == null) {
                fastDateFormat2.init();
                b.put(fastDateFormat2, fastDateFormat2);
                fastDateFormat = fastDateFormat2;
            }
        }
        return fastDateFormat;
    }

    public static FastDateFormat getDateInstance(int i2) {
        return getDateInstance(i2, null, null);
    }

    public static FastDateFormat getDateInstance(int i2, Locale locale) {
        return getDateInstance(i2, null, locale);
    }

    public static FastDateFormat getDateInstance(int i2, TimeZone timeZone) {
        return getDateInstance(i2, timeZone, null);
    }

    public static synchronized FastDateFormat getDateInstance(int i2, TimeZone timeZone, Locale locale) {
        FastDateFormat fastDateFormat;
        synchronized (FastDateFormat.class) {
            Object num = new Integer(i2);
            if (timeZone != null) {
                num = new d(num, timeZone);
            }
            if (locale == null) {
                locale = Locale.getDefault();
            }
            d dVar = new d(num, locale);
            fastDateFormat = (FastDateFormat) c.get(dVar);
            if (fastDateFormat == null) {
                try {
                    fastDateFormat = getInstance(((SimpleDateFormat) DateFormat.getDateInstance(i2, locale)).toPattern(), timeZone, locale);
                    c.put(dVar, fastDateFormat);
                } catch (ClassCastException unused) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("No date pattern for locale: ");
                    stringBuffer.append(locale);
                    throw new IllegalArgumentException(stringBuffer.toString());
                }
            }
        }
        return fastDateFormat;
    }

    public static FastDateFormat getTimeInstance(int i2) {
        return getTimeInstance(i2, null, null);
    }

    public static FastDateFormat getTimeInstance(int i2, Locale locale) {
        return getTimeInstance(i2, null, locale);
    }

    public static FastDateFormat getTimeInstance(int i2, TimeZone timeZone) {
        return getTimeInstance(i2, timeZone, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v5, types: [org.apache.commons.lang.time.FastDateFormat$d] */
    /* JADX WARN: Type inference failed for: r2v6, types: [org.apache.commons.lang.time.FastDateFormat$d] */
    public static synchronized FastDateFormat getTimeInstance(int i2, TimeZone timeZone, Locale locale) {
        FastDateFormat fastDateFormat;
        synchronized (FastDateFormat.class) {
            Integer num = new Integer(i2);
            if (timeZone != null) {
                num = new d(num, timeZone);
            }
            if (locale != null) {
                num = new d(num, locale);
            }
            fastDateFormat = (FastDateFormat) d.get(num);
            if (fastDateFormat == null) {
                if (locale == null) {
                    locale = Locale.getDefault();
                }
                try {
                    fastDateFormat = getInstance(((SimpleDateFormat) DateFormat.getTimeInstance(i2, locale)).toPattern(), timeZone, locale);
                    d.put(num, fastDateFormat);
                } catch (ClassCastException unused) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("No date pattern for locale: ");
                    stringBuffer.append(locale);
                    throw new IllegalArgumentException(stringBuffer.toString());
                }
            }
        }
        return fastDateFormat;
    }

    public static FastDateFormat getDateTimeInstance(int i2, int i3) {
        return getDateTimeInstance(i2, i3, null, null);
    }

    public static FastDateFormat getDateTimeInstance(int i2, int i3, Locale locale) {
        return getDateTimeInstance(i2, i3, null, locale);
    }

    public static FastDateFormat getDateTimeInstance(int i2, int i3, TimeZone timeZone) {
        return getDateTimeInstance(i2, i3, timeZone, null);
    }

    public static synchronized FastDateFormat getDateTimeInstance(int i2, int i3, TimeZone timeZone, Locale locale) {
        FastDateFormat fastDateFormat;
        synchronized (FastDateFormat.class) {
            d dVar = new d(new Integer(i2), new Integer(i3));
            if (timeZone != null) {
                dVar = new d(dVar, timeZone);
            }
            if (locale == null) {
                locale = Locale.getDefault();
            }
            d dVar2 = new d(dVar, locale);
            fastDateFormat = (FastDateFormat) e.get(dVar2);
            if (fastDateFormat == null) {
                try {
                    fastDateFormat = getInstance(((SimpleDateFormat) DateFormat.getDateTimeInstance(i2, i3, locale)).toPattern(), timeZone, locale);
                    e.put(dVar2, fastDateFormat);
                } catch (ClassCastException unused) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("No date time pattern for locale: ");
                    stringBuffer.append(locale);
                    throw new IllegalArgumentException(stringBuffer.toString());
                }
            }
        }
        return fastDateFormat;
    }

    static synchronized String a(TimeZone timeZone, boolean z, int i2, Locale locale) {
        String str;
        synchronized (FastDateFormat.class) {
            h hVar = new h(timeZone, z, i2, locale);
            str = (String) f.get(hVar);
            if (str == null) {
                str = timeZone.getDisplayName(z, i2, locale);
                f.put(hVar, str);
            }
        }
        return str;
    }

    private static synchronized String a() {
        String str;
        synchronized (FastDateFormat.class) {
            if (a == null) {
                a = new SimpleDateFormat().toPattern();
            }
            str = a;
        }
        return str;
    }

    protected FastDateFormat(String str, TimeZone timeZone, Locale locale) {
        if (str != null) {
            this.mPattern = str;
            boolean z = true;
            this.mTimeZoneForced = timeZone != null;
            this.mTimeZone = timeZone == null ? TimeZone.getDefault() : timeZone;
            this.mLocaleForced = locale == null ? false : z;
            this.mLocale = locale == null ? Locale.getDefault() : locale;
            return;
        }
        throw new IllegalArgumentException("The pattern must not be null");
    }

    protected void init() {
        List parsePattern = parsePattern();
        this.g = (e[]) parsePattern.toArray(new e[parsePattern.size()]);
        int length = this.g.length;
        int i2 = 0;
        while (true) {
            length--;
            if (length >= 0) {
                i2 += this.g[length].a();
            } else {
                this.h = i2;
                return;
            }
        }
    }

    protected List parsePattern() {
        Object obj;
        DateFormatSymbols dateFormatSymbols = new DateFormatSymbols(this.mLocale);
        ArrayList arrayList = new ArrayList();
        String[] eras = dateFormatSymbols.getEras();
        String[] months = dateFormatSymbols.getMonths();
        String[] shortMonths = dateFormatSymbols.getShortMonths();
        String[] weekdays = dateFormatSymbols.getWeekdays();
        String[] shortWeekdays = dateFormatSymbols.getShortWeekdays();
        String[] amPmStrings = dateFormatSymbols.getAmPmStrings();
        int length = this.mPattern.length();
        int i2 = 1;
        int[] iArr = new int[1];
        int i3 = 0;
        int i4 = 0;
        while (i4 < length) {
            iArr[i3] = i4;
            String parseToken = parseToken(this.mPattern, iArr);
            int i5 = iArr[i3];
            int length2 = parseToken.length();
            if (length2 == 0) {
                return arrayList;
            }
            switch (parseToken.charAt(i3)) {
                case '\'':
                    String substring = parseToken.substring(i2);
                    if (substring.length() != i2) {
                        i3 = 0;
                        obj = new f(substring);
                        break;
                    } else {
                        i3 = 0;
                        obj = new a(substring.charAt(0));
                        break;
                    }
                case 'D':
                    obj = selectNumberRule(6, length2);
                    i2 = 1;
                    i3 = 0;
                    break;
                case 'E':
                    obj = new g(7, length2 < 4 ? shortWeekdays : weekdays);
                    i2 = 1;
                    i3 = 0;
                    break;
                case 'F':
                    obj = selectNumberRule(8, length2);
                    i2 = 1;
                    i3 = 0;
                    break;
                case 'G':
                    obj = new g(0, eras);
                    i3 = 0;
                    i2 = 1;
                    break;
                case 'H':
                    obj = selectNumberRule(11, length2);
                    i2 = 1;
                    i3 = 0;
                    break;
                case 'K':
                    obj = selectNumberRule(10, length2);
                    i2 = 1;
                    i3 = 0;
                    break;
                case 'M':
                    if (length2 < 4) {
                        if (length2 != 3) {
                            if (length2 != 2) {
                                obj = p.a;
                                i2 = 1;
                                i3 = 0;
                                break;
                            } else {
                                obj = m.a;
                                i2 = 1;
                                i3 = 0;
                                break;
                            }
                        } else {
                            obj = new g(2, shortMonths);
                            i2 = 1;
                            i3 = 0;
                            break;
                        }
                    } else {
                        obj = new g(2, months);
                        i2 = 1;
                        i3 = 0;
                        break;
                    }
                case 'S':
                    obj = selectNumberRule(14, length2);
                    i2 = 1;
                    i3 = 0;
                    break;
                case 'W':
                    obj = selectNumberRule(4, length2);
                    i2 = 1;
                    i3 = 0;
                    break;
                case 'Z':
                    if (length2 != i2) {
                        obj = j.a;
                        i2 = 1;
                        i3 = 0;
                        break;
                    } else {
                        obj = j.b;
                        i2 = 1;
                        i3 = 0;
                        break;
                    }
                case 'a':
                    obj = new g(9, amPmStrings);
                    i2 = 1;
                    i3 = 0;
                    break;
                case 'd':
                    obj = selectNumberRule(5, length2);
                    i2 = 1;
                    i3 = 0;
                    break;
                case 'h':
                    obj = new k(selectNumberRule(10, length2));
                    i2 = 1;
                    i3 = 0;
                    break;
                case 'k':
                    obj = new l(selectNumberRule(11, length2));
                    i2 = 1;
                    i3 = 0;
                    break;
                case 'm':
                    obj = selectNumberRule(12, length2);
                    i2 = 1;
                    i3 = 0;
                    break;
                case 's':
                    obj = selectNumberRule(13, length2);
                    i2 = 1;
                    i3 = 0;
                    break;
                case 'w':
                    obj = selectNumberRule(3, length2);
                    i2 = 1;
                    i3 = 0;
                    break;
                case 'y':
                    if (length2 < 4) {
                        obj = o.a;
                        i2 = 1;
                        i3 = 0;
                        break;
                    } else {
                        i2 = 1;
                        obj = selectNumberRule(1, length2);
                        i3 = 0;
                        break;
                    }
                case 'z':
                    if (length2 < 4) {
                        obj = new i(this.mTimeZone, this.mTimeZoneForced, this.mLocale, 0);
                        i3 = 0;
                        i2 = 1;
                        break;
                    } else {
                        obj = new i(this.mTimeZone, this.mTimeZoneForced, this.mLocale, i2);
                        i3 = 0;
                        break;
                    }
                default:
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("Illegal pattern component: ");
                    stringBuffer.append(parseToken);
                    throw new IllegalArgumentException(stringBuffer.toString());
            }
            arrayList.add(obj);
            i4 = i5 + 1;
        }
        return arrayList;
    }

    protected String parseToken(String str, int[] iArr) {
        StrBuilder strBuilder = new StrBuilder();
        int i2 = iArr[0];
        int length = str.length();
        char charAt = str.charAt(i2);
        if ((charAt >= 'A' && charAt <= 'Z') || (charAt >= 'a' && charAt <= 'z')) {
            strBuilder.append(charAt);
            while (true) {
                int i3 = i2 + 1;
                if (i3 >= length || str.charAt(i3) != charAt) {
                    break;
                }
                strBuilder.append(charAt);
                i2 = i3;
            }
        } else {
            strBuilder.append('\'');
            boolean z = false;
            while (i2 < length) {
                char charAt2 = str.charAt(i2);
                if (charAt2 != '\'') {
                    if (!z && ((charAt2 >= 'A' && charAt2 <= 'Z') || (charAt2 >= 'a' && charAt2 <= 'z'))) {
                        i2--;
                        break;
                    }
                    strBuilder.append(charAt2);
                } else {
                    int i4 = i2 + 1;
                    if (i4 >= length || str.charAt(i4) != '\'') {
                        z = !z;
                    } else {
                        strBuilder.append(charAt2);
                        i2 = i4;
                    }
                }
                i2++;
            }
        }
        iArr[0] = i2;
        return strBuilder.toString();
    }

    protected b selectNumberRule(int i2, int i3) {
        switch (i3) {
            case 1:
                return new q(i2);
            case 2:
                return new n(i2);
            default:
                return new c(i2, i3);
        }
    }

    @Override // java.text.Format
    public StringBuffer format(Object obj, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        if (obj instanceof Date) {
            return format((Date) obj, stringBuffer);
        }
        if (obj instanceof Calendar) {
            return format((Calendar) obj, stringBuffer);
        }
        if (obj instanceof Long) {
            return format(((Long) obj).longValue(), stringBuffer);
        }
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("Unknown class: ");
        stringBuffer2.append(obj == null ? "<null>" : obj.getClass().getName());
        throw new IllegalArgumentException(stringBuffer2.toString());
    }

    public String format(long j2) {
        return format(new Date(j2));
    }

    public String format(Date date) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar(this.mTimeZone, this.mLocale);
        gregorianCalendar.setTime(date);
        return applyRules(gregorianCalendar, new StringBuffer(this.h)).toString();
    }

    public String format(Calendar calendar) {
        return format(calendar, new StringBuffer(this.h)).toString();
    }

    public StringBuffer format(long j2, StringBuffer stringBuffer) {
        return format(new Date(j2), stringBuffer);
    }

    public StringBuffer format(Date date, StringBuffer stringBuffer) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar(this.mTimeZone);
        gregorianCalendar.setTime(date);
        return applyRules(gregorianCalendar, stringBuffer);
    }

    public StringBuffer format(Calendar calendar, StringBuffer stringBuffer) {
        if (this.mTimeZoneForced) {
            calendar.getTime();
            calendar = (Calendar) calendar.clone();
            calendar.setTimeZone(this.mTimeZone);
        }
        return applyRules(calendar, stringBuffer);
    }

    protected StringBuffer applyRules(Calendar calendar, StringBuffer stringBuffer) {
        for (e eVar : this.g) {
            eVar.a(stringBuffer, calendar);
        }
        return stringBuffer;
    }

    @Override // java.text.Format
    public Object parseObject(String str, ParsePosition parsePosition) {
        parsePosition.setIndex(0);
        parsePosition.setErrorIndex(0);
        return null;
    }

    public String getPattern() {
        return this.mPattern;
    }

    public TimeZone getTimeZone() {
        return this.mTimeZone;
    }

    public boolean getTimeZoneOverridesCalendar() {
        return this.mTimeZoneForced;
    }

    public Locale getLocale() {
        return this.mLocale;
    }

    public int getMaxLengthEstimate() {
        return this.h;
    }

    public boolean equals(Object obj) {
        TimeZone timeZone;
        TimeZone timeZone2;
        Locale locale;
        Locale locale2;
        if (!(obj instanceof FastDateFormat)) {
            return false;
        }
        FastDateFormat fastDateFormat = (FastDateFormat) obj;
        String str = this.mPattern;
        String str2 = fastDateFormat.mPattern;
        return (str == str2 || str.equals(str2)) && ((timeZone = this.mTimeZone) == (timeZone2 = fastDateFormat.mTimeZone) || timeZone.equals(timeZone2)) && (((locale = this.mLocale) == (locale2 = fastDateFormat.mLocale) || locale.equals(locale2)) && this.mTimeZoneForced == fastDateFormat.mTimeZoneForced && this.mLocaleForced == fastDateFormat.mLocaleForced);
    }

    public int hashCode() {
        return this.mPattern.hashCode() + 0 + this.mTimeZone.hashCode() + (this.mTimeZoneForced ? 1 : 0) + this.mLocale.hashCode() + (this.mLocaleForced ? 1 : 0);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("FastDateFormat[");
        stringBuffer.append(this.mPattern);
        stringBuffer.append("]");
        return stringBuffer.toString();
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        init();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class a implements e {
        private final char a;

        @Override // org.apache.commons.lang.time.FastDateFormat.e
        public int a() {
            return 1;
        }

        a(char c) {
            this.a = c;
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.e
        public void a(StringBuffer stringBuffer, Calendar calendar) {
            stringBuffer.append(this.a);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class f implements e {
        private final String a;

        f(String str) {
            this.a = str;
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.e
        public int a() {
            return this.a.length();
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.e
        public void a(StringBuffer stringBuffer, Calendar calendar) {
            stringBuffer.append(this.a);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class g implements e {
        private final int a;
        private final String[] b;

        g(int i, String[] strArr) {
            this.a = i;
            this.b = strArr;
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.e
        public int a() {
            int length = this.b.length;
            int i = 0;
            while (true) {
                length--;
                if (length < 0) {
                    return i;
                }
                int length2 = this.b[length].length();
                if (length2 > i) {
                    i = length2;
                }
            }
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.e
        public void a(StringBuffer stringBuffer, Calendar calendar) {
            stringBuffer.append(this.b[calendar.get(this.a)]);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class q implements b {
        private final int a;

        @Override // org.apache.commons.lang.time.FastDateFormat.e
        public int a() {
            return 4;
        }

        q(int i) {
            this.a = i;
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.e
        public void a(StringBuffer stringBuffer, Calendar calendar) {
            a(stringBuffer, calendar.get(this.a));
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.b
        public final void a(StringBuffer stringBuffer, int i) {
            if (i < 10) {
                stringBuffer.append((char) (i + 48));
            } else if (i < 100) {
                stringBuffer.append((char) ((i / 10) + 48));
                stringBuffer.append((char) ((i % 10) + 48));
            } else {
                stringBuffer.append(Integer.toString(i));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class p implements b {
        static final p a = new p();

        @Override // org.apache.commons.lang.time.FastDateFormat.e
        public int a() {
            return 2;
        }

        p() {
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.e
        public void a(StringBuffer stringBuffer, Calendar calendar) {
            a(stringBuffer, calendar.get(2) + 1);
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.b
        public final void a(StringBuffer stringBuffer, int i) {
            if (i < 10) {
                stringBuffer.append((char) (i + 48));
                return;
            }
            stringBuffer.append((char) ((i / 10) + 48));
            stringBuffer.append((char) ((i % 10) + 48));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class c implements b {
        private final int a;
        private final int b;

        @Override // org.apache.commons.lang.time.FastDateFormat.e
        public int a() {
            return 4;
        }

        c(int i, int i2) {
            if (i2 >= 3) {
                this.a = i;
                this.b = i2;
                return;
            }
            throw new IllegalArgumentException();
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.e
        public void a(StringBuffer stringBuffer, Calendar calendar) {
            a(stringBuffer, calendar.get(this.a));
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.b
        public final void a(StringBuffer stringBuffer, int i) {
            int i2;
            if (i < 100) {
                int i3 = this.b;
                while (true) {
                    i3--;
                    if (i3 >= 2) {
                        stringBuffer.append('0');
                    } else {
                        stringBuffer.append((char) ((i / 10) + 48));
                        stringBuffer.append((char) ((i % 10) + 48));
                        return;
                    }
                }
            } else {
                if (i < 1000) {
                    i2 = 3;
                } else {
                    Validate.isTrue(i > -1, "Negative values should not be possible", i);
                    i2 = Integer.toString(i).length();
                }
                int i4 = this.b;
                while (true) {
                    i4--;
                    if (i4 >= i2) {
                        stringBuffer.append('0');
                    } else {
                        stringBuffer.append(Integer.toString(i));
                        return;
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class n implements b {
        private final int a;

        @Override // org.apache.commons.lang.time.FastDateFormat.e
        public int a() {
            return 2;
        }

        n(int i) {
            this.a = i;
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.e
        public void a(StringBuffer stringBuffer, Calendar calendar) {
            a(stringBuffer, calendar.get(this.a));
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.b
        public final void a(StringBuffer stringBuffer, int i) {
            if (i < 100) {
                stringBuffer.append((char) ((i / 10) + 48));
                stringBuffer.append((char) ((i % 10) + 48));
                return;
            }
            stringBuffer.append(Integer.toString(i));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class o implements b {
        static final o a = new o();

        @Override // org.apache.commons.lang.time.FastDateFormat.e
        public int a() {
            return 2;
        }

        o() {
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.e
        public void a(StringBuffer stringBuffer, Calendar calendar) {
            a(stringBuffer, calendar.get(1) % 100);
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.b
        public final void a(StringBuffer stringBuffer, int i) {
            stringBuffer.append((char) ((i / 10) + 48));
            stringBuffer.append((char) ((i % 10) + 48));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class m implements b {
        static final m a = new m();

        @Override // org.apache.commons.lang.time.FastDateFormat.e
        public int a() {
            return 2;
        }

        m() {
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.e
        public void a(StringBuffer stringBuffer, Calendar calendar) {
            a(stringBuffer, calendar.get(2) + 1);
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.b
        public final void a(StringBuffer stringBuffer, int i) {
            stringBuffer.append((char) ((i / 10) + 48));
            stringBuffer.append((char) ((i % 10) + 48));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class k implements b {
        private final b a;

        k(b bVar) {
            this.a = bVar;
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.e
        public int a() {
            return this.a.a();
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.e
        public void a(StringBuffer stringBuffer, Calendar calendar) {
            int i = calendar.get(10);
            if (i == 0) {
                i = calendar.getLeastMaximum(10) + 1;
            }
            this.a.a(stringBuffer, i);
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.b
        public void a(StringBuffer stringBuffer, int i) {
            this.a.a(stringBuffer, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class l implements b {
        private final b a;

        l(b bVar) {
            this.a = bVar;
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.e
        public int a() {
            return this.a.a();
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.e
        public void a(StringBuffer stringBuffer, Calendar calendar) {
            int i = calendar.get(11);
            if (i == 0) {
                i = calendar.getMaximum(11) + 1;
            }
            this.a.a(stringBuffer, i);
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.b
        public void a(StringBuffer stringBuffer, int i) {
            this.a.a(stringBuffer, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class i implements e {
        private final TimeZone a;
        private final boolean b;
        private final Locale c;
        private final int d;
        private final String e;
        private final String f;

        i(TimeZone timeZone, boolean z, Locale locale, int i) {
            this.a = timeZone;
            this.b = z;
            this.c = locale;
            this.d = i;
            if (z) {
                this.e = FastDateFormat.a(timeZone, false, i, locale);
                this.f = FastDateFormat.a(timeZone, true, i, locale);
                return;
            }
            this.e = null;
            this.f = null;
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.e
        public int a() {
            if (this.b) {
                return Math.max(this.e.length(), this.f.length());
            }
            return this.d == 0 ? 4 : 40;
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.e
        public void a(StringBuffer stringBuffer, Calendar calendar) {
            if (!this.b) {
                TimeZone timeZone = calendar.getTimeZone();
                if (!timeZone.useDaylightTime() || calendar.get(16) == 0) {
                    stringBuffer.append(FastDateFormat.a(timeZone, false, this.d, this.c));
                } else {
                    stringBuffer.append(FastDateFormat.a(timeZone, true, this.d, this.c));
                }
            } else if (!this.a.useDaylightTime() || calendar.get(16) == 0) {
                stringBuffer.append(this.e);
            } else {
                stringBuffer.append(this.f);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class j implements e {
        static final j a = new j(true);
        static final j b = new j(false);
        final boolean c;

        @Override // org.apache.commons.lang.time.FastDateFormat.e
        public int a() {
            return 5;
        }

        j(boolean z) {
            this.c = z;
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.e
        public void a(StringBuffer stringBuffer, Calendar calendar) {
            int i = calendar.get(15) + calendar.get(16);
            if (i < 0) {
                stringBuffer.append('-');
                i = -i;
            } else {
                stringBuffer.append('+');
            }
            int i2 = i / 3600000;
            stringBuffer.append((char) ((i2 / 10) + 48));
            stringBuffer.append((char) ((i2 % 10) + 48));
            if (this.c) {
                stringBuffer.append(':');
            }
            int i3 = (i / 60000) - (i2 * 60);
            stringBuffer.append((char) ((i3 / 10) + 48));
            stringBuffer.append((char) ((i3 % 10) + 48));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class h {
        private final TimeZone a;
        private final int b;
        private final Locale c;

        h(TimeZone timeZone, boolean z, int i, Locale locale) {
            this.a = timeZone;
            this.b = z ? i | Integer.MIN_VALUE : i;
            this.c = locale;
        }

        public int hashCode() {
            return (this.b * 31) + this.c.hashCode();
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof h)) {
                return false;
            }
            h hVar = (h) obj;
            return this.a.equals(hVar.a) && this.b == hVar.b && this.c.equals(hVar.c);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class d {
        private final Object a;
        private final Object b;

        public d(Object obj, Object obj2) {
            this.a = obj;
            this.b = obj2;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof d)) {
                return false;
            }
            d dVar = (d) obj;
            Object obj2 = this.a;
            if (obj2 != null ? obj2.equals(dVar.a) : dVar.a == null) {
                Object obj3 = this.b;
                if (obj3 == null) {
                    if (dVar.b == null) {
                        return true;
                    }
                } else if (obj3.equals(dVar.b)) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            Object obj = this.a;
            int i = 0;
            int hashCode = obj == null ? 0 : obj.hashCode();
            Object obj2 = this.b;
            if (obj2 != null) {
                i = obj2.hashCode();
            }
            return hashCode + i;
        }

        public String toString() {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("[");
            stringBuffer.append(this.a);
            stringBuffer.append(':');
            stringBuffer.append(this.b);
            stringBuffer.append(']');
            return stringBuffer.toString();
        }
    }
}
