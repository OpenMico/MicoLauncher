package com.mijiasdk.bleserver.service;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.util.Log;
import com.mijiasdk.bleserver.api.ConfigRouterCallBack;
import com.mijiasdk.bleserver.config.BleStandAuthGattCallback;
import com.mijiasdk.bleserver.util.BluetoothConstants;
import com.xiaomi.smarthome.devicelibrary.bluetooth.utils.BluetoothUtils;
import java.util.UUID;

/* loaded from: classes2.dex */
public class BleServerImpl {
    private static volatile BleServerImpl a;
    private Context b;
    private BluetoothManager c;
    private BluetoothAdapter d;
    private BluetoothGattServer e;
    private BleStandAuthGattCallback f;

    /* loaded from: classes2.dex */
    public interface SendDataCallback {
        void onResult(boolean z);
    }

    private BleServerImpl() {
    }

    public static BleServerImpl getInstance() {
        if (a == null) {
            synchronized (BleServerImpl.class) {
                if (a == null) {
                    a = new BleServerImpl();
                }
            }
        }
        return a;
    }

    public boolean initBluetooth(Context context, String str, String str2, ConfigRouterCallBack configRouterCallBack) {
        this.b = context.getApplicationContext();
        BluetoothUtils.setContext(context);
        if (this.c == null) {
            this.c = (BluetoothManager) this.b.getApplicationContext().getSystemService("bluetooth");
        }
        BluetoothManager bluetoothManager = this.c;
        if (bluetoothManager != null) {
            this.d = bluetoothManager.getAdapter();
        }
        BluetoothAdapter bluetoothAdapter = this.d;
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            Log.e("BleServerImpl", "Bluetooth needs to be opened!");
            return false;
        } else if (this.e == null) {
            this.f = new BleStandAuthGattCallback(str, str2, configRouterCallBack);
            this.e = this.c.openGattServer(this.b, this.f);
            BluetoothGattServer bluetoothGattServer = this.e;
            if (bluetoothGattServer == null) {
                Log.e("BleServerImpl", "gatt is null");
                return false;
            }
            this.f.setGattServer(bluetoothGattServer);
            a();
            return true;
        } else {
            Log.d("BleServerImpl", "has initialized");
            return true;
        }
    }

    private void a() {
        BluetoothGattService bluetoothGattService = new BluetoothGattService(BluetoothConstants.MISERVICE, 0);
        bluetoothGattService.addCharacteristic(new BluetoothGattCharacteristic(BluetoothConstants.CHARACTER_FIRMWARE_VERSION, 2, 1));
        BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(BluetoothConstants.CHARACTER_WIFIAPSSID, 16, 16);
        bluetoothGattCharacteristic.addDescriptor(new BluetoothGattDescriptor(BluetoothConstants.UUID_NOTIFY, 16));
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);
        BluetoothGattCharacteristic bluetoothGattCharacteristic2 = new BluetoothGattCharacteristic(BluetoothConstants.CHARACTER_EVENT, 20, 16);
        bluetoothGattCharacteristic2.addDescriptor(new BluetoothGattDescriptor(BluetoothConstants.UUID_NOTIFY, 16));
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic2);
        BluetoothGattCharacteristic bluetoothGattCharacteristic3 = new BluetoothGattCharacteristic(BluetoothConstants.CHARACTER_STANDARD_AUTH, 20, 16);
        bluetoothGattCharacteristic3.addDescriptor(new BluetoothGattDescriptor(BluetoothConstants.UUID_NOTIFY, 16));
        bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic3);
        this.e.addService(bluetoothGattService);
    }

    public void closeGattServer() {
        Log.d("BleServerImpl", "closeGattServer");
        if (this.e != null) {
            BleStandAuthGattCallback bleStandAuthGattCallback = this.f;
            if (bleStandAuthGattCallback != null) {
                bleStandAuthGattCallback.disconnect();
                this.f.setGattServer(null);
            }
            this.e.close();
            this.e = null;
        }
    }

    public BluetoothAdapter getBluetoothAdapter() {
        return this.d;
    }

    public void sendData(String str, UUID uuid, UUID uuid2, byte[] bArr, SendDataCallback sendDataCallback) {
        BleStandAuthGattCallback bleStandAuthGattCallback;
        if (this.e == null || (bleStandAuthGattCallback = this.f) == null) {
            Log.e("BleServerImpl", "sendData failed, Connection has been dropped");
            if (sendDataCallback != null) {
                sendDataCallback.onResult(false);
                return;
            }
            return;
        }
        bleStandAuthGattCallback.sendData(str, uuid, uuid2, bArr, sendDataCallback);
    }

    public void sendRouterConnectedResult(boolean z) {
        BleStandAuthGattCallback bleStandAuthGattCallback;
        if (this.e == null || (bleStandAuthGattCallback = this.f) == null) {
            Log.e("BleServerImpl", "sendRouterConnectedResult failed, Connection has been dropped");
        } else {
            bleStandAuthGattCallback.sendRouterConnectedResult(z);
        }
    }

    public void notifyPassportAuthSuccess() {
        BleStandAuthGattCallback bleStandAuthGattCallback;
        if (this.e == null || (bleStandAuthGattCallback = this.f) == null) {
            Log.e("BleServerImpl", "notifyPassportAuthSuccess failed, Connection has been dropped");
        } else {
            bleStandAuthGattCallback.notifyPassportAuthSuccess();
        }
    }

    public void notifyOTServerConnected() {
        BleStandAuthGattCallback bleStandAuthGattCallback;
        if (this.e == null || (bleStandAuthGattCallback = this.f) == null) {
            Log.e("BleServerImpl", "connectOTServer failed, Connection has been dropped");
        } else {
            bleStandAuthGattCallback.notifyOTServerConnected();
        }
    }

    public void destroy() {
        closeGattServer();
        this.d = null;
        this.c = null;
    }
}
