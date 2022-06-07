package io.netty.channel.socket.nio;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPromise;
import io.netty.channel.FileRegion;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.nio.AbstractNioByteChannel;
import io.netty.channel.nio.AbstractNioChannel;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.socket.DefaultSocketChannelConfig;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.SocketChannelConfig;
import io.netty.util.concurrent.GlobalEventExecutor;
import io.netty.util.internal.OneTimeTask;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.spi.SelectorProvider;
import java.util.concurrent.Executor;

/* loaded from: classes4.dex */
public class NioSocketChannel extends AbstractNioByteChannel implements SocketChannel {
    private static final InternalLogger f = InternalLoggerFactory.getInstance(NioSocketChannel.class);
    private static final ChannelMetadata g = new ChannelMetadata(false, 16);
    private static final SelectorProvider h = SelectorProvider.provider();
    private final SocketChannelConfig i;

    private static java.nio.channels.SocketChannel a(SelectorProvider selectorProvider) {
        try {
            return selectorProvider.openSocketChannel();
        } catch (IOException e) {
            throw new ChannelException("Failed to open a socket.", e);
        }
    }

    public NioSocketChannel() {
        this(h);
    }

    public NioSocketChannel(SelectorProvider selectorProvider) {
        this(a(selectorProvider));
    }

    public NioSocketChannel(java.nio.channels.SocketChannel socketChannel) {
        this(null, socketChannel);
    }

    public NioSocketChannel(Channel channel, java.nio.channels.SocketChannel socketChannel) {
        super(channel, socketChannel);
        this.i = new a(this, socketChannel.socket());
    }

    @Override // io.netty.channel.AbstractChannel, io.netty.channel.Channel
    public ServerSocketChannel parent() {
        return (ServerSocketChannel) super.parent();
    }

    @Override // io.netty.channel.Channel
    public ChannelMetadata metadata() {
        return g;
    }

