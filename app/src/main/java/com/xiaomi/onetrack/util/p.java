package com.xiaomi.onetrack.util;

import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.onetrack.e.a;

/* loaded from: classes4.dex */
public class p {
    public static boolean a = false;
    public static boolean b = false;
    public static boolean c = false;
    private static final int d = 3000;
    private static final String e = "OneTrack-Api-";
    private static final int f = 0;
    private static final int g = 1;
    private static final int h = 2;
    private static final int i = 3;
    private static final int j = 4;
    private static boolean k = false;
    private static boolean l = false;
    private static boolean m = false;
    private static boolean n = false;

    public static void a() {
        try {
            String d2 = a.d();
            String a2 = ab.a("debug.onetrack.log");
            boolean z = true;
            l = !TextUtils.isEmpty(a2) && !TextUtils.isEmpty(d2) && TextUtils.equals(d2, a2);
            String a3 = ab.a("debug.onetrack.upload");
            b = !TextUtils.isEmpty(a3) && !TextUtils.isEmpty(d2) && TextUtils.equals(d2, a3);
            String a4 = ab.a("debug.onetrack.test");
            if (TextUtils.isEmpty(a4) || TextUtils.isEmpty(d2) || !TextUtils.equals(d2, a4)) {
                z = false;
            }
            n = z;
            b();
            c();
        } catch (Exception e2) {
            Log.e("OneTrackSdk", "LogUtil static initializer: " + e2.toString());
        }
        Log.d("OneTrackSdk", "log on: " + l + ", quick upload on: " + b);
    }

    public static void a(String str, String str2) {
        if (a) {
            a(a(str), str2, 3);
        }
    }

    public static void a(String str, String str2, Throwable th) {
        if (a) {
            Log.d(a(str), str2, th);
        }
    }

    public static void b(String str, String str2) {
        if (a) {
            a(a(str), str2, 0);
        }
    }

    public static void b(String str, String str2, Throwable th) {
        if (a) {
            Log.e(a(str), str2, th);
        }
    }

    public static void c(String str, String str2) {
        if (a) {
            a(a(str), str2, 1);
        }
    }

    public static void c(String str, String str2, Throwable th) {
        if (a) {
            Log.w(a(str), str2, th);
        }
    }

    public static void d(String str, String str2) {
        if (a) {
            a(a(str), str2, 2);
        }
    }

    public static void d(String str, String str2, Throwable th) {
        if (a) {
            Log.i(a(str), str2, th);
        }
    }

    private static void a(String str, String str2, int i2) {
        if (str2 != null) {
            int i3 = 0;
            while (i3 <= str2.length() / 3000) {
                int i4 = i3 * 3000;
                i3++;
                int min = Math.min(str2.length(), i3 * 3000);
                if (i4 < min) {
                    String substring = str2.substring(i4, min);
                    switch (i2) {
                        case 0:
                            Log.e(str, substring);
                            continue;
                        case 1:
                            Log.w(str, substring);
                            continue;
                        case 2:
                            Log.i(str, substring);
                            continue;
                        case 3:
                            Log.d(str, substring);
                            continue;
                        case 4:
                            Log.v(str, substring);
                            continue;
                    }
                }
            }
        }
    }

    public static String a(String str) {
        return e + str;
    }

    public static void a(boolean z) {
        k = z;
        b();
    }

    private static void b() {
        a = k || l;
        Log.d("OneTrackSdk", "updateDebugSwitch sEnable: " + a + " sDebugMode：" + k + " sDebugProperty：" + l);
    }

    public static void b(boolean z) {
        m = z;
        c();
    }

    private static void c() {
        c = m || n;
        Log.d("OneTrackSdk", "updateTestSwitch sTestEnable: " + c + " sTestMode：" + m + " sTestProperty：" + n);
    }
}
