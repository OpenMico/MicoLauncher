package com.xiaomi.idm.api.conn;

import android.util.Base64;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.xiaomi.idm.api.proto.IDMServiceProto;
import com.xiaomi.mi_connect_sdk.util.LogUtil;
import java.util.Arrays;

/* loaded from: classes3.dex */
public class ConnParam {
    public static final int BLE_GATT = 6;
    public static final int BT_GATT = 5;
    public static final int BT_RFCOMM = 4;
    public static final int COAP = 7;
    public static final int CONNTYPE_WIFI_P2P_GC = 1;
    public static final int CONNTYPE_WIFI_P2P_GO = 0;
    public static final int CONNTYPE_WIFI_SOFTAP = 2;
    public static final int CONNTYPE_WIFI_STATION = 3;
    public static final int IDB = 9;
    public static final int NFC = 8;
    public static final int WLAN_GC_SOFTAP = 12;
    public static final int WLAN_P2P = 10;
    public static final int WLAN_SOFTAP = 11;
    private int a;
    private int b;
    private String c;
    private String d;
    private int e;
    private byte[] f;
    private ConnConfig g;

    public void setConnType(int i) {
        this.a = i;
    }

    public int getConnType() {
        return this.a;
    }

    public ConnConfig getConfig() {
        return this.g;
    }

    public void setConfig(ConnConfig connConfig) {
        this.g = connConfig;
    }

    public int getErrCode() {
        return this.b;
    }

    public String getErrMsg() {
        return this.c;
    }

    public String getIdHash() {
        return this.d;
    }

    public void setIdHash(String str) {
        this.d = str;
    }

    public int getConnLevel() {
        return this.e;
    }

    public void setConnLevel(int i) {
        this.e = i;
    }

    public byte[] getPrivateData() {
        return this.f;
    }

    public void setPrivateData(byte[] bArr) {
        this.f = bArr;
    }

    public static ConnParam buildFromQRCodeProto(String str) {
        IDMServiceProto.ConnectionQRCode connectionQRCode = null;
        if (str == null || str.isEmpty()) {
            return null;
        }
        try {
            connectionQRCode = IDMServiceProto.ConnectionQRCode.parseFrom(Base64.decode(str, 0));
        } catch (InvalidProtocolBufferException | IllegalArgumentException e) {
            LogUtil.d("ConnParam", "QR Code: " + str, new Object[0]);
            LogUtil.e("ConnParam", e.getMessage(), e);
        }
        return buildFromQRCodeProto(connectionQRCode);
    }

    public static ConnParam buildFromQRCodeProto(IDMServiceProto.ConnectionQRCode connectionQRCode) {
        if (connectionQRCode == null) {
            return null;
        }
        ConnParam connParam = new ConnParam();
        int i = 1;
        if (!(connectionQRCode.getConnType() == 2 || connectionQRCode.getConnType() == 0)) {
            i = -1;
        }
        if (i == -1) {
            LogUtil.e("ConnParam", "Illegal connType!", new Object[0]);
            return null;
        }
        connParam.setConnType(i);
        connParam.setIdHash(connectionQRCode.getIdHash());
        WifiConfig wifiConfig = new WifiConfig();
        wifiConfig.a = connectionQRCode.getSsid();
        wifiConfig.b = connectionQRCode.getPwd();
        wifiConfig.d = connectionQRCode.getChannel();
        wifiConfig.e = connectionQRCode.getMacAddr();
        connParam.setConfig(wifiConfig);
        return connParam;
    }

