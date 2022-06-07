package io.netty.channel;

/* loaded from: classes4.dex */
public interface MaxMessagesRecvByteBufAllocator extends RecvByteBufAllocator {
    int maxMessagesPerRead();

    MaxMessagesRecvByteBufAllocator maxMessagesPerRead(int i);
}
