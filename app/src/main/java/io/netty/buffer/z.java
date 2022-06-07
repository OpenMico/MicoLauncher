package io.netty.buffer;

import io.netty.util.internal.PlatformDependent;
import java.nio.ByteBuffer;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: UnpooledUnsafeNoCleanerDirectByteBuf.java */
/* loaded from: classes4.dex */
public final class z extends UnpooledUnsafeDirectByteBuf {
    /* JADX INFO: Access modifiers changed from: package-private */
    public z(ByteBufAllocator byteBufAllocator, int i, int i2) {
        super(byteBufAllocator, i, i2);
    }

    @Override // io.netty.buffer.UnpooledUnsafeDirectByteBuf
    protected ByteBuffer allocateDirect(int i) {
        return PlatformDependent.allocateDirectNoCleaner(i);
    }

    @Override // io.netty.buffer.UnpooledUnsafeDirectByteBuf
    protected void freeDirect(ByteBuffer byteBuffer) {
        PlatformDependent.freeDirectNoCleaner(byteBuffer);
    }

    @Override // io.netty.buffer.UnpooledUnsafeDirectByteBuf, io.netty.buffer.ByteBuf
    public ByteBuf capacity(int i) {
        ensureAccessible();
        if (i < 0 || i > maxCapacity()) {
            throw new IllegalArgumentException("newCapacity: " + i);
        }
        int readerIndex = readerIndex();
        int writerIndex = writerIndex();
        int capacity = capacity();
        if (i > capacity) {
            a(PlatformDependent.reallocateDirectNoCleaner(this.d, i), false);
        } else if (i < capacity) {
            ByteBuffer byteBuffer = this.d;
            ByteBuffer allocateDirect = allocateDirect(i);
            if (readerIndex < i) {
                if (writerIndex > i) {
                    writerIndex(i);
                } else {
                    i = writerIndex;
                }
                byteBuffer.position(readerIndex).limit(i);
                allocateDirect.position(readerIndex).limit(i);
                allocateDirect.put(byteBuffer);
                allocateDirect.clear();
            } else {
                setIndex(i, i);
            }
            a(allocateDirect, true);
        }
        return this;
    }
}
