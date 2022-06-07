package io.netty.channel.socket.nio;

import io.netty.channel.ChannelException;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.nio.AbstractNioMessageChannel;
import io.netty.channel.socket.DefaultServerSocketChannelConfig;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.ServerSocketChannelConfig;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.List;

/* loaded from: classes4.dex */
public class NioServerSocketChannel extends AbstractNioMessageChannel implements ServerSocketChannel {
    private static final ChannelMetadata g = new ChannelMetadata(false, 16);
    private static final SelectorProvider h = SelectorProvider.provider();
    private static final InternalLogger i = InternalLoggerFactory.getInstance(NioServerSocketChannel.class);
    private final ServerSocketChannelConfig j;

    @Override // io.netty.channel.AbstractChannel, io.netty.channel.Channel
    public InetSocketAddress remoteAddress() {
        return null;
    }

    @Override // io.netty.channel.AbstractChannel
    protected SocketAddress remoteAddress0() {
        return null;
    }

    private static java.nio.channels.ServerSocketChannel a(SelectorProvider selectorProvider) {
        try {
            return selectorProvider.openServerSocketChannel();
        } catch (IOException e) {
            throw new ChannelException("Failed to open a server socket.", e);
        }
    }

    public NioServerSocketChannel() {
        this(a(h));
    }

    public NioServerSocketChannel(SelectorProvider selectorProvider) {
        this(a(selectorProvider));
    }

    public NioServerSocketChannel(java.nio.channels.ServerSocketChannel serverSocketChannel) {
        super(null, serverSocketChannel, 16);
        this.j = new a(this, javaChannel().socket());
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
    public ServerSocketChannelConfig config() {
        return this.j;
    }

    @Override // io.netty.channel.Channel
    public boolean isActive() {
        return javaChannel().socket().isBound();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.channel.nio.AbstractNioChannel
    public java.nio.channels.ServerSocketChannel javaChannel() {
        return (java.nio.channels.ServerSocketChannel) super.javaChannel();
    }

    @Override // io.netty.channel.AbstractChannel
    protected SocketAddress localAddress0() {
        return javaChannel().socket().getLocalSocketAddress();
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doBind(SocketAddress socketAddress) throws Exception {
        javaChannel().socket().bind(socketAddress, this.j.getBacklog());
    }

    @Override // io.netty.channel.nio.AbstractNioChannel, io.netty.channel.AbstractChannel
    protected void doClose() throws Exception {
        javaChannel().close();
    }

    @Override // io.netty.channel.nio.AbstractNioMessageChannel
    protected int doReadMessages(List<Object> list) throws Exception {
        SocketChannel accept = javaChannel().accept();
        if (accept == null) {
            return 0;
        }
        try {
            list.add(new NioSocketChannel(this, accept));
            return 1;
        } catch (Throwable th) {
            i.warn("Failed to create a new channel from an accepted socket.", th);
            try {
                accept.close();
                return 0;
            } catch (Throwable th2) {
                i.warn("Failed to close a socket.", th2);
                return 0;
            }
        }
    }

    @Override // io.netty.channel.nio.AbstractNioChannel
    protected boolean doConnect(SocketAddress socketAddress, SocketAddress socketAddress2) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override // io.netty.channel.nio.AbstractNioChannel
    protected void doFinishConnect() throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doDisconnect() throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override // io.netty.channel.nio.AbstractNioMessageChannel
    protected boolean doWriteMessage(Object obj, ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override // io.netty.channel.AbstractChannel
    protected final Object filterOutboundMessage(Object obj) throws Exception {
        throw new UnsupportedOperationException();
    }

    /* loaded from: classes4.dex */
    private final class a extends DefaultServerSocketChannelConfig {
        private a(NioServerSocketChannel nioServerSocketChannel, ServerSocket serverSocket) {
            super(nioServerSocketChannel, serverSocket);
        }

        @Override // io.netty.channel.DefaultChannelConfig
        protected void autoReadCleared() {
            NioServerSocketChannel.this.clearReadPending();
        }
    }
}
