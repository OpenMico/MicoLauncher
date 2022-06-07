package com.google.zxing.common.reedsolomon;

import com.google.android.exoplayer2.analytics.AnalyticsListener;
import io.netty.util.internal.StringUtil;

/* loaded from: classes2.dex */
public final class GenericGF {
    public static final GenericGF AZTEC_DATA_8;
    public static final GenericGF DATA_MATRIX_FIELD_256;
    private final int[] a;
    private final int[] b;
    private final a c;
    private final a d;
    private final int e;
    private final int f;
    private final int g;
    public static final GenericGF AZTEC_DATA_12 = new GenericGF(4201, 4096, 1);
    public static final GenericGF AZTEC_DATA_10 = new GenericGF(AnalyticsListener.EVENT_DRM_KEYS_RESTORED, 1024, 1);
    public static final GenericGF AZTEC_DATA_6 = new GenericGF(67, 64, 1);
    public static final GenericGF AZTEC_PARAM = new GenericGF(19, 16, 1);
    public static final GenericGF QR_CODE_FIELD_256 = new GenericGF(285, 256, 0);
    public static final GenericGF MAXICODE_FIELD_64 = AZTEC_DATA_6;

    public static int b(int i, int i2) {
        return i ^ i2;
    }

    static {
        GenericGF genericGF = new GenericGF(301, 256, 1);
        DATA_MATRIX_FIELD_256 = genericGF;
        AZTEC_DATA_8 = genericGF;
    }

    public GenericGF(int i, int i2, int i3) {
        this.f = i;
        this.e = i2;
        this.g = i3;
        this.a = new int[i2];
        this.b = new int[i2];
        int i4 = 1;
        for (int i5 = 0; i5 < i2; i5++) {
            this.a[i5] = i4;
            i4 <<= 1;
            if (i4 >= i2) {
                i4 = (i4 ^ i) & (i2 - 1);
            }
        }
        for (int i6 = 0; i6 < i2 - 1; i6++) {
            this.b[this.a[i6]] = i6;
        }
        this.c = new a(this, new int[]{0});
        this.d = new a(this, new int[]{1});
    }

    public a a() {
        return this.c;
    }

    public a b() {
        return this.d;
    }

    public a a(int i, int i2) {
        if (i < 0) {
            throw new IllegalArgumentException();
        } else if (i2 == 0) {
            return this.c;
        } else {
            int[] iArr = new int[i + 1];
            iArr[0] = i2;
            return new a(this, iArr);
        }
    }

    public int a(int i) {
        return this.a[i];
    }

    public int b(int i) {
        if (i != 0) {
            return this.b[i];
        }
        throw new IllegalArgumentException();
    }

    public int c(int i) {
        if (i != 0) {
            return this.a[(this.e - this.b[i]) - 1];
        }
        throw new ArithmeticException();
    }

    public int c(int i, int i2) {
        if (i == 0 || i2 == 0) {
            return 0;
        }
        int[] iArr = this.a;
        int[] iArr2 = this.b;
        return iArr[(iArr2[i] + iArr2[i2]) % (this.e - 1)];
    }

    public int getSize() {
        return this.e;
    }

    public int getGeneratorBase() {
        return this.g;
    }

    public String toString() {
        return "GF(0x" + Integer.toHexString(this.f) + StringUtil.COMMA + this.e + ')';
    }
}
