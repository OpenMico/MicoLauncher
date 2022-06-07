package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: AI013x0x1xDecoder.java */
/* loaded from: classes2.dex */
public final class e extends i {
    private final String a;
    private final String b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public e(BitArray bitArray, String str, String str2) {
        super(bitArray);
        this.a = str2;
        this.b = str;
    }

    @Override // com.google.zxing.oned.rss.expanded.decoders.AbstractExpandedDecoder
    public String parseInformation() throws NotFoundException {
        if (getInformation().getSize() == 84) {
            StringBuilder sb = new StringBuilder();
            b(sb, 8);
            b(sb, 48, 20);
            c(sb, 68);
            return sb.toString();
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private void c(StringBuilder sb, int i) {
        int a = getGeneralDecoder().a(i, 16);
        if (a != 38400) {
            sb.append('(');
            sb.append(this.a);
            sb.append(')');
            int i2 = a % 32;
            int i3 = a / 32;
            int i4 = (i3 % 12) + 1;
            int i5 = i3 / 12;
            if (i5 / 10 == 0) {
                sb.append('0');
            }
            sb.append(i5);
            if (i4 / 10 == 0) {
                sb.append('0');
            }
            sb.append(i4);
            if (i2 / 10 == 0) {
                sb.append('0');
            }
            sb.append(i2);
        }
    }

    @Override // com.google.zxing.oned.rss.expanded.decoders.i
    protected void a(StringBuilder sb, int i) {
        sb.append('(');
        sb.append(this.b);
        sb.append(i / 100000);
        sb.append(')');
    }

    @Override // com.google.zxing.oned.rss.expanded.decoders.i
    protected int a(int i) {
        return i % 100000;
    }
}
