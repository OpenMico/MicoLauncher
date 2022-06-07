package io.netty.handler.codec.http;

import java.util.BitSet;

/* compiled from: CookieUtil.java */
@Deprecated
/* loaded from: classes4.dex */
final class b {
    private static final BitSet a = a();
    private static final BitSet b = a(a);

    private static BitSet a() {
        BitSet bitSet = new BitSet(8);
        for (int i = 35; i < 127; i++) {
            bitSet.set(i);
        }
        bitSet.set(34, false);
        bitSet.set(44, false);
        bitSet.set(59, false);
        bitSet.set(92, false);
        return bitSet;
    }

    private static BitSet a(BitSet bitSet) {
        BitSet bitSet2 = new BitSet(8);
        bitSet2.or(bitSet);
        bitSet2.set(40, false);
        bitSet2.set(41, false);
        bitSet2.set(60, false);
        bitSet2.set(62, false);
        bitSet2.set(64, false);
        bitSet2.set(58, false);
        bitSet2.set(47, false);
        bitSet2.set(91, false);
        bitSet2.set(93, false);
        bitSet2.set(63, false);
        bitSet2.set(61, false);
        bitSet2.set(123, false);
        bitSet2.set(125, false);
        bitSet2.set(32, false);
        bitSet2.set(9, false);
        return bitSet2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int a(CharSequence charSequence) {
        return a(charSequence, b);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int b(CharSequence charSequence) {
        return a(charSequence, a);
    }

    static int a(CharSequence charSequence, BitSet bitSet) {
        for (int i = 0; i < charSequence.length(); i++) {
            if (!bitSet.get(charSequence.charAt(i))) {
                return i;
            }
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
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
}
