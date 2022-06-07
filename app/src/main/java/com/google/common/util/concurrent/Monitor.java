package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.errorprone.annotations.concurrent.GuardedBy;
import com.google.j2objc.annotations.Weak;
import com.milink.base.contract.LockContract;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Beta
@GwtIncompatible
/* loaded from: classes2.dex */
public final class Monitor {
    private final boolean a;
    private final ReentrantLock b;
    @GuardedBy(LockContract.Matcher.LOCK)
    private Guard c;

    @Beta
    /* loaded from: classes2.dex */
    public static abstract class Guard {
        @Weak
        final Monitor b;
        final Condition c;
        @GuardedBy("monitor.lock")
        int d = 0;
        @NullableDecl
        @GuardedBy("monitor.lock")
        Guard e;

        public abstract boolean isSatisfied();

        /* JADX INFO: Access modifiers changed from: protected */
        public Guard(Monitor monitor) {
            this.b = (Monitor) Preconditions.checkNotNull(monitor, "monitor");
            this.c = monitor.b.newCondition();
        }
    }

    public Monitor() {
        this(false);
    }

    public Monitor(boolean z) {
        this.c = null;
        this.a = z;
        this.b = new ReentrantLock(z);
    }

    public void enter() {
        this.b.lock();
    }

    public boolean enter(long j, TimeUnit timeUnit) {
        Throwable th;
        boolean tryLock;
        long a = a(j, timeUnit);
        ReentrantLock reentrantLock = this.b;
        if (!this.a && reentrantLock.tryLock()) {
            return true;
        }
        boolean interrupted = Thread.interrupted();
        try {
            long nanoTime = System.nanoTime();
            long j2 = a;
            while (true) {
                try {
                    try {
                        tryLock = reentrantLock.tryLock(j2, TimeUnit.NANOSECONDS);
                        break;
                    } catch (Throwable th2) {
                        th = th2;
                        interrupted = true;
                        if (interrupted) {
                            Thread.currentThread().interrupt();
                        }
                        throw th;
                    }
                } catch (InterruptedException unused) {
                    j2 = a(nanoTime, a);
                    interrupted = true;
                }
            }
            if (interrupted) {
                Thread.currentThread().interrupt();
            }
            return tryLock;
        } catch (Throwable th3) {
            th = th3;
        }
    }

    public void enterInterruptibly() throws InterruptedException {
        this.b.lockInterruptibly();
    }

    public boolean enterInterruptibly(long j, TimeUnit timeUnit) throws InterruptedException {
        return this.b.tryLock(j, timeUnit);
    }

    public boolean tryEnter() {
        return this.b.tryLock();
    }

