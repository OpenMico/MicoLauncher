package com.squareup.javapoet.x2c;

import com.xiaomi.micolauncher.skills.music.model.lrc.LrcRow;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.lang.model.element.Modifier;

/* compiled from: Util.java */
/* loaded from: classes2.dex */
final class c {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static <K, V> Map<K, List<V>> a(Map<K, List<V>> map) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry<K, List<V>> entry : map.entrySet()) {
            if (!entry.getValue().isEmpty()) {
                linkedHashMap.put(entry.getKey(), a(entry.getValue()));
            }
        }
        return Collections.unmodifiableMap(linkedHashMap);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <K, V> Map<K, V> b(Map<K, V> map) {
        return Collections.unmodifiableMap(new LinkedHashMap(map));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(boolean z, String str, Object... objArr) {
        if (!z) {
            throw new IllegalArgumentException(String.format(str, objArr));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> T a(T t, String str, Object... objArr) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(String.format(str, objArr));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void b(boolean z, String str, Object... objArr) {
        if (!z) {
            throw new IllegalStateException(String.format(str, objArr));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> List<T> a(Collection<T> collection) {
        return Collections.unmodifiableList(new ArrayList(collection));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> Set<T> b(Collection<T> collection) {
        return Collections.unmodifiableSet(new LinkedHashSet(collection));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> Set<T> a(Set<T> set, Set<T> set2) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        linkedHashSet.addAll(set);
        linkedHashSet.addAll(set2);
        return linkedHashSet;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(Set<Modifier> set, Modifier... modifierArr) {
        int i = 0;
        for (Modifier modifier : modifierArr) {
            if (set.contains(modifier)) {
                i++;
            }
        }
        a(i == 1, "modifiers %s must contain one of %s", set, Arrays.toString(modifierArr));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String a(char c) {
        if (c == '\"') {
            return "\"";
        }
        if (c == '\'') {
            return "\\'";
        }
        if (c == '\\') {
            return "\\\\";
        }
        switch (c) {
            case '\b':
                return "\\b";
            case '\t':
                return "\\t";
            case '\n':
                return "\\n";
            default:
                switch (c) {
                    case '\f':
                        return "\\f";
                    case '\r':
                        return "\\r";
                    default:
                        return Character.isISOControl(c) ? String.format("\\u%04x", Integer.valueOf(c)) : Character.toString(c);
                }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String a(String str, String str2) {
        StringBuilder sb = new StringBuilder(str.length() + 2);
        sb.append('\"');
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (charAt == '\'') {
                sb.append(LrcRow.SINGLE_QUOTE);
            } else if (charAt == '\"') {
                sb.append("\\\"");
            } else {
                sb.append(a(charAt));
                if (charAt == '\n' && i + 1 < str.length()) {
                    sb.append("\"\n");
                    sb.append(str2);
                    sb.append(str2);
                    sb.append("+ \"");
                }
            }
        }
        sb.append('\"');
        return sb.toString();
    }
}
