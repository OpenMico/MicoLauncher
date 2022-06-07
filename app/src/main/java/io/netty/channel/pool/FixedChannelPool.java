package io.netty.channel.pool;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.Promise;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.OneTimeTask;
import java.nio.channels.ClosedChannelException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* loaded from: classes4.dex */
public final class FixedChannelPool extends SimpleChannelPool {
    static final /* synthetic */ boolean a = !FixedChannelPool.class.desiredAssertionStatus();
    private static final IllegalStateException c = new IllegalStateException("Too many outstanding acquire operations");
    private static final TimeoutException d = new TimeoutException("Acquire operation took longer then configured maximum time");
    private final EventExecutor e;
    private final long f;
    private final Runnable g;
    private final Queue<b> h;
    private final int i;
    private final int j;
    private int k;
    private int l;
    private boolean m;

    /* loaded from: classes4.dex */
    public enum AcquireTimeoutAction {
        NEW,
        FAIL
    }

    static {
        c.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
        d.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
    }

    static /* synthetic */ int f(FixedChannelPool fixedChannelPool) {
        int i = fixedChannelPool.l - 1;
        fixedChannelPool.l = i;
        return i;
    }

    static /* synthetic */ int h(FixedChannelPool fixedChannelPool) {
        int i = fixedChannelPool.k;
        fixedChannelPool.k = i + 1;
        return i;
    }

    public FixedChannelPool(Bootstrap bootstrap, ChannelPoolHandler channelPoolHandler, int i) {
        this(bootstrap, channelPoolHandler, i, Integer.MAX_VALUE);
    }

    public FixedChannelPool(Bootstrap bootstrap, ChannelPoolHandler channelPoolHandler, int i, int i2) {
        this(bootstrap, channelPoolHandler, ChannelHealthChecker.ACTIVE, null, -1L, i, i2);
    }

    public FixedChannelPool(Bootstrap bootstrap, ChannelPoolHandler channelPoolHandler, ChannelHealthChecker channelHealthChecker, AcquireTimeoutAction acquireTimeoutAction, long j, int i, int i2) {
        this(bootstrap, channelPoolHandler, channelHealthChecker, acquireTimeoutAction, j, i, i2, true);
    }

    public FixedChannelPool(Bootstrap bootstrap, ChannelPoolHandler channelPoolHandler, ChannelHealthChecker channelHealthChecker, AcquireTimeoutAction acquireTimeoutAction, long j, int i, int i2, boolean z) {
        super(bootstrap, channelPoolHandler, channelHealthChecker, z);
        this.h = new ArrayDeque();
        if (i < 1) {
            throw new IllegalArgumentException("maxConnections: " + i + " (expected: >= 1)");
        } else if (i2 >= 1) {
            if (acquireTimeoutAction == null && j == -1) {
                this.g = null;
                this.f = -1L;
            } else if (acquireTimeoutAction == null && j != -1) {
                throw new NullPointerException("action");
            } else if (acquireTimeoutAction == null || j >= 0) {
                this.f = TimeUnit.MILLISECONDS.toNanos(j);
                switch (acquireTimeoutAction) {
                    case FAIL:
                        this.g = new c() { // from class: io.netty.channel.pool.FixedChannelPool.1
                            @Override // io.netty.channel.pool.FixedChannelPool.c
                            public void a(b bVar) {
                                bVar.d.setFailure(FixedChannelPool.d);
                            }
                        };
                        break;
                    case NEW:
                        this.g = new c() { // from class: io.netty.channel.pool.FixedChannelPool.2
                            @Override // io.netty.channel.pool.FixedChannelPool.c
                            public void a(b bVar) {
                                bVar.a();
                                FixedChannelPool.super.acquire(bVar.d);
                            }
                        };
                        break;
                    default:
                        throw new Error();
                }
            } else {
                throw new IllegalArgumentException("acquireTimeoutMillis: " + j + " (expected: >= 1)");
            }
            this.e = bootstrap.group().next();
            this.i = i;
            this.j = i2;
        } else {
            throw new IllegalArgumentException("maxPendingAcquires: " + i2 + " (expected: >= 1)");
        }
    }

    @Override // io.netty.channel.pool.SimpleChannelPool, io.netty.channel.pool.ChannelPool
    public Future<Channel> acquire(final Promise<Channel> promise) {
        try {
            if (this.e.inEventLoop()) {
                a(promise);
            } else {
                this.e.execute(new OneTimeTask() { // from class: io.netty.channel.pool.FixedChannelPool.3
                    @Override // java.lang.Runnable
                    public void run() {
                        FixedChannelPool.this.a((Promise<Channel>) promise);
                    }
                });
            }
        } catch (Throwable th) {
            promise.setFailure(th);
        }
        return promise;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Promise<Channel> promise) {
        if (!a && !this.e.inEventLoop()) {
            throw new AssertionError();
        } else if (this.m) {
            promise.setFailure(new IllegalStateException("FixedChannelPooled was closed"));
        } else {
            int i = this.k;
            if (i >= this.i) {
                if (this.l >= this.j) {
                    promise.setFailure(c);
                } else {
                    b bVar = new b(promise);
                    if (this.h.offer(bVar)) {
                        this.l++;
                        Runnable runnable = this.g;
                        if (runnable != null) {
                            bVar.f = this.e.schedule(runnable, this.f, TimeUnit.NANOSECONDS);
                        }
                    } else {
                        promise.setFailure(c);
                    }
                }
                if (!a && this.l <= 0) {
                    throw new AssertionError();
                }
            } else if (a || i >= 0) {
                Promise<Channel> newPromise = this.e.newPromise();
                a aVar = new a(promise);
                aVar.a();
                newPromise.addListener((GenericFutureListener<? extends Future<? super Channel>>) aVar);
                super.acquire(newPromise);
            } else {
                throw new AssertionError();
            }
        }
    }

