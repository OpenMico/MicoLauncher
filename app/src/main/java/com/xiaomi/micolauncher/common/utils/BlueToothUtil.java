package com.xiaomi.micolauncher.common.utils;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import com.xiaomi.mipush.sdk.Constants;
import java.util.Set;

/* loaded from: classes3.dex */
public class BlueToothUtil {
    public static String getConnectedBluetoothDeviceAddressOnlyCase() {
        Set<BluetoothDevice> bondedDevices = BluetoothAdapter.getDefaultAdapter().getBondedDevices();
        if (bondedDevices.size() <= 0) {
            return null;
        }
        for (BluetoothDevice bluetoothDevice : bondedDevices) {
            if (bluetoothDevice.isConnected() && bluetoothDevice.getAddress() != null) {
                return bluetoothDevice.getAddress().replace(Constants.COLON_SEPARATOR, "").toUpperCase();
            }
        }
        return null;
    }
}
