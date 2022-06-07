package io.netty.channel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.RecvByteBufAllocator;

/* loaded from: classes4.dex */
public abstract class DefaultMaxMessagesRecvByteBufAllocator implements MaxMessagesRecvByteBufAllocator {
    private volatile int a;

    public DefaultMaxMessagesRecvByteBufAllocator() {
        this(1);
    }

    public DefaultMaxMessagesRecvByteBufAllocator(int i) {
        maxMessagesPerRead(i);
    }

    @Override // io.netty.channel.MaxMessagesRecvByteBufAllocator
    public int maxMessagesPerRead() {
        return this.a;
    }

    @Override // io.netty.channel.MaxMessagesRecvByteBufAllocator
    public MaxMessagesRecvByteBufAllocator maxMessagesPerRead(int i) {
        if (i > 0) {
            this.a = i;
            return this;
        }
        throw new IllegalArgumentException("maxMessagesPerRead: " + i + " (expected: > 0)");
    }

    /* loaded from: classes4.dex */
    public abstract class MaxMessageHandle implements RecvByteBufAllocator.Handle {
        private ChannelConfig a;
        private int c;
        private int d;
        private int e;
        private int f;
        private int g;

        @Override // io.netty.channel.RecvByteBufAllocator.Handle
        public void readComplete() {
        }

        public MaxMessageHandle() {
        }

        @Override // io.netty.channel.RecvByteBufAllocator.Handle
        public void reset(ChannelConfig channelConfig) {
            this.a = channelConfig;
            this.c = DefaultMaxMessagesRecvByteBufAllocator.this.maxMessagesPerRead();
            this.e = 0;
            this.d = 0;
        }

        @Override // io.netty.channel.RecvByteBufAllocator.Handle
        public ByteBuf allocate(ByteBufAllocator byteBufAllocator) {
            return byteBufAllocator.ioBuffer(guess());
        }

        @Override // io.netty.channel.RecvByteBufAllocator.Handle
        public final void incMessagesRead(int i) {
            this.d += i;
        }

        @Override // io.netty.channel.RecvByteBufAllocator.Handle
        public final void lastBytesRead(int i) {
            this.g = i;
            this.e += i;
            if (this.e < 0) {
                this.e = Integer.MAX_VALUE;
            }
        }

        @Override // io.netty.channel.RecvByteBufAllocator.Handle
        public final int lastBytesRead() {
            return this.g;
        }

        @Override // io.netty.channel.RecvByteBufAllocator.Handle
        public boolean continueReading() {
            return this.a.isAutoRead() && this.f == this.g && this.d < this.c && this.e < Integer.MAX_VALUE;
        }

        @Override // io.netty.channel.RecvByteBufAllocator.Handle
        public int attemptedBytesRead() {
            return this.f;
        }

        @Override // io.netty.channel.RecvByteBufAllocator.Handle
        public void attemptedBytesRead(int i) {
            this.f = i;
        }

        protected final int totalBytesRead() {
            return this.e;
        }
    }
}
