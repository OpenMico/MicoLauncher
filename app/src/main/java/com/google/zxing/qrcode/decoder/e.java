package com.google.zxing.qrcode.decoder;

/* compiled from: FormatInformation.java */
/* loaded from: classes2.dex */
final class e {
    private static final int[][] a = {new int[]{21522, 0}, new int[]{20773, 1}, new int[]{24188, 2}, new int[]{23371, 3}, new int[]{17913, 4}, new int[]{16590, 5}, new int[]{20375, 6}, new int[]{19104, 7}, new int[]{30660, 8}, new int[]{29427, 9}, new int[]{32170, 10}, new int[]{30877, 11}, new int[]{26159, 12}, new int[]{25368, 13}, new int[]{27713, 14}, new int[]{26998, 15}, new int[]{5769, 16}, new int[]{5054, 17}, new int[]{7399, 18}, new int[]{6608, 19}, new int[]{1890, 20}, new int[]{597, 21}, new int[]{3340, 22}, new int[]{2107, 23}, new int[]{13663, 24}, new int[]{12392, 25}, new int[]{16177, 26}, new int[]{14854, 27}, new int[]{9396, 28}, new int[]{8579, 29}, new int[]{11994, 30}, new int[]{11245, 31}};
    private final ErrorCorrectionLevel b;
    private final byte c;

    private e(int i) {
        this.b = ErrorCorrectionLevel.forBits((i >> 3) & 3);
        this.c = (byte) (i & 7);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int a(int i, int i2) {
        return Integer.bitCount(i ^ i2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static e b(int i, int i2) {
        e c = c(i, i2);
        return c != null ? c : c(i ^ 21522, i2 ^ 21522);
    }

    private static e c(int i, int i2) {
        int a2;
        int[][] iArr = a;
        int i3 = Integer.MAX_VALUE;
        int i4 = 0;
        for (int[] iArr2 : iArr) {
            int i5 = iArr2[0];
            if (i5 == i || i5 == i2) {
                return new e(iArr2[1]);
            }
            int a3 = a(i, i5);
            if (a3 < i3) {
                i4 = iArr2[1];
                i3 = a3;
            }
            if (i != i2 && (a2 = a(i2, i5)) < i3) {
                i4 = iArr2[1];
                i3 = a2;
            }
        }
        if (i3 <= 3) {
            return new e(i4);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ErrorCorrectionLevel a() {
        return this.b;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public byte b() {
        return this.c;
    }

    public int hashCode() {
        return (this.b.ordinal() << 3) | this.c;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof e)) {
            return false;
        }
        e eVar = (e) obj;
        return this.b == eVar.b && this.c == eVar.c;
    }
}
