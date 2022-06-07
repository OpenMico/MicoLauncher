package com.google.zxing.pdf417.decoder.ec;

import com.google.zxing.ChecksumException;

/* loaded from: classes2.dex */
public final class ErrorCorrection {
    private final ModulusGF a = ModulusGF.PDF417_GF;

    public int decode(int[] iArr, int i, int[] iArr2) throws ChecksumException {
        a aVar = new a(this.a, iArr);
        int[] iArr3 = new int[i];
        boolean z = false;
        for (int i2 = i; i2 > 0; i2--) {
            int b = aVar.b(this.a.a(i2));
            iArr3[i - i2] = b;
            if (b != 0) {
                z = true;
            }
        }
        if (!z) {
            return 0;
        }
        a b2 = this.a.b();
        if (iArr2 != null) {
            for (int i3 : iArr2) {
                int a = this.a.a((iArr.length - 1) - i3);
                ModulusGF modulusGF = this.a;
                b2 = b2.c(new a(modulusGF, new int[]{modulusGF.c(0, a), 1}));
            }
        }
        a[] a2 = a(this.a.a(i, 1), new a(this.a, iArr3), i);
        a aVar2 = a2[0];
        a aVar3 = a2[1];
        int[] a3 = a(aVar2);
        int[] a4 = a(aVar3, aVar2, a3);
        for (int i4 = 0; i4 < a3.length; i4++) {
            int length = (iArr.length - 1) - this.a.b(a3[i4]);
            if (length >= 0) {
                iArr[length] = this.a.c(iArr[length], a4[i4]);
            } else {
                throw ChecksumException.getChecksumInstance();
            }
        }
        return a3.length;
    }

    private a[] a(a aVar, a aVar2, int i) throws ChecksumException {
        if (aVar2.a() < aVar2.a()) {
            aVar2 = aVar2;
            aVar2 = aVar2;
        }
        a a = this.a.a();
        a = this.a.b();
        while (aVar2.a() >= i / 2) {
            if (!aVar2.b()) {
                a a2 = this.a.a();
                int c = this.a.c(aVar2.a(aVar2.a()));
                while (aVar2.a() >= aVar2.a() && !aVar2.b()) {
                    int a3 = aVar2.a() - aVar2.a();
                    int d = this.a.d(aVar2.a(aVar2.a()), c);
                    a2 = a2.a(this.a.a(a3, d));
                    aVar2 = aVar2.b(aVar2.a(a3, d));
                }
                a = a2.c(a).b(a).c();
            } else {
                throw ChecksumException.getChecksumInstance();
            }
        }
        int a4 = a.a(0);
        if (a4 != 0) {
            int c2 = this.a.c(a4);
            return new a[]{a.c(c2), aVar2.c(c2)};
        }
        throw ChecksumException.getChecksumInstance();
    }

    private int[] a(a aVar) throws ChecksumException {
        int a = aVar.a();
        int[] iArr = new int[a];
        int i = 0;
        for (int i2 = 1; i2 < this.a.c() && i < a; i2++) {
            if (aVar.b(i2) == 0) {
                iArr[i] = this.a.c(i2);
                i++;
            }
        }
        if (i == a) {
            return iArr;
        }
        throw ChecksumException.getChecksumInstance();
    }

    private int[] a(a aVar, a aVar2, int[] iArr) {
        int a = aVar2.a();
        int[] iArr2 = new int[a];
        for (int i = 1; i <= a; i++) {
            iArr2[a - i] = this.a.d(i, aVar2.a(i));
        }
        a aVar3 = new a(this.a, iArr2);
        int length = iArr.length;
        int[] iArr3 = new int[length];
        for (int i2 = 0; i2 < length; i2++) {
            int c = this.a.c(iArr[i2]);
            iArr3[i2] = this.a.d(this.a.c(0, aVar.b(c)), this.a.c(aVar3.b(c)));
        }
        return iArr3;
    }
}
