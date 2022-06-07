package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;

/* loaded from: classes2.dex */
public final class EAN8Reader extends UPCEANReader {
    private final int[] a = new int[4];

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.zxing.oned.UPCEANReader
    public int decodeMiddle(BitArray bitArray, int[] iArr, StringBuilder sb) throws NotFoundException {
        int[] iArr2 = this.a;
        iArr2[0] = 0;
        iArr2[1] = 0;
        iArr2[2] = 0;
        iArr2[3] = 0;
        int size = bitArray.getSize();
        int i = iArr[1];
        int i2 = 0;
        while (i2 < 4 && i < size) {
            sb.append((char) (a(bitArray, iArr2, i, e) + 48));
            int i3 = i;
            for (int i4 : iArr2) {
                i3 += i4;
            }
            i2++;
            i = i3;
        }
        int i5 = a(bitArray, i, true, c)[1];
        int i6 = 0;
        while (i6 < 4 && i5 < size) {
            sb.append((char) (a(bitArray, iArr2, i5, e) + 48));
            int i7 = i5;
            for (int i8 : iArr2) {
                i7 += i8;
            }
            i6++;
            i5 = i7;
        }
        return i5;
    }

    @Override // com.google.zxing.oned.UPCEANReader
    BarcodeFormat a() {
        return BarcodeFormat.EAN_8;
    }
}
