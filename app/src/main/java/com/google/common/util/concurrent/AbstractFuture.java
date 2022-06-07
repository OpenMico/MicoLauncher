package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.ForOverride;
import com.umeng.analytics.pro.ai;
import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.concurrent.locks.LockSupport;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import sun.misc.Unsafe;

@GwtCompatible(emulated = true)
/* loaded from: classes2.dex */
public abstract class AbstractFuture<V> extends FluentFuture<V> {
    private static final boolean a = Boolean.parseBoolean(System.getProperty("guava.concurrent.generate_cancellation_cause", "false"));
    private static final Logger b = Logger.getLogger(AbstractFuture.class.getName());
    private static final a c;
    private static final Object d;
    @NullableDecl
    private volatile Object e;
    @NullableDecl
    private volatile d f;
    @NullableDecl
    private volatile j g;

    @Beta
    @ForOverride
    protected void afterDone() {
    }

    protected void interruptTask() {
    }

    static {
        Throwable th;
        a aVar;
        Throwable th2 = null;
        try {
            aVar = new i();
            th = null;
        } catch (Throwable th3) {
            try {
                th = th3;
                aVar = new e(AtomicReferenceFieldUpdater.newUpdater(j.class, Thread.class, "b"), AtomicReferenceFieldUpdater.newUpdater(j.class, j.class, ai.aD), AtomicReferenceFieldUpdater.newUpdater(AbstractFuture.class, j.class, "g"), AtomicReferenceFieldUpdater.newUpdater(AbstractFuture.class, d.class, "f"), AtomicReferenceFieldUpdater.newUpdater(AbstractFuture.class, Object.class, "e"));
            } catch (Throwable th4) {
                th2 = th4;
                th = th3;
                aVar = new g();
            }
        }
        c = aVar;
        if (th2 != null) {
            b.log(Level.SEVERE, "UnsafeAtomicHelper is broken!", th);
            b.log(Level.SEVERE, "SafeAtomicHelper is broken!", th2);
        }
        d = new Object();
    }

    /* loaded from: classes2.dex */
    public static abstract class h<V> extends AbstractFuture<V> {
        @Override // com.google.common.util.concurrent.AbstractFuture, java.util.concurrent.Future
        @CanIgnoreReturnValue
        public final V get() throws InterruptedException, ExecutionException {
            return (V) AbstractFuture.super.get();
        }

        @Override // com.google.common.util.concurrent.AbstractFuture, java.util.concurrent.Future
        @CanIgnoreReturnValue
        public final V get(long j, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
            return (V) AbstractFuture.super.get(j, timeUnit);
        }

        @Override // com.google.common.util.concurrent.AbstractFuture, java.util.concurrent.Future
        public final boolean isDone() {
            return AbstractFuture.super.isDone();
        }

        @Override // com.google.common.util.concurrent.AbstractFuture, java.util.concurrent.Future
        public final boolean isCancelled() {
            return AbstractFuture.super.isCancelled();
        }

        @Override // com.google.common.util.concurrent.AbstractFuture, com.google.common.util.concurrent.ListenableFuture
        public final void addListener(Runnable runnable, Executor executor) {
            AbstractFuture.super.addListener(runnable, executor);
        }

        @Override // com.google.common.util.concurrent.AbstractFuture, java.util.concurrent.Future
        @CanIgnoreReturnValue
        public final boolean cancel(boolean z) {
            return AbstractFuture.super.cancel(z);
        }
    }

    /* loaded from: classes2.dex */
    public static final class j {
        static final j a = new j(false);
        @NullableDecl
        volatile Thread b;
        @NullableDecl
        volatile j c;

        j(boolean z) {
        }

        j() {
            AbstractFuture.c.a(this, Thread.currentThread());
        }

        void a(j jVar) {
            AbstractFuture.c.a(this, jVar);
        }

