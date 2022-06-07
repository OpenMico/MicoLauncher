package java8.util.concurrent;

import java.lang.Thread;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;
import java8.util.concurrent.ForkJoinPool;

/* loaded from: classes5.dex */
public class ForkJoinWorkerThread extends Thread {
    final ForkJoinPool a;
    final ForkJoinPool.d b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a() {
    }

    protected void onStart() {
    }

    protected void onTermination(Throwable th) {
    }

    protected ForkJoinWorkerThread(ForkJoinPool forkJoinPool) {
        super("aForkJoinWorkerThread");
        this.a = forkJoinPool;
        this.b = forkJoinPool.a(this);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ForkJoinWorkerThread(ForkJoinPool forkJoinPool, ClassLoader classLoader) {
        super("aForkJoinWorkerThread");
        c.a(this, classLoader);
        this.a = forkJoinPool;
        this.b = forkJoinPool.a(this);
    }

    ForkJoinWorkerThread(ForkJoinPool forkJoinPool, ClassLoader classLoader, ThreadGroup threadGroup, AccessControlContext accessControlContext) {
        super(threadGroup, "aForkJoinWorkerThread");
        super.setContextClassLoader(classLoader);
        c.a(this, accessControlContext);
        c.a(this);
        this.a = forkJoinPool;
        this.b = forkJoinPool.a(this);
    }

    public ForkJoinPool getPool() {
        return this.a;
    }

    public int getPoolIndex() {
        return this.b.a();
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        if (this.b.h == null) {
            Throwable th = null;
            try {
                onStart();
                this.a.a(this.b);
                try {
                    onTermination(null);
                } catch (Throwable th2) {
                    this.a.a(this, (Throwable) null);
                    throw th2;
                }
            } catch (Throwable th3) {
                th = th3;
                try {
                    onTermination(th);
                } catch (Throwable th4) {
                    this.a.a(this, th);
                    throw th4;
                }
            }
            this.a.a(this, th);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class a extends ForkJoinWorkerThread {
        private static final ThreadGroup c = (ThreadGroup) AccessController.doPrivileged(new PrivilegedAction<ThreadGroup>() { // from class: java8.util.concurrent.ForkJoinWorkerThread.a.1
            /* renamed from: a */
            public ThreadGroup run() {
                ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
                while (true) {
                    ThreadGroup parent = threadGroup.getParent();
                    if (parent == null) {
                        return new ThreadGroup(threadGroup, "InnocuousForkJoinWorkerThreadGroup");
                    }
                    threadGroup = parent;
                }
            }
        });
        private static final AccessControlContext d = new AccessControlContext(new ProtectionDomain[]{new ProtectionDomain(null, null)});

        @Override // java.lang.Thread
        public void setUncaughtExceptionHandler(Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public a(ForkJoinPool forkJoinPool) {
            super(forkJoinPool, ClassLoader.getSystemClassLoader(), c, d);
        }

        @Override // java8.util.concurrent.ForkJoinWorkerThread
        void a() {
            c.a(this);
        }

        @Override // java.lang.Thread
        public void setContextClassLoader(ClassLoader classLoader) {
            if (classLoader != null && ClassLoader.getSystemClassLoader() != classLoader) {
                throw new SecurityException("setContextClassLoader");
            }
        }
    }
}
