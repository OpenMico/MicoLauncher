package io.netty.channel.embedded;

import io.netty.channel.AbstractChannel;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelId;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.channel.DefaultChannelConfig;
import io.netty.channel.DefaultChannelPipeline;
import io.netty.channel.EventLoop;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.RecyclableArrayList;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.SocketAddress;
import java.nio.channels.ClosedChannelException;
import java.util.ArrayDeque;
import java.util.Queue;

/* loaded from: classes4.dex */
public class EmbeddedChannel extends AbstractChannel {
    static final /* synthetic */ boolean c = !EmbeddedChannel.class.desiredAssertionStatus();
    private static final SocketAddress d = new c();
    private static final SocketAddress e = new c();
    private static final ChannelHandler[] f = new ChannelHandler[0];
    private static final InternalLogger g = InternalLoggerFactory.getInstance(EmbeddedChannel.class);
    private static final ChannelMetadata h = new ChannelMetadata(false);
    private static final ChannelMetadata i = new ChannelMetadata(true);
    private final b j;
    private final ChannelFutureListener k;
    private final ChannelMetadata l;
    private final ChannelConfig m;
    private Queue<Object> n;
    private Queue<Object> o;
    private Throwable p;
    private c q;

    /* loaded from: classes4.dex */
    public enum c {
        OPEN,
        ACTIVE,
        CLOSED
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doBeginRead() throws Exception {
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doBind(SocketAddress socketAddress) throws Exception {
    }

    public EmbeddedChannel() {
        this(f);
    }

    public EmbeddedChannel(ChannelId channelId) {
        this(channelId, f);
    }

    public EmbeddedChannel(ChannelHandler... channelHandlerArr) {
        this(a.a, channelHandlerArr);
    }

    public EmbeddedChannel(boolean z, ChannelHandler... channelHandlerArr) {
        this(a.a, z, channelHandlerArr);
    }

    public EmbeddedChannel(ChannelId channelId, ChannelHandler... channelHandlerArr) {
        this(channelId, false, channelHandlerArr);
    }

    public EmbeddedChannel(ChannelId channelId, boolean z, ChannelHandler... channelHandlerArr) {
        super(null, channelId);
        this.j = new b();
        this.k = new ChannelFutureListener() { // from class: io.netty.channel.embedded.EmbeddedChannel.1
            /* renamed from: a */
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                EmbeddedChannel.this.a(channelFuture);
            }
        };
        this.l = a(z);
        this.m = new DefaultChannelConfig(this);
        a(channelHandlerArr);
    }

    public EmbeddedChannel(ChannelId channelId, boolean z, ChannelConfig channelConfig, ChannelHandler... channelHandlerArr) {
        super(null, channelId);
        this.j = new b();
        this.k = new ChannelFutureListener() { // from class: io.netty.channel.embedded.EmbeddedChannel.1
            /* renamed from: a */
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                EmbeddedChannel.this.a(channelFuture);
            }
        };
        this.l = a(z);
        this.m = (ChannelConfig) ObjectUtil.checkNotNull(channelConfig, "config");
        a(channelHandlerArr);
    }

    private static ChannelMetadata a(boolean z) {
        return z ? i : h;
    }

    private void a(final ChannelHandler... channelHandlerArr) {
        ObjectUtil.checkNotNull(channelHandlerArr, "handlers");
        pipeline().addLast(new ChannelInitializer<Channel>() { // from class: io.netty.channel.embedded.EmbeddedChannel.2
            @Override // io.netty.channel.ChannelInitializer
            protected void initChannel(Channel channel) throws Exception {
                ChannelPipeline pipeline = channel.pipeline();
                ChannelHandler[] channelHandlerArr2 = channelHandlerArr;
                for (ChannelHandler channelHandler : channelHandlerArr2) {
                    if (channelHandler != null) {
                        pipeline.addLast(channelHandler);
                    } else {
                        return;
                    }
                }
            }
        });
        ChannelFuture register = this.j.register(this);
        if (!c && !register.isDone()) {
            throw new AssertionError();
        }
    }

    @Override // io.netty.channel.AbstractChannel
    protected final DefaultChannelPipeline newChannelPipeline() {
        return new b(this);
    }

    @Override // io.netty.channel.Channel
    public ChannelMetadata metadata() {
        return this.l;
    }

