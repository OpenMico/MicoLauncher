package com.xiaomi.onetrack.util;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.StatFs;
import android.provider.Settings;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.Display;
import android.view.WindowManager;
import com.google.android.exoplayer2.C;
import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.infra.galaxy.fds.Constants;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothConstants;
import com.xiaomi.onetrack.c.d;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes4.dex */
public class DeviceUtil {
    private static String A = null;
    private static String B = null;
    private static String C = null;
    private static String D = null;
    private static String E = null;
    private static String F = null;
    private static String G = null;
    private static Boolean H = null;
    private static String I = null;
    static final int a = 15;
    static final int b = 6;
    private static final String c = "DeviceUtil";
    private static final long d = 7776000000L;
    private static final String e = "";
    private static final String f = "ro.build.product";
    private static final String g = "ro.build.description";
    private static final int h = 15;
    private static final int i = 14;
    private static Method j;
    private static Method k;
    private static Method l;
    private static Object m;
    private static Method n;
    private static Method o;
    private static String p;
    private static String q;
    private static String r;
    private static String s;
    private static String t;
    private static String u;
    private static String v;
    private static String w;
    private static String x;
    private static String y;
    private static String z;

    public static long a(long j2) {
        float f2 = (float) j2;
        if (f2 > 900.0d) {
            f2 /= 1000.0f;
        }
        if (f2 > 900.0d) {
            f2 /= 1000.0f;
        }
        if (f2 > 900.0d) {
            f2 /= 1000.0f;
        }
        return f2;
    }

    private static long c(long j2) {
        long j3 = 1;
        long j4 = 1;
        while (true) {
            long j5 = j3 * j4;
            if (j5 >= j2) {
                return j5;
            }
            j3 <<= 1;
            if (j3 > 512) {
                j4 *= 1000;
                j3 = 1;
            }
        }
    }

    static {
        try {
            j = Class.forName("android.os.SystemProperties").getMethod(BluetoothConstants.GET, String.class);
        } catch (Throwable th) {
            p.b(c, "sGetProp init failed ex: " + th.getMessage());
        }
        try {
            Class<?> cls = Class.forName("miui.telephony.TelephonyManagerEx");
            m = cls.getMethod("getDefault", new Class[0]).invoke(null, new Object[0]);
            k = cls.getMethod("getImeiList", new Class[0]);
            l = cls.getMethod("getMeidList", new Class[0]);
            o = cls.getMethod("getSubscriberIdForSlot", Integer.TYPE);
        } catch (Throwable th2) {
            p.b(c, "TelephonyManagerEx init failed ex: " + th2.getMessage());
        }
        try {
            if (Build.VERSION.SDK_INT >= 21) {
                n = Class.forName("android.telephony.TelephonyManager").getMethod("getImei", Integer.TYPE);
            }
        } catch (Throwable th3) {
            p.b(c, "sGetImeiForSlot init failed ex: " + th3.getMessage());
        }
    }

    public static String a(Context context) {
        if (!TextUtils.isEmpty(p)) {
            return p;
        }
        r(context);
        return !TextUtils.isEmpty(p) ? p : "";
    }

    public static String b(Context context) {
        if (!TextUtils.isEmpty(w)) {
            return w;
        }
        String a2 = a(context);
        if (TextUtils.isEmpty(a2)) {
            return "";
        }
        w = d.c(a2);
        return w;
    }

    public static String c(Context context) {
        if (!TextUtils.isEmpty(A)) {
            return A;
        }
        String a2 = a(context);
        if (TextUtils.isEmpty(a2)) {
            return "";
        }
        A = d.e(a2);
        return A;
    }

    public static String d(Context context) {
        if (!TextUtils.isEmpty(C)) {
            return C;
        }
        String a2 = a(context);
        if (TextUtils.isEmpty(a2)) {
            return "";
        }
        C = d.d(a2);
        return C;
    }

    public static String e(Context context) {
        if (!TextUtils.isEmpty(q)) {
            return q;
        }
        r(context);
        return !TextUtils.isEmpty(q) ? q : "";
    }

    public static String f(Context context) {
        if (!TextUtils.isEmpty(x)) {
            return x;
        }
        String e2 = e(context);
        if (TextUtils.isEmpty(e2)) {
            return "";
        }
        x = d.c(e2);
        return x;
    }

    public static String g(Context context) {
        if (!TextUtils.isEmpty(B)) {
            return B;
        }
        String e2 = e(context);
        if (TextUtils.isEmpty(e2)) {
            return "";
        }
        B = d.e(e2);
        return B;
    }

    public static String h(Context context) {
        if (!TextUtils.isEmpty(D)) {
            return D;
        }
        String e2 = e(context);
        if (TextUtils.isEmpty(e2)) {
            return "";
        }
        D = d.d(e2);
        return D;
    }

