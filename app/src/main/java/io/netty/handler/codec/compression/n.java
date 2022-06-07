package io.netty.handler.codec.compression;

import com.google.common.base.Ascii;

/* compiled from: FastLz.java */
/* loaded from: classes4.dex */
final class n {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static int a(int i) {
        return Math.max((int) (i * 1.06d), 66);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Removed duplicated region for block: B:101:0x021a  */
    /* JADX WARN: Removed duplicated region for block: B:102:0x0225  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x0233  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x02f1  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x015e A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:157:0x0131 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0092  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00bf  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x016a  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0182  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int a(byte[] r22, int r23, int r24, byte[] r25, int r26, int r27) {
        /*
            Method dump skipped, instructions count: 966
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.codec.compression.n.a(byte[], int, int, byte[], int, int):int");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int b(byte[] bArr, int i, int i2, byte[] bArr2, int i3, int i4) {
        int i5;
        boolean z;
        boolean z2;
        byte b;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        char c = 5;
        boolean z3 = true;
        int i12 = (bArr[i] >> 5) + 1;
        if (i12 == 1 || i12 == 2) {
            long j = bArr[i + 0] & Ascii.US;
            int i13 = 1;
            boolean z4 = true;
            int i14 = 0;
            while (true) {
                long j2 = j >> c;
                long j3 = (31 & j) << 8;
                if (j >= 32) {
                    long j4 = j2 - 1;
                    long j5 = i14;
                    int i15 = (int) (j5 - j3);
                    if (j4 != 6) {
                        j = j;
                        z2 = z4;
                        b = 255;
                        i7 = i13;
                        i6 = 1;
                    } else if (i12 == 1) {
                        i7 = i13 + 1;
                        b = 255;
                        z2 = z4;
                        j4 += bArr[i + i13] & 255;
                        j = j;
                        i6 = 1;
                    } else {
                        z2 = z4;
                        b = 255;
                        while (true) {
                            i11 = i13 + 1;
                            int i16 = bArr[i + i13] & 255;
                            j4 += i16;
                            if (i16 != 255) {
                                break;
                            }
                            i13 = i11;
                            j = j;
                        }
                        i7 = i11;
                        i6 = 1;
                    }
                    if (i12 == i6) {
                        i8 = i7 + 1;
                        i9 = i15 - (bArr[i + i7] & b);
                    } else {
                        i8 = i7 + 1;
                        int i17 = bArr[i + i7] & b;
                        i9 = i15 - i17;
                        if (i17 == b && j3 == 7936) {
                            int i18 = i8 + 1;
                            i8 = i18 + 1;
                            i9 = (int) ((j5 - (((bArr[i + i8] & b) << 8) + (bArr[i + i18] & b))) - 8191);
                        }
                    }
                    if (j5 + j4 + 3 > i4 || i9 - 1 < 0) {
                        return 0;
                    }
                    if (i8 < i2) {
                        i8++;
                        j = bArr[i + i8] & 255;
                        i10 = i14;
                    } else {
                        i10 = i14;
                        z2 = false;
                    }
                    if (i9 == i10) {
                        z3 = true;
                        byte b2 = bArr2[(i3 + i9) - 1];
                        int i19 = i10 + 1;
                        bArr2[i3 + i10] = b2;
                        int i20 = i19 + 1;
                        bArr2[i3 + i19] = b2;
                        i5 = i20 + 1;
                        bArr2[i3 + i20] = b2;
                        while (j4 != 0) {
                            i5++;
                            bArr2[i3 + i5] = b2;
                            j4--;
                        }
                        i13 = i8;
                        z4 = z2;
                        j = j;
                        z = false;
                    } else {
                        z3 = true;
                        int i21 = i9 - 1;
                        int i22 = i10 + 1;
                        int i23 = i21 + 1;
                        bArr2[i3 + i10] = bArr2[i3 + i21];
                        int i24 = i22 + 1;
                        int i25 = i23 + 1;
                        bArr2[i3 + i22] = bArr2[i3 + i23];
                        int i26 = i24 + 1;
                        int i27 = i25 + 1;
                        bArr2[i3 + i24] = bArr2[i3 + i25];
                        while (j4 != 0) {
                            i26++;
                            i27++;
                            bArr2[i3 + i26] = bArr2[i3 + i27];
                            j4--;
                        }
                        i5 = i26;
                        i13 = i8;
                        z4 = z2;
                        j = j;
                        z = false;
                    }
                } else {
                    long j6 = j + 1;
                    if (i14 + j6 > i4) {
                        return 0;
                    }
                    z = false;
                    if (i13 + j6 > i2) {
                        return 0;
                    }
                    i5 = i14 + 1;
                    int i28 = i13 + 1;
                    bArr2[i3 + i14] = bArr[i + i13];
                    j = j6 - 1;
                    while (j != 0) {
                        i5++;
                        i28++;
                        bArr2[i3 + i5] = bArr[i + i28];
                        j--;
                    }
                    boolean z5 = i28 < i2 ? z3 : false;
                    if (z5) {
                        i13 = i28 + 1;
                        j = bArr[i + i28] & 255;
                        z4 = z5;
                    } else {
                        i13 = i28;
                        z4 = z5;
                    }
                }
                if (!z4) {
                    return i5;
                }
                c = 5;
                i14 = i5;
            }
        } else {
            throw new DecompressionException(String.format("invalid level: %d (expected: %d or %d)", Integer.valueOf(i12), 1, 2));
        }
    }

    private static int a(byte[] bArr, int i) {
        int b = b(bArr, i);
        return ((b(bArr, i + 1) ^ (b >> 3)) ^ b) & 8191;
    }

    private static int b(byte[] bArr, int i) {
        int i2 = i + 1;
        if (i2 >= bArr.length) {
            return bArr[i] & 255;
        }
        return (bArr[i] & 255) | ((bArr[i2] & 255) << 8);
    }
}