        void a() {
            Thread thread = this.b;
            if (thread != null) {
                this.b = null;
                LockSupport.unpark(thread);
            }
        }
    }

    private void a(j jVar) {
        jVar.b = null;
        while (true) {
            j jVar2 = this.g;
            if (jVar2 != j.a) {
                j jVar3 = null;
                while (jVar2 != null) {
                    j jVar4 = jVar2.c;
                    if (jVar2.b != null) {
                        jVar3 = jVar2;
                    } else if (jVar3 != null) {
                        jVar3.c = jVar4;
                        if (jVar3.b == null) {
                            break;
                        }
                    } else if (!c.a((AbstractFuture<?>) this, jVar2, jVar4)) {
                        break;
                    }
                    jVar2 = jVar4;
                }
                return;
            }
            return;
        }
    }

    /* loaded from: classes2.dex */
    public static final class d {
        static final d a = new d(null, null);
        final Runnable b;
        final Executor c;
        @NullableDecl
        d d;

        d(Runnable runnable, Executor executor) {
            this.b = runnable;
            this.c = executor;
        }
    }

    /* loaded from: classes2.dex */
    public static final class c {
        static final c a = new c(new Throwable("Failure occurred while trying to finish a future.") { // from class: com.google.common.util.concurrent.AbstractFuture.c.1
            @Override // java.lang.Throwable
            public synchronized Throwable fillInStackTrace() {
                return this;
            }
        });
        final Throwable b;

        c(Throwable th) {
            this.b = (Throwable) Preconditions.checkNotNull(th);
        }
    }

    /* loaded from: classes2.dex */
    public static final class b {
        static final b a;
        static final b b;
        final boolean c;
        @NullableDecl
        final Throwable d;

        static {
            if (AbstractFuture.a) {
                b = null;
                a = null;
                return;
            }
            b = new b(false, null);
            a = new b(true, null);
        }

        b(boolean z, @NullableDecl Throwable th) {
            this.c = z;
            this.d = th;
        }
    }

    /* loaded from: classes2.dex */
    public static final class f<V> implements Runnable {
        final AbstractFuture<V> a;
        final ListenableFuture<? extends V> b;

