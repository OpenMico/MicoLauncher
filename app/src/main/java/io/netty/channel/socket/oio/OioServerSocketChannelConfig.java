package io.netty.channel.socket.oio;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.channel.socket.ServerSocketChannelConfig;

/* loaded from: classes4.dex */
public interface OioServerSocketChannelConfig extends ServerSocketChannelConfig {
    int getSoTimeout();

    @Override // io.netty.channel.socket.ServerSocketChannelConfig, io.netty.channel.ChannelConfig
    OioServerSocketChannelConfig setAllocator(ByteBufAllocator byteBufAllocator);

    @Override // io.netty.channel.ChannelConfig
    OioServerSocketChannelConfig setAutoClose(boolean z);

    @Override // io.netty.channel.socket.ServerSocketChannelConfig, io.netty.channel.ChannelConfig
    OioServerSocketChannelConfig setAutoRead(boolean z);

    @Override // io.netty.channel.socket.ServerSocketChannelConfig
    OioServerSocketChannelConfig setBacklog(int i);

    @Override // io.netty.channel.socket.ServerSocketChannelConfig, io.netty.channel.ChannelConfig
    OioServerSocketChannelConfig setConnectTimeoutMillis(int i);

    @Override // io.netty.channel.socket.ServerSocketChannelConfig, io.netty.channel.ChannelConfig
    @Deprecated
    OioServerSocketChannelConfig setMaxMessagesPerRead(int i);

    @Override // io.netty.channel.socket.ServerSocketChannelConfig, io.netty.channel.ChannelConfig
    OioServerSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator);

    @Override // io.netty.channel.socket.ServerSocketChannelConfig
    OioServerSocketChannelConfig setPerformancePreferences(int i, int i2, int i3);

    @Override // io.netty.channel.socket.ServerSocketChannelConfig
    OioServerSocketChannelConfig setReceiveBufferSize(int i);

    @Override // io.netty.channel.socket.ServerSocketChannelConfig, io.netty.channel.ChannelConfig
    OioServerSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator);

    @Override // io.netty.channel.socket.ServerSocketChannelConfig
    OioServerSocketChannelConfig setReuseAddress(boolean z);

    OioServerSocketChannelConfig setSoTimeout(int i);

    @Override // io.netty.channel.socket.ServerSocketChannelConfig, io.netty.channel.ChannelConfig
    @Deprecated
    OioServerSocketChannelConfig setWriteBufferHighWaterMark(int i);

    @Override // io.netty.channel.socket.ServerSocketChannelConfig, io.netty.channel.ChannelConfig
    @Deprecated
    OioServerSocketChannelConfig setWriteBufferLowWaterMark(int i);

    @Override // io.netty.channel.socket.ServerSocketChannelConfig, io.netty.channel.ChannelConfig
    OioServerSocketChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark);

    @Override // io.netty.channel.socket.ServerSocketChannelConfig, io.netty.channel.ChannelConfig
    OioServerSocketChannelConfig setWriteSpinCount(int i);
}
