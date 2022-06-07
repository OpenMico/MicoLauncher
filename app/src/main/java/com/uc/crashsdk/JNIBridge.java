package com.uc.crashsdk;

import android.os.Looper;
import android.os.Process;
import com.google.android.exoplayer2.source.rtsp.RtspMediaSource;
import com.uc.crashsdk.a.a;
import com.uc.crashsdk.a.e;
import com.uc.crashsdk.a.f;
import com.uc.crashsdk.a.g;
import com.uc.crashsdk.export.LogType;
import java.io.File;
import java.util.Locale;

/* compiled from: ProGuard */
/* loaded from: classes2.dex */
public class JNIBridge {
    public static native boolean nativeAddCachedInfo(String str, String str2);

    public static native int nativeAddCallbackInfo(String str, int i, long j, int i2);

    public static native int nativeAddDumpFile(String str, String str2, boolean z, boolean z2, int i, boolean z3);

    public static native void nativeAddHeaderInfo(String str, String str2);

    public static native boolean nativeChangeState(String str, String str2, boolean z);

    public static native void nativeClientCloseConnection(long j);

    public static native long nativeClientCreateConnection(String str, String str2, String str3, int i);

    public static native int nativeClientWriteData(long j, String str);

    public static native void nativeCloseFile(int i);

    public static native long nativeCmd(int i, long j, String str, Object[] objArr);

    public static native void nativeCrash(int i, int i2);

    public static native int nativeCreateCachedInfo(String str, int i, int i2);

    public static native String nativeDumpThreads(String str, long j);

    public static native int nativeGenerateUnexpLog(long j, int i);

    public static native String nativeGet(int i, long j, String str);

    public static native String nativeGetCallbackInfo(String str, long j, int i, boolean z);

    public static native boolean nativeIsCrashing();

    public static native boolean nativeLockFile(int i, boolean z);

    public static native int nativeLog(int i, String str, String str2);

    public static native int nativeOpenFile(String str);

    public static native long nativeSet(int i, long j, String str, Object[] objArr);

    public static native void nativeSetForeground(boolean z);

    private static String onNativeEvent(int i, long j, Object[] objArr) {
        switch (i) {
            case 1:
                return String.valueOf(Runtime.getRuntime().maxMemory());
            case 2:
                return e.d();
            case 3:
                if (objArr == null || objArr.length != 2 || !(objArr[0] instanceof String) || !(objArr[1] instanceof String)) {
                    return null;
                }
                return e.a((String) objArr[0], (String) objArr[1]);
            case 4:
                return e.g();
            case 5:
                a.a(true);
                break;
            case 6:
                return g.d();
            case 7:
                f.a(2, new e(102), RtspMediaSource.DEFAULT_TIMEOUT_MS);
                e.q();
                break;
        }
        return null;
    }

    protected static String getCallbackInfo(String str, boolean z) {
        return a.a(str, z);
    }

    private static String getJavaStackTrace(Thread thread, int i) {
        if (i != 0 && i == Process.myPid()) {
            thread = Looper.getMainLooper().getThread();
        }
        if (thread != null) {
            return e.a(thread.getStackTrace(), "getJavaStackTrace").toString();
        }
        return null;
    }

    private static void onCrashLogGenerated(String str, String str2, String str3, boolean z) {
        boolean equals = e.h().equals(str2);
        boolean equals2 = LogType.NATIVE_TYPE.equals(str3);
        if (!e.E()) {
            if (equals && equals2) {
                try {
                    f.c(true);
                } catch (Throwable th) {
                    g.a(th);
                }
            }
            str = e.a(str);
        }
        d.a(str, str2, str3);
        if (!e.E()) {
            if (z || (!equals && g.s())) {
                e.a(false, false);
            } else if (equals) {
                e.b(equals2);
            }
        }
    }

    private static void onCrashRestarting() {
        d.a(false);
        b.K();
    }

    private static void addHeaderInfo(String str, String str2) {
        a.a(str, str2);
    }

    private static int registerCurrentThread(String str, int i) {
        return a.a(i, str);
    }

    private static int registerInfoCallback(String str, int i, long j, int i2) {
        return a.a(str, i, null, j, i2);
    }

    private static int addDumpFile(String str, String str2, boolean z, boolean z2, int i, boolean z3) {
        return a.a(str, str2, z, z2, i, z3);
    }

    private static int createCachedInfo(String str, int i, int i2) {
        return a.a(str, i, i2);
    }

    private static int addCachedInfo(String str, String str2) {
        return a.b(str, str2);
    }

    private static boolean generateCustomLog(String str, String str2, long j, String str3, String str4, String str5, String str6) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str);
        return e.a(stringBuffer, str2, j, a.c(str3), a.c(str4), a.c(str5), str6);
    }

    private static void onKillProcess(String str, int i, int i2) {
        String str2 = "onKillProcess. SIG: " + i2;
        if (e.a()) {
            a.b("crashsdk", str2);
        } else {
            a.a("crashsdk", str2);
        }
        StringBuilder e = e.e("onKillProcess");
        e.insert(0, String.format(Locale.US, "State in disk: '%s'\n", b.m()));
        e.insert(0, String.format(Locale.US, "SIG: %d, fg: %s, exiting: %s, main process: %s, time: %s\n", Integer.valueOf(i2), Boolean.valueOf(b.y()), Boolean.valueOf(b.r()), Boolean.valueOf(b.C()), e.m()));
        e.insert(0, String.format(Locale.US, "Kill PID: %d (%s) by pid: %d (%s) tid: %d (%s)\n", Integer.valueOf(i), e.a(i), Integer.valueOf(Process.myPid()), e.a(Process.myPid()), Integer.valueOf(Process.myTid()), Thread.currentThread().getName()));
        String sb = e.toString();
        if (e.a()) {
            a.b("crashsdk", sb);
        } else {
            a.a("crashsdk", sb);
        }
        if (!b.I()) {
            g.a(new File(str), sb.getBytes());
        }
    }

    private static String getDatasForClientJavaLog(int i, String str) {
        boolean equals = "$all$".equals(str);
        if (i == 1) {
            if (equals) {
                return a.h();
            }
            return a.a(str);
        } else if (i == 2) {
            if (equals) {
                return a.j();
            }
            return a.a(str, true);
        } else if (i == 3) {
            if (equals) {
                return a.l();
            }
            return a.b(str);
        } else if (i == 4) {
            return e.c(str) ? "1" : "0";
        } else {
            return null;
        }
    }

    private static int onPreClientCustomLog(String str, String str2, boolean z) {
        g.a();
        e.a(false);
        if (e.a(str, str2, z)) {
            return 0;
        }
        e.b(str, str2, z);
        return 1;
    }

    public static long set(int i, boolean z) {
        return nativeSet(i, z ? 1L : 0L, null, null);
    }

    public static long set(int i, long j) {
        return nativeSet(i, j, null, null);
    }

    public static long set(int i, String str) {
        return nativeSet(i, 0L, str, null);
    }

    public static long cmd(int i) {
        return nativeCmd(i, 0L, null, null);
    }

    public static long cmd(int i, String str) {
        return nativeCmd(i, 0L, str, null);
    }
}
