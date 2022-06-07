package io.netty.channel.udt.nio;

import com.barchart.udt.TypeUDT;
import com.barchart.udt.nio.ServerSocketChannelUDT;
import com.barchart.udt.nio.SocketChannelUDT;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.nio.AbstractNioMessageChannel;
import io.netty.channel.udt.DefaultUdtServerChannelConfig;
import io.netty.channel.udt.UdtChannel;
import io.netty.channel.udt.UdtServerChannel;
import io.netty.channel.udt.UdtServerChannelConfig;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.List;

/* loaded from: classes4.dex */
public abstract class NioUdtAcceptorChannel extends AbstractNioMessageChannel implements UdtServerChannel {
    private final UdtServerChannelConfig h;
    protected static final InternalLogger logger = InternalLoggerFactory.getInstance(NioUdtAcceptorChannel.class);
    private static final ChannelMetadata g = new ChannelMetadata(false, 16);

    protected abstract UdtChannel newConnectorChannel(SocketChannelUDT socketChannelUDT);

    @Override // io.netty.channel.AbstractChannel, io.netty.channel.Channel
    public InetSocketAddress remoteAddress() {
        return null;
    }

    @Override // io.netty.channel.AbstractChannel
    protected SocketAddress remoteAddress0() {
        return null;
    }

    protected NioUdtAcceptorChannel(ServerSocketChannelUDT serverSocketChannelUDT) {
        super(null, serverSocketChannelUDT, 16);
        try {
            serverSocketChannelUDT.configureBlocking(false);
            this.h = new DefaultUdtServerChannelConfig(this, serverSocketChannelUDT, true);
        } catch (Exception e) {
            try {
                serverSocketChannelUDT.close();
            } catch (Exception e2) {
                if (logger.isWarnEnabled()) {
                    logger.warn("Failed to close channel.", (Throwable) e2);
                }
            }
            throw new ChannelException("Failed to configure channel.", e);
        }
    }

    public NioUdtAcceptorChannel(TypeUDT typeUDT) {
        this(NioUdtProvider.a(typeUDT));
    }

    @Override // io.netty.channel.Channel
    public UdtServerChannelConfig config() {
        return this.h;
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doBind(SocketAddress socketAddress) throws Exception {
        javaChannel().socket().bind(socketAddress, this.h.getBacklog());
    }

    @Override // io.netty.channel.nio.AbstractNioChannel, io.netty.channel.AbstractChannel
    protected void doClose() throws Exception {
        javaChannel().close();
    }

    @Override // io.netty.channel.nio.AbstractNioChannel
    protected boolean doConnect(SocketAddress socketAddress, SocketAddress socketAddress2) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doDisconnect() throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override // io.netty.channel.nio.AbstractNioChannel
    protected void doFinishConnect() throws Exception {
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

    @Override // io.netty.channel.Channel
    public boolean isActive() {
        return javaChannel().socket().isBound();
    }

    @Override // io.netty.channel.nio.AbstractNioChannel
    public ServerSocketChannelUDT javaChannel() {
        return super.javaChannel();
    }

    @Override // io.netty.channel.AbstractChannel
    protected SocketAddress localAddress0() {
        return javaChannel().socket().getLocalSocketAddress();
    }

    @Override // io.netty.channel.AbstractChannel, io.netty.channel.Channel
    public InetSocketAddress localAddress() {
        return (InetSocketAddress) super.localAddress();
    }

    @Override // io.netty.channel.Channel
    public ChannelMetadata metadata() {
        return g;
    }

    @Override // io.netty.channel.nio.AbstractNioMessageChannel
    protected int doReadMessages(List<Object> list) throws Exception {
        SocketChannelUDT accept = javaChannel().accept();
        if (accept == null) {
            return 0;
        }
        list.add(newConnectorChannel(accept));
        return 1;
    }
}
