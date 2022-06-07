package io.netty.buffer;

import com.fasterxml.jackson.core.JsonPointer;
import kotlinx.coroutines.internal.LockFreeTaskQueueCore;

/* compiled from: PoolChunk.java */
/* loaded from: classes4.dex */
public final class i<T> implements PoolChunkMetric {
    static final /* synthetic */ boolean g = !i.class.desiredAssertionStatus();
    final h<T> a;
    final T b;
    final boolean c;
    j<T> d;
    i<T> e;
    i<T> f;
    private final byte[] h;
    private final byte[] i;
    private final k<T>[] j;
    private final int k;
    private final int l;
    private final int m;
    private final int n;
    private final int o;
    private final int p;
    private final int q;
    private final byte r;
    private int s;

    private static int b(long j) {
        return (int) j;
    }

    private static int c(long j) {
        return (int) (j >>> 32);
    }

    public i(h<T> hVar, T t, int i, int i2, int i3, int i4) {
        this.c = false;
        this.a = hVar;
        this.b = t;
        this.l = i;
        this.m = i3;
        this.n = i2;
        this.o = i4;
        this.r = (byte) (i2 + 1);
        this.p = j(i4);
        this.k = ~(i - 1);
        this.s = i4;
        if (g || i2 < 30) {
            this.q = 1 << i2;
            this.h = new byte[this.q << 1];
            this.i = new byte[this.h.length];
            int i5 = 1;
            int i6 = 0;
            while (i6 <= i2) {
                int i7 = 1 << i6;
                for (int i8 = 0; i8 < i7; i8++) {
                    byte b = (byte) i6;
                    this.h[i5] = b;
                    this.i[i5] = b;
                    i5++;
                }
                i6++;
                i5 = i5;
            }
            this.j = b(this.q);
            return;
        }
        throw new AssertionError("maxOrder should be < 30, but is: " + i2);
    }

    public i(h<T> hVar, T t, int i) {
        this.c = true;
        this.a = hVar;
        this.b = t;
        this.h = null;
        this.i = null;
        this.j = null;
        this.k = 0;
        this.l = 0;
        this.m = 0;
        this.n = 0;
        this.r = (byte) (this.n + 1);
        this.o = i;
        this.p = j(this.o);
        this.q = 0;
    }

    private k<T>[] b(int i) {
        return new k[i];
    }

    @Override // io.netty.buffer.PoolChunkMetric
    public int usage() {
        int i = this.s;
        if (i == 0) {
            return 100;
        }
        int i2 = (int) ((i * 100) / this.o);
        if (i2 == 0) {
            return 99;
        }
        return 100 - i2;
    }

    public long a(int i) {
        if ((this.k & i) != 0) {
            return f(i);
        }
        return g(i);
    }

    private void c(int i) {
        while (i > 1) {
            int i2 = i >>> 1;
            byte h = h(i);
            byte h2 = h(i ^ 1);
            if (h < h2) {
                h2 = h;
            }
            a(i2, h2);
            i = i2;
        }
    }

    private void d(int i) {
        int i2 = i(i) + 1;
        while (i > 1) {
            int i3 = i >>> 1;
            byte h = h(i);
            byte h2 = h(i ^ 1);
            i2--;
            if (h == i2 && h2 == i2) {
                a(i3, (byte) (i2 - 1));
            } else {
                if (h < h2) {
                    h2 = h;
                }
                a(i3, h2);
            }
            i = i3;
        }
    }

    private int e(int i) {
        int i2;
        int i3 = 1 << i;
        int i4 = -i3;
        byte h = h(1);
        if (h > i) {
            return -1;
        }
        int i5 = 1;
        while (true) {
            if (h >= i) {
                i2 = i5 & i4;
                if (i2 != 0) {
                    break;
                }
            }
            i5 <<= 1;
            h = h(i5);
            if (h > i) {
                i5 ^= 1;
                h = h(i5);
            }
        }
        byte h2 = h(i5);
        if (g || (h2 == i && i2 == i3)) {
            a(i5, this.r);
            c(i5);
            return i5;
        }
        throw new AssertionError(String.format("val = %d, id & initial = %d, d = %d", Byte.valueOf(h2), Integer.valueOf(i2), Integer.valueOf(i)));
    }

    private long f(int i) {
        int e = e(this.n - (j(i) - this.m));
        if (e < 0) {
            return e;
        }
        this.s -= k(e);
        return e;
    }

    private long g(int i) {
        k<T> e = this.a.e(i);
        synchronized (e) {
            int e2 = e(this.n);
            if (e2 < 0) {
                return e2;
            }
            k<T>[] kVarArr = this.j;
            int i2 = this.l;
            this.s -= i2;
            int m = m(e2);
            k<T> kVar = kVarArr[m];
            if (kVar == null) {
                k<T> kVar2 = new k<>(e, this, e2, l(e2), i2, i);
                kVarArr[m] = kVar2;
                kVar = kVar2;
            } else {
                kVar.a(e, i);
            }
            return kVar.a();
        }
    }

    public void a(long j) {
        int b = b(j);
        int c = c(j);
        if (c != 0) {
            k<T> kVar = this.j[m(b)];
            if (g || (kVar != null && kVar.d)) {
                k<T> e = this.a.e(kVar.e);
                synchronized (e) {
                    if (kVar.b(e, c & LockFreeTaskQueueCore.MAX_CAPACITY_MASK)) {
                        return;
                    }
                }
            } else {
                throw new AssertionError();
            }
        }
        this.s += k(b);
        a(b, i(b));
        d(b);
    }

    public void a(m<T> mVar, long j, int i) {
        int b = b(j);
        int c = c(j);
        if (c == 0) {
            byte h = h(b);
            if (g || h == this.r) {
                mVar.a(this, j, l(b), i, k(b), this.a.b.a());
                return;
            }
            throw new AssertionError(String.valueOf((int) h));
        }
        a(mVar, j, c, i);
    }

    public void b(m<T> mVar, long j, int i) {
        a(mVar, j, c(j), i);
    }

    private void a(m<T> mVar, long j, int i, int i2) {
        if (g || i != 0) {
            int b = b(j);
            k<T> kVar = this.j[m(b)];
            if (!g && !kVar.d) {
                throw new AssertionError();
            } else if (g || i2 <= kVar.e) {
                mVar.a(this, j, l(b) + ((i & LockFreeTaskQueueCore.MAX_CAPACITY_MASK) * kVar.e), i2, kVar.e, this.a.b.a());
            } else {
                throw new AssertionError();
            }
        } else {
            throw new AssertionError();
        }
    }

    private byte h(int i) {
        return this.h[i];
    }

    private void a(int i, byte b) {
        this.h[i] = b;
    }

    private byte i(int i) {
        return this.i[i];
    }

    private static int j(int i) {
        return 31 - Integer.numberOfLeadingZeros(i);
    }

    private int k(int i) {
        return 1 << (this.p - i(i));
    }

    private int l(int i) {
        return ((1 << i(i)) ^ i) * k(i);
    }

    private int m(int i) {
        return i ^ this.q;
    }

    @Override // io.netty.buffer.PoolChunkMetric
    public int chunkSize() {
        return this.o;
    }

    @Override // io.netty.buffer.PoolChunkMetric
    public int freeBytes() {
        return this.s;
    }

    public String toString() {
        return "Chunk(" + Integer.toHexString(System.identityHashCode(this)) + ": " + usage() + "%, " + (this.o - this.s) + JsonPointer.SEPARATOR + this.o + ')';
    }
}
