package io.netty.channel.epoll;

import io.netty.channel.ChannelConfig;
import io.netty.channel.RecvByteBufAllocator;

/* compiled from: EpollRecvByteAllocatorHandle.java */
/* loaded from: classes4.dex */
class c extends RecvByteBufAllocator.DelegatingHandle {
    private boolean a;
    private final ChannelConfig b;
    private boolean c;

    /* JADX INFO: Access modifiers changed from: package-private */
    public c(RecvByteBufAllocator.Handle handle, ChannelConfig channelConfig) {
        super(handle);
        this.b = channelConfig;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void a() {
        this.c = true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean b() {
        return this.a && lastBytesRead() > 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void a(boolean z) {
        this.a = z;
    }

    final boolean c() {
        return this.a;
    }

    @Override // io.netty.channel.RecvByteBufAllocator.DelegatingHandle, io.netty.channel.RecvByteBufAllocator.Handle
    public final boolean continueReading() {
        return this.c || (b() && this.b.isAutoRead()) || super.continueReading();
    }
}