    @Override // io.netty.channel.pool.SimpleChannelPool, io.netty.channel.pool.ChannelPool
    public Future<Void> release(Channel channel, final Promise<Void> promise) {
        Promise newPromise = this.e.newPromise();
        super.release(channel, newPromise.addListener((GenericFutureListener) new FutureListener<Void>() { // from class: io.netty.channel.pool.FixedChannelPool.4
            static final /* synthetic */ boolean a = !FixedChannelPool.class.desiredAssertionStatus();

            @Override // io.netty.util.concurrent.GenericFutureListener
            public void operationComplete(Future<Void> future) throws Exception {
                if (!a && !FixedChannelPool.this.e.inEventLoop()) {
                    throw new AssertionError();
                } else if (FixedChannelPool.this.m) {
                    promise.setFailure(new IllegalStateException("FixedChannelPooled was closed"));
                } else if (future.isSuccess()) {
                    FixedChannelPool.this.b();
                    promise.setSuccess(null);
                } else {
                    if (!(future.cause() instanceof IllegalArgumentException)) {
                        FixedChannelPool.this.b();
                    }
                    promise.setFailure(future.cause());
                }
            }
        }));
        return newPromise;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        this.k--;
        if (a || this.k >= 0) {
            c();
            return;
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        b poll;
        while (this.k < this.i && (poll = this.h.poll()) != null) {
            ScheduledFuture<?> scheduledFuture = poll.f;
            if (scheduledFuture != null) {
                scheduledFuture.cancel(false);
            }
            this.l--;
            poll.a();
            super.acquire(poll.d);
        }
        if (!a && this.l < 0) {
            throw new AssertionError();
        } else if (!a && this.k < 0) {
            throw new AssertionError();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public final class b extends a {
        final Promise<Channel> d;
        final long e;
        ScheduledFuture<?> f;

        public b(Promise<Channel> promise) {
            super(promise);
            this.e = System.nanoTime() + FixedChannelPool.this.f;
            this.d = FixedChannelPool.this.e.newPromise().addListener((GenericFutureListener) this);
        }
    }

    /* loaded from: classes4.dex */
    private abstract class c implements Runnable {
        static final /* synthetic */ boolean b = !FixedChannelPool.class.desiredAssertionStatus();

        public abstract void a(b bVar);

        private c() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            if (b || FixedChannelPool.this.e.inEventLoop()) {
                long nanoTime = System.nanoTime();
                while (true) {
                    b bVar = (b) FixedChannelPool.this.h.peek();
                    if (bVar != null && nanoTime - bVar.e >= 0) {
                        FixedChannelPool.this.h.remove();
                        FixedChannelPool.f(FixedChannelPool.this);
                        a(bVar);
                    } else {
                        return;
                    }
                }
            } else {
                throw new AssertionError();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public class a implements FutureListener<Channel> {
        static final /* synthetic */ boolean b = !FixedChannelPool.class.desiredAssertionStatus();
        protected boolean a;
        private final Promise<Channel> d;

        a(Promise<Channel> promise) {
            this.d = promise;
        }

        @Override // io.netty.util.concurrent.GenericFutureListener
        public void operationComplete(Future<Channel> future) throws Exception {
            if (!b && !FixedChannelPool.this.e.inEventLoop()) {
                throw new AssertionError();
            } else if (FixedChannelPool.this.m) {
                this.d.setFailure(new IllegalStateException("FixedChannelPooled was closed"));
            } else if (future.isSuccess()) {
                this.d.setSuccess(future.getNow());
            } else {
                if (this.a) {
                    FixedChannelPool.this.b();
                } else {
                    FixedChannelPool.this.c();
                }
                this.d.setFailure(future.cause());
            }
        }

        public void a() {
            if (!this.a) {
                FixedChannelPool.h(FixedChannelPool.this);
                this.a = true;
            }
        }
    }

    @Override // io.netty.channel.pool.SimpleChannelPool, io.netty.channel.pool.ChannelPool, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.e.execute(new OneTimeTask() { // from class: io.netty.channel.pool.FixedChannelPool.5
            @Override // java.lang.Runnable
            public void run() {
                if (!FixedChannelPool.this.m) {
                    FixedChannelPool.this.m = true;
                    while (true) {
                        b bVar = (b) FixedChannelPool.this.h.poll();
                        if (bVar == null) {
                            FixedChannelPool.this.k = 0;
                            FixedChannelPool.this.l = 0;
                            FixedChannelPool.super.close();
                            return;
                        }
                        ScheduledFuture<?> scheduledFuture = bVar.f;
                        if (scheduledFuture != null) {
                            scheduledFuture.cancel(false);
                        }
                        bVar.d.setFailure(new ClosedChannelException());
                    }
                }
            }
        });
    }
}
