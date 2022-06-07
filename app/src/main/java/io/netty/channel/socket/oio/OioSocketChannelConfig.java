package io.netty.channel.socket.oio;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.channel.socket.SocketChannelConfig;

/* loaded from: classes4.dex */
public interface OioSocketChannelConfig extends SocketChannelConfig {
    int getSoTimeout();

    @Override // io.netty.channel.socket.SocketChannelConfig, io.netty.channel.ChannelConfig
    OioSocketChannelConfig setAllocator(ByteBufAllocator byteBufAllocator);

    @Override // io.netty.channel.socket.SocketChannelConfig
    OioSocketChannelConfig setAllowHalfClosure(boolean z);

    @Override // io.netty.channel.socket.SocketChannelConfig, io.netty.channel.ChannelConfig
    OioSocketChannelConfig setAutoClose(boolean z);

    @Override // io.netty.channel.socket.SocketChannelConfig, io.netty.channel.ChannelConfig
    OioSocketChannelConfig setAutoRead(boolean z);

    @Override // io.netty.channel.socket.SocketChannelConfig, io.netty.channel.ChannelConfig
    OioSocketChannelConfig setConnectTimeoutMillis(int i);

    @Override // io.netty.channel.socket.SocketChannelConfig
    OioSocketChannelConfig setKeepAlive(boolean z);

    @Override // io.netty.channel.socket.SocketChannelConfig, io.netty.channel.ChannelConfig
    @Deprecated
    OioSocketChannelConfig setMaxMessagesPerRead(int i);

    @Override // io.netty.channel.socket.SocketChannelConfig, io.netty.channel.ChannelConfig
    OioSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator);

    @Override // io.netty.channel.socket.SocketChannelConfig
    OioSocketChannelConfig setPerformancePreferences(int i, int i2, int i3);

    @Override // io.netty.channel.socket.SocketChannelConfig
    OioSocketChannelConfig setReceiveBufferSize(int i);

    @Override // io.netty.channel.socket.SocketChannelConfig, io.netty.channel.ChannelConfig
    OioSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator);

    @Override // io.netty.channel.socket.SocketChannelConfig
    OioSocketChannelConfig setReuseAddress(boolean z);

    @Override // io.netty.channel.socket.SocketChannelConfig
    OioSocketChannelConfig setSendBufferSize(int i);

    @Override // io.netty.channel.socket.SocketChannelConfig
    OioSocketChannelConfig setSoLinger(int i);

    OioSocketChannelConfig setSoTimeout(int i);

    @Override // io.netty.channel.socket.SocketChannelConfig
    OioSocketChannelConfig setTcpNoDelay(boolean z);

    @Override // io.netty.channel.socket.SocketChannelConfig
    OioSocketChannelConfig setTrafficClass(int i);

    @Override // io.netty.channel.ChannelConfig
    @Deprecated
    OioSocketChannelConfig setWriteBufferHighWaterMark(int i);

    @Override // io.netty.channel.ChannelConfig
    @Deprecated
    OioSocketChannelConfig setWriteBufferLowWaterMark(int i);

    @Override // io.netty.channel.socket.SocketChannelConfig, io.netty.channel.ChannelConfig
    OioSocketChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark);

    @Override // io.netty.channel.socket.SocketChannelConfig, io.netty.channel.ChannelConfig
    OioSocketChannelConfig setWriteSpinCount(int i);
}