    @Override // io.netty.channel.Channel
    public SocketChannelConfig config() {
        return this.i;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.channel.nio.AbstractNioChannel
    public java.nio.channels.SocketChannel javaChannel() {
        return (java.nio.channels.SocketChannel) super.javaChannel();
    }

    @Override // io.netty.channel.Channel
    public boolean isActive() {
        java.nio.channels.SocketChannel javaChannel = javaChannel();
        return javaChannel.isOpen() && javaChannel.isConnected();
    }

    @Override // io.netty.channel.socket.DuplexChannel
    public boolean isOutputShutdown() {
        return javaChannel().socket().isOutputShutdown() || !isActive();
    }

    @Override // io.netty.channel.socket.DuplexChannel
    public boolean isInputShutdown() {
        return javaChannel().socket().isInputShutdown() || !isActive();
    }

    @Override // io.netty.channel.socket.DuplexChannel
    public boolean isShutdown() {
        Socket socket = javaChannel().socket();
        return (socket.isInputShutdown() && socket.isOutputShutdown()) || !isActive();
    }

    @Override // io.netty.channel.AbstractChannel, io.netty.channel.Channel
    public InetSocketAddress localAddress() {
        return (InetSocketAddress) super.localAddress();
    }

    @Override // io.netty.channel.AbstractChannel, io.netty.channel.Channel
    public InetSocketAddress remoteAddress() {
        return (InetSocketAddress) super.remoteAddress();
    }

    @Override // io.netty.channel.socket.DuplexChannel
    public ChannelFuture shutdownOutput() {
        return shutdownOutput(newPromise());
    }

    @Override // io.netty.channel.socket.DuplexChannel
    public ChannelFuture shutdownOutput(final ChannelPromise channelPromise) {
        Executor prepareToClose = ((b) unsafe()).prepareToClose();
        if (prepareToClose != null) {
            prepareToClose.execute(new OneTimeTask() { // from class: io.netty.channel.socket.nio.NioSocketChannel.1
                @Override // java.lang.Runnable
                public void run() {
                    NioSocketChannel.this.a(channelPromise);
                }
            });
        } else {
            NioEventLoop eventLoop = eventLoop();
            if (eventLoop.inEventLoop()) {
                a(channelPromise);
            } else {
                eventLoop.execute(new OneTimeTask() { // from class: io.netty.channel.socket.nio.NioSocketChannel.2
                    @Override // java.lang.Runnable
                    public void run() {
                        NioSocketChannel.this.a(channelPromise);
                    }
                });
            }
        }
        return channelPromise;
    }

    @Override // io.netty.channel.nio.AbstractNioByteChannel, io.netty.channel.socket.DuplexChannel
    public ChannelFuture shutdownInput() {
        return shutdownInput(newPromise());
    }

    @Override // io.netty.channel.socket.DuplexChannel
    public ChannelFuture shutdownInput(final ChannelPromise channelPromise) {
        Executor prepareToClose = ((b) unsafe()).prepareToClose();
        if (prepareToClose != null) {
            prepareToClose.execute(new OneTimeTask() { // from class: io.netty.channel.socket.nio.NioSocketChannel.3
                @Override // java.lang.Runnable
                public void run() {
                    NioSocketChannel.this.b(channelPromise);
                }
            });
        } else {
            NioEventLoop eventLoop = eventLoop();
            if (eventLoop.inEventLoop()) {
                b(channelPromise);
            } else {
                eventLoop.execute(new OneTimeTask() { // from class: io.netty.channel.socket.nio.NioSocketChannel.4
                    @Override // java.lang.Runnable
                    public void run() {
                        NioSocketChannel.this.b(channelPromise);
                    }
                });
            }
        }
        return channelPromise;
    }

    @Override // io.netty.channel.socket.DuplexChannel
    public ChannelFuture shutdown() {
        return shutdown(newPromise());
    }

    @Override // io.netty.channel.socket.DuplexChannel
    public ChannelFuture shutdown(final ChannelPromise channelPromise) {
        Executor prepareToClose = ((b) unsafe()).prepareToClose();
        if (prepareToClose != null) {
            prepareToClose.execute(new OneTimeTask() { // from class: io.netty.channel.socket.nio.NioSocketChannel.5
                @Override // java.lang.Runnable
                public void run() {
                    NioSocketChannel.this.c(channelPromise);
                }
            });
        } else {
            NioEventLoop eventLoop = eventLoop();
            if (eventLoop.inEventLoop()) {
                c(channelPromise);
            } else {
                eventLoop.execute(new OneTimeTask() { // from class: io.netty.channel.socket.nio.NioSocketChannel.6
                    @Override // java.lang.Runnable
                    public void run() {
                        NioSocketChannel.this.c(channelPromise);
                    }
                });
            }
        }
        return channelPromise;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(ChannelPromise channelPromise) {
        try {
            javaChannel().socket().shutdownOutput();
            channelPromise.setSuccess();
        } catch (Throwable th) {
            channelPromise.setFailure(th);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(ChannelPromise channelPromise) {
        try {
            javaChannel().socket().shutdownInput();
            channelPromise.setSuccess();
        } catch (Throwable th) {
            channelPromise.setFailure(th);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(ChannelPromise channelPromise) {
        Throwable th;
        Socket socket = javaChannel().socket();
        try {
            socket.shutdownOutput();
            th = null;
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            socket.shutdownInput();
            if (th == null) {
                channelPromise.setSuccess();
            } else {
                channelPromise.setFailure(th);
            }
        } catch (Throwable th3) {
            if (th == null) {
                channelPromise.setFailure(th3);
                return;
            }
            f.debug("Exception suppressed because a previous exception occurred.", th3);
            channelPromise.setFailure(th);
        }
    }

    @Override // io.netty.channel.AbstractChannel
    protected SocketAddress localAddress0() {
        return javaChannel().socket().getLocalSocketAddress();
    }

    @Override // io.netty.channel.AbstractChannel
    protected SocketAddress remoteAddress0() {
        return javaChannel().socket().getRemoteSocketAddress();
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doBind(SocketAddress socketAddress) throws Exception {
        javaChannel().socket().bind(socketAddress);
    }

    @Override // io.netty.channel.nio.AbstractNioChannel
    protected boolean doConnect(SocketAddress socketAddress, SocketAddress socketAddress2) throws Exception {
        if (socketAddress2 != null) {
            javaChannel().socket().bind(socketAddress2);
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
        super.doClose();
        javaChannel().close();
    }

    @Override // io.netty.channel.nio.AbstractNioByteChannel
    protected int doReadBytes(ByteBuf byteBuf) throws Exception {
        RecvByteBufAllocator.Handle recvBufAllocHandle = unsafe().recvBufAllocHandle();
        recvBufAllocHandle.attemptedBytesRead(byteBuf.writableBytes());
        return byteBuf.writeBytes(javaChannel(), recvBufAllocHandle.attemptedBytesRead());
    }

    @Override // io.netty.channel.nio.AbstractNioByteChannel
    protected int doWriteBytes(ByteBuf byteBuf) throws Exception {
        return byteBuf.readBytes(javaChannel(), byteBuf.readableBytes());
    }

    @Override // io.netty.channel.nio.AbstractNioByteChannel
    protected long doWriteFileRegion(FileRegion fileRegion) throws Exception {
        return fileRegion.transferTo(javaChannel(), fileRegion.transferred());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.channel.nio.AbstractNioByteChannel, io.netty.channel.AbstractChannel
    public void doWrite(ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        long j;
        ChannelOutboundBuffer channelOutboundBuffer2;
        while (channelOutboundBuffer.size() != 0) {
            ByteBuffer[] nioBuffers = channelOutboundBuffer.nioBuffers();
            int nioBufferCount = channelOutboundBuffer.nioBufferCount();
            long nioBufferSize = channelOutboundBuffer.nioBufferSize();
            java.nio.channels.SocketChannel javaChannel = javaChannel();
            boolean z = false;
            boolean z2 = true;
            switch (nioBufferCount) {
                case 0:
                    super.doWrite(channelOutboundBuffer);
                    return;
                case 1:
                    ByteBuffer byteBuffer = nioBuffers[0];
                    int writeSpinCount = config().getWriteSpinCount() - 1;
                    j = 0;
                    while (true) {
                        if (writeSpinCount < 0) {
                            z2 = false;
                        } else {
                            int write = javaChannel.write(byteBuffer);
                            if (write != 0) {
                                long j2 = write;
                                nioBufferSize -= j2;
                                j += j2;
                                if (nioBufferSize == 0) {
                                    z2 = false;
                                    z = true;
                                } else {
                                    writeSpinCount--;
                                }
                            }
                        }
                    }
                    channelOutboundBuffer2 = channelOutboundBuffer;
                    break;
                default:
                    int writeSpinCount2 = config().getWriteSpinCount() - 1;
                    j = 0;
                    while (true) {
                        if (writeSpinCount2 < 0) {
                            channelOutboundBuffer2 = channelOutboundBuffer;
                            z2 = false;
                            break;
                        } else {
                            long write2 = javaChannel.write(nioBuffers, 0, nioBufferCount);
                            if (write2 == 0) {
                                channelOutboundBuffer2 = channelOutboundBuffer;
                                break;
                            } else {
                                nioBufferSize -= write2;
                                j += write2;
                                if (nioBufferSize == 0) {
                                    channelOutboundBuffer2 = channelOutboundBuffer;
                                    z2 = false;
                                    z = true;
                                    break;
                                } else {
                                    writeSpinCount2--;
                                }
                            }
                        }
                    }
            }
            channelOutboundBuffer2.removeBytes(j);
            if (!z) {
                incompleteWrite(z2);
                return;
            }
        }
        clearOpWrite();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.channel.nio.AbstractNioByteChannel, io.netty.channel.AbstractChannel
    public AbstractNioChannel.AbstractNioUnsafe newUnsafe() {
        return new b();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public final class b extends AbstractNioByteChannel.NioByteUnsafe {
        private b() {
            super();
        }

        @Override // io.netty.channel.AbstractChannel.AbstractUnsafe
        protected Executor prepareToClose() {
            try {
                if (!NioSocketChannel.this.javaChannel().isOpen() || NioSocketChannel.this.config().getSoLinger() <= 0) {
                    return null;
                }
                NioSocketChannel.this.doDeregister();
                return GlobalEventExecutor.INSTANCE;
            } catch (Throwable unused) {
                return null;
            }
        }
    }

    /* loaded from: classes4.dex */
    private final class a extends DefaultSocketChannelConfig {
        private a(NioSocketChannel nioSocketChannel, Socket socket) {
            super(nioSocketChannel, socket);
        }

        @Override // io.netty.channel.DefaultChannelConfig
        protected void autoReadCleared() {
            NioSocketChannel.this.clearReadPending();
        }
    }
}
