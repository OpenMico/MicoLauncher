package io.netty.buffer;

import io.netty.util.internal.PlatformDependent;

/* compiled from: UnsafeHeapSwappedByteBuf.java */
/* loaded from: classes4.dex */
final class ad extends b {
    /* JADX INFO: Access modifiers changed from: package-private */
    public ad(AbstractByteBuf abstractByteBuf) {
        super(abstractByteBuf);
    }

    private static int a(ByteBuf byteBuf, int i) {
        return byteBuf.arrayOffset() + i;
    }

    @Override // io.netty.buffer.b
    protected long c(AbstractByteBuf abstractByteBuf, int i) {
        return PlatformDependent.getLong(abstractByteBuf.array(), a((ByteBuf) abstractByteBuf, i));
    }

    @Override // io.netty.buffer.b
    protected int b(AbstractByteBuf abstractByteBuf, int i) {
        return PlatformDependent.getInt(abstractByteBuf.array(), a((ByteBuf) abstractByteBuf, i));
    }

    @Override // io.netty.buffer.b
    protected short a(AbstractByteBuf abstractByteBuf, int i) {
        return PlatformDependent.getShort(abstractByteBuf.array(), a((ByteBuf) abstractByteBuf, i));
    }

    @Override // io.netty.buffer.b
    protected void a(AbstractByteBuf abstractByteBuf, int i, short s) {
        PlatformDependent.putShort(abstractByteBuf.array(), a((ByteBuf) abstractByteBuf, i), s);
    }

    @Override // io.netty.buffer.b
    protected void a(AbstractByteBuf abstractByteBuf, int i, int i2) {
        PlatformDependent.putInt(abstractByteBuf.array(), a((ByteBuf) abstractByteBuf, i), i2);
    }

    @Override // io.netty.buffer.b
    protected void a(AbstractByteBuf abstractByteBuf, int i, long j) {
        PlatformDependent.putLong(abstractByteBuf.array(), a((ByteBuf) abstractByteBuf, i), j);
    }
}
