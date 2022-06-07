package io.netty.channel.epoll;

import io.netty.channel.Channel;
import io.netty.channel.EventLoop;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.unix.FileDescriptor;
import io.netty.channel.unix.NativeInetAddress;
import io.netty.channel.unix.Socket;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/* loaded from: classes4.dex */
public final class EpollServerSocketChannel extends AbstractEpollServerChannel implements ServerSocketChannel {
    private final EpollServerSocketChannelConfig c;
    private volatile InetSocketAddress d;
    private volatile Collection<InetAddress> e;

    public EpollServerSocketChannel() {
        super(Socket.newSocketStream(), false);
        this.e = Collections.emptyList();
        this.c = new EpollServerSocketChannelConfig(this);
    }

    @Deprecated
    public EpollServerSocketChannel(FileDescriptor fileDescriptor) {
        this(new Socket(fileDescriptor.intValue()));
    }

    @Deprecated
    public EpollServerSocketChannel(Socket socket) {
        super(socket);
        this.e = Collections.emptyList();
        this.d = socket.localAddress();
        this.c = new EpollServerSocketChannelConfig(this);
    }

    public EpollServerSocketChannel(Socket socket, boolean z) {
        super(socket, z);
        this.e = Collections.emptyList();
        this.d = socket.localAddress();
        this.c = new EpollServerSocketChannelConfig(this);
    }

    @Override // io.netty.channel.epoll.AbstractEpollServerChannel, io.netty.channel.epoll.AbstractEpollChannel, io.netty.channel.AbstractChannel
    protected boolean isCompatible(EventLoop eventLoop) {
        return eventLoop instanceof b;
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doBind(SocketAddress socketAddress) throws Exception {
        InetSocketAddress inetSocketAddress = (InetSocketAddress) socketAddress;
        checkResolvable(inetSocketAddress);
        fd().bind(inetSocketAddress);
        this.d = fd().localAddress();
        if (Native.IS_SUPPORTING_TCP_FASTOPEN && this.c.getTcpFastopen() > 0) {
            Native.setTcpFastopen(fd().intValue(), this.c.getTcpFastopen());
        }
        fd().listen(this.c.getBacklog());
        this.active = true;
    }

    @Override // io.netty.channel.AbstractChannel, io.netty.channel.Channel
    public InetSocketAddress remoteAddress() {
        return (InetSocketAddress) super.remoteAddress();
    }

    @Override // io.netty.channel.AbstractChannel, io.netty.channel.Channel
    public InetSocketAddress localAddress() {
        return (InetSocketAddress) super.localAddress();
    }

    @Override // io.netty.channel.epoll.AbstractEpollChannel, io.netty.channel.Channel
    public EpollServerSocketChannelConfig config() {
        return this.c;
    }

    @Override // io.netty.channel.AbstractChannel
    public InetSocketAddress localAddress0() {
        return this.d;
    }

    @Override // io.netty.channel.epoll.AbstractEpollServerChannel
    protected Channel newChildChannel(int i, byte[] bArr, int i2, int i3) throws Exception {
        return new EpollSocketChannel(this, new Socket(i), NativeInetAddress.address(bArr, i2, i3));
    }

    public Collection<InetAddress> c() {
        return this.e;
    }

    public void a(Map<InetAddress, byte[]> map) throws IOException {
        this.e = g.a(this, this.e, map);
    }
}