    public IDMServiceProto.ConnParam toProto() {
        IDMServiceProto.ConnParam.Builder newBuilder = IDMServiceProto.ConnParam.newBuilder();
        switch (this.a) {
            case 0:
                newBuilder.setConnType(IDMServiceProto.ConnParam.ConnType.WIFI_P2P_GO);
                break;
            case 1:
                newBuilder.setConnType(IDMServiceProto.ConnParam.ConnType.WIFI_P2P_GC);
                break;
            case 2:
                newBuilder.setConnType(IDMServiceProto.ConnParam.ConnType.WIFI_SOFTAP);
                break;
            case 3:
                newBuilder.setConnType(IDMServiceProto.ConnParam.ConnType.WIFI_STATION);
                break;
            case 4:
                newBuilder.setConnType(IDMServiceProto.ConnParam.ConnType.BT_RFCOMM);
                break;
            case 5:
                newBuilder.setConnType(IDMServiceProto.ConnParam.ConnType.BT_GATT);
                break;
            case 6:
                newBuilder.setConnType(IDMServiceProto.ConnParam.ConnType.BLE_GATT);
                break;
            case 7:
            case 8:
            case 9:
            default:
                newBuilder.setConnType(IDMServiceProto.ConnParam.ConnType.UNKNOWN);
                LogUtil.e("ConnParam", "ConnType is unKnown", new Object[0]);
                break;
            case 10:
                newBuilder.setConnType(IDMServiceProto.ConnParam.ConnType.WLAN_P2P);
                break;
            case 11:
                newBuilder.setConnType(IDMServiceProto.ConnParam.ConnType.WLAN_SOFTAP);
                break;
            case 12:
                newBuilder.setConnType(IDMServiceProto.ConnParam.ConnType.WLAN_GC_SOFTAP);
                break;
        }
        ConnConfig connConfig = this.g;
        if (connConfig != null) {
            newBuilder.setConfig(connConfig.toProto().toByteString());
        }
        byte[] bArr = this.f;
        if (bArr != null) {
            newBuilder.setPrivateData(ByteString.copyFrom(bArr));
        }
        newBuilder.setErrCode(this.b);
        String str = this.d;
        if (str != null) {
            newBuilder.setIdHash(str);
        }
        String str2 = this.c;
        if (str2 != null) {
            newBuilder.setErrMsg(str2);
        }
        newBuilder.setConnLevel(this.e);
        return newBuilder.build();
    }

    public static ConnParam buildFromProto(byte[] bArr) {
        IDMServiceProto.ConnParam connParam = null;
        if (bArr == null) {
            return null;
        }
        try {
            connParam = IDMServiceProto.ConnParam.parseFrom(bArr);
        } catch (InvalidProtocolBufferException e) {
            LogUtil.e("ConnParam", e.getMessage(), e);
        }
        return buildFromProto(connParam);
    }

    public static ConnParam buildFromProto(IDMServiceProto.ConnParam connParam) {
        if (connParam == null) {
            return null;
        }
        ConnParam connParam2 = new ConnParam();
        connParam2.a = connParam.getConnTypeValue();
        connParam2.b = connParam.getErrCode();
        connParam2.c = connParam.getErrMsg();
        connParam2.d = connParam.getIdHash();
        connParam2.e = connParam.getConnLevel();
        connParam2.f = connParam.getPrivateData().toByteArray();
        switch (connParam.getConnTypeValue()) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 10:
            case 11:
            case 12:
                connParam2.g = WifiConfig.buildFromProto(connParam.getConfig().toByteArray());
                break;
            case 4:
            case 5:
                connParam2.g = BTConfig.buildFromProto(connParam.getConfig().toByteArray());
                break;
            case 6:
                connParam2.g = BLEConfig.buildFromProto(connParam.getConfig().toByteArray());
            case 7:
            case 8:
            case 9:
            default:
                LogUtil.e("ConnParam", "Unknown ConnType = [" + connParam.getConnTypeValue() + "]", new Object[0]);
                break;
        }
        return connParam2;
    }

    public String toString() {
        return "ConnParam{connType = " + this.a + ", errCode = " + this.b + ", errMsg = '" + this.c + "', idHash = '" + this.d + "', connLevel = '" + this.e + "', config = '" + this.g + "', privateData = '" + Arrays.toString(this.f) + "'}";
    }
}
