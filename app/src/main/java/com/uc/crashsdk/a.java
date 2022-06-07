package com.uc.crashsdk;

import android.content.pm.PackageInfo;
import android.util.SparseArray;
import com.uc.crashsdk.a.b;
import com.uc.crashsdk.a.e;
import com.uc.crashsdk.a.f;
import com.uc.crashsdk.a.g;
import com.uc.crashsdk.export.LogType;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;

/* compiled from: ProGuard */
/* loaded from: classes2.dex */
public class a {
    static final /* synthetic */ boolean d = !a.class.desiredAssertionStatus();
    public static String a = "";
    public static String b = "";
    private static final Map<String, String> e = new HashMap();
    private static final List<String> f = new ArrayList();
    private static String g = "";
    private static String h = null;
    private static int i = -1;
    private static long j = 0;
    private static final HashMap<String, Object[]> k = new HashMap<>();
    private static final List<String> l = new ArrayList();
    private static int m = 0;
    private static int n = 0;
    private static int o = 0;
    private static int p = 0;
    private static final HashMap<String, Object[]> q = new HashMap<>();
    private static final List<String> r = new ArrayList();
    private static int s = 0;
    private static int t = 0;
    private static int u = 0;
    private static int v = 0;
    private static int w = 0;
    private static int x = 0;
    private static final SparseArray<Object[]> y = new SparseArray<>();
    private static final List<Integer> z = new ArrayList();
    private static final HashMap<String, Object[]> A = new HashMap<>();
    private static final List<String> B = new ArrayList();
    private static int C = 0;
    private static int D = 0;
    private static int E = 0;
    static boolean c = false;
    private static Runnable F = new e(201);
    private static boolean G = false;
    private static boolean H = false;
    private static boolean I = false;

    private static boolean o() {
        try {
            PackageInfo packageInfo = g.a().getPackageManager().getPackageInfo(a, 0);
            h = packageInfo.versionName;
            j = packageInfo.lastUpdateTime;
            i = packageInfo.versionCode;
            return true;
        } catch (Throwable th) {
            g.b(th);
            return false;
        }
    }

    public static String a() {
        String str = h;
        return str != null ? str : o() ? h : "";
    }

    public static long b() {
        return j;
    }

    public static int c() {
        if (i == -1) {
            o();
        }
        return i;
    }

    public static void a(String str, String str2) {
        synchronized (e) {
            if (!e.containsKey(str)) {
                f.add(str);
            }
            e.put(str, str2);
            if (b.d) {
                JNIBridge.nativeAddHeaderInfo(str, str2);
            }
            e.x();
        }
    }

    public static void d() {
        StringBuilder sb = new StringBuilder();
        synchronized (e) {
            for (String str : f) {
                String str2 = e.get(str);
                sb.append(str);
                sb.append(": ");
                if (str2 != null) {
                    sb.append(str2);
                }
                sb.append("\n");
            }
        }
        sb.append(String.format(Locale.US, "(saved at %s)\n", e.m()));
        b.a(b.e(), sb.toString());
    }

    public static void e() {
        if (d || b.d) {
            synchronized (e) {
                for (String str : f) {
                    JNIBridge.nativeAddHeaderInfo(str, e.get(str));
                }
            }
            return;
        }
        throw new AssertionError();
    }

    public static void a(OutputStream outputStream, String str) {
        synchronized (e) {
            for (String str2 : f) {
                StringBuilder sb = new StringBuilder(11);
                sb.append(str2);
                sb.append(": ");
                String str3 = e.get(str2);
                if (str3 != null) {
                    sb.append(str3);
                }
                sb.append("\n");
                outputStream.write(sb.toString().getBytes(str));
            }
        }
    }

    public static byte[] f() {
        return new byte[]{24, 99, 121, 60};
    }

    public static int a(String str, String str2, boolean z2, boolean z3, int i2, boolean z4) {
        int i3;
        int i4;
        boolean z5;
        if (str == null || str2 == null) {
            return 0;
        }
        if (str.length() > 256) {
            com.uc.crashsdk.a.a.a("crashsdk", "addDumpFile: description is too long!", null);
            return 0;
        }
        synchronized (k) {
            if (k.containsKey(str)) {
                i4 = ((Integer) k.get(str)[0]).intValue();
                i3 = LogType.addType(i4, i2);
            } else {
                i3 = i2;
                i4 = 0;
            }
            if (LogType.isForJava(i3) && !LogType.isForJava(i4)) {
                if (m >= 10) {
                    i3 = LogType.removeType(i3, 16);
                } else {
                    m++;
                }
            }
            if (LogType.isForNative(i3) && !LogType.isForNative(i4)) {
                if (n >= 10) {
                    i3 = LogType.removeType(i3, 1);
                } else {
                    n++;
                }
            }
            if (LogType.isForUnexp(i3) && !LogType.isForUnexp(i4)) {
                if (o >= 10) {
                    i3 = LogType.removeType(i3, 256);
                } else {
                    o++;
                }
            }
            if (LogType.isForANR(i3) && !LogType.isForANR(i4)) {
                if (p >= 10) {
                    i3 = LogType.removeType(i3, 1048576);
                } else {
                    p++;
                }
            }
            if ((1048849 & i3) == 0) {
                z5 = false;
            } else {
                if (i4 == 0) {
                    l.add(str);
                }
                z5 = true;
            }
            if (!z5) {
                return i3;
            }
            if (b.d && (1048833 & i2) != 0) {
                int nativeAddDumpFile = JNIBridge.nativeAddDumpFile(str, str2, z2, z3, i2, z4);
                if (!LogType.isForNative(nativeAddDumpFile)) {
                    i3 = LogType.removeType(i3, 1);
                }
                if (!LogType.isForUnexp(nativeAddDumpFile)) {
                    i3 = LogType.removeType(i3, 256);
                }
                if (!LogType.isForANR(nativeAddDumpFile)) {
                    i3 = LogType.removeType(i3, 1048576);
                }
            }
            k.put(str, new Object[]{Integer.valueOf(i3), str2, Boolean.valueOf(z2), Boolean.valueOf(z3), Boolean.valueOf(z4)});
            return i3;
        }
    }

