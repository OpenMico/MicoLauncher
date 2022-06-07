package androidx.work.impl.utils.futures;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import com.google.common.util.concurrent.ListenableFuture;
import com.umeng.analytics.pro.ai;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.concurrent.locks.LockSupport;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public abstract class AbstractFuture<V> implements ListenableFuture<V> {
    static final a b;
    private static final Object g;
    @Nullable
    volatile Object c;
    @Nullable
    volatile d d;
    @Nullable
    volatile h e;
    static final boolean a = Boolean.parseBoolean(System.getProperty("guava.concurrent.generate_cancellation_cause", "false"));
    private static final Logger f = Logger.getLogger(AbstractFuture.class.getName());

    protected void afterDone() {
    }

    protected void interruptTask() {
    }

    static {
        Throwable th;
        a aVar;
        try {
            aVar = new e(AtomicReferenceFieldUpdater.newUpdater(h.class, Thread.class, "b"), AtomicReferenceFieldUpdater.newUpdater(h.class, h.class, ai.aD), AtomicReferenceFieldUpdater.newUpdater(AbstractFuture.class, h.class, "e"), AtomicReferenceFieldUpdater.newUpdater(AbstractFuture.class, d.class, "d"), AtomicReferenceFieldUpdater.newUpdater(AbstractFuture.class, Object.class, ai.aD));
            th = null;
        } catch (Throwable th2) {
            th = th2;
            aVar = new g();
        }
        b = aVar;
        if (th != null) {
            f.log(Level.SEVERE, "SafeAtomicHelper is broken!", th);
        }
        g = new Object();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class h {
        static final h a = new h(false);
        @Nullable
        volatile Thread b;
        @Nullable
        volatile h c;

        h(boolean z) {
        }

        h() {
            AbstractFuture.b.a(this, Thread.currentThread());
        }

        void a(h hVar) {
            AbstractFuture.b.a(this, hVar);
        }

        void a() {
            Thread thread = this.b;
            if (thread != null) {
                this.b = null;
                LockSupport.unpark(thread);
            }
        }
    }

    private void a(h hVar) {
        hVar.b = null;
        while (true) {
            h hVar2 = this.e;
            if (hVar2 != h.a) {
                h hVar3 = null;
                while (hVar2 != null) {
                    h hVar4 = hVar2.c;
                    if (hVar2.b != null) {
                        hVar3 = hVar2;
                    } else if (hVar3 != null) {
                        hVar3.c = hVar4;
                        if (hVar3.b == null) {
                            break;
                        }
                    } else if (!b.a((AbstractFuture<?>) this, hVar2, hVar4)) {
                        break;
                    }
                    hVar2 = hVar4;
                }
                return;
            }
            return;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class d {
        static final d a = new d(null, null);
        final Runnable b;
        final Executor c;
        @Nullable
        d d;

        d(Runnable runnable, Executor executor) {
            this.b = runnable;
            this.c = executor;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class c {
        static final c a = new c(new Throwable("Failure occurred while trying to finish a future.") { // from class: androidx.work.impl.utils.futures.AbstractFuture.c.1
            @Override // java.lang.Throwable
            public synchronized Throwable fillInStackTrace() {
                return this;
            }
        });
        final Throwable b;

        c(Throwable th) {
            this.b = (Throwable) AbstractFuture.a(th);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class b {
        static final b a;
        static final b b;
        final boolean c;
        @Nullable
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

        b(boolean z, @Nullable Throwable th) {
            this.c = z;
            this.d = th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class f<V> implements Runnable {
        final AbstractFuture<V> a;
        final ListenableFuture<? extends V> b;

        f(AbstractFuture<V> abstractFuture, ListenableFuture<? extends V> listenableFuture) {
            this.a = abstractFuture;
            this.b = listenableFuture;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.a.c == this) {
                if (AbstractFuture.b.a((AbstractFuture<?>) this.a, (Object) this, AbstractFuture.a((ListenableFuture<?>) this.b))) {
                    AbstractFuture.a((AbstractFuture<?>) this.a);
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x0050, code lost:
        java.util.concurrent.locks.LockSupport.parkNanos(r17, r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0057, code lost:
        if (java.lang.Thread.interrupted() != false) goto L_0x007d;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0059, code lost:
        r4 = r17.c;
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
        if ((r4 instanceof androidx.work.impl.utils.futures.AbstractFuture.f) != false) goto L_0x0066;
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
        return b(r4);
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
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final V get(long r18, java.util.concurrent.TimeUnit r20) throws java.lang.InterruptedException, java.util.concurrent.TimeoutException, java.util.concurrent.ExecutionException {
        /*
            Method dump skipped, instructions count: 451
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.work.impl.utils.futures.AbstractFuture.get(long, java.util.concurrent.TimeUnit):java.lang.Object");
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0034, code lost:
        java.util.concurrent.locks.LockSupport.park(r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x003b, code lost:
        if (java.lang.Thread.interrupted() != false) goto L_0x0053;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x003d, code lost:
        r0 = r6.c;
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
        if ((r0 instanceof androidx.work.impl.utils.futures.AbstractFuture.f) != false) goto L_0x004a;
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
        return b(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0053, code lost:
        a(r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x005b, code lost:
        throw new java.lang.InterruptedException();
     */
    @Override // java.util.concurrent.Future
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final V get() throws java.lang.InterruptedException, java.util.concurrent.ExecutionException {
        /*
            r6 = this;
            boolean r0 = java.lang.Thread.interrupted()
            if (r0 != 0) goto L_0x0069
            java.lang.Object r0 = r6.c
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L_0x000e
            r3 = r1
            goto L_0x000f
        L_0x000e:
            r3 = r2
        L_0x000f:
            boolean r4 = r0 instanceof androidx.work.impl.utils.futures.AbstractFuture.f
            if (r4 != 0) goto L_0x0015
            r4 = r1
            goto L_0x0016
        L_0x0015:
            r4 = r2
        L_0x0016:
            r3 = r3 & r4
            if (r3 == 0) goto L_0x001e
            java.lang.Object r0 = r6.b(r0)
            return r0
        L_0x001e:
            androidx.work.impl.utils.futures.AbstractFuture$h r0 = r6.e
            androidx.work.impl.utils.futures.AbstractFuture$h r3 = androidx.work.impl.utils.futures.AbstractFuture.h.a
            if (r0 == r3) goto L_0x0062
            androidx.work.impl.utils.futures.AbstractFuture$h r3 = new androidx.work.impl.utils.futures.AbstractFuture$h
            r3.<init>()
        L_0x0029:
            r3.a(r0)
            androidx.work.impl.utils.futures.AbstractFuture$a r4 = androidx.work.impl.utils.futures.AbstractFuture.b
            boolean r0 = r4.a(r6, r0, r3)
            if (r0 == 0) goto L_0x005c
        L_0x0034:
            java.util.concurrent.locks.LockSupport.park(r6)
            boolean r0 = java.lang.Thread.interrupted()
            if (r0 != 0) goto L_0x0053
            java.lang.Object r0 = r6.c
            if (r0 == 0) goto L_0x0043
            r4 = r1
            goto L_0x0044
        L_0x0043:
            r4 = r2
        L_0x0044:
            boolean r5 = r0 instanceof androidx.work.impl.utils.futures.AbstractFuture.f
            if (r5 != 0) goto L_0x004a
            r5 = r1
            goto L_0x004b
        L_0x004a:
            r5 = r2
        L_0x004b:
            r4 = r4 & r5
            if (r4 == 0) goto L_0x0034
            java.lang.Object r0 = r6.b(r0)
            return r0
        L_0x0053:
            r6.a(r3)
            java.lang.InterruptedException r0 = new java.lang.InterruptedException
            r0.<init>()
            throw r0
        L_0x005c:
            androidx.work.impl.utils.futures.AbstractFuture$h r0 = r6.e
            androidx.work.impl.utils.futures.AbstractFuture$h r4 = androidx.work.impl.utils.futures.AbstractFuture.h.a
            if (r0 != r4) goto L_0x0029
        L_0x0062:
            java.lang.Object r0 = r6.c
            java.lang.Object r0 = r6.b(r0)
            return r0
        L_0x0069:
            java.lang.InterruptedException r0 = new java.lang.InterruptedException
            r0.<init>()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.work.impl.utils.futures.AbstractFuture.get():java.lang.Object");
    }

    /* JADX WARN: Multi-variable type inference failed */
    private V b(Object obj) throws ExecutionException {
        if (obj instanceof b) {
            throw a("Task was cancelled.", ((b) obj).d);
        } else if (obj instanceof c) {
            throw new ExecutionException(((c) obj).b);
        } else if (obj == g) {
            return null;
        } else {
            return obj;
        }
    }

    @Override // java.util.concurrent.Future
    public final boolean isDone() {
        Object obj = this.c;
        boolean z = true;
        boolean z2 = obj != null;
        if (obj instanceof f) {
            z = false;
        }
        return z2 & z;
    }

    @Override // java.util.concurrent.Future
    public final boolean isCancelled() {
        return this.c instanceof b;
    }

    @Override // java.util.concurrent.Future
    public final boolean cancel(boolean z) {
        b bVar;
        Object obj = this.c;
        if (!(obj == null) && !(obj instanceof f)) {
            return false;
        }
        if (a) {
            bVar = new b(z, new CancellationException("Future.cancel() was called."));
        } else if (z) {
            bVar = b.a;
        } else {
            bVar = b.b;
        }
        Object obj2 = obj;
        boolean z2 = false;
        AbstractFuture<V> abstractFuture = this;
        while (true) {
            if (b.a((AbstractFuture<?>) abstractFuture, obj2, (Object) bVar)) {
                if (z) {
                    abstractFuture.interruptTask();
                }
                a((AbstractFuture<?>) abstractFuture);
                if (!(obj2 instanceof f)) {
                    return true;
                }
                ListenableFuture<? extends V> listenableFuture = ((f) obj2).b;
                if (listenableFuture instanceof AbstractFuture) {
                    abstractFuture = (AbstractFuture) listenableFuture;
                    obj2 = abstractFuture.c;
                    if (!(obj2 == null) && !(obj2 instanceof f)) {
                        return true;
                    }
                    z2 = true;
                } else {
                    listenableFuture.cancel(z);
                    return true;
                }
            } else {
                obj2 = abstractFuture.c;
                if (!(obj2 instanceof f)) {
                    return z2;
                }
            }
        }
    }

    protected final boolean wasInterrupted() {
        Object obj = this.c;
        return (obj instanceof b) && ((b) obj).c;
    }

    @Override // com.google.common.util.concurrent.ListenableFuture
    public final void addListener(Runnable runnable, Executor executor) {
        a(runnable);
        a(executor);
        d dVar = this.d;
        if (dVar != d.a) {
            d dVar2 = new d(runnable, executor);
            do {
                dVar2.d = dVar;
                if (!b.a((AbstractFuture<?>) this, dVar, dVar2)) {
                    dVar = this.d;
                } else {
                    return;
                }
            } while (dVar != d.a);
            a(runnable, executor);
        }
        a(runnable, executor);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean set(@Nullable V v) {
        if (v == null) {
            v = (V) g;
        }
        if (!b.a((AbstractFuture<?>) this, (Object) null, (Object) v)) {
            return false;
        }
        a((AbstractFuture<?>) this);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean setException(Throwable th) {
        if (!b.a((AbstractFuture<?>) this, (Object) null, (Object) new c((Throwable) a(th)))) {
            return false;
        }
        a((AbstractFuture<?>) this);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean setFuture(ListenableFuture<? extends V> listenableFuture) {
        c cVar;
        a(listenableFuture);
        Object obj = this.c;
        if (obj == null) {
            if (listenableFuture.isDone()) {
                if (!b.a((AbstractFuture<?>) this, (Object) null, a((ListenableFuture<?>) listenableFuture))) {
                    return false;
                }
                a((AbstractFuture<?>) this);
                return true;
            }
            f fVar = new f(this, listenableFuture);
            if (b.a((AbstractFuture<?>) this, (Object) null, (Object) fVar)) {
                try {
                    listenableFuture.addListener(fVar, a.INSTANCE);
                } catch (Throwable th) {
                    try {
                        cVar = new c(th);
                    } catch (Throwable unused) {
                        cVar = c.a;
                    }
                    b.a((AbstractFuture<?>) this, (Object) fVar, (Object) cVar);
                }
                return true;
            }
            obj = this.c;
        }
        if (obj instanceof b) {
            listenableFuture.cancel(((b) obj).c);
        }
        return false;
    }

    static Object a(ListenableFuture<?> listenableFuture) {
        if (listenableFuture instanceof AbstractFuture) {
            Object obj = ((AbstractFuture) listenableFuture).c;
            if (!(obj instanceof b)) {
                return obj;
            }
            b bVar = (b) obj;
            return bVar.c ? bVar.d != null ? new b(false, bVar.d) : b.b : obj;
        }
        boolean isCancelled = listenableFuture.isCancelled();
        if ((!a) && isCancelled) {
            return b.b;
        }
        try {
            Object a2 = a((Future<Object>) listenableFuture);
            return a2 == null ? g : a2;
        } catch (CancellationException e2) {
            if (isCancelled) {
                return new b(false, e2);
            }
            return new c(new IllegalArgumentException("get() threw CancellationException, despite reporting isCancelled() == false: " + listenableFuture, e2));
        } catch (ExecutionException e3) {
            return new c(e3.getCause());
        } catch (Throwable th) {
            return new c(th);
        }
    }

    private static <V> V a(Future<V> future) throws ExecutionException {
        V v;
        boolean z = false;
        while (true) {
            try {
                v = future.get();
                break;
            } catch (InterruptedException unused) {
                z = true;
            } catch (Throwable th) {
                if (z) {
                    Thread.currentThread().interrupt();
                }
                throw th;
            }
        }
        if (z) {
            Thread.currentThread().interrupt();
        }
        return v;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0, types: [androidx.work.impl.utils.futures.AbstractFuture$a] */
    /* JADX WARN: Type inference failed for: r4v0, types: [androidx.work.impl.utils.futures.AbstractFuture<?>] */
    /* JADX WARN: Type inference failed for: r4v1, types: [androidx.work.impl.utils.futures.AbstractFuture] */
    /* JADX WARN: Type inference failed for: r4v6, types: [androidx.work.impl.utils.futures.AbstractFuture, androidx.work.impl.utils.futures.AbstractFuture<V>] */
    static void a(AbstractFuture<?> abstractFuture) {
        d dVar = null;
        while (true) {
            abstractFuture.a();
            abstractFuture.afterDone();
            d a2 = abstractFuture.a(dVar);
            while (a2 != null) {
                dVar = a2.d;
                Runnable runnable = a2.b;
                if (runnable instanceof f) {
                    f fVar = (f) runnable;
                    abstractFuture = fVar.a;
                    if (abstractFuture.c == fVar) {
                        if (b.a(abstractFuture, fVar, a((ListenableFuture<?>) fVar.b))) {
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

    private void a() {
        h hVar;
        do {
            hVar = this.e;
        } while (!b.a((AbstractFuture<?>) this, hVar, h.a));
        while (hVar != null) {
            hVar.a();
            hVar = hVar.c;
        }
    }

    private d a(d dVar) {
        d dVar2;
        do {
            dVar2 = this.d;
        } while (!b.a((AbstractFuture<?>) this, dVar2, d.a));
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
            if (str != null && !str.isEmpty()) {
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

    @Nullable
    protected String pendingToString() {
        Object obj = this.c;
        if (obj instanceof f) {
            return "setFuture=[" + c(((f) obj).b) + "]";
        } else if (!(this instanceof ScheduledFuture)) {
            return null;
        } else {
            return "remaining delay=[" + ((ScheduledFuture) this).getDelay(TimeUnit.MILLISECONDS) + " ms]";
        }
    }

    private void a(StringBuilder sb) {
        try {
            Object a2 = a((Future<Object>) this);
            sb.append("SUCCESS, result=[");
            sb.append(c(a2));
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

    private String c(Object obj) {
        return obj == this ? "this future" : String.valueOf(obj);
    }

    private static void a(Runnable runnable, Executor executor) {
        try {
            executor.execute(runnable);
        } catch (RuntimeException e2) {
            Logger logger = f;
            Level level = Level.SEVERE;
            logger.log(level, "RuntimeException while executing runnable " + runnable + " with executor " + executor, (Throwable) e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static abstract class a {
        abstract void a(h hVar, h hVar2);

        abstract void a(h hVar, Thread thread);

        abstract boolean a(AbstractFuture<?> abstractFuture, d dVar, d dVar2);

        abstract boolean a(AbstractFuture<?> abstractFuture, h hVar, h hVar2);

        abstract boolean a(AbstractFuture<?> abstractFuture, Object obj, Object obj2);

        private a() {
        }
    }

    /* loaded from: classes.dex */
    private static final class e extends a {
        final AtomicReferenceFieldUpdater<h, Thread> a;
        final AtomicReferenceFieldUpdater<h, h> b;
        final AtomicReferenceFieldUpdater<AbstractFuture, h> c;
        final AtomicReferenceFieldUpdater<AbstractFuture, d> d;
        final AtomicReferenceFieldUpdater<AbstractFuture, Object> e;

        e(AtomicReferenceFieldUpdater<h, Thread> atomicReferenceFieldUpdater, AtomicReferenceFieldUpdater<h, h> atomicReferenceFieldUpdater2, AtomicReferenceFieldUpdater<AbstractFuture, h> atomicReferenceFieldUpdater3, AtomicReferenceFieldUpdater<AbstractFuture, d> atomicReferenceFieldUpdater4, AtomicReferenceFieldUpdater<AbstractFuture, Object> atomicReferenceFieldUpdater5) {
            super();
            this.a = atomicReferenceFieldUpdater;
            this.b = atomicReferenceFieldUpdater2;
            this.c = atomicReferenceFieldUpdater3;
            this.d = atomicReferenceFieldUpdater4;
            this.e = atomicReferenceFieldUpdater5;
        }

        @Override // androidx.work.impl.utils.futures.AbstractFuture.a
        void a(h hVar, Thread thread) {
            this.a.lazySet(hVar, thread);
        }

        @Override // androidx.work.impl.utils.futures.AbstractFuture.a
        void a(h hVar, h hVar2) {
            this.b.lazySet(hVar, hVar2);
        }

        @Override // androidx.work.impl.utils.futures.AbstractFuture.a
        boolean a(AbstractFuture<?> abstractFuture, h hVar, h hVar2) {
            return this.c.compareAndSet(abstractFuture, hVar, hVar2);
        }

        @Override // androidx.work.impl.utils.futures.AbstractFuture.a
        boolean a(AbstractFuture<?> abstractFuture, d dVar, d dVar2) {
            return this.d.compareAndSet(abstractFuture, dVar, dVar2);
        }

        @Override // androidx.work.impl.utils.futures.AbstractFuture.a
        boolean a(AbstractFuture<?> abstractFuture, Object obj, Object obj2) {
            return this.e.compareAndSet(abstractFuture, obj, obj2);
        }
    }

    /* loaded from: classes.dex */
    private static final class g extends a {
        g() {
            super();
        }

        @Override // androidx.work.impl.utils.futures.AbstractFuture.a
        void a(h hVar, Thread thread) {
            hVar.b = thread;
        }

        @Override // androidx.work.impl.utils.futures.AbstractFuture.a
        void a(h hVar, h hVar2) {
            hVar.c = hVar2;
        }

        @Override // androidx.work.impl.utils.futures.AbstractFuture.a
        boolean a(AbstractFuture<?> abstractFuture, h hVar, h hVar2) {
            synchronized (abstractFuture) {
                if (abstractFuture.e != hVar) {
                    return false;
                }
                abstractFuture.e = hVar2;
                return true;
            }
        }

        @Override // androidx.work.impl.utils.futures.AbstractFuture.a
        boolean a(AbstractFuture<?> abstractFuture, d dVar, d dVar2) {
            synchronized (abstractFuture) {
                if (abstractFuture.d != dVar) {
                    return false;
                }
                abstractFuture.d = dVar2;
                return true;
            }
        }

        @Override // androidx.work.impl.utils.futures.AbstractFuture.a
        boolean a(AbstractFuture<?> abstractFuture, Object obj, Object obj2) {
            synchronized (abstractFuture) {
                if (abstractFuture.c != obj) {
                    return false;
                }
                abstractFuture.c = obj2;
                return true;
            }
        }
    }

    private static CancellationException a(@Nullable String str, @Nullable Throwable th) {
        CancellationException cancellationException = new CancellationException(str);
        cancellationException.initCause(th);
        return cancellationException;
    }

    @NonNull
    static <T> T a(@Nullable T t) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException();
    }
}
