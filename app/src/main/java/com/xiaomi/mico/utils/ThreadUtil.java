package com.xiaomi.mico.utils;

import android.os.Handler;
import androidx.annotation.NonNull;
import com.xiaomi.mico.utils.ThreadsImplementation;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes3.dex */
public class ThreadUtil {
    private static ExecutorService a = new ThreadPoolExecutor(0, 2, 0, TimeUnit.MINUTES, new LinkedBlockingQueue(), new a("StartupThreadPool_"), new ThreadPoolExecutor.AbortPolicy());

    public static ExecutorService getIoThreadPool() {
        return Threads.getIoThreadPool();
    }

    public static int getMaxThreadsOfIoThreadPool() {
        return Threads.getMaxThreadsOfIoThreadPool();
    }

    public static void destroyStartupPool() {
        a.shutdown();
        a = null;
    }

    public static ExecutorService getComputationThreadPool() {
        return Threads.getComputationThreadPool();
    }

    public static ExecutorService getStartupThreadPool() {
        return a;
    }

    public static int getMaxThreadsOfComputationThreadPool() {
        return Threads.getMaxThreadsOfComputationThreadPool();
    }

    public static Handler getLightWorkHandler() {
        return Threads.getLightWorkHandler();
    }

    public static Handler getWorkHandler() {
        return Threads.getHeavyWorkHandler();
    }

    public static void postInMainThread(Runnable runnable) {
        Threads.postInMainThread(runnable);
    }

    public static void postDelayedInMainThread(Runnable runnable, long j) {
        Threads.postDelayedInMainThread(runnable, j);
    }

    public static void removeCallbacksInMainThread(Runnable runnable) {
        Threads.removeCallbacksInMainThread(runnable);
    }

    public static void verifyThread() {
        Threads.verifyThread();
    }

    public static void verifyMainThread() {
        Threads.verifyMainThread();
    }

    public static boolean isThread() {
        return Threads.isThread();
    }

    public static boolean isMainThread() {
        return Threads.isMainThread();
    }

    /* loaded from: classes3.dex */
    public static class SameThreadChecker {
        ThreadsImplementation.SameThreadChecker a;

        public static SameThreadChecker get(String str) {
            return new SameThreadChecker(str);
        }

        private SameThreadChecker(String str) {
            this.a = ThreadsImplementation.SameThreadChecker.get(str);
        }

        public void setupThread() {
            this.a.setupThread();
        }

        public void check() {
            this.a.check();
        }

        public boolean notSameThread() {
            return this.a.notSameThread();
        }
    }

    /* loaded from: classes3.dex */
    private static class a implements ThreadFactory {
        private final AtomicInteger a = new AtomicInteger(0);
        private final String b;

        a(String str) {
            this.b = str;
        }

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(@NonNull Runnable runnable) {
            Thread thread = new Thread(runnable, this.b + this.a.incrementAndGet());
            thread.setPriority(5);
            return thread;
        }
    }
}
