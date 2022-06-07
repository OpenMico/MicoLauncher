package io.netty.channel;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.util.DefaultAttributeMap;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.OneTimeTask;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.NoRouteToHostException;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.NotYetConnectedException;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;

/* loaded from: classes4.dex */
public abstract class AbstractChannel extends DefaultAttributeMap implements Channel {
    private final Channel d;
    private final ChannelId e;
    private final Channel.Unsafe f;
    private final DefaultChannelPipeline g;
    private final g h;
    private final a i;
    private volatile SocketAddress j;
    private volatile SocketAddress k;
    private volatile EventLoop l;
    private volatile boolean m;
    private boolean n;
    private String o;
    private static final InternalLogger c = InternalLoggerFactory.getInstance(AbstractChannel.class);
    static final ClosedChannelException a = new ClosedChannelException();
    static final NotYetConnectedException b = new NotYetConnectedException();

    protected abstract void doBeginRead() throws Exception;

    protected abstract void doBind(SocketAddress socketAddress) throws Exception;

    protected abstract void doClose() throws Exception;

    protected void doDeregister() throws Exception {
    }

    protected abstract void doDisconnect() throws Exception;

    protected void doRegister() throws Exception {
    }

    protected abstract void doWrite(ChannelOutboundBuffer channelOutboundBuffer) throws Exception;

    public final boolean equals(Object obj) {
        return this == obj;
    }

    protected Object filterOutboundMessage(Object obj) throws Exception {
        return obj;
    }

    protected abstract boolean isCompatible(EventLoop eventLoop);

    protected abstract SocketAddress localAddress0();

    protected abstract AbstractUnsafe newUnsafe();

    protected abstract SocketAddress remoteAddress0();

    static /* synthetic */ InternalLogger a() {
        return c;
    }

    static {
        a.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
        b.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
    }

    public AbstractChannel(Channel channel) {
        this.h = new g(this, false);
        this.i = new a(this);
        this.d = channel;
        this.e = newId();
        this.f = newUnsafe();
        this.g = newChannelPipeline();
    }

    public AbstractChannel(Channel channel, ChannelId channelId) {
        this.h = new g(this, false);
        this.i = new a(this);
        this.d = channel;
        this.e = channelId;
        this.f = newUnsafe();
        this.g = newChannelPipeline();
    }

    @Override // io.netty.channel.Channel
    public final ChannelId id() {
        return this.e;
    }

    protected ChannelId newId() {
        return DefaultChannelId.newInstance();
    }

    protected DefaultChannelPipeline newChannelPipeline() {
        return new DefaultChannelPipeline(this);
    }

    @Override // io.netty.channel.Channel
    public boolean isWritable() {
        ChannelOutboundBuffer outboundBuffer = this.f.outboundBuffer();
        return outboundBuffer != null && outboundBuffer.isWritable();
    }

    @Override // io.netty.channel.Channel
    public long bytesBeforeUnwritable() {
        ChannelOutboundBuffer outboundBuffer = this.f.outboundBuffer();
        if (outboundBuffer != null) {
            return outboundBuffer.bytesBeforeUnwritable();
        }
        return 0L;
    }

    @Override // io.netty.channel.Channel
    public long bytesBeforeWritable() {
        ChannelOutboundBuffer outboundBuffer = this.f.outboundBuffer();
        if (outboundBuffer != null) {
            return outboundBuffer.bytesBeforeWritable();
        }
        return Long.MAX_VALUE;
    }

    @Override // io.netty.channel.Channel
    public Channel parent() {
        return this.d;
    }

    @Override // io.netty.channel.Channel
    public ChannelPipeline pipeline() {
        return this.g;
    }

    @Override // io.netty.channel.Channel
    public ByteBufAllocator alloc() {
        return config().getAllocator();
    }

    @Override // io.netty.channel.Channel
    public EventLoop eventLoop() {
        EventLoop eventLoop = this.l;
        if (eventLoop != null) {
            return eventLoop;
        }
        throw new IllegalStateException("channel not registered to an event loop");
    }

