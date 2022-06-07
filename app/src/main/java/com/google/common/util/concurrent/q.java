package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.concurrent.GuardedBy;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.logging.Logger;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: SequentialExecutor.java */
@GwtIncompatible
/* loaded from: classes2.dex */
public final class q implements Executor {
    private static final Logger a = Logger.getLogger(q.class.getName());
    private final Executor b;
    @GuardedBy("queue")
    private final Deque<Runnable> c = new ArrayDeque();
    @GuardedBy("queue")
    private b d = b.IDLE;
    @GuardedBy("queue")
    private long e = 0;
    private final a f = new a();

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: SequentialExecutor.java */
    /* loaded from: classes2.dex */
    public enum b {
        IDLE,
        QUEUING,
        QUEUED,
        RUNNING
    }

    static /* synthetic */ long c(q qVar) {
        long j = qVar.e;
        qVar.e = 1 + j;
        return j;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public q(Executor executor) {
        this.b = (Executor) Preconditions.checkNotNull(executor);
    }

    @Override // java.util.concurrent.Executor
    public void execute(final Runnable runnable) {
        Preconditions.checkNotNull(runnable);
        synchronized (this.c) {
            if (!(this.d == b.RUNNING || this.d == b.QUEUED)) {
                long j = this.e;
                Runnable runnable2 = new Runnable() { // from class: com.google.common.util.concurrent.q.1
                    @Override // java.lang.Runnable
                    public void run() {
                        runnable.run();
                    }
                };
                this.c.add(runnable2);
                this.d = b.QUEUING;
                boolean z = true;
                try {
                    this.b.execute(this.f);
                    if (this.d == b.QUEUING) {
                        z = false;
                    }
                    if (!z) {
                        synchronized (this.c) {
                            if (this.e == j && this.d == b.QUEUING) {
                                this.d = b.QUEUED;
                            }
                        }
                        return;
                    }
                    return;
                } catch (Error | RuntimeException e) {
                    synchronized (this.c) {
                        if ((this.d != b.IDLE && this.d != b.QUEUING) || !this.c.removeLastOccurrence(runnable2)) {
                            z = false;
                        }
                        if (!(e instanceof RejectedExecutionException) || z) {
                            throw e;
                        }
                    }
                    return;
                }
            }
            this.c.add(runnable);
        }
    }

    /* compiled from: SequentialExecutor.java */
    /* loaded from: classes2.dex */
    private final class a implements Runnable {
        private a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                a();
            } catch (Error e) {
                synchronized (q.this.c) {
                    q.this.d = b.IDLE;
                    throw e;
                }
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:17:0x0043, code lost:
            if (r1 == false) goto L_?;
         */
        /* JADX WARN: Code restructure failed: missing block: B:18:0x0045, code lost:
            java.lang.Thread.currentThread().interrupt();
         */
        /* JADX WARN: Code restructure failed: missing block: B:19:0x004c, code lost:
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:22:0x0052, code lost:
            r1 = r1 | java.lang.Thread.interrupted();
         */
        /* JADX WARN: Code restructure failed: missing block: B:23:0x0053, code lost:
            r3.run();
         */
        /* JADX WARN: Code restructure failed: missing block: B:24:0x0057, code lost:
            r2 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:25:0x0058, code lost:
            com.google.common.util.concurrent.q.a.log(java.util.logging.Level.SEVERE, "Exception while executing runnable " + r3, (java.lang.Throwable) r2);
         */
        /* JADX WARN: Code restructure failed: missing block: B:39:?, code lost:
            return;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private void a() {
            /*
                r8 = this;
                r0 = 0
                r1 = r0
            L_0x0002:
                com.google.common.util.concurrent.q r2 = com.google.common.util.concurrent.q.this     // Catch: all -> 0x0076
                java.util.Deque r2 = com.google.common.util.concurrent.q.a(r2)     // Catch: all -> 0x0076
                monitor-enter(r2)     // Catch: all -> 0x0076
                if (r0 != 0) goto L_0x002d
                com.google.common.util.concurrent.q r0 = com.google.common.util.concurrent.q.this     // Catch: all -> 0x0073
                com.google.common.util.concurrent.q$b r0 = com.google.common.util.concurrent.q.b(r0)     // Catch: all -> 0x0073
                com.google.common.util.concurrent.q$b r3 = com.google.common.util.concurrent.q.b.RUNNING     // Catch: all -> 0x0073
                if (r0 != r3) goto L_0x0020
                monitor-exit(r2)     // Catch: all -> 0x0073
                if (r1 == 0) goto L_0x001f
                java.lang.Thread r0 = java.lang.Thread.currentThread()
                r0.interrupt()
            L_0x001f:
                return
            L_0x0020:
                com.google.common.util.concurrent.q r0 = com.google.common.util.concurrent.q.this     // Catch: all -> 0x0073
                com.google.common.util.concurrent.q.c(r0)     // Catch: all -> 0x0073
                com.google.common.util.concurrent.q r0 = com.google.common.util.concurrent.q.this     // Catch: all -> 0x0073
                com.google.common.util.concurrent.q$b r3 = com.google.common.util.concurrent.q.b.RUNNING     // Catch: all -> 0x0073
                com.google.common.util.concurrent.q.a(r0, r3)     // Catch: all -> 0x0073
                r0 = 1
            L_0x002d:
                com.google.common.util.concurrent.q r3 = com.google.common.util.concurrent.q.this     // Catch: all -> 0x0073
                java.util.Deque r3 = com.google.common.util.concurrent.q.a(r3)     // Catch: all -> 0x0073
                java.lang.Object r3 = r3.poll()     // Catch: all -> 0x0073
                java.lang.Runnable r3 = (java.lang.Runnable) r3     // Catch: all -> 0x0073
                if (r3 != 0) goto L_0x004d
                com.google.common.util.concurrent.q r0 = com.google.common.util.concurrent.q.this     // Catch: all -> 0x0073
                com.google.common.util.concurrent.q$b r3 = com.google.common.util.concurrent.q.b.IDLE     // Catch: all -> 0x0073
                com.google.common.util.concurrent.q.a(r0, r3)     // Catch: all -> 0x0073
                monitor-exit(r2)     // Catch: all -> 0x0073
                if (r1 == 0) goto L_0x004c
                java.lang.Thread r0 = java.lang.Thread.currentThread()
                r0.interrupt()
            L_0x004c:
                return
            L_0x004d:
                monitor-exit(r2)     // Catch: all -> 0x0073
                boolean r2 = java.lang.Thread.interrupted()     // Catch: all -> 0x0076
                r1 = r1 | r2
                r3.run()     // Catch: RuntimeException -> 0x0057, all -> 0x0076
                goto L_0x0002
            L_0x0057:
                r2 = move-exception
                java.util.logging.Logger r4 = com.google.common.util.concurrent.q.a()     // Catch: all -> 0x0076
                java.util.logging.Level r5 = java.util.logging.Level.SEVERE     // Catch: all -> 0x0076
                java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: all -> 0x0076
                r6.<init>()     // Catch: all -> 0x0076
                java.lang.String r7 = "Exception while executing runnable "
                r6.append(r7)     // Catch: all -> 0x0076
                r6.append(r3)     // Catch: all -> 0x0076
                java.lang.String r3 = r6.toString()     // Catch: all -> 0x0076
                r4.log(r5, r3, r2)     // Catch: all -> 0x0076
                goto L_0x0002
            L_0x0073:
                r0 = move-exception
                monitor-exit(r2)     // Catch: all -> 0x0073
                throw r0     // Catch: all -> 0x0076
            L_0x0076:
                r0 = move-exception
                if (r1 == 0) goto L_0x0080
                java.lang.Thread r1 = java.lang.Thread.currentThread()
                r1.interrupt()
            L_0x0080:
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.q.a.a():void");
        }
    }
}
