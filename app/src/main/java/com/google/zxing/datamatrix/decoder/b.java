package com.google.zxing.datamatrix.decoder;

import com.google.zxing.datamatrix.decoder.Version;

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
    public static b[] a(byte[] bArr, Version version) {
        Version.b a = version.a();
        Version.a[] b = a.b();
        int i = 0;
        for (Version.a aVar : b) {
            i += aVar.a();
        }
        b[] bVarArr = new b[i];
        int length = b.length;
        int i2 = 0;
        int i3 = 0;
        while (i2 < length) {
            Version.a aVar2 = b[i2];
            int i4 = i3;
            for (int i5 = 0; i5 < aVar2.a(); i5++) {
                int b2 = aVar2.b();
                i4++;
                bVarArr[i4] = new b(b2, new byte[a.a() + b2]);
            }
            i2++;
            i3 = i4;
        }
        int length2 = bVarArr[0].b.length - a.a();
        int i6 = length2 - 1;
        int i7 = 0;
        int i8 = 0;
        while (i7 < i6) {
            int i9 = i8;
            for (int i10 = 0; i10 < i3; i10++) {
                i9++;
                bVarArr[i10].b[i7] = bArr[i9];
            }
            i7++;
            i8 = i9;
        }
        boolean z = version.getVersionNumber() == 24;
        int i11 = z ? 8 : i3;
        int i12 = i8;
        for (int i13 = 0; i13 < i11; i13++) {
            i12++;
            bVarArr[i13].b[i6] = bArr[i12];
        }
        int length3 = bVarArr[0].b.length;
        while (length2 < length3) {
            for (int i14 = 0; i14 < i3; i14++) {
                int i15 = z ? (i14 + 8) % i3 : i14;
                i12++;
                bVarArr[i15].b[(!z || i15 <= 7) ? length2 : length2 - 1] = bArr[i12];
            }
            length2++;
        }
        if (i12 == bArr.length) {
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
