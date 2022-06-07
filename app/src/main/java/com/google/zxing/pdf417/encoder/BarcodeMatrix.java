package com.google.zxing.pdf417.encoder;

import java.lang.reflect.Array;

/* loaded from: classes2.dex */
public final class BarcodeMatrix {
    private final a[] a;
    private int b;
    private final int c;
    private final int d;

    /* JADX INFO: Access modifiers changed from: package-private */
    public BarcodeMatrix(int i, int i2) {
        this.a = new a[i];
        int length = this.a.length;
        for (int i3 = 0; i3 < length; i3++) {
            this.a[i3] = new a(((i2 + 4) * 17) + 1);
        }
        this.d = i2 * 17;
        this.c = i;
        this.b = -1;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a() {
        this.b++;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public a b() {
        return this.a[this.b];
    }

    public byte[][] getMatrix() {
        return getScaledMatrix(1, 1);
    }

    public byte[][] getScaledMatrix(int i, int i2) {
        byte[][] bArr = (byte[][]) Array.newInstance(byte.class, this.c * i2, this.d * i);
        int i3 = this.c * i2;
        for (int i4 = 0; i4 < i3; i4++) {
            bArr[(i3 - i4) - 1] = this.a[i4 / i2].a(i);
        }
        return bArr;
    }
}