    public void enterWhen(Guard guard) throws InterruptedException {
        if (guard.b == this) {
            ReentrantLock reentrantLock = this.b;
            boolean isHeldByCurrentThread = reentrantLock.isHeldByCurrentThread();
            reentrantLock.lockInterruptibly();
            try {
                if (!guard.isSatisfied()) {
                    a(guard, isHeldByCurrentThread);
                }
            } catch (Throwable th) {
                leave();
                throw th;
            }
        } else {
            throw new IllegalMonitorStateException();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0047, code lost:
        if (a(r11, r0, r3) != false) goto L_0x0049;
     */
    /* JADX WARN: Removed duplicated region for block: B:18:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x004c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean enterWhen(com.google.common.util.concurrent.Monitor.Guard r11, long r12, java.util.concurrent.TimeUnit r14) throws java.lang.InterruptedException {
        /*
            r10 = this;
            long r0 = a(r12, r14)
            com.google.common.util.concurrent.Monitor r2 = r11.b
            if (r2 != r10) goto L_0x0060
            java.util.concurrent.locks.ReentrantLock r2 = r10.b
            boolean r3 = r2.isHeldByCurrentThread()
            boolean r4 = r10.a
            r5 = 0
            r6 = 0
            if (r4 != 0) goto L_0x0029
            boolean r4 = java.lang.Thread.interrupted()
            if (r4 != 0) goto L_0x0023
            boolean r4 = r2.tryLock()
            if (r4 == 0) goto L_0x0029
            r8 = r6
            goto L_0x0034
        L_0x0023:
            java.lang.InterruptedException r11 = new java.lang.InterruptedException
            r11.<init>()
            throw r11
        L_0x0029:
            long r8 = a(r0)
            boolean r12 = r2.tryLock(r12, r14)
            if (r12 != 0) goto L_0x0034
            return r5
        L_0x0034:
            boolean r12 = r11.isSatisfied()     // Catch: all -> 0x0050
            if (r12 != 0) goto L_0x0049
            int r12 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
            if (r12 != 0) goto L_0x003f
            goto L_0x0043
        L_0x003f:
            long r0 = a(r8, r0)     // Catch: all -> 0x0050
        L_0x0043:
            boolean r11 = r10.a(r11, r0, r3)     // Catch: all -> 0x0050
            if (r11 == 0) goto L_0x004a
        L_0x0049:
            r5 = 1
        L_0x004a:
            if (r5 != 0) goto L_0x004f
            r2.unlock()
        L_0x004f:
            return r5
        L_0x0050:
            r11 = move-exception
            if (r3 != 0) goto L_0x005c
            r10.a()     // Catch: all -> 0x0057
            goto L_0x005c
        L_0x0057:
            r11 = move-exception
            r2.unlock()
            throw r11
        L_0x005c:
            r2.unlock()
            throw r11
        L_0x0060:
            java.lang.IllegalMonitorStateException r11 = new java.lang.IllegalMonitorStateException
            r11.<init>()
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.Monitor.enterWhen(com.google.common.util.concurrent.Monitor$Guard, long, java.util.concurrent.TimeUnit):boolean");
    }

    public void enterWhenUninterruptibly(Guard guard) {
        if (guard.b == this) {
            ReentrantLock reentrantLock = this.b;
            boolean isHeldByCurrentThread = reentrantLock.isHeldByCurrentThread();
            reentrantLock.lock();
            try {
                if (!guard.isSatisfied()) {
                    b(guard, isHeldByCurrentThread);
                }
            } catch (Throwable th) {
                leave();
                throw th;
            }
        } else {
            throw new IllegalMonitorStateException();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0037  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x004b A[Catch: all -> 0x0073, TRY_ENTER, TRY_LEAVE, TryCatch #1 {all -> 0x0073, blocks: (B:5:0x0013, B:7:0x001a, B:11:0x0023, B:13:0x0028, B:24:0x004b, B:15:0x0030, B:20:0x003b, B:21:0x0041, B:22:0x0045), top: B:42:0x0013 }] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0050  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean enterWhenUninterruptibly(com.google.common.util.concurrent.Monitor.Guard r12, long r13, java.util.concurrent.TimeUnit r15) {
        /*
            r11 = this;
            long r13 = a(r13, r15)
            com.google.common.util.concurrent.Monitor r15 = r12.b
            if (r15 != r11) goto L_0x007e
            java.util.concurrent.locks.ReentrantLock r15 = r11.b
            boolean r0 = r15.isHeldByCurrentThread()
            boolean r1 = java.lang.Thread.interrupted()
            r2 = 1
            boolean r3 = r11.a     // Catch: all -> 0x0073
            r4 = 0
            r5 = 0
            if (r3 != 0) goto L_0x0023
            boolean r3 = r15.tryLock()     // Catch: all -> 0x0073
            if (r3 != 0) goto L_0x0021
            goto L_0x0023
        L_0x0021:
            r7 = r5
            goto L_0x0030
        L_0x0023:
            long r7 = a(r13)     // Catch: all -> 0x0073
            r9 = r13
        L_0x0028:
            java.util.concurrent.TimeUnit r3 = java.util.concurrent.TimeUnit.NANOSECONDS     // Catch: InterruptedException -> 0x006a, all -> 0x0073
            boolean r3 = r15.tryLock(r9, r3)     // Catch: InterruptedException -> 0x006a, all -> 0x0073
            if (r3 == 0) goto L_0x0060
        L_0x0030:
            boolean r3 = r12.isSatisfied()     // Catch: InterruptedException -> 0x005d, all -> 0x0058
            if (r3 == 0) goto L_0x0037
            goto L_0x0049
        L_0x0037:
            int r3 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
            if (r3 != 0) goto L_0x0041
            long r7 = a(r13)     // Catch: InterruptedException -> 0x005d, all -> 0x0058
            r9 = r13
            goto L_0x0045
        L_0x0041:
            long r9 = a(r7, r13)     // Catch: InterruptedException -> 0x005d, all -> 0x0058
        L_0x0045:
            boolean r2 = r11.a(r12, r9, r0)     // Catch: InterruptedException -> 0x005d, all -> 0x0058
        L_0x0049:
            if (r2 != 0) goto L_0x004e
            r15.unlock()     // Catch: all -> 0x0073
        L_0x004e:
            if (r1 == 0) goto L_0x0057
            java.lang.Thread r12 = java.lang.Thread.currentThread()
            r12.interrupt()
        L_0x0057:
            return r2
        L_0x0058:
            r12 = move-exception
            r15.unlock()     // Catch: all -> 0x0073
            throw r12     // Catch: all -> 0x0073
        L_0x005d:
            r1 = r2
            r0 = r4
            goto L_0x0030
        L_0x0060:
            if (r1 == 0) goto L_0x0069
            java.lang.Thread r12 = java.lang.Thread.currentThread()
            r12.interrupt()
        L_0x0069:
            return r4
        L_0x006a:
            long r9 = a(r7, r13)     // Catch: all -> 0x0070
            r1 = r2
            goto L_0x0028
        L_0x0070:
            r12 = move-exception
            r1 = r2
            goto L_0x0074
        L_0x0073:
            r12 = move-exception
        L_0x0074:
            if (r1 == 0) goto L_0x007d
            java.lang.Thread r13 = java.lang.Thread.currentThread()
            r13.interrupt()
        L_0x007d:
            throw r12
        L_0x007e:
            java.lang.IllegalMonitorStateException r12 = new java.lang.IllegalMonitorStateException
            r12.<init>()
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.Monitor.enterWhenUninterruptibly(com.google.common.util.concurrent.Monitor$Guard, long, java.util.concurrent.TimeUnit):boolean");
    }

    public boolean enterIf(Guard guard) {
        if (guard.b == this) {
            ReentrantLock reentrantLock = this.b;
            reentrantLock.lock();
            try {
                boolean isSatisfied = guard.isSatisfied();
                if (!isSatisfied) {
                }
                return isSatisfied;
            } finally {
                reentrantLock.unlock();
            }
        } else {
            throw new IllegalMonitorStateException();
        }
    }

    public boolean enterIf(Guard guard, long j, TimeUnit timeUnit) {
        if (guard.b != this) {
            throw new IllegalMonitorStateException();
        } else if (!enter(j, timeUnit)) {
            return false;
        } else {
            try {
                boolean isSatisfied = guard.isSatisfied();
                if (!isSatisfied) {
                }
                return isSatisfied;
            } finally {
                this.b.unlock();
            }
        }
    }

    public boolean enterIfInterruptibly(Guard guard) throws InterruptedException {
        if (guard.b == this) {
            ReentrantLock reentrantLock = this.b;
            reentrantLock.lockInterruptibly();
            try {
                boolean isSatisfied = guard.isSatisfied();
                if (!isSatisfied) {
                }
                return isSatisfied;
            } finally {
                reentrantLock.unlock();
            }
        } else {
            throw new IllegalMonitorStateException();
        }
    }

    public boolean enterIfInterruptibly(Guard guard, long j, TimeUnit timeUnit) throws InterruptedException {
        if (guard.b == this) {
            ReentrantLock reentrantLock = this.b;
            if (!reentrantLock.tryLock(j, timeUnit)) {
                return false;
            }
            try {
                boolean isSatisfied = guard.isSatisfied();
                if (!isSatisfied) {
                }
                return isSatisfied;
            } finally {
                reentrantLock.unlock();
            }
        } else {
            throw new IllegalMonitorStateException();
        }
    }

    public boolean tryEnterIf(Guard guard) {
        if (guard.b == this) {
            ReentrantLock reentrantLock = this.b;
            if (!reentrantLock.tryLock()) {
                return false;
            }
            try {
                boolean isSatisfied = guard.isSatisfied();
                if (!isSatisfied) {
                }
                return isSatisfied;
            } finally {
                reentrantLock.unlock();
            }
        } else {
            throw new IllegalMonitorStateException();
        }
    }

    public void waitFor(Guard guard) throws InterruptedException {
        if (!(guard.b == this) || !this.b.isHeldByCurrentThread()) {
            throw new IllegalMonitorStateException();
        } else if (!guard.isSatisfied()) {
            a(guard, true);
        }
    }

    public boolean waitFor(Guard guard, long j, TimeUnit timeUnit) throws InterruptedException {
        long a = a(j, timeUnit);
        if (!(guard.b == this) || !this.b.isHeldByCurrentThread()) {
            throw new IllegalMonitorStateException();
        } else if (guard.isSatisfied()) {
            return true;
        } else {
            if (!Thread.interrupted()) {
                return a(guard, a, true);
            }
            throw new InterruptedException();
        }
    }

    public void waitForUninterruptibly(Guard guard) {
        if (!(guard.b == this) || !this.b.isHeldByCurrentThread()) {
            throw new IllegalMonitorStateException();
        } else if (!guard.isSatisfied()) {
            b(guard, true);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0051  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean waitForUninterruptibly(com.google.common.util.concurrent.Monitor.Guard r8, long r9, java.util.concurrent.TimeUnit r11) {
        /*
            r7 = this;
            long r9 = a(r9, r11)
            com.google.common.util.concurrent.Monitor r11 = r8.b
            r0 = 0
            r1 = 1
            if (r11 != r7) goto L_0x000c
            r11 = r1
            goto L_0x000d
        L_0x000c:
            r11 = r0
        L_0x000d:
            java.util.concurrent.locks.ReentrantLock r2 = r7.b
            boolean r2 = r2.isHeldByCurrentThread()
            r11 = r11 & r2
            if (r11 == 0) goto L_0x0059
            boolean r11 = r8.isSatisfied()
            if (r11 == 0) goto L_0x001d
            return r1
        L_0x001d:
            long r2 = a(r9)
            boolean r11 = java.lang.Thread.interrupted()
            r4 = r9
            r6 = r11
            r11 = r1
        L_0x0028:
            boolean r8 = r7.a(r8, r4, r11)     // Catch: InterruptedException -> 0x0039, all -> 0x0036
            if (r6 == 0) goto L_0x0035
            java.lang.Thread r9 = java.lang.Thread.currentThread()
            r9.interrupt()
        L_0x0035:
            return r8
        L_0x0036:
            r8 = move-exception
            r1 = r6
            goto L_0x004f
        L_0x0039:
            boolean r11 = r8.isSatisfied()     // Catch: all -> 0x004e
            if (r11 == 0) goto L_0x0047
            java.lang.Thread r8 = java.lang.Thread.currentThread()
            r8.interrupt()
            return r1
        L_0x0047:
            long r4 = a(r2, r9)     // Catch: all -> 0x004e
            r11 = r0
            r6 = r1
            goto L_0x0028
        L_0x004e:
            r8 = move-exception
        L_0x004f:
            if (r1 == 0) goto L_0x0058
            java.lang.Thread r9 = java.lang.Thread.currentThread()
            r9.interrupt()
        L_0x0058:
            throw r8
        L_0x0059:
            java.lang.IllegalMonitorStateException r8 = new java.lang.IllegalMonitorStateException
            r8.<init>()
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.Monitor.waitForUninterruptibly(com.google.common.util.concurrent.Monitor$Guard, long, java.util.concurrent.TimeUnit):boolean");
    }

    public void leave() {
        ReentrantLock reentrantLock = this.b;
        try {
            if (reentrantLock.getHoldCount() == 1) {
                a();
            }
        } finally {
            reentrantLock.unlock();
        }
    }

    public boolean isFair() {
        return this.a;
    }

    public boolean isOccupied() {
        return this.b.isLocked();
    }

    public boolean isOccupiedByCurrentThread() {
        return this.b.isHeldByCurrentThread();
    }

    public int getOccupiedDepth() {
        return this.b.getHoldCount();
    }

    public int getQueueLength() {
        return this.b.getQueueLength();
    }

    public boolean hasQueuedThreads() {
        return this.b.hasQueuedThreads();
    }

    public boolean hasQueuedThread(Thread thread) {
        return this.b.hasQueuedThread(thread);
    }

    public boolean hasWaiters(Guard guard) {
        return getWaitQueueLength(guard) > 0;
    }

    public int getWaitQueueLength(Guard guard) {
        if (guard.b == this) {
            this.b.lock();
            try {
                return guard.d;
            } finally {
                this.b.unlock();
            }
        } else {
            throw new IllegalMonitorStateException();
        }
    }

    private static long a(long j, TimeUnit timeUnit) {
        long nanos = timeUnit.toNanos(j);
        if (nanos <= 0) {
            return 0L;
        }
        if (nanos > 6917529027641081853L) {
            return 6917529027641081853L;
        }
        return nanos;
    }

    private static long a(long j) {
        if (j <= 0) {
            return 0L;
        }
        long nanoTime = System.nanoTime();
        if (nanoTime == 0) {
            return 1L;
        }
        return nanoTime;
    }

    private static long a(long j, long j2) {
        if (j2 <= 0) {
            return 0L;
        }
        return j2 - (System.nanoTime() - j);
    }

    @GuardedBy(LockContract.Matcher.LOCK)
    private void a() {
        for (Guard guard = this.c; guard != null; guard = guard.e) {
            if (a(guard)) {
                guard.c.signal();
                return;
            }
        }
    }

    @GuardedBy(LockContract.Matcher.LOCK)
    private boolean a(Guard guard) {
        try {
            return guard.isSatisfied();
        } catch (Throwable th) {
            b();
            throw Throwables.propagate(th);
        }
    }

    @GuardedBy(LockContract.Matcher.LOCK)
    private void b() {
        for (Guard guard = this.c; guard != null; guard = guard.e) {
            guard.c.signalAll();
        }
    }

    @GuardedBy(LockContract.Matcher.LOCK)
    private void b(Guard guard) {
        int i = guard.d;
        guard.d = i + 1;
        if (i == 0) {
            guard.e = this.c;
            this.c = guard;
        }
    }

    @GuardedBy(LockContract.Matcher.LOCK)
    private void c(Guard guard) {
        int i = guard.d - 1;
        guard.d = i;
        if (i == 0) {
            Guard guard2 = this.c;
            Guard guard3 = null;
            while (guard2 != guard) {
                guard3 = guard2;
                guard2 = guard2.e;
            }
            if (guard3 == null) {
                this.c = guard2.e;
            } else {
                guard3.e = guard2.e;
            }
            guard2.e = null;
        }
    }

    @GuardedBy(LockContract.Matcher.LOCK)
    private void a(Guard guard, boolean z) throws InterruptedException {
        if (z) {
            a();
        }
        b(guard);
        do {
            try {
                guard.c.await();
            } finally {
                c(guard);
            }
        } while (!guard.isSatisfied());
    }

    @GuardedBy(LockContract.Matcher.LOCK)
    private void b(Guard guard, boolean z) {
        if (z) {
            a();
        }
        b(guard);
        do {
            try {
                guard.c.awaitUninterruptibly();
            } finally {
                c(guard);
            }
        } while (!guard.isSatisfied());
    }

    @GuardedBy(LockContract.Matcher.LOCK)
    private boolean a(Guard guard, long j, boolean z) throws InterruptedException {
        boolean z2 = true;
        while (j > 0) {
            if (z2) {
                if (z) {
                    try {
                        a();
                    } finally {
                        if (!z2) {
                            c(guard);
                        }
                    }
                }
                b(guard);
                z2 = false;
            }
            j = guard.c.awaitNanos(j);
            if (guard.isSatisfied()) {
                if (!z2) {
                    c(guard);
                }
                return true;
            }
        }
        return false;
    }
}
