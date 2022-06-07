package io.netty.util.concurrent;

import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.StringUtil;
import java.util.Locale;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes4.dex */
public class DefaultThreadFactory implements ThreadFactory {
    private static final AtomicInteger a = new AtomicInteger();
    private final AtomicInteger b;
    private final String c;
    private final boolean d;
    private final int e;
    private final ThreadGroup f;

    public DefaultThreadFactory(Class<?> cls) {
        this(cls, false, 5);
    }

    public DefaultThreadFactory(String str) {
        this(str, false, 5);
    }

    public DefaultThreadFactory(Class<?> cls, boolean z) {
        this(cls, z, 5);
    }

    public DefaultThreadFactory(String str, boolean z) {
        this(str, z, 5);
    }

    public DefaultThreadFactory(Class<?> cls, int i) {
        this(cls, false, i);
    }

    public DefaultThreadFactory(String str, int i) {
        this(str, false, i);
    }

    public DefaultThreadFactory(Class<?> cls, boolean z, int i) {
        this(a(cls), z, i);
    }

    private static String a(Class<?> cls) {
        if (cls != null) {
            String simpleClassName = StringUtil.simpleClassName(cls);
            switch (simpleClassName.length()) {
                case 0:
                    return "unknown";
                case 1:
                    return simpleClassName.toLowerCase(Locale.US);
                default:
                    if (!Character.isUpperCase(simpleClassName.charAt(0)) || !Character.isLowerCase(simpleClassName.charAt(1))) {
                        return simpleClassName;
                    }
                    return Character.toLowerCase(simpleClassName.charAt(0)) + simpleClassName.substring(1);
            }
        } else {
            throw new NullPointerException("poolType");
        }
    }

    public DefaultThreadFactory(String str, boolean z, int i, ThreadGroup threadGroup) {
        this.b = new AtomicInteger();
        if (str == null) {
            throw new NullPointerException("poolName");
        } else if (i < 1 || i > 10) {
            throw new IllegalArgumentException("priority: " + i + " (expected: Thread.MIN_PRIORITY <= priority <= Thread.MAX_PRIORITY)");
        } else {
            this.c = str + '-' + a.incrementAndGet() + '-';
            this.d = z;
            this.e = i;
            this.f = (ThreadGroup) ObjectUtil.checkNotNull(threadGroup, "threadGroup");
        }
    }

    public DefaultThreadFactory(String str, boolean z, int i) {
        this(str, z, i, Thread.currentThread().getThreadGroup());
    }

    @Override // java.util.concurrent.ThreadFactory
    public Thread newThread(Runnable runnable) {
        a aVar = new a(runnable);
        Thread newThread = newThread(aVar, this.c + this.b.incrementAndGet());
        try {
            if (newThread.isDaemon()) {
                if (!this.d) {
                    newThread.setDaemon(false);
                }
            } else if (this.d) {
                newThread.setDaemon(true);
            }
            if (newThread.getPriority() != this.e) {
                newThread.setPriority(this.e);
            }
        } catch (Exception unused) {
        }
        return newThread;
    }

    protected Thread newThread(Runnable runnable, String str) {
        return new FastThreadLocalThread(this.f, runnable, str);
    }

    /* loaded from: classes4.dex */
    private static final class a implements Runnable {
        private final Runnable a;

        a(Runnable runnable) {
            this.a = runnable;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                this.a.run();
            } finally {
                FastThreadLocal.removeAll();
            }
        }
    }
}
