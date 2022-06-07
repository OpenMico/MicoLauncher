package com.xiaomi.micolauncher.module.setting.bluetooth;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.Context;
import android.os.ParcelUuid;
import android.text.TextUtils;
import com.xiaomi.mi_connect_sdk.api.DefaultMiApp;
import com.xiaomi.mi_connect_sdk.api.MiAppCallback;
import com.xiaomi.mi_connect_sdk.api.MiConnect;
import com.xiaomi.mi_connect_service.ISoundBoxWhiteNameCallBack;
import com.xiaomi.mico.base.utils.BluetoothUtil;
import com.xiaomi.mico.base.utils.CommonUtils;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.application.Constants;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.module.setting.utils.BluetoothWhiteList;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

/* loaded from: classes3.dex */
public class BluetoothAudioRelayManager {
    @SuppressLint({"StaticFieldLeak"})
    private static volatile BluetoothAudioRelayManager i;
    private final Context a;
    private DefaultMiApp b;
    private final String c = Constants.getSn();
    private final ParcelUuid d = ParcelUuid.fromString("0000FDAA-0000-1000-8000-00805F9B34FB");
    private BluetoothAdapter e = BluetoothAdapter.getDefaultAdapter();
    private BluetoothLeAdvertiser f = this.e.getBluetoothLeAdvertiser();
    private a g = new a();
    private volatile boolean h = false;
    private MiAppCallback j = new MiAppCallback() { // from class: com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothAudioRelayManager.1
        @Override // com.xiaomi.mi_connect_sdk.api.MiAppCallback
        public void onAdvertingResult(int i2, int i3) {
            L.bluetooth.d("%s onAdvertingResult", "[BluetoothAudioRelayManager]:");
        }

        @Override // com.xiaomi.mi_connect_sdk.api.MiAppCallback
        public void onDiscoveryResult(int i2, int i3) {
            L.bluetooth.d("%s onDiscoveryResult", "[BluetoothAudioRelayManager]:");
        }

        @Override // com.xiaomi.mi_connect_sdk.api.MiAppCallback
        public void onEndpointFound(int i2, int i3, String str, byte[] bArr) {
            L.bluetooth.d("%s onEndpointFound", "[BluetoothAudioRelayManager]:");
        }

        @Override // com.xiaomi.mi_connect_sdk.api.MiAppCallback
        public void onEndpointLost(int i2, int i3, String str) {
            L.bluetooth.d("%s onEndpointLost", "[BluetoothAudioRelayManager]:");
        }

        @Override // com.xiaomi.mi_connect_sdk.api.MiAppCallback
        public void onConnectionInitiated(int i2, int i3, String str, byte[] bArr, byte[] bArr2) {
            L.bluetooth.d("%s onConnectionInitiated", "[BluetoothAudioRelayManager]:");
        }

        @Override // com.xiaomi.mi_connect_sdk.api.MiAppCallback
        public void onConnectionResult(int i2, int i3, String str, int i4) {
            L.bluetooth.d("%s onConnectionResult", "[BluetoothAudioRelayManager]:");
        }

        @Override // com.xiaomi.mi_connect_sdk.api.MiAppCallback
        public void onDisconnection(int i2, int i3) {
            L.bluetooth.d("%s onDisconnection", "[BluetoothAudioRelayManager]:");
        }

        @Override // com.xiaomi.mi_connect_sdk.api.MiAppCallback
        public void onPayloadSentResult(int i2, int i3, int i4) {
            L.bluetooth.d("%s onPayloadSentResult", "[BluetoothAudioRelayManager]:");
        }

        @Override // com.xiaomi.mi_connect_sdk.api.MiAppCallback
        public void onPayloadReceived(int i2, int i3, byte[] bArr) {
            L.bluetooth.d("%s onPayloadReceived", "[BluetoothAudioRelayManager]:");
        }

        @Override // com.xiaomi.mi_connect_sdk.api.MiAppCallback
        public void onServiceError(int i2) {
            L.bluetooth.d("%s onServiceError", "[BluetoothAudioRelayManager]:");
            BluetoothAudioRelayManager.this.h = false;
        }

        @Override // com.xiaomi.mi_connect_sdk.api.MiAppCallback
        public void onServiceBind() {
            L.bluetooth.d("%s onServiceBind", "[BluetoothAudioRelayManager]:");
            BluetoothAudioRelayManager.this.h = true;
            BluetoothAudioRelayManager bluetoothAudioRelayManager = BluetoothAudioRelayManager.this;
            bluetoothAudioRelayManager.a(bluetoothAudioRelayManager.l);
            BluetoothAudioRelayManager.this.b.registerSoundBoxWhiteName(BluetoothAudioRelayManager.this.k);
        }

        @Override // com.xiaomi.mi_connect_sdk.api.MiAppCallback
        public void onServiceUnbind() {
            L.bluetooth.d("%s onServiceUnbind", "[BluetoothAudioRelayManager]:");
            BluetoothAudioRelayManager.this.h = false;
            BluetoothAudioRelayManager.this.stopAudioRelayAdvertising();
        }
    };
    private ISoundBoxWhiteNameCallBack k = new ISoundBoxWhiteNameCallBack.Stub() { // from class: com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothAudioRelayManager.2
        @Override // com.xiaomi.mi_connect_service.ISoundBoxWhiteNameCallBack
        public int onWriteWhiteName(String str) {
            L.bluetooth.d("%s onWriteWhiteName: %s", "[BluetoothAudioRelayManager]:", str);
            if (TextUtils.isEmpty(str)) {
                L.bluetooth.e("%s jsonInfo is null", "[BluetoothAudioRelayManager]:");
                return -1;
            }
            BluetoothAudioRelayManager.this.b(str);
            return 0;
        }
    };
    private AdvertiseCallback l = new AdvertiseCallback() { // from class: com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothAudioRelayManager.3
        @Override // android.bluetooth.le.AdvertiseCallback
        public void onStartFailure(int i2) {
            super.onStartFailure(i2);
            L.bluetooth.i("startAdvertising onStartFailure: %s", Integer.valueOf(i2));
        }

        @Override // android.bluetooth.le.AdvertiseCallback
        public void onStartSuccess(AdvertiseSettings advertiseSettings) {
            super.onStartSuccess(advertiseSettings);
            L.bluetooth.i("startAdvertising onStartSuccess");
        }
    };

