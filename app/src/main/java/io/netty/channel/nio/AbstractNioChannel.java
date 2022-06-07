package io.netty.channel.nio;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.AbstractChannel;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelPromise;
import io.netty.channel.ConnectTimeoutException;
import io.netty.channel.EventLoop;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.ReferenceCounted;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.OneTimeTask;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public abstract class AbstractNioChannel extends AbstractChannel {
    static final /* synthetic */ boolean e = !AbstractNioChannel.class.desiredAssertionStatus();
    private static final InternalLogger f = InternalLoggerFactory.getInstance(AbstractNioChannel.class);
    private static final ClosedChannelException g = new ClosedChannelException();
    volatile SelectionKey c;
    boolean d;
    private final SelectableChannel h;
    private final Runnable i = new Runnable() { // from class: io.netty.channel.nio.AbstractNioChannel.1
        @Override // java.lang.Runnable
        public void run() {
            AbstractNioChannel.this.b();
        }
    };
    private ChannelPromise j;
    private ScheduledFuture<?> k;
    private SocketAddress l;
    protected final int readInterestOp;

    /* loaded from: classes4.dex */
    public interface NioUnsafe extends Channel.Unsafe {
        SelectableChannel ch();

        void finishConnect();

        void forceFlush();

        void read();
    }

    protected abstract boolean doConnect(SocketAddress socketAddress, SocketAddress socketAddress2) throws Exception;

    protected abstract void doFinishConnect() throws Exception;

    static {
        g.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
    }

    static /* synthetic */ SocketAddress d(AbstractNioChannel abstractNioChannel) {
        return abstractNioChannel.l;
    }

    public AbstractNioChannel(Channel channel, SelectableChannel selectableChannel, int i) {
        super(channel);
        this.h = selectableChannel;
        this.readInterestOp = i;
        try {
            selectableChannel.configureBlocking(false);
        } catch (IOException e2) {
            try {
                selectableChannel.close();
            } catch (IOException e3) {
                if (f.isWarnEnabled()) {
                    f.warn("Failed to close a partially initialized socket.", (Throwable) e3);
                }
            }
            throw new ChannelException("Failed to enter non-blocking mode.", e2);
        }
    }

    @Override // io.netty.channel.Channel
    public boolean isOpen() {
        return this.h.isOpen();
    }

    @Override // io.netty.channel.AbstractChannel, io.netty.channel.Channel
    public NioUnsafe unsafe() {
        return (NioUnsafe) super.unsafe();
    }

    protected SelectableChannel javaChannel() {
        return this.h;
    }

    @Override // io.netty.channel.AbstractChannel, io.netty.channel.Channel
    public NioEventLoop eventLoop() {
        return (NioEventLoop) super.eventLoop();
    }

    protected SelectionKey selectionKey() {
        if (e || this.c != null) {
            return this.c;
        }
        throw new AssertionError();
    }

    @Deprecated
    protected boolean isReadPending() {
        return this.d;
    }

    @Deprecated
    protected void setReadPending(final boolean z) {
        if (isRegistered()) {
            NioEventLoop eventLoop = eventLoop();
            if (eventLoop.inEventLoop()) {
                a(z);
            } else {
                eventLoop.execute(new OneTimeTask() { // from class: io.netty.channel.nio.AbstractNioChannel.2
                    @Override // java.lang.Runnable
                    public void run() {
                        AbstractNioChannel.this.a(z);
                    }
                });
            }
        } else {
            this.d = z;
        }
    }

    protected final void clearReadPending() {
        if (isRegistered()) {
            NioEventLoop eventLoop = eventLoop();
            if (eventLoop.inEventLoop()) {
                b();
            } else {
                eventLoop.execute(this.i);
            }
        } else {
            this.d = false;
        }
    }

    public void a(boolean z) {
        this.d = z;
        if (!z) {
            ((AbstractNioUnsafe) unsafe()).removeReadOp();
        }
    }

    public void b() {
        this.d = false;
        ((AbstractNioUnsafe) unsafe()).removeReadOp();
    }

    /* loaded from: classes4.dex */
    public abstract class AbstractNioUnsafe extends AbstractChannel.AbstractUnsafe implements NioUnsafe {
        static final /* synthetic */ boolean d = !AbstractNioChannel.class.desiredAssertionStatus();

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AbstractNioUnsafe() {
            super();
            AbstractNioChannel.this = r1;
        }

        protected final void removeReadOp() {
            SelectionKey selectionKey = AbstractNioChannel.this.selectionKey();
            if (selectionKey.isValid()) {
                int interestOps = selectionKey.interestOps();
                if ((AbstractNioChannel.this.readInterestOp & interestOps) != 0) {
                    selectionKey.interestOps(interestOps & (~AbstractNioChannel.this.readInterestOp));
                }
            }
        }

        @Override // io.netty.channel.nio.AbstractNioChannel.NioUnsafe
        public final SelectableChannel ch() {
            return AbstractNioChannel.this.javaChannel();
        }

        @Override // io.netty.channel.Channel.Unsafe
        public final void connect(final SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) {
            if (channelPromise.setUncancellable() && ensureOpen(channelPromise)) {
                try {
                    if (AbstractNioChannel.this.j == null) {
                        boolean isActive = AbstractNioChannel.this.isActive();
                        if (AbstractNioChannel.this.doConnect(socketAddress, socketAddress2)) {
                            a(channelPromise, isActive);
                            return;
                        }
                        AbstractNioChannel.this.j = channelPromise;
                        AbstractNioChannel.this.l = socketAddress;
                        int connectTimeoutMillis = AbstractNioChannel.this.config().getConnectTimeoutMillis();
                        if (connectTimeoutMillis > 0) {
                            AbstractNioChannel.this.k = AbstractNioChannel.this.eventLoop().schedule((Runnable) new OneTimeTask() { // from class: io.netty.channel.nio.AbstractNioChannel.AbstractNioUnsafe.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    ChannelPromise channelPromise2 = AbstractNioChannel.this.j;
                                    ConnectTimeoutException connectTimeoutException = new ConnectTimeoutException("connection timed out: " + socketAddress);
                                    if (channelPromise2 != null && channelPromise2.tryFailure(connectTimeoutException)) {
                                        AbstractNioUnsafe abstractNioUnsafe = AbstractNioUnsafe.this;
                                        abstractNioUnsafe.close(abstractNioUnsafe.voidPromise());
                                    }
                                }
                            }, connectTimeoutMillis, TimeUnit.MILLISECONDS);
                        }
                        channelPromise.addListener((GenericFutureListener<? extends Future<? super Void>>) new ChannelFutureListener() { // from class: io.netty.channel.nio.AbstractNioChannel.AbstractNioUnsafe.2
                            /* renamed from: a */
                            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                                if (channelFuture.isCancelled()) {
                                    if (AbstractNioChannel.this.k != null) {
                                        AbstractNioChannel.this.k.cancel(false);
                                    }
                                    AbstractNioChannel.this.j = null;
                                    AbstractNioUnsafe abstractNioUnsafe = AbstractNioUnsafe.this;
                                    abstractNioUnsafe.close(abstractNioUnsafe.voidPromise());
                                }
                            }
                        });
                        return;
                    }
                    throw new IllegalStateException("connection attempt already made");
                } catch (Throwable th) {
                    channelPromise.tryFailure(annotateConnectException(th, socketAddress));
                    closeIfClosed();
                }
            }
        }

        private void a(ChannelPromise channelPromise, boolean z) {
            if (channelPromise != null) {
                boolean trySuccess = channelPromise.trySuccess();
                if (!z && AbstractNioChannel.this.isActive()) {
                    AbstractNioChannel.this.pipeline().fireChannelActive();
                }
                if (!trySuccess) {
                    close(voidPromise());
                }
            }
        }

        private void a(ChannelPromise channelPromise, Throwable th) {
            if (channelPromise != null) {
                channelPromise.tryFailure(th);
                closeIfClosed();
            }
        }

        /* JADX WARN: Finally extract failed */
        @Override // io.netty.channel.nio.AbstractNioChannel.NioUnsafe
        public final void finishConnect() {
            if (d || AbstractNioChannel.this.eventLoop().inEventLoop()) {
                try {
                    boolean isActive = AbstractNioChannel.this.isActive();
                    AbstractNioChannel.this.doFinishConnect();
                    a(AbstractNioChannel.this.j, isActive);
                    if (AbstractNioChannel.this.k != null) {
                        AbstractNioChannel.this.k.cancel(false);
                    }
                    AbstractNioChannel.this.j = null;
                } catch (Throwable th) {
                    if (AbstractNioChannel.this.k != null) {
                        AbstractNioChannel.this.k.cancel(false);
                    }
                    AbstractNioChannel.this.j = null;
                    throw th;
                }
            } else {
                throw new AssertionError();
            }
        }

        @Override // io.netty.channel.AbstractChannel.AbstractUnsafe
        public final void flush0() {
            if (!a()) {
                super.flush0();
            }
        }

        @Override // io.netty.channel.nio.AbstractNioChannel.NioUnsafe
        public final void forceFlush() {
            super.flush0();
        }

        private boolean a() {
            SelectionKey selectionKey = AbstractNioChannel.this.selectionKey();
            return selectionKey.isValid() && (selectionKey.interestOps() & 4) != 0;
        }
    }

    @Override // io.netty.channel.AbstractChannel
    protected boolean isCompatible(EventLoop eventLoop) {
        return eventLoop instanceof NioEventLoop;
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doRegister() throws Exception {
        boolean z = false;
        while (true) {
            try {
                this.c = javaChannel().register(eventLoop().a, 0, this);
                return;
            } catch (CancelledKeyException e2) {
                if (!z) {
                    eventLoop().a();
                    z = true;
                } else {
                    throw e2;
                }
            }
        }
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doDeregister() throws Exception {
        eventLoop().a(selectionKey());
    }

    @Override // io.netty.channel.AbstractChannel
    public void doBeginRead() throws Exception {
        SelectionKey selectionKey = this.c;
        if (selectionKey.isValid()) {
            this.d = true;
            int interestOps = selectionKey.interestOps();
            int i = this.readInterestOp;
            if ((interestOps & i) == 0) {
                selectionKey.interestOps(interestOps | i);
            }
        }
    }

    protected final ByteBuf newDirectBuffer(ByteBuf byteBuf) {
        int readableBytes = byteBuf.readableBytes();
        if (readableBytes == 0) {
            ReferenceCountUtil.safeRelease(byteBuf);
            return Unpooled.EMPTY_BUFFER;
        }
        ByteBufAllocator alloc = alloc();
        if (alloc.isDirectBufferPooled()) {
            ByteBuf directBuffer = alloc.directBuffer(readableBytes);
            directBuffer.writeBytes(byteBuf, byteBuf.readerIndex(), readableBytes);
            ReferenceCountUtil.safeRelease(byteBuf);
            return directBuffer;
        }
        ByteBuf threadLocalDirectBuffer = ByteBufUtil.threadLocalDirectBuffer();
        if (threadLocalDirectBuffer == null) {
            return byteBuf;
        }
        threadLocalDirectBuffer.writeBytes(byteBuf, byteBuf.readerIndex(), readableBytes);
        ReferenceCountUtil.safeRelease(byteBuf);
        return threadLocalDirectBuffer;
    }

    protected final ByteBuf newDirectBuffer(ReferenceCounted referenceCounted, ByteBuf byteBuf) {
        int readableBytes = byteBuf.readableBytes();
        if (readableBytes == 0) {
            ReferenceCountUtil.safeRelease(referenceCounted);
            return Unpooled.EMPTY_BUFFER;
        }
        ByteBufAllocator alloc = alloc();
        if (alloc.isDirectBufferPooled()) {
            ByteBuf directBuffer = alloc.directBuffer(readableBytes);
            directBuffer.writeBytes(byteBuf, byteBuf.readerIndex(), readableBytes);
            ReferenceCountUtil.safeRelease(referenceCounted);
            return directBuffer;
        }
        ByteBuf threadLocalDirectBuffer = ByteBufUtil.threadLocalDirectBuffer();
        if (threadLocalDirectBuffer != null) {
            threadLocalDirectBuffer.writeBytes(byteBuf, byteBuf.readerIndex(), readableBytes);
            ReferenceCountUtil.safeRelease(referenceCounted);
            return threadLocalDirectBuffer;
        }
        if (referenceCounted != byteBuf) {
            byteBuf.retain();
            ReferenceCountUtil.safeRelease(referenceCounted);
        }
        return byteBuf;
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doClose() throws Exception {
        ChannelPromise channelPromise = this.j;
        if (channelPromise != null) {
            channelPromise.tryFailure(g);
            this.j = null;
        }
        ScheduledFuture<?> scheduledFuture = this.k;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(false);
            this.k = null;
        }
    }
}
