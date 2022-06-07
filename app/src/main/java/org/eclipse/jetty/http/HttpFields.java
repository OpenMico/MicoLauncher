package org.eclipse.jetty.http;

import com.xiaomi.accountsdk.utils.DateUtils;
import io.netty.handler.codec.http.cookie.CookieHeaderNames;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.eclipse.jetty.io.Buffer;
import org.eclipse.jetty.io.BufferCache;
import org.eclipse.jetty.io.BufferDateCache;
import org.eclipse.jetty.io.BufferUtil;
import org.eclipse.jetty.io.ByteArrayBuffer;
import org.eclipse.jetty.util.LazyList;
import org.eclipse.jetty.util.QuotedStringTokenizer;
import org.eclipse.jetty.util.StringMap;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes5.dex */
public class HttpFields {
    public static final String __COOKIE_DELIM = "\"\\\n\r\t\f\b%+ ;=";
    public static final String __separators = ", \t";
    private final ArrayList<Field> _fields = new ArrayList<>(20);
    private final HashMap<Buffer, Field> _names = new HashMap<>(32);
    private static final Logger LOG = Log.getLogger(HttpFields.class);
    public static final TimeZone __GMT = TimeZone.getTimeZone("GMT");
    public static final BufferDateCache __dateCache = new BufferDateCache("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
    private static final String[] DAYS = {"Sat", "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    private static final String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec", "Jan"};
    private static final ThreadLocal<DateGenerator> __dateGenerator = new ThreadLocal<DateGenerator>() { // from class: org.eclipse.jetty.http.HttpFields.1
        @Override // java.lang.ThreadLocal
        public DateGenerator initialValue() {
            return new DateGenerator();
        }
    };
    private static final String[] __dateReceiveFmt = {DateUtils.PATTERN_RFC1123, "EEE, dd-MMM-yy HH:mm:ss", "EEE MMM dd HH:mm:ss yyyy", "EEE, dd MMM yyyy HH:mm:ss", "EEE dd MMM yyyy HH:mm:ss zzz", "EEE dd MMM yyyy HH:mm:ss", "EEE MMM dd yyyy HH:mm:ss zzz", "EEE MMM dd yyyy HH:mm:ss", "EEE MMM-dd-yyyy HH:mm:ss zzz", "EEE MMM-dd-yyyy HH:mm:ss", "dd MMM yyyy HH:mm:ss zzz", "dd MMM yyyy HH:mm:ss", "dd-MMM-yy HH:mm:ss zzz", "dd-MMM-yy HH:mm:ss", "MMM dd HH:mm:ss yyyy zzz", "MMM dd HH:mm:ss yyyy", "EEE MMM dd HH:mm:ss yyyy zzz", "EEE, MMM dd HH:mm:ss yyyy zzz", "EEE, MMM dd HH:mm:ss yyyy", "EEE, dd-MMM-yy HH:mm:ss zzz", "EEE dd-MMM-yy HH:mm:ss zzz", "EEE dd-MMM-yy HH:mm:ss"};
    private static final ThreadLocal<DateParser> __dateParser = new ThreadLocal<DateParser>() { // from class: org.eclipse.jetty.http.HttpFields.2
        @Override // java.lang.ThreadLocal
        public DateParser initialValue() {
            return new DateParser();
        }
    };
    public static final String __01Jan1970 = formatDate(0);
    public static final Buffer __01Jan1970_BUFFER = new ByteArrayBuffer(__01Jan1970);
    public static final String __01Jan1970_COOKIE = formatCookieDate(0).trim();
    private static ConcurrentMap<String, Buffer> __cache = new ConcurrentHashMap();
    private static int __cacheSize = Integer.getInteger("org.eclipse.jetty.http.HttpFields.CACHE", 2000).intValue();
    private static final Float __one = new Float("1.0");
    private static final Float __zero = new Float("0.0");
    private static final StringMap __qualities = new StringMap();

    static {
        __GMT.setID("GMT");
        __dateCache.setTimeZone(__GMT);
        __qualities.put((String) null, (Object) __one);
        __qualities.put("1.0", (Object) __one);
        __qualities.put("1", (Object) __one);
        __qualities.put("0.9", (Object) new Float("0.9"));
        __qualities.put("0.8", (Object) new Float("0.8"));
        __qualities.put("0.7", (Object) new Float("0.7"));
        __qualities.put("0.66", (Object) new Float("0.66"));
        __qualities.put("0.6", (Object) new Float("0.6"));
        __qualities.put("0.5", (Object) new Float("0.5"));
        __qualities.put("0.4", (Object) new Float("0.4"));
        __qualities.put("0.33", (Object) new Float("0.33"));
        __qualities.put("0.3", (Object) new Float("0.3"));
        __qualities.put("0.2", (Object) new Float("0.2"));
        __qualities.put("0.1", (Object) new Float("0.1"));
        __qualities.put("0", (Object) __zero);
        __qualities.put("0.0", (Object) __zero);
    }

    /* loaded from: classes5.dex */
    public static class DateGenerator {
        private final StringBuilder buf;
        private final GregorianCalendar gc;

        private DateGenerator() {
            this.buf = new StringBuilder(32);
            this.gc = new GregorianCalendar(HttpFields.__GMT);
        }

        public String formatDate(long j) {
            this.buf.setLength(0);
            this.gc.setTimeInMillis(j);
            int i = this.gc.get(7);
            int i2 = this.gc.get(5);
            int i3 = this.gc.get(2);
            int i4 = this.gc.get(1);
            int i5 = this.gc.get(11);
            int i6 = this.gc.get(12);
            int i7 = this.gc.get(13);
            this.buf.append(HttpFields.DAYS[i]);
            this.buf.append(StringUtil.COMMA);
            this.buf.append(' ');
            org.eclipse.jetty.util.StringUtil.append2digits(this.buf, i2);
            this.buf.append(' ');
            this.buf.append(HttpFields.MONTHS[i3]);
            this.buf.append(' ');
            org.eclipse.jetty.util.StringUtil.append2digits(this.buf, i4 / 100);
            org.eclipse.jetty.util.StringUtil.append2digits(this.buf, i4 % 100);
            this.buf.append(' ');
            org.eclipse.jetty.util.StringUtil.append2digits(this.buf, i5);
            this.buf.append(':');
            org.eclipse.jetty.util.StringUtil.append2digits(this.buf, i6);
            this.buf.append(':');
            org.eclipse.jetty.util.StringUtil.append2digits(this.buf, i7);
            this.buf.append(" GMT");
            return this.buf.toString();
        }

        public void formatCookieDate(StringBuilder sb, long j) {
            this.gc.setTimeInMillis(j);
            int i = this.gc.get(7);
            int i2 = this.gc.get(5);
            int i3 = this.gc.get(2);
            int i4 = this.gc.get(1) % 10000;
            int i5 = (int) ((j / 1000) % 86400);
            int i6 = i5 % 60;
            int i7 = i5 / 60;
            sb.append(HttpFields.DAYS[i]);
            sb.append(StringUtil.COMMA);
            sb.append(' ');
            org.eclipse.jetty.util.StringUtil.append2digits(sb, i2);
            sb.append('-');
            sb.append(HttpFields.MONTHS[i3]);
            sb.append('-');
            org.eclipse.jetty.util.StringUtil.append2digits(sb, i4 / 100);
            org.eclipse.jetty.util.StringUtil.append2digits(sb, i4 % 100);
            sb.append(' ');
            org.eclipse.jetty.util.StringUtil.append2digits(sb, i7 / 60);
            sb.append(':');
            org.eclipse.jetty.util.StringUtil.append2digits(sb, i7 % 60);
            sb.append(':');
            org.eclipse.jetty.util.StringUtil.append2digits(sb, i6);
            sb.append(" GMT");
        }
    }

    public static String formatDate(long j) {
        return __dateGenerator.get().formatDate(j);
    }

    public static void formatCookieDate(StringBuilder sb, long j) {
        __dateGenerator.get().formatCookieDate(sb, j);
    }

    public static String formatCookieDate(long j) {
        StringBuilder sb = new StringBuilder(28);
        formatCookieDate(sb, j);
        return sb.toString();
    }

    /* loaded from: classes5.dex */
    public static class DateParser {
        final SimpleDateFormat[] _dateReceive;

        private DateParser() {
            this._dateReceive = new SimpleDateFormat[HttpFields.__dateReceiveFmt.length];
        }

        long parse(String str) {
            int i = 0;
            int i2 = 0;
            while (true) {
                SimpleDateFormat[] simpleDateFormatArr = this._dateReceive;
                if (i2 < simpleDateFormatArr.length) {
                    if (simpleDateFormatArr[i2] == null) {
                        simpleDateFormatArr[i2] = new SimpleDateFormat(HttpFields.__dateReceiveFmt[i2], Locale.US);
                        this._dateReceive[i2].setTimeZone(HttpFields.__GMT);
                    }
                    try {
                        continue;
                        return ((Date) this._dateReceive[i2].parseObject(str)).getTime();
                    } catch (Exception unused) {
                        i2++;
                    }
                } else if (!str.endsWith(" GMT")) {
                    return -1L;
                } else {
                    String substring = str.substring(i, str.length() - 4);
                    while (true) {
                        SimpleDateFormat[] simpleDateFormatArr2 = this._dateReceive;
                        if (i >= simpleDateFormatArr2.length) {
                            return -1L;
                        }
                        try {
                            return ((Date) simpleDateFormatArr2[i].parseObject(substring)).getTime();
                        } catch (Exception unused2) {
                            i++;
                        }
                    }
                }
            }
        }
    }

    public static long parseDate(String str) {
        return __dateParser.get().parse(str);
    }

    private Buffer convertValue(String str) {
        Buffer buffer = __cache.get(str);
        if (buffer != null) {
            return buffer;
        }
        try {
            ByteArrayBuffer byteArrayBuffer = new ByteArrayBuffer(str, "ISO-8859-1");
            if (__cacheSize > 0) {
                if (__cache.size() > __cacheSize) {
                    __cache.clear();
                }
                Buffer putIfAbsent = __cache.putIfAbsent(str, byteArrayBuffer);
                if (putIfAbsent != null) {
                    return putIfAbsent;
                }
            }
            return byteArrayBuffer;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public Collection<String> getFieldNamesCollection() {
        ArrayList arrayList = new ArrayList(this._fields.size());
        Iterator<Field> it = this._fields.iterator();
        while (it.hasNext()) {
            Field next = it.next();
            if (next != null) {
                arrayList.add(BufferUtil.to8859_1_String(next._name));
            }
        }
        return arrayList;
    }

    public Enumeration<String> getFieldNames() {
        final Enumeration enumeration = Collections.enumeration(this._names.keySet());
        return new Enumeration<String>() { // from class: org.eclipse.jetty.http.HttpFields.3
            @Override // java.util.Enumeration
            public String nextElement() {
                return enumeration.nextElement().toString();
            }

            @Override // java.util.Enumeration
            public boolean hasMoreElements() {
                return enumeration.hasMoreElements();
            }
        };
    }

    public int size() {
        return this._fields.size();
    }

    public Field getField(int i) {
        return this._fields.get(i);
    }

    private Field getField(String str) {
        return this._names.get(HttpHeaders.CACHE.lookup(str));
    }

    private Field getField(Buffer buffer) {
        return this._names.get(HttpHeaders.CACHE.lookup(buffer));
    }

    public boolean containsKey(Buffer buffer) {
        return this._names.containsKey(HttpHeaders.CACHE.lookup(buffer));
    }

    public boolean containsKey(String str) {
        return this._names.containsKey(HttpHeaders.CACHE.lookup(str));
    }

    public String getStringField(String str) {
        Field field = getField(str);
        if (field == null) {
            return null;
        }
        return field.getValue();
    }

    public String getStringField(Buffer buffer) {
        Field field = getField(buffer);
        if (field == null) {
            return null;
        }
        return field.getValue();
    }

    public Buffer get(Buffer buffer) {
        Field field = getField(buffer);
        if (field == null) {
            return null;
        }
        return field._value;
    }

    public Collection<String> getValuesCollection(String str) {
        Field field = getField(str);
        if (field == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        while (field != null) {
            arrayList.add(field.getValue());
            field = field._next;
        }
        return arrayList;
    }

    public Enumeration<String> getValues(String str) {
        final Field field = getField(str);
        if (field == null) {
            return Collections.enumeration(Collections.emptyList());
        }
        return new Enumeration<String>() { // from class: org.eclipse.jetty.http.HttpFields.4
            Field f;

            {
                HttpFields.this = this;
                this.f = field;
            }

            @Override // java.util.Enumeration
            public boolean hasMoreElements() {
                return this.f != null;
            }

            @Override // java.util.Enumeration
            public String nextElement() throws NoSuchElementException {
                Field field2 = this.f;
                if (field2 != null) {
                    this.f = field2._next;
                    return field2.getValue();
                }
                throw new NoSuchElementException();
            }
        };
    }

    public Enumeration<String> getValues(Buffer buffer) {
        final Field field = getField(buffer);
        if (field == null) {
            return Collections.enumeration(Collections.emptyList());
        }
        return new Enumeration<String>() { // from class: org.eclipse.jetty.http.HttpFields.5
            Field f;

            {
                HttpFields.this = this;
                this.f = field;
            }

            @Override // java.util.Enumeration
            public boolean hasMoreElements() {
                return this.f != null;
            }

            @Override // java.util.Enumeration
            public String nextElement() throws NoSuchElementException {
                Field field2 = this.f;
                if (field2 != null) {
                    this.f = field2._next;
                    return field2.getValue();
                }
                throw new NoSuchElementException();
            }
        };
    }

    public Enumeration<String> getValues(String str, final String str2) {
        final Enumeration<String> values = getValues(str);
        if (values == null) {
            return null;
        }
        return new Enumeration<String>() { // from class: org.eclipse.jetty.http.HttpFields.6
            QuotedStringTokenizer tok = null;

            @Override // java.util.Enumeration
            public boolean hasMoreElements() {
                QuotedStringTokenizer quotedStringTokenizer = this.tok;
                if (quotedStringTokenizer != null && quotedStringTokenizer.hasMoreElements()) {
                    return true;
                }
                while (values.hasMoreElements()) {
                    this.tok = new QuotedStringTokenizer((String) values.nextElement(), str2, false, false);
                    if (this.tok.hasMoreElements()) {
                        return true;
                    }
                }
                this.tok = null;
                return false;
            }

            @Override // java.util.Enumeration
            public String nextElement() throws NoSuchElementException {
                if (hasMoreElements()) {
                    String str3 = (String) this.tok.nextElement();
                    return str3 != null ? str3.trim() : str3;
                }
                throw new NoSuchElementException();
            }
        };
    }

    public void put(String str, String str2) {
        if (str2 == null) {
            remove(str);
        } else {
            put(HttpHeaders.CACHE.lookup(str), convertValue(str2));
        }
    }

    public void put(Buffer buffer, String str) {
        put(HttpHeaders.CACHE.lookup(buffer), convertValue(str));
    }

    public void put(Buffer buffer, Buffer buffer2) {
        remove(buffer);
        if (buffer2 != null) {
            if (!(buffer instanceof BufferCache.CachedBuffer)) {
                buffer = HttpHeaders.CACHE.lookup(buffer);
            }
            if (!(buffer2 instanceof BufferCache.CachedBuffer)) {
                buffer2 = HttpHeaderValues.CACHE.lookup(buffer2).asImmutableBuffer();
            }
            Field field = new Field(buffer, buffer2);
            this._fields.add(field);
            this._names.put(buffer, field);
        }
    }

    public void put(String str, List<?> list) {
        if (list == null || list.size() == 0) {
            remove(str);
            return;
        }
        Buffer lookup = HttpHeaders.CACHE.lookup(str);
        Object obj = list.get(0);
        if (obj != null) {
            put(lookup, HttpHeaderValues.CACHE.lookup(obj.toString()));
        } else {
            remove(lookup);
        }
        if (list.size() > 1) {
            Iterator<?> it = list.iterator();
            it.next();
            while (it.hasNext()) {
                Object next = it.next();
                if (next != null) {
                    put(lookup, HttpHeaderValues.CACHE.lookup(next.toString()));
                }
            }
        }
    }

    public void add(String str, String str2) throws IllegalArgumentException {
        if (str2 != null) {
            add(HttpHeaders.CACHE.lookup(str), convertValue(str2));
        }
    }

    public void add(Buffer buffer, Buffer buffer2) throws IllegalArgumentException {
        if (buffer2 != null) {
            if (!(buffer instanceof BufferCache.CachedBuffer)) {
                buffer = HttpHeaders.CACHE.lookup(buffer);
            }
            Buffer asImmutableBuffer = buffer.asImmutableBuffer();
            if (!(buffer2 instanceof BufferCache.CachedBuffer) && HttpHeaderValues.hasKnownValues(HttpHeaders.CACHE.getOrdinal(asImmutableBuffer))) {
                buffer2 = HttpHeaderValues.CACHE.lookup(buffer2);
            }
            Buffer asImmutableBuffer2 = buffer2.asImmutableBuffer();
            Field field = null;
            for (Field field2 = this._names.get(asImmutableBuffer); field2 != null; field2 = field2._next) {
                field = field2;
            }
            Field field3 = new Field(asImmutableBuffer, asImmutableBuffer2);
            this._fields.add(field3);
            if (field != null) {
                field._next = field3;
            } else {
                this._names.put(asImmutableBuffer, field3);
            }
        } else {
            throw new IllegalArgumentException("null value");
        }
    }

    public void remove(String str) {
        remove(HttpHeaders.CACHE.lookup(str));
    }

    public void remove(Buffer buffer) {
        if (!(buffer instanceof BufferCache.CachedBuffer)) {
            buffer = HttpHeaders.CACHE.lookup(buffer);
        }
        for (Field remove = this._names.remove(buffer); remove != null; remove = remove._next) {
            this._fields.remove(remove);
        }
    }

    public long getLongField(String str) throws NumberFormatException {
        Field field = getField(str);
        if (field == null) {
            return -1L;
        }
        return field.getLongValue();
    }

    public long getLongField(Buffer buffer) throws NumberFormatException {
        Field field = getField(buffer);
        if (field == null) {
            return -1L;
        }
        return field.getLongValue();
    }

    public long getDateField(String str) {
        String valueParameters;
        Field field = getField(str);
        if (field == null || (valueParameters = valueParameters(BufferUtil.to8859_1_String(field._value), null)) == null) {
            return -1L;
        }
        long parse = __dateParser.get().parse(valueParameters);
        if (parse != -1) {
            return parse;
        }
        throw new IllegalArgumentException("Cannot convert date: " + valueParameters);
    }

    public void putLongField(Buffer buffer, long j) {
        put(buffer, BufferUtil.toBuffer(j));
    }

    public void putLongField(String str, long j) {
        put(HttpHeaders.CACHE.lookup(str), BufferUtil.toBuffer(j));
    }

    public void addLongField(String str, long j) {
        add(HttpHeaders.CACHE.lookup(str), BufferUtil.toBuffer(j));
    }

    public void addLongField(Buffer buffer, long j) {
        add(buffer, BufferUtil.toBuffer(j));
    }

    public void putDateField(Buffer buffer, long j) {
        put(buffer, new ByteArrayBuffer(formatDate(j)));
    }

    public void putDateField(String str, long j) {
        putDateField(HttpHeaders.CACHE.lookup(str), j);
    }

    public void addDateField(String str, long j) {
        add(HttpHeaders.CACHE.lookup(str), new ByteArrayBuffer(formatDate(j)));
    }

    public void addSetCookie(HttpCookie httpCookie) {
        addSetCookie(httpCookie.getName(), httpCookie.getValue(), httpCookie.getDomain(), httpCookie.getPath(), httpCookie.getMaxAge(), httpCookie.getComment(), httpCookie.isSecure(), httpCookie.isHttpOnly(), httpCookie.getVersion());
    }

    public void addSetCookie(String str, String str2, String str3, String str4, long j, String str5, boolean z, boolean z2, int i) {
        boolean z3;
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("Bad cookie name");
        }
        StringBuilder sb = new StringBuilder(128);
        QuotedStringTokenizer.quoteIfNeeded(sb, str, __COOKIE_DELIM);
        sb.append('=');
        String sb2 = sb.toString();
        if (str2 != null && str2.length() > 0) {
            QuotedStringTokenizer.quoteIfNeeded(sb, str2, __COOKIE_DELIM);
        }
        if (str5 != null && str5.length() > 0) {
            sb.append(";Comment=");
            QuotedStringTokenizer.quoteIfNeeded(sb, str5, __COOKIE_DELIM);
        }
        boolean z4 = false;
        if (str4 == null || str4.length() <= 0) {
            z3 = false;
        } else {
            sb.append(";Path=");
            if (str4.trim().startsWith("\"")) {
                sb.append(str4);
            } else {
                QuotedStringTokenizer.quoteIfNeeded(sb, str4, __COOKIE_DELIM);
            }
            z3 = true;
        }
        if (str3 != null && str3.length() > 0) {
            sb.append(";Domain=");
            QuotedStringTokenizer.quoteIfNeeded(sb, str3.toLowerCase(Locale.ENGLISH), __COOKIE_DELIM);
            z4 = true;
        }
        int i2 = (j > 0L ? 1 : (j == 0L ? 0 : -1));
        if (i2 >= 0) {
            sb.append(";Expires=");
            if (i2 == 0) {
                sb.append(__01Jan1970_COOKIE);
            } else {
                formatCookieDate(sb, System.currentTimeMillis() + (1000 * j));
            }
            if (i > 0) {
                sb.append(";Max-Age=");
                sb.append(j);
            }
        }
        if (z) {
            sb.append(";Secure");
        }
        if (z2) {
            sb.append(";HttpOnly");
        }
        String sb3 = sb.toString();
        Field field = null;
        for (Field field2 = getField("Set-Cookie"); field2 != null; field2 = field2._next) {
            String obj = field2._value == null ? null : field2._value.toString();
            if (obj != null && obj.startsWith(sb2)) {
                if (z4 || obj.contains(CookieHeaderNames.DOMAIN)) {
                    if (z4) {
                        if (!obj.contains("Domain=" + str3)) {
                            continue;
                        }
                    } else {
                        continue;
                    }
                }
                if (z3 || obj.contains(CookieHeaderNames.PATH)) {
                    if (z3) {
                        if (obj.contains("Path=" + str4)) {
                        }
                    } else {
                        continue;
                    }
                }
                this._fields.remove(field2);
                if (field == null) {
                    this._names.put(HttpHeaders.SET_COOKIE_BUFFER, field2._next);
                } else {
                    field._next = field2._next;
                }
                add(HttpHeaders.SET_COOKIE_BUFFER, new ByteArrayBuffer(sb3));
                put(HttpHeaders.EXPIRES_BUFFER, __01Jan1970_BUFFER);
            }
            field = field2;
        }
        add(HttpHeaders.SET_COOKIE_BUFFER, new ByteArrayBuffer(sb3));
        put(HttpHeaders.EXPIRES_BUFFER, __01Jan1970_BUFFER);
    }

    public void putTo(Buffer buffer) throws IOException {
        for (int i = 0; i < this._fields.size(); i++) {
            Field field = this._fields.get(i);
            if (field != null) {
                field.putTo(buffer);
            }
        }
        BufferUtil.putCRLF(buffer);
    }

    public String toString() {
        try {
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < this._fields.size(); i++) {
                Field field = this._fields.get(i);
                if (field != null) {
                    String name = field.getName();
                    if (name != null) {
                        stringBuffer.append(name);
                    }
                    stringBuffer.append(": ");
                    String value = field.getValue();
                    if (value != null) {
                        stringBuffer.append(value);
                    }
                    stringBuffer.append("\r\n");
                }
            }
            stringBuffer.append("\r\n");
            return stringBuffer.toString();
        } catch (Exception e) {
            LOG.warn(e);
            return e.toString();
        }
    }

    public void clear() {
        this._fields.clear();
        this._names.clear();
    }

    public void add(HttpFields httpFields) {
        if (httpFields != null) {
            Enumeration<String> fieldNames = httpFields.getFieldNames();
            while (fieldNames.hasMoreElements()) {
                String nextElement = fieldNames.nextElement();
                Enumeration<String> values = httpFields.getValues(nextElement);
                while (values.hasMoreElements()) {
                    add(nextElement, values.nextElement());
                }
            }
        }
    }

    public static String valueParameters(String str, Map<String, String> map) {
        if (str == null) {
            return null;
        }
        int indexOf = str.indexOf(59);
        if (indexOf < 0) {
            return str;
        }
        if (map == null) {
            return str.substring(0, indexOf).trim();
        }
        QuotedStringTokenizer quotedStringTokenizer = new QuotedStringTokenizer(str.substring(indexOf), ";", false, true);
        while (quotedStringTokenizer.hasMoreTokens()) {
            QuotedStringTokenizer quotedStringTokenizer2 = new QuotedStringTokenizer(quotedStringTokenizer.nextToken(), "= ");
            if (quotedStringTokenizer2.hasMoreTokens()) {
                map.put(quotedStringTokenizer2.nextToken(), quotedStringTokenizer2.hasMoreTokens() ? quotedStringTokenizer2.nextToken() : null);
            }
        }
        return str.substring(0, indexOf).trim();
    }

    public static Float getQuality(String str) {
        if (str == null) {
            return __zero;
        }
        int indexOf = str.indexOf(";");
        int i = indexOf + 1;
        if (indexOf < 0 || i == str.length()) {
            return __one;
        }
        int i2 = i + 1;
        if (str.charAt(i) == 'q') {
            int i3 = i2 + 1;
            Map.Entry entry = __qualities.getEntry(str, i3, str.length() - i3);
            if (entry != null) {
                return (Float) entry.getValue();
            }
        }
        HashMap hashMap = new HashMap(3);
        valueParameters(str, hashMap);
        String str2 = (String) hashMap.get("q");
        Float f = (Float) __qualities.get(str2);
        if (f != null) {
            return f;
        }
        try {
            return new Float(str2);
        } catch (Exception unused) {
            return __one;
        }
    }

    public static List qualityList(Enumeration enumeration) {
        if (enumeration == null || !enumeration.hasMoreElements()) {
            return Collections.EMPTY_LIST;
        }
        Object obj = null;
        Object obj2 = null;
        while (enumeration.hasMoreElements()) {
            String obj3 = enumeration.nextElement().toString();
            Float quality = getQuality(obj3);
            if (quality.floatValue() >= 0.001d) {
                obj = LazyList.add(obj, obj3);
                obj2 = LazyList.add(obj2, quality);
            }
        }
        List list = LazyList.getList(obj, false);
        if (list.size() < 2) {
            return list;
        }
        List list2 = LazyList.getList(obj2, false);
        Float f = __zero;
        int size = list.size();
        while (true) {
            int i = size - 1;
            if (size > 0) {
                Float f2 = (Float) list2.get(i);
                if (f.compareTo(f2) > 0) {
                    Object obj4 = list.get(i);
                    int i2 = i + 1;
                    list.set(i, list.get(i2));
                    list.set(i2, obj4);
                    list2.set(i, list2.get(i2));
                    list2.set(i2, f2);
                    f = __zero;
                    size = list.size();
                } else {
                    f = f2;
                    size = i;
                }
            } else {
                list2.clear();
                return list;
            }
        }
    }

    /* loaded from: classes5.dex */
    public static final class Field {
        private Buffer _name;
        private Field _next;
        private Buffer _value;

        private Field(Buffer buffer, Buffer buffer2) {
            this._name = buffer;
            this._value = buffer2;
            this._next = null;
        }

        public void putTo(Buffer buffer) throws IOException {
            Buffer buffer2 = this._name;
            int i = -1;
            if ((buffer2 instanceof BufferCache.CachedBuffer ? ((BufferCache.CachedBuffer) buffer2).getOrdinal() : -1) >= 0) {
                buffer.put(this._name);
            } else {
                int index = this._name.getIndex();
                int putIndex = this._name.putIndex();
                while (index < putIndex) {
                    index++;
                    byte peek = this._name.peek(index);
                    if (!(peek == 10 || peek == 13 || peek == 58)) {
                        buffer.put(peek);
                    }
                }
            }
            buffer.put((byte) 58);
            buffer.put((byte) 32);
            Buffer buffer3 = this._value;
            if (buffer3 instanceof BufferCache.CachedBuffer) {
                i = ((BufferCache.CachedBuffer) buffer3).getOrdinal();
            }
            if (i >= 0) {
                buffer.put(this._value);
            } else {
                int index2 = this._value.getIndex();
                int putIndex2 = this._value.putIndex();
                while (index2 < putIndex2) {
                    index2++;
                    byte peek2 = this._value.peek(index2);
                    if (!(peek2 == 10 || peek2 == 13)) {
                        buffer.put(peek2);
                    }
                }
            }
            BufferUtil.putCRLF(buffer);
        }

        public String getName() {
            return BufferUtil.to8859_1_String(this._name);
        }

        Buffer getNameBuffer() {
            return this._name;
        }

        public int getNameOrdinal() {
            return HttpHeaders.CACHE.getOrdinal(this._name);
        }

        public String getValue() {
            return BufferUtil.to8859_1_String(this._value);
        }

        public Buffer getValueBuffer() {
            return this._value;
        }

        public int getValueOrdinal() {
            return HttpHeaderValues.CACHE.getOrdinal(this._value);
        }

        public int getIntValue() {
            return (int) getLongValue();
        }

        public long getLongValue() {
            return BufferUtil.toLong(this._value);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            sb.append(getName());
            sb.append("=");
            sb.append(this._value);
            sb.append(this._next == null ? "" : "->");
            sb.append("]");
            return sb.toString();
        }
    }
}
