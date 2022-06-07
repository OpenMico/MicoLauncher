package com.xiaomi.mico.base.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiEnterpriseConfig;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.utils.LogCallback;
import com.xiaomi.mico.utils.Threads;
import com.xiaomi.mico.utils.UtilsConfig;
import com.xiaomi.mipush.sdk.Constants;
import com.xiaomi.smarthome.library.common.network.Network;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

/* loaded from: classes3.dex */
public class WiFiUtil {
    public static final String EAP = "EAP";
    public static final String[] EAP_METHOD = {"PEAP", "TLS", "TTLS"};
    public static final String IEEE8021X = "IEEE8021X";
    public static final String KEY_MAC_ADDRESS = "MAC_ADDRESS";
    public static final String NONE = "NONE";
    public static final String OPEN = "Open";
    public static final String WEP = "WEP";
    public static final String WLAN_0 = "wlan0";
    public static final String WPA = "WPA";
    public static final String WPA2 = "WPA2";
    public static final String WPA_EAP = "WPA-EAP";

    public static boolean is5GHz(int i) {
        return i > 4900 && i < 5900;
    }

    public static void enableWifiConnectivityManager(Context context, boolean z) {
        if (context != null) {
            getWifiManager(context).enableWifiConnectivityManager(z);
        }
    }

    public static WifiInfo getWiFiInfo(Context context) {
        WifiInfo connectionInfo = getWifiManager(context).getConnectionInfo();
        if (connectionInfo == null || connectionInfo.getNetworkId() == -1) {
            return null;
        }
        if (connectionInfo.getRssi() == -127 && connectionInfo.getLinkSpeed() == -1) {
            return null;
        }
        return connectionInfo;
    }

    public static String getBSSID(Context context) {
        WifiInfo wiFiInfo = getWiFiInfo(context);
        if (wiFiInfo == null) {
            return null;
        }
        return wiFiInfo.getBSSID();
    }

    public static String getSSID(Context context) {
        String ssid;
        if (context == null) {
            return "";
        }
        WifiInfo wiFiInfo = getWiFiInfo(context);
        if (wiFiInfo == null || (ssid = wiFiInfo.getSSID()) == null) {
            return null;
        }
        return stripSSID(ssid);
    }

    public static boolean isWifiInfoHidden(Context context) {
        if (context != null) {
            WifiInfo wiFiInfo = getWiFiInfo(context);
            if (wiFiInfo == null) {
                return false;
            }
            return wiFiInfo.getHiddenSSID();
        }
        throw new IllegalArgumentException("context must be not null");
    }

    public static List<ScanResult> getWiFiList(Context context) {
        Threads.verifyThread();
        return getWifiManager(context).getScanResults();
    }

    public static String stripSSID(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return (str.length() < 3 || !str.startsWith("\"") || !str.endsWith("\"")) ? str : str.substring(1, str.length() - 1);
    }

    public static String getMacAddress() {
        String a = a();
        return a == null ? c() : a;
    }

