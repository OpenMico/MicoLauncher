package io.netty.channel.epoll;

import io.netty.channel.Channel;
import io.netty.channel.unix.DomainSocketAddress;
import io.netty.channel.unix.FileDescriptor;
import io.netty.channel.unix.ServerDomainSocketChannel;
import io.netty.channel.unix.Socket;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.File;
import java.net.SocketAddress;

/* loaded from: classes4.dex */
public final class EpollServerDomainSocketChannel extends AbstractEpollServerChannel implements ServerDomainSocketChannel {
    private static final InternalLogger c = InternalLoggerFactory.getInstance(EpollServerDomainSocketChannel.class);
    private final EpollServerChannelConfig d = new EpollServerChannelConfig(this);
    private volatile DomainSocketAddress e;

    public EpollServerDomainSocketChannel() {
        super(Socket.newSocketDomain(), false);
    }

    public EpollServerDomainSocketChannel(FileDescriptor fileDescriptor) {
        super(fileDescriptor);
    }

    public EpollServerDomainSocketChannel(Socket socket) {
        super(socket);
    }

    public EpollServerDomainSocketChannel(Socket socket, boolean z) {
        super(socket, z);
    }

    @Override // io.netty.channel.epoll.AbstractEpollServerChannel
    protected Channel newChildChannel(int i, byte[] bArr, int i2, int i3) throws Exception {
        return new EpollDomainSocketChannel((Channel) this, new Socket(i));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.channel.AbstractChannel
    public DomainSocketAddress localAddress0() {
        return this.e;
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doBind(SocketAddress socketAddress) throws Exception {
        fd().bind(socketAddress);
        fd().listen(this.d.getBacklog());
        this.e = (DomainSocketAddress) socketAddress;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.channel.epoll.AbstractEpollChannel, io.netty.channel.AbstractChannel
    public void doClose() throws Exception {
        try {
            super.doClose();
            DomainSocketAddress domainSocketAddress = this.e;
            if (domainSocketAddress != null && !new File(domainSocketAddress.path()).delete() && c.isDebugEnabled()) {
                c.debug("Failed to delete a domain socket file: {}", domainSocketAddress.path());
            }
        } catch (Throwable th) {
            DomainSocketAddress domainSocketAddress2 = this.e;
            if (domainSocketAddress2 != null && !new File(domainSocketAddress2.path()).delete() && c.isDebugEnabled()) {
                c.debug("Failed to delete a domain socket file: {}", domainSocketAddress2.path());
            }
            throw th;
        }
    }

    @Override // io.netty.channel.epoll.AbstractEpollChannel, io.netty.channel.Channel
    public EpollServerChannelConfig config() {
        return this.d;
    }

    @Override // io.netty.channel.AbstractChannel, io.netty.channel.Channel
    public DomainSocketAddress remoteAddress() {
        return (DomainSocketAddress) super.remoteAddress();
    }

    @Override // io.netty.channel.AbstractChannel, io.netty.channel.Channel
    public DomainSocketAddress localAddress() {
        return (DomainSocketAddress) super.localAddress();
    }
}
