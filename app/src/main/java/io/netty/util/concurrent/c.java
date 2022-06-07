package io.netty.util.concurrent;

import io.netty.util.internal.StringUtil;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ScheduledFutureTask.java */
/* loaded from: classes4.dex */
public final class c<V> extends b<V> implements ScheduledFuture<V> {
    static final /* synthetic */ boolean b = !c.class.desiredAssertionStatus();
    private static final AtomicLong c = new AtomicLong();
    private static final long d = System.nanoTime();
    private final long e;
    private long f;
    private final long g;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static long b() {
        return System.nanoTime() - d;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static long a(long j) {
        return b() + j;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public c(AbstractScheduledEventExecutor abstractScheduledEventExecutor, Runnable runnable, V v, long j) {
        this(abstractScheduledEventExecutor, a(runnable, v), j);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public c(AbstractScheduledEventExecutor abstractScheduledEventExecutor, Callable<V> callable, long j, long j2) {
        super(abstractScheduledEventExecutor, callable);
        this.e = c.getAndIncrement();
        if (j2 != 0) {
            this.f = j;
            this.g = j2;
            return;
        }
        throw new IllegalArgumentException("period: 0 (expected: != 0)");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public c(AbstractScheduledEventExecutor abstractScheduledEventExecutor, Callable<V> callable, long j) {
        super(abstractScheduledEventExecutor, callable);
        this.e = c.getAndIncrement();
        this.f = j;
        this.g = 0L;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.util.concurrent.DefaultPromise
    public EventExecutor executor() {
        return super.executor();
    }

    public long c() {
        return this.f;
    }

    public long d() {
        return Math.max(0L, c() - b());
    }

    public long b(long j) {
        return Math.max(0L, c() - (j - d));
    }

    @Override // java.util.concurrent.Delayed
    public long getDelay(TimeUnit timeUnit) {
        return timeUnit.convert(d(), TimeUnit.NANOSECONDS);
    }

    /* renamed from: a */
    public int compareTo(Delayed delayed) {
        if (this == delayed) {
            return 0;
        }
        c cVar = (c) delayed;
        int i = ((c() - cVar.c()) > 0L ? 1 : ((c() - cVar.c()) == 0L ? 0 : -1));
        if (i < 0) {
            return -1;
        }
        if (i > 0) {
            return 1;
        }
        long j = this.e;
        long j2 = cVar.e;
        if (j < j2) {
            return -1;
        }
        if (j != j2) {
            return 1;
        }
        throw new Error();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.netty.util.concurrent.b, java.util.concurrent.RunnableFuture, java.lang.Runnable
    public void run() {
        if (b || executor().inEventLoop()) {
            try {
                if (this.g == 0) {
                    if (a()) {
                        a((c<V>) this.a.call());
                    }
                } else if (!isCancelled()) {
                    this.a.call();
                    if (!executor().isShutdown()) {
                        long j = this.g;
                        if (j > 0) {
                            this.f += j;
                        } else {
                            this.f = b() - j;
                        }
                        if (!isCancelled()) {
                            Queue<c<?>> queue = ((AbstractScheduledEventExecutor) executor()).b;
                            if (!b && queue == null) {
                                throw new AssertionError();
                            }
                            queue.add(this);
                        }
                    }
                }
            } catch (Throwable th) {
                a(th);
            }
        } else {
            throw new AssertionError();
        }
    }

    @Override // io.netty.util.concurrent.DefaultPromise, io.netty.util.concurrent.Future, java.util.concurrent.Future
    public boolean cancel(boolean z) {
        boolean cancel = super.cancel(z);
        if (cancel) {
            ((AbstractScheduledEventExecutor) executor()).b(this);
        }
        return cancel;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean a(boolean z) {
        return super.cancel(z);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.util.concurrent.b, io.netty.util.concurrent.DefaultPromise
    public StringBuilder toStringBuilder() {
        StringBuilder stringBuilder = super.toStringBuilder();
        stringBuilder.setCharAt(stringBuilder.length() - 1, StringUtil.COMMA);
        stringBuilder.append(" id: ");
        stringBuilder.append(this.e);
        stringBuilder.append(", deadline: ");
        stringBuilder.append(this.f);
        stringBuilder.append(", period: ");
        stringBuilder.append(this.g);
        stringBuilder.append(')');
        return stringBuilder;
    }
}
