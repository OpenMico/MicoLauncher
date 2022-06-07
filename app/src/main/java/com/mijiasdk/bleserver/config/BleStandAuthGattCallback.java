package com.mijiasdk.bleserver.config;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothGattServerCallback;
import android.bluetooth.BluetoothGattService;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import com.google.common.primitives.SignedBytes;
import com.mijiasdk.bleserver.api.BleServerManager;
import com.mijiasdk.bleserver.api.ConfigRouterCallBack;
import com.mijiasdk.bleserver.protocol.BleComboStandardAuth;
import com.mijiasdk.bleserver.protocol.BleComboWifiConfig;
import com.mijiasdk.bleserver.service.BleServerImpl;
import com.mijiasdk.bleserver.util.BluetoothConstants;
import com.xiaomi.smarthome.core.server.bluetooth.IBleChannelReader;
import com.xiaomi.smarthome.core.server.bluetooth.IBleChannelWriter;
import com.xiaomi.smarthome.core.server.bluetooth.IBleResponse;
import com.xiaomi.smarthome.core.server.internal.bluetooth.channel.ChannelManager;
import com.xiaomi.smarthome.core.server.internal.bluetooth.channel.StandardAuthChannelManager;
import com.xiaomi.smarthome.devicelibrary.bluetooth.channel.ChannelState;
import com.xiaomi.smarthome.devicelibrary.bluetooth.channel.packet.MNGPacket;
import com.xiaomi.smarthome.devicelibrary.bluetooth.utils.BluetoothLog;
import com.xiaomi.smarthome.devicelibrary.common.util.ByteUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/* loaded from: classes2.dex */
public class BleStandAuthGattCallback extends BluetoothGattServerCallback {
    public static final int DEVICE_SUPPORT_MTU = 64;
    public static final int NOTIFY_AUTHENTICATION_ERROR = 5;
    public static final int NOTIFY_CONNECTING_ROUTER = 1;
    public static final int NOTIFY_PASSPORT_AUTH_SUCCESS = 6;
    public static final int NOTIFY_ROUTER_CONNECTED = 2;
    public static final int NOTIFY_SERVER_CONNECTED = 3;
    public static final int NOTIFY_START = 0;
    public static final int NOTIFY_UNKNOWN_ERROR = 4;
    private static final Handler a = new Handler(Looper.getMainLooper());
    private BluetoothGattServer b;
    private ConfigRouterCallBack c;
    private IBleChannelWriter d;
    private BleComboStandardAuth e;
    private String f;
    private String g;
    private BluetoothDevice h = null;
    private Map<String, BluetoothDevice> i = new HashMap();
    private int j = 0;
    private BleComboStandardAuth.AuthCallback k = new BleComboStandardAuth.AuthCallback() { // from class: com.mijiasdk.bleserver.config.BleStandAuthGattCallback.1
        @Override // com.mijiasdk.bleserver.protocol.BleComboStandardAuth.AuthCallback
        public void sendStandAuthData(byte[] bArr, final int i, final BleComboStandardAuth.SendAuthResponse sendAuthResponse) {
            Log.d("BleStandAuthGattCB", "sendStandAuthData packageType: " + i + ", value: " + BleServerManager.bytesToHexString(bArr));
            BleStandAuthGattCallback.this.a(bArr, i, new IBleResponse() { // from class: com.mijiasdk.bleserver.config.BleStandAuthGattCallback.1.1
                @Override // android.os.IInterface
                public IBinder asBinder() {
                    return null;
                }

                @Override // com.xiaomi.smarthome.core.server.bluetooth.IBleResponse
                public void onResponse(int i2, Bundle bundle) throws RemoteException {
                    Log.d("BleStandAuthGattCB", "RESPONSE sendStandAuthData packageType: " + i + ", result: " + i2);
                    BleComboStandardAuth.SendAuthResponse sendAuthResponse2 = sendAuthResponse;
                    if (sendAuthResponse2 != null) {
                        sendAuthResponse2.onResponse(i2, bundle);
                    }
                }
            });
        }

        @Override // com.mijiasdk.bleserver.protocol.BleComboStandardAuth.AuthCallback
        public void onAuthSuccess(BleComboWifiConfig bleComboWifiConfig) {
            Log.d("BleStandAuthGattCB", "onAuthSuccess");
            BleStandAuthGattCallback.this.j = 1;
            BleStandAuthGattCallback.a.removeCallbacks(BleStandAuthGattCallback.this.l);
            BleStandAuthGattCallback.a.post(BleStandAuthGattCallback.this.l);
            if (BleStandAuthGattCallback.this.c != null) {
                BleStandAuthGattCallback.this.c.onReceiveWifiConfig(bleComboWifiConfig);
            }
        }

        @Override // com.mijiasdk.bleserver.protocol.BleComboStandardAuth.AuthCallback
        public void onAuthFailed() {
            Log.d("BleStandAuthGattCB", "onAuthFailed");
            BleStandAuthGattCallback.this.j = 4;
            BleStandAuthGattCallback.a.removeCallbacks(BleStandAuthGattCallback.this.l);
            BleStandAuthGattCallback.a.post(BleStandAuthGattCallback.this.l);
        }
    };
    private Runnable l = new Runnable() { // from class: com.mijiasdk.bleserver.config.BleStandAuthGattCallback.2
        @Override // java.lang.Runnable
        public void run() {
            if (BleStandAuthGattCallback.this.h != null) {
                byte[] bArr = {(byte) BleStandAuthGattCallback.this.j};
                Log.d("BleStandAuthGattCB", "send mNotifyStatus: " + BleStandAuthGattCallback.this.j);
                BleStandAuthGattCallback bleStandAuthGattCallback = BleStandAuthGattCallback.this;
                bleStandAuthGattCallback.sendData(bleStandAuthGattCallback.h.getAddress(), BluetoothConstants.MISERVICE, BluetoothConstants.CHARACTER_WIFIAPSSID, bArr, new BleServerImpl.SendDataCallback() { // from class: com.mijiasdk.bleserver.config.BleStandAuthGattCallback.2.1
                    @Override // com.mijiasdk.bleserver.service.BleServerImpl.SendDataCallback
                    public void onResult(boolean z) {
                        BleStandAuthGattCallback.a.postDelayed(BleStandAuthGattCallback.this.l, 1000L);
                    }
                });
            }
        }
    };
    private IBleChannelReader m = new IBleChannelReader() { // from class: com.mijiasdk.bleserver.config.BleStandAuthGattCallback.3
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaomi.smarthome.core.server.bluetooth.IBleChannelReader
        public void onRead(String str, byte[] bArr, int i) throws RemoteException {
            BleStandAuthGattCallback.this.a(bArr, i);
        }
    };

