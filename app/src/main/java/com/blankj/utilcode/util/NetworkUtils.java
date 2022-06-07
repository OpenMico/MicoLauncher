package com.blankj.utilcode.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Formatter;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import com.blankj.utilcode.util.Utils;
import com.xiaomi.smarthome.library.common.network.Network;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArraySet;

/* loaded from: classes.dex */
public final class NetworkUtils {
    private static final Set<Utils.Consumer<WifiScanResults>> a = new CopyOnWriteArraySet();
    private static Timer b;
    private static WifiScanResults c;

    /* loaded from: classes.dex */
    public enum NetworkType {
        NETWORK_ETHERNET,
        NETWORK_WIFI,
        NETWORK_5G,
        NETWORK_4G,
        NETWORK_3G,
        NETWORK_2G,
        NETWORK_UNKNOWN,
        NETWORK_NO
    }

    /* loaded from: classes.dex */
    public interface OnNetworkStatusChangedListener {
        void onConnected(NetworkType networkType);

        void onDisconnected();
    }

    private NetworkUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void openWirelessSettings() {
        Utils.getApp().startActivity(new Intent("android.settings.WIRELESS_SETTINGS").setFlags(268435456));
    }

    @RequiresPermission("android.permission.ACCESS_NETWORK_STATE")
    public static boolean isConnected() {
        NetworkInfo g = g();
        return g != null && g.isConnected();
    }

