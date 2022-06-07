package io.netty.handler.proxy;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.PendingWriteQueue;
import io.netty.handler.codec.rtsp.RtspHeaders;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.ScheduledFuture;
import io.netty.util.internal.OneTimeTask;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.SocketAddress;
import java.nio.channels.ConnectionPendingException;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public abstract class ProxyHandler extends ChannelDuplexHandler {
    private static final InternalLogger a = InternalLoggerFactory.getInstance(ProxyHandler.class);
    private final SocketAddress b;
    private volatile SocketAddress c;
    private volatile ChannelHandlerContext e;
    private PendingWriteQueue f;
    private boolean g;
    private boolean h;
    private boolean i;
    private ScheduledFuture<?> k;
    private volatile long d = 10000;
    private final a j = new a();
    private final ChannelFutureListener l = new ChannelFutureListener() { // from class: io.netty.handler.proxy.ProxyHandler.1
        /* renamed from: a */
        public void operationComplete(ChannelFuture channelFuture) throws Exception {
            if (!channelFuture.isSuccess()) {
                ProxyHandler.this.a(channelFuture.cause());
            }
        }
    };

    protected abstract void addCodec(ChannelHandlerContext channelHandlerContext) throws Exception;

    public abstract String authScheme();

    protected abstract boolean handleResponse(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception;

    protected abstract Object newInitialMessage(ChannelHandlerContext channelHandlerContext) throws Exception;

    public abstract String protocol();

    protected abstract void removeDecoder(ChannelHandlerContext channelHandlerContext) throws Exception;

    protected abstract void removeEncoder(ChannelHandlerContext channelHandlerContext) throws Exception;

    /* JADX INFO: Access modifiers changed from: protected */
    public ProxyHandler(SocketAddress socketAddress) {
        if (socketAddress != null) {
            this.b = socketAddress;
            return;
        }
        throw new NullPointerException("proxyAddress");
    }

    public final <T extends SocketAddress> T proxyAddress() {
        return (T) this.b;
    }

    public final <T extends SocketAddress> T destinationAddress() {
        return (T) this.c;
    }

    public final boolean isConnected() {
        return this.j.isSuccess();
    }

    public final Future<Channel> connectFuture() {
        return this.j;
    }

    public final long connectTimeoutMillis() {
        return this.d;
    }

    public final void setConnectTimeoutMillis(long j) {
        if (j <= 0) {
            j = 0;
        }
        this.d = j;
    }

    @Override // io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler
    public final void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.e = channelHandlerContext;
        addCodec(channelHandlerContext);
        if (channelHandlerContext.channel().isActive()) {
            a(channelHandlerContext);
        }
    }

    @Override // io.netty.channel.ChannelDuplexHandler, io.netty.channel.ChannelOutboundHandler
    public final void connect(ChannelHandlerContext channelHandlerContext, SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) throws Exception {
        if (this.c != null) {
            channelPromise.setFailure((Throwable) new ConnectionPendingException());
            return;
        }
        this.c = socketAddress;
        channelHandlerContext.connect(this.b, socketAddress2, channelPromise);
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public final void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
        a(channelHandlerContext);
        channelHandlerContext.fireChannelActive();
    }

    private void a(ChannelHandlerContext channelHandlerContext) throws Exception {
        long j = this.d;
        if (j > 0) {
            this.k = channelHandlerContext.executor().schedule((Runnable) new OneTimeTask() { // from class: io.netty.handler.proxy.ProxyHandler.2
                @Override // java.lang.Runnable
                public void run() {
                    if (!ProxyHandler.this.j.isDone()) {
                        ProxyHandler proxyHandler = ProxyHandler.this;
                        proxyHandler.a(new ProxyConnectException(proxyHandler.exceptionMessage(RtspHeaders.Values.TIMEOUT)));
                    }
                }
            }, j, TimeUnit.MILLISECONDS);
        }
        Object newInitialMessage = newInitialMessage(channelHandlerContext);
        if (newInitialMessage != null) {
            sendToProxyServer(newInitialMessage);
        }
    }

    protected final void sendToProxyServer(Object obj) {
        this.e.writeAndFlush(obj).addListener((GenericFutureListener<? extends Future<? super Void>>) this.l);
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public final void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (this.g) {
            channelHandlerContext.fireChannelInactive();
        } else {
            a(new ProxyConnectException(exceptionMessage("disconnected")));
        }
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler, io.netty.channel.ChannelInboundHandler
    public final void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable th) throws Exception {
        if (this.g) {
            channelHandlerContext.fireExceptionCaught(th);
        } else {
            a(th);
        }
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public final void channelRead(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
        if (this.g) {
            this.h = false;
            channelHandlerContext.fireChannelRead(obj);
            return;
        }
        this.h = true;
        try {
            if (handleResponse(channelHandlerContext, obj)) {
                a();
            }
            ReferenceCountUtil.release(obj);
        } catch (Throwable th) {
            ReferenceCountUtil.release(obj);
            throw th;
        }
    }

    private void a() {
        this.g = true;
        ScheduledFuture<?> scheduledFuture = this.k;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(false);
        }
        if (this.j.trySuccess(this.e.channel())) {
            boolean c = true & c();
            this.e.fireUserEventTriggered((Object) new ProxyConnectionEvent(protocol(), authScheme(), this.b, this.c));
            if (c && b()) {
                d();
                if (this.i) {
                    this.e.flush();
                    return;
                }
                return;
            }
            ProxyConnectException proxyConnectException = new ProxyConnectException("failed to remove all codec handlers added by the proxy handler; bug?");
            b(proxyConnectException);
            this.e.fireExceptionCaught((Throwable) proxyConnectException);
            this.e.close();
        }
    }

    private boolean b() {
        try {
            removeDecoder(this.e);
            return true;
        } catch (Exception e) {
            a.warn("Failed to remove proxy decoders:", (Throwable) e);
            return false;
        }
    }

    private boolean c() {
        try {
            removeEncoder(this.e);
            return true;
        } catch (Exception e) {
            a.warn("Failed to remove proxy encoders:", (Throwable) e);
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Throwable th) {
        this.g = true;
        ScheduledFuture<?> scheduledFuture = this.k;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(false);
        }
        if (!(th instanceof ProxyConnectException)) {
            th = new ProxyConnectException(exceptionMessage(th.toString()), th);
        }
        if (this.j.tryFailure(th)) {
            b();
            c();
            b(th);
            this.e.fireExceptionCaught(th);
            this.e.close();
        }
    }

    protected final String exceptionMessage(String str) {
        if (str == null) {
            str = "";
        }
        StringBuilder sb = new StringBuilder(str.length() + 128);
        sb.append(protocol());
        sb.append(", ");
        sb.append(authScheme());
        sb.append(", ");
        sb.append(this.b);
        sb.append(" => ");
        sb.append(this.c);
        if (!str.isEmpty()) {
            sb.append(", ");
            sb.append(str);
        }
        return sb.toString();
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public final void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (this.h) {
            this.h = false;
            if (!channelHandlerContext.channel().config().isAutoRead()) {
                channelHandlerContext.read();
                return;
            }
            return;
        }
        channelHandlerContext.fireChannelReadComplete();
    }

    @Override // io.netty.channel.ChannelDuplexHandler, io.netty.channel.ChannelOutboundHandler
    public final void write(ChannelHandlerContext channelHandlerContext, Object obj, ChannelPromise channelPromise) throws Exception {
        if (this.g) {
            d();
            channelHandlerContext.write(obj, channelPromise);
            return;
        }
        a(channelHandlerContext, obj, channelPromise);
    }

    @Override // io.netty.channel.ChannelDuplexHandler, io.netty.channel.ChannelOutboundHandler
    public final void flush(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (this.g) {
            d();
            channelHandlerContext.flush();
            return;
        }
        this.i = true;
    }

    private void d() {
        PendingWriteQueue pendingWriteQueue = this.f;
        if (pendingWriteQueue != null) {
            pendingWriteQueue.removeAndWriteAll();
            this.f = null;
        }
    }

    private void b(Throwable th) {
        PendingWriteQueue pendingWriteQueue = this.f;
        if (pendingWriteQueue != null) {
            pendingWriteQueue.removeAndFailAll(th);
            this.f = null;
        }
    }

    private void a(ChannelHandlerContext channelHandlerContext, Object obj, ChannelPromise channelPromise) {
        PendingWriteQueue pendingWriteQueue = this.f;
        if (pendingWriteQueue == null) {
            pendingWriteQueue = new PendingWriteQueue(channelHandlerContext);
            this.f = pendingWriteQueue;
        }
        pendingWriteQueue.add(obj, channelPromise);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public final class a extends DefaultPromise<Channel> {
        private a() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // io.netty.util.concurrent.DefaultPromise
        public EventExecutor executor() {
            if (ProxyHandler.this.e != null) {
                return ProxyHandler.this.e.executor();
            }
            throw new IllegalStateException();
        }
    }
}
