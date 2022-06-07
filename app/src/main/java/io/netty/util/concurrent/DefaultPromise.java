package io.netty.util.concurrent;

import io.netty.util.Signal;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.InternalThreadLocalMap;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.OneTimeTask;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.concurrent.CancellationException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/* loaded from: classes4.dex */
public class DefaultPromise<V> extends AbstractFuture<V> implements Promise<V> {
    private static final AtomicReferenceFieldUpdater<DefaultPromise, Object> c;
    private volatile Object g;
    private final EventExecutor h;
    private Object i;
    private short j;
    private boolean k;
    private static final InternalLogger a = InternalLoggerFactory.getInstance(DefaultPromise.class);
    private static final InternalLogger b = InternalLoggerFactory.getInstance(DefaultPromise.class.getName() + ".rejectedExecution");
    private static final Signal d = Signal.valueOf(DefaultPromise.class, "SUCCESS");
    private static final Signal e = Signal.valueOf(DefaultPromise.class, "UNCANCELLABLE");
    private static final a f = new a(new CancellationException());

    static {
        AtomicReferenceFieldUpdater<DefaultPromise, Object> newAtomicReferenceFieldUpdater = PlatformDependent.newAtomicReferenceFieldUpdater(DefaultPromise.class, "result");
        if (newAtomicReferenceFieldUpdater == null) {
            newAtomicReferenceFieldUpdater = AtomicReferenceFieldUpdater.newUpdater(DefaultPromise.class, Object.class, "g");
        }
        c = newAtomicReferenceFieldUpdater;
        f.a.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
    }

    public DefaultPromise(EventExecutor eventExecutor) {
        this.h = (EventExecutor) ObjectUtil.checkNotNull(eventExecutor, "executor");
    }

    public DefaultPromise() {
        this.h = null;
    }

    public Promise<V> setSuccess(V v) {
        if (a((DefaultPromise<V>) v)) {
            a();
            return this;
        }
        throw new IllegalStateException("complete already: " + this);
    }

    public boolean trySuccess(V v) {
        if (!a((DefaultPromise<V>) v)) {
            return false;
        }
        a();
        return true;
    }

    public Promise<V> setFailure(Throwable th) {
        if (a(th)) {
            a();
            return this;
        }
        throw new IllegalStateException("complete already: " + this, th);
    }

    public boolean tryFailure(Throwable th) {
        if (!a(th)) {
            return false;
        }
        a();
        return true;
    }

    @Override // io.netty.util.concurrent.Promise
    public boolean setUncancellable() {
        if (c.compareAndSet(this, null, e)) {
            return true;
        }
        Object obj = this.g;
        return !d(obj) || !c(obj);
    }

    @Override // io.netty.util.concurrent.Future
    public boolean isSuccess() {
        Object obj = this.g;
        return (obj == null || obj == e || (obj instanceof a)) ? false : true;
    }

    @Override // io.netty.util.concurrent.Future
    public boolean isCancellable() {
        return this.g == null;
    }

    @Override // io.netty.util.concurrent.Future
    public Throwable cause() {
        Object obj = this.g;
        if (obj instanceof a) {
            return ((a) obj).a;
        }
        return null;
    }

    @Override // io.netty.util.concurrent.Future
    public Promise<V> addListener(GenericFutureListener<? extends Future<? super V>> genericFutureListener) {
        ObjectUtil.checkNotNull(genericFutureListener, "listener");
        synchronized (this) {
            a((GenericFutureListener) genericFutureListener);
        }
        if (isDone()) {
            a();
        }
        return this;
    }

    @Override // io.netty.util.concurrent.Future
    public Promise<V> addListeners(GenericFutureListener<? extends Future<? super V>>... genericFutureListenerArr) {
        ObjectUtil.checkNotNull(genericFutureListenerArr, "listeners");
        synchronized (this) {
            for (GenericFutureListener<? extends Future<? super V>> genericFutureListener : genericFutureListenerArr) {
                if (genericFutureListener == null) {
                    break;
                }
                a((GenericFutureListener) genericFutureListener);
            }
        }
        if (isDone()) {
            a();
        }
        return this;
    }

