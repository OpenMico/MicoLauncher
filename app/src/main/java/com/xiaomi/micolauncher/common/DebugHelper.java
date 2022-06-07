package com.xiaomi.micolauncher.common;

import android.os.Environment;
import android.os.SystemClock;
import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.common.build.BuildSettings;
import java.io.File;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes3.dex */
public class DebugHelper {
    private static final File a = new File(Environment.getExternalStorageDirectory(), "monkeyTest");
    private static final File b = new File(Environment.getExternalStorageDirectory(), "NoEnterChildmodeWhenMonkey");
    private static ConcurrentHashMap<Long, Long> c = new ConcurrentHashMap<>();
    private static final AtomicLong d = new AtomicLong(0);

    public static boolean isDebugInConfg() {
        return false;
    }

    public static void printStackTrace(String str) {
        printStackTrace(str, L.base);
    }

    public static void printStackTrace(String str, Logger logger) {
        if (b()) {
            a(str, logger);
        }
    }

    private static void a(String str, Logger logger) {
        a(str, logger, Thread.currentThread().getStackTrace());
    }

    private static void a(String str, Logger logger, StackTraceElement[] stackTraceElementArr) {
        logger.d("%s : stack start------------------------------------", str);
        for (StackTraceElement stackTraceElement : stackTraceElementArr) {
            logger.d("%s %s", str, stackTraceElement);
        }
        logger.d("%s : stack end------------------------------------", str);
    }

    public static long beginTiming() {
        return beginTiming("", new Object[0]);
    }

    public static long beginTiming(String str, Object... objArr) {
        if (c()) {
            return -1L;
        }
        str = "";
        try {
            if (!ContainerUtil.isEmpty(str) && !ContainerUtil.isEmpty(objArr)) {
                str = String.format(str, objArr);
            }
        } catch (Exception e) {
            L.profile.e("beginTiming failed", e);
        }
        if (ContainerUtil.hasData(str)) {
            L.profile.d("performance(%s) starts", str);
        }
        long a2 = a();
        c.put(Long.valueOf(a2), Long.valueOf(SystemClock.elapsedRealtime()));
        return a2;
    }

    private static long a() {
        return d.getAndIncrement();
    }

    public static void endTiming(long j, String str, Object... objArr) {
        endTiming(j, false, str, objArr);
    }

    public static void endTiming(long j, boolean z, String str, Object... objArr) {
        if (!c() && j >= 0) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            Long l = c.get(Long.valueOf(j));
            if (l != null) {
                str = "";
                try {
                    if (!ContainerUtil.isEmpty(str) && !ContainerUtil.isEmpty(objArr)) {
                        str = String.format(str, objArr);
                    }
                } catch (Exception e) {
                    XLog.d("endTiming failed, " + e);
                }
                L.profile.d("performance(%s) starts at %s, end at %s, costs %s millis", str, l, Long.valueOf(elapsedRealtime), Long.valueOf(elapsedRealtime - l.longValue()));
                if (!z) {
                    c.remove(Long.valueOf(j));
                }
            }
        }
    }

    private static boolean b() {
        return isDebugVersion();
    }

    private static boolean c() {
        return !isDebugVersion();
    }

    public static boolean isDebugVersion() {
        return isDebugInConfg() || isDaily();
    }

    public static boolean isDaily() {
        return BuildSettings.IsDailyBuild;
    }

    public static boolean isChangeModeNotExecuted() {
        return b.exists();
    }

    public static boolean isAutomatorRun() {
        return a.exists();
    }
}
