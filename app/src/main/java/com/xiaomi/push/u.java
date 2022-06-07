package com.xiaomi.push;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothConstants;

/* loaded from: classes4.dex */
public class u {
    private static Context a;
    private static String b;

    public static int a() {
        try {
            Class<?> cls = Class.forName("miui.os.Build");
            if (cls.getField("IS_STABLE_VERSION").getBoolean(null)) {
                return 3;
            }
            return cls.getField("IS_DEVELOPMENT_VERSION").getBoolean(null) ? 2 : 1;
        } catch (Exception unused) {
            return 0;
        }
    }

    /* renamed from: a  reason: collision with other method in class */
    public static Context m1170a() {
        return a;
    }

    /* renamed from: a  reason: collision with other method in class */
    public static synchronized String m1171a() {
        synchronized (u.class) {
            if (b != null) {
                return b;
            }
            String str = Build.VERSION.INCREMENTAL;
            if (a() <= 0) {
                str = c();
                if (TextUtils.isEmpty(str)) {
                    str = d();
                    if (TextUtils.isEmpty(str)) {
                        str = e();
                        if (TextUtils.isEmpty(str)) {
                            str = String.valueOf(t.a("ro.product.brand", "Android") + "_" + str);
                        }
                    }
                }
                b = str;
                return str;
            }
            b = str;
            return str;
        }
    }

    public static String a(Context context) {
        if (l.m1114b()) {
            return "";
        }
        String str = (String) au.a("com.xiaomi.xmsf.helper.MIIDAccountHelper", "getMIID", context);
        return TextUtils.isEmpty(str) ? "0" : str;
    }

    /* renamed from: a  reason: collision with other method in class */
    public static void m1172a(Context context) {
        a = context.getApplicationContext();
    }

    /* renamed from: a  reason: collision with other method in class */
    public static boolean m1173a() {
        return TextUtils.equals((String) au.a("android.os.SystemProperties", BluetoothConstants.GET, "sys.boot_completed"), "1");
    }

    /* renamed from: a  reason: collision with other method in class */
    public static boolean m1174a(Context context) {
        try {
            return (context.getApplicationInfo().flags & 2) != 0;
        } catch (Exception e) {
            b.a(e);
            return false;
        }
    }

    public static boolean b() {
        try {
            return Class.forName("miui.os.Build").getField("IS_GLOBAL_BUILD").getBoolean(false);
        } catch (ClassNotFoundException unused) {
            b.d("miui.os.Build ClassNotFound");
            return false;
        } catch (Exception e) {
            b.a(e);
            return false;
        }
    }

    private static String c() {
        b = t.a("ro.build.version.emui", "");
        return b;
    }

    private static String d() {
        String a2 = t.a("ro.build.version.opporom", "");
        if (!TextUtils.isEmpty(a2) && !a2.startsWith("ColorOS_")) {
            b = "ColorOS_" + a2;
        }
        return b;
    }

    private static String e() {
        String a2 = t.a("ro.vivo.os.version", "");
        if (!TextUtils.isEmpty(a2) && !a2.startsWith("FuntouchOS_")) {
            b = "FuntouchOS_" + a2;
        }
        return b;
    }
}
