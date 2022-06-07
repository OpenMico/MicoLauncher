package io.netty.buffer;

import io.netty.util.Recycler;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* compiled from: PooledByteBuf.java */
/* loaded from: classes4.dex */
public abstract class m<T> extends AbstractReferenceCountedByteBuf {
    static final /* synthetic */ boolean k = !m.class.desiredAssertionStatus();
    protected i<T> d;
    protected long e;
    protected T f;
    protected int g;
    protected int h;
    int i;
    l j;
    private final Recycler.Handle<m<T>> l;
    private ByteBuffer m;

    protected abstract ByteBuffer a(T t);

    @Override // io.netty.buffer.ByteBuf
    public final ByteBuf unwrap() {
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public m(Recycler.Handle<? extends m<T>> handle, int i) {
        super(i);
        this.l = handle;
    }

    public void a(i<T> iVar, long j, int i, int i2, int i3, l lVar) {
        if (!k && j < 0) {
            throw new AssertionError();
        } else if (k || iVar != null) {
            this.d = iVar;
            this.e = j;
            this.f = iVar.b;
            this.g = i;
            this.h = i2;
            this.i = i3;
            this.m = null;
            this.j = lVar;
        } else {
            throw new AssertionError();
        }
    }

    public void a(i<T> iVar, int i) {
        if (k || iVar != null) {
            this.d = iVar;
            this.e = 0L;
            this.f = iVar.b;
            this.g = 0;
            this.i = i;
            this.h = i;
            this.m = null;
            this.j = null;
            return;
        }
        throw new AssertionError();
    }

    final void a(int i) {
        maxCapacity(i);
        setRefCnt(1);
        b(0, 0);
        a();
    }

    @Override // io.netty.buffer.ByteBuf
    public final int capacity() {
        return this.h;
    }

    @Override // io.netty.buffer.ByteBuf
    public final ByteBuf capacity(int i) {
        ensureAccessible();
        if (!this.d.c) {
            int i2 = this.h;
            if (i > i2) {
                if (i <= this.i) {
                    this.h = i;
                    return this;
                }
            } else if (i >= i2) {
                return this;
            } else {
                int i3 = this.i;
                if (i > (i3 >>> 1)) {
                    if (i3 > 512) {
                        this.h = i;
                        setIndex(Math.min(readerIndex(), i), Math.min(writerIndex(), i));
                        return this;
                    } else if (i > i3 - 16) {
                        this.h = i;
                        setIndex(Math.min(readerIndex(), i), Math.min(writerIndex(), i));
                        return this;
                    }
                }
            }
        } else if (i == this.h) {
            return this;
        }
        this.d.a.a((m) this, i, true);
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public final ByteBufAllocator alloc() {
        return this.d.a.b;
    }

    @Override // io.netty.buffer.ByteBuf
    public final ByteOrder order() {
        return ByteOrder.BIG_ENDIAN;
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public final ByteBuf retainedDuplicate() {
        return o.a(this, readerIndex(), writerIndex());
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public final ByteBuf retainedSlice() {
        int readerIndex = readerIndex();
        return retainedSlice(readerIndex, writerIndex() - readerIndex);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public final ByteBuf retainedSlice(int i, int i2) {
        return q.b(this, i, i2, i);
    }

    protected final ByteBuffer b() {
        ByteBuffer byteBuffer = this.m;
        if (byteBuffer != null) {
            return byteBuffer;
        }
        ByteBuffer a = a((m<T>) this.f);
        this.m = a;
        return a;
    }

    @Override // io.netty.buffer.AbstractReferenceCountedByteBuf
    protected final void deallocate() {
        long j = this.e;
        if (j >= 0) {
            this.e = -1L;
            this.f = null;
            this.d.a.a(this.d, j, this.i, this.j);
            c();
        }
    }

    private void c() {
        this.l.recycle(this);
    }

    protected final int b(int i) {
        return this.g + i;
    }
}
