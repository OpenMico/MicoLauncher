package io.netty.channel;

import io.netty.buffer.ByteBufAllocator;
import java.util.Map;

/* loaded from: classes4.dex */
public interface ChannelConfig {
    ByteBufAllocator getAllocator();

    int getConnectTimeoutMillis();

    @Deprecated
    int getMaxMessagesPerRead();

    MessageSizeEstimator getMessageSizeEstimator();

    <T> T getOption(ChannelOption<T> channelOption);

    Map<ChannelOption<?>, Object> getOptions();

    <T extends RecvByteBufAllocator> T getRecvByteBufAllocator();

    int getWriteBufferHighWaterMark();

    int getWriteBufferLowWaterMark();

    WriteBufferWaterMark getWriteBufferWaterMark();

    int getWriteSpinCount();

    @Deprecated
    boolean isAutoClose();

    boolean isAutoRead();

    ChannelConfig setAllocator(ByteBufAllocator byteBufAllocator);

    @Deprecated
    ChannelConfig setAutoClose(boolean z);

    ChannelConfig setAutoRead(boolean z);

    ChannelConfig setConnectTimeoutMillis(int i);

    @Deprecated
    ChannelConfig setMaxMessagesPerRead(int i);

    ChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator);

    <T> boolean setOption(ChannelOption<T> channelOption, T t);

    boolean setOptions(Map<ChannelOption<?>, ?> map);

    ChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator);

    @Deprecated
    ChannelConfig setWriteBufferHighWaterMark(int i);

    @Deprecated
    ChannelConfig setWriteBufferLowWaterMark(int i);

    ChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark);

    ChannelConfig setWriteSpinCount(int i);
}
