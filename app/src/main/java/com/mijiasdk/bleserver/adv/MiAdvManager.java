package com.mijiasdk.bleserver.adv;

import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.os.Build;
import android.os.ParcelUuid;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;

/* loaded from: classes2.dex */
public class MiAdvManager {
    public static final int ADVERTISE_MODE_LOW_LATENCY = 2;
    public static final int ADVERTISE_TX_POWER_HIGH = 3;

    @Deprecated
    public static AdvertiseData setActiveAdvertiseDataForMiJia() {
        ParcelUuid fromString = ParcelUuid.fromString("0000FE95-0000-1000-8000-00805F9B34FB");
        ByteBuffer order = ByteBuffer.wrap(new byte[22]).order(ByteOrder.LITTLE_ENDIAN);
        order.put(new byte[]{88, 112});
        order.put(new byte[]{8, -18});
        order.put((byte) 2);
        order.put((byte) 113);
        order.put((byte) -64);
        order.put((byte) -51);
        order.put((byte) 45);
        order.put((byte) 24);
        order.put((byte) 93);
        order.put((byte) 24);
        order.put((byte) 24);
        order.put((byte) 95);
        order.put((byte) -113);
        order.put((byte) 3);
        order.put((byte) -110);
        order.put((byte) 0);
        order.put((byte) 0);
        order.put((byte) 0);
        order.put((byte) 0);
        order.put((byte) 0);
        return new AdvertiseData.Builder().setIncludeDeviceName(false).setIncludeTxPowerLevel(false).addServiceUuid(fromString).addServiceData(fromString, order.array()).build();
    }

    public static void startAdvertising(BluetoothLeAdvertiser bluetoothLeAdvertiser, AdvertiseCallback advertiseCallback) {
        if (bluetoothLeAdvertiser != null && Build.VERSION.SDK_INT >= 21) {
            AdvertiseSettings build = new AdvertiseSettings.Builder().setAdvertiseMode(2).setTxPowerLevel(3).setTimeout(0).setConnectable(true).build();
            ByteBuffer order = ByteBuffer.wrap(new byte[16]).order(ByteOrder.LITTLE_ENDIAN);
            order.put((byte) 48);
            order.put((byte) 56);
            order.put(new byte[]{-18, 8});
            order.put((byte) 0);
            order.put((byte) 17);
            order.put((byte) 34);
            order.put((byte) 51);
            order.put((byte) 68);
            order.put((byte) 85);
            order.put((byte) 102);
            order.put((byte) 24);
            order.putShort((short) -103);
            bluetoothLeAdvertiser.startAdvertising(build, new AdvertiseData.Builder().setIncludeDeviceName(false).setIncludeTxPowerLevel(false).addServiceData(new ParcelUuid(UUID.fromString("0000fe95-0000-1000-8000-00805f9b34fb")), order.array()).build(), null, advertiseCallback);
        }
    }

    public static void stopAdvertising(BluetoothLeAdvertiser bluetoothLeAdvertiser, AdvertiseCallback advertiseCallback) {
        if (bluetoothLeAdvertiser != null && Build.VERSION.SDK_INT >= 21) {
            bluetoothLeAdvertiser.stopAdvertising(advertiseCallback);
        }
    }
}
