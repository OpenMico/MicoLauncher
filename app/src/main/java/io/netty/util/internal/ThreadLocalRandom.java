package io.netty.util.internal;

import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.lang.Thread;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes4.dex */
public final class ThreadLocalRandom extends Random {
    private static final InternalLogger a = InternalLoggerFactory.getInstance(ThreadLocalRandom.class);
    private static final AtomicLong b = new AtomicLong();
    private static volatile long c = 0;
    private static final Thread d;
    private static final BlockingQueue<Long> e;
    private static final long f;
    private static volatile long g = 0;
    private static final long serialVersionUID = -5851777807851030925L;
    boolean initialized = true;
    private long pad0;
    private long pad1;
    private long pad2;
    private long pad3;
    private long pad4;
    private long pad5;
    private long pad6;
    private long pad7;
    private long rnd;

    private static long b(long j) {
        long j2 = (j ^ (j >>> 33)) * (-49064778989728563L);
        long j3 = (j2 ^ (j2 >>> 33)) * (-4265267296055464877L);
        return j3 ^ (j3 >>> 33);
    }

    static {
        c = SystemPropertyUtil.getLong("io.netty.initialSeedUniquifier", 0L);
        if (c != 0) {
            d = null;
            e = null;
            f = 0L;
        } else if (((Boolean) AccessController.doPrivileged(new PrivilegedAction<Boolean>() { // from class: io.netty.util.internal.ThreadLocalRandom.1
            /* renamed from: a */
            public Boolean run() {
                return Boolean.valueOf(SystemPropertyUtil.getBoolean("java.util.secureRandomSeed", false));
            }
        })).booleanValue()) {
            e = new LinkedBlockingQueue();
            f = System.nanoTime();
            d = new Thread("initialSeedUniquifierGenerator") { // from class: io.netty.util.internal.ThreadLocalRandom.2
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    byte[] generateSeed = new SecureRandom().generateSeed(8);
                    long unused = ThreadLocalRandom.g = System.nanoTime();
                    ThreadLocalRandom.e.add(Long.valueOf(((generateSeed[0] & 255) << 56) | ((generateSeed[1] & 255) << 48) | ((generateSeed[2] & 255) << 40) | ((generateSeed[3] & 255) << 32) | ((generateSeed[4] & 255) << 24) | ((generateSeed[5] & 255) << 16) | ((generateSeed[6] & 255) << 8) | (generateSeed[7] & 255)));
                }
            };
            d.setDaemon(true);
            d.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() { // from class: io.netty.util.internal.ThreadLocalRandom.3
                @Override // java.lang.Thread.UncaughtExceptionHandler
                public void uncaughtException(Thread thread, Throwable th) {
                    ThreadLocalRandom.a.debug("An exception has been raised by {}", thread.getName(), th);
                }
            });
            d.start();
        } else {
            c = b(System.currentTimeMillis()) ^ b(System.nanoTime());
            d = null;
            e = null;
            f = 0L;
        }
    }

    public static void setInitialSeedUniquifier(long j) {
        c = j;
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x003f, code lost:
        r4 = r10.longValue();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static long getInitialSeedUniquifier() {
        /*
            long r0 = io.netty.util.internal.ThreadLocalRandom.c
            r2 = 0
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 == 0) goto L_0x0009
            return r0
        L_0x0009:
            java.lang.Class<io.netty.util.internal.ThreadLocalRandom> r0 = io.netty.util.internal.ThreadLocalRandom.class
            monitor-enter(r0)
            long r4 = io.netty.util.internal.ThreadLocalRandom.c     // Catch: all -> 0x008c
            int r1 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r1 == 0) goto L_0x0014
            monitor-exit(r0)     // Catch: all -> 0x008c
            return r4
        L_0x0014:
            long r6 = io.netty.util.internal.ThreadLocalRandom.f     // Catch: all -> 0x008c
            java.util.concurrent.TimeUnit r1 = java.util.concurrent.TimeUnit.SECONDS     // Catch: all -> 0x008c
            r8 = 3
            long r10 = r1.toNanos(r8)     // Catch: all -> 0x008c
            long r6 = r6 + r10
            r1 = 0
        L_0x0020:
            long r10 = java.lang.System.nanoTime()     // Catch: all -> 0x008c
            long r10 = r6 - r10
            int r12 = (r10 > r2 ? 1 : (r10 == r2 ? 0 : -1))
            if (r12 > 0) goto L_0x0033
            java.util.concurrent.BlockingQueue<java.lang.Long> r10 = io.netty.util.internal.ThreadLocalRandom.e     // Catch: InterruptedException -> 0x0057, all -> 0x008c
            java.lang.Object r10 = r10.poll()     // Catch: InterruptedException -> 0x0057, all -> 0x008c
            java.lang.Long r10 = (java.lang.Long) r10     // Catch: InterruptedException -> 0x0057, all -> 0x008c
            goto L_0x003d
        L_0x0033:
            java.util.concurrent.BlockingQueue<java.lang.Long> r13 = io.netty.util.internal.ThreadLocalRandom.e     // Catch: InterruptedException -> 0x0057, all -> 0x008c
            java.util.concurrent.TimeUnit r14 = java.util.concurrent.TimeUnit.NANOSECONDS     // Catch: InterruptedException -> 0x0057, all -> 0x008c
            java.lang.Object r10 = r13.poll(r10, r14)     // Catch: InterruptedException -> 0x0057, all -> 0x008c
            java.lang.Long r10 = (java.lang.Long) r10     // Catch: InterruptedException -> 0x0057, all -> 0x008c
        L_0x003d:
            if (r10 == 0) goto L_0x0044
            long r4 = r10.longValue()     // Catch: InterruptedException -> 0x0057, all -> 0x008c
            goto L_0x005f
        L_0x0044:
            if (r12 > 0) goto L_0x0020
            java.lang.Thread r6 = io.netty.util.internal.ThreadLocalRandom.d     // Catch: all -> 0x008c
            r6.interrupt()     // Catch: all -> 0x008c
            io.netty.util.internal.logging.InternalLogger r6 = io.netty.util.internal.ThreadLocalRandom.a     // Catch: all -> 0x008c
            java.lang.String r7 = "Failed to generate a seed from SecureRandom within {} seconds. Not enough entropy?"
            java.lang.Long r8 = java.lang.Long.valueOf(r8)     // Catch: all -> 0x008c
            r6.warn(r7, r8)     // Catch: all -> 0x008c
            goto L_0x005f
        L_0x0057:
            r1 = 1
            io.netty.util.internal.logging.InternalLogger r6 = io.netty.util.internal.ThreadLocalRandom.a     // Catch: all -> 0x008c
            java.lang.String r7 = "Failed to generate a seed from SecureRandom due to an InterruptedException."
            r6.warn(r7)     // Catch: all -> 0x008c
        L_0x005f:
            r6 = 3627065505421648153(0x3255ecdc33bae119, double:3.253008663204319E-66)
            long r4 = r4 ^ r6
            long r6 = java.lang.System.nanoTime()     // Catch: all -> 0x008c
            long r6 = java.lang.Long.reverse(r6)     // Catch: all -> 0x008c
            long r4 = r4 ^ r6
            io.netty.util.internal.ThreadLocalRandom.c = r4     // Catch: all -> 0x008c
            if (r1 == 0) goto L_0x007e
            java.lang.Thread r1 = java.lang.Thread.currentThread()     // Catch: all -> 0x008c
            r1.interrupt()     // Catch: all -> 0x008c
            java.lang.Thread r1 = io.netty.util.internal.ThreadLocalRandom.d     // Catch: all -> 0x008c
            r1.interrupt()     // Catch: all -> 0x008c
        L_0x007e:
            long r6 = io.netty.util.internal.ThreadLocalRandom.g     // Catch: all -> 0x008c
            int r1 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r1 != 0) goto L_0x008a
            long r1 = java.lang.System.nanoTime()     // Catch: all -> 0x008c
            io.netty.util.internal.ThreadLocalRandom.g = r1     // Catch: all -> 0x008c
        L_0x008a:
            monitor-exit(r0)     // Catch: all -> 0x008c
            return r4
        L_0x008c:
            r1 = move-exception
            monitor-exit(r0)     // Catch: all -> 0x008c
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.util.internal.ThreadLocalRandom.getInitialSeedUniquifier():long");
    }

    private static long c() {
        long j;
        int i;
        long initialSeedUniquifier;
        long j2;
        do {
            j = b.get();
            i = (j > 0L ? 1 : (j == 0L ? 0 : -1));
            initialSeedUniquifier = i != 0 ? j : getInitialSeedUniquifier();
            j2 = 181783497276652981L * initialSeedUniquifier;
        } while (!b.compareAndSet(j, j2));
        if (i == 0 && a.isDebugEnabled()) {
            if (g != 0) {
                a.debug(String.format("-Dio.netty.initialSeedUniquifier: 0x%016x (took %d ms)", Long.valueOf(initialSeedUniquifier), Long.valueOf(TimeUnit.NANOSECONDS.toMillis(g - f))));
            } else {
                a.debug(String.format("-Dio.netty.initialSeedUniquifier: 0x%016x", Long.valueOf(initialSeedUniquifier)));
            }
        }
        return System.nanoTime() ^ j2;
    }

    public ThreadLocalRandom() {
        super(c());
    }

    public static ThreadLocalRandom current() {
        return InternalThreadLocalMap.get().random();
    }

    @Override // java.util.Random
    public void setSeed(long j) {
        if (!this.initialized) {
            this.rnd = (j ^ 25214903917L) & 281474976710655L;
            return;
        }
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Random
    protected int next(int i) {
        this.rnd = ((this.rnd * 25214903917L) + 11) & 281474976710655L;
        return (int) (this.rnd >>> (48 - i));
    }

    public int nextInt(int i, int i2) {
        if (i < i2) {
            return nextInt(i2 - i) + i;
        }
        throw new IllegalArgumentException();
    }

    public long nextLong(long j) {
        long j2 = 0;
        if (j > 0) {
            while (j >= 2147483647L) {
                int next = next(2);
                long j3 = j >>> 1;
                if ((next & 2) != 0) {
                    j3 = j - j3;
                }
                if ((next & 1) == 0) {
                    j2 += j - j3;
                }
                j = j3;
            }
            return j2 + nextInt((int) j);
        }
        throw new IllegalArgumentException("n must be positive");
    }

    public long nextLong(long j, long j2) {
        if (j < j2) {
            return nextLong(j2 - j) + j;
        }
        throw new IllegalArgumentException();
    }

    public double nextDouble(double d2) {
        if (d2 > 0.0d) {
            return nextDouble() * d2;
        }
        throw new IllegalArgumentException("n must be positive");
    }

    public double nextDouble(double d2, double d3) {
        if (d2 < d3) {
            return (nextDouble() * (d3 - d2)) + d2;
        }
        throw new IllegalArgumentException();
    }
}
