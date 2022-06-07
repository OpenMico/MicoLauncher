package io.netty.channel.socket;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelOption;
import io.netty.channel.DefaultChannelConfig;
import io.netty.channel.FixedRecvByteBufAllocator;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Map;

/* loaded from: classes4.dex */
public class DefaultDatagramChannelConfig extends DefaultChannelConfig implements DatagramChannelConfig {
    private static final InternalLogger a = InternalLoggerFactory.getInstance(DefaultDatagramChannelConfig.class);
    private final DatagramSocket b;
    private volatile boolean c;

    public DefaultDatagramChannelConfig(DatagramChannel datagramChannel, DatagramSocket datagramSocket) {
        super(datagramChannel, new FixedRecvByteBufAllocator(2048));
        if (datagramSocket != null) {
            this.b = datagramSocket;
            return;
        }
        throw new NullPointerException("javaSocket");
    }

    @Override // io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public Map<ChannelOption<?>, Object> getOptions() {
        return getOptions(super.getOptions(), ChannelOption.SO_BROADCAST, ChannelOption.SO_RCVBUF, ChannelOption.SO_SNDBUF, ChannelOption.SO_REUSEADDR, ChannelOption.IP_MULTICAST_LOOP_DISABLED, ChannelOption.IP_MULTICAST_ADDR, ChannelOption.IP_MULTICAST_IF, ChannelOption.IP_MULTICAST_TTL, ChannelOption.IP_TOS, ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION);
    }

    @Override // io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public <T> T getOption(ChannelOption<T> channelOption) {
        if (channelOption == ChannelOption.SO_BROADCAST) {
            return (T) Boolean.valueOf(isBroadcast());
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
        if (channelOption == ChannelOption.IP_MULTICAST_LOOP_DISABLED) {
            return (T) Boolean.valueOf(isLoopbackModeDisabled());
        }
        if (channelOption == ChannelOption.IP_MULTICAST_ADDR) {
            return (T) getInterface();
        }
        if (channelOption == ChannelOption.IP_MULTICAST_IF) {
            return (T) getNetworkInterface();
        }
        if (channelOption == ChannelOption.IP_MULTICAST_TTL) {
            return (T) Integer.valueOf(getTimeToLive());
        }
        if (channelOption == ChannelOption.IP_TOS) {
            return (T) Integer.valueOf(getTrafficClass());
        }
        if (channelOption == ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION) {
            return (T) Boolean.valueOf(this.c);
        }
        return (T) super.getOption(channelOption);
    }

    @Override // io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public <T> boolean setOption(ChannelOption<T> channelOption, T t) {
        validate(channelOption, t);
        if (channelOption == ChannelOption.SO_BROADCAST) {
            setBroadcast(((Boolean) t).booleanValue());
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
        } else if (channelOption == ChannelOption.IP_MULTICAST_LOOP_DISABLED) {
            setLoopbackModeDisabled(((Boolean) t).booleanValue());
            return true;
        } else if (channelOption == ChannelOption.IP_MULTICAST_ADDR) {
            setInterface((InetAddress) t);
            return true;
        } else if (channelOption == ChannelOption.IP_MULTICAST_IF) {
            setNetworkInterface((NetworkInterface) t);
            return true;
        } else if (channelOption == ChannelOption.IP_MULTICAST_TTL) {
            setTimeToLive(((Integer) t).intValue());
            return true;
        } else if (channelOption == ChannelOption.IP_TOS) {
            setTrafficClass(((Integer) t).intValue());
            return true;
        } else if (channelOption != ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION) {
            return super.setOption(channelOption, t);
        } else {
            a(((Boolean) t).booleanValue());
            return true;
        }
    }

    private void a(boolean z) {
        if (!this.channel.isRegistered()) {
            this.c = z;
            return;
        }
        throw new IllegalStateException("Can only changed before channel was registered");
    }

    @Override // io.netty.channel.socket.DatagramChannelConfig
    public boolean isBroadcast() {
        try {
            return this.b.getBroadcast();
        } catch (SocketException e) {
            throw new ChannelException(e);
        }
    }

    @Override // io.netty.channel.socket.DatagramChannelConfig
    public DatagramChannelConfig setBroadcast(boolean z) {
        if (z) {
            try {
                if (!this.b.getLocalAddress().isAnyLocalAddress() && !PlatformDependent.isWindows() && !PlatformDependent.isRoot()) {
                    InternalLogger internalLogger = a;
                    internalLogger.warn("A non-root user can't receive a broadcast packet if the socket is not bound to a wildcard address; setting the SO_BROADCAST flag anyway as requested on the socket which is bound to " + this.b.getLocalSocketAddress() + '.');
                }
            } catch (SocketException e) {
                throw new ChannelException(e);
            }
        }
        this.b.setBroadcast(z);
        return this;
    }

    @Override // io.netty.channel.socket.DatagramChannelConfig
    public InetAddress getInterface() {
        DatagramSocket datagramSocket = this.b;
        if (datagramSocket instanceof MulticastSocket) {
            try {
                return ((MulticastSocket) datagramSocket).getInterface();
            } catch (SocketException e) {
                throw new ChannelException(e);
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override // io.netty.channel.socket.DatagramChannelConfig
    public DatagramChannelConfig setInterface(InetAddress inetAddress) {
        DatagramSocket datagramSocket = this.b;
        if (datagramSocket instanceof MulticastSocket) {
            try {
                ((MulticastSocket) datagramSocket).setInterface(inetAddress);
                return this;
            } catch (SocketException e) {
                throw new ChannelException(e);
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override // io.netty.channel.socket.DatagramChannelConfig
    public boolean isLoopbackModeDisabled() {
        DatagramSocket datagramSocket = this.b;
        if (datagramSocket instanceof MulticastSocket) {
            try {
                return ((MulticastSocket) datagramSocket).getLoopbackMode();
            } catch (SocketException e) {
                throw new ChannelException(e);
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override // io.netty.channel.socket.DatagramChannelConfig
    public DatagramChannelConfig setLoopbackModeDisabled(boolean z) {
        DatagramSocket datagramSocket = this.b;
        if (datagramSocket instanceof MulticastSocket) {
            try {
                ((MulticastSocket) datagramSocket).setLoopbackMode(z);
                return this;
            } catch (SocketException e) {
                throw new ChannelException(e);
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override // io.netty.channel.socket.DatagramChannelConfig
    public NetworkInterface getNetworkInterface() {
        DatagramSocket datagramSocket = this.b;
        if (datagramSocket instanceof MulticastSocket) {
            try {
                return ((MulticastSocket) datagramSocket).getNetworkInterface();
            } catch (SocketException e) {
                throw new ChannelException(e);
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override // io.netty.channel.socket.DatagramChannelConfig
    public DatagramChannelConfig setNetworkInterface(NetworkInterface networkInterface) {
        DatagramSocket datagramSocket = this.b;
        if (datagramSocket instanceof MulticastSocket) {
            try {
                ((MulticastSocket) datagramSocket).setNetworkInterface(networkInterface);
                return this;
            } catch (SocketException e) {
                throw new ChannelException(e);
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override // io.netty.channel.socket.DatagramChannelConfig
    public boolean isReuseAddress() {
        try {
            return this.b.getReuseAddress();
        } catch (SocketException e) {
            throw new ChannelException(e);
        }
    }

    @Override // io.netty.channel.socket.DatagramChannelConfig
    public DatagramChannelConfig setReuseAddress(boolean z) {
        try {
            this.b.setReuseAddress(z);
            return this;
        } catch (SocketException e) {
            throw new ChannelException(e);
        }
    }

    @Override // io.netty.channel.socket.DatagramChannelConfig
    public int getReceiveBufferSize() {
        try {
            return this.b.getReceiveBufferSize();
        } catch (SocketException e) {
            throw new ChannelException(e);
        }
    }

    @Override // io.netty.channel.socket.DatagramChannelConfig
    public DatagramChannelConfig setReceiveBufferSize(int i) {
        try {
            this.b.setReceiveBufferSize(i);
            return this;
        } catch (SocketException e) {
            throw new ChannelException(e);
        }
    }

    @Override // io.netty.channel.socket.DatagramChannelConfig
    public int getSendBufferSize() {
        try {
            return this.b.getSendBufferSize();
        } catch (SocketException e) {
            throw new ChannelException(e);
        }
    }

    @Override // io.netty.channel.socket.DatagramChannelConfig
    public DatagramChannelConfig setSendBufferSize(int i) {
        try {
            this.b.setSendBufferSize(i);
            return this;
        } catch (SocketException e) {
            throw new ChannelException(e);
        }
    }

    @Override // io.netty.channel.socket.DatagramChannelConfig
    public int getTimeToLive() {
        DatagramSocket datagramSocket = this.b;
        if (datagramSocket instanceof MulticastSocket) {
            try {
                return ((MulticastSocket) datagramSocket).getTimeToLive();
            } catch (IOException e) {
                throw new ChannelException(e);
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override // io.netty.channel.socket.DatagramChannelConfig
    public DatagramChannelConfig setTimeToLive(int i) {
        DatagramSocket datagramSocket = this.b;
        if (datagramSocket instanceof MulticastSocket) {
            try {
                ((MulticastSocket) datagramSocket).setTimeToLive(i);
                return this;
            } catch (IOException e) {
                throw new ChannelException(e);
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override // io.netty.channel.socket.DatagramChannelConfig
    public int getTrafficClass() {
        try {
            return this.b.getTrafficClass();
        } catch (SocketException e) {
            throw new ChannelException(e);
        }
    }

    @Override // io.netty.channel.socket.DatagramChannelConfig
    public DatagramChannelConfig setTrafficClass(int i) {
        try {
            this.b.setTrafficClass(i);
            return this;
        } catch (SocketException e) {
            throw new ChannelException(e);
        }
    }

    @Override // io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public DatagramChannelConfig setWriteSpinCount(int i) {
        super.setWriteSpinCount(i);
        return this;
    }

    @Override // io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public DatagramChannelConfig setConnectTimeoutMillis(int i) {
        super.setConnectTimeoutMillis(i);
        return this;
    }

    @Override // io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    @Deprecated
    public DatagramChannelConfig setMaxMessagesPerRead(int i) {
        super.setMaxMessagesPerRead(i);
        return this;
    }

    @Override // io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public DatagramChannelConfig setAllocator(ByteBufAllocator byteBufAllocator) {
        super.setAllocator(byteBufAllocator);
        return this;
    }

    @Override // io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public DatagramChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator) {
        super.setRecvByteBufAllocator(recvByteBufAllocator);
        return this;
    }

    @Override // io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public DatagramChannelConfig setAutoRead(boolean z) {
        super.setAutoRead(z);
        return this;
    }

    @Override // io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public DatagramChannelConfig setAutoClose(boolean z) {
        super.setAutoClose(z);
        return this;
    }

    @Override // io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    @Deprecated
    public DatagramChannelConfig setWriteBufferHighWaterMark(int i) {
        super.setWriteBufferHighWaterMark(i);
        return this;
    }

    @Override // io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    @Deprecated
    public DatagramChannelConfig setWriteBufferLowWaterMark(int i) {
        super.setWriteBufferLowWaterMark(i);
        return this;
    }

    @Override // io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public DatagramChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
        super.setWriteBufferWaterMark(writeBufferWaterMark);
        return this;
    }

    @Override // io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
    public DatagramChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator) {
        super.setMessageSizeEstimator(messageSizeEstimator);
        return this;
    }
}
