package com.google.android.exoplayer2.util;

import androidx.annotation.Nullable;
import java.util.Arrays;

/* loaded from: classes2.dex */
public final class TimedValueQueue<V> {
    private long[] a;
    private V[] b;
    private int c;
    private int d;

    public TimedValueQueue() {
        this(10);
    }

    public TimedValueQueue(int i) {
        this.a = new long[i];
        this.b = (V[]) a(i);
    }

    public synchronized void add(long j, V v) {
        a(j);
        b();
        a(j, (long) v);
    }

    public synchronized void clear() {
        this.c = 0;
        this.d = 0;
        Arrays.fill(this.b, (Object) null);
    }

    public synchronized int size() {
        return this.d;
    }

    @Nullable
    public synchronized V pollFirst() {
        return this.d == 0 ? null : a();
    }

    @Nullable
    public synchronized V pollFloor(long j) {
        return a(j, true);
    }

    @Nullable
    public synchronized V poll(long j) {
        return a(j, false);
    }

    @Nullable
    private V a(long j, boolean z) {
        V v = null;
        long j2 = Long.MAX_VALUE;
        while (this.d > 0) {
            long j3 = j - this.a[this.c];
            if (j3 < 0 && (z || (-j3) >= j2)) {
                break;
            }
            v = a();
            j2 = j3;
        }
        return v;
    }

    @Nullable
    private V a() {
        Assertions.checkState(this.d > 0);
        V[] vArr = this.b;
        int i = this.c;
        V v = vArr[i];
        vArr[i] = null;
        this.c = (i + 1) % vArr.length;
        this.d--;
        return v;
    }

    private void a(long j) {
        int i = this.d;
        if (i > 0) {
            if (j <= this.a[((this.c + i) - 1) % this.b.length]) {
                clear();
            }
        }
    }

    private void b() {
        int length = this.b.length;
        if (this.d >= length) {
            int i = length * 2;
            long[] jArr = new long[i];
            V[] vArr = (V[]) a(i);
            int i2 = this.c;
            int i3 = length - i2;
            System.arraycopy(this.a, i2, jArr, 0, i3);
            System.arraycopy(this.b, this.c, vArr, 0, i3);
            int i4 = this.c;
            if (i4 > 0) {
                System.arraycopy(this.a, 0, jArr, i3, i4);
                System.arraycopy(this.b, 0, vArr, i3, this.c);
            }
            this.a = jArr;
            this.b = vArr;
            this.c = 0;
        }
    }

    private void a(long j, V v) {
        int i = this.c;
        int i2 = this.d;
        V[] vArr = this.b;
        int length = (i + i2) % vArr.length;
        this.a[length] = j;
        vArr[length] = v;
        this.d = i2 + 1;
    }

    private static <V> V[] a(int i) {
        return (V[]) new Object[i];
    }
}
