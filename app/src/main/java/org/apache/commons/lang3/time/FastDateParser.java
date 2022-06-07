package org.apache.commons.lang3.time;

import com.xiaomi.mipush.sdk.Constants;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes5.dex */
public class FastDateParser implements Serializable, DateParser {
    private static final long serialVersionUID = 3;
    private transient List<g> b;
    private final int century;
    private final Locale locale;
    private final String pattern;
    private final int startYear;
    private final TimeZone timeZone;
    static final Locale a = new Locale("ja", "JP", "JP");
    private static final Comparator<String> c = new Comparator<String>() { // from class: org.apache.commons.lang3.time.FastDateParser.1
        /* renamed from: a */
        public int compare(String str, String str2) {
            return str2.compareTo(str);
        }
    };
    private static final ConcurrentMap<Locale, f>[] d = new ConcurrentMap[17];
    private static final f e = new d(1) { // from class: org.apache.commons.lang3.time.FastDateParser.2
        @Override // org.apache.commons.lang3.time.FastDateParser.d
        int a(FastDateParser fastDateParser, int i2) {
            return i2 < 100 ? fastDateParser.a(i2) : i2;
        }
    };
    private static final f f = new d(2) { // from class: org.apache.commons.lang3.time.FastDateParser.3
        @Override // org.apache.commons.lang3.time.FastDateParser.d
        int a(FastDateParser fastDateParser, int i2) {
            return i2 - 1;
        }
    };
    private static final f g = new d(1);
    private static final f h = new d(3);
    private static final f i = new d(4);
    private static final f j = new d(6);
    private static final f k = new d(5);
    private static final f l = new d(7) { // from class: org.apache.commons.lang3.time.FastDateParser.4
        @Override // org.apache.commons.lang3.time.FastDateParser.d
        int a(FastDateParser fastDateParser, int i2) {
            if (i2 != 7) {
                return 1 + i2;
            }
            return 1;
        }
    };
    private static final f m = new d(8);
    private static final f n = new d(11);
    private static final f o = new d(11) { // from class: org.apache.commons.lang3.time.FastDateParser.5
        @Override // org.apache.commons.lang3.time.FastDateParser.d
        int a(FastDateParser fastDateParser, int i2) {
            if (i2 == 24) {
                return 0;
            }
            return i2;
        }
    };
    private static final f p = new d(10) { // from class: org.apache.commons.lang3.time.FastDateParser.6
        @Override // org.apache.commons.lang3.time.FastDateParser.d
        int a(FastDateParser fastDateParser, int i2) {
            if (i2 == 12) {
                return 0;
            }
            return i2;
        }
    };
    private static final f q = new d(10);
    private static final f r = new d(12);
    private static final f s = new d(13);
    private static final f t = new d(14);

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean b(char c2) {
        return (c2 >= 'A' && c2 <= 'Z') || (c2 >= 'a' && c2 <= 'z');
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public FastDateParser(String str, TimeZone timeZone, Locale locale) {
        this(str, timeZone, locale, null);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public FastDateParser(String str, TimeZone timeZone, Locale locale, Date date) {
        int i2;
        this.pattern = str;
        this.timeZone = timeZone;
        this.locale = locale;
        Calendar instance = Calendar.getInstance(timeZone, locale);
        if (date != null) {
            instance.setTime(date);
            i2 = instance.get(1);
        } else if (locale.equals(a)) {
            i2 = 0;
        } else {
            instance.setTime(new Date());
            i2 = instance.get(1) - 80;
        }
        this.century = (i2 / 100) * 100;
        this.startYear = i2 - this.century;
        a(instance);
    }

    private void a(Calendar calendar) {
        this.b = new ArrayList();
        h hVar = new h(this.pattern, calendar);
        while (true) {
            g a2 = hVar.a();
            if (a2 != null) {
                this.b.add(a2);
            } else {
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class g {
        final f a;
        final int b;

        g(f fVar, int i) {
            this.a = fVar;
            this.b = i;
        }

        int a(ListIterator<g> listIterator) {
            if (!this.a.a() || !listIterator.hasNext()) {
                return 0;
            }
            f fVar = listIterator.next().a;
            listIterator.previous();
            if (fVar.a()) {
                return this.b;
            }
            return 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public class h {
        private final String b;
        private final Calendar c;
        private int d;

        h(String str, Calendar calendar) {
            this.b = str;
            this.c = calendar;
        }

        g a() {
            if (this.d >= this.b.length()) {
                return null;
            }
            char charAt = this.b.charAt(this.d);
            if (FastDateParser.b(charAt)) {
                return a(charAt);
            }
            return b();
        }

        private g a(char c) {
            int i = this.d;
            do {
                int i2 = this.d + 1;
                this.d = i2;
                if (i2 >= this.b.length()) {
                    break;
                }
            } while (this.b.charAt(this.d) == c);
            int i3 = this.d - i;
            return new g(FastDateParser.this.a(c, i3, this.c), i3);
        }

        private g b() {
            StringBuilder sb = new StringBuilder();
            boolean z = false;
            while (this.d < this.b.length()) {
                char charAt = this.b.charAt(this.d);
                if (!z && FastDateParser.b(charAt)) {
                    break;
                }
                if (charAt == '\'') {
                    int i = this.d + 1;
                    this.d = i;
                    if (i == this.b.length() || this.b.charAt(this.d) != '\'') {
                        z = !z;
                    }
                }
                this.d++;
                sb.append(charAt);
            }
            if (!z) {
                String sb2 = sb.toString();
                return new g(new b(sb2), sb2.length());
            }
            throw new IllegalArgumentException("Unterminated quote");
        }
    }

    @Override // org.apache.commons.lang3.time.DateParser, org.apache.commons.lang3.time.DatePrinter
    public String getPattern() {
        return this.pattern;
    }

    @Override // org.apache.commons.lang3.time.DateParser, org.apache.commons.lang3.time.DatePrinter
    public TimeZone getTimeZone() {
        return this.timeZone;
    }

    @Override // org.apache.commons.lang3.time.DateParser, org.apache.commons.lang3.time.DatePrinter
    public Locale getLocale() {
        return this.locale;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof FastDateParser)) {
            return false;
        }
        FastDateParser fastDateParser = (FastDateParser) obj;
        return this.pattern.equals(fastDateParser.pattern) && this.timeZone.equals(fastDateParser.timeZone) && this.locale.equals(fastDateParser.locale);
    }

    public int hashCode() {
        return this.pattern.hashCode() + ((this.timeZone.hashCode() + (this.locale.hashCode() * 13)) * 13);
    }

    public String toString() {
        return "FastDateParser[" + this.pattern + Constants.ACCEPT_TIME_SEPARATOR_SP + this.locale + Constants.ACCEPT_TIME_SEPARATOR_SP + this.timeZone.getID() + "]";
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        a(Calendar.getInstance(this.timeZone, this.locale));
    }

    @Override // org.apache.commons.lang3.time.DateParser
    public Object parseObject(String str) throws ParseException {
        return parse(str);
    }

    @Override // org.apache.commons.lang3.time.DateParser
    public Date parse(String str) throws ParseException {
        ParsePosition parsePosition = new ParsePosition(0);
        Date parse = parse(str, parsePosition);
        if (parse != null) {
            return parse;
        }
        if (this.locale.equals(a)) {
            throw new ParseException("(The " + this.locale + " locale does not support dates before 1868 AD)\nUnparseable date: \"" + str, parsePosition.getErrorIndex());
        }
        throw new ParseException("Unparseable date: " + str, parsePosition.getErrorIndex());
    }

    @Override // org.apache.commons.lang3.time.DateParser
    public Object parseObject(String str, ParsePosition parsePosition) {
        return parse(str, parsePosition);
    }

    @Override // org.apache.commons.lang3.time.DateParser
    public Date parse(String str, ParsePosition parsePosition) {
        Calendar instance = Calendar.getInstance(this.timeZone, this.locale);
        instance.clear();
        if (parse(str, parsePosition, instance)) {
            return instance.getTime();
        }
        return null;
    }

    @Override // org.apache.commons.lang3.time.DateParser
    public boolean parse(String str, ParsePosition parsePosition, Calendar calendar) {
        ListIterator<g> listIterator = this.b.listIterator();
        while (listIterator.hasNext()) {
            g next = listIterator.next();
            if (!next.a.a(this, calendar, str, parsePosition, next.a(listIterator))) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static StringBuilder b(StringBuilder sb, String str) {
        for (int i2 = 0; i2 < str.length(); i2++) {
            char charAt = str.charAt(i2);
            switch (charAt) {
                case '$':
                case '(':
                case ')':
                case '*':
                case '+':
                case '.':
                case '?':
                case '[':
                case '\\':
                case '^':
                case '{':
                case '|':
                    sb.append('\\');
                    break;
            }
            sb.append(charAt);
        }
        return sb;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Map<String, Integer> b(Calendar calendar, Locale locale, int i2, StringBuilder sb) {
        HashMap hashMap = new HashMap();
        Map<String, Integer> displayNames = calendar.getDisplayNames(i2, 0, locale);
        TreeSet treeSet = new TreeSet(c);
        for (Map.Entry<String, Integer> entry : displayNames.entrySet()) {
            String lowerCase = entry.getKey().toLowerCase(locale);
            if (treeSet.add(lowerCase)) {
                hashMap.put(lowerCase, entry.getValue());
            }
        }
        Iterator it = treeSet.iterator();
        while (it.hasNext()) {
            b(sb, (String) it.next()).append('|');
        }
        return hashMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int a(int i2) {
        int i3 = this.century + i2;
        return i2 >= this.startYear ? i3 : i3 + 100;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static abstract class f {
        boolean a() {
            return false;
        }

        abstract boolean a(FastDateParser fastDateParser, Calendar calendar, String str, ParsePosition parsePosition, int i);

        private f() {
        }
    }

    /* loaded from: classes5.dex */
    private static abstract class e extends f {
        private Pattern a;

        abstract void a(FastDateParser fastDateParser, Calendar calendar, String str);

        @Override // org.apache.commons.lang3.time.FastDateParser.f
        boolean a() {
            return false;
        }

        private e() {
            super();
        }

        void a(StringBuilder sb) {
            a(sb.toString());
        }

        void a(String str) {
            this.a = Pattern.compile(str);
        }

        @Override // org.apache.commons.lang3.time.FastDateParser.f
        boolean a(FastDateParser fastDateParser, Calendar calendar, String str, ParsePosition parsePosition, int i) {
            Matcher matcher = this.a.matcher(str.substring(parsePosition.getIndex()));
            if (!matcher.lookingAt()) {
                parsePosition.setErrorIndex(parsePosition.getIndex());
                return false;
            }
            parsePosition.setIndex(parsePosition.getIndex() + matcher.end(1));
            a(fastDateParser, calendar, matcher.group(1));
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0064  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public org.apache.commons.lang3.time.FastDateParser.f a(char r2, int r3, java.util.Calendar r4) {
        /*
            r1 = this;
            switch(r2) {
                case 68: goto L_0x0086;
                case 69: goto L_0x0080;
                case 70: goto L_0x007d;
                case 71: goto L_0x0077;
                case 72: goto L_0x0074;
                default: goto L_0x0003;
            }
        L_0x0003:
            r0 = 2
            switch(r2) {
                case 87: goto L_0x0071;
                case 88: goto L_0x006c;
                case 89: goto L_0x0064;
                case 90: goto L_0x0056;
                default: goto L_0x0007;
            }
        L_0x0007:
            switch(r2) {
                case 121: goto L_0x0064;
                case 122: goto L_0x005d;
                default: goto L_0x000a;
            }
        L_0x000a:
            switch(r2) {
                case 75: goto L_0x0053;
                case 77: goto L_0x0048;
                case 83: goto L_0x0045;
                case 97: goto L_0x003e;
                case 100: goto L_0x003b;
                case 104: goto L_0x0038;
                case 107: goto L_0x0035;
                case 109: goto L_0x0032;
                case 115: goto L_0x002f;
                case 117: goto L_0x002c;
                case 119: goto L_0x0029;
                default: goto L_0x000d;
            }
        L_0x000d:
            java.lang.IllegalArgumentException r3 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r0 = "Format '"
            r4.append(r0)
            r4.append(r2)
            java.lang.String r2 = "' not supported"
            r4.append(r2)
            java.lang.String r2 = r4.toString()
            r3.<init>(r2)
            throw r3
        L_0x0029:
            org.apache.commons.lang3.time.FastDateParser$f r2 = org.apache.commons.lang3.time.FastDateParser.h
            return r2
        L_0x002c:
            org.apache.commons.lang3.time.FastDateParser$f r2 = org.apache.commons.lang3.time.FastDateParser.l
            return r2
        L_0x002f:
            org.apache.commons.lang3.time.FastDateParser$f r2 = org.apache.commons.lang3.time.FastDateParser.s
            return r2
        L_0x0032:
            org.apache.commons.lang3.time.FastDateParser$f r2 = org.apache.commons.lang3.time.FastDateParser.r
            return r2
        L_0x0035:
            org.apache.commons.lang3.time.FastDateParser$f r2 = org.apache.commons.lang3.time.FastDateParser.o
            return r2
        L_0x0038:
            org.apache.commons.lang3.time.FastDateParser$f r2 = org.apache.commons.lang3.time.FastDateParser.p
            return r2
        L_0x003b:
            org.apache.commons.lang3.time.FastDateParser$f r2 = org.apache.commons.lang3.time.FastDateParser.k
            return r2
        L_0x003e:
            r2 = 9
            org.apache.commons.lang3.time.FastDateParser$f r2 = r1.a(r2, r4)
            return r2
        L_0x0045:
            org.apache.commons.lang3.time.FastDateParser$f r2 = org.apache.commons.lang3.time.FastDateParser.t
            return r2
        L_0x0048:
            r2 = 3
            if (r3 < r2) goto L_0x0050
            org.apache.commons.lang3.time.FastDateParser$f r2 = r1.a(r0, r4)
            goto L_0x0052
        L_0x0050:
            org.apache.commons.lang3.time.FastDateParser$f r2 = org.apache.commons.lang3.time.FastDateParser.f
        L_0x0052:
            return r2
        L_0x0053:
            org.apache.commons.lang3.time.FastDateParser$f r2 = org.apache.commons.lang3.time.FastDateParser.q
            return r2
        L_0x0056:
            if (r3 != r0) goto L_0x005d
            org.apache.commons.lang3.time.FastDateParser$f r2 = org.apache.commons.lang3.time.FastDateParser.c.b()
            return r2
        L_0x005d:
            r2 = 15
            org.apache.commons.lang3.time.FastDateParser$f r2 = r1.a(r2, r4)
            return r2
        L_0x0064:
            if (r3 <= r0) goto L_0x0069
            org.apache.commons.lang3.time.FastDateParser$f r2 = org.apache.commons.lang3.time.FastDateParser.g
            goto L_0x006b
        L_0x0069:
            org.apache.commons.lang3.time.FastDateParser$f r2 = org.apache.commons.lang3.time.FastDateParser.e
        L_0x006b:
            return r2
        L_0x006c:
            org.apache.commons.lang3.time.FastDateParser$f r2 = org.apache.commons.lang3.time.FastDateParser.c.a(r3)
            return r2
        L_0x0071:
            org.apache.commons.lang3.time.FastDateParser$f r2 = org.apache.commons.lang3.time.FastDateParser.i
            return r2
        L_0x0074:
            org.apache.commons.lang3.time.FastDateParser$f r2 = org.apache.commons.lang3.time.FastDateParser.n
            return r2
        L_0x0077:
            r2 = 0
            org.apache.commons.lang3.time.FastDateParser$f r2 = r1.a(r2, r4)
            return r2
        L_0x007d:
            org.apache.commons.lang3.time.FastDateParser$f r2 = org.apache.commons.lang3.time.FastDateParser.m
            return r2
        L_0x0080:
            r2 = 7
            org.apache.commons.lang3.time.FastDateParser$f r2 = r1.a(r2, r4)
            return r2
        L_0x0086:
            org.apache.commons.lang3.time.FastDateParser$f r2 = org.apache.commons.lang3.time.FastDateParser.j
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.lang3.time.FastDateParser.a(char, int, java.util.Calendar):org.apache.commons.lang3.time.FastDateParser$f");
    }

    private static ConcurrentMap<Locale, f> b(int i2) {
        ConcurrentMap<Locale, f> concurrentMap;
        synchronized (d) {
            if (d[i2] == null) {
                d[i2] = new ConcurrentHashMap(3);
            }
            concurrentMap = d[i2];
        }
        return concurrentMap;
    }

    private f a(int i2, Calendar calendar) {
        ConcurrentMap<Locale, f> b2 = b(i2);
        f fVar = b2.get(this.locale);
        if (fVar == null) {
            fVar = i2 == 15 ? new i(this.locale) : new a(i2, calendar, this.locale);
            f putIfAbsent = b2.putIfAbsent(this.locale, fVar);
            if (putIfAbsent != null) {
                return putIfAbsent;
            }
        }
        return fVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class b extends f {
        private final String a;

        @Override // org.apache.commons.lang3.time.FastDateParser.f
        boolean a() {
            return false;
        }

        b(String str) {
            super();
            this.a = str;
        }

        @Override // org.apache.commons.lang3.time.FastDateParser.f
        boolean a(FastDateParser fastDateParser, Calendar calendar, String str, ParsePosition parsePosition, int i) {
            for (int i2 = 0; i2 < this.a.length(); i2++) {
                int index = parsePosition.getIndex() + i2;
                if (index == str.length()) {
                    parsePosition.setErrorIndex(index);
                    return false;
                } else if (this.a.charAt(i2) != str.charAt(index)) {
                    parsePosition.setErrorIndex(index);
                    return false;
                }
            }
            parsePosition.setIndex(this.a.length() + parsePosition.getIndex());
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class a extends e {
        final Locale a;
        private final int b;
        private final Map<String, Integer> c;

        a(int i, Calendar calendar, Locale locale) {
            super();
            this.b = i;
            this.a = locale;
            StringBuilder sb = new StringBuilder();
            sb.append("((?iu)");
            this.c = FastDateParser.b(calendar, locale, i, sb);
            sb.setLength(sb.length() - 1);
            sb.append(")");
            a(sb);
        }

        @Override // org.apache.commons.lang3.time.FastDateParser.e
        void a(FastDateParser fastDateParser, Calendar calendar, String str) {
            calendar.set(this.b, this.c.get(str.toLowerCase(this.a)).intValue());
        }
    }

    /* loaded from: classes5.dex */
    private static class d extends f {
        private final int a;

        int a(FastDateParser fastDateParser, int i) {
            return i;
        }

        @Override // org.apache.commons.lang3.time.FastDateParser.f
        boolean a() {
            return true;
        }

        d(int i) {
            super();
            this.a = i;
        }

        @Override // org.apache.commons.lang3.time.FastDateParser.f
        boolean a(FastDateParser fastDateParser, Calendar calendar, String str, ParsePosition parsePosition, int i) {
            int index = parsePosition.getIndex();
            int length = str.length();
            if (i == 0) {
                while (index < length && Character.isWhitespace(str.charAt(index))) {
                    index++;
                }
                parsePosition.setIndex(index);
            } else {
                int i2 = i + index;
                if (length > i2) {
                    length = i2;
                }
            }
            while (index < length && Character.isDigit(str.charAt(index))) {
                index++;
            }
            if (parsePosition.getIndex() == index) {
                parsePosition.setErrorIndex(index);
                return false;
            }
            int parseInt = Integer.parseInt(str.substring(parsePosition.getIndex(), index));
            parsePosition.setIndex(index);
            calendar.set(this.a, a(fastDateParser, parseInt));
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static class i extends e {
        private final Locale a;
        private final Map<String, a> b = new HashMap();

        /* loaded from: classes5.dex */
        private static class a {
            TimeZone a;
            int b;

            a(TimeZone timeZone, boolean z) {
                this.a = timeZone;
                this.b = z ? timeZone.getDSTSavings() : 0;
            }
        }

        i(Locale locale) {
            super();
            this.a = locale;
            StringBuilder sb = new StringBuilder();
            sb.append("((?iu)[+-]\\d{4}|GMT[+-]\\d{1,2}:\\d{2}");
            TreeSet<String> treeSet = new TreeSet(FastDateParser.c);
            String[][] zoneStrings = DateFormatSymbols.getInstance(locale).getZoneStrings();
            for (String[] strArr : zoneStrings) {
                String str = strArr[0];
                if (!str.equalsIgnoreCase("GMT")) {
                    TimeZone timeZone = TimeZone.getTimeZone(str);
                    a aVar = new a(timeZone, false);
                    a aVar2 = aVar;
                    for (int i = 1; i < strArr.length; i++) {
                        if (i == 3) {
                            aVar2 = new a(timeZone, true);
                        } else if (i == 5) {
                            aVar2 = aVar;
                        }
                        String lowerCase = strArr[i].toLowerCase(locale);
                        if (treeSet.add(lowerCase)) {
                            this.b.put(lowerCase, aVar2);
                        }
                    }
                }
            }
            for (String str2 : treeSet) {
                sb.append('|');
                FastDateParser.b(sb, str2);
            }
            sb.append(")");
            a(sb);
        }

        @Override // org.apache.commons.lang3.time.FastDateParser.e
        void a(FastDateParser fastDateParser, Calendar calendar, String str) {
            if (str.charAt(0) == '+' || str.charAt(0) == '-') {
                calendar.setTimeZone(TimeZone.getTimeZone("GMT" + str));
            } else if (str.regionMatches(true, 0, "GMT", 0, 3)) {
                calendar.setTimeZone(TimeZone.getTimeZone(str.toUpperCase()));
            } else {
                a aVar = this.b.get(str.toLowerCase(this.a));
                calendar.set(16, aVar.b);
                calendar.set(15, aVar.a.getRawOffset());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class c extends e {
        private static final f a = new c("(Z|(?:[+-]\\d{2}))");
        private static final f b = new c("(Z|(?:[+-]\\d{2}\\d{2}))");
        private static final f c = new c("(Z|(?:[+-]\\d{2}(?::)\\d{2}))");

        c(String str) {
            super();
            a(str);
        }

        @Override // org.apache.commons.lang3.time.FastDateParser.e
        void a(FastDateParser fastDateParser, Calendar calendar, String str) {
            if (str.equals("Z")) {
                calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
                return;
            }
            calendar.setTimeZone(TimeZone.getTimeZone("GMT" + str));
        }

        static f a(int i) {
            switch (i) {
                case 1:
                    return a;
                case 2:
                    return b;
                case 3:
                    return c;
                default:
                    throw new IllegalArgumentException("invalid number of X");
            }
        }
    }
}
