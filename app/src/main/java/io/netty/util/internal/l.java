package io.netty.util.internal;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: MpscArrayQueue.java */
/* loaded from: classes4.dex */
public abstract class l<E> extends o<E> {
    private static final long d;
    private volatile long e;

    static {
        try {
            d = w.a.objectFieldOffset(l.class.getDeclaredField("e"));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public l(int i) {
        super(i);
    }

    protected final long a() {
        return this.e;
    }

    protected void b(long j) {
        w.a.putOrderedLong(this, d, j);
    }
}
