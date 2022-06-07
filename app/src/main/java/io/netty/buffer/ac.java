package io.netty.buffer;

import io.netty.util.internal.PlatformDependent;

/* compiled from: UnsafeDirectSwappedByteBuf.java */
/* loaded from: classes4.dex */
final class ac extends b {
    /* JADX INFO: Access modifiers changed from: package-private */
    public ac(AbstractByteBuf abstractByteBuf) {
        super(abstractByteBuf);
    }

    private static long d(AbstractByteBuf abstractByteBuf, int i) {
        return abstractByteBuf.memoryAddress() + i;
    }

    @Override // io.netty.buffer.b
    protected long c(AbstractByteBuf abstractByteBuf, int i) {
        return PlatformDependent.getLong(d(abstractByteBuf, i));
    }

    @Override // io.netty.buffer.b
    protected int b(AbstractByteBuf abstractByteBuf, int i) {
        return PlatformDependent.getInt(d(abstractByteBuf, i));
    }

    @Override // io.netty.buffer.b
    protected short a(AbstractByteBuf abstractByteBuf, int i) {
        return PlatformDependent.getShort(d(abstractByteBuf, i));
    }

    @Override // io.netty.buffer.b
    protected void a(AbstractByteBuf abstractByteBuf, int i, short s) {
        PlatformDependent.putShort(d(abstractByteBuf, i), s);
    }

    @Override // io.netty.buffer.b
    protected void a(AbstractByteBuf abstractByteBuf, int i, int i2) {
        PlatformDependent.putInt(d(abstractByteBuf, i), i2);
    }

    @Override // io.netty.buffer.b
    protected void a(AbstractByteBuf abstractByteBuf, int i, long j) {
        PlatformDependent.putLong(d(abstractByteBuf, i), j);
    }
}
