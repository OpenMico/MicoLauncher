package com.mijiasdk.bleserver.protocol;

import android.os.Bundle;
import android.util.Log;
import com.mijiasdk.bleserver.protocol.utils.ECCPointConvert;
import com.mijiasdk.bleserver.protocol.utils.Hkdf;
import com.mijiasdk.bleserver.protocol.utils.SecurityChipUtil;
import com.xiaomi.smarthome.devicelibrary.common.util.ByteUtils;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.ECPublicKey;
import javax.crypto.SecretKey;
import kotlin.jvm.internal.ShortCompanionObject;

/* loaded from: classes2.dex */
public class BleComboStandardAuth {
    private static final byte[] a = {-94, 0, 0, 0};
    private static final byte[] b = {21, 0, 0, 0};
    private AuthCallback c;
    private String d;
    private KeyPair e;
    private PublicKey f;
    private SecretKey g;
    private byte[] h;
    private byte[] i;
    private byte[] j;
    private byte[] k;
    private byte[] l;
    private a m = new a();

    /* loaded from: classes2.dex */
    public interface AuthCallback {
        void onAuthFailed();

        void onAuthSuccess(BleComboWifiConfig bleComboWifiConfig);

        void sendStandAuthData(byte[] bArr, int i, SendAuthResponse sendAuthResponse);
    }

    /* loaded from: classes2.dex */
    public interface SendAuthResponse {
        void onResponse(int i, Bundle bundle);
    }

    public BleComboStandardAuth(String str, AuthCallback authCallback) {
        this.d = str;
        this.c = authCallback;
    }

    public void release() {
        this.c = null;
    }

    public void receiveControlPointData(byte[] bArr) {
        if (ByteUtils.byteEquals(a, bArr)) {
            Log.d("BleComboStandardAuth", "receive REQ_DEV_INFO command");
            b();
        } else if (ByteUtils.byteEquals(b, bArr)) {
            Log.d("BleComboStandardAuth", "receive REG_START_WO_PKI command");
            a();
        }
    }

    public void receviceStandAuthData(byte[] bArr, int i) {
        if (i == 3) {
            a(bArr);
        } else if (i == 15) {
            c(bArr);
        }
    }

    private synchronized void a() {
        this.m.a();
        this.e = null;
        this.f = null;
        this.g = null;
        this.h = null;
        this.i = null;
        this.j = null;
        this.k = null;
        this.l = null;
    }

    private void b() {
        Log.d("BleComboStandardAuth", "sendDevInfo");
        ByteBuffer order = ByteBuffer.allocate(1024).order(ByteOrder.LITTLE_ENDIAN);
        order.putShort((short) 1);
        order.putShort((short) 0);
        order.put(this.d.getBytes());
        AuthCallback authCallback = this.c;
        if (authCallback != null) {
            authCallback.sendStandAuthData(a(order), 0, new SendAuthResponse() { // from class: com.mijiasdk.bleserver.protocol.BleComboStandardAuth.1
                @Override // com.mijiasdk.bleserver.protocol.BleComboStandardAuth.SendAuthResponse
                public void onResponse(int i, Bundle bundle) {
                    if (i != 0) {
                        Log.e("BleComboStandardAuth", "sendDevInfo failed");
                        BleComboStandardAuth.this.e();
                    }
                }
            });
        }
    }

    private void c() {
        Log.d("BleComboStandardAuth", "sendDevicePubKey");
        KeyPair keyPair = this.e;
        if (keyPair == null) {
            Log.e("BleComboStandardAuth", "sendDevicePubKey failed, mDeviceKeyPair is null");
            e();
            return;
        }
        AuthCallback authCallback = this.c;
        if (authCallback != null) {
            authCallback.sendStandAuthData(SecurityChipUtil.getRawPublicKey(keyPair.getPublic()), 3, new SendAuthResponse() { // from class: com.mijiasdk.bleserver.protocol.BleComboStandardAuth.2
                @Override // com.mijiasdk.bleserver.protocol.BleComboStandardAuth.SendAuthResponse
                public void onResponse(int i, Bundle bundle) {
                    if (i != 0) {
                        Log.e("BleComboStandardAuth", "sendDevicePubKey failed");
                        BleComboStandardAuth.this.e();
                    }
                }
            });
        }
    }

    private byte[] d() {
        return new byte[16];
    }

