package com.xiaomi.miot.host.lan.impl.wifi;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.text.TextUtils;
import com.xiaomi.miot.host.lan.impl.MiotLogUtil;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class WifiSettingUtils {
    public static final int SECURITY_EAP = 3;
    public static final int SECURITY_NONE = 0;
    public static final int SECURITY_PSK = 2;
    public static final int SECURITY_WAPI = 4;
    public static final int SECURITY_WEP = 1;
    private static final String TAG = "com.xiaomi.miot.host.lan.impl.wifi.WifiSettingUtils";
    private static boolean mIsArch64 = false;
    private static boolean mIsReaded = false;

    /* loaded from: classes2.dex */
    enum PskType {
        UNKNOWN,
        WPA,
        WPA2,
        WPA_WPA2
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public enum TKIPType {
        TKIP_CCMP,
        TKIP,
        CCMP,
        NONE
    }

    public static int calculateSignalLevel(int i, int i2) {
        if (i <= -100) {
            return 0;
        }
        if (i >= -55) {
            return 100;
        }
        return (int) (((i - (-100)) * 100) / 45);
    }

    public static boolean is24GHzWifi(int i) {
        return i > 2400 && i < 2500;
    }

    public boolean is5GHzWifi(int i) {
        return i > 4900 && i < 5900;
    }

    public static int addHideNoSecureWifiConfig(WifiManager wifiManager, String str) {
        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        setWifiConfig(wifiConfiguration, str, null, "", true);
        if (wifiManager != null) {
            return wifiManager.addNetwork(wifiConfiguration);
        }
        MiotLogUtil.e(TAG, "wifiManager is null!");
        return -1;
    }

    public static void connectHideAp(WifiManager wifiManager, int i) {
        if (wifiManager != null) {
            wifiManager.disconnect();
            wifiManager.enableNetwork(i, true);
            wifiManager.reconnect();
            return;
        }
        MiotLogUtil.e(TAG, "connectHideAp fail because wifiManager is null!");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static void setWifiConfig(WifiConfiguration wifiConfiguration, String str, String str2, String str3, boolean z) {
        int security = getSecurity(str3);
        wifiConfiguration.allowedAuthAlgorithms.clear();
        wifiConfiguration.allowedGroupCiphers.clear();
        wifiConfiguration.allowedKeyManagement.clear();
        wifiConfiguration.allowedPairwiseCiphers.clear();
        wifiConfiguration.allowedProtocols.clear();
        wifiConfiguration.SSID = convertToQuotedString(str);
        if (z) {
            wifiConfiguration.hiddenSSID = true;
        }
        switch (security) {
            case 0:
                wifiConfiguration.allowedKeyManagement.set(0);
                return;
            case 1:
                wifiConfiguration.hiddenSSID = true;
                String[] strArr = wifiConfiguration.wepKeys;
                strArr[0] = "\"" + str2 + "\"";
                wifiConfiguration.allowedAuthAlgorithms.set(1);
                wifiConfiguration.allowedGroupCiphers.set(3);
                wifiConfiguration.allowedGroupCiphers.set(2);
                wifiConfiguration.allowedGroupCiphers.set(0);
                wifiConfiguration.allowedGroupCiphers.set(1);
                wifiConfiguration.allowedKeyManagement.set(0);
                wifiConfiguration.wepTxKeyIndex = 0;
                break;
            case 2:
                break;
            default:
                return;
        }
        wifiConfiguration.preSharedKey = "\"" + str2 + "\"";
        wifiConfiguration.allowedKeyManagement.set(1);
        TKIPType tKIPType = getTKIPType(str3);
        if (tKIPType == TKIPType.TKIP_CCMP) {
            wifiConfiguration.allowedGroupCiphers.set(3);
            wifiConfiguration.allowedGroupCiphers.set(2);
            wifiConfiguration.allowedPairwiseCiphers.set(2);
            wifiConfiguration.allowedPairwiseCiphers.set(1);
        } else if (tKIPType == TKIPType.TKIP) {
            wifiConfiguration.allowedGroupCiphers.set(2);
            wifiConfiguration.allowedPairwiseCiphers.set(1);
        } else if (tKIPType == TKIPType.CCMP) {
            wifiConfiguration.allowedGroupCiphers.set(3);
            wifiConfiguration.allowedPairwiseCiphers.set(2);
        }
        wifiConfiguration.status = 0;
    }

    public static boolean isIntel64BitArchitecture() {
        if (Build.VERSION.SDK_INT >= 21) {
            for (String str : Build.SUPPORTED_ABIS) {
                if (str.equals("x86_64")) {
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static void setWifiConfig(WifiConfiguration wifiConfiguration, ScanResult scanResult, String str) {
        int security = getSecurity(scanResult);
        wifiConfiguration.SSID = convertToQuotedString(scanResult.SSID);
        switch (security) {
            case 0:
                wifiConfiguration.allowedKeyManagement.set(0);
                return;
            case 1:
                wifiConfiguration.hiddenSSID = true;
                String[] strArr = wifiConfiguration.wepKeys;
                strArr[0] = "\"" + str + "\"";
                wifiConfiguration.allowedAuthAlgorithms.set(1);
                wifiConfiguration.allowedGroupCiphers.set(3);
                wifiConfiguration.allowedGroupCiphers.set(2);
                wifiConfiguration.allowedGroupCiphers.set(0);
                wifiConfiguration.allowedGroupCiphers.set(1);
                wifiConfiguration.allowedKeyManagement.set(0);
                wifiConfiguration.wepTxKeyIndex = 0;
                break;
            case 2:
                break;
            default:
                return;
        }
        wifiConfiguration.preSharedKey = "\"" + str + "\"";
        wifiConfiguration.allowedKeyManagement.set(1);
        TKIPType tKIPType = getTKIPType(scanResult);
        if (tKIPType == TKIPType.TKIP_CCMP) {
            wifiConfiguration.allowedGroupCiphers.set(3);
            wifiConfiguration.allowedGroupCiphers.set(2);
            wifiConfiguration.allowedPairwiseCiphers.set(2);
            wifiConfiguration.allowedPairwiseCiphers.set(1);
        } else if (tKIPType == TKIPType.TKIP) {
            wifiConfiguration.allowedGroupCiphers.set(2);
            wifiConfiguration.allowedPairwiseCiphers.set(1);
        } else if (tKIPType == TKIPType.CCMP) {
            wifiConfiguration.allowedGroupCiphers.set(3);
            wifiConfiguration.allowedPairwiseCiphers.set(2);
        }
        wifiConfiguration.status = 0;
    }

    public static int getSecurity(ScanResult scanResult) {
        if (scanResult.capabilities.contains("WEP")) {
            return 1;
        }
        if (scanResult.capabilities.contains("PSK")) {
            return 2;
        }
        if (scanResult.capabilities.contains("EAP")) {
            return 3;
        }
        return scanResult.capabilities.contains("WAPI") ? 4 : 0;
    }

    public static int getSecurity(String str) {
        if (str.equalsIgnoreCase("psk2") || str.equalsIgnoreCase("mixed-psk")) {
            return 2;
        }
        if (str.contains("WEP")) {
            return 1;
        }
        if (str.contains("PSK")) {
            return 2;
        }
        if (str.contains("EAP")) {
            return 3;
        }
        return str.contains("WAPI") ? 4 : 0;
    }

    public static TKIPType getTKIPType(ScanResult scanResult) {
        boolean contains = scanResult.capabilities.contains("TKIP");
        boolean contains2 = scanResult.capabilities.contains("CCMP");
        if (contains && contains2) {
            return TKIPType.TKIP_CCMP;
        }
        if (contains) {
            return TKIPType.TKIP;
        }
        if (contains2) {
            return TKIPType.CCMP;
        }
        return TKIPType.NONE;
    }

    public static TKIPType getTKIPType(String str) {
        if (str.equalsIgnoreCase("psk2")) {
            return TKIPType.CCMP;
        }
        return TKIPType.TKIP_CCMP;
    }

    public static PskType getPskType(ScanResult scanResult) {
        boolean contains = scanResult.capabilities.contains("WPA-PSK");
        boolean contains2 = scanResult.capabilities.contains("WPA2-PSK");
        if (contains2 && contains) {
            return PskType.WPA_WPA2;
        }
        if (contains2) {
            return PskType.WPA2;
        }
        if (contains) {
            return PskType.WPA;
        }
        return PskType.UNKNOWN;
    }

    public static String convertToQuotedString(String str) {
        return "\"" + str + "\"";
    }

    public static boolean isEqualWifi(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return false;
        }
        if (str.startsWith("\"") && str.length() > 2) {
            str = str.substring(1, str.length() - 1);
        }
        if (str2.startsWith("\"") && str2.length() > 2) {
            str2 = str2.substring(1, str2.length() - 1);
        }
        return str.equalsIgnoreCase(str2);
    }

    public static int getAuthType(WifiConfiguration wifiConfiguration) {
        return wifiConfiguration.allowedKeyManagement.get(1) ? 2 : 0;
    }

    public static boolean isMiwifi(ScanResult scanResult) {
        try {
            Field declaredField = Class.forName(ScanResult.class.getName()).getDeclaredField("isXiaomiRouter");
            if (declaredField != null) {
                return declaredField.getBoolean(scanResult);
            }
            return false;
        } catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException unused) {
            return false;
        }
    }

    public static Class isSupportMiui() {
        Class<?> cls;
        try {
            cls = Class.forName("com.miui.whetstone.WhetstoneManager");
        } catch (Exception unused) {
        }
        if (((Integer) cls.getDeclaredMethod("getSmartConfigLevel", new Class[0]).invoke(null, new Object[0])).intValue() == 2) {
            return cls;
        }
        return null;
    }

    public static boolean isContainUnsupportChar(String str) {
        for (byte b : str.getBytes()) {
            if ((b & 128) != 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPasswdSaveByFramework(WifiManager wifiManager, String str) {
        WifiConfiguration wifiConfiguration;
        List<WifiConfiguration> configuredNetworks = wifiManager.getConfiguredNetworks();
        if (configuredNetworks == null) {
            return false;
        }
        Iterator<WifiConfiguration> it = configuredNetworks.iterator();
        while (true) {
            if (!it.hasNext()) {
                wifiConfiguration = null;
                break;
            }
            wifiConfiguration = it.next();
            if (isEqualWifi(wifiConfiguration.SSID, str)) {
                break;
            }
        }
        return wifiConfiguration != null;
    }

    public static boolean connectToAP(WifiManager wifiManager, String str, String str2, String str3, String str4) {
        boolean z = false;
        if (!wifiManager.isWifiEnabled()) {
            return false;
        }
        WifiConfiguration wifiConfiguration = null;
        Iterator<WifiConfiguration> it = wifiManager.getConfiguredNetworks().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            WifiConfiguration next = it.next();
            if (next.SSID != null && next.SSID.equals(convertToQuotedString(str)) && getAuthType(next) == getSecurity(str4)) {
                wifiConfiguration = next;
                break;
            }
        }
        Iterator<ScanResult> it2 = wifiManager.getScanResults().iterator();
        while (true) {
            if (it2.hasNext()) {
                if (it2.next().SSID.equalsIgnoreCase(str)) {
                    z = true;
                    break;
                }
            } else {
                break;
            }
        }
        MiotLogUtil.e(TAG, "isContain " + str + " - " + z);
        if (wifiConfiguration != null) {
            wifiManager.disconnect();
            wifiManager.enableNetwork(wifiConfiguration.networkId, true);
            wifiManager.reconnect();
        } else {
            WifiConfiguration wifiConfiguration2 = new WifiConfiguration();
            setWifiConfig(wifiConfiguration2, str, str2, str4, true);
            wifiConfiguration2.BSSID = str3;
            try {
                int addNetwork = wifiManager.addNetwork(wifiConfiguration2);
                wifiManager.disconnect();
                wifiManager.enableNetwork(addNetwork, true);
                wifiManager.reconnect();
            } catch (Exception unused) {
            }
        }
        return true;
    }

    public static void disconnectAp(WifiManager wifiManager, String str) {
        List<WifiConfiguration> configuredNetworks = wifiManager.getConfiguredNetworks();
        if (configuredNetworks != null) {
            WifiConfiguration wifiConfiguration = null;
            Iterator<WifiConfiguration> it = configuredNetworks.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                WifiConfiguration next = it.next();
                if (next.SSID != null && next.SSID.equals(convertToQuotedString(str))) {
                    wifiConfiguration = next;
                    break;
                }
            }
            if (wifiConfiguration != null) {
                String str2 = TAG;
                MiotLogUtil.e(str2, "disable : " + wifiConfiguration.SSID);
                wifiManager.disableNetwork(wifiConfiguration.networkId);
            }
        }
    }

    public static List<ScanResult> getWifiScanResult(WifiManager wifiManager, String str) {
        ArrayList arrayList = new ArrayList();
        List<ScanResult> scanResults = wifiManager.getScanResults();
        String str2 = TAG;
        MiotLogUtil.e(str2, "scanResultList:" + scanResults.size());
        if (scanResults != null && scanResults.size() > 0) {
            for (int i = 0; i < scanResults.size(); i++) {
                ScanResult scanResult = scanResults.get(i);
                String str3 = TAG;
                MiotLogUtil.e(str3, "scanResult:" + i + scanResult.SSID);
                if (scanResult != null && is24GHzWifi(scanResult.frequency) && !TextUtils.isEmpty(scanResult.SSID) && TextUtils.equals(scanResult.SSID, str)) {
                    arrayList.add(scanResult);
                }
            }
        }
        return arrayList;
    }

    public static List<ScanResult> getWifiScanResult(WifiManager wifiManager) {
        ArrayList arrayList = new ArrayList();
        List<ScanResult> scanResults = wifiManager.getScanResults();
        if (scanResults != null && scanResults.size() > 0) {
            for (int i = 0; i < scanResults.size(); i++) {
                arrayList.add(scanResults.get(i));
            }
        }
        return arrayList;
    }

    public static boolean isWifiConnected(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.getType() == 1;
    }
}
