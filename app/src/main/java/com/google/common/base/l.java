package com.google.common.base;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.CharMatcher;
import java.util.BitSet;

/* compiled from: SmallCharMatcher.java */
@GwtIncompatible
/* loaded from: classes2.dex */
final class l extends CharMatcher.u {
    private final char[] a;
    private final boolean b;
    private final long c;

    private l(char[] cArr, long j, boolean z, String str) {
        super(str);
        this.a = cArr;
        this.c = j;
        this.b = z;
    }

    static int a(int i) {
        return Integer.rotateLeft(i * (-862048943), 15) * 461845907;
    }

    private boolean c(int i) {
        return 1 == ((this.c >> i) & 1);
    }

    @VisibleForTesting
    static int b(int i) {
        if (i == 1) {
            return 2;
        }
        int highestOneBit = Integer.highestOneBit(i - 1) << 1;
        while (highestOneBit * 0.5d < i) {
            highestOneBit <<= 1;
        }
        return highestOneBit;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static CharMatcher a(BitSet bitSet, String str) {
        int i;
        int cardinality = bitSet.cardinality();
        boolean z = bitSet.get(0);
        char[] cArr = new char[b(cardinality)];
        int length = cArr.length - 1;
        int nextSetBit = bitSet.nextSetBit(0);
        long j = 0;
        while (nextSetBit != -1) {
            j = (1 << nextSetBit) | j;
            int a = a(nextSetBit);
            while (true) {
                i = a & length;
                if (cArr[i] == 0) {
                    break;
                }
                a = i + 1;
            }
            cArr[i] = (char) nextSetBit;
            nextSetBit = bitSet.nextSetBit(nextSetBit + 1);
        }
        return new l(cArr, j, z, str);
    }

    @Override // com.google.common.base.CharMatcher
    public boolean matches(char c) {
        if (c == 0) {
            return this.b;
        }
        if (!c(c)) {
            return false;
        }
        int length = this.a.length - 1;
        int a = a((int) c) & length;
        int i = a;
        do {
            char[] cArr = this.a;
            if (cArr[i] == 0) {
                return false;
            }
            if (cArr[i] == c) {
                return true;
            }
            i = (i + 1) & length;
        } while (i != a);
        return false;
    }

    @Override // com.google.common.base.CharMatcher
    void a(BitSet bitSet) {
        if (this.b) {
            bitSet.set(0);
        }
        char[] cArr = this.a;
        for (char c : cArr) {
            if (c != 0) {
                bitSet.set(c);
            }
        }
    }
}
