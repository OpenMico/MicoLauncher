package com.google.zxing.pdf417.decoder;

import com.google.zxing.ResultPoint;

/* compiled from: DetectionResultRowIndicatorColumn.java */
/* loaded from: classes2.dex */
final class h extends g {
    private final boolean a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public h(c cVar, boolean z) {
        super(cVar);
        this.a = z;
    }

    private void f() {
        d[] b = b();
        for (d dVar : b) {
            if (dVar != null) {
                dVar.b();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(a aVar) {
        d[] b = b();
        f();
        a(b, aVar);
        c a = a();
        ResultPoint e = this.a ? a.e() : a.f();
        ResultPoint g = this.a ? a.g() : a.h();
        int b2 = b((int) e.getY());
        int b3 = b((int) g.getY());
        int i = -1;
        int i2 = 0;
        int i3 = 1;
        while (b2 < b3) {
            if (b[b2] != null) {
                d dVar = b[b2];
                int h = dVar.h() - i;
                if (h == 0) {
                    i2++;
                } else if (h == 1) {
                    i3 = Math.max(i3, i2);
                    i = dVar.h();
                    i2 = 1;
                } else if (h < 0 || dVar.h() >= aVar.c() || h > b2) {
                    b[b2] = null;
                } else {
                    if (i3 > 2) {
                        h *= i3 - 2;
                    }
                    boolean z = h >= b2;
                    for (int i4 = 1; i4 <= h && !z; i4++) {
                        z = b[b2 - i4] != null;
                    }
                    if (z) {
                        b[b2] = null;
                    } else {
                        i = dVar.h();
                        i2 = 1;
                    }
                }
            }
            b2++;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int[] c() {
        int h;
        a d = d();
        if (d == null) {
            return null;
        }
        b(d);
        int[] iArr = new int[d.c()];
        d[] b = b();
        for (d dVar : b) {
            if (dVar != null && (h = dVar.h()) < iArr.length) {
                iArr[h] = iArr[h] + 1;
            }
        }
        return iArr;
    }

    private void b(a aVar) {
        c a = a();
        ResultPoint e = this.a ? a.e() : a.f();
        ResultPoint g = this.a ? a.g() : a.h();
        int b = b((int) g.getY());
        d[] b2 = b();
        int i = -1;
        int i2 = 0;
        int i3 = 1;
        for (int b3 = b((int) e.getY()); b3 < b; b3++) {
            if (b2[b3] != null) {
                d dVar = b2[b3];
                dVar.b();
                int h = dVar.h() - i;
                if (h == 0) {
                    i2++;
                } else if (h == 1) {
                    i3 = Math.max(i3, i2);
                    i = dVar.h();
                    i2 = 1;
                } else if (dVar.h() >= aVar.c()) {
                    b2[b3] = null;
                } else {
                    i = dVar.h();
                    i2 = 1;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public a d() {
        d[] b = b();
        b bVar = new b();
        b bVar2 = new b();
        b bVar3 = new b();
        b bVar4 = new b();
        for (d dVar : b) {
            if (dVar != null) {
                dVar.b();
                int g = dVar.g() % 30;
                int h = dVar.h();
                if (!this.a) {
                    h += 2;
                }
                switch (h % 3) {
                    case 0:
                        bVar2.a((g * 3) + 1);
                        continue;
                    case 1:
                        bVar4.a(g / 3);
                        bVar3.a(g % 3);
                        continue;
                    case 2:
                        bVar.a(g + 1);
                        continue;
                }
            }
        }
        if (bVar.a().length == 0 || bVar2.a().length == 0 || bVar3.a().length == 0 || bVar4.a().length == 0 || bVar.a()[0] <= 0 || bVar2.a()[0] + bVar3.a()[0] < 3 || bVar2.a()[0] + bVar3.a()[0] > 90) {
            return null;
        }
        a aVar = new a(bVar.a()[0], bVar2.a()[0], bVar3.a()[0], bVar4.a()[0]);
        a(b, aVar);
        return aVar;
    }

    private void a(d[] dVarArr, a aVar) {
        for (int i = 0; i < dVarArr.length; i++) {
            d dVar = dVarArr[i];
            if (dVarArr[i] != null) {
                int g = dVar.g() % 30;
                int h = dVar.h();
                if (h > aVar.c()) {
                    dVarArr[i] = null;
                } else {
                    if (!this.a) {
                        h += 2;
                    }
                    switch (h % 3) {
                        case 0:
                            if ((g * 3) + 1 == aVar.d()) {
                                break;
                            } else {
                                dVarArr[i] = null;
                                continue;
                            }
                        case 1:
                            if (g / 3 != aVar.b() || g % 3 != aVar.e()) {
                                dVarArr[i] = null;
                                break;
                            } else {
                                continue;
                            }
                        case 2:
                            if (g + 1 != aVar.a()) {
                                dVarArr[i] = null;
                                break;
                            } else {
                                continue;
                            }
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean e() {
        return this.a;
    }

    @Override // com.google.zxing.pdf417.decoder.g
    public String toString() {
        return "IsLeft: " + this.a + '\n' + super.toString();
    }
}
