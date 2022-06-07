package com.uc.crashsdk.a;

import androidx.work.PeriodicWorkRequest;
import com.uc.crashsdk.a;
import com.uc.crashsdk.b;
import com.uc.crashsdk.e;
import com.uc.crashsdk.g;
import com.umeng.analytics.pro.ai;
import com.umeng.commonsdk.framework.UMModuleRegister;

/* compiled from: ProGuard */
/* loaded from: classes2.dex */
public class d {
    static final /* synthetic */ boolean a = !d.class.desiredAssertionStatus();
    private static boolean b = true;
    private static final Object c = new Object();
    private static boolean d = false;
    private static String e = "hsdk";
    private static String f = "alid ";
    private static String g = null;
    private static final Object h = new Object();
    private static String i = null;

    public static void a() {
        f.a(0, new e(500), b.E() ? PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS : 90000L);
    }

    public static void a(int i2) {
        if (i2 == 500) {
            synchronized (c) {
                g = null;
                a(!b.C());
                if (g.b(g)) {
                    h.a(g);
                }
            }
        } else if (!a) {
            throw new AssertionError();
        }
    }

    public static String b() {
        try {
            return "inv" + f + "cras" + e;
        } catch (Throwable th) {
            g.b(th);
            return "";
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0084 A[ADDED_TO_REGION] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static boolean a(boolean r12) {
        /*
            Method dump skipped, instructions count: 397
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.crashsdk.a.d.a(boolean):boolean");
    }

    private static String e() {
        if (g.a(i)) {
            synchronized (h) {
                String str = "https://errlog.umeng.com";
                if (g.P()) {
                    str = "https://errlogos.umeng.com";
                }
                i = g.a(b.g(), str + "/api/crashsdk/validate", true);
            }
        }
        return i;
    }

    public static byte[] c() {
        return new byte[]{6, 0, 23, 8};
    }

    private static String f() {
        byte[] bArr;
        String e2;
        byte[] a2;
        byte[] bArr2;
        StringBuilder sb = new StringBuilder();
        a(sb, "platform", g.e());
        a(sb, "pkgname", a.a);
        a(sb, UMModuleRegister.PROCESS, e.h());
        a(sb, "version", a.a());
        a(sb, "cver", "3.2.0.4");
        a(sb, "ctag", "release");
        a(sb, "inter", g.P() ? "true" : "false");
        a(sb, ai.x, "android");
        String sb2 = sb.toString();
        byte[] bArr3 = new byte[16];
        c.a(bArr3, 0, h.j());
        c.a(bArr3, 4, c.a());
        c.a(bArr3, 8, c());
        c.a(bArr3, 12, a.f());
        try {
            bArr = c.a(sb2.getBytes(), bArr3, true);
        } catch (Throwable th) {
            g.a(th);
            bArr = null;
        }
        if (bArr == null || (e2 = e()) == null || (a2 = c.a(e2, bArr)) == null) {
            return null;
        }
        try {
            bArr2 = c.a(a2, bArr3, false);
        } catch (Throwable th2) {
            g.a(th2);
            bArr2 = null;
        }
        if (bArr2 != null) {
            return new String(bArr2);
        }
        return null;
    }

    private static StringBuilder a(StringBuilder sb, String str, String str2) {
        if (sb.length() > 0) {
            sb.append("`");
        }
        sb.append(str);
        sb.append("=");
        sb.append(str2);
        return sb;
    }

    public static boolean d() {
        if (!e.E() && !b.I()) {
            a(true);
            return b;
        }
        return true;
    }
}
