package io.netty.channel.socket;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelOption;
import io.netty.channel.DefaultChannelConfig;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.util.internal.PlatformDependent;
import java.net.Socket;
import java.net.SocketException;
import java.util.Map;

/* loaded from: classes4.dex */
public class DefaultSocketChannelConfig extends DefaultChannelConfig implements SocketChannelConfig {
    private volatile boolean a;
    protected final Socket javaSocket;

    public DefaultSocketChannelConfig(SocketChannel socketChannel, Socket socket) {
        super(socketChannel);
        if (socket != null) {
            this.javaSocket = socket;
            if (PlatformDependent.canEnableTcpNoDelayByDefault()) {
                try {
                    setTcpNoDelay(true);
                } catch (Exception unused) {
                }
            }
        } else {
            throw new NullPointerException("javaSocket");
        }
    }

    @Override // io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public Map<ChannelOption<?>, Object> getOptions() {
        return getOptions(super.getOptions(), ChannelOption.SO_RCVBUF, ChannelOption.SO_SNDBUF, ChannelOption.TCP_NODELAY, ChannelOption.SO_KEEPALIVE, ChannelOption.SO_REUSEADDR, ChannelOption.SO_LINGER, ChannelOption.IP_TOS, ChannelOption.ALLOW_HALF_CLOSURE);
    }

    @Override // io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public <T> T getOption(ChannelOption<T> channelOption) {
        if (channelOption == ChannelOption.SO_RCVBUF) {
            return (T) Integer.valueOf(getReceiveBufferSize());
        }
        if (channelOption == ChannelOption.SO_SNDBUF) {
            return (T) Integer.valueOf(getSendBufferSize());
        }
        if (channelOption == ChannelOption.TCP_NODELAY) {
            return (T) Boolean.valueOf(isTcpNoDelay());
        }
        if (channelOption == ChannelOption.SO_KEEPALIVE) {
            return (T) Boolean.valueOf(isKeepAlive());
        }
        if (channelOption == ChannelOption.SO_REUSEADDR) {
            return (T) Boolean.valueOf(isReuseAddress());
        }
        if (channelOption == ChannelOption.SO_LINGER) {
            return (T) Integer.valueOf(getSoLinger());
        }
        if (channelOption == ChannelOption.IP_TOS) {
            return (T) Integer.valueOf(getTrafficClass());
        }
        if (channelOption == ChannelOption.ALLOW_HALF_CLOSURE) {
            return (T) Boolean.valueOf(isAllowHalfClosure());
        }
        return (T) super.getOption(channelOption);
    }

    @Override // io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public <T> boolean setOption(ChannelOption<T> channelOption, T t) {
        validate(channelOption, t);
        if (channelOption == ChannelOption.SO_RCVBUF) {
            setReceiveBufferSize(((Integer) t).intValue());
            return true;
        } else if (channelOption == ChannelOption.SO_SNDBUF) {
            setSendBufferSize(((Integer) t).intValue());
            return true;
        } else if (channelOption == ChannelOption.TCP_NODELAY) {
            setTcpNoDelay(((Boolean) t).booleanValue());
            return true;
        } else if (channelOption == ChannelOption.SO_KEEPALIVE) {
            setKeepAlive(((Boolean) t).booleanValue());
            return true;
        } else if (channelOption == ChannelOption.SO_REUSEADDR) {
            setReuseAddress(((Boolean) t).booleanValue());
            return true;
        } else if (channelOption == ChannelOption.SO_LINGER) {
            setSoLinger(((Integer) t).intValue());
            return true;
        } else if (channelOption == ChannelOption.IP_TOS) {
            setTrafficClass(((Integer) t).intValue());
            return true;
        } else if (channelOption != ChannelOption.ALLOW_HALF_CLOSURE) {
            return super.setOption(channelOption, t);
        } else {
            setAllowHalfClosure(((Boolean) t).booleanValue());
            return true;
        }
    }

    @Override // io.netty.channel.socket.SocketChannelConfig
    public int getReceiveBufferSize() {
        try {
            return this.javaSocket.getReceiveBufferSize();
        } catch (SocketException e) {
            throw new ChannelException(e);
        }
    }

    @Override // io.netty.channel.socket.SocketChannelConfig
    public int getSendBufferSize() {
        try {
            return this.javaSocket.getSendBufferSize();
        } catch (SocketException e) {
            throw new ChannelException(e);
        }
    }

    @Override // io.netty.channel.socket.SocketChannelConfig
    public int getSoLinger() {
        try {
            return this.javaSocket.getSoLinger();
        } catch (SocketException e) {
            throw new ChannelException(e);
        }
    }

    @Override // io.netty.channel.socket.SocketChannelConfig
    public int getTrafficClass() {
        try {
            return this.javaSocket.getTrafficClass();
        } catch (SocketException e) {
            throw new ChannelException(e);
        }
    }

    @Override // io.netty.channel.socket.SocketChannelConfig
    public boolean isKeepAlive() {
        try {
            return this.javaSocket.getKeepAlive();
        } catch (SocketException e) {
            throw new ChannelException(e);
        }
    }

    @Override // io.netty.channel.socket.SocketChannelConfig
    public boolean isReuseAddress() {
        try {
            return this.javaSocket.getReuseAddress();
        } catch (SocketException e) {
            throw new ChannelException(e);
        }
    }

    @Override // io.netty.channel.socket.SocketChannelConfig
    public boolean isTcpNoDelay() {
        try {
            return this.javaSocket.getTcpNoDelay();
        } catch (SocketException e) {
            throw new ChannelException(e);
        }
    }

    @Override // io.netty.channel.socket.SocketChannelConfig
    public SocketChannelConfig setKeepAlive(boolean z) {
        try {
            this.javaSocket.setKeepAlive(z);
            return this;
        } catch (SocketException e) {
            throw new ChannelException(e);
        }
    }

    @Override // io.netty.channel.socket.SocketChannelConfig
    public SocketChannelConfig setPerformancePreferences(int i, int i2, int i3) {
        this.javaSocket.setPerformancePreferences(i, i2, i3);
        return this;
    }

    @Override // io.netty.channel.socket.SocketChannelConfig
    public SocketChannelConfig setReceiveBufferSize(int i) {
        try {
            this.javaSocket.setReceiveBufferSize(i);
            return this;
        } catch (SocketException e) {
            throw new ChannelException(e);
        }
    }

    @Override // io.netty.channel.socket.SocketChannelConfig
    public SocketChannelConfig setReuseAddress(boolean z) {
        try {
            this.javaSocket.setReuseAddress(z);
            return this;
        } catch (SocketException e) {
            throw new ChannelException(e);
        }
    }

    @Override // io.netty.channel.socket.SocketChannelConfig
    public SocketChannelConfig setSendBufferSize(int i) {
        try {
            this.javaSocket.setSendBufferSize(i);
            return this;
        } catch (SocketException e) {
            throw new ChannelException(e);
        }
    }

    @Override // io.netty.channel.socket.SocketChannelConfig
    public SocketChannelConfig setSoLinger(int i) {
        try {
            if (i < 0) {
                this.javaSocket.setSoLinger(false, 0);
            } else {
                this.javaSocket.setSoLinger(true, i);
            }
            return this;
        } catch (SocketException e) {
            throw new ChannelException(e);
        }
    }

    @Override // io.netty.channel.socket.SocketChannelConfig
    public SocketChannelConfig setTcpNoDelay(boolean z) {
        try {
            this.javaSocket.setTcpNoDelay(z);
            return this;
        } catch (SocketException e) {
            throw new ChannelException(e);
        }
    }

    @Override // io.netty.channel.socket.SocketChannelConfig
    public SocketChannelConfig setTrafficClass(int i) {
        try {
            this.javaSocket.setTrafficClass(i);
            return this;
        } catch (SocketException e) {
            throw new ChannelException(e);
        }
    }

    @Override // io.netty.channel.socket.SocketChannelConfig
    public boolean isAllowHalfClosure() {
        return this.a;
    }

    @Override // io.netty.channel.socket.SocketChannelConfig
    public SocketChannelConfig setAllowHalfClosure(boolean z) {
        this.a = z;
        return this;
    }

    @Override // io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public SocketChannelConfig setConnectTimeoutMillis(int i) {
        super.setConnectTimeoutMillis(i);
        return this;
    }

    @Override // io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    @Deprecated
    public SocketChannelConfig setMaxMessagesPerRead(int i) {
        super.setMaxMessagesPerRead(i);
        return this;
    }

    @Override // io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public SocketChannelConfig setWriteSpinCount(int i) {
        super.setWriteSpinCount(i);
        return this;
    }

    @Override // io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public SocketChannelConfig setAllocator(ByteBufAllocator byteBufAllocator) {
        super.setAllocator(byteBufAllocator);
        return this;
    }

    @Override // io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public SocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator) {
        super.setRecvByteBufAllocator(recvByteBufAllocator);
        return this;
    }

    @Override // io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public SocketChannelConfig setAutoRead(boolean z) {
        super.setAutoRead(z);
        return this;
    }

    @Override // io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public SocketChannelConfig setAutoClose(boolean z) {
        super.setAutoClose(z);
        return this;
    }

    @Override // io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    @Deprecated
    public SocketChannelConfig setWriteBufferHighWaterMark(int i) {
        super.setWriteBufferHighWaterMark(i);
        return this;
    }

    @Override // io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    @Deprecated
    public SocketChannelConfig setWriteBufferLowWaterMark(int i) {
        super.setWriteBufferLowWaterMark(i);
        return this;
    }

    @Override // io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public SocketChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
        super.setWriteBufferWaterMark(writeBufferWaterMark);
        return this;
    }

    @Override // io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public SocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator) {
        super.setMessageSizeEstimator(messageSizeEstimator);
        return this;
    }
}
