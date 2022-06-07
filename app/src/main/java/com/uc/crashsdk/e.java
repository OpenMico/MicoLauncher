package com.uc.crashsdk;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Debug;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.os.StatFs;
import android.os.StrictMode;
import android.support.v4.media.session.PlaybackStateCompat;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.uc.crashsdk.a.f;
import com.uc.crashsdk.a.g;
import com.uc.crashsdk.a.h;
import com.uc.crashsdk.export.LogType;
import com.xiaomi.idm.api.IDMServer;
import com.xiaomi.mipush.sdk.Constants;
import io.netty.handler.codec.http.multipart.DiskFileUpload;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.Thread;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import miuix.animation.internal.AnimTask;
import xcrash.TombstoneParser;

/* compiled from: ProGuard */
/* loaded from: classes2.dex */
public class e implements Thread.UncaughtExceptionHandler {
    private static long b;
    private static String i;
    private final List<FileInputStream> e = new ArrayList();
    static final /* synthetic */ boolean a = !e.class.desiredAssertionStatus();
    private static final AtomicBoolean c = new AtomicBoolean(false);
    private static boolean d = false;
    private static long f = 0;
    private static long g = -1;
    private static boolean h = true;
    private static String j = "";
    private static String k = null;
    private static String l = null;
    private static String m = null;
    private static final Object n = new Object();
    private static final Object o = new Object();
    private static final Object p = new Object();
    private static final Object q = new Object();
    private static final ArrayList<String> r = new ArrayList<>();
    private static int s = 0;
    private static String t = null;
    private static boolean u = false;
    private static String v = null;
    private static String w = null;
    private static String x = null;
    private static final Object y = new Object();
    private static final Object z = new Object();
    private static Map<String, Integer> A = null;
    private static String B = null;
    private static int C = -1;
    private static int D = -1;
    private static int E = -1;
    private static int F = -1;
    private static int G = -1;
    private static int H = -1;
    private static int I = -1;
    private static String J = "?";
    private static boolean K = false;
    private static boolean L = false;
    private static int M = 0;
    private static int N = 0;
    private static boolean O = false;
    private static com.uc.crashsdk.a.e P = new com.uc.crashsdk.a.e(405);
    private static c Q = new c((byte) 0);
    private static boolean R = false;
    private static final com.uc.crashsdk.a.e S = new com.uc.crashsdk.a.e(412);
    private static Thread.UncaughtExceptionHandler T = null;
    private static Throwable U = null;
    private static boolean V = false;
    private static boolean W = false;
    private static Runnable X = null;
    private static final Object Y = new Object();
    private static int Z = 101;
    private static Runnable aa = new com.uc.crashsdk.a.e(407);
    private static final Object ab = new Object();
    private static boolean ac = false;
    private static ParcelFileDescriptor ad = null;
    private static boolean ae = false;
    private static boolean af = false;

    public static String d(boolean z2) {
        return z2 ? "https://errlogos.umeng.com/upload" : "https://errlog.umeng.com/upload";
    }

    static /* synthetic */ int I() {
        int i2 = M + 1;
        M = i2;
        return i2;
    }

    static /* synthetic */ void J() {
        StringBuilder X2;
        if (b.d && (X2 = X()) != null) {
            JNIBridge.set(125, X2.toString());
        }
        L = true;
        Y();
    }

    public e() {
        try {
            L();
        } catch (Throwable th) {
            g.a(th);
        }
    }

    private void L() {
        int G2 = g.G();
        for (int i2 = 0; i2 < G2; i2++) {
            try {
                this.e.add(new FileInputStream("/dev/null"));
            } catch (Exception e) {
                g.a(e);
                return;
            }
        }
    }

    private void M() {
        for (FileInputStream fileInputStream : this.e) {
            g.a(fileInputStream);
        }
        this.e.clear();
    }

    public static boolean N() {
        if (g.N()) {
            return true;
        }
        return a();
    }

    public static boolean a() {
        if (f == 0) {
            f = 2L;
            if (g(b.a("logs")) == 1) {
                f = 1L;
            }
        }
        return f == 1;
    }

    private static long g(String str) {
        try {
            Method declaredMethod = Class.forName("android.os.SystemProperties").getDeclaredMethod("getLong", String.class, Long.TYPE);
            if (declaredMethod != null) {
                declaredMethod.setAccessible(true);
                return ((Long) declaredMethod.invoke(null, str, 0L)).longValue();
            }
        } catch (Throwable th) {
            g.a(th);
        }
        return 0L;
    }

    public static long b() {
        if (g == -1) {
            g = g(b.a("local"));
        }
        return g;
    }

    /* compiled from: ProGuard */
    /* loaded from: classes2.dex */
    public static class a extends OutputStream {
        private final long a;
        private final OutputStream b;
        private int c = 0;
        private int d = 0;
        private boolean e = false;

        a(long j, OutputStream outputStream) {
            this.a = j;
            this.b = outputStream;
        }

        /* JADX WARN: Removed duplicated region for block: B:13:0x0027  */
        /* JADX WARN: Removed duplicated region for block: B:14:0x0030  */
        /* JADX WARN: Removed duplicated region for block: B:16:0x0037  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private int a(byte[] r6, int r7, int r8) {
            /*
                r5 = this;
                int r0 = r5.d
                int r0 = r0 + r8
                r5.d = r0
                boolean r0 = r5.e
                if (r0 == 0) goto L_0x000b
                r6 = 0
                return r6
            L_0x000b:
                int r0 = com.uc.crashsdk.g.y()
                if (r0 <= 0) goto L_0x0019
                int r1 = r5.c
                int r2 = r1 + r8
                if (r2 <= r0) goto L_0x0019
                int r0 = r0 - r1
                goto L_0x001a
            L_0x0019:
                r0 = r8
            L_0x001a:
                int r1 = r5.c
                int r1 = r1 + r0
                r5.c = r1
                long r1 = r5.a
                r3 = 0
                int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
                if (r1 == 0) goto L_0x0030
                java.lang.String r1 = new java.lang.String
                r1.<init>(r6, r7, r0)
                r5.b(r1)
                goto L_0x0035
            L_0x0030:
                java.io.OutputStream r1 = r5.b
                r1.write(r6, r7, r0)
            L_0x0035:
                if (r0 >= r8) goto L_0x003a
                r6 = 1
                r5.e = r6
            L_0x003a:
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.uc.crashsdk.e.a.a(byte[], int, int):int");
        }

        final void a() {
            try {
                if (this.d - this.c > 0) {
                    a("\n");
                    a("--- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---\n");
                }
                a(String.format(Locale.US, "Full: %d bytes, write: %d bytes, limit: %d bytes, reject: %d bytes.\n", Integer.valueOf(this.d), Integer.valueOf(this.c), Integer.valueOf(g.y()), Integer.valueOf(this.d - this.c)));
            } catch (Throwable th) {
                g.a(th);
            }
        }

        final void a(String str) {
            if (e.h && e.N()) {
                com.uc.crashsdk.a.a.d("DEBUG", str);
            }
            if (this.a != 0) {
                b(str);
            } else {
                this.b.write(str.getBytes("UTF-8"));
            }
        }

        @Override // java.io.OutputStream
        public final void write(int i) {
            if (e.h && e.N()) {
                com.uc.crashsdk.a.a.d("DEBUG", String.format(Locale.US, "%c", Integer.valueOf(i)));
            }
            if (this.a != 0) {
                b(String.format(Locale.US, "%c", Integer.valueOf(i)));
            } else {
                this.b.write(i);
            }
            this.c++;
            this.d++;
        }

        @Override // java.io.OutputStream
        public final void write(byte[] bArr, int i, int i2) {
            if (e.h && e.N()) {
                byte[] bArr2 = new byte[i2];
                System.arraycopy(bArr, i, bArr2, 0, i2);
                if (!(i2 == 1 && bArr2[0] == 10)) {
                    try {
                        com.uc.crashsdk.a.a.d("DEBUG", new String(bArr2));
                    } catch (Throwable unused) {
                    }
                }
            }
            a(bArr, i, i2);
        }

        @Override // java.io.OutputStream
        public final void write(byte[] bArr) {
            if (e.h && e.N() && !(bArr.length == 1 && bArr[0] == 10)) {
                try {
                    com.uc.crashsdk.a.a.d("DEBUG", new String(bArr));
                } catch (Throwable unused) {
                }
            }
            a(bArr, 0, bArr.length);
        }

        private void b(String str) {
            if (b.d) {
                JNIBridge.nativeClientWriteData(this.a, str);
            }
        }
    }

    private static String h(String str) {
        try {
            return str.replaceAll("[^0-9a-zA-Z-.]", Constants.ACCEPT_TIME_SEPARATOR_SERVER);
        } catch (Throwable unused) {
            return "unknown";
        }
    }

    private static String O() {
        return g.e() + "_";
    }

    public static void c() {
        i = null;
    }

    public static String d() {
        String str = i;
        if (str != null) {
            return str;
        }
        String i2 = i((String) null);
        i = i2;
        return i2;
    }

    private static String i(String str) {
        if (str == null) {
            str = String.valueOf(System.currentTimeMillis()) + new Random().nextInt(65536);
        }
        return String.format(Locale.US, "%s%s_%s_%s_%s_%s_", O(), g.R(), g.T(), h(Build.MODEL), h(Build.VERSION.RELEASE), str);
    }

    private static String P() {
        return b.y() ? "fg" : "bg";
    }

    private static byte[] Q() {
        byte[] bArr = null;
        int i2 = 1024;
        while (bArr == null && i2 > 0) {
            try {
                bArr = new byte[i2];
            } catch (Throwable unused) {
                i2 /= 2;
                if (i2 < 16) {
                    break;
                }
            }
        }
        return bArr;
    }

    private static String j(String str) {
        return String.format(Locale.US, "%s%s_%s_%s.log", d(), m(), P(), str);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static void a(int i2, Object[] objArr) {
        int i3;
        switch (i2) {
            case 401:
                JNIBridge.nativeCmd(10, b.F() == 5 ? 1L : 0L, null, null);
                a.c = true;
                a.a(false);
                L = true;
                Y();
                x();
                return;
            case 402:
                synchronized (Y) {
                    if (X != null) {
                        W = true;
                        if (b.n()) {
                            return;
                        }
                        if (!com.uc.crashsdk.a.d.d()) {
                            com.uc.crashsdk.a.a.c("DEBUG", com.uc.crashsdk.a.d.b());
                            return;
                        } else if (!c(LogType.UNEXP_TYPE)) {
                            com.uc.crashsdk.a.a.d("DEBUG", "unexp sample miss");
                            return;
                        } else {
                            int nativeGenerateUnexpLog = JNIBridge.nativeGenerateUnexpLog(g.o(), g.p());
                            if (nativeGenerateUnexpLog != 0) {
                                f.a(11);
                                if ((nativeGenerateUnexpLog & 4352) != 0) {
                                    Z = 105;
                                    i3 = 30;
                                } else if ((nativeGenerateUnexpLog & LogType.UNEXP_EXIT) != 0) {
                                    Z = 104;
                                    i3 = 31;
                                } else if ((nativeGenerateUnexpLog & LogType.UNEXP_RESTART) != 0) {
                                    Z = 106;
                                    i3 = 32;
                                } else {
                                    if ((nativeGenerateUnexpLog & 1280) != 0) {
                                        Z = 103;
                                        f.a(10);
                                    } else if ((nativeGenerateUnexpLog & LogType.UNEXP_LOW_MEMORY) != 0) {
                                        Z = 107;
                                        f.a(29);
                                    } else {
                                        Z = 102;
                                    }
                                    a(true);
                                }
                                f.a(i3);
                                a(true);
                            }
                            synchronized (Y) {
                                X = null;
                            }
                            return;
                        }
                    } else {
                        return;
                    }
                }
            case 403:
                aa();
                return;
            case 404:
            default:
                if (!a) {
                    throw new AssertionError();
                }
                return;
            case 405:
                L = false;
                StringBuilder X2 = X();
                String d2 = b.d();
                if (X2 != null) {
                    g.a(new File(d2), X2.toString());
                    return;
                }
                return;
            case 406:
                if (a || objArr != null) {
                    a((String) objArr[0], ((Boolean) objArr[1]).booleanValue(), ((Boolean) objArr[2]).booleanValue());
                    return;
                }
                throw new AssertionError();
            case 407:
                try {
                    a.d();
                    return;
                } catch (Throwable th) {
                    g.a(th);
                    return;
                }
            case 408:
                synchronized (ab) {
                    if (!ac && g.O() && b.w()) {
                        b.p();
                        h.f();
                        f.c();
                        if (b.C()) {
                            B();
                        }
                        if (g.O()) {
                            a(Calendar.getInstance());
                        }
                        ac = true;
                        return;
                    }
                    return;
                }
            case 409:
                b(false, false);
                return;
            case 410:
                a(false, true);
                return;
            case 411:
                if (b.d) {
                    JNIBridge.set(28, c(LogType.NATIVE_TYPE));
                    JNIBridge.set(29, c("anr"));
                    return;
                }
                return;
            case 412:
                if (!R && b.y() && g.K()) {
                    b(g.a());
                    return;
                } else if (!R) {
                    return;
                } else {
                    if (!b.y() || !g.K()) {
                        try {
                            g.a().unregisterReceiver(Q);
                            R = false;
                            return;
                        } catch (Throwable th2) {
                            g.a(th2);
                            return;
                        }
                    } else {
                        return;
                    }
                }
            case 413:
                JNIBridge.cmd(8);
                return;
            case 414:
                try {
                    if (!d(g.a())) {
                        int i4 = N + 1;
                        N = i4;
                        if (i4 < 10) {
                            W();
                            return;
                        } else if (b.d) {
                            JNIBridge.set(130, "(get failed)");
                            return;
                        } else {
                            return;
                        }
                    } else {
                        return;
                    }
                } catch (Throwable th3) {
                    g.a(th3);
                    return;
                }
            case 415:
                if (a || objArr != null) {
                    long longValue = ((Long) objArr[0]).longValue();
                    Calendar instance = Calendar.getInstance();
                    if (instance.getTimeInMillis() >= longValue) {
                        h.g();
                        f.a(100);
                        b(true, true);
                        h.b();
                    } else {
                        h.h();
                        h.i();
                        h.c();
                    }
                    a(instance);
                    break;
                } else {
                    throw new AssertionError();
                }
            case 416:
                break;
        }
        V();
    }

    public static boolean b(int i2, Object[] objArr) {
        switch (i2) {
            case 451:
                if (a || objArr != null) {
                    return a((String) objArr[0], (d) objArr[1]);
                }
                throw new AssertionError();
            case 452:
                if (a || objArr != null) {
                    d dVar = (d) objArr[1];
                    return g.a(new File((String) objArr[0]), String.format(Locale.US, "%d %d %d %d", Long.valueOf(dVar.a), Long.valueOf(dVar.b), Integer.valueOf(dVar.c), Integer.valueOf(dVar.d)).getBytes());
                }
                throw new AssertionError();
            default:
                if (a) {
                    return false;
                }
                throw new AssertionError();
        }
    }

    private static String R() {
        return (!b.C() || d) ? "java" : "ucebujava";
    }

    public static void a(boolean z2) {
        File[] listFiles;
        try {
            if (b.v() && (listFiles = new File(g.V()).listFiles()) != null) {
                int l2 = g.l();
                int m2 = g.m();
                if (listFiles.length >= Math.min(l2, m2)) {
                    int i2 = 0;
                    int i3 = 0;
                    for (File file : listFiles) {
                        if (b(file)) {
                            i2++;
                        } else {
                            i3++;
                        }
                    }
                    int i4 = (!z2 || i2 < l2) ? 0 : (i2 - l2) + 1;
                    int i5 = (z2 || i3 < m2) ? 0 : (i3 - m2) + 1;
                    if (!(i4 == 0 && i5 == 0)) {
                        Arrays.sort(listFiles, new b((byte) 0));
                        int i6 = i5;
                        int i7 = i4;
                        for (File file2 : listFiles) {
                            boolean b2 = b(file2);
                            if (b2 && i7 > 0) {
                                com.uc.crashsdk.a.a.a("crashsdk", "Delete oldest crash log: " + file2.getPath());
                                file2.delete();
                                i7 += -1;
                            } else if (!b2 && i6 > 0) {
                                com.uc.crashsdk.a.a.a("crashsdk", "Delete oldest custom log: " + file2.getPath());
                                file2.delete();
                                i6 += -1;
                            }
                            if (i7 == 0 && i6 == 0) {
                                break;
                            }
                        }
                        f.a(16, i4 + i5);
                        if (i4 > 0) {
                            f.a(22, i4);
                        }
                        if (i5 > 0) {
                            f.a(23, i5);
                        }
                    }
                }
            }
        } catch (Throwable th) {
            g.a(th);
        }
    }

    /* compiled from: ProGuard */
    /* loaded from: classes2.dex */
    public static class b implements Comparator<File> {
        private b() {
        }

