package com.uc.crashsdk;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.util.Log;
import com.uc.crashsdk.a.a;
import com.uc.crashsdk.a.e;
import com.uc.crashsdk.a.f;
import com.uc.crashsdk.a.g;
import com.uc.crashsdk.a.h;
import com.xiaomi.idm.api.IDMServer;
import java.io.File;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Locale;
import java.util.Map;
import java.util.WeakHashMap;

/* compiled from: ProGuard */
/* loaded from: classes2.dex */
public class b {
    static final /* synthetic */ boolean i = !b.class.desiredAssertionStatus();
    private static String j = null;
    private static String k = null;
    private static String l = null;
    private static String m = null;
    private static String n = null;
    private static String o = null;
    private static String p = null;
    private static String q = null;
    private static String r = null;
    private static String s = null;
    private static String t = null;
    private static String u = null;
    private static String v = null;
    public static boolean a = false;
    public static boolean b = false;
    public static boolean c = false;
    public static boolean d = false;
    public static final Object e = new Object();
    public static boolean f = false;
    public static boolean g = true;
    public static boolean h = false;
    private static boolean w = false;
    private static boolean x = false;
    private static volatile boolean y = false;
    private static boolean z = false;
    private static boolean A = false;
    private static boolean B = false;
    private static boolean C = false;
    private static boolean D = false;
    private static boolean E = false;
    private static final Object F = new Object();
    private static String G = null;
    private static int H = 0;
    private static boolean I = false;
    private static boolean J = false;
    private static boolean K = true;
    private static RandomAccessFile L = null;
    private static boolean M = false;
    private static final Object N = new Object();
    private static String O = null;
    private static boolean P = false;
    private static volatile Object[] Q = null;
    private static Runnable R = new e(101);
    private static boolean S = false;
    private static long T = 0;
    private static final Object U = new Object();
    private static long V = 0;
    private static boolean W = false;
    private static boolean X = false;
    private static boolean Y = false;
    private static long Z = 0;
    private static final WeakHashMap<Activity, Integer> aa = new WeakHashMap<>();
    private static boolean ab = false;
    private static String ac = null;
    private static boolean ad = false;
    private static boolean ae = false;
    private static boolean af = false;
    private static boolean ag = false;
    private static boolean ah = false;
    private static final Object ai = new Object();
    private static PendingIntent aj = null;

    public static String a() {
        int i2;
        String str = G;
        if (str != null) {
            return str;
        }
        String h2 = e.h();
        if (g.a(h2)) {
            G = "LLUN";
        } else {
            if (h2.length() > 48) {
                i2 = h2.length() - 48;
                h2 = h2.substring(0, 48);
            } else {
                i2 = 0;
            }
            StringBuilder sb = new StringBuilder();
            byte[] bytes = h2.getBytes();
            for (int length = bytes.length - 1; length >= 0; length--) {
                byte b2 = bytes[length];
                if (b2 == 46) {
                    sb.append('0');
                } else if (b2 == 58) {
                    sb.append('1');
                } else if (b2 >= 97 && b2 <= 122) {
                    sb.append((char) ((b2 + 65) - 97));
                } else if (b2 >= 65 && b2 <= 90) {
                    sb.append((char) b2);
                } else if (b2 < 48 || b2 > 57) {
                    sb.append('2');
                } else {
                    sb.append((char) b2);
                }
            }
            if (i2 > 0) {
                sb.append(String.valueOf(i2));
            }
            G = sb.toString();
        }
        return G;
    }

    private static String c(String str) {
        return g.U() + a() + "." + str;
    }

    private static String N() {
        if (j == null) {
            j = c("ss");
        }
        return j;
    }

    public static String b() {
        if (k == null) {
            k = c("ctj");
        }
        return k;
    }

    private static String O() {
        if (l == null) {
            l = c("ctn");
        }
        return l;
    }

    private static String P() {
        if (m == null) {
            m = c("cta");
        }
        return m;
    }

    public static String c() {
        if (n == null) {
            n = c("st");
        }
        return n;
    }

    public static String d() {
        if (u == null) {
            u = c("bati");
        }
        return u;
    }

    public static String e() {
        if (v == null) {
            v = c("hdr");
        }
        return v;
    }

