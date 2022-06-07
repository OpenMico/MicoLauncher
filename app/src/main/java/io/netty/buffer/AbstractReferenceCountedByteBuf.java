package io.netty.buffer;

import io.netty.util.IllegalReferenceCountException;
import io.netty.util.internal.PlatformDependent;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/* loaded from: classes4.dex */
public abstract class AbstractReferenceCountedByteBuf extends AbstractByteBuf {
    private static final AtomicIntegerFieldUpdater<AbstractReferenceCountedByteBuf> d;
    private volatile int e = 1;

    protected abstract void deallocate();

    @Override // io.netty.buffer.ByteBuf, io.netty.util.ReferenceCounted
    public ByteBuf touch() {
        return this;
    }

    @Override // io.netty.buffer.ByteBuf, io.netty.util.ReferenceCounted
    public ByteBuf touch(Object obj) {
        return this;
    }

    static {
        AtomicIntegerFieldUpdater<AbstractReferenceCountedByteBuf> newAtomicIntegerFieldUpdater = PlatformDependent.newAtomicIntegerFieldUpdater(AbstractReferenceCountedByteBuf.class, "refCnt");
        if (newAtomicIntegerFieldUpdater == null) {
            newAtomicIntegerFieldUpdater = AtomicIntegerFieldUpdater.newUpdater(AbstractReferenceCountedByteBuf.class, "e");
        }
        d = newAtomicIntegerFieldUpdater;
    }

    public AbstractReferenceCountedByteBuf(int i) {
        super(i);
    }

    @Override // io.netty.util.ReferenceCounted
    public int refCnt() {
        return this.e;
    }

    protected final void setRefCnt(int i) {
        this.e = i;
    }

    @Override // io.netty.buffer.ByteBuf, io.netty.util.ReferenceCounted
    public ByteBuf retain() {
        int i;
        do {
            i = this.e;
            if (i == 0) {
                throw new IllegalReferenceCountException(0, 1);
            } else if (i == Integer.MAX_VALUE) {
                throw new IllegalReferenceCountException(Integer.MAX_VALUE, 1);
            }
        } while (!d.compareAndSet(this, i, i + 1));
        return this;
    }

    @Override // io.netty.buffer.ByteBuf, io.netty.util.ReferenceCounted
    public ByteBuf retain(int i) {
        int i2;
        if (i > 0) {
            do {
                i2 = this.e;
                if (i2 == 0) {
                    throw new IllegalReferenceCountException(0, i);
                } else if (i2 > Integer.MAX_VALUE - i) {
                    throw new IllegalReferenceCountException(i2, i);
                }
            } while (!d.compareAndSet(this, i2, i2 + i));
            return this;
        }
        throw new IllegalArgumentException("increment: " + i + " (expected: > 0)");
    }

    @Override // io.netty.util.ReferenceCounted
    public boolean release() {
        int i;
        do {
            i = this.e;
            if (i == 0) {
                throw new IllegalReferenceCountException(0, -1);
            }
        } while (!d.compareAndSet(this, i, i - 1));
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
                i2 = this.e;
                if (i2 < i) {
                    throw new IllegalReferenceCountException(i2, -i);
                }
            } while (!d.compareAndSet(this, i2, i2 - i));
            if (i2 != i) {
                return false;
            }
            deallocate();
            return true;
        }
        throw new IllegalArgumentException("decrement: " + i + " (expected: > 0)");
    }
}
