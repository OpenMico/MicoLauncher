package com.xiaomi.miot.host.lan.impl.util;

import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import com.xiaomi.idm.api.IDMServer;
import com.xiaomi.miot.host.lan.impl.MiotLogUtil;
import com.xiaomi.miot.host.lan.impl.MiotStore;
import com.xiaomi.miot.typedef.device.Device;
import com.xiaomi.smarthome.library.common.network.Network;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class MiotInfoManager {
    private static final String DEFAULT_MAC_ADDRESS = "02:00:00:00:00:00";
    private static final String EMPTY_MAC_ADDRESS = "00:00:00:00:00:00";

    public static OTInfo retrieveMiotInfo(Device device, Context context) {
        MiotStore miotStore = new MiotStore(context);
        OTInfo oTInfo = new OTInfo();
        if (device != null) {
            long j = 0;
            try {
                j = Long.valueOf(device.getDeviceId()).longValue();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            oTInfo.setDid(j);
            oTInfo.setKey(device.getMiotToken());
            oTInfo.setMac(device.getMacAddress());
            oTInfo.setModel(device.getModelName());
            oTInfo.setMmfree(getAvailableMemory(context));
            oTInfo.setHw_ver("Android");
            try {
                JSONObject optJSONObject = new JSONObject(device.getMiotInfo()).optJSONObject("params");
                String optString = optJSONObject.optString(MiotStore.COUNTRYDOMAIN);
                String optString2 = optJSONObject.optString("fw_ver");
                if (TextUtils.isEmpty(optString)) {
                    optString = miotStore.loadcountryDomain();
                } else {
                    miotStore.saveCountryDomain(optString);
                }
                oTInfo.setCountry_domain(optString);
                oTInfo.setFw_ver(optString2);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            try {
                WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Network.NETWORK_TYPE_WIFI);
                if (wifiManager != null) {
                    WifiInfo connectionInfo = wifiManager.getConnectionInfo();
                    DhcpInfo dhcpInfo = wifiManager.getDhcpInfo();
                    if (connectionInfo != null) {
                        String ssid = connectionInfo.getSSID();
                        if (!TextUtils.isEmpty(ssid) && ssid.contains("unknown ssid")) {
                            ssid = "";
                        }
                        oTInfo.setRssi(connectionInfo.getRssi());
                        MiotLogUtil.d("MiotHost", "sdk java get ssid: " + ssid + ", and wifi ssid: " + connectionInfo.getSSID());
                        oTInfo.setSsid(ssid);
                        oTInfo.setBssid(connectionInfo.getBSSID());
                        if (Build.VERSION.SDK_INT >= 21) {
                            oTInfo.setFreq(connectionInfo.getFrequency());
                        }
                    }
                    if (dhcpInfo != null) {
                        oTInfo.setLocalIp(intToIp(dhcpInfo.ipAddress));
                        oTInfo.setMask(intToIp(dhcpInfo.netmask));
                        oTInfo.setGw(intToIp(dhcpInfo.gateway));
                    }
                }
                NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
                if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                    try {
                        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
                        while (networkInterfaces != null) {
                            if (!networkInterfaces.hasMoreElements()) {
                                break;
                            }
                            List<InterfaceAddress> interfaceAddresses = networkInterfaces.nextElement().getInterfaceAddresses();
                            if (interfaceAddresses != null) {
                                for (int i = 0; i < interfaceAddresses.size(); i++) {
                                    InetAddress address = interfaceAddresses.get(i).getAddress();
                                    if (!address.isLoopbackAddress() && (address instanceof Inet4Address)) {
                                        String hostAddress = address.getHostAddress();
                                        oTInfo.setLocalIp(hostAddress);
                                        String calcMaskByPrefixLength = calcMaskByPrefixLength(interfaceAddresses.get(i).getNetworkPrefixLength());
                                        oTInfo.setMask(calcMaskByPrefixLength);
                                        String calcSubnetAddress = calcSubnetAddress(hostAddress, calcMaskByPrefixLength);
                                        oTInfo.setGw(calcSubnetAddress);
                                        MiotLogUtil.d("MiotHost", "ip:" + hostAddress + ", mask:" + calcMaskByPrefixLength + ", gateway:" + calcSubnetAddress);
                                    }
                                }
                            }
                        }
                    } catch (SocketException e3) {
                        e3.printStackTrace();
                    }
                }
            } catch (Exception e4) {
                e4.printStackTrace();
            }
            oTInfo.setBleMac(getBluetoothMacAddress(context));
        }
        return oTInfo;
    }

    public static String calcMaskByPrefixLength(int i) {
        int i2;
        int i3 = (-1) << (32 - i);
        int[] iArr = new int[4];
        int i4 = 0;
        while (true) {
            if (i4 >= iArr.length) {
                break;
            }
            iArr[(iArr.length - 1) - i4] = (i3 >> (i4 * 8)) & 255;
            i4++;
        }
        String str = "" + iArr[0];
        for (i2 = 1; i2 < iArr.length; i2++) {
            str = str + "." + iArr[i2];
        }
        return str;
    }

    public static String calcSubnetAddress(String str, String str2) {
        String str3 = "";
        try {
            InetAddress byName = InetAddress.getByName(str);
            InetAddress byName2 = InetAddress.getByName(str2);
            byte[] address = byName.getAddress();
            byte[] address2 = byName2.getAddress();
            int[] iArr = new int[address.length];
            for (int i = 0; i < iArr.length; i++) {
                iArr[i] = address[i] & address2[i] & 255;
            }
            str3 = str3 + iArr[0];
            for (int i2 = 1; i2 < iArr.length; i2++) {
                str3 = str3 + "." + iArr[i2];
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return str3;
    }

    private static int getAvailableMemory(Context context) {
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        ((ActivityManager) context.getSystemService(IDMServer.PERSIST_TYPE_ACTIVITY)).getMemoryInfo(memoryInfo);
        return (int) (memoryInfo.availMem / 1048576);
    }

    public static String getFirmwareVersion(Context context) {
        int i = 0;
        try {
            i = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "4.3.4_" + i;
    }

    public static String getBluetoothMacAddress(Context context) {
        BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService("bluetooth");
        String str = "";
        if (bluetoothManager != null) {
            BluetoothAdapter adapter = bluetoothManager.getAdapter();
            if (adapter != null) {
                str = adapter.getAddress();
            }
            if (TextUtils.isEmpty(str) || TextUtils.equals(str, DEFAULT_MAC_ADDRESS)) {
                str = Settings.Secure.getString(context.getContentResolver(), "bluetooth_address");
            }
        }
        if (str == null) {
            str = "";
        }
        return (TextUtils.equals(str, EMPTY_MAC_ADDRESS) || TextUtils.equals(str, DEFAULT_MAC_ADDRESS)) ? "" : str;
    }

    private static String intToIp(int i) {
        return (i & 255) + "." + ((i >> 8) & 255) + "." + ((i >> 16) & 255) + "." + ((i >> 24) & 255);
    }

    public static boolean isWifiOpened(Context context) {
        NetworkInfo activeNetworkInfo;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        return (connectivityManager == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null || activeNetworkInfo.getType() != 1) ? false : true;
    }
}