    public static String f() {
        if (p == null) {
            p = g.U() + "up";
        }
        return p;
    }

    public static String g() {
        if (q == null) {
            q = g.U() + "authu";
        }
        return q;
    }

    public static String h() {
        if (r == null) {
            r = g.U() + "statu";
        }
        return r;
    }

    public static String i() {
        if (s == null) {
            s = g.U() + "poli";
        }
        return s;
    }

    public static String j() {
        if (t == null) {
            t = g.U() + "ver";
        }
        return t;
    }

    public static String k() {
        return g.U() + "bvu";
    }

    public static String l() {
        return g.U() + "fds";
    }

    public static String m() {
        return g.a(new File(N()), 8, false);
    }

    public static boolean n() {
        return x;
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0073 A[Catch: Throwable -> 0x0077, all -> 0x007f, TRY_LEAVE, TryCatch #1 {, blocks: (B:8:0x000d, B:10:0x0011, B:12:0x0013, B:14:0x0051, B:19:0x0059, B:21:0x005d, B:23:0x0061, B:25:0x0065, B:26:0x006d, B:28:0x0073, B:29:0x007b, B:30:0x007d), top: B:36:0x000d }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void Q() {
        /*
            boolean r0 = com.uc.crashsdk.b.y
            if (r0 != 0) goto L_0x0082
            boolean r0 = com.uc.crashsdk.b.x
            if (r0 == 0) goto L_0x000a
            goto L_0x0082
        L_0x000a:
            java.lang.Object r0 = com.uc.crashsdk.b.F
            monitor-enter(r0)
            boolean r1 = com.uc.crashsdk.b.y     // Catch: all -> 0x007f
            if (r1 == 0) goto L_0x0013
            monitor-exit(r0)     // Catch: all -> 0x007f
            return
        L_0x0013:
            java.lang.String r1 = com.uc.crashsdk.g.U()     // Catch: all -> 0x007f
            d(r1)     // Catch: all -> 0x007f
            java.lang.String r1 = m()     // Catch: all -> 0x007f
            java.io.File r2 = new java.io.File     // Catch: all -> 0x007f
            java.lang.String r3 = b()     // Catch: all -> 0x007f
            r2.<init>(r3)     // Catch: all -> 0x007f
            java.io.File r3 = new java.io.File     // Catch: all -> 0x007f
            java.lang.String r4 = O()     // Catch: all -> 0x007f
            r3.<init>(r4)     // Catch: all -> 0x007f
            java.lang.String r4 = "f"
            boolean r4 = r4.equals(r1)     // Catch: all -> 0x007f
            com.uc.crashsdk.b.z = r4     // Catch: all -> 0x007f
            java.lang.String r4 = "b"
            boolean r1 = r4.equals(r1)     // Catch: all -> 0x007f
            com.uc.crashsdk.b.A = r1     // Catch: all -> 0x007f
            boolean r1 = r2.exists()     // Catch: all -> 0x007f
            com.uc.crashsdk.b.C = r1     // Catch: all -> 0x007f
            boolean r1 = r3.exists()     // Catch: all -> 0x007f
            com.uc.crashsdk.b.D = r1     // Catch: all -> 0x007f
            boolean r1 = com.uc.crashsdk.b.C     // Catch: all -> 0x007f
            r2 = 1
            if (r1 != 0) goto L_0x0058
            boolean r1 = com.uc.crashsdk.b.D     // Catch: all -> 0x007f
            if (r1 == 0) goto L_0x0056
            goto L_0x0058
        L_0x0056:
            r1 = 0
            goto L_0x0059
        L_0x0058:
            r1 = r2
        L_0x0059:
            com.uc.crashsdk.b.B = r1     // Catch: all -> 0x007f
            if (r1 != 0) goto L_0x006d
            boolean r1 = com.uc.crashsdk.b.z     // Catch: all -> 0x007f
            if (r1 != 0) goto L_0x0065
            boolean r1 = com.uc.crashsdk.b.A     // Catch: all -> 0x007f
            if (r1 == 0) goto L_0x006d
        L_0x0065:
            boolean r1 = o()     // Catch: all -> 0x007f
            com.uc.crashsdk.b.E = r1     // Catch: all -> 0x007f
            com.uc.crashsdk.b.B = r1     // Catch: all -> 0x007f
        L_0x006d:
            boolean r1 = w()     // Catch: Throwable -> 0x0077, all -> 0x007f
            if (r1 == 0) goto L_0x007b
            W()     // Catch: Throwable -> 0x0077, all -> 0x007f
            goto L_0x007b
        L_0x0077:
            r1 = move-exception
            com.uc.crashsdk.a.g.a(r1)     // Catch: all -> 0x007f
        L_0x007b:
            com.uc.crashsdk.b.y = r2     // Catch: all -> 0x007f
            monitor-exit(r0)     // Catch: all -> 0x007f
            return
        L_0x007f:
            r1 = move-exception
            monitor-exit(r0)     // Catch: all -> 0x007f
            throw r1
        L_0x0082:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.crashsdk.b.Q():void");
    }

    public static boolean o() {
        if (!I) {
            if (d) {
                J = JNIBridge.cmd(15) == 1;
            } else {
                J = new File(P()).exists();
            }
            I = true;
        }
        return J;
    }

    public static void a(boolean z2) {
        K = z2;
        if (d) {
            JNIBridge.set(30, K);
        }
    }

    public static boolean p() {
        Q();
        return z;
    }

    public static boolean q() {
        Q();
        return A;
    }

    private static boolean R() {
        Q();
        return B;
    }

    private static boolean S() {
        Q();
        return C;
    }

    private static boolean T() {
        Q();
        return D;
    }

    private static boolean U() {
        Q();
        return E;
    }

    public static boolean r() {
        return w;
    }

    public static void s() {
        boolean z2;
        d(g.U());
        x = true;
        z = false;
        A = false;
        B = false;
        C = false;
        D = false;
        E = false;
        String[] strArr = {".st", ".wa", ".callback", ".ctn", ".ctj", ".cta", ".signal"};
        String[] strArr2 = {"up", "authu", "statu", "poli"};
        File[] listFiles = new File(g.U()).listFiles();
        if (listFiles != null) {
            for (File file : listFiles) {
                String name = file.getName();
                int i2 = 0;
                while (true) {
                    if (i2 >= 7) {
                        z2 = false;
                        break;
                    } else if (name.endsWith(strArr[i2])) {
                        z2 = true;
                        break;
                    } else {
                        i2++;
                    }
                }
                if (!z2) {
                    int i3 = 0;
                    while (true) {
                        if (i3 >= 4) {
                            break;
                        } else if (name.equals(strArr2[i3])) {
                            z2 = true;
                            break;
                        } else {
                            i3++;
                        }
                    }
                }
                if (z2) {
                    a.a("crashsdk", "delete file: " + file.getPath());
                    g.a(file);
                }
            }
        }
        W();
    }

    public static void t() {
        if (!w) {
            w = true;
            if (!I() && !e.t()) {
                d(g.U());
                V();
                W();
            }
        }
    }

    private static void V() {
        if (d) {
            JNIBridge.set(26, w);
        }
    }

    public static String a(String str) {
        return "debug.crs." + str;
    }

    private static void W() {
        if (!S) {
            S = true;
            try {
                new File(b()).delete();
            } catch (Throwable th) {
                g.a(th);
            }
            try {
                new File(O()).delete();
            } catch (Throwable th2) {
                g.a(th2);
            }
            try {
                if (d) {
                    JNIBridge.cmd(16);
                } else {
                    new File(P()).delete();
                }
            } catch (Throwable th3) {
                g.a(th3);
            }
        }
        Object[] Y2 = Y();
        if (Y2[0].equals(O) || Q != null) {
            P = true;
            X();
            return;
        }
        a(Y2);
    }

    private static void X() {
        if (!f.b(R)) {
            f.a(1, R);
            return;
        }
        Object[] objArr = Q;
        if (objArr == null || !Y()[0].equals(objArr[0])) {
            f.a(R);
            f.a(1, R);
        }
    }

    private static void a(Object[] objArr) {
        Q = objArr;
        synchronized (N) {
            String str = (String) objArr[0];
            long longValue = ((Long) objArr[1]).longValue();
            if (longValue < T) {
                a.c("crashsdk", String.format(Locale.US, "Update state generation %d, last is: %d", Long.valueOf(longValue), Long.valueOf(T)));
                return;
            }
            T = longValue;
            String N2 = N();
            if (d) {
                if (L != null) {
                    g.a(L);
                    L = null;
                }
                boolean nativeChangeState = JNIBridge.nativeChangeState(N2, str, M);
                M = false;
                if (!nativeChangeState) {
                    a.b("write state failed: " + str);
                }
            } else {
                if (L == null || M) {
                    if (L != null) {
                        g.a(L);
                        L = null;
                    }
                    try {
                        RandomAccessFile randomAccessFile = new RandomAccessFile(N2, "rw");
                        L = randomAccessFile;
                        randomAccessFile.seek(0L);
                        M = false;
                    } catch (Exception e2) {
                        g.a(e2);
                    }
                }
                try {
                    L.write(str.getBytes());
                    L.seek(0L);
                } catch (Exception e3) {
                    g.a(e3);
                }
            }
            O = str;
            Q = null;
        }
    }

    private static Object[] Y() {
        synchronized (U) {
            V++;
            if (w) {
                return new Object[]{"e", Long.valueOf(V)};
            } else if (y()) {
                return new Object[]{"f", Long.valueOf(V)};
            } else {
                return new Object[]{"b", Long.valueOf(V)};
            }
        }
    }

    public static boolean u() {
        return d(g.U());
    }

    public static boolean v() {
        return d(g.V());
    }

    private static boolean d(String str) {
        File file = new File(str);
        if (!file.exists()) {
            file.mkdirs();
        }
        if (file.isDirectory()) {
            return true;
        }
        a.a("crashsdk", "Crash log directory was placed by a file!", null);
        if (!file.delete()) {
            return false;
        }
        file.mkdirs();
        return true;
    }

    public static boolean w() {
        return W || !aa();
    }

    public static boolean x() {
        return X || !aa();
    }

    public static void b(boolean z2) {
        if (!e.t()) {
            if (z2 && w) {
                if (g.M()) {
                    Log.v("crashsdk", "setForeground, reset sExited to false!!!");
                }
                w = false;
                V();
            }
            boolean z3 = e.E() || I();
            long currentTimeMillis = System.currentTimeMillis();
            if (W && !X && z2) {
                long j2 = Z;
                if (j2 != 0 && !z3 && currentTimeMillis - j2 > 1800000) {
                    f.a(1, new e(104), 1000L);
                }
            }
            Z = currentTimeMillis;
            X = z2;
            if (z2) {
                W = true;
            }
            if (d) {
                JNIBridge.nativeSetForeground(z2);
            }
            if (!w && !z3) {
                Q();
                W();
                if (z2) {
                    a.a(false);
                    if (!Y) {
                        e.A();
                        Y = true;
                    }
                }
                if (!M) {
                    X();
                }
                e.c(z2);
            }
        }
    }

    public static boolean y() {
        return X && !w;
    }

    public static boolean a(Context context) {
        try {
            ((Application) context).registerActivityLifecycleCallbacks(new c());
            if (!g.J()) {
                return true;
            }
            z();
            return true;
        } catch (Throwable th) {
            g.a(th);
            return false;
        }
    }

    public static void z() {
        f.a(2, new e(100));
    }

    public static void A() {
        String str;
        if (d && (str = ac) != null) {
            JNIBridge.set(129, str);
        }
    }

    public static String B() {
        String str = ac;
        return str == null ? "" : str;
    }

    public static void a(int i2) {
        Object a2;
        Activity activity;
        int i3;
        boolean z2 = false;
        boolean z3 = true;
        switch (i2) {
            case 100:
                Object Z2 = Z();
                if (Z2 != null && (a2 = a(Z2, (Class<?>) null, "mActivities")) != null) {
                    try {
                        boolean z4 = false;
                        for (Map.Entry entry : ((Map) a2).entrySet()) {
                            Object value = entry.getValue();
                            if (!(value == null || (activity = (Activity) a(value, (Class<?>) null, IDMServer.PERSIST_TYPE_ACTIVITY)) == null)) {
                                boolean booleanValue = ((Boolean) a(value, (Class<?>) null, "paused")).booleanValue();
                                boolean booleanValue2 = ((Boolean) a(value, (Class<?>) null, "stopped")).booleanValue();
                                synchronized (aa) {
                                    if (booleanValue || booleanValue2) {
                                        i3 = 2;
                                    } else {
                                        i3 = 1;
                                        z4 = true;
                                    }
                                    aa.put(activity, Integer.valueOf(i3));
                                }
                            }
                            z2 = true;
                        }
                        if (z2) {
                            b(z4);
                            return;
                        }
                        return;
                    } catch (Throwable th) {
                        g.a(th);
                        return;
                    }
                } else {
                    return;
                }
            case 101:
                try {
                    if (new File(N()).exists()) {
                        z3 = false;
                    }
                    M = z3;
                    if (z3 || P) {
                        a(Y());
                        P = false;
                        return;
                    }
                    return;
                } catch (Throwable th2) {
                    g.a(th2);
                    return;
                }
            case 102:
                f.a(1, new e(103));
                return;
            case 103:
                try {
                    g.a(new File(P()));
                    return;
                } catch (Throwable th3) {
                    g.a(th3);
                    return;
                }
            case 104:
                h.d();
                f.a(102);
                if (C()) {
                    e.B();
                    return;
                }
                return;
            default:
                if (!i) {
                    throw new AssertionError();
                }
                return;
        }
    }

    private static Object Z() {
        Method declaredMethod;
        Object a2;
        Object a3 = a((Application) g.a(), Application.class, "mLoadedApk");
        if (a3 != null && (a2 = a(a3, (Class<?>) null, "mActivityThread")) != null) {
            return a2;
        }
        try {
            Class<?> cls = Class.forName("android.app.ActivityThread");
            if (!(cls == null || (declaredMethod = cls.getDeclaredMethod("currentActivityThread", new Class[0])) == null)) {
                declaredMethod.setAccessible(true);
                return declaredMethod.invoke(null, new Object[0]);
            }
        } catch (Throwable th) {
            g.a(th);
        }
        return null;
    }

    private static Object a(Object obj, Class<?> cls, String str) {
        if (obj == null) {
            return null;
        }
        if (cls == null) {
            cls = obj.getClass();
        }
        try {
            Field declaredField = cls.getDeclaredField(str);
            declaredField.setAccessible(true);
            return declaredField.get(obj);
        } catch (Throwable th) {
            g.a(th);
            return null;
        }
    }

    public static boolean C() {
        if (!ad) {
            if (!g.a(a.a) && a.a.equals(e.h())) {
                ae = true;
                if (d) {
                    JNIBridge.set(2, true);
                }
            }
            ad = true;
        }
        return ae;
    }

    public static void D() {
        af = true;
        if (d) {
            JNIBridge.set(34, true);
        }
    }

    public static boolean E() {
        return af;
    }

    public static int F() {
        boolean R2 = R();
        return q() ? R2 ? 3 : 6 : p() ? R2 ? 2 : 5 : R2 ? 4 : 1;
    }

    public static int G() {
        boolean S2 = S();
        boolean T2 = T();
        boolean U2 = U();
        if (q()) {
            if (S2) {
                return 12;
            }
            if (T2) {
                return 14;
            }
            return U2 ? 16 : 98;
        } else if (!p()) {
            return 1;
        } else {
            if (S2) {
                return 11;
            }
            if (T2) {
                return 13;
            }
            return U2 ? 15 : 97;
        }
    }

    public static void b(int i2) {
        H = i2;
        H();
    }

    public static void H() {
        if (d) {
            JNIBridge.nativeSet(27, H, "12", null);
            JNIBridge.set(30, K);
        }
    }

    public static boolean c(int i2) {
        return (i2 & H) != 0;
    }

    public static boolean a(Object obj, String str, e eVar) {
        Throwable th;
        Exception e2;
        synchronized (obj) {
            FileChannel fileChannel = null;
            r1 = null;
            fileChannel = null;
            FileLock lock = null;
            boolean z2 = false;
            if (d) {
                int nativeOpenFile = JNIBridge.nativeOpenFile(str);
                if (nativeOpenFile < 0) {
                    a.a("crashsdk", "Can not open file: " + str, null);
                    return false;
                }
                boolean nativeLockFile = JNIBridge.nativeLockFile(nativeOpenFile, true);
                try {
                    boolean a2 = eVar.a();
                    JNIBridge.nativeCloseFile(nativeOpenFile);
                    z2 = a2;
                } finally {
                    if (nativeLockFile) {
                        JNIBridge.nativeLockFile(nativeOpenFile, false);
                    }
                }
            } else {
                File file = new File(str);
                if (!file.exists()) {
                    try {
                        file.createNewFile();
                    } catch (Exception e3) {
                        g.a(e3);
                    }
                }
                try {
                    try {
                        fileChannel = new RandomAccessFile(file, "rw").getChannel();
                    } catch (Exception e4) {
                        try {
                            g.a(e4);
                            fileChannel = null;
                        } catch (Exception e5) {
                            e2 = e5;
                            g.a(e2);
                            g.a(fileChannel);
                            return z2;
                        }
                    }
                    if (fileChannel != null) {
                        try {
                            try {
                                lock = fileChannel.lock();
                            } catch (Exception e6) {
                                try {
                                    g.a(e6);
                                } catch (Exception e7) {
                                    e2 = e7;
                                    fileChannel = fileChannel;
                                    g.a(e2);
                                    g.a(fileChannel);
                                    return z2;
                                }
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            g.a(fileChannel);
                            throw th;
                        }
                    }
                    try {
                        z2 = eVar.a();
                        g.a(fileChannel);
                    } finally {
                        if (lock != null) {
                            try {
                                lock.release();
                            } catch (Exception e8) {
                                g.a(e8);
                            }
                        }
                    }
                } catch (Throwable th3) {
                    th = th3;
                    g.a(fileChannel);
                    throw th;
                }
            }
            return z2;
        }
    }

    private static boolean aa() {
        String a2 = g.a(new File("/proc/self/cgroup"), 512, false);
        if (g.a(a2)) {
            return false;
        }
        return a2.contains("/bg_non_interactive") || a2.contains("/background");
    }

    public static boolean I() {
        if (!ah) {
            synchronized (ai) {
                if (!ah) {
                    ag = ab();
                    ah = true;
                }
            }
        }
        return ag;
    }

    private static boolean ab() {
        try {
            Method declaredMethod = Process.class.getDeclaredMethod("isIsolated", new Class[0]);
            if (declaredMethod != null) {
                declaredMethod.setAccessible(true);
                Object invoke = declaredMethod.invoke(null, new Object[0]);
                if (invoke != null && (invoke instanceof Boolean)) {
                    return ((Boolean) invoke).booleanValue();
                }
            }
        } catch (Throwable th) {
            g.a(th);
        }
        int myUid = Process.myUid() % 100000;
        return myUid >= 99000 && myUid <= 99999;
    }

    public static void J() {
        if (!e.E() && !I() && aj == null && g.h() >= 0) {
            try {
                Context a2 = g.a();
                Intent launchIntentForPackage = a2.getPackageManager().getLaunchIntentForPackage(a2.getPackageName());
                launchIntentForPackage.addFlags(335544320);
                aj = PendingIntent.getActivity(a2, 0, launchIntentForPackage, 0);
            } catch (Throwable th) {
                g.a(th);
            }
        }
    }

    public static void b(Context context) {
        a.a("Restart APP");
        if (context != null) {
            if (o == null) {
                o = c("rt");
            }
            File file = new File(o);
            long j2 = -1;
            try {
                j2 = Long.parseLong(g.d(file));
            } catch (Throwable th) {
                g.a(th);
            }
            boolean z2 = false;
            long currentTimeMillis = System.currentTimeMillis() / 1000;
            if (g.h() >= 0 && (j2 <= 0 || currentTimeMillis - j2 > g.h())) {
                d(g.U());
                g.a(file, String.valueOf(currentTimeMillis));
                z2 = true;
            }
            a.a("lastTime: " + j2 + ", currentTime: " + currentTimeMillis + ", needRestart: " + z2);
            if (z2) {
                try {
                    d.a(true);
                } catch (Throwable th2) {
                    g.a(th2);
                }
                K();
            }
        }
    }

    public static boolean K() {
        if (aj == null) {
            a.b("Restart intent is null!");
            return false;
        }
        try {
            a.a("crashsdk", "restarting ...");
            ((AlarmManager) g.a().getSystemService("alarm")).set(1, System.currentTimeMillis() + 200, aj);
            return true;
        } catch (Throwable th) {
            g.a(th);
            return false;
        }
    }
}