    @Override // io.netty.util.concurrent.Future
    public Promise<V> removeListener(GenericFutureListener<? extends Future<? super V>> genericFutureListener) {
        ObjectUtil.checkNotNull(genericFutureListener, "listener");
        synchronized (this) {
            b((GenericFutureListener) genericFutureListener);
        }
        return this;
    }

    @Override // io.netty.util.concurrent.Future
    public Promise<V> removeListeners(GenericFutureListener<? extends Future<? super V>>... genericFutureListenerArr) {
        ObjectUtil.checkNotNull(genericFutureListenerArr, "listeners");
        synchronized (this) {
            for (GenericFutureListener<? extends Future<? super V>> genericFutureListener : genericFutureListenerArr) {
                if (genericFutureListener == null) {
                    break;
                }
                b((GenericFutureListener) genericFutureListener);
            }
        }
        return this;
    }

    @Override // io.netty.util.concurrent.Future
    public Promise<V> await() throws InterruptedException {
        if (isDone()) {
            return this;
        }
        if (!Thread.interrupted()) {
            checkDeadLock();
            synchronized (this) {
                while (!isDone()) {
                    e();
                    wait();
                    f();
                }
            }
            return this;
        }
        throw new InterruptedException(toString());
    }

    @Override // io.netty.util.concurrent.Future
    public Promise<V> awaitUninterruptibly() {
        if (isDone()) {
            return this;
        }
        checkDeadLock();
        boolean z = false;
        synchronized (this) {
            while (!isDone()) {
                e();
                try {
                    wait();
                    f();
                } catch (InterruptedException unused) {
                    z = true;
                    f();
                }
            }
        }
        if (z) {
            Thread.currentThread().interrupt();
        }
        return this;
    }

    @Override // io.netty.util.concurrent.Future
    public boolean await(long j, TimeUnit timeUnit) throws InterruptedException {
        return a(timeUnit.toNanos(j), true);
    }

    @Override // io.netty.util.concurrent.Future
    public boolean await(long j) throws InterruptedException {
        return a(TimeUnit.MILLISECONDS.toNanos(j), true);
    }

    @Override // io.netty.util.concurrent.Future
    public boolean awaitUninterruptibly(long j, TimeUnit timeUnit) {
        try {
            return a(timeUnit.toNanos(j), false);
        } catch (InterruptedException unused) {
            throw new InternalError();
        }
    }

    @Override // io.netty.util.concurrent.Future
    public boolean awaitUninterruptibly(long j) {
        try {
            return a(TimeUnit.MILLISECONDS.toNanos(j), false);
        } catch (InterruptedException unused) {
            throw new InternalError();
        }
    }

    @Override // io.netty.util.concurrent.Future
    public V getNow() {
        V v = (V) this.g;
        if ((v instanceof a) || v == d) {
            return null;
        }
        return v;
    }

    @Override // io.netty.util.concurrent.Future, java.util.concurrent.Future
    public boolean cancel(boolean z) {
        if (!c.compareAndSet(this, null, f)) {
            return false;
        }
        d();
        a();
        return true;
    }

    @Override // java.util.concurrent.Future
    public boolean isCancelled() {
        return c(this.g);
    }

    @Override // java.util.concurrent.Future
    public boolean isDone() {
        return d(this.g);
    }

    @Override // io.netty.util.concurrent.Future
    public Promise<V> sync() throws InterruptedException {
        await();
        g();
        return this;
    }

    @Override // io.netty.util.concurrent.Future
    public Promise<V> syncUninterruptibly() {
        awaitUninterruptibly();
        g();
        return this;
    }

    public String toString() {
        return toStringBuilder().toString();
    }

