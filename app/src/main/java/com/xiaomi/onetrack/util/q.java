package com.xiaomi.onetrack.util;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.Signature;
import android.graphics.Point;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.support.v4.media.session.PlaybackStateCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import androidx.exifinterface.media.ExifInterface;
import com.xiaomi.micolauncher.module.homepage.record.HomePageRecordHelper;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothConstants;
import com.xiaomi.mipush.sdk.Constants;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.onetrack.a.a;
import com.xiaomi.onetrack.b.g;
import com.xiaomi.onetrack.c.d;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;

/* loaded from: classes4.dex */
public class q {
    private static final int B = 1;
    private static final int C = 2;
    private static final String D = "";
    private static Method E = null;
    private static boolean F = false;
    private static String G = null;
    private static int I = 0;
    private static final Set<String> J;
    public static final int a = 29;
    public static final int b = 25;
    public static final int c = 24;
    public static final int d = 23;
    public static final int e = 22;
    public static final int f = 21;
    public static final int g = 19;
    public static final int h = 17;
    public static final int i = 28;
    private static final String j = "OsUtil";
    private static Class k = null;
    private static Method l = null;
    private static Boolean m = null;
    private static final String n = "ro.build.version.sdk";
    private static final String o = "ro.miui.cust_variant";
    private static final String p = "ro.miui.region";
    private static final String q = "ro.product.mod_device";
    private static final String r = "persist.radio.modem";
    private static final String s = "ro.board.platform";
    private static final String t = "\\d+.\\d+.\\d+(-internal)?";
    private static final long u = 1024;
    private static final long v = 1024;
    private static final long w = 1048576;
    private static final long x = 1073741824;
    private static final DecimalFormat y = new DecimalFormat("#0");
    private static final DecimalFormat z = new DecimalFormat("#0.#");
    private static volatile long A = 0;
    private static boolean H = false;

    static {
        try {
            E = Class.forName("android.os.SystemProperties").getMethod(BluetoothConstants.GET, String.class);
        } catch (Throwable th) {
            p.b(j, "sGetProp init failed ex: " + th.getMessage());
        }
        try {
            k = Class.forName("miui.os.Build");
        } catch (Throwable th2) {
            p.b(j, "sMiuiBuild init failed ex: " + th2.getMessage());
        }
        try {
            l = Class.forName("android.provider.MiuiSettings$Secure").getDeclaredMethod("isUserExperienceProgramEnable", ContentResolver.class);
            l.setAccessible(true);
        } catch (Throwable th3) {
            p.b(j, "sMiuiUEPMethod init failed ex: " + th3.getMessage());
        }
        J = new HashSet(Arrays.asList("AT", "BE", "BG", "CY", "CZ", "DE", "DK", "EE", "ES", "FI", "FR", "GB", "GR", "HR", "HU", "IE", "IT", "LT", "LU", "LV", "MT", "NL", "PL", "PT", "RO", "SE", "SI", "SK"));
    }

    private static String f(String str) {
        try {
            if (E != null) {
                return String.valueOf(E.invoke(null, str));
            }
        } catch (Exception e2) {
            p.b(j, "getProp failed ex: " + e2.getMessage());
        }
        return null;
    }

    public static boolean a() {
        Boolean bool = m;
        if (bool != null) {
            return bool.booleanValue();
        }
        if (!TextUtils.isEmpty(f("ro.miui.ui.version.code"))) {
            m = true;
        } else {
            m = false;
        }
        return m.booleanValue();
    }

    public static String b() {
        return a(TimeZone.getDefault().getRawOffset());
    }

    public static String a(int i2) {
        try {
            int i3 = i2 / 60000;
            char c2 = '+';
            if (i3 < 0) {
                c2 = '-';
                i3 = -i3;
            }
            StringBuilder sb = new StringBuilder(9);
            sb.append("GMT");
            sb.append(c2);
            a(sb, i3 / 60);
            sb.append(':');
            a(sb, i3 % 60);
            return sb.toString();
        } catch (Exception unused) {
            return "";
        }
    }

