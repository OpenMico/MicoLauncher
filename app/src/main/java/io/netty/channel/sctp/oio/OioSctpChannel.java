package io.netty.channel.sctp.oio;

import com.sun.nio.sctp.Association;
import com.sun.nio.sctp.MessageInfo;
import com.sun.nio.sctp.NotificationHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPromise;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.oio.AbstractOioMessageChannel;
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
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/* loaded from: classes4.dex */
public class OioSctpChannel extends AbstractOioMessageChannel implements SctpChannel {
    private static final InternalLogger d = InternalLoggerFactory.getInstance(OioSctpChannel.class);
    private static final ChannelMetadata e = new ChannelMetadata(false);
    private static final String f = " (expected: " + StringUtil.simpleClassName((Class<?>) SctpMessage.class) + ')';
    private final com.sun.nio.sctp.SctpChannel g;
    private final SctpChannelConfig h;
    private final Selector i;
    private final Selector j;
    private final Selector k;
    private final NotificationHandler<?> l;

    private static com.sun.nio.sctp.SctpChannel b() {
        try {
            return com.sun.nio.sctp.SctpChannel.open();
        } catch (IOException e2) {
            throw new ChannelException("Failed to open a sctp channel.", e2);
        }
    }

    public OioSctpChannel() {
        this(b());
    }

    public OioSctpChannel(com.sun.nio.sctp.SctpChannel sctpChannel) {
        this(null, sctpChannel);
    }

