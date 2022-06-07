package io.netty.channel.sctp.nio;

import com.sun.nio.sctp.SctpChannel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPromise;
import io.netty.channel.nio.AbstractNioMessageChannel;
import io.netty.channel.sctp.DefaultSctpServerChannelConfig;
import io.netty.channel.sctp.SctpServerChannel;
import io.netty.channel.sctp.SctpServerChannelConfig;
import io.netty.util.internal.OneTimeTask;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/* loaded from: classes4.dex */
public class NioSctpServerChannel extends AbstractNioMessageChannel implements SctpServerChannel {
    private static final ChannelMetadata g = new ChannelMetadata(false, 16);
    private final SctpServerChannelConfig h = new a(this, javaChannel());

    @Override // io.netty.channel.AbstractChannel, io.netty.channel.Channel
    public InetSocketAddress remoteAddress() {
        return null;
    }

    @Override // io.netty.channel.AbstractChannel
    protected SocketAddress remoteAddress0() {
        return null;
    }

    private static com.sun.nio.sctp.SctpServerChannel b() {
        try {
            return com.sun.nio.sctp.SctpServerChannel.open();
        } catch (IOException e) {
            throw new ChannelException("Failed to open a server socket.", e);
        }
    }

    public NioSctpServerChannel() {
        super(null, b(), 16);
    }

    @Override // io.netty.channel.Channel
    public ChannelMetadata metadata() {
        return g;
    }

    @Override // io.netty.channel.sctp.SctpServerChannel
    public Set<InetSocketAddress> allLocalAddresses() {
        try {
            Set<SocketAddress> allLocalAddresses = javaChannel().getAllLocalAddresses();
            LinkedHashSet linkedHashSet = new LinkedHashSet(allLocalAddresses.size());
            for (SocketAddress socketAddress : allLocalAddresses) {
                linkedHashSet.add((InetSocketAddress) socketAddress);
            }
            return linkedHashSet;
        } catch (Throwable unused) {
            return Collections.emptySet();
        }
    }

    @Override // io.netty.channel.Channel
    public SctpServerChannelConfig config() {
        return this.h;
    }

    @Override // io.netty.channel.Channel
    public boolean isActive() {
        return isOpen() && !allLocalAddresses().isEmpty();
    }

    @Override // io.netty.channel.AbstractChannel, io.netty.channel.Channel
    public InetSocketAddress localAddress() {
        return (InetSocketAddress) super.localAddress();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.channel.nio.AbstractNioChannel
    public com.sun.nio.sctp.SctpServerChannel javaChannel() {
        return super.javaChannel();
    }

    @Override // io.netty.channel.AbstractChannel
    protected SocketAddress localAddress0() {
        try {
            Iterator it = javaChannel().getAllLocalAddresses().iterator();
            if (it.hasNext()) {
                return (SocketAddress) it.next();
            }
            return null;
        } catch (IOException unused) {
            return null;
        }
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doBind(SocketAddress socketAddress) throws Exception {
        javaChannel().bind(socketAddress, this.h.getBacklog());
    }

    @Override // io.netty.channel.nio.AbstractNioChannel, io.netty.channel.AbstractChannel
    protected void doClose() throws Exception {
        javaChannel().close();
    }

    @Override // io.netty.channel.nio.AbstractNioMessageChannel
    protected int doReadMessages(List<Object> list) throws Exception {
        SctpChannel accept = javaChannel().accept();
        if (accept == null) {
            return 0;
        }
        list.add(new NioSctpChannel(this, accept));
        return 1;
    }

    @Override // io.netty.channel.sctp.SctpServerChannel
    public ChannelFuture bindAddress(InetAddress inetAddress) {
        return bindAddress(inetAddress, newPromise());
    }

    @Override // io.netty.channel.sctp.SctpServerChannel
    public ChannelFuture bindAddress(final InetAddress inetAddress, final ChannelPromise channelPromise) {
        if (eventLoop().inEventLoop()) {
            try {
                javaChannel().bindAddress(inetAddress);
                channelPromise.setSuccess();
            } catch (Throwable th) {
                channelPromise.setFailure(th);
            }
        } else {
            eventLoop().execute(new OneTimeTask() { // from class: io.netty.channel.sctp.nio.NioSctpServerChannel.1
                @Override // java.lang.Runnable
                public void run() {
                    NioSctpServerChannel.this.bindAddress(inetAddress, channelPromise);
                }
            });
        }
        return channelPromise;
    }

    @Override // io.netty.channel.sctp.SctpServerChannel
    public ChannelFuture unbindAddress(InetAddress inetAddress) {
        return unbindAddress(inetAddress, newPromise());
    }

    @Override // io.netty.channel.sctp.SctpServerChannel
    public ChannelFuture unbindAddress(final InetAddress inetAddress, final ChannelPromise channelPromise) {
        if (eventLoop().inEventLoop()) {
            try {
                javaChannel().unbindAddress(inetAddress);
                channelPromise.setSuccess();
            } catch (Throwable th) {
                channelPromise.setFailure(th);
            }
        } else {
            eventLoop().execute(new OneTimeTask() { // from class: io.netty.channel.sctp.nio.NioSctpServerChannel.2
                @Override // java.lang.Runnable
                public void run() {
                    NioSctpServerChannel.this.unbindAddress(inetAddress, channelPromise);
                }
            });
        }
        return channelPromise;
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
    protected Object filterOutboundMessage(Object obj) throws Exception {
        throw new UnsupportedOperationException();
    }

    /* loaded from: classes4.dex */
    private final class a extends DefaultSctpServerChannelConfig {
        private a(NioSctpServerChannel nioSctpServerChannel, com.sun.nio.sctp.SctpServerChannel sctpServerChannel) {
            super(nioSctpServerChannel, sctpServerChannel);
        }

        @Override // io.netty.channel.DefaultChannelConfig
        protected void autoReadCleared() {
            NioSctpServerChannel.this.clearReadPending();
        }
    }
}