    public StringBuilder toStringBuilder() {
        StringBuilder sb = new StringBuilder(64);
        sb.append(StringUtil.simpleClassName(this));
        sb.append('@');
        sb.append(Integer.toHexString(hashCode()));
        Object obj = this.g;
        if (obj == d) {
            sb.append("(success)");
        } else if (obj == e) {
            sb.append("(uncancellable)");
        } else if (obj instanceof a) {
            sb.append("(failure: ");
            sb.append(((a) obj).a);
            sb.append(')');
        } else if (obj != null) {
            sb.append("(success: ");
            sb.append(obj);
            sb.append(')');
        } else {
            sb.append("(incomplete)");
        }
        return sb;
    }

    public EventExecutor executor() {
        return this.h;
    }

    public void checkDeadLock() {
        EventExecutor executor = executor();
        if (executor != null && executor.inEventLoop()) {
            throw new BlockingOperationException(toString());
        }
    }

    public static void notifyListener(EventExecutor eventExecutor, Future<?> future, GenericFutureListener<?> genericFutureListener) {
        ObjectUtil.checkNotNull(eventExecutor, "eventExecutor");
        ObjectUtil.checkNotNull(future, "future");
        ObjectUtil.checkNotNull(genericFutureListener, "listener");
        a(eventExecutor, future, genericFutureListener);
    }

    private void a() {
        if (this.i != null) {
            b();
        }
    }

    private void b() {
        InternalThreadLocalMap internalThreadLocalMap;
        int futureListenerStackDepth;
        EventExecutor executor = executor();
        if (!executor.inEventLoop() || (futureListenerStackDepth = (internalThreadLocalMap = InternalThreadLocalMap.get()).futureListenerStackDepth()) >= 8) {
            a(executor, new OneTimeTask() { // from class: io.netty.util.concurrent.DefaultPromise.1
                @Override // java.lang.Runnable
                public void run() {
                    DefaultPromise.this.c();
                }
            });
            return;
        }
        internalThreadLocalMap.setFutureListenerStackDepth(futureListenerStackDepth + 1);
        try {
            c();
        } finally {
            internalThreadLocalMap.setFutureListenerStackDepth(futureListenerStackDepth);
        }
    }

    private static void a(EventExecutor eventExecutor, final Future<?> future, final GenericFutureListener<?> genericFutureListener) {
        InternalThreadLocalMap internalThreadLocalMap;
        int futureListenerStackDepth;
        if (!eventExecutor.inEventLoop() || (futureListenerStackDepth = (internalThreadLocalMap = InternalThreadLocalMap.get()).futureListenerStackDepth()) >= 8) {
            a(eventExecutor, new OneTimeTask() { // from class: io.netty.util.concurrent.DefaultPromise.2
                @Override // java.lang.Runnable
                public void run() {
                    DefaultPromise.b(future, genericFutureListener);
                }
            });
            return;
        }
        internalThreadLocalMap.setFutureListenerStackDepth(futureListenerStackDepth + 1);
        try {
            b(future, genericFutureListener);
        } finally {
            internalThreadLocalMap.setFutureListenerStackDepth(futureListenerStackDepth);
        }
    }

    public void c() {
        synchronized (this) {
            if (!this.k && this.i != null) {
                this.k = true;
                Object obj = this.i;
                this.i = null;
                while (true) {
                    if (obj instanceof a) {
                        a((a) obj);
                    } else {
                        b(this, (GenericFutureListener) obj);
                    }
                    synchronized (this) {
                        if (this.i == null) {
                            this.k = false;
                            return;
                        } else {
                            obj = this.i;
                            this.i = null;
                        }
                    }
                }
            }
        }
    }

    private void a(a aVar) {
        GenericFutureListener<? extends Future<?>>[] a2 = aVar.a();
        int b2 = aVar.b();
        for (int i = 0; i < b2; i++) {
            b(this, a2[i]);
        }
    }

