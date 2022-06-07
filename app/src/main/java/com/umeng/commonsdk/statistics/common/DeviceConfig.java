package com.umeng.commonsdk.statistics.common;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import com.umeng.analytics.pro.ai;
import com.umeng.commonsdk.config.FieldManager;
import com.umeng.commonsdk.framework.UMEnvelopeBuild;
import com.umeng.commonsdk.statistics.AnalyticsConstants;
import com.umeng.commonsdk.statistics.idtracking.i;
import com.umeng.commonsdk.utils.UMUtils;
import com.umeng.commonsdk.utils.b;
import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.idm.api.IDMServer;
import com.xiaomi.smarthome.library.common.network.Network;
import com.xiaomi.smarthome.setting.ServerSetting;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import javax.microedition.khronos.opengles.GL10;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes2.dex */
public class DeviceConfig {
    public static final int DEFAULT_TIMEZONE = 8;
    private static final String KEY_EMUI_VERSION_CODE = "ro.build.hw_emui_api_level";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    protected static final String LOG_TAG = "com.umeng.commonsdk.statistics.common.DeviceConfig";
    public static final String MOBILE_NETWORK = "2G/3G";
    public static final String UNKNOW = "";
    public static final String WIFI = "Wi-Fi";
    private static DeviceTypeEnum deviceTypeEnum = DeviceTypeEnum.DEFAULT;
    private static String sWifiMac = "";
    private static String sImei = "";
    private static String sMeid = "";
    private static String sImsi = "";

    public static String getImei(Context context) {
        TelephonyManager telephonyManager;
        if (!TextUtils.isEmpty(sImei)) {
            return sImei;
        }
        String str = null;
        try {
            if (FieldManager.allow(b.g) && context != null && (telephonyManager = (TelephonyManager) context.getSystemService("phone")) != null && checkPermission(context, "android.permission.READ_PHONE_STATE")) {
                str = telephonyManager.getDeviceId();
            }
        } catch (Exception e) {
            if (AnalyticsConstants.UM_DEBUG) {
                MLog.w("No IMEI.", e);
            }
        }
        sImei = str;
        return str;
    }

    public static String getImeiNew(Context context) {
        TelephonyManager telephonyManager;
        if (!TextUtils.isEmpty(sImei)) {
            return sImei;
        }
        String str = null;
        try {
            if (FieldManager.allow(b.g) && context != null && (telephonyManager = (TelephonyManager) context.getSystemService("phone")) != null && checkPermission(context, "android.permission.READ_PHONE_STATE")) {
                if (Build.VERSION.SDK_INT >= 26) {
                    try {
                        Method method = telephonyManager.getClass().getMethod("getImei", new Class[0]);
                        method.setAccessible(true);
                        str = (String) method.invoke(telephonyManager, new Object[0]);
                    } catch (Exception unused) {
                    }
                    if (TextUtils.isEmpty(str)) {
                        str = telephonyManager.getDeviceId();
                    }
                } else {
                    str = telephonyManager.getDeviceId();
                }
            }
        } catch (Exception e) {
            if (AnalyticsConstants.UM_DEBUG) {
                MLog.w("No IMEI.", e);
            }
        }
        sImei = str;
        return str;
    }

    public static String getAndroidId(Context context) {
        if (FieldManager.allow(b.i) && context != null) {
            try {
                return Settings.Secure.getString(context.getContentResolver(), "android_id");
            } catch (Exception unused) {
                if (AnalyticsConstants.UM_DEBUG) {
                    MLog.w("can't read android id");
                }
            }
        }
        return null;
    }

    public static String getSerial() {
        if (!FieldManager.allow(b.j) || Build.VERSION.SDK_INT < 9) {
            return null;
        }
        if (Build.VERSION.SDK_INT < 26) {
            return Build.SERIAL;
        }
        try {
            Class<?> cls = Class.forName("android.os.Build");
            return (String) cls.getMethod("getSerial", new Class[0]).invoke(cls, new Object[0]);
        } catch (Throwable unused) {
            return null;
        }
    }

    public static String getAppVersionCode(Context context) {
        return UMUtils.getAppVersionCode(context);
    }

    public static String getAppVersionName(Context context) {
        return UMUtils.getAppVersionName(context);
    }

    public static boolean checkPermission(Context context, String str) {
        if (context == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT < 23) {
            return context.getPackageManager().checkPermission(str, context.getPackageName()) == 0;
        }
        try {
            return ((Integer) Class.forName("android.content.Context").getMethod("checkSelfPermission", String.class).invoke(context, str)).intValue() == 0;
        } catch (Throwable unused) {
            return false;
        }
    }

