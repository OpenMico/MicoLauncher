package com.google.zxing.pdf417.decoder;

import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: BoundingBox.java */
/* loaded from: classes2.dex */
public final class c {
    private BitMatrix a;
    private ResultPoint b;
    private ResultPoint c;
    private ResultPoint d;
    private ResultPoint e;
    private int f;
    private int g;
    private int h;
    private int i;

    public c(BitMatrix bitMatrix, ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, ResultPoint resultPoint4) throws NotFoundException {
        if (!(resultPoint == null && resultPoint3 == null) && (!(resultPoint2 == null && resultPoint4 == null) && ((resultPoint == null || resultPoint2 != null) && (resultPoint3 == null || resultPoint4 != null)))) {
            a(bitMatrix, resultPoint, resultPoint2, resultPoint3, resultPoint4);
            return;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    public c(c cVar) {
        a(cVar.a, cVar.b, cVar.c, cVar.d, cVar.e);
    }

    private void a(BitMatrix bitMatrix, ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, ResultPoint resultPoint4) {
        this.a = bitMatrix;
        this.b = resultPoint;
        this.c = resultPoint2;
        this.d = resultPoint3;
        this.e = resultPoint4;
        i();
    }

    public static c a(c cVar, c cVar2) throws NotFoundException {
        return cVar == null ? cVar2 : cVar2 == null ? cVar : new c(cVar.a, cVar.b, cVar.c, cVar2.d, cVar2.e);
    }

    public c a(int i, int i2, boolean z) throws NotFoundException {
        ResultPoint resultPoint;
        ResultPoint resultPoint2;
        ResultPoint resultPoint3;
        ResultPoint resultPoint4;
        ResultPoint resultPoint5 = this.b;
        ResultPoint resultPoint6 = this.c;
        ResultPoint resultPoint7 = this.d;
        ResultPoint resultPoint8 = this.e;
        if (i > 0) {
            ResultPoint resultPoint9 = z ? resultPoint5 : resultPoint7;
            int y = ((int) resultPoint9.getY()) - i;
            if (y < 0) {
                y = 0;
            }
            ResultPoint resultPoint10 = new ResultPoint(resultPoint9.getX(), y);
            if (z) {
                resultPoint2 = resultPoint10;
                resultPoint = resultPoint7;
            } else {
                resultPoint = resultPoint10;
                resultPoint2 = resultPoint5;
            }
        } else {
            resultPoint2 = resultPoint5;
            resultPoint = resultPoint7;
        }
        if (i2 > 0) {
            ResultPoint resultPoint11 = z ? this.c : this.e;
            int y2 = ((int) resultPoint11.getY()) + i2;
            if (y2 >= this.a.getHeight()) {
                y2 = this.a.getHeight() - 1;
            }
            ResultPoint resultPoint12 = new ResultPoint(resultPoint11.getX(), y2);
            if (z) {
                resultPoint4 = resultPoint12;
                resultPoint3 = resultPoint8;
            } else {
                resultPoint3 = resultPoint12;
                resultPoint4 = resultPoint6;
            }
        } else {
            resultPoint4 = resultPoint6;
            resultPoint3 = resultPoint8;
        }
        i();
        return new c(this.a, resultPoint2, resultPoint4, resultPoint, resultPoint3);
    }

    private void i() {
        if (this.b == null) {
            this.b = new ResultPoint(0.0f, this.d.getY());
            this.c = new ResultPoint(0.0f, this.e.getY());
        } else if (this.d == null) {
            this.d = new ResultPoint(this.a.getWidth() - 1, this.b.getY());
            this.e = new ResultPoint(this.a.getWidth() - 1, this.c.getY());
        }
        this.f = (int) Math.min(this.b.getX(), this.c.getX());
        this.g = (int) Math.max(this.d.getX(), this.e.getX());
        this.h = (int) Math.min(this.b.getY(), this.d.getY());
        this.i = (int) Math.max(this.c.getY(), this.e.getY());
    }

    public int a() {
        return this.f;
    }

    public int b() {
        return this.g;
    }

    public int c() {
        return this.h;
    }

    public int d() {
        return this.i;
    }

    public ResultPoint e() {
        return this.b;
    }

    public ResultPoint f() {
        return this.d;
    }

    public ResultPoint g() {
        return this.c;
    }

    public ResultPoint h() {
        return this.e;
    }
}
