package java8.util.concurrent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.List;
import java.util.RandomAccess;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReentrantLock;
import java8.util.Objects;
import java8.util.concurrent.ForkJoinPool;
import sun.misc.Unsafe;

/* loaded from: classes5.dex */
public abstract class ForkJoinTask<V> implements Serializable, Future<V> {
    private static final d[] a = new d[32];
    private static final ReentrantLock b = new ReentrantLock();
    private static final ReferenceQueue<ForkJoinTask<?>> c = new ReferenceQueue<>();
    private static final Unsafe d = d.a;
    private static final long e;
    private static final long serialVersionUID = -7721805057305804111L;
    volatile int status;

    void a(Throwable th) {
    }

    protected abstract boolean exec();

    public abstract V getRawResult();

    protected abstract void setRawResult(V v);

    private int a(int i) {
        int i2;
        do {
            i2 = this.status;
            if (i2 < 0) {
                return i2;
            }
        } while (!d.compareAndSwapInt(this, e, i2, i2 | i));
        if ((i2 >>> 16) != 0) {
            synchronized (this) {
                notifyAll();
            }
        }
        return i;
    }

    public final int d() {
        int i = this.status;
        if (i < 0) {
            return i;
        }
        try {
            return exec() ? a(-268435456) : i;
        } catch (Throwable th) {
            return e(th);
        }
    }

    public final void a(long j) {
        int i = this.status;
        if (i >= 0 && d.compareAndSwapInt(this, e, i, i | 65536)) {
            synchronized (this) {
                if (this.status >= 0) {
                    try {
                        wait(j);
                    } catch (InterruptedException unused) {
                    }
                } else {
                    notifyAll();
                }
            }
        }
    }

    private int a() {
        int i;
        boolean z = false;
        if (this instanceof CountedCompleter) {
            i = ForkJoinPool.b.a((CountedCompleter) this, 0);
        } else {
            i = ForkJoinPool.b.b((ForkJoinTask<?>) this) ? d() : 0;
        }
        if (i < 0) {
            return i;
        }
        int i2 = this.status;
        if (i2 < 0) {
            return i2;
        }
        int i3 = i2;
        do {
            if (d.compareAndSwapInt(this, e, i3, i3 | 65536)) {
                synchronized (this) {
                    if (this.status >= 0) {
                        try {
                            wait(0L);
                        } catch (InterruptedException unused) {
                            z = true;
                        }
                    } else {
                        notifyAll();
                    }
                }
            }
            i3 = this.status;
        } while (i3 >= 0);
        if (z) {
            Thread.currentThread().interrupt();
        }
        return i3;
    }

    private int b() throws InterruptedException {
        int i;
        if (!Thread.interrupted()) {
            int i2 = this.status;
            if (i2 < 0) {
                return i2;
            }
            if (this instanceof CountedCompleter) {
                i = ForkJoinPool.b.a((CountedCompleter) this, 0);
            } else {
                i = ForkJoinPool.b.b((ForkJoinTask<?>) this) ? d() : 0;
            }
            if (i < 0) {
                return i;
            }
            while (true) {
                int i3 = this.status;
                if (i3 < 0) {
                    return i3;
                }
                if (d.compareAndSwapInt(this, e, i3, i3 | 65536)) {
                    synchronized (this) {
                        if (this.status >= 0) {
                            wait(0L);
                        } else {
                            notifyAll();
                        }
                    }
                }
            }
        } else {
            throw new InterruptedException();
        }
    }

    private int c() {
        int d2;
        int i = this.status;
        if (i < 0) {
            return i;
        }
        Thread currentThread = Thread.currentThread();
        if (!(currentThread instanceof ForkJoinWorkerThread)) {
            return a();
        }
        ForkJoinWorkerThread forkJoinWorkerThread = (ForkJoinWorkerThread) currentThread;
        ForkJoinPool.d dVar = forkJoinWorkerThread.b;
        return (!dVar.b((ForkJoinTask<?>) this) || (d2 = d()) >= 0) ? forkJoinWorkerThread.a.a(dVar, (ForkJoinTask<?>) this, 0L) : d2;
    }

    private int f() {
        int d2 = d();
        if (d2 < 0) {
            return d2;
        }
        Thread currentThread = Thread.currentThread();
        if (!(currentThread instanceof ForkJoinWorkerThread)) {
            return a();
        }
        ForkJoinWorkerThread forkJoinWorkerThread = (ForkJoinWorkerThread) currentThread;
        return forkJoinWorkerThread.a.a(forkJoinWorkerThread.b, (ForkJoinTask<?>) this, 0L);
    }

