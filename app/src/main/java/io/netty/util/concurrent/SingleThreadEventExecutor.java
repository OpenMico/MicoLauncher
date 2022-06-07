package io.netty.util.concurrent;

import com.xiaomi.account.openauth.XiaomiOAuthConstants;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.lang.Thread;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/* loaded from: classes4.dex */
public abstract class SingleThreadEventExecutor extends AbstractScheduledEventExecutor {
    private static final AtomicIntegerFieldUpdater<SingleThreadEventExecutor> g;
    private static final AtomicReferenceFieldUpdater<SingleThreadEventExecutor, ThreadProperties> h;
    private static final long w;
    private final Queue<Runnable> i;
    private volatile Thread j;
    private volatile ThreadProperties k;
    private final Executor l;
    private volatile boolean m;
    private final Semaphore n;
    private final Set<Runnable> o;
    private final boolean p;
    private long q;
    private volatile int r;
    private volatile long s;
    private volatile long t;
    private long u;
    private final Promise<?> v;
    static final /* synthetic */ boolean d = !SingleThreadEventExecutor.class.desiredAssertionStatus();
    private static final InternalLogger a = InternalLoggerFactory.getInstance(SingleThreadEventExecutor.class);
    private static final Runnable e = new Runnable() { // from class: io.netty.util.concurrent.SingleThreadEventExecutor.1
        @Override // java.lang.Runnable
        public void run() {
        }
    };
    private static final Runnable f = new Runnable() { // from class: io.netty.util.concurrent.SingleThreadEventExecutor.2
        @Override // java.lang.Runnable
        public void run() {
        }
    };

    protected void cleanup() {
    }

    protected abstract void run();

    protected boolean wakesUpForTask(Runnable runnable) {
        return true;
    }

    static {
        AtomicIntegerFieldUpdater<SingleThreadEventExecutor> newAtomicIntegerFieldUpdater = PlatformDependent.newAtomicIntegerFieldUpdater(SingleThreadEventExecutor.class, XiaomiOAuthConstants.EXTRA_STATE_2);
        if (newAtomicIntegerFieldUpdater == null) {
            newAtomicIntegerFieldUpdater = AtomicIntegerFieldUpdater.newUpdater(SingleThreadEventExecutor.class, "r");
        }
        g = newAtomicIntegerFieldUpdater;
        AtomicReferenceFieldUpdater<SingleThreadEventExecutor, ThreadProperties> newAtomicReferenceFieldUpdater = PlatformDependent.newAtomicReferenceFieldUpdater(SingleThreadEventExecutor.class, "threadProperties");
        if (newAtomicReferenceFieldUpdater == null) {
            newAtomicReferenceFieldUpdater = AtomicReferenceFieldUpdater.newUpdater(SingleThreadEventExecutor.class, ThreadProperties.class, "k");
        }
        h = newAtomicReferenceFieldUpdater;
        w = TimeUnit.SECONDS.toNanos(1L);
    }

    static /* synthetic */ Thread a(SingleThreadEventExecutor singleThreadEventExecutor, Thread thread) {
        singleThreadEventExecutor.j = thread;
        return thread;
    }

    static /* synthetic */ InternalLogger b() {
        return a;
    }

    static /* synthetic */ boolean b(SingleThreadEventExecutor singleThreadEventExecutor) {
        return singleThreadEventExecutor.m;
    }

    static /* synthetic */ Thread c(SingleThreadEventExecutor singleThreadEventExecutor) {
        return singleThreadEventExecutor.j;
    }

    static /* synthetic */ AtomicIntegerFieldUpdater c() {
        return g;
    }

    static /* synthetic */ long d(SingleThreadEventExecutor singleThreadEventExecutor) {
        return singleThreadEventExecutor.u;
    }

    static /* synthetic */ Semaphore e(SingleThreadEventExecutor singleThreadEventExecutor) {
        return singleThreadEventExecutor.n;
    }

    static /* synthetic */ Queue f(SingleThreadEventExecutor singleThreadEventExecutor) {
        return singleThreadEventExecutor.i;
    }

