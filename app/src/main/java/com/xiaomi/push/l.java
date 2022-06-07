package com.xiaomi.push;

import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothConstants;
import com.xiaomi.micolauncher.skills.update.VersionUtil;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes4.dex */
public class l {
    private static int a = 0;
    private static int b = -1;
    private static Map<String, p> c;

    /* JADX WARN: Removed duplicated region for block: B:15:0x0027  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0028  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static synchronized int a() {
        /*
            java.lang.Class<com.xiaomi.push.l> r0 = com.xiaomi.push.l.class
            monitor-enter(r0)
            int r1 = com.xiaomi.push.l.a     // Catch: all -> 0x004e
            if (r1 != 0) goto L_0x004a
            r1 = 0
            java.lang.String r2 = "ro.miui.ui.version.code"
            java.lang.String r2 = m1112a(r2)     // Catch: Throwable -> 0x002c, all -> 0x004e
            boolean r2 = android.text.TextUtils.isEmpty(r2)     // Catch: Throwable -> 0x002c, all -> 0x004e
            r3 = 1
            if (r2 == 0) goto L_0x0024
            java.lang.String r2 = "ro.miui.ui.version.name"
            java.lang.String r2 = m1112a(r2)     // Catch: Throwable -> 0x002c, all -> 0x004e
            boolean r2 = android.text.TextUtils.isEmpty(r2)     // Catch: Throwable -> 0x002c, all -> 0x004e
            if (r2 != 0) goto L_0x0022
            goto L_0x0024
        L_0x0022:
            r2 = r1
            goto L_0x0025
        L_0x0024:
            r2 = r3
        L_0x0025:
            if (r2 == 0) goto L_0x0028
            goto L_0x0029
        L_0x0028:
            r3 = 2
        L_0x0029:
            com.xiaomi.push.l.a = r3     // Catch: Throwable -> 0x002c, all -> 0x004e
            goto L_0x0034
        L_0x002c:
            r2 = move-exception
            java.lang.String r3 = "get isMIUI failed"
            com.xiaomi.channel.commonutils.logger.b.a(r3, r2)     // Catch: all -> 0x004e
            com.xiaomi.push.l.a = r1     // Catch: all -> 0x004e
        L_0x0034:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: all -> 0x004e
            r1.<init>()     // Catch: all -> 0x004e
            java.lang.String r2 = "isMIUI's value is: "
            r1.append(r2)     // Catch: all -> 0x004e
            int r2 = com.xiaomi.push.l.a     // Catch: all -> 0x004e
            r1.append(r2)     // Catch: all -> 0x004e
            java.lang.String r1 = r1.toString()     // Catch: all -> 0x004e
            com.xiaomi.channel.commonutils.logger.b.b(r1)     // Catch: all -> 0x004e
        L_0x004a:
            int r1 = com.xiaomi.push.l.a     // Catch: all -> 0x004e
            monitor-exit(r0)
            return r1
        L_0x004e:
            r1 = move-exception
            monitor-exit(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.l.a():int");
    }

    public static p a(String str) {
        p b2 = b(str);
        return b2 == null ? p.Global : b2;
    }

    /* renamed from: a */
    public static synchronized String m1111a() {
        synchronized (l.class) {
            int a2 = u.a();
            return (!m1113a() || a2 <= 0) ? "" : a2 < 2 ? "alpha" : a2 < 3 ? "development" : VersionUtil.VERSION_STABLE;
        }
    }

    /* renamed from: a */
    public static String m1112a(String str) {
        try {
            try {
                return (String) au.a("android.os.SystemProperties", BluetoothConstants.GET, str, "");
            } catch (Exception e) {
                b.a(e);
                return null;
            }
        } catch (Throwable unused) {
            return null;
        }
    }

    /* renamed from: a */
    public static synchronized boolean m1113a() {
        boolean z;
        synchronized (l.class) {
            z = true;
            if (a() != 1) {
                z = false;
            }
        }
        return z;
    }

