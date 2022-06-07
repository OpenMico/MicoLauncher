package io.netty.util.internal;

/* compiled from: MpscArrayQueue.java */
/* loaded from: classes4.dex */
abstract class m<E> extends p<E> {
    private volatile long d;

    public m(int i) {
        super(i);
    }

    protected final long b() {
        return this.d;
    }

    protected final void c(long j) {
        this.d = j;
    }
}
