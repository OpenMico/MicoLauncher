package com.google.zxing.pdf417.decoder;

import com.google.zxing.pdf417.PDF417Common;
import java.util.Formatter;

/* compiled from: DetectionResult.java */
/* loaded from: classes2.dex */
final class f {
    private final a a;
    private final g[] b;
    private c c;
    private final int d;

    /* JADX INFO: Access modifiers changed from: package-private */
    public f(a aVar, c cVar) {
        this.a = aVar;
        this.d = aVar.a();
        this.c = cVar;
        this.b = new g[this.d + 2];
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public g[] a() {
        a(this.b[0]);
        a(this.b[this.d + 1]);
        int i = PDF417Common.MAX_CODEWORDS_IN_BARCODE;
        while (true) {
            int f = f();
            if (f <= 0 || f >= i) {
                break;
            }
            i = f;
        }
        return this.b;
    }

    private void a(g gVar) {
        if (gVar != null) {
            ((h) gVar).a(this.a);
        }
    }

    private int f() {
        int g = g();
        if (g == 0) {
            return 0;
        }
        for (int i = 1; i < this.d + 1; i++) {
            d[] b = this.b[i].b();
            for (int i2 = 0; i2 < b.length; i2++) {
                if (b[i2] != null && !b[i2].a()) {
                    a(i, i2, b);
                }
            }
        }
        return g;
    }

    private int g() {
        h();
        return j() + i();
    }

    private void h() {
        g[] gVarArr = this.b;
        if (!(gVarArr[0] == null || gVarArr[this.d + 1] == null)) {
            d[] b = gVarArr[0].b();
            d[] b2 = this.b[this.d + 1].b();
            for (int i = 0; i < b.length; i++) {
                if (!(b[i] == null || b2[i] == null || b[i].h() != b2[i].h())) {
                    for (int i2 = 1; i2 <= this.d; i2++) {
                        d dVar = this.b[i2].b()[i];
                        if (dVar != null) {
                            dVar.b(b[i].h());
                            if (!dVar.a()) {
                                this.b[i2].b()[i] = null;
                            }
                        }
                    }
                }
            }
        }
    }

    private int i() {
        g[] gVarArr = this.b;
        int i = this.d;
        if (gVarArr[i + 1] == null) {
            return 0;
        }
        d[] b = gVarArr[i + 1].b();
        int i2 = 0;
        for (int i3 = 0; i3 < b.length; i3++) {
            if (b[i3] != null) {
                int h = b[i3].h();
                int i4 = i2;
                int i5 = 0;
                for (int i6 = this.d + 1; i6 > 0 && i5 < 2; i6--) {
                    d dVar = this.b[i6].b()[i3];
                    if (dVar != null) {
                        i5 = a(h, i5, dVar);
                        if (!dVar.a()) {
                            i4++;
                        }
                    }
                }
                i2 = i4;
            }
        }
        return i2;
    }

    private int j() {
        g[] gVarArr = this.b;
        if (gVarArr[0] == null) {
            return 0;
        }
        d[] b = gVarArr[0].b();
        int i = 0;
        for (int i2 = 0; i2 < b.length; i2++) {
            if (b[i2] != null) {
                int h = b[i2].h();
                int i3 = 0;
                int i4 = i;
                for (int i5 = 1; i5 < this.d + 1 && i3 < 2; i5++) {
                    d dVar = this.b[i5].b()[i2];
                    if (dVar != null) {
                        i3 = a(h, i3, dVar);
                        if (!dVar.a()) {
                            i4++;
                        }
                    }
                }
                i = i4;
            }
        }
        return i;
    }

    private static int a(int i, int i2, d dVar) {
        if (dVar == null || dVar.a()) {
            return i2;
        }
        if (!dVar.a(i)) {
            return i2 + 1;
        }
        dVar.b(i);
        return 0;
    }

    private void a(int i, int i2, d[] dVarArr) {
        d dVar = dVarArr[i2];
        d[] b = this.b[i - 1].b();
        g[] gVarArr = this.b;
        int i3 = i + 1;
        d[] b2 = gVarArr[i3] != null ? gVarArr[i3].b() : b;
        d[] dVarArr2 = new d[14];
        dVarArr2[2] = b[i2];
        dVarArr2[3] = b2[i2];
        if (i2 > 0) {
            int i4 = i2 - 1;
            dVarArr2[0] = dVarArr[i4];
            dVarArr2[4] = b[i4];
            dVarArr2[5] = b2[i4];
        }
        if (i2 > 1) {
            int i5 = i2 - 2;
            dVarArr2[8] = dVarArr[i5];
            dVarArr2[10] = b[i5];
            dVarArr2[11] = b2[i5];
        }
        if (i2 < dVarArr.length - 1) {
            int i6 = i2 + 1;
            dVarArr2[1] = dVarArr[i6];
            dVarArr2[6] = b[i6];
            dVarArr2[7] = b2[i6];
        }
        if (i2 < dVarArr.length - 2) {
            int i7 = i2 + 2;
            dVarArr2[9] = dVarArr[i7];
            dVarArr2[12] = b[i7];
            dVarArr2[13] = b2[i7];
        }
        for (int i8 = 0; i8 < 14 && !a(dVar, dVarArr2[i8]); i8++) {
        }
    }

    private static boolean a(d dVar, d dVar2) {
        if (dVar2 == null || !dVar2.a() || dVar2.f() != dVar.f()) {
            return false;
        }
        dVar.b(dVar2.h());
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int b() {
        return this.d;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int c() {
        return this.a.c();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int d() {
        return this.a.b();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(c cVar) {
        this.c = cVar;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public c e() {
        return this.c;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(int i, g gVar) {
        this.b[i] = gVar;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public g a(int i) {
        return this.b[i];
    }

    public String toString() {
        g[] gVarArr = this.b;
        g gVar = gVarArr[0];
        if (gVar == null) {
            gVar = gVarArr[this.d + 1];
        }
        Formatter formatter = new Formatter();
        Throwable th = null;
        for (int i = 0; i < gVar.b().length; i++) {
            try {
                formatter.format("CW %3d:", Integer.valueOf(i));
                for (int i2 = 0; i2 < this.d + 2; i2++) {
                    if (this.b[i2] == null) {
                        formatter.format("    |   ", new Object[0]);
                    } else {
                        d dVar = this.b[i2].b()[i];
                        if (dVar == null) {
                            formatter.format("    |   ", new Object[0]);
                        } else {
                            formatter.format(" %3d|%3d", Integer.valueOf(dVar.h()), Integer.valueOf(dVar.g()));
                        }
                    }
                }
                formatter.format("%n", new Object[0]);
            } catch (Throwable th2) {
                if (0 != 0) {
                    try {
                        formatter.close();
                    } catch (Throwable th3) {
                        th.addSuppressed(th3);
                    }
                } else {
                    formatter.close();
                }
                throw th2;
            }
        }
        String formatter2 = formatter.toString();
        formatter.close();
        return formatter2;
    }
}
