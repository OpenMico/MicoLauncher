package io.netty.channel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.util.internal.ObjectUtil;

/* loaded from: classes4.dex */
public interface RecvByteBufAllocator {

    /* loaded from: classes4.dex */
    public interface Handle {
        ByteBuf allocate(ByteBufAllocator byteBufAllocator);

        int attemptedBytesRead();

        void attemptedBytesRead(int i);

        boolean continueReading();

        int guess();

        void incMessagesRead(int i);

        int lastBytesRead();

        void lastBytesRead(int i);

        void readComplete();

        void reset(ChannelConfig channelConfig);
    }

    Handle newHandle();

    /* loaded from: classes4.dex */
    public static class DelegatingHandle implements Handle {
        private final Handle a;

        public DelegatingHandle(Handle handle) {
            this.a = (Handle) ObjectUtil.checkNotNull(handle, "delegate");
        }

        protected final Handle delegate() {
            return this.a;
        }

        @Override // io.netty.channel.RecvByteBufAllocator.Handle
        public ByteBuf allocate(ByteBufAllocator byteBufAllocator) {
            return this.a.allocate(byteBufAllocator);
        }

        @Override // io.netty.channel.RecvByteBufAllocator.Handle
        public int guess() {
            return this.a.guess();
        }

        @Override // io.netty.channel.RecvByteBufAllocator.Handle
        public void reset(ChannelConfig channelConfig) {
            this.a.reset(channelConfig);
        }

        @Override // io.netty.channel.RecvByteBufAllocator.Handle
        public void incMessagesRead(int i) {
            this.a.incMessagesRead(i);
        }

        @Override // io.netty.channel.RecvByteBufAllocator.Handle
        public void lastBytesRead(int i) {
            this.a.lastBytesRead(i);
        }

        @Override // io.netty.channel.RecvByteBufAllocator.Handle
        public int lastBytesRead() {
            return this.a.lastBytesRead();
        }

        @Override // io.netty.channel.RecvByteBufAllocator.Handle
        public boolean continueReading() {
            return this.a.continueReading();
        }

        @Override // io.netty.channel.RecvByteBufAllocator.Handle
        public int attemptedBytesRead() {
            return this.a.attemptedBytesRead();
        }

        @Override // io.netty.channel.RecvByteBufAllocator.Handle
        public void attemptedBytesRead(int i) {
            this.a.attemptedBytesRead(i);
        }

        @Override // io.netty.channel.RecvByteBufAllocator.Handle
        public void readComplete() {
            this.a.readComplete();
        }
    }
}