    public static void g() {
        if (d || b.d) {
            synchronized (k) {
                for (String str : l) {
                    Object[] objArr = k.get(str);
                    int intValue = ((Integer) objArr[0]).intValue();
                    if ((1048833 & intValue) != 0) {
                        JNIBridge.nativeAddDumpFile(str, (String) objArr[1], ((Boolean) objArr[2]).booleanValue(), ((Boolean) objArr[3]).booleanValue(), intValue, ((Boolean) objArr[4]).booleanValue());
                    }
                }
            }
            return;
        }
        throw new AssertionError();
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0050 A[Catch: Throwable -> 0x00dc, all -> 0x0108, TRY_LEAVE, TryCatch #1 {Throwable -> 0x00dc, blocks: (B:12:0x0025, B:14:0x002f, B:17:0x003e, B:20:0x0045, B:22:0x0050), top: B:67:0x0025 }] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0075  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0078  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00b1 A[Catch: Throwable -> 0x00d8, all -> 0x0108, TryCatch #2 {, blocks: (B:8:0x0012, B:9:0x0019, B:11:0x001f, B:12:0x0025, B:14:0x002f, B:17:0x003e, B:20:0x0045, B:22:0x0050, B:24:0x0063, B:32:0x007e, B:34:0x009a, B:36:0x00a2, B:38:0x00b1, B:39:0x00ba, B:44:0x00c8, B:46:0x00d3, B:51:0x00df, B:54:0x00e8, B:55:0x00ec, B:57:0x00f2, B:59:0x0100, B:60:0x0106), top: B:69:0x0012 }] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00ba A[Catch: Throwable -> 0x00d8, all -> 0x0108, TRY_LEAVE, TryCatch #2 {, blocks: (B:8:0x0012, B:9:0x0019, B:11:0x001f, B:12:0x0025, B:14:0x002f, B:17:0x003e, B:20:0x0045, B:22:0x0050, B:24:0x0063, B:32:0x007e, B:34:0x009a, B:36:0x00a2, B:38:0x00b1, B:39:0x00ba, B:44:0x00c8, B:46:0x00d3, B:51:0x00df, B:54:0x00e8, B:55:0x00ec, B:57:0x00f2, B:59:0x0100, B:60:0x0106), top: B:69:0x0012 }] */
    /* JADX WARN: Removed duplicated region for block: B:83:0x00d3 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0019 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void a(java.io.OutputStream r18, java.lang.String r19, java.util.ArrayList<java.lang.String> r20) {
        /*
            Method dump skipped, instructions count: 267
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.crashsdk.a.a(java.io.OutputStream, java.lang.String, java.util.ArrayList):void");
    }

    public static String h() {
        StringBuilder sb = new StringBuilder();
        synchronized (k) {
            boolean z2 = true;
            for (String str : l) {
                if (LogType.isForJava(((Integer) k.get(str)[0]).intValue())) {
                    if (!z2) {
                        sb.append("`");
                    }
                    sb.append(str);
                    z2 = false;
                }
            }
        }
        return sb.toString();
    }

    public static String a(String str) {
        synchronized (k) {
            Object[] objArr = k.get(str);
            if (objArr == null) {
                return null;
            }
            int i2 = 1;
            boolean booleanValue = ((Boolean) objArr[2]).booleanValue();
            boolean booleanValue2 = ((Boolean) objArr[3]).booleanValue();
            Locale locale = Locale.US;
            Object[] objArr2 = new Object[4];
            objArr2[0] = (String) objArr[1];
            objArr2[1] = "`";
            objArr2[2] = Integer.valueOf(booleanValue ? 1 : 0);
            if (!booleanValue2) {
                i2 = 0;
            }
            objArr2[3] = Integer.valueOf(i2);
            return String.format(locale, "%s%s%d%d", objArr2);
        }
    }

    private static boolean a(List<String> list, String str) {
        if (g.a(str)) {
            return false;
        }
        for (String str2 : list) {
            if (str.equals(str2)) {
                return true;
            }
        }
        return false;
    }

    public static int a(String str, int i2, Callable<String> callable, long j2, int i3) {
        int i4;
        int i5;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        boolean z7;
        boolean z8;
        if (str == null) {
            return 0;
        }
        synchronized (q) {
            if (q.containsKey(str)) {
                i5 = ((Integer) q.get(str)[0]).intValue();
                i4 = LogType.addType(i5, i2);
                if (i5 == i4) {
                    return i5;
                }
            } else {
                i4 = i2;
                i5 = 0;
            }
            if (LogType.isForJava(i4) && !LogType.isForJava(i5)) {
                if (s >= 8) {
                    z8 = true;
                } else if (j2 == 0) {
                    if (s - w >= 6) {
                        z8 = true;
                    }
                    s++;
                    z8 = false;
                } else if (w >= 6) {
                    z8 = true;
                } else {
                    w++;
                    s++;
                    z8 = false;
                }
                if (z8) {
                    i4 = LogType.removeType(i4, 16);
                }
            }
            if (!LogType.isForNative(i4) || LogType.isForNative(i5)) {
                z3 = false;
                z2 = false;
            } else {
                if (t >= 6) {
                    z3 = false;
                    z2 = false;
                    z7 = true;
                } else if (j2 != 0) {
                    if (x >= 4) {
                        z3 = false;
                        z2 = false;
                        z7 = true;
                    } else {
                        x++;
                        t++;
                        z7 = false;
                        z3 = true;
                        z2 = true;
                    }
                } else if (t - x >= 4) {
                    z3 = false;
                    z2 = false;
                    z7 = true;
                } else {
                    t++;
                    z7 = false;
                    z2 = false;
                    z3 = true;
                }
                if (z7) {
                    i4 = LogType.removeType(i4, 1);
                }
            }
            if (!LogType.isForANR(i4) || LogType.isForANR(i5)) {
                z4 = false;
            } else if (v >= 6) {
                i4 = LogType.removeType(i4, 1048576);
                z4 = false;
            } else {
                v++;
                z4 = true;
            }
            if (!LogType.isForUnexp(i4) || LogType.isForUnexp(i5)) {
                z5 = false;
            } else if (u >= 6) {
                i4 = LogType.removeType(i4, 256);
                z5 = false;
            } else {
                u++;
                z5 = true;
            }
            if ((1048849 & i4) == 0) {
                z6 = false;
            } else {
                if (i5 == 0) {
                    r.add(str);
                }
                z6 = true;
            }
            if (!z6) {
                return i4;
            }
            if (b.d && (1048833 & i2) != 0) {
                int nativeAddCallbackInfo = JNIBridge.nativeAddCallbackInfo(str, i2, j2, i3);
                if (!LogType.isForNative(nativeAddCallbackInfo)) {
                    i4 = LogType.removeType(i4, 1);
                    if (z3) {
                        t--;
                    }
                    if (z2) {
                        x--;
                    }
                }
                if (!LogType.isForANR(nativeAddCallbackInfo)) {
                    i4 = LogType.removeType(i4, 1048576);
                    if (z4) {
                        v--;
                    }
                }
                if (!LogType.isForUnexp(nativeAddCallbackInfo)) {
                    i4 = LogType.removeType(i4, 256);
                    if (z5) {
                        u--;
                    }
                }
            }
            q.put(str, new Object[]{Integer.valueOf(i4), callable, Long.valueOf(j2), Integer.valueOf(i3)});
            return i4;
        }
    }

    public static void i() {
        if (d || b.d) {
            synchronized (q) {
                for (String str : r) {
                    Object[] objArr = q.get(str);
                    int intValue = ((Integer) objArr[0]).intValue();
                    if ((1048833 & intValue) != 0) {
                        JNIBridge.nativeAddCallbackInfo(str, intValue, ((Long) objArr[2]).longValue(), ((Integer) objArr[3]).intValue());
                    }
                }
            }
            return;
        }
        throw new AssertionError();
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x005d A[Catch: Throwable -> 0x008d, all -> 0x00d1, TryCatch #2 {, blocks: (B:4:0x0003, B:5:0x0009, B:7:0x000f, B:8:0x0015, B:10:0x0028, B:13:0x002f, B:16:0x0036, B:18:0x005d, B:19:0x006b, B:21:0x0075, B:23:0x007b, B:24:0x0083, B:25:0x0091, B:27:0x00ab, B:29:0x00b1, B:30:0x00b5, B:32:0x00bb, B:34:0x00c9, B:35:0x00cf), top: B:40:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x006b A[Catch: Throwable -> 0x008d, all -> 0x00d1, TryCatch #2 {, blocks: (B:4:0x0003, B:5:0x0009, B:7:0x000f, B:8:0x0015, B:10:0x0028, B:13:0x002f, B:16:0x0036, B:18:0x005d, B:19:0x006b, B:21:0x0075, B:23:0x007b, B:24:0x0083, B:25:0x0091, B:27:0x00ab, B:29:0x00b1, B:30:0x00b5, B:32:0x00bb, B:34:0x00c9, B:35:0x00cf), top: B:40:0x0003 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void a(java.io.OutputStream r9, java.lang.String r10, java.lang.String r11, java.util.ArrayList<java.lang.String> r12) {
        /*
            java.util.HashMap<java.lang.String, java.lang.Object[]> r0 = com.uc.crashsdk.a.q
            monitor-enter(r0)
            java.util.List<java.lang.String> r1 = com.uc.crashsdk.a.r     // Catch: all -> 0x00d1
            java.util.Iterator r1 = r1.iterator()     // Catch: all -> 0x00d1
        L_0x0009:
            boolean r2 = r1.hasNext()     // Catch: all -> 0x00d1
            if (r2 == 0) goto L_0x00a9
            java.lang.Object r2 = r1.next()     // Catch: all -> 0x00d1
            java.lang.String r2 = (java.lang.String) r2     // Catch: all -> 0x00d1
            java.util.HashMap<java.lang.String, java.lang.Object[]> r3 = com.uc.crashsdk.a.q     // Catch: Throwable -> 0x008d, all -> 0x00d1
            java.lang.Object r3 = r3.get(r2)     // Catch: Throwable -> 0x008d, all -> 0x00d1
            java.lang.Object[] r3 = (java.lang.Object[]) r3     // Catch: Throwable -> 0x008d, all -> 0x00d1
            r4 = 0
            r5 = r3[r4]     // Catch: Throwable -> 0x008d, all -> 0x00d1
            java.lang.Integer r5 = (java.lang.Integer) r5     // Catch: Throwable -> 0x008d, all -> 0x00d1
            int r5 = r5.intValue()     // Catch: Throwable -> 0x008d, all -> 0x00d1
            if (r12 != 0) goto L_0x002f
            boolean r5 = com.uc.crashsdk.export.LogType.isForJava(r5)     // Catch: Throwable -> 0x008d, all -> 0x00d1
            if (r5 != 0) goto L_0x0036
            goto L_0x0009
        L_0x002f:
            boolean r5 = a(r12, r2)     // Catch: Throwable -> 0x008d, all -> 0x00d1
            if (r5 != 0) goto L_0x0036
            goto L_0x0009
        L_0x0036:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: Throwable -> 0x008d, all -> 0x00d1
            r5.<init>()     // Catch: Throwable -> 0x008d, all -> 0x00d1
            r5.append(r2)     // Catch: Throwable -> 0x008d, all -> 0x00d1
            java.lang.String r6 = "\n"
            r5.append(r6)     // Catch: Throwable -> 0x008d, all -> 0x00d1
            java.lang.String r5 = r5.toString()     // Catch: Throwable -> 0x008d, all -> 0x00d1
            byte[] r5 = r5.getBytes(r10)     // Catch: Throwable -> 0x008d, all -> 0x00d1
            r9.write(r5)     // Catch: Throwable -> 0x008d, all -> 0x00d1
            r5 = 2
            r5 = r3[r5]     // Catch: Throwable -> 0x008d, all -> 0x00d1
            java.lang.Long r5 = (java.lang.Long) r5     // Catch: Throwable -> 0x008d, all -> 0x00d1
            long r5 = r5.longValue()     // Catch: Throwable -> 0x008d, all -> 0x00d1
            r7 = 0
            int r7 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r7 == 0) goto L_0x006b
            r7 = 3
            r3 = r3[r7]     // Catch: Throwable -> 0x008d, all -> 0x00d1
            java.lang.Integer r3 = (java.lang.Integer) r3     // Catch: Throwable -> 0x008d, all -> 0x00d1
            int r3 = r3.intValue()     // Catch: Throwable -> 0x008d, all -> 0x00d1
            java.lang.String r2 = com.uc.crashsdk.JNIBridge.nativeGetCallbackInfo(r2, r5, r3, r4)     // Catch: Throwable -> 0x008d, all -> 0x00d1
            goto L_0x0073
        L_0x006b:
            java.lang.StringBuilder r2 = b(r2, r4)     // Catch: Throwable -> 0x008d, all -> 0x00d1
            java.lang.String r2 = r2.toString()     // Catch: Throwable -> 0x008d, all -> 0x00d1
        L_0x0073:
            if (r2 == 0) goto L_0x0083
            int r3 = r2.length()     // Catch: Throwable -> 0x008d, all -> 0x00d1
            if (r3 <= 0) goto L_0x0083
            byte[] r2 = r2.getBytes(r10)     // Catch: Throwable -> 0x008d, all -> 0x00d1
            r9.write(r2)     // Catch: Throwable -> 0x008d, all -> 0x00d1
            goto L_0x0091
        L_0x0083:
            java.lang.String r2 = "(data is null)\n"
            byte[] r2 = r2.getBytes(r10)     // Catch: Throwable -> 0x008d, all -> 0x00d1
            r9.write(r2)     // Catch: Throwable -> 0x008d, all -> 0x00d1
            goto L_0x0091
        L_0x008d:
            r2 = move-exception
            com.uc.crashsdk.e.a(r2, r9)     // Catch: all -> 0x00d1
        L_0x0091:
            java.lang.String r2 = "\n"
            byte[] r2 = r2.getBytes(r10)     // Catch: Throwable -> 0x00a3, all -> 0x00d1
            r9.write(r2)     // Catch: Throwable -> 0x00a3, all -> 0x00d1
            byte[] r2 = r11.getBytes(r10)     // Catch: Throwable -> 0x00a3, all -> 0x00d1
            r9.write(r2)     // Catch: Throwable -> 0x00a3, all -> 0x00d1
            goto L_0x0009
        L_0x00a3:
            r2 = move-exception
            com.uc.crashsdk.e.a(r2, r9)     // Catch: all -> 0x00d1
            goto L_0x0009
        L_0x00a9:
            if (r12 == 0) goto L_0x00cf
            boolean r10 = com.uc.crashsdk.e.E()     // Catch: all -> 0x00d1
            if (r10 == 0) goto L_0x00cf
            java.util.Iterator r10 = r12.iterator()     // Catch: all -> 0x00d1
        L_0x00b5:
            boolean r11 = r10.hasNext()     // Catch: all -> 0x00d1
            if (r11 == 0) goto L_0x00cf
            java.lang.Object r11 = r10.next()     // Catch: all -> 0x00d1
            java.lang.String r11 = (java.lang.String) r11     // Catch: all -> 0x00d1
            java.util.List<java.lang.String> r12 = com.uc.crashsdk.a.r     // Catch: all -> 0x00d1
            boolean r12 = a(r12, r11)     // Catch: all -> 0x00d1
            if (r12 != 0) goto L_0x00b5
            java.lang.String r12 = "CUSTOMCALLBACKINFO"
            com.uc.crashsdk.e.a(r9, r12, r11)     // Catch: all -> 0x00d1
            goto L_0x00b5
        L_0x00cf:
            monitor-exit(r0)     // Catch: all -> 0x00d1
            return
        L_0x00d1:
            r9 = move-exception
            monitor-exit(r0)     // Catch: all -> 0x00d1
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.crashsdk.a.a(java.io.OutputStream, java.lang.String, java.lang.String, java.util.ArrayList):void");
    }

    public static String a(String str, boolean z2) {
        String str2;
        synchronized (q) {
            Object[] objArr = q.get(str);
            long longValue = ((Long) objArr[2]).longValue();
            if (longValue != 0) {
                str2 = JNIBridge.nativeGetCallbackInfo(str, longValue, ((Integer) objArr[3]).intValue(), z2);
            } else {
                str2 = b(str, z2).toString();
            }
        }
        return str2;
    }

    private static StringBuilder b(String str, boolean z2) {
        String str2;
        StringBuilder sb = new StringBuilder();
        try {
            Object[] objArr = q.get(str);
            if (objArr == null) {
                str2 = "Unknown callback: " + str;
            } else {
                Callable callable = (Callable) objArr[1];
                if (callable != null) {
                    str2 = (String) callable.call();
                } else {
                    str2 = d.a(str, z2);
                }
            }
            if (str2 != null) {
                sb.append(str2);
            }
        } catch (Throwable th) {
            g.a(th);
        }
        try {
            if (sb.length() == 0) {
                sb.append("(data is null)\n");
            }
        } catch (Throwable th2) {
            g.a(th2);
        }
        return sb;
    }

    public static String j() {
        String sb;
        synchronized (q) {
            StringBuilder sb2 = new StringBuilder();
            synchronized (r) {
                boolean z2 = true;
                for (String str : r) {
                    if (LogType.isForJava(((Integer) q.get(str)[0]).intValue())) {
                        if (!z2) {
                            sb2.append("`");
                        }
                        sb2.append(str);
                        z2 = false;
                    }
                }
            }
            sb = sb2.toString();
        }
        return sb;
    }

    private static boolean a(String str, Thread thread) {
        if (thread == null) {
            return false;
        }
        synchronized (y) {
            int id = (int) thread.getId();
            if (y.get(id) == null) {
                z.add(Integer.valueOf(id));
            }
            y.put(id, new Object[]{new WeakReference(thread), str});
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0087  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00cc A[Catch: Throwable -> 0x0102, all -> 0x0120, TryCatch #7 {, blocks: (B:4:0x0003, B:5:0x000d, B:7:0x0013, B:9:0x0020, B:12:0x002b, B:13:0x0035, B:15:0x003b, B:22:0x0068, B:23:0x006b, B:27:0x0089, B:28:0x00c5, B:30:0x00cc, B:31:0x00d5, B:33:0x00da, B:35:0x00de, B:36:0x00e7, B:38:0x0106, B:39:0x011e), top: B:46:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00da A[Catch: Throwable -> 0x0102, all -> 0x0120, TryCatch #7 {, blocks: (B:4:0x0003, B:5:0x000d, B:7:0x0013, B:9:0x0020, B:12:0x002b, B:13:0x0035, B:15:0x003b, B:22:0x0068, B:23:0x006b, B:27:0x0089, B:28:0x00c5, B:30:0x00cc, B:31:0x00d5, B:33:0x00da, B:35:0x00de, B:36:0x00e7, B:38:0x0106, B:39:0x011e), top: B:46:0x0003 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void a(java.io.OutputStream r13, java.lang.String r14, java.lang.String r15) {
        /*
            Method dump skipped, instructions count: 291
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.crashsdk.a.a(java.io.OutputStream, java.lang.String, java.lang.String):void");
    }

    public static int a(String str, int i2, int i3) {
        int i4;
        int i5;
        boolean z2;
        if (str == null || i2 <= 0) {
            return 0;
        }
        if (i2 > 1500) {
            com.uc.crashsdk.a.a.a("crashsdk", "createCachedInfo: capacity is too large!", null);
            return 0;
        }
        synchronized (A) {
            if (A.containsKey(str)) {
                i5 = ((Integer) A.get(str)[1]).intValue();
                i4 = LogType.addType(i5, i3);
            } else {
                i4 = i3;
                i5 = 0;
            }
            if (LogType.isForJava(i4) && !LogType.isForJava(i5)) {
                if (C >= 8) {
                    i4 = LogType.removeType(i4, 16);
                } else {
                    C++;
                }
            }
            if (LogType.isForNative(i4) && !LogType.isForNative(i5)) {
                if (D >= 8) {
                    i4 = LogType.removeType(i4, 1);
                } else {
                    D++;
                }
            }
            if (LogType.isForANR(i4) && !LogType.isForANR(i5)) {
                if (E >= 8) {
                    i4 = LogType.removeType(i4, 1048576);
                } else {
                    E++;
                }
            }
            if ((1048849 & i4) == 0) {
                z2 = false;
            } else {
                if (i5 == 0) {
                    B.add(str);
                }
                z2 = true;
            }
            if (!z2) {
                return i4;
            }
            if (b.d && (i3 & 1048577) != 0) {
                int nativeCreateCachedInfo = JNIBridge.nativeCreateCachedInfo(str, i2, i4);
                if (!LogType.isForNative(nativeCreateCachedInfo)) {
                    i4 = LogType.removeType(i4, 1);
                }
                if (!LogType.isForANR(nativeCreateCachedInfo)) {
                    i4 = LogType.removeType(i4, 1048576);
                }
            }
            A.put(str, new Object[]{Integer.valueOf(i2), Integer.valueOf(i4), new ArrayList()});
            return i4;
        }
    }

    public static int b(String str, String str2) {
        int i2;
        int i3 = 0;
        if (str == null || str2 == null) {
            return 0;
        }
        if (str2.length() > 2048) {
            str2 = str2.substring(0, 2048);
        }
        synchronized (A) {
            Object[] objArr = A.get(str);
            if (objArr != null) {
                int intValue = ((Integer) objArr[0]).intValue();
                i2 = ((Integer) objArr[1]).intValue();
                List list = (List) objArr[2];
                if (list.size() >= intValue) {
                    list.remove(0);
                }
                list.add(str2);
                if (LogType.isForJava(i2)) {
                    i3 = LogType.addType(0, 16);
                }
                if (!b.d) {
                    if (LogType.isForNative(i2)) {
                        i3 = LogType.addType(i3, 1);
                    }
                    if (LogType.isForANR(i2)) {
                        i3 = LogType.addType(i3, 1048576);
                    }
                }
            } else {
                i2 = 0;
            }
            if (b.d && JNIBridge.nativeAddCachedInfo(str, str2)) {
                if (LogType.isForNative(i2)) {
                    i3 = LogType.addType(i3, 1);
                }
                if (LogType.isForANR(i2)) {
                    i3 = LogType.addType(i3, 1048576);
                }
            }
        }
        return i3;
    }

    public static void k() {
        if (d || b.d) {
            synchronized (A) {
                for (String str : B) {
                    Object[] objArr = A.get(str);
                    int intValue = ((Integer) objArr[0]).intValue();
                    int intValue2 = ((Integer) objArr[1]).intValue();
                    List list = (List) objArr[2];
                    if (!((1048577 & intValue2) == 0 || JNIBridge.nativeCreateCachedInfo(str, intValue, intValue2) == 0)) {
                        Iterator it = list.iterator();
                        while (it.hasNext() && JNIBridge.nativeAddCachedInfo(str, (String) it.next())) {
                        }
                    }
                }
            }
            return;
        }
        throw new AssertionError();
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0076 A[Catch: Throwable -> 0x008d, all -> 0x00d1, LOOP:1: B:16:0x0070->B:18:0x0076, LOOP_END, TRY_LEAVE, TryCatch #2 {, blocks: (B:4:0x0003, B:5:0x0009, B:7:0x000f, B:9:0x0036, B:12:0x003d, B:14:0x0043, B:15:0x006c, B:16:0x0070, B:18:0x0076, B:19:0x0091, B:21:0x00ab, B:23:0x00b1, B:24:0x00b5, B:26:0x00bb, B:28:0x00c9, B:29:0x00cf), top: B:34:0x0003 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void b(java.io.OutputStream r11, java.lang.String r12, java.lang.String r13, java.util.ArrayList<java.lang.String> r14) {
        /*
            java.util.HashMap<java.lang.String, java.lang.Object[]> r0 = com.uc.crashsdk.a.A
            monitor-enter(r0)
            java.util.List<java.lang.String> r1 = com.uc.crashsdk.a.B     // Catch: all -> 0x00d1
            java.util.Iterator r1 = r1.iterator()     // Catch: all -> 0x00d1
        L_0x0009:
            boolean r2 = r1.hasNext()     // Catch: all -> 0x00d1
            if (r2 == 0) goto L_0x00a9
            java.lang.Object r2 = r1.next()     // Catch: all -> 0x00d1
            java.lang.String r2 = (java.lang.String) r2     // Catch: all -> 0x00d1
            java.util.HashMap<java.lang.String, java.lang.Object[]> r3 = com.uc.crashsdk.a.A     // Catch: all -> 0x00d1
            java.lang.Object r3 = r3.get(r2)     // Catch: all -> 0x00d1
            java.lang.Object[] r3 = (java.lang.Object[]) r3     // Catch: all -> 0x00d1
            r4 = 0
            r5 = r3[r4]     // Catch: all -> 0x00d1
            java.lang.Integer r5 = (java.lang.Integer) r5     // Catch: all -> 0x00d1
            int r5 = r5.intValue()     // Catch: all -> 0x00d1
            r6 = 1
            r7 = r3[r6]     // Catch: all -> 0x00d1
            java.lang.Integer r7 = (java.lang.Integer) r7     // Catch: all -> 0x00d1
            int r7 = r7.intValue()     // Catch: all -> 0x00d1
            r8 = 2
            r3 = r3[r8]     // Catch: all -> 0x00d1
            java.util.List r3 = (java.util.List) r3     // Catch: all -> 0x00d1
            if (r14 != 0) goto L_0x003d
            boolean r7 = com.uc.crashsdk.export.LogType.isForJava(r7)     // Catch: all -> 0x00d1
            if (r7 != 0) goto L_0x0043
            goto L_0x0009
        L_0x003d:
            boolean r7 = a(r14, r2)     // Catch: all -> 0x00d1
            if (r7 == 0) goto L_0x0009
        L_0x0043:
            java.util.Locale r7 = java.util.Locale.US     // Catch: Throwable -> 0x0068, all -> 0x00d1
            java.lang.String r9 = "%s (%d/%d)\n"
            r10 = 3
            java.lang.Object[] r10 = new java.lang.Object[r10]     // Catch: Throwable -> 0x0068, all -> 0x00d1
            r10[r4] = r2     // Catch: Throwable -> 0x0068, all -> 0x00d1
            int r2 = r3.size()     // Catch: Throwable -> 0x0068, all -> 0x00d1
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch: Throwable -> 0x0068, all -> 0x00d1
            r10[r6] = r2     // Catch: Throwable -> 0x0068, all -> 0x00d1
            java.lang.Integer r2 = java.lang.Integer.valueOf(r5)     // Catch: Throwable -> 0x0068, all -> 0x00d1
            r10[r8] = r2     // Catch: Throwable -> 0x0068, all -> 0x00d1
            java.lang.String r2 = java.lang.String.format(r7, r9, r10)     // Catch: Throwable -> 0x0068, all -> 0x00d1
            byte[] r2 = r2.getBytes(r12)     // Catch: Throwable -> 0x0068, all -> 0x00d1
            r11.write(r2)     // Catch: Throwable -> 0x0068, all -> 0x00d1
            goto L_0x006c
        L_0x0068:
            r2 = move-exception
            com.uc.crashsdk.e.a(r2, r11)     // Catch: all -> 0x00d1
        L_0x006c:
            java.util.Iterator r2 = r3.iterator()     // Catch: Throwable -> 0x008d, all -> 0x00d1
        L_0x0070:
            boolean r3 = r2.hasNext()     // Catch: Throwable -> 0x008d, all -> 0x00d1
            if (r3 == 0) goto L_0x0091
            java.lang.Object r3 = r2.next()     // Catch: Throwable -> 0x008d, all -> 0x00d1
            java.lang.String r3 = (java.lang.String) r3     // Catch: Throwable -> 0x008d, all -> 0x00d1
            byte[] r3 = r3.getBytes(r12)     // Catch: Throwable -> 0x008d, all -> 0x00d1
            r11.write(r3)     // Catch: Throwable -> 0x008d, all -> 0x00d1
            java.lang.String r3 = "\n"
            byte[] r3 = r3.getBytes(r12)     // Catch: Throwable -> 0x008d, all -> 0x00d1
            r11.write(r3)     // Catch: Throwable -> 0x008d, all -> 0x00d1
            goto L_0x0070
        L_0x008d:
            r2 = move-exception
            com.uc.crashsdk.e.a(r2, r11)     // Catch: all -> 0x00d1
        L_0x0091:
            java.lang.String r2 = "\n"
            byte[] r2 = r2.getBytes(r12)     // Catch: Throwable -> 0x00a3, all -> 0x00d1
            r11.write(r2)     // Catch: Throwable -> 0x00a3, all -> 0x00d1
            byte[] r2 = r13.getBytes(r12)     // Catch: Throwable -> 0x00a3, all -> 0x00d1
            r11.write(r2)     // Catch: Throwable -> 0x00a3, all -> 0x00d1
            goto L_0x0009
        L_0x00a3:
            r2 = move-exception
            com.uc.crashsdk.e.a(r2, r11)     // Catch: all -> 0x00d1
            goto L_0x0009
        L_0x00a9:
            if (r14 == 0) goto L_0x00cf
            boolean r12 = com.uc.crashsdk.e.E()     // Catch: all -> 0x00d1
            if (r12 == 0) goto L_0x00cf
            java.util.Iterator r12 = r14.iterator()     // Catch: all -> 0x00d1
        L_0x00b5:
            boolean r13 = r12.hasNext()     // Catch: all -> 0x00d1
            if (r13 == 0) goto L_0x00cf
            java.lang.Object r13 = r12.next()     // Catch: all -> 0x00d1
            java.lang.String r13 = (java.lang.String) r13     // Catch: all -> 0x00d1
            java.util.List<java.lang.String> r14 = com.uc.crashsdk.a.B     // Catch: all -> 0x00d1
            boolean r14 = a(r14, r13)     // Catch: all -> 0x00d1
            if (r14 != 0) goto L_0x00b5
            java.lang.String r14 = "CUSTOMCACHEDINFO"
            com.uc.crashsdk.e.a(r11, r14, r13)     // Catch: all -> 0x00d1
            goto L_0x00b5
        L_0x00cf:
            monitor-exit(r0)     // Catch: all -> 0x00d1
            return
        L_0x00d1:
            r11 = move-exception
            monitor-exit(r0)     // Catch: all -> 0x00d1
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.crashsdk.a.b(java.io.OutputStream, java.lang.String, java.lang.String, java.util.ArrayList):void");
    }

    public static String l() {
        StringBuilder sb = new StringBuilder();
        synchronized (A) {
            boolean z2 = true;
            for (String str : B) {
                if (LogType.isForJava(((Integer) A.get(str)[1]).intValue())) {
                    if (!z2) {
                        sb.append("`");
                    }
                    sb.append(str);
                    z2 = false;
                }
            }
        }
        return sb.toString();
    }

    public static String b(String str) {
        StringBuilder sb = new StringBuilder();
        synchronized (A) {
            Object[] objArr = A.get(str);
            int intValue = ((Integer) objArr[0]).intValue();
            List<String> list = (List) objArr[2];
            sb.append(String.format(Locale.US, "%s (%d/%d)\n", str, Integer.valueOf(list.size()), Integer.valueOf(intValue)));
            for (String str2 : list) {
                sb.append(str2);
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public static int a(int i2, String str) {
        if (g.a(str)) {
            str = Thread.currentThread().getName();
        }
        boolean z2 = false;
        z2 = false;
        if (LogType.isForNative(i2) || LogType.isForANR(i2)) {
            if (b.d) {
                synchronized (y) {
                    JNIBridge.nativeCmd(4, i2, str, null);
                }
                z2 = LogType.isForNative(i2);
                if (LogType.isForANR(i2)) {
                    z2 = (z2 ? 1 : 0) | true;
                }
            } else {
                com.uc.crashsdk.a.a.a("crashsdk", "crashsdk so has not loaded!", null);
            }
        }
        if (!LogType.isForJava(i2)) {
            int i3 = z2 ? 1 : 0;
            int i4 = z2 ? 1 : 0;
            int i5 = z2 ? 1 : 0;
            int i6 = z2 ? 1 : 0;
            int i7 = z2 ? 1 : 0;
            return i3;
        }
        a(str, Thread.currentThread());
        int i8 = z2 ? 1 : 0;
        char c2 = z2 ? 1 : 0;
        char c3 = z2 ? 1 : 0;
        char c4 = z2 ? 1 : 0;
        return i8 | 16;
    }

    public static boolean a(boolean z2) {
        int i2;
        if (!b.c) {
            com.uc.crashsdk.a.a.a("crashsdk", "Unexp log not enabled, skip update unexp info!");
            return false;
        } else if (e.E() || b.I()) {
            return false;
        } else {
            if (z2) {
                f.a(F);
                i2 = 0;
            } else if (!b.y()) {
                com.uc.crashsdk.a.a.a("crashsdk", "Stop update unexp info in background!");
                return false;
            } else if (g.E() <= 0) {
                return false;
            } else {
                if (f.b(F)) {
                    return true;
                }
                i2 = g.E() * 1000;
            }
            f.a(0, F, i2);
            return true;
        }
    }

    public static ArrayList<String> c(String str) {
        if (g.a(str)) {
            return null;
        }
        String[] split = str.split(";", 20);
        ArrayList<String> arrayList = new ArrayList<>();
        for (String str2 : split) {
            if (!g.a(str2)) {
                arrayList.add(str2);
            }
        }
        return arrayList;
    }

    public static String m() {
        if (!G) {
            g = b.a(b.j());
            G = true;
            if (g == null) {
                g = "";
            }
        }
        return g;
    }

    public static void n() {
        p();
        if (!H) {
            H = true;
            f.a(0, new e(202));
        } else if (b.d) {
            JNIBridge.set(128, g);
        }
    }

    private static void p() {
        if (I || !e.a()) {
            return;
        }
        if (b.d || !b.g) {
            String format = String.format(Locale.US, "%s/%s/%s", g.R(), g.S(), g.T());
            com.uc.crashsdk.a.a.b("crashsdk", "UUID: " + e.p());
            com.uc.crashsdk.a.a.b("crashsdk", "Version: " + format);
            com.uc.crashsdk.a.a.b("crashsdk", "Process Name: " + e.h());
            I = true;
        }
    }

    public static void a(int i2) {
        switch (i2) {
            case 201:
                com.uc.crashsdk.a.a.a("crashsdk", "Begin update info ...");
                long currentTimeMillis = System.currentTimeMillis();
                if (b.d && c) {
                    JNIBridge.nativeCmd(11, g.E(), String.valueOf(g.F()), null);
                }
                com.uc.crashsdk.a.a.a("crashsdk", "Update info took " + (System.currentTimeMillis() - currentTimeMillis) + " ms");
                a(false);
                return;
            case 202:
                p();
                String format = String.format(Locale.US, "%s/%s/%s", g.R(), g.S(), g.T());
                g = m();
                if (b.d) {
                    JNIBridge.set(128, g);
                }
                boolean z2 = !format.equals(g);
                if (z2) {
                    b.a(b.j(), format);
                }
                if (z2 && g.u()) {
                    com.uc.crashsdk.a.a.a("crashsdk", String.format(Locale.US, "Is new version ('%s' -> '%s'), deleting old stats data!", g, format));
                    b.s();
                    return;
                }
                return;
            default:
                if (!d) {
                    throw new AssertionError();
                }
                return;
        }
    }
}
