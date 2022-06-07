package io.netty.channel.epoll;

import io.netty.channel.ChannelConfig;
import io.netty.channel.RecvByteBufAllocator;

/* compiled from: EpollRecvByteAllocatorStreamingHandle.java */
/* loaded from: classes4.dex */
final class d extends c {
    public d(RecvByteBufAllocator.Handle handle, ChannelConfig channelConfig) {
        super(handle, channelConfig);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // io.netty.channel.epoll.c
    public boolean b() {
        return c() && lastBytesRead() == attemptedBytesRead();
    }
}
