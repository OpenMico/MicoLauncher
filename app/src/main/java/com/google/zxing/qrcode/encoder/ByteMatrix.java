package com.google.zxing.qrcode.encoder;

import java.lang.reflect.Array;
import java.util.Arrays;

/* loaded from: classes2.dex */
public final class ByteMatrix {
    private final byte[][] a;
    private final int b;
    private final int c;

    public ByteMatrix(int i, int i2) {
        this.a = (byte[][]) Array.newInstance(byte.class, i2, i);
        this.b = i;
        this.c = i2;
    }

    public int getHeight() {
        return this.c;
    }

    public int getWidth() {
        return this.b;
    }

    public byte get(int i, int i2) {
        return this.a[i2][i];
    }

    public byte[][] getArray() {
        return this.a;
    }

    public void set(int i, int i2, byte b) {
        this.a[i2][i] = b;
    }

    public void set(int i, int i2, int i3) {
        this.a[i2][i] = (byte) i3;
    }

    public void set(int i, int i2, boolean z) {
        this.a[i2][i] = z ? (byte) 1 : (byte) 0;
    }

    public void clear(byte b) {
        for (byte[] bArr : this.a) {
            Arrays.fill(bArr, b);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder((this.b * 2 * this.c) + 2);
        for (int i = 0; i < this.c; i++) {
            byte[] bArr = this.a[i];
            for (int i2 = 0; i2 < this.b; i2++) {
                switch (bArr[i2]) {
                    case 0:
                        sb.append(" 0");
                        break;
                    case 1:
                        sb.append(" 1");
                        break;
                    default:
                        sb.append("  ");
                        break;
                }
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
