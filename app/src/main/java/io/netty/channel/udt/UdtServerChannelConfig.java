package io.netty.channel.udt;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;

/* loaded from: classes4.dex */
public interface UdtServerChannelConfig extends UdtChannelConfig {
    int getBacklog();

    @Override // io.netty.channel.udt.UdtChannelConfig, io.netty.channel.ChannelConfig
    UdtServerChannelConfig setAllocator(ByteBufAllocator byteBufAllocator);

    @Override // io.netty.channel.udt.UdtChannelConfig, io.netty.channel.ChannelConfig
    UdtServerChannelConfig setAutoClose(boolean z);

    @Override // io.netty.channel.udt.UdtChannelConfig, io.netty.channel.ChannelConfig
    UdtServerChannelConfig setAutoRead(boolean z);

    UdtServerChannelConfig setBacklog(int i);

    @Override // io.netty.channel.udt.UdtChannelConfig, io.netty.channel.ChannelConfig
    UdtServerChannelConfig setConnectTimeoutMillis(int i);

    @Override // io.netty.channel.udt.UdtChannelConfig, io.netty.channel.ChannelConfig
    @Deprecated
    UdtServerChannelConfig setMaxMessagesPerRead(int i);

    @Override // io.netty.channel.udt.UdtChannelConfig, io.netty.channel.ChannelConfig
    UdtServerChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator);

    @Override // io.netty.channel.udt.UdtChannelConfig
    UdtServerChannelConfig setProtocolReceiveBufferSize(int i);

    @Override // io.netty.channel.udt.UdtChannelConfig
    UdtServerChannelConfig setProtocolSendBufferSize(int i);

    @Override // io.netty.channel.udt.UdtChannelConfig
    UdtServerChannelConfig setReceiveBufferSize(int i);

    @Override // io.netty.channel.udt.UdtChannelConfig, io.netty.channel.ChannelConfig
    UdtServerChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator);

    @Override // io.netty.channel.udt.UdtChannelConfig
    UdtServerChannelConfig setReuseAddress(boolean z);

    @Override // io.netty.channel.udt.UdtChannelConfig
    UdtServerChannelConfig setSendBufferSize(int i);

    @Override // io.netty.channel.udt.UdtChannelConfig
    UdtServerChannelConfig setSoLinger(int i);

    @Override // io.netty.channel.udt.UdtChannelConfig
    UdtServerChannelConfig setSystemReceiveBufferSize(int i);

    @Override // io.netty.channel.udt.UdtChannelConfig
    UdtServerChannelConfig setSystemSendBufferSize(int i);

    @Override // io.netty.channel.udt.UdtChannelConfig, io.netty.channel.ChannelConfig
    @Deprecated
    UdtServerChannelConfig setWriteBufferHighWaterMark(int i);

    @Override // io.netty.channel.udt.UdtChannelConfig, io.netty.channel.ChannelConfig
    @Deprecated
    UdtServerChannelConfig setWriteBufferLowWaterMark(int i);

    @Override // io.netty.channel.udt.UdtChannelConfig, io.netty.channel.ChannelConfig
    UdtServerChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark);

    @Override // io.netty.channel.udt.UdtChannelConfig, io.netty.channel.ChannelConfig
    UdtServerChannelConfig setWriteSpinCount(int i);
}
