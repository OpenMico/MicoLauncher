package io.netty.channel.sctp;

import com.sun.nio.sctp.SctpStandardSocketOptions;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelConfig;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;

/* loaded from: classes4.dex */
public interface SctpChannelConfig extends ChannelConfig {
    SctpStandardSocketOptions.InitMaxStreams getInitMaxStreams();

    int getReceiveBufferSize();

    int getSendBufferSize();

    boolean isSctpNoDelay();

    @Override // io.netty.channel.ChannelConfig
    SctpChannelConfig setAllocator(ByteBufAllocator byteBufAllocator);

    @Override // io.netty.channel.ChannelConfig
    SctpChannelConfig setAutoClose(boolean z);

    @Override // io.netty.channel.ChannelConfig
    SctpChannelConfig setAutoRead(boolean z);

    @Override // io.netty.channel.ChannelConfig
    SctpChannelConfig setConnectTimeoutMillis(int i);

    SctpChannelConfig setInitMaxStreams(SctpStandardSocketOptions.InitMaxStreams initMaxStreams);

    @Override // io.netty.channel.ChannelConfig
    @Deprecated
    SctpChannelConfig setMaxMessagesPerRead(int i);

    @Override // io.netty.channel.ChannelConfig
    SctpChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator);

    SctpChannelConfig setReceiveBufferSize(int i);

    @Override // io.netty.channel.ChannelConfig
    SctpChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator);

    SctpChannelConfig setSctpNoDelay(boolean z);

    SctpChannelConfig setSendBufferSize(int i);

    @Override // io.netty.channel.ChannelConfig
    @Deprecated
    SctpChannelConfig setWriteBufferHighWaterMark(int i);

    @Override // io.netty.channel.ChannelConfig
    @Deprecated
    SctpChannelConfig setWriteBufferLowWaterMark(int i);

    @Override // io.netty.channel.ChannelConfig
    SctpChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark);

    @Override // io.netty.channel.ChannelConfig
    SctpChannelConfig setWriteSpinCount(int i);
}
