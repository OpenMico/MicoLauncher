package io.netty.buffer;

import com.fasterxml.jackson.core.JsonPointer;
import com.google.common.primitives.Longs;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: PoolSubpage.java */
/* loaded from: classes4.dex */
public final class k<T> implements PoolSubpageMetric {
    static final /* synthetic */ boolean f = !k.class.desiredAssertionStatus();
    final i<T> a;
    k<T> b;
    k<T> c;
    boolean d;
    int e;
    private final int g;
    private final int h;
    private final int i;
    private final long[] j;
    private int k;
    private int l;
    private int m;
    private int n;

    /* JADX INFO: Access modifiers changed from: package-private */
    public k(int i) {
        this.a = null;
        this.g = -1;
        this.h = -1;
        this.e = -1;
        this.i = i;
        this.j = null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public k(k<T> kVar, i<T> iVar, int i, int i2, int i3, int i4) {
        this.a = iVar;
        this.g = i;
        this.h = i2;
        this.i = i3;
        this.j = new long[i3 >>> 10];
        a(kVar, i4);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(k<T> kVar, int i) {
        this.d = true;
        this.e = i;
        if (i != 0) {
            int i2 = this.i / i;
            this.n = i2;
            this.k = i2;
            this.m = 0;
            int i3 = this.k;
            this.l = i3 >>> 6;
            if ((i3 & 63) != 0) {
                this.l++;
            }
            for (int i4 = 0; i4 < this.l; i4++) {
                this.j[i4] = 0;
            }
        }
        a(kVar);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public long a() {
        if (this.e == 0) {
            return b(0);
        }
        if (this.n == 0 || !this.d) {
            return -1L;
        }
        int c = c();
        int i = c >>> 6;
        int i2 = c & 63;
        if (f || ((this.j[i] >>> i2) & 1) == 0) {
            long[] jArr = this.j;
            jArr[i] = (1 << i2) | jArr[i];
            int i3 = this.n - 1;
            this.n = i3;
            if (i3 == 0) {
                b();
            }
            return b(c);
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean b(k<T> kVar, int i) {
        if (this.e == 0) {
            return true;
        }
        int i2 = i >>> 6;
        int i3 = i & 63;
        if (f || ((this.j[i2] >>> i3) & 1) != 0) {
            long[] jArr = this.j;
            jArr[i2] = (1 << i3) ^ jArr[i2];
            a(i);
            int i4 = this.n;
            this.n = i4 + 1;
            if (i4 == 0) {
                a(kVar);
                return true;
            } else if (this.n != this.k || this.b == this.c) {
                return true;
            } else {
                this.d = false;
                b();
                return false;
            }
        } else {
            throw new AssertionError();
        }
    }

    private void a(k<T> kVar) {
        if (f || (this.b == null && this.c == null)) {
            this.b = kVar;
            this.c = kVar.c;
            this.c.b = this;
            kVar.c = this;
            return;
        }
        throw new AssertionError();
    }

    private void b() {
        if (f || !(this.b == null || this.c == null)) {
            k<T> kVar = this.b;
            kVar.c = this.c;
            this.c.b = kVar;
            this.c = null;
            this.b = null;
            return;
        }
        throw new AssertionError();
    }

    private void a(int i) {
        this.m = i;
    }

    private int c() {
        int i = this.m;
        if (i < 0) {
            return d();
        }
        this.m = -1;
        return i;
    }

    private int d() {
        long[] jArr = this.j;
        int i = this.l;
        for (int i2 = 0; i2 < i; i2++) {
            long j = jArr[i2];
            if ((~j) != 0) {
                return a(i2, j);
            }
        }
        return -1;
    }

    private int a(int i, long j) {
        int i2 = this.k;
        int i3 = i << 6;
        for (int i4 = 0; i4 < 64; i4++) {
            if ((1 & j) == 0) {
                int i5 = i3 | i4;
                if (i5 < i2) {
                    return i5;
                }
                return -1;
            }
            j >>>= 1;
        }
        return -1;
    }

    private long b(int i) {
        return (i << 32) | Longs.MAX_POWER_OF_TWO | this.g;
    }

    public String toString() {
        if (!this.d) {
            return "(" + this.g + ": not in use)";
        }
        return String.valueOf('(') + this.g + ": " + (this.k - this.n) + JsonPointer.SEPARATOR + this.k + ", offset: " + this.h + ", length: " + this.i + ", elemSize: " + this.e + ')';
    }

    @Override // io.netty.buffer.PoolSubpageMetric
    public int maxNumElements() {
        return this.k;
    }

    @Override // io.netty.buffer.PoolSubpageMetric
    public int numAvailable() {
        return this.n;
    }

    @Override // io.netty.buffer.PoolSubpageMetric
    public int elementSize() {
        return this.e;
    }

    @Override // io.netty.buffer.PoolSubpageMetric
    public int pageSize() {
        return this.i;
    }
}
