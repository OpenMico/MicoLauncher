package io.netty.channel;

import io.netty.channel.DefaultMaxMessagesRecvByteBufAllocator;
import io.netty.channel.RecvByteBufAllocator;

/* loaded from: classes4.dex */
public class FixedRecvByteBufAllocator extends DefaultMaxMessagesRecvByteBufAllocator {
    private final int a;

    /* loaded from: classes4.dex */
    private final class a extends DefaultMaxMessagesRecvByteBufAllocator.MaxMessageHandle {
        private final int c;

        public a(int i) {
            super();
            this.c = i;
        }

        @Override // io.netty.channel.RecvByteBufAllocator.Handle
        public int guess() {
            return this.c;
        }
    }

    public FixedRecvByteBufAllocator(int i) {
        if (i > 0) {
            this.a = i;
            return;
        }
        throw new IllegalArgumentException("bufferSize must greater than 0: " + i);
    }

    @Override // io.netty.channel.RecvByteBufAllocator
    public RecvByteBufAllocator.Handle newHandle() {
        return new a(this.a);
    }
}
