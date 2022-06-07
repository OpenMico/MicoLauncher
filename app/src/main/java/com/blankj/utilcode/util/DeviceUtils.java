package com.blankj.utilcode.util;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;
import com.blankj.utilcode.util.ShellUtils;
import com.xiaomi.mico.base.utils.WiFiUtil;
import com.xiaomi.mipush.sdk.Constants;
import com.xiaomi.smarthome.library.common.network.Network;
import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.UUID;

/* loaded from: classes.dex */
public final class DeviceUtils {
    private static volatile String a;

    private DeviceUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static boolean isDeviceRooted() {
        for (String str : new String[]{"/system/bin/", "/system/xbin/", "/sbin/", "/system/sd/xbin/", "/system/bin/failsafe/", "/data/local/xbin/", "/data/local/bin/", "/data/local/", "/system/sbin/", "/usr/bin/", "/vendor/bin/"}) {
            if (new File(str + "su").exists()) {
                return true;
            }
        }
        return false;
    }

    @RequiresApi(api = 17)
    public static boolean isAdbEnabled() {
        return Settings.Secure.getInt(Utils.getApp().getContentResolver(), "adb_enabled", 0) > 0;
    }

    public static String getSDKVersionName() {
        return Build.VERSION.RELEASE;
    }

    public static int getSDKVersionCode() {
        return Build.VERSION.SDK_INT;
    }

    @SuppressLint({"HardwareIds"})
    public static String getAndroidID() {
        String string = Settings.Secure.getString(Utils.getApp().getContentResolver(), "android_id");
        return (!"9774d56d682e549c".equals(string) && string != null) ? string : "";
    }

    @RequiresPermission(allOf = {"android.permission.ACCESS_WIFI_STATE", "android.permission.CHANGE_WIFI_STATE"})
    public static String getMacAddress() {
        String[] strArr = null;
        String macAddress = getMacAddress(strArr);
        if (!TextUtils.isEmpty(macAddress) || a()) {
            return macAddress;
        }
        a(true);
        a(false);
        return getMacAddress(strArr);
    }

    private static boolean a() {
        WifiManager wifiManager = (WifiManager) Utils.getApp().getSystemService(Network.NETWORK_TYPE_WIFI);
        if (wifiManager == null) {
            return false;
        }
        return wifiManager.isWifiEnabled();
    }

    @RequiresPermission("android.permission.CHANGE_WIFI_STATE")
    private static void a(boolean z) {
        WifiManager wifiManager = (WifiManager) Utils.getApp().getSystemService(Network.NETWORK_TYPE_WIFI);
        if (wifiManager != null && z != wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(z);
        }
    }

    @RequiresPermission(allOf = {"android.permission.ACCESS_WIFI_STATE"})
    public static String getMacAddress(String... strArr) {
        String c = c();
        if (a(c, strArr)) {
            return c;
        }
        String d = d();
        if (a(d, strArr)) {
            return d;
        }
        String b = b();
        if (a(b, strArr)) {
            return b;
        }
        String f = f();
        return a(f, strArr) ? f : "";
    }

    private static boolean a(String str, String... strArr) {
        if (TextUtils.isEmpty(str) || "02:00:00:00:00:00".equals(str)) {
            return false;
        }
        if (strArr == null || strArr.length == 0) {
            return true;
        }
        for (String str2 : strArr) {
            if (str2 != null && str2.equals(str)) {
                return false;
            }
        }
        return true;
    }

    @RequiresPermission("android.permission.ACCESS_WIFI_STATE")
    private static String b() {
        WifiInfo connectionInfo;
        try {
            WifiManager wifiManager = (WifiManager) Utils.getApp().getApplicationContext().getSystemService(Network.NETWORK_TYPE_WIFI);
            if (wifiManager == null || (connectionInfo = wifiManager.getConnectionInfo()) == null) {
                return "02:00:00:00:00:00";
            }
            String macAddress = connectionInfo.getMacAddress();
            return !TextUtils.isEmpty(macAddress) ? macAddress : "02:00:00:00:00:00";
        } catch (Exception e) {
            e.printStackTrace();
            return "02:00:00:00:00:00";
        }
    }

