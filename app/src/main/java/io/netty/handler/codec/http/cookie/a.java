package io.netty.handler.codec.http.cookie;

import io.netty.util.internal.InternalThreadLocalMap;
import java.util.BitSet;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: CookieUtil.java */
/* loaded from: classes4.dex */
public final class a {
    private static final BitSet a = b();
    private static final BitSet b = c();
    private static final BitSet c = d();

    private static BitSet b() {
        BitSet bitSet = new BitSet();
        for (int i = 32; i < 127; i++) {
            bitSet.set(i);
        }
        for (int i2 : new int[]{40, 41, 60, 62, 64, 44, 59, 58, 92, 34, 47, 91, 93, 63, 61, 123, 125, 32, 9}) {
            bitSet.set(i2, false);
        }
        return bitSet;
    }

    private static BitSet c() {
        BitSet bitSet = new BitSet();
        bitSet.set(33);
        for (int i = 35; i <= 43; i++) {
            bitSet.set(i);
        }
        for (int i2 = 45; i2 <= 58; i2++) {
            bitSet.set(i2);
        }
        for (int i3 = 60; i3 <= 91; i3++) {
            bitSet.set(i3);
        }
        for (int i4 = 93; i4 <= 126; i4++) {
            bitSet.set(i4);
        }
        return bitSet;
    }

    private static BitSet d() {
        BitSet bitSet = new BitSet();
        for (int i = 32; i < 127; i++) {
            bitSet.set(i);
        }
        bitSet.set(59, false);
        return bitSet;
    }

    public static StringBuilder a() {
        return InternalThreadLocalMap.get().stringBuilder();
    }

    public static String a(StringBuilder sb) {
        if (sb.length() == 0) {
            return null;
        }
        return b(sb);
    }

    public static String b(StringBuilder sb) {
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 2);
        }
        return sb.toString();
    }

    public static void a(StringBuilder sb, String str, long j) {
        sb.append(str);
        sb.append('=');
        sb.append(j);
        sb.append(';');
        sb.append(' ');
    }

    public static void a(StringBuilder sb, String str, String str2) {
        sb.append(str);
        sb.append('=');
        sb.append(str2);
        sb.append(';');
        sb.append(' ');
    }

    public static void a(StringBuilder sb, String str) {
        sb.append(str);
        sb.append(';');
        sb.append(' ');
    }

    public static void b(StringBuilder sb, String str, String str2) {
        if (str2 == null) {
            str2 = "";
        }
        sb.append(str);
        sb.append('=');
        sb.append('\"');
        sb.append(str2);
        sb.append('\"');
        sb.append(';');
        sb.append(' ');
    }

    public static int a(CharSequence charSequence) {
        return a(charSequence, a);
    }

    public static int b(CharSequence charSequence) {
        return a(charSequence, b);
    }

    static int a(CharSequence charSequence, BitSet bitSet) {
        for (int i = 0; i < charSequence.length(); i++) {
            if (!bitSet.get(charSequence.charAt(i))) {
                return i;
            }
        }
        return -1;
    }

    public static CharSequence c(CharSequence charSequence) {
        int length = charSequence.length();
        if (length <= 0 || charSequence.charAt(0) != '\"') {
            return charSequence;
        }
        if (length < 2) {
            return null;
        }
        int i = length - 1;
        if (charSequence.charAt(i) == '\"') {
            return length == 2 ? "" : charSequence.subSequence(1, i);
        }
        return null;
    }

    public static String a(String str, String str2) {
        if (str2 == null) {
            return null;
        }
        String trim = str2.trim();
        if (trim.isEmpty()) {
            return null;
        }
        int a2 = a(trim, c);
        if (a2 == -1) {
            return trim;
        }
        throw new IllegalArgumentException(str + " contains the prohibited characters: " + trim.charAt(a2));
    }
}
