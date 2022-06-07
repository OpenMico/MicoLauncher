package io.netty.channel.udt.nio;

import com.barchart.udt.StatusUDT;
import com.barchart.udt.TypeUDT;
import com.barchart.udt.nio.SocketChannelUDT;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.FileRegion;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.nio.AbstractNioByteChannel;
import io.netty.channel.udt.DefaultUdtChannelConfig;
import io.netty.channel.udt.UdtChannel;
import io.netty.channel.udt.UdtChannelConfig;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;

/* loaded from: classes4.dex */
public class NioUdtByteConnectorChannel extends AbstractNioByteChannel implements UdtChannel {
    private static final InternalLogger f = InternalLoggerFactory.getInstance(NioUdtByteConnectorChannel.class);
    private static final ChannelMetadata g = new ChannelMetadata(false, 16);
    private final UdtChannelConfig h;

    public NioUdtByteConnectorChannel() {
        this(TypeUDT.STREAM);
    }

    public NioUdtByteConnectorChannel(Channel channel, SocketChannelUDT socketChannelUDT) {
        super(channel, socketChannelUDT);
        try {
            socketChannelUDT.configureBlocking(false);
            switch (AnonymousClass1.a[socketChannelUDT.socketUDT().status().ordinal()]) {
                case 1:
                case 2:
                    this.h = new DefaultUdtChannelConfig(this, socketChannelUDT, true);
                    return;
                default:
                    this.h = new DefaultUdtChannelConfig(this, socketChannelUDT, false);
                    return;
            }
        } catch (Exception e) {
            try {
                socketChannelUDT.close();
            } catch (Exception e2) {
                if (f.isWarnEnabled()) {
                    f.warn("Failed to close channel.", (Throwable) e2);
                }
            }
            throw new ChannelException("Failed to configure channel.", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: io.netty.channel.udt.nio.NioUdtByteConnectorChannel$1 */
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

    public NioUdtByteConnectorChannel(SocketChannelUDT socketChannelUDT) {
        this(null, socketChannelUDT);
    }

    public NioUdtByteConnectorChannel(TypeUDT typeUDT) {
        this(NioUdtProvider.b(typeUDT));
    }

    @Override // io.netty.channel.Channel
    public UdtChannelConfig config() {
        return this.h;
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

    @Override // io.netty.channel.nio.AbstractNioByteChannel
    protected int doReadBytes(ByteBuf byteBuf) throws Exception {
        RecvByteBufAllocator.Handle recvBufAllocHandle = unsafe().recvBufAllocHandle();
        recvBufAllocHandle.attemptedBytesRead(byteBuf.writableBytes());
        return byteBuf.writeBytes((ScatteringByteChannel) javaChannel(), recvBufAllocHandle.attemptedBytesRead());
    }

    @Override // io.netty.channel.nio.AbstractNioByteChannel
    protected int doWriteBytes(ByteBuf byteBuf) throws Exception {
        return byteBuf.readBytes((GatheringByteChannel) javaChannel(), byteBuf.readableBytes());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.channel.nio.AbstractNioByteChannel, io.netty.channel.socket.DuplexChannel
    public ChannelFuture shutdownInput() {
        return newFailedFuture(new UnsupportedOperationException("shutdownInput"));
    }

    @Override // io.netty.channel.nio.AbstractNioByteChannel
    protected long doWriteFileRegion(FileRegion fileRegion) throws Exception {
        throw new UnsupportedOperationException();
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
        return g;
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
