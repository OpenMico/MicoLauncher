package io.netty.channel.local;

import io.netty.channel.AbstractChannel;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.channel.DefaultChannelConfig;
import io.netty.channel.EventLoop;
import io.netty.channel.SingleThreadEventLoop;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.SingleThreadEventExecutor;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.InternalThreadLocalMap;
import io.netty.util.internal.OneTimeTask;
import io.netty.util.internal.PlatformDependent;
import java.net.ConnectException;
import java.net.SocketAddress;
import java.nio.channels.AlreadyConnectedException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.ConnectionPendingException;
import java.nio.channels.NotYetConnectedException;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/* loaded from: classes4.dex */
public class LocalChannel extends AbstractChannel {
    private static final AtomicReferenceFieldUpdater<LocalChannel, Future> c;
    private static final ChannelMetadata d = new ChannelMetadata(false);
    private static final ClosedChannelException e = new ClosedChannelException();
    private final ChannelConfig f = new DefaultChannelConfig(this);
    private final Queue<Object> g = PlatformDependent.newSpscQueue();
    private final Runnable h = new Runnable() { // from class: io.netty.channel.local.LocalChannel.1
        @Override // java.lang.Runnable
        public void run() {
            ChannelPipeline pipeline = LocalChannel.this.pipeline();
            while (true) {
                Object poll = LocalChannel.this.g.poll();
                if (poll == null) {
                    pipeline.fireChannelReadComplete();
                    return;
                }
                pipeline.fireChannelRead(poll);
            }
        }
    };
    private final Runnable i = new Runnable() { // from class: io.netty.channel.local.LocalChannel.2
        @Override // java.lang.Runnable
        public void run() {
            LocalChannel.this.unsafe().close(LocalChannel.this.unsafe().voidPromise());
        }
    };
    private volatile b j;
    private volatile LocalChannel k;
    private volatile LocalAddress l;
    private volatile LocalAddress m;
    private volatile ChannelPromise n;
    private volatile boolean o;
    private volatile boolean p;
    private volatile boolean q;
    private volatile Future<?> r;

    /* loaded from: classes4.dex */
    public enum b {
        OPEN,
        BOUND,
        CONNECTED,
        CLOSED
    }

    static {
        AtomicReferenceFieldUpdater<LocalChannel, Future> newAtomicReferenceFieldUpdater = PlatformDependent.newAtomicReferenceFieldUpdater(LocalChannel.class, "finishReadFuture");
        if (newAtomicReferenceFieldUpdater == null) {
            newAtomicReferenceFieldUpdater = AtomicReferenceFieldUpdater.newUpdater(LocalChannel.class, Future.class, "r");
        }
        c = newAtomicReferenceFieldUpdater;
        e.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
    }

    public LocalChannel() {
        super(null);
    }

    public LocalChannel(LocalServerChannel localServerChannel, LocalChannel localChannel) {
        super(localServerChannel);
        this.k = localChannel;
        this.l = localServerChannel.localAddress();
        this.m = localChannel.localAddress();
    }

    @Override // io.netty.channel.Channel
    public ChannelMetadata metadata() {
        return d;
    }

    @Override // io.netty.channel.Channel
    public ChannelConfig config() {
        return this.f;
    }

    @Override // io.netty.channel.AbstractChannel, io.netty.channel.Channel
    public LocalServerChannel parent() {
        return (LocalServerChannel) super.parent();
    }

    @Override // io.netty.channel.AbstractChannel, io.netty.channel.Channel
    public LocalAddress localAddress() {
        return (LocalAddress) super.localAddress();
    }

    @Override // io.netty.channel.AbstractChannel, io.netty.channel.Channel
    public LocalAddress remoteAddress() {
        return (LocalAddress) super.remoteAddress();
    }

    @Override // io.netty.channel.Channel
    public boolean isOpen() {
        return this.j != b.CLOSED;
    }

    @Override // io.netty.channel.Channel
    public boolean isActive() {
        return this.j == b.CONNECTED;
    }

