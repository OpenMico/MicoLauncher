package io.netty.channel.epoll;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelOption;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.channel.socket.ServerSocketChannelConfig;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Map;

/* loaded from: classes4.dex */
public final class EpollServerSocketChannelConfig extends EpollServerChannelConfig implements ServerSocketChannelConfig {
    @Override // io.netty.channel.socket.ServerSocketChannelConfig
    public EpollServerSocketChannelConfig setPerformancePreferences(int i, int i2, int i3) {
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public EpollServerSocketChannelConfig(EpollServerSocketChannel epollServerSocketChannel) {
        super(epollServerSocketChannel);
        setReuseAddress(true);
    }

    @Override // io.netty.channel.epoll.EpollServerChannelConfig, io.netty.channel.epoll.EpollChannelConfig, io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public Map<ChannelOption<?>, Object> getOptions() {
        return getOptions(super.getOptions(), EpollChannelOption.SO_REUSEPORT, EpollChannelOption.IP_FREEBIND, EpollChannelOption.TCP_DEFER_ACCEPT);
    }

    @Override // io.netty.channel.epoll.EpollServerChannelConfig, io.netty.channel.epoll.EpollChannelConfig, io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public <T> T getOption(ChannelOption<T> channelOption) {
        if (channelOption == EpollChannelOption.SO_REUSEPORT) {
            return (T) Boolean.valueOf(isReusePort());
        }
        if (channelOption == EpollChannelOption.IP_FREEBIND) {
            return (T) Boolean.valueOf(isFreeBind());
        }
        if (channelOption == EpollChannelOption.TCP_DEFER_ACCEPT) {
            return (T) Integer.valueOf(getTcpDeferAccept());
        }
        return (T) super.getOption(channelOption);
    }

    @Override // io.netty.channel.epoll.EpollServerChannelConfig, io.netty.channel.epoll.EpollChannelConfig, io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public <T> boolean setOption(ChannelOption<T> channelOption, T t) {
        validate(channelOption, t);
        if (channelOption == EpollChannelOption.SO_REUSEPORT) {
            setReusePort(((Boolean) t).booleanValue());
            return true;
        } else if (channelOption == EpollChannelOption.IP_FREEBIND) {
            setFreeBind(((Boolean) t).booleanValue());
            return true;
        } else if (channelOption == EpollChannelOption.TCP_MD5SIG) {
            setTcpMd5Sig((Map) t);
            return true;
        } else if (channelOption != EpollChannelOption.TCP_DEFER_ACCEPT) {
            return super.setOption(channelOption, t);
        } else {
            setTcpDeferAccept(((Integer) t).intValue());
            return true;
        }
    }

    @Override // io.netty.channel.epoll.EpollServerChannelConfig, io.netty.channel.socket.ServerSocketChannelConfig
    public EpollServerSocketChannelConfig setReuseAddress(boolean z) {
        super.setReuseAddress(z);
        return this;
    }

    @Override // io.netty.channel.epoll.EpollServerChannelConfig, io.netty.channel.socket.ServerSocketChannelConfig
    public EpollServerSocketChannelConfig setReceiveBufferSize(int i) {
        super.setReceiveBufferSize(i);
        return this;
    }

    @Override // io.netty.channel.epoll.EpollServerChannelConfig, io.netty.channel.socket.ServerSocketChannelConfig
    public EpollServerSocketChannelConfig setBacklog(int i) {
        super.setBacklog(i);
        return this;
    }

    @Override // io.netty.channel.epoll.EpollServerChannelConfig, io.netty.channel.epoll.EpollChannelConfig, io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public EpollServerSocketChannelConfig setConnectTimeoutMillis(int i) {
        super.setConnectTimeoutMillis(i);
        return this;
    }

    @Override // io.netty.channel.epoll.EpollServerChannelConfig, io.netty.channel.epoll.EpollChannelConfig, io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    @Deprecated
    public EpollServerSocketChannelConfig setMaxMessagesPerRead(int i) {
        super.setMaxMessagesPerRead(i);
        return this;
    }

    @Override // io.netty.channel.epoll.EpollServerChannelConfig, io.netty.channel.epoll.EpollChannelConfig, io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public EpollServerSocketChannelConfig setWriteSpinCount(int i) {
        super.setWriteSpinCount(i);
        return this;
    }

    @Override // io.netty.channel.epoll.EpollServerChannelConfig, io.netty.channel.epoll.EpollChannelConfig, io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public EpollServerSocketChannelConfig setAllocator(ByteBufAllocator byteBufAllocator) {
        super.setAllocator(byteBufAllocator);
        return this;
    }

    @Override // io.netty.channel.epoll.EpollServerChannelConfig, io.netty.channel.epoll.EpollChannelConfig, io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public EpollServerSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator) {
        super.setRecvByteBufAllocator(recvByteBufAllocator);
        return this;
    }

    @Override // io.netty.channel.epoll.EpollServerChannelConfig, io.netty.channel.epoll.EpollChannelConfig, io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public EpollServerSocketChannelConfig setAutoRead(boolean z) {
        super.setAutoRead(z);
        return this;
    }

    @Override // io.netty.channel.epoll.EpollServerChannelConfig, io.netty.channel.epoll.EpollChannelConfig, io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    @Deprecated
    public EpollServerSocketChannelConfig setWriteBufferHighWaterMark(int i) {
        super.setWriteBufferHighWaterMark(i);
        return this;
    }

    @Override // io.netty.channel.epoll.EpollServerChannelConfig, io.netty.channel.epoll.EpollChannelConfig, io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    @Deprecated
    public EpollServerSocketChannelConfig setWriteBufferLowWaterMark(int i) {
        super.setWriteBufferLowWaterMark(i);
        return this;
    }

    @Override // io.netty.channel.epoll.EpollServerChannelConfig, io.netty.channel.epoll.EpollChannelConfig, io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public EpollServerSocketChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
        super.setWriteBufferWaterMark(writeBufferWaterMark);
        return this;
    }

    @Override // io.netty.channel.epoll.EpollServerChannelConfig, io.netty.channel.epoll.EpollChannelConfig, io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public EpollServerSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator) {
        super.setMessageSizeEstimator(messageSizeEstimator);
        return this;
    }

    public EpollServerSocketChannelConfig setTcpMd5Sig(Map<InetAddress, byte[]> map) {
        try {
            ((EpollServerSocketChannel) this.channel).a(map);
            return this;
        } catch (IOException e) {
            throw new ChannelException(e);
        }
    }

    public boolean isReusePort() {
        try {
            return Native.isReusePort(this.channel.fd().intValue()) == 1;
        } catch (IOException e) {
            throw new ChannelException(e);
        }
    }

    public EpollServerSocketChannelConfig setReusePort(boolean z) {
        try {
            Native.setReusePort(this.channel.fd().intValue(), z ? 1 : 0);
            return this;
        } catch (IOException e) {
            throw new ChannelException(e);
        }
    }

    public boolean isFreeBind() {
        try {
            return Native.isIpFreeBind(this.channel.fd().intValue()) != 0;
        } catch (IOException e) {
            throw new ChannelException(e);
        }
    }

    public EpollServerSocketChannelConfig setFreeBind(boolean z) {
        try {
            Native.setIpFreeBind(this.channel.fd().intValue(), z ? 1 : 0);
            return this;
        } catch (IOException e) {
            throw new ChannelException(e);
        }
    }

    public EpollServerSocketChannelConfig setTcpDeferAccept(int i) {
        try {
            this.channel.fd().setTcpDeferAccept(i);
            return this;
        } catch (IOException e) {
            throw new ChannelException(e);
        }
    }

    public int getTcpDeferAccept() {
        try {
            return this.channel.fd().getTcpDeferAccept();
        } catch (IOException e) {
            throw new ChannelException(e);
        }
    }
}
