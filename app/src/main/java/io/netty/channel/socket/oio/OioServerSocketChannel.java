package io.netty.channel.socket.oio;

import io.netty.channel.ChannelException;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.oio.AbstractOioMessageChannel;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes4.dex */
public class OioServerSocketChannel extends AbstractOioMessageChannel implements ServerSocketChannel {
    private static final InternalLogger f = InternalLoggerFactory.getInstance(OioServerSocketChannel.class);
    private static final ChannelMetadata g = new ChannelMetadata(false, 1);
    final ServerSocket d;
    final Lock e;
    private final OioServerSocketChannelConfig h;

    @Override // io.netty.channel.AbstractChannel, io.netty.channel.Channel
    public InetSocketAddress remoteAddress() {
        return null;
    }

    @Override // io.netty.channel.AbstractChannel
    protected SocketAddress remoteAddress0() {
        return null;
    }

    private static ServerSocket c() {
        try {
            return new ServerSocket();
        } catch (IOException e) {
            throw new ChannelException("failed to create a server socket", e);
        }
    }

    public OioServerSocketChannel() {
        this(c());
    }

    public OioServerSocketChannel(ServerSocket serverSocket) {
        super(null);
        this.e = new ReentrantLock();
        if (serverSocket != null) {
            try {
                try {
                    serverSocket.setSoTimeout(1000);
                    this.d = serverSocket;
                    this.h = new DefaultOioServerSocketChannelConfig(this, serverSocket);
                } catch (IOException e) {
                    throw new ChannelException("Failed to set the server socket timeout.", e);
                }
            } catch (Throwable th) {
                try {
                    serverSocket.close();
                } catch (IOException e2) {
                    if (f.isWarnEnabled()) {
                        f.warn("Failed to close a partially initialized socket.", (Throwable) e2);
                    }
                }
                throw th;
            }
        } else {
            throw new NullPointerException("socket");
        }
    }

    @Override // io.netty.channel.AbstractChannel, io.netty.channel.Channel
    public InetSocketAddress localAddress() {
        return (InetSocketAddress) super.localAddress();
    }

    @Override // io.netty.channel.Channel
    public ChannelMetadata metadata() {
        return g;
    }

    @Override // io.netty.channel.Channel
    public OioServerSocketChannelConfig config() {
        return this.h;
    }

    @Override // io.netty.channel.Channel
    public boolean isOpen() {
        return !this.d.isClosed();
    }

    @Override // io.netty.channel.Channel
    public boolean isActive() {
        return isOpen() && this.d.isBound();
    }

    @Override // io.netty.channel.AbstractChannel
    protected SocketAddress localAddress0() {
        return this.d.getLocalSocketAddress();
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doBind(SocketAddress socketAddress) throws Exception {
        this.d.bind(socketAddress, this.h.getBacklog());
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doClose() throws Exception {
        this.d.close();
    }

    @Override // io.netty.channel.oio.AbstractOioMessageChannel
    protected int doReadMessages(List<Object> list) throws Exception {
        if (this.d.isClosed()) {
            return -1;
        }
        try {
            list.add(new OioSocketChannel(this, this.d.accept()));
            return 1;
        } catch (SocketTimeoutException unused) {
            return 0;
        }
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doWrite(ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override // io.netty.channel.AbstractChannel
    protected Object filterOutboundMessage(Object obj) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override // io.netty.channel.oio.AbstractOioChannel
    protected void doConnect(SocketAddress socketAddress, SocketAddress socketAddress2) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doDisconnect() throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override // io.netty.channel.oio.AbstractOioChannel
    @Deprecated
    protected void setReadPending(boolean z) {
        super.setReadPending(z);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void b() {
        super.clearReadPending();
    }
}