    static /* synthetic */ Promise g(SingleThreadEventExecutor singleThreadEventExecutor) {
        return singleThreadEventExecutor.v;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public SingleThreadEventExecutor(EventExecutorGroup eventExecutorGroup, ThreadFactory threadFactory, boolean z) {
        this(eventExecutorGroup, new ThreadPerTaskExecutor(threadFactory), z);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public SingleThreadEventExecutor(EventExecutorGroup eventExecutorGroup, Executor executor, boolean z) {
        super(eventExecutorGroup);
        this.n = new Semaphore(0);
        this.o = new LinkedHashSet();
        this.r = 1;
        this.v = new DefaultPromise(GlobalEventExecutor.INSTANCE);
        if (executor != null) {
            this.p = z;
            this.l = executor;
            this.i = newTaskQueue();
            return;
        }
        throw new NullPointerException("executor");
    }

    protected Queue<Runnable> newTaskQueue() {
        return new LinkedBlockingQueue();
    }

    protected void interruptThread() {
        Thread thread = this.j;
        if (thread == null) {
            this.m = true;
        } else {
            thread.interrupt();
        }
    }

    protected Runnable pollTask() {
        Runnable poll;
        if (d || inEventLoop()) {
            do {
                poll = this.i.poll();
            } while (poll == e);
            return poll;
        }
        throw new AssertionError();
    }

    protected Runnable takeTask() {
        Runnable runnable;
        if (d || inEventLoop()) {
            Queue<Runnable> queue = this.i;
            if (queue instanceof BlockingQueue) {
                BlockingQueue blockingQueue = (BlockingQueue) queue;
                do {
                    c<?> e2 = e();
                    if (e2 == null) {
                        try {
                            Runnable runnable2 = (Runnable) blockingQueue.take();
                            try {
                                if (runnable2 == e) {
                                    return null;
                                }
                                return runnable2;
                            } catch (InterruptedException unused) {
                                return runnable2;
                            }
                        } catch (InterruptedException unused2) {
                            return null;
                        }
                    } else {
                        long d2 = e2.d();
                        if (d2 > 0) {
                            try {
                                runnable = (Runnable) blockingQueue.poll(d2, TimeUnit.NANOSECONDS);
                            } catch (InterruptedException unused3) {
                                return null;
                            }
                        } else {
                            runnable = null;
                        }
                        if (runnable == null) {
                            a();
                            runnable = (Runnable) blockingQueue.poll();
                            continue;
                        }
                    }
                } while (runnable == null);
                return runnable;
            }
            throw new UnsupportedOperationException();
        }
        throw new AssertionError();
    }

    private void a() {
        if (hasScheduledTasks()) {
            long nanoTime = AbstractScheduledEventExecutor.nanoTime();
            while (true) {
                Runnable pollScheduledTask = pollScheduledTask(nanoTime);
                if (pollScheduledTask != null) {
                    this.i.add(pollScheduledTask);
                } else {
                    return;
                }
            }
        }
    }

    protected Runnable peekTask() {
        if (d || inEventLoop()) {
            return this.i.peek();
        }
        throw new AssertionError();
    }

    protected boolean hasTasks() {
        if (d || inEventLoop()) {
            return !this.i.isEmpty();
        }
        throw new AssertionError();
    }

    public int pendingTasks() {
        return this.i.size();
    }

    protected void addTask(Runnable runnable) {
        if (runnable != null) {
            if (isShutdown()) {
                reject();
            }
            this.i.add(runnable);
            return;
        }
        throw new NullPointerException("task");
    }

    protected boolean removeTask(Runnable runnable) {
        if (runnable != null) {
            return this.i.remove(runnable);
        }
        throw new NullPointerException("task");
    }

    protected boolean runAllTasks() {
        a();
        Runnable pollTask = pollTask();
        if (pollTask == null) {
            return false;
        }
        do {
            try {
                pollTask.run();
            } catch (Throwable th) {
                a.warn("A task raised an exception.", th);
            }
            pollTask = pollTask();
        } while (pollTask != null);
        this.q = c.b();
        return true;
    }

    protected boolean runAllTasks(long j) {
        long j2;
        a();
        Runnable pollTask = pollTask();
        if (pollTask == null) {
            return false;
        }
        long b = c.b() + j;
        long j3 = 0;
        while (true) {
            try {
                pollTask.run();
            } catch (Throwable th) {
                a.warn("A task raised an exception.", th);
            }
            j3++;
            if ((63 & j3) == 0) {
                j2 = c.b();
                if (j2 >= b) {
                    break;
                }
            }
            pollTask = pollTask();
            if (pollTask == null) {
                j2 = c.b();
                break;
            }
        }
        this.q = j2;
        return true;
    }

    protected long delayNanos(long j) {
        c<?> e2 = e();
        if (e2 == null) {
            return w;
        }
        return e2.b(j);
    }

    protected void updateLastExecutionTime() {
        this.q = c.b();
    }

    protected void wakeup(boolean z) {
        if (!z || g.get(this) == 3) {
            this.i.add(e);
        }
    }

    @Override // io.netty.util.concurrent.EventExecutor
    public boolean inEventLoop(Thread thread) {
        return thread == this.j;
    }

    public void addShutdownHook(final Runnable runnable) {
        if (inEventLoop()) {
            this.o.add(runnable);
        } else {
            execute(new Runnable() { // from class: io.netty.util.concurrent.SingleThreadEventExecutor.3
                @Override // java.lang.Runnable
                public void run() {
                    SingleThreadEventExecutor.this.o.add(runnable);
                }
            });
        }
    }

    public void removeShutdownHook(final Runnable runnable) {
        if (inEventLoop()) {
            this.o.remove(runnable);
        } else {
            execute(new Runnable() { // from class: io.netty.util.concurrent.SingleThreadEventExecutor.4
                @Override // java.lang.Runnable
                public void run() {
                    SingleThreadEventExecutor.this.o.remove(runnable);
                }
            });
        }
    }

    private boolean f() {
        boolean z = false;
        while (!this.o.isEmpty()) {
            ArrayList<Runnable> arrayList = new ArrayList(this.o);
            this.o.clear();
            for (Runnable runnable : arrayList) {
                runnable.run();
                z = true;
            }
        }
        if (z) {
            this.q = c.b();
        }
        return z;
    }

    @Override // io.netty.util.concurrent.EventExecutorGroup
    public Future<?> shutdownGracefully(long j, long j2, TimeUnit timeUnit) {
        boolean z;
        if (j < 0) {
            throw new IllegalArgumentException("quietPeriod: " + j + " (expected >= 0)");
        } else if (j2 < j) {
            throw new IllegalArgumentException("timeout: " + j2 + " (expected >= quietPeriod (" + j + "))");
        } else if (timeUnit == null) {
            throw new NullPointerException("unit");
        } else if (isShuttingDown()) {
            return terminationFuture();
        } else {
            boolean inEventLoop = inEventLoop();
            while (!isShuttingDown()) {
                int i = g.get(this);
                int i2 = 3;
                if (!inEventLoop) {
                    switch (i) {
                        case 1:
                        case 2:
                            z = true;
                            break;
                        default:
                            z = false;
                            i2 = i;
                            break;
                    }
                } else {
                    z = true;
                }
                if (g.compareAndSet(this, i, i2)) {
                    this.s = timeUnit.toNanos(j);
                    this.t = timeUnit.toNanos(j2);
                    if (i == 1) {
                        h();
                    }
                    if (z) {
                        wakeup(inEventLoop);
                    }
                    return terminationFuture();
                }
            }
            return terminationFuture();
        }
    }

    @Override // io.netty.util.concurrent.EventExecutorGroup
    public Future<?> terminationFuture() {
        return this.v;
    }

    @Override // io.netty.util.concurrent.AbstractEventExecutor, java.util.concurrent.ExecutorService, io.netty.util.concurrent.EventExecutorGroup
    @Deprecated
    public void shutdown() {
        boolean z;
        if (!isShutdown()) {
            boolean inEventLoop = inEventLoop();
            while (!isShuttingDown()) {
                int i = g.get(this);
                int i2 = 4;
                if (!inEventLoop) {
                    switch (i) {
                        case 1:
                        case 2:
                        case 3:
                            z = true;
                            break;
                        default:
                            z = false;
                            i2 = i;
                            break;
                    }
                } else {
                    z = true;
                }
                if (g.compareAndSet(this, i, i2)) {
                    if (i == 1) {
                        h();
                    }
                    if (z) {
                        wakeup(inEventLoop);
                        return;
                    }
                    return;
                }
            }
        }
    }

    @Override // io.netty.util.concurrent.EventExecutorGroup
    public boolean isShuttingDown() {
        return g.get(this) >= 3;
    }

    @Override // java.util.concurrent.ExecutorService
    public boolean isShutdown() {
        return g.get(this) >= 4;
    }

    @Override // java.util.concurrent.ExecutorService
    public boolean isTerminated() {
        return g.get(this) == 5;
    }

    protected boolean confirmShutdown() {
        if (!isShuttingDown()) {
            return false;
        }
        if (inEventLoop()) {
            cancelScheduledTasks();
            if (this.u == 0) {
                this.u = c.b();
            }
            if (!runAllTasks() && !f()) {
                long b = c.b();
                if (isShutdown() || b - this.u > this.t || b - this.q > this.s) {
                    return true;
                }
                wakeup(true);
                try {
                    Thread.sleep(100L);
                } catch (InterruptedException unused) {
                }
                return false;
            } else if (isShutdown()) {
                return true;
            } else {
                wakeup(true);
                return false;
            }
        } else {
            throw new IllegalStateException("must be invoked from an event loop");
        }
    }

    @Override // java.util.concurrent.ExecutorService
    public boolean awaitTermination(long j, TimeUnit timeUnit) throws InterruptedException {
        if (timeUnit == null) {
            throw new NullPointerException("unit");
        } else if (!inEventLoop()) {
            if (this.n.tryAcquire(j, timeUnit)) {
                this.n.release();
            }
            return isTerminated();
        } else {
            throw new IllegalStateException("cannot await termination of the current thread");
        }
    }

    @Override // java.util.concurrent.Executor
    public void execute(Runnable runnable) {
        if (runnable != null) {
            boolean inEventLoop = inEventLoop();
            if (inEventLoop) {
                addTask(runnable);
            } else {
                g();
                addTask(runnable);
                if (isShutdown() && removeTask(runnable)) {
                    reject();
                }
            }
            if (!this.p && wakesUpForTask(runnable)) {
                wakeup(inEventLoop);
                return;
            }
            return;
        }
        throw new NullPointerException("task");
    }

    public final ThreadProperties threadProperties() {
        ThreadProperties threadProperties = this.k;
        if (threadProperties != null) {
            return threadProperties;
        }
        Thread thread = this.j;
        if (thread == null) {
            if (d || !inEventLoop()) {
                submit(f).syncUninterruptibly();
                thread = this.j;
                if (!d && thread == null) {
                    throw new AssertionError();
                }
            } else {
                throw new AssertionError();
            }
        }
        a aVar = new a(thread);
        return !h.compareAndSet(this, null, aVar) ? this.k : aVar;
    }

    protected static void reject() {
        throw new RejectedExecutionException("event executor terminated");
    }

    private void g() {
        if (g.get(this) == 1 && g.compareAndSet(this, 1, 2)) {
            h();
        }
    }

    private void h() {
        if (d || this.j == null) {
            this.l.execute(new Runnable() { // from class: io.netty.util.concurrent.SingleThreadEventExecutor.5
                /*  JADX ERROR: JadxRuntimeException in pass: BlockProcessor
                    jadx.core.utils.exceptions.JadxRuntimeException: CFG modification limit reached, blocks count: 191
                    	at jadx.core.dex.visitors.blocks.BlockProcessor.processBlocksTree(BlockProcessor.java:66)
                    	at jadx.core.dex.visitors.blocks.BlockProcessor.visit(BlockProcessor.java:44)
                    */
                @Override // java.lang.Runnable
                public void run() {
                    /*
                        Method dump skipped, instructions count: 1145
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: io.netty.util.concurrent.SingleThreadEventExecutor.AnonymousClass5.run():void");
                }
            });
            return;
        }
        throw new AssertionError();
    }

    /* loaded from: classes4.dex */
    private static final class a implements ThreadProperties {
        private final Thread a;

        a(Thread thread) {
            this.a = thread;
        }

        @Override // io.netty.util.concurrent.ThreadProperties
        public Thread.State state() {
            return this.a.getState();
        }

        @Override // io.netty.util.concurrent.ThreadProperties
        public int priority() {
            return this.a.getPriority();
        }

        @Override // io.netty.util.concurrent.ThreadProperties
        public boolean isInterrupted() {
            return this.a.isInterrupted();
        }

        @Override // io.netty.util.concurrent.ThreadProperties
        public boolean isDaemon() {
            return this.a.isDaemon();
        }

        @Override // io.netty.util.concurrent.ThreadProperties
        public String name() {
            return this.a.getName();
        }

        @Override // io.netty.util.concurrent.ThreadProperties
        public long id() {
            return this.a.getId();
        }

        @Override // io.netty.util.concurrent.ThreadProperties
        public StackTraceElement[] stackTrace() {
            return this.a.getStackTrace();
        }

        @Override // io.netty.util.concurrent.ThreadProperties
        public boolean isAlive() {
            return this.a.isAlive();
        }
    }
}
