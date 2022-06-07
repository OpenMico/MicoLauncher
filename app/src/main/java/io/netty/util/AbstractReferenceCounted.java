package io.netty.util;

import io.netty.util.internal.PlatformDependent;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/* loaded from: classes4.dex */
public abstract class AbstractReferenceCounted implements ReferenceCounted {
    private static final AtomicIntegerFieldUpdater<AbstractReferenceCounted> a;
    private volatile int b = 1;

    protected abstract void deallocate();

    static {
        AtomicIntegerFieldUpdater<AbstractReferenceCounted> newAtomicIntegerFieldUpdater = PlatformDependent.newAtomicIntegerFieldUpdater(AbstractReferenceCounted.class, "refCnt");
        if (newAtomicIntegerFieldUpdater == null) {
            newAtomicIntegerFieldUpdater = AtomicIntegerFieldUpdater.newUpdater(AbstractReferenceCounted.class, "b");
        }
        a = newAtomicIntegerFieldUpdater;
    }

    @Override // io.netty.util.ReferenceCounted
    public final int refCnt() {
        return this.b;
    }

    protected final void setRefCnt(int i) {
        this.b = i;
    }

    @Override // io.netty.util.ReferenceCounted
    public ReferenceCounted retain() {
        int i;
        do {
            i = this.b;
            if (i == 0) {
                throw new IllegalReferenceCountException(0, 1);
            } else if (i == Integer.MAX_VALUE) {
                throw new IllegalReferenceCountException(Integer.MAX_VALUE, 1);
            }
        } while (!a.compareAndSet(this, i, i + 1));
        return this;
    }

    @Override // io.netty.util.ReferenceCounted
    public ReferenceCounted retain(int i) {
        int i2;
        if (i > 0) {
            do {
                i2 = this.b;
                if (i2 == 0) {
                    throw new IllegalReferenceCountException(0, 1);
                } else if (i2 > Integer.MAX_VALUE - i) {
                    throw new IllegalReferenceCountException(i2, i);
                }
            } while (!a.compareAndSet(this, i2, i2 + i));
            return this;
        }
        throw new IllegalArgumentException("increment: " + i + " (expected: > 0)");
    }

    @Override // io.netty.util.ReferenceCounted
    public ReferenceCounted touch() {
        return touch(null);
    }

    @Override // io.netty.util.ReferenceCounted
    public boolean release() {
        int i;
        do {
            i = this.b;
            if (i == 0) {
                throw new IllegalReferenceCountException(0, -1);
            }
        } while (!a.compareAndSet(this, i, i - 1));
        if (i != 1) {
            return false;
        }
        deallocate();
        return true;
    }

    @Override // io.netty.util.ReferenceCounted
    public boolean release(int i) {
        int i2;
        if (i > 0) {
            do {
                i2 = this.b;
                if (i2 < i) {
                    throw new IllegalReferenceCountException(i2, -i);
                }
            } while (!a.compareAndSet(this, i2, i2 - i));
            if (i2 != i) {
                return false;
            }
            deallocate();
            return true;
        }
        throw new IllegalArgumentException("decrement: " + i + " (expected: > 0)");
    }
}
