package com.blankj.utilcode.util;

import androidx.annotation.NonNull;
import com.blankj.utilcode.util.b;
import java.io.File;
import java.lang.Thread;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/* loaded from: classes.dex */
public final class CrashUtils {
    private static final String a = System.getProperty("file.separator");
    private static final Thread.UncaughtExceptionHandler b = Thread.getDefaultUncaughtExceptionHandler();

    /* loaded from: classes.dex */
    public interface OnCrashListener {
        void onCrash(CrashInfo crashInfo);
    }

    private CrashUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void init() {
        init("");
    }

    public static void init(@NonNull File file) {
        if (file != null) {
            init(file.getAbsolutePath(), (OnCrashListener) null);
            return;
        }
        throw new NullPointerException("Argument 'crashDir' of type File (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void init(String str) {
        init(str, (OnCrashListener) null);
    }

    public static void init(OnCrashListener onCrashListener) {
        init("", onCrashListener);
    }

    public static void init(@NonNull File file, OnCrashListener onCrashListener) {
        if (file != null) {
            init(file.getAbsolutePath(), onCrashListener);
            return;
        }
        throw new NullPointerException("Argument 'crashDir' of type File (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void init(String str, OnCrashListener onCrashListener) {
        if (b.o(str)) {
            if (!b.s() || Utils.getApp().getExternalFilesDir(null) == null) {
                str = Utils.getApp().getFilesDir() + a + "crash" + a;
            } else {
                str = Utils.getApp().getExternalFilesDir(null) + a + "crash" + a;
            }
        } else if (!str.endsWith(a)) {
            str = str + a;
        }
        Thread.setDefaultUncaughtExceptionHandler(a(str, onCrashListener));
    }

    private static Thread.UncaughtExceptionHandler a(final String str, final OnCrashListener onCrashListener) {
        return new Thread.UncaughtExceptionHandler() { // from class: com.blankj.utilcode.util.CrashUtils.1
            @Override // java.lang.Thread.UncaughtExceptionHandler
            public void uncaughtException(@NonNull Thread thread, @NonNull Throwable th) {
                if (thread == null) {
                    throw new NullPointerException("Argument 't' of type Thread (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
                } else if (th != null) {
                    String format = new SimpleDateFormat("yyyy_MM_dd-HH_mm_ss").format(new Date());
                    CrashInfo crashInfo = new CrashInfo(format, th);
                    OnCrashListener onCrashListener2 = OnCrashListener.this;
                    if (onCrashListener2 != null) {
                        onCrashListener2.onCrash(crashInfo);
                    }
                    b.a(str + format + ".txt", crashInfo.toString(), true);
                    if (CrashUtils.b != null) {
                        CrashUtils.b.uncaughtException(thread, th);
                    }
                } else {
                    throw new NullPointerException("Argument 'e' of type Throwable (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
                }
            }
        };
    }

    /* loaded from: classes.dex */
    public static final class CrashInfo {
        private b.a a;
        private Throwable b;

        private CrashInfo(String str, Throwable th) {
            this.b = th;
            this.a = new b.a("Crash");
            this.a.a("Time Of Crash", str);
        }

        public final void addExtraHead(Map<String, String> map) {
            this.a.a(map);
        }

        public final void addExtraHead(String str, String str2) {
            this.a.b(str, str2);
        }

        public final Throwable getThrowable() {
            return this.b;
        }

        public String toString() {
            return this.a.toString() + b.a(this.b);
        }
    }
}
