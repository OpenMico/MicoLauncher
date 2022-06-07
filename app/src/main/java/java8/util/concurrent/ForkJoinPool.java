package java8.util.concurrent;

import com.umeng.analytics.pro.ai;
import java.lang.Thread;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.Permission;
import java.security.Permissions;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import java8.util.Objects;
import java8.util.concurrent.ForkJoinTask;
import java8.util.concurrent.ForkJoinWorkerThread;
import java8.util.function.Predicate;
import sun.misc.Unsafe;

/* loaded from: classes5.dex */
public class ForkJoinPool extends AbstractExecutorService {
    static final RuntimePermission a;
    static final ForkJoinPool b;
    static final int c;
    public static final ForkJoinWorkerThreadFactory defaultForkJoinWorkerThreadFactory;
    private static final int o;
    private static int p;
    private static final Unsafe q = d.a;
    private static final long r;
    private static final long s;
    private static final int t;
    private static final int u;
    private static final Class<?> v;
    volatile long d;
    volatile long e;
    final long f;
    int g;
    final int h;
    volatile int i;
    d[] j;
    final String k;
    final ForkJoinWorkerThreadFactory l;
    final Thread.UncaughtExceptionHandler m;
    final Predicate<? super ForkJoinPool> n;

    /* loaded from: classes5.dex */
    public interface ForkJoinWorkerThreadFactory {
        ForkJoinWorkerThread newThread(ForkJoinPool forkJoinPool);
    }

    /* loaded from: classes5.dex */
    public interface ManagedBlocker {
        boolean block() throws InterruptedException;

        boolean isReleasable();
    }