    boolean a(byte[] bArr, int i, IBleResponse iBleResponse) {
        try {
            if (this.d == null) {
                return true;
            }
            this.d.write(bArr, i, iBleResponse);
            return true;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public BleStandAuthGattCallback(String str, String str2, ConfigRouterCallBack configRouterCallBack) {
        Log.d("BleStandAuthGattCB", "Version = 1.8");
        this.f = str;
        this.g = str2;
        this.c = configRouterCallBack;
    }

    public void setGattServer(BluetoothGattServer bluetoothGattServer) {
        this.b = bluetoothGattServer;
    }

    @Override // android.bluetooth.BluetoothGattServerCallback
    public void onConnectionStateChange(BluetoothDevice bluetoothDevice, int i, int i2) {
        Log.d("BleStandAuthGattCB", "onConnectionStateChange status = " + i + ", newState = " + i2 + ", mac = " + bluetoothDevice.getAddress());
        if (2 == i2) {
            this.i.put(bluetoothDevice.getAddress(), bluetoothDevice);
        } else if (i2 == 0) {
            BluetoothDevice bluetoothDevice2 = this.h;
            if (bluetoothDevice2 == null || !TextUtils.equals(bluetoothDevice2.getAddress(), bluetoothDevice.getAddress())) {
                BluetoothGattServer bluetoothGattServer = this.b;
                if (bluetoothGattServer != null) {
                    bluetoothGattServer.cancelConnection(bluetoothDevice);
                }
                this.i.remove(bluetoothDevice.getAddress());
                return;
            }
            disconnect();
        }
    }

    private void a(BluetoothDevice bluetoothDevice) {
        if (this.h == null) {
            this.h = bluetoothDevice;
            if (this.e == null) {
                this.e = new BleComboStandardAuth(this.f, this.k);
            }
            if (this.d == null) {
                this.d = StandardAuthChannelManager.getInstance().registerChannelReader(bluetoothDevice.getAddress(), this.m);
            }
            this.j = 0;
            a.postDelayed(this.l, 1000L);
            ConfigRouterCallBack configRouterCallBack = this.c;
            if (configRouterCallBack != null) {
                configRouterCallBack.onBleConnected();
            }
        }
    }

    @Override // android.bluetooth.BluetoothGattServerCallback
    public void onCharacteristicReadRequest(BluetoothDevice bluetoothDevice, int i, int i2, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        BluetoothLog.w(String.format("GET READ: CHARACTER=%s", bluetoothGattCharacteristic.getUuid()));
        if ((bluetoothGattCharacteristic.getProperties() & 2) == 0) {
            BluetoothLog.w(String.format("no read property!!", new Object[0]));
            a(bluetoothDevice, i, 6, ByteUtils.EMPTY_BYTES);
        } else if ((bluetoothGattCharacteristic.getPermissions() & 1) == 0) {
            BluetoothLog.w(String.format("no read permission!!", new Object[0]));
            a(bluetoothDevice, i, 2, ByteUtils.EMPTY_BYTES);
        } else {
            byte[] bytes = (!bluetoothGattCharacteristic.getService().getUuid().equals(BluetoothConstants.MISERVICE) || !bluetoothGattCharacteristic.getUuid().equals(BluetoothConstants.CHARACTER_FIRMWARE_VERSION) || TextUtils.isEmpty(this.g)) ? new byte[]{0} : this.g.getBytes();
            BluetoothGattServer bluetoothGattServer = this.b;
            if (bluetoothGattServer != null) {
                bluetoothGattServer.sendResponse(bluetoothDevice, i, 0, i2, bytes);
            }
        }
    }

    @Override // android.bluetooth.BluetoothGattServerCallback
    public void onCharacteristicWriteRequest(final BluetoothDevice bluetoothDevice, int i, BluetoothGattCharacteristic bluetoothGattCharacteristic, boolean z, boolean z2, int i2, byte[] bArr) {
        ChannelManager.BleChannel channel;
        BluetoothLog.w(String.format("GET WRITE: CHARACTER=%s, value=%s", bluetoothGattCharacteristic.getUuid(), BleServerManager.bytesToHexString(bArr)));
        if ((bluetoothGattCharacteristic.getProperties() & 8) == 0 && (bluetoothGattCharacteristic.getProperties() & 4) == 0) {
            BluetoothLog.w(String.format("no write property!!", new Object[0]));
            a(bluetoothDevice, i, 6, ByteUtils.EMPTY_BYTES);
        } else if ((bluetoothGattCharacteristic.getPermissions() & 16) == 0) {
            BluetoothLog.w(String.format("no write permission!!", new Object[0]));
            a(bluetoothDevice, i, 3, ByteUtils.EMPTY_BYTES);
        } else {
            if (bluetoothGattCharacteristic.getService().getUuid().equals(BluetoothConstants.MISERVICE)) {
                a(bluetoothDevice);
                if (bluetoothGattCharacteristic.getUuid().equals(BluetoothConstants.CHARACTER_EVENT)) {
                    BleComboStandardAuth bleComboStandardAuth = this.e;
                    if (bleComboStandardAuth != null) {
                        bleComboStandardAuth.receiveControlPointData(bArr);
                    }
                    if (ByteUtils.isSameElement(bArr, (byte) -92)) {
                        byte[] bArr2 = {6, SignedBytes.MAX_POWER_OF_TWO};
                        StandardAuthChannelManager.getInstance().setMtu(bluetoothDevice.getAddress(), 64);
                        BluetoothLog.w(String.format("start send TYPE_ASK_SUPPORT.", new Object[0]));
                        BleServerImpl.getInstance().sendData(bluetoothDevice.getAddress(), BluetoothConstants.MISERVICE, BluetoothConstants.CHARACTER_STANDARD_AUTH, new MNGPacket(0, bArr2).toBytes(), new BleServerImpl.SendDataCallback() { // from class: com.mijiasdk.bleserver.config.BleStandAuthGattCallback.4
                            @Override // com.mijiasdk.bleserver.service.BleServerImpl.SendDataCallback
                            public void onResult(boolean z3) {
                                if (z3) {
                                    BluetoothLog.e(String.format("send TYPE_ASK_SUPPORT successfully!!", new Object[0]));
                                    StandardAuthChannelManager.getInstance().setState(bluetoothDevice.getAddress(), ChannelState.WAIT_MNG_ACK.ordinal());
                                    return;
                                }
                                BluetoothLog.e("send TYPE_ASK_SUPPORT failed!");
                            }
                        });
                    }
                } else if (bluetoothGattCharacteristic.getUuid().equals(BluetoothConstants.CHARACTER_STANDARD_AUTH) && (channel = StandardAuthChannelManager.getInstance().getChannel(bluetoothDevice.getAddress())) != null) {
                    channel.onRead(bArr);
                }
            }
            BluetoothGattServer bluetoothGattServer = this.b;
            if (bluetoothGattServer != null) {
                bluetoothGattServer.sendResponse(bluetoothDevice, i, 0, i2, new byte[]{0});
            }
        }
    }

    @Override // android.bluetooth.BluetoothGattServerCallback
    public void onDescriptorReadRequest(BluetoothDevice bluetoothDevice, int i, int i2, BluetoothGattDescriptor bluetoothGattDescriptor) {
        BluetoothGattServer bluetoothGattServer = this.b;
        if (bluetoothGattServer != null) {
            bluetoothGattServer.sendResponse(bluetoothDevice, i, 0, i2, new byte[]{0});
        }
    }

    @Override // android.bluetooth.BluetoothGattServerCallback
    public void onDescriptorWriteRequest(BluetoothDevice bluetoothDevice, int i, BluetoothGattDescriptor bluetoothGattDescriptor, boolean z, boolean z2, int i2, byte[] bArr) {
        BluetoothLog.w(String.format("GET WRITE DESCRIPTOR: CHARACTER=%s, value=%s", bluetoothGattDescriptor.getUuid(), BleServerManager.bytesToHexString(bArr)));
        if ((bluetoothGattDescriptor.getCharacteristic().getProperties() & 16) == 0) {
            BluetoothLog.w(String.format("no notify descriptor!!", new Object[0]));
            a(bluetoothDevice, i, 6, ByteUtils.EMPTY_BYTES);
        } else if ((bluetoothGattDescriptor.getPermissions() & 16) == 0) {
            BluetoothLog.w(String.format("descriptor not writable!!", new Object[0]));
            a(bluetoothDevice, i, 3, ByteUtils.EMPTY_BYTES);
        } else {
            bluetoothGattDescriptor.setValue(bArr);
            a(bluetoothDevice, i, 0, bArr);
        }
    }

    @Override // android.bluetooth.BluetoothGattServerCallback
    public void onMtuChanged(BluetoothDevice bluetoothDevice, int i) {
        BluetoothLog.w(String.format("GET onMtuChanged: mtu=%d", Integer.valueOf(i)));
        super.onMtuChanged(bluetoothDevice, i);
    }

    private boolean a(BluetoothDevice bluetoothDevice, int i, int i2, byte[] bArr) {
        BluetoothGattServer bluetoothGattServer;
        BluetoothLog.v(String.format("sendResponse requestId = %d, status = %d, value = %s", Integer.valueOf(i), Integer.valueOf(i2), ByteUtils.byteToString(bArr)));
        if (bluetoothDevice == null || (bluetoothGattServer = this.b) == null) {
            return false;
        }
        return bluetoothGattServer.sendResponse(bluetoothDevice, i, i2, 0, bArr);
    }

    public void disconnect() {
        if (this.b != null && this.i.size() > 0) {
            for (Map.Entry<String, BluetoothDevice> entry : this.i.entrySet()) {
                if (entry.getValue() != null) {
                    this.b.cancelConnection(entry.getValue());
                }
            }
            this.i.clear();
        }
        String str = "";
        BluetoothDevice bluetoothDevice = this.h;
        if (bluetoothDevice != null) {
            str = bluetoothDevice.getAddress();
            this.h = null;
        }
        this.j = 0;
        a.removeCallbacks(this.l);
        BleComboStandardAuth bleComboStandardAuth = this.e;
        if (bleComboStandardAuth != null) {
            bleComboStandardAuth.release();
            this.e = null;
        }
        this.d = null;
        StandardAuthChannelManager.getInstance().unregisterChannelReader(str, this.m);
        ConfigRouterCallBack configRouterCallBack = this.c;
        if (configRouterCallBack != null) {
            configRouterCallBack.onBleDisconnected();
        }
    }

    public void sendData(final String str, final UUID uuid, final UUID uuid2, final byte[] bArr, final BleServerImpl.SendDataCallback sendDataCallback) {
        a.post(new Runnable() { // from class: com.mijiasdk.bleserver.config.BleStandAuthGattCallback.5
            @Override // java.lang.Runnable
            public void run() {
                boolean z = false;
                if (BleStandAuthGattCallback.this.b == null || BleStandAuthGattCallback.this.h == null) {
                    Log.e("BleStandAuthGattCB", "sendData failed, Connection has been dropped");
                    BleServerImpl.SendDataCallback sendDataCallback2 = sendDataCallback;
                    if (sendDataCallback2 != null) {
                        sendDataCallback2.onResult(false);
                    }
                } else if (!TextUtils.equals(BleStandAuthGattCallback.this.h.getAddress(), str)) {
                    Log.e("BleStandAuthGattCB", "mac don't match");
                    BleServerImpl.SendDataCallback sendDataCallback3 = sendDataCallback;
                    if (sendDataCallback3 != null) {
                        sendDataCallback3.onResult(false);
                    }
                } else {
                    BluetoothGattService service = BleStandAuthGattCallback.this.b.getService(uuid);
                    if (service == null) {
                        BleServerImpl.SendDataCallback sendDataCallback4 = sendDataCallback;
                        if (sendDataCallback4 != null) {
                            sendDataCallback4.onResult(false);
                            return;
                        }
                        return;
                    }
                    BluetoothGattCharacteristic characteristic = service.getCharacteristic(uuid2);
                    characteristic.setValue(bArr);
                    try {
                        z = BleStandAuthGattCallback.this.b.notifyCharacteristicChanged(BleStandAuthGattCallback.this.h, characteristic, false);
                    } catch (Exception e) {
                        Log.e("BleStandAuthGattCB", "notifyCharacteristicChanged failed: " + e.getMessage());
                    }
                    BleServerImpl.SendDataCallback sendDataCallback5 = sendDataCallback;
                    if (sendDataCallback5 != null) {
                        sendDataCallback5.onResult(z);
                    }
                }
            }
        });
    }

    public void sendRouterConnectedResult(boolean z) {
        Log.d("BleStandAuthGattCB", "sendRouterConnectedResult isConnected = " + z);
        if (this.b == null || this.h == null) {
            Log.e("BleStandAuthGattCB", "sendRouterConnectedResult notify failed, Connection has been dropped");
            return;
        }
        if (z) {
            this.j = 2;
        } else {
            this.j = 5;
        }
        a.removeCallbacks(this.l);
        a.post(this.l);
    }

    public void notifyPassportAuthSuccess() {
        Log.d("BleStandAuthGattCB", "notifyPassportAuthSuccess");
        if (this.b == null || this.h == null) {
            Log.e("BleStandAuthGattCB", "notifyPassportAuthSuccess notify failed, Connection has been dropped");
            return;
        }
        this.j = 6;
        a.removeCallbacks(this.l);
        a.post(this.l);
    }

    public void notifyOTServerConnected() {
        Log.d("BleStandAuthGattCB", "notifyOTServerConnected");
        if (this.b == null || this.h == null) {
            Log.e("BleStandAuthGattCB", "connectOTServer notify failed, Connection has been dropped");
            return;
        }
        this.j = 3;
        a.removeCallbacks(this.l);
        a.post(this.l);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(byte[] bArr, int i) {
        BleComboStandardAuth bleComboStandardAuth = this.e;
        if (bleComboStandardAuth != null) {
            bleComboStandardAuth.receviceStandAuthData(bArr, i);
        }
    }
}
