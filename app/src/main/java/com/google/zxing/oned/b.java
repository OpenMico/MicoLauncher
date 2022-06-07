package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import java.util.EnumMap;
import java.util.Map;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: UPCEANExtension2Support.java */
/* loaded from: classes2.dex */
public final class b {
    private final int[] a = new int[4];
    private final StringBuilder b = new StringBuilder();

    /* JADX INFO: Access modifiers changed from: package-private */
    public Result a(int i, BitArray bitArray, int[] iArr) throws NotFoundException {
        StringBuilder sb = this.b;
        sb.setLength(0);
        int a = a(bitArray, iArr, sb);
        String sb2 = sb.toString();
        Map<ResultMetadataType, Object> a2 = a(sb2);
        float f = i;
        Result result = new Result(sb2, null, new ResultPoint[]{new ResultPoint((iArr[0] + iArr[1]) / 2.0f, f), new ResultPoint(a, f)}, BarcodeFormat.UPC_EAN_EXTENSION);
        if (a2 != null) {
            result.putAllMetadata(a2);
        }
        return result;
    }

    private int a(BitArray bitArray, int[] iArr, StringBuilder sb) throws NotFoundException {
        int[] iArr2 = this.a;
        iArr2[0] = 0;
        iArr2[1] = 0;
        iArr2[2] = 0;
        iArr2[3] = 0;
        int size = bitArray.getSize();
        int i = iArr[1];
        int i2 = 0;
        int i3 = 0;
        while (i2 < 2 && i < size) {
            int a = UPCEANReader.a(bitArray, iArr2, i, UPCEANReader.f);
            sb.append((char) ((a % 10) + 48));
            int i4 = i;
            for (int i5 : iArr2) {
                i4 += i5;
            }
            if (a >= 10) {
                i3 = (1 << (1 - i2)) | i3;
            }
            i = i2 != 1 ? bitArray.getNextUnset(bitArray.getNextSet(i4)) : i4;
            i2++;
        }
        if (sb.length() != 2) {
            throw NotFoundException.getNotFoundInstance();
        } else if (Integer.parseInt(sb.toString()) % 4 == i3) {
            return i;
        } else {
            throw NotFoundException.getNotFoundInstance();
        }
    }

    private static Map<ResultMetadataType, Object> a(String str) {
        if (str.length() != 2) {
            return null;
        }
        EnumMap enumMap = new EnumMap(ResultMetadataType.class);
        enumMap.put((EnumMap) ResultMetadataType.ISSUE_NUMBER, (ResultMetadataType) Integer.valueOf(str));
        return enumMap;
    }
}
