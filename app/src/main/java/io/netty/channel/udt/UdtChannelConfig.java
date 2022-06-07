package io.netty.channel.udt;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelConfig;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;

/* loaded from: classes4.dex */
public interface UdtChannelConfig extends ChannelConfig {
    int getProtocolReceiveBufferSize();

    int getProtocolSendBufferSize();

    int getReceiveBufferSize();

    int getSendBufferSize();

    int getSoLinger();

    int getSystemReceiveBufferSize();

    int getSystemSendBufferSize();

    boolean isReuseAddress();

    @Override // io.netty.channel.ChannelConfig
    UdtChannelConfig setAllocator(ByteBufAllocator byteBufAllocator);

    @Override // io.netty.channel.ChannelConfig
    UdtChannelConfig setAutoClose(boolean z);

    @Override // io.netty.channel.ChannelConfig
    UdtChannelConfig setAutoRead(boolean z);

    @Override // io.netty.channel.ChannelConfig
    UdtChannelConfig setConnectTimeoutMillis(int i);

    @Override // io.netty.channel.ChannelConfig
    @Deprecated
    UdtChannelConfig setMaxMessagesPerRead(int i);

    @Override // io.netty.channel.ChannelConfig
    UdtChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator);

    UdtChannelConfig setProtocolReceiveBufferSize(int i);

    UdtChannelConfig setProtocolSendBufferSize(int i);

    UdtChannelConfig setReceiveBufferSize(int i);

    @Override // io.netty.channel.ChannelConfig
    UdtChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator);

    UdtChannelConfig setReuseAddress(boolean z);

    UdtChannelConfig setSendBufferSize(int i);

    UdtChannelConfig setSoLinger(int i);

    UdtChannelConfig setSystemReceiveBufferSize(int i);

    UdtChannelConfig setSystemSendBufferSize(int i);

    @Override // io.netty.channel.ChannelConfig
    @Deprecated
    UdtChannelConfig setWriteBufferHighWaterMark(int i);

    @Override // io.netty.channel.ChannelConfig
    @Deprecated
    UdtChannelConfig setWriteBufferLowWaterMark(int i);

    @Override // io.netty.channel.ChannelConfig
    UdtChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark);

    @Override // io.netty.channel.ChannelConfig
    UdtChannelConfig setWriteSpinCount(int i);
}