    private static void e() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(a);
        }
    }

    static AccessControlContext a(Permission... permissionArr) {
        Permissions permissions = new Permissions();
        for (Permission permission : permissionArr) {
            permissions.add(permission);
        }
        return new AccessControlContext(new ProtectionDomain[]{new ProtectionDomain(null, permissions)});
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static final class a implements ForkJoinWorkerThreadFactory {
        private static final AccessControlContext a = ForkJoinPool.a(new RuntimePermission("getClassLoader"));

        private a() {
        }

        @Override // java8.util.concurrent.ForkJoinPool.ForkJoinWorkerThreadFactory
        public final ForkJoinWorkerThread newThread(final ForkJoinPool forkJoinPool) {
            return (ForkJoinWorkerThread) AccessController.doPrivileged(new PrivilegedAction<ForkJoinWorkerThread>() { // from class: java8.util.concurrent.ForkJoinPool.a.1
                /* renamed from: a */
                public ForkJoinWorkerThread run() {
                    return new ForkJoinWorkerThread(forkJoinPool, ClassLoader.getSystemClassLoader());
                }
            }, a);
        }
    }

    /* loaded from: classes5.dex */
    public static final class d {
        private static final Unsafe k = d.a;
        private static final long l;
        private static final int m;
        private static final int n;
        volatile int a;
        int b;
        int c;
        int d;
        volatile int e;
        ForkJoinTask<?>[] h;
        final ForkJoinPool i;
        final ForkJoinWorkerThread j;
        int g = 4096;
        volatile int f = 4096;

        d(ForkJoinPool forkJoinPool, ForkJoinWorkerThread forkJoinWorkerThread) {
            this.i = forkJoinPool;
            this.j = forkJoinWorkerThread;
        }

        public final int a() {
            return (this.d & 65535) >>> 1;
        }

        public final int b() {
            int i = this.f - this.g;
            if (i >= 0) {
                return 0;
            }
            return -i;
        }

        final boolean c() {
            ForkJoinTask<?>[] forkJoinTaskArr;
            int length;
            int i = this.f;
            int i2 = i - this.g;
            if (i2 < 0) {
                return i2 == -1 && ((forkJoinTaskArr = this.h) == null || (length = forkJoinTaskArr.length) == 0 || forkJoinTaskArr[i & (length - 1)] == null);
            }
            return true;
        }

        public final void a(ForkJoinTask<?> forkJoinTask) {
            int length;
            int i = this.g;
            ForkJoinTask<?>[] forkJoinTaskArr = this.h;
            if (forkJoinTaskArr != null && (length = forkJoinTaskArr.length) > 0) {
                long j = (((length - 1) & i) << n) + m;
                ForkJoinPool forkJoinPool = this.i;
                this.g = i + 1;
                k.putOrderedObject(forkJoinTaskArr, j, forkJoinTask);
                int i2 = this.f - i;
                if (i2 == 0 && forkJoinPool != null) {
                    c.b();
                    forkJoinPool.a();
                } else if (i2 + length == 1) {
                    d();
                }
            }
        }

        final ForkJoinTask<?>[] d() {
            int i;
            ForkJoinTask<?>[] forkJoinTaskArr = this.h;
            int length = forkJoinTaskArr != null ? forkJoinTaskArr.length : 0;
            int i2 = length > 0 ? length << 1 : 8192;
            if (i2 < 8192 || i2 > 67108864) {
                throw new RejectedExecutionException("Queue capacity exceeded");
            }
            ForkJoinTask<?>[] forkJoinTaskArr2 = new ForkJoinTask[i2];
            this.h = forkJoinTaskArr2;
            if (forkJoinTaskArr != null && length - 1 > 0) {
                int i3 = this.g;
                int i4 = this.f;
                if (i3 - i4 > 0) {
                    int i5 = i2 - 1;
                    int i6 = i4;
                    do {
                        long j = m + ((i6 & i) << n);
                        ForkJoinTask<?> forkJoinTask = (ForkJoinTask) k.getObjectVolatile(forkJoinTaskArr, j);
                        if (forkJoinTask != null && k.compareAndSwapObject(forkJoinTaskArr, j, forkJoinTask, (Object) null)) {
                            forkJoinTaskArr2[i6 & i5] = forkJoinTask;
                        }
                        i6++;
                    } while (i6 != i3);
                    c.a();
                }
            }
            return forkJoinTaskArr2;
        }

        final ForkJoinTask<?> e() {
            int length;
            int i = this.f;
            int i2 = this.g;
            ForkJoinTask<?>[] forkJoinTaskArr = this.h;
            if (forkJoinTaskArr == null || i == i2 || (length = forkJoinTaskArr.length) <= 0) {
                return null;
            }
            int i3 = i2 - 1;
            long j = (((length - 1) & i3) << n) + m;
            ForkJoinTask<?> forkJoinTask = (ForkJoinTask) k.getObject(forkJoinTaskArr, j);
            if (forkJoinTask == null || !k.compareAndSwapObject(forkJoinTaskArr, j, forkJoinTask, (Object) null)) {
                return null;
            }
            this.g = i3;
            c.a();
            return forkJoinTask;
        }

        final ForkJoinTask<?> f() {
            int i;
            int length;
            while (true) {
                int i2 = this.f;
                int i3 = this.g;
                ForkJoinTask<?>[] forkJoinTaskArr = this.h;
                if (forkJoinTaskArr == null || (i = i2 - i3) >= 0 || (length = forkJoinTaskArr.length) <= 0) {
                    return null;
                }
                long j = (((length - 1) & i2) << n) + m;
                ForkJoinTask<?> forkJoinTask = (ForkJoinTask) k.getObjectVolatile(forkJoinTaskArr, j);
                int i4 = i2 + 1;
                if (i2 == this.f) {
                    if (forkJoinTask != null) {
                        if (k.compareAndSwapObject(forkJoinTaskArr, j, forkJoinTask, (Object) null)) {
                            this.f = i4;
                            return forkJoinTask;
                        }
                    } else if (i == -1) {
                        return null;
                    }
                }
            }
        }

        public final ForkJoinTask<?> g() {
            return (this.d & 65536) != 0 ? f() : e();
        }

        public final ForkJoinTask<?> h() {
            int length;
            ForkJoinTask<?>[] forkJoinTaskArr = this.h;
            if (forkJoinTaskArr == null || (length = forkJoinTaskArr.length) <= 0) {
                return null;
            }
            return forkJoinTaskArr[(length - 1) & ((this.d & 65536) != 0 ? this.f : this.g - 1)];
        }

        public final boolean b(ForkJoinTask<?> forkJoinTask) {
            int length;
            int i = this.f;
            int i2 = this.g;
            ForkJoinTask<?>[] forkJoinTaskArr = this.h;
            if (forkJoinTaskArr == null || i == i2 || (length = forkJoinTaskArr.length) <= 0) {
                return false;
            }
            int i3 = i2 - 1;
            if (!k.compareAndSwapObject(forkJoinTaskArr, (((length - 1) & i3) << n) + m, forkJoinTask, (Object) null)) {
                return false;
            }
            this.g = i3;
            c.a();
            return true;
        }

        final void i() {
            while (true) {
                ForkJoinTask<?> f = f();
                if (f != null) {
                    ForkJoinTask.a(f);
                } else {
                    return;
                }
            }
        }

        final void a(int i) {
            int length;
            while (true) {
                int i2 = this.f;
                int i3 = this.g;
                ForkJoinTask<?>[] forkJoinTaskArr = this.h;
                if (forkJoinTaskArr != null && i2 != i3 && (length = forkJoinTaskArr.length) > 0) {
                    int i4 = i3 - 1;
                    ForkJoinTask forkJoinTask = (ForkJoinTask) ForkJoinPool.a(forkJoinTaskArr, (((length - 1) & i4) << n) + m, (Object) null);
                    if (forkJoinTask != null) {
                        this.g = i4;
                        c.a();
                        forkJoinTask.d();
                        if (i != 0 && i - 1 == 0) {
                            return;
                        }
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            }
        }

        final void b(int i) {
            int i2;
            int length;
            int i3 = 0;
            while (true) {
                int i4 = this.f;
                int i5 = this.g;
                ForkJoinTask<?>[] forkJoinTaskArr = this.h;
                if (forkJoinTaskArr != null && (i2 = i4 - i5) < 0 && (length = forkJoinTaskArr.length) > 0) {
                    int i6 = i4 + 1;
                    ForkJoinTask forkJoinTask = (ForkJoinTask) ForkJoinPool.a(forkJoinTaskArr, ((i4 & (length - 1)) << n) + m, (Object) null);
                    if (forkJoinTask != null) {
                        this.f = i6;
                        forkJoinTask.d();
                        if (i != 0 && (i3 = i3 + 1) == i) {
                            return;
                        }
                    } else if (i2 != -1) {
                        i3 = 0;
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            }
        }

        final void c(ForkJoinTask<?> forkJoinTask) {
            ForkJoinTask<?>[] forkJoinTaskArr;
            int length;
            int i = this.f;
            int i2 = this.g;
            if (i - i2 < 0 && (forkJoinTaskArr = this.h) != null && (length = forkJoinTaskArr.length) > 0) {
                int i3 = length - 1;
                int i4 = i2 - 1;
                int i5 = i4;
                while (true) {
                    long j = ((i5 & i3) << n) + m;
                    ForkJoinTask<?> forkJoinTask2 = (ForkJoinTask) k.getObject(forkJoinTaskArr, j);
                    if (forkJoinTask2 != null) {
                        if (forkJoinTask2 != forkJoinTask) {
                            i5--;
                        } else if (k.compareAndSwapObject(forkJoinTaskArr, j, forkJoinTask2, (Object) null)) {
                            this.g = i4;
                            while (i5 != i4) {
                                int i6 = i5 + 1;
                                long j2 = ((i6 & i3) << n) + m;
                                k.putObjectVolatile(forkJoinTaskArr, j2, (Object) null);
                                k.putOrderedObject(forkJoinTaskArr, ((i5 & i3) << n) + m, (ForkJoinTask) k.getObject(forkJoinTaskArr, j2));
                                i5 = i6;
                            }
                            c.a();
                            forkJoinTask2.d();
                            return;
                        } else {
                            return;
                        }
                    } else {
                        return;
                    }
                }
            }
        }

        final int a(CountedCompleter<?> countedCompleter, int i) {
            boolean z;
            int i2;
            int length;
            if (countedCompleter == null) {
                return 0;
            }
            int i3 = countedCompleter.status;
            if (i3 < 0) {
                return i3;
            }
            while (true) {
                int i4 = this.f;
                int i5 = this.g;
                ForkJoinTask<?>[] forkJoinTaskArr = this.h;
                if (forkJoinTaskArr != null && i4 != i5 && (length = forkJoinTaskArr.length) > 0) {
                    int i6 = i5 - 1;
                    long j = (((length - 1) & i6) << n) + m;
                    ForkJoinTask forkJoinTask = (ForkJoinTask) k.getObject(forkJoinTaskArr, j);
                    if (forkJoinTask instanceof CountedCompleter) {
                        CountedCompleter<?> countedCompleter2 = (CountedCompleter) forkJoinTask;
                        CountedCompleter<?> countedCompleter3 = countedCompleter2;
                        while (true) {
                            if (countedCompleter3 != countedCompleter) {
                                countedCompleter3 = countedCompleter3.completer;
                                if (countedCompleter3 == null) {
                                    break;
                                }
                            } else if (k.compareAndSwapObject(forkJoinTaskArr, j, countedCompleter2, (Object) null)) {
                                this.g = i6;
                                c.a();
                                countedCompleter2.d();
                                z = true;
                            }
                        }
                    }
                }
                z = false;
                i2 = countedCompleter.status;
                if (i2 < 0 || !z || (i != 0 && i - 1 == 0)) {
                    break;
                }
            }
            return i2;
        }

        final boolean j() {
            return k.compareAndSwapInt(this, l, 0, 1);
        }

        final boolean d(ForkJoinTask<?> forkJoinTask) {
            int length;
            boolean z = true;
            int i = this.g - 1;
            ForkJoinTask<?>[] forkJoinTaskArr = this.h;
            if (forkJoinTaskArr != null && (length = forkJoinTaskArr.length) > 0) {
                long j = (((length - 1) & i) << n) + m;
                if (((ForkJoinTask) k.getObject(forkJoinTaskArr, j)) == forkJoinTask && k.compareAndSwapInt(this, l, 0, 1)) {
                    if (this.g == i + 1 && this.h == forkJoinTaskArr && k.compareAndSwapObject(forkJoinTaskArr, j, forkJoinTask, (Object) null)) {
                        this.g = i;
                    } else {
                        z = false;
                    }
                    k.putOrderedInt(this, l, 0);
                    return z;
                }
            }
            return false;
        }

        final int b(CountedCompleter<?> countedCompleter, int i) {
            boolean z;
            int i2;
            int length;
            if (countedCompleter == null) {
                return 0;
            }
            int i3 = countedCompleter.status;
            if (i3 < 0) {
                return i3;
            }
            int i4 = i;
            while (true) {
                int i5 = this.f;
                int i6 = this.g;
                ForkJoinTask<?>[] forkJoinTaskArr = this.h;
                if (forkJoinTaskArr != null && i5 != i6 && (length = forkJoinTaskArr.length) > 0) {
                    int i7 = i6 - 1;
                    long j = (((length - 1) & i7) << n) + m;
                    ForkJoinTask forkJoinTask = (ForkJoinTask) k.getObject(forkJoinTaskArr, j);
                    if (forkJoinTask instanceof CountedCompleter) {
                        CountedCompleter<?> countedCompleter2 = (CountedCompleter) forkJoinTask;
                        CountedCompleter<?> countedCompleter3 = countedCompleter2;
                        while (true) {
                            if (countedCompleter3 != countedCompleter) {
                                countedCompleter3 = countedCompleter3.completer;
                                if (countedCompleter3 == null) {
                                    break;
                                }
                            } else if (k.compareAndSwapInt(this, l, 0, 1)) {
                                if (this.g == i6 && this.h == forkJoinTaskArr && k.compareAndSwapObject(forkJoinTaskArr, j, countedCompleter2, (Object) null)) {
                                    this.g = i7;
                                    z = true;
                                } else {
                                    z = false;
                                }
                                k.putOrderedInt(this, l, 0);
                                if (z) {
                                    countedCompleter2.d();
                                }
                            }
                        }
                    }
                }
                z = false;
                i2 = countedCompleter.status;
                if (i2 < 0 || !z || (i4 != 0 && i4 - 1 == 0)) {
                    break;
                }
            }
            return i2;
        }

        final boolean k() {
            Thread.State state;
            ForkJoinWorkerThread forkJoinWorkerThread = this.j;
            return (forkJoinWorkerThread == null || (state = forkJoinWorkerThread.getState()) == Thread.State.BLOCKED || state == Thread.State.WAITING || state == Thread.State.TIMED_WAITING) ? false : true;
        }

        static {
            try {
                l = k.objectFieldOffset(d.class.getDeclaredField(ai.at));
                m = k.arrayBaseOffset(ForkJoinTask[].class);
                int arrayIndexScale = k.arrayIndexScale(ForkJoinTask[].class);
                if (((arrayIndexScale - 1) & arrayIndexScale) == 0) {
                    n = 31 - Integer.numberOfLeadingZeros(arrayIndexScale);
                    return;
                }
                throw new ExceptionInInitializerError("array index scale not a power of two");
            } catch (Exception e) {
                throw new ExceptionInInitializerError(e);
            }
        }
    }

    private static final synchronized int f() {
        int i;
        synchronized (ForkJoinPool.class) {
            i = p + 1;
            p = i;
        }
        return i;
    }

    static long a(Object obj, long j, long j2) {
        long longVolatile;
        do {
            longVolatile = q.getLongVolatile(obj, j);
        } while (!q.compareAndSwapLong(obj, j, longVolatile, longVolatile + j2));
        return longVolatile;
    }

    static Object a(Object obj, long j, Object obj2) {
        Object objectVolatile;
        do {
            objectVolatile = q.getObjectVolatile(obj, j);
        } while (!q.compareAndSwapObject(obj, j, objectVolatile, obj2));
        return objectVolatile;
    }

    private boolean g() {
        ForkJoinWorkerThread forkJoinWorkerThread;
        ForkJoinWorkerThreadFactory forkJoinWorkerThreadFactory = this.l;
        Throwable th = null;
        if (forkJoinWorkerThreadFactory != null) {
            try {
                forkJoinWorkerThread = forkJoinWorkerThreadFactory.newThread(this);
                if (forkJoinWorkerThread != null) {
                    try {
                        forkJoinWorkerThread.start();
                        return true;
                    } catch (Throwable th2) {
                        th = th2;
                    }
                }
            } catch (Throwable th3) {
                th = th3;
                forkJoinWorkerThread = null;
            }
        } else {
            forkJoinWorkerThread = null;
        }
        a(forkJoinWorkerThread, th);
        return false;
    }

    private void a(long j) {
        long j2 = j;
        do {
            long j3 = ((-281474976710656L) & (281474976710656L + j2)) | (281470681743360L & (4294967296L + j2));
            if (this.d != j2 || !q.compareAndSwapLong(this, r, j2, j3)) {
                j2 = this.d;
                if ((140737488355328L & j2) == 0) {
                    return;
                }
            } else {
                g();
                return;
            }
        } while (((int) j2) == 0);
    }

    public final d a(ForkJoinWorkerThread forkJoinWorkerThread) {
        int i;
        int length;
        forkJoinWorkerThread.setDaemon(true);
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = this.m;
        if (uncaughtExceptionHandler != null) {
            forkJoinWorkerThread.setUncaughtExceptionHandler(uncaughtExceptionHandler);
        }
        d dVar = new d(this, forkJoinWorkerThread);
        int i2 = this.i & 65536;
        String str = this.k;
        if (str != null) {
            synchronized (str) {
                d[] dVarArr = this.j;
                int i3 = this.g - 1640531527;
                this.g = i3;
                i = 0;
                if (dVarArr != null && (length = dVarArr.length) > 1) {
                    int i4 = length - 1;
                    i = i3 & i4;
                    int i5 = ((i3 << 1) | 1) & i4;
                    int i6 = length >>> 1;
                    while (true) {
                        d dVar2 = dVarArr[i5];
                        if (dVar2 == null || dVar2.a == 1073741824) {
                            break;
                        }
                        i6--;
                        if (i6 == 0) {
                            i5 = length | 1;
                            break;
                        }
                        i5 = (i5 + 2) & i4;
                    }
                    int i7 = i2 | i5 | (i3 & 1073610752);
                    dVar.d = i7;
                    dVar.a = i7;
                    if (i5 < length) {
                        dVarArr[i5] = dVar;
                    } else {
                        int i8 = length << 1;
                        d[] dVarArr2 = new d[i8];
                        dVarArr2[i5] = dVar;
                        int i9 = i8 - 1;
                        while (i < length) {
                            d dVar3 = dVarArr[i];
                            if (dVar3 != null) {
                                dVarArr2[dVar3.d & i9 & 126] = dVar3;
                            }
                            int i10 = i + 1;
                            if (i10 >= length) {
                                break;
                            }
                            dVarArr2[i10] = dVarArr[i10];
                            i = i10 + 1;
                        }
                        this.j = dVarArr2;
                    }
                }
            }
            forkJoinWorkerThread.setName(str.concat(Integer.toString(i)));
        }
        return dVar;
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0040 A[LOOP:0: B:27:0x0040->B:28:0x0064, LOOP_START] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0080  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void a(java8.util.concurrent.ForkJoinWorkerThread r18, java.lang.Throwable r19) {
        /*
            r17 = this;
            r9 = r17
            r0 = r18
            r10 = 4294967295(0xffffffff, double:2.1219957905E-314)
            r1 = 0
            r12 = 0
            if (r0 == 0) goto L_0x003a
            java8.util.concurrent.ForkJoinPool$d r0 = r0.b
            if (r0 == 0) goto L_0x003b
            java.lang.String r2 = r9.k
            int r3 = r0.c
            long r3 = (long) r3
            long r3 = r3 & r10
            int r5 = r0.d
            r6 = 65535(0xffff, float:9.1834E-41)
            r5 = r5 & r6
            if (r2 == 0) goto L_0x0037
            monitor-enter(r2)
            java8.util.concurrent.ForkJoinPool$d[] r6 = r9.j     // Catch: all -> 0x0034
            if (r6 == 0) goto L_0x002d
            int r7 = r6.length     // Catch: all -> 0x0034
            if (r7 <= r5) goto L_0x002d
            r7 = r6[r5]     // Catch: all -> 0x0034
            if (r7 != r0) goto L_0x002d
            r6[r5] = r1     // Catch: all -> 0x0034
        L_0x002d:
            long r5 = r9.e     // Catch: all -> 0x0034
            long r5 = r5 + r3
            r9.e = r5     // Catch: all -> 0x0034
            monitor-exit(r2)     // Catch: all -> 0x0034
            goto L_0x0037
        L_0x0034:
            r0 = move-exception
            monitor-exit(r2)     // Catch: all -> 0x0034
            throw r0
        L_0x0037:
            int r1 = r0.a
            goto L_0x003c
        L_0x003a:
            r0 = r1
        L_0x003b:
            r1 = r12
        L_0x003c:
            r2 = 1073741824(0x40000000, float:2.0)
            if (r1 == r2) goto L_0x0066
        L_0x0040:
            sun.misc.Unsafe r1 = java8.util.concurrent.ForkJoinPool.q
            long r3 = java8.util.concurrent.ForkJoinPool.r
            long r5 = r9.d
            r7 = -281474976710656(0xffff000000000000, double:NaN)
            r13 = 281474976710656(0x1000000000000, double:1.390671161567E-309)
            long r13 = r5 - r13
            long r7 = r7 & r13
            r13 = 281470681743360(0xffff00000000, double:1.39064994160909E-309)
            r15 = 4294967296(0x100000000, double:2.121995791E-314)
            long r15 = r5 - r15
            long r13 = r13 & r15
            long r7 = r7 | r13
            long r13 = r5 & r10
            long r7 = r7 | r13
            r2 = r17
            boolean r1 = r1.compareAndSwapLong(r2, r3, r5, r7)
            if (r1 == 0) goto L_0x0040
        L_0x0066:
            if (r0 == 0) goto L_0x006b
            r0.i()
        L_0x006b:
            boolean r1 = r9.a(r12, r12)
            if (r1 != 0) goto L_0x007a
            if (r0 == 0) goto L_0x007a
            java8.util.concurrent.ForkJoinTask<?>[] r0 = r0.h
            if (r0 == 0) goto L_0x007a
            r17.a()
        L_0x007a:
            if (r19 != 0) goto L_0x0080
            java8.util.concurrent.ForkJoinTask.e()
            goto L_0x0083
        L_0x0080:
            java8.util.concurrent.ForkJoinTask.c(r19)
        L_0x0083:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: java8.util.concurrent.ForkJoinPool.a(java8.util.concurrent.ForkJoinWorkerThread, java.lang.Throwable):void");
    }

    final void a() {
        int i;
        d dVar;
        while (true) {
            long j = this.d;
            if (j < 0) {
                int i2 = (int) j;
                if (i2 != 0) {
                    d[] dVarArr = this.j;
                    if (dVarArr != null && dVarArr.length > (i = 65535 & i2) && (dVar = dVarArr[i]) != null) {
                        int i3 = i2 & Integer.MAX_VALUE;
                        int i4 = dVar.a;
                        long j2 = (dVar.b & 4294967295L) | ((-4294967296L) & (281474976710656L + j));
                        ForkJoinWorkerThread forkJoinWorkerThread = dVar.j;
                        if (i2 == i4 && q.compareAndSwapLong(this, r, j, j2)) {
                            dVar.a = i3;
                            if (dVar.e < 0) {
                                LockSupport.unpark(forkJoinWorkerThread);
                                return;
                            }
                            return;
                        }
                    } else {
                        return;
                    }
                } else if ((140737488355328L & j) != 0) {
                    a(j);
                    return;
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    private int d(d dVar) {
        int i;
        int length;
        boolean z;
        Thread.State state;
        long j = this.d;
        d[] dVarArr = this.j;
        short s2 = (short) (j >>> 32);
        if (s2 < 0) {
            i = 0;
        } else if (dVarArr == null || (length = dVarArr.length) <= 0 || dVar == null) {
            return 0;
        } else {
            int i2 = (int) j;
            if (i2 != 0) {
                d dVar2 = dVarArr[i2 & (length - 1)];
                int i3 = dVar.a;
                long j2 = (-4294967296L) & (i3 < 0 ? j + 281474976710656L : j);
                int i4 = i2 & Integer.MAX_VALUE;
                if (dVar2 == null) {
                    return 0;
                }
                int i5 = dVar2.a;
                ForkJoinWorkerThread forkJoinWorkerThread = dVar2.j;
                long j3 = (dVar2.b & 4294967295L) | j2;
                if (i5 != i2 || !q.compareAndSwapLong(this, r, j, j3)) {
                    return 0;
                }
                dVar2.a = i4;
                if (dVar2.e < 0) {
                    LockSupport.unpark(forkJoinWorkerThread);
                }
                return i3 < 0 ? -1 : 1;
            } else if (((int) (j >> 48)) - ((short) (this.h & 65535)) > 0) {
                return q.compareAndSwapLong(this, r, j, ((-281474976710656L) & (j - 281474976710656L)) | (281474976710655L & j)) ? 1 : 0;
            } else {
                int i6 = this.i & 65535;
                int i7 = i6 + s2;
                int i8 = i7;
                int i9 = 1;
                int i10 = 0;
                while (true) {
                    if (i9 >= length) {
                        z = false;
                        break;
                    }
                    d dVar3 = dVarArr[i9];
                    if (dVar3 != null) {
                        if (dVar3.e == 0) {
                            z = true;
                            break;
                        }
                        i8--;
                        ForkJoinWorkerThread forkJoinWorkerThread2 = dVar3.j;
                        if (forkJoinWorkerThread2 != null && ((state = forkJoinWorkerThread2.getState()) == Thread.State.BLOCKED || state == Thread.State.WAITING)) {
                            i10++;
                        }
                    }
                    i9 += 2;
                }
                if (z || i8 != 0 || this.d != j) {
                    return 0;
                }
                if (i7 >= 32767 || s2 >= (this.h >>> 16)) {
                    Predicate<? super ForkJoinPool> predicate = this.n;
                    if (predicate != null && predicate.test(this)) {
                        return -1;
                    }
                    if (i10 < i6) {
                        Thread.yield();
                        return 0;
                    }
                    throw new RejectedExecutionException("Thread limit exceeded replacing blocked worker");
                }
                i = 0;
            }
        }
        if (!q.compareAndSwapLong(this, r, j, ((4294967296L + j) & 281470681743360L) | ((-281470681743361L) & j)) || !g()) {
            return i;
        }
        return 1;
    }

    public final void a(d dVar) {
        int i;
        boolean z;
        long j;
        d dVar2;
        ForkJoinTask<?>[] forkJoinTaskArr;
        int length;
        dVar.d();
        int d2 = dVar.d ^ c.d();
        if (d2 == 0) {
            d2 = 1;
        }
        int i2 = 0;
        while (true) {
            d[] dVarArr = this.j;
            if (dVarArr != null) {
                int length2 = dVarArr.length;
                int i3 = length2 - 1;
                int i4 = length2;
                boolean z2 = false;
                while (true) {
                    if (i4 <= 0) {
                        i = d2;
                        break;
                    }
                    int i5 = d2 & i3;
                    if (i5 >= 0 && i5 < length2 && (dVar2 = dVarArr[i5]) != null) {
                        int i6 = dVar2.f;
                        if (i6 - dVar2.g < 0 && (forkJoinTaskArr = dVar2.h) != null && (length = forkJoinTaskArr.length) > 0) {
                            i2 = dVar2.d;
                            long j2 = t + (((length - 1) & i6) << u);
                            ForkJoinTask forkJoinTask = (ForkJoinTask) q.getObjectVolatile(forkJoinTaskArr, j2);
                            if (forkJoinTask != null) {
                                int i7 = i6 + 1;
                                if (i6 == dVar2.f && q.compareAndSwapObject(forkJoinTaskArr, j2, forkJoinTask, (Object) null)) {
                                    dVar2.f = i7;
                                    if (i7 - dVar2.g < 0 && i2 != i2) {
                                        a();
                                    }
                                    dVar.e = i2;
                                    forkJoinTask.d();
                                    if ((dVar.d & 65536) != 0) {
                                        dVar.b(1024);
                                    } else {
                                        dVar.a(1024);
                                    }
                                    ForkJoinWorkerThread forkJoinWorkerThread = dVar.j;
                                    dVar.c++;
                                    dVar.e = 0;
                                    if (forkJoinWorkerThread != null) {
                                        forkJoinWorkerThread.a();
                                    }
                                    d2 = d2;
                                    z2 = true;
                                    i4--;
                                }
                            }
                            i2 = i2;
                            d2 = d2;
                            z2 = true;
                            i4--;
                        }
                    }
                    i = d2;
                    if (z2) {
                        break;
                    }
                    d2 = i + 1;
                    i4--;
                }
                if (z2) {
                    int i8 = i ^ (i << 13);
                    int i9 = i8 ^ (i8 >>> 17);
                    d2 = i9 ^ (i9 << 5);
                    i2 = i2;
                } else {
                    int i10 = dVar.a;
                    if (i10 >= 0) {
                        int i11 = (i10 + 65536) | Integer.MIN_VALUE;
                        dVar.a = i11;
                        do {
                            j = this.d;
                            dVar.b = (int) j;
                        } while (!q.compareAndSwapLong(this, r, j, ((j - 281474976710656L) & (-4294967296L)) | (i11 & 4294967295L)));
                    } else {
                        int i12 = dVar.b;
                        dVar.e = -1073741824;
                        int i13 = 0;
                        while (dVar.a < 0) {
                            int i14 = this.i;
                            if (i14 >= 0) {
                                long j3 = this.d;
                                int i15 = (65535 & i14) + ((int) (j3 >> 48));
                                if (i15 > 0 || (i14 & 262144) == 0) {
                                    z = false;
                                } else {
                                    z = false;
                                    if (a(false, false)) {
                                        return;
                                    }
                                }
                                int i16 = i13 + 1;
                                if ((i16 & 1) == 0) {
                                    Thread.interrupted();
                                } else if (i15 > 0 || i12 == 0 || i10 != ((int) j3)) {
                                    LockSupport.park(this);
                                } else {
                                    long currentTimeMillis = this.f + System.currentTimeMillis();
                                    LockSupport.parkUntil(this, currentTimeMillis);
                                    if (this.d == j3 && currentTimeMillis - System.currentTimeMillis() <= 20) {
                                        if (q.compareAndSwapLong(this, r, j3, ((j3 - 4294967296L) & (-4294967296L)) | (i12 & 4294967295L))) {
                                            dVar.a = 1073741824;
                                            return;
                                        }
                                    }
                                }
                                i13 = i16;
                            } else {
                                return;
                            }
                        }
                        dVar.e = 0;
                    }
                    d2 = i;
                    i2 = 0;
                }
            } else {
                return;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00d2 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int a(java8.util.concurrent.ForkJoinPool.d r22, java8.util.concurrent.ForkJoinTask<?> r23, long r24) {
        /*
            Method dump skipped, instructions count: 211
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: java8.util.concurrent.ForkJoinPool.a(java8.util.concurrent.ForkJoinPool$d, java8.util.concurrent.ForkJoinTask, long):int");
    }

    public final void b(d dVar) {
        boolean z;
        boolean z2;
        char c2;
        d dVar2;
        ForkJoinTask<?>[] forkJoinTaskArr;
        int length;
        int i = dVar.e;
        int i2 = dVar.d & 65536;
        char c3 = 65535;
        int i3 = i;
        char c4 = 65535;
        while (true) {
            if (i2 != 0) {
                dVar.b(0);
            } else {
                dVar.a(0);
            }
            if (c4 == c3 && dVar.a >= 0) {
                c4 = 1;
            }
            int d2 = c.d();
            d[] dVarArr = this.j;
            if (dVarArr != null) {
                int length2 = dVarArr.length;
                int i4 = length2 - 1;
                int i5 = length2;
                z = true;
                while (true) {
                    if (i5 <= 0) {
                        i3 = i3;
                        z2 = true;
                        break;
                    }
                    int i6 = (d2 - i5) & i4;
                    if (i6 >= 0 && i6 < length2 && (dVar2 = dVarArr[i6]) != null) {
                        int i7 = dVar2.f;
                        if (i7 - dVar2.g < 0 && (forkJoinTaskArr = dVar2.h) != null && (length = forkJoinTaskArr.length) > 0) {
                            int i8 = dVar2.d;
                            if (c4 == 0) {
                                a(this, r, 281474976710656L);
                                c4 = 1;
                            }
                            long j = (((length - 1) & i7) << u) + t;
                            ForkJoinTask forkJoinTask = (ForkJoinTask) q.getObjectVolatile(forkJoinTaskArr, j);
                            if (forkJoinTask != null) {
                                int i9 = i7 + 1;
                                if (i7 == dVar2.f && q.compareAndSwapObject(forkJoinTaskArr, j, forkJoinTask, (Object) null)) {
                                    dVar2.f = i9;
                                    dVar.e = dVar2.d;
                                    forkJoinTask.d();
                                    dVar.e = i;
                                    i3 = i;
                                }
                            }
                            z2 = false;
                            z = false;
                        } else if ((dVar2.e & 1073741824) == 0) {
                            z = false;
                        }
                    }
                    i5--;
                }
            } else {
                i3 = i3;
                z2 = true;
                z = true;
            }
            if (z) {
                break;
            }
            if (z2) {
                int i10 = 1073741824;
                if (i3 != 1073741824) {
                    dVar.e = 1073741824;
                    c2 = 1;
                } else {
                    i10 = i3;
                    c2 = 1;
                }
                if (c4 == c2) {
                    a(this, r, -281474976710656L);
                    i3 = i10;
                    c4 = 0;
                } else {
                    i3 = i10;
                }
            } else {
                i3 = i3;
            }
            c3 = 65535;
        }
        if (c4 == 0) {
            a(this, r, 281474976710656L);
        }
        dVar.e = i;
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x003d, code lost:
        r14 = (((r11 - 1) & r10) << java8.util.concurrent.ForkJoinPool.u) + java8.util.concurrent.ForkJoinPool.t;
        r1 = (java8.util.concurrent.ForkJoinTask) java8.util.concurrent.ForkJoinPool.q.getObjectVolatile(r13, r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0052, code lost:
        if (r1 == null) goto L_0x0002;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0054, code lost:
        r2 = r10 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0058, code lost:
        if (r10 != r9.f) goto L_0x0002;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0064, code lost:
        if (java8.util.concurrent.ForkJoinPool.q.compareAndSwapObject(r13, r14, r1, (java.lang.Object) null) == false) goto L_0x0002;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0066, code lost:
        r9.f = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0068, code lost:
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0002, code lost:
        continue;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java8.util.concurrent.ForkJoinTask<?> a(boolean r19) {
        /*
            r18 = this;
            r0 = r18
        L_0x0002:
            int r1 = r0.i
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 != 0) goto L_0x0073
            java8.util.concurrent.ForkJoinPool$d[] r1 = r0.j
            if (r1 == 0) goto L_0x0073
            int r2 = r1.length
            if (r2 <= 0) goto L_0x0073
            int r2 = r2 + (-1)
            int r3 = java8.util.concurrent.c.d()
            int r4 = r3 >>> 16
            if (r19 == 0) goto L_0x0022
            r3 = r3 & (-2)
            r3 = r3 & r2
            r4 = r4 & (-2)
            r4 = r4 | 2
            goto L_0x0025
        L_0x0022:
            r3 = r3 & r2
            r4 = r4 | 1
        L_0x0025:
            r5 = 0
            r6 = r3
            r7 = r5
            r8 = r7
        L_0x0029:
            r9 = r1[r6]
            if (r9 == 0) goto L_0x0069
            int r10 = r9.f
            int r7 = r7 + r10
            int r11 = r9.g
            int r11 = r10 - r11
            if (r11 >= 0) goto L_0x0069
            java8.util.concurrent.ForkJoinTask<?>[] r13 = r9.h
            if (r13 == 0) goto L_0x0069
            int r11 = r13.length
            if (r11 <= 0) goto L_0x0069
            int r11 = r11 + (-1)
            r1 = r11 & r10
            long r1 = (long) r1
            int r3 = java8.util.concurrent.ForkJoinPool.u
            long r1 = r1 << r3
            int r3 = java8.util.concurrent.ForkJoinPool.t
            long r3 = (long) r3
            long r14 = r1 + r3
            sun.misc.Unsafe r1 = java8.util.concurrent.ForkJoinPool.q
            java.lang.Object r1 = r1.getObjectVolatile(r13, r14)
            java8.util.concurrent.ForkJoinTask r1 = (java8.util.concurrent.ForkJoinTask) r1
            if (r1 == 0) goto L_0x0002
            int r2 = r10 + 1
            int r3 = r9.f
            if (r10 != r3) goto L_0x0002
            sun.misc.Unsafe r12 = java8.util.concurrent.ForkJoinPool.q
            r17 = 0
            r16 = r1
            boolean r3 = r12.compareAndSwapObject(r13, r14, r16, r17)
            if (r3 == 0) goto L_0x0002
            r9.f = r2
            return r1
        L_0x0069:
            int r6 = r6 + r4
            r6 = r6 & r2
            if (r6 != r3) goto L_0x0029
            if (r8 != r7) goto L_0x0070
            goto L_0x0073
        L_0x0070:
            r8 = r7
            r7 = r5
            goto L_0x0029
        L_0x0073:
            r1 = 0
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: java8.util.concurrent.ForkJoinPool.a(boolean):java8.util.concurrent.ForkJoinTask");
    }

    public final ForkJoinTask<?> c(d dVar) {
        if (dVar != null) {
            ForkJoinTask<?> f = (dVar.d & 65536) != 0 ? dVar.f() : dVar.e();
            if (f != null) {
                return f;
            }
        }
        return a(false);
    }

    public final void a(ForkJoinTask<?> forkJoinTask) {
        int length;
        int i;
        int i2;
        int length2;
        int length3;
        int length4;
        int c2 = c.c();
        if (c2 == 0) {
            c.a();
            c2 = c.c();
        }
        while (true) {
            int i3 = this.i;
            d[] dVarArr = this.j;
            if ((i3 & 262144) != 0 || dVarArr == null || (length = dVarArr.length) <= 0) {
                break;
            }
            d dVar = dVarArr[(length - 1) & c2 & 126];
            int i4 = 0;
            i4 = 1;
            if (dVar == null) {
                String str = this.k;
                int i5 = (c2 | 1073741824) & (-65538);
                dVar = new d(this, null);
                dVar.d = i5;
                dVar.e = 1073741824;
                if (str != null) {
                    synchronized (str) {
                        d[] dVarArr2 = this.j;
                        if (dVarArr2 != null && (length4 = dVarArr2.length) > 0) {
                            int i6 = i5 & (length4 - 1) & 126;
                            if (dVarArr2[i6] == null) {
                                dVarArr2[i6] = dVar;
                                i2 = i4;
                                i = i2;
                            }
                        }
                        i2 = i4;
                        i = i2;
                    }
                } else {
                    i2 = i4;
                    i = i2;
                }
                dVar = dVar;
            } else if (dVar.j()) {
                int i7 = dVar.f;
                int i8 = dVar.g;
                ForkJoinTask<?>[] forkJoinTaskArr = dVar.h;
                if (forkJoinTaskArr != null && (length3 = forkJoinTaskArr.length) > 0) {
                    int i9 = length3 - 1;
                    int i10 = i7 - i8;
                    if (i9 + i10 > 0) {
                        forkJoinTaskArr[i9 & i8] = forkJoinTask;
                        dVar.g = i8 + 1;
                        if (i10 >= 0 || dVar.f - i8 >= -1) {
                            i = i4;
                            i2 = i4;
                        } else {
                            return;
                        }
                    }
                }
                i = i4;
                i2 = i4;
            } else {
                i2 = i4;
                i = i2;
            }
            if (i2 != 0) {
                if (i != 0) {
                    try {
                        dVar.d();
                        int i11 = dVar.g;
                        ForkJoinTask<?>[] forkJoinTaskArr2 = dVar.h;
                        if (forkJoinTaskArr2 != null && (length2 = forkJoinTaskArr2.length) > 0) {
                            forkJoinTaskArr2[(length2 - i4) & i11] = forkJoinTask;
                            dVar.g = i11 + i4;
                        }
                    } finally {
                        dVar.a = i4;
                    }
                }
                a();
                return;
            }
            c2 = c.a(c2);
        }
        throw new RejectedExecutionException();
    }

    private <T> ForkJoinTask<T> d(ForkJoinTask<T> forkJoinTask) {
        d dVar;
        Objects.requireNonNull(forkJoinTask);
        Thread currentThread = Thread.currentThread();
        if (currentThread instanceof ForkJoinWorkerThread) {
            ForkJoinWorkerThread forkJoinWorkerThread = (ForkJoinWorkerThread) currentThread;
            if (forkJoinWorkerThread.a == this && (dVar = forkJoinWorkerThread.b) != null) {
                dVar.a((ForkJoinTask<?>) forkJoinTask);
                return forkJoinTask;
            }
        }
        a((ForkJoinTask<?>) forkJoinTask);
        return forkJoinTask;
    }

    public static d b() {
        d[] dVarArr;
        int length;
        ForkJoinPool forkJoinPool = b;
        int c2 = c.c();
        if (forkJoinPool == null || (dVarArr = forkJoinPool.j) == null || (length = dVarArr.length) <= 0) {
            return null;
        }
        return dVarArr[c2 & (length - 1) & 126];
    }

    public final boolean b(ForkJoinTask<?> forkJoinTask) {
        int length;
        d dVar;
        int c2 = c.c();
        d[] dVarArr = this.j;
        return dVarArr != null && (length = dVarArr.length) > 0 && (dVar = dVarArr[(c2 & (length - 1)) & 126]) != null && dVar.d(forkJoinTask);
    }

    public final int a(CountedCompleter<?> countedCompleter, int i) {
        int length;
        d dVar;
        int c2 = c.c();
        d[] dVarArr = this.j;
        if (dVarArr == null || (length = dVarArr.length) <= 0 || (dVar = dVarArr[c2 & (length - 1) & 126]) == null) {
            return 0;
        }
        return dVar.b(countedCompleter, i);
    }

    public final int a(d dVar, CountedCompleter<?> countedCompleter, int i) {
        if (dVar == null) {
            return 0;
        }
        return dVar.a(countedCompleter, i);
    }

    public static int c() {
        ForkJoinWorkerThread forkJoinWorkerThread;
        ForkJoinPool forkJoinPool;
        d dVar;
        Thread currentThread = Thread.currentThread();
        int i = 0;
        if (!(currentThread instanceof ForkJoinWorkerThread) || (forkJoinPool = (forkJoinWorkerThread = (ForkJoinWorkerThread) currentThread).a) == null || (dVar = forkJoinWorkerThread.b) == null) {
            return 0;
        }
        int i2 = forkJoinPool.i & 65535;
        int i3 = ((int) (forkJoinPool.d >> 48)) + i2;
        int i4 = dVar.g - dVar.f;
        int i5 = i2 >>> 1;
        if (i3 <= i5) {
            int i6 = i5 >>> 1;
            if (i3 > i6) {
                i = 1;
            } else {
                int i7 = i6 >>> 1;
                i = i3 > i7 ? 2 : i3 > (i7 >>> 1) ? 4 : 8;
            }
        }
        return i4 - i;
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x001f, code lost:
        r0 = r16.i;
        r9 = 65535;
        r10 = 0;
        r13 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x002d, code lost:
        if ((r0 & Integer.MIN_VALUE) != 0) goto L_0x00ab;
     */
    /* JADX WARN: Code restructure failed: missing block: B:123:?, code lost:
        return true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x002f, code lost:
        if (r17 != false) goto L_0x0098;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0031, code lost:
        r2 = r16.d;
        r4 = r16.j;
        r5 = '0';
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x003c, code lost:
        if (((r0 & r9) + ((int) (r2 >> 48))) <= 0) goto L_0x003f;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x003f, code lost:
        if (r4 == null) goto L_0x007a;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0043, code lost:
        if (r0 >= r4.length) goto L_0x0078;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0045, code lost:
        r6 = r4[r0];
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0047, code lost:
        if (r6 == null) goto L_0x006e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0049, code lost:
        r14 = r6.e;
        r15 = r6.a;
        r9 = r6.d;
        r8 = r6.f;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0053, code lost:
        if (r8 != r6.g) goto L_0x006c;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0057, code lost:
        if ((r9 & 1) != r13) goto L_0x005e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0059, code lost:
        if (r14 >= 0) goto L_0x006c;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x005b, code lost:
        if (r15 < 0) goto L_0x005e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x005e, code lost:
        r2 = r2 + ((((r14 << r5) + (r15 << 32)) + (r8 << 16)) + r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x006c, code lost:
        r13 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x006e, code lost:
        r0 = r0 + 1;
        r5 = '0';
        r13 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0078, code lost:
        r13 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x007a, code lost:
        r13 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x007b, code lost:
        r0 = r16.i;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x007f, code lost:
        if ((r0 & Integer.MIN_VALUE) == 0) goto L_0x0083;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0081, code lost:
        r8 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0083, code lost:
        if (r13 == 0) goto L_0x0087;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0085, code lost:
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0087, code lost:
        r8 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x008a, code lost:
        if (r16.j != r4) goto L_0x0093;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x008e, code lost:
        if (r10 != r2) goto L_0x0092;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0090, code lost:
        r5 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0092, code lost:
        r10 = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0093, code lost:
        r9 = 65535;
        r13 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0098, code lost:
        r5 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x009b, code lost:
        if ((r5 & Integer.MIN_VALUE) != 0) goto L_0x001f;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x009d, code lost:
        java8.util.concurrent.ForkJoinPool.q.compareAndSwapInt(r16, java8.util.concurrent.ForkJoinPool.s, r5, r5 | Integer.MIN_VALUE);
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00b0, code lost:
        if ((r16.i & 524288) != 0) goto L_0x011c;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00b2, code lost:
        r2 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00b3, code lost:
        r4 = r16.d;
        r0 = r16.j;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00b7, code lost:
        if (r0 == null) goto L_0x00da;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00b9, code lost:
        r5 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x00bc, code lost:
        if (r4 >= r0.length) goto L_0x00d9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x00be, code lost:
        r9 = r0[r4];
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x00c0, code lost:
        if (r9 == null) goto L_0x00d5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x00c2, code lost:
        r13 = r9.j;
        r9.i();
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x00c7, code lost:
        if (r13 == null) goto L_0x00cc;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x00c9, code lost:
        r13.interrupt();
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x00d9, code lost:
        r4 = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x00da, code lost:
        r6 = r16.i;
        r8 = r6 & 524288;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x00de, code lost:
        if (r8 != 0) goto L_0x00ec;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x00e2, code lost:
        if (r16.j != r0) goto L_0x00ea;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x00e6, code lost:
        if (r2 != r4) goto L_0x00e9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x00e9, code lost:
        r2 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x00ea, code lost:
        r8 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x00ec, code lost:
        if (r8 == 0) goto L_0x00f0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x00ee, code lost:
        return true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x00fb, code lost:
        if (((r6 & 65535) + ((short) (r16.d >>> 32))) <= 0) goto L_0x00ff;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x00fd, code lost:
        return true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x010e, code lost:
        if (java8.util.concurrent.ForkJoinPool.q.compareAndSwapInt(r16, java8.util.concurrent.ForkJoinPool.s, r6, r6 | 524288) == false) goto L_0x011a;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x0110, code lost:
        monitor-enter(r16);
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x0111, code lost:
        notifyAll();
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x0114, code lost:
        monitor-exit(r16);
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x011a, code lost:
        r8 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x011c, code lost:
        return true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean a(boolean r17, boolean r18) {
        /*
            Method dump skipped, instructions count: 286
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: java8.util.concurrent.ForkJoinPool.a(boolean, boolean):boolean");
    }

    public ForkJoinPool() {
        this(Math.min(32767, Runtime.getRuntime().availableProcessors()), defaultForkJoinWorkerThreadFactory, null, false, 0, 32767, 1, null, 60000L, TimeUnit.MILLISECONDS);
    }

    public ForkJoinPool(int i) {
        this(i, defaultForkJoinWorkerThreadFactory, null, false, 0, 32767, 1, null, 60000L, TimeUnit.MILLISECONDS);
    }

    public ForkJoinPool(int i, ForkJoinWorkerThreadFactory forkJoinWorkerThreadFactory, Thread.UncaughtExceptionHandler uncaughtExceptionHandler, boolean z) {
        this(i, forkJoinWorkerThreadFactory, uncaughtExceptionHandler, z, 0, 32767, 1, null, 60000L, TimeUnit.MILLISECONDS);
    }

    public ForkJoinPool(int i, ForkJoinWorkerThreadFactory forkJoinWorkerThreadFactory, Thread.UncaughtExceptionHandler uncaughtExceptionHandler, boolean z, int i2, int i3, int i4, Predicate<? super ForkJoinPool> predicate, long j, TimeUnit timeUnit) {
        if (i <= 0 || i > 32767 || i3 < i || j <= 0) {
            throw new IllegalArgumentException();
        }
        Objects.requireNonNull(forkJoinWorkerThreadFactory);
        long max = Math.max(timeUnit.toMillis(j), 20L);
        long j2 = (((-Math.min(Math.max(i2, i), 32767)) << 32) & 281470681743360L) | (((-i) << 48) & (-281474976710656L));
        int i5 = (z ? 65536 : 0) | i;
        int min = ((Math.min(i3, 32767) - i) << 16) | ((Math.min(Math.max(i4, 0), 32767) - i) & 65535);
        int i6 = i > 1 ? i - 1 : 1;
        int i7 = i6 | (i6 >>> 1);
        int i8 = i7 | (i7 >>> 2);
        int i9 = i8 | (i8 >>> 4);
        int i10 = i9 | (i9 >>> 8);
        this.k = "ForkJoinPool-" + f() + "-worker-";
        this.j = new d[((i10 | (i10 >>> 16)) + 1) << 1];
        this.l = forkJoinWorkerThreadFactory;
        this.m = uncaughtExceptionHandler;
        this.n = predicate;
        this.f = max;
        this.h = min;
        this.i = i5;
        this.d = j2;
        e();
    }

    private static Object a(String str) throws Exception {
        String property = System.getProperty(str);
        if (property == null) {
            return null;
        }
        return ClassLoader.getSystemClassLoader().loadClass(property).getConstructor(new Class[0]).newInstance(new Object[0]);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0026  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0047  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x006b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private ForkJoinPool(byte r11) {
        /*
            r10 = this;
            r10.<init>()
            r11 = 0
            r0 = -1
            java.lang.String r1 = "java.util.concurrent.ForkJoinPool.common.parallelism"
            java.lang.String r1 = java.lang.System.getProperty(r1)     // Catch: Exception -> 0x0022
            if (r1 == 0) goto L_0x0011
            int r0 = java.lang.Integer.parseInt(r1)     // Catch: Exception -> 0x0022
        L_0x0011:
            java.lang.String r1 = "java.util.concurrent.ForkJoinPool.common.threadFactory"
            java.lang.Object r1 = a(r1)     // Catch: Exception -> 0x0022
            java8.util.concurrent.ForkJoinPool$ForkJoinWorkerThreadFactory r1 = (java8.util.concurrent.ForkJoinPool.ForkJoinWorkerThreadFactory) r1     // Catch: Exception -> 0x0022
            java.lang.String r2 = "java.util.concurrent.ForkJoinPool.common.exceptionHandler"
            java.lang.Object r2 = a(r2)     // Catch: Exception -> 0x0023
            java.lang.Thread$UncaughtExceptionHandler r2 = (java.lang.Thread.UncaughtExceptionHandler) r2     // Catch: Exception -> 0x0023
            goto L_0x0024
        L_0x0022:
            r1 = r11
        L_0x0023:
            r2 = r11
        L_0x0024:
            if (r1 != 0) goto L_0x0034
            java.lang.SecurityManager r1 = java.lang.System.getSecurityManager()
            if (r1 != 0) goto L_0x002f
            java8.util.concurrent.ForkJoinPool$ForkJoinWorkerThreadFactory r1 = java8.util.concurrent.ForkJoinPool.defaultForkJoinWorkerThreadFactory
            goto L_0x0034
        L_0x002f:
            java8.util.concurrent.ForkJoinPool$b r1 = new java8.util.concurrent.ForkJoinPool$b
            r1.<init>()
        L_0x0034:
            r3 = 1
            if (r0 >= 0) goto L_0x0043
            java.lang.Runtime r0 = java.lang.Runtime.getRuntime()
            int r0 = r0.availableProcessors()
            int r0 = r0 - r3
            if (r0 > 0) goto L_0x0043
            r0 = r3
        L_0x0043:
            r4 = 32767(0x7fff, float:4.5916E-41)
            if (r0 <= r4) goto L_0x0048
            r0 = r4
        L_0x0048:
            int r4 = -r0
            long r4 = (long) r4
            r6 = 32
            long r6 = r4 << r6
            r8 = 281470681743360(0xffff00000000, double:1.39064994160909E-309)
            long r6 = r6 & r8
            r8 = 48
            long r4 = r4 << r8
            r8 = -281474976710656(0xffff000000000000, double:NaN)
            long r4 = r4 & r8
            long r4 = r4 | r6
            int r6 = 1 - r0
            r7 = 65535(0xffff, float:9.1834E-41)
            r6 = r6 & r7
            int r7 = java8.util.concurrent.ForkJoinPool.o
            int r7 = r7 << 16
            r6 = r6 | r7
            if (r0 <= r3) goto L_0x006b
            int r7 = r0 + (-1)
            goto L_0x006c
        L_0x006b:
            r7 = r3
        L_0x006c:
            int r8 = r7 >>> 1
            r7 = r7 | r8
            int r8 = r7 >>> 2
            r7 = r7 | r8
            int r8 = r7 >>> 4
            r7 = r7 | r8
            int r8 = r7 >>> 8
            r7 = r7 | r8
            int r8 = r7 >>> 16
            r7 = r7 | r8
            int r7 = r7 + r3
            int r3 = r7 << 1
            java.lang.String r7 = "ForkJoinPool.commonPool-worker-"
            r10.k = r7
            java8.util.concurrent.ForkJoinPool$d[] r3 = new java8.util.concurrent.ForkJoinPool.d[r3]
            r10.j = r3
            r10.l = r1
            r10.m = r2
            r10.n = r11
            r1 = 60000(0xea60, double:2.9644E-319)
            r10.f = r1
            r10.h = r6
            r10.i = r0
            r10.d = r4
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: java8.util.concurrent.ForkJoinPool.<init>(byte):void");
    }

    public static ForkJoinPool commonPool() {
        return b;
    }

    public <T> T invoke(ForkJoinTask<T> forkJoinTask) {
        d((ForkJoinTask) Objects.requireNonNull(forkJoinTask));
        return forkJoinTask.join();
    }

    public void execute(ForkJoinTask<?> forkJoinTask) {
        d(forkJoinTask);
    }

    @Override // java.util.concurrent.Executor
    public void execute(Runnable runnable) {
        ForkJoinTask forkJoinTask;
        Objects.requireNonNull(runnable);
        if (runnable instanceof ForkJoinTask) {
            forkJoinTask = (ForkJoinTask) runnable;
        } else {
            forkJoinTask = new ForkJoinTask.e(runnable);
        }
        d(forkJoinTask);
    }

    public <T> ForkJoinTask<T> submit(ForkJoinTask<T> forkJoinTask) {
        return d(forkJoinTask);
    }

    @Override // java.util.concurrent.AbstractExecutorService, java.util.concurrent.ExecutorService
    public <T> ForkJoinTask<T> submit(Callable<T> callable) {
        return d(new ForkJoinTask.a(callable));
    }

    @Override // java.util.concurrent.AbstractExecutorService, java.util.concurrent.ExecutorService
    public <T> ForkJoinTask<T> submit(Runnable runnable, T t2) {
        return d(new ForkJoinTask.b(runnable, t2));
    }

    @Override // java.util.concurrent.AbstractExecutorService, java.util.concurrent.ExecutorService
    public ForkJoinTask<?> submit(Runnable runnable) {
        Objects.requireNonNull(runnable);
        return d((ForkJoinTask) (runnable instanceof ForkJoinTask ? (ForkJoinTask) runnable : new ForkJoinTask.c(runnable)));
    }

    @Override // java.util.concurrent.AbstractExecutorService, java.util.concurrent.ExecutorService
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> collection) {
        ArrayList arrayList = new ArrayList(collection.size());
        try {
            Iterator<? extends Callable<T>> it = collection.iterator();
            while (it.hasNext()) {
                ForkJoinTask.a aVar = new ForkJoinTask.a((Callable) it.next());
                arrayList.add(aVar);
                d(aVar);
            }
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                ((ForkJoinTask) arrayList.get(i)).quietlyJoin();
            }
            return arrayList;
        } catch (Throwable th) {
            int size2 = arrayList.size();
            for (int i2 = 0; i2 < size2; i2++) {
                ((Future) arrayList.get(i2)).cancel(false);
            }
            throw th;
        }
    }

    public ForkJoinWorkerThreadFactory getFactory() {
        return this.l;
    }

    public Thread.UncaughtExceptionHandler getUncaughtExceptionHandler() {
        return this.m;
    }

    public int getParallelism() {
        int i = this.i & 65535;
        if (i > 0) {
            return i;
        }
        return 1;
    }

    public static int getCommonPoolParallelism() {
        return c;
    }

    public int getPoolSize() {
        return (this.i & 65535) + ((short) (this.d >>> 32));
    }

    public boolean getAsyncMode() {
        return (this.i & 65536) != 0;
    }

    public int getRunningThreadCount() {
        d[] dVarArr = this.j;
        int i = 0;
        if (dVarArr != null) {
            for (int i2 = 1; i2 < dVarArr.length; i2 += 2) {
                d dVar = dVarArr[i2];
                if (dVar != null && dVar.k()) {
                    i++;
                }
            }
        }
        return i;
    }

    public int getActiveThreadCount() {
        int i = (this.i & 65535) + ((int) (this.d >> 48));
        if (i <= 0) {
            return 0;
        }
        return i;
    }

    public boolean isQuiescent() {
        while (true) {
            long j = this.d;
            int i = this.i;
            int i2 = 65535 & i;
            int i3 = ((short) (j >>> 32)) + i2;
            int i4 = i2 + ((int) (j >> 48));
            if ((i & (-2146959360)) != 0) {
                return true;
            }
            if (i4 > 0) {
                return false;
            }
            d[] dVarArr = this.j;
            if (dVarArr != null) {
                int i5 = i3;
                for (int i6 = 1; i6 < dVarArr.length; i6 += 2) {
                    d dVar = dVarArr[i6];
                    if (dVar != null) {
                        if ((dVar.e & 1073741824) == 0) {
                            return false;
                        }
                        i5--;
                    }
                }
                i3 = i5;
            }
            if (i3 == 0 && this.d == j) {
                return true;
            }
        }
    }

    public long getStealCount() {
        long j = this.e;
        d[] dVarArr = this.j;
        if (dVarArr != null) {
            for (int i = 1; i < dVarArr.length; i += 2) {
                d dVar = dVarArr[i];
                if (dVar != null) {
                    j += dVar.c & 4294967295L;
                }
            }
        }
        return j;
    }

    public long getQueuedTaskCount() {
        d[] dVarArr = this.j;
        long j = 0;
        if (dVarArr != null) {
            for (int i = 1; i < dVarArr.length; i += 2) {
                d dVar = dVarArr[i];
                if (dVar != null) {
                    j += dVar.b();
                }
            }
        }
        return j;
    }

    public int getQueuedSubmissionCount() {
        d[] dVarArr = this.j;
        if (dVarArr == null) {
            return 0;
        }
        int i = 0;
        for (int i2 = 0; i2 < dVarArr.length; i2 += 2) {
            d dVar = dVarArr[i2];
            if (dVar != null) {
                i += dVar.b();
            }
        }
        return i;
    }

    public boolean hasQueuedSubmissions() {
        d[] dVarArr = this.j;
        if (dVarArr != null) {
            for (int i = 0; i < dVarArr.length; i += 2) {
                d dVar = dVarArr[i];
                if (!(dVar == null || dVar.c())) {
                    return true;
                }
            }
        }
        return false;
    }

    public ForkJoinTask<?> pollSubmission() {
        return a(true);
    }

    protected int drainTasksTo(Collection<? super ForkJoinTask<?>> collection) {
        d[] dVarArr = this.j;
        if (dVarArr == null) {
            return 0;
        }
        int i = 0;
        for (d dVar : dVarArr) {
            if (dVar != null) {
                while (true) {
                    ForkJoinTask<?> f = dVar.f();
                    if (f != null) {
                        collection.add(f);
                        i++;
                    }
                }
            }
        }
        return i;
    }

    public String toString() {
        long j;
        long j2;
        int i;
        long j3 = this.e;
        d[] dVarArr = this.j;
        long j4 = 0;
        int i2 = 0;
        if (dVarArr != null) {
            j = j3;
            j2 = 0;
            i = 0;
            for (int i3 = 0; i3 < dVarArr.length; i3++) {
                d dVar = dVarArr[i3];
                if (dVar != null) {
                    int b2 = dVar.b();
                    if ((i3 & 1) == 0) {
                        j2 += b2;
                    } else {
                        j4 += b2;
                        j += dVar.c & 4294967295L;
                        if (dVar.k()) {
                            i++;
                        }
                    }
                }
            }
        } else {
            j = j3;
            j2 = 0;
            i = 0;
        }
        int i4 = this.i;
        int i5 = 65535 & i4;
        long j5 = this.d;
        int i6 = ((short) (j5 >>> 32)) + i5;
        int i7 = ((int) (j5 >> 48)) + i5;
        if (i7 >= 0) {
            i2 = i7;
        }
        return super.toString() + "[" + ((524288 & i4) != 0 ? "Terminated" : (Integer.MIN_VALUE & i4) != 0 ? "Terminating" : (i4 & 262144) != 0 ? "Shutting down" : "Running") + ", parallelism = " + i5 + ", size = " + i6 + ", active = " + i2 + ", running = " + i + ", steals = " + j + ", tasks = " + j4 + ", submissions = " + j2 + "]";
    }

    @Override // java.util.concurrent.ExecutorService
    public void shutdown() {
        e();
        a(false, true);
    }

    @Override // java.util.concurrent.ExecutorService
    public List<Runnable> shutdownNow() {
        e();
        a(true, true);
        return Collections.emptyList();
    }

    @Override // java.util.concurrent.ExecutorService
    public boolean isTerminated() {
        return (this.i & 524288) != 0;
    }

    public boolean isTerminating() {
        int i = this.i;
        return (Integer.MIN_VALUE & i) != 0 && (i & 524288) == 0;
    }

    @Override // java.util.concurrent.ExecutorService
    public boolean isShutdown() {
        return (this.i & 262144) != 0;
    }

    @Override // java.util.concurrent.ExecutorService
    public boolean awaitTermination(long j, TimeUnit timeUnit) throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        } else if (this == b) {
            awaitQuiescence(j, timeUnit);
            return false;
        } else {
            long nanos = timeUnit.toNanos(j);
            if (isTerminated()) {
                return true;
            }
            if (nanos <= 0) {
                return false;
            }
            long nanoTime = System.nanoTime() + nanos;
            synchronized (this) {
                while (!isTerminated()) {
                    if (nanos <= 0) {
                        return false;
                    }
                    long millis = TimeUnit.NANOSECONDS.toMillis(nanos);
                    if (millis <= 0) {
                        millis = 1;
                    }
                    wait(millis);
                    nanos = nanoTime - System.nanoTime();
                }
                return true;
            }
        }
    }

    public boolean awaitQuiescence(long j, TimeUnit timeUnit) {
        long nanos = timeUnit.toNanos(j);
        Thread currentThread = Thread.currentThread();
        if (currentThread instanceof ForkJoinWorkerThread) {
            ForkJoinWorkerThread forkJoinWorkerThread = (ForkJoinWorkerThread) currentThread;
            if (forkJoinWorkerThread.a == this) {
                b(forkJoinWorkerThread.b);
                return true;
            }
        }
        long nanoTime = System.nanoTime();
        while (true) {
            ForkJoinTask<?> a2 = a(false);
            if (a2 != null) {
                a2.d();
            } else if (isQuiescent()) {
                return true;
            } else {
                if (System.nanoTime() - nanoTime > nanos) {
                    return false;
                }
                Thread.yield();
            }
        }
    }

    public static void d() {
        b.awaitQuiescence(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x001e, code lost:
        r3 = 281474976710656L;
        r5 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0026, code lost:
        if (r9.isReleasable() != false) goto L_0x002e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x002c, code lost:
        if (r9.block() == false) goto L_0x001e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0038, code lost:
        r9 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0039, code lost:
        r7 = java8.util.concurrent.ForkJoinPool.r;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x003b, code lost:
        if (r2 <= 0) goto L_0x003e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x003e, code lost:
        r3 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x003f, code lost:
        a(r1, r7, r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0042, code lost:
        throw r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x004f, code lost:
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void managedBlock(java8.util.concurrent.ForkJoinPool.ManagedBlocker r9) throws java.lang.InterruptedException {
        /*
            java.lang.Thread r0 = java.lang.Thread.currentThread()
            boolean r1 = r0 instanceof java8.util.concurrent.ForkJoinWorkerThread
            if (r1 == 0) goto L_0x0043
            java8.util.concurrent.ForkJoinWorkerThread r0 = (java8.util.concurrent.ForkJoinWorkerThread) r0
            java8.util.concurrent.ForkJoinPool r1 = r0.a
            if (r1 == 0) goto L_0x0043
            java8.util.concurrent.ForkJoinPool$d r0 = r0.b
            if (r0 == 0) goto L_0x0043
        L_0x0012:
            boolean r2 = r9.isReleasable()
            if (r2 != 0) goto L_0x004f
            int r2 = r1.d(r0)
            if (r2 == 0) goto L_0x0012
        L_0x001e:
            r3 = 281474976710656(0x1000000000000, double:1.390671161567E-309)
            r5 = 0
            boolean r0 = r9.isReleasable()     // Catch: all -> 0x0038
            if (r0 != 0) goto L_0x002e
            boolean r0 = r9.block()     // Catch: all -> 0x0038
            if (r0 == 0) goto L_0x001e
        L_0x002e:
            long r7 = java8.util.concurrent.ForkJoinPool.r
            if (r2 <= 0) goto L_0x0033
            goto L_0x0034
        L_0x0033:
            r3 = r5
        L_0x0034:
            a(r1, r7, r3)
            goto L_0x004f
        L_0x0038:
            r9 = move-exception
            long r7 = java8.util.concurrent.ForkJoinPool.r
            if (r2 <= 0) goto L_0x003e
            goto L_0x003f
        L_0x003e:
            r3 = r5
        L_0x003f:
            a(r1, r7, r3)
            throw r9
        L_0x0043:
            boolean r0 = r9.isReleasable()
            if (r0 != 0) goto L_0x004f
            boolean r0 = r9.block()
            if (r0 == 0) goto L_0x0043
        L_0x004f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: java8.util.concurrent.ForkJoinPool.managedBlock(java8.util.concurrent.ForkJoinPool$ManagedBlocker):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0031 A[LOOP:0: B:19:0x0031->B:48:0x0031, LOOP_START] */
    /* JADX WARN: Removed duplicated region for block: B:54:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void a(java.util.concurrent.Executor r10, java8.util.concurrent.ForkJoinPool.ManagedBlocker r11) {
        /*
            if (r11 == 0) goto L_0x007e
            boolean r0 = r10 instanceof java8.util.concurrent.ForkJoinPool
            if (r0 == 0) goto L_0x007e
            java8.util.concurrent.ForkJoinPool r10 = (java8.util.concurrent.ForkJoinPool) r10
            java.lang.Thread r0 = java.lang.Thread.currentThread()
            boolean r1 = r0 instanceof java8.util.concurrent.ForkJoinWorkerThread
            if (r1 == 0) goto L_0x0019
            java8.util.concurrent.ForkJoinWorkerThread r0 = (java8.util.concurrent.ForkJoinWorkerThread) r0
            java8.util.concurrent.ForkJoinPool r1 = r0.a
            if (r1 != r10) goto L_0x0019
            java8.util.concurrent.ForkJoinPool$d r10 = r0.b
            goto L_0x002f
        L_0x0019:
            int r0 = java8.util.concurrent.c.c()
            if (r0 == 0) goto L_0x002e
            java8.util.concurrent.ForkJoinPool$d[] r10 = r10.j
            if (r10 == 0) goto L_0x002e
            int r1 = r10.length
            if (r1 <= 0) goto L_0x002e
            int r1 = r1 + (-1)
            r0 = r0 & r1
            r0 = r0 & 126(0x7e, float:1.77E-43)
            r10 = r10[r0]
            goto L_0x002f
        L_0x002e:
            r10 = 0
        L_0x002f:
            if (r10 == 0) goto L_0x007e
        L_0x0031:
            int r0 = r10.f
            int r1 = r10.g
            java8.util.concurrent.ForkJoinTask<?>[] r3 = r10.h
            if (r3 == 0) goto L_0x007e
            int r1 = r0 - r1
            if (r1 >= 0) goto L_0x007e
            int r2 = r3.length
            if (r2 <= 0) goto L_0x007e
            int r2 = r2 + (-1)
            r2 = r2 & r0
            long r4 = (long) r2
            int r2 = java8.util.concurrent.ForkJoinPool.u
            long r4 = r4 << r2
            int r2 = java8.util.concurrent.ForkJoinPool.t
            long r6 = (long) r2
            long r4 = r4 + r6
            sun.misc.Unsafe r2 = java8.util.concurrent.ForkJoinPool.q
            java.lang.Object r2 = r2.getObjectVolatile(r3, r4)
            r8 = r2
            java8.util.concurrent.ForkJoinTask r8 = (java8.util.concurrent.ForkJoinTask) r8
            boolean r2 = r11.isReleasable()
            if (r2 == 0) goto L_0x005b
            goto L_0x007e
        L_0x005b:
            int r9 = r0 + 1
            int r2 = r10.f
            if (r0 != r2) goto L_0x0031
            if (r8 != 0) goto L_0x0067
            r0 = -1
            if (r1 != r0) goto L_0x0031
            goto L_0x007e
        L_0x0067:
            boolean r0 = c(r8)
            if (r0 != 0) goto L_0x006e
            goto L_0x007e
        L_0x006e:
            sun.misc.Unsafe r2 = java8.util.concurrent.ForkJoinPool.q
            r7 = 0
            r6 = r8
            boolean r0 = r2.compareAndSwapObject(r3, r4, r6, r7)
            if (r0 == 0) goto L_0x0031
            r10.f = r9
            r8.d()
            goto L_0x0031
        L_0x007e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: java8.util.concurrent.ForkJoinPool.a(java.util.concurrent.Executor, java8.util.concurrent.ForkJoinPool$ManagedBlocker):void");
    }

    static boolean c(ForkJoinTask<?> forkJoinTask) {
        Class<?> cls;
        if (forkJoinTask == null || (cls = v) == null) {
            return false;
        }
        return cls.isAssignableFrom(forkJoinTask.getClass());
    }

    @Override // java.util.concurrent.AbstractExecutorService
    protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T t2) {
        return new ForkJoinTask.b(runnable, t2);
    }

    @Override // java.util.concurrent.AbstractExecutorService
    protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        return new ForkJoinTask.a(callable);
    }

    /* JADX WARN: Type inference failed for: r1v10, types: [java.lang.Class<?>, java8.util.concurrent.ForkJoinPool$1] */
    static {
        try {
            r = q.objectFieldOffset(ForkJoinPool.class.getDeclaredField("d"));
            s = q.objectFieldOffset(ForkJoinPool.class.getDeclaredField(ai.aA));
            t = q.arrayBaseOffset(ForkJoinTask[].class);
            int arrayIndexScale = q.arrayIndexScale(ForkJoinTask[].class);
            if (((arrayIndexScale - 1) & arrayIndexScale) == 0) {
                u = 31 - Integer.numberOfLeadingZeros(arrayIndexScale);
                int i = 256;
                try {
                    String property = System.getProperty("java.util.concurrent.ForkJoinPool.common.maximumSpares");
                    if (property != null) {
                        i = Integer.parseInt(property);
                    }
                } catch (Exception unused) {
                }
                o = i;
                Class<?> cls = 0;
                defaultForkJoinWorkerThreadFactory = new a();
                a = new RuntimePermission("modifyThread");
                b = (ForkJoinPool) AccessController.doPrivileged(new PrivilegedAction<ForkJoinPool>() { // from class: java8.util.concurrent.ForkJoinPool.1
                    /* renamed from: a */
                    public ForkJoinPool run() {
                        return new ForkJoinPool((byte) 0);
                    }
                });
                c = Math.max(b.i & 65535, 1);
                try {
                    cls = Class.forName("java8.util.concurrent.CompletableFuture$AsynchronousCompletionTask");
                } catch (Exception unused2) {
                } finally {
                    v = cls;
                }
            } else {
                throw new ExceptionInInitializerError("array index scale not a power of two");
            }
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static final class b implements ForkJoinWorkerThreadFactory {
        private static final AccessControlContext a = ForkJoinPool.a(ForkJoinPool.a, new RuntimePermission("enableContextClassLoaderOverride"), new RuntimePermission("modifyThreadGroup"), new RuntimePermission("getClassLoader"), new RuntimePermission("setContextClassLoader"));

        private b() {
        }

        @Override // java8.util.concurrent.ForkJoinPool.ForkJoinWorkerThreadFactory
        public final ForkJoinWorkerThread newThread(final ForkJoinPool forkJoinPool) {
            return (ForkJoinWorkerThread) AccessController.doPrivileged(new PrivilegedAction<ForkJoinWorkerThread>() { // from class: java8.util.concurrent.ForkJoinPool.b.1
                /* renamed from: a */
                public ForkJoinWorkerThread run() {
                    return new ForkJoinWorkerThread.a(forkJoinPool);
                }
            }, a);
        }
    }

    /* loaded from: classes5.dex */
    public static final class c {
        private static final b a = new b();
        private static final Unsafe b = d.a;
        private static final long c;

        static {
            try {
                c = b.objectFieldOffset(b.class.getDeclaredField(ai.at));
            } catch (Exception e) {
                throw new ExceptionInInitializerError(e);
            }
        }

        static void a() {
            b.putOrderedInt(a, c, 0);
        }

        static void b() {
            b.putIntVolatile(a, c, 0);
        }
    }
}