    @Override // io.netty.channel.AbstractChannel
    protected AbstractChannel.AbstractUnsafe newUnsafe() {
        return new a();
    }

    @Override // io.netty.channel.AbstractChannel
    protected boolean isCompatible(EventLoop eventLoop) {
        return eventLoop instanceof SingleThreadEventLoop;
    }

    @Override // io.netty.channel.AbstractChannel
    protected SocketAddress localAddress0() {
        return this.l;
    }

    @Override // io.netty.channel.AbstractChannel
    protected SocketAddress remoteAddress0() {
        return this.m;
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doRegister() throws Exception {
        if (!(this.k == null || parent() == null)) {
            final LocalChannel localChannel = this.k;
            this.p = true;
            this.j = b.CONNECTED;
            localChannel.m = parent() == null ? null : parent().localAddress();
            localChannel.j = b.CONNECTED;
            localChannel.eventLoop().execute(new OneTimeTask() { // from class: io.netty.channel.local.LocalChannel.3
                @Override // java.lang.Runnable
                public void run() {
                    LocalChannel.this.p = false;
                    ChannelPromise channelPromise = localChannel.n;
                    if (channelPromise != null && channelPromise.trySuccess()) {
                        localChannel.pipeline().fireChannelActive();
                    }
                }
            });
        }
        ((SingleThreadEventExecutor) eventLoop()).addShutdownHook(this.i);
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doBind(SocketAddress socketAddress) throws Exception {
        this.l = a.a(this, this.l, socketAddress);
        this.j = b.BOUND;
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doDisconnect() throws Exception {
        doClose();
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doClose() throws Exception {
        final LocalChannel localChannel = this.k;
        if (this.j != b.CLOSED) {
            if (this.l != null) {
                if (parent() == null) {
                    a.a(this.l);
                }
                this.l = null;
            }
            this.j = b.CLOSED;
            ChannelPromise channelPromise = this.n;
            if (channelPromise != null) {
                channelPromise.tryFailure(e);
                this.n = null;
            }
            if (this.q && localChannel != null) {
                d(localChannel);
            }
        }
        if (localChannel != null && localChannel.isActive()) {
            if (!localChannel.eventLoop().inEventLoop() || this.p) {
                final boolean z = localChannel.q;
                try {
                    localChannel.eventLoop().execute(new OneTimeTask() { // from class: io.netty.channel.local.LocalChannel.4
                        @Override // java.lang.Runnable
                        public void run() {
                            LocalChannel.this.b(localChannel, z);
                        }
                    });
                } catch (RuntimeException e2) {
                    b();
                    throw e2;
                }
            } else {
                b(localChannel, localChannel.q);
            }
            this.k = null;
        }
    }

    public void b(LocalChannel localChannel, boolean z) {
        if (z) {
            f(this);
        }
        localChannel.unsafe().close(localChannel.unsafe().voidPromise());
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doDeregister() throws Exception {
        ((SingleThreadEventExecutor) eventLoop()).removeShutdownHook(this.i);
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doBeginRead() throws Exception {
        if (!this.o) {
            ChannelPipeline pipeline = pipeline();
            Queue<Object> queue = this.g;
            if (queue.isEmpty()) {
                this.o = true;
                return;
            }
            InternalThreadLocalMap internalThreadLocalMap = InternalThreadLocalMap.get();
            Integer valueOf = Integer.valueOf(internalThreadLocalMap.localChannelReaderStackDepth());
            if (valueOf.intValue() < 8) {
                internalThreadLocalMap.setLocalChannelReaderStackDepth(valueOf.intValue() + 1);
                while (true) {
                    try {
                        Object poll = queue.poll();
                        if (poll == null) {
                            pipeline.fireChannelReadComplete();
                            return;
                        }
                        pipeline.fireChannelRead(poll);
                    } finally {
                        internalThreadLocalMap.setLocalChannelReaderStackDepth(valueOf.intValue());
                    }
                }
            } else {
                try {
                    eventLoop().execute(this.h);
                } catch (RuntimeException e2) {
                    b();
                    throw e2;
                }
            }
        }
    }

    /* JADX WARN: Finally extract failed */
    @Override // io.netty.channel.AbstractChannel
    protected void doWrite(ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        switch (this.j) {
            case OPEN:
            case BOUND:
                throw new NotYetConnectedException();
            case CLOSED:
                throw e;
            default:
                LocalChannel localChannel = this.k;
                this.q = true;
                while (true) {
                    try {
                        Object current = channelOutboundBuffer.current();
                        if (current == null) {
                            this.q = false;
                            d(localChannel);
                            return;
                        } else if (localChannel.j == b.CONNECTED) {
                            localChannel.g.add(ReferenceCountUtil.retain(current));
                            channelOutboundBuffer.remove();
                        } else {
                            channelOutboundBuffer.remove(e);
                        }
                    } catch (Throwable th) {
                        this.q = false;
                        throw th;
                    }
                }
        }
    }

    private void d(LocalChannel localChannel) {
        if (localChannel.eventLoop() != eventLoop() || localChannel.q) {
            e(localChannel);
        } else {
            f(localChannel);
        }
    }

    private void e(final LocalChannel localChannel) {
        OneTimeTask oneTimeTask = new OneTimeTask() { // from class: io.netty.channel.local.LocalChannel.5
            @Override // java.lang.Runnable
            public void run() {
                LocalChannel.this.f(localChannel);
            }
        };
        try {
            if (localChannel.q) {
                localChannel.r = localChannel.eventLoop().submit((Runnable) oneTimeTask);
            } else {
                localChannel.eventLoop().execute(oneTimeTask);
            }
        } catch (RuntimeException e2) {
            localChannel.b();
            throw e2;
        }
    }

    private void b() {
        while (true) {
            Object poll = this.g.poll();
            if (poll != null) {
                ReferenceCountUtil.release(poll);
            } else {
                return;
            }
        }
    }

    public void f(LocalChannel localChannel) {
        Future<?> future = localChannel.r;
        if (future != null) {
            if (!future.isDone()) {
                e(localChannel);
                return;
            }
            c.compareAndSet(localChannel, future, null);
        }
        ChannelPipeline pipeline = localChannel.pipeline();
        if (localChannel.o) {
            localChannel.o = false;
            while (true) {
                Object poll = localChannel.g.poll();
                if (poll == null) {
                    pipeline.fireChannelReadComplete();
                    return;
                }
                pipeline.fireChannelRead(poll);
            }
        }
    }

    /* loaded from: classes4.dex */
    private class a extends AbstractChannel.AbstractUnsafe {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        private a() {
            super();
            LocalChannel.this = r1;
        }

        @Override // io.netty.channel.Channel.Unsafe
        public void connect(SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) {
            if (channelPromise.setUncancellable() && ensureOpen(channelPromise)) {
                if (LocalChannel.this.j == b.CONNECTED) {
                    AlreadyConnectedException alreadyConnectedException = new AlreadyConnectedException();
                    safeSetFailure(channelPromise, alreadyConnectedException);
                    LocalChannel.this.pipeline().fireExceptionCaught((Throwable) alreadyConnectedException);
                } else if (LocalChannel.this.n == null) {
                    LocalChannel.this.n = channelPromise;
                    if (LocalChannel.this.j != b.BOUND && socketAddress2 == null) {
                        socketAddress2 = new LocalAddress(LocalChannel.this);
                    }
                    if (socketAddress2 != null) {
                        try {
                            LocalChannel.this.doBind(socketAddress2);
                        } catch (Throwable th) {
                            safeSetFailure(channelPromise, th);
                            close(voidPromise());
                            return;
                        }
                    }
                    Channel a = a.a(socketAddress);
                    if (!(a instanceof LocalServerChannel)) {
                        safeSetFailure(channelPromise, new ConnectException("connection refused: " + socketAddress));
                        close(voidPromise());
                        return;
                    }
                    LocalChannel localChannel = LocalChannel.this;
                    localChannel.k = ((LocalServerChannel) a).a(localChannel);
                } else {
                    throw new ConnectionPendingException();
                }
            }
        }
    }
}