    public OioSctpChannel(Channel channel, com.sun.nio.sctp.SctpChannel sctpChannel) {
        super(channel);
        this.g = sctpChannel;
        try {
            try {
                sctpChannel.configureBlocking(false);
                this.i = Selector.open();
                this.j = Selector.open();
                this.k = Selector.open();
                sctpChannel.register(this.i, 1);
                sctpChannel.register(this.j, 4);
                sctpChannel.register(this.k, 8);
                this.h = new a(this, sctpChannel);
                this.l = new SctpNotificationHandler(this);
            } catch (Exception e2) {
                throw new ChannelException("failed to initialize a sctp channel", e2);
            }
        } catch (Throwable th) {
            try {
                sctpChannel.close();
            } catch (IOException e3) {
                d.warn("Failed to close a sctp channel.", (Throwable) e3);
            }
            throw th;
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
        return e;
    }

    @Override // io.netty.channel.Channel
    public SctpChannelConfig config() {
        return this.h;
    }

    @Override // io.netty.channel.Channel
    public boolean isOpen() {
        return this.g.isOpen();
    }

    @Override // io.netty.channel.oio.AbstractOioMessageChannel
    protected int doReadMessages(List<Object> list) throws Exception {
        if (!this.i.isOpen()) {
            return 0;
        }
        if (!(this.i.select(1000L) > 0)) {
            return 0;
        }
        this.i.selectedKeys().clear();
        RecvByteBufAllocator.Handle recvBufAllocHandle = unsafe().recvBufAllocHandle();
        ByteBuf allocate = recvBufAllocHandle.allocate(config().getAllocator());
        try {
            ByteBuffer nioBuffer = allocate.nioBuffer(allocate.writerIndex(), allocate.writableBytes());
            MessageInfo receive = this.g.receive(nioBuffer, (Object) null, this.l);
            if (receive == null) {
                return 0;
            }
            nioBuffer.flip();
            recvBufAllocHandle.lastBytesRead(nioBuffer.remaining());
            list.add(new SctpMessage(receive, allocate.writerIndex(allocate.writerIndex() + recvBufAllocHandle.lastBytesRead())));
            return 1;
        } finally {
            allocate.release();
        }
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doWrite(ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        ByteBuffer byteBuffer;
        if (this.j.isOpen()) {
            int size = channelOutboundBuffer.size();
            if (this.j.select(1000L) > 0) {
                Set<SelectionKey> selectedKeys = this.j.selectedKeys();
                if (!selectedKeys.isEmpty()) {
                    Iterator<SelectionKey> it = selectedKeys.iterator();
                    int i = 0;
                    while (i != size) {
                        it.next();
                        it.remove();
                        SctpMessage sctpMessage = (SctpMessage) channelOutboundBuffer.current();
                        if (sctpMessage != null) {
                            ByteBuf content = sctpMessage.content();
                            int readableBytes = content.readableBytes();
                            if (content.nioBufferCount() != -1) {
                                byteBuffer = content.nioBuffer();
                            } else {
                                ByteBuffer allocate = ByteBuffer.allocate(readableBytes);
                                content.getBytes(content.readerIndex(), allocate);
                                allocate.flip();
                                byteBuffer = allocate;
                            }
                            MessageInfo createOutgoing = MessageInfo.createOutgoing(association(), (SocketAddress) null, sctpMessage.streamIdentifier());
                            createOutgoing.payloadProtocolID(sctpMessage.protocolIdentifier());
                            createOutgoing.streamNumber(sctpMessage.streamIdentifier());
                            createOutgoing.unordered(sctpMessage.isUnordered());
                            this.g.send(byteBuffer, createOutgoing);
                            i++;
                            channelOutboundBuffer.remove();
                            if (!it.hasNext()) {
                                return;
                            }
                        } else {
                            return;
                        }
                    }
                }
            }
        }
    }

    @Override // io.netty.channel.AbstractChannel
    protected Object filterOutboundMessage(Object obj) throws Exception {
        if (obj instanceof SctpMessage) {
            return obj;
        }
        throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(obj) + f);
    }

    @Override // io.netty.channel.sctp.SctpChannel
    public Association association() {
        try {
            return this.g.association();
        } catch (IOException unused) {
            return null;
        }
    }

    @Override // io.netty.channel.Channel
    public boolean isActive() {
        return isOpen() && association() != null;
    }

    @Override // io.netty.channel.AbstractChannel
    protected SocketAddress localAddress0() {
        try {
            Iterator it = this.g.getAllLocalAddresses().iterator();
            if (it.hasNext()) {
                return (SocketAddress) it.next();
            }
            return null;
        } catch (IOException unused) {
            return null;
        }
    }

    @Override // io.netty.channel.sctp.SctpChannel
    public Set<InetSocketAddress> allLocalAddresses() {
        try {
            Set<SocketAddress> allLocalAddresses = this.g.getAllLocalAddresses();
            LinkedHashSet linkedHashSet = new LinkedHashSet(allLocalAddresses.size());
            for (SocketAddress socketAddress : allLocalAddresses) {
                linkedHashSet.add((InetSocketAddress) socketAddress);
            }
            return linkedHashSet;
        } catch (Throwable unused) {
            return Collections.emptySet();
        }
    }

    @Override // io.netty.channel.AbstractChannel
    protected SocketAddress remoteAddress0() {
        try {
            Iterator it = this.g.getRemoteAddresses().iterator();
            if (it.hasNext()) {
                return (SocketAddress) it.next();
            }
            return null;
        } catch (IOException unused) {
            return null;
        }
    }

    @Override // io.netty.channel.sctp.SctpChannel
    public Set<InetSocketAddress> allRemoteAddresses() {
        try {
            Set<SocketAddress> remoteAddresses = this.g.getRemoteAddresses();
            LinkedHashSet linkedHashSet = new LinkedHashSet(remoteAddresses.size());
            for (SocketAddress socketAddress : remoteAddresses) {
                linkedHashSet.add((InetSocketAddress) socketAddress);
            }
            return linkedHashSet;
        } catch (Throwable unused) {
            return Collections.emptySet();
        }
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doBind(SocketAddress socketAddress) throws Exception {
        this.g.bind(socketAddress);
    }

    @Override // io.netty.channel.oio.AbstractOioChannel
    protected void doConnect(SocketAddress socketAddress, SocketAddress socketAddress2) throws Exception {
        if (socketAddress2 != null) {
            this.g.bind(socketAddress2);
        }
        try {
            this.g.connect(socketAddress);
            boolean z = false;
            while (!z) {
                if (this.k.select(1000L) >= 0) {
                    Set<SelectionKey> selectedKeys = this.k.selectedKeys();
                    Iterator<SelectionKey> it = selectedKeys.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            if (it.next().isConnectable()) {
                                selectedKeys.clear();
                                z = true;
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                    selectedKeys.clear();
                }
            }
            if (this.g.finishConnect()) {
            }
        } finally {
            doClose();
        }
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doDisconnect() throws Exception {
        doClose();
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doClose() throws Exception {
        a("read", this.i);
        a("write", this.j);
        a("connect", this.k);
        this.g.close();
    }

    private static void a(String str, Selector selector) {
        try {
            selector.close();
        } catch (IOException e2) {
            InternalLogger internalLogger = d;
            internalLogger.warn("Failed to close a " + str + " selector.", (Throwable) e2);
        }
    }

    @Override // io.netty.channel.sctp.SctpChannel
    public ChannelFuture bindAddress(InetAddress inetAddress) {
        return bindAddress(inetAddress, newPromise());
    }

    @Override // io.netty.channel.sctp.SctpChannel
    public ChannelFuture bindAddress(final InetAddress inetAddress, final ChannelPromise channelPromise) {
        if (eventLoop().inEventLoop()) {
            try {
                this.g.bindAddress(inetAddress);
                channelPromise.setSuccess();
            } catch (Throwable th) {
                channelPromise.setFailure(th);
            }
        } else {
            eventLoop().execute(new OneTimeTask() { // from class: io.netty.channel.sctp.oio.OioSctpChannel.1
                @Override // java.lang.Runnable
                public void run() {
                    OioSctpChannel.this.bindAddress(inetAddress, channelPromise);
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
                this.g.unbindAddress(inetAddress);
                channelPromise.setSuccess();
            } catch (Throwable th) {
                channelPromise.setFailure(th);
            }
        } else {
            eventLoop().execute(new OneTimeTask() { // from class: io.netty.channel.sctp.oio.OioSctpChannel.2
                @Override // java.lang.Runnable
                public void run() {
                    OioSctpChannel.this.unbindAddress(inetAddress, channelPromise);
                }
            });
        }
        return channelPromise;
    }

    /* loaded from: classes4.dex */
    private final class a extends DefaultSctpChannelConfig {
        private a(OioSctpChannel oioSctpChannel, com.sun.nio.sctp.SctpChannel sctpChannel) {
            super(oioSctpChannel, sctpChannel);
        }

        @Override // io.netty.channel.DefaultChannelConfig
        protected void autoReadCleared() {
            OioSctpChannel.this.clearReadPending();
        }
    }
}