    @Override // io.netty.channel.Channel
    public SocketAddress localAddress() {
        SocketAddress socketAddress = this.j;
        if (socketAddress != null) {
            return socketAddress;
        }
        try {
            SocketAddress localAddress = unsafe().localAddress();
            this.j = localAddress;
            return localAddress;
        } catch (Throwable unused) {
            return null;
        }
    }

    protected void invalidateLocalAddress() {
        this.j = null;
    }

    @Override // io.netty.channel.Channel
    public SocketAddress remoteAddress() {
        SocketAddress socketAddress = this.k;
        if (socketAddress != null) {
            return socketAddress;
        }
        try {
            SocketAddress remoteAddress = unsafe().remoteAddress();
            this.k = remoteAddress;
            return remoteAddress;
        } catch (Throwable unused) {
            return null;
        }
    }

    protected void invalidateRemoteAddress() {
        this.k = null;
    }

    @Override // io.netty.channel.Channel
    public boolean isRegistered() {
        return this.m;
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public ChannelFuture bind(SocketAddress socketAddress) {
        return this.g.bind(socketAddress);
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public ChannelFuture connect(SocketAddress socketAddress) {
        return this.g.connect(socketAddress);
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public ChannelFuture connect(SocketAddress socketAddress, SocketAddress socketAddress2) {
        return this.g.connect(socketAddress, socketAddress2);
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public ChannelFuture disconnect() {
        return this.g.disconnect();
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public ChannelFuture close() {
        return this.g.close();
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public ChannelFuture deregister() {
        return this.g.deregister();
    }

    @Override // io.netty.channel.Channel, io.netty.channel.ChannelOutboundInvoker
    public Channel flush() {
        this.g.flush();
        return this;
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public ChannelFuture bind(SocketAddress socketAddress, ChannelPromise channelPromise) {
        return this.g.bind(socketAddress, channelPromise);
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public ChannelFuture connect(SocketAddress socketAddress, ChannelPromise channelPromise) {
        return this.g.connect(socketAddress, channelPromise);
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public ChannelFuture connect(SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) {
        return this.g.connect(socketAddress, socketAddress2, channelPromise);
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public ChannelFuture disconnect(ChannelPromise channelPromise) {
        return this.g.disconnect(channelPromise);
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public ChannelFuture close(ChannelPromise channelPromise) {
        return this.g.close(channelPromise);
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public ChannelFuture deregister(ChannelPromise channelPromise) {
        return this.g.deregister(channelPromise);
    }

    @Override // io.netty.channel.Channel, io.netty.channel.ChannelOutboundInvoker
    public Channel read() {
        this.g.read();
        return this;
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public ChannelFuture write(Object obj) {
        return this.g.write(obj);
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public ChannelFuture write(Object obj, ChannelPromise channelPromise) {
        return this.g.write(obj, channelPromise);
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public ChannelFuture writeAndFlush(Object obj) {
        return this.g.writeAndFlush(obj);
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public ChannelFuture writeAndFlush(Object obj, ChannelPromise channelPromise) {
        return this.g.writeAndFlush(obj, channelPromise);
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public ChannelPromise newPromise() {
        return this.g.newPromise();
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public ChannelProgressivePromise newProgressivePromise() {
        return this.g.newProgressivePromise();
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public ChannelFuture newSucceededFuture() {
        return this.g.newSucceededFuture();
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public ChannelFuture newFailedFuture(Throwable th) {
        return this.g.newFailedFuture(th);
    }

    @Override // io.netty.channel.Channel
    public ChannelFuture closeFuture() {
        return this.i;
    }

    @Override // io.netty.channel.Channel
    public Channel.Unsafe unsafe() {
        return this.f;
    }

    public final int hashCode() {
        return this.e.hashCode();
    }

    public final int compareTo(Channel channel) {
        if (this == channel) {
            return 0;
        }
        return id().compareTo(channel.id());
    }

    public String toString() {
        String str;
        boolean isActive = isActive();
        if (this.n == isActive && (str = this.o) != null) {
            return str;
        }
        SocketAddress remoteAddress = remoteAddress();
        SocketAddress localAddress = localAddress();
        if (remoteAddress != null) {
            StringBuilder sb = new StringBuilder(96);
            sb.append("[id: 0x");
            sb.append(this.e.asShortText());
            sb.append(", L:");
            sb.append(localAddress);
            sb.append(isActive ? " - " : " ! ");
            sb.append("R:");
            sb.append(remoteAddress);
            sb.append(']');
            this.o = sb.toString();
        } else if (localAddress != null) {
            StringBuilder sb2 = new StringBuilder(64);
            sb2.append("[id: 0x");
            sb2.append(this.e.asShortText());
            sb2.append(", L:");
            sb2.append(localAddress);
            sb2.append(']');
            this.o = sb2.toString();
        } else {
            StringBuilder sb3 = new StringBuilder(16);
            sb3.append("[id: 0x");
            sb3.append(this.e.asShortText());
            sb3.append(']');
            this.o = sb3.toString();
        }
        this.n = isActive;
        return this.o;
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public final ChannelPromise voidPromise() {
        return this.g.voidPromise();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: classes4.dex */
    public abstract class AbstractUnsafe implements Channel.Unsafe {
        static final /* synthetic */ boolean a = !AbstractChannel.class.desiredAssertionStatus();
        private ChannelOutboundBuffer c;
        private RecvByteBufAllocator.Handle d;
        private boolean e;
        private boolean f = true;

        protected Executor prepareToClose() {
            return null;
        }

        public AbstractUnsafe() {
            AbstractChannel.this = r2;
            this.c = new ChannelOutboundBuffer(AbstractChannel.this);
        }

        private void a() {
            if (!a && AbstractChannel.this.m && !AbstractChannel.this.l.inEventLoop()) {
                throw new AssertionError();
            }
        }

        @Override // io.netty.channel.Channel.Unsafe
        public RecvByteBufAllocator.Handle recvBufAllocHandle() {
            if (this.d == null) {
                this.d = AbstractChannel.this.config().getRecvByteBufAllocator().newHandle();
            }
            return this.d;
        }

        @Override // io.netty.channel.Channel.Unsafe
        public final ChannelOutboundBuffer outboundBuffer() {
            return this.c;
        }

        @Override // io.netty.channel.Channel.Unsafe
        public final SocketAddress localAddress() {
            return AbstractChannel.this.localAddress0();
        }

        @Override // io.netty.channel.Channel.Unsafe
        public final SocketAddress remoteAddress() {
            return AbstractChannel.this.remoteAddress0();
        }

        @Override // io.netty.channel.Channel.Unsafe
        public final void register(EventLoop eventLoop, final ChannelPromise channelPromise) {
            if (eventLoop == null) {
                throw new NullPointerException("eventLoop");
            } else if (AbstractChannel.this.isRegistered()) {
                channelPromise.setFailure((Throwable) new IllegalStateException("registered to an event loop already"));
            } else if (!AbstractChannel.this.isCompatible(eventLoop)) {
                channelPromise.setFailure((Throwable) new IllegalStateException("incompatible event loop type: " + eventLoop.getClass().getName()));
            } else {
                AbstractChannel.this.l = eventLoop;
                if (eventLoop.inEventLoop()) {
                    a(channelPromise);
                    return;
                }
                try {
                    eventLoop.execute(new OneTimeTask() { // from class: io.netty.channel.AbstractChannel.AbstractUnsafe.1
                        @Override // java.lang.Runnable
                        public void run() {
                            AbstractUnsafe.this.a(channelPromise);
                        }
                    });
                } catch (Throwable th) {
                    AbstractChannel.c.warn("Force-closing a channel whose registration task was not accepted by an event loop: {}", AbstractChannel.this, th);
                    closeForcibly();
                    AbstractChannel.this.i.a();
                    safeSetFailure(channelPromise, th);
                }
            }
        }

        public void a(ChannelPromise channelPromise) {
            try {
                if (channelPromise.setUncancellable() && ensureOpen(channelPromise)) {
                    boolean z = this.f;
                    AbstractChannel.this.doRegister();
                    this.f = false;
                    AbstractChannel.this.m = true;
                    safeSetSuccess(channelPromise);
                    AbstractChannel.this.g.fireChannelRegistered();
                    if (AbstractChannel.this.isActive()) {
                        if (z) {
                            AbstractChannel.this.g.fireChannelActive();
                        } else if (AbstractChannel.this.config().isAutoRead()) {
                            beginRead();
                        }
                    }
                }
            } catch (Throwable th) {
                closeForcibly();
                AbstractChannel.this.i.a();
                safeSetFailure(channelPromise, th);
            }
        }

        @Override // io.netty.channel.Channel.Unsafe
        public final void bind(SocketAddress socketAddress, ChannelPromise channelPromise) {
            a();
            if (channelPromise.setUncancellable() && ensureOpen(channelPromise)) {
                if (Boolean.TRUE.equals(AbstractChannel.this.config().getOption(ChannelOption.SO_BROADCAST)) && (socketAddress instanceof InetSocketAddress) && !((InetSocketAddress) socketAddress).getAddress().isAnyLocalAddress() && !PlatformDependent.isWindows() && !PlatformDependent.isRoot()) {
                    InternalLogger internalLogger = AbstractChannel.c;
                    internalLogger.warn("A non-root user can't receive a broadcast packet if the socket is not bound to a wildcard address; binding to a non-wildcard address (" + socketAddress + ") anyway as requested.");
                }
                boolean isActive = AbstractChannel.this.isActive();
                try {
                    AbstractChannel.this.doBind(socketAddress);
                    if (!isActive && AbstractChannel.this.isActive()) {
                        a(new OneTimeTask() { // from class: io.netty.channel.AbstractChannel.AbstractUnsafe.2
                            @Override // java.lang.Runnable
                            public void run() {
                                AbstractChannel.this.g.fireChannelActive();
                            }
                        });
                    }
                    safeSetSuccess(channelPromise);
                } catch (Throwable th) {
                    safeSetFailure(channelPromise, th);
                    closeIfClosed();
                }
            }
        }

        @Override // io.netty.channel.Channel.Unsafe
        public final void disconnect(ChannelPromise channelPromise) {
            a();
            if (channelPromise.setUncancellable()) {
                boolean isActive = AbstractChannel.this.isActive();
                try {
                    AbstractChannel.this.doDisconnect();
                    if (isActive && !AbstractChannel.this.isActive()) {
                        a(new OneTimeTask() { // from class: io.netty.channel.AbstractChannel.AbstractUnsafe.3
                            @Override // java.lang.Runnable
                            public void run() {
                                AbstractChannel.this.g.fireChannelInactive();
                            }
                        });
                    }
                    safeSetSuccess(channelPromise);
                    closeIfClosed();
                } catch (Throwable th) {
                    safeSetFailure(channelPromise, th);
                    closeIfClosed();
                }
            }
        }

        @Override // io.netty.channel.Channel.Unsafe
        public final void close(ChannelPromise channelPromise) {
            a();
            a(channelPromise, AbstractChannel.a, false);
        }

        /* JADX WARN: Finally extract failed */
        private void a(final ChannelPromise channelPromise, final Throwable th, final boolean z) {
            if (channelPromise.setUncancellable()) {
                final ChannelOutboundBuffer channelOutboundBuffer = this.c;
                if (channelOutboundBuffer == null) {
                    if (!(channelPromise instanceof g)) {
                        AbstractChannel.this.i.addListener((GenericFutureListener<? extends Future<? super Void>>) new ChannelFutureListener() { // from class: io.netty.channel.AbstractChannel.AbstractUnsafe.4
                            /* renamed from: a */
                            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                                channelPromise.setSuccess();
                            }
                        });
                    }
                } else if (AbstractChannel.this.i.isDone()) {
                    safeSetSuccess(channelPromise);
                } else {
                    final boolean isActive = AbstractChannel.this.isActive();
                    this.c = null;
                    Executor prepareToClose = prepareToClose();
                    if (prepareToClose != null) {
                        prepareToClose.execute(new OneTimeTask() { // from class: io.netty.channel.AbstractChannel.AbstractUnsafe.5
                            @Override // java.lang.Runnable
                            public void run() {
                                try {
                                    AbstractUnsafe.this.b(channelPromise);
                                } finally {
                                    AbstractUnsafe.this.a(new OneTimeTask() { // from class: io.netty.channel.AbstractChannel.AbstractUnsafe.5.1
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            channelOutboundBuffer.a(th, z);
                                            channelOutboundBuffer.a(AbstractChannel.a);
                                            AbstractUnsafe.this.a(isActive);
                                        }
                                    });
                                }
                            }
                        });
                        return;
                    }
                    try {
                        b(channelPromise);
                        channelOutboundBuffer.a(th, z);
                        channelOutboundBuffer.a(AbstractChannel.a);
                        if (this.e) {
                            a(new OneTimeTask() { // from class: io.netty.channel.AbstractChannel.AbstractUnsafe.6
                                @Override // java.lang.Runnable
                                public void run() {
                                    AbstractUnsafe.this.a(isActive);
                                }
                            });
                        } else {
                            a(isActive);
                        }
                    } catch (Throwable th2) {
                        channelOutboundBuffer.a(th, z);
                        channelOutboundBuffer.a(AbstractChannel.a);
                        throw th2;
                    }
                }
            }
        }

        public void b(ChannelPromise channelPromise) {
            try {
                AbstractChannel.this.doClose();
                AbstractChannel.this.i.a();
                safeSetSuccess(channelPromise);
            } catch (Throwable th) {
                AbstractChannel.this.i.a();
                safeSetFailure(channelPromise, th);
            }
        }

        public void a(boolean z) {
            a(voidPromise(), z && !AbstractChannel.this.isActive());
        }

        @Override // io.netty.channel.Channel.Unsafe
        public final void closeForcibly() {
            a();
            try {
                AbstractChannel.this.doClose();
            } catch (Exception e) {
                AbstractChannel.c.warn("Failed to close a channel.", (Throwable) e);
            }
        }

        @Override // io.netty.channel.Channel.Unsafe
        public final void deregister(ChannelPromise channelPromise) {
            a();
            a(channelPromise, false);
        }

        private void a(final ChannelPromise channelPromise, final boolean z) {
            if (channelPromise.setUncancellable()) {
                if (!AbstractChannel.this.m) {
                    safeSetSuccess(channelPromise);
                } else {
                    a(new OneTimeTask() { // from class: io.netty.channel.AbstractChannel.AbstractUnsafe.7
                        @Override // java.lang.Runnable
                        public void run() {
                            try {
                                AbstractChannel.this.doDeregister();
                            } finally {
                                if (z) {
                                    AbstractChannel.this.g.fireChannelInactive();
                                }
                                if (AbstractChannel.this.m) {
                                    AbstractChannel.this.m = false;
                                    AbstractChannel.this.g.fireChannelUnregistered();
                                }
                                AbstractUnsafe.this.safeSetSuccess(channelPromise);
                            }
                        }
                    });
                }
            }
        }

        @Override // io.netty.channel.Channel.Unsafe
        public final void beginRead() {
            a();
            if (AbstractChannel.this.isActive()) {
                try {
                    AbstractChannel.this.doBeginRead();
                } catch (Exception e) {
                    a(new OneTimeTask() { // from class: io.netty.channel.AbstractChannel.AbstractUnsafe.8
                        @Override // java.lang.Runnable
                        public void run() {
                            AbstractChannel.this.g.fireExceptionCaught((Throwable) e);
                        }
                    });
                    close(voidPromise());
                }
            }
        }

        @Override // io.netty.channel.Channel.Unsafe
        public final void write(Object obj, ChannelPromise channelPromise) {
            a();
            ChannelOutboundBuffer channelOutboundBuffer = this.c;
            if (channelOutboundBuffer == null) {
                safeSetFailure(channelPromise, AbstractChannel.a);
                ReferenceCountUtil.release(obj);
                return;
            }
            try {
                obj = AbstractChannel.this.filterOutboundMessage(obj);
                int size = AbstractChannel.this.g.a().size(obj);
                if (size < 0) {
                    size = 0;
                }
                channelOutboundBuffer.addMessage(obj, size, channelPromise);
            } catch (Throwable th) {
                safeSetFailure(channelPromise, th);
                ReferenceCountUtil.release(obj);
            }
        }

        @Override // io.netty.channel.Channel.Unsafe
        public final void flush() {
            a();
            ChannelOutboundBuffer channelOutboundBuffer = this.c;
            if (channelOutboundBuffer != null) {
                channelOutboundBuffer.addFlush();
                flush0();
            }
        }

        public void flush0() {
            ChannelOutboundBuffer channelOutboundBuffer;
            if (!this.e && (channelOutboundBuffer = this.c) != null && !channelOutboundBuffer.isEmpty()) {
                this.e = true;
                if (!AbstractChannel.this.isActive()) {
                    try {
                        if (AbstractChannel.this.isOpen()) {
                            channelOutboundBuffer.a((Throwable) AbstractChannel.b, true);
                        } else {
                            channelOutboundBuffer.a((Throwable) AbstractChannel.a, false);
                        }
                    } finally {
                    }
                } else {
                    try {
                        AbstractChannel.this.doWrite(channelOutboundBuffer);
                    } finally {
                    }
                }
            }
        }

        @Override // io.netty.channel.Channel.Unsafe
        public final ChannelPromise voidPromise() {
            a();
            return AbstractChannel.this.h;
        }

        protected final boolean ensureOpen(ChannelPromise channelPromise) {
            if (AbstractChannel.this.isOpen()) {
                return true;
            }
            safeSetFailure(channelPromise, AbstractChannel.a);
            return false;
        }

        public final void safeSetSuccess(ChannelPromise channelPromise) {
            if (!(channelPromise instanceof g) && !channelPromise.trySuccess()) {
                AbstractChannel.c.warn("Failed to mark a promise as success because it is done already: {}", channelPromise);
            }
        }

        public final void safeSetFailure(ChannelPromise channelPromise, Throwable th) {
            if (!(channelPromise instanceof g) && !channelPromise.tryFailure(th)) {
                AbstractChannel.c.warn("Failed to mark a promise as failure because it's done already: {}", channelPromise, th);
            }
        }

        public final void closeIfClosed() {
            if (!AbstractChannel.this.isOpen()) {
                close(voidPromise());
            }
        }

        public void a(Runnable runnable) {
            try {
                AbstractChannel.this.eventLoop().execute(runnable);
            } catch (RejectedExecutionException e) {
                AbstractChannel.c.warn("Can't invoke task later as EventLoop rejected it", (Throwable) e);
            }
        }

        protected final Throwable annotateConnectException(Throwable th, SocketAddress socketAddress) {
            if (th instanceof ConnectException) {
                ConnectException connectException = new ConnectException(th.getMessage() + ": " + socketAddress);
                connectException.setStackTrace(th.getStackTrace());
                return connectException;
            } else if (th instanceof NoRouteToHostException) {
                NoRouteToHostException noRouteToHostException = new NoRouteToHostException(th.getMessage() + ": " + socketAddress);
                noRouteToHostException.setStackTrace(th.getStackTrace());
                return noRouteToHostException;
            } else if (!(th instanceof SocketException)) {
                return th;
            } else {
                SocketException socketException = new SocketException(th.getMessage() + ": " + socketAddress);
                socketException.setStackTrace(th.getStackTrace());
                return socketException;
            }
        }
    }

    /* loaded from: classes4.dex */
    public static final class a extends DefaultChannelPromise {
        a(AbstractChannel abstractChannel) {
            super(abstractChannel);
        }

        @Override // io.netty.channel.DefaultChannelPromise, io.netty.channel.ChannelPromise
        public ChannelPromise setSuccess() {
            throw new IllegalStateException();
        }

        @Override // io.netty.channel.DefaultChannelPromise, io.netty.util.concurrent.DefaultPromise, io.netty.util.concurrent.Promise, io.netty.channel.ChannelPromise
        public ChannelPromise setFailure(Throwable th) {
            throw new IllegalStateException();
        }

        @Override // io.netty.channel.DefaultChannelPromise, io.netty.channel.ChannelPromise
        public boolean trySuccess() {
            throw new IllegalStateException();
        }

        @Override // io.netty.util.concurrent.DefaultPromise, io.netty.util.concurrent.Promise
        public boolean tryFailure(Throwable th) {
            throw new IllegalStateException();
        }

        boolean a() {
            return super.trySuccess();
        }
    }
}