    private synchronized void a(byte[] bArr) {
        Log.d("BleComboStandardAuth", "recvAppPubKey");
        this.e = SecurityChipUtil.generateEcc256KeyPair();
        byte[] bArr2 = new byte[65];
        bArr2[0] = 4;
        System.arraycopy(bArr, 0, bArr2, 1, bArr.length);
        try {
            this.f = ECCPointConvert.fromUncompressedPoint(bArr2, ((ECPublicKey) this.e.getPublic()).getParams());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (this.f == null) {
            Log.d("BleComboStandardAuth", "recvAppPubKey failed, mAppPubKey is null");
            e();
            return;
        }
        this.g = SecurityChipUtil.getSecret(this.f, this.e.getPrivate());
        if (this.g == null) {
            Log.d("BleComboStandardAuth", "recvAppPubKey failed, eShareKey is null");
            e();
            return;
        }
        this.h = d();
        byte[] a2 = a(this.g.getEncoded(), this.h);
        this.i = new byte[16];
        System.arraycopy(a2, 0, this.i, 0, 16);
        this.j = new byte[16];
        System.arraycopy(a2, 16, this.j, 0, 16);
        this.k = new byte[4];
        System.arraycopy(a2, 32, this.k, 0, 4);
        this.l = new byte[4];
        System.arraycopy(a2, 36, this.l, 0, 4);
        c();
    }

    private byte[] b(byte[] bArr) {
        if (this.l == null || this.j == null) {
            Log.e("BleComboStandardAuth", "decryptAppData failed, mAppIv or mAppKey is null");
            return null;
        } else if (bArr == null || bArr.length <= 2) {
            Log.e("BleComboStandardAuth", "decryptAppData failed, data format error");
            return null;
        } else {
            short s = (short) ((bArr[1] << 8) | (bArr[0] & 255));
            byte[] bArr2 = new byte[bArr.length - 2];
            System.arraycopy(bArr, 2, bArr2, 0, bArr.length - 2);
            if (!this.m.a(s)) {
                Log.e("BleComboStandardAuth", "decryptAppData failed, app cnt error");
                return null;
            }
            this.m.b(s);
            byte[] bArr3 = new byte[12];
            System.arraycopy(this.l, 0, bArr3, 0, 4);
            System.arraycopy(this.m.b(), 0, bArr3, 8, 4);
            return SecurityChipUtil.AESDecrypt(this.j, bArr3, bArr2);
        }
    }

    private void c(byte[] bArr) {
        Log.d("BleComboStandardAuth", "recvWifiConfig");
        if (this.l == null || this.i == null) {
            Log.d("BleComboStandardAuth", "recvWifiConfig failed, mAppIv or mDeviceKey is null");
            e();
            return;
        }
        byte[] b2 = b(bArr);
        if (b2 == null) {
            Log.d("BleComboStandardAuth", "decrypt wifi config failed");
            e();
            return;
        }
        BleComboWifiConfig bleComboWifiConfig = new BleComboWifiConfig();
        int i = 0;
        while (true) {
            if (i < b2.length) {
                int i2 = i + 1;
                int i3 = b2[i] & 255;
                if (i2 >= b2.length) {
                    Log.e("BleComboStandardAuth", "format error 1");
                } else {
                    int i4 = i2 + 1;
                    int i5 = b2[i2] & 255;
                    int i6 = i4 + i5;
                    if (i6 > b2.length) {
                        Log.e("BleComboStandardAuth", "format error 2");
                    } else if (i5 == 0) {
                        i = i4;
                    } else {
                        byte[] bArr2 = new byte[i5];
                        System.arraycopy(b2, i4, bArr2, 0, i5);
                        switch (i3) {
                            case 0:
                                String str = "";
                                try {
                                    str = String.valueOf(ByteUtils.toLong(bArr2));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                bleComboWifiConfig.uid = str;
                                break;
                            case 1:
                                bleComboWifiConfig.ssid = new String(bArr2);
                                break;
                            case 2:
                                bleComboWifiConfig.password = new String(bArr2);
                                break;
                            case 3:
                            case 4:
                            default:
                                Log.e("BleComboStandardAuth", "can't recognized type: " + i3);
                                break;
                            case 5:
                                bleComboWifiConfig.gmtOffset = ByteUtils.toInt(bArr2);
                                break;
                            case 6:
                                byte[] cutAfterBytes = ByteUtils.cutAfterBytes(bArr2, (byte) 0);
                                if (cutAfterBytes != null && cutAfterBytes.length > 0) {
                                    bleComboWifiConfig.countryDomain = new String(cutAfterBytes);
                                    break;
                                }
                                break;
                            case 7:
                                bleComboWifiConfig.timezone = new String(bArr2);
                                break;
                            case 8:
                                bleComboWifiConfig.configType = new String(bArr2);
                                break;
                            case 9:
                                bleComboWifiConfig.bindKey = new String(bArr2);
                                break;
                            case 10:
                                bleComboWifiConfig.countryCode = new String(bArr2);
                                break;
                            case 11:
                                bleComboWifiConfig.passportUrl = new String(bArr2);
                                break;
                        }
                        i = i6;
                    }
                }
            }
        }
        a(bleComboWifiConfig);
    }

    private void a(BleComboWifiConfig bleComboWifiConfig) {
        AuthCallback authCallback = this.c;
        if (authCallback != null) {
            authCallback.onAuthSuccess(bleComboWifiConfig);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e() {
        AuthCallback authCallback = this.c;
        if (authCallback != null) {
            authCallback.onAuthFailed();
        }
    }

    private byte[] a(ByteBuffer byteBuffer) {
        byte[] bArr = new byte[byteBuffer.position()];
        System.arraycopy(byteBuffer.array(), 0, bArr, 0, bArr.length);
        return bArr;
    }

    private byte[] a(byte[] bArr, byte[] bArr2) {
        try {
            Hkdf instance = Hkdf.getInstance("HmacSHA256");
            instance.init(bArr, bArr2);
            return instance.deriveKey("mible-setup-info".getBytes(), 64);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class a {
        private short a;
        private short b;
        private short c;
        private short d;

        public void a() {
            this.a = (short) 0;
            this.b = (short) 0;
            this.c = (short) 0;
            this.d = (short) 0;
        }

        public boolean a(short s) {
            short s2 = this.a;
            short s3 = this.b;
            return (((s3 & ShortCompanionObject.MIN_VALUE) >> 15) != ((32768 & s) >> 15) ? ((s2 + 1) << 16) + s : (s2 << 16) + s) > (s2 << 16) + s3;
        }

        public void b(short s) {
            int i = (this.b & ShortCompanionObject.MIN_VALUE) >> 15;
            int i2 = (32768 & s) >> 15;
            this.b = s;
            if (i != i2) {
                this.a = (short) (this.a + 1);
            }
        }

        public byte[] b() {
            short s = this.b;
            short s2 = this.a;
            return new byte[]{(byte) (s & 255), (byte) (s >> 8), (byte) (s2 & 255), (byte) (s2 >> 8)};
        }
    }
}
