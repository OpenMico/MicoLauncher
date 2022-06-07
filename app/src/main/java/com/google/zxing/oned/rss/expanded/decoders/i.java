package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.common.BitArray;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: AI01weightDecoder.java */
/* loaded from: classes2.dex */
public abstract class i extends h {
    protected abstract int a(int i);

    protected abstract void a(StringBuilder sb, int i);

    public i(BitArray bitArray) {
        super(bitArray);
    }

    final void b(StringBuilder sb, int i, int i2) {
        int a = getGeneralDecoder().a(i, i2);
        a(sb, a);
        int a2 = a(a);
        int i3 = 100000;
        for (int i4 = 0; i4 < 5; i4++) {
            if (a2 / i3 == 0) {
                sb.append('0');
            }
            i3 /= 10;
        }
        sb.append(a2);
    }
}