    private static p b(String str) {
        e();
        return c.get(str.toUpperCase());
    }

    public static String b() {
        String a2 = t.a("ro.miui.region", "");
        if (TextUtils.isEmpty(a2)) {
            a2 = t.a("persist.sys.oppo.region", "");
        }
        if (TextUtils.isEmpty(a2)) {
            a2 = t.a("ro.oppo.regionmark", "");
        }
        if (TextUtils.isEmpty(a2)) {
            a2 = t.a("ro.hw.country", "");
        }
        if (TextUtils.isEmpty(a2)) {
            a2 = t.a("ro.csc.countryiso_code", "");
        }
        if (TextUtils.isEmpty(a2)) {
            a2 = t.a("ro.product.country.region", "");
        }
        if (TextUtils.isEmpty(a2)) {
            a2 = t.a("gsm.vivo.countrycode", "");
        }
        if (TextUtils.isEmpty(a2)) {
            a2 = t.a("persist.sys.oem.region", "");
        }
        if (TextUtils.isEmpty(a2)) {
            a2 = t.a("ro.product.locale.region", "");
        }
        if (TextUtils.isEmpty(a2)) {
            a2 = t.a("persist.sys.country", "");
        }
        if (!TextUtils.isEmpty(a2)) {
            b.m149a("get region from system, region = " + a2);
        }
        if (!TextUtils.isEmpty(a2)) {
            return a2;
        }
        String country = Locale.getDefault().getCountry();
        b.m149a("locale.default.country = " + country);
        return country;
    }

    /* renamed from: b */
    public static synchronized boolean m1114b() {
        boolean z;
        synchronized (l.class) {
            z = a() == 2;
        }
        return z;
    }

    public static boolean c() {
        if (b < 0) {
            Object a2 = au.a("miui.external.SdkHelper", "isMiuiSystem", new Object[0]);
            b = 0;
            if (a2 != null && (a2 instanceof Boolean) && !((Boolean) Boolean.class.cast(a2)).booleanValue()) {
                b = 1;
            }
        }
        return b > 0;
    }

    public static boolean d() {
        return !p.China.name().equalsIgnoreCase(a(b()).name());
    }

    private static void e() {
        if (c == null) {
            c = new HashMap();
            c.put("CN", p.China);
            c.put("FI", p.Europe);
            c.put("SE", p.Europe);
            c.put("NO", p.Europe);
            c.put("FO", p.Europe);
            c.put("EE", p.Europe);
            c.put("LV", p.Europe);
            c.put("LT", p.Europe);
            c.put("BY", p.Europe);
            c.put("MD", p.Europe);
            c.put("UA", p.Europe);
            c.put("PL", p.Europe);
            c.put("CZ", p.Europe);
            c.put("SK", p.Europe);
            c.put("HU", p.Europe);
            c.put("DE", p.Europe);
            c.put("AT", p.Europe);
            c.put("CH", p.Europe);
            c.put("LI", p.Europe);
            c.put("GB", p.Europe);
            c.put("IE", p.Europe);
            c.put("NL", p.Europe);
            c.put("BE", p.Europe);
            c.put("LU", p.Europe);
            c.put("FR", p.Europe);
            c.put("RO", p.Europe);
            c.put("BG", p.Europe);
            c.put("RS", p.Europe);
            c.put("MK", p.Europe);
            c.put("AL", p.Europe);
            c.put("GR", p.Europe);
            c.put("SI", p.Europe);
            c.put("HR", p.Europe);
            c.put("IT", p.Europe);
            c.put("SM", p.Europe);
            c.put("MT", p.Europe);
            c.put("ES", p.Europe);
            c.put("PT", p.Europe);
            c.put("AD", p.Europe);
            c.put("CY", p.Europe);
            c.put("DK", p.Europe);
            c.put("RU", p.Russia);
            c.put("IN", p.India);
        }
    }
}
