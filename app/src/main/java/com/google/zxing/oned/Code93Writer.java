package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import java.util.Map;

/* loaded from: classes2.dex */
public class Code93Writer extends OneDimensionalCodeWriter {
    @Override // com.google.zxing.oned.OneDimensionalCodeWriter, com.google.zxing.Writer
    public BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i, int i2, Map<EncodeHintType, ?> map) throws WriterException {
        if (barcodeFormat == BarcodeFormat.CODE_93) {
            return super.encode(str, barcodeFormat, i, i2, map);
        }
        throw new IllegalArgumentException("Can only encode CODE_93, but got " + barcodeFormat);
    }

    @Override // com.google.zxing.oned.OneDimensionalCodeWriter
    public boolean[] encode(String str) {
        int length = str.length();
        if (length <= 80) {
            int[] iArr = new int[9];
            a(Code93Reader.a[47], iArr);
            boolean[] zArr = new boolean[((str.length() + 2 + 2) * 9) + 1];
            int a = a(zArr, 0, iArr);
            for (int i = 0; i < length; i++) {
                a(Code93Reader.a["0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%abcd*".indexOf(str.charAt(i))], iArr);
                a += a(zArr, a, iArr);
            }
            int a2 = a(str, 20);
            a(Code93Reader.a[a2], iArr);
            int a3 = a + a(zArr, a, iArr);
            a(Code93Reader.a[a(str + "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%abcd*".charAt(a2), 15)], iArr);
            int a4 = a3 + a(zArr, a3, iArr);
            a(Code93Reader.a[47], iArr);
            zArr[a4 + a(zArr, a4, iArr)] = true;
            return zArr;
        }
        throw new IllegalArgumentException("Requested contents should be less than 80 digits long, but got " + length);
    }

    private static void a(int i, int[] iArr) {
        for (int i2 = 0; i2 < 9; i2++) {
            int i3 = 1;
            if (((1 << (8 - i2)) & i) == 0) {
                i3 = 0;
            }
            iArr[i2] = i3;
        }
    }

    @Deprecated
    protected static int appendPattern(boolean[] zArr, int i, int[] iArr, boolean z) {
        return a(zArr, i, iArr);
    }

    private static int a(boolean[] zArr, int i, int[] iArr) {
        int i2 = i;
        for (int i3 : iArr) {
            i2++;
            zArr[i2] = i3 != 0;
        }
        return 9;
    }

    private static int a(String str, int i) {
        int i2 = 0;
        int i3 = 1;
        for (int length = str.length() - 1; length >= 0; length--) {
            i2 += "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%abcd*".indexOf(str.charAt(length)) * i3;
            i3++;
            if (i3 > i) {
                i3 = 1;
            }
        }
        return i2 % 47;
    }
}
