package com.xiaomi.push;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.mipush.sdk.Constants;
import java.io.File;
import java.io.IOException;

/* loaded from: classes4.dex */
public class i {
    private static String a = null;
    private static String b = "";
    private static String c = null;
    private static String d = null;
    private static String e = null;
    private static volatile boolean f = false;

    @TargetApi(17)
    public static int a() {
        Object a2;
        if (Build.VERSION.SDK_INT >= 17 && (a2 = au.a("android.os.UserHandle", "myUserId", new Object[0])) != null) {
            return ((Integer) Integer.class.cast(a2)).intValue();
        }
        return -1;
    }

    /* renamed from: a  reason: collision with other method in class */
    public static String m1009a() {
        if (Build.VERSION.SDK_INT > 8 && Build.VERSION.SDK_INT < 26) {
            return Build.SERIAL;
        }
        if (Build.VERSION.SDK_INT >= 26) {
            return (String) au.a("android.os.Build", "getSerial", (Object[]) null);
        }
        return null;
    }

    public static String a(Context context) {
        String e2 = e(context);
        return "a-" + az.b(((String) null) + e2 + ((String) null));
    }

    public static String a(Context context, boolean z) {
        if (c == null) {
            String str = "";
            if (!l.d()) {
                str = z ? f(context) : p(context);
            }
            String e2 = e(context);
            String a2 = m1009a();
            StringBuilder sb = new StringBuilder();
            sb.append("a-");
            sb.append(az.b(str + e2 + a2));
            c = sb.toString();
        }
        return c;
    }

    public static void a(Context context, String str) {
        b.c("update vdevid = " + str);
        if (!TextUtils.isEmpty(str)) {
            e = str;
            v vVar = null;
            try {
                try {
                    if (o(context)) {
                        File file = new File(Environment.getExternalStorageDirectory(), "/Xiaomi/");
                        if (file.exists() && file.isFile()) {
                            file.delete();
                        }
                        File file2 = new File(file, ".vdevid");
                        vVar = v.a(context, file2);
                        z.m1175a(file2);
                        z.a(file2, e);
                    }
                    z.a(new File(context.getFilesDir(), ".vdevid"), e);
                    if (vVar == null) {
                        return;
                    }
                } catch (IOException e2) {
                    b.m149a("update vdevid failure :" + e2.getMessage());
                    if (vVar == null) {
                        return;
                    }
                }
                vVar.a();
            } catch (Throwable th) {
                if (vVar != null) {
                    vVar.a();
                }
                throw th;
            }
        }
    }

    private static boolean a(String str) {
        return !TextUtils.isEmpty(str) && str.length() <= 15 && str.length() >= 14 && az.m763b(str) && !az.c(str);
    }

    public static String b(Context context) {
        try {
            return gw.a(context).a();
        } catch (Exception e2) {
            b.m149a("failure to get gaid:" + e2.getMessage());
            return null;
        }
    }

