package io.netty.util;

import com.google.android.exoplayer2.C;
import com.umeng.analytics.pro.ai;
import com.xiaomi.account.openauth.XiaomiOAuthConstants;
import io.netty.util.internal.MpscLinkedQueueNode;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.Collections;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/* loaded from: classes4.dex */
public class HashedWheelTimer implements Timer {
    public static final int WORKER_STATE_INIT = 0;
    public static final int WORKER_STATE_SHUTDOWN = 2;
    public static final int WORKER_STATE_STARTED = 1;
    static final InternalLogger a = InternalLoggerFactory.getInstance(HashedWheelTimer.class);
    private static final ResourceLeakDetector<HashedWheelTimer> b = new ResourceLeakDetector<>(HashedWheelTimer.class, 1, Runtime.getRuntime().availableProcessors() * 4);
    private static final AtomicIntegerFieldUpdater<HashedWheelTimer> c;
    private final ResourceLeak d;
    private final c e;
    private final Thread f;
    private volatile int g;
    private final long h;
    private final a[] i;
    private final int j;
    private final CountDownLatch k;
    private final Queue<b> l;
    private final Queue<Runnable> m;
    private volatile long n;

    private static int b(int i) {
        int i2 = 1;
        while (i2 < i) {
            i2 <<= 1;
        }
        return i2;
    }

    static {
        AtomicIntegerFieldUpdater<HashedWheelTimer> newAtomicIntegerFieldUpdater = PlatformDependent.newAtomicIntegerFieldUpdater(HashedWheelTimer.class, "workerState");
        if (newAtomicIntegerFieldUpdater == null) {
            newAtomicIntegerFieldUpdater = AtomicIntegerFieldUpdater.newUpdater(HashedWheelTimer.class, "g");
        }
        c = newAtomicIntegerFieldUpdater;
    }

    public HashedWheelTimer() {
        this(Executors.defaultThreadFactory());
    }

    public HashedWheelTimer(long j, TimeUnit timeUnit) {
        this(Executors.defaultThreadFactory(), j, timeUnit);
    }

    public HashedWheelTimer(long j, TimeUnit timeUnit, int i) {
        this(Executors.defaultThreadFactory(), j, timeUnit, i);
    }

    public HashedWheelTimer(ThreadFactory threadFactory) {
        this(threadFactory, 100L, TimeUnit.MILLISECONDS);
    }

    public HashedWheelTimer(ThreadFactory threadFactory, long j, TimeUnit timeUnit) {
        this(threadFactory, j, timeUnit, 512);
    }

    public HashedWheelTimer(ThreadFactory threadFactory, long j, TimeUnit timeUnit, int i) {
        this.e = new c();
        this.g = 0;
        this.k = new CountDownLatch(1);
        this.l = PlatformDependent.newMpscQueue();
        this.m = PlatformDependent.newMpscQueue();
        if (threadFactory == null) {
            throw new NullPointerException("threadFactory");
        } else if (timeUnit == null) {
            throw new NullPointerException("unit");
        } else if (j <= 0) {
            throw new IllegalArgumentException("tickDuration must be greater than 0: " + j);
        } else if (i > 0) {
            this.i = a(i);
            this.j = this.i.length - 1;
            this.h = timeUnit.toNanos(j);
            if (this.h < Long.MAX_VALUE / this.i.length) {
                this.f = threadFactory.newThread(this.e);
                this.d = b.open(this);
                return;
            }
            throw new IllegalArgumentException(String.format("tickDuration: %d (expected: 0 < tickDuration in nanos < %d", Long.valueOf(j), Long.valueOf(Long.MAX_VALUE / this.i.length)));
        } else {
            throw new IllegalArgumentException("ticksPerWheel must be greater than 0: " + i);
        }
    }