    private static String A(Context context) {
        if (!TextUtils.isEmpty(r)) {
            return r;
        }
        D(context);
        return !TextUtils.isEmpty(r) ? r : "";
    }

    public static String i(Context context) {
        if (!TextUtils.isEmpty(y)) {
            return y;
        }
        String A2 = A(context);
        if (TextUtils.isEmpty(A2)) {
            return "";
        }
        y = d.c(A2);
        return y;
    }

    public static String j(Context context) {
        if (!TextUtils.isEmpty(E)) {
            return E;
        }
        String A2 = A(context);
        if (TextUtils.isEmpty(A2)) {
            return "";
        }
        E = d.e(A2);
        return E;
    }

    public static String k(Context context) {
        if (!TextUtils.isEmpty(s)) {
            return s;
        }
        String b2 = a.b(context);
        if (TextUtils.isEmpty(b2)) {
            return "";
        }
        s = b2;
        return s;
    }

    public static String l(Context context) {
        if (!TextUtils.isEmpty(t)) {
            return t;
        }
        String u2 = u(context);
        if (TextUtils.isEmpty(u2)) {
            return "";
        }
        t = u2;
        return t;
    }

    public static String m(Context context) {
        if (!TextUtils.isEmpty(z)) {
            return z;
        }
        String l2 = l(context);
        if (TextUtils.isEmpty(l2)) {
            return "";
        }
        z = d.c(l2);
        return z;
    }

    public static String n(Context context) {
        if (!TextUtils.isEmpty(G)) {
            return G;
        }
        String l2 = l(context);
        if (TextUtils.isEmpty(l2)) {
            return "";
        }
        G = d.e(l2);
        return G;
    }

    public static String o(Context context) {
        if (!TextUtils.isEmpty(u)) {
            return u;
        }
        try {
            String type = context.getContentResolver().getType(Uri.parse("content://com.miui.analytics.server.AnalyticsProvider/aaid"));
            if (!TextUtils.isEmpty(type)) {
                u = type;
                return type;
            }
            Object invoke = Class.forName("android.provider.MiuiSettings$Ad").getDeclaredMethod("getAaid", ContentResolver.class).invoke(null, context.getContentResolver());
            if (!(invoke instanceof String) || TextUtils.isEmpty((String) invoke)) {
                return "";
            }
            u = (String) invoke;
            return u;
        } catch (Throwable th) {
            p.a(c, "getAaid failed ex: " + th.getMessage());
            return "";
        }
    }

    public static String p(Context context) {
        if (!TextUtils.isEmpty(v)) {
            return v;
        }
        if (GAIDClient.b(context)) {
            return "";
        }
        String a2 = GAIDClient.a(context);
        if (TextUtils.isEmpty(a2)) {
            return "";
        }
        v = a2;
        return v;
    }

    public static void a() {
        v = null;
    }

    public static List<String> q(Context context) {
        List<String> r2 = r(context);
        ArrayList arrayList = new ArrayList();
        if (r2 != null && !r2.isEmpty()) {
            for (int i2 = 0; i2 < r2.size(); i2++) {
                if (!TextUtils.isEmpty(r2.get(i2))) {
                    arrayList.add(i2, d.c(r2.get(i2)));
                }
            }
        }
        return arrayList;
    }

    @SuppressLint({"MissingPermission"})
    public static List<String> r(Context context) {
        List<String> list;
        if (u.a(context)) {
            list = l();
            if (list == null || list.isEmpty()) {
                if (Build.VERSION.SDK_INT >= 21) {
                    list = B(context);
                } else {
                    list = C(context);
                }
            }
        } else {
            list = null;
        }
        if (list != null && !list.isEmpty()) {
            Collections.sort(list);
            p = list.get(0);
            if (list.size() >= 2) {
                q = list.get(1);
            }
        }
        return list;
    }

    private static List<String> l() {
        if (k == null || n()) {
            return null;
        }
        try {
            List<String> list = (List) k.invoke(m, new Object[0]);
            if (list == null || list.size() <= 0) {
                return null;
            }
            if (!a(list)) {
                return list;
            }
            return null;
        } catch (Exception e2) {
            p.a(c, "getImeiListFromMiui failed ex: " + e2.getMessage());
            return null;
        }
    }

    private static List<String> B(Context context) {
        if (n == null) {
            return null;
        }
        try {
            ArrayList arrayList = new ArrayList();
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            String str = (String) n.invoke(telephonyManager, 0);
            if (c(str)) {
                arrayList.add(str);
            }
            if (m()) {
                String str2 = (String) n.invoke(telephonyManager, 1);
                if (c(str2)) {
                    arrayList.add(str2);
                }
            }
            return arrayList;
        } catch (Exception e2) {
            p.a(c, "getImeiListAboveLollipop failed ex: " + e2.getMessage());
            return null;
        }
    }

