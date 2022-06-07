package miuix.arch.component;

import android.util.Log;
import android.util.TimingLogger;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: DebugUtils.java */
/* loaded from: classes5.dex */
final class b {
    private static final boolean a = Log.isLoggable("miuix-app-comp", 2);
    private static ConcurrentHashMap<String, TimingLogger> b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(String str, Object... objArr) {
        if (a) {
            Log.v("miuix-app-comp", String.format(str, objArr));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void b(String str, Object... objArr) {
        Log.w("miuix-app-comp", String.format(str, objArr));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(Throwable th, String str, Object... objArr) {
        Log.e("miuix-app-comp", String.format(str, objArr), th);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(String str) {
        if (a) {
            if (b == null) {
                b = new ConcurrentHashMap<>();
            }
            if (b.get(str) == null) {
                b.put(str, new TimingLogger("miuix-app-comp", str));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(String str, String str2, Object... objArr) {
        TimingLogger timingLogger;
        if (a && (timingLogger = b.get(str)) != null) {
            timingLogger.addSplit(String.format(str2, objArr));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(String str, boolean z) {
        TimingLogger timingLogger;
        if (a && (timingLogger = b.get(str)) != null) {
            timingLogger.dumpToLog();
            b.remove(str);
        }
    }
}
