package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.common.BitArray;

/* compiled from: AI01decoder.java */
/* loaded from: classes2.dex */
public abstract class h extends AbstractExpandedDecoder {
    public h(BitArray bitArray) {
        super(bitArray);
    }

    final void b(StringBuilder sb, int i) {
        sb.append("(01)");
        int length = sb.length();
        sb.append('9');
        a(sb, i, length);
    }

    final void a(StringBuilder sb, int i, int i2) {
        for (int i3 = 0; i3 < 4; i3++) {
            int a = getGeneralDecoder().a((i3 * 10) + i, 10);
            if (a / 100 == 0) {
                sb.append('0');
            }
            if (a / 10 == 0) {
                sb.append('0');
            }
            sb.append(a);
        }
        a(sb, i2);
    }

    private static void a(StringBuilder sb, int i) {
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < 13; i4++) {
            int charAt = sb.charAt(i4 + i) - '0';
            if ((i4 & 1) == 0) {
                charAt *= 3;
            }
            i3 += charAt;
        }
        int i5 = 10 - (i3 % 10);
        if (i5 != 10) {
            i2 = i5;
        }
        sb.append(i2);
    }
}
