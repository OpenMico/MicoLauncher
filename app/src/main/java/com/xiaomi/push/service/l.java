package com.xiaomi.push.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.ac;
import com.xiaomi.push.fo;
import com.xiaomi.push.i;
import com.xiaomi.push.p;

/* loaded from: classes4.dex */
public class l {
    private static k a;
    private static a b;

    /* loaded from: classes4.dex */
    public interface a {
        void a();
    }

    public static synchronized k a(Context context) {
        synchronized (l.class) {
            if (a != null) {
                return a;
            }
            SharedPreferences sharedPreferences = context.getSharedPreferences("mipush_account", 0);
            String string = sharedPreferences.getString("uuid", null);
            String string2 = sharedPreferences.getString("token", null);
            String string3 = sharedPreferences.getString("security", null);
            String string4 = sharedPreferences.getString("app_id", null);
            String string5 = sharedPreferences.getString("app_token", null);
            String string6 = sharedPreferences.getString("package_name", null);
            String string7 = sharedPreferences.getString("device_id", null);
            int i = sharedPreferences.getInt("env_type", 1);
            if (!TextUtils.isEmpty(string7) && string7.startsWith("a-")) {
                string7 = i.k(context);
                sharedPreferences.edit().putString("device_id", string7).commit();
            }
            if (TextUtils.isEmpty(string) || TextUtils.isEmpty(string2) || TextUtils.isEmpty(string3)) {
                return null;
            }
            String k = i.k(context);
            if (!"com.xiaomi.xmsf".equals(context.getPackageName()) && !TextUtils.isEmpty(k) && !TextUtils.isEmpty(string7) && !string7.equals(k)) {
                b.m149a("read_phone_state permission changes.");
            }
            a = new k(string, string2, string3, string4, string5, string6, i);
            return a;
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(31:72|4|(2:8|(28:10|12|(1:14)|15|(1:17)|18|(1:20)|21|(1:23)|24|(1:26)|27|73|28|31|(1:33)(1:34)|35|(6:37|(1:39)|40|(1:44)|45|(1:47))|48|(1:50)|51|(1:53)|54|(1:56)|57|(2:59|(5:61|(1:63)|64|65|66)(1:67))|68|69))|11|12|(0)|15|(0)|18|(0)|21|(0)|24|(0)|27|73|28|31|(0)(0)|35|(0)|48|(0)|51|(0)|54|(0)|57|(0)|68|69) */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0096, code lost:
        r10 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0097, code lost:
        com.xiaomi.channel.commonutils.logger.b.a(r10);
        r10 = null;
     */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0052 A[Catch: all -> 0x0216, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x0020, B:8:0x002a, B:10:0x0040, B:12:0x004c, B:14:0x0052, B:15:0x0057, B:17:0x0061, B:18:0x0066, B:21:0x006e, B:24:0x0077, B:27:0x0080, B:28:0x008b, B:30:0x0097, B:33:0x009f, B:35:0x00a8, B:37:0x00cf, B:39:0x00db, B:40:0x00ee, B:42:0x00f8, B:44:0x00fe, B:45:0x0112, B:47:0x0118, B:48:0x011d, B:50:0x0140, B:51:0x0149, B:53:0x0153, B:54:0x015c, B:56:0x0189, B:57:0x018d, B:59:0x0193, B:61:0x01a0, B:63:0x01be, B:64:0x01d4, B:67:0x0202), top: B:72:0x0003, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0061 A[Catch: all -> 0x0216, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x0020, B:8:0x002a, B:10:0x0040, B:12:0x004c, B:14:0x0052, B:15:0x0057, B:17:0x0061, B:18:0x0066, B:21:0x006e, B:24:0x0077, B:27:0x0080, B:28:0x008b, B:30:0x0097, B:33:0x009f, B:35:0x00a8, B:37:0x00cf, B:39:0x00db, B:40:0x00ee, B:42:0x00f8, B:44:0x00fe, B:45:0x0112, B:47:0x0118, B:48:0x011d, B:50:0x0140, B:51:0x0149, B:53:0x0153, B:54:0x015c, B:56:0x0189, B:57:0x018d, B:59:0x0193, B:61:0x01a0, B:63:0x01be, B:64:0x01d4, B:67:0x0202), top: B:72:0x0003, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0075  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x009f A[Catch: all -> 0x0216, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x0020, B:8:0x002a, B:10:0x0040, B:12:0x004c, B:14:0x0052, B:15:0x0057, B:17:0x0061, B:18:0x0066, B:21:0x006e, B:24:0x0077, B:27:0x0080, B:28:0x008b, B:30:0x0097, B:33:0x009f, B:35:0x00a8, B:37:0x00cf, B:39:0x00db, B:40:0x00ee, B:42:0x00f8, B:44:0x00fe, B:45:0x0112, B:47:0x0118, B:48:0x011d, B:50:0x0140, B:51:0x0149, B:53:0x0153, B:54:0x015c, B:56:0x0189, B:57:0x018d, B:59:0x0193, B:61:0x01a0, B:63:0x01be, B:64:0x01d4, B:67:0x0202), top: B:72:0x0003, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00a6  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00cf A[Catch: all -> 0x0216, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x0020, B:8:0x002a, B:10:0x0040, B:12:0x004c, B:14:0x0052, B:15:0x0057, B:17:0x0061, B:18:0x0066, B:21:0x006e, B:24:0x0077, B:27:0x0080, B:28:0x008b, B:30:0x0097, B:33:0x009f, B:35:0x00a8, B:37:0x00cf, B:39:0x00db, B:40:0x00ee, B:42:0x00f8, B:44:0x00fe, B:45:0x0112, B:47:0x0118, B:48:0x011d, B:50:0x0140, B:51:0x0149, B:53:0x0153, B:54:0x015c, B:56:0x0189, B:57:0x018d, B:59:0x0193, B:61:0x01a0, B:63:0x01be, B:64:0x01d4, B:67:0x0202), top: B:72:0x0003, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0140 A[Catch: all -> 0x0216, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x0020, B:8:0x002a, B:10:0x0040, B:12:0x004c, B:14:0x0052, B:15:0x0057, B:17:0x0061, B:18:0x0066, B:21:0x006e, B:24:0x0077, B:27:0x0080, B:28:0x008b, B:30:0x0097, B:33:0x009f, B:35:0x00a8, B:37:0x00cf, B:39:0x00db, B:40:0x00ee, B:42:0x00f8, B:44:0x00fe, B:45:0x0112, B:47:0x0118, B:48:0x011d, B:50:0x0140, B:51:0x0149, B:53:0x0153, B:54:0x015c, B:56:0x0189, B:57:0x018d, B:59:0x0193, B:61:0x01a0, B:63:0x01be, B:64:0x01d4, B:67:0x0202), top: B:72:0x0003, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0153 A[Catch: all -> 0x0216, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x0020, B:8:0x002a, B:10:0x0040, B:12:0x004c, B:14:0x0052, B:15:0x0057, B:17:0x0061, B:18:0x0066, B:21:0x006e, B:24:0x0077, B:27:0x0080, B:28:0x008b, B:30:0x0097, B:33:0x009f, B:35:0x00a8, B:37:0x00cf, B:39:0x00db, B:40:0x00ee, B:42:0x00f8, B:44:0x00fe, B:45:0x0112, B:47:0x0118, B:48:0x011d, B:50:0x0140, B:51:0x0149, B:53:0x0153, B:54:0x015c, B:56:0x0189, B:57:0x018d, B:59:0x0193, B:61:0x01a0, B:63:0x01be, B:64:0x01d4, B:67:0x0202), top: B:72:0x0003, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0189 A[Catch: all -> 0x0216, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x0020, B:8:0x002a, B:10:0x0040, B:12:0x004c, B:14:0x0052, B:15:0x0057, B:17:0x0061, B:18:0x0066, B:21:0x006e, B:24:0x0077, B:27:0x0080, B:28:0x008b, B:30:0x0097, B:33:0x009f, B:35:0x00a8, B:37:0x00cf, B:39:0x00db, B:40:0x00ee, B:42:0x00f8, B:44:0x00fe, B:45:0x0112, B:47:0x0118, B:48:0x011d, B:50:0x0140, B:51:0x0149, B:53:0x0153, B:54:0x015c, B:56:0x0189, B:57:0x018d, B:59:0x0193, B:61:0x01a0, B:63:0x01be, B:64:0x01d4, B:67:0x0202), top: B:72:0x0003, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0193 A[Catch: all -> 0x0216, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x0020, B:8:0x002a, B:10:0x0040, B:12:0x004c, B:14:0x0052, B:15:0x0057, B:17:0x0061, B:18:0x0066, B:21:0x006e, B:24:0x0077, B:27:0x0080, B:28:0x008b, B:30:0x0097, B:33:0x009f, B:35:0x00a8, B:37:0x00cf, B:39:0x00db, B:40:0x00ee, B:42:0x00f8, B:44:0x00fe, B:45:0x0112, B:47:0x0118, B:48:0x011d, B:50:0x0140, B:51:0x0149, B:53:0x0153, B:54:0x015c, B:56:0x0189, B:57:0x018d, B:59:0x0193, B:61:0x01a0, B:63:0x01be, B:64:0x01d4, B:67:0x0202), top: B:72:0x0003, inners: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static synchronized com.xiaomi.push.service.k a(android.content.Context r9, java.lang.String r10, java.lang.String r11, java.lang.String r12) {
        /*
            Method dump skipped, instructions count: 537
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.service.l.a(android.content.Context, java.lang.String, java.lang.String, java.lang.String):com.xiaomi.push.service.k");
    }

    /* renamed from: a */
    public static String m1159a(Context context) {
        StringBuilder sb;
        String str;
        String a2 = a.a(context).a();
        if (ac.b()) {
            sb = new StringBuilder();
            sb.append("http://");
            sb.append(fo.b);
            str = ":9085";
        } else if (p.China.name().equals(a2)) {
            sb = new StringBuilder();
            str = "https://cn.register.xmpush.xiaomi.com";
        } else if (p.Global.name().equals(a2)) {
            sb = new StringBuilder();
            str = "https://register.xmpush.global.xiaomi.com";
        } else if (p.Europe.name().equals(a2)) {
            sb = new StringBuilder();
            str = "https://fr.register.xmpush.global.xiaomi.com";
        } else if (p.Russia.name().equals(a2)) {
            sb = new StringBuilder();
            str = "https://ru.register.xmpush.global.xiaomi.com";
        } else if (p.India.name().equals(a2)) {
            sb = new StringBuilder();
            str = "https://idmb.register.xmpush.global.xiaomi.com";
        } else {
            sb = new StringBuilder();
            sb.append("https://");
            str = ac.m754a() ? "sandbox.xmpush.xiaomi.com" : "register.xmpush.xiaomi.com";
        }
        sb.append(str);
        sb.append("/pass/v2/register");
        return sb.toString();
    }

    public static void a() {
        a aVar = b;
        if (aVar != null) {
            aVar.a();
        }
    }

    /* renamed from: a */
    public static void m1160a(Context context) {
        context.getSharedPreferences("mipush_account", 0).edit().clear().commit();
        a = null;
        a();
    }

    public static void a(Context context, k kVar) {
        SharedPreferences.Editor edit = context.getSharedPreferences("mipush_account", 0).edit();
        edit.putString("uuid", kVar.f190a);
        edit.putString("security", kVar.c);
        edit.putString("token", kVar.b);
        edit.putString("app_id", kVar.d);
        edit.putString("package_name", kVar.f);
        edit.putString("app_token", kVar.e);
        edit.putString("device_id", i.k(context));
        edit.putInt("env_type", kVar.a);
        edit.commit();
        a();
    }

    public static void a(a aVar) {
        b = aVar;
    }

    private static boolean b(Context context) {
        return context.getPackageName().equals("com.xiaomi.xmsf");
    }
}
