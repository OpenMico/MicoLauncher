package io.netty.channel.udt.nio;

import com.barchart.udt.StatusUDT;
import com.barchart.udt.TypeUDT;
import com.barchart.udt.nio.SocketChannelUDT;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.nio.AbstractNioMessageChannel;
import io.netty.channel.udt.DefaultUdtChannelConfig;
import io.netty.channel.udt.UdtChannel;
import io.netty.channel.udt.UdtChannelConfig;
import io.netty.channel.udt.UdtMessage;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.ScatteringByteChannel;
import java.util.List;

/* loaded from: classes4.dex */
public class NioUdtMessageConnectorChannel extends AbstractNioMessageChannel implements UdtChannel {
    private static final InternalLogger g = InternalLoggerFactory.getInstance(NioUdtMessageConnectorChannel.class);
    private static final ChannelMetadata h = new ChannelMetadata(false);
    private final UdtChannelConfig i;

    public NioUdtMessageConnectorChannel() {
        this(TypeUDT.DATAGRAM);
    }

    public NioUdtMessageConnectorChannel(Channel channel, SocketChannelUDT socketChannelUDT) {
        super(channel, socketChannelUDT, 1);
        try {
            socketChannelUDT.configureBlocking(false);
            switch (AnonymousClass1.a[socketChannelUDT.socketUDT().status().ordinal()]) {
                case 1:
                case 2:
                    this.i = new DefaultUdtChannelConfig(this, socketChannelUDT, true);
                    return;
                default:
                    this.i = new DefaultUdtChannelConfig(this, socketChannelUDT, false);
                    return;
            }
        } catch (Exception e) {
            try {
                socketChannelUDT.close();
            } catch (Exception e2) {
                if (g.isWarnEnabled()) {
                    g.warn("Failed to close channel.", (Throwable) e2);
                }
            }
            throw new ChannelException("Failed to configure channel.", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: io.netty.channel.udt.nio.NioUdtMessageConnectorChannel$1 */
    /* loaded from: classes4.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a = new int[StatusUDT.values().length];

        static {
            try {
                a[StatusUDT.INIT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[StatusUDT.OPENED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public NioUdtMessageConnectorChannel(SocketChannelUDT socketChannelUDT) {
        this(null, socketChannelUDT);
    }

    public NioUdtMessageConnectorChannel(TypeUDT typeUDT) {
        this(NioUdtProvider.b(typeUDT));
    }

    @Override // io.netty.channel.Channel
    public UdtChannelConfig config() {
        return this.i;
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doBind(SocketAddress socketAddress) throws Exception {
        javaChannel().bind(socketAddress);
    }

    @Override // io.netty.channel.nio.AbstractNioChannel, io.netty.channel.AbstractChannel
    protected void doClose() throws Exception {
        javaChannel().close();
    }

    @Override // io.netty.channel.nio.AbstractNioChannel
    protected boolean doConnect(SocketAddress socketAddress, SocketAddress socketAddress2) throws Exception {
        if (socketAddress2 == null) {
            socketAddress2 = new InetSocketAddress(0);
        }
        doBind(socketAddress2);
        try {
            boolean connect = javaChannel().connect(socketAddress);
            if (!connect) {
                selectionKey().interestOps(selectionKey().interestOps() | 8);
            }
            return connect;
        } catch (Throwable th) {
            doClose();
            throw th;
        }
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doDisconnect() throws Exception {
        doClose();
    }

    @Override // io.netty.channel.nio.AbstractNioChannel
    protected void doFinishConnect() throws Exception {
        if (javaChannel().finishConnect()) {
            selectionKey().interestOps(selectionKey().interestOps() & (-9));
            return;
        }
        throw new Error("Provider error: failed to finish connect. Provider library should be upgraded.");
    }

    @Override // io.netty.channel.nio.AbstractNioMessageChannel
    protected int doReadMessages(List<Object> list) throws Exception {
        int receiveBufferSize = this.i.getReceiveBufferSize();
        ByteBuf directBuffer = this.i.getAllocator().directBuffer(receiveBufferSize);
        int writeBytes = directBuffer.writeBytes((ScatteringByteChannel) javaChannel(), receiveBufferSize);
        if (writeBytes <= 0) {
            directBuffer.release();
            return 0;
        } else if (writeBytes < receiveBufferSize) {
            list.add(new UdtMessage(directBuffer));
            return 1;
        } else {
            javaChannel().close();
            throw new ChannelException("Invalid config : increase receive buffer size to avoid message truncation");
        }
    }

    @Override // io.netty.channel.nio.AbstractNioMessageChannel
    protected boolean doWriteMessage(Object obj, ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        long j;
        ByteBuf content = ((UdtMessage) obj).content();
        int readableBytes = content.readableBytes();
        if (readableBytes == 0) {
            return true;
        }
        if (content.nioBufferCount() == 1) {
            j = javaChannel().write(content.nioBuffer());
        } else {
            j = javaChannel().write(content.nioBuffers());
        }
        int i = (j > 0L ? 1 : (j == 0L ? 0 : -1));
        if (i <= 0 || j == readableBytes) {
            return i > 0;
        }
        throw new Error("Provider error: failed to write message. Provider library should be upgraded.");
    }

    @Override // io.netty.channel.Channel
    public boolean isActive() {
        SocketChannelUDT javaChannel = javaChannel();
        return javaChannel.isOpen() && javaChannel.isConnectFinished();
    }

    @Override // io.netty.channel.nio.AbstractNioChannel
    public SocketChannelUDT javaChannel() {
        return super.javaChannel();
    }

    @Override // io.netty.channel.AbstractChannel
    protected SocketAddress localAddress0() {
        return javaChannel().socket().getLocalSocketAddress();
    }

    @Override // io.netty.channel.Channel
    public ChannelMetadata metadata() {
        return h;
    }

    @Override // io.netty.channel.AbstractChannel
    protected SocketAddress remoteAddress0() {
        return javaChannel().socket().getRemoteSocketAddress();
    }

    @Override // io.netty.channel.AbstractChannel, io.netty.channel.Channel
    public InetSocketAddress localAddress() {
        return (InetSocketAddress) super.localAddress();
    }

    @Override // io.netty.channel.AbstractChannel, io.netty.channel.Channel
    public InetSocketAddress remoteAddress() {
        return (InetSocketAddress) super.remoteAddress();
    }
}
