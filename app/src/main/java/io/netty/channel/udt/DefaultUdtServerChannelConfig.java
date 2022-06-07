package io.netty.channel.udt;

import com.barchart.udt.nio.ChannelUDT;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelOption;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;
import java.io.IOException;
import java.util.Map;

/* loaded from: classes4.dex */
public class DefaultUdtServerChannelConfig extends DefaultUdtChannelConfig implements UdtServerChannelConfig {
    private volatile int a = 64;

    @Override // io.netty.channel.udt.DefaultUdtChannelConfig
    protected void apply(ChannelUDT channelUDT) throws IOException {
    }

    public DefaultUdtServerChannelConfig(UdtChannel udtChannel, ChannelUDT channelUDT, boolean z) throws IOException {
        super(udtChannel, channelUDT, z);
        if (z) {
            apply(channelUDT);
        }
    }

    @Override // io.netty.channel.udt.UdtServerChannelConfig
    public int getBacklog() {
        return this.a;
    }

    @Override // io.netty.channel.udt.DefaultUdtChannelConfig, io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public <T> T getOption(ChannelOption<T> channelOption) {
        if (channelOption == ChannelOption.SO_BACKLOG) {
            return (T) Integer.valueOf(getBacklog());
        }
        return (T) super.getOption(channelOption);
    }

    @Override // io.netty.channel.udt.DefaultUdtChannelConfig, io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public Map<ChannelOption<?>, Object> getOptions() {
        return getOptions(super.getOptions(), ChannelOption.SO_BACKLOG);
    }

    @Override // io.netty.channel.udt.UdtServerChannelConfig
    public UdtServerChannelConfig setBacklog(int i) {
        this.a = i;
        return this;
    }

    @Override // io.netty.channel.udt.DefaultUdtChannelConfig, io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public <T> boolean setOption(ChannelOption<T> channelOption, T t) {
        validate(channelOption, t);
        if (channelOption != ChannelOption.SO_BACKLOG) {
            return super.setOption(channelOption, t);
        }
        setBacklog(((Integer) t).intValue());
        return true;
    }

    @Override // io.netty.channel.udt.DefaultUdtChannelConfig, io.netty.channel.udt.UdtChannelConfig
    public UdtServerChannelConfig setProtocolReceiveBufferSize(int i) {
        super.setProtocolReceiveBufferSize(i);
        return this;
    }

    @Override // io.netty.channel.udt.DefaultUdtChannelConfig, io.netty.channel.udt.UdtChannelConfig
    public UdtServerChannelConfig setProtocolSendBufferSize(int i) {
        super.setProtocolSendBufferSize(i);
        return this;
    }

    @Override // io.netty.channel.udt.DefaultUdtChannelConfig, io.netty.channel.udt.UdtChannelConfig
    public UdtServerChannelConfig setReceiveBufferSize(int i) {
        super.setReceiveBufferSize(i);
        return this;
    }

    @Override // io.netty.channel.udt.DefaultUdtChannelConfig, io.netty.channel.udt.UdtChannelConfig
    public UdtServerChannelConfig setReuseAddress(boolean z) {
        super.setReuseAddress(z);
        return this;
    }

    @Override // io.netty.channel.udt.DefaultUdtChannelConfig, io.netty.channel.udt.UdtChannelConfig
    public UdtServerChannelConfig setSendBufferSize(int i) {
        super.setSendBufferSize(i);
        return this;
    }

    @Override // io.netty.channel.udt.DefaultUdtChannelConfig, io.netty.channel.udt.UdtChannelConfig
    public UdtServerChannelConfig setSoLinger(int i) {
        super.setSoLinger(i);
        return this;
    }

    @Override // io.netty.channel.udt.DefaultUdtChannelConfig, io.netty.channel.udt.UdtChannelConfig
    public UdtServerChannelConfig setSystemReceiveBufferSize(int i) {
        super.setSystemReceiveBufferSize(i);
        return this;
    }

    @Override // io.netty.channel.udt.DefaultUdtChannelConfig, io.netty.channel.udt.UdtChannelConfig
    public UdtServerChannelConfig setSystemSendBufferSize(int i) {
        super.setSystemSendBufferSize(i);
        return this;
    }

    @Override // io.netty.channel.udt.DefaultUdtChannelConfig, io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public UdtServerChannelConfig setConnectTimeoutMillis(int i) {
        super.setConnectTimeoutMillis(i);
        return this;
    }

    @Override // io.netty.channel.udt.DefaultUdtChannelConfig, io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    @Deprecated
    public UdtServerChannelConfig setMaxMessagesPerRead(int i) {
        super.setMaxMessagesPerRead(i);
        return this;
    }

    @Override // io.netty.channel.udt.DefaultUdtChannelConfig, io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public UdtServerChannelConfig setWriteSpinCount(int i) {
        super.setWriteSpinCount(i);
        return this;
    }

    @Override // io.netty.channel.udt.DefaultUdtChannelConfig, io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public UdtServerChannelConfig setAllocator(ByteBufAllocator byteBufAllocator) {
        super.setAllocator(byteBufAllocator);
        return this;
    }

    @Override // io.netty.channel.udt.DefaultUdtChannelConfig, io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public UdtServerChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator) {
        super.setRecvByteBufAllocator(recvByteBufAllocator);
        return this;
    }

    @Override // io.netty.channel.udt.DefaultUdtChannelConfig, io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public UdtServerChannelConfig setAutoRead(boolean z) {
        super.setAutoRead(z);
        return this;
    }

    @Override // io.netty.channel.udt.DefaultUdtChannelConfig, io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public UdtServerChannelConfig setAutoClose(boolean z) {
        super.setAutoClose(z);
        return this;
    }

    @Override // io.netty.channel.udt.DefaultUdtChannelConfig, io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    @Deprecated
    public UdtServerChannelConfig setWriteBufferLowWaterMark(int i) {
        super.setWriteBufferLowWaterMark(i);
        return this;
    }

    @Override // io.netty.channel.udt.DefaultUdtChannelConfig, io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    @Deprecated
    public UdtServerChannelConfig setWriteBufferHighWaterMark(int i) {
        super.setWriteBufferHighWaterMark(i);
        return this;
    }

    @Override // io.netty.channel.udt.DefaultUdtChannelConfig, io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public UdtServerChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
        super.setWriteBufferWaterMark(writeBufferWaterMark);
        return this;
    }

    @Override // io.netty.channel.udt.DefaultUdtChannelConfig, io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public UdtServerChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator) {
        super.setMessageSizeEstimator(messageSizeEstimator);
        return this;
    }
}
