package com.google.zxing.qrcode.decoder;

import com.google.zxing.qrcode.decoder.Version;

/* compiled from: DataBlock.java */
/* loaded from: classes2.dex */
final class b {
    private final int a;
    private final byte[] b;

    private b(int i, byte[] bArr) {
        this.a = i;
        this.b = bArr;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static b[] a(byte[] bArr, Version version, ErrorCorrectionLevel errorCorrectionLevel) {
        if (bArr.length == version.getTotalCodewords()) {
            Version.ECBlocks eCBlocksForLevel = version.getECBlocksForLevel(errorCorrectionLevel);
            Version.ECB[] eCBlocks = eCBlocksForLevel.getECBlocks();
            int i = 0;
            for (Version.ECB ecb : eCBlocks) {
                i += ecb.getCount();
            }
            b[] bVarArr = new b[i];
            int length = eCBlocks.length;
            int i2 = 0;
            int i3 = 0;
            while (i2 < length) {
                Version.ECB ecb2 = eCBlocks[i2];
                int i4 = i3;
                for (int i5 = 0; i5 < ecb2.getCount(); i5++) {
                    int dataCodewords = ecb2.getDataCodewords();
                    i4++;
                    bVarArr[i4] = new b(dataCodewords, new byte[eCBlocksForLevel.getECCodewordsPerBlock() + dataCodewords]);
                }
                i2++;
                i3 = i4;
            }
            int length2 = bVarArr[0].b.length;
            int length3 = bVarArr.length - 1;
            while (length3 >= 0 && bVarArr[length3].b.length != length2) {
                length3--;
            }
            int i6 = length3 + 1;
            int eCCodewordsPerBlock = length2 - eCBlocksForLevel.getECCodewordsPerBlock();
            int i7 = 0;
            int i8 = 0;
            while (i7 < eCCodewordsPerBlock) {
                int i9 = i8;
                for (int i10 = 0; i10 < i3; i10++) {
                    i9++;
                    bVarArr[i10].b[i7] = bArr[i9];
                }
                i7++;
                i8 = i9;
            }
            for (int i11 = i6; i11 < i3; i11++) {
                i8++;
                bVarArr[i11].b[eCCodewordsPerBlock] = bArr[i8];
            }
            int length4 = bVarArr[0].b.length;
            while (eCCodewordsPerBlock < length4) {
                int i12 = 0;
                while (i12 < i3) {
                    i8++;
                    bVarArr[i12].b[i12 < i6 ? eCCodewordsPerBlock : eCCodewordsPerBlock + 1] = bArr[i8];
                    i12++;
                }
                eCCodewordsPerBlock++;
                i8 = i8;
            }
            return bVarArr;
        }
        throw new IllegalArgumentException();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int a() {
        return this.a;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public byte[] b() {
        return this.b;
    }
}
