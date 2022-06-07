package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.google.common.util.concurrent.r;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;

@Beta
@GwtIncompatible
/* loaded from: classes2.dex */
public abstract class RateLimiter {
    private final a a;
    @MonotonicNonNullDecl
    private volatile Object b;

    abstract double a();

    abstract long a(long j);

    abstract void a(double d, long j);

    abstract long b(int i, long j);

    public static RateLimiter create(double d) {
        return a(d, a.b());
    }

    @VisibleForTesting
    static RateLimiter a(double d, a aVar) {
        r.a aVar2 = new r.a(aVar, 1.0d);
        aVar2.setRate(d);
        return aVar2;
    }

    public static RateLimiter create(double d, long j, TimeUnit timeUnit) {
        Preconditions.checkArgument(j >= 0, "warmupPeriod must not be negative: %s", j);
        return a(d, j, timeUnit, 3.0d, a.b());
    }

    @VisibleForTesting
    static RateLimiter a(double d, long j, TimeUnit timeUnit, double d2, a aVar) {
        r.b bVar = new r.b(aVar, j, timeUnit, d2);
        bVar.setRate(d);
        return bVar;
    }

    private Object b() {
        Object obj = this.b;
        if (obj == null) {
            synchronized (this) {
                obj = this.b;
                if (obj == null) {
                    obj = new Object();
                    this.b = obj;
                }
            }
        }
        return obj;
    }

    public RateLimiter(a aVar) {
        this.a = (a) Preconditions.checkNotNull(aVar);
    }

    public final void setRate(double d) {
        Preconditions.checkArgument(d > 0.0d && !Double.isNaN(d), "rate must be positive");
        synchronized (b()) {
            a(d, this.a.a());
        }
    }

    public final double getRate() {
        double a2;
        synchronized (b()) {
            a2 = a();
        }
        return a2;
    }

    @CanIgnoreReturnValue
    public double acquire() {
        return acquire(1);
    }

    @CanIgnoreReturnValue
    public double acquire(int i) {
        long a2 = a(i);
        this.a.a(a2);
        return (a2 * 1.0d) / TimeUnit.SECONDS.toMicros(1L);
    }

    final long a(int i) {
        long a2;
        b(i);
        synchronized (b()) {
            a2 = a(i, this.a.a());
        }
        return a2;
    }

    public boolean tryAcquire(long j, TimeUnit timeUnit) {
        return tryAcquire(1, j, timeUnit);
    }

    public boolean tryAcquire(int i) {
        return tryAcquire(i, 0L, TimeUnit.MICROSECONDS);
    }

    public boolean tryAcquire() {
        return tryAcquire(1, 0L, TimeUnit.MICROSECONDS);
    }

    public boolean tryAcquire(int i, long j, TimeUnit timeUnit) {
        long max = Math.max(timeUnit.toMicros(j), 0L);
        b(i);
        synchronized (b()) {
            long a2 = this.a.a();
            if (!a(a2, max)) {
                return false;
            }
            this.a.a(a(i, a2));
            return true;
        }
    }

    private boolean a(long j, long j2) {
        return a(j) - j2 <= j;
    }

    final long a(int i, long j) {
        return Math.max(b(i, j) - j, 0L);
    }

    public String toString() {
        return String.format(Locale.ROOT, "RateLimiter[stableRate=%3.1fqps]", Double.valueOf(getRate()));
    }

    /* loaded from: classes2.dex */
    public static abstract class a {
        protected abstract long a();

        protected abstract void a(long j);

        protected a() {
        }

        public static a b() {
            return new a() { // from class: com.google.common.util.concurrent.RateLimiter.a.1
                final Stopwatch a = Stopwatch.createStarted();

                @Override // com.google.common.util.concurrent.RateLimiter.a
                protected long a() {
                    return this.a.elapsed(TimeUnit.MICROSECONDS);
                }

                @Override // com.google.common.util.concurrent.RateLimiter.a
                protected void a(long j) {
                    if (j > 0) {
                        Uninterruptibles.sleepUninterruptibly(j, TimeUnit.MICROSECONDS);
                    }
                }
            };
        }
    }

    private static void b(int i) {
        Preconditions.checkArgument(i > 0, "Requested permits (%s) must be positive", i);
    }
}