    public static String c(Context context) {
        v vVar = null;
        if (!o(context)) {
            return null;
        }
        if (!TextUtils.isEmpty(e)) {
            return e;
        }
        e = z.a(new File(context.getFilesDir(), ".vdevid"));
        try {
            if (!TextUtils.isEmpty(e)) {
                return e;
            }
            try {
                File file = new File(new File(Environment.getExternalStorageDirectory(), "/Xiaomi/"), ".vdevid");
                vVar = v.a(context, file);
                e = "";
                String a2 = z.a(file);
                if (a2 != null) {
                    e = a2;
                }
                String str = e;
                if (vVar != null) {
                    vVar.a();
                }
                return str;
            } catch (IOException e2) {
                b.m149a("getVDevID failure :" + e2.getMessage());
                if (vVar != null) {
                    vVar.a();
                }
                return e;
            }
        } catch (Throwable th) {
            if (vVar != null) {
                vVar.a();
            }
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:22:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00be  */
    /* JADX WARN: Type inference failed for: r2v10 */
    /* JADX WARN: Type inference failed for: r2v3, types: [boolean] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String d(android.content.Context r6) {
        /*
            boolean r0 = o(r6)
            r1 = 0
            if (r0 == 0) goto L_0x00cd
            boolean r0 = com.xiaomi.push.i.f
            if (r0 == 0) goto L_0x000d
            goto L_0x00cd
        L_0x000d:
            r0 = 1
            com.xiaomi.push.i.f = r0
            java.io.File r0 = new java.io.File
            java.io.File r2 = r6.getFilesDir()
            java.lang.String r3 = ".vdevid"
            r0.<init>(r2, r3)
            java.lang.String r0 = com.xiaomi.push.z.a(r0)
            java.io.File r2 = new java.io.File     // Catch: IOException -> 0x0045, all -> 0x0041
            java.io.File r3 = android.os.Environment.getExternalStorageDirectory()     // Catch: IOException -> 0x0045, all -> 0x0041
            java.lang.String r4 = "/Xiaomi/"
            r2.<init>(r3, r4)     // Catch: IOException -> 0x0045, all -> 0x0041
            java.io.File r3 = new java.io.File     // Catch: IOException -> 0x0045, all -> 0x0041
            java.lang.String r4 = ".vdevid"
            r3.<init>(r2, r4)     // Catch: IOException -> 0x0045, all -> 0x0041
            com.xiaomi.push.v r2 = com.xiaomi.push.v.a(r6, r3)     // Catch: IOException -> 0x0045, all -> 0x0041
            java.lang.String r3 = com.xiaomi.push.z.a(r3)     // Catch: IOException -> 0x003f, all -> 0x00c6
            if (r2 == 0) goto L_0x0065
            r2.a()
            goto L_0x0065
        L_0x003f:
            r3 = move-exception
            goto L_0x0047
        L_0x0041:
            r6 = move-exception
            r2 = r1
            goto L_0x00c7
        L_0x0045:
            r3 = move-exception
            r2 = r1
        L_0x0047:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: all -> 0x00c6
            r4.<init>()     // Catch: all -> 0x00c6
            java.lang.String r5 = "check id failure :"
            r4.append(r5)     // Catch: all -> 0x00c6
            java.lang.String r3 = r3.getMessage()     // Catch: all -> 0x00c6
            r4.append(r3)     // Catch: all -> 0x00c6
            java.lang.String r3 = r4.toString()     // Catch: all -> 0x00c6
            com.xiaomi.channel.commonutils.logger.b.m149a(r3)     // Catch: all -> 0x00c6
            if (r2 == 0) goto L_0x0064
            r2.a()
        L_0x0064:
            r3 = r1
        L_0x0065:
            boolean r2 = android.text.TextUtils.isEmpty(r0)
            if (r2 != 0) goto L_0x00be
            com.xiaomi.push.i.e = r0
            boolean r2 = android.text.TextUtils.isEmpty(r3)
            if (r2 != 0) goto L_0x0088
            int r2 = r3.length()
            r4 = 128(0x80, float:1.794E-43)
            if (r2 <= r4) goto L_0x007c
            goto L_0x0088
        L_0x007c:
            boolean r6 = android.text.TextUtils.equals(r0, r3)
            if (r6 != 0) goto L_0x009f
            java.lang.String r6 = "vid changed, need sync"
            com.xiaomi.channel.commonutils.logger.b.m149a(r6)
            return r3
        L_0x0088:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r4 = "recover vid :"
            r2.append(r4)
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            com.xiaomi.channel.commonutils.logger.b.m149a(r2)
            a(r6, r0)
        L_0x009f:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r0 = "vdevid = "
            r6.append(r0)
            java.lang.String r0 = com.xiaomi.push.i.e
            r6.append(r0)
            java.lang.String r0 = " "
            r6.append(r0)
            r6.append(r3)
            java.lang.String r6 = r6.toString()
            com.xiaomi.channel.commonutils.logger.b.c(r6)
            return r1
        L_0x00be:
            java.lang.String r6 = "empty local vid"
            com.xiaomi.channel.commonutils.logger.b.m149a(r6)
            java.lang.String r6 = "F*"
            return r6
        L_0x00c6:
            r6 = move-exception
        L_0x00c7:
            if (r2 == 0) goto L_0x00cc
            r2.a()
        L_0x00cc:
            throw r6
        L_0x00cd:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.i.d(android.content.Context):java.lang.String");
    }

    public static String e(Context context) {
        try {
            return Settings.Secure.getString(context.getContentResolver(), "android_id");
        } catch (Throwable th) {
            b.a(th);
            return null;
        }
    }

    public static String f(Context context) {
        String g = g(context);
        int i = 10;
        while (g == null) {
            i--;
            if (i <= 0) {
                break;
            }
            try {
                Thread.sleep(500L);
            } catch (InterruptedException unused) {
            }
            g = g(context);
        }
        return g;
    }

    public static String g(Context context) {
        Object a2;
        Object a3;
        Object a4;
        if (l.d()) {
            return "";
        }
        String str = a;
        if (str != null) {
            return str;
        }
        try {
            String str2 = (!l.m1113a() || (a3 = au.a("miui.telephony.TelephonyManager", "getDefault", new Object[0])) == null || (a4 = au.a(a3, "getMiuiDeviceId", new Object[0])) == null || !(a4 instanceof String)) ? null : (String) String.class.cast(a4);
            if (str2 == null && q(context)) {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                if (Build.VERSION.SDK_INT < 26) {
                    str2 = telephonyManager.getDeviceId();
                } else {
                    if (1 == telephonyManager.getPhoneType()) {
                        a2 = au.a(telephonyManager, "getImei", (Object[]) null);
                    } else if (2 == telephonyManager.getPhoneType()) {
                        a2 = au.a(telephonyManager, "getMeid", (Object[]) null);
                    }
                    str2 = (String) a2;
                }
            }
            if (!a(str2)) {
                return "";
            }
            a = str2;
            return str2;
        } catch (Throwable th) {
            b.a(th);
            return null;
        }
    }

    public static String h(Context context) {
        String j = j(context);
        int i = 10;
        while (j == null) {
            i--;
            if (i <= 0) {
                break;
            }
            try {
                Thread.sleep(500L);
            } catch (InterruptedException unused) {
            }
            j = j(context);
        }
        return j;
    }

    public static String i(Context context) {
        Object a2;
        if (l.d() || Build.VERSION.SDK_INT < 22) {
            return "";
        }
        if (!TextUtils.isEmpty(b)) {
            return b;
        }
        if (!q(context)) {
            return "";
        }
        g(context);
        if (TextUtils.isEmpty(a)) {
            return "";
        }
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            Integer num = (Integer) au.a(telephonyManager, "getPhoneCount", new Object[0]);
            if (num == null || num.intValue() <= 1) {
                return "";
            }
            String str = null;
            for (int i = 0; i < num.intValue(); i++) {
                if (Build.VERSION.SDK_INT < 26) {
                    a2 = au.a(telephonyManager, "getDeviceId", Integer.valueOf(i));
                } else if (1 == telephonyManager.getPhoneType()) {
                    a2 = au.a(telephonyManager, "getImei", Integer.valueOf(i));
                } else {
                    if (2 == telephonyManager.getPhoneType()) {
                        a2 = au.a(telephonyManager, "getMeid", Integer.valueOf(i));
                    }
                    if (!TextUtils.isEmpty(str) && !TextUtils.equals(a, str) && a(str)) {
                        b += str + Constants.ACCEPT_TIME_SEPARATOR_SP;
                    }
                }
                str = (String) a2;
                if (!TextUtils.isEmpty(str)) {
                    b += str + Constants.ACCEPT_TIME_SEPARATOR_SP;
                }
            }
            int length = b.length();
            if (length > 0) {
                b = b.substring(0, length - 1);
            }
            return b;
        } catch (Exception e2) {
            b.d(e2.toString());
            return "";
        }
    }

    public static String j(Context context) {
        i(context);
        if (TextUtils.isEmpty(b)) {
            return "";
        }
        String[] split = b.split(Constants.ACCEPT_TIME_SEPARATOR_SP);
        String str = "";
        for (String str2 : split) {
            if (a(str2)) {
                str = str + az.a(str2) + Constants.ACCEPT_TIME_SEPARATOR_SP;
            }
        }
        int length = str.length();
        return length > 0 ? str.substring(0, length - 1) : str;
    }

    public static synchronized String k(Context context) {
        synchronized (i.class) {
            if (d != null) {
                return d;
            }
            String e2 = e(context);
            String a2 = m1009a();
            d = az.b(e2 + a2);
            return d;
        }
    }

    public static synchronized String l(Context context) {
        String b2;
        synchronized (i.class) {
            String e2 = e(context);
            b2 = az.b(e2 + ((String) null));
        }
        return b2;
    }

    public static String m(Context context) {
        return ((TelephonyManager) context.getSystemService("phone")).getSimOperatorName();
    }

    public static String n(Context context) {
        return "";
    }

    private static boolean o(Context context) {
        boolean z = false;
        if (!n.a(context, "android.permission.WRITE_EXTERNAL_STORAGE") || l.m1113a()) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= 26) {
            z = true;
        }
        return !z ? u.m1174a(context) : z;
    }

    private static String p(Context context) {
        String g = g(context);
        int i = 10;
        while (TextUtils.isEmpty(g)) {
            i--;
            if (i <= 0) {
                break;
            }
            try {
                Thread.sleep(500L);
            } catch (InterruptedException unused) {
            }
            g = g(context);
        }
        return g;
    }

    private static boolean q(Context context) {
        return context.getPackageManager().checkPermission("android.permission.READ_PHONE_STATE", context.getPackageName()) == 0;
    }
}
