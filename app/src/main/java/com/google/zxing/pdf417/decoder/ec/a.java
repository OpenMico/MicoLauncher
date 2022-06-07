package com.google.zxing.pdf417.decoder.ec;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ModulusPoly.java */
/* loaded from: classes2.dex */
public final class a {
    private final ModulusGF a;
    private final int[] b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(ModulusGF modulusGF, int[] iArr) {
        if (iArr.length != 0) {
            this.a = modulusGF;
            int length = iArr.length;
            if (length <= 1 || iArr[0] != 0) {
                this.b = iArr;
                return;
            }
            int i = 1;
            while (i < length && iArr[i] == 0) {
                i++;
            }
            if (i == length) {
                this.b = new int[]{0};
                return;
            }
            this.b = new int[length - i];
            int[] iArr2 = this.b;
            System.arraycopy(iArr, i, iArr2, 0, iArr2.length);
            return;
        }
        throw new IllegalArgumentException();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int a() {
        return this.b.length - 1;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean b() {
        return this.b[0] == 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int a(int i) {
        int[] iArr = this.b;
        return iArr[(iArr.length - 1) - i];
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int b(int i) {
        if (i == 0) {
            return a(0);
        }
        if (i == 1) {
            int i2 = 0;
            for (int i3 : this.b) {
                i2 = this.a.b(i2, i3);
            }
            return i2;
        }
        int[] iArr = this.b;
        int i4 = iArr[0];
        int length = iArr.length;
        for (int i5 = 1; i5 < length; i5++) {
            ModulusGF modulusGF = this.a;
            i4 = modulusGF.b(modulusGF.d(i, i4), this.b[i5]);
        }
        return i4;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public a a(a aVar) {
        if (!this.a.equals(aVar.a)) {
            throw new IllegalArgumentException("ModulusPolys do not have same ModulusGF field");
        } else if (b()) {
            return aVar;
        } else {
            if (aVar.b()) {
                return this;
            }
            int[] iArr = this.b;
            int[] iArr2 = aVar.b;
            if (iArr.length > iArr2.length) {
                iArr = iArr2;
                iArr2 = iArr;
            }
            int[] iArr3 = new int[iArr2.length];
            int length = iArr2.length - iArr.length;
            System.arraycopy(iArr2, 0, iArr3, 0, length);
            for (int i = length; i < iArr2.length; i++) {
                iArr3[i] = this.a.b(iArr[i - length], iArr2[i]);
            }
            return new a(this.a, iArr3);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public a b(a aVar) {
        if (this.a.equals(aVar.a)) {
            return aVar.b() ? this : a(aVar.c());
        }
        throw new IllegalArgumentException("ModulusPolys do not have same ModulusGF field");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public a c(a aVar) {
        if (!this.a.equals(aVar.a)) {
            throw new IllegalArgumentException("ModulusPolys do not have same ModulusGF field");
        } else if (b() || aVar.b()) {
            return this.a.a();
        } else {
            int[] iArr = this.b;
            int length = iArr.length;
            int[] iArr2 = aVar.b;
            int length2 = iArr2.length;
            int[] iArr3 = new int[(length + length2) - 1];
            for (int i = 0; i < length; i++) {
                int i2 = iArr[i];
                for (int i3 = 0; i3 < length2; i3++) {
                    int i4 = i + i3;
                    ModulusGF modulusGF = this.a;
                    iArr3[i4] = modulusGF.b(iArr3[i4], modulusGF.d(i2, iArr2[i3]));
                }
            }
            return new a(this.a, iArr3);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public a c() {
        int length = this.b.length;
        int[] iArr = new int[length];
        for (int i = 0; i < length; i++) {
            iArr[i] = this.a.c(0, this.b[i]);
        }
        return new a(this.a, iArr);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public a c(int i) {
        if (i == 0) {
            return this.a.a();
        }
        if (i == 1) {
            return this;
        }
        int length = this.b.length;
        int[] iArr = new int[length];
        for (int i2 = 0; i2 < length; i2++) {
            iArr[i2] = this.a.d(this.b[i2], i);
        }
        return new a(this.a, iArr);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public a a(int i, int i2) {
        if (i < 0) {
            throw new IllegalArgumentException();
        } else if (i2 == 0) {
            return this.a.a();
        } else {
            int length = this.b.length;
            int[] iArr = new int[i + length];
            for (int i3 = 0; i3 < length; i3++) {
                iArr[i3] = this.a.d(this.b[i3], i2);
            }
            return new a(this.a, iArr);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(a() * 8);
        for (int a = a(); a >= 0; a--) {
            int a2 = a(a);
            if (a2 != 0) {
                if (a2 < 0) {
                    sb.append(" - ");
                    a2 = -a2;
                } else if (sb.length() > 0) {
                    sb.append(" + ");
                }
                if (a == 0 || a2 != 1) {
                    sb.append(a2);
                }
                if (a != 0) {
                    if (a == 1) {
                        sb.append('x');
                    } else {
                        sb.append("x^");
                        sb.append(a);
                    }
                }
            }
        }
        return sb.toString();
    }
}
