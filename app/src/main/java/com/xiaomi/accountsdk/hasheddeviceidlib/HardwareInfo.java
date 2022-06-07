package com.xiaomi.accountsdk.hasheddeviceidlib;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.text.TextUtils;
import com.xiaomi.accountsdk.utils.AccountLog;
import com.xiaomi.smarthome.library.common.network.Network;

/* loaded from: classes2.dex */
public class HardwareInfo {
    private static final String DEFAULT_MAC_ADDRESS = "0";
    private static final String FAKE_MAC_ADDRESS = "02:00:00:00:00:00";
    private static final String TAG = "UserEnvironment";

    public static String getWifiMacAddress(Context context) {
        String wifiMacAddressHack = getWifiMacAddressHack();
        if (!TextUtils.isEmpty(wifiMacAddressHack) && !wifiMacAddressHack.equals("0")) {
            return wifiMacAddressHack;
        }
        if (context == null) {
            return "0";
        }
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Network.NETWORK_TYPE_WIFI);
            if (wifiManager.getConnectionInfo() == null) {
                return "0";
            }
            String macAddress = wifiManager.getConnectionInfo().getMacAddress();
            return !TextUtils.isEmpty(macAddress) ? !macAddress.equals(FAKE_MAC_ADDRESS) ? macAddress : "0" : "0";
        } catch (SecurityException e) {
            AccountLog.e(TAG, "failed to get Mac Address", e);
            return "0";
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0028, code lost:
        r0 = r1.getHardwareAddress();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.lang.String getWifiMacAddressHack() {
        /*
            java.util.Enumeration r0 = java.net.NetworkInterface.getNetworkInterfaces()     // Catch: SocketException -> 0x0067
            if (r0 != 0) goto L_0x0009
            java.lang.String r0 = "0"
            return r0
        L_0x0009:
            boolean r1 = r0.hasMoreElements()     // Catch: SocketException -> 0x0067
            if (r1 == 0) goto L_0x002d
            java.lang.Object r1 = r0.nextElement()     // Catch: SocketException -> 0x0067
            java.net.NetworkInterface r1 = (java.net.NetworkInterface) r1     // Catch: SocketException -> 0x0067
            java.lang.String r2 = r1.getName()     // Catch: SocketException -> 0x0067
            if (r2 == 0) goto L_0x0009
            java.lang.String r2 = r1.getName()     // Catch: SocketException -> 0x0067
            java.lang.String r3 = "wlan0"
            boolean r2 = r2.equalsIgnoreCase(r3)     // Catch: SocketException -> 0x0067
            if (r2 == 0) goto L_0x0009
            byte[] r0 = r1.getHardwareAddress()     // Catch: SocketException -> 0x0067
            goto L_0x002e
        L_0x002d:
            r0 = 0
        L_0x002e:
            if (r0 != 0) goto L_0x0033
            java.lang.String r0 = "0"
            return r0
        L_0x0033:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            int r2 = r0.length
            r3 = 0
            r4 = r3
        L_0x003b:
            r5 = 1
            if (r4 >= r2) goto L_0x0054
            byte r6 = r0[r4]
            java.lang.String r7 = "%02X:"
            java.lang.Object[] r5 = new java.lang.Object[r5]
            java.lang.Byte r6 = java.lang.Byte.valueOf(r6)
            r5[r3] = r6
            java.lang.String r5 = java.lang.String.format(r7, r5)
            r1.append(r5)
            int r4 = r4 + 1
            goto L_0x003b
        L_0x0054:
            int r0 = r1.length()
            if (r0 <= 0) goto L_0x0062
            int r0 = r1.length()
            int r0 = r0 - r5
            r1.deleteCharAt(r0)
        L_0x0062:
            java.lang.String r0 = r1.toString()
            return r0
        L_0x0067:
            r0 = move-exception
            java.lang.String r1 = "UserEnvironment"
            java.lang.String r2 = "failed to get wifi Mac Address"
            com.xiaomi.accountsdk.utils.AccountLog.e(r1, r2, r0)
            java.lang.String r0 = "0"
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.accountsdk.hasheddeviceidlib.HardwareInfo.getWifiMacAddressHack():java.lang.String");
    }

    public static String getBluetoothMacAddress(Context context) {
        String bluetoothMacAddressHack = getBluetoothMacAddressHack(context);
        if (!TextUtils.isEmpty(bluetoothMacAddressHack)) {
            return bluetoothMacAddressHack;
        }
        try {
            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            if (defaultAdapter == null) {
                return "0";
            }
            String address = defaultAdapter.getAddress();
            return !TextUtils.isEmpty(address) ? address.equals(FAKE_MAC_ADDRESS) ? "0" : address : "0";
        } catch (SecurityException e) {
            AccountLog.e(TAG, "failed to get bluetooth id", e);
            return "0";
        }
    }

    private static String getBluetoothMacAddressHack(Context context) {
        if (context == null) {
            return null;
        }
        return Settings.Secure.getString(context.getContentResolver(), "bluetooth_address");
    }
}
