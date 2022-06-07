package io.netty.handler.traffic;

import com.alibaba.fastjson.asm.Opcodes;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes4.dex */
public class TrafficCounter {
    private static final InternalLogger i = InternalLoggerFactory.getInstance(TrafficCounter.class);
    final String c;
    final AbstractTrafficShapingHandler d;
    final ScheduledExecutorService e;
    Runnable f;
    volatile ScheduledFuture<?> g;
    volatile boolean h;
    private long l;
    private long m;
    private long p;
    private long q;
    private long r;
    private volatile long s;
    private volatile long t;
    private volatile long u;
    private volatile long v;
    private long x;
    private final AtomicLong j = new AtomicLong();
    private final AtomicLong k = new AtomicLong();
    private final AtomicLong n = new AtomicLong();
    private final AtomicLong o = new AtomicLong();
    final AtomicLong a = new AtomicLong();
    private final AtomicLong w = new AtomicLong();
    final AtomicLong b = new AtomicLong(1000);

    public static long milliSecondFromNano() {
        return System.nanoTime() / 1000000;
    }

    /* loaded from: classes4.dex */
    public final class a implements Runnable {
        private a() {
            TrafficCounter.this = r1;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (TrafficCounter.this.h) {
                TrafficCounter.this.a(TrafficCounter.milliSecondFromNano());
                if (TrafficCounter.this.d != null) {
                    TrafficCounter.this.d.doAccounting(TrafficCounter.this);
                }
                TrafficCounter trafficCounter = TrafficCounter.this;
                trafficCounter.g = trafficCounter.e.schedule(this, TrafficCounter.this.b.get(), TimeUnit.MILLISECONDS);
            }
        }
    }

    public synchronized void start() {
        if (!this.h) {
            this.a.set(milliSecondFromNano());
            long j = this.b.get();
            if (j > 0 && this.e != null) {
                this.h = true;
                this.f = new a();
                this.g = this.e.schedule(this.f, j, TimeUnit.MILLISECONDS);
            }
        }
    }

    public synchronized void stop() {
        if (this.h) {
            this.h = false;
            a(milliSecondFromNano());
            if (this.d != null) {
                this.d.doAccounting(this);
            }
            if (this.g != null) {
                this.g.cancel(true);
            }
        }
    }

    public synchronized void a(long j) {
        long andSet = j - this.a.getAndSet(j);
        if (andSet != 0) {
            if (i.isDebugEnabled() && andSet > (checkInterval() << 1)) {
                InternalLogger internalLogger = i;
                internalLogger.debug("Acct schedule not ok: " + andSet + " > 2*" + checkInterval() + " from " + this.c);
            }
            this.t = this.k.getAndSet(0L);
            this.s = this.j.getAndSet(0L);
            this.r = (this.t * 1000) / andSet;
            this.q = (this.s * 1000) / andSet;
            this.x = (this.w.getAndSet(0L) * 1000) / andSet;
            this.u = Math.max(this.u, this.l);
            this.v = Math.max(this.v, this.m);
        }
    }

    public TrafficCounter(ScheduledExecutorService scheduledExecutorService, String str, long j) {
        if (str != null) {
            this.d = null;
            this.e = scheduledExecutorService;
            this.c = str;
            e(j);
            return;
        }
        throw new NullPointerException("name");
    }

    public TrafficCounter(AbstractTrafficShapingHandler abstractTrafficShapingHandler, ScheduledExecutorService scheduledExecutorService, String str, long j) {
        if (abstractTrafficShapingHandler == null) {
            throw new IllegalArgumentException("trafficShapingHandler");
        } else if (str != null) {
            this.d = abstractTrafficShapingHandler;
            this.e = scheduledExecutorService;
            this.c = str;
            e(j);
        } else {
            throw new NullPointerException("name");
        }
    }

    private void e(long j) {
        this.p = System.currentTimeMillis();
        this.l = milliSecondFromNano();
        long j2 = this.l;
        this.m = j2;
        this.u = j2;
        this.v = j2;
        configure(j);
    }

    public void configure(long j) {
        long j2 = (j / 10) * 10;
        if (this.b.getAndSet(j2) == j2) {
            return;
        }
        if (j2 <= 0) {
            stop();
            this.a.set(milliSecondFromNano());
            return;
        }
        start();
    }

    void b(long j) {
        this.k.addAndGet(j);
        this.o.addAndGet(j);
    }

    void c(long j) {
        this.j.addAndGet(j);
        this.n.addAndGet(j);
    }

    public void d(long j) {
        this.w.addAndGet(j);
    }

    public long checkInterval() {
        return this.b.get();
    }

    public long lastReadThroughput() {
        return this.r;
    }

    public long lastWriteThroughput() {
        return this.q;
    }

    public long lastReadBytes() {
        return this.t;
    }

    public long lastWrittenBytes() {
        return this.s;
    }

    public long currentReadBytes() {
        return this.k.get();
    }