    private static String a() {
        try {
            InetAddress b = b();
            if (b == null) {
                return null;
            }
            byte[] hardwareAddress = NetworkInterface.getByInetAddress(b).getHardwareAddress();
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < hardwareAddress.length; i++) {
                if (i != 0) {
                    stringBuffer.append(':');
                }
                String hexString = Integer.toHexString(hardwareAddress[i] & 255);
                if (hexString.length() == 1) {
                    hexString = 0 + hexString;
                }
                stringBuffer.append(hexString);
            }
            return stringBuffer.toString().toUpperCase();
        } catch (SocketException e) {
            a("getMacAddress", e);
            return null;
        }
    }

    private static InetAddress b() {
        InetAddress inetAddress;
        SocketException e;
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            inetAddress = null;
            do {
                try {
                    if (!networkInterfaces.hasMoreElements()) {
                        break;
                    }
                    Enumeration<InetAddress> inetAddresses = networkInterfaces.nextElement().getInetAddresses();
                    while (true) {
                        if (inetAddresses.hasMoreElements()) {
                            InetAddress nextElement = inetAddresses.nextElement();
                            try {
                                if (!nextElement.isLoopbackAddress() && !nextElement.getHostAddress().contains(Constants.COLON_SEPARATOR)) {
                                    inetAddress = nextElement;
                                    continue;
                                    break;
                                }
                                inetAddress = null;
                            } catch (SocketException e2) {
                                e = e2;
                                inetAddress = nextElement;
                                a("getLocalInetAddress", e);
                                return inetAddress;
                            }
                        }
                    }
                } catch (SocketException e3) {
                    e = e3;
                }
            } while (inetAddress == null);
        } catch (SocketException e4) {
            e = e4;
            inetAddress = null;
        }
        return inetAddress;
    }

    private static String c() {
        try {
            for (NetworkInterface networkInterface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                if (networkInterface.getName().equalsIgnoreCase(WLAN_0)) {
                    byte[] hardwareAddress = networkInterface.getHardwareAddress();
                    if (hardwareAddress == null) {
                        return "";
                    }
                    StringBuilder sb = new StringBuilder();
                    for (byte b : hardwareAddress) {
                        String hexString = Integer.toHexString(b & 255);
                        if (hexString.length() == 1) {
                            hexString = "0".concat(hexString);
                        }
                        sb.append(hexString.concat(Constants.COLON_SEPARATOR));
                    }
                    if (sb.length() > 0) {
                        sb.deleteCharAt(sb.length() - 1);
                    }
                    return sb.toString().toUpperCase();
                }
            }
            return "";
        } catch (SocketException e) {
            a("getMacAddrBackup", e);
            return "";
        }
    }

    public static String getConnectedWifiMacAddress(Context context) {
        WifiManager wifiManager = getWifiManager(context);
        if (wifiManager == null) {
            return null;
        }
        List<ScanResult> scanResults = wifiManager.getScanResults();
        WifiInfo connectionInfo = wifiManager.getConnectionInfo();
        if (scanResults == null || connectionInfo == null) {
            return null;
        }
        for (int i = 0; i < scanResults.size(); i++) {
            ScanResult scanResult = scanResults.get(i);
            if (connectionInfo.getBSSID().equals(scanResult.BSSID)) {
                return scanResult.BSSID.toUpperCase();
            }
        }
        return null;
    }

    public static boolean hasPassword(String str) {
        return str.contains("WEP") || str.contains("WPA");
    }

    public static boolean isEnterprise(String str) {
        return str.contains("-EAP-");
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

    public static boolean isWifiConnected(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo == null || activeNetworkInfo.getType() != 1) {
            return false;
        }
        return activeNetworkInfo.isConnected();
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
        if (networkCapabilities == null) {
            return false;
        }
        return networkCapabilities.hasCapability(16);
    }

    public static int getCurrentWifiLevel(Context context, int i) {
        return WifiManager.calculateSignalLevel(getWifiManager(context).getConnectionInfo().getRssi(), i);
    }

    public static boolean supportWPA(String str) {
        return str.equalsIgnoreCase("WPA");
    }

    public static boolean supportWEP(String str) {
        return str.equalsIgnoreCase("WEP");
    }

    public static boolean isSecurityOpenWifi(Context context, String str, String str2, String str3) {
        SecurityInfo security = getSecurity(context, str, str2, str3);
        return security != null && TextUtils.equals("Open", security.a);
    }

    public static SecurityInfo getSecurity(Context context, String str, String str2, String str3) {
        String str4;
        ScanResult scanResult = null;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        SecurityInfo securityInfo = new SecurityInfo();
        List<ScanResult> wiFiList = getWiFiList(context);
        boolean isEmpty = ContainerUtil.isEmpty(wiFiList);
        if (isEmpty) {
            c("WiFiUtil Scan result is empty, this may cause connection issue for security type is unknown");
        }
        if (!isEmpty) {
            Iterator<ScanResult> it = wiFiList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                ScanResult next = it.next();
                if (isSameSSID(next.SSID, str)) {
                    scanResult = next;
                    break;
                }
            }
        }
        if (scanResult != null) {
            str4 = getScanResultSecurity(scanResult);
        } else {
            securityInfo.b = true;
            if (TextUtils.isEmpty(str3)) {
                str4 = TextUtils.isEmpty(str2) ? "Open" : "WPA2";
            } else {
                str4 = "WPA-EAP";
            }
        }
        securityInfo.a = str4;
        return securityInfo;
    }

    public static WifiConfiguration buildWifiConfiguration(Context context, String str, String str2, String str3) {
        a("WiFiUtil buildWifiConfiguration ssid:%s pssword:%s identity:%s", str, str2, str3);
        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        wifiConfiguration.SSID = a(str);
        SecurityInfo security = getSecurity(context, str, str2, str3);
        if (security.b) {
            a("%s wifi not found, set as hiddenSSID", "WiFiUtil");
            wifiConfiguration.hiddenSSID = true;
        }
        String str4 = security.a;
        wifiConfiguration.status = 2;
        a("%s wifi security is %s", "WiFiUtil", str4);
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
                a("%s wifi security enterprise config", "WiFiUtil");
                wifiConfiguration.allowedGroupCiphers.set(2);
                wifiConfiguration.allowedGroupCiphers.set(3);
                if (str4.equals("WPA-EAP")) {
                    wifiConfiguration.allowedKeyManagement.set(2);
                } else {
                    wifiConfiguration.allowedKeyManagement.set(3);
                }
                if (!TextUtils.isEmpty(str2)) {
                    wifiConfiguration.enterpriseConfig = new WifiEnterpriseConfig();
                    a("%s wifi security enterprise config set id and password", "WiFiUtil");
                    wifiConfiguration.enterpriseConfig.setIdentity(str3);
                    wifiConfiguration.enterpriseConfig.setPassword(str2);
                    wifiConfiguration.enterpriseConfig.setEapMethod(0);
                    break;
                }
                break;
            default:
                c("WiFiUtil Unexpected security " + str4);
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

    public static void disableNetwork(Context context, String str) {
        getWifiManager(context).disableEphemeralNetwork(str);
    }

    public static int enableNetwork(Context context, String str) {
        WifiManager wifiManager = getWifiManager(context);
        List<WifiConfiguration> configuredNetworks = wifiManager.getConfiguredNetworks();
        wifiManager.setWifiEnabled(true);
        for (WifiConfiguration wifiConfiguration : configuredNetworks) {
            if (TextUtils.equals(stripSSID(str), stripSSID(wifiConfiguration.SSID))) {
                wifiManager.enableNetwork(wifiConfiguration.networkId, true);
                a("%s connected wifi %s turn to connect %s", "WiFiUtil", wifiManager.getConnectionInfo().getSSID(), str);
                return wifiConfiguration.networkId;
            }
        }
        return -1;
    }

    public static String getIPAddress(boolean z) {
        try {
            for (NetworkInterface networkInterface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                for (InetAddress inetAddress : Collections.list(networkInterface.getInetAddresses())) {
                    if (!inetAddress.isLoopbackAddress()) {
                        String hostAddress = inetAddress.getHostAddress();
                        boolean z2 = hostAddress.indexOf(58) < 0;
                        if (z) {
                            if (z2) {
                                return hostAddress;
                            }
                        } else if (!z2) {
                            int indexOf = hostAddress.indexOf(37);
                            return indexOf < 0 ? hostAddress.toUpperCase() : hostAddress.substring(0, indexOf).toUpperCase();
                        }
                    }
                }
            }
            return "";
        } catch (Exception unused) {
            return "";
        }
    }

    public static void startScan(Context context) {
        getWifiManager(context).startScan();
    }

    public static boolean isSameSSID(String str, String str2) {
        String stripSSID = stripSSID(str);
        String stripSSID2 = stripSSID(str2);
        if (stripSSID != null) {
            return stripSSID.equals(stripSSID2);
        }
        return stripSSID2 == null;
    }

    public static boolean support5G(Context context) {
        return getWifiManager(context).is5GHzBandSupported();
    }

    public static String getBroadcastAddress() {
        InetAddress broadcast;
        String str = null;
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface nextElement = networkInterfaces.nextElement();
                if (nextElement.isUp()) {
                    Iterator<InterfaceAddress> it = nextElement.getInterfaceAddresses().iterator();
                    while (true) {
                        if (it.hasNext()) {
                            InterfaceAddress next = it.next();
                            InetAddress address = next.getAddress();
                            if (!address.isLoopbackAddress() && address.getHostAddress().indexOf(Constants.COLON_SEPARATOR) <= 0 && (broadcast = next.getBroadcast()) != null) {
                                str = broadcast.getHostAddress();
                                break;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            a("WiFiUtil getBroadcastAddress", e);
        }
        return str;
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

    public static int addWifiNetwork(Context context, String str, String str2, String str3) {
        WifiManager wifiManager = getWifiManager(context.getApplicationContext());
        String encryption = getEncryption(str3);
        a("%s WifiUtil addWifiNetwork encrypte = %s", "WiFiUtil", encryption);
        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        wifiConfiguration.SSID = a(str);
        if (str2 == null || str2.length() == 0) {
            wifiConfiguration.allowedKeyManagement.set(0);
            wifiConfiguration.allowedProtocols.set(1);
            wifiConfiguration.allowedProtocols.set(0);
            wifiConfiguration.allowedAuthAlgorithms.clear();
            wifiConfiguration.allowedPairwiseCiphers.set(2);
            wifiConfiguration.allowedPairwiseCiphers.set(1);
            wifiConfiguration.allowedGroupCiphers.set(0);
            wifiConfiguration.allowedGroupCiphers.set(1);
            wifiConfiguration.allowedGroupCiphers.set(3);
            wifiConfiguration.allowedGroupCiphers.set(2);
        } else {
            wifiConfiguration.allowedPairwiseCiphers.set(2);
            if (!TextUtils.isEmpty(str3)) {
                if (str3.contains("TKIP")) {
                    wifiConfiguration.allowedPairwiseCiphers.set(1);
                }
                if (str3.contains("WPA-")) {
                    wifiConfiguration.allowedProtocols.set(0);
                }
                if (str3.contains("WPA2-")) {
                    wifiConfiguration.allowedProtocols.set(1);
                }
            }
            if ("WPA/WPA2".equals(encryption)) {
                wifiConfiguration.allowedKeyManagement.set(1);
                wifiConfiguration.allowedAuthAlgorithms.set(0);
                wifiConfiguration.allowedGroupCiphers.set(0);
                wifiConfiguration.allowedGroupCiphers.set(1);
                wifiConfiguration.allowedGroupCiphers.set(3);
                wifiConfiguration.allowedGroupCiphers.set(2);
                wifiConfiguration.preSharedKey = "\"".concat(str2).concat("\"");
            } else if ("WEP".equals(encryption)) {
                wifiConfiguration.allowedKeyManagement.set(0);
                wifiConfiguration.allowedAuthAlgorithms.set(0);
                wifiConfiguration.allowedAuthAlgorithms.set(1);
                wifiConfiguration.allowedGroupCiphers.set(0);
                wifiConfiguration.allowedGroupCiphers.set(1);
                if (b(str2)) {
                    wifiConfiguration.wepKeys[0] = str2;
                } else {
                    wifiConfiguration.wepKeys[0] = "\"".concat(str2).concat("\"");
                }
                wifiConfiguration.wepTxKeyIndex = 0;
            }
        }
        wifiConfiguration.status = 2;
        int addNetwork = wifiManager == null ? -1 : wifiManager.addNetwork(wifiConfiguration);
        if (addNetwork != -1) {
            wifiManager.saveConfiguration();
        }
        return addNetwork;
    }

    public static String getEncryption(String str) {
        ArrayList arrayList = new ArrayList();
        if (str != null) {
            if (str.toUpperCase().contains("WPA")) {
                arrayList.add("WPA");
            }
            if (str.toUpperCase().contains("WPA2")) {
                arrayList.add("WPA2");
            }
            if (str.toUpperCase().contains("WEP")) {
                arrayList.add("WEP");
            }
            if (str.toUpperCase().contains("EAP")) {
                arrayList.add("EAP");
            }
        }
        if (arrayList.isEmpty()) {
            arrayList.add("NONE");
        }
        return XMStringUtils.join(arrayList, "/");
    }

    private static boolean b(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (!(length == 10 || length == 26 || length == 58)) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if ((charAt < '0' || charAt > '9') && ((charAt < 'a' || charAt > 'f') && (charAt < 'A' || charAt > 'F'))) {
                return false;
            }
        }
        return true;
    }

    public static int getNetworkId(Context context) {
        WifiManager wifiManager = getWifiManager(context.getApplicationContext());
        WifiInfo connectionInfo = wifiManager == null ? null : wifiManager.getConnectionInfo();
        if (connectionInfo != null) {
            return connectionInfo.getNetworkId();
        }
        return -1;
    }

    public static WifiManager getWifiManager(Context context) {
        return (WifiManager) context.getSystemService(Network.NETWORK_TYPE_WIFI);
    }

    private static void a(String str, Object... objArr) {
        LogCallback logCallback = UtilsConfig.getLogCallback();
        if (logCallback == null) {
            Log.i("WiFiUtil", String.format(str, objArr));
        } else {
            logCallback.i(str, objArr);
        }
    }

    private static void a(String str, Throwable th) {
        LogCallback logCallback = UtilsConfig.getLogCallback();
        if (logCallback == null) {
            Log.e("WiFiUtil", str, th);
        } else {
            logCallback.e(str, th);
        }
    }

    private static void c(String str) {
        LogCallback logCallback = UtilsConfig.getLogCallback();
        if (logCallback == null) {
            Log.e("WiFiUtil", str);
        } else {
            logCallback.e(str, new Object[0]);
        }
    }

    public static int frequencyToChannel(int i) {
        if (i == 2484) {
            return 14;
        }
        if (i < 2484) {
            return (i - 2407) / 5;
        }
        return (i / 5) - 1000;
    }

    /* loaded from: classes3.dex */
    public static class SecurityInfo {
        String a;
        boolean b;

        public String getSecurity() {
            return this.a;
        }

        public void setSecurity(String str) {
            this.a = str;
        }

        public boolean isHidden() {
            return this.b;
        }

        public void setHidden(boolean z) {
            this.b = z;
        }
    }
}
