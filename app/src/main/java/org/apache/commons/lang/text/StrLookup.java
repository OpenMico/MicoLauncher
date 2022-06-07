package org.apache.commons.lang.text;

import java.util.Map;

/* loaded from: classes5.dex */
public abstract class StrLookup {
    private static final StrLookup a = new a(null);
    private static final StrLookup b;

    public abstract String lookup(String str);

    static {
        StrLookup strLookup;
        try {
            strLookup = new a(System.getProperties());
        } catch (SecurityException unused) {
            strLookup = a;
        }
        b = strLookup;
    }

    public static StrLookup noneLookup() {
        return a;
    }

    public static StrLookup systemPropertiesLookup() {
        return b;
    }

    public static StrLookup mapLookup(Map map) {
        return new a(map);
    }

    protected StrLookup() {
    }

    /* loaded from: classes5.dex */
    static class a extends StrLookup {
        private final Map a;

        a(Map map) {
            this.a = map;
        }

        @Override // org.apache.commons.lang.text.StrLookup
        public String lookup(String str) {
            Object obj;
            Map map = this.a;
            if (map == null || (obj = map.get(str)) == null) {
                return null;
            }
            return obj.toString();
        }
    }
}
