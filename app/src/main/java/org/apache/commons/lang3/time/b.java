package org.apache.commons.lang3.time;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* compiled from: FormatCache.java */
/* loaded from: classes5.dex */
abstract class b<F extends Format> {
    private static final ConcurrentMap<a, String> b = new ConcurrentHashMap(7);
    private final ConcurrentMap<a, F> a = new ConcurrentHashMap(7);

    protected abstract F b(String str, TimeZone timeZone, Locale locale);

    public F a() {
        return a(3, 3, TimeZone.getDefault(), Locale.getDefault());
    }

    public F c(String str, TimeZone timeZone, Locale locale) {
        if (str != null) {
            if (timeZone == null) {
                timeZone = TimeZone.getDefault();
            }
            if (locale == null) {
                locale = Locale.getDefault();
            }
            a aVar = new a(str, timeZone, locale);
            F f = this.a.get(aVar);
            if (f != null) {
                return f;
            }
            F b2 = b(str, timeZone, locale);
            F putIfAbsent = this.a.putIfAbsent(aVar, b2);
            return putIfAbsent != null ? putIfAbsent : b2;
        }
        throw new NullPointerException("pattern must not be null");
    }

    private F a(Integer num, Integer num2, TimeZone timeZone, Locale locale) {
        if (locale == null) {
            locale = Locale.getDefault();
        }
        return c(a(num, num2, locale), timeZone, locale);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public F a(int i, int i2, TimeZone timeZone, Locale locale) {
        return a(Integer.valueOf(i), Integer.valueOf(i2), timeZone, locale);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public F a(int i, TimeZone timeZone, Locale locale) {
        return a(Integer.valueOf(i), (Integer) null, timeZone, locale);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public F b(int i, TimeZone timeZone, Locale locale) {
        return a((Integer) null, Integer.valueOf(i), timeZone, locale);
    }

    static String a(Integer num, Integer num2, Locale locale) {
        DateFormat dateFormat;
        a aVar = new a(num, num2, locale);
        String str = b.get(aVar);
        if (str != null) {
            return str;
        }
        try {
            if (num == null) {
                dateFormat = DateFormat.getTimeInstance(num2.intValue(), locale);
            } else if (num2 == null) {
                dateFormat = DateFormat.getDateInstance(num.intValue(), locale);
            } else {
                dateFormat = DateFormat.getDateTimeInstance(num.intValue(), num2.intValue(), locale);
            }
            String pattern = ((SimpleDateFormat) dateFormat).toPattern();
            String putIfAbsent = b.putIfAbsent(aVar, pattern);
            return putIfAbsent != null ? putIfAbsent : pattern;
        } catch (ClassCastException unused) {
            throw new IllegalArgumentException("No date time pattern for locale: " + locale);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: FormatCache.java */
    /* loaded from: classes5.dex */
    public static class a {
        private final Object[] a;
        private int b;

        public a(Object... objArr) {
            this.a = objArr;
        }

        public boolean equals(Object obj) {
            return Arrays.equals(this.a, ((a) obj).a);
        }

        public int hashCode() {
            if (this.b == 0) {
                Object[] objArr = this.a;
                int i = 0;
                for (Object obj : objArr) {
                    if (obj != null) {
                        i = (i * 7) + obj.hashCode();
                    }
                }
                this.b = i;
            }
            return this.b;
        }
    }
}