    public static String[] getGPU(GL10 gl10) {
        try {
            return new String[]{gl10.glGetString(7936), gl10.glGetString(7937)};
        } catch (Throwable th) {
            if (AnalyticsConstants.UM_DEBUG) {
                MLog.e(LOG_TAG, "Could not read gpu infor:", th);
            }
            return new String[0];
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x0013 A[Catch: Throwable -> 0x0078, TryCatch #0 {Throwable -> 0x0078, blocks: (B:3:0x0001, B:5:0x0009, B:6:0x000d, B:8:0x0013, B:10:0x0025, B:12:0x0031, B:14:0x0037, B:17:0x003b, B:20:0x0046, B:21:0x005c, B:23:0x0062, B:24:0x006a), top: B:29:0x0001 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.lang.String getMacByJavaAPI() {
        /*
            r0 = 0
            java.lang.String r1 = "header_device_id_mac"
            boolean r1 = com.umeng.commonsdk.config.FieldManager.allow(r1)     // Catch: Throwable -> 0x0078
            if (r1 == 0) goto L_0x0078
            java.util.Enumeration r1 = java.net.NetworkInterface.getNetworkInterfaces()     // Catch: Throwable -> 0x0078
        L_0x000d:
            boolean r2 = r1.hasMoreElements()     // Catch: Throwable -> 0x0078
            if (r2 == 0) goto L_0x0078
            java.lang.Object r2 = r1.nextElement()     // Catch: Throwable -> 0x0078
            java.net.NetworkInterface r2 = (java.net.NetworkInterface) r2     // Catch: Throwable -> 0x0078
            java.lang.String r3 = "wlan0"
            java.lang.String r4 = r2.getName()     // Catch: Throwable -> 0x0078
            boolean r3 = r3.equals(r4)     // Catch: Throwable -> 0x0078
            if (r3 != 0) goto L_0x0031
            java.lang.String r3 = "eth0"
            java.lang.String r4 = r2.getName()     // Catch: Throwable -> 0x0078
            boolean r3 = r3.equals(r4)     // Catch: Throwable -> 0x0078
            if (r3 == 0) goto L_0x000d
        L_0x0031:
            byte[] r1 = r2.getHardwareAddress()     // Catch: Throwable -> 0x0078
            if (r1 == 0) goto L_0x0077
            int r2 = r1.length     // Catch: Throwable -> 0x0078
            if (r2 != 0) goto L_0x003b
            goto L_0x0077
        L_0x003b:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: Throwable -> 0x0078
            r2.<init>()     // Catch: Throwable -> 0x0078
            int r3 = r1.length     // Catch: Throwable -> 0x0078
            r4 = 0
            r5 = r4
        L_0x0043:
            r6 = 1
            if (r5 >= r3) goto L_0x005c
            byte r7 = r1[r5]     // Catch: Throwable -> 0x0078
            java.lang.String r8 = "%02X:"
            java.lang.Object[] r6 = new java.lang.Object[r6]     // Catch: Throwable -> 0x0078
            java.lang.Byte r7 = java.lang.Byte.valueOf(r7)     // Catch: Throwable -> 0x0078
            r6[r4] = r7     // Catch: Throwable -> 0x0078
            java.lang.String r6 = java.lang.String.format(r8, r6)     // Catch: Throwable -> 0x0078
            r2.append(r6)     // Catch: Throwable -> 0x0078
            int r5 = r5 + 1
            goto L_0x0043
        L_0x005c:
            int r1 = r2.length()     // Catch: Throwable -> 0x0078
            if (r1 <= 0) goto L_0x006a
            int r1 = r2.length()     // Catch: Throwable -> 0x0078
            int r1 = r1 - r6
            r2.deleteCharAt(r1)     // Catch: Throwable -> 0x0078
        L_0x006a:
            java.lang.String r1 = r2.toString()     // Catch: Throwable -> 0x0078
            java.util.Locale r2 = java.util.Locale.getDefault()     // Catch: Throwable -> 0x0078
            java.lang.String r0 = r1.toLowerCase(r2)     // Catch: Throwable -> 0x0078
            return r0
        L_0x0077:
            return r0
        L_0x0078:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.commonsdk.statistics.common.DeviceConfig.getMacByJavaAPI():java.lang.String");
    }

    private static String getMacShell() {
        try {
            if (!FieldManager.allow(b.h)) {
                return null;
            }
            for (String str : new String[]{"/sys/class/net/wlan0/address", "/sys/class/net/eth0/address", "/sys/devices/virtual/net/wlan0/address"}) {
                String reaMac = reaMac(str);
                if (reaMac != null) {
                    return reaMac;
                }
            }
            return null;
        } catch (Throwable unused) {
            return null;
        }
    }

    private static String reaMac(String str) {
        BufferedReader bufferedReader;
        Throwable th;
        String str2 = null;
        try {
            FileReader fileReader = new FileReader(str);
            try {
                bufferedReader = new BufferedReader(fileReader, 1024);
                try {
                    str2 = bufferedReader.readLine();
                    try {
                        fileReader.close();
                    } catch (Throwable unused) {
                    }
                    bufferedReader.close();
                } catch (Throwable th2) {
                    th = th2;
                    try {
                        fileReader.close();
                    } catch (Throwable unused2) {
                    }
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (Throwable unused3) {
                        }
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                bufferedReader = null;
            }
        } catch (Throwable unused4) {
        }
        return str2;
    }

    public static String getCPU() {
        String str = null;
        try {
            FileReader fileReader = new FileReader("/proc/cpuinfo");
            BufferedReader bufferedReader = new BufferedReader(fileReader, 1024);
            str = bufferedReader.readLine();
            bufferedReader.close();
            fileReader.close();
        } catch (FileNotFoundException e) {
            MLog.e(LOG_TAG, "Could not open file /proc/cpuinfo", e);
        }
        return str != null ? str.substring(str.indexOf(58) + 1).trim() : "";
    }

    public static String getDeviceId(Context context) {
        if (AnalyticsConstants.getDeviceType() == 2) {
            return getDeviceIdForBox(context);
        }
        return getDeviceIdForGeneral(context);
    }

    public static String getDeviceIdType() {
        return deviceTypeEnum.getDeviceIdType();
    }

    public static String getDeviceIdUmengMD5(Context context) {
        return HelperUtils.getUmengMD5(getDeviceId(context));
    }

    public static String getMCCMNC(Context context) {
        if (context == null) {
            return null;
        }
        if (getImsi(context) == null) {
            return null;
        }
        int i = context.getResources().getConfiguration().mcc;
        int i2 = context.getResources().getConfiguration().mnc;
        if (i != 0) {
            String valueOf = String.valueOf(i2);
            if (i2 < 10) {
                valueOf = String.format("%02d", Integer.valueOf(i2));
            }
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(String.valueOf(i));
            stringBuffer.append(valueOf);
            return stringBuffer.toString();
        }
        return null;
    }

    public static String getImsi(Context context) {
        if (!TextUtils.isEmpty(sImsi)) {
            return sImsi;
        }
        String str = null;
        if (context == null) {
            return null;
        }
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        if (FieldManager.allow(b.ai)) {
            try {
                if (checkPermission(context, "android.permission.READ_PHONE_STATE") && telephonyManager != null) {
                    str = telephonyManager.getSubscriberId();
                }
            } catch (Throwable unused) {
            }
        }
        sImsi = str;
        return sImsi;
    }

    public static String getMeid(Context context) {
        TelephonyManager telephonyManager;
        String str = null;
        if (context == null || (telephonyManager = (TelephonyManager) context.getSystemService("phone")) == null) {
            return null;
        }
        if (FieldManager.allow(b.aj)) {
            try {
                if (checkPermission(context, "android.permission.READ_PHONE_STATE") && telephonyManager != null) {
                    if (Build.VERSION.SDK_INT < 26) {
                        str = getIMEI(context);
                    } else {
                        str = meid(context);
                        if (TextUtils.isEmpty(str)) {
                            str = getIMEI(context);
                        }
                    }
                }
            } catch (Throwable unused) {
            }
        }
        return str;
    }

    private static String meid(Context context) {
        if (TextUtils.isEmpty(sMeid)) {
            return sMeid;
        }
        String str = null;
        if (context == null) {
            return null;
        }
        try {
            Object invoke = Class.forName("android.telephony.TelephonyManager").getMethod("getMeid", new Class[0]).invoke(null, new Object[0]);
            if (invoke != null && (invoke instanceof String)) {
                str = (String) invoke;
            }
        } catch (Throwable th) {
            ULog.e("meid:" + th.getMessage());
        }
        sMeid = str;
        return sMeid;
    }

    public static String getSimICCID(Context context) {
        TelephonyManager telephonyManager;
        if (!FieldManager.allow(b.am) || context == null) {
            return null;
        }
        try {
            if (!UMUtils.checkPermission(context, "android.permission.READ_PHONE_STATE") || (telephonyManager = (TelephonyManager) context.getSystemService("phone")) == null) {
                return null;
            }
            return telephonyManager.getSimSerialNumber();
        } catch (Throwable unused) {
            return null;
        }
    }

    public static String getSecondSimIMEi(Context context) {
        if (context == null || !FieldManager.allow(b.al) || Build.VERSION.SDK_INT < 23 || !UMUtils.checkPermission(context, "android.permission.READ_PHONE_STATE")) {
            return null;
        }
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (telephonyManager == null) {
                return null;
            }
            Class<?> cls = telephonyManager.getClass();
            if (((Integer) cls.getMethod("getPhoneCount", new Class[0]).invoke(telephonyManager, new Object[0])).intValue() == 2) {
                return (String) cls.getMethod("getDeviceId", Integer.TYPE).invoke(telephonyManager, 2);
            }
            return null;
        } catch (Throwable unused) {
            return null;
        }
    }

    public static String getRegisteredOperator(Context context) {
        if (context == null) {
            return null;
        }
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (!checkPermission(context, "android.permission.READ_PHONE_STATE") || telephonyManager == null) {
                return null;
            }
            return telephonyManager.getNetworkOperator();
        } catch (Throwable unused) {
            return null;
        }
    }

    public static String getNetworkOperatorName(Context context) {
        if (context == null) {
            return "";
        }
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            return (!checkPermission(context, "android.permission.READ_PHONE_STATE") || telephonyManager == null) ? "" : telephonyManager.getNetworkOperatorName();
        } catch (Throwable unused) {
            return "";
        }
    }

    public static String getDisplayResolution(Context context) {
        if (context == null) {
            return "";
        }
        try {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            WindowManager windowManager = (WindowManager) context.getSystemService("window");
            if (windowManager == null) {
                return "";
            }
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            int i = displayMetrics.widthPixels;
            int i2 = displayMetrics.heightPixels;
            return String.valueOf(i2) + "*" + String.valueOf(i);
        } catch (Throwable unused) {
            return "";
        }
    }

    public static String[] getNetworkAccessMode(Context context) {
        String[] strArr = {"", ""};
        if (context == null) {
            return strArr;
        }
        if (!checkPermission(context, "android.permission.ACCESS_NETWORK_STATE")) {
            strArr[0] = "";
            return strArr;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null) {
            strArr[0] = "";
            return strArr;
        }
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(1);
        if (networkInfo == null || networkInfo.getState() != NetworkInfo.State.CONNECTED) {
            NetworkInfo networkInfo2 = connectivityManager.getNetworkInfo(0);
            if (networkInfo2 != null && networkInfo2.getState() == NetworkInfo.State.CONNECTED) {
                strArr[0] = "2G/3G";
                strArr[1] = networkInfo2.getSubtypeName();
                return strArr;
            }
            return strArr;
        }
        strArr[0] = "Wi-Fi";
        return strArr;
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0070 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0071  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0014 A[Catch: SocketException -> 0x006c, TryCatch #1 {SocketException -> 0x006c, blocks: (B:5:0x000e, B:7:0x0014, B:8:0x0026, B:10:0x002c, B:12:0x0038, B:14:0x003c, B:17:0x004a, B:18:0x004e, B:20:0x0054, B:22:0x0058, B:25:0x0066), top: B:35:0x000e }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getIPAddress(android.content.Context r7) {
        /*
            r7 = 0
            java.util.Enumeration r0 = java.net.NetworkInterface.getNetworkInterfaces()     // Catch: SocketException -> 0x006b
            java.util.ArrayList r0 = java.util.Collections.list(r0)     // Catch: SocketException -> 0x006b
            java.util.Iterator r0 = r0.iterator()     // Catch: SocketException -> 0x006b
            r1 = r7
        L_0x000e:
            boolean r2 = r0.hasNext()     // Catch: SocketException -> 0x006c
            if (r2 == 0) goto L_0x006e
            java.lang.Object r2 = r0.next()     // Catch: SocketException -> 0x006c
            java.net.NetworkInterface r2 = (java.net.NetworkInterface) r2     // Catch: SocketException -> 0x006c
            java.util.Enumeration r3 = r2.getInetAddresses()     // Catch: SocketException -> 0x006c
            java.util.ArrayList r3 = java.util.Collections.list(r3)     // Catch: SocketException -> 0x006c
            java.util.Iterator r3 = r3.iterator()     // Catch: SocketException -> 0x006c
        L_0x0026:
            boolean r4 = r3.hasNext()     // Catch: SocketException -> 0x006c
            if (r4 == 0) goto L_0x000e
            java.lang.Object r4 = r3.next()     // Catch: SocketException -> 0x006c
            java.net.InetAddress r4 = (java.net.InetAddress) r4     // Catch: SocketException -> 0x006c
            boolean r5 = r4.isLoopbackAddress()     // Catch: SocketException -> 0x006c
            if (r5 != 0) goto L_0x004e
            boolean r5 = r4 instanceof java.net.Inet4Address     // Catch: SocketException -> 0x006c
            if (r5 == 0) goto L_0x004e
            java.lang.String r5 = r2.getDisplayName()     // Catch: SocketException -> 0x006c
            java.lang.String r6 = "dummy"
            boolean r5 = r5.contains(r6)     // Catch: SocketException -> 0x006c
            if (r5 != 0) goto L_0x004e
            if (r7 != 0) goto L_0x004e
            java.lang.String r7 = r4.getHostAddress()     // Catch: SocketException -> 0x006c
        L_0x004e:
            boolean r5 = r4.isLoopbackAddress()     // Catch: SocketException -> 0x006c
            if (r5 != 0) goto L_0x0026
            boolean r5 = r4 instanceof java.net.Inet6Address     // Catch: SocketException -> 0x006c
            if (r5 == 0) goto L_0x0026
            java.lang.String r5 = r2.getDisplayName()     // Catch: SocketException -> 0x006c
            java.lang.String r6 = "dummy"
            boolean r5 = r5.contains(r6)     // Catch: SocketException -> 0x006c
            if (r5 != 0) goto L_0x0026
            if (r1 != 0) goto L_0x0026
            java.lang.String r1 = r4.getHostAddress()     // Catch: SocketException -> 0x006c
            goto L_0x0026
        L_0x006b:
            r1 = r7
        L_0x006c:
            java.lang.String r7 = "SocketException"
        L_0x006e:
            if (r7 == 0) goto L_0x0071
            return r7
        L_0x0071:
            if (r1 != 0) goto L_0x0075
            java.lang.String r1 = "null"
        L_0x0075:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.commonsdk.statistics.common.DeviceConfig.getIPAddress(android.content.Context):java.lang.String");
    }

    public static int getNetworkType(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (checkPermission(context, "android.permission.READ_PHONE_STATE")) {
                return telephonyManager.getNetworkType();
            }
            return 0;
        } catch (Exception unused) {
            return -100;
        }
    }

    public static boolean isWiFiAvailable(Context context) {
        if (context == null) {
            return false;
        }
        return "Wi-Fi".equals(getNetworkAccessMode(context)[0]);
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager;
        NetworkInfo activeNetworkInfo;
        if (context == null) {
            return false;
        }
        try {
            if (!(!checkPermission(context, "android.permission.ACCESS_NETWORK_STATE") || (connectivityManager = (ConnectivityManager) context.getSystemService("connectivity")) == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null)) {
                return activeNetworkInfo.isConnectedOrConnecting();
            }
        } catch (Throwable unused) {
        }
        return false;
    }

    public static int getTimeZone(Context context) {
        if (context == null) {
            return 8;
        }
        try {
            Calendar instance = Calendar.getInstance(getLocale(context));
            if (instance != null) {
                return instance.getTimeZone().getRawOffset() / 3600000;
            }
        } catch (Throwable th) {
            MLog.i(LOG_TAG, "error in getTimeZone", th);
        }
        return 8;
    }

    public static boolean isChineseAera(Context context) {
        String imprintProperty;
        if (context == null) {
            return false;
        }
        try {
            imprintProperty = UMEnvelopeBuild.imprintProperty(context, ai.O, "");
        } catch (Throwable unused) {
        }
        if (!TextUtils.isEmpty(imprintProperty)) {
            return imprintProperty.equals(ServerSetting.SERVER_CN);
        }
        if (getImsi(context) == null) {
            String str = getLocaleInfo(context)[0];
            if (!TextUtils.isEmpty(str) && str.equalsIgnoreCase(ServerSetting.SERVER_CN)) {
                return true;
            }
        } else {
            int i = context.getResources().getConfiguration().mcc;
            if (!(i == 460 || i == 461)) {
                if (i == 0) {
                    String str2 = getLocaleInfo(context)[0];
                    if (!TextUtils.isEmpty(str2) && str2.equalsIgnoreCase(ServerSetting.SERVER_CN)) {
                        return true;
                    }
                }
            }
            return true;
        }
        return false;
    }

    public static String[] getLocaleInfo(Context context) {
        String[] strArr = {"Unknown", "Unknown"};
        if (context == null) {
            return strArr;
        }
        try {
            Locale locale = getLocale(context);
            if (locale != null) {
                strArr[0] = locale.getCountry();
                strArr[1] = locale.getLanguage();
            }
            if (TextUtils.isEmpty(strArr[0])) {
                strArr[0] = "Unknown";
            }
            if (TextUtils.isEmpty(strArr[1])) {
                strArr[1] = "Unknown";
            }
            return strArr;
        } catch (Throwable th) {
            MLog.e(LOG_TAG, "error in getLocaleInfo", th);
            return strArr;
        }
    }

    private static Locale getLocale(Context context) {
        Locale locale;
        if (context == null) {
            return Locale.getDefault();
        }
        try {
            Configuration configuration = new Configuration();
            configuration.setToDefaults();
            Settings.System.getConfiguration(context.getContentResolver(), configuration);
            locale = configuration.locale;
        } catch (Throwable unused) {
            MLog.e(LOG_TAG, "fail to read user config locale");
            locale = null;
        }
        return locale == null ? Locale.getDefault() : locale;
    }

    public static String getMac(Context context) {
        if (!TextUtils.isEmpty(sWifiMac)) {
            return sWifiMac;
        }
        String str = "";
        if (FieldManager.allow(b.h)) {
            if (context == null) {
                return str;
            }
            if (Build.VERSION.SDK_INT < 23) {
                str = getMacBySystemInterface(context);
            } else if (Build.VERSION.SDK_INT == 23) {
                str = getMacByJavaAPI();
                if (TextUtils.isEmpty(str)) {
                    if (AnalyticsConstants.CHECK_DEVICE) {
                        str = getMacShell();
                    } else {
                        str = getMacBySystemInterface(context);
                    }
                }
            } else {
                str = getMacByJavaAPI();
                if (TextUtils.isEmpty(str)) {
                    str = getMacBySystemInterface(context);
                }
            }
        }
        sWifiMac = str;
        return sWifiMac;
    }

    private static String getMacBySystemInterface(Context context) {
        if (context == null) {
            return "";
        }
        try {
            if (!FieldManager.allow(b.h)) {
                return "";
            }
            WifiManager wifiManager = (WifiManager) context.getSystemService(Network.NETWORK_TYPE_WIFI);
            if (checkPermission(context, "android.permission.ACCESS_WIFI_STATE")) {
                return wifiManager != null ? wifiManager.getConnectionInfo().getMacAddress() : "";
            }
            if (!AnalyticsConstants.UM_DEBUG) {
                return "";
            }
            MLog.w(LOG_TAG, "Could not get mac address.[no permission android.permission.ACCESS_WIFI_STATE");
            return "";
        } catch (Throwable th) {
            if (!AnalyticsConstants.UM_DEBUG) {
                return "";
            }
            String str = LOG_TAG;
            MLog.w(str, "Could not get mac address." + th.toString());
            return "";
        }
    }

    public static int[] getResolutionArray(Context context) {
        int i;
        int i2;
        Method method;
        if (context == null) {
            return null;
        }
        int[] iArr = new int[2];
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        if (windowManager != null) {
            if (Build.VERSION.SDK_INT >= 17) {
                Display defaultDisplay = windowManager.getDefaultDisplay();
                DisplayMetrics displayMetrics = new DisplayMetrics();
                try {
                    Class<?> cls = Class.forName("android.view.Display");
                    if (!(cls == null || (method = cls.getMethod("getRealMetrics", DisplayMetrics.class)) == null)) {
                        method.invoke(defaultDisplay, displayMetrics);
                        int i3 = displayMetrics.widthPixels;
                        int i4 = displayMetrics.heightPixels;
                        if (i3 > i4) {
                            iArr[0] = i4;
                            iArr[1] = i3;
                        } else {
                            iArr[0] = i3;
                            iArr[1] = i4;
                        }
                        iArr[0] = displayMetrics.widthPixels;
                        iArr[1] = displayMetrics.heightPixels;
                        return iArr;
                    }
                } catch (Throwable unused) {
                    return null;
                }
            } else {
                try {
                    DisplayMetrics displayMetrics2 = new DisplayMetrics();
                    windowManager.getDefaultDisplay().getMetrics(displayMetrics2);
                    if ((context.getApplicationInfo().flags & 8192) == 0) {
                        i = reflectMetrics(displayMetrics2, "noncompatWidthPixels");
                        i2 = reflectMetrics(displayMetrics2, "noncompatHeightPixels");
                    } else {
                        i = -1;
                        i2 = -1;
                    }
                    if (i == -1 || i2 == -1) {
                        i = displayMetrics2.widthPixels;
                        i2 = displayMetrics2.heightPixels;
                    }
                    if (i > i2) {
                        iArr[0] = i2;
                        iArr[1] = i;
                    } else {
                        iArr[0] = i;
                        iArr[1] = i2;
                    }
                    return iArr;
                } catch (Throwable unused2) {
                }
            }
        }
        return null;
    }

    private static int reflectMetrics(Object obj, String str) {
        try {
            Field declaredField = DisplayMetrics.class.getDeclaredField(str);
            declaredField.setAccessible(true);
            return declaredField.getInt(obj);
        } catch (Throwable unused) {
            return -1;
        }
    }

    public static String getPackageName(Context context) {
        if (context == null) {
            return null;
        }
        return context.getPackageName();
    }

    public static String getAppSHA1Key(Context context) {
        try {
            return byte2HexFormatted(MessageDigest.getInstance("SHA1").digest(((X509Certificate) CertificateFactory.getInstance("X509").generateCertificate(new ByteArrayInputStream(context.getPackageManager().getPackageInfo(getPackageName(context), 64).signatures[0].toByteArray()))).getEncoded()));
        } catch (Exception unused) {
            return null;
        }
    }

    public static String getAppHashKey(Context context) {
        try {
            Signature[] signatureArr = context.getPackageManager().getPackageInfo(getPackageName(context), 64).signatures;
            if (signatureArr.length <= 0) {
                return null;
            }
            Signature signature = signatureArr[0];
            MessageDigest instance = MessageDigest.getInstance("SHA");
            instance.update(signature.toByteArray());
            return Base64.encodeToString(instance.digest(), 0).trim();
        } catch (Throwable unused) {
            return null;
        }
    }

    public static String getAppMD5Signature(Context context) {
        if (context == null) {
            return null;
        }
        try {
            return byte2HexFormatted(MessageDigest.getInstance("MD5").digest(((X509Certificate) CertificateFactory.getInstance("X509").generateCertificate(new ByteArrayInputStream(context.getPackageManager().getPackageInfo(getPackageName(context), 64).signatures[0].toByteArray()))).getEncoded()));
        } catch (Throwable unused) {
            return null;
        }
    }

    private static String byte2HexFormatted(byte[] bArr) {
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (int i = 0; i < bArr.length; i++) {
            String hexString = Integer.toHexString(bArr[i]);
            int length = hexString.length();
            if (length == 1) {
                hexString = "0" + hexString;
            }
            if (length > 2) {
                hexString = hexString.substring(length - 2, length);
            }
            sb.append(hexString.toUpperCase(Locale.getDefault()));
            if (i < bArr.length - 1) {
                sb.append(':');
            }
        }
        return sb.toString();
    }

    public static String getApplicationLable(Context context) {
        return context == null ? "" : context.getPackageManager().getApplicationLabel(context.getApplicationInfo()).toString();
    }

    public static String getAppName(Context context) {
        if (context == null) {
            return null;
        }
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).applicationInfo.loadLabel(context.getPackageManager()).toString();
        } catch (Throwable th) {
            if (!AnalyticsConstants.UM_DEBUG) {
                return null;
            }
            MLog.i(LOG_TAG, th);
            return null;
        }
    }

    public static String getDeviceIdForGeneral(Context context) {
        if (context == null) {
            return "";
        }
        try {
            if (Build.VERSION.SDK_INT < 23) {
                String imei = getIMEI(context);
                deviceTypeEnum = DeviceTypeEnum.IMEI;
                if (!TextUtils.isEmpty(imei)) {
                    return imei;
                }
                if (AnalyticsConstants.UM_DEBUG) {
                    MLog.w(LOG_TAG, "No IMEI.");
                }
                String macBySystemInterface = getMacBySystemInterface(context);
                deviceTypeEnum = DeviceTypeEnum.MAC;
                if (!TextUtils.isEmpty(macBySystemInterface)) {
                    return macBySystemInterface;
                }
                if (FieldManager.allow(b.i)) {
                    macBySystemInterface = Settings.Secure.getString(context.getContentResolver(), "android_id");
                    deviceTypeEnum = DeviceTypeEnum.ANDROIDID;
                    if (AnalyticsConstants.UM_DEBUG) {
                        String str = LOG_TAG;
                        MLog.i(str, "getDeviceId, ANDROID_ID: " + macBySystemInterface);
                    }
                }
                if (!TextUtils.isEmpty(macBySystemInterface)) {
                    return macBySystemInterface;
                }
                String serialNo = getSerialNo();
                deviceTypeEnum = DeviceTypeEnum.SERIALNO;
                return serialNo;
            } else if (Build.VERSION.SDK_INT == 23) {
                String imei2 = getIMEI(context);
                deviceTypeEnum = DeviceTypeEnum.IMEI;
                if (!TextUtils.isEmpty(imei2)) {
                    return imei2;
                }
                String macByJavaAPI = getMacByJavaAPI();
                deviceTypeEnum = DeviceTypeEnum.MAC;
                if (TextUtils.isEmpty(macByJavaAPI)) {
                    if (AnalyticsConstants.CHECK_DEVICE) {
                        macByJavaAPI = getMacShell();
                        deviceTypeEnum = DeviceTypeEnum.MAC;
                    } else {
                        macByJavaAPI = getMacBySystemInterface(context);
                        deviceTypeEnum = DeviceTypeEnum.MAC;
                    }
                }
                if (AnalyticsConstants.UM_DEBUG) {
                    String str2 = LOG_TAG;
                    MLog.i(str2, "getDeviceId, MAC: " + macByJavaAPI);
                }
                if (!TextUtils.isEmpty(macByJavaAPI)) {
                    return macByJavaAPI;
                }
                if (FieldManager.allow(b.i)) {
                    macByJavaAPI = Settings.Secure.getString(context.getContentResolver(), "android_id");
                    deviceTypeEnum = DeviceTypeEnum.ANDROIDID;
                    if (AnalyticsConstants.UM_DEBUG) {
                        String str3 = LOG_TAG;
                        MLog.i(str3, "getDeviceId, ANDROID_ID: " + macByJavaAPI);
                    }
                }
                if (!TextUtils.isEmpty(macByJavaAPI)) {
                    return macByJavaAPI;
                }
                String serialNo2 = getSerialNo();
                deviceTypeEnum = DeviceTypeEnum.SERIALNO;
                return serialNo2;
            } else if (Build.VERSION.SDK_INT >= 29) {
                String oaid = getOaid(context);
                deviceTypeEnum = DeviceTypeEnum.OAID;
                if (!TextUtils.isEmpty(oaid)) {
                    return oaid;
                }
                String idfa = getIdfa(context);
                deviceTypeEnum = DeviceTypeEnum.IDFA;
                if (!TextUtils.isEmpty(idfa)) {
                    return idfa;
                }
                String androidId = getAndroidId(context);
                deviceTypeEnum = DeviceTypeEnum.ANDROIDID;
                if (!TextUtils.isEmpty(androidId)) {
                    return androidId;
                }
                String serialNo3 = getSerialNo();
                deviceTypeEnum = DeviceTypeEnum.SERIALNO;
                if (!TextUtils.isEmpty(serialNo3)) {
                    return serialNo3;
                }
                String macByJavaAPI2 = getMacByJavaAPI();
                deviceTypeEnum = DeviceTypeEnum.MAC;
                if (!TextUtils.isEmpty(macByJavaAPI2)) {
                    return macByJavaAPI2;
                }
                String macBySystemInterface2 = getMacBySystemInterface(context);
                deviceTypeEnum = DeviceTypeEnum.MAC;
                return macBySystemInterface2;
            } else {
                String imei3 = getIMEI(context);
                deviceTypeEnum = DeviceTypeEnum.IMEI;
                if (!TextUtils.isEmpty(imei3)) {
                    return imei3;
                }
                String serialNo4 = getSerialNo();
                deviceTypeEnum = DeviceTypeEnum.SERIALNO;
                if (!TextUtils.isEmpty(serialNo4)) {
                    return serialNo4;
                }
                if (FieldManager.allow(b.i)) {
                    serialNo4 = Settings.Secure.getString(context.getContentResolver(), "android_id");
                    deviceTypeEnum = DeviceTypeEnum.ANDROIDID;
                    if (AnalyticsConstants.UM_DEBUG) {
                        String str4 = LOG_TAG;
                        MLog.i(str4, "getDeviceId, ANDROID_ID: " + serialNo4);
                    }
                }
                if (!TextUtils.isEmpty(serialNo4)) {
                    return serialNo4;
                }
                String macByJavaAPI3 = getMacByJavaAPI();
                deviceTypeEnum = DeviceTypeEnum.MAC;
                if (!TextUtils.isEmpty(macByJavaAPI3)) {
                    return macByJavaAPI3;
                }
                String macBySystemInterface3 = getMacBySystemInterface(context);
                deviceTypeEnum = DeviceTypeEnum.MAC;
                if (!AnalyticsConstants.UM_DEBUG) {
                    return macBySystemInterface3;
                }
                String str5 = LOG_TAG;
                MLog.i(str5, "getDeviceId, MAC: " + macBySystemInterface3);
                return macBySystemInterface3;
            }
        } catch (Throwable unused) {
            return "";
        }
    }

    public static String getDeviceIdForBox(Context context) {
        String str = "";
        if (context == null) {
            return str;
        }
        try {
            if (Build.VERSION.SDK_INT < 23) {
                if (FieldManager.allow(b.i)) {
                    str = Settings.Secure.getString(context.getContentResolver(), "android_id");
                    deviceTypeEnum = DeviceTypeEnum.ANDROIDID;
                    if (AnalyticsConstants.UM_DEBUG) {
                        String str2 = LOG_TAG;
                        MLog.i(str2, "getDeviceId, ANDROID_ID: " + str);
                    }
                }
                if (!TextUtils.isEmpty(str)) {
                    return str;
                }
                String macBySystemInterface = getMacBySystemInterface(context);
                deviceTypeEnum = DeviceTypeEnum.MAC;
                if (AnalyticsConstants.UM_DEBUG) {
                    String str3 = LOG_TAG;
                    MLog.i(str3, "getDeviceId, MAC: " + macBySystemInterface);
                }
                if (!TextUtils.isEmpty(macBySystemInterface)) {
                    return macBySystemInterface;
                }
                String serialNo = getSerialNo();
                deviceTypeEnum = DeviceTypeEnum.SERIALNO;
                if (!TextUtils.isEmpty(serialNo)) {
                    return serialNo;
                }
                String imei = getIMEI(context);
                deviceTypeEnum = DeviceTypeEnum.IMEI;
                return imei;
            } else if (Build.VERSION.SDK_INT == 23) {
                if (FieldManager.allow(b.i)) {
                    str = Settings.Secure.getString(context.getContentResolver(), "android_id");
                    deviceTypeEnum = DeviceTypeEnum.ANDROIDID;
                    if (AnalyticsConstants.UM_DEBUG) {
                        String str4 = LOG_TAG;
                        MLog.i(str4, "getDeviceId, ANDROID_ID: " + str);
                    }
                }
                if (!TextUtils.isEmpty(str)) {
                    return str;
                }
                String macByJavaAPI = getMacByJavaAPI();
                deviceTypeEnum = DeviceTypeEnum.MAC;
                if (TextUtils.isEmpty(macByJavaAPI)) {
                    if (AnalyticsConstants.CHECK_DEVICE) {
                        macByJavaAPI = getMacShell();
                        deviceTypeEnum = DeviceTypeEnum.MAC;
                    } else {
                        macByJavaAPI = getMacBySystemInterface(context);
                        deviceTypeEnum = DeviceTypeEnum.MAC;
                    }
                }
                if (AnalyticsConstants.UM_DEBUG) {
                    String str5 = LOG_TAG;
                    MLog.i(str5, "getDeviceId, MAC: " + macByJavaAPI);
                }
                if (!TextUtils.isEmpty(macByJavaAPI)) {
                    return macByJavaAPI;
                }
                String serialNo2 = getSerialNo();
                deviceTypeEnum = DeviceTypeEnum.SERIALNO;
                if (!TextUtils.isEmpty(serialNo2)) {
                    return serialNo2;
                }
                String imei2 = getIMEI(context);
                deviceTypeEnum = DeviceTypeEnum.IMEI;
                return imei2;
            } else if (Build.VERSION.SDK_INT >= 29) {
                String oaid = getOaid(context);
                deviceTypeEnum = DeviceTypeEnum.OAID;
                if (!TextUtils.isEmpty(oaid)) {
                    return oaid;
                }
                String idfa = getIdfa(context);
                deviceTypeEnum = DeviceTypeEnum.IDFA;
                if (!TextUtils.isEmpty(idfa)) {
                    return idfa;
                }
                String androidId = getAndroidId(context);
                deviceTypeEnum = DeviceTypeEnum.ANDROIDID;
                if (!TextUtils.isEmpty(androidId)) {
                    return androidId;
                }
                String serialNo3 = getSerialNo();
                deviceTypeEnum = DeviceTypeEnum.SERIALNO;
                if (!TextUtils.isEmpty(serialNo3)) {
                    return serialNo3;
                }
                String macByJavaAPI2 = getMacByJavaAPI();
                deviceTypeEnum = DeviceTypeEnum.MAC;
                if (!TextUtils.isEmpty(macByJavaAPI2)) {
                    return macByJavaAPI2;
                }
                String macBySystemInterface2 = getMacBySystemInterface(context);
                deviceTypeEnum = DeviceTypeEnum.MAC;
                return macBySystemInterface2;
            } else {
                if (FieldManager.allow(b.i)) {
                    str = Settings.Secure.getString(context.getContentResolver(), "android_id");
                    deviceTypeEnum = DeviceTypeEnum.ANDROIDID;
                    if (AnalyticsConstants.UM_DEBUG) {
                        String str6 = LOG_TAG;
                        MLog.i(str6, "getDeviceId: ANDROID_ID: " + str);
                    }
                }
                if (!TextUtils.isEmpty(str)) {
                    return str;
                }
                String serialNo4 = getSerialNo();
                deviceTypeEnum = DeviceTypeEnum.SERIALNO;
                if (!TextUtils.isEmpty(serialNo4)) {
                    return serialNo4;
                }
                String imei3 = getIMEI(context);
                deviceTypeEnum = DeviceTypeEnum.IMEI;
                if (!TextUtils.isEmpty(imei3)) {
                    return imei3;
                }
                String macByJavaAPI3 = getMacByJavaAPI();
                deviceTypeEnum = DeviceTypeEnum.MAC;
                if (!TextUtils.isEmpty(macByJavaAPI3)) {
                    return macByJavaAPI3;
                }
                String macBySystemInterface3 = getMacBySystemInterface(context);
                deviceTypeEnum = DeviceTypeEnum.MAC;
                if (!AnalyticsConstants.UM_DEBUG) {
                    return macBySystemInterface3;
                }
                String str7 = LOG_TAG;
                MLog.i(str7, "getDeviceId, MAC: " + macBySystemInterface3);
                return macBySystemInterface3;
            }
        } catch (Throwable unused) {
            return str;
        }
    }

    public static String getOaid(Context context) {
        if (!FieldManager.allow(b.G)) {
            return "";
        }
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(i.a, 0);
            if (sharedPreferences != null) {
                return sharedPreferences.getString(i.b, "");
            }
            return "";
        } catch (Throwable unused) {
            return "";
        }
    }

    private static String getIdfa(Context context) {
        try {
            if (FieldManager.allow(b.w)) {
                return a.a(context);
            }
            return "";
        } catch (Throwable unused) {
            return "";
        }
    }

    private static String getIMEI(Context context) {
        Throwable th;
        if (!TextUtils.isEmpty(sImei)) {
            return sImei;
        }
        String str = "";
        if (FieldManager.allow(b.g)) {
            if (context == null) {
                return str;
            }
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (telephonyManager != null) {
                try {
                    if (checkPermission(context, "android.permission.READ_PHONE_STATE")) {
                        str = telephonyManager.getDeviceId();
                        try {
                            if (AnalyticsConstants.UM_DEBUG) {
                                String str2 = LOG_TAG;
                                MLog.i(str2, "getDeviceId, IMEI: " + str);
                            }
                            str = str;
                        } catch (Throwable th2) {
                            th = th2;
                            if (AnalyticsConstants.UM_DEBUG) {
                                MLog.w(LOG_TAG, "No IMEI.", th);
                            }
                            sImei = str;
                            return sImei;
                        }
                    }
                } catch (Throwable th3) {
                    th = th3;
                    str = str;
                }
            }
        }
        sImei = str;
        return sImei;
    }

    private static String getSerialNo() {
        String str = "";
        if (FieldManager.allow(b.j) && Build.VERSION.SDK_INT >= 9) {
            if (Build.VERSION.SDK_INT >= 26) {
                try {
                    Class<?> cls = Class.forName("android.os.Build");
                    str = (String) cls.getMethod("getSerial", new Class[0]).invoke(cls, new Object[0]);
                } catch (Throwable unused) {
                }
            } else {
                str = Build.SERIAL;
            }
        }
        if (AnalyticsConstants.UM_DEBUG) {
            String str2 = LOG_TAG;
            MLog.i(str2, "getDeviceId, serial no: " + str);
        }
        return str;
    }

    public static String getSubOSName(Context context) {
        Properties buildProp = getBuildProp();
        try {
            String property = buildProp.getProperty(KEY_MIUI_VERSION_NAME);
            return TextUtils.isEmpty(property) ? isFlyMe() ? "Flyme" : isEmui(buildProp) ? "Emui" : !TextUtils.isEmpty(getYunOSVersion(buildProp)) ? "YunOS" : property : "MIUI";
        } catch (Throwable unused) {
            return null;
        }
    }

    public static String getSubOSVersion(Context context) {
        Properties buildProp = getBuildProp();
        try {
            String property = buildProp.getProperty(KEY_MIUI_VERSION_NAME);
            if (!TextUtils.isEmpty(property)) {
                return property;
            }
            try {
                if (isFlyMe()) {
                    property = getFlymeVersion(buildProp);
                } else if (isEmui(buildProp)) {
                    property = getEmuiVersion(buildProp);
                } else {
                    property = getYunOSVersion(buildProp);
                }
                return property;
            } catch (Throwable unused) {
                return property;
            }
        } catch (Throwable unused2) {
            return null;
        }
    }

    private static String getYunOSVersion(Properties properties) {
        try {
            String property = properties.getProperty("ro.yunos.version");
            if (!TextUtils.isEmpty(property)) {
                return property;
            }
            return null;
        } catch (Throwable unused) {
            return null;
        }
    }

    private static String getFlymeVersion(Properties properties) {
        try {
            String lowerCase = properties.getProperty("ro.build.display.id").toLowerCase(Locale.getDefault());
            if (lowerCase.contains("flyme os")) {
                return lowerCase.split(StringUtils.SPACE)[2];
            }
            return null;
        } catch (Throwable unused) {
            return null;
        }
    }

    private static String getEmuiVersion(Properties properties) {
        try {
            return properties.getProperty(KEY_EMUI_VERSION_CODE, null);
        } catch (Exception unused) {
            return null;
        }
    }

    private static Properties getBuildProp() {
        Properties properties;
        FileInputStream fileInputStream;
        Throwable th;
        FileInputStream fileInputStream2;
        try {
            properties = new Properties();
            fileInputStream = null;
            try {
                fileInputStream2 = new FileInputStream(new File(Environment.getRootDirectory(), "build.prop"));
            } catch (Throwable unused) {
            }
        } catch (Throwable unused2) {
        }
        try {
            properties.load(fileInputStream2);
            fileInputStream2.close();
        } catch (Throwable th2) {
            th = th2;
            fileInputStream = fileInputStream2;
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (Throwable unused3) {
                }
            }
            throw th;
        }
        return properties;
    }

    private static boolean isFlyMe() {
        try {
            Build.class.getMethod("hasSmartBar", new Class[0]);
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    private static boolean isEmui(Properties properties) {
        try {
            return properties.getProperty(KEY_EMUI_VERSION_CODE, null) != null;
        } catch (Exception unused) {
            return false;
        }
    }

    public static String getDeviceType(Context context) {
        if (context == null) {
            return AIApiConstants.Phone.NAME;
        }
        try {
            return (context.getResources().getConfiguration().screenLayout & 15) >= 3 ? "Tablet" : AIApiConstants.Phone.NAME;
        } catch (Throwable unused) {
            return AIApiConstants.Phone.NAME;
        }
    }

    public static String getDBencryptID(Context context) {
        return UMUtils.genId();
    }

    public static Activity getGlobleActivity(Context context) {
        Activity activity = null;
        try {
            Class<?> cls = Class.forName("android.app.ActivityThread");
            Object invoke = cls.getMethod("currentActivityThread", new Class[0]).invoke(null, new Object[0]);
            Field declaredField = cls.getDeclaredField("mActivities");
            declaredField.setAccessible(true);
            for (Object obj : ((Map) declaredField.get(invoke)).values()) {
                Class<?> cls2 = obj.getClass();
                Field declaredField2 = cls2.getDeclaredField("paused");
                declaredField2.setAccessible(true);
                if (!declaredField2.getBoolean(obj)) {
                    Field declaredField3 = cls2.getDeclaredField(IDMServer.PERSIST_TYPE_ACTIVITY);
                    declaredField3.setAccessible(true);
                    activity = (Activity) declaredField3.get(obj);
                }
            }
        } catch (Throwable unused) {
        }
        return activity;
    }
}
