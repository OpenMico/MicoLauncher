package io.netty.buffer;

import io.netty.util.internal.PlatformDependent;

/* loaded from: classes4.dex */
public final class UnpooledByteBufAllocator extends AbstractByteBufAllocator {
    public static final UnpooledByteBufAllocator DEFAULT = new UnpooledByteBufAllocator(PlatformDependent.directBufferPreferred());
    private final boolean a;

    @Override // io.netty.buffer.ByteBufAllocator
    public boolean isDirectBufferPooled() {
        return false;
    }

    public UnpooledByteBufAllocator(boolean z) {
        this(z, false);
    }

    public UnpooledByteBufAllocator(boolean z, boolean z2) {
        super(z);
        this.a = z2;
    }

    @Override // io.netty.buffer.AbstractByteBufAllocator
    protected ByteBuf newHeapBuffer(int i, int i2) {
        return PlatformDependent.hasUnsafe() ? new y(this, i, i2) : new UnpooledHeapByteBuf(this, i, i2);
    }

    @Override // io.netty.buffer.AbstractByteBufAllocator
    protected ByteBuf newDirectBuffer(int i, int i2) {
        ByteBuf a = PlatformDependent.hasUnsafe() ? ab.a(this, i, i2) : new UnpooledDirectByteBuf(this, i, i2);
        return this.a ? a : toLeakAwareBuffer(a);
    }

    @Override // io.netty.buffer.AbstractByteBufAllocator, io.netty.buffer.ByteBufAllocator
    public CompositeByteBuf compositeHeapBuffer(int i) {
        CompositeByteBuf compositeByteBuf = new CompositeByteBuf(this, false, i);
        return this.a ? compositeByteBuf : toLeakAwareBuffer(compositeByteBuf);
    }

    @Override // io.netty.buffer.AbstractByteBufAllocator, io.netty.buffer.ByteBufAllocator
    public CompositeByteBuf compositeDirectBuffer(int i) {
        CompositeByteBuf compositeByteBuf = new CompositeByteBuf(this, true, i);
        return this.a ? compositeByteBuf : toLeakAwareBuffer(compositeByteBuf);
    }
}
