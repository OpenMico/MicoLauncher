package com.xiaomi.micolauncher.common.bluetooth;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothGattServerCallback;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.Context;
import android.os.ParcelUuid;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Base64;
import com.elvishew.xlog.Logger;
import com.xiaomi.mico.base.utils.WiFiUtil;
import com.xiaomi.mico.base.utils.XMStringUtils;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.mico.settingslib.wifi.WiFiConnState;
import com.xiaomi.mico.utils.Threads;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.Init;
import com.xiaomi.micolauncher.application.Constants;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.smarthome.library.crypto.rc4coder.RC4Coder;
import java.net.NetworkInterface;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/* loaded from: classes3.dex */
public class BluetoothInitManager {
    private static final List<String> a = new ArrayList<String>() { // from class: com.xiaomi.micolauncher.common.bluetooth.BluetoothInitManager.1
        {
            add(Hardware.X08A.getName());
            add(Hardware.X08C.getName());
            add(Hardware.X08E.getName());
            add(Hardware.LX04.getName());
        }
    };
    private static final UUID b = UUID.fromString("8CBEBE01-0001-0002-0003-010203040506");
    private static final String c = Constants.getSn();
    private static final byte d = Hardware.current(MicoApplication.getApp()).getBLEType();
    @SuppressLint({"StaticFieldLeak"})
    private static BluetoothInitManager m = null;
    private InitStepListener e;
    private BluetoothGattCharacteristic g;
    private BluetoothGattCharacteristic h;
    private BluetoothGattCharacteristic i;
    private SecretKey j;
    private final Context l;
    private BluetoothAdapter f = BluetoothAdapter.getDefaultAdapter();
    private AtomicReference<BluetoothManager> k = new AtomicReference<>();
    private AtomicReference<BluetoothGattServer> n = new AtomicReference<>();
    private b o = new b();
    private b p = new b();

    /* loaded from: classes3.dex */
    public interface InitStepListener {
        void onDeviceConnected(BluetoothDevice bluetoothDevice);

        void onEncryptedRc4KeyReceived(byte[] bArr);

        void onRetryQrReceived();

        void onWifiInfoReceived(Init.InitWifiInfo initWifiInfo);
    }

    public static BluetoothInitManager getInstance() {
        if (m == null) {
            m = new BluetoothInitManager(MicoApplication.getGlobalContext());
        }
        return m;
    }

    public void setInitListener(InitStepListener initStepListener) {
        this.e = initStepListener;
    }

    private BluetoothInitManager(Context context) {
        this.l = context;
    }

