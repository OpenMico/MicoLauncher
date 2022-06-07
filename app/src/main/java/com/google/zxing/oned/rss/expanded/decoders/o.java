package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.FormatException;

/* compiled from: DecodedNumeric.java */
/* loaded from: classes2.dex */
final class o extends p {
    private final int a;
    private final int b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public o(int i, int i2, int i3) throws FormatException {
        super(i);
        if (i2 < 0 || i2 > 10 || i3 < 0 || i3 > 10) {
            throw FormatException.getFormatInstance();
        }
        this.a = i2;
        this.b = i3;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int a() {
        return this.a;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int b() {
        return this.b;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean c() {
        return this.a == 10;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean d() {
        return this.b == 10;
    }
}
