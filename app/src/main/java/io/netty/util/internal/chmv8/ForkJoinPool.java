package io.netty.util.internal.chmv8;

import io.netty.util.internal.ThreadLocalRandom;
import io.netty.util.internal.chmv8.ForkJoinTask;
import io.realm.BuildConfig;
import java.lang.Thread;
import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
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
import sun.misc.Unsafe;

/* loaded from: classes4.dex */
public class ForkJoinPool extends AbstractExecutorService {
    static final ThreadLocal<int[]> a;
    static final ForkJoinPool b;
    static final int c;
    public static final ForkJoinWorkerThreadFactory defaultForkJoinWorkerThreadFactory;
    private static final RuntimePermission n;
    private static int o;
    private static final Unsafe p;
    private static final long q;
    private static final long r;
    private static final int s;
    private static final int t;
    private static final long u;
    private static final long v;
    private static final long w;
    private static final long x;
    private static final long y;
    volatile long d;
    volatile long e;
    volatile int f;
    volatile int g;
    final short h;
    final short i;
    c[] j;
    final ForkJoinWorkerThreadFactory k;
    final Thread.UncaughtExceptionHandler l;
    final String m;

    /* loaded from: classes4.dex */
    public interface ForkJoinWorkerThreadFactory {
        ForkJoinWorkerThread newThread(ForkJoinPool forkJoinPool);
    }

    /* loaded from: classes4.dex */
    public interface ManagedBlocker {
        boolean block() throws InterruptedException;

        boolean isReleasable();
    }