    static {
        try {
            e = d.objectFieldOffset(ForkJoinTask.class.getDeclaredField("status"));
        } catch (Exception e2) {
            throw new ExceptionInInitializerError(e2);
        }
    }

    /* loaded from: classes5.dex */
    public static final class d extends WeakReference<ForkJoinTask<?>> {
        final Throwable a;
        d b;
        final long c = Thread.currentThread().getId();
        final int d;

        d(ForkJoinTask<?> forkJoinTask, Throwable th, d dVar, ReferenceQueue<ForkJoinTask<?>> referenceQueue) {
            super(forkJoinTask, referenceQueue);
            this.a = th;
            this.b = dVar;
            this.d = System.identityHashCode(forkJoinTask);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x001a, code lost:
        r2[r0] = new java8.util.concurrent.ForkJoinTask.d(r6, r7, r2[r0], java8.util.concurrent.ForkJoinTask.c);
     */
    /* JADX WARN: Finally extract failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    final int b(java.lang.Throwable r7) {
        /*
            r6 = this;
            int r0 = r6.status
            if (r0 < 0) goto L_0x003e
            int r0 = java.lang.System.identityHashCode(r6)
            java.util.concurrent.locks.ReentrantLock r1 = java8.util.concurrent.ForkJoinTask.b
            r1.lock()
            i()     // Catch: all -> 0x0039
            java8.util.concurrent.ForkJoinTask$d[] r2 = java8.util.concurrent.ForkJoinTask.a     // Catch: all -> 0x0039
            int r3 = r2.length     // Catch: all -> 0x0039
            int r3 = r3 + (-1)
            r0 = r0 & r3
            r3 = r2[r0]     // Catch: all -> 0x0039
        L_0x0018:
            if (r3 != 0) goto L_0x0026
            java8.util.concurrent.ForkJoinTask$d r3 = new java8.util.concurrent.ForkJoinTask$d     // Catch: all -> 0x0039
            r4 = r2[r0]     // Catch: all -> 0x0039
            java.lang.ref.ReferenceQueue<java8.util.concurrent.ForkJoinTask<?>> r5 = java8.util.concurrent.ForkJoinTask.c     // Catch: all -> 0x0039
            r3.<init>(r6, r7, r4, r5)     // Catch: all -> 0x0039
            r2[r0] = r3     // Catch: all -> 0x0039
            goto L_0x002c
        L_0x0026:
            java.lang.Object r4 = r3.get()     // Catch: all -> 0x0039
            if (r4 != r6) goto L_0x0036
        L_0x002c:
            r1.unlock()
            r7 = -2147483648(0xffffffff80000000, float:-0.0)
            int r0 = r6.a(r7)
            goto L_0x003e
        L_0x0036:
            java8.util.concurrent.ForkJoinTask$d r3 = r3.b     // Catch: all -> 0x0039
            goto L_0x0018
        L_0x0039:
            r7 = move-exception
            r1.unlock()
            throw r7
        L_0x003e:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: java8.util.concurrent.ForkJoinTask.b(java.lang.Throwable):int");
    }

    private int e(Throwable th) {
        int b2 = b(th);
        if (((-268435456) & b2) == Integer.MIN_VALUE) {
            a(th);
        }
        return b2;
    }

    public static final void a(ForkJoinTask<?> forkJoinTask) {
        if (forkJoinTask != null && forkJoinTask.status >= 0) {
            try {
                forkJoinTask.cancel(false);
            } catch (Throwable unused) {
            }
        }
    }

    private void g() {
        int identityHashCode = System.identityHashCode(this);
        ReentrantLock reentrantLock = b;
        reentrantLock.lock();
        try {
            d[] dVarArr = a;
            int length = identityHashCode & (dVarArr.length - 1);
            d dVar = null;
            d dVar2 = dVarArr[length];
            while (true) {
                if (dVar2 == null) {
                    break;
                }
                d dVar3 = dVar2.b;
                if (dVar2.get() != this) {
                    dVar = dVar2;
                    dVar2 = dVar3;
                } else if (dVar == null) {
                    dVarArr[length] = dVar3;
                } else {
                    dVar.b = dVar3;
                }
            }
            i();
            this.status = 0;
        } finally {
            reentrantLock.unlock();
        }
    }

    /* JADX WARN: Finally extract failed */
    private Throwable h() {
        Throwable th;
        int identityHashCode = System.identityHashCode(this);
        ReentrantLock reentrantLock = b;
        reentrantLock.lock();
        try {
            i();
            d[] dVarArr = a;
            d dVar = dVarArr[identityHashCode & (dVarArr.length - 1)];
            while (dVar != null) {
                if (dVar.get() == this) {
                    break;
                }
                dVar = dVar.b;
            }
            reentrantLock.unlock();
            if (dVar == null || (th = dVar.a) == null) {
                return null;
            }
            if (dVar.c != Thread.currentThread().getId()) {
                try {
                    Constructor<?>[] constructors = th.getClass().getConstructors();
                    Constructor<?> constructor = null;
                    for (Constructor<?> constructor2 : constructors) {
                        Class<?>[] parameterTypes = constructor2.getParameterTypes();
                        if (parameterTypes.length == 0) {
                            constructor = constructor2;
                        } else if (parameterTypes.length == 1 && parameterTypes[0] == Throwable.class) {
                            return (Throwable) constructor2.newInstance(th);
                        }
                    }
                    if (constructor != null) {
                        Throwable th2 = (Throwable) constructor.newInstance(new Object[0]);
                        th2.initCause(th);
                        return th2;
                    }
                } catch (Exception unused) {
                }
            }
            return th;
        } catch (Throwable th3) {
            reentrantLock.unlock();
            throw th3;
        }
    }

    private static void i() {
        while (true) {
            Reference<? extends ForkJoinTask<?>> poll = c.poll();
            if (poll == null) {
                return;
            }
            if (poll instanceof d) {
                d[] dVarArr = a;
                int length = ((d) poll).d & (dVarArr.length - 1);
                d dVar = null;
                d dVar2 = dVarArr[length];
                while (true) {
                    if (dVar2 != null) {
                        d dVar3 = dVar2.b;
                        if (dVar2 != poll) {
                            dVar = dVar2;
                            dVar2 = dVar3;
                        } else if (dVar == null) {
                            dVarArr[length] = dVar3;
                        } else {
                            dVar.b = dVar3;
                        }
                    }
                }
            }
        }
    }

    public static final void e() {
        ReentrantLock reentrantLock = b;
        if (reentrantLock.tryLock()) {
            try {
                i();
            } finally {
                reentrantLock.unlock();
            }
        }
    }

    public static void c(Throwable th) {
        d(th);
    }

    static <T extends Throwable> void d(Throwable th) throws Throwable {
        if (th != null) {
            throw th;
        }
        throw new Error("Unknown Exception");
    }

    private void b(int i) {
        if (i == -1073741824) {
            throw new CancellationException();
        } else if (i == Integer.MIN_VALUE) {
            c(h());
        }
    }

    public final ForkJoinTask<V> fork() {
        Thread currentThread = Thread.currentThread();
        if (currentThread instanceof ForkJoinWorkerThread) {
            ((ForkJoinWorkerThread) currentThread).b.a((ForkJoinTask<?>) this);
        } else {
            ForkJoinPool.b.a((ForkJoinTask<?>) this);
        }
        return this;
    }

    public final V join() {
        int c2 = c() & (-268435456);
        if (c2 != -268435456) {
            b(c2);
        }
        return getRawResult();
    }

    public final V invoke() {
        int f = f() & (-268435456);
        if (f != -268435456) {
            b(f);
        }
        return getRawResult();
    }

    public static void invokeAll(ForkJoinTask<?> forkJoinTask, ForkJoinTask<?> forkJoinTask2) {
        forkJoinTask2.fork();
        int f = forkJoinTask.f() & (-268435456);
        if (f != -268435456) {
            forkJoinTask.b(f);
        }
        int c2 = forkJoinTask2.c() & (-268435456);
        if (c2 != -268435456) {
            forkJoinTask2.b(c2);
        }
    }

    public static void invokeAll(ForkJoinTask<?>... forkJoinTaskArr) {
        int length = forkJoinTaskArr.length - 1;
        Throwable th = null;
        for (int i = length; i >= 0; i--) {
            ForkJoinTask<?> forkJoinTask = forkJoinTaskArr[i];
            if (forkJoinTask == null) {
                if (th == null) {
                    th = new NullPointerException();
                }
            } else if (i != 0) {
                forkJoinTask.fork();
            } else if (forkJoinTask.f() < -268435456 && th == null) {
                th = forkJoinTask.getException();
            }
        }
        for (int i2 = 1; i2 <= length; i2++) {
            ForkJoinTask<?> forkJoinTask2 = forkJoinTaskArr[i2];
            if (forkJoinTask2 != null) {
                if (th != null) {
                    forkJoinTask2.cancel(false);
                } else if (forkJoinTask2.c() < -268435456) {
                    th = forkJoinTask2.getException();
                }
            }
        }
        if (th != null) {
            c(th);
        }
    }

    public static <T extends ForkJoinTask<?>> Collection<T> invokeAll(Collection<T> collection) {
        if (!(collection instanceof RandomAccess) || !(collection instanceof List)) {
            invokeAll((ForkJoinTask[]) collection.toArray(new ForkJoinTask[0]));
            return collection;
        }
        List list = (List) collection;
        Throwable th = null;
        int size = list.size() - 1;
        for (int i = size; i >= 0; i--) {
            ForkJoinTask forkJoinTask = (ForkJoinTask) list.get(i);
            if (forkJoinTask == null) {
                if (th == null) {
                    th = new NullPointerException();
                }
            } else if (i != 0) {
                forkJoinTask.fork();
            } else if (forkJoinTask.f() < -268435456 && th == null) {
                th = forkJoinTask.getException();
            }
        }
        for (int i2 = 1; i2 <= size; i2++) {
            ForkJoinTask forkJoinTask2 = (ForkJoinTask) list.get(i2);
            if (forkJoinTask2 != null) {
                if (th != null) {
                    forkJoinTask2.cancel(false);
                } else if (forkJoinTask2.c() < -268435456) {
                    th = forkJoinTask2.getException();
                }
            }
        }
        if (th != null) {
            c(th);
        }
        return collection;
    }

    @Override // java.util.concurrent.Future
    public boolean cancel(boolean z) {
        return (a(-1073741824) & (-268435456)) == -1073741824;
    }

    @Override // java.util.concurrent.Future
    public final boolean isDone() {
        return this.status < 0;
    }

    @Override // java.util.concurrent.Future
    public final boolean isCancelled() {
        return (this.status & (-268435456)) == -1073741824;
    }

    public final boolean isCompletedAbnormally() {
        return this.status < -268435456;
    }

    public final boolean isCompletedNormally() {
        return (this.status & (-268435456)) == -268435456;
    }

    public final Throwable getException() {
        int i = this.status & (-268435456);
        if (i >= -268435456) {
            return null;
        }
        if (i == -1073741824) {
            return new CancellationException();
        }
        return h();
    }

    public void completeExceptionally(Throwable th) {
        if (!(th instanceof RuntimeException) && !(th instanceof Error)) {
            th = new RuntimeException(th);
        }
        e(th);
    }

    public void complete(V v) {
        try {
            setRawResult(v);
            a(-268435456);
        } catch (Throwable th) {
            e(th);
        }
    }

    public final void quietlyComplete() {
        a(-268435456);
    }

    @Override // java.util.concurrent.Future
    public final V get() throws InterruptedException, ExecutionException {
        int c2 = (Thread.currentThread() instanceof ForkJoinWorkerThread ? c() : b()) & (-268435456);
        if (c2 == -1073741824) {
            throw new CancellationException();
        } else if (c2 != Integer.MIN_VALUE) {
            return getRawResult();
        } else {
            throw new ExecutionException(h());
        }
    }

    @Override // java.util.concurrent.Future
    public final V get(long j, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        int i;
        long nanos = timeUnit.toNanos(j);
        if (!Thread.interrupted()) {
            int i2 = this.status;
            if (i2 >= 0 && nanos > 0) {
                long nanoTime = System.nanoTime() + nanos;
                if (nanoTime == 0) {
                    nanoTime = 1;
                }
                Thread currentThread = Thread.currentThread();
                if (currentThread instanceof ForkJoinWorkerThread) {
                    ForkJoinWorkerThread forkJoinWorkerThread = (ForkJoinWorkerThread) currentThread;
                    i2 = forkJoinWorkerThread.a.a(forkJoinWorkerThread.b, (ForkJoinTask<?>) this, nanoTime);
                } else {
                    if (this instanceof CountedCompleter) {
                        i2 = ForkJoinPool.b.a((CountedCompleter) this, 0);
                    } else {
                        i2 = ForkJoinPool.b.b((ForkJoinTask<?>) this) ? d() : 0;
                    }
                    if (i2 >= 0) {
                        while (true) {
                            i = this.status;
                            if (i < 0) {
                                break;
                            }
                            long nanoTime2 = nanoTime - System.nanoTime();
                            if (nanoTime2 <= 0) {
                                break;
                            }
                            long millis = TimeUnit.NANOSECONDS.toMillis(nanoTime2);
                            if (millis > 0 && d.compareAndSwapInt(this, e, i, i | 65536)) {
                                synchronized (this) {
                                    if (this.status >= 0) {
                                        wait(millis);
                                    } else {
                                        notifyAll();
                                    }
                                }
                            }
                        }
                        i2 = i;
                    }
                }
            }
            if (i2 >= 0) {
                i2 = this.status;
            }
            int i3 = i2 & (-268435456);
            if (i3 == -268435456) {
                return getRawResult();
            }
            if (i3 == -1073741824) {
                throw new CancellationException();
            } else if (i3 != Integer.MIN_VALUE) {
                throw new TimeoutException();
            } else {
                throw new ExecutionException(h());
            }
        } else {
            throw new InterruptedException();
        }
    }

    public final void quietlyJoin() {
        c();
    }

    public final void quietlyInvoke() {
        f();
    }

    public static void helpQuiesce() {
        Thread currentThread = Thread.currentThread();
        if (currentThread instanceof ForkJoinWorkerThread) {
            ForkJoinWorkerThread forkJoinWorkerThread = (ForkJoinWorkerThread) currentThread;
            forkJoinWorkerThread.a.b(forkJoinWorkerThread.b);
            return;
        }
        ForkJoinPool.d();
    }

    public void reinitialize() {
        if ((this.status & (-268435456)) == Integer.MIN_VALUE) {
            g();
        } else {
            this.status = 0;
        }
    }

    public static ForkJoinPool getPool() {
        Thread currentThread = Thread.currentThread();
        if (currentThread instanceof ForkJoinWorkerThread) {
            return ((ForkJoinWorkerThread) currentThread).a;
        }
        return null;
    }

    public static boolean inForkJoinPool() {
        return Thread.currentThread() instanceof ForkJoinWorkerThread;
    }

    public boolean tryUnfork() {
        Thread currentThread = Thread.currentThread();
        if (currentThread instanceof ForkJoinWorkerThread) {
            return ((ForkJoinWorkerThread) currentThread).b.b((ForkJoinTask<?>) this);
        }
        return ForkJoinPool.b.b((ForkJoinTask<?>) this);
    }

    public static int getQueuedTaskCount() {
        ForkJoinPool.d dVar;
        Thread currentThread = Thread.currentThread();
        if (currentThread instanceof ForkJoinWorkerThread) {
            dVar = ((ForkJoinWorkerThread) currentThread).b;
        } else {
            dVar = ForkJoinPool.b();
        }
        if (dVar == null) {
            return 0;
        }
        return dVar.b();
    }

    public static int getSurplusQueuedTaskCount() {
        return ForkJoinPool.c();
    }

    protected static ForkJoinTask<?> peekNextLocalTask() {
        ForkJoinPool.d dVar;
        Thread currentThread = Thread.currentThread();
        if (currentThread instanceof ForkJoinWorkerThread) {
            dVar = ((ForkJoinWorkerThread) currentThread).b;
        } else {
            dVar = ForkJoinPool.b();
        }
        if (dVar == null) {
            return null;
        }
        return dVar.h();
    }

    protected static ForkJoinTask<?> pollNextLocalTask() {
        Thread currentThread = Thread.currentThread();
        if (currentThread instanceof ForkJoinWorkerThread) {
            return ((ForkJoinWorkerThread) currentThread).b.g();
        }
        return null;
    }

    protected static ForkJoinTask<?> pollTask() {
        Thread currentThread = Thread.currentThread();
        if (!(currentThread instanceof ForkJoinWorkerThread)) {
            return null;
        }
        ForkJoinWorkerThread forkJoinWorkerThread = (ForkJoinWorkerThread) currentThread;
        return forkJoinWorkerThread.a.c(forkJoinWorkerThread.b);
    }

    protected static ForkJoinTask<?> pollSubmission() {
        Thread currentThread = Thread.currentThread();
        if (currentThread instanceof ForkJoinWorkerThread) {
            return ((ForkJoinWorkerThread) currentThread).a.pollSubmission();
        }
        return null;
    }

    public final short getForkJoinTaskTag() {
        return (short) this.status;
    }

    public final short setForkJoinTaskTag(short s) {
        Unsafe unsafe;
        long j;
        int i;
        do {
            unsafe = d;
            j = e;
            i = this.status;
        } while (!unsafe.compareAndSwapInt(this, j, i, ((-65536) & i) | (65535 & s)));
        return (short) i;
    }

    public final boolean compareAndSetForkJoinTaskTag(short s, short s2) {
        int i;
        do {
            i = this.status;
            if (((short) i) != s) {
                return false;
            }
        } while (!d.compareAndSwapInt(this, e, i, (65535 & s2) | ((-65536) & i)));
        return true;
    }

    /* loaded from: classes5.dex */
    public static final class b<T> extends ForkJoinTask<T> implements RunnableFuture<T> {
        private static final long serialVersionUID = 5232453952276885070L;
        T result;
        final Runnable runnable;

        public b(Runnable runnable, T t) {
            this.runnable = (Runnable) Objects.requireNonNull(runnable);
            this.result = t;
        }

        @Override // java8.util.concurrent.ForkJoinTask
        public final T getRawResult() {
            return this.result;
        }

        @Override // java8.util.concurrent.ForkJoinTask
        public final void setRawResult(T t) {
            this.result = t;
        }

        @Override // java8.util.concurrent.ForkJoinTask
        public final boolean exec() {
            this.runnable.run();
            return true;
        }

        @Override // java.util.concurrent.RunnableFuture, java.lang.Runnable
        public final void run() {
            invoke();
        }

        public String toString() {
            return super.toString() + "[Wrapped task = " + this.runnable + "]";
        }
    }

    /* loaded from: classes5.dex */
    public static final class c extends ForkJoinTask<Void> implements RunnableFuture<Void> {
        private static final long serialVersionUID = 5232453952276885070L;
        final Runnable runnable;

        /* renamed from: a */
        public final Void getRawResult() {
            return null;
        }

        /* renamed from: a */
        public final void setRawResult(Void r1) {
        }

        public c(Runnable runnable) {
            this.runnable = (Runnable) Objects.requireNonNull(runnable);
        }

        @Override // java8.util.concurrent.ForkJoinTask
        public final boolean exec() {
            this.runnable.run();
            return true;
        }

        @Override // java.util.concurrent.RunnableFuture, java.lang.Runnable
        public final void run() {
            invoke();
        }

        public String toString() {
            return super.toString() + "[Wrapped task = " + this.runnable + "]";
        }
    }

    /* loaded from: classes5.dex */
    static final class e extends ForkJoinTask<Void> {
        private static final long serialVersionUID = 5232453952276885070L;
        final Runnable runnable;

        /* renamed from: a */
        public final Void getRawResult() {
            return null;
        }

        /* renamed from: a */
        public final void setRawResult(Void r1) {
        }

        public e(Runnable runnable) {
            this.runnable = (Runnable) Objects.requireNonNull(runnable);
        }

        @Override // java8.util.concurrent.ForkJoinTask
        public final boolean exec() {
            this.runnable.run();
            return true;
        }

        @Override // java8.util.concurrent.ForkJoinTask
        void a(Throwable th) {
            c(th);
        }
    }

    /* loaded from: classes5.dex */
    public static final class a<T> extends ForkJoinTask<T> implements RunnableFuture<T> {
        private static final long serialVersionUID = 2838392045355241008L;
        final Callable<? extends T> callable;
        T result;

        public a(Callable<? extends T> callable) {
            this.callable = (Callable) Objects.requireNonNull(callable);
        }

        @Override // java8.util.concurrent.ForkJoinTask
        public final T getRawResult() {
            return this.result;
        }

        @Override // java8.util.concurrent.ForkJoinTask
        public final void setRawResult(T t) {
            this.result = t;
        }

        @Override // java8.util.concurrent.ForkJoinTask
        public final boolean exec() {
            try {
                this.result = (T) this.callable.call();
                return true;
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e2) {
                throw new RuntimeException(e2);
            }
        }

        @Override // java.util.concurrent.RunnableFuture, java.lang.Runnable
        public final void run() {
            invoke();
        }

        public String toString() {
            return super.toString() + "[Wrapped task = " + this.callable + "]";
        }
    }

    public static ForkJoinTask<?> adapt(Runnable runnable) {
        return new c(runnable);
    }

    public static <T> ForkJoinTask<T> adapt(Runnable runnable, T t) {
        return new b(runnable, t);
    }

    public static <T> ForkJoinTask<T> adapt(Callable<? extends T> callable) {
        return new a(callable);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(getException());
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        Object readObject = objectInputStream.readObject();
        if (readObject != null) {
            e((Throwable) readObject);
        }
    }
}
