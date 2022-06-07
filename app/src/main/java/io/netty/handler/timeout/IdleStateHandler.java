package io.netty.handler.timeout;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class IdleStateHandler extends ChannelDuplexHandler {
    private static final long f = TimeUnit.MILLISECONDS.toNanos(1);
    volatile ScheduledFuture<?> a;
    volatile long b;
    volatile ScheduledFuture<?> c;
    volatile long d;
    volatile ScheduledFuture<?> e;
    private final ChannelFutureListener g;
    private final long h;
    private final long i;
    private final long j;
    private boolean k;
    private boolean l;
    private boolean m;
    private volatile int n;
    private volatile boolean o;

    public IdleStateHandler(int i, int i2, int i3) {
        this(i, i2, i3, TimeUnit.SECONDS);
    }

    public IdleStateHandler(long j, long j2, long j3, TimeUnit timeUnit) {
        this.g = new ChannelFutureListener() { // from class: io.netty.handler.timeout.IdleStateHandler.1
            /* renamed from: a */
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                IdleStateHandler.this.d = System.nanoTime();
                IdleStateHandler idleStateHandler = IdleStateHandler.this;
                idleStateHandler.l = idleStateHandler.m = true;
            }
        };
        this.k = true;
        this.l = true;
        this.m = true;
        if (timeUnit != null) {
            if (j <= 0) {
                this.h = 0L;
            } else {
                this.h = Math.max(timeUnit.toNanos(j), f);
            }
            if (j2 <= 0) {
                this.i = 0L;
            } else {
                this.i = Math.max(timeUnit.toNanos(j2), f);
            }
            if (j3 <= 0) {
                this.j = 0L;
            } else {
                this.j = Math.max(timeUnit.toNanos(j3), f);
            }
        } else {
            throw new NullPointerException("unit");
        }
    }

    public long getReaderIdleTimeInMillis() {
        return TimeUnit.NANOSECONDS.toMillis(this.h);
    }

    public long getWriterIdleTimeInMillis() {
        return TimeUnit.NANOSECONDS.toMillis(this.i);
    }

    public long getAllIdleTimeInMillis() {
        return TimeUnit.NANOSECONDS.toMillis(this.j);
    }

    @Override // io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (channelHandlerContext.channel().isActive() && channelHandlerContext.channel().isRegistered()) {
            a(channelHandlerContext);
        }
    }

    @Override // io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
        a();
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelRegistered(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (channelHandlerContext.channel().isActive()) {
            a(channelHandlerContext);
        }
        super.channelRegistered(channelHandlerContext);
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
        a(channelHandlerContext);
        super.channelActive(channelHandlerContext);
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
        a();
        super.channelInactive(channelHandlerContext);
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
        if (this.h > 0 || this.j > 0) {
            this.o = true;
            this.m = true;
            this.k = true;
        }
        channelHandlerContext.fireChannelRead(obj);
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (this.h > 0 || this.j > 0) {
            this.b = System.nanoTime();
            this.o = false;
        }
        channelHandlerContext.fireChannelReadComplete();
    }

    @Override // io.netty.channel.ChannelDuplexHandler, io.netty.channel.ChannelOutboundHandler
    public void write(ChannelHandlerContext channelHandlerContext, Object obj, ChannelPromise channelPromise) throws Exception {
        if (this.i > 0 || this.j > 0) {
            ChannelPromise unvoid = channelPromise.unvoid();
            unvoid.addListener((GenericFutureListener<? extends Future<? super Void>>) this.g);
            channelHandlerContext.write(obj, unvoid);
            return;
        }
        channelHandlerContext.write(obj, channelPromise);
    }

    private void a(ChannelHandlerContext channelHandlerContext) {
        switch (this.n) {
            case 1:
            case 2:
                return;
            default:
                this.n = 1;
                EventExecutor executor = channelHandlerContext.executor();
                long nanoTime = System.nanoTime();
                this.d = nanoTime;
                this.b = nanoTime;
                if (this.h > 0) {
                    this.a = executor.schedule((Runnable) new b(channelHandlerContext), this.h, TimeUnit.NANOSECONDS);
                }
                if (this.i > 0) {
                    this.c = executor.schedule((Runnable) new c(channelHandlerContext), this.i, TimeUnit.NANOSECONDS);
                }
                if (this.j > 0) {
                    this.e = executor.schedule((Runnable) new a(channelHandlerContext), this.j, TimeUnit.NANOSECONDS);
                    return;
                }
                return;
        }
    }

    private void a() {
        this.n = 2;
        if (this.a != null) {
            this.a.cancel(false);
            this.a = null;
        }
        if (this.c != null) {
            this.c.cancel(false);
            this.c = null;
        }
        if (this.e != null) {
            this.e.cancel(false);
            this.e = null;
        }
    }

    protected void channelIdle(ChannelHandlerContext channelHandlerContext, IdleStateEvent idleStateEvent) throws Exception {
        channelHandlerContext.fireUserEventTriggered((Object) idleStateEvent);
    }

    protected IdleStateEvent newIdleStateEvent(IdleState idleState, boolean z) {
        switch (idleState) {
            case ALL_IDLE:
                return z ? IdleStateEvent.FIRST_ALL_IDLE_STATE_EVENT : IdleStateEvent.ALL_IDLE_STATE_EVENT;
            case READER_IDLE:
                return z ? IdleStateEvent.FIRST_READER_IDLE_STATE_EVENT : IdleStateEvent.READER_IDLE_STATE_EVENT;
            case WRITER_IDLE:
                return z ? IdleStateEvent.FIRST_WRITER_IDLE_STATE_EVENT : IdleStateEvent.WRITER_IDLE_STATE_EVENT;
            default:
                throw new Error();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public final class b implements Runnable {
        private final ChannelHandlerContext b;

        b(ChannelHandlerContext channelHandlerContext) {
            this.b = channelHandlerContext;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.b.channel().isOpen()) {
                long j = IdleStateHandler.this.h;
                if (!IdleStateHandler.this.o) {
                    j -= System.nanoTime() - IdleStateHandler.this.b;
                }
                if (j <= 0) {
                    IdleStateHandler.this.a = this.b.executor().schedule((Runnable) this, IdleStateHandler.this.h, TimeUnit.NANOSECONDS);
                    try {
                        IdleStateEvent newIdleStateEvent = IdleStateHandler.this.newIdleStateEvent(IdleState.READER_IDLE, IdleStateHandler.this.k);
                        if (IdleStateHandler.this.k) {
                            IdleStateHandler.this.k = false;
                        }
                        IdleStateHandler.this.channelIdle(this.b, newIdleStateEvent);
                    } catch (Throwable th) {
                        this.b.fireExceptionCaught(th);
                    }
                } else {
                    IdleStateHandler.this.a = this.b.executor().schedule((Runnable) this, j, TimeUnit.NANOSECONDS);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public final class c implements Runnable {
        private final ChannelHandlerContext b;

        c(ChannelHandlerContext channelHandlerContext) {
            this.b = channelHandlerContext;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.b.channel().isOpen()) {
                long nanoTime = IdleStateHandler.this.i - (System.nanoTime() - IdleStateHandler.this.d);
                if (nanoTime <= 0) {
                    IdleStateHandler.this.c = this.b.executor().schedule((Runnable) this, IdleStateHandler.this.i, TimeUnit.NANOSECONDS);
                    try {
                        IdleStateEvent newIdleStateEvent = IdleStateHandler.this.newIdleStateEvent(IdleState.WRITER_IDLE, IdleStateHandler.this.l);
                        if (IdleStateHandler.this.l) {
                            IdleStateHandler.this.l = false;
                        }
                        IdleStateHandler.this.channelIdle(this.b, newIdleStateEvent);
                    } catch (Throwable th) {
                        this.b.fireExceptionCaught(th);
                    }
                } else {
                    IdleStateHandler.this.c = this.b.executor().schedule((Runnable) this, nanoTime, TimeUnit.NANOSECONDS);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public final class a implements Runnable {
        private final ChannelHandlerContext b;

        a(ChannelHandlerContext channelHandlerContext) {
            this.b = channelHandlerContext;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.b.channel().isOpen()) {
                long j = IdleStateHandler.this.j;
                if (!IdleStateHandler.this.o) {
                    j -= System.nanoTime() - Math.max(IdleStateHandler.this.b, IdleStateHandler.this.d);
                }
                if (j <= 0) {
                    IdleStateHandler.this.e = this.b.executor().schedule((Runnable) this, IdleStateHandler.this.j, TimeUnit.NANOSECONDS);
                    try {
                        IdleStateEvent newIdleStateEvent = IdleStateHandler.this.newIdleStateEvent(IdleState.ALL_IDLE, IdleStateHandler.this.m);
                        if (IdleStateHandler.this.m) {
                            IdleStateHandler.this.m = false;
                        }
                        IdleStateHandler.this.channelIdle(this.b, newIdleStateEvent);
                    } catch (Throwable th) {
                        this.b.fireExceptionCaught(th);
                    }
                } else {
                    IdleStateHandler.this.e = this.b.executor().schedule((Runnable) this, j, TimeUnit.NANOSECONDS);
                }
            }
        }
    }
}
