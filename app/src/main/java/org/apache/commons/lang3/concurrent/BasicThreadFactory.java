package org.apache.commons.lang3.concurrent;

import java.lang.Thread;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes5.dex */
public class BasicThreadFactory implements ThreadFactory {
    private final AtomicLong a;
    private final ThreadFactory b;
    private final Thread.UncaughtExceptionHandler c;
    private final String d;
    private final Integer e;
    private final Boolean f;

    private BasicThreadFactory(Builder builder) {
        if (builder.a == null) {
            this.b = Executors.defaultThreadFactory();
        } else {
            this.b = builder.a;
        }
        this.d = builder.c;
        this.e = builder.d;
        this.f = builder.e;
        this.c = builder.b;
        this.a = new AtomicLong();
    }

    public final ThreadFactory getWrappedFactory() {
        return this.b;
    }

    public final String getNamingPattern() {
        return this.d;
    }

    public final Boolean getDaemonFlag() {
        return this.f;
    }

    public final Integer getPriority() {
        return this.e;
    }

    public final Thread.UncaughtExceptionHandler getUncaughtExceptionHandler() {
        return this.c;
    }

    public long getThreadCount() {
        return this.a.get();
    }

    @Override // java.util.concurrent.ThreadFactory
    public Thread newThread(Runnable runnable) {
        Thread newThread = getWrappedFactory().newThread(runnable);
        a(newThread);
        return newThread;
    }

    private void a(Thread thread) {
        if (getNamingPattern() != null) {
            thread.setName(String.format(getNamingPattern(), Long.valueOf(this.a.incrementAndGet())));
        }
        if (getUncaughtExceptionHandler() != null) {
            thread.setUncaughtExceptionHandler(getUncaughtExceptionHandler());
        }
        if (getPriority() != null) {
            thread.setPriority(getPriority().intValue());
        }
        if (getDaemonFlag() != null) {
            thread.setDaemon(getDaemonFlag().booleanValue());
        }
    }

    /* loaded from: classes5.dex */
    public static class Builder implements org.apache.commons.lang3.builder.Builder<BasicThreadFactory> {
        private ThreadFactory a;
        private Thread.UncaughtExceptionHandler b;
        private String c;
        private Integer d;
        private Boolean e;

        public Builder wrappedFactory(ThreadFactory threadFactory) {
            if (threadFactory != null) {
                this.a = threadFactory;
                return this;
            }
            throw new NullPointerException("Wrapped ThreadFactory must not be null!");
        }

        public Builder namingPattern(String str) {
            if (str != null) {
                this.c = str;
                return this;
            }
            throw new NullPointerException("Naming pattern must not be null!");
        }

        public Builder daemon(boolean z) {
            this.e = Boolean.valueOf(z);
            return this;
        }

        public Builder priority(int i) {
            this.d = Integer.valueOf(i);
            return this;
        }

        public Builder uncaughtExceptionHandler(Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
            if (uncaughtExceptionHandler != null) {
                this.b = uncaughtExceptionHandler;
                return this;
            }
            throw new NullPointerException("Uncaught exception handler must not be null!");
        }

        public void reset() {
            this.a = null;
            this.b = null;
            this.c = null;
            this.d = null;
            this.e = null;
        }

        @Override // org.apache.commons.lang3.builder.Builder
        public BasicThreadFactory build() {
            BasicThreadFactory basicThreadFactory = new BasicThreadFactory(this);
            reset();
            return basicThreadFactory;
        }
    }
}
