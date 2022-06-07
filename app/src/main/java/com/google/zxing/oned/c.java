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
/* compiled from: UPCEANExtension5Support.java */
/* loaded from: classes2.dex */
public final class c {
    private static final int[] a = {24, 20, 18, 17, 12, 6, 3, 10, 9, 5};
    private final int[] b = new int[4];
    private final StringBuilder c = new StringBuilder();

    /* JADX INFO: Access modifiers changed from: package-private */
    public Result a(int i, BitArray bitArray, int[] iArr) throws NotFoundException {
        StringBuilder sb = this.c;
        sb.setLength(0);
        int a2 = a(bitArray, iArr, sb);
        String sb2 = sb.toString();
        Map<ResultMetadataType, Object> a3 = a(sb2);
        float f = i;
        Result result = new Result(sb2, null, new ResultPoint[]{new ResultPoint((iArr[0] + iArr[1]) / 2.0f, f), new ResultPoint(a2, f)}, BarcodeFormat.UPC_EAN_EXTENSION);
        if (a3 != null) {
            result.putAllMetadata(a3);
        }
        return result;
    }

    private int a(BitArray bitArray, int[] iArr, StringBuilder sb) throws NotFoundException {
        int[] iArr2 = this.b;
        iArr2[0] = 0;
        iArr2[1] = 0;
        iArr2[2] = 0;
        iArr2[3] = 0;
        int size = bitArray.getSize();
        int i = iArr[1];
        int i2 = 0;
        int i3 = 0;
        while (i2 < 5 && i < size) {
            int a2 = UPCEANReader.a(bitArray, iArr2, i, UPCEANReader.f);
            sb.append((char) ((a2 % 10) + 48));
            int i4 = i;
            for (int i5 : iArr2) {
                i4 += i5;
            }
            if (a2 >= 10) {
                i3 |= 1 << (4 - i2);
            }
            i = i2 != 4 ? bitArray.getNextUnset(bitArray.getNextSet(i4)) : i4;
            i2++;
        }
        if (sb.length() == 5) {
            if (a((CharSequence) sb.toString()) == a(i3)) {
                return i;
            }
            throw NotFoundException.getNotFoundInstance();
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static int a(CharSequence charSequence) {
        int length = charSequence.length();
        int i = 0;
        for (int i2 = length - 2; i2 >= 0; i2 -= 2) {
            i += charSequence.charAt(i2) - '0';
        }
        int i3 = i * 3;
        for (int i4 = length - 1; i4 >= 0; i4 -= 2) {
            i3 += charSequence.charAt(i4) - '0';
        }
        return (i3 * 3) % 10;
    }

    private static int a(int i) throws NotFoundException {
        for (int i2 = 0; i2 < 10; i2++) {
            if (i == a[i2]) {
                return i2;
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static Map<ResultMetadataType, Object> a(String str) {
        String b;
        if (str.length() != 5 || (b = b(str)) == null) {
            return null;
        }
        EnumMap enumMap = new EnumMap(ResultMetadataType.class);
        enumMap.put((EnumMap) ResultMetadataType.SUGGESTED_PRICE, (ResultMetadataType) b);
        return enumMap;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x003d, code lost:
        if (r5.equals("90000") != false) goto L_0x0041;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.lang.String b(java.lang.String r5) {
        /*
            r0 = 0
            char r1 = r5.charAt(r0)
            r2 = 48
            r3 = 1
            if (r1 == r2) goto L_0x0052
            r2 = 53
            if (r1 == r2) goto L_0x004f
            r2 = 57
            if (r1 == r2) goto L_0x0015
            java.lang.String r0 = ""
            goto L_0x0054
        L_0x0015:
            r1 = -1
            int r2 = r5.hashCode()
            r4 = 54118329(0x339c7b9, float:5.4595884E-37)
            if (r2 == r4) goto L_0x0037
            switch(r2) {
                case 54395376: goto L_0x002d;
                case 54395377: goto L_0x0023;
                default: goto L_0x0022;
            }
        L_0x0022:
            goto L_0x0040
        L_0x0023:
            java.lang.String r0 = "99991"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x0040
            r0 = r3
            goto L_0x0041
        L_0x002d:
            java.lang.String r0 = "99990"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x0040
            r0 = 2
            goto L_0x0041
        L_0x0037:
            java.lang.String r2 = "90000"
            boolean r2 = r5.equals(r2)
            if (r2 == 0) goto L_0x0040
            goto L_0x0041
        L_0x0040:
            r0 = r1
        L_0x0041:
            switch(r0) {
                case 0: goto L_0x004d;
                case 1: goto L_0x004a;
                case 2: goto L_0x0047;
                default: goto L_0x0044;
            }
        L_0x0044:
            java.lang.String r0 = ""
            goto L_0x0054
        L_0x0047:
            java.lang.String r5 = "Used"
            return r5
        L_0x004a:
            java.lang.String r5 = "0.00"
            return r5
        L_0x004d:
            r5 = 0
            return r5
        L_0x004f:
            java.lang.String r0 = "$"
            goto L_0x0054
        L_0x0052:
            java.lang.String r0 = "£"
        L_0x0054:
            java.lang.String r5 = r5.substring(r3)
            int r5 = java.lang.Integer.parseInt(r5)
            int r1 = r5 / 100
            java.lang.String r1 = java.lang.String.valueOf(r1)
            int r5 = r5 % 100
            r2 = 10
            if (r5 >= r2) goto L_0x0077
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "0"
            r2.<init>(r3)
            r2.append(r5)
            java.lang.String r5 = r2.toString()
            goto L_0x007b
        L_0x0077:
            java.lang.String r5 = java.lang.String.valueOf(r5)
        L_0x007b:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r0)
            r2.append(r1)
            r0 = 46
            r2.append(r0)
            r2.append(r5)
            java.lang.String r5 = r2.toString()
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.oned.c.b(java.lang.String):java.lang.String");
    }
}
