package com.google.zxing.pdf417.decoder;

import java.util.Formatter;

/* compiled from: DetectionResultColumn.java */
/* loaded from: classes2.dex */
class g {
    private final c a;
    private final d[] b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public g(c cVar) {
        this.a = new c(cVar);
        this.b = new d[(cVar.d() - cVar.c()) + 1];
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final d a(int i) {
        d dVar;
        d dVar2;
        d c = c(i);
        if (c != null) {
            return c;
        }
        for (int i2 = 1; i2 < 5; i2++) {
            int b = b(i) - i2;
            if (b >= 0 && (dVar2 = this.b[b]) != null) {
                return dVar2;
            }
            int b2 = b(i) + i2;
            d[] dVarArr = this.b;
            if (b2 < dVarArr.length && (dVar = dVarArr[b2]) != null) {
                return dVar;
            }
        }
        return null;
    }

    final int b(int i) {
        return i - this.a.c();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void a(int i, d dVar) {
        this.b[b(i)] = dVar;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final d c(int i) {
        return this.b[b(i)];
    }

    final c a() {
        return this.a;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final d[] b() {
        return this.b;
    }

    public String toString() {
        Formatter formatter = new Formatter();
        Throwable th = null;
        try {
            d[] dVarArr = this.b;
            int i = 0;
            for (d dVar : dVarArr) {
                if (dVar == null) {
                    i++;
                    formatter.format("%3d:    |   %n", Integer.valueOf(i));
                } else {
                    i++;
                    formatter.format("%3d: %3d|%3d%n", Integer.valueOf(i), Integer.valueOf(dVar.h()), Integer.valueOf(dVar.g()));
                }
            }
            String formatter2 = formatter.toString();
            formatter.close();
            return formatter2;
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
}