    public static void b(Future future, GenericFutureListener genericFutureListener) {
        try {
            genericFutureListener.operationComplete(future);
        } catch (Throwable th) {
            a.warn("An exception was thrown by {}.operationComplete()", genericFutureListener.getClass().getName(), th);
        }
    }

    private void a(GenericFutureListener<? extends Future<? super V>> genericFutureListener) {
        Object obj = this.i;
        if (obj == null) {
            this.i = genericFutureListener;
        } else if (obj instanceof a) {
            ((a) obj).a(genericFutureListener);
        } else {
            this.i = new a((GenericFutureListener) obj, genericFutureListener);
        }
    }

    private void b(GenericFutureListener<? extends Future<? super V>> genericFutureListener) {
        Object obj = this.i;
        if (obj instanceof a) {
            ((a) obj).b(genericFutureListener);
        } else if (obj == genericFutureListener) {
            this.i = null;
        }
    }

    private boolean a(V v) {
        if (v == null) {
            v = (V) d;
        }
        return b(v);
    }

    private boolean a(Throwable th) {
        return b(new a((Throwable) ObjectUtil.checkNotNull(th, "cause")));
    }

    private boolean b(Object obj) {
        if (!c.compareAndSet(this, null, obj) && !c.compareAndSet(this, e, obj)) {
            return false;
        }
        d();
        return true;
    }

    private synchronized void d() {
        if (this.j > 0) {
            notifyAll();
        }
    }

    private void e() {
        short s = this.j;
        if (s != Short.MAX_VALUE) {
            this.j = (short) (s + 1);
            return;
        }
        throw new IllegalStateException("too many waiters: " + this);
    }

    private void f() {
        this.j = (short) (this.j - 1);
    }

    private void g() {
        Throwable cause = cause();
        if (cause != null) {
            PlatformDependent.throwException(cause);
        }
    }

    private boolean a(long j, boolean z) throws InterruptedException {
        Throwable th;
        boolean z2 = true;
        if (isDone()) {
            return true;
        }
        if (j <= 0) {
            return isDone();
        }
        if (!z || !Thread.interrupted()) {
            checkDeadLock();
            long nanoTime = System.nanoTime();
            boolean z3 = false;
            long j2 = j;
            do {
                try {
                    synchronized (this) {
                        try {
                            e();
                            try {
                                wait(j2 / 1000000, (int) (j2 % 1000000));
                                f();
                            } catch (InterruptedException e2) {
                                if (!z) {
                                    try {
                                        f();
                                        z3 = true;
                                    } catch (Throwable th2) {
                                        th = th2;
                                        try {
                                            throw th;
                                        } catch (Throwable th3) {
                                            th = th3;
                                            z3 = z2;
                                            if (z3) {
                                                Thread.currentThread().interrupt();
                                            }
                                            throw th;
                                        }
                                    }
                                } else {
                                    throw e2;
                                }
                            }
                            if (isDone()) {
                                if (z3) {
                                    Thread.currentThread().interrupt();
                                }
                                return true;
                            }
                            j2 = j - (System.nanoTime() - nanoTime);
                        } catch (Throwable th4) {
                            th = th4;
                            z2 = z3;
                            throw th;
                        }
                    }
                } catch (Throwable th5) {
                    th = th5;
                }
            } while (j2 > 0);
            boolean isDone = isDone();
            if (z3) {
                Thread.currentThread().interrupt();
            }
            return isDone;
        }
        throw new InterruptedException(toString());
    }

