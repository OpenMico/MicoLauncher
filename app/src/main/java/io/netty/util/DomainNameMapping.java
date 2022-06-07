package io.netty.util;

import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.StringUtil;
import java.net.IDN;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes4.dex */
public class DomainNameMapping<V> implements Mapping<String, V> {
    final V a;
    private final Map<String, V> b;
    private final Map<String, V> c;

    @Deprecated
    public DomainNameMapping(V v) {
        this(4, v);
    }

    @Deprecated
    public DomainNameMapping(int i, V v) {
        this(new LinkedHashMap(i), v);
    }

    public DomainNameMapping(Map<String, V> map, V v) {
        this.a = (V) ObjectUtil.checkNotNull(v, "defaultValue");
        this.b = map;
        this.c = map != null ? Collections.unmodifiableMap(map) : null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Deprecated
    public DomainNameMapping<V> add(String str, V v) {
        this.b.put(a((String) ObjectUtil.checkNotNull(str, "hostname")), ObjectUtil.checkNotNull(v, "output"));
        return this;
    }

    static boolean a(String str, String str2) {
        if (str.startsWith("*.")) {
            return str.regionMatches(2, str2, 0, str2.length()) || StringUtil.commonSuffixOfLength(str2, str, str.length() - 1);
        }
        return str.equals(str2);
    }

    static String a(String str) {
        if (b(str)) {
            str = IDN.toASCII(str, 1);
        }
        return str.toLowerCase(Locale.US);
    }

    private static boolean b(String str) {
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (str.charAt(i) > 127) {
                return true;
            }
        }
        return false;
    }

    public V map(String str) {
        if (str != null) {
            String a = a(str);
            for (Map.Entry<String, V> entry : this.b.entrySet()) {
                if (a(entry.getKey(), a)) {
                    return entry.getValue();
                }
            }
        }
        return this.a;
    }

    public Map<String, V> asMap() {
        return this.c;
    }

    public String toString() {
        return StringUtil.simpleClassName(this) + "(default: " + this.a + ", map: " + this.b + ')';
    }
}