    @RequiresPermission("android.permission.INTERNET")
    public static Utils.Task<Boolean> isAvailableAsync(@NonNull Utils.Consumer<Boolean> consumer) {
        if (consumer != null) {
            return b.a((Utils.Task) new Utils.Task<Boolean>(consumer) { // from class: com.blankj.utilcode.util.NetworkUtils.1
                @RequiresPermission("android.permission.INTERNET")
                /* renamed from: a */
                public Boolean doInBackground() {
                    return Boolean.valueOf(NetworkUtils.isAvailable());
                }
            });
        }
        throw new NullPointerException("Argument 'consumer' of type Utils.Consumer<Boolean> (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    @RequiresPermission("android.permission.INTERNET")
    public static boolean isAvailable() {
        return isAvailableByDns() || isAvailableByPing(null);
    }

    @RequiresPermission("android.permission.INTERNET")
    public static void isAvailableByPingAsync(Utils.Consumer<Boolean> consumer) {
        isAvailableByPingAsync("", consumer);
    }

    @RequiresPermission("android.permission.INTERNET")
    public static Utils.Task<Boolean> isAvailableByPingAsync(final String str, @NonNull Utils.Consumer<Boolean> consumer) {
        if (consumer != null) {
            return b.a((Utils.Task) new Utils.Task<Boolean>(consumer) { // from class: com.blankj.utilcode.util.NetworkUtils.2
                @RequiresPermission("android.permission.INTERNET")
                /* renamed from: a */
                public Boolean doInBackground() {
                    return Boolean.valueOf(NetworkUtils.isAvailableByPing(str));
                }
            });
        }
        throw new NullPointerException("Argument 'consumer' of type Utils.Consumer<Boolean> (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    @RequiresPermission("android.permission.INTERNET")
    public static boolean isAvailableByPing() {
        return isAvailableByPing("");
    }

    @RequiresPermission("android.permission.INTERNET")
    public static boolean isAvailableByPing(String str) {
        if (TextUtils.isEmpty(str)) {
            str = "223.5.5.5";
        }
        return ShellUtils.execCmd(String.format("ping -c 1 %s", str), false).result == 0;
    }

    @RequiresPermission("android.permission.INTERNET")
    public static void isAvailableByDnsAsync(Utils.Consumer<Boolean> consumer) {
        isAvailableByDnsAsync("", consumer);
    }

    @RequiresPermission("android.permission.INTERNET")
    public static Utils.Task isAvailableByDnsAsync(final String str, @NonNull Utils.Consumer<Boolean> consumer) {
        if (consumer != null) {
            return b.a((Utils.Task) new Utils.Task<Boolean>(consumer) { // from class: com.blankj.utilcode.util.NetworkUtils.3
                @RequiresPermission("android.permission.INTERNET")
                /* renamed from: a */
                public Boolean doInBackground() {
                    return Boolean.valueOf(NetworkUtils.isAvailableByDns(str));
                }
            });
        }
        throw new NullPointerException("Argument 'consumer' of type Utils.Consumer<Boolean> (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    @RequiresPermission("android.permission.INTERNET")
    public static boolean isAvailableByDns() {
        return isAvailableByDns("");
    }

    @RequiresPermission("android.permission.INTERNET")
    public static boolean isAvailableByDns(String str) {
        if (TextUtils.isEmpty(str)) {
            str = "www.baidu.com";
        }
        try {
            return InetAddress.getByName(str) != null;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean getMobileDataEnabled() {
        TelephonyManager telephonyManager;
        try {
            telephonyManager = (TelephonyManager) Utils.getApp().getSystemService("phone");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (telephonyManager == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= 26) {
            return telephonyManager.isDataEnabled();
        }
        Method declaredMethod = telephonyManager.getClass().getDeclaredMethod("getDataEnabled", new Class[0]);
        if (declaredMethod != null) {
            return ((Boolean) declaredMethod.invoke(telephonyManager, new Object[0])).booleanValue();
        }
        return false;
    }

    @RequiresPermission("android.permission.ACCESS_NETWORK_STATE")
    public static boolean isMobileData() {
        NetworkInfo g = g();
        return g != null && g.isAvailable() && g.getType() == 0;
    }

    @RequiresPermission("android.permission.ACCESS_NETWORK_STATE")
    public static boolean is4G() {
        NetworkInfo g = g();
        return g != null && g.isAvailable() && g.getSubtype() == 13;
    }

    @RequiresPermission("android.permission.ACCESS_NETWORK_STATE")
    public static boolean is5G() {
        NetworkInfo g = g();
        return g != null && g.isAvailable() && g.getSubtype() == 20;
    }

    @RequiresPermission("android.permission.ACCESS_WIFI_STATE")
    public static boolean getWifiEnabled() {
        WifiManager wifiManager = (WifiManager) Utils.getApp().getSystemService(Network.NETWORK_TYPE_WIFI);
        if (wifiManager == null) {
            return false;
        }
        return wifiManager.isWifiEnabled();
    }

    @RequiresPermission("android.permission.CHANGE_WIFI_STATE")
    public static void setWifiEnabled(boolean z) {
        WifiManager wifiManager = (WifiManager) Utils.getApp().getSystemService(Network.NETWORK_TYPE_WIFI);
        if (wifiManager != null && z != wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(z);
        }
    }

    @RequiresPermission("android.permission.ACCESS_NETWORK_STATE")
    public static boolean isWifiConnected() {
        NetworkInfo activeNetworkInfo;
        ConnectivityManager connectivityManager = (ConnectivityManager) Utils.getApp().getSystemService("connectivity");
        return (connectivityManager == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null || activeNetworkInfo.getType() != 1) ? false : true;
    }

    @RequiresPermission(allOf = {"android.permission.ACCESS_WIFI_STATE", "android.permission.INTERNET"})
    public static boolean isWifiAvailable() {
        return getWifiEnabled() && isAvailable();
    }

    @RequiresPermission(allOf = {"android.permission.ACCESS_WIFI_STATE", "android.permission.INTERNET"})
    public static Utils.Task<Boolean> isWifiAvailableAsync(@NonNull Utils.Consumer<Boolean> consumer) {
        if (consumer != null) {
            return b.a((Utils.Task) new Utils.Task<Boolean>(consumer) { // from class: com.blankj.utilcode.util.NetworkUtils.4
                @RequiresPermission(allOf = {"android.permission.ACCESS_WIFI_STATE", "android.permission.INTERNET"})
                /* renamed from: a */
                public Boolean doInBackground() {
                    return Boolean.valueOf(NetworkUtils.isWifiAvailable());
                }
            });
        }
        throw new NullPointerException("Argument 'consumer' of type Utils.Consumer<Boolean> (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static String getNetworkOperatorName() {
        TelephonyManager telephonyManager = (TelephonyManager) Utils.getApp().getSystemService("phone");
        return telephonyManager == null ? "" : telephonyManager.getNetworkOperatorName();
    }

    @RequiresPermission("android.permission.ACCESS_NETWORK_STATE")
    public static NetworkType getNetworkType() {
        if (f()) {
            return NetworkType.NETWORK_ETHERNET;
        }
        NetworkInfo g = g();
        if (g == null || !g.isAvailable()) {
            return NetworkType.NETWORK_NO;
        }
        if (g.getType() == 1) {
            return NetworkType.NETWORK_WIFI;
        }
        if (g.getType() != 0) {
            return NetworkType.NETWORK_UNKNOWN;
        }
        switch (g.getSubtype()) {
            case 1:
            case 2:
            case 4:
            case 7:
            case 11:
            case 16:
                return NetworkType.NETWORK_2G;
            case 3:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 12:
            case 14:
            case 15:
            case 17:
                return NetworkType.NETWORK_3G;
            case 13:
            case 18:
                return NetworkType.NETWORK_4G;
            case 19:
            default:
                String subtypeName = g.getSubtypeName();
                if (subtypeName.equalsIgnoreCase("TD-SCDMA") || subtypeName.equalsIgnoreCase("WCDMA") || subtypeName.equalsIgnoreCase("CDMA2000")) {
                    return NetworkType.NETWORK_3G;
                }
                return NetworkType.NETWORK_UNKNOWN;
            case 20:
                return NetworkType.NETWORK_5G;
        }
    }

    @RequiresPermission("android.permission.ACCESS_NETWORK_STATE")
    private static boolean f() {
        NetworkInfo networkInfo;
        NetworkInfo.State state;
        ConnectivityManager connectivityManager = (ConnectivityManager) Utils.getApp().getSystemService("connectivity");
        if (connectivityManager == null || (networkInfo = connectivityManager.getNetworkInfo(9)) == null || (state = networkInfo.getState()) == null) {
            return false;
        }
        return state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING;
    }

    @RequiresPermission("android.permission.ACCESS_NETWORK_STATE")
    private static NetworkInfo g() {
        ConnectivityManager connectivityManager = (ConnectivityManager) Utils.getApp().getSystemService("connectivity");
        if (connectivityManager == null) {
            return null;
        }
        return connectivityManager.getActiveNetworkInfo();
    }

    public static Utils.Task<String> getIPAddressAsync(final boolean z, @NonNull Utils.Consumer<String> consumer) {
        if (consumer != null) {
            return b.a((Utils.Task) new Utils.Task<String>(consumer) { // from class: com.blankj.utilcode.util.NetworkUtils.5
                @RequiresPermission("android.permission.INTERNET")
                /* renamed from: a */
                public String doInBackground() {
                    return NetworkUtils.getIPAddress(z);
                }
            });
        }
        throw new NullPointerException("Argument 'consumer' of type Utils.Consumer<String> (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    @RequiresPermission("android.permission.INTERNET")
    public static String getIPAddress(boolean z) {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            LinkedList linkedList = new LinkedList();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface nextElement = networkInterfaces.nextElement();
                if (nextElement.isUp() && !nextElement.isLoopback()) {
                    Enumeration<InetAddress> inetAddresses = nextElement.getInetAddresses();
                    while (inetAddresses.hasMoreElements()) {
                        linkedList.addFirst(inetAddresses.nextElement());
                    }
                }
            }
            Iterator it = linkedList.iterator();
            while (it.hasNext()) {
                InetAddress inetAddress = (InetAddress) it.next();
                if (!inetAddress.isLoopbackAddress()) {
                    String hostAddress = inetAddress.getHostAddress();
                    boolean z2 = hostAddress.indexOf(58) < 0;
                    if (z) {
                        if (z2) {
                            return hostAddress;
                        }
                    } else if (!z2) {
                        int indexOf = hostAddress.indexOf(37);
                        if (indexOf < 0) {
                            return hostAddress.toUpperCase();
                        }
                        return hostAddress.substring(0, indexOf).toUpperCase();
                    }
                }
            }
            return "";
        } catch (SocketException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getBroadcastIpAddress() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            new LinkedList();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface nextElement = networkInterfaces.nextElement();
                if (nextElement.isUp() && !nextElement.isLoopback()) {
                    List<InterfaceAddress> interfaceAddresses = nextElement.getInterfaceAddresses();
                    int size = interfaceAddresses.size();
                    for (int i = 0; i < size; i++) {
                        InetAddress broadcast = interfaceAddresses.get(i).getBroadcast();
                        if (broadcast != null) {
                            return broadcast.getHostAddress();
                        }
                    }
                    continue;
                }
            }
            return "";
        } catch (SocketException e) {
            e.printStackTrace();
            return "";
        }
    }

    @RequiresPermission("android.permission.INTERNET")
    public static Utils.Task<String> getDomainAddressAsync(final String str, @NonNull Utils.Consumer<String> consumer) {
        if (consumer != null) {
            return b.a((Utils.Task) new Utils.Task<String>(consumer) { // from class: com.blankj.utilcode.util.NetworkUtils.6
                @RequiresPermission("android.permission.INTERNET")
                /* renamed from: a */
                public String doInBackground() {
                    return NetworkUtils.getDomainAddress(str);
                }
            });
        }
        throw new NullPointerException("Argument 'consumer' of type Utils.Consumer<String> (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    @RequiresPermission("android.permission.INTERNET")
    public static String getDomainAddress(String str) {
        try {
            return InetAddress.getByName(str).getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "";
        }
    }

    @RequiresPermission("android.permission.ACCESS_WIFI_STATE")
    public static String getIpAddressByWifi() {
        WifiManager wifiManager = (WifiManager) Utils.getApp().getSystemService(Network.NETWORK_TYPE_WIFI);
        return wifiManager == null ? "" : Formatter.formatIpAddress(wifiManager.getDhcpInfo().ipAddress);
    }

    @RequiresPermission("android.permission.ACCESS_WIFI_STATE")
    public static String getGatewayByWifi() {
        WifiManager wifiManager = (WifiManager) Utils.getApp().getSystemService(Network.NETWORK_TYPE_WIFI);
        return wifiManager == null ? "" : Formatter.formatIpAddress(wifiManager.getDhcpInfo().gateway);
    }

    @RequiresPermission("android.permission.ACCESS_WIFI_STATE")
    public static String getNetMaskByWifi() {
        WifiManager wifiManager = (WifiManager) Utils.getApp().getSystemService(Network.NETWORK_TYPE_WIFI);
        return wifiManager == null ? "" : Formatter.formatIpAddress(wifiManager.getDhcpInfo().netmask);
    }

    @RequiresPermission("android.permission.ACCESS_WIFI_STATE")
    public static String getServerAddressByWifi() {
        WifiManager wifiManager = (WifiManager) Utils.getApp().getSystemService(Network.NETWORK_TYPE_WIFI);
        return wifiManager == null ? "" : Formatter.formatIpAddress(wifiManager.getDhcpInfo().serverAddress);
    }

    @RequiresPermission("android.permission.ACCESS_WIFI_STATE")
    public static String getSSID() {
        WifiInfo connectionInfo;
        WifiManager wifiManager = (WifiManager) Utils.getApp().getApplicationContext().getSystemService(Network.NETWORK_TYPE_WIFI);
        if (wifiManager == null || (connectionInfo = wifiManager.getConnectionInfo()) == null) {
            return "";
        }
        String ssid = connectionInfo.getSSID();
        return TextUtils.isEmpty(ssid) ? "" : (ssid.length() > 2 && ssid.charAt(0) == '\"' && ssid.charAt(ssid.length() - 1) == '\"') ? ssid.substring(1, ssid.length() - 1) : ssid;
    }

    @RequiresPermission("android.permission.ACCESS_NETWORK_STATE")
    public static void registerNetworkStatusChangedListener(OnNetworkStatusChangedListener onNetworkStatusChangedListener) {
        NetworkChangedReceiver.b().a(onNetworkStatusChangedListener);
    }

    public static boolean isRegisteredNetworkStatusChangedListener(OnNetworkStatusChangedListener onNetworkStatusChangedListener) {
        return NetworkChangedReceiver.b().b(onNetworkStatusChangedListener);
    }

    public static void unregisterNetworkStatusChangedListener(OnNetworkStatusChangedListener onNetworkStatusChangedListener) {
        NetworkChangedReceiver.b().c(onNetworkStatusChangedListener);
    }

    @RequiresPermission(allOf = {"android.permission.ACCESS_WIFI_STATE", "android.permission.ACCESS_COARSE_LOCATION"})
    public static WifiScanResults getWifiScanResult() {
        List<ScanResult> scanResults;
        WifiScanResults wifiScanResults = new WifiScanResults();
        if (getWifiEnabled() && (scanResults = ((WifiManager) Utils.getApp().getSystemService(Network.NETWORK_TYPE_WIFI)).getScanResults()) != null) {
            wifiScanResults.setAllResults(scanResults);
        }
        return wifiScanResults;
    }

    @RequiresPermission(allOf = {"android.permission.ACCESS_WIFI_STATE", "android.permission.CHANGE_WIFI_STATE", "android.permission.ACCESS_COARSE_LOCATION"})
    public static void addOnWifiChangedConsumer(final Utils.Consumer<WifiScanResults> consumer) {
        if (consumer != null) {
            b.a(new Runnable() { // from class: com.blankj.utilcode.util.NetworkUtils.7
                @Override // java.lang.Runnable
                public void run() {
                    if (NetworkUtils.a.isEmpty()) {
                        NetworkUtils.a.add(Utils.Consumer.this);
                        NetworkUtils.h();
                        return;
                    }
                    Utils.Consumer.this.accept(NetworkUtils.c);
                    NetworkUtils.a.add(Utils.Consumer.this);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void h() {
        c = new WifiScanResults();
        b = new Timer();
        b.schedule(new TimerTask() { // from class: com.blankj.utilcode.util.NetworkUtils.8
            @Override // java.util.TimerTask, java.lang.Runnable
            @RequiresPermission(allOf = {"android.permission.ACCESS_WIFI_STATE", "android.permission.CHANGE_WIFI_STATE", "android.permission.ACCESS_COARSE_LOCATION"})
            public void run() {
                NetworkUtils.i();
                WifiScanResults wifiScanResult = NetworkUtils.getWifiScanResult();
                if (!NetworkUtils.b(NetworkUtils.c.a, wifiScanResult.a)) {
                    WifiScanResults unused = NetworkUtils.c = wifiScanResult;
                    b.a(new Runnable() { // from class: com.blankj.utilcode.util.NetworkUtils.8.1
                        @Override // java.lang.Runnable
                        public void run() {
                            for (Utils.Consumer consumer : NetworkUtils.a) {
                                consumer.accept(NetworkUtils.c);
                            }
                        }
                    });
                }
            }
        }, 0L, 3000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresPermission(allOf = {"android.permission.ACCESS_WIFI_STATE", "android.permission.CHANGE_WIFI_STATE"})
    public static void i() {
        if (getWifiEnabled()) {
            ((WifiManager) Utils.getApp().getSystemService(Network.NETWORK_TYPE_WIFI)).startScan();
        }
    }

    public static void removeOnWifiChangedConsumer(final Utils.Consumer<WifiScanResults> consumer) {
        if (consumer != null) {
            b.a(new Runnable() { // from class: com.blankj.utilcode.util.NetworkUtils.9
                @Override // java.lang.Runnable
                public void run() {
                    NetworkUtils.a.remove(Utils.Consumer.this);
                    if (NetworkUtils.a.isEmpty()) {
                        NetworkUtils.j();
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void j() {
        Timer timer = b;
        if (timer != null) {
            timer.cancel();
            b = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean b(List<ScanResult> list, List<ScanResult> list2) {
        if (list == null && list2 == null) {
            return true;
        }
        if (list == null || list2 == null || list.size() != list2.size()) {
            return false;
        }
        for (int i = 0; i < list.size(); i++) {
            if (!a(list.get(i), list2.get(i))) {
                return false;
            }
        }
        return true;
    }

    private static boolean a(ScanResult scanResult, ScanResult scanResult2) {
        return scanResult != null && scanResult2 != null && b.a((CharSequence) scanResult.BSSID, (CharSequence) scanResult2.BSSID) && b.a((CharSequence) scanResult.SSID, (CharSequence) scanResult2.SSID) && b.a((CharSequence) scanResult.capabilities, (CharSequence) scanResult2.capabilities) && scanResult.level == scanResult2.level;
    }

    /* loaded from: classes.dex */
    public static final class NetworkChangedReceiver extends BroadcastReceiver {
        private NetworkType a;
        private Set<OnNetworkStatusChangedListener> b = new HashSet();

        /* JADX INFO: Access modifiers changed from: private */
        public static NetworkChangedReceiver b() {
            return a.a;
        }

        @RequiresPermission("android.permission.ACCESS_NETWORK_STATE")
        void a(final OnNetworkStatusChangedListener onNetworkStatusChangedListener) {
            if (onNetworkStatusChangedListener != null) {
                b.a(new Runnable() { // from class: com.blankj.utilcode.util.NetworkUtils.NetworkChangedReceiver.1
                    @Override // java.lang.Runnable
                    @RequiresPermission("android.permission.ACCESS_NETWORK_STATE")
                    public void run() {
                        int size = NetworkChangedReceiver.this.b.size();
                        NetworkChangedReceiver.this.b.add(onNetworkStatusChangedListener);
                        if (size == 0 && NetworkChangedReceiver.this.b.size() == 1) {
                            NetworkChangedReceiver.this.a = NetworkUtils.getNetworkType();
                            Utils.getApp().registerReceiver(NetworkChangedReceiver.b(), new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
                        }
                    }
                });
            }
        }

        boolean b(OnNetworkStatusChangedListener onNetworkStatusChangedListener) {
            if (onNetworkStatusChangedListener == null) {
                return false;
            }
            return this.b.contains(onNetworkStatusChangedListener);
        }

        void c(final OnNetworkStatusChangedListener onNetworkStatusChangedListener) {
            if (onNetworkStatusChangedListener != null) {
                b.a(new Runnable() { // from class: com.blankj.utilcode.util.NetworkUtils.NetworkChangedReceiver.2
                    @Override // java.lang.Runnable
                    public void run() {
                        int size = NetworkChangedReceiver.this.b.size();
                        NetworkChangedReceiver.this.b.remove(onNetworkStatusChangedListener);
                        if (size == 1 && NetworkChangedReceiver.this.b.size() == 0) {
                            Utils.getApp().unregisterReceiver(NetworkChangedReceiver.b());
                        }
                    }
                });
            }
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
                b.a(new Runnable() { // from class: com.blankj.utilcode.util.NetworkUtils.NetworkChangedReceiver.3
                    @Override // java.lang.Runnable
                    @RequiresPermission("android.permission.ACCESS_NETWORK_STATE")
                    public void run() {
                        NetworkType networkType = NetworkUtils.getNetworkType();
                        if (NetworkChangedReceiver.this.a != networkType) {
                            NetworkChangedReceiver.this.a = networkType;
                            if (networkType == NetworkType.NETWORK_NO) {
                                for (OnNetworkStatusChangedListener onNetworkStatusChangedListener : NetworkChangedReceiver.this.b) {
                                    onNetworkStatusChangedListener.onDisconnected();
                                }
                                return;
                            }
                            for (OnNetworkStatusChangedListener onNetworkStatusChangedListener2 : NetworkChangedReceiver.this.b) {
                                onNetworkStatusChangedListener2.onConnected(networkType);
                            }
                        }
                    }
                }, 1000L);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes.dex */
        public static class a {
            private static final NetworkChangedReceiver a = new NetworkChangedReceiver();
        }
    }

    /* loaded from: classes.dex */
    public static final class WifiScanResults {
        private List<ScanResult> a = new ArrayList();
        private List<ScanResult> b = new ArrayList();

        public List<ScanResult> getAllResults() {
            return this.a;
        }

        public List<ScanResult> getFilterResults() {
            return this.b;
        }

        public void setAllResults(List<ScanResult> list) {
            this.a = list;
            this.b = a(list);
        }

        private static List<ScanResult> a(List<ScanResult> list) {
            ScanResult scanResult;
            if (list == null || list.isEmpty()) {
                return new ArrayList();
            }
            LinkedHashMap linkedHashMap = new LinkedHashMap(list.size());
            for (ScanResult scanResult2 : list) {
                if (!TextUtils.isEmpty(scanResult2.SSID) && ((scanResult = (ScanResult) linkedHashMap.get(scanResult2.SSID)) == null || scanResult.level < scanResult2.level)) {
                    linkedHashMap.put(scanResult2.SSID, scanResult2);
                }
            }
            return new ArrayList(linkedHashMap.values());
        }
    }
}
