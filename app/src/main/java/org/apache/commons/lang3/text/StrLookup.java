package org.apache.commons.lang3.text;

import java.util.Map;

/* loaded from: classes5.dex */
public abstract class StrLookup<V> {
    private static final StrLookup<String> a = new a(null);
    private static final StrLookup<String> b = new b();

    public abstract String lookup(String str);

    public static StrLookup<?> noneLookup() {
        return a;
    }

    public static StrLookup<String> systemPropertiesLookup() {
        return b;
    }

    public static <V> StrLookup<V> mapLookup(Map<String, V> map) {
        return new a(map);
    }

    protected StrLookup() {
    }

    /* loaded from: classes5.dex */
    static class a<V> extends StrLookup<V> {
        private final Map<String, V> a;

        a(Map<String, V> map) {
            this.a = map;
        }

        @Override // org.apache.commons.lang3.text.StrLookup
        public String lookup(String str) {
            V v;
            Map<String, V> map = this.a;
            if (map == null || (v = map.get(str)) == null) {
                return null;
            }
            return v.toString();
        }
    }

    /* loaded from: classes5.dex */
    private static class b extends StrLookup<String> {
        private b() {
        }

        @Override // org.apache.commons.lang3.text.StrLookup
        public String lookup(String str) {
            if (str.length() <= 0) {
                return null;
            }
            try {
                return System.getProperty(str);
            } catch (SecurityException unused) {
                return null;
            }
        }
    }
}
