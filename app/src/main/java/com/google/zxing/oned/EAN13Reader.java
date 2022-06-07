package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;

/* loaded from: classes2.dex */
public final class EAN13Reader extends UPCEANReader {
    static final int[] a = {0, 11, 13, 14, 19, 25, 28, 21, 22, 26};
    private final int[] g = new int[4];

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.zxing.oned.UPCEANReader
    public int decodeMiddle(BitArray bitArray, int[] iArr, StringBuilder sb) throws NotFoundException {
        int[] iArr2 = this.g;
        iArr2[0] = 0;
        iArr2[1] = 0;
        iArr2[2] = 0;
        iArr2[3] = 0;
        int size = bitArray.getSize();
        int i = iArr[1];
        int i2 = 0;
        int i3 = 0;
        while (i2 < 6 && i < size) {
            int a2 = a(bitArray, iArr2, i, f);
            sb.append((char) ((a2 % 10) + 48));
            int i4 = i;
            for (int i5 : iArr2) {
                i4 += i5;
            }
            if (a2 >= 10) {
                i3 = (1 << (5 - i2)) | i3;
            }
            i2++;
            i = i4;
        }
        a(sb, i3);
        int i6 = a(bitArray, i, true, c)[1];
        int i7 = 0;
        while (i7 < 6 && i6 < size) {
            sb.append((char) (a(bitArray, iArr2, i6, e) + 48));
            int i8 = i6;
            for (int i9 : iArr2) {
                i8 += i9;
            }
            i7++;
            i6 = i8;
        }
        return i6;
    }

    @Override // com.google.zxing.oned.UPCEANReader
    BarcodeFormat a() {
        return BarcodeFormat.EAN_13;
    }

    private static void a(StringBuilder sb, int i) throws NotFoundException {
        for (int i2 = 0; i2 < 10; i2++) {
            if (i == a[i2]) {
                sb.insert(0, (char) (i2 + 48));
                return;
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }
}
