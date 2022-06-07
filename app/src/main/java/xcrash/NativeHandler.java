package xcrash;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import java.io.File;
import java.util.Map;

@SuppressLint({"StaticFieldLeak"})
/* loaded from: classes6.dex */
public class NativeHandler {
    private static final NativeHandler a = new NativeHandler();
    private Context c;
    private boolean d;
    private ICrashCallback e;
    private boolean f;
    private boolean g;
    private ICrashCallback h;
    private long b = 15000;
    private boolean i = false;

    private static native int nativeInit(int i, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, boolean z, boolean z2, int i2, int i3, int i4, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, int i5, String[] strArr, boolean z8, boolean z9, int i6, int i7, int i8, boolean z10, boolean z11);

    private static native void nativeNotifyJavaCrashed();

    private static native void nativeTestCrash(int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeTraceSignalInit();

    private NativeHandler() {
    }

    public static NativeHandler a() {
        return a;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v3 */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Type inference failed for: r0v6, types: [android.content.Context] */
    /* JADX WARN: Type inference failed for: r0v8, types: [void, android.content.pm.ApplicationInfo] */
    /* JADX WARN: Type inference failed for: r35v0, types: [com.elvishew.xlog.printer.file.backup.BackupStrategy, xcrash.ILibLoader] */
    /* JADX WARN: Unknown variable types count: 2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int a(com.elvishew.xlog.internal.printer.file.backup.BackupStrategyWrapper r34, xcrash.ILibLoader r35, java.lang.String r36, java.lang.String r37, java.lang.String r38, boolean r39, boolean r40, int r41, int r42, int r43, boolean r44, boolean r45, boolean r46, boolean r47, boolean r48, int r49, java.lang.String[] r50, xcrash.ICrashCallback r51, boolean r52, boolean r53, boolean r54, int r55, int r56, int r57, boolean r58, boolean r59, xcrash.ICrashCallback r60) {
        /*
            r33 = this;
            r1 = r33
            r0 = r35
            r2 = -2
            if (r0 != 0) goto L_0x001c
            java.lang.String r0 = "xcrash"
            java.lang.System.loadLibrary(r0)     // Catch: Throwable -> 0x000f
            r0 = r34
            goto L_0x0023
        L_0x000f:
            r0 = move-exception
            xcrash.ILogger r3 = xcrash.XCrash.d()
            java.lang.String r4 = "xcrash"
            java.lang.String r5 = "NativeHandler System.loadLibrary failed"
            r3.e(r4, r5, r0)
            return r2
        L_0x001c:
            java.lang.String r3 = "xcrash"
            r0.loadLibrary(r3)     // Catch: Throwable -> 0x00aa
            r0 = r34
        L_0x0023:
            r1.c = r0
            r14 = r40
            r1.d = r14
            r2 = r51
            r1.e = r2
            r15 = r52
            r1.f = r15
            r2 = r54
            r1.g = r2
            r2 = r60
            r1.h = r2
            if (r53 == 0) goto L_0x003e
            r2 = 15000(0x3a98, double:7.411E-320)
            goto L_0x0040
        L_0x003e:
            r2 = 30000(0x7530, double:1.4822E-319)
        L_0x0040:
            r1.b = r2
            r32 = -3
            int r2 = android.os.Build.VERSION.SDK_INT     // Catch: Throwable -> 0x009d
            java.lang.String r3 = android.os.Build.VERSION.RELEASE     // Catch: Throwable -> 0x009d
            java.lang.String r4 = xcrash.f.b()     // Catch: Throwable -> 0x009d
            java.lang.String r5 = android.os.Build.MANUFACTURER     // Catch: Throwable -> 0x009d
            java.lang.String r6 = android.os.Build.BRAND     // Catch: Throwable -> 0x009d
            java.lang.String r7 = android.os.Build.MODEL     // Catch: Throwable -> 0x009d
            java.lang.String r8 = android.os.Build.FINGERPRINT     // Catch: Throwable -> 0x009d
            void r0 = r34.<init>(r35)     // Catch: Throwable -> 0x009d
            java.lang.String r11 = r0.nativeLibraryDir     // Catch: Throwable -> 0x009d
            r9 = r36
            r10 = r37
            r12 = r38
            r13 = r39
            r14 = r40
            r15 = r41
            r16 = r42
            r17 = r43
            r18 = r44
            r19 = r45
            r20 = r46
            r21 = r47
            r22 = r48
            r23 = r49
            r24 = r50
            r25 = r52
            r26 = r53
            r27 = r55
            r28 = r56
            r29 = r57
            r30 = r58
            r31 = r59
            int r0 = nativeInit(r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24, r25, r26, r27, r28, r29, r30, r31)     // Catch: Throwable -> 0x009d
            if (r0 == 0) goto L_0x0098
            xcrash.ILogger r0 = xcrash.XCrash.d()     // Catch: Throwable -> 0x009d
            java.lang.String r2 = "xcrash"
            java.lang.String r3 = "NativeHandler init failed"
            r0.e(r2, r3)     // Catch: Throwable -> 0x009d
            return r32
        L_0x0098:
            r0 = 1
            r1.i = r0     // Catch: Throwable -> 0x009d
            r0 = 0
            return r0
        L_0x009d:
            r0 = move-exception
            xcrash.ILogger r2 = xcrash.XCrash.d()
            java.lang.String r3 = "xcrash"
            java.lang.String r4 = "NativeHandler init failed"
            r2.e(r3, r4, r0)
            return r32
        L_0x00aa:
            r0 = move-exception
            xcrash.ILogger r3 = xcrash.XCrash.d()
            java.lang.String r4 = "xcrash"
            java.lang.String r5 = "NativeHandler ILibLoader.loadLibrary failed"
            r3.e(r4, r5, r0)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: xcrash.NativeHandler.a(android.content.Context, xcrash.ILibLoader, java.lang.String, java.lang.String, java.lang.String, boolean, boolean, int, int, int, boolean, boolean, boolean, boolean, boolean, int, java.lang.String[], xcrash.ICrashCallback, boolean, boolean, boolean, int, int, int, boolean, boolean, xcrash.ICrashCallback):int");
    }

    public void b() {
        if (this.i && this.f) {
            nativeNotifyJavaCrashed();
        }
    }

    public void a(boolean z) {
        if (this.i) {
            nativeTestCrash(z ? 1 : 0);
        }
    }

    private static String a(boolean z, String str) {
        try {
            for (Map.Entry<Thread, StackTraceElement[]> entry : Thread.getAllStackTraces().entrySet()) {
                Thread key = entry.getKey();
                if ((z && key.getName().equals("main")) || (!z && key.getName().contains(str))) {
                    StringBuilder sb = new StringBuilder();
                    StackTraceElement[] value = entry.getValue();
                    for (StackTraceElement stackTraceElement : value) {
                        sb.append("    at ");
                        sb.append(stackTraceElement.toString());
                        sb.append("\n");
                    }
                    return sb.toString();
                }
            }
            return null;
        } catch (Exception e) {
            XCrash.d().e("xcrash", "NativeHandler getStacktraceByThreadName failed", e);
            return null;
        }
    }

    private static void crashCallback(String str, String str2, boolean z, boolean z2, String str3) {
        if (!TextUtils.isEmpty(str)) {
            if (z) {
                String a2 = a(z2, str3);
                if (!TextUtils.isEmpty(a2)) {
                    TombstoneManager.appendSection(str, TombstoneParser.keyJavaStacktrace, a2);
                }
            }
            TombstoneManager.appendSection(str, TombstoneParser.keyMemoryInfo, f.c());
            TombstoneManager.appendSection(str, "foreground", a.a().c() ? "yes" : "no");
        }
        ICrashCallback iCrashCallback = a().e;
        if (iCrashCallback != null) {
            try {
                iCrashCallback.onCrash(str, str2);
            } catch (Exception e) {
                XCrash.d().w("xcrash", "NativeHandler native crash callback.onCrash failed", e);
            }
        }
        if (!a().d) {
            a.a().b();
        }
    }

    private static void traceSignalInit() {
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: xcrash.NativeHandler.1
            @Override // java.lang.Runnable
            public void run() {
                NativeHandler.nativeTraceSignalInit();
            }
        });
    }

    private static void traceCallback(String str, String str2) {
        if (!TextUtils.isEmpty(str)) {
            TombstoneManager.appendSection(str, TombstoneParser.keyMemoryInfo, f.c());
            TombstoneManager.appendSection(str, "foreground", a.a().c() ? "yes" : "no");
            if (a().g && !f.a(a().c, a().b)) {
                d.a().a(new File(str));
            } else if (d.a().c()) {
                String str3 = str.substring(0, str.length() - 13) + ".anr.xcrash";
                File file = new File(str);
                if (!file.renameTo(new File(str3))) {
                    d.a().a(file);
                    return;
                }
                ICrashCallback iCrashCallback = a().h;
                if (iCrashCallback != null) {
                    try {
                        iCrashCallback.onCrash(str3, str2);
                    } catch (Exception e) {
                        XCrash.d().w("xcrash", "NativeHandler ANR callback.onCrash failed", e);
                    }
                }
            }
        }
    }
}
