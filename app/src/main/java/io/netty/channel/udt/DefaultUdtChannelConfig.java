package io.netty.channel.udt;

import com.barchart.udt.OptionUDT;
import com.barchart.udt.SocketUDT;
import com.barchart.udt.nio.ChannelUDT;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelOption;
import io.netty.channel.DefaultChannelConfig;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;
import java.io.IOException;
import java.util.Map;

/* loaded from: classes4.dex */
public class DefaultUdtChannelConfig extends DefaultChannelConfig implements UdtChannelConfig {
    private volatile int g;
    private volatile int a = 10485760;
    private volatile int b = 10485760;
    private volatile int c = 1048576;
    private volatile int d = 1048576;
    private volatile int e = 131072;
    private volatile int f = 131072;
    private volatile boolean h = true;

    public DefaultUdtChannelConfig(UdtChannel udtChannel, ChannelUDT channelUDT, boolean z) throws IOException {
        super(udtChannel);
        if (z) {
            apply(channelUDT);
        }
    }

    protected void apply(ChannelUDT channelUDT) throws IOException {
        SocketUDT socketUDT = channelUDT.socketUDT();
        socketUDT.setReuseAddress(isReuseAddress());
        socketUDT.setSendBufferSize(getSendBufferSize());
        if (getSoLinger() <= 0) {
            socketUDT.setSoLinger(false, 0);
        } else {
            socketUDT.setSoLinger(true, getSoLinger());
        }
        socketUDT.setOption(OptionUDT.Protocol_Receive_Buffer_Size, Integer.valueOf(getProtocolReceiveBufferSize()));
        socketUDT.setOption(OptionUDT.Protocol_Send_Buffer_Size, Integer.valueOf(getProtocolSendBufferSize()));
        socketUDT.setOption(OptionUDT.System_Receive_Buffer_Size, Integer.valueOf(getSystemReceiveBufferSize()));
        socketUDT.setOption(OptionUDT.System_Send_Buffer_Size, Integer.valueOf(getSystemSendBufferSize()));
    }

    @Override // io.netty.channel.udt.UdtChannelConfig
    public int getProtocolReceiveBufferSize() {
        return this.a;
    }

