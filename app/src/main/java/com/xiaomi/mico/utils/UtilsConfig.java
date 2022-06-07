package com.xiaomi.mico.utils;

import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class UtilsConfig {
    private static volatile LogCallback a;
    private static volatile boolean b;
    private static volatile int c = (Runtime.getRuntime().availableProcessors() * 2) + 1;
    private static volatile int d = 40;
    private static long e = TimeUnit.SECONDS.toMillis(1);
    private static long f = TimeUnit.SECONDS.toMillis(10);
    private static int g = 0;
    private static int h = Math.max(4, (Runtime.getRuntime().availableProcessors() / 2) + 1);
    private static volatile boolean i = false;
    private static volatile LogCallback j = new LogCallback() { // from class: com.xiaomi.mico.utils.UtilsConfig.1
        @Override // com.xiaomi.mico.utils.LogCallback
        public void d(String str, Throwable th) {
        }

        @Override // com.xiaomi.mico.utils.LogCallback
        public void d(String str, Object... objArr) {
        }

        @Override // com.xiaomi.mico.utils.LogCallback
        public void e(String str, Throwable th) {
        }

        @Override // com.xiaomi.mico.utils.LogCallback
        public void e(String str, Object... objArr) {
        }

        @Override // com.xiaomi.mico.utils.LogCallback
        public void i(String str, Throwable th) {
        }

        @Override // com.xiaomi.mico.utils.LogCallback
        public void i(String str, Object... objArr) {
        }

        @Override // com.xiaomi.mico.utils.LogCallback
        public void printStackTrace(String str) {
        }

        @Override // com.xiaomi.mico.utils.LogCallback
        public void v(String str, Throwable th) {
        }

        @Override // com.xiaomi.mico.utils.LogCallback
        public void v(String str, Object... objArr) {
        }

        @Override // com.xiaomi.mico.utils.LogCallback
        public void w(String str, Throwable th) {
        }

        @Override // com.xiaomi.mico.utils.LogCallback
        public void w(String str, Object... objArr) {
        }
    };

    public static LogCallback getLogCallback() {
        LogCallback logCallback = a;
        return logCallback == null ? j : logCallback;
    }

    public static void setLogCallback(LogCallback logCallback) {
        a = logCallback;
    }

    public static void setCrashOnBugDetected(boolean z) {
        b = z;
    }

    public static boolean isCrashOnBugDetected() {
        return b;
    }

    public static int getComputationThreadsCount() {
        return c;
    }

    public static void setComputationThreadsCount(int i2) {
        c = i2;
    }

    public static int getIoCorePoolSize() {
        return h;
    }

    public static void setIoCorePoolSize(int i2) {
        h = i2;
    }

    public static int getIoThreadsCount() {
        return d;
    }

    public static void setIoThreadsCount(int i2) {
        d = i2;
    }

    public static long getLightWorkMaxDuration() {
        return e;
    }

    public static void setLightWorkMaxDuration(long j2) {
        e = j2;
    }

    public static long getHeavyWorkMaxDuration() {
        return f;
    }

    public static void setHeavyWorkMaxDuration(long j2) {
        f = j2;
    }

    public static int getThreadPriority() {
        return g;
    }

    public static void setThreadPriority(int i2) {
        g = i2;
    }

    public static boolean isEnableTracing() {
        return i;
    }

    public static void setEnableTracing(boolean z) {
        i = z;
    }
}