        f(AbstractFuture<V> abstractFuture, ListenableFuture<? extends V> listenableFuture) {
            this.a = abstractFuture;
            this.b = listenableFuture;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (((AbstractFuture) this.a).e == this) {
                if (AbstractFuture.c.a((AbstractFuture<?>) this.a, (Object) this, AbstractFuture.a((ListenableFuture<?>) this.b))) {
                    AbstractFuture.e(this.a);
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x0050, code lost:
        java.util.concurrent.locks.LockSupport.parkNanos(r16, r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0057, code lost:
        if (java.lang.Thread.interrupted() != false) goto L_0x007d;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0059, code lost:
        r4 = r16.e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x005b, code lost:
        if (r4 == null) goto L_0x005f;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x005d, code lost:
        r5 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x005f, code lost:
        r5 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0062, code lost:
        if ((r4 instanceof com.google.common.util.concurrent.AbstractFuture.f) != false) goto L_0x0066;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0064, code lost:
        r6 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0066, code lost:
        r6 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0068, code lost:
        if ((r5 & r6) == false) goto L_0x006f;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x006e, code lost:
        return a(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x006f, code lost:
        r4 = r11 - java.lang.System.nanoTime();
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0077, code lost:
        if (r4 >= 1000) goto L_0x0050;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0079, code lost:
        a(r15);
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x007d, code lost:
        a(r15);
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0085, code lost:
        throw new java.lang.InterruptedException();
     */
    @Override // java.util.concurrent.Future
    @com.google.errorprone.annotations.CanIgnoreReturnValue
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public V get(long r17, java.util.concurrent.TimeUnit r19) throws java.lang.InterruptedException, java.util.concurrent.TimeoutException, java.util.concurrent.ExecutionException {
        /*
            Method dump skipped, instructions count: 299
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.AbstractFuture.get(long, java.util.concurrent.TimeUnit):java.lang.Object");
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0034, code lost:
        java.util.concurrent.locks.LockSupport.park(r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x003b, code lost:
        if (java.lang.Thread.interrupted() != false) goto L_0x0053;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x003d, code lost:
        r0 = r6.e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x003f, code lost:
        if (r0 == null) goto L_0x0043;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0041, code lost:
        r4 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0043, code lost:
        r4 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0046, code lost:
        if ((r0 instanceof com.google.common.util.concurrent.AbstractFuture.f) != false) goto L_0x004a;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0048, code lost:
        r5 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x004a, code lost:
        r5 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x004c, code lost:
        if ((r4 & r5) == false) goto L_0x0034;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0052, code lost:
        return a(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0053, code lost:
        a(r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x005b, code lost:
        throw new java.lang.InterruptedException();
     */
    @Override // java.util.concurrent.Future
    @com.google.errorprone.annotations.CanIgnoreReturnValue
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public V get() throws java.lang.InterruptedException, java.util.concurrent.ExecutionException {
        /*
            r6 = this;
            boolean r0 = java.lang.Thread.interrupted()
            if (r0 != 0) goto L_0x0069
            java.lang.Object r0 = r6.e
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L_0x000e
            r3 = r1
            goto L_0x000f
        L_0x000e:
            r3 = r2
        L_0x000f:
            boolean r4 = r0 instanceof com.google.common.util.concurrent.AbstractFuture.f
            if (r4 != 0) goto L_0x0015
            r4 = r1
            goto L_0x0016
        L_0x0015:
            r4 = r2
        L_0x0016:
            r3 = r3 & r4
            if (r3 == 0) goto L_0x001e
            java.lang.Object r0 = r6.a(r0)
            return r0
        L_0x001e:
            com.google.common.util.concurrent.AbstractFuture$j r0 = r6.g
            com.google.common.util.concurrent.AbstractFuture$j r3 = com.google.common.util.concurrent.AbstractFuture.j.a
            if (r0 == r3) goto L_0x0062
            com.google.common.util.concurrent.AbstractFuture$j r3 = new com.google.common.util.concurrent.AbstractFuture$j
            r3.<init>()
        L_0x0029:
            r3.a(r0)
            com.google.common.util.concurrent.AbstractFuture$a r4 = com.google.common.util.concurrent.AbstractFuture.c
            boolean r0 = r4.a(r6, r0, r3)
            if (r0 == 0) goto L_0x005c
        L_0x0034:
            java.util.concurrent.locks.LockSupport.park(r6)
            boolean r0 = java.lang.Thread.interrupted()
            if (r0 != 0) goto L_0x0053
            java.lang.Object r0 = r6.e
            if (r0 == 0) goto L_0x0043
            r4 = r1
            goto L_0x0044
        L_0x0043:
            r4 = r2
        L_0x0044:
            boolean r5 = r0 instanceof com.google.common.util.concurrent.AbstractFuture.f
            if (r5 != 0) goto L_0x004a
            r5 = r1
            goto L_0x004b
        L_0x004a:
            r5 = r2
        L_0x004b:
            r4 = r4 & r5
            if (r4 == 0) goto L_0x0034
            java.lang.Object r0 = r6.a(r0)
            return r0
        L_0x0053:
            r6.a(r3)
            java.lang.InterruptedException r0 = new java.lang.InterruptedException
            r0.<init>()
            throw r0
        L_0x005c:
            com.google.common.util.concurrent.AbstractFuture$j r0 = r6.g
            com.google.common.util.concurrent.AbstractFuture$j r4 = com.google.common.util.concurrent.AbstractFuture.j.a
            if (r0 != r4) goto L_0x0029
        L_0x0062:
            java.lang.Object r0 = r6.e
            java.lang.Object r0 = r6.a(r0)
            return r0
        L_0x0069:
            java.lang.InterruptedException r0 = new java.lang.InterruptedException
            r0.<init>()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.AbstractFuture.get():java.lang.Object");
    }

    /* JADX WARN: Multi-variable type inference failed */
    private V a(Object obj) throws ExecutionException {
        if (obj instanceof b) {
            throw a("Task was cancelled.", ((b) obj).d);
        } else if (obj instanceof c) {
            throw new ExecutionException(((c) obj).b);
        } else if (obj == d) {
            return null;
        } else {
            return obj;
        }
    }

    @Override // java.util.concurrent.Future
    public boolean isDone() {
        Object obj = this.e;
        boolean z = true;
        boolean z2 = obj != null;
        if (obj instanceof f) {
            z = false;
        }
        return z2 & z;
    }

    @Override // java.util.concurrent.Future
    public boolean isCancelled() {
        return this.e instanceof b;
    }

    @Override // java.util.concurrent.Future
    @CanIgnoreReturnValue
    public boolean cancel(boolean z) {
        Object obj = this.e;
        if (!(obj == null) && !(obj instanceof f)) {
            return false;
        }
        b bVar = a ? new b(z, new CancellationException("Future.cancel() was called.")) : z ? b.a : b.b;
        Object obj2 = obj;
        boolean z2 = false;
        AbstractFuture<V> abstractFuture = this;
        while (true) {
            if (c.a((AbstractFuture<?>) abstractFuture, obj2, (Object) bVar)) {
                if (z) {
                    abstractFuture.interruptTask();
                }
                e(abstractFuture);
                if (!(obj2 instanceof f)) {
                    return true;
                }
                ListenableFuture<? extends V> listenableFuture = ((f) obj2).b;
                if (listenableFuture instanceof h) {
                    abstractFuture = (AbstractFuture) listenableFuture;
                    obj2 = abstractFuture.e;
                    if (!(obj2 == null) && !(obj2 instanceof f)) {
                        return true;
                    }
                    z2 = true;
                } else {
                    listenableFuture.cancel(z);
                    return true;
                }
            } else {
                obj2 = abstractFuture.e;
                if (!(obj2 instanceof f)) {
                    return z2;
                }
            }
        }
    }

    protected final boolean wasInterrupted() {
        Object obj = this.e;
        return (obj instanceof b) && ((b) obj).c;
    }

    @Override // com.google.common.util.concurrent.ListenableFuture
    public void addListener(Runnable runnable, Executor executor) {
        Preconditions.checkNotNull(runnable, "Runnable was null.");
        Preconditions.checkNotNull(executor, "Executor was null.");
        d dVar = this.f;
        if (dVar != d.a) {
            d dVar2 = new d(runnable, executor);
            do {
                dVar2.d = dVar;
                if (!c.a((AbstractFuture<?>) this, dVar, dVar2)) {
                    dVar = this.f;
                } else {
                    return;
                }
            } while (dVar != d.a);
            a(runnable, executor);
        }
        a(runnable, executor);
    }

    @CanIgnoreReturnValue
    protected boolean set(@NullableDecl V v) {
        if (v == null) {
            v = (V) d;
        }
        if (!c.a((AbstractFuture<?>) this, (Object) null, (Object) v)) {
            return false;
        }
        e(this);
        return true;
    }

    @CanIgnoreReturnValue
    public boolean setException(Throwable th) {
        if (!c.a((AbstractFuture<?>) this, (Object) null, (Object) new c((Throwable) Preconditions.checkNotNull(th)))) {
            return false;
        }
        e(this);
        return true;
    }

    @CanIgnoreReturnValue
    @Beta
    public boolean setFuture(ListenableFuture<? extends V> listenableFuture) {
        c cVar;
        Preconditions.checkNotNull(listenableFuture);
        Object obj = this.e;
        if (obj == null) {
            if (listenableFuture.isDone()) {
                if (!c.a((AbstractFuture<?>) this, (Object) null, a((ListenableFuture<?>) listenableFuture))) {
                    return false;
                }
                e(this);
                return true;
            }
            f fVar = new f(this, listenableFuture);
            if (c.a((AbstractFuture<?>) this, (Object) null, (Object) fVar)) {
                try {
                    listenableFuture.addListener(fVar, MoreExecutors.directExecutor());
                } catch (Throwable th) {
                    try {
                        cVar = new c(th);
                    } catch (Throwable unused) {
                        cVar = c.a;
                    }
                    c.a((AbstractFuture<?>) this, (Object) fVar, (Object) cVar);
                }
                return true;
            }
            obj = this.e;
        }
        if (obj instanceof b) {
            listenableFuture.cancel(((b) obj).c);
        }
        return false;
    }

    public static Object a(ListenableFuture<?> listenableFuture) {
        if (listenableFuture instanceof h) {
            Object obj = ((AbstractFuture) listenableFuture).e;
            if (!(obj instanceof b)) {
                return obj;
            }
            b bVar = (b) obj;
            return bVar.c ? bVar.d != null ? new b(false, bVar.d) : b.b : obj;
        }
        try {
            Object done = Futures.getDone(listenableFuture);
            if (done == null) {
                done = d;
            }
            return done;
        } catch (CancellationException e2) {
            return new b(false, e2);
        } catch (ExecutionException e3) {
            return new c(e3.getCause());
        } catch (Throwable th) {
            return new c(th);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0, types: [com.google.common.util.concurrent.AbstractFuture$a] */
    /* JADX WARN: Type inference failed for: r4v0, types: [com.google.common.util.concurrent.AbstractFuture<?>] */
    /* JADX WARN: Type inference failed for: r4v1, types: [com.google.common.util.concurrent.AbstractFuture] */
    /* JADX WARN: Type inference failed for: r4v6, types: [com.google.common.util.concurrent.AbstractFuture<V>, com.google.common.util.concurrent.AbstractFuture] */
    public static void e(AbstractFuture<?> abstractFuture) {
        d dVar = null;
        while (true) {
            abstractFuture.d();
            abstractFuture.afterDone();
            d a2 = abstractFuture.a(dVar);
            while (a2 != null) {
                dVar = a2.d;
                Runnable runnable = a2.b;
                if (runnable instanceof f) {
                    f fVar = (f) runnable;
                    abstractFuture = fVar.a;
                    if (((AbstractFuture) abstractFuture).e == fVar) {
                        if (c.a(abstractFuture, fVar, a((ListenableFuture<?>) fVar.b))) {
                            break;
                        }
                    } else {
                        continue;
                    }
                } else {
                    a(runnable, a2.c);
                }
                a2 = dVar;
            }
            return;
        }
    }

    final Throwable a() {
        return ((c) this.e).b;
    }

    final void a(@NullableDecl Future<?> future) {
        if ((future != null) && isCancelled()) {
            future.cancel(wasInterrupted());
        }
    }

    private void d() {
        j jVar;
        do {
            jVar = this.g;
        } while (!c.a((AbstractFuture<?>) this, jVar, j.a));
        while (jVar != null) {
            jVar.a();
            jVar = jVar.c;
        }
    }

    private d a(d dVar) {
        d dVar2;
        do {
            dVar2 = this.f;
        } while (!c.a((AbstractFuture<?>) this, dVar2, d.a));
        d dVar3 = dVar;
        d dVar4 = dVar2;
        while (dVar4 != null) {
            d dVar5 = dVar4.d;
            dVar4.d = dVar3;
            dVar3 = dVar4;
            dVar4 = dVar5;
        }
        return dVar3;
    }

    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("[status=");
        if (isCancelled()) {
            sb.append("CANCELLED");
        } else if (isDone()) {
            a(sb);
        } else {
            try {
                str = pendingToString();
            } catch (RuntimeException e2) {
                str = "Exception thrown from implementation: " + e2.getClass();
            }
            if (!Strings.isNullOrEmpty(str)) {
                sb.append("PENDING, info=[");
                sb.append(str);
                sb.append("]");
            } else if (isDone()) {
                a(sb);
            } else {
                sb.append("PENDING");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    @NullableDecl
    protected String pendingToString() {
        Object obj = this.e;
        if (obj instanceof f) {
            return "setFuture=[" + b((Object) ((f) obj).b) + "]";
        } else if (!(this instanceof ScheduledFuture)) {
            return null;
        } else {
            return "remaining delay=[" + ((ScheduledFuture) this).getDelay(TimeUnit.MILLISECONDS) + " ms]";
        }
    }

    private void a(StringBuilder sb) {
        try {
            Object done = Futures.getDone(this);
            sb.append("SUCCESS, result=[");
            sb.append(b(done));
            sb.append("]");
        } catch (CancellationException unused) {
            sb.append("CANCELLED");
        } catch (RuntimeException e2) {
            sb.append("UNKNOWN, cause=[");
            sb.append(e2.getClass());
            sb.append(" thrown from get()]");
        } catch (ExecutionException e3) {
            sb.append("FAILURE, cause=[");
            sb.append(e3.getCause());
            sb.append("]");
        }
    }

    private String b(Object obj) {
        return obj == this ? "this future" : String.valueOf(obj);
    }

    private static void a(Runnable runnable, Executor executor) {
        try {
            executor.execute(runnable);
        } catch (RuntimeException e2) {
            Logger logger = b;
            Level level = Level.SEVERE;
            logger.log(level, "RuntimeException while executing runnable " + runnable + " with executor " + executor, (Throwable) e2);
        }
    }

    /* loaded from: classes2.dex */
    public static abstract class a {
        abstract void a(j jVar, j jVar2);

        abstract void a(j jVar, Thread thread);

        abstract boolean a(AbstractFuture<?> abstractFuture, d dVar, d dVar2);

        abstract boolean a(AbstractFuture<?> abstractFuture, j jVar, j jVar2);

        abstract boolean a(AbstractFuture<?> abstractFuture, Object obj, Object obj2);

        private a() {
        }
    }

    /* loaded from: classes2.dex */
    private static final class i extends a {
        static final Unsafe a;
        static final long b;
        static final long c;
        static final long d;
        static final long e;
        static final long f;

        private i() {
            super();
        }

        static {
            Unsafe unsafe;
            try {
                try {
                    unsafe = Unsafe.getUnsafe();
                } catch (PrivilegedActionException e2) {
                    throw new RuntimeException("Could not initialize intrinsics", e2.getCause());
                }
            } catch (SecurityException unused) {
                unsafe = (Unsafe) AccessController.doPrivileged(new PrivilegedExceptionAction<Unsafe>() { // from class: com.google.common.util.concurrent.AbstractFuture.i.1
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
            try {
                c = unsafe.objectFieldOffset(AbstractFuture.class.getDeclaredField("waiters"));
                b = unsafe.objectFieldOffset(AbstractFuture.class.getDeclaredField("listeners"));
                d = unsafe.objectFieldOffset(AbstractFuture.class.getDeclaredField(com.xiaomi.onetrack.api.b.p));
                e = unsafe.objectFieldOffset(j.class.getDeclaredField("b"));
                f = unsafe.objectFieldOffset(j.class.getDeclaredField(ai.aD));
                a = unsafe;
            } catch (Exception e3) {
                Throwables.throwIfUnchecked(e3);
                throw new RuntimeException(e3);
            }
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.a
        void a(j jVar, Thread thread) {
            a.putObject(jVar, e, thread);
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.a
        void a(j jVar, j jVar2) {
            a.putObject(jVar, f, jVar2);
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.a
        boolean a(AbstractFuture<?> abstractFuture, j jVar, j jVar2) {
            return a.compareAndSwapObject(abstractFuture, c, jVar, jVar2);
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.a
        boolean a(AbstractFuture<?> abstractFuture, d dVar, d dVar2) {
            return a.compareAndSwapObject(abstractFuture, b, dVar, dVar2);
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.a
        boolean a(AbstractFuture<?> abstractFuture, Object obj, Object obj2) {
            return a.compareAndSwapObject(abstractFuture, d, obj, obj2);
        }
    }

    /* loaded from: classes2.dex */
    private static final class e extends a {
        final AtomicReferenceFieldUpdater<j, Thread> a;
        final AtomicReferenceFieldUpdater<j, j> b;
        final AtomicReferenceFieldUpdater<AbstractFuture, j> c;
        final AtomicReferenceFieldUpdater<AbstractFuture, d> d;
        final AtomicReferenceFieldUpdater<AbstractFuture, Object> e;

        e(AtomicReferenceFieldUpdater<j, Thread> atomicReferenceFieldUpdater, AtomicReferenceFieldUpdater<j, j> atomicReferenceFieldUpdater2, AtomicReferenceFieldUpdater<AbstractFuture, j> atomicReferenceFieldUpdater3, AtomicReferenceFieldUpdater<AbstractFuture, d> atomicReferenceFieldUpdater4, AtomicReferenceFieldUpdater<AbstractFuture, Object> atomicReferenceFieldUpdater5) {
            super();
            this.a = atomicReferenceFieldUpdater;
            this.b = atomicReferenceFieldUpdater2;
            this.c = atomicReferenceFieldUpdater3;
            this.d = atomicReferenceFieldUpdater4;
            this.e = atomicReferenceFieldUpdater5;
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.a
        void a(j jVar, Thread thread) {
            this.a.lazySet(jVar, thread);
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.a
        void a(j jVar, j jVar2) {
            this.b.lazySet(jVar, jVar2);
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.a
        boolean a(AbstractFuture<?> abstractFuture, j jVar, j jVar2) {
            return this.c.compareAndSet(abstractFuture, jVar, jVar2);
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.a
        boolean a(AbstractFuture<?> abstractFuture, d dVar, d dVar2) {
            return this.d.compareAndSet(abstractFuture, dVar, dVar2);
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.a
        boolean a(AbstractFuture<?> abstractFuture, Object obj, Object obj2) {
            return this.e.compareAndSet(abstractFuture, obj, obj2);
        }
    }

    /* loaded from: classes2.dex */
    private static final class g extends a {
        private g() {
            super();
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.a
        void a(j jVar, Thread thread) {
            jVar.b = thread;
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.a
        void a(j jVar, j jVar2) {
            jVar.c = jVar2;
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.a
        boolean a(AbstractFuture<?> abstractFuture, j jVar, j jVar2) {
            synchronized (abstractFuture) {
                if (((AbstractFuture) abstractFuture).g != jVar) {
                    return false;
                }
                ((AbstractFuture) abstractFuture).g = jVar2;
                return true;
            }
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.a
        boolean a(AbstractFuture<?> abstractFuture, d dVar, d dVar2) {
            synchronized (abstractFuture) {
                if (((AbstractFuture) abstractFuture).f != dVar) {
                    return false;
                }
                ((AbstractFuture) abstractFuture).f = dVar2;
                return true;
            }
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.a
        boolean a(AbstractFuture<?> abstractFuture, Object obj, Object obj2) {
            synchronized (abstractFuture) {
                if (((AbstractFuture) abstractFuture).e != obj) {
                    return false;
                }
                ((AbstractFuture) abstractFuture).e = obj2;
                return true;
            }
        }
    }

    private static CancellationException a(@NullableDecl String str, @NullableDecl Throwable th) {
        CancellationException cancellationException = new CancellationException(str);
        cancellationException.initCause(th);
        return cancellationException;
    }
}
