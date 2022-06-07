package org.conscrypt;

import java.nio.ByteBuffer;

/* loaded from: classes5.dex */
public abstract class AllocatedBuffer {
    public abstract ByteBuffer nioBuffer();

    public abstract AllocatedBuffer release();

    @Deprecated
    public AllocatedBuffer retain() {
        return this;
    }

    public static AllocatedBuffer wrap(final ByteBuffer byteBuffer) {
        Preconditions.checkNotNull(byteBuffer, "buffer");
        return new AllocatedBuffer() { // from class: org.conscrypt.AllocatedBuffer.1
            @Override // org.conscrypt.AllocatedBuffer
            public AllocatedBuffer release() {
                return this;
            }

            @Override // org.conscrypt.AllocatedBuffer
            public ByteBuffer nioBuffer() {
                return byteBuffer;
            }
        };
    }
}
