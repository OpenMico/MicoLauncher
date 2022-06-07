package com.xiaomi.micolauncher.module.setting.bluetooth.lib;

import android.bluetooth.BluetoothDevice;

/* loaded from: classes3.dex */
public interface BluetoothCallback {
    void onBluetoothStateChanged(int i);

    void onConnectionStateChanged(BluetoothDevice bluetoothDevice, int i);

    void onDeviceAdded(BluetoothDevice bluetoothDevice);

    void onDeviceBondStateChanged(BluetoothDevice bluetoothDevice, int i);

    void onDeviceDeleted(BluetoothDevice bluetoothDevice);

    void onScanedBluetoothDevice(BluetoothDevice bluetoothDevice);

    void onScanningStateChanged(boolean z);
}
