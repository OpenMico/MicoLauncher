package io.netty.handler.codec.http2.internal.hpack;

/* compiled from: DynamicTable.java */
/* loaded from: classes4.dex */
final class a {
    b[] a;
    int b;
    int c;
    private int d;
    private int e = -1;

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(int i) {
        b(i);
    }

    public int a() {
        int i = this.b;
        int i2 = this.c;
        return i < i2 ? (this.a.length - i2) + i : i - i2;
    }

    public int b() {
        return this.e;
    }

    public b a(int i) {
        if (i <= 0 || i > a()) {
            throw new IndexOutOfBoundsException();
        }
        int i2 = this.b - i;
        if (i2 >= 0) {
            return this.a[i2];
        }
        b[] bVarArr = this.a;
        return bVarArr[i2 + bVarArr.length];
    }

    public void a(b bVar) {
        int i;
        int a = bVar.a();
        if (a > this.e) {
            d();
            return;
        }
        while (true) {
            i = this.d;
            if (i + a <= this.e) {
                break;
            }
            c();
        }
        b[] bVarArr = this.a;
        int i2 = this.b;
        this.b = i2 + 1;
        bVarArr[i2] = bVar;
        this.d = i + bVar.a();
        if (this.b == this.a.length) {
            this.b = 0;
        }
    }

    public b c() {
        b bVar = this.a[this.c];
        if (bVar == null) {
            return null;
        }
        this.d -= bVar.a();
        b[] bVarArr = this.a;
        int i = this.c;
        this.c = i + 1;
        bVarArr[i] = null;
        if (this.c == bVarArr.length) {
            this.c = 0;
        }
        return bVar;
    }

    public void d() {
        while (true) {
            int i = this.c;
            if (i != this.b) {
                b[] bVarArr = this.a;
                this.c = i + 1;
                bVarArr[i] = null;
                if (this.c == bVarArr.length) {
                    this.c = 0;
                }
            } else {
                this.b = 0;
                this.c = 0;
                this.d = 0;
                return;
            }
        }
    }

    public void b(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("Illegal Capacity: " + i);
        } else if (this.e != i) {
            this.e = i;
            if (i == 0) {
                d();
            } else {
                while (this.d > i) {
                    c();
                }
            }
            int i2 = i / 32;
            if (i % 32 != 0) {
                i2++;
            }
            b[] bVarArr = this.a;
            if (bVarArr == null || bVarArr.length != i2) {
                b[] bVarArr2 = new b[i2];
                int a = a();
                int i3 = this.c;
                for (int i4 = 0; i4 < a; i4++) {
                    b[] bVarArr3 = this.a;
                    int i5 = i3 + 1;
                    bVarArr2[i4] = bVarArr3[i3];
                    i3 = i5 == bVarArr3.length ? 0 : i5;
                }
                this.c = 0;
                this.b = this.c + a;
                this.a = bVarArr2;
            }
        }
    }
}
