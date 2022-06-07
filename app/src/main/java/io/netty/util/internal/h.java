package io.netty.util.internal;

import java.util.Iterator;

/* compiled from: ConcurrentCircularArrayQueue.java */
/* loaded from: classes4.dex */
abstract class h<E> extends i<E> {
    protected static final int a;
    private static final long d;
    private static final int e;
    protected final long b;
    protected final E[] c;

    static {
        int arrayIndexScale = w.a.arrayIndexScale(Object[].class);
        if (4 == arrayIndexScale) {
            e = 2;
        } else if (8 == arrayIndexScale) {
            e = 3;
        } else {
            throw new IllegalStateException("Unknown pointer size");
        }
        a = 128 / arrayIndexScale;
        d = w.a.arrayBaseOffset(Object[].class) + (a * arrayIndexScale);
    }

    public h(int i) {
        int a2 = a(i);
        this.b = a2 - 1;
        this.c = (E[]) new Object[a2 + (a * 2)];
    }

    private static int a(int i) {
        return 1 << (32 - Integer.numberOfLeadingZeros(i - 1));
    }

    protected final long a(long j) {
        return a(j, this.b);
    }

    protected static final long a(long j, long j2) {
        return d + ((j & j2) << e);
    }

    protected static final <E> void a(E[] eArr, long j, E e2) {
        w.a.putObject(eArr, j, e2);
    }

    protected final void a(long j, E e2) {
        b(this.c, j, e2);
    }

    protected static final <E> void b(E[] eArr, long j, E e2) {
        w.a.putOrderedObject(eArr, j, e2);
    }

    protected static final <E> E a(E[] eArr, long j) {
        return (E) w.a.getObjectVolatile(eArr, j);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractQueue, java.util.AbstractCollection, java.util.Collection
    public void clear() {
        while (true) {
            if (poll() == null && isEmpty()) {
                return;
            }
        }
    }
}
