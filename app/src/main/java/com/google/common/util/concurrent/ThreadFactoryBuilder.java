package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.CheckReturnValue;
import java.lang.Thread;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

@CanIgnoreReturnValue
@GwtIncompatible
/* loaded from: classes2.dex */
public final class ThreadFactoryBuilder {
    private String a = null;
    private Boolean b = null;
    private Integer c = null;
    private Thread.UncaughtExceptionHandler d = null;
    private ThreadFactory e = null;

    public ThreadFactoryBuilder setNameFormat(String str) {
        b(str, 0);
        this.a = str;
        return this;
    }

    public ThreadFactoryBuilder setDaemon(boolean z) {
        this.b = Boolean.valueOf(z);
        return this;
    }

    public ThreadFactoryBuilder setPriority(int i) {
        boolean z = false;
        Preconditions.checkArgument(i >= 1, "Thread priority (%s) must be >= %s", i, 1);
        if (i <= 10) {
            z = true;
        }
        Preconditions.checkArgument(z, "Thread priority (%s) must be <= %s", i, 10);
        this.c = Integer.valueOf(i);
        return this;
    }

    public ThreadFactoryBuilder setUncaughtExceptionHandler(Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.d = (Thread.UncaughtExceptionHandler) Preconditions.checkNotNull(uncaughtExceptionHandler);
        return this;
    }

    public ThreadFactoryBuilder setThreadFactory(ThreadFactory threadFactory) {
        this.e = (ThreadFactory) Preconditions.checkNotNull(threadFactory);
        return this;
    }

    @CheckReturnValue
    public ThreadFactory build() {
        return a(this);
    }

    private static ThreadFactory a(ThreadFactoryBuilder threadFactoryBuilder) {
        final String str = threadFactoryBuilder.a;
        final Boolean bool = threadFactoryBuilder.b;
        final Integer num = threadFactoryBuilder.c;
        final Thread.UncaughtExceptionHandler uncaughtExceptionHandler = threadFactoryBuilder.d;
        ThreadFactory threadFactory = threadFactoryBuilder.e;
        final ThreadFactory defaultThreadFactory = threadFactory != null ? threadFactory : Executors.defaultThreadFactory();
        final AtomicLong atomicLong = str != null ? new AtomicLong(0L) : null;
        return new ThreadFactory() { // from class: com.google.common.util.concurrent.ThreadFactoryBuilder.1
            @Override // java.util.concurrent.ThreadFactory
            public Thread newThread(Runnable runnable) {
                Thread newThread = defaultThreadFactory.newThread(runnable);
                String str2 = str;
                if (str2 != null) {
                    newThread.setName(ThreadFactoryBuilder.b(str2, Long.valueOf(atomicLong.getAndIncrement())));
                }
                Boolean bool2 = bool;
                if (bool2 != null) {
                    newThread.setDaemon(bool2.booleanValue());
                }
                Integer num2 = num;
                if (num2 != null) {
                    newThread.setPriority(num2.intValue());
                }
                Thread.UncaughtExceptionHandler uncaughtExceptionHandler2 = uncaughtExceptionHandler;
                if (uncaughtExceptionHandler2 != null) {
                    newThread.setUncaughtExceptionHandler(uncaughtExceptionHandler2);
                }
                return newThread;
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String b(String str, Object... objArr) {
        return String.format(Locale.ROOT, str, objArr);
    }
}