    private static void a(StringBuilder sb, int i2) {
        String num = Integer.toString(i2);
        for (int i3 = 0; i3 < 2 - num.length(); i3++) {
            sb.append('0');
        }
        sb.append(num);
    }

    public static String c() {
        Locale locale = Locale.getDefault();
        return locale.getLanguage() + "_" + locale.getCountry();
    }

    public static String a(Context context) {
        String f2 = f("gsm.operator.numeric");
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(f2)) {
            String[] split = f2.split(Constants.ACCEPT_TIME_SEPARATOR_SP);
            for (String str : split) {
                if (!TextUtils.isEmpty(str) && !"00000".equals(str)) {
                    if (sb.length() > 0) {
                        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
                    }
                    sb.append(str);
                }
            }
        }
        String sb2 = sb.toString();
        if (TextUtils.isEmpty(sb2)) {
            sb2 = ((TelephonyManager) context.getSystemService("phone")).getNetworkOperator();
        }
        return sb2 == null ? "" : sb2;
    }

    public static String d() {
        Class cls = k;
        if (cls == null) {
            return "";
        }
        try {
            return ((Boolean) cls.getField("IS_ALPHA_BUILD").get(null)).booleanValue() ? "A" : ((Boolean) k.getField("IS_DEVELOPMENT_VERSION").get(null)).booleanValue() ? HomePageRecordHelper.AREA_D : ((Boolean) k.getField("IS_STABLE_VERSION").get(null)).booleanValue() ? ExifInterface.LATITUDE_SOUTH : "";
        } catch (Exception e2) {
            Log.e(j, "getRomBuildCode failed: " + e2.toString());
            return "";
        }
    }

    public static boolean b(Context context) {
        Method method = l;
        if (method == null) {
            return true;
        }
        try {
            return ((Boolean) method.invoke(null, context.getContentResolver())).booleanValue();
        } catch (Exception e2) {
            Log.e(j, "isUserExperiencePlanEnabled failed: " + e2.toString());
            return true;
        }
    }

    public static boolean a(String str) {
        if (OneTrack.isDisable() || OneTrack.isUseSystemNetTrafficOnly()) {
            p.c(str, "should not access network or location, cta");
            return true;
        } else if (!w()) {
            p.c(str, "should not access network or location, not provisioned");
            return true;
        } else if (g.b()) {
            return false;
        } else {
            p.c(str, "should not access network or location, cta");
            return true;
        }
    }

    public static String e() {
        return ab.a("ro.carrier.name", "");
    }

    public static String f() {
        return ab.a("ro.miui.xms.version", "");
    }

    public static boolean g() {
        String a2 = ab.a(p, "unknown");
        if (TextUtils.isEmpty(a2) || TextUtils.equals(a2, "unknown")) {
            return true;
        }
        return J.contains(a2.toUpperCase());
    }

    public static String h() {
        return Build.VERSION.INCREMENTAL;
    }

    private static String A() {
        try {
            String a2 = ab.a(p, "");
            if (TextUtils.isEmpty(a2)) {
                a2 = ab.a("ro.product.locale.region", "");
            }
            if (TextUtils.isEmpty(a2) && Build.VERSION.SDK_INT >= 24) {
                Object invoke = Class.forName("android.os.LocaleList").getMethod("getDefault", new Class[0]).invoke(null, new Object[0]);
                Object invoke2 = invoke.getClass().getMethod("size", new Class[0]).invoke(invoke, new Object[0]);
                if ((invoke2 instanceof Integer) && ((Integer) invoke2).intValue() > 0) {
                    Object invoke3 = invoke.getClass().getMethod(BluetoothConstants.GET, Integer.TYPE).invoke(invoke, 0);
                    Object invoke4 = invoke3.getClass().getMethod("getCountry", new Class[0]).invoke(invoke3, new Object[0]);
                    if (invoke4 instanceof String) {
                        a2 = (String) invoke4;
                    }
                }
            }
            if (TextUtils.isEmpty(a2)) {
                a2 = Locale.getDefault().getCountry();
            }
            return !TextUtils.isEmpty(a2) ? a2.trim() : "";
        } catch (Throwable th) {
            p.b(j, "getRegion Exception: " + th.getMessage());
            return "";
        }
    }

    public static String i() {
        return Build.VERSION.RELEASE;
    }

    public static int j() {
        return Build.VERSION.SDK_INT;
    }

    public static String k() {
        return ab.a(o, "");
    }

    public static String l() {
        return ab.a(p, "");
    }

    public static String m() {
        return ab.a(r, "");
    }

    public static String c(Context context) {
        return a(f(context), true);
    }

    public static String n() {
        return a(B(), true);
    }

    public static String o() {
        return ab.a(s, "");
    }

    public static boolean p() {
        return ab.a(q, "").endsWith("_global");
    }

    public static int q() {
        try {
            Class<?> cls = Class.forName("miui.telephony.TelephonyManager");
            return ((Integer) cls.getMethod("getPhoneCount", new Class[0]).invoke(cls.getMethod("getDefault", new Class[0]).invoke(null, new Object[0]), new Object[0])).intValue();
        } catch (Throwable th) {
            p.b(j, "getPhoneCount Exception: " + th.getMessage());
            return 0;
        }
    }

    private static long f(Context context) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("/proc/meminfo"), 2048);
            String readLine = bufferedReader.readLine();
            String substring = readLine.substring(readLine.indexOf("MemTotal:"));
            bufferedReader.close();
            return Integer.parseInt(substring.replaceAll("\\D+", "")) * PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
        } catch (Exception e2) {
            p.b(j, "getTotalRAM Exception: ", e2);
            return 0L;
        }
    }

    public static double d(Context context) {
        try {
            Intent registerReceiver = context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
            return Math.round(((registerReceiver.getIntExtra(a.d, -1) / registerReceiver.getIntExtra("scale", -1)) * 100.0d) * 10.0d) / 10.0d;
        } catch (Exception e2) {
            Log.e(p.a(j), "getBatteryInfo exception", e2);
            return 0.0d;
        }
    }

    private static long B() {
        try {
            StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
            long blockCount = statFs.getBlockCount() * statFs.getBlockSize();
            if (blockCount >= 8589934592L) {
                return (((blockCount / 8) / 1073741824) + 1) * 1073741824 * 8;
            }
            return ((blockCount / 1073741824) + 1) * 1073741824;
        } catch (Exception e2) {
            p.b(j, "getTotalROM Exception: ", e2);
            return 0L;
        }
    }

    public static String a(long j2, boolean z2) {
        try {
            DecimalFormat decimalFormat = z2 ? y : z;
            if (j2 < PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID && j2 > 0) {
                return decimalFormat.format(j2) + "B";
            } else if (j2 < 1048576) {
                return decimalFormat.format(j2 / 1024.0d) + "KB";
            } else if (j2 < 1073741824) {
                return decimalFormat.format(j2 / 1048576.0d) + "MB";
            } else {
                return decimalFormat.format(j2 / 1.073741824E9d) + "GB";
            }
        } catch (Exception e2) {
            p.b(j, "formatFileSize Exception: ", e2);
            return "";
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x006e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int r() {
        /*
            r0 = 0
            r1 = 0
            int r2 = android.os.Build.VERSION.SDK_INT     // Catch: Throwable -> 0x004d
            r3 = 17
            if (r2 < r3) goto L_0x006c
            java.lang.String r2 = "android.os.UserHandle"
            java.lang.Class r2 = java.lang.Class.forName(r2)     // Catch: Throwable -> 0x004d
            java.lang.String r3 = "getUserId"
            r4 = 1
            java.lang.Class[] r5 = new java.lang.Class[r4]     // Catch: Throwable -> 0x004d
            java.lang.Class r6 = java.lang.Integer.TYPE     // Catch: Throwable -> 0x004d
            r5[r1] = r6     // Catch: Throwable -> 0x004d
            java.lang.reflect.Method r2 = r2.getDeclaredMethod(r3, r5)     // Catch: Throwable -> 0x004d
            r2.setAccessible(r4)     // Catch: Throwable -> 0x004d
            int r3 = android.os.Process.myUid()     // Catch: Throwable -> 0x004d
            java.lang.Object[] r5 = new java.lang.Object[r4]     // Catch: Throwable -> 0x004d
            java.lang.Integer r6 = java.lang.Integer.valueOf(r3)     // Catch: Throwable -> 0x004d
            r5[r1] = r6     // Catch: Throwable -> 0x004d
            java.lang.Object r2 = r2.invoke(r0, r5)     // Catch: Throwable -> 0x004d
            java.lang.Integer r2 = (java.lang.Integer) r2     // Catch: Throwable -> 0x004d
            java.lang.String r0 = "OsUtil"
            java.lang.String r5 = "getUserId, uid:%d, userId:%d"
            r6 = 2
            java.lang.Object[] r6 = new java.lang.Object[r6]     // Catch: Throwable -> 0x0048
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch: Throwable -> 0x0048
            r6[r1] = r3     // Catch: Throwable -> 0x0048
            r6[r4] = r2     // Catch: Throwable -> 0x0048
            java.lang.String r3 = java.lang.String.format(r5, r6)     // Catch: Throwable -> 0x0048
            com.xiaomi.onetrack.util.p.a(r0, r3)     // Catch: Throwable -> 0x0048
            r0 = r2
            goto L_0x006c
        L_0x0048:
            r0 = move-exception
            r7 = r2
            r2 = r0
            r0 = r7
            goto L_0x004e
        L_0x004d:
            r2 = move-exception
        L_0x004e:
            java.lang.String r3 = "OsUtil"
            java.lang.String r3 = com.xiaomi.onetrack.util.p.a(r3)
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "getUserId exception: "
            r4.append(r5)
            java.lang.String r2 = r2.getMessage()
            r4.append(r2)
            java.lang.String r2 = r4.toString()
            android.util.Log.e(r3, r2)
        L_0x006c:
            if (r0 != 0) goto L_0x0072
            java.lang.Integer r0 = java.lang.Integer.valueOf(r1)
        L_0x0072:
            int r0 = r0.intValue()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.onetrack.util.q.r():int");
    }

    public static boolean s() {
        return r() == 0;
    }

    public static boolean t() {
        int r2 = r();
        if (r2 < 10 || r2 == 99 || r2 == 999) {
            return false;
        }
        p.a(j, "second space");
        return true;
    }

    public static boolean u() {
        return r() == 999;
    }

    public static boolean b(String str) {
        try {
            return ((Boolean) Class.forName("miui.os.MiuiInit").getMethod("isPreinstalledPackage", String.class).invoke(null, str)).booleanValue();
        } catch (ClassNotFoundException e2) {
            if (a()) {
                String a2 = p.a(j);
                Log.e(a2, "checkPreinstallApp failed:" + e2.getMessage());
            }
            return false;
        } catch (Throwable th) {
            String a3 = p.a(j);
            Log.e(a3, "checkPreinstallApp failed:" + th.getMessage());
            return false;
        }
    }

    public static boolean c(String str) {
        try {
            return ((Boolean) Class.forName("miui.os.MiuiInit").getMethod("isPreinstalledPAIPackage", String.class).invoke(null, str)).booleanValue();
        } catch (Throwable th) {
            String a2 = p.a(j);
            Log.e(a2, "isPreinstalledPAIPackage failed:" + th.getMessage());
            return false;
        }
    }

    public static int d(String str) {
        boolean b2 = b(str);
        boolean c2 = c(str);
        int i2 = b2 ? 1 : 0;
        return c2 ? i2 | 2 : i2;
    }

    public static String v() {
        long currentTimeMillis = System.currentTimeMillis();
        return d.h(String.valueOf(currentTimeMillis + new Random(currentTimeMillis).nextDouble())).substring(0, 24);
    }

    public static boolean a(Context context, String str) {
        try {
            return context.getPackageManager().checkPermission(str, context.getPackageName()) == 0;
        } catch (Exception unused) {
            return false;
        }
    }

    public static boolean a(ApplicationInfo applicationInfo) {
        return (applicationInfo.flags & 1) != 0;
    }

    public static boolean b(Context context, String str) {
        try {
            return a(a(context, str, 0).applicationInfo);
        } catch (Exception unused) {
            return false;
        }
    }

    public static PackageInfo a(Context context, String str, int i2) {
        try {
            return context.getPackageManager().getPackageInfo(str, i2);
        } catch (Exception unused) {
            return null;
        }
    }

    public static String e(String str) {
        try {
            return com.xiaomi.onetrack.e.a.a().getPackageManager().getPackageInfo(str, 16384).versionName;
        } catch (Exception e2) {
            e2.printStackTrace();
            return "";
        }
    }

    public static boolean c(Context context, String str) {
        PackageInfo a2 = a(context, str, 0);
        return (a2 == null || a2.applicationInfo == null) ? false : true;
    }

    public static Signature[] a(Context context, File file) {
        try {
            return context.getPackageManager().getPackageArchiveInfo(file.getPath(), 64).signatures;
        } catch (Exception unused) {
            return null;
        }
    }

    public static Signature[] d(Context context, String str) {
        try {
            return context.getPackageManager().getPackageInfo(str, 64).signatures;
        } catch (Exception unused) {
            return null;
        }
    }

    @TargetApi(17)
    public static boolean w() {
        try {
            Context a2 = com.xiaomi.onetrack.e.a.a();
            if (Build.VERSION.SDK_INT < 17) {
                return true;
            }
            boolean z2 = false;
            if (Settings.Global.getInt(a2.getContentResolver(), "device_provisioned", 0) != 0) {
                z2 = true;
            }
            if (!z2) {
                p.c(j, "Provisioned: " + z2);
            }
            return z2;
        } catch (Exception e2) {
            p.b(j, "isDeviceProvisioned exception", e2);
            return true;
        }
    }

    public static String e(Context context) {
        Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= 17) {
            defaultDisplay.getRealSize(point);
        } else {
            defaultDisplay.getSize(point);
        }
        return String.format("%d*%d", Integer.valueOf(point.y), Integer.valueOf(point.x));
    }

    private static boolean C() {
        Class cls = k;
        if (cls != null) {
            try {
                return ((Boolean) cls.getField("IS_INTERNATIONAL_BUILD").get(null)).booleanValue();
            } catch (Exception unused) {
            }
        }
        String A2 = A();
        if (!TextUtils.isEmpty(A2)) {
            return !TextUtils.equals("CN", A2.toUpperCase());
        }
        return false;
    }

    public static boolean x() {
        if (!a() || H) {
            return F;
        }
        return C();
    }

    public static String y() {
        if (!a() || H) {
            return !TextUtils.isEmpty(G) ? G : A();
        }
        return A();
    }

    public static void a(boolean z2, String str, OneTrack.Mode mode) {
        int i2;
        if (mode == OneTrack.Mode.APP) {
            i2 = 3;
        } else if (mode == OneTrack.Mode.PLUGIN) {
            i2 = 2;
        } else {
            i2 = mode == OneTrack.Mode.SDK ? 1 : 0;
        }
        if (I <= i2) {
            F = z2;
            G = str;
            I = i2;
        }
    }

    public static void a(boolean z2) {
        H = z2;
    }

    public static String z() {
        return A();
    }
}