    private static String c() {
        byte[] hardwareAddress;
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface nextElement = networkInterfaces.nextElement();
                if (nextElement != null && nextElement.getName().equalsIgnoreCase(WiFiUtil.WLAN_0) && (hardwareAddress = nextElement.getHardwareAddress()) != null && hardwareAddress.length > 0) {
                    StringBuilder sb = new StringBuilder();
                    int length = hardwareAddress.length;
                    for (int i = 0; i < length; i++) {
                        sb.append(String.format("%02x:", Byte.valueOf(hardwareAddress[i])));
                    }
                    return sb.substring(0, sb.length() - 1);
                }
            }
            return "02:00:00:00:00:00";
        } catch (Exception e) {
            e.printStackTrace();
            return "02:00:00:00:00:00";
        }
    }

    private static String d() {
        NetworkInterface byInetAddress;
        byte[] hardwareAddress;
        try {
            InetAddress e = e();
            if (e == null || (byInetAddress = NetworkInterface.getByInetAddress(e)) == null || (hardwareAddress = byInetAddress.getHardwareAddress()) == null || hardwareAddress.length <= 0) {
                return "02:00:00:00:00:00";
            }
            StringBuilder sb = new StringBuilder();
            int length = hardwareAddress.length;
            for (int i = 0; i < length; i++) {
                sb.append(String.format("%02x:", Byte.valueOf(hardwareAddress[i])));
            }
            return sb.substring(0, sb.length() - 1);
        } catch (Exception e2) {
            e2.printStackTrace();
            return "02:00:00:00:00:00";
        }
    }

    private static InetAddress e() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface nextElement = networkInterfaces.nextElement();
                if (nextElement.isUp()) {
                    Enumeration<InetAddress> inetAddresses = nextElement.getInetAddresses();
                    while (inetAddresses.hasMoreElements()) {
                        InetAddress nextElement2 = inetAddresses.nextElement();
                        if (!nextElement2.isLoopbackAddress() && nextElement2.getHostAddress().indexOf(58) < 0) {
                            return nextElement2;
                        }
                    }
                    continue;
                }
            }
            return null;
        } catch (SocketException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String f() {
        String str;
        String str2;
        ShellUtils.CommandResult b = b.b("getprop wifi.interface", false);
        if (b.result != 0 || (str = b.successMsg) == null) {
            return "02:00:00:00:00:00";
        }
        ShellUtils.CommandResult b2 = b.b("cat /sys/class/net/" + str + "/address", false);
        return (b2.result != 0 || (str2 = b2.successMsg) == null || str2.length() <= 0) ? "02:00:00:00:00:00" : str2;
    }

    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    public static String getModel() {
        String str = Build.MODEL;
        return str != null ? str.trim().replaceAll("\\s*", "") : "";
    }

    public static String[] getABIs() {
        if (Build.VERSION.SDK_INT >= 21) {
            return Build.SUPPORTED_ABIS;
        }
        return !TextUtils.isEmpty(Build.CPU_ABI2) ? new String[]{Build.CPU_ABI, Build.CPU_ABI2} : new String[]{Build.CPU_ABI};
    }

    public static boolean isTablet() {
        return (Resources.getSystem().getConfiguration().screenLayout & 15) >= 3;
    }

    public static boolean isEmulator() {
        String networkOperatorName;
        if (Build.FINGERPRINT.startsWith("generic") || Build.FINGERPRINT.toLowerCase().contains("vbox") || Build.FINGERPRINT.toLowerCase().contains("test-keys") || Build.MODEL.contains("google_sdk") || Build.MODEL.contains("Emulator") || Build.MODEL.contains("Android SDK built for x86") || Build.MANUFACTURER.contains("Genymotion") || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")) || "google_sdk".equals(Build.PRODUCT)) {
            return true;
        }
        String str = "";
        TelephonyManager telephonyManager = (TelephonyManager) Utils.getApp().getSystemService("phone");
        if (!(telephonyManager == null || (networkOperatorName = telephonyManager.getNetworkOperatorName()) == null)) {
            str = networkOperatorName;
        }
        if (str.toLowerCase().equals("android")) {
            return true;
        }
        Intent intent = new Intent();
        intent.setData(Uri.parse("tel:123456"));
        intent.setAction("android.intent.action.DIAL");
        return intent.resolveActivity(Utils.getApp().getPackageManager()) == null;
    }

    @RequiresApi(api = 17)
    public static boolean isDevelopmentSettingsEnabled() {
        return Settings.Global.getInt(Utils.getApp().getContentResolver(), "development_settings_enabled", 0) > 0;
    }

    public static String getUniqueDeviceId() {
        return getUniqueDeviceId("", true);
    }

    public static String getUniqueDeviceId(String str) {
        return getUniqueDeviceId(str, true);
    }

    public static String getUniqueDeviceId(boolean z) {
        return getUniqueDeviceId("", z);
    }

    public static String getUniqueDeviceId(String str, boolean z) {
        if (!z) {
            return a(str);
        }
        if (a == null) {
            synchronized (DeviceUtils.class) {
                if (a == null) {
                    String string = b.t().getString("KEY_UDID", null);
                    if (string != null) {
                        a = string;
                        return a;
                    }
                    return a(str);
                }
            }
        }
        return a;
    }

    private static String a(String str) {
        try {
            String androidID = getAndroidID();
            if (!TextUtils.isEmpty(androidID)) {
                return a(str + 2, androidID);
            }
        } catch (Exception unused) {
        }
        return a(str + 9, "");
    }

    @RequiresPermission(allOf = {"android.permission.ACCESS_WIFI_STATE", "android.permission.INTERNET", "android.permission.CHANGE_WIFI_STATE"})
    public static boolean isSameDevice(String str) {
        if (TextUtils.isEmpty(str) && str.length() < 33) {
            return false;
        }
        if (str.equals(a) || str.equals(b.t().getString("KEY_UDID", null))) {
            return true;
        }
        int length = str.length() - 33;
        int i = length + 1;
        String substring = str.substring(length, i);
        if (substring.startsWith("1")) {
            String macAddress = getMacAddress();
            if (macAddress.equals("")) {
                return false;
            }
            return str.substring(i).equals(b("", macAddress));
        } else if (!substring.startsWith("2")) {
            return false;
        } else {
            String androidID = getAndroidID();
            if (TextUtils.isEmpty(androidID)) {
                return false;
            }
            return str.substring(i).equals(b("", androidID));
        }
    }

    private static String a(String str, String str2) {
        a = b(str, str2);
        b.t().put("KEY_UDID", a);
        return a;
    }

    private static String b(String str, String str2) {
        if (str2.equals("")) {
            return str + UUID.randomUUID().toString().replace(Constants.ACCEPT_TIME_SEPARATOR_SERVER, "");
        }
        return str + UUID.nameUUIDFromBytes(str2.getBytes()).toString().replace(Constants.ACCEPT_TIME_SEPARATOR_SERVER, "");
    }
}
