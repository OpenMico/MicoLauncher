package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import java.util.Arrays;
import java.util.Map;

/* loaded from: classes2.dex */
public final class CodaBarReader extends OneDReader {
    static final char[] a = "0123456789-$:/.+ABCD".toCharArray();
    static final int[] b = {3, 6, 9, 96, 18, 66, 33, 36, 48, 72, 12, 24, 69, 81, 84, 21, 26, 41, 11, 14};
    private static final char[] c = {'A', 'B', 'C', 'D'};
    private final StringBuilder d = new StringBuilder(20);
    private int[] e = new int[80];
    private int f = 0;

    @Override // com.google.zxing.oned.OneDReader
    public Result decodeRow(int i, BitArray bitArray, Map<DecodeHintType, ?> map) throws NotFoundException {
        Arrays.fill(this.e, 0);
        a(bitArray);
        int a2 = a();
        this.d.setLength(0);
        int i2 = a2;
        do {
            int c2 = c(i2);
            if (c2 != -1) {
                this.d.append((char) c2);
                i2 += 8;
                if (this.d.length() > 1 && a(c, a[c2])) {
                    break;
                }
            } else {
                throw NotFoundException.getNotFoundInstance();
            }
        } while (i2 < this.f);
        int i3 = i2 - 1;
        int i4 = this.e[i3];
        int i5 = 0;
        for (int i6 = -8; i6 < -1; i6++) {
            i5 += this.e[i2 + i6];
        }
        if (i2 >= this.f || i4 >= i5 / 2) {
            a(a2);
            for (int i7 = 0; i7 < this.d.length(); i7++) {
                StringBuilder sb = this.d;
                sb.setCharAt(i7, a[sb.charAt(i7)]);
            }
            if (a(c, this.d.charAt(0))) {
                StringBuilder sb2 = this.d;
                if (!a(c, sb2.charAt(sb2.length() - 1))) {
                    throw NotFoundException.getNotFoundInstance();
                } else if (this.d.length() > 3) {
                    if (map == null || !map.containsKey(DecodeHintType.RETURN_CODABAR_START_END)) {
                        StringBuilder sb3 = this.d;
                        sb3.deleteCharAt(sb3.length() - 1);
                        this.d.deleteCharAt(0);
                    }
                    int i8 = 0;
                    for (int i9 = 0; i9 < a2; i9++) {
                        i8 += this.e[i9];
                    }
                    float f = i8;
                    while (a2 < i3) {
                        i8 += this.e[a2];
                        a2++;
                    }
                    float f2 = i;
                    return new Result(this.d.toString(), null, new ResultPoint[]{new ResultPoint(f, f2), new ResultPoint(i8, f2)}, BarcodeFormat.CODABAR);
                } else {
                    throw NotFoundException.getNotFoundInstance();
                }
            } else {
                throw NotFoundException.getNotFoundInstance();
            }
        } else {
            throw NotFoundException.getNotFoundInstance();
        }
    }

    private void a(int i) throws NotFoundException {
        int[] iArr = {0, 0, 0, 0};
        int[] iArr2 = {0, 0, 0, 0};
        int length = this.d.length() - 1;
        int i2 = 0;
        int i3 = i;
        int i4 = 0;
        while (true) {
            int i5 = b[this.d.charAt(i4)];
            for (int i6 = 6; i6 >= 0; i6--) {
                int i7 = (i6 & 1) + ((i5 & 1) << 1);
                iArr[i7] = iArr[i7] + this.e[i3 + i6];
                iArr2[i7] = iArr2[i7] + 1;
                i5 >>= 1;
            }
            if (i4 >= length) {
                break;
            }
            i3 += 8;
            i4++;
        }
        float[] fArr = new float[4];
        float[] fArr2 = new float[4];
        for (int i8 = 0; i8 < 2; i8++) {
            fArr2[i8] = 0.0f;
            int i9 = i8 + 2;
            fArr2[i9] = ((iArr[i8] / iArr2[i8]) + (iArr[i9] / iArr2[i9])) / 2.0f;
            fArr[i8] = fArr2[i9];
            fArr[i9] = ((iArr[i9] * 2.0f) + 1.5f) / iArr2[i9];
        }
        loop3: while (true) {
            int i10 = b[this.d.charAt(i2)];
            for (int i11 = 6; i11 >= 0; i11--) {
                int i12 = (i11 & 1) + ((i10 & 1) << 1);
                float f = this.e[i + i11];
                if (f < fArr2[i12] || f > fArr[i12]) {
                    break loop3;
                }
                i10 >>= 1;
            }
            if (i2 < length) {
                i += 8;
                i2++;
            } else {
                return;
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private void a(BitArray bitArray) throws NotFoundException {
        int i = 0;
        this.f = 0;
        int nextUnset = bitArray.getNextUnset(0);
        int size = bitArray.getSize();
        if (nextUnset < size) {
            boolean z = true;
            while (nextUnset < size) {
                if (bitArray.get(nextUnset) != z) {
                    i++;
                } else {
                    b(i);
                    z = !z;
                    i = 1;
                }
                nextUnset++;
            }
            b(i);
            return;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private void b(int i) {
        int[] iArr = this.e;
        int i2 = this.f;
        iArr[i2] = i;
        this.f = i2 + 1;
        int i3 = this.f;
        if (i3 >= iArr.length) {
            int[] iArr2 = new int[i3 << 1];
            System.arraycopy(iArr, 0, iArr2, 0, i3);
            this.e = iArr2;
        }
    }

    private int a() throws NotFoundException {
        for (int i = 1; i < this.f; i += 2) {
            int c2 = c(i);
            if (c2 != -1 && a(c, a[c2])) {
                int i2 = 0;
                for (int i3 = i; i3 < i + 7; i3++) {
                    i2 += this.e[i3];
                }
                if (i == 1 || this.e[i - 1] >= i2 / 2) {
                    return i;
                }
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean a(char[] cArr, char c2) {
        if (cArr != null) {
            for (char c3 : cArr) {
                if (c3 == c2) {
                    return true;
                }
            }
        }
        return false;
    }

    private int c(int i) {
        int i2 = i + 7;
        if (i2 >= this.f) {
            return -1;
        }
        int[] iArr = this.e;
        int i3 = Integer.MAX_VALUE;
        int i4 = 0;
        int i5 = Integer.MAX_VALUE;
        int i6 = 0;
        for (int i7 = i; i7 < i2; i7 += 2) {
            int i8 = iArr[i7];
            if (i8 < i5) {
                i5 = i8;
            }
            if (i8 > i6) {
                i6 = i8;
            }
        }
        int i9 = (i5 + i6) / 2;
        int i10 = 0;
        for (int i11 = i + 1; i11 < i2; i11 += 2) {
            int i12 = iArr[i11];
            if (i12 < i3) {
                i3 = i12;
            }
            if (i12 > i10) {
                i10 = i12;
            }
        }
        int i13 = (i3 + i10) / 2;
        int i14 = 128;
        int i15 = 0;
        for (int i16 = 0; i16 < 7; i16++) {
            i14 >>= 1;
            if (iArr[i + i16] > ((i16 & 1) == 0 ? i9 : i13)) {
                i15 |= i14;
            }
        }
        while (true) {
            int[] iArr2 = b;
            if (i4 >= iArr2.length) {
                return -1;
            }
            if (iArr2[i4] == i15) {
                return i4;
            }
            i4++;
        }
    }
}
