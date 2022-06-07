package io.netty.channel.socket.oio;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelOption;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.channel.socket.DefaultServerSocketChannelConfig;
import io.netty.channel.socket.ServerSocketChannel;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Map;

/* loaded from: classes4.dex */
public class DefaultOioServerSocketChannelConfig extends DefaultServerSocketChannelConfig implements OioServerSocketChannelConfig {
    @Deprecated
    public DefaultOioServerSocketChannelConfig(ServerSocketChannel serverSocketChannel, ServerSocket serverSocket) {
        super(serverSocketChannel, serverSocket);
    }

    public DefaultOioServerSocketChannelConfig(OioServerSocketChannel oioServerSocketChannel, ServerSocket serverSocket) {
        super(oioServerSocketChannel, serverSocket);
    }

    @Override // io.netty.channel.socket.DefaultServerSocketChannelConfig, io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public Map<ChannelOption<?>, Object> getOptions() {
        return getOptions(super.getOptions(), ChannelOption.SO_TIMEOUT);
    }

    @Override // io.netty.channel.socket.DefaultServerSocketChannelConfig, io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public <T> T getOption(ChannelOption<T> channelOption) {
        if (channelOption == ChannelOption.SO_TIMEOUT) {
            return (T) Integer.valueOf(getSoTimeout());
        }
        return (T) super.getOption(channelOption);
    }

    @Override // io.netty.channel.socket.DefaultServerSocketChannelConfig, io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public <T> boolean setOption(ChannelOption<T> channelOption, T t) {
        validate(channelOption, t);
        if (channelOption != ChannelOption.SO_TIMEOUT) {
            return super.setOption(channelOption, t);
        }
        setSoTimeout(((Integer) t).intValue());
        return true;
    }

    @Override // io.netty.channel.socket.oio.OioServerSocketChannelConfig
    public OioServerSocketChannelConfig setSoTimeout(int i) {
        try {
            this.javaSocket.setSoTimeout(i);
            return this;
        } catch (IOException e) {
            throw new ChannelException(e);
        }
    }

    @Override // io.netty.channel.socket.oio.OioServerSocketChannelConfig
    public int getSoTimeout() {
        try {
            return this.javaSocket.getSoTimeout();
        } catch (IOException e) {
            throw new ChannelException(e);
        }
    }

    @Override // io.netty.channel.socket.DefaultServerSocketChannelConfig, io.netty.channel.socket.ServerSocketChannelConfig
    public OioServerSocketChannelConfig setBacklog(int i) {
        super.setBacklog(i);
        return this;
    }

    @Override // io.netty.channel.socket.DefaultServerSocketChannelConfig, io.netty.channel.socket.ServerSocketChannelConfig
    public OioServerSocketChannelConfig setReuseAddress(boolean z) {
        super.setReuseAddress(z);
        return this;
    }

    @Override // io.netty.channel.socket.DefaultServerSocketChannelConfig, io.netty.channel.socket.ServerSocketChannelConfig
    public OioServerSocketChannelConfig setReceiveBufferSize(int i) {
        super.setReceiveBufferSize(i);
        return this;
    }

    @Override // io.netty.channel.socket.DefaultServerSocketChannelConfig, io.netty.channel.socket.ServerSocketChannelConfig
    public OioServerSocketChannelConfig setPerformancePreferences(int i, int i2, int i3) {
        super.setPerformancePreferences(i, i2, i3);
        return this;
    }

    @Override // io.netty.channel.socket.DefaultServerSocketChannelConfig, io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public OioServerSocketChannelConfig setConnectTimeoutMillis(int i) {
        super.setConnectTimeoutMillis(i);
        return this;
    }

    @Override // io.netty.channel.socket.DefaultServerSocketChannelConfig, io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    @Deprecated
    public OioServerSocketChannelConfig setMaxMessagesPerRead(int i) {
        super.setMaxMessagesPerRead(i);
        return this;
    }

    @Override // io.netty.channel.socket.DefaultServerSocketChannelConfig, io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public OioServerSocketChannelConfig setWriteSpinCount(int i) {
        super.setWriteSpinCount(i);
        return this;
    }

    @Override // io.netty.channel.socket.DefaultServerSocketChannelConfig, io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public OioServerSocketChannelConfig setAllocator(ByteBufAllocator byteBufAllocator) {
        super.setAllocator(byteBufAllocator);
        return this;
    }

    @Override // io.netty.channel.socket.DefaultServerSocketChannelConfig, io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public OioServerSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator) {
        super.setRecvByteBufAllocator(recvByteBufAllocator);
        return this;
    }

    @Override // io.netty.channel.socket.DefaultServerSocketChannelConfig, io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public OioServerSocketChannelConfig setAutoRead(boolean z) {
        super.setAutoRead(z);
        return this;
    }

    @Override // io.netty.channel.DefaultChannelConfig
    protected void autoReadCleared() {
        if (this.channel instanceof OioServerSocketChannel) {
            ((OioServerSocketChannel) this.channel).b();
        }
    }

    @Override // io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public OioServerSocketChannelConfig setAutoClose(boolean z) {
        super.setAutoClose(z);
        return this;
    }

    @Override // io.netty.channel.socket.DefaultServerSocketChannelConfig, io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    @Deprecated
    public OioServerSocketChannelConfig setWriteBufferHighWaterMark(int i) {
        super.setWriteBufferHighWaterMark(i);
        return this;
    }

    @Override // io.netty.channel.socket.DefaultServerSocketChannelConfig, io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    @Deprecated
    public OioServerSocketChannelConfig setWriteBufferLowWaterMark(int i) {
        super.setWriteBufferLowWaterMark(i);
        return this;
    }

    @Override // io.netty.channel.socket.DefaultServerSocketChannelConfig, io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public OioServerSocketChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
        super.setWriteBufferWaterMark(writeBufferWaterMark);
        return this;
    }

    @Override // io.netty.channel.socket.DefaultServerSocketChannelConfig, io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public OioServerSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator) {
        super.setMessageSizeEstimator(messageSizeEstimator);
        return this;
    }
}