    private BluetoothAudioRelayManager(Context context) {
        this.a = context;
    }

    public static BluetoothAudioRelayManager getInstance() {
        if (i == null) {
            L.bluetooth.e("%s instance is null !!", "[BluetoothAudioRelayManager]:");
            init(MicoApplication.getGlobalContext());
        }
        return i;
    }

    public static void init(Context context) {
        if (i == null && context != null) {
            synchronized (BluetoothAudioRelayManager.class) {
                if (i == null) {
                    i = new BluetoothAudioRelayManager(context);
                }
            }
        }
    }

    public void start() {
        if (f()) {
            bindMiConnectService();
        }
    }

    public void destroy() {
        a();
    }

    public void bindMiConnectService() {
        L.bluetooth.d("%s bindMiConnectService isServiceBound: %s", "[BluetoothAudioRelayManager]:", Boolean.valueOf(this.h));
        if (!this.h) {
            this.b = (DefaultMiApp) MiConnect.newApp(this.a, this.j, 0);
            this.b.init();
            return;
        }
        a(this.l);
    }

    private void a() {
        L.bluetooth.d("%s unBindMiConnectService", "[BluetoothAudioRelayManager]:");
        stopAudioRelayAdvertising();
        DefaultMiApp defaultMiApp = this.b;
        if (defaultMiApp != null) {
            MiConnect.delApp(defaultMiApp, 2);
        }
    }

