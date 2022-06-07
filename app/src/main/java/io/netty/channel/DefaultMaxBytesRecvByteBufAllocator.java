package io.netty.channel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.RecvByteBufAllocator;
import java.util.AbstractMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class DefaultMaxBytesRecvByteBufAllocator implements MaxBytesRecvByteBufAllocator {
    private volatile int a;
    private volatile int b;

    /* loaded from: classes4.dex */
    private final class a implements RecvByteBufAllocator.Handle {
        private int b;
        private int c;
        private int d;
        private int e;

        @Override // io.netty.channel.RecvByteBufAllocator.Handle
        public void incMessagesRead(int i) {
        }

        @Override // io.netty.channel.RecvByteBufAllocator.Handle
        public void readComplete() {
        }

        private a() {
        }

        @Override // io.netty.channel.RecvByteBufAllocator.Handle
        public ByteBuf allocate(ByteBufAllocator byteBufAllocator) {
            return byteBufAllocator.ioBuffer(guess());
        }

        @Override // io.netty.channel.RecvByteBufAllocator.Handle
        public int guess() {
            return Math.min(this.b, this.c);
        }

        @Override // io.netty.channel.RecvByteBufAllocator.Handle
        public void reset(ChannelConfig channelConfig) {
            this.c = DefaultMaxBytesRecvByteBufAllocator.this.maxBytesPerRead();
            this.b = DefaultMaxBytesRecvByteBufAllocator.this.maxBytesPerIndividualRead();
        }

        @Override // io.netty.channel.RecvByteBufAllocator.Handle
        public void lastBytesRead(int i) {
            this.d = i;
            this.c -= i;
        }

        @Override // io.netty.channel.RecvByteBufAllocator.Handle
        public int lastBytesRead() {
            return this.d;
        }

        @Override // io.netty.channel.RecvByteBufAllocator.Handle
        public boolean continueReading() {
            return this.c > 0 && this.e == this.d;
        }

        @Override // io.netty.channel.RecvByteBufAllocator.Handle
        public void attemptedBytesRead(int i) {
            this.e = i;
        }

        @Override // io.netty.channel.RecvByteBufAllocator.Handle
        public int attemptedBytesRead() {
            return this.e;
        }
    }

    public DefaultMaxBytesRecvByteBufAllocator() {
        this(65536, 65536);
    }

    public DefaultMaxBytesRecvByteBufAllocator(int i, int i2) {
        a(i, i2);
        this.a = i;
        this.b = i2;
    }

    @Override // io.netty.channel.RecvByteBufAllocator
    public RecvByteBufAllocator.Handle newHandle() {
        return new a();
    }

    @Override // io.netty.channel.MaxBytesRecvByteBufAllocator
    public int maxBytesPerRead() {
        return this.a;
    }

    @Override // io.netty.channel.MaxBytesRecvByteBufAllocator
    public DefaultMaxBytesRecvByteBufAllocator maxBytesPerRead(int i) {
        if (i > 0) {
            synchronized (this) {
                int maxBytesPerIndividualRead = maxBytesPerIndividualRead();
                if (i >= maxBytesPerIndividualRead) {
                    this.a = i;
                } else {
                    throw new IllegalArgumentException("maxBytesPerRead cannot be less than maxBytesPerIndividualRead (" + maxBytesPerIndividualRead + "): " + i);
                }
            }
            return this;
        }
        throw new IllegalArgumentException("maxBytesPerRead: " + i + " (expected: > 0)");
    }

    @Override // io.netty.channel.MaxBytesRecvByteBufAllocator
    public int maxBytesPerIndividualRead() {
        return this.b;
    }

    @Override // io.netty.channel.MaxBytesRecvByteBufAllocator
    public DefaultMaxBytesRecvByteBufAllocator maxBytesPerIndividualRead(int i) {
        if (i > 0) {
            synchronized (this) {
                int maxBytesPerRead = maxBytesPerRead();
                if (i <= maxBytesPerRead) {
                    this.b = i;
                } else {
                    throw new IllegalArgumentException("maxBytesPerIndividualRead cannot be greater than maxBytesPerRead (" + maxBytesPerRead + "): " + i);
                }
            }
            return this;
        }
        throw new IllegalArgumentException("maxBytesPerIndividualRead: " + i + " (expected: > 0)");
    }

    @Override // io.netty.channel.MaxBytesRecvByteBufAllocator
    public synchronized Map.Entry<Integer, Integer> maxBytesPerReadPair() {
        return new AbstractMap.SimpleEntry(Integer.valueOf(this.a), Integer.valueOf(this.b));
    }

    private void a(int i, int i2) {
        if (i <= 0) {
            throw new IllegalArgumentException("maxBytesPerRead: " + i + " (expected: > 0)");
        } else if (i2 <= 0) {
            throw new IllegalArgumentException("maxBytesPerIndividualRead: " + i2 + " (expected: > 0)");
        } else if (i < i2) {
            throw new IllegalArgumentException("maxBytesPerRead cannot be less than maxBytesPerIndividualRead (" + i2 + "): " + i);
        }
    }

    @Override // io.netty.channel.MaxBytesRecvByteBufAllocator
    public DefaultMaxBytesRecvByteBufAllocator maxBytesPerReadPair(int i, int i2) {
        a(i, i2);
        synchronized (this) {
            this.a = i;
            this.b = i2;
        }
        return this;
    }
}
