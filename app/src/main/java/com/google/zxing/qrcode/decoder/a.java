package com.google.zxing.qrcode.decoder;

import com.google.zxing.FormatException;
import com.google.zxing.common.BitMatrix;

/* compiled from: BitMatrixParser.java */
/* loaded from: classes2.dex */
final class a {
    private final BitMatrix a;
    private Version b;
    private e c;
    private boolean d;

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(BitMatrix bitMatrix) throws FormatException {
        int height = bitMatrix.getHeight();
        if (height < 21 || (height & 3) != 1) {
            throw FormatException.getFormatInstance();
        }
        this.a = bitMatrix;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public e a() throws FormatException {
        e eVar = this.c;
        if (eVar != null) {
            return eVar;
        }
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < 6; i3++) {
            i2 = a(i3, 8, i2);
        }
        int a = a(8, 7, a(8, 8, a(7, 8, i2)));
        for (int i4 = 5; i4 >= 0; i4--) {
            a = a(8, i4, a);
        }
        int height = this.a.getHeight();
        int i5 = height - 7;
        for (int i6 = height - 1; i6 >= i5; i6--) {
            i = a(8, i6, i);
        }
        for (int i7 = height - 8; i7 < height; i7++) {
            i = a(i7, 8, i);
        }
        this.c = e.b(a, i);
        e eVar2 = this.c;
        if (eVar2 != null) {
            return eVar2;
        }
        throw FormatException.getFormatInstance();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Version b() throws FormatException {
        Version version = this.b;
        if (version != null) {
            return version;
        }
        int height = this.a.getHeight();
        int i = (height - 17) / 4;
        if (i <= 6) {
            return Version.getVersionForNumber(i);
        }
        int i2 = height - 11;
        int i3 = 0;
        int i4 = 0;
        for (int i5 = 5; i5 >= 0; i5--) {
            for (int i6 = height - 9; i6 >= i2; i6--) {
                i4 = a(i6, i5, i4);
            }
        }
        Version a = Version.a(i4);
        if (a == null || a.getDimensionForVersion() != height) {
            for (int i7 = 5; i7 >= 0; i7--) {
                for (int i8 = height - 9; i8 >= i2; i8--) {
                    i3 = a(i7, i8, i3);
                }
            }
            Version a2 = Version.a(i3);
            if (a2 == null || a2.getDimensionForVersion() != height) {
                throw FormatException.getFormatInstance();
            }
            this.b = a2;
            return a2;
        }
        this.b = a;
        return a;
    }

    private int a(int i, int i2, int i3) {
        return this.d ? this.a.get(i2, i) : this.a.get(i, i2) ? (i3 << 1) | 1 : i3 << 1;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public byte[] c() throws FormatException {
        e a = a();
        Version b = b();
        c cVar = c.values()[a.b()];
        int height = this.a.getHeight();
        cVar.a(this.a, height);
        BitMatrix a2 = b.a();
        byte[] bArr = new byte[b.getTotalCodewords()];
        int i = height - 1;
        boolean z = true;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = i;
        while (i5 > 0) {
            if (i5 == 6) {
                i5--;
            }
            int i6 = i2;
            int i7 = 0;
            while (i7 < height) {
                int i8 = z ? i - i7 : i7;
                int i9 = i4;
                int i10 = i3;
                int i11 = i6;
                for (int i12 = 0; i12 < 2; i12++) {
                    int i13 = i5 - i12;
                    if (!a2.get(i13, i8)) {
                        i10++;
                        int i14 = i9 << 1;
                        int i15 = this.a.get(i13, i8) ? i14 | 1 : i14;
                        if (i10 == 8) {
                            i11++;
                            bArr[i11] = (byte) i15;
                            i10 = 0;
                            i9 = 0;
                        } else {
                            i9 = i15;
                        }
                    }
                }
                i7++;
                i6 = i11;
                i3 = i10;
                i4 = i9;
            }
            z = !z;
            i5 -= 2;
            i2 = i6;
            i3 = i3;
            i4 = i4;
        }
        if (i2 == b.getTotalCodewords()) {
            return bArr;
        }
        throw FormatException.getFormatInstance();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void d() {
        if (this.c != null) {
            c.values()[this.c.b()].a(this.a, this.a.getHeight());
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(boolean z) {
        this.b = null;
        this.c = null;
        this.d = z;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void e() {
        int i = 0;
        while (i < this.a.getWidth()) {
            int i2 = i + 1;
            for (int i3 = i2; i3 < this.a.getHeight(); i3++) {
                if (this.a.get(i, i3) != this.a.get(i3, i)) {
                    this.a.flip(i3, i);
                    this.a.flip(i, i3);
                }
            }
            i = i2;
        }
    }
}
