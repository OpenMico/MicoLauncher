package io.reactivex.rxjava3.internal.queue;

import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.internal.fuseable.SimplePlainQueue;
import io.reactivex.rxjava3.internal.util.Pow2;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReferenceArray;

/* loaded from: classes5.dex */
public final class SpscLinkedArrayQueue<T> implements SimplePlainQueue<T> {
    static final int a = Integer.getInteger("jctools.spsc.max.lookahead.step", 4096).intValue();
    private static final Object j = new Object();
    int c;
    long d;
    final int e;
    AtomicReferenceArray<Object> f;
    final int g;
    AtomicReferenceArray<Object> h;
    final AtomicLong b = new AtomicLong();
    final AtomicLong i = new AtomicLong();

    private static int b(int i) {
        return i;
    }

    public SpscLinkedArrayQueue(int i) {
        int roundToPowerOfTwo = Pow2.roundToPowerOfTwo(Math.max(8, i));
        int i2 = roundToPowerOfTwo - 1;
        AtomicReferenceArray<Object> atomicReferenceArray = new AtomicReferenceArray<>(roundToPowerOfTwo + 1);
        this.f = atomicReferenceArray;
        this.e = i2;
        a(roundToPowerOfTwo);
        this.h = atomicReferenceArray;
        this.g = i2;
        this.d = i2 - 1;
        a(0L);
    }

    @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
    public boolean offer(T t) {
        if (t != null) {
            AtomicReferenceArray<Object> atomicReferenceArray = this.f;
            long c = c();
            int i = this.e;
            int a2 = a(c, i);
            if (c < this.d) {
                return a(atomicReferenceArray, t, c, a2);
            }
            long j2 = this.c + c;
            if (b(atomicReferenceArray, a(j2, i)) == null) {
                this.d = j2 - 1;
                return a(atomicReferenceArray, t, c, a2);
            } else if (b(atomicReferenceArray, a(1 + c, i)) == null) {
                return a(atomicReferenceArray, t, c, a2);
            } else {
                a(atomicReferenceArray, c, a2, t, i);
                return true;
            }
        } else {
            throw new NullPointerException("Null is not a valid element");
        }
    }

    private boolean a(AtomicReferenceArray<Object> atomicReferenceArray, T t, long j2, int i) {
        a(atomicReferenceArray, i, t);
        a(j2 + 1);
        return true;
    }

    private void a(AtomicReferenceArray<Object> atomicReferenceArray, long j2, int i, T t, long j3) {
        AtomicReferenceArray<Object> atomicReferenceArray2 = new AtomicReferenceArray<>(atomicReferenceArray.length());
        this.f = atomicReferenceArray2;
        this.d = (j3 + j2) - 1;
        a(atomicReferenceArray2, i, t);
        a(atomicReferenceArray, atomicReferenceArray2);
        a(atomicReferenceArray, i, j);
        a(j2 + 1);
    }

    private void a(AtomicReferenceArray<Object> atomicReferenceArray, AtomicReferenceArray<Object> atomicReferenceArray2) {
        a(atomicReferenceArray, b(atomicReferenceArray.length() - 1), atomicReferenceArray2);
    }

    private AtomicReferenceArray<Object> a(AtomicReferenceArray<Object> atomicReferenceArray, int i) {
        int b = b(i);
        AtomicReferenceArray<Object> atomicReferenceArray2 = (AtomicReferenceArray) b(atomicReferenceArray, b);
        a(atomicReferenceArray, b, (Object) null);
        return atomicReferenceArray2;
    }

    @Override // io.reactivex.rxjava3.internal.fuseable.SimplePlainQueue, io.reactivex.rxjava3.internal.fuseable.SimpleQueue
    @Nullable
    public T poll() {
        AtomicReferenceArray<Object> atomicReferenceArray = this.h;
        long d = d();
        int i = this.g;
        int a2 = a(d, i);
        T t = (T) b(atomicReferenceArray, a2);
        boolean z = t == j;
        if (t != null && !z) {
            a(atomicReferenceArray, a2, (Object) null);
            b(d + 1);
            return t;
        } else if (z) {
            return a(a(atomicReferenceArray, i + 1), d, i);
        } else {
            return null;
        }
    }

    private T a(AtomicReferenceArray<Object> atomicReferenceArray, long j2, int i) {
        this.h = atomicReferenceArray;
        int a2 = a(j2, i);
        T t = (T) b(atomicReferenceArray, a2);
        if (t != null) {
            a(atomicReferenceArray, a2, (Object) null);
            b(j2 + 1);
        }
        return t;
    }

    public T peek() {
        AtomicReferenceArray<Object> atomicReferenceArray = this.h;
        long d = d();
        int i = this.g;
        T t = (T) b(atomicReferenceArray, a(d, i));
        return t == j ? b(a(atomicReferenceArray, i + 1), d, i) : t;
    }

    private T b(AtomicReferenceArray<Object> atomicReferenceArray, long j2, int i) {
        this.h = atomicReferenceArray;
        return (T) b(atomicReferenceArray, a(j2, i));
    }

    @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
    public void clear() {
        while (true) {
            if (poll() == null && isEmpty()) {
                return;
            }
        }
    }

    public int size() {
        long b = b();
        while (true) {
            long a2 = a();
            long b2 = b();
            if (b == b2) {
                return (int) (a2 - b2);
            }
            b = b2;
        }
    }

    @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
    public boolean isEmpty() {
        return a() == b();
    }

    private void a(int i) {
        this.c = Math.min(i / 4, a);
    }

    private long a() {
        return this.b.get();
    }

    private long b() {
        return this.i.get();
    }

    private long c() {
        return this.b.get();
    }

    private long d() {
        return this.i.get();
    }

    private void a(long j2) {
        this.b.lazySet(j2);
    }

    private void b(long j2) {
        this.i.lazySet(j2);
    }

    private static int a(long j2, int i) {
        return b(((int) j2) & i);
    }

    private static void a(AtomicReferenceArray<Object> atomicReferenceArray, int i, Object obj) {
        atomicReferenceArray.lazySet(i, obj);
    }

    private static Object b(AtomicReferenceArray<Object> atomicReferenceArray, int i) {
        return atomicReferenceArray.get(i);
    }

    @Override // io.reactivex.rxjava3.internal.fuseable.SimpleQueue
    public boolean offer(T t, T t2) {
        AtomicReferenceArray<Object> atomicReferenceArray = this.f;
        long a2 = a();
        int i = this.e;
        long j2 = 2 + a2;
        if (b(atomicReferenceArray, a(j2, i)) == null) {
            int a3 = a(a2, i);
            a(atomicReferenceArray, a3 + 1, t2);
            a(atomicReferenceArray, a3, t);
            a(j2);
            return true;
        }
        AtomicReferenceArray<Object> atomicReferenceArray2 = new AtomicReferenceArray<>(atomicReferenceArray.length());
        this.f = atomicReferenceArray2;
        int a4 = a(a2, i);
        a(atomicReferenceArray2, a4 + 1, t2);
        a(atomicReferenceArray2, a4, t);
        a(atomicReferenceArray, atomicReferenceArray2);
        a(atomicReferenceArray, a4, j);
        a(j2);
        return true;
    }
}
