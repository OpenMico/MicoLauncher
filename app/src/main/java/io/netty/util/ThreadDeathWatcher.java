package io.netty.util;

import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.internal.MpscLinkedQueueNode;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes4.dex */
public final class ThreadDeathWatcher {
    private static final ThreadFactory b;
    private static volatile Thread f;
    private static final InternalLogger a = InternalLoggerFactory.getInstance(ThreadDeathWatcher.class);
    private static final Queue<a> c = PlatformDependent.newMpscQueue();
    private static final b d = new b();
    private static final AtomicBoolean e = new AtomicBoolean();

    static {
        String str = "threadDeathWatcher";
        String str2 = SystemPropertyUtil.get("io.netty.serviceThreadPrefix");
        if (!StringUtil.isNullOrEmpty(str2)) {
            str = str2 + str;
        }
        b = new DefaultThreadFactory(str, true, 1);
    }

    public static void watch(Thread thread, Runnable runnable) {
        if (thread == null) {
            throw new NullPointerException("thread");
        } else if (runnable == null) {
            throw new NullPointerException("task");
        } else if (thread.isAlive()) {
            a(thread, runnable, true);
        } else {
            throw new IllegalArgumentException("thread must be alive.");
        }
    }

    public static void unwatch(Thread thread, Runnable runnable) {
        if (thread == null) {
            throw new NullPointerException("thread");
        } else if (runnable != null) {
            a(thread, runnable, false);
        } else {
            throw new NullPointerException("task");
        }
    }

    private static void a(Thread thread, Runnable runnable, boolean z) {
        c.add(new a(thread, runnable, z));
        if (e.compareAndSet(false, true)) {
            Thread newThread = b.newThread(d);
            newThread.start();
            f = newThread;
        }
    }

    public static boolean awaitInactivity(long j, TimeUnit timeUnit) throws InterruptedException {
        if (timeUnit != null) {
            Thread thread = f;
            if (thread == null) {
                return true;
            }
            thread.join(timeUnit.toMillis(j));
            return !thread.isAlive();
        }
        throw new NullPointerException("unit");
    }

    private ThreadDeathWatcher() {
    }

    /* loaded from: classes4.dex */
    public static final class b implements Runnable {
        static final /* synthetic */ boolean a = !ThreadDeathWatcher.class.desiredAssertionStatus();
        private final List<a> b;

        private b() {
            this.b = new ArrayList();
        }

        @Override // java.lang.Runnable
        public void run() {
            while (true) {
                a();
                b();
                a();
                b();
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException unused) {
                }
                if (this.b.isEmpty() && ThreadDeathWatcher.c.isEmpty()) {
                    boolean compareAndSet = ThreadDeathWatcher.e.compareAndSet(true, false);
                    if (!a && !compareAndSet) {
                        throw new AssertionError();
                    } else if (ThreadDeathWatcher.c.isEmpty() || !ThreadDeathWatcher.e.compareAndSet(false, true)) {
                        return;
                    }
                }
            }
        }

        private void a() {
            while (true) {
                a aVar = (a) ThreadDeathWatcher.c.poll();
                if (aVar != null) {
                    if (aVar.c) {
                        this.b.add(aVar);
                    } else {
                        this.b.remove(aVar);
                    }
                } else {
                    return;
                }
            }
        }

        private void b() {
            List<a> list = this.b;
            int i = 0;
            while (i < list.size()) {
                a aVar = list.get(i);
                if (!aVar.a.isAlive()) {
                    list.remove(i);
                    try {
                        aVar.b.run();
                    } catch (Throwable th) {
                        ThreadDeathWatcher.a.warn("Thread death watcher task raised an exception:", th);
                    }
                } else {
                    i++;
                }
            }
        }
    }

    /* loaded from: classes4.dex */
    public static final class a extends MpscLinkedQueueNode<a> {
        final Thread a;
        final Runnable b;
        final boolean c;

        /* renamed from: a */
        public a value() {
            return this;
        }

        a(Thread thread, Runnable runnable, boolean z) {
            this.a = thread;
            this.b = runnable;
            this.c = z;
        }

        public int hashCode() {
            return this.a.hashCode() ^ this.b.hashCode();
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof a)) {
                return false;
            }
            a aVar = (a) obj;
            return this.a == aVar.a && this.b == aVar.b;
        }
    }
}
