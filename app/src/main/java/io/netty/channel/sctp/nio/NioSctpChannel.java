package io.netty.channel.sctp.nio;

import com.sun.nio.sctp.Association;
import com.sun.nio.sctp.MessageInfo;
import com.sun.nio.sctp.NotificationHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPromise;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.nio.AbstractNioMessageChannel;
import io.netty.channel.sctp.DefaultSctpChannelConfig;
import io.netty.channel.sctp.SctpChannel;
import io.netty.channel.sctp.SctpChannelConfig;
import io.netty.channel.sctp.SctpMessage;
import io.netty.channel.sctp.SctpNotificationHandler;
import io.netty.channel.sctp.SctpServerChannel;
import io.netty.util.internal.OneTimeTask;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/* loaded from: classes4.dex */
public class NioSctpChannel extends AbstractNioMessageChannel implements SctpChannel {
    private static final ChannelMetadata g = new ChannelMetadata(false);
    private static final InternalLogger h = InternalLoggerFactory.getInstance(NioSctpChannel.class);
    private final SctpChannelConfig i;
    private final NotificationHandler<?> j;

    private static com.sun.nio.sctp.SctpChannel b() {
        try {
            return com.sun.nio.sctp.SctpChannel.open();
        } catch (IOException e) {
            throw new ChannelException("Failed to open a sctp channel.", e);
        }
    }

    public NioSctpChannel() {
        this(b());
    }

    public NioSctpChannel(com.sun.nio.sctp.SctpChannel sctpChannel) {
        this(null, sctpChannel);
    }

    public NioSctpChannel(Channel channel, com.sun.nio.sctp.SctpChannel sctpChannel) {
        super(channel, sctpChannel, 1);
        try {
            sctpChannel.configureBlocking(false);
            this.i = new a(this, sctpChannel);
            this.j = new SctpNotificationHandler(this);
        } catch (IOException e) {
            try {
                sctpChannel.close();
            } catch (IOException e2) {
                if (h.isWarnEnabled()) {
                    h.warn("Failed to close a partially initialized sctp channel.", (Throwable) e2);
                }
            }
            throw new ChannelException("Failed to enter non-blocking mode.", e);
        }
    }

    @Override // io.netty.channel.AbstractChannel, io.netty.channel.Channel
    public InetSocketAddress localAddress() {
        return (InetSocketAddress) super.localAddress();
    }

    @Override // io.netty.channel.AbstractChannel, io.netty.channel.Channel
    public InetSocketAddress remoteAddress() {
        return (InetSocketAddress) super.remoteAddress();
    }

    @Override // io.netty.channel.AbstractChannel, io.netty.channel.Channel
    public SctpServerChannel parent() {
        return (SctpServerChannel) super.parent();
    }

    @Override // io.netty.channel.Channel
    public ChannelMetadata metadata() {
        return g;
    }

    @Override // io.netty.channel.sctp.SctpChannel
    public Association association() {
        try {
            return javaChannel().association();
        } catch (IOException unused) {
            return null;
        }
    }

    @Override // io.netty.channel.sctp.SctpChannel
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
    public SctpChannelConfig config() {
        return this.i;
    }