        /* synthetic */ b(byte b) {
            this();
        }

        @Override // java.util.Comparator
        public final /* synthetic */ int compare(File file, File file2) {
            File file3 = file;
            File file4 = file2;
            if (file3.lastModified() > file4.lastModified()) {
                return 1;
            }
            return file3.lastModified() < file4.lastModified() ? -1 : 0;
        }
    }

    private static void b(OutputStream outputStream, String str, String str2) {
        String str3;
        try {
            outputStream.write("*** *** *** *** *** *** *** *** *** *** *** *** *** *** *** ***\n".getBytes("UTF-8"));
        } catch (Throwable th) {
            a(th, outputStream);
        }
        try {
            outputStream.write(String.format(Locale.US, "Basic Information: 'pid: %d/tid: %d/time: %s'\n", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()), m()).getBytes("UTF-8"));
            Locale locale = Locale.US;
            Object[] objArr = new Object[3];
            objArr[0] = e();
            if (g.a(l)) {
                S();
            }
            objArr[1] = l;
            objArr[2] = f();
            outputStream.write(String.format(locale, "Cpu Information: 'abi: %s/processor: %s/hardware: %s'\n", objArr).getBytes("UTF-8"));
        } catch (Throwable th2) {
            a(th2, outputStream);
        }
        try {
            outputStream.write(String.format(Locale.US, "Mobile Information: 'model: %s/version: %s/sdk: %d'\n", Build.MODEL, Build.VERSION.RELEASE, Integer.valueOf(Build.VERSION.SDK_INT)).getBytes("UTF-8"));
            outputStream.write(("Build fingerprint: '" + Build.FINGERPRINT + "'\n").getBytes("UTF-8"));
            Locale locale2 = Locale.US;
            Object[] objArr2 = new Object[4];
            objArr2[0] = a(new Date(b));
            objArr2[1] = Long.valueOf(Runtime.getRuntime().maxMemory());
            objArr2[2] = g.d();
            objArr2[3] = b.y() ? "fg" : "bg";
            outputStream.write(String.format(locale2, "Runtime Information: 'start: %s/maxheap: %s/primaryabi: %s/ground: %s'\n", objArr2).getBytes("UTF-8"));
        } catch (Throwable th3) {
            a(th3, outputStream);
        }
        try {
            outputStream.write(String.format(Locale.US, "Application Information: 'version: %s/subversion: %s/buildseq: %s/versioncode: %d'\n", g.R(), g.S(), g.T(), Integer.valueOf(a.c())).getBytes("UTF-8"));
            String str4 = "0";
            String str5 = "";
            if (b.d) {
                String nativeGet = JNIBridge.nativeGet(1, 0L, null);
                str5 = JNIBridge.nativeGet(2, 0L, null);
                str4 = nativeGet;
            }
            outputStream.write(String.format(Locale.US, "CrashSDK Information: 'version: %s/nativeseq: %s/javaseq: %s/arch: %s/target: %s'\n", "3.2.0.4", str4, "210105150455", str5, "release").getBytes("UTF-8"));
            if (str == null) {
                str = "";
            }
            outputStream.write(("Report Name: " + str.substring(str.lastIndexOf(47) + 1) + "\n").getBytes("UTF-8"));
        } catch (Throwable th4) {
            a(th4, outputStream);
        }
        try {
            if (af) {
                str3 = r("UUID");
            } else {
                str3 = B;
            }
            outputStream.write(String.format("UUID: %s\n", str3).getBytes("UTF-8"));
            outputStream.write(("Log Type: " + str2 + "\n").getBytes("UTF-8"));
        } catch (Throwable th5) {
            a(th5, outputStream);
        }
        try {
            String B2 = b.B();
            if (g.a(B2)) {
                B2 = "(none)";
            }
            outputStream.write(("Activity: " + B2 + "\n").getBytes("UTF-8"));
        } catch (Throwable th6) {
            a(th6, outputStream);
        }
        a(outputStream);
        try {
            a.a(outputStream, "UTF-8");
            if (af) {
                h = false;
                outputStream.write(r("HEADER").getBytes("UTF-8"));
                h = true;
            }
        } catch (Throwable th7) {
            a(th7, outputStream);
        }
        a(outputStream);
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00af A[Catch: Throwable -> 0x00d4, TRY_LEAVE, TryCatch #2 {Throwable -> 0x00d4, blocks: (B:34:0x0096, B:36:0x00af), top: B:64:0x0096 }] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00d2  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00e1 A[Catch: Throwable -> 0x0148, TryCatch #4 {Throwable -> 0x0148, blocks: (B:44:0x00db, B:46:0x00e1, B:48:0x00e9, B:49:0x0111, B:51:0x0117, B:53:0x011f), top: B:68:0x00db }] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0117 A[Catch: Throwable -> 0x0148, TryCatch #4 {Throwable -> 0x0148, blocks: (B:44:0x00db, B:46:0x00e1, B:48:0x00e9, B:49:0x0111, B:51:0x0117, B:53:0x011f), top: B:68:0x00db }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String e() {
        /*
            Method dump skipped, instructions count: 335
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.crashsdk.e.e():java.lang.String");
    }

    public static String f() {
        if (g.a(k)) {
            S();
        }
        return k;
    }

    private static void S() {
        Closeable closeable;
        FileReader fileReader;
        Throwable th;
        BufferedReader bufferedReader;
        Throwable th2;
        String str = Constants.ACCEPT_TIME_SEPARATOR_SERVER;
        String str2 = Constants.ACCEPT_TIME_SEPARATOR_SERVER;
        try {
            str = Build.HARDWARE;
        } catch (Throwable th3) {
            g.a(th3);
        }
        try {
            try {
                fileReader = new FileReader(new File("/proc/cpuinfo"));
                try {
                    bufferedReader = new BufferedReader(fileReader, 512);
                    int i2 = 0;
                    do {
                        try {
                            String readLine = bufferedReader.readLine();
                            if (readLine == null) {
                                break;
                            } else if (readLine.startsWith("Hardware")) {
                                str = readLine.substring(readLine.indexOf(Constants.COLON_SEPARATOR) + 1).trim();
                                i2++;
                            } else if (readLine.startsWith("Processor")) {
                                str2 = readLine.substring(readLine.indexOf(Constants.COLON_SEPARATOR) + 1).trim();
                                i2++;
                            }
                        } catch (Throwable th4) {
                            th2 = th4;
                            g.a(th2);
                            g.a(fileReader);
                            g.a(bufferedReader);
                            k = str;
                            l = str2;
                        }
                    } while (i2 < 2);
                    g.a(fileReader);
                } catch (Throwable th5) {
                    th2 = th5;
                    bufferedReader = null;
                }
            } catch (Throwable th6) {
                th = th6;
                fileReader = null;
                closeable = null;
            }
            g.a(bufferedReader);
            k = str;
            l = str2;
        } catch (Throwable th7) {
            th = th7;
        }
    }

    public static String g() {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("JavaMax:    ");
            sb.append(Runtime.getRuntime().maxMemory() / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID);
            sb.append(" kB\n");
            sb.append("JavaTotal:  ");
            sb.append(Runtime.getRuntime().totalMemory() / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID);
            sb.append(" kB\n");
            sb.append("JavaFree:   ");
            sb.append(Runtime.getRuntime().freeMemory() / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID);
            sb.append(" kB\n");
            sb.append("NativeHeap: ");
            sb.append(Debug.getNativeHeapSize() / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID);
            sb.append(" kB\n");
            sb.append("NativeAllocated: ");
            sb.append(Debug.getNativeHeapAllocatedSize() / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID);
            sb.append(" kB\n");
            sb.append("NativeFree: ");
            sb.append(Debug.getNativeHeapFreeSize() / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID);
            sb.append(" kB\n");
            ActivityManager activityManager = (ActivityManager) g.a().getSystemService(IDMServer.PERSIST_TYPE_ACTIVITY);
            if (activityManager != null) {
                ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
                activityManager.getMemoryInfo(memoryInfo);
                sb.append("availMem:   ");
                sb.append(memoryInfo.availMem / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID);
                sb.append(" kB\n");
                sb.append("threshold:  ");
                sb.append(memoryInfo.threshold / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID);
                sb.append(" kB\n");
                sb.append("lowMemory:  ");
                sb.append(memoryInfo.lowMemory);
                sb.append("\n");
            }
            return sb.toString();
        } catch (Throwable th) {
            g.a(th);
            return "";
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0063, code lost:
        r3.write(r4.getBytes("UTF-8"));
        r3.write("\n".getBytes("UTF-8"));
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String a(java.lang.String r7, java.lang.String r8) {
        /*
            int r0 = android.os.Build.VERSION.SDK_INT     // Catch: Throwable -> 0x007f
            r1 = 26
            if (r0 < r1) goto L_0x000f
            java.lang.String r0 = "ps"
            java.lang.String r1 = "-ef"
            java.lang.String[] r0 = new java.lang.String[]{r0, r1}     // Catch: Throwable -> 0x007f
            goto L_0x0015
        L_0x000f:
            java.lang.String r0 = "ps"
            java.lang.String[] r0 = new java.lang.String[]{r0}     // Catch: Throwable -> 0x007f
        L_0x0015:
            java.lang.Runtime r1 = java.lang.Runtime.getRuntime()     // Catch: Throwable -> 0x007f
            java.lang.Process r0 = r1.exec(r0)     // Catch: Throwable -> 0x007f
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch: Throwable -> 0x007f
            java.io.InputStreamReader r2 = new java.io.InputStreamReader     // Catch: Throwable -> 0x007f
            java.io.InputStream r0 = r0.getInputStream()     // Catch: Throwable -> 0x007f
            r2.<init>(r0)     // Catch: Throwable -> 0x007f
            r1.<init>(r2)     // Catch: Throwable -> 0x007f
            boolean r0 = com.uc.crashsdk.a.g.b(r7)     // Catch: Throwable -> 0x007f
            boolean r2 = com.uc.crashsdk.a.g.b(r8)     // Catch: Throwable -> 0x007f
            java.io.ByteArrayOutputStream r3 = new java.io.ByteArrayOutputStream     // Catch: Throwable -> 0x007f
            r3.<init>()     // Catch: Throwable -> 0x007f
        L_0x0038:
            java.lang.String r4 = r1.readLine()     // Catch: Throwable -> 0x007f
            if (r4 == 0) goto L_0x0078
            r5 = 1
            if (r0 == 0) goto L_0x0047
            boolean r6 = r4.contains(r7)     // Catch: Throwable -> 0x007f
            if (r6 != 0) goto L_0x0061
        L_0x0047:
            if (r2 == 0) goto L_0x0050
            boolean r6 = r4.contains(r8)     // Catch: Throwable -> 0x007f
            if (r6 == 0) goto L_0x0050
            goto L_0x0061
        L_0x0050:
            r6 = 47
            int r6 = r4.indexOf(r6)     // Catch: Throwable -> 0x007f
            if (r6 >= 0) goto L_0x0060
            r6 = 46
            int r6 = r4.indexOf(r6)     // Catch: Throwable -> 0x007f
            if (r6 > 0) goto L_0x0061
        L_0x0060:
            r5 = 0
        L_0x0061:
            if (r5 == 0) goto L_0x0038
            java.lang.String r5 = "UTF-8"
            byte[] r4 = r4.getBytes(r5)     // Catch: Throwable -> 0x007f
            r3.write(r4)     // Catch: Throwable -> 0x007f
            java.lang.String r4 = "\n"
            java.lang.String r5 = "UTF-8"
            byte[] r4 = r4.getBytes(r5)     // Catch: Throwable -> 0x007f
            r3.write(r4)     // Catch: Throwable -> 0x007f
            goto L_0x0038
        L_0x0078:
            java.lang.String r7 = "UTF-8"
            java.lang.String r7 = r3.toString(r7)     // Catch: Throwable -> 0x007f
            return r7
        L_0x007f:
            r7 = move-exception
            com.uc.crashsdk.a.g.a(r7)
            java.lang.String r7 = "exception exists."
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.crashsdk.e.a(java.lang.String, java.lang.String):java.lang.String");
    }

    private static BufferedReader a(InputStreamReader inputStreamReader) {
        BufferedReader bufferedReader = null;
        int i2 = 8192;
        while (bufferedReader == null && i2 > 0) {
            try {
                bufferedReader = new BufferedReader(inputStreamReader, i2);
            } catch (Throwable unused) {
                i2 /= 2;
                if (i2 < 512) {
                    break;
                }
            }
        }
        return bufferedReader;
    }

    private static void a(OutputStream outputStream) {
        try {
            outputStream.write("--- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---\n".getBytes("UTF-8"));
        } catch (Throwable th) {
            a(th, outputStream);
        }
    }

    private static void b(OutputStream outputStream) {
        BufferedReader bufferedReader = null;
        try {
            outputStream.write("logcat:\n".getBytes("UTF-8"));
            if (g.n() <= 0) {
                try {
                    outputStream.write("[DEBUG] custom java logcat lines count is 0!\n".getBytes("UTF-8"));
                } catch (Throwable th) {
                    a(th, outputStream);
                }
                a(outputStream);
                return;
            }
            int n2 = g.n();
            bufferedReader = a(new InputStreamReader(Runtime.getRuntime().exec(new String[]{TombstoneParser.keyLogcat, "-d", "-b", "events", "-b", "main", "-v", "threadtime", "-t", String.valueOf(n2)}).getInputStream()));
            if (bufferedReader == null) {
                try {
                    outputStream.write("[DEBUG] alloc buffer failed!\n".getBytes("UTF-8"));
                } catch (Throwable th2) {
                    a(th2, outputStream);
                }
                a(outputStream);
                return;
            }
            h = false;
            int i2 = 0;
            int i3 = 0;
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    i2++;
                    if (i3 < n2 && !readLine.contains(" I auditd ") && !readLine.contains(" I liblog ")) {
                        outputStream.write(readLine.getBytes("UTF-8"));
                        outputStream.write("\n".getBytes("UTF-8"));
                        i3++;
                    }
                } else {
                    try {
                        break;
                    } catch (Throwable th3) {
                        a(th3, outputStream);
                    }
                }
            }
            outputStream.write(String.format(Locale.US, "[DEBUG] Read %d lines, wrote %d lines.\n", Integer.valueOf(i2), Integer.valueOf(i3)).getBytes("UTF-8"));
            h = true;
            g.a(bufferedReader);
            a(outputStream);
        } finally {
            g.a(bufferedReader);
        }
    }

    private static String a(File file) {
        String str;
        try {
            str = file.getCanonicalPath();
        } catch (Throwable unused) {
            str = null;
        }
        return g.a(str) ? file.getPath() : str;
    }

    private static long a(StatFs statFs, String str, String str2) {
        try {
            if (Build.VERSION.SDK_INT >= 18) {
                Method declaredMethod = StatFs.class.getDeclaredMethod(str, new Class[0]);
                declaredMethod.setAccessible(true);
                Object invoke = declaredMethod.invoke(statFs, new Object[0]);
                if (invoke != null && (invoke instanceof Long)) {
                    return ((Long) invoke).longValue();
                }
            }
        } catch (Throwable unused) {
        }
        try {
            Method declaredMethod2 = StatFs.class.getDeclaredMethod(str2, new Class[0]);
            declaredMethod2.setAccessible(true);
            Object invoke2 = declaredMethod2.invoke(statFs, new Object[0]);
            if (invoke2 == null || !(invoke2 instanceof Integer)) {
                return 0L;
            }
            return ((Integer) invoke2).intValue();
        } catch (Throwable th) {
            g.a(th);
            return 0L;
        }
    }

    private static void c(OutputStream outputStream) {
        if (b.d) {
            String l2 = b.l();
            h = false;
            if (1 == JNIBridge.cmd(17, l2)) {
                File file = new File(l2);
                try {
                    byte[] e = g.e(file);
                    if (e != null) {
                        outputStream.write(e);
                    }
                } catch (Throwable th) {
                    a(th, outputStream);
                }
                try {
                    file.delete();
                } catch (Throwable th2) {
                    a(th2, outputStream);
                }
                h = true;
                a(outputStream);
            }
            h = true;
            return;
        }
        File[] fileArr = null;
        int i2 = 900;
        try {
            i2 = g.H();
            fileArr = new File("/proc/self/fd").listFiles();
            if (fileArr != null) {
                outputStream.write(String.format(Locale.US, "opened file count: %d, write limit: %d.\n", Integer.valueOf(fileArr.length), Integer.valueOf(i2)).getBytes("UTF-8"));
            } else {
                outputStream.write("[DEBUG] listFiles failed!\n".getBytes("UTF-8"));
            }
        } catch (Throwable th3) {
            a(th3, outputStream);
        }
        if (fileArr != null) {
            try {
                if (fileArr.length >= i2) {
                    outputStream.write("opened files:\n".getBytes("UTF-8"));
                    StringBuilder sb = new StringBuilder();
                    for (File file2 : fileArr) {
                        sb.append(file2.getName());
                        sb.append(" -> ");
                        sb.append(file2.getCanonicalPath());
                        sb.append("\n");
                    }
                    outputStream.write(sb.toString().getBytes("UTF-8"));
                }
            } catch (Throwable th4) {
                a(th4, outputStream);
            }
        }
        a(outputStream);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0023 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0024 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void d(java.io.OutputStream r11) {
        /*
            r0 = 0
            r1 = 0
            int r2 = com.uc.crashsdk.g.I()     // Catch: Throwable -> 0x001a
            java.io.File r3 = new java.io.File     // Catch: Throwable -> 0x0018
            java.lang.String r4 = "/proc/self/task"
            r3.<init>(r4)     // Catch: Throwable -> 0x0018
            java.io.File[] r1 = r3.listFiles()     // Catch: Throwable -> 0x0018
            if (r1 != 0) goto L_0x0014
            return
        L_0x0014:
            int r3 = r1.length     // Catch: Throwable -> 0x0018
            if (r3 >= r2) goto L_0x0021
            return
        L_0x0018:
            r3 = move-exception
            goto L_0x001d
        L_0x001a:
            r3 = move-exception
            r2 = 300(0x12c, float:4.2E-43)
        L_0x001d:
            com.uc.crashsdk.a.g.a(r3)
            r3 = r0
        L_0x0021:
            if (r1 != 0) goto L_0x0024
            return
        L_0x0024:
            java.lang.String r4 = "threads info:\n"
            java.lang.String r5 = "UTF-8"
            byte[] r4 = r4.getBytes(r5)     // Catch: Throwable -> 0x0094
            r11.write(r4)     // Catch: Throwable -> 0x0094
            java.util.Locale r4 = java.util.Locale.US     // Catch: Throwable -> 0x0094
            java.lang.String r5 = "threads count: %d, dump limit: %d.\n"
            r6 = 2
            java.lang.Object[] r7 = new java.lang.Object[r6]     // Catch: Throwable -> 0x0094
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch: Throwable -> 0x0094
            r7[r0] = r3     // Catch: Throwable -> 0x0094
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch: Throwable -> 0x0094
            r3 = 1
            r7[r3] = r2     // Catch: Throwable -> 0x0094
            java.lang.String r2 = java.lang.String.format(r4, r5, r7)     // Catch: Throwable -> 0x0094
            java.lang.String r4 = "UTF-8"
            byte[] r2 = r2.getBytes(r4)     // Catch: Throwable -> 0x0094
            r11.write(r2)     // Catch: Throwable -> 0x0094
            java.lang.String r2 = " tid     name\n"
            java.lang.String r4 = "UTF-8"
            byte[] r2 = r2.getBytes(r4)     // Catch: Throwable -> 0x0094
            r11.write(r2)     // Catch: Throwable -> 0x0094
            int r2 = r1.length     // Catch: Throwable -> 0x0094
            r4 = r0
        L_0x005d:
            if (r4 >= r2) goto L_0x0098
            r5 = r1[r4]     // Catch: Throwable -> 0x0094
            java.io.File r7 = new java.io.File     // Catch: Throwable -> 0x0094
            java.lang.String r8 = r5.getPath()     // Catch: Throwable -> 0x0094
            java.lang.String r9 = "comm"
            r7.<init>(r8, r9)     // Catch: Throwable -> 0x0094
            r8 = 128(0x80, float:1.794E-43)
            java.lang.String r7 = com.uc.crashsdk.a.g.a(r7, r8, r0)     // Catch: Throwable -> 0x0094
            java.lang.String r7 = k(r7)     // Catch: Throwable -> 0x0094
            java.util.Locale r8 = java.util.Locale.US     // Catch: Throwable -> 0x0094
            java.lang.String r9 = "%5s %s\n"
            java.lang.Object[] r10 = new java.lang.Object[r6]     // Catch: Throwable -> 0x0094
            java.lang.String r5 = r5.getName()     // Catch: Throwable -> 0x0094
            r10[r0] = r5     // Catch: Throwable -> 0x0094
            r10[r3] = r7     // Catch: Throwable -> 0x0094
            java.lang.String r5 = java.lang.String.format(r8, r9, r10)     // Catch: Throwable -> 0x0094
            java.lang.String r7 = "UTF-8"
            byte[] r5 = r5.getBytes(r7)     // Catch: Throwable -> 0x0094
            r11.write(r5)     // Catch: Throwable -> 0x0094
            int r4 = r4 + 1
            goto L_0x005d
        L_0x0094:
            r0 = move-exception
            a(r0, r11)
        L_0x0098:
            a(r11)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.crashsdk.e.d(java.io.OutputStream):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:55:0x006f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x009d A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void e(java.io.OutputStream r10) {
        /*
            Method dump skipped, instructions count: 226
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.crashsdk.e.e(java.io.OutputStream):void");
    }

    private static void a(a aVar) {
        try {
            aVar.a(String.format(Locale.US, "log end: %s\n", m()));
        } catch (Throwable th) {
            a(th, aVar);
        }
    }

    private static void b(a aVar) {
        h = false;
        try {
            aVar.write((r("LOG_END") + "\n").getBytes("UTF-8"));
        } catch (Throwable th) {
            g.a(th);
        }
        h = true;
    }

    public static int a(OutputStream outputStream, String str, int i2) {
        int i3;
        Throwable th;
        if (str == null) {
            a(outputStream);
            return 0;
        }
        try {
            String a2 = com.uc.crashsdk.a.b.a(str);
            if (a2 == null) {
                a2 = "file: '" + str + "' not found or decode failed!";
            }
            i3 = a2.length();
            if (i3 > i2 + 32) {
                i3 = i2;
            }
            if (i3 > 0) {
                try {
                    outputStream.write(a2.getBytes("UTF-8"), 0, i3);
                    outputStream.write("\n".getBytes("UTF-8"));
                } catch (Throwable th2) {
                    th = th2;
                    a(th, outputStream);
                    a(outputStream);
                    return i3;
                }
            }
            if (i3 < a2.length()) {
                outputStream.write(String.format(Locale.US, "(truncated %d bytes)\n", Integer.valueOf(a2.length() - i3)).getBytes("UTF-8"));
            }
        } catch (Throwable th3) {
            th = th3;
            i3 = 0;
        }
        a(outputStream);
        return i3;
    }

    public static int b(OutputStream outputStream, String str, int i2) {
        Throwable th;
        int i3;
        Throwable th2;
        int i4;
        try {
            DataInputStream dataInputStream = null;
            try {
                File file = new File(str);
                if (file.exists()) {
                    byte[] Q2 = Q();
                    if (Q2 == null) {
                        outputStream.write("(alloc buffer failed!)\n".getBytes("UTF-8"));
                        g.a((Closeable) null);
                        return 0;
                    }
                    dataInputStream = new DataInputStream(new FileInputStream(file));
                    i4 = 0;
                    i3 = 0;
                    boolean z2 = false;
                    while (true) {
                        try {
                            int read = dataInputStream.read(Q2);
                            if (read == -1) {
                                break;
                            }
                            i4 += read;
                            int i5 = i2 - i3;
                            if (read <= i5 + 32) {
                                i5 = read;
                            }
                            if (i5 > 0 && !z2) {
                                outputStream.write(Q2, 0, i5);
                                i3 += i5;
                            }
                            if (!z2) {
                                z2 = i5 < read || i3 >= i2;
                            }
                        } catch (Throwable th3) {
                            th2 = th3;
                            a(th2, outputStream);
                            g.a(dataInputStream);
                            a(outputStream);
                            return i3;
                        }
                    }
                } else {
                    outputStream.write(("file: '" + str + "' not exists!\n").getBytes("UTF-8"));
                    dataInputStream = null;
                    i4 = 0;
                    i3 = 0;
                }
                if (i3 > 0) {
                    outputStream.write("\n".getBytes("UTF-8"));
                }
                if (i3 < i4) {
                    outputStream.write(String.format(Locale.US, "(truncated %d bytes)\n", Integer.valueOf(i4 - i3)).getBytes("UTF-8"));
                }
                g.a(dataInputStream);
            } catch (Throwable th4) {
                th2 = th4;
                i3 = 0;
            }
            a(outputStream);
            return i3;
        } catch (Throwable th5) {
            th = th5;
        }
    }

    public static String a(int i2) {
        try {
            String a2 = g.a(new File(String.format(Locale.US, "/proc/%d/cmdline", Integer.valueOf(i2))), 128, false);
            return g.b(a2) ? k(a2) : "unknown";
        } catch (Throwable th) {
            g.a(th);
            return "unknown";
        }
    }

    private static String k(String str) {
        if (!g.b(str)) {
            return "";
        }
        int indexOf = str.indexOf(0);
        if (indexOf >= 0) {
            str = str.substring(0, indexOf);
        }
        return str.trim();
    }

    public static String h() {
        String str = m;
        if (str != null) {
            return str;
        }
        String a2 = a(Process.myPid());
        m = a2;
        return a2;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(57:350|3|4|(2:270|6)|11|(1:13)(1:14)|15|(3:306|16|(1:18))|24|(2:300|26)|(2:286|32)|(7:323|36|304|37|(1:39)|43|(1:47))|(2:302|51)|(2:284|55)|59|(2:333|60)|(2:348|64)|(2:325|69)|73|268|74|78|(2:327|79)|(2:297|83)|87|(2:345|88)|92|(4:94|313|95|99)|(2:282|100)|104|(2:331|105)|(2:318|109)|(3:337|113|(1:115)(2:116|(1:118)))|122|(2:320|123)|127|(4:129|276|130|134)(3:322|136|(7:143|290|144|145|315|146|(28:148|149|299|150|295|151|152|292|153|170|310|171|175|(3:177|341|178)(7:288|182|(1:184)|185|(1:187)|188|(4:190|(1:192)(1:194)|193|195))|198|343|199|203|(4:205|308|206|210)|280|211|335|215|219|(4:221|329|222|226)|227|266|228)))|135|170|310|171|175|(0)(0)|198|343|199|203|(0)|280|211|335|215|219|(0)|227|266|228) */
    /* JADX WARN: Can't wrap try/catch for region: R(62:350|3|4|(2:270|6)|11|(1:13)(1:14)|15|(3:306|16|(1:18))|24|(2:300|26)|286|32|(7:323|36|304|37|(1:39)|43|(1:47))|302|51|284|55|59|(2:333|60)|(2:348|64)|(2:325|69)|73|268|74|78|(2:327|79)|297|83|87|(2:345|88)|92|(4:94|313|95|99)|282|100|104|(2:331|105)|(2:318|109)|(3:337|113|(1:115)(2:116|(1:118)))|122|(2:320|123)|127|(4:129|276|130|134)(3:322|136|(7:143|290|144|145|315|146|(28:148|149|299|150|295|151|152|292|153|170|310|171|175|(3:177|341|178)(7:288|182|(1:184)|185|(1:187)|188|(4:190|(1:192)(1:194)|193|195))|198|343|199|203|(4:205|308|206|210)|280|211|335|215|219|(4:221|329|222|226)|227|266|228)))|135|170|310|171|175|(0)(0)|198|343|199|203|(0)|280|211|335|215|219|(0)|227|266|228) */
    /* JADX WARN: Can't wrap try/catch for region: R(75:350|3|4|(2:270|6)|11|(1:13)(1:14)|15|306|16|(1:18)|24|(2:300|26)|286|32|323|36|304|37|(1:39)|43|(1:47)|302|51|284|55|59|333|60|(2:348|64)|(2:325|69)|73|268|74|78|327|79|297|83|87|(2:345|88)|92|(4:94|313|95|99)|282|100|104|331|105|318|109|(3:337|113|(1:115)(2:116|(1:118)))|122|320|123|127|(4:129|276|130|134)(3:322|136|(7:143|290|144|145|315|146|(28:148|149|299|150|295|151|152|292|153|170|310|171|175|(3:177|341|178)(7:288|182|(1:184)|185|(1:187)|188|(4:190|(1:192)(1:194)|193|195))|198|343|199|203|(4:205|308|206|210)|280|211|335|215|219|(4:221|329|222|226)|227|266|228)))|135|170|310|171|175|(0)(0)|198|343|199|203|(0)|280|211|335|215|219|(0)|227|266|228) */
    /* JADX WARN: Can't wrap try/catch for region: R(77:350|3|4|(2:270|6)|11|(1:13)(1:14)|15|306|16|(1:18)|24|(2:300|26)|286|32|323|36|304|37|(1:39)|43|(1:47)|302|51|284|55|59|333|60|(2:348|64)|(2:325|69)|73|268|74|78|327|79|297|83|87|(2:345|88)|92|(4:94|313|95|99)|282|100|104|331|105|318|109|337|113|(1:115)(2:116|(1:118))|122|320|123|127|(4:129|276|130|134)(3:322|136|(7:143|290|144|145|315|146|(28:148|149|299|150|295|151|152|292|153|170|310|171|175|(3:177|341|178)(7:288|182|(1:184)|185|(1:187)|188|(4:190|(1:192)(1:194)|193|195))|198|343|199|203|(4:205|308|206|210)|280|211|335|215|219|(4:221|329|222|226)|227|266|228)))|135|170|310|171|175|(0)(0)|198|343|199|203|(0)|280|211|335|215|219|(0)|227|266|228) */
    /* JADX WARN: Code restructure failed: missing block: B:172:0x0395, code lost:
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:174:0x0397, code lost:
        a(r0, r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:200:0x0477, code lost:
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:202:0x0479, code lost:
        a(r0, r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:212:0x049f, code lost:
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:214:0x04a1, code lost:
        com.uc.crashsdk.a.g.a(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:216:0x04ad, code lost:
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:218:0x04af, code lost:
        a(r0, r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:232:0x04e0, code lost:
        if (r8 != 0) goto L_0x04fa;
     */
    /* JADX WARN: Code restructure failed: missing block: B:245:0x04f8, code lost:
        if (r27 == 0) goto L_0x04fd;
     */
    /* JADX WARN: Code restructure failed: missing block: B:246:0x04fa, code lost:
        b(r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:247:0x04fd, code lost:
        com.uc.crashsdk.a.g.a(r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:248:0x0502, code lost:
        if (com.uc.crashsdk.e.af != false) goto L_0x0507;
     */
    /* JADX WARN: Code restructure failed: missing block: B:249:0x0504, code lost:
        q(r26);
     */
    /* JADX WARN: Code restructure failed: missing block: B:251:0x0509, code lost:
        if (com.uc.crashsdk.e.af != false) goto L_0x0514;
     */
    /* JADX WARN: Code restructure failed: missing block: B:252:0x050b, code lost:
        r1 = a(l(r26));
     */
    /* JADX WARN: Code restructure failed: missing block: B:253:0x0514, code lost:
        r1 = r26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:254:0x0516, code lost:
        b(r1, "java");
     */
    /* JADX WARN: Code restructure failed: missing block: B:255:0x051c, code lost:
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:256:0x051d, code lost:
        com.uc.crashsdk.a.g.a(r0);
     */
    /* JADX WARN: Removed duplicated region for block: B:177:0x039e  */
    /* JADX WARN: Removed duplicated region for block: B:205:0x0480 A[Catch: Throwable -> 0x001a, all -> 0x0522, TRY_LEAVE, TryCatch #2 {Throwable -> 0x001a, blocks: (B:6:0x0010, B:13:0x0021, B:169:0x0383, B:170:0x0386, B:174:0x0397, B:175:0x039a, B:181:0x03b7, B:198:0x0465, B:202:0x0479, B:203:0x047c, B:205:0x0480, B:209:0x0495, B:210:0x0498, B:214:0x04a1, B:218:0x04af, B:219:0x04b2, B:221:0x04b6, B:225:0x04cb, B:226:0x04ce, B:227:0x04d1, B:231:0x04dd), top: B:270:0x0010 }] */
    /* JADX WARN: Removed duplicated region for block: B:221:0x04b6 A[Catch: Throwable -> 0x001a, all -> 0x0522, TRY_LEAVE, TryCatch #2 {Throwable -> 0x001a, blocks: (B:6:0x0010, B:13:0x0021, B:169:0x0383, B:170:0x0386, B:174:0x0397, B:175:0x039a, B:181:0x03b7, B:198:0x0465, B:202:0x0479, B:203:0x047c, B:205:0x0480, B:209:0x0495, B:210:0x0498, B:214:0x04a1, B:218:0x04af, B:219:0x04b2, B:221:0x04b6, B:225:0x04cb, B:226:0x04ce, B:227:0x04d1, B:231:0x04dd), top: B:270:0x0010 }] */
    /* JADX WARN: Removed duplicated region for block: B:262:0x052a  */
    /* JADX WARN: Removed duplicated region for block: B:288:0x03bc A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.lang.String a(java.lang.Throwable r25, java.lang.String r26, long r27, boolean r29) {
        /*
            Method dump skipped, instructions count: 1329
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.crashsdk.e.a(java.lang.Throwable, java.lang.String, long, boolean):java.lang.String");
    }

    private static void f(OutputStream outputStream) {
        String str;
        try {
            outputStream.write("recent status:\n".getBytes("UTF-8"));
        } catch (Throwable th) {
            a(th, outputStream);
        }
        try {
            if (af) {
                str = r("LASTVER");
            } else {
                str = a.m();
            }
            outputStream.write(String.format(Locale.US, "last version: '%s'\n", str).getBytes("UTF-8"));
        } catch (Throwable th2) {
            a(th2, outputStream);
        }
        try {
            synchronized (r) {
                if (t != null) {
                    outputStream.write(String.format(Locale.US, "generating log: %s\n", t).getBytes("UTF-8"));
                }
                if (s > 0 || r.size() > 0) {
                    outputStream.write(String.format(Locale.US, "generated %d logs, recent are:\n", Integer.valueOf(s)).getBytes("UTF-8"));
                    Iterator<String> it = r.iterator();
                    while (it.hasNext()) {
                        outputStream.write(String.format(Locale.US, "* %s\n", it.next()).getBytes("UTF-8"));
                    }
                }
            }
            outputStream.write(String.format(Locale.US, "dumping all threads: %s\n", Boolean.valueOf(u)).getBytes("UTF-8"));
            if (v != null) {
                outputStream.write(String.format(Locale.US, "dumping threads: %s\n", v).getBytes("UTF-8"));
            }
        } catch (Throwable th3) {
            a(th3, outputStream);
        }
        a(outputStream);
    }

    private static String l(String str) {
        String a2 = com.uc.crashsdk.a.b.a(str, g.w(), g.v());
        if (!str.equals(a2)) {
            File file = new File(str);
            if (file.exists()) {
                file.delete();
            }
        }
        return a2;
    }

    public static String a(String str) {
        int lastIndexOf;
        int indexOf;
        int i2;
        int indexOf2;
        File file;
        byte[] e;
        if (!g.x() || (lastIndexOf = str.lastIndexOf(47)) <= 0 || (indexOf = str.indexOf(95, lastIndexOf)) <= lastIndexOf || (indexOf2 = str.indexOf(95, (i2 = indexOf + 1))) <= indexOf) {
            return str;
        }
        String d2 = g.d("CrashSDK" + str.substring(lastIndexOf + 1, indexOf) + str.substring(i2, indexOf2));
        if (d2 == null || (e = g.e((file = new File(str)))) == null || e.length <= 0) {
            return str;
        }
        byte[] bArr = null;
        try {
            bArr = com.uc.crashsdk.a.c.b(e, d2.substring(0, 16).getBytes());
        } catch (Throwable th) {
            g.a(th);
        }
        if (bArr == null) {
            return str;
        }
        String str2 = str + ".ec";
        File file2 = new File(str2 + DiskFileUpload.postfix);
        if (!g.a(file2, bArr)) {
            return str;
        }
        if (!file2.renameTo(new File(str2))) {
            file2.delete();
            return str;
        }
        file.delete();
        return str2;
    }

    public static void a(Throwable th, OutputStream outputStream) {
        if (outputStream != null) {
            try {
                outputStream.write("[DEBUG] CrashHandler occurred new exception:\n".getBytes("UTF-8"));
                th.printStackTrace(new PrintStream(outputStream));
                outputStream.write("\n\n".getBytes("UTF-8"));
            } catch (Throwable th2) {
                g.a(th2);
            }
        }
        g.a(th);
    }

    /* JADX WARN: Removed duplicated region for block: B:109:0x02ec  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x02fa A[Catch: Throwable -> 0x0436, all -> 0x0434, TryCatch #2 {, blocks: (B:175:0x042e, B:176:0x0430, B:177:0x043f, B:4:0x000a, B:6:0x0010, B:8:0x001f, B:9:0x0034, B:11:0x003a, B:12:0x004d, B:14:0x005d, B:16:0x0067, B:18:0x006d, B:20:0x007b, B:22:0x008d, B:25:0x00ae, B:27:0x00be, B:31:0x00cc, B:38:0x00ea, B:42:0x00f8, B:45:0x0122, B:47:0x0128, B:49:0x013c, B:51:0x0151, B:58:0x01c1, B:59:0x01c5, B:61:0x01d9, B:63:0x01dd, B:64:0x01df, B:66:0x01e4, B:67:0x01e6, B:68:0x01eb, B:72:0x01f5, B:74:0x0203, B:76:0x0209, B:78:0x020e, B:79:0x0224, B:81:0x022a, B:83:0x0233, B:85:0x0241, B:87:0x0260, B:88:0x0273, B:90:0x0285, B:92:0x0294, B:93:0x02a5, B:95:0x02ac, B:97:0x02b2, B:100:0x02ba, B:102:0x02be, B:104:0x02d2, B:106:0x02d6, B:107:0x02e8, B:113:0x02fa, B:116:0x0303, B:119:0x030f, B:121:0x031d, B:123:0x032a, B:126:0x0331, B:130:0x033e, B:132:0x034a, B:134:0x0364, B:135:0x0369, B:137:0x0378, B:138:0x037f, B:139:0x0385, B:140:0x03a3, B:142:0x03a9, B:145:0x03b1, B:147:0x03bf, B:150:0x03d3, B:152:0x03da, B:154:0x03e1, B:156:0x03e8, B:158:0x03ef, B:160:0x03f6, B:164:0x0401, B:166:0x0408, B:168:0x040f, B:170:0x0416, B:172:0x041d, B:173:0x0425), top: B:182:0x000a }] */
    /* JADX WARN: Removed duplicated region for block: B:132:0x034a A[Catch: Throwable -> 0x0436, all -> 0x0434, TryCatch #2 {, blocks: (B:175:0x042e, B:176:0x0430, B:177:0x043f, B:4:0x000a, B:6:0x0010, B:8:0x001f, B:9:0x0034, B:11:0x003a, B:12:0x004d, B:14:0x005d, B:16:0x0067, B:18:0x006d, B:20:0x007b, B:22:0x008d, B:25:0x00ae, B:27:0x00be, B:31:0x00cc, B:38:0x00ea, B:42:0x00f8, B:45:0x0122, B:47:0x0128, B:49:0x013c, B:51:0x0151, B:58:0x01c1, B:59:0x01c5, B:61:0x01d9, B:63:0x01dd, B:64:0x01df, B:66:0x01e4, B:67:0x01e6, B:68:0x01eb, B:72:0x01f5, B:74:0x0203, B:76:0x0209, B:78:0x020e, B:79:0x0224, B:81:0x022a, B:83:0x0233, B:85:0x0241, B:87:0x0260, B:88:0x0273, B:90:0x0285, B:92:0x0294, B:93:0x02a5, B:95:0x02ac, B:97:0x02b2, B:100:0x02ba, B:102:0x02be, B:104:0x02d2, B:106:0x02d6, B:107:0x02e8, B:113:0x02fa, B:116:0x0303, B:119:0x030f, B:121:0x031d, B:123:0x032a, B:126:0x0331, B:130:0x033e, B:132:0x034a, B:134:0x0364, B:135:0x0369, B:137:0x0378, B:138:0x037f, B:139:0x0385, B:140:0x03a3, B:142:0x03a9, B:145:0x03b1, B:147:0x03bf, B:150:0x03d3, B:152:0x03da, B:154:0x03e1, B:156:0x03e8, B:158:0x03ef, B:160:0x03f6, B:164:0x0401, B:166:0x0408, B:168:0x040f, B:170:0x0416, B:172:0x041d, B:173:0x0425), top: B:182:0x000a }] */
    /* JADX WARN: Removed duplicated region for block: B:140:0x03a3 A[Catch: Throwable -> 0x0436, all -> 0x0434, TryCatch #2 {, blocks: (B:175:0x042e, B:176:0x0430, B:177:0x043f, B:4:0x000a, B:6:0x0010, B:8:0x001f, B:9:0x0034, B:11:0x003a, B:12:0x004d, B:14:0x005d, B:16:0x0067, B:18:0x006d, B:20:0x007b, B:22:0x008d, B:25:0x00ae, B:27:0x00be, B:31:0x00cc, B:38:0x00ea, B:42:0x00f8, B:45:0x0122, B:47:0x0128, B:49:0x013c, B:51:0x0151, B:58:0x01c1, B:59:0x01c5, B:61:0x01d9, B:63:0x01dd, B:64:0x01df, B:66:0x01e4, B:67:0x01e6, B:68:0x01eb, B:72:0x01f5, B:74:0x0203, B:76:0x0209, B:78:0x020e, B:79:0x0224, B:81:0x022a, B:83:0x0233, B:85:0x0241, B:87:0x0260, B:88:0x0273, B:90:0x0285, B:92:0x0294, B:93:0x02a5, B:95:0x02ac, B:97:0x02b2, B:100:0x02ba, B:102:0x02be, B:104:0x02d2, B:106:0x02d6, B:107:0x02e8, B:113:0x02fa, B:116:0x0303, B:119:0x030f, B:121:0x031d, B:123:0x032a, B:126:0x0331, B:130:0x033e, B:132:0x034a, B:134:0x0364, B:135:0x0369, B:137:0x0378, B:138:0x037f, B:139:0x0385, B:140:0x03a3, B:142:0x03a9, B:145:0x03b1, B:147:0x03bf, B:150:0x03d3, B:152:0x03da, B:154:0x03e1, B:156:0x03e8, B:158:0x03ef, B:160:0x03f6, B:164:0x0401, B:166:0x0408, B:168:0x040f, B:170:0x0416, B:172:0x041d, B:173:0x0425), top: B:182:0x000a }] */
    /* JADX WARN: Removed duplicated region for block: B:146:0x03bc  */
    /* JADX WARN: Removed duplicated region for block: B:189:0x03b1 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x011f  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x01bb  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x01d9 A[Catch: Throwable -> 0x0436, all -> 0x0434, TryCatch #2 {, blocks: (B:175:0x042e, B:176:0x0430, B:177:0x043f, B:4:0x000a, B:6:0x0010, B:8:0x001f, B:9:0x0034, B:11:0x003a, B:12:0x004d, B:14:0x005d, B:16:0x0067, B:18:0x006d, B:20:0x007b, B:22:0x008d, B:25:0x00ae, B:27:0x00be, B:31:0x00cc, B:38:0x00ea, B:42:0x00f8, B:45:0x0122, B:47:0x0128, B:49:0x013c, B:51:0x0151, B:58:0x01c1, B:59:0x01c5, B:61:0x01d9, B:63:0x01dd, B:64:0x01df, B:66:0x01e4, B:67:0x01e6, B:68:0x01eb, B:72:0x01f5, B:74:0x0203, B:76:0x0209, B:78:0x020e, B:79:0x0224, B:81:0x022a, B:83:0x0233, B:85:0x0241, B:87:0x0260, B:88:0x0273, B:90:0x0285, B:92:0x0294, B:93:0x02a5, B:95:0x02ac, B:97:0x02b2, B:100:0x02ba, B:102:0x02be, B:104:0x02d2, B:106:0x02d6, B:107:0x02e8, B:113:0x02fa, B:116:0x0303, B:119:0x030f, B:121:0x031d, B:123:0x032a, B:126:0x0331, B:130:0x033e, B:132:0x034a, B:134:0x0364, B:135:0x0369, B:137:0x0378, B:138:0x037f, B:139:0x0385, B:140:0x03a3, B:142:0x03a9, B:145:0x03b1, B:147:0x03bf, B:150:0x03d3, B:152:0x03da, B:154:0x03e1, B:156:0x03e8, B:158:0x03ef, B:160:0x03f6, B:164:0x0401, B:166:0x0408, B:168:0x040f, B:170:0x0416, B:172:0x041d, B:173:0x0425), top: B:182:0x000a }] */
    /* JADX WARN: Removed duplicated region for block: B:70:0x01f1  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x01f3  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x020e A[Catch: Throwable -> 0x0436, all -> 0x0434, TryCatch #2 {, blocks: (B:175:0x042e, B:176:0x0430, B:177:0x043f, B:4:0x000a, B:6:0x0010, B:8:0x001f, B:9:0x0034, B:11:0x003a, B:12:0x004d, B:14:0x005d, B:16:0x0067, B:18:0x006d, B:20:0x007b, B:22:0x008d, B:25:0x00ae, B:27:0x00be, B:31:0x00cc, B:38:0x00ea, B:42:0x00f8, B:45:0x0122, B:47:0x0128, B:49:0x013c, B:51:0x0151, B:58:0x01c1, B:59:0x01c5, B:61:0x01d9, B:63:0x01dd, B:64:0x01df, B:66:0x01e4, B:67:0x01e6, B:68:0x01eb, B:72:0x01f5, B:74:0x0203, B:76:0x0209, B:78:0x020e, B:79:0x0224, B:81:0x022a, B:83:0x0233, B:85:0x0241, B:87:0x0260, B:88:0x0273, B:90:0x0285, B:92:0x0294, B:93:0x02a5, B:95:0x02ac, B:97:0x02b2, B:100:0x02ba, B:102:0x02be, B:104:0x02d2, B:106:0x02d6, B:107:0x02e8, B:113:0x02fa, B:116:0x0303, B:119:0x030f, B:121:0x031d, B:123:0x032a, B:126:0x0331, B:130:0x033e, B:132:0x034a, B:134:0x0364, B:135:0x0369, B:137:0x0378, B:138:0x037f, B:139:0x0385, B:140:0x03a3, B:142:0x03a9, B:145:0x03b1, B:147:0x03bf, B:150:0x03d3, B:152:0x03da, B:154:0x03e1, B:156:0x03e8, B:158:0x03ef, B:160:0x03f6, B:164:0x0401, B:166:0x0408, B:168:0x040f, B:170:0x0416, B:172:0x041d, B:173:0x0425), top: B:182:0x000a }] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0224 A[Catch: Throwable -> 0x0436, all -> 0x0434, TryCatch #2 {, blocks: (B:175:0x042e, B:176:0x0430, B:177:0x043f, B:4:0x000a, B:6:0x0010, B:8:0x001f, B:9:0x0034, B:11:0x003a, B:12:0x004d, B:14:0x005d, B:16:0x0067, B:18:0x006d, B:20:0x007b, B:22:0x008d, B:25:0x00ae, B:27:0x00be, B:31:0x00cc, B:38:0x00ea, B:42:0x00f8, B:45:0x0122, B:47:0x0128, B:49:0x013c, B:51:0x0151, B:58:0x01c1, B:59:0x01c5, B:61:0x01d9, B:63:0x01dd, B:64:0x01df, B:66:0x01e4, B:67:0x01e6, B:68:0x01eb, B:72:0x01f5, B:74:0x0203, B:76:0x0209, B:78:0x020e, B:79:0x0224, B:81:0x022a, B:83:0x0233, B:85:0x0241, B:87:0x0260, B:88:0x0273, B:90:0x0285, B:92:0x0294, B:93:0x02a5, B:95:0x02ac, B:97:0x02b2, B:100:0x02ba, B:102:0x02be, B:104:0x02d2, B:106:0x02d6, B:107:0x02e8, B:113:0x02fa, B:116:0x0303, B:119:0x030f, B:121:0x031d, B:123:0x032a, B:126:0x0331, B:130:0x033e, B:132:0x034a, B:134:0x0364, B:135:0x0369, B:137:0x0378, B:138:0x037f, B:139:0x0385, B:140:0x03a3, B:142:0x03a9, B:145:0x03b1, B:147:0x03bf, B:150:0x03d3, B:152:0x03da, B:154:0x03e1, B:156:0x03e8, B:158:0x03ef, B:160:0x03f6, B:164:0x0401, B:166:0x0408, B:168:0x040f, B:170:0x0416, B:172:0x041d, B:173:0x0425), top: B:182:0x000a }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void a(java.lang.String r31, boolean r32, boolean r33) {
        /*
            Method dump skipped, instructions count: 1100
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.crashsdk.e.a(java.lang.String, boolean, boolean):void");
    }

    public static boolean i() {
        return d;
    }

    public static boolean a(boolean z2, boolean z3) {
        if (!d) {
            if (b.d) {
                JNIBridge.set(1, true);
            }
            d = true;
        }
        try {
            String k2 = k();
            if (g.a(k2)) {
                com.uc.crashsdk.a.a.a("crashsdk", "CrashHandler url is empty!");
                return false;
            }
            synchronized (n) {
                if (f.a(z2 ? 1 : 0, new com.uc.crashsdk.a.e(406, new Object[]{k2, Boolean.valueOf(z3), Boolean.valueOf(z2)})) && z2) {
                    try {
                        n.wait();
                    } catch (InterruptedException e) {
                        g.a(e);
                    }
                }
            }
            return true;
        } catch (Throwable th) {
            g.a(th);
            return false;
        }
    }

    public static void b(boolean z2) {
        boolean z3;
        try {
            if (g.r()) {
                z3 = b.C() && !d;
            } else {
                z3 = false;
            }
            if (!z3) {
                z3 = g.s();
            }
            if (z3) {
                if (z2) {
                    String k2 = k();
                    if (!g.a(k2)) {
                        j();
                        a(k2, false, false);
                        return;
                    }
                    return;
                }
                a(true, false);
            }
        } catch (Throwable th) {
            g.a(th);
        }
    }

    public static void j() {
        try {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder(StrictMode.getThreadPolicy()).permitNetwork().build());
        } catch (Throwable th) {
            g.a(th);
        }
    }

    private static String T() {
        return g.U() + "bytes";
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [java.io.File] */
    /* JADX WARN: Type inference failed for: r1v1, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r1v2 */
    /* JADX WARN: Type inference failed for: r5v15 */
    /* JADX WARN: Type inference failed for: r5v3, types: [java.lang.Throwable, java.lang.Exception] */
    /* JADX WARN: Type inference failed for: r5v4 */
    /* JADX WARN: Unknown variable types count: 1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static boolean a(java.lang.String r5, com.uc.crashsdk.a.e r6) {
        /*
            java.lang.Object r0 = com.uc.crashsdk.e.o
            monitor-enter(r0)
            java.io.File r1 = new java.io.File     // Catch: all -> 0x0066
            r1.<init>(r5)     // Catch: all -> 0x0066
            boolean r5 = r1.exists()     // Catch: all -> 0x0066
            if (r5 != 0) goto L_0x0016
            r1.createNewFile()     // Catch: Exception -> 0x0012, all -> 0x0066
            goto L_0x0016
        L_0x0012:
            r5 = move-exception
            com.uc.crashsdk.a.g.a(r5)     // Catch: all -> 0x0066
        L_0x0016:
            r5 = 0
            r2 = 0
            java.io.RandomAccessFile r3 = new java.io.RandomAccessFile     // Catch: Exception -> 0x0027, all -> 0x0024
            java.lang.String r4 = "rw"
            r3.<init>(r1, r4)     // Catch: Exception -> 0x0027, all -> 0x0024
            java.nio.channels.FileChannel r1 = r3.getChannel()     // Catch: Exception -> 0x0027, all -> 0x0024
            goto L_0x002c
        L_0x0024:
            r6 = move-exception
            r1 = r5
            goto L_0x0062
        L_0x0027:
            r1 = move-exception
            com.uc.crashsdk.a.g.a(r1)     // Catch: Exception -> 0x0058, all -> 0x0024
            r1 = r5
        L_0x002c:
            if (r1 == 0) goto L_0x003a
            java.nio.channels.FileLock r5 = r1.lock()     // Catch: Exception -> 0x0033, all -> 0x0061
            goto L_0x003a
        L_0x0033:
            r3 = move-exception
            com.uc.crashsdk.a.g.a(r3)     // Catch: Exception -> 0x0038, all -> 0x0061
            goto L_0x003a
        L_0x0038:
            r5 = move-exception
            goto L_0x005b
        L_0x003a:
            boolean r2 = r6.a()     // Catch: all -> 0x004c
            if (r5 == 0) goto L_0x0048
            r5.release()     // Catch: Exception -> 0x0044, all -> 0x0061
            goto L_0x0048
        L_0x0044:
            r5 = move-exception
            com.uc.crashsdk.a.g.a(r5)     // Catch: Exception -> 0x0038, all -> 0x0061
        L_0x0048:
            com.uc.crashsdk.a.g.a(r1)     // Catch: all -> 0x0066
            goto L_0x005f
        L_0x004c:
            r6 = move-exception
            if (r5 == 0) goto L_0x0057
            r5.release()     // Catch: Exception -> 0x0053, all -> 0x0061
            goto L_0x0057
        L_0x0053:
            r5 = move-exception
            com.uc.crashsdk.a.g.a(r5)     // Catch: Exception -> 0x0038, all -> 0x0061
        L_0x0057:
            throw r6     // Catch: Exception -> 0x0038, all -> 0x0061
        L_0x0058:
            r6 = move-exception
            r1 = r5
            r5 = r6
        L_0x005b:
            com.uc.crashsdk.a.g.a(r5)     // Catch: all -> 0x0061
            goto L_0x0048
        L_0x005f:
            monitor-exit(r0)     // Catch: all -> 0x0066
            return r2
        L_0x0061:
            r6 = move-exception
        L_0x0062:
            com.uc.crashsdk.a.g.a(r1)     // Catch: all -> 0x0066
            throw r6     // Catch: all -> 0x0066
        L_0x0066:
            r5 = move-exception
            monitor-exit(r0)     // Catch: all -> 0x0066
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.crashsdk.e.a(java.lang.String, com.uc.crashsdk.a.e):boolean");
    }

    /* compiled from: ProGuard */
    /* loaded from: classes2.dex */
    public static class d {
        long a;
        long b;
        int c;
        int d;
        boolean e;
        boolean f;
        boolean g;

        private d() {
            this.a = 0L;
            this.b = 0L;
            this.c = 0;
            this.d = 0;
            this.e = false;
            this.f = false;
            this.g = false;
        }

        /* synthetic */ d(byte b) {
            this();
        }
    }

    private static boolean a(String str, d dVar) {
        String a2 = g.a(new File(str), 64, false);
        if (a2 == null) {
            return false;
        }
        try {
            Matcher matcher = Pattern.compile("(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)").matcher(a2);
            if (matcher.find()) {
                long parseLong = Long.parseLong(matcher.group(1));
                if (System.currentTimeMillis() - parseLong < 86400000) {
                    dVar.b = Long.parseLong(matcher.group(2));
                    dVar.c = Integer.parseInt(matcher.group(3));
                    dVar.d = Integer.parseInt(matcher.group(4));
                    dVar.a = parseLong;
                }
            }
        } catch (Throwable th) {
            g.a(th);
        }
        return true;
    }

    private static boolean b(File file) {
        int indexOf;
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(95);
        if (lastIndexOf <= 0 || (indexOf = name.indexOf(46, lastIndexOf)) <= 0) {
            return false;
        }
        String substring = name.substring(lastIndexOf + 1, indexOf);
        return "java".equals(substring) || "ucebujava".equals(substring) || LogType.NATIVE_TYPE.equals(substring) || "ucebujni".equals(substring) || LogType.UNEXP_TYPE.equals(substring) || "anr".equals(substring);
    }

    private static boolean[] m(String str) {
        int i2;
        boolean v2 = g.v();
        boolean x2 = g.x();
        if (v2 || x2) {
            if (str.endsWith(DiskFileUpload.postfix) || str.contains(".ec")) {
                v2 = false;
                x2 = false;
            } else {
                int lastIndexOf = str.lastIndexOf(File.separatorChar);
                if (lastIndexOf < 0) {
                    lastIndexOf = 0;
                    i2 = 0;
                } else {
                    i2 = 0;
                }
                do {
                    lastIndexOf = str.indexOf(95, lastIndexOf);
                    if (lastIndexOf >= 0) {
                        i2++;
                        lastIndexOf++;
                        continue;
                    }
                } while (lastIndexOf >= 0);
                if (i2 != 8) {
                    v2 = false;
                    x2 = false;
                } else {
                    String w2 = g.w();
                    if (!str.endsWith(".log")) {
                        if (g.a(w2) || !str.endsWith(w2)) {
                            v2 = false;
                            x2 = false;
                        } else {
                            v2 = false;
                        }
                    } else if (g.a(w2)) {
                        v2 = false;
                    } else if (str.indexOf(".log", str.lastIndexOf(95)) != str.lastIndexOf(".log")) {
                        v2 = false;
                    }
                }
            }
        }
        return new boolean[]{v2, x2};
    }

    private static String b(String str, boolean z2, boolean z3) {
        if (z2) {
            try {
                str = l(str);
            } catch (Throwable th) {
                g.a(th);
            }
        }
        if (!z3) {
            return str;
        }
        try {
            return a(str);
        } catch (Throwable th2) {
            g.a(th2);
            return str;
        }
    }

    public static boolean a(StringBuffer stringBuffer, String str, long j2, ArrayList<String> arrayList, ArrayList<String> arrayList2, ArrayList<String> arrayList3, String str2) {
        long j3;
        boolean a2;
        long j4;
        if (c.get()) {
            com.uc.crashsdk.a.a.b("Processing java crash, skip generate custom log: " + str);
            return false;
        }
        boolean z2 = af || b.I();
        if (!z2 && !com.uc.crashsdk.a.d.d()) {
            com.uc.crashsdk.a.a.c("DEBUG", com.uc.crashsdk.a.d.b());
            return false;
        } else if (!c(str)) {
            com.uc.crashsdk.a.a.d("DEBUG", "custom log sample miss: " + str);
            return false;
        } else if (Z()) {
            com.uc.crashsdk.a.a.b("Processing native crash, skip generate custom log: " + str);
            return false;
        } else if (stringBuffer == null || str == null) {
            return false;
        } else {
            String str3 = g.V() + j(str);
            boolean z3 = (j2 & 32) != 0;
            if (z2) {
                if (b.d) {
                    int i2 = z3 ? 1 : 0;
                    int i3 = z3 ? 1 : 0;
                    int i4 = z3 ? 1 : 0;
                    j4 = JNIBridge.nativeClientCreateConnection(str3, "custom", str, i2);
                } else {
                    j4 = 0;
                }
                if (j4 == 0) {
                    com.uc.crashsdk.a.a.d("DEBUG", "skip custom log: " + str);
                    return false;
                }
                j3 = j4;
            } else if (a(h(), str, z3)) {
                return false;
            } else {
                g.a();
                a(false);
                j3 = 0;
            }
            synchronized (p) {
                a2 = a(str3, j3, stringBuffer, str, j2, arrayList, arrayList2, arrayList3, str2);
            }
            if (a2 && !z2) {
                b(h(), str, z3);
            }
            if (j3 != 0) {
                JNIBridge.nativeClientCloseConnection(j3);
            }
            if (!a2) {
                return false;
            }
            if (!z2) {
                q(str3);
            }
            if (!z2) {
                str3 = a(l(str3));
            }
            b(str3, str);
            if (!z3 || z2) {
                return true;
            }
            try {
                a(true, false);
                return true;
            } catch (Throwable th) {
                g.a(th);
                return true;
            }
        }
    }

    public static boolean a(String str, String str2, boolean z2) {
        if (!n(str2)) {
            return false;
        }
        h.a(str, str2, true, z2);
        com.uc.crashsdk.a.a.b(String.format(Locale.US, "Custom log '%s' has reach max count!", str2));
        return true;
    }

    public static void b(String str, String str2, boolean z2) {
        h.a(str, str2, false, z2);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(13:53|4|(3:6|(3:9|(1:11)(16:58|12|13|(2:51|15)(1:18)|19|(1:23)(1:22)|24|(1:27)|47|28|49|29|55|30|31|40)|7)|57)|25|(0)|47|28|49|29|55|30|31|40) */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00da, code lost:
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x00db, code lost:
        r1 = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x00dd, code lost:
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00de, code lost:
        r1 = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00e2, code lost:
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x00e3, code lost:
        com.uc.crashsdk.a.g.a(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00e6, code lost:
        com.uc.crashsdk.a.g.a(r1);
        r1 = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00eb, code lost:
        com.uc.crashsdk.a.g.a((java.io.Closeable) r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00ee, code lost:
        throw r0;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00af A[Catch: all -> 0x00ef, TRY_LEAVE, TryCatch #5 {, blocks: (B:4:0x0005, B:6:0x0033, B:7:0x0041, B:9:0x0047, B:11:0x0051, B:12:0x0056, B:15:0x0068, B:17:0x0072, B:19:0x0079, B:24:0x0084, B:27:0x00af, B:31:0x00d6, B:39:0x00e6, B:40:0x00e9, B:42:0x00eb, B:43:0x00ee), top: B:53:0x0005, inners: #4 }] */
    /* JADX WARN: Type inference failed for: r10v1, types: [java.lang.Object[]] */
    /* JADX WARN: Type inference failed for: r13v2, types: [java.lang.Object[]] */
    /* JADX WARN: Type inference failed for: r1v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v10, types: [java.lang.Long] */
    /* JADX WARN: Type inference failed for: r1v12 */
    /* JADX WARN: Type inference failed for: r1v2, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v5 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static boolean n(java.lang.String r17) {
        /*
            Method dump skipped, instructions count: 242
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.crashsdk.e.n(java.lang.String):boolean");
    }

    private static void a(a aVar, String str, long j2) {
        String str2 = null;
        if (b.d) {
            try {
                aVar.flush();
            } catch (Throwable th) {
                g.a(th);
            }
            str2 = JNIBridge.nativeDumpThreads(str, j2);
            if (af || str2 == null || str2.length() >= 512 || !str2.startsWith("/") || str2.indexOf(10) >= 0) {
                str2 = str2;
            } else {
                if (!new File(str2).exists()) {
                    str2 = "Can not found " + str2;
                }
                str2 = str2;
            }
        } else {
            str2 = "Native not initialized, skip dump!";
        }
        if (str2 != null) {
            try {
                aVar.write(str2.getBytes("UTF-8"));
                aVar.write("\n".getBytes("UTF-8"));
            } catch (Throwable th2) {
                g.a(th2);
            }
            a((OutputStream) aVar);
        } else if (str2 != null && !af) {
            b(aVar, str2, 1048576);
            File file = new File(str2);
            if (file.exists()) {
                file.delete();
            }
        }
        try {
            aVar.flush();
        } catch (Throwable th3) {
            g.a(th3);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0043 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0044  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static boolean a(java.lang.String r17, long r18, java.lang.StringBuffer r20, java.lang.String r21, long r22, java.util.ArrayList<java.lang.String> r24, java.util.ArrayList<java.lang.String> r25, java.util.ArrayList<java.lang.String> r26, java.lang.String r27) {
        /*
            Method dump skipped, instructions count: 398
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.crashsdk.e.a(java.lang.String, long, java.lang.StringBuffer, java.lang.String, long, java.util.ArrayList, java.util.ArrayList, java.util.ArrayList, java.lang.String):boolean");
    }

    public static String k() {
        if (g.a(w)) {
            synchronized (y) {
                w = g.a(b.f(), x, true);
            }
        }
        return w;
    }

    public static void a(String str, boolean z2) {
        if (z2) {
            x = str;
            return;
        }
        synchronized (y) {
            w = str;
            com.uc.crashsdk.a.b.a(b.f(), str + "\n");
        }
    }

    public static void b(String str) {
        synchronized (z) {
            String i2 = b.i();
            com.uc.crashsdk.a.b.a(i2, str + "\n");
        }
    }

    public static boolean c(String str) {
        if (af) {
            return true;
        }
        try {
            return o(str);
        } catch (Throwable th) {
            g.a(th);
            return true;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x00a0 A[Catch: all -> 0x0106, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x0008, B:7:0x0018, B:9:0x0023, B:10:0x002d, B:11:0x0033, B:13:0x003b, B:14:0x0044, B:16:0x004c, B:18:0x0054, B:20:0x005c, B:26:0x006a, B:28:0x0074, B:30:0x0081, B:32:0x008b, B:33:0x0096, B:35:0x00a0, B:38:0x00ae, B:49:0x00d3, B:52:0x00de, B:56:0x00ee, B:63:0x00f9, B:67:0x0104), top: B:72:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00ab  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00ae A[Catch: all -> 0x0106, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x0008, B:7:0x0018, B:9:0x0023, B:10:0x002d, B:11:0x0033, B:13:0x003b, B:14:0x0044, B:16:0x004c, B:18:0x0054, B:20:0x005c, B:26:0x006a, B:28:0x0074, B:30:0x0081, B:32:0x008b, B:33:0x0096, B:35:0x00a0, B:38:0x00ae, B:49:0x00d3, B:52:0x00de, B:56:0x00ee, B:63:0x00f9, B:67:0x0104), top: B:72:0x0003 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static boolean o(java.lang.String r14) {
        /*
            Method dump skipped, instructions count: 265
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.crashsdk.e.o(java.lang.String):boolean");
    }

    private static Map<String, Integer> p(String str) {
        HashMap hashMap = new HashMap();
        for (String str2 : str.split("\\|", 30)) {
            String[] split = str2.split(Constants.COLON_SEPARATOR, 3);
            if (split.length == 2) {
                String trim = split[0].trim();
                if (!g.a(trim)) {
                    int i2 = 1;
                    try {
                        i2 = Integer.parseInt(split[1].trim(), 10);
                    } catch (Throwable th) {
                        g.a(th);
                    }
                    hashMap.put(trim, Integer.valueOf(i2));
                }
            }
        }
        return hashMap;
    }

    public static void l() {
        if (!af) {
            f.a(1, new com.uc.crashsdk.a.e(411), 1000L);
        }
    }

    public static String m() {
        return a(new Date());
    }

    private static String a(Date date) {
        return String.format(Locale.US, "%d%02d%02d%02d%02d%02d", Integer.valueOf(date.getYear() + org.fourthline.cling.model.Constants.UPNP_MULTICAST_PORT), Integer.valueOf(date.getMonth() + 1), Integer.valueOf(date.getDate()), Integer.valueOf(date.getHours()), Integer.valueOf(date.getMinutes()), Integer.valueOf(date.getSeconds()));
    }

    public static void n() {
        b = System.currentTimeMillis();
    }

    private static void q(String str) {
        if (g.q()) {
            try {
                aa();
            } catch (Throwable th) {
                g.a(th);
            }
            if (str != null && !"".equals(str)) {
                try {
                    File file = new File(g.W());
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    com.uc.crashsdk.a.a.a("crashsdk", "copy log to: " + file);
                    g.a(new File(str), file);
                } catch (Throwable th2) {
                    g.a(th2);
                }
            }
        }
    }

    private static void b(String str, String str2) {
        try {
            d.a(str, h(), str2);
        } catch (Throwable th) {
            g.a(th);
        }
    }

    public static void o() {
        Throwable th;
        if (g.a(B)) {
            String str = null;
            try {
                File file = new File(g.U() + "unique");
                if (file.exists()) {
                    String a2 = g.a(file, 48, false);
                    if (a2 != null) {
                        try {
                            try {
                                if (a2.length() == 36) {
                                    str = a2.replaceAll("[^0-9a-zA-Z-]", Constants.ACCEPT_TIME_SEPARATOR_SERVER);
                                }
                            } catch (Exception e) {
                                g.a(e);
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            str = a2;
                            g.a(th);
                            B = str;
                        }
                    }
                    str = a2;
                }
                if (g.a(str)) {
                    b.D();
                    str = UUID.randomUUID().toString();
                    if (!g.a(str)) {
                        g.a(file, str.getBytes());
                    }
                }
            } catch (Throwable th3) {
                th = th3;
            }
            B = str;
        }
    }

    public static String p() {
        return B;
    }

    private static String r(String str) {
        return String.format("$^%s^$", str);
    }

    public static void a(OutputStream outputStream, String str, String str2, int i2, boolean z2, boolean z3) {
        int i3 = 0;
        h = false;
        try {
            Locale locale = Locale.US;
            Object[] objArr = new Object[5];
            objArr[0] = str;
            objArr[1] = str2;
            objArr[2] = Integer.valueOf(i2);
            objArr[3] = Integer.valueOf(z2 ? 1 : 0);
            if (z3) {
                i3 = 1;
            }
            objArr[4] = Integer.valueOf(i3);
            outputStream.write(String.format(locale, "$^%s`%s`%d`%d,%d^$", objArr).getBytes("UTF-8"));
        } catch (Throwable th) {
            g.a(th);
        }
        h = true;
        a(outputStream);
    }

    public static void a(OutputStream outputStream, String str, String str2) {
        h = false;
        try {
            outputStream.write(String.format(Locale.US, "$^%s`%s^$", str, str2).getBytes("UTF-8"));
        } catch (Throwable th) {
            g.a(th);
        }
        h = true;
    }

    /* compiled from: ProGuard */
    /* loaded from: classes2.dex */
    public static class c extends BroadcastReceiver {
        private c() {
        }

        /* synthetic */ c(byte b) {
            this();
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.intent.action.BATTERY_CHANGED".equals(action)) {
                int unused = e.C = intent.getIntExtra(com.xiaomi.onetrack.a.a.d, -1);
                int unused2 = e.D = intent.getIntExtra("scale", -1);
                int unused3 = e.E = intent.getIntExtra("voltage", -1);
                int unused4 = e.F = intent.getIntExtra("health", -1);
                int unused5 = e.G = intent.getIntExtra("plugged", -1);
                int unused6 = e.H = intent.getIntExtra("status", -1);
                int unused7 = e.I = intent.getIntExtra("temperature", -1);
                String unused8 = e.J = intent.getStringExtra("technology");
                if (e.I() >= 2) {
                    e.J();
                    int unused9 = e.M = 0;
                }
            } else if ("android.intent.action.BATTERY_LOW".equals(action) || "android.intent.action.BATTERY_OKAY".equals(action)) {
                boolean unused10 = e.K = "android.intent.action.BATTERY_LOW".equals(action);
                e.J();
            } else if ("android.intent.action.ANR".equals(action)) {
                try {
                    e.d(context);
                } catch (Throwable th) {
                    g.a(th);
                }
            }
        }
    }

    private static boolean U() {
        return Build.VERSION.SDK_INT < 29;
    }

    public static void a(Context context) {
        try {
            if (U()) {
                context.registerReceiver(new c((byte) 0), new IntentFilter("android.intent.action.ANR"), null, f.a(3));
            }
        } catch (Throwable th) {
            g.a(th);
        }
    }

    public static boolean d(Context context) {
        List<ActivityManager.ProcessErrorStateInfo> processesInErrorState;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(IDMServer.PERSIST_TYPE_ACTIVITY);
        boolean z2 = false;
        if (activityManager == null || (processesInErrorState = activityManager.getProcessesInErrorState()) == null) {
            return false;
        }
        int myPid = Process.myPid();
        Iterator<ActivityManager.ProcessErrorStateInfo> it = processesInErrorState.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            ActivityManager.ProcessErrorStateInfo next = it.next();
            if (next.pid == myPid) {
                O = true;
                if (N()) {
                    com.uc.crashsdk.a.a.d("crashsdk", "ANR occurred in process: " + next.processName);
                }
                if (b.d) {
                    JNIBridge.set(130, next.longMsg);
                }
                z2 = true;
            }
        }
        if (!z2 && b.d) {
            V();
        }
        return true;
    }

    private static void V() {
        if (!O && !b.C() && !b.I()) {
            JNIBridge.cmd(18);
        }
    }

    public static void q() {
        O = false;
        if (!b.y()) {
            f.a(3, new com.uc.crashsdk.a.e(416), 11000L);
        }
        if (!U()) {
            N = 0;
            W();
        }
    }

    private static void W() {
        f.a(3, new com.uc.crashsdk.a.e(414), 1000L);
    }

    private static StringBuilder X() {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("level: ");
            sb.append(C);
            sb.append("\n");
            sb.append("scale: ");
            sb.append(D);
            sb.append("\n");
            String str = " (?)";
            switch (F) {
                case 1:
                    str = " (Unknown)";
                    break;
                case 2:
                    str = " (Good)";
                    break;
                case 3:
                    str = " (Overheat)";
                    break;
                case 4:
                    str = " (Dead)";
                    break;
                case 5:
                    str = " (Over voltage)";
                    break;
                case 6:
                    str = " (Unspecified failure)";
                    break;
                case 7:
                    str = " (Cold)";
                    break;
            }
            sb.append("health: ");
            sb.append(F);
            sb.append(str);
            sb.append("\n");
            String str2 = " (?)";
            int i2 = G;
            if (i2 != 4) {
                switch (i2) {
                    case 0:
                        str2 = " (None)";
                        break;
                    case 1:
                        str2 = " (AC charger)";
                        break;
                    case 2:
                        str2 = " (USB port)";
                        break;
                }
            } else {
                str2 = " (Wireless)";
            }
            sb.append("pluged: ");
            sb.append(G);
            sb.append(str2);
            sb.append("\n");
            String str3 = " (?)";
            switch (H) {
                case 1:
                    str3 = " (Unknown)";
                    break;
                case 2:
                    str3 = " (Charging)";
                    break;
                case 3:
                    str3 = " (Discharging)";
                    break;
                case 4:
                    str3 = " (Not charging)";
                    break;
                case 5:
                    str3 = " (Full)";
                    break;
            }
            sb.append("status: ");
            sb.append(H);
            sb.append(str3);
            sb.append("\n");
            sb.append("voltage: ");
            sb.append(E);
            sb.append("\n");
            sb.append("temperature: ");
            sb.append(I);
            sb.append("\n");
            sb.append("technology: ");
            sb.append(J);
            sb.append("\n");
            sb.append("battery low: ");
            sb.append(K);
            sb.append("\n");
            return sb;
        } catch (Throwable th) {
            g.a(th);
            return null;
        }
    }

    private static void Y() {
        if (b.c && L && a.c) {
            L = false;
            if (!f.b(P)) {
                f.a(0, P, SimpleExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
            }
        }
    }

    public static void b(Context context) {
        if (g.K()) {
            try {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
                intentFilter.addAction("android.intent.action.BATTERY_LOW");
                intentFilter.addAction("android.intent.action.BATTERY_OKAY");
                context.registerReceiver(Q, intentFilter, null, f.a(1));
                R = true;
            } catch (Throwable th) {
                g.a(th);
            }
        }
    }

    public static void c(boolean z2) {
        boolean z3 = true;
        if (!R ? !z2 || !g.K() : z2 && g.K()) {
            z3 = false;
        }
        if (z3) {
            if (f.b(S)) {
                f.a(S);
            }
            f.a(0, S, 3000L);
        }
    }

    public static void r() {
        T = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new e());
    }

    public static void s() {
        Thread.setDefaultUncaughtExceptionHandler(T);
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread thread, Throwable th) {
        a(thread, th, false);
    }

    private static boolean Z() {
        return b.d && JNIBridge.nativeIsCrashing();
    }

    public static boolean t() {
        return c.get() || Z();
    }

    /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
        jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 8, insn: 0x0621: MOVE  (r9 I:??[long, double]) = (r8 I:??[long, double]), block:B:416:0x0621
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
        	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
        	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:41)
        */
    public final void a(
    /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
        jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 8, insn: 0x0621: MOVE  (r9 I:??[long, double]) = (r8 I:??[long, double]), block:B:416:0x0621
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
        	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
        */
    /*  JADX ERROR: Method generation error
        jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r17v0 ??
        	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:233)
        	at jadx.core.codegen.MethodGen.addMethodArguments(MethodGen.java:209)
        	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:162)
        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:364)
        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:304)
        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:270)
        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(Unknown Source)
        	at java.base/java.util.ArrayList.forEach(Unknown Source)
        	at java.base/java.util.stream.SortedOps$RefSortingSink.end(Unknown Source)
        	at java.base/java.util.stream.Sink$ChainedReference.end(Unknown Source)
        */

    private static void a(Throwable th) {
        try {
            com.uc.crashsdk.a.a.d("DEBUG", a(th.getStackTrace(), (String) null).toString());
        } catch (Throwable unused) {
        }
    }

    public static Throwable u() {
        return U;
    }

    public static int v() {
        if (b.F() == 5) {
            return Z;
        }
        return 100;
    }

    public static void w() {
        long o2 = g.o();
        if (o2 >= 0) {
            boolean z2 = b.F() == 5;
            f.a(0, new com.uc.crashsdk.a.e(401));
            if (z2) {
                X = new com.uc.crashsdk.a.e(402);
                f.a(0, X, o2);
            }
        }
    }

    public static void x() {
        if (b.c && a.c && !f.b(aa)) {
            f.a(0, aa, 1000L);
        }
    }

    public static boolean y() {
        synchronized (Y) {
            if (X == null || W) {
                return false;
            }
            f.a(X);
            X = null;
            return true;
        }
    }

    public static boolean d(String str) {
        try {
            if (!g.b(str) || !str.startsWith("lib") || !str.endsWith(".so")) {
                return false;
            }
            System.loadLibrary(str.substring(3, str.length() - 3));
            return true;
        } catch (Throwable th) {
            g.a(th);
            return false;
        }
    }

    public static void b(int i2) {
        f.a(0, new com.uc.crashsdk.a.e(410), i2 * 1000);
    }

    private static void aa() {
        String W2 = g.W();
        File file = new File(W2);
        if (file.isDirectory()) {
            try {
                File[] listFiles = file.listFiles();
                if (listFiles != null && listFiles.length > 150) {
                    Arrays.sort(listFiles, new b((byte) 0));
                    int length = listFiles.length - AnimTask.MAX_PAGE_SIZE;
                    if (length < 0) {
                        length = 0;
                    }
                    long currentTimeMillis = System.currentTimeMillis();
                    int i2 = 0;
                    int i3 = 0;
                    while (i2 < listFiles.length) {
                        File file2 = listFiles[i2];
                        boolean z2 = i2 < length;
                        if (!z2 && currentTimeMillis - file2.lastModified() >= 432000000) {
                            z2 = true;
                        }
                        if (!z2) {
                            break;
                        }
                        file2.delete();
                        i3++;
                        if (0 >= 3) {
                            break;
                        }
                        i2++;
                    }
                    com.uc.crashsdk.a.a.a("Removed " + i3 + " logs in " + W2);
                }
            } catch (Throwable th) {
                g.a(th);
            }
        }
    }

    public static void z() {
        if (g.q()) {
            f.a(0, new com.uc.crashsdk.a.e(403), 10000L);
        }
    }

    public static void A() {
        if (!ac && !b.I()) {
            f.a(1, new com.uc.crashsdk.a.e(408), 1000L);
        }
    }

    private static void a(Calendar calendar) {
        if (g.Q()) {
            long timeInMillis = calendar.getTimeInMillis();
            calendar.add(5, 1);
            calendar.set(11, 0);
            calendar.set(12, 0);
            calendar.set(13, 0);
            long timeInMillis2 = calendar.getTimeInMillis();
            long j2 = timeInMillis2 - timeInMillis;
            long j3 = 3600000;
            if (j2 <= 3600000) {
                j3 = 1000 + j2;
            }
            f.a(0, new com.uc.crashsdk.a.e(415, new Object[]{Long.valueOf(timeInMillis2)}), j3);
        }
    }

    public static void B() {
        f.a(1, new com.uc.crashsdk.a.e(409), 7000L);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static int b(boolean z2, boolean z3) {
        int i2;
        if (z3 && !ac) {
            f.d(false);
        }
        if (z2) {
            boolean a2 = f.a(b.c(), false);
            h.i();
            i2 = a2;
        } else {
            int a3 = f.a();
            h.i();
            i2 = a3;
        }
        if (z3) {
            return f.a(z2);
        }
        return i2 == 1 ? 1 : 0;
    }

    public static int e(boolean z2) {
        int i2;
        if (z2) {
            i2 = f.a(b.c()) ? 1 : 0;
        } else {
            i2 = f.b();
        }
        int b2 = f.b(z2);
        return b2 > i2 ? b2 : i2;
    }

    public static StringBuilder a(StackTraceElement[] stackTraceElementArr, String str) {
        StringBuilder sb = new StringBuilder();
        int i2 = 0;
        if (stackTraceElementArr != null && stackTraceElementArr.length > 0) {
            boolean z2 = str == null;
            int i3 = 0;
            for (StackTraceElement stackTraceElement : stackTraceElementArr) {
                i3++;
                sb.append("  at ");
                sb.append(stackTraceElement.toString());
                sb.append("\n");
                if (!z2 && stackTraceElement.getMethodName().contains(str)) {
                    sb.delete(0, sb.length());
                    i3 = 0;
                    z2 = true;
                }
            }
            i2 = i3;
        }
        if (i2 == 0) {
            sb.append("  (no java stack)\n");
        }
        return sb;
    }

    public static StringBuilder e(String str) {
        return a(Thread.currentThread().getStackTrace(), str);
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x004b, code lost:
        com.uc.crashsdk.a.a.a("crashsdk", "SIG 3 is disabled by settings");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void C() {
        /*
            int r0 = com.uc.crashsdk.g.L()
            r1 = 4
            r2 = 3
            r3 = 1
            r4 = 0
            if (r0 == 0) goto L_0x0013
            if (r0 == r2) goto L_0x0013
            if (r0 != r1) goto L_0x000f
            goto L_0x0013
        L_0x000f:
            if (r0 != r3) goto L_0x0049
            r3 = r4
            goto L_0x0049
        L_0x0013:
            int r5 = android.os.Build.VERSION.SDK_INT
            r6 = 21
            if (r5 < r6) goto L_0x0049
            int r5 = android.os.Build.VERSION.SDK_INT
            r6 = 25
            if (r5 > r6) goto L_0x0049
            if (r0 != 0) goto L_0x0023
            r5 = r4
            goto L_0x0024
        L_0x0023:
            r5 = r3
        L_0x0024:
            r6 = 0
            if (r0 != r2) goto L_0x0037
            long r8 = java.lang.System.currentTimeMillis()
            r10 = 10
            long r8 = r8 % r10
            int r2 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
            if (r2 != 0) goto L_0x0035
            r2 = r3
            goto L_0x0038
        L_0x0035:
            r2 = r4
            goto L_0x0038
        L_0x0037:
            r2 = r5
        L_0x0038:
            if (r0 != r1) goto L_0x0048
            long r0 = java.lang.System.currentTimeMillis()
            r8 = 3
            long r0 = r0 % r8
            int r0 = (r0 > r6 ? 1 : (r0 == r6 ? 0 : -1))
            if (r0 != 0) goto L_0x0046
            goto L_0x0049
        L_0x0046:
            r3 = r4
            goto L_0x0049
        L_0x0048:
            r3 = r2
        L_0x0049:
            if (r3 != 0) goto L_0x0052
            java.lang.String r0 = "crashsdk"
            java.lang.String r1 = "SIG 3 is disabled by settings"
            com.uc.crashsdk.a.a.a(r0, r1)
        L_0x0052:
            boolean r0 = com.uc.crashsdk.b.I()
            android.os.Looper r1 = android.os.Looper.getMainLooper()
            android.os.Looper r2 = android.os.Looper.myLooper()
            if (r1 == r2) goto L_0x006e
            if (r3 == 0) goto L_0x006e
            r1 = 2
            com.uc.crashsdk.a.e r2 = new com.uc.crashsdk.a.e
            r3 = 413(0x19d, float:5.79E-43)
            r2.<init>(r3)
            com.uc.crashsdk.a.f.a(r1, r2)
            r3 = r4
        L_0x006e:
            r1 = 7
            long r4 = (long) r0
            r0 = 0
            com.uc.crashsdk.JNIBridge.nativeCmd(r1, r4, r0, r0)
            if (r3 == 0) goto L_0x007b
            r0 = 8
            com.uc.crashsdk.JNIBridge.cmd(r0)
        L_0x007b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.crashsdk.e.C():void");
    }

    public static ParcelFileDescriptor D() {
        if (!b.d) {
            com.uc.crashsdk.a.a.d("crashsdk", "Crash so is not loaded!");
            return null;
        }
        ParcelFileDescriptor parcelFileDescriptor = ad;
        if (parcelFileDescriptor != null) {
            return parcelFileDescriptor;
        }
        int cmd = (int) JNIBridge.cmd(14);
        if (cmd == -1) {
            return null;
        }
        ad = ParcelFileDescriptor.adoptFd(cmd);
        ae = true;
        return ad;
    }

    public static boolean a(ParcelFileDescriptor parcelFileDescriptor) {
        if (ae) {
            com.uc.crashsdk.a.a.d("crashsdk", "Can not call setHostFd and getHostFd in the same process!");
            return false;
        } else if (!b.d) {
            com.uc.crashsdk.a.a.d("crashsdk", "Crash so is not loaded!");
            return false;
        } else {
            if (ad != null) {
                com.uc.crashsdk.a.a.c("crashsdk", "Has already set host fd!");
            }
            ad = parcelFileDescriptor;
            int fd = parcelFileDescriptor.getFd();
            int nativeCmd = (int) JNIBridge.nativeCmd(13, fd, null, null);
            af = nativeCmd != -1;
            return fd == -1 || nativeCmd != -1;
        }
    }

    public static boolean E() {
        return af;
    }

    public static void F() {
        String V2 = g.V();
        File file = new File(V2);
        if (file.exists() && file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles == null) {
                com.uc.crashsdk.a.a.b("Ucebu can not list folder: " + V2);
                return;
            }
            for (File file2 : listFiles) {
                if (file2.isFile() && file2.getName().contains("ucebu")) {
                    a(false, false);
                    return;
                }
            }
        }
    }
}