    private static a[] a(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("ticksPerWheel must be greater than 0: " + i);
        } else if (i <= 1073741824) {
            a[] aVarArr = new a[b(i)];
            for (int i2 = 0; i2 < aVarArr.length; i2++) {
                aVarArr[i2] = new a();
            }
            return aVarArr;
        } else {
            throw new IllegalArgumentException("ticksPerWheel may not be greater than 2^30: " + i);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public void start() {
        switch (c.get(this)) {
            case 0:
                if (c.compareAndSet(this, 0, 1)) {
                    this.f.start();
                    break;
                }
                break;
            case 1:
                break;
            case 2:
                throw new IllegalStateException("cannot be started once stopped");
            default:
                throw new Error("Invalid WorkerState");
        }
        while (this.n == 0) {
            try {
                this.k.await();
            } catch (InterruptedException unused) {
            }
        }
    }

    @Override // io.netty.util.Timer
    public Set<Timeout> stop() {
        if (Thread.currentThread() == this.f) {
            throw new IllegalStateException(HashedWheelTimer.class.getSimpleName() + ".stop() cannot be called from " + TimerTask.class.getSimpleName());
        } else if (!c.compareAndSet(this, 1, 2)) {
            c.set(this, 2);
            ResourceLeak resourceLeak = this.d;
            if (resourceLeak != null) {
                resourceLeak.close();
            }
            return Collections.emptySet();
        } else {
            boolean z = false;
            while (this.f.isAlive()) {
                this.f.interrupt();
                try {
                    this.f.join(100L);
                } catch (InterruptedException unused) {
                    z = true;
                }
            }
            if (z) {
                Thread.currentThread().interrupt();
            }
            ResourceLeak resourceLeak2 = this.d;
            if (resourceLeak2 != null) {
                resourceLeak2.close();
            }
            return this.e.a();
        }
    }

    @Override // io.netty.util.Timer
    public Timeout newTimeout(TimerTask timerTask, long j, TimeUnit timeUnit) {
        if (timerTask == null) {
            throw new NullPointerException("task");
        } else if (timeUnit != null) {
            start();
            b bVar = new b(this, timerTask, (System.nanoTime() + timeUnit.toNanos(j)) - this.n);
            this.l.add(bVar);
            return bVar;
        } else {
            throw new NullPointerException("unit");
        }
    }

    /* loaded from: classes4.dex */
    private final class c implements Runnable {
        private final Set<Timeout> b;
        private long c;

        private c() {
            this.b = new HashSet();
        }

        @Override // java.lang.Runnable
        public void run() {
            HashedWheelTimer.this.n = System.nanoTime();
            if (HashedWheelTimer.this.n == 0) {
                HashedWheelTimer.this.n = 1L;
            }
            HashedWheelTimer.this.k.countDown();
            do {
                long d = d();
                if (d > 0) {
                    c();
                    a aVar = HashedWheelTimer.this.i[(int) (this.c & HashedWheelTimer.this.j)];
                    b();
                    aVar.a(d);
                    this.c++;
                }
            } while (HashedWheelTimer.c.get(HashedWheelTimer.this) == 1);
            for (a aVar2 : HashedWheelTimer.this.i) {
                aVar2.a(this.b);
            }
            while (true) {
                b bVar = (b) HashedWheelTimer.this.l.poll();
                if (bVar == null) {
                    c();
                    return;
                } else if (!bVar.isCancelled()) {
                    this.b.add(bVar);
                }
            }
        }

        private void b() {
            b bVar;
            for (int i = 0; i < 100000 && (bVar = (b) HashedWheelTimer.this.l.poll()) != null; i++) {
                if (bVar.a() != 1) {
                    long j = bVar.h / HashedWheelTimer.this.h;
                    bVar.a = (j - this.c) / HashedWheelTimer.this.i.length;
                    HashedWheelTimer.this.i[(int) (Math.max(j, this.c) & HashedWheelTimer.this.j)].a(bVar);
                }
            }
        }

        private void c() {
            while (true) {
                Runnable runnable = (Runnable) HashedWheelTimer.this.m.poll();
                if (runnable != null) {
                    try {
                        runnable.run();
                    } catch (Throwable th) {
                        if (HashedWheelTimer.a.isWarnEnabled()) {
                            HashedWheelTimer.a.warn("An exception was thrown while process a cancellation task", th);
                        }
                    }
                } else {
                    return;
                }
            }
        }

        private long d() {
            long j = HashedWheelTimer.this.h * (this.c + 1);
            while (true) {
                long nanoTime = System.nanoTime() - HashedWheelTimer.this.n;
                long j2 = ((j - nanoTime) + 999999) / 1000000;
                if (j2 <= 0) {
                    return nanoTime == Long.MIN_VALUE ? C.TIME_UNSET : nanoTime;
                }
                if (PlatformDependent.isWindows()) {
                    j2 = (j2 / 10) * 10;
                }
                try {
                    Thread.sleep(j2);
                } catch (InterruptedException unused) {
                    if (HashedWheelTimer.c.get(HashedWheelTimer.this) == 2) {
                        return Long.MIN_VALUE;
                    }
                }
            }
        }

        public Set<Timeout> a() {
            return Collections.unmodifiableSet(this.b);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static final class b extends MpscLinkedQueueNode<Timeout> implements Timeout {
        private static final AtomicIntegerFieldUpdater<b> e;
        long a;
        b b;
        b c;
        a d;
        private final HashedWheelTimer f;
        private final TimerTask g;
        private final long h;
        private volatile int i = 0;

        /* renamed from: b */
        public b value() {
            return this;
        }

        static {
            AtomicIntegerFieldUpdater<b> newAtomicIntegerFieldUpdater = PlatformDependent.newAtomicIntegerFieldUpdater(b.class, XiaomiOAuthConstants.EXTRA_STATE_2);
            if (newAtomicIntegerFieldUpdater == null) {
                newAtomicIntegerFieldUpdater = AtomicIntegerFieldUpdater.newUpdater(b.class, ai.aA);
            }
            e = newAtomicIntegerFieldUpdater;
        }

        b(HashedWheelTimer hashedWheelTimer, TimerTask timerTask, long j) {
            this.f = hashedWheelTimer;
            this.g = timerTask;
            this.h = j;
        }

        @Override // io.netty.util.Timeout
        public Timer timer() {
            return this.f;
        }

        @Override // io.netty.util.Timeout
        public TimerTask task() {
            return this.g;
        }

        @Override // io.netty.util.Timeout
        public boolean cancel() {
            if (!a(0, 1)) {
                return false;
            }
            this.f.m.add(new Runnable() { // from class: io.netty.util.HashedWheelTimer.b.1
                @Override // java.lang.Runnable
                public void run() {
                    a aVar = b.this.d;
                    if (aVar != null) {
                        aVar.b(b.this);
                    }
                }
            });
            return true;
        }

        public boolean a(int i, int i2) {
            return e.compareAndSet(this, i, i2);
        }

        public int a() {
            return this.i;
        }

        @Override // io.netty.util.Timeout
        public boolean isCancelled() {
            return a() == 1;
        }

        @Override // io.netty.util.Timeout
        public boolean isExpired() {
            return a() == 2;
        }

        public void c() {
            if (a(0, 2)) {
                try {
                    this.g.run(this);
                } catch (Throwable th) {
                    if (HashedWheelTimer.a.isWarnEnabled()) {
                        InternalLogger internalLogger = HashedWheelTimer.a;
                        internalLogger.warn("An exception was thrown by " + TimerTask.class.getSimpleName() + '.', th);
                    }
                }
            }
        }

        public String toString() {
            long nanoTime = (this.h - System.nanoTime()) + this.f.n;
            StringBuilder sb = new StringBuilder(192);
            sb.append(StringUtil.simpleClassName(this));
            sb.append('(');
            sb.append("deadline: ");
            int i = (nanoTime > 0L ? 1 : (nanoTime == 0L ? 0 : -1));
            if (i > 0) {
                sb.append(nanoTime);
                sb.append(" ns later");
            } else if (i < 0) {
                sb.append(-nanoTime);
                sb.append(" ns ago");
            } else {
                sb.append("now");
            }
            if (isCancelled()) {
                sb.append(", cancelled");
            }
            sb.append(", task: ");
            sb.append(task());
            sb.append(')');
            return sb.toString();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static final class a {
        static final /* synthetic */ boolean a = !HashedWheelTimer.class.desiredAssertionStatus();
        private b b;
        private b c;

        private a() {
        }

        public void a(b bVar) {
            if (a || bVar.d == null) {
                bVar.d = this;
                if (this.b == null) {
                    this.c = bVar;
                    this.b = bVar;
                    return;
                }
                b bVar2 = this.c;
                bVar2.b = bVar;
                bVar.c = bVar2;
                this.c = bVar;
                return;
            }
            throw new AssertionError();
        }

        public void a(long j) {
            b bVar = this.b;
            while (bVar != null) {
                boolean z = true;
                if (bVar.a <= 0) {
                    if (bVar.h <= j) {
                        bVar.c();
                    } else {
                        throw new IllegalStateException(String.format("timeout.deadline (%d) > deadline (%d)", Long.valueOf(bVar.h), Long.valueOf(j)));
                    }
                } else if (!bVar.isCancelled()) {
                    bVar.a--;
                    z = false;
                }
                b bVar2 = bVar.b;
                if (z) {
                    b(bVar);
                }
                bVar = bVar2;
            }
        }

        public void b(b bVar) {
            b bVar2 = bVar.b;
            if (bVar.c != null) {
                bVar.c.b = bVar2;
            }
            if (bVar.b != null) {
                bVar.b.c = bVar.c;
            }
            if (bVar == this.b) {
                if (bVar == this.c) {
                    this.c = null;
                    this.b = null;
                } else {
                    this.b = bVar2;
                }
            } else if (bVar == this.c) {
                this.c = bVar.c;
            }
            bVar.c = null;
            bVar.b = null;
            bVar.d = null;
        }

        public void a(Set<Timeout> set) {
            while (true) {
                b a2 = a();
                if (a2 != null) {
                    if (!a2.isExpired() && !a2.isCancelled()) {
                        set.add(a2);
                    }
                } else {
                    return;
                }
            }
        }

        private b a() {
            b bVar = this.b;
            if (bVar == null) {
                return null;
            }
            b bVar2 = bVar.b;
            if (bVar2 == null) {
                this.b = null;
                this.c = null;
            } else {
                this.b = bVar2;
                bVar2.c = null;
            }
            bVar.b = null;
            bVar.c = null;
            bVar.d = null;
            return bVar;
        }
    }
}