    @Override // io.netty.channel.sctp.SctpChannel
    public Set<InetSocketAddress> allRemoteAddresses() {
        try {
            Set<SocketAddress> remoteAddresses = javaChannel().getRemoteAddresses();
            HashSet hashSet = new HashSet(remoteAddresses.size());
            for (SocketAddress socketAddress : remoteAddresses) {
                hashSet.add((InetSocketAddress) socketAddress);
            }
            return hashSet;
        } catch (Throwable unused) {
            return Collections.emptySet();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.channel.nio.AbstractNioChannel
    public com.sun.nio.sctp.SctpChannel javaChannel() {
        return super.javaChannel();
    }

    @Override // io.netty.channel.Channel
    public boolean isActive() {
        return javaChannel().isOpen() && association() != null;
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
    protected SocketAddress remoteAddress0() {
        try {
            Iterator it = javaChannel().getRemoteAddresses().iterator();
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
        javaChannel().bind(socketAddress);
    }

    @Override // io.netty.channel.nio.AbstractNioChannel
    protected boolean doConnect(SocketAddress socketAddress, SocketAddress socketAddress2) throws Exception {
        if (socketAddress2 != null) {
            javaChannel().bind(socketAddress2);
        }
        try {
            boolean connect = javaChannel().connect(socketAddress);
            if (!connect) {
                selectionKey().interestOps(8);
            }
            return connect;
        } catch (Throwable th) {
            doClose();
            throw th;
        }
    }

    @Override // io.netty.channel.nio.AbstractNioChannel
    protected void doFinishConnect() throws Exception {
        if (!javaChannel().finishConnect()) {
            throw new Error();
        }
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doDisconnect() throws Exception {
        doClose();
    }

    @Override // io.netty.channel.nio.AbstractNioChannel, io.netty.channel.AbstractChannel
    protected void doClose() throws Exception {
        javaChannel().close();
    }

    @Override // io.netty.channel.nio.AbstractNioMessageChannel
    protected int doReadMessages(List<Object> list) throws Exception {
        com.sun.nio.sctp.SctpChannel javaChannel = javaChannel();
        RecvByteBufAllocator.Handle recvBufAllocHandle = unsafe().recvBufAllocHandle();
        ByteBuf allocate = recvBufAllocHandle.allocate(config().getAllocator());
        try {
            ByteBuffer internalNioBuffer = allocate.internalNioBuffer(allocate.writerIndex(), allocate.writableBytes());
            int position = internalNioBuffer.position();
            MessageInfo receive = javaChannel.receive(internalNioBuffer, (Object) null, this.j);
            if (receive == null) {
                return 0;
            }
            recvBufAllocHandle.lastBytesRead(internalNioBuffer.position() - position);
            list.add(new SctpMessage(receive, allocate.writerIndex(allocate.writerIndex() + recvBufAllocHandle.lastBytesRead())));
            return 1;
        } finally {
            allocate.release();
        }
    }

    @Override // io.netty.channel.nio.AbstractNioMessageChannel
    protected boolean doWriteMessage(Object obj, ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        ByteBuffer byteBuffer;
        SctpMessage sctpMessage = (SctpMessage) obj;
        ByteBuf content = sctpMessage.content();
        int readableBytes = content.readableBytes();
        if (readableBytes == 0) {
            return true;
        }
        ByteBufAllocator alloc = alloc();
        boolean z = content.nioBufferCount() != 1;
        if (!z && !content.isDirect() && alloc.isDirectBufferPooled()) {
            z = true;
        }
        if (!z) {
            byteBuffer = content.nioBuffer();
        } else {
            byteBuffer = alloc.directBuffer(readableBytes).writeBytes(content).nioBuffer();
        }
        MessageInfo createOutgoing = MessageInfo.createOutgoing(association(), (SocketAddress) null, sctpMessage.streamIdentifier());
        createOutgoing.payloadProtocolID(sctpMessage.protocolIdentifier());
        createOutgoing.streamNumber(sctpMessage.streamIdentifier());
        createOutgoing.unordered(sctpMessage.isUnordered());
        return javaChannel().send(byteBuffer, createOutgoing) > 0;
    }

    @Override // io.netty.channel.AbstractChannel
    protected final Object filterOutboundMessage(Object obj) throws Exception {
        if (obj instanceof SctpMessage) {
            SctpMessage sctpMessage = (SctpMessage) obj;
            ByteBuf content = sctpMessage.content();
            return (!content.isDirect() || content.nioBufferCount() != 1) ? new SctpMessage(sctpMessage.protocolIdentifier(), sctpMessage.streamIdentifier(), sctpMessage.isUnordered(), newDirectBuffer(sctpMessage, content)) : sctpMessage;
        }
        throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(obj) + " (expected: " + StringUtil.simpleClassName((Class<?>) SctpMessage.class));
    }

    @Override // io.netty.channel.sctp.SctpChannel
    public ChannelFuture bindAddress(InetAddress inetAddress) {
        return bindAddress(inetAddress, newPromise());
    }

    @Override // io.netty.channel.sctp.SctpChannel
    public ChannelFuture bindAddress(final InetAddress inetAddress, final ChannelPromise channelPromise) {
        if (eventLoop().inEventLoop()) {
            try {
                javaChannel().bindAddress(inetAddress);
                channelPromise.setSuccess();
            } catch (Throwable th) {
                channelPromise.setFailure(th);
            }
        } else {
            eventLoop().execute(new OneTimeTask() { // from class: io.netty.channel.sctp.nio.NioSctpChannel.1
                @Override // java.lang.Runnable
                public void run() {
                    NioSctpChannel.this.bindAddress(inetAddress, channelPromise);
                }
            });
        }
        return channelPromise;
    }

    @Override // io.netty.channel.sctp.SctpChannel
    public ChannelFuture unbindAddress(InetAddress inetAddress) {
        return unbindAddress(inetAddress, newPromise());
    }

    @Override // io.netty.channel.sctp.SctpChannel
    public ChannelFuture unbindAddress(final InetAddress inetAddress, final ChannelPromise channelPromise) {
        if (eventLoop().inEventLoop()) {
            try {
                javaChannel().unbindAddress(inetAddress);
                channelPromise.setSuccess();
            } catch (Throwable th) {
                channelPromise.setFailure(th);
            }
        } else {
            eventLoop().execute(new OneTimeTask() { // from class: io.netty.channel.sctp.nio.NioSctpChannel.2
                @Override // java.lang.Runnable
                public void run() {
                    NioSctpChannel.this.unbindAddress(inetAddress, channelPromise);
                }
            });
        }
        return channelPromise;
    }

    /* loaded from: classes4.dex */
    private final class a extends DefaultSctpChannelConfig {
        private a(NioSctpChannel nioSctpChannel, com.sun.nio.sctp.SctpChannel sctpChannel) {
            super(nioSctpChannel, sctpChannel);
        }

        @Override // io.netty.channel.DefaultChannelConfig
        protected void autoReadCleared() {
            NioSctpChannel.this.clearReadPending();
        }
    }
}
