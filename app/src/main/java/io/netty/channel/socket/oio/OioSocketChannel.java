package io.netty.channel.socket.oio;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelPromise;
import io.netty.channel.ConnectTimeoutException;
import io.netty.channel.EventLoop;
import io.netty.channel.oio.OioByteStreamChannel;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.internal.OneTimeTask;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;

/* loaded from: classes4.dex */
public class OioSocketChannel extends OioByteStreamChannel implements SocketChannel {
    private static final InternalLogger d = InternalLoggerFactory.getInstance(OioSocketChannel.class);
    private final Socket e;
    private final OioSocketChannelConfig f;

    public OioSocketChannel() {
        this(new Socket());
    }

    public OioSocketChannel(Socket socket) {
        this(null, socket);
    }

    public OioSocketChannel(Channel channel, Socket socket) {
        try {
            super(channel);
            this.e = socket;
            this.f = new DefaultOioSocketChannelConfig(this, socket);
            try {
                if (socket.isConnected()) {
                    activate(socket.getInputStream(), socket.getOutputStream());
                }
                socket.setSoTimeout(1000);
            } catch (Exception e) {
                throw new ChannelException("failed to initialize a socket", e);
            }
        } catch (Throwable th) {
            try {
                socket.close();
            } catch (IOException e2) {
                d.warn("Failed to close a socket.", (Throwable) e2);
            }
            throw th;
        }
    }

    @Override // io.netty.channel.AbstractChannel, io.netty.channel.Channel
    public ServerSocketChannel parent() {
        return (ServerSocketChannel) super.parent();
    }

    @Override // io.netty.channel.Channel
    public OioSocketChannelConfig config() {
        return this.f;
    }

    @Override // io.netty.channel.Channel
    public boolean isOpen() {
        return !this.e.isClosed();
    }

    @Override // io.netty.channel.oio.OioByteStreamChannel, io.netty.channel.Channel
    public boolean isActive() {
        return !this.e.isClosed() && this.e.isConnected();
    }

    @Override // io.netty.channel.socket.DuplexChannel
    public boolean isOutputShutdown() {
        return this.e.isOutputShutdown() || !isActive();
    }

    @Override // io.netty.channel.oio.AbstractOioByteChannel
    public boolean isInputShutdown() {
        return this.e.isInputShutdown() || !isActive();
    }

    @Override // io.netty.channel.socket.DuplexChannel
    public boolean isShutdown() {
        return (this.e.isInputShutdown() && this.e.isOutputShutdown()) || !isActive();
    }

    @Override // io.netty.channel.socket.DuplexChannel
    public ChannelFuture shutdownOutput() {
        return shutdownOutput(newPromise());
    }

    @Override // io.netty.channel.oio.AbstractOioByteChannel
    public ChannelFuture shutdownInput() {
        return shutdownInput(newPromise());
    }

    @Override // io.netty.channel.socket.DuplexChannel
    public ChannelFuture shutdown() {
        return shutdown(newPromise());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.channel.oio.OioByteStreamChannel, io.netty.channel.oio.AbstractOioByteChannel
    public int doReadBytes(ByteBuf byteBuf) throws Exception {
        if (this.e.isClosed()) {
            return -1;
        }
        try {
            return super.doReadBytes(byteBuf);
        } catch (SocketTimeoutException unused) {
            return 0;
        }
    }

    @Override // io.netty.channel.socket.DuplexChannel
    public ChannelFuture shutdownOutput(final ChannelPromise channelPromise) {
        EventLoop eventLoop = eventLoop();
        if (eventLoop.inEventLoop()) {
            a(channelPromise);
        } else {
            eventLoop.execute(new OneTimeTask() { // from class: io.netty.channel.socket.oio.OioSocketChannel.1
                @Override // java.lang.Runnable
                public void run() {
                    OioSocketChannel.this.a(channelPromise);
                }
            });
        }
        return channelPromise;
    }

    public void a(ChannelPromise channelPromise) {
        try {
            this.e.shutdownOutput();
            channelPromise.setSuccess();
        } catch (Throwable th) {
            channelPromise.setFailure(th);
        }
    }

    @Override // io.netty.channel.socket.DuplexChannel
    public ChannelFuture shutdownInput(final ChannelPromise channelPromise) {
        EventLoop eventLoop = eventLoop();
        if (eventLoop.inEventLoop()) {
            b(channelPromise);
        } else {
            eventLoop.execute(new OneTimeTask() { // from class: io.netty.channel.socket.oio.OioSocketChannel.2
                @Override // java.lang.Runnable
                public void run() {
                    OioSocketChannel.this.b(channelPromise);
                }
            });
        }
        return channelPromise;
    }

    public void b(ChannelPromise channelPromise) {
        try {
            this.e.shutdownInput();
            channelPromise.setSuccess();
        } catch (Throwable th) {
            channelPromise.setFailure(th);
        }
    }

    @Override // io.netty.channel.socket.DuplexChannel
    public ChannelFuture shutdown(final ChannelPromise channelPromise) {
        EventLoop eventLoop = eventLoop();
        if (eventLoop.inEventLoop()) {
            c(channelPromise);
        } else {
            eventLoop.execute(new OneTimeTask() { // from class: io.netty.channel.socket.oio.OioSocketChannel.3
                @Override // java.lang.Runnable
                public void run() {
                    OioSocketChannel.this.c(channelPromise);
                }
            });
        }
        return channelPromise;
    }

    public void c(ChannelPromise channelPromise) {
        Throwable th;
        try {
            this.e.shutdownOutput();
            th = null;
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            this.e.shutdownInput();
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
            d.debug("Exception suppressed because a previous exception occurred.", th3);
            channelPromise.setFailure(th);
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

    @Override // io.netty.channel.AbstractChannel
    protected SocketAddress localAddress0() {
        return this.e.getLocalSocketAddress();
    }

    @Override // io.netty.channel.AbstractChannel
    protected SocketAddress remoteAddress0() {
        return this.e.getRemoteSocketAddress();
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doBind(SocketAddress socketAddress) throws Exception {
        this.e.bind(socketAddress);
    }

    @Override // io.netty.channel.oio.AbstractOioChannel
    protected void doConnect(SocketAddress socketAddress, SocketAddress socketAddress2) throws Exception {
        if (socketAddress2 != null) {
            this.e.bind(socketAddress2);
        }
        try {
            try {
                this.e.connect(socketAddress, config().getConnectTimeoutMillis());
                activate(this.e.getInputStream(), this.e.getOutputStream());
            } catch (SocketTimeoutException e) {
                ConnectTimeoutException connectTimeoutException = new ConnectTimeoutException("connection timed out: " + socketAddress);
                connectTimeoutException.setStackTrace(e.getStackTrace());
                throw connectTimeoutException;
            }
        } catch (Throwable th) {
            doClose();
            throw th;
        }
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doDisconnect() throws Exception {
        doClose();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.channel.oio.OioByteStreamChannel, io.netty.channel.AbstractChannel
    public void doClose() throws Exception {
        this.e.close();
    }

    protected boolean checkInputShutdown() {
        if (!isInputShutdown()) {
            return false;
        }
        try {
            Thread.sleep(config().getSoTimeout());
            return true;
        } catch (Throwable unused) {
            return true;
        }
    }

    @Override // io.netty.channel.oio.AbstractOioChannel
    @Deprecated
    protected void setReadPending(boolean z) {
        super.setReadPending(z);
    }

    public final void b() {
        clearReadPending();
    }
}