    @SuppressLint({"MissingPermission"})
    private static List<String> C(Context context) {
        try {
            ArrayList arrayList = new ArrayList();
            Class<?> cls = Class.forName("android.telephony.TelephonyManager");
            if (!m()) {
                String deviceId = ((TelephonyManager) cls.getMethod("getDefault", new Class[0]).invoke(null, new Object[0])).getDeviceId();
                if (c(deviceId)) {
                    arrayList.add(deviceId);
                }
                return arrayList;
            }
            String deviceId2 = ((TelephonyManager) cls.getMethod("getDefault", Integer.TYPE).invoke(null, 0)).getDeviceId();
            String deviceId3 = ((TelephonyManager) cls.getMethod("getDefault", Integer.TYPE).invoke(null, 1)).getDeviceId();
            if (c(deviceId2)) {
                arrayList.add(deviceId2);
            }
            if (c(deviceId3)) {
                arrayList.add(deviceId3);
            }
            return arrayList;
        } catch (Throwable th) {
            p.a(c, "getImeiListBelowLollipop failed ex: " + th.getMessage());
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x0090 A[Catch: Throwable -> 0x009b, TRY_LEAVE, TryCatch #0 {Throwable -> 0x009b, blocks: (B:16:0x004b, B:18:0x005b, B:20:0x0061, B:22:0x006b, B:24:0x0076, B:26:0x0080, B:28:0x008a, B:30:0x0090), top: B:35:0x004b }] */
    @android.annotation.SuppressLint({"MissingPermission"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.util.List<java.lang.String> D(android.content.Context r6) {
        /*
            boolean r0 = com.xiaomi.onetrack.util.u.a(r6)
            r1 = 0
            if (r0 == 0) goto L_0x00b6
            java.lang.reflect.Method r0 = com.xiaomi.onetrack.util.DeviceUtil.l
            r2 = 0
            if (r0 == 0) goto L_0x004b
            java.lang.Object r3 = com.xiaomi.onetrack.util.DeviceUtil.m     // Catch: Exception -> 0x0030
            java.lang.Object[] r4 = new java.lang.Object[r2]     // Catch: Exception -> 0x0030
            java.lang.Object r0 = r0.invoke(r3, r4)     // Catch: Exception -> 0x0030
            java.util.List r0 = (java.util.List) r0     // Catch: Exception -> 0x0030
            if (r0 == 0) goto L_0x004b
            int r3 = r0.size()     // Catch: Exception -> 0x0030
            if (r3 <= 0) goto L_0x004b
            boolean r3 = b(r0)     // Catch: Exception -> 0x0030
            if (r3 != 0) goto L_0x004b
            java.util.Collections.sort(r0)     // Catch: Exception -> 0x0030
            java.lang.Object r3 = r0.get(r2)     // Catch: Exception -> 0x0030
            java.lang.String r3 = (java.lang.String) r3     // Catch: Exception -> 0x0030
            com.xiaomi.onetrack.util.DeviceUtil.r = r3     // Catch: Exception -> 0x0030
            return r0
        L_0x0030:
            r0 = move-exception
            java.lang.String r3 = "DeviceUtil"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "queryMeidList failed ex: "
            r4.append(r5)
            java.lang.String r0 = r0.getMessage()
            r4.append(r0)
            java.lang.String r0 = r4.toString()
            com.xiaomi.onetrack.util.p.a(r3, r0)
        L_0x004b:
            java.lang.String r0 = "android.telephony.TelephonyManager"
            java.lang.Class r0 = java.lang.Class.forName(r0)     // Catch: Throwable -> 0x009b
            java.lang.String r3 = "phone"
            java.lang.Object r6 = r6.getSystemService(r3)     // Catch: Throwable -> 0x009b
            android.telephony.TelephonyManager r6 = (android.telephony.TelephonyManager) r6     // Catch: Throwable -> 0x009b
            if (r0 == 0) goto L_0x0089
            int r3 = android.os.Build.VERSION.SDK_INT     // Catch: Throwable -> 0x009b
            r4 = 26
            if (r3 < r4) goto L_0x0076
            java.lang.String r3 = "getMeid"
            java.lang.Class[] r4 = new java.lang.Class[r2]     // Catch: Throwable -> 0x009b
            java.lang.reflect.Method r0 = r0.getMethod(r3, r4)     // Catch: Throwable -> 0x009b
            if (r0 == 0) goto L_0x0074
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch: Throwable -> 0x009b
            java.lang.Object r6 = r0.invoke(r6, r2)     // Catch: Throwable -> 0x009b
            java.lang.String r6 = (java.lang.String) r6     // Catch: Throwable -> 0x009b
            goto L_0x008a
        L_0x0074:
            r6 = r1
            goto L_0x008a
        L_0x0076:
            java.lang.String r3 = "getDeviceId"
            java.lang.Class[] r4 = new java.lang.Class[r2]     // Catch: Throwable -> 0x009b
            java.lang.reflect.Method r0 = r0.getMethod(r3, r4)     // Catch: Throwable -> 0x009b
            if (r0 == 0) goto L_0x0089
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch: Throwable -> 0x009b
            java.lang.Object r6 = r0.invoke(r6, r2)     // Catch: Throwable -> 0x009b
            java.lang.String r6 = (java.lang.String) r6     // Catch: Throwable -> 0x009b
            goto L_0x008a
        L_0x0089:
            r6 = r1
        L_0x008a:
            boolean r0 = d(r6)     // Catch: Throwable -> 0x009b
            if (r0 == 0) goto L_0x00b6
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch: Throwable -> 0x009b
            r0.<init>()     // Catch: Throwable -> 0x009b
            r0.add(r6)     // Catch: Throwable -> 0x009b
            com.xiaomi.onetrack.util.DeviceUtil.r = r6     // Catch: Throwable -> 0x009b
            return r0
        L_0x009b:
            r6 = move-exception
            java.lang.String r0 = "DeviceUtil"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "queryMeidList->getMeid failed ex: "
            r2.append(r3)
            java.lang.String r6 = r6.getMessage()
            r2.append(r6)
            java.lang.String r6 = r2.toString()
            com.xiaomi.onetrack.util.p.a(r0, r6)
        L_0x00b6:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.onetrack.util.DeviceUtil.D(android.content.Context):java.util.List");
    }

    public static String s(Context context) {
        try {
            List<String> D2 = D(context);
            if (D2 == null) {
                return "";
            }
            Collections.sort(D2, new h());
            for (int i2 = 0; i2 < D2.size(); i2++) {
                D2.set(i2, d.h(D2.get(i2)));
            }
            return D2.toString();
        } catch (Throwable th) {
            p.b(c, "getMeidListMd5 e", th);
            return "";
        }
    }

    @SuppressLint({"MissingPermission"})
    public static List<String> t(Context context) {
        String str;
        String str2;
        if (u.b(context)) {
            ArrayList arrayList = new ArrayList();
            try {
                if (m()) {
                    Class<?> cls = Class.forName("android.telephony.TelephonyManager");
                    if (Build.VERSION.SDK_INT >= 22) {
                        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                        SubscriptionManager subscriptionManager = (SubscriptionManager) context.getSystemService("telephony_subscription_service");
                        Class<?> cls2 = Class.forName("android.telephony.SubscriptionManager");
                        if (Build.VERSION.SDK_INT < 29) {
                            str = a(cls, cls2, telephonyManager, subscriptionManager)[0];
                            str2 = a(cls, cls2, telephonyManager, subscriptionManager)[1];
                        } else {
                            str = b(cls, cls2, telephonyManager, subscriptionManager)[0];
                            str2 = b(cls, cls2, telephonyManager, subscriptionManager)[1];
                        }
                    } else {
                        str = ((TelephonyManager) cls.getMethod("getDefault", Integer.TYPE).invoke(null, 0)).getSubscriberId();
                        str2 = ((TelephonyManager) cls.getMethod("getDefault", Integer.TYPE).invoke(null, 1)).getSubscriberId();
                    }
                    if (!e(str)) {
                        str = "";
                    }
                    arrayList.add(str);
                    if (!e(str2)) {
                        str2 = "";
                    }
                    arrayList.add(str2);
                    return arrayList;
                }
                String subscriberId = ((TelephonyManager) context.getSystemService("phone")).getSubscriberId();
                if (e(subscriberId)) {
                    arrayList.add(subscriberId);
                }
                return arrayList;
            } catch (SecurityException unused) {
                p.a(c, "getImsiList failed with on permission");
            } catch (Throwable th) {
                p.b(c, "getImsiList failed: " + th.getMessage());
            }
        }
        return null;
    }

    private static boolean m() {
        if ("dsds".equals(b("persist.radio.multisim.config"))) {
            return true;
        }
        String str = Build.DEVICE;
        return "lcsh92_wet_jb9".equals(str) || "lcsh92_wet_tdd".equals(str) || "HM2013022".equals(str) || "HM2013023".equals(str) || "armani".equals(str) || "HM2014011".equals(str) || "HM2014012".equals(str);
    }

    public static String b(String str) {
        try {
            if (j != null) {
                return String.valueOf(j.invoke(null, str));
            }
        } catch (Exception e2) {
            p.a(c, "getProp failed ex: " + e2.getMessage());
        }
        return null;
    }

    private static boolean n() {
        if (Build.VERSION.SDK_INT >= 21) {
            return false;
        }
        String str = Build.DEVICE;
        String b2 = b("persist.radio.modem");
        if ("HM2014812".equals(str) || "HM2014821".equals(str)) {
            return true;
        }
        return ("gucci".equals(str) && "ct".equals(b("persist.sys.modem"))) || "CDMA".equals(b2) || "HM1AC".equals(b2) || "LTE-X5-ALL".equals(b2) || "LTE-CT".equals(b2) || "MI 3C".equals(Build.MODEL);
    }

    private static boolean a(List<String> list) {
        for (String str : list) {
            if (!c(str)) {
                return true;
            }
        }
        return false;
    }

    private static boolean b(List<String> list) {
        for (String str : list) {
            if (!d(str)) {
                return true;
            }
        }
        return false;
    }

    private static boolean c(String str) {
        return str != null && str.length() == 15 && !str.matches("^0*$");
    }

    private static boolean d(String str) {
        return str != null && str.length() == 14 && !str.matches("^0*$");
    }

    @SuppressLint({"MissingPermission"})
    public static String u(Context context) {
        String str = null;
        if (Build.VERSION.SDK_INT < 26) {
            str = Build.SERIAL;
        } else if (u.b(context)) {
            try {
                Method method = Class.forName("android.os.Build").getMethod("getSerial", new Class[0]);
                if (method != null) {
                    str = (String) method.invoke(null, new Object[0]);
                }
            } catch (Throwable th) {
                p.a(c, "querySerial failed ex: " + th.getMessage());
            }
        }
        if (TextUtils.isEmpty(str) || "unknown".equals(str)) {
            return "";
        }
        t = str;
        return str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static class GAIDClient {
        private static final String a = "GAIDClient";

        private GAIDClient() {
        }

        static String a(Context context) {
            if (!c(context)) {
                p.a(a, "Google play service is not available");
                return "";
            }
            AdvertisingConnection advertisingConnection = new AdvertisingConnection(null);
            try {
                try {
                    Intent intent = new Intent("com.google.android.gms.ads.identifier.service.START");
                    intent.setPackage("com.google.android.gms");
                    if (context.bindService(intent, advertisingConnection, 1)) {
                        return new a(advertisingConnection.a()).a();
                    }
                } catch (Exception e) {
                    p.a(a, "Query Google ADID failed ", e);
                }
                context.unbindService(advertisingConnection);
                return "";
            } finally {
                context.unbindService(advertisingConnection);
            }
        }

        static boolean b(Context context) {
            if (!c(context)) {
                p.a(a, "Google play service is not available");
                return false;
            }
            AdvertisingConnection advertisingConnection = new AdvertisingConnection(null);
            try {
                try {
                    Intent intent = new Intent("com.google.android.gms.ads.identifier.service.START");
                    intent.setPackage("com.google.android.gms");
                    if (context.bindService(intent, advertisingConnection, 1)) {
                        return new a(advertisingConnection.a()).a(true);
                    }
                } catch (Exception e) {
                    p.a(a, "Query Google isLimitAdTrackingEnabled failed ", e);
                }
                return false;
            } finally {
                context.unbindService(advertisingConnection);
            }
        }

        private static boolean c(Context context) {
            try {
                context.getPackageManager().getPackageInfo("com.android.vending", 16384);
                return true;
            } catch (PackageManager.NameNotFoundException unused) {
                return false;
            }
        }

        /* loaded from: classes4.dex */
        public static final class AdvertisingConnection implements ServiceConnection {
            private static final int a = 30000;
            private boolean b;
            private IBinder c;

            private AdvertisingConnection() {
                this.b = false;
            }

            /* synthetic */ AdvertisingConnection(h hVar) {
                this();
            }

            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                synchronized (this) {
                    this.c = iBinder;
                    notifyAll();
                }
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName componentName) {
                this.b = true;
                this.c = null;
            }

            public IBinder a() throws InterruptedException {
                IBinder iBinder = this.c;
                if (iBinder != null) {
                    return iBinder;
                }
                if (iBinder == null && !this.b) {
                    synchronized (this) {
                        wait(30000L);
                        if (this.c == null) {
                            throw new InterruptedException("Not connect or connect timeout to google play service");
                        }
                    }
                }
                return this.c;
            }
        }

        /* loaded from: classes4.dex */
        public static final class a implements IInterface {
            private IBinder a;

            public a(IBinder iBinder) {
                this.a = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.a;
            }

            public String a() throws RemoteException {
                if (this.a == null) {
                    return "";
                }
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                    this.a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean a(boolean z) throws RemoteException {
                boolean z2 = false;
                if (this.a == null) {
                    return false;
                }
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                    obtain.writeInt(z ? 1 : 0);
                    this.a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z2 = true;
                    }
                    return z2;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }

    /* loaded from: classes4.dex */
    public static class a {
        private static final String a = "box";
        private static final String b = "tvbox";
        private static final String c = "projector";
        private static final String d = "tv";
        private static final String e = "mi_device_mac";
        private static Signature[] f;
        private static final Signature g = new Signature("3082033b30820223a003020102020900a07a328482f70d2a300d06092a864886f70d01010505003035310b30090603550406130255533113301106035504080c0a43616c69666f726e69613111300f06035504070c084d6f756e7461696e301e170d3133303430313033303831325a170d3430303831373033303831325a3035310b30090603550406130255533113301106035504080c0a43616c69666f726e69613111300f06035504070c084d6f756e7461696e30820120300d06092a864886f70d01010105000382010d00308201080282010100ac678c9234a0226edbeb75a43e8e18f632d8c8a094c087fffbbb0b5e4429d845e36bffbe2d7098e320855258aa777368c18c538f968063d5d61663dc946ab03acbb31d00a27d452e12e6d42865e27d6d0ad2d8b12cf3b3096a7ec66a21db2a6a697857fd4d29fb4cdf294b3371d7601f2e3f190c0164efa543897026c719b334808e4f612fe3a3da589115fc30f9ca89862feefdf31a9164ecb295dcf7767e673be2192dda64f88189fd6e6ebd62e572c7997c2385a0ea9292ec799dee8f87596fc73aa123fb6f577d09ac0c123179c3bdbc978c2fe6194eb9fa4ab3658bfe8b44040bb13fe7809409e622189379fbc63966ab36521793547b01673ecb5f15cf020103a350304e301d0603551d0e0416041447203684e562385ada79108c4c94c5055037592f301f0603551d2304183016801447203684e562385ada79108c4c94c5055037592f300c0603551d13040530030101ff300d06092a864886f70d010105050003820101008d530fe05c6fe694c7559ddb5dd2c556528dd3cad4f7580f439f9a90a4681d37ce246b9a6681bdd5a5437f0b8bba903e39bac309fc0e9ee5553681612e723e9ec4f6abab6b643b33013f09246a9b5db7703b96f838fb27b00612f5fcd431bea32f68350ae51a4a1d012c520c401db7cccc15a7b19c4310b0c3bfc625ce5744744d0b9eeb02b0a4e7d51ed59849ce580b9f7c3062c84b9a0b13cc211e1c916c289820266a610801e3316c915649804571b147beadbf88d3b517ee04121d40630853f2f2a506bb788620de9648faeacff568e5033a666316bc2046526674ed3de25ceefdc4ad3628f1a230fd41bf9ca9f6a078173850dba555768fe1c191483ad9");

        private a() {
        }

        static boolean a(Context context) {
            if (f == null) {
                f = new Signature[]{c(context)};
            }
            Signature[] signatureArr = f;
            return signatureArr[0] != null && signatureArr[0].equals(g);
        }

        private static Signature a(PackageInfo packageInfo) {
            if (packageInfo == null || packageInfo.signatures == null || packageInfo.signatures.length <= 0) {
                return null;
            }
            return packageInfo.signatures[0];
        }

        private static Signature c(Context context) {
            try {
                return a(context.getPackageManager().getPackageInfo("android", 64));
            } catch (Exception unused) {
                return null;
            }
        }

        public static String b(Context context) {
            String str;
            if (Build.VERSION.SDK_INT >= 17) {
                try {
                    String string = Settings.Global.getString(context.getContentResolver(), e);
                    if (!TextUtils.isEmpty(string)) {
                        return string;
                    }
                } catch (Exception unused) {
                }
            }
            boolean z = false;
            try {
                String str2 = Build.PRODUCT;
                String b2 = DeviceUtil.b("ro.product.model");
                z = true;
                if ((!TextUtils.equals(d, a()) || "batman".equals(str2) || "conan".equals(str2)) && !"augustrush".equals(str2) && "casablanca".equals(str2)) {
                }
                if (TextUtils.equals("me2", str2)) {
                    str = DeviceUtil.b("persist.service.bdroid.bdaddr");
                } else if ((TextUtils.equals("transformers", str2) && TextUtils.equals("MiBOX4C", b2)) || TextUtils.equals("dolphin-zorro", str2)) {
                    str = a("/sys/class/net/wlan0/address");
                } else if (z) {
                    str = a("/sys/class/net/eth0/address");
                } else {
                    str = a("ro.boot.btmac");
                }
                return !TextUtils.isEmpty(str) ? str.trim() : "";
            } catch (Exception e2) {
                p.a(DeviceUtil.c, "getMiTvMac exception", e2);
                return "";
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        private static String a(String str) {
            Throwable th;
            BufferedReader bufferedReader;
            String str2;
            BufferedReader bufferedReader2;
            Exception e2;
            BufferedReader bufferedReader3;
            BufferedReader bufferedReader4;
            try {
                str2 = "";
                bufferedReader2 = null;
                bufferedReader4 = null;
                try {
                    bufferedReader3 = new BufferedReader(new InputStreamReader(new FileInputStream(str)), 512);
                } catch (Exception e3) {
                    e2 = e3;
                }
            } catch (Throwable th2) {
                th = th2;
            }
            try {
                String readLine = bufferedReader3.readLine();
                if (readLine != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str2);
                    sb.append(readLine);
                    str2 = sb.toString();
                    bufferedReader4 = sb;
                }
                m.a(bufferedReader3);
                bufferedReader = bufferedReader4;
            } catch (Exception e4) {
                e2 = e4;
                bufferedReader2 = bufferedReader3;
                p.b(DeviceUtil.c, "catEntry exception", e2);
                m.a(bufferedReader2);
                bufferedReader = bufferedReader2;
                return str2;
            } catch (Throwable th3) {
                th = th3;
                bufferedReader = bufferedReader3;
                m.a(bufferedReader);
                throw th;
            }
            return str2;
        }

        private static String a() {
            try {
                Class<?> cls = Class.forName("mitv.common.ConfigurationManager");
                int parseInt = Integer.parseInt(String.valueOf(cls.getMethod("getProductCategory", new Class[0]).invoke(cls.getMethod("getInstance", new Class[0]).invoke(cls, new Object[0]), new Object[0])));
                Class<?> cls2 = Class.forName("mitv.tv.TvContext");
                return parseInt == Integer.parseInt(String.valueOf(a(cls2, "PRODUCT_CATEGORY_MITV"))) ? d : parseInt == Integer.parseInt(String.valueOf(a(cls2, "PRODUCT_CATEGORY_MIBOX"))) ? a : parseInt == Integer.parseInt(String.valueOf(a(cls2, "PRODUCT_CATEGORY_MITVBOX"))) ? b : parseInt == Integer.parseInt(String.valueOf(a(cls2, "PRODUCT_CATEGORY_MIPROJECTOR"))) ? c : "";
            } catch (Throwable th) {
                p.a(DeviceUtil.c, "getMiTvProductCategory exception: " + th.getMessage());
                return "";
            }
        }

        private static <T> T a(Class<?> cls, String str) {
            try {
                Field declaredField = cls.getDeclaredField(str);
                declaredField.setAccessible(true);
                return (T) declaredField.get(null);
            } catch (Exception e2) {
                p.b(DeviceUtil.c, "getStaticVariableValue exception", e2);
                return null;
            }
        }
    }

    private static boolean e(String str) {
        return str != null && str.length() >= 6 && str.length() <= 15 && !str.matches("^0*$");
    }

    private static String[] a(Class<?> cls, Class<?> cls2, TelephonyManager telephonyManager, SubscriptionManager subscriptionManager) {
        String[] strArr = new String[2];
        try {
            if (Build.VERSION.SDK_INT == 21) {
                strArr[0] = (String) cls.getMethod("getSubscriberId", Long.TYPE).invoke(telephonyManager, Long.valueOf(((long[]) cls2.getMethod("getSubId", Integer.TYPE).invoke(subscriptionManager, 0))[0]));
            } else {
                strArr[0] = (String) cls.getMethod("getSubscriberId", Integer.TYPE).invoke(telephonyManager, Integer.valueOf(((int[]) cls2.getMethod("getSubId", Integer.TYPE).invoke(subscriptionManager, 0))[0]));
            }
            if (!e(strArr[0]) && o != null && m != null) {
                strArr[0] = (String) o.invoke(m, 0);
                strArr[1] = (String) o.invoke(m, 1);
            } else if (Build.VERSION.SDK_INT == 21) {
                strArr[1] = (String) cls.getMethod("getSubscriberId", Long.TYPE).invoke(telephonyManager, Long.valueOf(((long[]) cls2.getMethod("getSubId", Integer.TYPE).invoke(subscriptionManager, 1))[0]));
            } else {
                strArr[1] = (String) cls.getMethod("getSubscriberId", Integer.TYPE).invoke(telephonyManager, Integer.valueOf(((int[]) cls2.getMethod("getSubId", Integer.TYPE).invoke(subscriptionManager, 1))[0]));
            }
        } catch (Exception e2) {
            p.a(c, "getImsiFromLToP: " + e2);
        }
        return strArr;
    }

    private static String[] b(Class<?> cls, Class<?> cls2, TelephonyManager telephonyManager, SubscriptionManager subscriptionManager) {
        String[] strArr = new String[2];
        try {
            int[] iArr = (int[]) cls2.getMethod("getSubscriptionIds", Integer.TYPE).invoke(subscriptionManager, 0);
            if (iArr != null) {
                strArr[0] = (String) cls.getMethod("getSubscriberId", Integer.TYPE).invoke(telephonyManager, Integer.valueOf(iArr[0]));
            }
        } catch (Exception e2) {
            p.b(c, "get imsi1 above Android Q exception:" + e2);
        }
        try {
            int[] iArr2 = (int[]) cls2.getMethod("getSubscriptionIds", Integer.TYPE).invoke(subscriptionManager, 1);
            if (iArr2 != null) {
                strArr[1] = (String) cls.getMethod("getSubscriberId", Integer.TYPE).invoke(telephonyManager, Integer.valueOf(iArr2[0]));
            }
        } catch (Exception e3) {
            p.b(c, "get imsi2 above Android Q exception:" + e3);
        }
        return strArr;
    }

    public static String v(Context context) {
        try {
            List<String> t2 = t(context);
            if (t2 == null) {
                return "";
            }
            for (int i2 = 0; i2 < t2.size(); i2++) {
                t2.set(i2, d.h(t2.get(i2)));
            }
            return t2.toString();
        } catch (Throwable th) {
            p.b(p.a(c), "getImeiListMd5 failed!", th);
            return "";
        }
    }

    public static String b() {
        return b("ro.product.name");
    }

    public static String c() {
        return Build.MODEL;
    }

    public static String d() {
        return Build.MANUFACTURER;
    }

    public static String w(Context context) {
        if (!TextUtils.isEmpty(I)) {
            return I;
        }
        if (o()) {
            I = "Pad";
            return I;
        } else if (!F(context)) {
            return AIApiConstants.Phone.NAME;
        } else {
            I = "Tv";
            return I;
        }
    }

    public static String e() {
        try {
            String b2 = b("ro.product.model.real");
            return TextUtils.isEmpty(b2) ? c() : b2;
        } catch (Exception e2) {
            p.b(c, "getModelReal Exception: ", e2);
            return "";
        }
    }

    public static String f() {
        try {
            String a2 = ab.a("ro.product.mod_device", "");
            return TextUtils.isEmpty(a2) ? Build.DEVICE : a2;
        } catch (Exception e2) {
            p.b(c, "getModDevice Exception: ", e2);
            return "";
        }
    }

    public static String g() {
        return ab.a(g, "");
    }

    public static String h() {
        try {
            return ab.a("ro.product.manufacturer", "");
        } catch (Exception e2) {
            p.b(p.a(c), "getProductManufacturer e", e2);
            return "";
        }
    }

    private static boolean o() {
        try {
            Class<?> cls = Class.forName("miui.os.Build");
            if (cls != null) {
                return ((Boolean) cls.getField("IS_TABLET").get(null)).booleanValue();
            }
        } catch (Throwable th) {
            p.b(c, "get miui.os.Build Exception: " + th.getMessage());
        }
        try {
            Class<?> cls2 = Class.forName("miui.util.FeatureParser");
            if (cls2 != null) {
                return ((Boolean) cls2.getMethod("getBoolean", String.class, Boolean.TYPE).invoke(null, "is_pad", false)).booleanValue();
            }
        } catch (Throwable th2) {
            p.b(c, "get miui.util.FeatureParser Exception: " + th2.getMessage());
        }
        return false;
    }

    private static boolean E(Context context) {
        return (context.getResources().getConfiguration().screenLayout & 15) >= 3;
    }

    public static boolean x(Context context) {
        if (H == null) {
            H = Boolean.valueOf(a.a(context));
        }
        return H.booleanValue();
    }

    private static boolean F(Context context) {
        return a.a(context) || (context.getResources().getConfiguration().uiMode & 15) == 4;
    }

    public static String y(Context context) {
        return com.xiaomi.onetrack.util.oaid.a.a().a(context);
    }

    public static long i() {
        long j2;
        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
        if (Build.VERSION.SDK_INT >= 18) {
            j2 = statFs.getBlockSizeLong() * statFs.getBlockCountLong();
        } else {
            j2 = statFs.getBlockSize() * statFs.getBlockCount();
        }
        return j2 / Constants.DEFAULT_SPACE_LIMIT;
    }

    public static String j() {
        long j2;
        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
        if (Build.VERSION.SDK_INT >= 18) {
            j2 = statFs.getBlockSizeLong() * statFs.getBlockCountLong();
        } else {
            j2 = statFs.getBlockSize() * statFs.getBlockCount();
        }
        return String.format("%dGB", Long.valueOf(b(j2)));
    }

    public static long k() {
        long j2;
        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
        if (Build.VERSION.SDK_INT >= 18) {
            j2 = statFs.getBlockSizeLong() * statFs.getAvailableBlocksLong();
        } else {
            j2 = statFs.getBlockSize() * statFs.getAvailableBlocks();
        }
        return a(j2);
    }

    private static long b(long j2) {
        long j3;
        if (j2 >= 8000000000L) {
            j3 = ((long) Math.pow(2.0d, Math.ceil(Math.log(j2 / C.NANOS_PER_SECOND) / Math.log(2.0d)))) * C.NANOS_PER_SECOND;
        } else {
            j3 = ((j2 / C.NANOS_PER_SECOND) + 1) * C.NANOS_PER_SECOND;
        }
        if (j3 < j2) {
            j3 = c(j2);
        }
        return j3 / C.NANOS_PER_SECOND;
    }

    public static String z(Context context) {
        Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= 17) {
            defaultDisplay.getRealSize(point);
        } else {
            defaultDisplay.getSize(point);
        }
        return String.format("%d*%d", Integer.valueOf(point.y), Integer.valueOf(point.x));
    }
}
