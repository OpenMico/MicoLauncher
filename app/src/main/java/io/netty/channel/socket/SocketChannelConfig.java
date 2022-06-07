package io.netty.channel.socket;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelConfig;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;

/* loaded from: classes4.dex */
public interface SocketChannelConfig extends ChannelConfig {
    int getReceiveBufferSize();

    int getSendBufferSize();

    int getSoLinger();

    int getTrafficClass();

    boolean isAllowHalfClosure();

    boolean isKeepAlive();

    boolean isReuseAddress();

    boolean isTcpNoDelay();

    @Override // io.netty.channel.ChannelConfig
    SocketChannelConfig setAllocator(ByteBufAllocator byteBufAllocator);

    SocketChannelConfig setAllowHalfClosure(boolean z);

    @Override // io.netty.channel.ChannelConfig
    SocketChannelConfig setAutoClose(boolean z);

    @Override // io.netty.channel.ChannelConfig
    SocketChannelConfig setAutoRead(boolean z);

    @Override // io.netty.channel.ChannelConfig
    SocketChannelConfig setConnectTimeoutMillis(int i);

    SocketChannelConfig setKeepAlive(boolean z);

    @Override // io.netty.channel.ChannelConfig
    @Deprecated
    SocketChannelConfig setMaxMessagesPerRead(int i);

    @Override // io.netty.channel.ChannelConfig
    SocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator);

    SocketChannelConfig setPerformancePreferences(int i, int i2, int i3);

    SocketChannelConfig setReceiveBufferSize(int i);

    @Override // io.netty.channel.ChannelConfig
    SocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator);

    SocketChannelConfig setReuseAddress(boolean z);

    SocketChannelConfig setSendBufferSize(int i);

    SocketChannelConfig setSoLinger(int i);

    SocketChannelConfig setTcpNoDelay(boolean z);

    SocketChannelConfig setTrafficClass(int i);

    @Override // io.netty.channel.ChannelConfig
    SocketChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark);

    @Override // io.netty.channel.ChannelConfig
    SocketChannelConfig setWriteSpinCount(int i);
}