    @Override // io.netty.channel.Channel
    public ChannelConfig config() {
        return this.m;
    }

    @Override // io.netty.channel.Channel
    public boolean isOpen() {
        return this.q != c.CLOSED;
    }

    @Override // io.netty.channel.Channel
    public boolean isActive() {
        return this.q == c.ACTIVE;
    }

    public Queue<Object> inboundMessages() {
        if (this.n == null) {
            this.n = new ArrayDeque();
        }
        return this.n;
    }

    @Deprecated
    public Queue<Object> lastInboundBuffer() {
        return inboundMessages();
    }

    public Queue<Object> outboundMessages() {
        if (this.o == null) {
            this.o = new ArrayDeque();
        }
        return this.o;
    }

    @Deprecated
    public Queue<Object> lastOutboundBuffer() {
        return outboundMessages();
    }

    public <T> T readInbound() {
        return (T) c(this.n);
    }

    public <T> T readOutbound() {
        return (T) c(this.o);
    }

    public boolean writeInbound(Object... objArr) {
        ensureOpen();
        if (objArr.length == 0) {
            return b(this.n);
        }
        ChannelPipeline pipeline = pipeline();
        for (Object obj : objArr) {
            pipeline.fireChannelRead(obj);
        }
        pipeline.fireChannelReadComplete();
        runPendingTasks();
        checkException();
        return b(this.n);
    }

    public boolean writeOutbound(Object... objArr) {
        ensureOpen();
        if (objArr.length == 0) {
            return b(this.o);
        }
        RecyclableArrayList newInstance = RecyclableArrayList.newInstance(objArr.length);
        try {
            for (Object obj : objArr) {
                if (obj == null) {
                    break;
                }
                newInstance.add(write(obj));
            }
            runPendingTasks();
            flush();
            int size = newInstance.size();
            for (int i2 = 0; i2 < size; i2++) {
                ChannelFuture channelFuture = (ChannelFuture) newInstance.get(i2);
                if (channelFuture.isDone()) {
                    a(channelFuture);
                } else {
                    channelFuture.addListener((GenericFutureListener<? extends Future<? super Void>>) this.k);
                }
            }
            checkException();
            return b(this.o);
        } finally {
            newInstance.recycle();
        }
    }

    public boolean finish() {
        return b(false);
    }

