package io.netty.channel.epoll;

import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.epoll.AbstractEpollChannel;
import io.netty.channel.epoll.AbstractEpollStreamChannel;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.unix.FileDescriptor;
import io.netty.channel.unix.Socket;
import io.netty.util.concurrent.GlobalEventExecutor;
import io.netty.util.internal.PlatformDependent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.Executor;

/* loaded from: classes4.dex */
public final class EpollSocketChannel extends AbstractEpollStreamChannel implements SocketChannel {
    private final EpollSocketChannelConfig e;
    private volatile InetSocketAddress f;
    private volatile InetSocketAddress g;
    private InetSocketAddress h;
    private volatile Collection<InetAddress> i;

    public EpollSocketChannel(Channel channel, Socket socket, InetSocketAddress inetSocketAddress) {
        super(channel, socket);
        this.i = Collections.emptyList();
        this.e = new EpollSocketChannelConfig(this);
        this.g = inetSocketAddress;
        this.f = socket.localAddress();
        if (channel instanceof EpollServerSocketChannel) {
            this.i = ((EpollServerSocketChannel) channel).c();
        }
    }

    public EpollSocketChannel() {
        super(Socket.newSocketStream(), false);
        this.i = Collections.emptyList();
        this.e = new EpollSocketChannelConfig(this);
    }

    @Deprecated
    public EpollSocketChannel(FileDescriptor fileDescriptor) {
        super(fileDescriptor);
        this.i = Collections.emptyList();
        this.g = fd().remoteAddress();
        this.f = fd().localAddress();
        this.e = new EpollSocketChannelConfig(this);
    }

    public EpollSocketChannel(Socket socket, boolean z) {
        super(socket, z);
        this.i = Collections.emptyList();
        this.g = socket.remoteAddress();
        this.f = socket.localAddress();
        this.e = new EpollSocketChannelConfig(this);
    }

    public EpollTcpInfo tcpInfo() {
        return tcpInfo(new EpollTcpInfo());
    }

    public EpollTcpInfo tcpInfo(EpollTcpInfo epollTcpInfo) {
        try {
            Native.tcpInfo(fd().intValue(), epollTcpInfo);
            return epollTcpInfo;
        } catch (IOException e) {
            throw new ChannelException(e);
        }
    }

    @Override // io.netty.channel.AbstractChannel, io.netty.channel.Channel
    public InetSocketAddress remoteAddress() {
        return (InetSocketAddress) super.remoteAddress();
    }

    @Override // io.netty.channel.AbstractChannel, io.netty.channel.Channel
    public InetSocketAddress localAddress() {
        return (InetSocketAddress) super.localAddress();
    }

    @Override // io.netty.channel.AbstractChannel
    protected SocketAddress localAddress0() {
        return this.f;
    }

    @Override // io.netty.channel.AbstractChannel
    protected SocketAddress remoteAddress0() {
        return this.g;
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doBind(SocketAddress socketAddress) throws Exception {
        fd().bind((InetSocketAddress) socketAddress);
        this.f = fd().localAddress();
    }

    @Override // io.netty.channel.epoll.AbstractEpollChannel, io.netty.channel.Channel
    public EpollSocketChannelConfig config() {
        return this.e;
    }

    @Override // io.netty.channel.AbstractChannel, io.netty.channel.Channel
    public ServerSocketChannel parent() {
        return (ServerSocketChannel) super.parent();
    }

    @Override // io.netty.channel.epoll.AbstractEpollStreamChannel, io.netty.channel.epoll.AbstractEpollChannel, io.netty.channel.AbstractChannel
    public AbstractEpollChannel.AbstractEpollUnsafe newUnsafe() {
        return new a();
    }

    public static InetSocketAddress b(InetSocketAddress inetSocketAddress, InetSocketAddress inetSocketAddress2) {
        if (inetSocketAddress2 == null) {
            return inetSocketAddress;
        }
        if (PlatformDependent.javaVersion() >= 7) {
            try {
                return new InetSocketAddress(InetAddress.getByAddress(inetSocketAddress.getHostString(), inetSocketAddress2.getAddress().getAddress()), inetSocketAddress2.getPort());
            } catch (UnknownHostException unused) {
            }
        }
        return inetSocketAddress2;
    }

    @Override // io.netty.channel.epoll.AbstractEpollStreamChannel
    public boolean doConnect(SocketAddress socketAddress, SocketAddress socketAddress2) throws Exception {
        if (socketAddress2 != null) {
            checkResolvable((InetSocketAddress) socketAddress2);
        }
        InetSocketAddress inetSocketAddress = (InetSocketAddress) socketAddress;
        checkResolvable(inetSocketAddress);
        boolean doConnect = super.doConnect(socketAddress, socketAddress2);
        if (doConnect) {
            this.g = b(inetSocketAddress, fd().remoteAddress());
        } else {
            this.h = inetSocketAddress;
        }
        this.f = fd().localAddress();
        return doConnect;
    }

    /* loaded from: classes4.dex */
    public final class a extends AbstractEpollStreamChannel.a {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        private a() {
            super();
            EpollSocketChannel.this = r1;
        }

        @Override // io.netty.channel.epoll.AbstractEpollStreamChannel.a, io.netty.channel.AbstractChannel.AbstractUnsafe
        protected Executor prepareToClose() {
            try {
                if (!EpollSocketChannel.this.isOpen() || EpollSocketChannel.this.config().getSoLinger() <= 0) {
                    return null;
                }
                ((b) EpollSocketChannel.this.eventLoop()).c(EpollSocketChannel.this);
                return GlobalEventExecutor.INSTANCE;
            } catch (Throwable unused) {
                return null;
            }
        }

        @Override // io.netty.channel.epoll.AbstractEpollStreamChannel.a
        public boolean g() throws Exception {
            if (!super.g()) {
                return false;
            }
            EpollSocketChannel epollSocketChannel = EpollSocketChannel.this;
            epollSocketChannel.g = EpollSocketChannel.b(epollSocketChannel.h, EpollSocketChannel.this.fd().remoteAddress());
            EpollSocketChannel.this.h = null;
            return true;
        }
    }

    public void a(Map<InetAddress, byte[]> map) throws IOException {
        this.i = g.a(this, this.i, map);
    }
}
