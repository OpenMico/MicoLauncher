package io.netty.util.internal;

/* compiled from: MpscArrayQueue.java */
/* loaded from: classes4.dex */
abstract class q<E> extends n<E> {
    private static final long d;
    private volatile long e;

    static {
        try {
            d = w.a.objectFieldOffset(q.class.getDeclaredField("e"));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public q(int i) {
        super(i);
    }

    protected final long c() {
        return this.e;
    }

    protected final boolean b(long j, long j2) {
        return w.a.compareAndSwapLong(this, d, j, j2);
    }
}