    public void a(AdvertiseCallback advertiseCallback) {
        L.bluetooth.i("%s startAudioRelayAdvertising!", "[BluetoothAudioRelayManager]:");
        if (this.e == null) {
            this.e = BluetoothAdapter.getDefaultAdapter();
            if (this.e == null) {
                L.camera2.e("%s btAdapter is null!", "[BluetoothAudioRelayManager]:");
                return;
            }
        }
        if (this.f == null) {
            this.f = this.e.getBluetoothLeAdvertiser();
            if (this.f == null) {
                L.camera2.e("%s advertiser is null!", "[BluetoothAudioRelayManager]:");
                return;
            }
        }
        byte[] d = d();
        if (d == null) {
            L.bluetooth.e("%s getAppData failed", "[BluetoothAudioRelayManager]:");
            return;
        }
        L.bluetooth.d("%s appDataString: %s", "[BluetoothAudioRelayManager]:", CommonUtils.byte2hex(d));
        AdvertiseData c = c();
        AdvertiseData a2 = a(d);
        AdvertiseSettings b = b();
        BluetoothLeAdvertiser bluetoothLeAdvertiser = this.f;
        if (bluetoothLeAdvertiser != null) {
            bluetoothLeAdvertiser.startAdvertisingWithInterval(b, c, a2, 96, advertiseCallback);
        }
    }

    private AdvertiseData a(byte[] bArr) {
        return new AdvertiseData.Builder().setIncludeDeviceName(false).setIncludeTxPowerLevel(false).addServiceData(this.d, bArr).build();
    }

    public void stopAudioRelayAdvertising() {
        L.bluetooth.d("%s stopAudioRelayAdvertising", "[BluetoothAudioRelayManager]:");
        BluetoothLeAdvertiser bluetoothLeAdvertiser = this.f;
        if (bluetoothLeAdvertiser == null) {
            L.camera2.e("%s advertiser is null, can't run stopAdvertising!", "[BluetoothAudioRelayManager]:");
        } else {
            bluetoothLeAdvertiser.stopAdvertising(this.l);
        }
    }

    private AdvertiseSettings b() {
        return new AdvertiseSettings.Builder().setAdvertiseMode(2).setConnectable(true).setTxPowerLevel(3).build();
    }

    private AdvertiseData c() {
        short miotProductId = Hardware.current(this.a).getMiotProductId();
        ByteBuffer allocate = ByteBuffer.allocate(20);
        allocate.put((byte) 34);
        allocate.put((byte) 17);
        allocate.put(new byte[]{16, 17});
        allocate.put(new byte[]{1, 1, Byte.MAX_VALUE, -6});
        allocate.put((byte) 2);
        allocate.put((byte) 52);
        allocate.put(e());
        allocate.put((byte) -123);
        allocate.put((byte) (miotProductId & 255));
        allocate.put((byte) ((miotProductId >> 8) & 255));
        allocate.put((byte) 0);
        allocate.put((byte) 6);
        allocate.put(this.g.b);
        allocate.put(this.g.c);
        L.bluetooth.d("%s byteBufferString: %s", "[BluetoothAudioRelayManager]:", CommonUtils.byte2hex(allocate.array()));
        return new AdvertiseData.Builder().addServiceUuid(this.d).addManufacturerData(911, allocate.array()).build();
    }

    private byte[] d() {
        String macAddress = BluetoothUtil.getMacAddress(this.a);
        byte b = BluetoothSettingManager.getInstance().isDeviceConnected() ? (byte) 1 : (byte) 0;
        if (macAddress == null || TextUtils.isEmpty(macAddress)) {
            return null;
        }
        L.bluetooth.d("%s btAddress: %s", "[BluetoothAudioRelayManager]:", macAddress);
        byte[] macBytes = BluetoothUtil.getMacBytes(macAddress);
        if (macBytes == null || macBytes.length != 6) {
            L.bluetooth.d("btAddressBytes is null");
            macBytes = new byte[6];
        } else {
            L.bluetooth.d("btAddressBytes len: %s", Integer.valueOf(macBytes.length));
        }
        this.g.h = macBytes;
        this.g.i = b;
        L.bluetooth.d("%s byteBufferString1: %s", "[BluetoothAudioRelayManager]:", Arrays.toString(this.g.a()));
        return this.g.a();
    }

    /* loaded from: classes3.dex */
    public class a {
        private byte b;
        private byte c;
        private byte d;
        private byte e;
        private byte[] f;
        private byte g;
        private byte[] h;
        private byte i;
        private byte j;
        private byte k;
        private byte[] l;