    @Override // io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public <T> T getOption(ChannelOption<T> channelOption) {
        if (channelOption == UdtChannelOption.PROTOCOL_RECEIVE_BUFFER_SIZE) {
            return (T) Integer.valueOf(getProtocolReceiveBufferSize());
        }
        if (channelOption == UdtChannelOption.PROTOCOL_SEND_BUFFER_SIZE) {
            return (T) Integer.valueOf(getProtocolSendBufferSize());
        }
        if (channelOption == UdtChannelOption.SYSTEM_RECEIVE_BUFFER_SIZE) {
            return (T) Integer.valueOf(getSystemReceiveBufferSize());
        }
        if (channelOption == UdtChannelOption.SYSTEM_SEND_BUFFER_SIZE) {
            return (T) Integer.valueOf(getSystemSendBufferSize());
        }
        if (channelOption == ChannelOption.SO_RCVBUF) {
            return (T) Integer.valueOf(getReceiveBufferSize());
        }
        if (channelOption == ChannelOption.SO_SNDBUF) {
            return (T) Integer.valueOf(getSendBufferSize());
        }
        if (channelOption == ChannelOption.SO_REUSEADDR) {
            return (T) Boolean.valueOf(isReuseAddress());
        }
        if (channelOption == ChannelOption.SO_LINGER) {
            return (T) Integer.valueOf(getSoLinger());
        }
        return (T) super.getOption(channelOption);
    }

    @Override // io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public Map<ChannelOption<?>, Object> getOptions() {
        return getOptions(super.getOptions(), UdtChannelOption.PROTOCOL_RECEIVE_BUFFER_SIZE, UdtChannelOption.PROTOCOL_SEND_BUFFER_SIZE, UdtChannelOption.SYSTEM_RECEIVE_BUFFER_SIZE, UdtChannelOption.SYSTEM_SEND_BUFFER_SIZE, ChannelOption.SO_RCVBUF, ChannelOption.SO_SNDBUF, ChannelOption.SO_REUSEADDR, ChannelOption.SO_LINGER);
    }

    @Override // io.netty.channel.udt.UdtChannelConfig
    public int getReceiveBufferSize() {
        return this.e;
    }

    @Override // io.netty.channel.udt.UdtChannelConfig
    public int getSendBufferSize() {
        return this.f;
    }

    @Override // io.netty.channel.udt.UdtChannelConfig
    public int getSoLinger() {
        return this.g;
    }

    @Override // io.netty.channel.udt.UdtChannelConfig
    public boolean isReuseAddress() {
        return this.h;
    }

    @Override // io.netty.channel.udt.UdtChannelConfig
    public UdtChannelConfig setProtocolReceiveBufferSize(int i) {
        this.a = i;
        return this;
    }

    @Override // io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public <T> boolean setOption(ChannelOption<T> channelOption, T t) {
        validate(channelOption, t);
        if (channelOption == UdtChannelOption.PROTOCOL_RECEIVE_BUFFER_SIZE) {
            setProtocolReceiveBufferSize(((Integer) t).intValue());
            return true;
        } else if (channelOption == UdtChannelOption.PROTOCOL_SEND_BUFFER_SIZE) {
            setProtocolSendBufferSize(((Integer) t).intValue());
            return true;
        } else if (channelOption == UdtChannelOption.SYSTEM_RECEIVE_BUFFER_SIZE) {
            setSystemReceiveBufferSize(((Integer) t).intValue());
            return true;
        } else if (channelOption == UdtChannelOption.SYSTEM_SEND_BUFFER_SIZE) {
            setSystemSendBufferSize(((Integer) t).intValue());
            return true;
        } else if (channelOption == ChannelOption.SO_RCVBUF) {
            setReceiveBufferSize(((Integer) t).intValue());
            return true;
        } else if (channelOption == ChannelOption.SO_SNDBUF) {
            setSendBufferSize(((Integer) t).intValue());
            return true;
        } else if (channelOption == ChannelOption.SO_REUSEADDR) {
            setReuseAddress(((Boolean) t).booleanValue());
            return true;
        } else if (channelOption != ChannelOption.SO_LINGER) {
            return super.setOption(channelOption, t);
        } else {
            setSoLinger(((Integer) t).intValue());
            return true;
        }
    }

    @Override // io.netty.channel.udt.UdtChannelConfig
    public UdtChannelConfig setReceiveBufferSize(int i) {
        this.e = i;
        return this;
    }

    @Override // io.netty.channel.udt.UdtChannelConfig
    public UdtChannelConfig setReuseAddress(boolean z) {
        this.h = z;
        return this;
    }

    @Override // io.netty.channel.udt.UdtChannelConfig
    public UdtChannelConfig setSendBufferSize(int i) {
        this.f = i;
        return this;
    }

    @Override // io.netty.channel.udt.UdtChannelConfig
    public UdtChannelConfig setSoLinger(int i) {
        this.g = i;
        return this;
    }

    @Override // io.netty.channel.udt.UdtChannelConfig
    public int getSystemReceiveBufferSize() {
        return this.c;
    }

    @Override // io.netty.channel.udt.UdtChannelConfig
    public UdtChannelConfig setSystemSendBufferSize(int i) {
        this.c = i;
        return this;
    }

    @Override // io.netty.channel.udt.UdtChannelConfig
    public int getProtocolSendBufferSize() {
        return this.b;
    }

    @Override // io.netty.channel.udt.UdtChannelConfig
    public UdtChannelConfig setProtocolSendBufferSize(int i) {
        this.b = i;
        return this;
    }

    @Override // io.netty.channel.udt.UdtChannelConfig
    public UdtChannelConfig setSystemReceiveBufferSize(int i) {
        this.d = i;
        return this;
    }

    @Override // io.netty.channel.udt.UdtChannelConfig
    public int getSystemSendBufferSize() {
        return this.d;
    }

    @Override // io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public UdtChannelConfig setConnectTimeoutMillis(int i) {
        super.setConnectTimeoutMillis(i);
        return this;
    }

    @Override // io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    @Deprecated
    public UdtChannelConfig setMaxMessagesPerRead(int i) {
        super.setMaxMessagesPerRead(i);
        return this;
    }

    @Override // io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public UdtChannelConfig setWriteSpinCount(int i) {
        super.setWriteSpinCount(i);
        return this;
    }

    @Override // io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public UdtChannelConfig setAllocator(ByteBufAllocator byteBufAllocator) {
        super.setAllocator(byteBufAllocator);
        return this;
    }

    @Override // io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public UdtChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator) {
        super.setRecvByteBufAllocator(recvByteBufAllocator);
        return this;
    }

    @Override // io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public UdtChannelConfig setAutoRead(boolean z) {
        super.setAutoRead(z);
        return this;
    }

    @Override // io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public UdtChannelConfig setAutoClose(boolean z) {
        super.setAutoClose(z);
        return this;
    }

    @Override // io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    @Deprecated
    public UdtChannelConfig setWriteBufferLowWaterMark(int i) {
        super.setWriteBufferLowWaterMark(i);
        return this;
    }

    @Override // io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    @Deprecated
    public UdtChannelConfig setWriteBufferHighWaterMark(int i) {
        super.setWriteBufferHighWaterMark(i);
        return this;
    }

    @Override // io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public UdtChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
        super.setWriteBufferWaterMark(writeBufferWaterMark);
        return this;
    }

    @Override // io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public UdtChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator) {
        super.setMessageSizeEstimator(messageSizeEstimator);
        return this;
    }
}
