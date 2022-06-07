package io.netty.channel.socket;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelConfig;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;

/* loaded from: classes4.dex */
public interface ServerSocketChannelConfig extends ChannelConfig {
    int getBacklog();

    int getReceiveBufferSize();

    boolean isReuseAddress();

    @Override // io.netty.channel.ChannelConfig
    ServerSocketChannelConfig setAllocator(ByteBufAllocator byteBufAllocator);

    @Override // io.netty.channel.ChannelConfig
    ServerSocketChannelConfig setAutoRead(boolean z);

    ServerSocketChannelConfig setBacklog(int i);

    @Override // io.netty.channel.ChannelConfig
    ServerSocketChannelConfig setConnectTimeoutMillis(int i);

    @Override // io.netty.channel.ChannelConfig
    @Deprecated
    ServerSocketChannelConfig setMaxMessagesPerRead(int i);

    @Override // io.netty.channel.ChannelConfig
    ServerSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator);

    ServerSocketChannelConfig setPerformancePreferences(int i, int i2, int i3);

    ServerSocketChannelConfig setReceiveBufferSize(int i);

    @Override // io.netty.channel.ChannelConfig
    ServerSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator);

    ServerSocketChannelConfig setReuseAddress(boolean z);

    @Override // io.netty.channel.ChannelConfig
    @Deprecated
    ServerSocketChannelConfig setWriteBufferHighWaterMark(int i);

    @Override // io.netty.channel.ChannelConfig
    @Deprecated
    ServerSocketChannelConfig setWriteBufferLowWaterMark(int i);

    @Override // io.netty.channel.ChannelConfig
    ServerSocketChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark);

    @Override // io.netty.channel.ChannelConfig
    ServerSocketChannelConfig setWriteSpinCount(int i);
}
