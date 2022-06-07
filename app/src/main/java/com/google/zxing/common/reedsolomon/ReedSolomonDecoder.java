package com.google.zxing.common.reedsolomon;

/* loaded from: classes2.dex */
public final class ReedSolomonDecoder {
    private final GenericGF a;

    public ReedSolomonDecoder(GenericGF genericGF) {
        this.a = genericGF;
    }

    public void decode(int[] iArr, int i) throws ReedSolomonException {
        a aVar = new a(this.a, iArr);
        int[] iArr2 = new int[i];
        boolean z = true;
        for (int i2 = 0; i2 < i; i2++) {
            GenericGF genericGF = this.a;
            int b = aVar.b(genericGF.a(genericGF.getGeneratorBase() + i2));
            iArr2[(i - 1) - i2] = b;
            if (b != 0) {
                z = false;
            }
        }
        if (!z) {
            a[] a = a(this.a.a(i, 1), new a(this.a, iArr2), i);
            a aVar2 = a[0];
            a aVar3 = a[1];
            int[] a2 = a(aVar2);
            int[] a3 = a(aVar3, a2);
            for (int i3 = 0; i3 < a2.length; i3++) {
                int length = (iArr.length - 1) - this.a.b(a2[i3]);
                if (length >= 0) {
                    iArr[length] = GenericGF.b(iArr[length], a3[i3]);
                } else {
                    throw new ReedSolomonException("Bad error location");
                }
            }
        }
    }

    private a[] a(a aVar, a aVar2, int i) throws ReedSolomonException {
        if (aVar2.b() < aVar2.b()) {
            aVar2 = aVar2;
            aVar2 = aVar2;
        }
        a a = this.a.a();
        a = this.a.b();
        while (aVar2.b() >= i / 2) {
            if (!aVar2.c()) {
                a a2 = this.a.a();
                int c = this.a.c(aVar2.a(aVar2.b()));
                while (aVar2.b() >= aVar2.b() && !aVar2.c()) {
                    int b = aVar2.b() - aVar2.b();
                    int c2 = this.a.c(aVar2.a(aVar2.b()), c);
                    a2 = a2.a(this.a.a(b, c2));
                    aVar2 = aVar2.a(aVar2.a(b, c2));
                }
                a = a2.b(a).a(a);
                if (aVar2.b() >= aVar2.b()) {
                    throw new IllegalStateException("Division algorithm failed to reduce polynomial?");
                }
            } else {
                throw new ReedSolomonException("r_{i-1} was zero");
            }
        }
        int a3 = a.a(0);
        if (a3 != 0) {
            int c3 = this.a.c(a3);
            return new a[]{a.c(c3), aVar2.c(c3)};
        }
        throw new ReedSolomonException("sigmaTilde(0) was zero");
    }

    private int[] a(a aVar) throws ReedSolomonException {
        int b = aVar.b();
        int i = 0;
        if (b == 1) {
            return new int[]{aVar.a(1)};
        }
        int[] iArr = new int[b];
        for (int i2 = 1; i2 < this.a.getSize() && i < b; i2++) {
            if (aVar.b(i2) == 0) {
                iArr[i] = this.a.c(i2);
                i++;
            }
        }
        if (i == b) {
            return iArr;
        }
        throw new ReedSolomonException("Error locator degree does not match number of roots");
    }

    private int[] a(a aVar, int[] iArr) {
        int length = iArr.length;
        int[] iArr2 = new int[length];
        for (int i = 0; i < length; i++) {
            int c = this.a.c(iArr[i]);
            int i2 = 1;
            for (int i3 = 0; i3 < length; i3++) {
                if (i != i3) {
                    int c2 = this.a.c(iArr[i3], c);
                    i2 = this.a.c(i2, (c2 & 1) == 0 ? c2 | 1 : c2 & (-2));
                }
            }
            iArr2[i] = this.a.c(aVar.b(c), this.a.c(i2));
            if (this.a.getGeneratorBase() != 0) {
                iArr2[i] = this.a.c(iArr2[i], c);
            }
        }
        return iArr2;
    }
}