    private static void g() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(n);
        }
    }

    /* loaded from: classes4.dex */
    static final class a implements ForkJoinWorkerThreadFactory {
        a() {
        }

        @Override // io.netty.util.internal.chmv8.ForkJoinPool.ForkJoinWorkerThreadFactory
        public final ForkJoinWorkerThread newThread(ForkJoinPool forkJoinPool) {
            return new ForkJoinWorkerThread(forkJoinPool);
        }
    }

    /* loaded from: classes4.dex */
    public static final class b extends ForkJoinTask<Void> {
        private static final long serialVersionUID = -7721805057305804111L;

        /* renamed from: a */
        public final Void getRawResult() {
            return null;
        }

        /* renamed from: a */
        public final void setRawResult(Void r1) {
        }

        @Override // io.netty.util.internal.chmv8.ForkJoinTask
        public final boolean exec() {
            return true;
        }

        b() {
            this.status = -268435456;
        }
    }

    /* loaded from: classes4.dex */
    public static final class c {
        private static final Unsafe p;
        private static final long q;
        private static final long r;
        private static final int s;
        private static final int t;
        volatile int a;
        int b;
        int c;
        int d;
        short e;
        final short f;
        volatile int g;
        ForkJoinTask<?>[] j;
        final ForkJoinPool k;
        final ForkJoinWorkerThread l;
        volatile Thread m;
        volatile ForkJoinTask<?> n;
        ForkJoinTask<?> o;
        int i = 4096;
        volatile int h = 4096;

        c(ForkJoinPool forkJoinPool, ForkJoinWorkerThread forkJoinWorkerThread, int i, int i2) {
            this.k = forkJoinPool;
            this.l = forkJoinWorkerThread;
            this.f = (short) i;
            this.d = i2;
        }

        public final int a() {
            int i = this.h - this.i;
            if (i >= 0) {
                return 0;
            }
            return -i;
        }

        final boolean b() {
            ForkJoinTask<?>[] forkJoinTaskArr;
            int length;
            int i = this.h;
            int i2 = this.i;
            int i3 = i - i2;
            if (i3 < 0) {
                return i3 == -1 && ((forkJoinTaskArr = this.j) == null || (length = forkJoinTaskArr.length - 1) < 0 || p.getObject(forkJoinTaskArr, ((long) (((i2 - 1) & length) << t)) + ((long) s)) == null);
            }
            return true;
        }

        public final void a(ForkJoinTask<?> forkJoinTask) {
            int i = this.i;
            ForkJoinTask<?>[] forkJoinTaskArr = this.j;
            if (forkJoinTaskArr != null) {
                int length = forkJoinTaskArr.length - 1;
                p.putOrderedObject(forkJoinTaskArr, ((length & i) << t) + s, forkJoinTask);
                int i2 = i + 1;
                this.i = i2;
                int i3 = i2 - this.h;
                if (i3 <= 2) {
                    ForkJoinPool forkJoinPool = this.k;
                    forkJoinPool.a(forkJoinPool.j, this);
                } else if (i3 >= length) {
                    c();
                }
            }
        }

        final ForkJoinTask<?>[] c() {
            int length;
            ForkJoinTask<?>[] forkJoinTaskArr = this.j;
            int length2 = forkJoinTaskArr != null ? forkJoinTaskArr.length << 1 : 8192;
            if (length2 <= 67108864) {
                ForkJoinTask<?>[] forkJoinTaskArr2 = new ForkJoinTask[length2];
                this.j = forkJoinTaskArr2;
                if (forkJoinTaskArr != null && forkJoinTaskArr.length - 1 >= 0) {
                    int i = this.i;
                    int i2 = this.h;
                    if (i - i2 > 0) {
                        int i3 = length2 - 1;
                        int i4 = i2;
                        do {
                            int i5 = t;
                            int i6 = s;
                            int i7 = ((i4 & i3) << i5) + i6;
                            long j = ((i4 & length) << i5) + i6;
                            ForkJoinTask forkJoinTask = (ForkJoinTask) p.getObjectVolatile(forkJoinTaskArr, j);
                            if (forkJoinTask != null && p.compareAndSwapObject(forkJoinTaskArr, j, forkJoinTask, (Object) null)) {
                                p.putObjectVolatile(forkJoinTaskArr2, i7, forkJoinTask);
                            }
                            i4++;
                        } while (i4 != i);
                    }
                }
                return forkJoinTaskArr2;
            }
            throw new RejectedExecutionException("Queue capacity exceeded");
        }

        final ForkJoinTask<?> d() {
            int length;
            int i;
            long j;
            ForkJoinTask<?> forkJoinTask;
            ForkJoinTask<?>[] forkJoinTaskArr = this.j;
            if (forkJoinTaskArr == null || forkJoinTaskArr.length - 1 < 0) {
                return null;
            }
            do {
                i = this.i - 1;
                if (i - this.h < 0) {
                    return null;
                }
                j = ((length & i) << t) + s;
                forkJoinTask = (ForkJoinTask) p.getObject(forkJoinTaskArr, j);
                if (forkJoinTask == null) {
                    return null;
                }
            } while (!p.compareAndSwapObject(forkJoinTaskArr, j, forkJoinTask, (Object) null));
            this.i = i;
            return forkJoinTask;
        }

        final ForkJoinTask<?> a(int i) {
            ForkJoinTask<?>[] forkJoinTaskArr = this.j;
            if (forkJoinTaskArr == null) {
                return null;
            }
            long length = (((forkJoinTaskArr.length - 1) & i) << t) + s;
            ForkJoinTask<?> forkJoinTask = (ForkJoinTask) p.getObjectVolatile(forkJoinTaskArr, length);
            if (forkJoinTask == null || this.h != i || !p.compareAndSwapObject(forkJoinTaskArr, length, forkJoinTask, (Object) null)) {
                return null;
            }
            p.putOrderedInt(this, q, i + 1);
            return forkJoinTask;
        }

        final ForkJoinTask<?> e() {
            ForkJoinTask<?>[] forkJoinTaskArr;
            while (true) {
                int i = this.h;
                if (i - this.i >= 0 || (forkJoinTaskArr = this.j) == null) {
                    return null;
                }
                long length = (((forkJoinTaskArr.length - 1) & i) << t) + s;
                ForkJoinTask<?> forkJoinTask = (ForkJoinTask) p.getObjectVolatile(forkJoinTaskArr, length);
                if (forkJoinTask != null) {
                    if (p.compareAndSwapObject(forkJoinTaskArr, length, forkJoinTask, (Object) null)) {
                        p.putOrderedInt(this, q, i + 1);
                        return forkJoinTask;
                    }
                } else if (this.h != i) {
                    continue;
                } else if (i + 1 == this.i) {
                    return null;
                } else {
                    Thread.yield();
                }
            }
        }

        public final ForkJoinTask<?> f() {
            return this.f == 0 ? d() : e();
        }

        public final ForkJoinTask<?> g() {
            int length;
            ForkJoinTask<?>[] forkJoinTaskArr = this.j;
            if (forkJoinTaskArr == null || forkJoinTaskArr.length - 1 < 0) {
                return null;
            }
            return (ForkJoinTask) p.getObjectVolatile(forkJoinTaskArr, ((length & (this.f == 0 ? this.i - 1 : this.h)) << t) + s);
        }

        public final boolean b(ForkJoinTask<?> forkJoinTask) {
            int i;
            ForkJoinTask<?>[] forkJoinTaskArr = this.j;
            if (forkJoinTaskArr == null || (i = this.i) == this.h) {
                return false;
            }
            int i2 = i - 1;
            if (!p.compareAndSwapObject(forkJoinTaskArr, (((forkJoinTaskArr.length - 1) & i2) << t) + s, forkJoinTask, (Object) null)) {
                return false;
            }
            this.i = i2;
            return true;
        }

        final void h() {
            ForkJoinTask.a(this.n);
            ForkJoinTask.a(this.o);
            while (true) {
                ForkJoinTask<?> e = e();
                if (e != null) {
                    ForkJoinTask.a(e);
                } else {
                    return;
                }
            }
        }

        final void i() {
            while (true) {
                ForkJoinTask<?> e = e();
                if (e != null) {
                    e.c();
                } else {
                    return;
                }
            }
        }

        final void c(ForkJoinTask<?> forkJoinTask) {
            this.o = forkJoinTask;
            if (forkJoinTask != null) {
                forkJoinTask.c();
                ForkJoinTask<?>[] forkJoinTaskArr = this.j;
                short s2 = this.f;
                this.c++;
                this.o = null;
                if (s2 != 0) {
                    i();
                } else if (forkJoinTaskArr != null) {
                    int length = forkJoinTaskArr.length - 1;
                    while (true) {
                        int i = this.i - 1;
                        if (i - this.h >= 0) {
                            long j = ((length & i) << t) + s;
                            ForkJoinTask forkJoinTask2 = (ForkJoinTask) p.getObject(forkJoinTaskArr, j);
                            if (forkJoinTask2 != null) {
                                if (p.compareAndSwapObject(forkJoinTaskArr, j, forkJoinTask2, (Object) null)) {
                                    this.i = i;
                                    forkJoinTask2.c();
                                }
                            } else {
                                return;
                            }
                        } else {
                            return;
                        }
                    }
                }
            }
        }

        final boolean d(ForkJoinTask<?> forkJoinTask) {
            ForkJoinTask<?>[] forkJoinTaskArr;
            int i;
            int i2;
            boolean z = false;
            if (forkJoinTask == null || (forkJoinTaskArr = this.j) == null) {
                return false;
            }
            boolean z2 = true;
            int length = forkJoinTaskArr.length - 1;
            if (length < 0 || (r5 = (i = this.i) - (i2 = this.h)) <= 0) {
                return false;
            }
            boolean z3 = true;
            while (true) {
                int i3 = i - 1;
                long j = ((i3 & length) << t) + s;
                ForkJoinTask<?> forkJoinTask2 = (ForkJoinTask) p.getObject(forkJoinTaskArr, j);
                if (forkJoinTask2 == null) {
                    break;
                } else if (forkJoinTask2 != forkJoinTask) {
                    if (forkJoinTask2.status >= 0) {
                        z3 = false;
                    } else if (i3 + 1 == this.i) {
                        if (p.compareAndSwapObject(forkJoinTaskArr, j, forkJoinTask2, (Object) null)) {
                            this.i = i3;
                        }
                    }
                    int i4 = i4 - 1;
                    if (i4 != 0) {
                        i = i3;
                    } else if (!z3 && this.h == i2) {
                        z2 = false;
                    }
                } else if (i3 + 1 == this.i) {
                    if (p.compareAndSwapObject(forkJoinTaskArr, j, forkJoinTask, (Object) null)) {
                        this.i = i3;
                        z = true;
                    }
                } else if (this.h == i2) {
                    z = p.compareAndSwapObject(forkJoinTaskArr, j, forkJoinTask, new b());
                }
            }
            if (z) {
                forkJoinTask.c();
            }
            return z2;
        }

        final boolean a(CountedCompleter<?> countedCompleter) {
            ForkJoinTask<?>[] forkJoinTaskArr;
            int i = this.h;
            if (i - this.i >= 0 || (forkJoinTaskArr = this.j) == null) {
                return false;
            }
            long length = (((forkJoinTaskArr.length - 1) & i) << t) + s;
            Object objectVolatile = p.getObjectVolatile(forkJoinTaskArr, length);
            if (objectVolatile == null) {
                return true;
            }
            if (!(objectVolatile instanceof CountedCompleter)) {
                return false;
            }
            CountedCompleter<?> countedCompleter2 = (CountedCompleter) objectVolatile;
            CountedCompleter<?> countedCompleter3 = countedCompleter2;
            while (countedCompleter3 != countedCompleter) {
                countedCompleter3 = countedCompleter3.completer;
                if (countedCompleter3 == null) {
                    return false;
                }
            }
            if (this.h == i && p.compareAndSwapObject(forkJoinTaskArr, length, countedCompleter2, (Object) null)) {
                p.putOrderedInt(this, q, i + 1);
                countedCompleter2.c();
            }
            return true;
        }

        final boolean b(CountedCompleter<?> countedCompleter) {
            ForkJoinTask<?>[] forkJoinTaskArr;
            int i = this.h;
            int i2 = this.i;
            if (i - i2 < 0 && (forkJoinTaskArr = this.j) != null) {
                int i3 = i2 - 1;
                long length = (((forkJoinTaskArr.length - 1) & i3) << t) + s;
                Object object = p.getObject(forkJoinTaskArr, length);
                if (object instanceof CountedCompleter) {
                    CountedCompleter<?> countedCompleter2 = (CountedCompleter) object;
                    CountedCompleter<?> countedCompleter3 = countedCompleter2;
                    while (countedCompleter3 != countedCompleter) {
                        countedCompleter3 = countedCompleter3.completer;
                        if (countedCompleter3 != null) {
                            i3 = i3;
                        }
                    }
                    if (p.compareAndSwapInt(this, r, 0, 1)) {
                        if (this.i == i2 && this.j == forkJoinTaskArr && p.compareAndSwapObject(forkJoinTaskArr, length, countedCompleter2, (Object) null)) {
                            this.i = i3;
                            this.g = 0;
                            countedCompleter2.c();
                        } else {
                            this.g = 0;
                        }
                    }
                    return true;
                }
            }
            return false;
        }

        final boolean c(CountedCompleter<?> countedCompleter) {
            ForkJoinTask<?>[] forkJoinTaskArr;
            int i = this.h;
            int i2 = this.i;
            if (i - i2 >= 0 || (forkJoinTaskArr = this.j) == null) {
                return false;
            }
            int i3 = i2 - 1;
            long length = (((forkJoinTaskArr.length - 1) & i3) << t) + s;
            Object object = p.getObject(forkJoinTaskArr, length);
            if (!(object instanceof CountedCompleter)) {
                return false;
            }
            CountedCompleter<?> countedCompleter2 = (CountedCompleter) object;
            CountedCompleter<?> countedCompleter3 = countedCompleter2;
            while (countedCompleter3 != countedCompleter) {
                countedCompleter3 = countedCompleter3.completer;
                if (countedCompleter3 == null) {
                    return false;
                }
            }
            if (p.compareAndSwapObject(forkJoinTaskArr, length, countedCompleter2, (Object) null)) {
                this.i = i3;
                countedCompleter2.c();
            }
            return true;
        }

        final boolean j() {
            ForkJoinWorkerThread forkJoinWorkerThread;
            Thread.State state;
            return (this.a < 0 || (forkJoinWorkerThread = this.l) == null || (state = forkJoinWorkerThread.getState()) == Thread.State.BLOCKED || state == Thread.State.WAITING || state == Thread.State.TIMED_WAITING) ? false : true;
        }

        static {
            try {
                p = ForkJoinPool.m();
                q = p.objectFieldOffset(c.class.getDeclaredField(BuildConfig.FLAVOR));
                r = p.objectFieldOffset(c.class.getDeclaredField("qlock"));
                s = p.arrayBaseOffset(ForkJoinTask[].class);
                int arrayIndexScale = p.arrayIndexScale(ForkJoinTask[].class);
                if (((arrayIndexScale - 1) & arrayIndexScale) == 0) {
                    t = 31 - Integer.numberOfLeadingZeros(arrayIndexScale);
                    return;
                }
                throw new Error("data type scale not a power of two");
            } catch (Exception e) {
                throw new Error(e);
            }
        }
    }

    private static final synchronized int h() {
        int i;
        synchronized (ForkJoinPool.class) {
            i = o + 1;
            o = i;
        }
        return i;
    }

    private int i() {
        int i = 256;
        while (true) {
            int i2 = this.f;
            if ((i2 & 2) == 0) {
                int i3 = i2 + 2;
                if (p.compareAndSwapInt(this, v, i2, i3)) {
                    return i3;
                }
            }
            if (i >= 0) {
                if (ThreadLocalRandom.current().nextInt() >= 0) {
                    i--;
                }
            } else if (p.compareAndSwapInt(this, v, i2, i2 | 1)) {
                synchronized (this) {
                    if ((this.f & 1) != 0) {
                        try {
                            try {
                                wait();
                            } catch (InterruptedException unused) {
                                Thread.currentThread().interrupt();
                            }
                        } catch (SecurityException unused2) {
                        }
                    } else {
                        notifyAll();
                    }
                }
            } else {
                continue;
            }
        }
    }

    private void a(int i) {
        this.f = i;
        synchronized (this) {
            notifyAll();
        }
    }

    private void j() {
        long j;
        int i;
        int i2;
        ForkJoinWorkerThread forkJoinWorkerThread;
        do {
            j = this.e;
            i = (int) (j >>> 32);
            if (i >= 0 || (32768 & i) == 0 || (i2 = (int) j) < 0) {
                return;
            }
        } while (!p.compareAndSwapLong(this, q, j, ((((i + 65536) & (-65536)) | ((i + 1) & 65535)) << 32) | i2));
        Throwable th = null;
        try {
            ForkJoinWorkerThreadFactory forkJoinWorkerThreadFactory = this.k;
            if (forkJoinWorkerThreadFactory != null) {
                forkJoinWorkerThread = forkJoinWorkerThreadFactory.newThread(this);
                if (forkJoinWorkerThread != null) {
                    try {
                        forkJoinWorkerThread.start();
                        return;
                    } catch (Throwable th2) {
                        th = th2;
                    }
                }
            } else {
                forkJoinWorkerThread = null;
            }
        } catch (Throwable th3) {
            th = th3;
            forkJoinWorkerThread = null;
        }
        a(forkJoinWorkerThread, th);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0050 A[Catch: all -> 0x00a8, TryCatch #0 {all -> 0x00a8, blocks: (B:15:0x004c, B:17:0x0050, B:22:0x005f, B:24:0x0069, B:26:0x006f, B:28:0x0072, B:29:0x0080), top: B:40:0x004c }] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0093 A[DONT_GENERATE] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final io.netty.util.internal.chmv8.ForkJoinPool.c a(io.netty.util.internal.chmv8.ForkJoinWorkerThread r13) {
        /*
            r12 = this;
            r0 = 1
            r13.setDaemon(r0)
            java.lang.Thread$UncaughtExceptionHandler r1 = r12.l
            if (r1 == 0) goto L_0x000b
            r13.setUncaughtExceptionHandler(r1)
        L_0x000b:
            sun.misc.Unsafe r2 = io.netty.util.internal.chmv8.ForkJoinPool.p
            long r4 = io.netty.util.internal.chmv8.ForkJoinPool.w
            int r6 = r12.g
            r1 = 1640531527(0x61c88647, float:4.6237806E20)
            int r1 = r1 + r6
            r3 = r12
            r7 = r1
            boolean r2 = r2.compareAndSwapInt(r3, r4, r6, r7)
            if (r2 == 0) goto L_0x000b
            if (r1 == 0) goto L_0x000b
            io.netty.util.internal.chmv8.ForkJoinPool$c r2 = new io.netty.util.internal.chmv8.ForkJoinPool$c
            short r3 = r12.i
            r2.<init>(r12, r13, r3, r1)
            int r8 = r12.f
            r3 = r8 & 2
            if (r3 != 0) goto L_0x003d
            sun.misc.Unsafe r4 = io.netty.util.internal.chmv8.ForkJoinPool.p
            long r6 = io.netty.util.internal.chmv8.ForkJoinPool.v
            int r3 = r8 + 2
            r5 = r12
            r9 = r3
            boolean r4 = r4.compareAndSwapInt(r5, r6, r8, r9)
            if (r4 != 0) goto L_0x003b
            goto L_0x003d
        L_0x003b:
            r8 = r3
            goto L_0x0042
        L_0x003d:
            int r3 = r12.i()
            r8 = r3
        L_0x0042:
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r3 & r8
            int r4 = r8 + 2
            r5 = 2147483647(0x7fffffff, float:NaN)
            r4 = r4 & r5
            r3 = r3 | r4
            io.netty.util.internal.chmv8.ForkJoinPool$c[] r4 = r12.j     // Catch: all -> 0x00a8
            if (r4 == 0) goto L_0x0087
            int r5 = r4.length     // Catch: all -> 0x00a8
            int r6 = r5 + (-1)
            int r1 = r1 << r0
            r1 = r1 | r0
            r1 = r1 & r6
            r7 = r4[r1]     // Catch: all -> 0x00a8
            if (r7 == 0) goto L_0x0080
            r7 = 4
            r9 = 2
            if (r5 > r7) goto L_0x005f
            goto L_0x0066
        L_0x005f:
            int r7 = r5 >>> 1
            r10 = 65534(0xfffe, float:9.1833E-41)
            r7 = r7 & r10
            int r9 = r9 + r7
        L_0x0066:
            r7 = 0
            r10 = r5
            r5 = r7
        L_0x0069:
            int r1 = r1 + r9
            r1 = r1 & r6
            r11 = r4[r1]     // Catch: all -> 0x00a8
            if (r11 == 0) goto L_0x0080
            int r5 = r5 + r0
            if (r5 < r10) goto L_0x0069
            int r10 = r10 << 1
            java.lang.Object[] r4 = java.util.Arrays.copyOf(r4, r10)     // Catch: all -> 0x00a8
            io.netty.util.internal.chmv8.ForkJoinPool$c[] r4 = (io.netty.util.internal.chmv8.ForkJoinPool.c[]) r4     // Catch: all -> 0x00a8
            r12.j = r4     // Catch: all -> 0x00a8
            int r6 = r10 + (-1)
            r5 = r7
            goto L_0x0069
        L_0x0080:
            short r5 = (short) r1     // Catch: all -> 0x00a8
            r2.e = r5     // Catch: all -> 0x00a8
            r2.a = r1     // Catch: all -> 0x00a8
            r4[r1] = r2     // Catch: all -> 0x00a8
        L_0x0087:
            sun.misc.Unsafe r4 = io.netty.util.internal.chmv8.ForkJoinPool.p
            long r6 = io.netty.util.internal.chmv8.ForkJoinPool.v
            r5 = r12
            r9 = r3
            boolean r1 = r4.compareAndSwapInt(r5, r6, r8, r9)
            if (r1 != 0) goto L_0x0096
            r12.a(r3)
        L_0x0096:
            java.lang.String r1 = r12.m
            short r3 = r2.e
            int r0 = r3 >>> 1
            java.lang.String r0 = java.lang.Integer.toString(r0)
            java.lang.String r0 = r1.concat(r0)
            r13.setName(r0)
            return r2
        L_0x00a8:
            r13 = move-exception
            sun.misc.Unsafe r4 = io.netty.util.internal.chmv8.ForkJoinPool.p
            long r6 = io.netty.util.internal.chmv8.ForkJoinPool.v
            r5 = r12
            r9 = r3
            boolean r0 = r4.compareAndSwapInt(r5, r6, r8, r9)
            if (r0 != 0) goto L_0x00b8
            r12.a(r3)
        L_0x00b8:
            throw r13
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.util.internal.chmv8.ForkJoinPool.a(io.netty.util.internal.chmv8.ForkJoinWorkerThread):io.netty.util.internal.chmv8.ForkJoinPool$c");
    }

    /* JADX WARN: Code restructure failed: missing block: B:63:0x010c, code lost:
        io.netty.util.internal.chmv8.ForkJoinTask.e();
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:?, code lost:
        return;
     */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0067 A[DONT_GENERATE] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void a(io.netty.util.internal.chmv8.ForkJoinWorkerThread r18, java.lang.Throwable r19) {
        /*
            Method dump skipped, instructions count: 276
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.util.internal.chmv8.ForkJoinPool.a(io.netty.util.internal.chmv8.ForkJoinWorkerThread, java.lang.Throwable):void");
    }

    public final void a(ForkJoinTask<?> forkJoinTask) {
        int length;
        int length2;
        int i;
        int i2;
        int[] iArr = a.get();
        int i3 = this.f;
        c[] cVarArr = this.j;
        if (iArr != null && i3 > 0 && cVarArr != null && (length = cVarArr.length - 1) >= 0) {
            int i4 = iArr[0];
            c cVar = cVarArr[length & i4 & 126];
            if (!(cVar == null || i4 == 0 || !p.compareAndSwapInt(cVar, y, 0, 1))) {
                ForkJoinTask<?>[] forkJoinTaskArr = cVar.j;
                if (forkJoinTaskArr == null || (length2 = forkJoinTaskArr.length - 1) <= (i2 = (i = cVar.i) - cVar.h)) {
                    cVar.g = 0;
                } else {
                    p.putOrderedObject(forkJoinTaskArr, ((length2 & i) << t) + s, forkJoinTask);
                    cVar.i = i + 1;
                    cVar.g = 0;
                    if (i2 <= 1) {
                        a(cVarArr, cVar);
                        return;
                    }
                    return;
                }
            }
        }
        c(forkJoinTask);
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x007e, code lost:
        if (r2.length <= ((r4 + 1) - r3.h)) goto L_0x0080;
     */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00ff  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0168  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x00a3 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void c(io.netty.util.internal.chmv8.ForkJoinTask<?> r21) {
        /*
            Method dump skipped, instructions count: 372
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.util.internal.chmv8.ForkJoinPool.c(io.netty.util.internal.chmv8.ForkJoinTask):void");
    }

    public final void a() {
        Unsafe unsafe;
        long j;
        long j2;
        do {
            unsafe = p;
            j = q;
            j2 = this.e;
        } while (!unsafe.compareAndSwapLong(this, j, j2, (281474976710655L & j2) | (((-281474976710656L) & j2) + 281474976710656L)));
    }

    final void a(c[] cVarArr, c cVar) {
        int i;
        c cVar2;
        while (true) {
            long j = this.e;
            int i2 = (int) (j >>> 32);
            if (i2 < 0) {
                int i3 = (int) j;
                if (i3 <= 0) {
                    if (((short) i2) < 0) {
                        j();
                        return;
                    }
                    return;
                } else if (cVarArr != null && cVarArr.length > (i = 65535 & i3) && (cVar2 = cVarArr[i]) != null) {
                    long j2 = (cVar2.b & Integer.MAX_VALUE) | ((i2 + 65536) << 32);
                    int i4 = (65536 + i3) & Integer.MAX_VALUE;
                    if (cVar2.a == (Integer.MIN_VALUE | i3) && p.compareAndSwapLong(this, q, j, j2)) {
                        cVar2.a = i4;
                        Thread thread = cVar2.m;
                        if (thread != null) {
                            p.unpark(thread);
                            return;
                        }
                        return;
                    } else if (cVar != null && cVar.h >= cVar.i) {
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

    public final void a(c cVar) {
        cVar.c();
        int i = cVar.d;
        while (a(cVar, i) == 0) {
            int i2 = i ^ (i << 13);
            int i3 = i2 ^ (i2 >>> 17);
            i = i3 ^ (i3 << 5);
        }
    }

    private final int a(c cVar, int i) {
        int length;
        ForkJoinTask<?>[] forkJoinTaskArr;
        long j = this.e;
        c[] cVarArr = this.j;
        if (cVarArr == null || cVarArr.length - 1 < 0 || cVar == null) {
            return 0;
        }
        int i2 = length + length + 1;
        int i3 = cVar.a;
        do {
            c cVar2 = cVarArr[(i - i2) & length];
            if (cVar2 != null) {
                int i4 = cVar2.h;
                if (i4 - cVar2.i < 0 && (forkJoinTaskArr = cVar2.j) != null) {
                    long length2 = (((forkJoinTaskArr.length - 1) & i4) << t) + s;
                    ForkJoinTask<?> forkJoinTask = (ForkJoinTask) p.getObjectVolatile(forkJoinTaskArr, length2);
                    if (forkJoinTask == null) {
                        return 0;
                    }
                    if (i3 < 0) {
                        a(j, cVarArr, cVar, cVar2, i4);
                        return 0;
                    } else if (cVar2.h != i4 || !p.compareAndSwapObject(forkJoinTaskArr, length2, forkJoinTask, (Object) null)) {
                        return 0;
                    } else {
                        int i5 = i4 + 1;
                        p.putOrderedInt(cVar2, x, i5);
                        if (i5 - cVar2.i < 0) {
                            a(cVarArr, cVar2);
                        }
                        cVar.c(forkJoinTask);
                        return 0;
                    }
                }
            }
            i2--;
        } while (i2 >= 0);
        int i6 = (int) j;
        if ((i3 | i6) < 0) {
            return a(cVar, j, i3);
        }
        if (this.e != j) {
            return 0;
        }
        long j2 = (j - 281474976710656L) & (-4294967296L);
        cVar.b = i6;
        cVar.a = Integer.MIN_VALUE | i3;
        if (p.compareAndSwapLong(this, q, j, j2 | i3)) {
            return 0;
        }
        cVar.a = i3;
        return 0;
    }

    private final int a(c cVar, long j, int i) {
        long j2;
        long j3;
        Unsafe unsafe;
        long j4;
        long j5;
        int i2 = cVar.g;
        if (i2 >= 0 && cVar.a == i && this.e == j && !Thread.interrupted()) {
            int i3 = (int) j;
            int i4 = (int) (j >>> 32);
            int i5 = (i4 >> 16) + this.h;
            if (i3 < 0 || (i5 <= 0 && a(false, false))) {
                cVar.g = -1;
                return -1;
            }
            int i6 = cVar.c;
            if (i6 != 0) {
                cVar.c = 0;
                do {
                    unsafe = p;
                    j4 = u;
                    j5 = this.d;
                } while (!unsafe.compareAndSwapLong(this, j4, j5, i6 + j5));
            } else {
                long j6 = (i5 > 0 || i != (i3 | Integer.MIN_VALUE)) ? 0L : ((65536 + i4) << 32) | (cVar.b & Integer.MAX_VALUE);
                if (j6 != 0) {
                    int i7 = -((short) i4);
                    j3 = i7 < 0 ? 200000000L : (i7 + 1) * 2000000000;
                    j2 = (System.nanoTime() + j3) - 2000000;
                } else {
                    j3 = 0;
                    j2 = 0;
                }
                if (cVar.a == i && this.e == j) {
                    Thread currentThread = Thread.currentThread();
                    p.putObject(currentThread, r, this);
                    cVar.m = currentThread;
                    if (cVar.a == i && this.e == j) {
                        p.park(false, j3);
                    }
                    cVar.m = null;
                    p.putObject(currentThread, r, (Object) null);
                    if (j3 != 0 && this.e == j && j2 - System.nanoTime() <= 0 && p.compareAndSwapLong(this, q, j, j6)) {
                        cVar.g = -1;
                        return -1;
                    }
                }
            }
        }
        return i2;
    }

    private final void a(long j, c[] cVarArr, c cVar, c cVar2, int i) {
        int i2;
        int i3;
        c cVar3;
        if (cVar != null && cVar.a < 0 && (i2 = (int) j) > 0 && cVarArr != null && cVarArr.length > (i3 = 65535 & i2) && (cVar3 = cVarArr[i3]) != null) {
            if (this.e == j) {
                long j2 = (cVar3.b & Integer.MAX_VALUE) | ((((int) (j >>> 32)) + 65536) << 32);
                int i4 = (65536 + i2) & Integer.MAX_VALUE;
                if (cVar2 != null && cVar2.h == i && cVar.a < 0 && cVar3.a == (Integer.MIN_VALUE | i2) && p.compareAndSwapLong(this, q, j, j2)) {
                    cVar3.a = i4;
                    Thread thread = cVar3.m;
                    if (thread != null) {
                        p.unpark(thread);
                    }
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:102:?, code lost:
        return 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x0047, code lost:
        continue;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int c(io.netty.util.internal.chmv8.ForkJoinPool.c r19, io.netty.util.internal.chmv8.ForkJoinTask<?> r20) {
        /*
            Method dump skipped, instructions count: 225
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.util.internal.chmv8.ForkJoinPool.c(io.netty.util.internal.chmv8.ForkJoinPool$c, io.netty.util.internal.chmv8.ForkJoinTask):int");
    }

    private int a(c cVar, CountedCompleter<?> countedCompleter) {
        int length;
        c[] cVarArr = this.j;
        if (cVarArr == null || cVarArr.length - 1 < 0 || cVar == null || countedCompleter == null) {
            return 0;
        }
        int i = cVar.e;
        int i2 = length + length + 1;
        long j = 0;
        int i3 = i2;
        while (true) {
            int i4 = countedCompleter.status;
            if (i4 < 0) {
                return i4;
            }
            if (!cVar.c(countedCompleter)) {
                int i5 = countedCompleter.status;
                if (i5 < 0) {
                    return i5;
                }
                c cVar2 = cVarArr[i & length];
                if (cVar2 == null || !cVar2.a(countedCompleter)) {
                    i3--;
                    if (i3 < 0) {
                        long j2 = this.e;
                        if (j == j2) {
                            return i5;
                        }
                        i3 = i2;
                        j = j2;
                    } else {
                        continue;
                    }
                    i += 2;
                }
            }
            i3 = i2;
            i += 2;
        }
    }

    public final boolean a(long j) {
        int length;
        ForkJoinWorkerThread forkJoinWorkerThread;
        c[] cVarArr = this.j;
        short s2 = this.h;
        int i = (int) j;
        if (cVarArr == null || (length = cVarArr.length - 1) < 0 || i < 0 || this.e != j) {
            return false;
        }
        c cVar = cVarArr[length & i];
        if (i == 0 || cVar == null) {
            short s3 = (short) (j >>> 32);
            if (s3 >= 0 && ((int) (j >> 48)) + s2 > 1) {
                return p.compareAndSwapLong(this, q, j, ((j - 281474976710656L) & (-281474976710656L)) | (281474976710655L & j));
            }
            if (s3 + s2 >= 32767) {
                return false;
            }
            if (!p.compareAndSwapLong(this, q, j, ((4294967296L + j) & 281470681743360L) | ((-281470681743361L) & j))) {
                return false;
            }
            Throwable th = null;
            try {
                ForkJoinWorkerThreadFactory forkJoinWorkerThreadFactory = this.k;
                if (forkJoinWorkerThreadFactory != null) {
                    forkJoinWorkerThread = forkJoinWorkerThreadFactory.newThread(this);
                    if (forkJoinWorkerThread != null) {
                        try {
                            forkJoinWorkerThread.start();
                            return true;
                        } catch (Throwable th2) {
                            th = th2;
                            a(forkJoinWorkerThread, th);
                            return false;
                        }
                    }
                } else {
                    forkJoinWorkerThread = null;
                }
            } catch (Throwable th3) {
                th = th3;
                forkJoinWorkerThread = null;
            }
            a(forkJoinWorkerThread, th);
            return false;
        }
        long j2 = (cVar.b & Integer.MAX_VALUE) | ((-4294967296L) & j);
        int i2 = (65536 + i) & Integer.MAX_VALUE;
        if (cVar.a != (i | Integer.MIN_VALUE) || !p.compareAndSwapLong(this, q, j, j2)) {
            return false;
        }
        cVar.a = i2;
        Thread thread = cVar.m;
        if (thread != null) {
            p.unpark(thread);
        }
        return true;
    }

    public final int a(c cVar, ForkJoinTask<?> forkJoinTask) {
        Unsafe unsafe;
        long j;
        long j2;
        if (forkJoinTask == null) {
            return 0;
        }
        int i = forkJoinTask.status;
        if (i < 0 || cVar == null) {
            return i;
        }
        ForkJoinTask<?> forkJoinTask2 = cVar.n;
        cVar.n = forkJoinTask;
        while (cVar.d(forkJoinTask) && (i = forkJoinTask.status) >= 0) {
        }
        if (i >= 0 && (forkJoinTask instanceof CountedCompleter)) {
            i = a(cVar, (CountedCompleter) forkJoinTask);
        }
        long j3 = 0;
        while (i >= 0) {
            i = forkJoinTask.status;
            if (i < 0) {
                break;
            }
            i = c(cVar, forkJoinTask);
            if (i == 0 && (i = forkJoinTask.status) >= 0) {
                if (!a(j3)) {
                    j3 = this.e;
                } else {
                    if (forkJoinTask.d()) {
                        int i2 = forkJoinTask.status;
                        if (i2 >= 0) {
                            synchronized (forkJoinTask) {
                                if (forkJoinTask.status >= 0) {
                                    try {
                                        forkJoinTask.wait();
                                    } catch (InterruptedException unused) {
                                    }
                                } else {
                                    forkJoinTask.notifyAll();
                                }
                            }
                        }
                        i = i2;
                    } else {
                        i = i;
                    }
                    do {
                        unsafe = p;
                        j = q;
                        j2 = this.e;
                    } while (!unsafe.compareAndSwapLong(this, j, j2, (281474976710655L & j2) | (((-281474976710656L) & j2) + 281474976710656L)));
                }
            }
        }
        cVar.n = forkJoinTask2;
        return i;
    }

    public final void b(c cVar, ForkJoinTask<?> forkJoinTask) {
        int i;
        if (cVar != null && forkJoinTask != null && (i = forkJoinTask.status) >= 0) {
            ForkJoinTask<?> forkJoinTask2 = cVar.n;
            cVar.n = forkJoinTask;
            while (cVar.d(forkJoinTask) && (i = forkJoinTask.status) >= 0) {
            }
            if (i >= 0) {
                if (forkJoinTask instanceof CountedCompleter) {
                    a(cVar, (CountedCompleter) forkJoinTask);
                }
                while (forkJoinTask.status >= 0 && c(cVar, forkJoinTask) > 0) {
                }
            }
            cVar.n = forkJoinTask2;
        }
    }

    private c k() {
        int i;
        int length;
        int nextInt = ThreadLocalRandom.current().nextInt();
        do {
            i = this.f;
            c[] cVarArr = this.j;
            if (cVarArr != null && cVarArr.length - 1 >= 0) {
                for (int i2 = (length + 1) << 2; i2 >= 0; i2--) {
                    c cVar = cVarArr[(((nextInt - i2) << 1) | 1) & length];
                    if (cVar != null && cVar.h - cVar.i < 0) {
                        return cVar;
                    }
                }
            }
        } while (this.f != i);
        return null;
    }

    public final void b(c cVar) {
        ForkJoinTask<?> a2;
        Unsafe unsafe;
        long j;
        long j2;
        ForkJoinTask<?> forkJoinTask = cVar.o;
        boolean z = true;
        while (true) {
            ForkJoinTask<?> f = cVar.f();
            if (f != null) {
                f.c();
            } else {
                c k = k();
                if (k != null) {
                    if (!z) {
                        do {
                            unsafe = p;
                            j = q;
                            j2 = this.e;
                        } while (!unsafe.compareAndSwapLong(this, j, j2, (j2 & 281474976710655L) | ((j2 & (-281474976710656L)) + 281474976710656L)));
                        z = true;
                    }
                    int i = k.h;
                    if (i - k.i < 0 && (a2 = k.a(i)) != null) {
                        cVar.o = a2;
                        a2.c();
                        cVar.o = forkJoinTask;
                    }
                } else if (z) {
                    long j3 = this.e;
                    long j4 = ((j3 & (-281474976710656L)) - 281474976710656L) | (j3 & 281474976710655L);
                    if (((int) (j4 >> 48)) + this.h != 0) {
                        if (p.compareAndSwapLong(this, q, j3, j4)) {
                            z = false;
                        }
                    } else {
                        return;
                    }
                } else {
                    long j5 = this.e;
                    if (((int) (j5 >> 48)) + this.h <= 0 && p.compareAndSwapLong(this, q, j5, (j5 & 281474976710655L) | ((j5 & (-281474976710656L)) + 281474976710656L))) {
                        return;
                    }
                }
            }
        }
    }

    public final ForkJoinTask<?> c(c cVar) {
        ForkJoinTask<?> a2;
        while (true) {
            ForkJoinTask<?> f = cVar.f();
            if (f != null) {
                return f;
            }
            c k = k();
            if (k == null) {
                return null;
            }
            int i = k.h;
            if (i - k.i < 0 && (a2 = k.a(i)) != null) {
                return a2;
            }
        }
    }

    public static int b() {
        Thread currentThread = Thread.currentThread();
        int i = 0;
        if (!(currentThread instanceof ForkJoinWorkerThread)) {
            return 0;
        }
        ForkJoinWorkerThread forkJoinWorkerThread = (ForkJoinWorkerThread) currentThread;
        ForkJoinPool forkJoinPool = forkJoinWorkerThread.a;
        short s2 = forkJoinPool.h;
        c cVar = forkJoinWorkerThread.b;
        int i2 = cVar.i - cVar.h;
        int i3 = ((int) (forkJoinPool.e >> 48)) + s2;
        int i4 = s2 >>> 1;
        if (i3 <= i4) {
            int i5 = i4 >>> 1;
            if (i3 > i5) {
                i = 1;
            } else {
                int i6 = i5 >>> 1;
                i = i3 > i6 ? 2 : i3 > (i6 >>> 1) ? 4 : 8;
            }
        }
        return i2 - i;
    }

    /* JADX WARN: Code restructure failed: missing block: B:47:0x0090, code lost:
        a(r3, r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0093, code lost:
        return r10;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0041  */
    /* JADX WARN: Type inference failed for: r13v1 */
    /* JADX WARN: Type inference failed for: r13v2 */
    /* JADX WARN: Type inference failed for: r1v5 */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean a(boolean r20, boolean r21) {
        /*
            Method dump skipped, instructions count: 310
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.util.internal.chmv8.ForkJoinPool.a(boolean, boolean):boolean");
    }

    public static c c() {
        ForkJoinPool forkJoinPool;
        c[] cVarArr;
        int length;
        int[] iArr = a.get();
        if (iArr == null || (forkJoinPool = b) == null || (cVarArr = forkJoinPool.j) == null || cVarArr.length - 1 < 0) {
            return null;
        }
        return cVarArr[iArr[0] & length & 126];
    }

    public final boolean b(ForkJoinTask<?> forkJoinTask) {
        c cVar;
        int i;
        ForkJoinTask<?>[] forkJoinTaskArr;
        int[] iArr = a.get();
        c[] cVarArr = this.j;
        if (iArr == null || cVarArr == null) {
            return false;
        }
        boolean z = true;
        int length = cVarArr.length - 1;
        if (length < 0 || (cVar = cVarArr[iArr[0] & length & 126]) == null || cVar.h == (i = cVar.i) || (forkJoinTaskArr = cVar.j) == null) {
            return false;
        }
        int i2 = i - 1;
        long length2 = (((forkJoinTaskArr.length - 1) & i2) << t) + s;
        if (p.getObject(forkJoinTaskArr, length2) != forkJoinTask || !p.compareAndSwapInt(cVar, y, 0, 1)) {
            return false;
        }
        if (cVar.i == i && cVar.j == forkJoinTaskArr && p.compareAndSwapObject(forkJoinTaskArr, length2, forkJoinTask, (Object) null)) {
            cVar.i = i2;
        } else {
            z = false;
        }
        cVar.g = 0;
        return z;
    }

    public final int a(CountedCompleter<?> countedCompleter) {
        int length;
        int[] iArr = a.get();
        c[] cVarArr = this.j;
        if (!(iArr == null || cVarArr == null || cVarArr.length - 1 < 0)) {
            int i = iArr[0];
            c cVar = cVarArr[i & length & 126];
            if (!(cVar == null || countedCompleter == null)) {
                int i2 = length + length + 1;
                long j = 0;
                int i3 = i | 1;
                int i4 = i2;
                while (true) {
                    int i5 = countedCompleter.status;
                    if (i5 < 0) {
                        return i5;
                    }
                    if (!cVar.b(countedCompleter)) {
                        int i6 = countedCompleter.status;
                        if (i6 < 0) {
                            return i6;
                        }
                        c cVar2 = cVarArr[i3 & length];
                        if (cVar2 == null || !cVar2.a(countedCompleter)) {
                            i4--;
                            if (i4 < 0) {
                                long j2 = this.e;
                                if (j == j2) {
                                    return i6;
                                }
                                i4 = i2;
                                j = j2;
                            } else {
                                continue;
                            }
                            i3 += 2;
                        }
                    }
                    i4 = i2;
                    i3 += 2;
                }
            }
        }
        return 0;
    }

    public ForkJoinPool() {
        this(Math.min(32767, Runtime.getRuntime().availableProcessors()), defaultForkJoinWorkerThreadFactory, null, false);
    }

    public ForkJoinPool(int i) {
        this(i, defaultForkJoinWorkerThreadFactory, null, false);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ForkJoinPool(int r7, io.netty.util.internal.chmv8.ForkJoinPool.ForkJoinWorkerThreadFactory r8, java.lang.Thread.UncaughtExceptionHandler r9, boolean r10) {
        /*
            r6 = this;
            int r1 = b(r7)
            io.netty.util.internal.chmv8.ForkJoinPool$ForkJoinWorkerThreadFactory r2 = a(r8)
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "ForkJoinPool-"
            r7.append(r8)
            int r8 = h()
            r7.append(r8)
            java.lang.String r8 = "-worker-"
            r7.append(r8)
            java.lang.String r5 = r7.toString()
            r0 = r6
            r3 = r9
            r4 = r10
            r0.<init>(r1, r2, r3, r4, r5)
            g()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.util.internal.chmv8.ForkJoinPool.<init>(int, io.netty.util.internal.chmv8.ForkJoinPool$ForkJoinWorkerThreadFactory, java.lang.Thread$UncaughtExceptionHandler, boolean):void");
    }

    private static int b(int i) {
        if (i > 0 && i <= 32767) {
            return i;
        }
        throw new IllegalArgumentException();
    }

    private static ForkJoinWorkerThreadFactory a(ForkJoinWorkerThreadFactory forkJoinWorkerThreadFactory) {
        if (forkJoinWorkerThreadFactory != null) {
            return forkJoinWorkerThreadFactory;
        }
        throw new NullPointerException();
    }

    private ForkJoinPool(int i, ForkJoinWorkerThreadFactory forkJoinWorkerThreadFactory, Thread.UncaughtExceptionHandler uncaughtExceptionHandler, int i2, String str) {
        this.m = str;
        this.k = forkJoinWorkerThreadFactory;
        this.l = uncaughtExceptionHandler;
        this.i = (short) i2;
        this.h = (short) i;
        long j = -i;
        this.e = ((j << 32) & 281470681743360L) | ((j << 48) & (-281474976710656L));
    }

    public static ForkJoinPool commonPool() {
        return b;
    }

    public <T> T invoke(ForkJoinTask<T> forkJoinTask) {
        if (forkJoinTask != null) {
            a((ForkJoinTask<?>) forkJoinTask);
            return forkJoinTask.join();
        }
        throw new NullPointerException();
    }

    public void execute(ForkJoinTask<?> forkJoinTask) {
        if (forkJoinTask != null) {
            a(forkJoinTask);
            return;
        }
        throw new NullPointerException();
    }

    @Override // java.util.concurrent.Executor
    public void execute(Runnable runnable) {
        ForkJoinTask<?> forkJoinTask;
        if (runnable != null) {
            if (runnable instanceof ForkJoinTask) {
                forkJoinTask = (ForkJoinTask) runnable;
            } else {
                forkJoinTask = new ForkJoinTask.e(runnable);
            }
            a(forkJoinTask);
            return;
        }
        throw new NullPointerException();
    }

    public <T> ForkJoinTask<T> submit(ForkJoinTask<T> forkJoinTask) {
        if (forkJoinTask != null) {
            a((ForkJoinTask<?>) forkJoinTask);
            return forkJoinTask;
        }
        throw new NullPointerException();
    }

    @Override // java.util.concurrent.AbstractExecutorService, java.util.concurrent.ExecutorService
    public <T> ForkJoinTask<T> submit(Callable<T> callable) {
        ForkJoinTask.a aVar = new ForkJoinTask.a(callable);
        a(aVar);
        return aVar;
    }

    @Override // java.util.concurrent.AbstractExecutorService, java.util.concurrent.ExecutorService
    public <T> ForkJoinTask<T> submit(Runnable runnable, T t2) {
        ForkJoinTask.b bVar = new ForkJoinTask.b(runnable, t2);
        a(bVar);
        return bVar;
    }

    @Override // java.util.concurrent.AbstractExecutorService, java.util.concurrent.ExecutorService
    public ForkJoinTask<?> submit(Runnable runnable) {
        ForkJoinTask<?> forkJoinTask;
        if (runnable != null) {
            if (runnable instanceof ForkJoinTask) {
                forkJoinTask = (ForkJoinTask) runnable;
            } else {
                forkJoinTask = new ForkJoinTask.c(runnable);
            }
            a(forkJoinTask);
            return forkJoinTask;
        }
        throw new NullPointerException();
    }

    @Override // java.util.concurrent.AbstractExecutorService, java.util.concurrent.ExecutorService
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> collection) {
        ArrayList arrayList = new ArrayList(collection.size());
        try {
            Iterator<? extends Callable<T>> it = collection.iterator();
            while (it.hasNext()) {
                ForkJoinTask.a aVar = new ForkJoinTask.a((Callable) it.next());
                arrayList.add(aVar);
                a(aVar);
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
        return this.k;
    }

    public Thread.UncaughtExceptionHandler getUncaughtExceptionHandler() {
        return this.l;
    }

    public int getParallelism() {
        short s2 = this.h;
        if (s2 > 0) {
            return s2;
        }
        return 1;
    }

    public static int getCommonPoolParallelism() {
        return c;
    }

    public int getPoolSize() {
        return this.h + ((short) (this.e >>> 32));
    }

    public boolean getAsyncMode() {
        return this.i == 1;
    }

    public int getRunningThreadCount() {
        c[] cVarArr = this.j;
        int i = 0;
        if (cVarArr != null) {
            for (int i2 = 1; i2 < cVarArr.length; i2 += 2) {
                c cVar = cVarArr[i2];
                if (cVar != null && cVar.j()) {
                    i++;
                }
            }
        }
        return i;
    }

    public int getActiveThreadCount() {
        int i = this.h + ((int) (this.e >> 48));
        if (i <= 0) {
            return 0;
        }
        return i;
    }

    public boolean isQuiescent() {
        return this.h + ((int) (this.e >> 48)) <= 0;
    }

    public long getStealCount() {
        long j = this.d;
        c[] cVarArr = this.j;
        if (cVarArr != null) {
            for (int i = 1; i < cVarArr.length; i += 2) {
                c cVar = cVarArr[i];
                if (cVar != null) {
                    j += cVar.c;
                }
            }
        }
        return j;
    }

    public long getQueuedTaskCount() {
        c[] cVarArr = this.j;
        long j = 0;
        if (cVarArr != null) {
            for (int i = 1; i < cVarArr.length; i += 2) {
                c cVar = cVarArr[i];
                if (cVar != null) {
                    j += cVar.a();
                }
            }
        }
        return j;
    }

    public int getQueuedSubmissionCount() {
        c[] cVarArr = this.j;
        if (cVarArr == null) {
            return 0;
        }
        int i = 0;
        for (int i2 = 0; i2 < cVarArr.length; i2 += 2) {
            c cVar = cVarArr[i2];
            if (cVar != null) {
                i += cVar.a();
            }
        }
        return i;
    }

    public boolean hasQueuedSubmissions() {
        c[] cVarArr = this.j;
        if (cVarArr != null) {
            for (int i = 0; i < cVarArr.length; i += 2) {
                c cVar = cVarArr[i];
                if (!(cVar == null || cVar.b())) {
                    return true;
                }
            }
        }
        return false;
    }

    protected ForkJoinTask<?> pollSubmission() {
        ForkJoinTask<?> e;
        c[] cVarArr = this.j;
        if (cVarArr == null) {
            return null;
        }
        for (int i = 0; i < cVarArr.length; i += 2) {
            c cVar = cVarArr[i];
            if (!(cVar == null || (e = cVar.e()) == null)) {
                return e;
            }
        }
        return null;
    }

    protected int drainTasksTo(Collection<? super ForkJoinTask<?>> collection) {
        c[] cVarArr = this.j;
        if (cVarArr == null) {
            return 0;
        }
        int i = 0;
        for (c cVar : cVarArr) {
            if (cVar != null) {
                while (true) {
                    ForkJoinTask<?> e = cVar.e();
                    if (e != null) {
                        collection.add(e);
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
        long j3;
        int i;
        String str;
        long j4 = this.d;
        long j5 = this.e;
        c[] cVarArr = this.j;
        if (cVarArr != null) {
            j2 = j4;
            i = 0;
            j3 = 0;
            j = 0;
            for (int i2 = 0; i2 < cVarArr.length; i2++) {
                c cVar = cVarArr[i2];
                if (cVar != null) {
                    int a2 = cVar.a();
                    if ((i2 & 1) == 0) {
                        j += a2;
                    } else {
                        j3 += a2;
                        j2 += cVar.c;
                        if (cVar.j()) {
                            i++;
                        }
                    }
                }
            }
        } else {
            j2 = j4;
            i = 0;
            j3 = 0;
            j = 0;
        }
        short s2 = this.h;
        int i3 = ((short) (j5 >>> 32)) + s2;
        int i4 = ((int) (j5 >> 48)) + s2;
        if (i4 < 0) {
            i4 = 0;
        }
        if ((j5 & 2147483648L) != 0) {
            str = i3 == 0 ? "Terminated" : "Terminating";
        } else {
            str = this.f < 0 ? "Shutting down" : "Running";
        }
        return super.toString() + "[" + str + ", parallelism = " + ((int) s2) + ", size = " + i3 + ", active = " + i4 + ", running = " + i + ", steals = " + j2 + ", tasks = " + j3 + ", submissions = " + j + "]";
    }

    @Override // java.util.concurrent.ExecutorService
    public void shutdown() {
        g();
        a(false, true);
    }

    @Override // java.util.concurrent.ExecutorService
    public List<Runnable> shutdownNow() {
        g();
        a(true, true);
        return Collections.emptyList();
    }

    @Override // java.util.concurrent.ExecutorService
    public boolean isTerminated() {
        long j = this.e;
        return (2147483648L & j) != 0 && ((short) ((int) (j >>> 32))) + this.h <= 0;
    }

    public boolean isTerminating() {
        long j = this.e;
        return (2147483648L & j) != 0 && ((short) ((int) (j >>> 32))) + this.h > 0;
    }

    @Override // java.util.concurrent.ExecutorService
    public boolean isShutdown() {
        return this.f < 0;
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
        c[] cVarArr;
        int length;
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
        int i = 0;
        boolean z = true;
        while (!isQuiescent() && (cVarArr = this.j) != null && (length = cVarArr.length - 1) >= 0) {
            if (!z) {
                if (System.nanoTime() - nanoTime > nanos) {
                    return false;
                }
                Thread.yield();
            }
            int i2 = (length + 1) << 2;
            while (true) {
                if (i2 < 0) {
                    z = false;
                    break;
                }
                int i3 = i + 1;
                c cVar = cVarArr[i & length];
                if (cVar != null) {
                    int i4 = cVar.h;
                    if (i4 - cVar.i < 0) {
                        ForkJoinTask<?> a2 = cVar.a(i4);
                        if (a2 != null) {
                            a2.c();
                        }
                        z = true;
                        i = i3;
                    }
                }
                i2--;
                i = i3;
            }
        }
        return true;
    }

    public static void d() {
        b.awaitQuiescence(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x001e, code lost:
        if (r3.isReleasable() != false) goto L_0x0026;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0024, code lost:
        if (r3.block() == false) goto L_0x001a;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x002a, code lost:
        r3 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x002b, code lost:
        r0.a();
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x002e, code lost:
        throw r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x003b, code lost:
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void managedBlock(io.netty.util.internal.chmv8.ForkJoinPool.ManagedBlocker r3) throws java.lang.InterruptedException {
        /*
            java.lang.Thread r0 = java.lang.Thread.currentThread()
            boolean r1 = r0 instanceof io.netty.util.internal.chmv8.ForkJoinWorkerThread
            if (r1 == 0) goto L_0x002f
            io.netty.util.internal.chmv8.ForkJoinWorkerThread r0 = (io.netty.util.internal.chmv8.ForkJoinWorkerThread) r0
            io.netty.util.internal.chmv8.ForkJoinPool r0 = r0.a
        L_0x000c:
            boolean r1 = r3.isReleasable()
            if (r1 != 0) goto L_0x003b
            long r1 = r0.e
            boolean r1 = r0.a(r1)
            if (r1 == 0) goto L_0x000c
        L_0x001a:
            boolean r1 = r3.isReleasable()     // Catch: all -> 0x002a
            if (r1 != 0) goto L_0x0026
            boolean r1 = r3.block()     // Catch: all -> 0x002a
            if (r1 == 0) goto L_0x001a
        L_0x0026:
            r0.a()
            goto L_0x003b
        L_0x002a:
            r3 = move-exception
            r0.a()
            throw r3
        L_0x002f:
            boolean r0 = r3.isReleasable()
            if (r0 != 0) goto L_0x003b
            boolean r0 = r3.block()
            if (r0 == 0) goto L_0x002f
        L_0x003b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.util.internal.chmv8.ForkJoinPool.managedBlock(io.netty.util.internal.chmv8.ForkJoinPool$ManagedBlocker):void");
    }

    @Override // java.util.concurrent.AbstractExecutorService
    protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T t2) {
        return new ForkJoinTask.b(runnable, t2);
    }

    @Override // java.util.concurrent.AbstractExecutorService
    protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        return new ForkJoinTask.a(callable);
    }

    static {
        try {
            p = m();
            q = p.objectFieldOffset(ForkJoinPool.class.getDeclaredField("ctl"));
            u = p.objectFieldOffset(ForkJoinPool.class.getDeclaredField("stealCount"));
            v = p.objectFieldOffset(ForkJoinPool.class.getDeclaredField("plock"));
            w = p.objectFieldOffset(ForkJoinPool.class.getDeclaredField("indexSeed"));
            r = p.objectFieldOffset(Thread.class.getDeclaredField("parkBlocker"));
            x = p.objectFieldOffset(c.class.getDeclaredField(BuildConfig.FLAVOR));
            y = p.objectFieldOffset(c.class.getDeclaredField("qlock"));
            s = p.arrayBaseOffset(ForkJoinTask[].class);
            int arrayIndexScale = p.arrayIndexScale(ForkJoinTask[].class);
            if (((arrayIndexScale - 1) & arrayIndexScale) == 0) {
                t = 31 - Integer.numberOfLeadingZeros(arrayIndexScale);
                a = new ThreadLocal<>();
                defaultForkJoinWorkerThreadFactory = new a();
                n = new RuntimePermission("modifyThread");
                b = (ForkJoinPool) AccessController.doPrivileged(new PrivilegedAction<ForkJoinPool>() { // from class: io.netty.util.internal.chmv8.ForkJoinPool.1
                    /* renamed from: a */
                    public ForkJoinPool run() {
                        return ForkJoinPool.l();
                    }
                });
                short s2 = b.h;
                if (s2 <= 0) {
                    s2 = 1;
                }
                c = s2;
                return;
            }
            throw new Error("data type scale not a power of two");
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    public static ForkJoinPool l() {
        ForkJoinWorkerThreadFactory forkJoinWorkerThreadFactory = defaultForkJoinWorkerThreadFactory;
        int i = -1;
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = null;
        try {
            String property = System.getProperty("java.util.concurrent.ForkJoinPool.common.parallelism");
            String property2 = System.getProperty("java.util.concurrent.ForkJoinPool.common.threadFactory");
            String property3 = System.getProperty("java.util.concurrent.ForkJoinPool.common.exceptionHandler");
            if (property != null) {
                i = Integer.parseInt(property);
            }
            if (property2 != null) {
                forkJoinWorkerThreadFactory = (ForkJoinWorkerThreadFactory) ClassLoader.getSystemClassLoader().loadClass(property2).newInstance();
            }
            if (property3 != null) {
                uncaughtExceptionHandler = (Thread.UncaughtExceptionHandler) ClassLoader.getSystemClassLoader().loadClass(property3).newInstance();
            }
            forkJoinWorkerThreadFactory = forkJoinWorkerThreadFactory;
        } catch (Exception unused) {
            uncaughtExceptionHandler = null;
        }
        if (i < 0 && Runtime.getRuntime().availableProcessors() - 1 < 0) {
            i = 0;
        }
        return new ForkJoinPool(i > 32767 ? 32767 : i, forkJoinWorkerThreadFactory, uncaughtExceptionHandler, 0, "ForkJoinPool.commonPool-worker-");
    }

    public static Unsafe m() {
        try {
            try {
                return Unsafe.getUnsafe();
            } catch (PrivilegedActionException e) {
                throw new RuntimeException("Could not initialize intrinsics", e.getCause());
            }
        } catch (SecurityException unused) {
            return (Unsafe) AccessController.doPrivileged(new PrivilegedExceptionAction<Unsafe>() { // from class: io.netty.util.internal.chmv8.ForkJoinPool.2
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