        private a() {
            BluetoothAudioRelayManager.this = r4;
            String substring = r4.c.substring(r4.c.length() - 4);
            this.f = new byte[]{1, 0};
            this.b = (byte) 7;
            this.c = (byte) 16;
            this.d = (byte) 1;
            this.e = (byte) 14;
            this.g = (byte) 2;
            this.j = (byte) -25;
            this.k = (byte) -35;
            this.l = r4.a(substring);
        }

        byte[] a() {
            ByteBuffer allocate = ByteBuffer.allocate(this.e + 2);
            allocate.put(this.d);
            allocate.put(this.e);
            allocate.put(this.f);
            allocate.put(this.g);
            allocate.put(this.h);
            allocate.put(this.i);
            allocate.put(this.j);
            allocate.put(this.k);
            allocate.put(this.l);
            return allocate.array();
        }
    }

    private byte[] e() {
        byte[] bArr = {0, 0, 0};
        byte[] idHash = this.b.getIdHash();
        if (idHash == null) {
            L.bluetooth.e("%s getShortHash hashData is null !", "[BluetoothAudioRelayManager]:");
            return bArr;
        }
        byte[] copyOf = Arrays.copyOf(idHash, bArr.length);
        L.bluetooth.d("%s hashData: %s, shortHash: %s", "[BluetoothAudioRelayManager]:", Arrays.toString(idHash), Arrays.toString(idHash));
        return copyOf;
    }

    public byte[] a(String str) {
        if (str == null || "".equals(str) || str.length() % 2 != 0) {
            return null;
        }
        int length = str.length() / 2;
        byte[] bArr = new byte[length];
        char[] charArray = str.toCharArray();
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = i2 * 2;
            bArr[i2] = (byte) (a(charArray[i3 + 1]) | (a(charArray[i3]) << 4));
        }
        return bArr;
    }

    private static byte a(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public void b(String str) {
        L.bluetooth.d("setBluetoothWhiteList");
        if (TextUtils.isEmpty(str)) {
            L.bluetooth.e("%s jsonInfo is null", "[BluetoothAudioRelayManager]:");
            return;
        }
        BluetoothWhiteList bluetoothWhiteList = new BluetoothWhiteList();
        ArrayList arrayList = new ArrayList();
        BluetoothWhiteList.DevicesBean devicesBean = (BluetoothWhiteList.DevicesBean) Gsons.getGson().fromJson(str, (Class<Object>) BluetoothWhiteList.DevicesBean.class);
        if (devicesBean == null) {
            L.bluetooth.e("%s jsonDevice is null", "[BluetoothAudioRelayManager]:");
            return;
        }
        if (!bluetoothWhiteList.isFileExist()) {
            L.bluetooth.d("%s File not exist", "[BluetoothAudioRelayManager]:");
            arrayList.add(devicesBean);
            bluetoothWhiteList.setDevices(arrayList);
        } else {
            String readFromFile = bluetoothWhiteList.readFromFile();
            BluetoothWhiteList bluetoothWhiteList2 = (BluetoothWhiteList) Gsons.getGson().fromJson(readFromFile, (Class<Object>) BluetoothWhiteList.class);
            L.bluetooth.d("%s read file string: %s", "[BluetoothAudioRelayManager]:", readFromFile);
            if (bluetoothWhiteList2 == null || bluetoothWhiteList2.getDevices() == null) {
                L.bluetooth.d("%s list or device is null", "[BluetoothAudioRelayManager]:");
                arrayList.add(devicesBean);
                bluetoothWhiteList.setDevices(arrayList);
            } else {
                if (bluetoothWhiteList2.isDeviceChecked(devicesBean)) {
                    bluetoothWhiteList2.updateDeviceInfo(devicesBean);
                } else {
                    bluetoothWhiteList2.getDevices().add(devicesBean);
                }
                bluetoothWhiteList = bluetoothWhiteList2;
            }
        }
        bluetoothWhiteList.setEnable(true);
        bluetoothWhiteList.writeToFile(bluetoothWhiteList);
        L.bluetooth.d("%s setBluetoothWhiteList end", "[BluetoothAudioRelayManager]:");
    }

    private boolean f() {
        return SystemSetting.getKeyAudioRelayEnable();
    }
}