    public long currentWrittenBytes() {
        return this.j.get();
    }

    public long lastTime() {
        return this.a.get();
    }

    public long cumulativeWrittenBytes() {
        return this.n.get();
    }

    public long cumulativeReadBytes() {
        return this.o.get();
    }

    public long lastCumulativeTime() {
        return this.p;
    }

    public AtomicLong getRealWrittenBytes() {
        return this.w;
    }

    public long getRealWriteThroughput() {
        return this.x;
    }

    public void resetCumulativeTime() {
        this.p = System.currentTimeMillis();
        this.o.set(0L);
        this.n.set(0L);
    }

    public String name() {
        return this.c;
    }

    @Deprecated
    public long readTimeToWait(long j, long j2, long j3) {
        return readTimeToWait(j, j2, j3, milliSecondFromNano());
    }

    public long readTimeToWait(long j, long j2, long j3, long j4) {
        b(j);
        if (j == 0 || j2 == 0) {
            return 0L;
        }
        long j5 = this.a.get();
        long j6 = this.k.get();
        long j7 = this.m;
        long j8 = this.t;
        long j9 = j4 - j5;
        long max = Math.max(this.v - j5, 0L);
        if (j9 > 10) {
            long j10 = (((1000 * j6) / j2) - j9) + max;
            if (j10 > 10) {
                if (i.isDebugEnabled()) {
                    InternalLogger internalLogger = i;
                    internalLogger.debug("Time: " + j10 + ':' + j6 + ':' + j9 + ':' + max);
                }
                if (j10 > j3 && (j4 + j10) - j7 > j3) {
                    j10 = j3;
                }
                this.m = Math.max(j7, j4 + j10);
                return j10;
            }
            this.m = Math.max(j7, j4);
            return 0L;
        }
        long j11 = j6 + j8;
        long j12 = j9 + this.b.get();
        long j13 = (((1000 * j11) / j2) - j12) + max;
        if (j13 > 10) {
            if (i.isDebugEnabled()) {
                InternalLogger internalLogger2 = i;
                internalLogger2.debug("Time: " + j13 + ':' + j11 + ':' + j12 + ':' + max);
            }
            if (j13 > j3 && (j4 + j13) - j7 > j3) {
                j13 = j3;
            }
            this.m = Math.max(j7, j4 + j13);
            return j13;
        }
        this.m = Math.max(j7, j4);
        return 0L;
    }

    @Deprecated
    public long writeTimeToWait(long j, long j2, long j3) {
        return writeTimeToWait(j, j2, j3, milliSecondFromNano());
    }

    public long writeTimeToWait(long j, long j2, long j3, long j4) {
        c(j);
        if (j == 0 || j2 == 0) {
            return 0L;
        }
        long j5 = this.a.get();
        long j6 = this.j.get();
        long j7 = this.s;
        long j8 = this.l;
        long max = Math.max(this.u - j5, 0L);
        long j9 = j4 - j5;
        if (j9 > 10) {
            long j10 = (((1000 * j6) / j2) - j9) + max;
            if (j10 > 10) {
                if (i.isDebugEnabled()) {
                    InternalLogger internalLogger = i;
                    internalLogger.debug("Time: " + j10 + ':' + j6 + ':' + j9 + ':' + max);
                }
                if (j10 > j3 && (j4 + j10) - j8 > j3) {
                    j10 = j3;
                }
                this.l = Math.max(j8, j4 + j10);
                return j10;
            }
            this.l = Math.max(j8, j4);
            return 0L;
        }
        long j11 = j6 + j7;
        long j12 = j9 + this.b.get();
        long j13 = (((1000 * j11) / j2) - j12) + max;
        if (j13 > 10) {
            if (i.isDebugEnabled()) {
                InternalLogger internalLogger2 = i;
                internalLogger2.debug("Time: " + j13 + ':' + j11 + ':' + j12 + ':' + max);
            }
            if (j13 > j3 && (j4 + j13) - j8 > j3) {
                j13 = j3;
            }
            this.l = Math.max(j8, j4 + j13);
            return j13;
        }
        this.l = Math.max(j8, j4);
        return 0L;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder((int) Opcodes.IF_ACMPEQ);
        sb.append("Monitor ");
        sb.append(this.c);
        sb.append(" Current Speed Read: ");
        sb.append(this.r >> 10);
        sb.append(" KB/s, ");
        sb.append("Asked Write: ");
        sb.append(this.q >> 10);
        sb.append(" KB/s, ");
        sb.append("Real Write: ");
        sb.append(this.x >> 10);
        sb.append(" KB/s, ");
        sb.append("Current Read: ");
        sb.append(this.k.get() >> 10);
        sb.append(" KB, ");
        sb.append("Current asked Write: ");
        sb.append(this.j.get() >> 10);
        sb.append(" KB, ");
        sb.append("Current real Write: ");
        sb.append(this.w.get() >> 10);
        sb.append(" KB");
        return sb.toString();
    }
}