    void a(final long j, final long j2) {
        Object h = h();
        if (h != null) {
            final ProgressiveFuture progressiveFuture = (ProgressiveFuture) this;
            EventExecutor executor = executor();
            if (executor.inEventLoop()) {
                if (h instanceof GenericProgressiveFutureListener[]) {
                    b(progressiveFuture, (GenericProgressiveFutureListener[]) h, j, j2);
                } else {
                    b(progressiveFuture, (GenericProgressiveFutureListener) h, j, j2);
                }
            } else if (h instanceof GenericProgressiveFutureListener[]) {
                final GenericProgressiveFutureListener[] genericProgressiveFutureListenerArr = (GenericProgressiveFutureListener[]) h;
                a(executor, new OneTimeTask() { // from class: io.netty.util.concurrent.DefaultPromise.3
                    @Override // java.lang.Runnable
                    public void run() {
                        DefaultPromise.b(progressiveFuture, genericProgressiveFutureListenerArr, j, j2);
                    }
                });
            } else {
                final GenericProgressiveFutureListener genericProgressiveFutureListener = (GenericProgressiveFutureListener) h;
                a(executor, new OneTimeTask() { // from class: io.netty.util.concurrent.DefaultPromise.4
                    @Override // java.lang.Runnable
                    public void run() {
                        DefaultPromise.b(progressiveFuture, genericProgressiveFutureListener, j, j2);
                    }
                });
            }
        }
    }

    private synchronized Object h() {
        Object obj = this.i;
        if (obj == null) {
            return null;
        }
        if (obj instanceof a) {
            a aVar = (a) obj;
            int c2 = aVar.c();
            int i = 0;
            switch (c2) {
                case 0:
                    return null;
                case 1:
                    GenericFutureListener<? extends Future<?>>[] a2 = aVar.a();
                    int length = a2.length;
                    while (i < length) {
                        GenericFutureListener<? extends Future<?>> genericFutureListener = a2[i];
                        if (genericFutureListener instanceof GenericProgressiveFutureListener) {
                            return genericFutureListener;
                        }
                        i++;
                    }
                    return null;
                default:
                    GenericFutureListener<? extends Future<?>>[] a3 = aVar.a();
                    GenericProgressiveFutureListener[] genericProgressiveFutureListenerArr = new GenericProgressiveFutureListener[c2];
                    int i2 = 0;
                    while (i < c2) {
                        GenericFutureListener<? extends Future<?>> genericFutureListener2 = a3[i2];
                        if (genericFutureListener2 instanceof GenericProgressiveFutureListener) {
                            i++;
                            genericProgressiveFutureListenerArr[i] = (GenericProgressiveFutureListener) genericFutureListener2;
                        }
                        i2++;
                    }
                    return genericProgressiveFutureListenerArr;
            }
        } else if (obj instanceof GenericProgressiveFutureListener) {
            return obj;
        } else {
            return null;
        }
    }

    public static void b(ProgressiveFuture<?> progressiveFuture, GenericProgressiveFutureListener<?>[] genericProgressiveFutureListenerArr, long j, long j2) {
        for (GenericProgressiveFutureListener<?> genericProgressiveFutureListener : genericProgressiveFutureListenerArr) {
            if (genericProgressiveFutureListener != null) {
                b(progressiveFuture, genericProgressiveFutureListener, j, j2);
            } else {
                return;
            }
        }
    }

    public static void b(ProgressiveFuture progressiveFuture, GenericProgressiveFutureListener genericProgressiveFutureListener, long j, long j2) {
        try {
            genericProgressiveFutureListener.operationProgressed(progressiveFuture, j, j2);
        } catch (Throwable th) {
            a.warn("An exception was thrown by {}.operationProgressed()", genericProgressiveFutureListener.getClass().getName(), th);
        }
    }

    private static boolean c(Object obj) {
        return (obj instanceof a) && (((a) obj).a instanceof CancellationException);
    }

    private static boolean d(Object obj) {
        return (obj == null || obj == e) ? false : true;
    }

    /* loaded from: classes4.dex */
    public static final class a {
        final Throwable a;

        a(Throwable th) {
            this.a = th;
        }
    }

    private static void a(EventExecutor eventExecutor, Runnable runnable) {
        try {
            eventExecutor.execute(runnable);
        } catch (Throwable th) {
            b.error("Failed to submit a listener notification task. Event loop shut down?", th);
        }
    }
}