    public boolean finishAndReleaseAll() {
        return b(true);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x001c A[DONT_GENERATE] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean b(boolean r2) {
        /*
            r1 = this;
            r1.close()
            r1.checkException()     // Catch: all -> 0x0027
            java.util.Queue<java.lang.Object> r0 = r1.n     // Catch: all -> 0x0027
            boolean r0 = b(r0)     // Catch: all -> 0x0027
            if (r0 != 0) goto L_0x0019
            java.util.Queue<java.lang.Object> r0 = r1.o     // Catch: all -> 0x0027
            boolean r0 = b(r0)     // Catch: all -> 0x0027
            if (r0 == 0) goto L_0x0017
            goto L_0x0019
        L_0x0017:
            r0 = 0
            goto L_0x001a
        L_0x0019:
            r0 = 1
        L_0x001a:
            if (r2 == 0) goto L_0x0026
            java.util.Queue<java.lang.Object> r2 = r1.n
            a(r2)
            java.util.Queue<java.lang.Object> r2 = r1.o
            a(r2)
        L_0x0026:
            return r0
        L_0x0027:
            r0 = move-exception
            if (r2 == 0) goto L_0x0034
            java.util.Queue<java.lang.Object> r2 = r1.n
            a(r2)
            java.util.Queue<java.lang.Object> r2 = r1.o
            a(r2)
        L_0x0034:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.channel.embedded.EmbeddedChannel.b(boolean):boolean");
    }

    public boolean releaseInbound() {
        return a(this.n);
    }

    public boolean releaseOutbound() {
        return a(this.o);
    }

    private static boolean a(Queue<Object> queue) {
        if (!b(queue)) {
            return false;
        }
        while (true) {
            Object poll = queue.poll();
            if (poll == null) {
                return true;
            }
            ReferenceCountUtil.release(poll);
        }
    }

    private void c(boolean z) {
        runPendingTasks();
        if (z) {
            this.j.cancelScheduledTasks();
        }
    }

    @Override // io.netty.channel.AbstractChannel, io.netty.channel.ChannelOutboundInvoker
    public final ChannelFuture close() {
        return close(newPromise());
    }

    @Override // io.netty.channel.AbstractChannel, io.netty.channel.ChannelOutboundInvoker
    public final ChannelFuture disconnect() {
        return disconnect(newPromise());
    }

    @Override // io.netty.channel.AbstractChannel, io.netty.channel.ChannelOutboundInvoker
    public final ChannelFuture close(ChannelPromise channelPromise) {
        runPendingTasks();
        ChannelFuture close = super.close(channelPromise);
        c(true);
        return close;
    }

    @Override // io.netty.channel.AbstractChannel, io.netty.channel.ChannelOutboundInvoker
    public final ChannelFuture disconnect(ChannelPromise channelPromise) {
        ChannelFuture disconnect = super.disconnect(channelPromise);
        c(!this.l.hasDisconnect());
        return disconnect;
    }

    private static boolean b(Queue<Object> queue) {
        return queue != null && !queue.isEmpty();
    }

    private static Object c(Queue<Object> queue) {
        if (queue != null) {
            return queue.poll();
        }
        return null;
    }

    public void runPendingTasks() {
        try {
            this.j.a();
        } catch (Exception e2) {
            a(e2);
        }
        try {
            this.j.b();
        } catch (Exception e3) {
            a(e3);
        }
    }

    public long runScheduledPendingTasks() {
        try {
            return this.j.b();
        } catch (Exception e2) {
            a(e2);
            return this.j.c();
        }
    }

    public void a(ChannelFuture channelFuture) {
        if (!channelFuture.isSuccess()) {
            a(channelFuture.cause());
        }
    }

    public void a(Throwable th) {
        if (this.p == null) {
            this.p = th;
        } else {
            g.warn("More than one exception was raised. Will report only the first one and log others.", th);
        }
    }

    public void checkException() {
        Throwable th = this.p;
        if (th != null) {
            this.p = null;
            PlatformDependent.throwException(th);
        }
    }

    protected final void ensureOpen() {
        if (!isOpen()) {
            a(new ClosedChannelException());
            checkException();
        }
    }

    @Override // io.netty.channel.AbstractChannel
    protected boolean isCompatible(EventLoop eventLoop) {
        return eventLoop instanceof b;
    }

    @Override // io.netty.channel.AbstractChannel
    protected SocketAddress localAddress0() {
        if (isActive()) {
            return d;
        }
        return null;
    }

    @Override // io.netty.channel.AbstractChannel
    protected SocketAddress remoteAddress0() {
        if (isActive()) {
            return e;
        }
        return null;
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doRegister() throws Exception {
        this.q = c.ACTIVE;
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doDisconnect() throws Exception {
        if (!this.l.hasDisconnect()) {
            doClose();
        }
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doClose() throws Exception {
        this.q = c.CLOSED;
    }

    @Override // io.netty.channel.AbstractChannel
    protected AbstractChannel.AbstractUnsafe newUnsafe() {
        return new a();
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doWrite(ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        while (true) {
            Object current = channelOutboundBuffer.current();
            if (current != null) {
                ReferenceCountUtil.retain(current);
                outboundMessages().add(current);
                channelOutboundBuffer.remove();
            } else {
                return;
            }
        }
    }

    /* loaded from: classes4.dex */
    private class a extends AbstractChannel.AbstractUnsafe {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        private a() {
            super();
            EmbeddedChannel.this = r1;
        }

        @Override // io.netty.channel.Channel.Unsafe
        public void connect(SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) {
            safeSetSuccess(channelPromise);
        }
    }

    /* loaded from: classes4.dex */
    private final class b extends DefaultChannelPipeline {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public b(EmbeddedChannel embeddedChannel) {
            super(embeddedChannel);
            EmbeddedChannel.this = r1;
        }

        @Override // io.netty.channel.DefaultChannelPipeline
        protected void onUnhandledInboundException(Throwable th) {
            EmbeddedChannel.this.a(th);
        }

        @Override // io.netty.channel.DefaultChannelPipeline
        protected void onUnhandledInboundMessage(Object obj) {
            EmbeddedChannel.this.inboundMessages().add(obj);
        }
    }
}
