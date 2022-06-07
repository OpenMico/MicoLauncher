package com.xiaomi.smarthome.library.commonapi;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothConstants;
import com.xiaomi.smarthome.library.common.network.Network;
import com.xiaomi.smarthome.library.crypto.SHA1Util;
import com.xiaomi.smarthome.library.http.util.HeaderUtil;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.TimeZone;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class SystemApi {
    public static final int NETWORK_2G = 10;
    public static final int NETWORK_3G = 20;
    public static final int NETWORK_4G = 30;
    public static final int NETWORK_MOBILE_3G = 22;
    public static final int NETWORK_MOBILE_UNICOM_2G = 12;
    public static final int NETWORK_NOT_CONNECTED = -1;
    public static final int NETWORK_TELECOM_2G = 11;
    public static final int NETWORK_TELECOM_3G = 21;
    public static final int NETWORK_UNICOM_3G = 23;
    public static final int NETWORK_UNKNOWN = 0;
    public static final int NETWORK_WIFI = 1;
    private static String a;
    private static String b;
    private static int c;
    private static String d;
    private static SystemApi e;
    private static Object f = new Object();
    private static Boolean g = null;
    private static String h = null;

    public String getAppManufacture() {
        return "com.xiaomi.mihome";
    }

    public String getOsName() {
        return "Android";
    }

    public String getPlatform() {
        return "phone";
    }

    private SystemApi() {
    }

    public static SystemApi getInstance() {
        if (e == null) {
            synchronized (f) {
                if (e == null) {
                    e = new SystemApi();
                }
            }
        }
        return e;
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0018, code lost:
        r2 = r2.getClasses();
        r4 = (java.lang.String) r0.pop();
        r5 = r2.length;
        r7 = null;
        r6 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x0025, code lost:
        if (r6 >= r5) goto L_0x0037;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0027, code lost:
        r8 = r2[r6];
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0031, code lost:
        if (r8.getSimpleName().equals(r4) == false) goto L_0x0034;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0033, code lost:
        r7 = r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0034, code lost:
        r6 = r6 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0037, code lost:
        r2 = r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0014, code lost:
        if (r0.isEmpty() != false) goto L_0x0059;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0016, code lost:
        if (r2 == null) goto L_0x0059;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.lang.Class<?> a(java.lang.String r10) throws java.lang.ClassNotFoundException {
        /*
            java.util.Stack r0 = new java.util.Stack
            r0.<init>()
            r1 = 0
            r2 = r1
        L_0x0007:
            if (r10 == 0) goto L_0x0059
            java.lang.Class r2 = java.lang.Class.forName(r10)     // Catch: ClassNotFoundException -> 0x000d
        L_0x000d:
            r3 = 0
            if (r2 == 0) goto L_0x0039
        L_0x0010:
            boolean r4 = r0.isEmpty()
            if (r4 != 0) goto L_0x0059
            if (r2 == 0) goto L_0x0059
            java.lang.Class[] r2 = r2.getClasses()
            java.lang.Object r4 = r0.pop()
            java.lang.String r4 = (java.lang.String) r4
            int r5 = r2.length
            r7 = r1
            r6 = r3
        L_0x0025:
            if (r6 >= r5) goto L_0x0037
            r8 = r2[r6]
            java.lang.String r9 = r8.getSimpleName()
            boolean r9 = r9.equals(r4)
            if (r9 == 0) goto L_0x0034
            r7 = r8
        L_0x0034:
            int r6 = r6 + 1
            goto L_0x0025
        L_0x0037:
            r2 = r7
            goto L_0x0010
        L_0x0039:
            r4 = 46
            int r4 = r10.lastIndexOf(r4)
            if (r4 <= 0) goto L_0x0057
            int r5 = r10.length()
            int r5 = r5 + (-1)
            if (r4 >= r5) goto L_0x0057
            int r5 = r4 + 1
            java.lang.String r5 = r10.substring(r5)
            r0.add(r5)
            java.lang.String r10 = r10.substring(r3, r4)
            goto L_0x0007
        L_0x0057:
            r10 = r1
            goto L_0x0007
        L_0x0059:
            if (r2 == 0) goto L_0x005c
            return r2
        L_0x005c:
            java.lang.ClassNotFoundException r0 = new java.lang.ClassNotFoundException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "failed to guess class: "
            r1.append(r2)
            r1.append(r10)
            java.lang.String r10 = r1.toString()
            r0.<init>(r10)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.smarthome.library.commonapi.SystemApi.a(java.lang.String):java.lang.Class");
    }

    public static String getMiuiVersion() {
        Class<?> cls;
        try {
            cls = a("android.os.SystemProperties");
        } catch (ClassNotFoundException unused) {
            cls = null;
        }
        if (cls == null) {
            return "";
        }
        try {
            Method declaredMethod = cls.getDeclaredMethod(BluetoothConstants.GET, String.class, String.class);
            return declaredMethod != null ? (String) declaredMethod.invoke(null, "ro.miui.ui.version.code", "") : "";
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
            return "";
        } catch (NoSuchMethodException e3) {
            e3.printStackTrace();
            return "";
        } catch (InvocationTargetException e4) {
            e4.printStackTrace();
            return "";
        }
    }

    public static boolean isMiui() {
        if (g == null) {
            Class<?> cls = null;
            try {
                cls = a("miui.os.Build");
            } catch (ClassNotFoundException unused) {
            }
            if (cls != null) {
                g = true;
            } else {
                g = false;
            }
        }
        return g.booleanValue();
    }

    public String getDeviceId(Context context) {
        return getDeviceId(context, false);
    }

    public String getDeviceIdSHA2(Context context) {
        return getDeviceId(context, true);
    }

    public String getDeviceId(Context context, boolean z) {
        if (TextUtils.isEmpty(a)) {
            String str = "";
            try {
                str = ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
            } catch (Exception unused) {
            }
            if (str == null) {
                str = "";
            }
            String str2 = "";
            try {
                str2 = Settings.Secure.getString(context.getContentResolver(), "android_id");
            } catch (Exception unused2) {
            }
            if (str2 == null) {
                str2 = "";
            }
            String str3 = Build.SERIAL;
            a = SHA1Util.getSHA1Digest(str + str2 + str3);
            b = SHA1Util.getSHA2Digest(str + str2 + str3);
        }
        return z ? b : a;
    }

    public String getImei(Context context) {
        String str = "";
        try {
            str = ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
        } catch (Exception unused) {
        }
        if (TextUtils.isEmpty(str)) {
            try {
                str = Settings.Secure.getString(context.getContentResolver(), "android_id");
            } catch (Exception unused2) {
            }
        }
        return TextUtils.isEmpty(str) ? "" : str;
    }

    public String getSDKVersion() {
        return Integer.toString(Build.VERSION.SDK_INT);
    }

    public String getOsVersion() {
        return !TextUtils.isEmpty(Build.VERSION.RELEASE) ? Build.VERSION.RELEASE : "unknown";
    }

    public String getDeviceModel() {
        try {
            HeaderUtil.checkName(Build.MODEL);
            return Build.MODEL;
        } catch (Exception unused) {
            return "unknown";
        }
    }

    public String getDeviceInfo() {
        if (h == null) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("manu", Build.MANUFACTURER);
                jSONObject.put("hard", Build.HARDWARE);
            } catch (Exception unused) {
            }
            h = getDeviceModel() + "|" + jSONObject.toString();
        }
        return h;
    }

    public String getOsIncremental() {
        return Build.VERSION.INCREMENTAL;
    }

    public String getAppVersion(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception e2) {
            e2.printStackTrace();
            return "1.0.0";
        }
    }

    public int getAppVersionCode(Context context) {
        if (c <= 0) {
            try {
                c = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
            } catch (PackageManager.NameNotFoundException e2) {
                e2.printStackTrace();
                c = 0;
            }
        }
        return c;
    }

    public String getAppVersionName(Context context) {
        if (TextUtils.isEmpty(d)) {
            try {
                d = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            } catch (PackageManager.NameNotFoundException e2) {
                e2.printStackTrace();
                d = "";
            }
        }
        return d;
    }

    public float getScreenDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    public int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public String getMac(Context context) {
        try {
            WifiInfo connectionInfo = ((WifiManager) context.getSystemService(Network.NETWORK_TYPE_WIFI)).getConnectionInfo();
            return connectionInfo != null ? connectionInfo.getBSSID() : "";
        } catch (Exception unused) {
            return null;
        }
    }

    public int getNetworkType(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo == null) {
            return -1;
        }
        int type = activeNetworkInfo.getType();
        if (type == 1) {
            return 1;
        }
        if (type != 0) {
            return 0;
        }
        switch (activeNetworkInfo.getSubtype()) {
            case 1:
            case 2:
                return 12;
            case 3:
            case 8:
            case 15:
                return 23;
            case 4:
                return 11;
            case 5:
            case 6:
            case 12:
                return 21;
            case 7:
            case 9:
            case 10:
            case 11:
            case 13:
            case 14:
            default:
                return 0;
        }
    }

    public String getTimeZone() {
        try {
            int rawOffset = TimeZone.getDefault().getRawOffset() / 60000;
            char c2 = '+';
            if (rawOffset < 0) {
                c2 = '-';
                rawOffset = -rawOffset;
            }
            StringBuilder sb = new StringBuilder(9);
            sb.append("GMT");
            sb.append(c2);
            a(sb, 2, rawOffset / 60);
            sb.append(':');
            a(sb, 2, rawOffset % 60);
            return sb.toString();
        } catch (Exception unused) {
            return "";
        }
    }

    private void a(StringBuilder sb, int i, int i2) {
        String num = Integer.toString(i2);
        for (int i3 = 0; i3 < i - num.length(); i3++) {
            sb.append('0');
        }
        sb.append(num);
    }
}
