package com.google.zxing.common;

import com.google.zxing.Binarizer;
import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;
import java.lang.reflect.Array;

/* loaded from: classes2.dex */
public final class HybridBinarizer extends GlobalHistogramBinarizer {
    private BitMatrix a;

    private static int a(int i, int i2, int i3) {
        return i < i2 ? i2 : i > i3 ? i3 : i;
    }

    public HybridBinarizer(LuminanceSource luminanceSource) {
        super(luminanceSource);
    }

    @Override // com.google.zxing.common.GlobalHistogramBinarizer, com.google.zxing.Binarizer
    public BitMatrix getBlackMatrix() throws NotFoundException {
        BitMatrix bitMatrix = this.a;
        if (bitMatrix != null) {
            return bitMatrix;
        }
        LuminanceSource luminanceSource = getLuminanceSource();
        int width = luminanceSource.getWidth();
        int height = luminanceSource.getHeight();
        if (width < 40 || height < 40) {
            this.a = super.getBlackMatrix();
        } else {
            byte[] matrix = luminanceSource.getMatrix();
            int i = width >> 3;
            int i2 = (width & 7) != 0 ? i + 1 : i;
            int i3 = height >> 3;
            int i4 = (height & 7) != 0 ? i3 + 1 : i3;
            int[][] a = a(matrix, i2, i4, width, height);
            BitMatrix bitMatrix2 = new BitMatrix(width, height);
            a(matrix, i2, i4, width, height, a, bitMatrix2);
            this.a = bitMatrix2;
        }
        return this.a;
    }

    @Override // com.google.zxing.common.GlobalHistogramBinarizer, com.google.zxing.Binarizer
    public Binarizer createBinarizer(LuminanceSource luminanceSource) {
        return new HybridBinarizer(luminanceSource);
    }

    private static void a(byte[] bArr, int i, int i2, int i3, int i4, int[][] iArr, BitMatrix bitMatrix) {
        int i5 = i4 - 8;
        int i6 = i3 - 8;
        for (int i7 = 0; i7 < i2; i7++) {
            int i8 = i7 << 3;
            int i9 = i8 > i5 ? i5 : i8;
            int a = a(i7, 2, i2 - 3);
            for (int i10 = 0; i10 < i; i10++) {
                int i11 = i10 << 3;
                int i12 = i11 > i6 ? i6 : i11;
                int a2 = a(i10, 2, i - 3);
                int i13 = 0;
                for (int i14 = -2; i14 <= 2; i14++) {
                    int[] iArr2 = iArr[a + i14];
                    i13 += iArr2[a2 - 2] + iArr2[a2 - 1] + iArr2[a2] + iArr2[a2 + 1] + iArr2[a2 + 2];
                }
                a(bArr, i12, i9, i13 / 25, i3, bitMatrix);
            }
        }
    }

    private static void a(byte[] bArr, int i, int i2, int i3, int i4, BitMatrix bitMatrix) {
        int i5 = (i2 * i4) + i;
        int i6 = 0;
        while (i6 < 8) {
            for (int i7 = 0; i7 < 8; i7++) {
                if ((bArr[i5 + i7] & 255) <= i3) {
                    bitMatrix.set(i + i7, i2 + i6);
                }
            }
            i6++;
            i5 += i4;
        }
    }

    private static int[][] a(byte[] bArr, int i, int i2, int i3, int i4) {
        int i5 = 8;
        int i6 = i4 - 8;
        int i7 = i3 - 8;
        int[][] iArr = (int[][]) Array.newInstance(int.class, i2, i);
        for (int i8 = 0; i8 < i2; i8++) {
            int i9 = i8 << 3;
            if (i9 > i6) {
                i9 = i6;
            }
            for (int i10 = 0; i10 < i; i10++) {
                int i11 = i10 << 3;
                if (i11 > i7) {
                    i11 = i7;
                }
                int i12 = (i9 * i3) + i11;
                int i13 = 255;
                int i14 = 0;
                int i15 = 0;
                int i16 = 0;
                while (i14 < i5) {
                    i15 = i15;
                    int i17 = 0;
                    while (i17 < i5) {
                        int i18 = bArr[i12 + i17] & 255;
                        i15 += i18;
                        if (i18 < i13) {
                            i13 = i18;
                        }
                        if (i18 > i16) {
                            i16 = i18;
                        }
                        i17++;
                        i5 = 8;
                    }
                    if (i16 - i13 > 24) {
                        i14++;
                        i12 += i3;
                        i5 = 8;
                        while (i14 < 8) {
                            for (int i19 = 0; i19 < 8; i19++) {
                                i15 += bArr[i12 + i19] & 255;
                            }
                            i14++;
                            i12 += i3;
                        }
                    } else {
                        i5 = 8;
                    }
                    i14++;
                    i12 += i3;
                }
                int i20 = i15 >> 6;
                if (i16 - i13 <= 24) {
                    i20 = i13 / 2;
                    if (i8 > 0 && i10 > 0) {
                        int i21 = i8 - 1;
                        int i22 = i10 - 1;
                        int i23 = ((iArr[i21][i10] + (iArr[i8][i22] * 2)) + iArr[i21][i22]) / 4;
                        if (i13 < i23) {
                            i20 = i23;
                        }
                    }
                }
                iArr[i8][i10] = i20;
            }
        }
        return iArr;
    }
}