    public void startAdvertising(final AdvertiseCallback advertiseCallback) {
        L.bluetooth.i("%s startAdvertising", "[BluetoothInitManager]: ");
        if (!this.f.isEnabled()) {
            L.bluetooth.i("%s BluetoothAdapter is disabled, try to enable!", "[BluetoothInitManager]: ");
            this.f.enable();
        } else {
            L.bluetooth.i("%s BluetoothAdapter set SCAN_MODE_NONE", "[BluetoothInitManager]: ");
            this.f.setScanMode(20);
        }
        if (!this.f.isLeEnabled()) {
            L.bluetooth.i("%s BluetoothAdapter LE is disabled, try to enable LE!", "[BluetoothInitManager]: ");
            this.f.enableBLE();
        }
        Threads.getComputationThreadPool().submit(new Runnable() { // from class: com.xiaomi.micolauncher.common.bluetooth.-$$Lambda$BluetoothInitManager$kkSa0_WuC6So-5en8Hdaq1tMT6g
            @Override // java.lang.Runnable
            public final void run() {
                BluetoothInitManager.this.a(advertiseCallback);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(AdvertiseCallback advertiseCallback) {
        int i = 900;
        while (true) {
            i--;
            if (i > 0) {
                L.bluetooth.i("%s checking BluetoothLeAdvertiser.", "[BluetoothInitManager]: ");
                BluetoothLeAdvertiser bluetoothLeAdvertiser = this.f.getBluetoothLeAdvertiser();
                if (bluetoothLeAdvertiser == null || !this.f.isLeEnabled() || !this.f.isEnabled()) {
                    Logger logger = L.bluetooth;
                    Object[] objArr = new Object[4];
                    objArr[0] = Boolean.valueOf(bluetoothLeAdvertiser == null);
                    objArr[1] = Boolean.valueOf(this.f.isLeEnabled());
                    objArr[2] = Boolean.valueOf(this.f.isEnabled());
                    objArr[3] = Boolean.valueOf(this.f.isDiscovering());
                    logger.d("advertiser is null ? %s,  isLeEnabled %s, isEnabled %s, isDiscovering %s", objArr);
                    SystemClock.sleep(10L);
                } else {
                    a(bluetoothLeAdvertiser, advertiseCallback);
                    return;
                }
            } else {
                L.bluetooth.e("%s Init failed, failed to get BluetoothLeAdvertiser.", "[BluetoothInitManager]: ");
                return;
            }
        }
    }

    private void a(BluetoothLeAdvertiser bluetoothLeAdvertiser, AdvertiseCallback advertiseCallback) {
        AdvertiseData advertiseData;
        AdvertiseData advertiseData2;
        if (TextUtils.isEmpty(c)) {
            L.bluetooth.e("%s SERIAL is null", "[BluetoothInitManager]: ");
            return;
        }
        String str = c;
        String substring = str.substring(str.length() - 4);
        if (TextUtils.isEmpty(substring)) {
            L.bluetooth.e("%s subStringSn is null", "[BluetoothInitManager]: ");
            return;
        }
        String str2 = this.l.getString(R.string.lx04_default_name) + com.xiaomi.mipush.sdk.Constants.ACCEPT_TIME_SEPARATOR_SERVER + substring;
        this.f.setName(str2);
        Logger logger = L.bluetooth;
        String str3 = c;
        logger.i("%s doStartAdvertising default name: %s, SN: %s, len: %s, string: %s", "[BluetoothInitManager]: ", str2, str3, Integer.valueOf(str3.length()), substring);
        AdvertiseSettings f = f();
        c();
        L.bluetooth.i("%s Hardware model: %s", "[BluetoothInitManager]: ", a());
        if (!a.contains(a())) {
            advertiseData2 = e();
            advertiseData = d();
            L.bluetooth.i("set active advertise data, old type");
        } else {
            advertiseData2 = b();
            advertiseData = null;
            L.bluetooth.i("set active advertise data for mijia");
        }
        bluetoothLeAdvertiser.startAdvertising(f, advertiseData2, advertiseData, advertiseCallback);
        L.bluetooth.i("%s doStartAdvertising done", "[BluetoothInitManager]: ");
    }

    private String a() {
        return Hardware.current(this.l).getName();
    }

    private AdvertiseData b() {
        ParcelUuid fromString = ParcelUuid.fromString("0000FE95-0000-1000-8000-00805F9B34FB");
        String str = c;
        byte[] a2 = a(str.substring(str.length() - 4));
        int length = a2 != null ? a2.length : 0;
        ByteBuffer order = ByteBuffer.wrap(new byte[22]).order(ByteOrder.LITTLE_ENDIAN);
        if (length != 2) {
            L.bluetooth.e("%s subHexSn length: %s, not equal to 2", "[BluetoothInitManager]: ", Integer.valueOf(length));
            return null;
        }
        byte[] h = h();
        if (h == null) {
            L.bluetooth.e("%s wifiMacByte is null !", "[BluetoothInitManager]: ");
            return null;
        }
        order.putShort((short) 22640);
        order.putShort(Hardware.current(this.l).getMiotProductId());
        order.put((byte) 0);
        order.put(h[3]);
        order.put(h[2]);
        order.put(h[1]);
        order.put(h[0]);
        order.put(a2[1]);
        order.put(a2[0]);
        order.put((byte) 24);
        order.put(h[5]);
        order.put(h[4]);
        order.putShort((short) 4113);
        order.put((byte) 5);
        order.put((byte) 6);
        order.put((byte) 0);
        order.put(d);
        order.put((byte) 0);
        order.put((byte) 0);
        return new AdvertiseData.Builder().setIncludeDeviceName(false).setIncludeTxPowerLevel(false).addServiceData(fromString, order.array()).build();
    }

    private void c() {
        ArrayList<a> arrayList = new ArrayList();
        BluetoothGattService bluetoothGattService = new BluetoothGattService(b, 0);
        this.k.set((BluetoothManager) this.l.getSystemService("bluetooth"));
        this.n.set(this.k.get().openGattServer(this.l, new c()));
        arrayList.add(new a("8CBEBE01-0001-0002-0003-010203040510", 8, 16));
        arrayList.add(new a("8CBEBE01-0001-0002-0003-010203040511", 16, 0));
        arrayList.add(new a("8CBEBE01-0001-0002-0003-010203040512", 8, 16));
        arrayList.add(new a("8CBEBE01-0001-0002-0003-010203040513", 16, 0));
        arrayList.add(new a("8CBEBE01-0001-0002-0003-010203040603", 8, 16));
        arrayList.add(new a("8CBEBE01-0001-0002-0003-010203040604", 16, 0));
        for (a aVar : arrayList) {
            BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(UUID.fromString(aVar.b), aVar.c, aVar.d);
            bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);
            if (aVar.b.equals("8CBEBE01-0001-0002-0003-010203040513")) {
                L.bluetooth.d("%s Set notifyWifiResultCharacteristic", "[BluetoothInitManager]: ");
                this.g = bluetoothGattCharacteristic;
            }
            if (aVar.b.equals("8CBEBE01-0001-0002-0003-010203040604")) {
                L.bluetooth.d("%s Set notifyQrRetryCharacteristic", "[BluetoothInitManager]: ");
                this.h = bluetoothGattCharacteristic;
            }
            if (aVar.b.equals("8CBEBE01-0001-0002-0003-010203040511")) {
                L.bluetooth.d("%s Set notifyRc4EncryptedKeyCharacteristic", "[BluetoothInitManager]: ");
                this.i = bluetoothGattCharacteristic;
            }
        }
        AtomicReference<BluetoothGattServer> atomicReference = this.n;
        if (atomicReference != null) {
            atomicReference.get().addService(bluetoothGattService);
        }
    }

    private AdvertiseData d() {
        if (a().equals("LX04")) {
            return new AdvertiseData.Builder().setIncludeDeviceName(false).addManufacturerData(911, new byte[]{-110, 5, 0, d, 0, 0}).build();
        }
        return new AdvertiseData.Builder().setIncludeDeviceName(false).addManufacturerData(911, new byte[]{5, 2, 5, 0, d, 0, 0}).build();
    }

    private AdvertiseData e() {
        return new AdvertiseData.Builder().setIncludeDeviceName(true).build();
    }

    private AdvertiseSettings f() {
        return new AdvertiseSettings.Builder().setAdvertiseMode(2).setConnectable(true).setTxPowerLevel(3).build();
    }

    public void stopAdvertising(AdvertiseCallback advertiseCallback) {
        if (this.f.getBluetoothLeAdvertiser() != null) {
            this.f.getBluetoothLeAdvertiser().stopAdvertising(advertiseCallback);
        } else {
            L.bluetooth.e("Bluetooth is turned off.");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public class c extends BluetoothGattServerCallback {
        c() {
        }

        @Override // android.bluetooth.BluetoothGattServerCallback
        public void onCharacteristicReadRequest(BluetoothDevice bluetoothDevice, int i, int i2, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
            super.onCharacteristicReadRequest(bluetoothDevice, i, i2, bluetoothGattCharacteristic);
            L.bluetooth.i("%s onCharacteristicReadRequest", "[BluetoothInitManager]: ");
        }

        /* JADX WARN: Removed duplicated region for block: B:48:0x01da  */
        /* JADX WARN: Removed duplicated region for block: B:50:? A[RETURN, SYNTHETIC] */
        @Override // android.bluetooth.BluetoothGattServerCallback
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onCharacteristicWriteRequest(android.bluetooth.BluetoothDevice r15, int r16, android.bluetooth.BluetoothGattCharacteristic r17, boolean r18, boolean r19, int r20, byte[] r21) {
            /*
                Method dump skipped, instructions count: 526
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.micolauncher.common.bluetooth.BluetoothInitManager.c.onCharacteristicWriteRequest(android.bluetooth.BluetoothDevice, int, android.bluetooth.BluetoothGattCharacteristic, boolean, boolean, int, byte[]):void");
        }

        @Override // android.bluetooth.BluetoothGattServerCallback
        public void onConnectionStateChange(BluetoothDevice bluetoothDevice, int i, int i2) {
            super.onConnectionStateChange(bluetoothDevice, i, i2);
            L.bluetooth.i("%s onConnectionStateChange %s status: %s newState: %s", "[BluetoothInitManager]: ", bluetoothDevice, BluetoothInitManager.this.a(i), BluetoothInitManager.this.a(i2));
            if (i2 == 2) {
                BluetoothInitManager.this.e.onDeviceConnected(bluetoothDevice);
            }
        }

        @Override // android.bluetooth.BluetoothGattServerCallback
        public void onNotificationSent(BluetoothDevice bluetoothDevice, int i) {
            super.onNotificationSent(bluetoothDevice, i);
        }
    }

    public synchronized void notifyRc4EncryptedKey(byte[] bArr) {
        a(this.i, a(bArr));
    }

    public synchronized void notifyWifiResult(WiFiConnState wiFiConnState, String str, String str2) {
        Init.InitWifiInfoResp initWifiInfoResp = new Init.InitWifiInfoResp();
        initWifiInfoResp.code = String.valueOf(wiFiConnState.getCode());
        initWifiInfoResp.did = str;
        initWifiInfoResp.qr = str2;
        String json = Gsons.getGson().toJson(initWifiInfoResp);
        L.init.d(json);
        byte[] a2 = a(1, json.getBytes());
        if (a2 != null) {
            a(this.g, a(a2));
        } else {
            L.bluetooth.e("%s cipherData encryptedResult failed", "[BluetoothInitManager]: ");
        }
    }

    private void a(BluetoothGattCharacteristic bluetoothGattCharacteristic, List<byte[]> list) {
        try {
            List<BluetoothDevice> connectedDevices = this.k.get().getConnectedDevices(8);
            for (int i = 0; i < list.size(); i++) {
                bluetoothGattCharacteristic.setValue(list.get(i));
                L.bluetooth.i("%s sending[%s/%s]: %s", "[BluetoothInitManager]: ", Integer.valueOf(i), Integer.valueOf(list.size()), XMStringUtils.bytesToHex(list.get(i)));
                L.bluetooth.i("%s devices size: %s", "[BluetoothInitManager]: ", Integer.valueOf(connectedDevices.size()));
                for (int i2 = 0; i2 < connectedDevices.size(); i2++) {
                    this.n.get().notifyCharacteristicChanged(connectedDevices.get(i2), bluetoothGattCharacteristic, false);
                }
            }
            L.bluetooth.i("%s send finish", "[BluetoothInitManager]: ");
        } catch (Exception e) {
            L.bluetooth.e("%s sendData failed: %s", "[BluetoothInitManager]: ", e);
        }
    }

    private List<byte[]> a(byte[] bArr) {
        int length = bArr.length;
        int i = (length + 18) / 19;
        ArrayList arrayList = new ArrayList(i);
        for (int i2 = 0; i2 < i; i2++) {
            int i3 = i2 * 19;
            int min = Math.min(length - i3, 19);
            byte[] bArr2 = new byte[min + 1];
            bArr2[0] = (byte) ((((i - 1) << 4) + i2) & 255);
            System.arraycopy(bArr, i3, bArr2, 1, min);
            arrayList.add(bArr2);
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String a(int i) {
        switch (i) {
            case 0:
                return "STATE_DISCONNECTED";
            case 1:
                return "STATE_CONNECTING";
            case 2:
                return "STATE_CONNECTED";
            case 3:
                return "STATE_DISCONNECTING";
            default:
                return String.format("unknown state: %s", String.valueOf(i));
        }
    }

    /* loaded from: classes3.dex */
    private class b {
        public ByteBuffer a;
        private int c;
        private int d;

        private b() {
            this.a = ByteBuffer.allocate(1024);
            this.d = -1;
        }

        boolean a(byte[] bArr) {
            int i = bArr[0] & 15;
            if (i == 0) {
                this.d = -1;
                this.c = (bArr[0] & 240) >> 4;
                this.a.clear();
            }
            if (this.d + 1 != i || i > this.c) {
                return true;
            }
            this.d = i;
            this.a.put(Arrays.copyOfRange(bArr, 1, bArr.length));
            return false;
        }

        public boolean a() {
            return this.c == this.d;
        }

        byte[] b() {
            byte[] copyOfRange = Arrays.copyOfRange(this.a.array(), 0, this.a.position());
            L.bluetooth.i("%s buffer Data len: %s, value len: %s", "[BluetoothInitManager]: ", Integer.valueOf(this.a.array().length), Integer.valueOf(copyOfRange.length));
            return copyOfRange;
        }

        public String c() {
            return new String(Arrays.copyOfRange(this.a.array(), 0, this.a.position()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public class a {
        private String b;
        private int c;
        private int d;

        a(String str, int i, int i2) {
            this.b = str;
            this.c = i;
            this.d = i2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public SecretKey g() {
        L.bluetooth.i("%s Generate Rc4Key", "[BluetoothInitManager]: ");
        try {
            KeyGenerator instance = KeyGenerator.getInstance(RC4Coder.RC4_ALGORITHM);
            if (instance != null) {
                instance.init(496);
                SecretKey generateKey = instance.generateKey();
                byte[] encoded = generateKey.getEncoded();
                L.bluetooth.i("%s RC4 Key len: %s, String: %s", "[BluetoothInitManager]: ", Integer.valueOf(encoded.length), Arrays.toString(encoded));
                L.bluetooth.i("%s Base64 RC4 Key: %s", "[BluetoothInitManager]: ", Base64.encodeToString(encoded, 0));
                return generateKey;
            }
            L.bluetooth.e("rc4KeyGenerator is null!");
            return null;
        } catch (NoSuchAlgorithmException e) {
            L.bluetooth.e("%s KeyGenerator getInstance failed: %s", "[BluetoothInitManager]: ", e);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public byte[] a(SecretKey secretKey) {
        PublicKey publicKey;
        Cipher cipher;
        byte[] decode = Base64.decode("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCDsAf8FW1O8JykVbx5JMSs907IovC+RlkwOzRg6r71GkNS+Zkp3P/nibmzVfTBME5xbrxRmUkQnNJvJRR60G41S6EOmQ+UEQVVljy3NySYZbb0xhQVLjj30Uvlu9k42PY+ZWaSuRxM82jaAfQi1xqGmLpmCL/1nFpYzVDuuk9p0QIDAQAB", 0);
        try {
            KeyFactory instance = KeyFactory.getInstance("RSA");
            if (instance != null) {
                try {
                    publicKey = instance.generatePublic(new X509EncodedKeySpec(decode));
                } catch (InvalidKeySpecException e) {
                    L.bluetooth.e("%s rsaKeyFactory generatePublic failed: %s", "[BluetoothInitManager]: ", e);
                    publicKey = null;
                }
                try {
                    cipher = Cipher.getInstance("RSA/ECB/OAEPwithSHA-1andMGF1Padding");
                } catch (NoSuchAlgorithmException | NoSuchPaddingException e2) {
                    L.bluetooth.e("%s Cipher getInstance failed: %s", "[BluetoothInitManager]: ", e2);
                    cipher = null;
                }
                if (cipher != null) {
                    byte[] bArr = new byte[0];
                    try {
                        cipher.init(1, publicKey);
                    } catch (InvalidKeyException e3) {
                        e3.printStackTrace();
                    }
                    try {
                        bArr = cipher.doFinal(secretKey.getEncoded());
                    } catch (BadPaddingException | IllegalBlockSizeException e4) {
                        L.bluetooth.e("%s rsaCipher doFinal failed: %s", "[BluetoothInitManager]: ", e4);
                    }
                    L.bluetooth.i("Base64 encrypted RC4 Key: %s", Base64.encodeToString(bArr, 0));
                    return bArr;
                }
                L.bluetooth.e("%s rsaCipher is null", "[BluetoothInitManager]: ");
                return null;
            }
            L.bluetooth.e("%s rsaKeyFactory is null", "[BluetoothInitManager]: ");
            return null;
        } catch (NoSuchAlgorithmException e5) {
            L.bluetooth.e("%s KeyFactory getInstance failed: %s", "[BluetoothInitManager]: ", e5);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public byte[] a(int i, byte[] bArr) {
        L.bluetooth.i("%s cipherData", "[BluetoothInitManager]: ");
        try {
            Cipher instance = Cipher.getInstance(RC4Coder.RC4_ALGORITHM);
            try {
                instance.init(i, this.j);
                try {
                    return instance.doFinal(bArr);
                } catch (BadPaddingException | IllegalBlockSizeException e) {
                    L.bluetooth.e("%s rsaCipher doFinal failed: %s", "[BluetoothInitManager]: ", e);
                    return null;
                }
            } catch (InvalidKeyException e2) {
                L.bluetooth.e("%s rsaCipher init failed: %s", "[BluetoothInitManager]: ", e2);
                return null;
            }
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e3) {
            L.bluetooth.e("%s Cipher getInstance failed: %s", "[BluetoothInitManager]: ", e3);
            return null;
        }
    }

    private byte[] a(String str) {
        if (str == null || "".equals(str) || str.length() % 2 != 0) {
            return null;
        }
        int length = str.length() / 2;
        byte[] bArr = new byte[length];
        char[] charArray = str.toCharArray();
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            bArr[i] = (byte) (a(charArray[i2 + 1]) | (a(charArray[i2]) << 4));
        }
        return bArr;
    }

    private static byte a(char c2) {
        return (byte) "0123456789ABCDEF".indexOf(c2);
    }

    private byte[] h() {
        String i = i();
        if (!TextUtils.isEmpty(i)) {
            L.bluetooth.i("%s wifiMac: %s", "[BluetoothInitManager]: ", i);
            return b(i);
        }
        L.bluetooth.e("%s wifiMac is null ！", "[BluetoothInitManager]: ");
        return null;
    }

    private static String i() {
        try {
            for (NetworkInterface networkInterface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                if (WiFiUtil.WLAN_0.equalsIgnoreCase(networkInterface.getName())) {
                    byte[] hardwareAddress = networkInterface.getHardwareAddress();
                    if (hardwareAddress == null) {
                        return null;
                    }
                    StringBuilder sb = new StringBuilder();
                    int length = hardwareAddress.length;
                    for (int i = 0; i < length; i++) {
                        sb.append(String.format("%02X:", Byte.valueOf(hardwareAddress[i])));
                    }
                    if (sb.length() > 0) {
                        sb.deleteCharAt(sb.length() - 1);
                    }
                    return sb.toString();
                }
            }
        } catch (Exception e) {
            L.bluetooth.e("%s getWiFiMac Exception: %s", "[BluetoothInitManager]: ", e);
        }
        return null;
    }

    private static byte[] b(String str) {
        if (TextUtils.isEmpty(str)) {
            L.bluetooth.d("%s mac is null", "[BluetoothInitManager]: ");
            return null;
        }
        byte[] bArr = new byte[6];
        String[] split = str.split(com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR);
        for (int i = 0; i < split.length; i++) {
            bArr[i] = (byte) Integer.parseInt(split[i], 16);
        }
        return bArr;
    }
}
