package io.netty.util.internal.chmv8;

import io.netty.util.internal.chmv8.ForkJoinPool;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Collection;
import java.util.List;
import java.util.RandomAccess;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.locks.ReentrantLock;
import sun.misc.Unsafe;

/* loaded from: classes4.dex */
public abstract class ForkJoinTask<V> implements Serializable, Future<V> {
    private static final Unsafe d;
    private static final long e;
    private static final long serialVersionUID = -7721805057305804111L;
    volatile int status;
    private static final ReentrantLock b = new ReentrantLock();
    private static final ReferenceQueue<Object> c = new ReferenceQueue<>();
    private static final d[] a = new d[32];

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

    public final int c() {
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

    public final boolean d() {
        int i = this.status;
        return i >= 0 && d.compareAndSwapInt(this, e, i, i | 65536);
    }

    private int a() {
        ForkJoinPool forkJoinPool = ForkJoinPool.b;
        int i = this.status;
        if (i < 0) {
            return i;
        }
        if (forkJoinPool != null) {
            if (this instanceof CountedCompleter) {
                i = forkJoinPool.a((CountedCompleter) this);
            } else if (forkJoinPool.b((ForkJoinTask<?>) this)) {
                i = c();
            }
        }
        if (i < 0) {
            return i;
        }
        int i2 = this.status;
        if (i2 < 0) {
            return i2;
        }
        boolean z = false;
        int i3 = i2;
        do {
            if (d.compareAndSwapInt(this, e, i3, i3 | 65536)) {
                synchronized (this) {
                    if (this.status >= 0) {
                        try {
                            wait();
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
        ForkJoinPool forkJoinPool = ForkJoinPool.b;
        if (!Thread.interrupted()) {
            if (this.status >= 0 && forkJoinPool != null) {
                if (this instanceof CountedCompleter) {
                    forkJoinPool.a((CountedCompleter) this);
                } else if (forkJoinPool.b((ForkJoinTask<?>) this)) {
                    c();
                }
            }
            while (true) {
                int i = this.status;
                if (i < 0) {
                    return i;
                }
                if (d.compareAndSwapInt(this, e, i, i | 65536)) {
                    synchronized (this) {
                        if (this.status >= 0) {
                            wait();
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

    private int g() {
        int c2;
        int i = this.status;
        if (i < 0) {
            return i;
        }
        Thread currentThread = Thread.currentThread();
        if (!(currentThread instanceof ForkJoinWorkerThread)) {
            return a();
        }
        ForkJoinWorkerThread forkJoinWorkerThread = (ForkJoinWorkerThread) currentThread;
        ForkJoinPool.c cVar = forkJoinWorkerThread.b;
        return (!cVar.b((ForkJoinTask<?>) this) || (c2 = c()) >= 0) ? forkJoinWorkerThread.a.a(cVar, (ForkJoinTask<?>) this) : c2;
    }

    private int h() {
        int c2 = c();
        if (c2 < 0) {
            return c2;
        }
        Thread currentThread = Thread.currentThread();
        if (!(currentThread instanceof ForkJoinWorkerThread)) {
            return a();
        }
        ForkJoinWorkerThread forkJoinWorkerThread = (ForkJoinWorkerThread) currentThread;
        return forkJoinWorkerThread.a.a(forkJoinWorkerThread.b, (ForkJoinTask<?>) this);
    }

    /* loaded from: classes4.dex */
    public static final class d extends WeakReference<ForkJoinTask<?>> {
        final Throwable a;
        d b;
        final long c = Thread.currentThread().getId();

        d(ForkJoinTask<?> forkJoinTask, Throwable th, d dVar) {
            super(forkJoinTask, ForkJoinTask.c);
            this.a = th;
            this.b = dVar;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x001a, code lost:
        r2[r0] = new io.netty.util.internal.chmv8.ForkJoinTask.d(r5, r6, r2[r0]);
     */
    /* JADX WARN: Finally extract failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    final int b(java.lang.Throwable r6) {
        /*
            r5 = this;
            int r0 = r5.status
            if (r0 < 0) goto L_0x003c
            int r0 = java.lang.System.identityHashCode(r5)
            java.util.concurrent.locks.ReentrantLock r1 = io.netty.util.internal.chmv8.ForkJoinTask.b
            r1.lock()
            k()     // Catch: all -> 0x0037
            io.netty.util.internal.chmv8.ForkJoinTask$d[] r2 = io.netty.util.internal.chmv8.ForkJoinTask.a     // Catch: all -> 0x0037
            int r3 = r2.length     // Catch: all -> 0x0037
            int r3 = r3 + (-1)
            r0 = r0 & r3
            r3 = r2[r0]     // Catch: all -> 0x0037
        L_0x0018:
            if (r3 != 0) goto L_0x0024
            io.netty.util.internal.chmv8.ForkJoinTask$d r3 = new io.netty.util.internal.chmv8.ForkJoinTask$d     // Catch: all -> 0x0037
            r4 = r2[r0]     // Catch: all -> 0x0037
            r3.<init>(r5, r6, r4)     // Catch: all -> 0x0037
            r2[r0] = r3     // Catch: all -> 0x0037
            goto L_0x002a
        L_0x0024:
            java.lang.Object r4 = r3.get()     // Catch: all -> 0x0037
            if (r4 != r5) goto L_0x0034
        L_0x002a:
            r1.unlock()
            r6 = -2147483648(0xffffffff80000000, float:-0.0)
            int r0 = r5.a(r6)
            goto L_0x003c
        L_0x0034:
            io.netty.util.internal.chmv8.ForkJoinTask$d r3 = r3.b     // Catch: all -> 0x0037
            goto L_0x0018
        L_0x0037:
            r6 = move-exception
            r1.unlock()
            throw r6
        L_0x003c:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.util.internal.chmv8.ForkJoinTask.b(java.lang.Throwable):int");
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

    private void i() {
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
            k();
            this.status = 0;
        } finally {
            reentrantLock.unlock();
        }
    }

    private Throwable j() {
        Throwable th;
        if ((this.status & (-268435456)) != Integer.MIN_VALUE) {
            return null;
        }
        int identityHashCode = System.identityHashCode(this);
        ReentrantLock reentrantLock = b;
        reentrantLock.lock();
        try {
            k();
            d[] dVarArr = a;
            d dVar = dVarArr[identityHashCode & (dVarArr.length - 1)];
            while (dVar != null) {
                if (dVar.get() == this) {
                    break;
                }
                dVar = dVar.b;
            }
            if (dVar == null || (th = dVar.a) == null) {
                return null;
            }
            return th;
        } finally {
            reentrantLock.unlock();
        }
    }

    private static void k() {
        while (true) {
            Reference<? extends Object> poll = c.poll();
            if (poll == null) {
                return;
            }
            if (poll instanceof d) {
                d[] dVarArr = a;
                int identityHashCode = System.identityHashCode((ForkJoinTask) ((d) poll).get()) & (dVarArr.length - 1);
                d dVar = null;
                d dVar2 = dVarArr[identityHashCode];
                while (true) {
                    if (dVar2 != null) {
                        d dVar3 = dVar2.b;
                        if (dVar2 != poll) {
                            dVar = dVar2;
                            dVar2 = dVar3;
                        } else if (dVar == null) {
                            dVarArr[identityHashCode] = dVar3;
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
                k();
            } finally {
                reentrantLock.unlock();
            }
        }
    }

    public static void c(Throwable th) {
        if (th != null) {
            d(th);
        }
    }

    static <T extends Throwable> void d(Throwable th) throws Throwable {
        throw th;
    }

    private void b(int i) {
        if (i == -1073741824) {
            throw new CancellationException();
        } else if (i == Integer.MIN_VALUE) {
            c(j());
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
        int g = g() & (-268435456);
        if (g != -268435456) {
            b(g);
        }
        return getRawResult();
    }

    public final V invoke() {
        int h = h() & (-268435456);
        if (h != -268435456) {
            b(h);
        }
        return getRawResult();
    }

    public static void invokeAll(ForkJoinTask<?> forkJoinTask, ForkJoinTask<?> forkJoinTask2) {
        forkJoinTask2.fork();
        int h = forkJoinTask.h() & (-268435456);
        if (h != -268435456) {
            forkJoinTask.b(h);
        }
        int g = forkJoinTask2.g() & (-268435456);
        if (g != -268435456) {
            forkJoinTask2.b(g);
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
            } else if (forkJoinTask.h() < -268435456 && th == null) {
                th = forkJoinTask.getException();
            }
        }
        for (int i2 = 1; i2 <= length; i2++) {
            ForkJoinTask<?> forkJoinTask2 = forkJoinTaskArr[i2];
            if (forkJoinTask2 != null) {
                if (th != null) {
                    forkJoinTask2.cancel(false);
                } else if (forkJoinTask2.g() < -268435456) {
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
            invokeAll((ForkJoinTask[]) collection.toArray(new ForkJoinTask[collection.size()]));
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
            } else if (forkJoinTask.h() < -268435456 && th == null) {
                th = forkJoinTask.getException();
            }
        }
        for (int i2 = 1; i2 <= size; i2++) {
            ForkJoinTask forkJoinTask2 = (ForkJoinTask) list.get(i2);
            if (forkJoinTask2 != null) {
                if (th != null) {
                    forkJoinTask2.cancel(false);
                } else if (forkJoinTask2.g() < -268435456) {
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
        return i == -1073741824 ? new CancellationException() : j();
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
        Throwable j;
        int g = (Thread.currentThread() instanceof ForkJoinWorkerThread ? g() : b()) & (-268435456);
        if (g == -1073741824) {
            throw new CancellationException();
        } else if (g != Integer.MIN_VALUE || (j = j()) == null) {
            return getRawResult();
        } else {
            throw new ExecutionException(j);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:55:0x00bb, code lost:
        r2 = r0;
     */
    @Override // java.util.concurrent.Future
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final V get(long r20, java.util.concurrent.TimeUnit r22) throws java.lang.InterruptedException, java.util.concurrent.ExecutionException, java.util.concurrent.TimeoutException {
        /*
            Method dump skipped, instructions count: 271
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.util.internal.chmv8.ForkJoinTask.get(long, java.util.concurrent.TimeUnit):java.lang.Object");
    }

    public final void quietlyJoin() {
        g();
    }

    public final void quietlyInvoke() {
        h();
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
            i();
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
        return currentThread instanceof ForkJoinWorkerThread ? ((ForkJoinWorkerThread) currentThread).b.b((ForkJoinTask<?>) this) : ForkJoinPool.b.b((ForkJoinTask<?>) this);
    }

    public static int getQueuedTaskCount() {
        ForkJoinPool.c cVar;
        Thread currentThread = Thread.currentThread();
        if (currentThread instanceof ForkJoinWorkerThread) {
            cVar = ((ForkJoinWorkerThread) currentThread).b;
        } else {
            cVar = ForkJoinPool.c();
        }
        if (cVar == null) {
            return 0;
        }
        return cVar.a();
    }

    public static int getSurplusQueuedTaskCount() {
        return ForkJoinPool.b();
    }

    protected static ForkJoinTask<?> peekNextLocalTask() {
        ForkJoinPool.c cVar;
        Thread currentThread = Thread.currentThread();
        if (currentThread instanceof ForkJoinWorkerThread) {
            cVar = ((ForkJoinWorkerThread) currentThread).b;
        } else {
            cVar = ForkJoinPool.c();
        }
        if (cVar == null) {
            return null;
        }
        return cVar.g();
    }

    protected static ForkJoinTask<?> pollNextLocalTask() {
        Thread currentThread = Thread.currentThread();
        if (currentThread instanceof ForkJoinWorkerThread) {
            return ((ForkJoinWorkerThread) currentThread).b.f();
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

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class b<T> extends ForkJoinTask<T> implements RunnableFuture<T> {
        private static final long serialVersionUID = 5232453952276885070L;
        T result;
        final Runnable runnable;

        public b(Runnable runnable, T t) {
            if (runnable != null) {
                this.runnable = runnable;
                this.result = t;
                return;
            }
            throw new NullPointerException();
        }

        @Override // io.netty.util.internal.chmv8.ForkJoinTask
        public final T getRawResult() {
            return this.result;
        }

        @Override // io.netty.util.internal.chmv8.ForkJoinTask
        public final void setRawResult(T t) {
            this.result = t;
        }

        @Override // io.netty.util.internal.chmv8.ForkJoinTask
        public final boolean exec() {
            this.runnable.run();
            return true;
        }

        @Override // java.util.concurrent.RunnableFuture, java.lang.Runnable
        public final void run() {
            invoke();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
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
            if (runnable != null) {
                this.runnable = runnable;
                return;
            }
            throw new NullPointerException();
        }

        @Override // io.netty.util.internal.chmv8.ForkJoinTask
        public final boolean exec() {
            this.runnable.run();
            return true;
        }

        @Override // java.util.concurrent.RunnableFuture, java.lang.Runnable
        public final void run() {
            invoke();
        }
    }

    /* loaded from: classes4.dex */
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
            if (runnable != null) {
                this.runnable = runnable;
                return;
            }
            throw new NullPointerException();
        }

        @Override // io.netty.util.internal.chmv8.ForkJoinTask
        public final boolean exec() {
            this.runnable.run();
            return true;
        }

        @Override // io.netty.util.internal.chmv8.ForkJoinTask
        void a(Throwable th) {
            c(th);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class a<T> extends ForkJoinTask<T> implements RunnableFuture<T> {
        private static final long serialVersionUID = 2838392045355241008L;
        final Callable<? extends T> callable;
        T result;

        public a(Callable<? extends T> callable) {
            if (callable != null) {
                this.callable = callable;
                return;
            }
            throw new NullPointerException();
        }

        @Override // io.netty.util.internal.chmv8.ForkJoinTask
        public final T getRawResult() {
            return this.result;
        }

        @Override // io.netty.util.internal.chmv8.ForkJoinTask
        public final void setRawResult(T t) {
            this.result = t;
        }

        @Override // io.netty.util.internal.chmv8.ForkJoinTask
        public final boolean exec() {
            try {
                this.result = (T) this.callable.call();
                return true;
            } catch (Error e) {
                throw e;
            } catch (RuntimeException e2) {
                throw e2;
            } catch (Exception e3) {
                throw new RuntimeException(e3);
            }
        }

        @Override // java.util.concurrent.RunnableFuture, java.lang.Runnable
        public final void run() {
            invoke();
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

    static {
        try {
            d = l();
            e = d.objectFieldOffset(ForkJoinTask.class.getDeclaredField("status"));
        } catch (Exception e2) {
            throw new Error(e2);
        }
    }

    private static Unsafe l() {
        try {
            try {
                return Unsafe.getUnsafe();
            } catch (PrivilegedActionException e2) {
                throw new RuntimeException("Could not initialize intrinsics", e2.getCause());
            }
        } catch (SecurityException unused) {
            return (Unsafe) AccessController.doPrivileged(new PrivilegedExceptionAction<Unsafe>() { // from class: io.netty.util.internal.chmv8.ForkJoinTask.1
                /* renamed from: a */
                public Unsafe run() throws Exception {
                    Field[] declaredFields = Unsafe.class.getDeclaredFields();
                    for (Field field : declaredFields) {
                        field.setAccessible(true);
                        Object obj = field.get(null);
                        if (Unsafe.class.isInstance(obj)) {
                            return (Unsafe) Unsafe.class.cast(obj);
                        }
                    }
                    throw new NoSuchFieldError("the Unsafe");
                }
            });
        }
    }
}
