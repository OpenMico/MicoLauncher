package com.xiaomi.mico.settingslib.wifi;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiEnterpriseConfig;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.xiaomi.smarthome.library.common.network.Network;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class WiFiUtil {
    public static final String EAP = "EAP";
    public static final String IEEE8021X = "IEEE8021X";
    public static final String NONE = "NONE";
    public static final String OPEN = "Open";
    public static final String WEP = "WEP";
    public static final String WPA = "WPA";
    public static final String WPA2 = "WPA2";
    public static final String WPA_EAP = "WPA-EAP";

    public static boolean isWifiConnected(@NonNull Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo == null || activeNetworkInfo.getType() != 1) {
            return false;
        }
        return activeNetworkInfo.isConnected();
    }

    public static List<ScanResult> getWiFiList(Context context) {
        return ((WifiManager) context.getSystemService(Network.NETWORK_TYPE_WIFI)).getScanResults();
    }

    public static String getSSID(Context context) {
        String ssid;
        if (context == null) {
            return "";
        }
        WifiInfo connectionInfo = ((WifiManager) context.getSystemService(Network.NETWORK_TYPE_WIFI)).getConnectionInfo();
        if (connectionInfo == null || connectionInfo.getNetworkId() == -1 || (ssid = connectionInfo.getSSID()) == null) {
            return null;
        }
        if (connectionInfo.getRssi() == -127 && connectionInfo.getLinkSpeed() == -1) {
            return null;
        }
        return stripSSID(ssid);
    }

    public static String stripSSID(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return (str.length() < 3 || !str.startsWith("\"") || !str.endsWith("\"")) ? str : str.substring(1, str.length() - 1);
    }

    public static boolean isSameSSID(String str, String str2) {
        String stripSSID = stripSSID(str);
        String stripSSID2 = stripSSID(str2);
        if (stripSSID != null) {
            return stripSSID.equals(stripSSID2);
        }
        return stripSSID2 == null;
    }

    public static WifiConfiguration getWifiConfigurationBySsid(String str, @Nullable List<WifiConfiguration> list) {
        if (TextUtils.isEmpty(str) || list == null) {
            return null;
        }
        for (int size = list.size() - 1; size >= 0; size--) {
            WifiConfiguration wifiConfiguration = list.get(size);
            if (wifiConfiguration.SSID != null && stripSSID(str).equals(stripSSID(wifiConfiguration.SSID))) {
                return wifiConfiguration;
            }
        }
        return null;
    }

    public static WifiConfiguration buildWifiConfiguration(Context context, String str, String str2, String str3) {
        ScanResult scanResult;
        String str4;
        Log.i("WiFiUtil", String.format("buildWifiConfiguration ssid:%s identity:%s", str, str3));
        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        wifiConfiguration.SSID = a(str);
        Iterator<ScanResult> it = getWiFiList(context).iterator();
        while (true) {
            if (!it.hasNext()) {
                scanResult = null;
                break;
            }
            scanResult = it.next();
            if (scanResult.SSID.equals(str)) {
                break;
            }
        }
        if (scanResult != null) {
            str4 = getScanResultSecurity(scanResult);
        } else {
            Log.w("WiFiUtil", " wifi not found set as hiddenSSID");
            wifiConfiguration.hiddenSSID = true;
            if (TextUtils.isEmpty(str3)) {
                str4 = TextUtils.isEmpty(str2) ? "Open" : "WPA2";
            } else {
                str4 = str3;
            }
        }
        wifiConfiguration.status = 2;
        Log.i("WiFiUtil", "wifi security is " + str4);
        char c = 65535;
        switch (str4.hashCode()) {
            case -2039788433:
                if (str4.equals("WPA-EAP")) {
                    c = 4;
                    break;
                }
                break;
            case 85826:
                if (str4.equals("WEP")) {
                    c = 0;
                    break;
                }
                break;
            case 86152:
                if (str4.equals("WPA")) {
                    c = 1;
                    break;
                }
                break;
            case 2464362:
                if (str4.equals("Open")) {
                    c = 3;
                    break;
                }
                break;
            case 2670762:
                if (str4.equals("WPA2")) {
                    c = 2;
                    break;
                }
                break;
            case 36491973:
                if (str4.equals("IEEE8021X")) {
                    c = 5;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                wifiConfiguration.wepKeys[0] = a(str2);
                wifiConfiguration.wepTxKeyIndex = 0;
                wifiConfiguration.allowedAuthAlgorithms.set(0);
                wifiConfiguration.allowedAuthAlgorithms.set(1);
                wifiConfiguration.allowedKeyManagement.set(0);
                wifiConfiguration.allowedGroupCiphers.set(0);
                wifiConfiguration.allowedGroupCiphers.set(1);
                break;
            case 1:
            case 2:
                wifiConfiguration.allowedGroupCiphers.set(2);
                wifiConfiguration.allowedGroupCiphers.set(3);
                wifiConfiguration.allowedKeyManagement.set(1);
                wifiConfiguration.allowedPairwiseCiphers.set(2);
                wifiConfiguration.allowedPairwiseCiphers.set(1);
                wifiConfiguration.allowedProtocols.set(1);
                wifiConfiguration.allowedProtocols.set(0);
                if (!TextUtils.isEmpty(str2)) {
                    wifiConfiguration.preSharedKey = a(str2);
                    break;
                }
                break;
            case 3:
                wifiConfiguration.allowedKeyManagement.set(0);
                wifiConfiguration.allowedProtocols.set(1);
                wifiConfiguration.allowedProtocols.set(0);
                wifiConfiguration.allowedPairwiseCiphers.set(2);
                wifiConfiguration.allowedPairwiseCiphers.set(1);
                wifiConfiguration.allowedGroupCiphers.set(0);
                wifiConfiguration.allowedGroupCiphers.set(1);
                wifiConfiguration.allowedGroupCiphers.set(3);
                wifiConfiguration.allowedGroupCiphers.set(2);
                break;
            case 4:
            case 5:
                Log.i("WiFiUtil", "wifi security enterprise config");
                wifiConfiguration.allowedGroupCiphers.set(2);
                wifiConfiguration.allowedGroupCiphers.set(3);
                if (str4.equals("WPA-EAP")) {
                    wifiConfiguration.allowedKeyManagement.set(2);
                } else {
                    wifiConfiguration.allowedKeyManagement.set(3);
                }
                if (!TextUtils.isEmpty(str2)) {
                    wifiConfiguration.enterpriseConfig = new WifiEnterpriseConfig();
                    Log.i("WiFiUtil", "wifi security enterprise config set id and password");
                    wifiConfiguration.enterpriseConfig.setIdentity(str3);
                    wifiConfiguration.enterpriseConfig.setPassword(str2);
                    wifiConfiguration.enterpriseConfig.setEapMethod(0);
                    wifiConfiguration.enterpriseConfig.setPhase2Method(3);
                    break;
                }
                break;
        }
        return wifiConfiguration;
    }

    private static String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        int length = str.length() - 1;
        if (length < 0 || (str.charAt(0) == '\"' && str.charAt(length) == '\"')) {
            return str;
        }
        return "\"" + str + "\"";
    }

    public static String getScanResultSecurity(ScanResult scanResult) {
        String str = scanResult.capabilities;
        String[] strArr = {"WEP", "WPA", "WPA2", "WPA-EAP", "IEEE8021X"};
        if (str.contains("WPA2-EAP")) {
            return "WPA-EAP";
        }
        for (int length = strArr.length - 1; length >= 0; length--) {
            if (str.contains(strArr[length])) {
                return strArr[length];
            }
        }
        return "Open";
    }
}
