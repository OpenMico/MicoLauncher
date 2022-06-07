package com.xiaomi.mico.utils;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.ExecutorService;

/* loaded from: classes3.dex */
public class Threads {
    private static ThreadsImplementation a = new ThreadsImplementation();

    private Threads() {
        a = new ThreadsImplementation();
    }

    public static ExecutorService getIoThreadPool() {
        return a.a();
    }

    public static int getMaxThreadsOfIoThreadPool() {
        return a.b();
    }

    public static ExecutorService getComputationThreadPool() {
        return a.c();
    }

    public static int getMaxThreadsOfComputationThreadPool() {
        return a.d();
    }

    public static Handler getLightWorkHandler() {
        return a.f();
    }

    public static Handler getHeavyWorkHandler() {
        return a.g();
    }

    public void removeCallbacksInLightWorkThread(Runnable runnable) {
        a.a(runnable);
    }

    public void removeCallbacksInHeavyWorkThread(Runnable runnable) {
        a.b(runnable);
    }

    public static void postInMainThread(Runnable runnable) {
        a.c(runnable);
    }

    public static void postDelayedInMainThread(Runnable runnable, long j) {
        a.a(runnable, j);
    }

    public static void removeCallbacksInMainThread(Runnable runnable) {
        a.d(runnable);
    }

    public static void verifyThread() {
        a.verifyThread();
    }

    public static void verifyMainThread() {
        a.h();
    }

    public static boolean isThread() {
        return Looper.getMainLooper() != Looper.myLooper();
    }

    public static boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    public static LogCallback getLogCallback() {
        return UtilsConfig.getLogCallback();
    }
}
