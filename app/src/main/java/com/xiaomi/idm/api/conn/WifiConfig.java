package com.xiaomi.idm.api.conn;

import com.google.protobuf.InvalidProtocolBufferException;
import com.xiaomi.idm.api.proto.IDMServiceProto;
import com.xiaomi.mi_connect_sdk.util.LogUtil;
import com.xiaomi.mipush.sdk.Constants;

/* loaded from: classes3.dex */
public class WifiConfig extends ConnConfig {
    String a;
    String b;
    boolean c;
    int d;
    String e;
    String f;
    String g;

    public static WifiConfig buildFromProto(byte[] bArr) {
        IDMServiceProto.WifiConfig wifiConfig = null;
        if (bArr == null) {
            return null;
        }
        try {
            wifiConfig = IDMServiceProto.WifiConfig.parseFrom(bArr);
        } catch (InvalidProtocolBufferException e) {
            LogUtil.e("WifiConfig", e.getMessage(), e);
        }
        return buildFromProto(wifiConfig);
    }

    public static WifiConfig buildFromProto(IDMServiceProto.WifiConfig wifiConfig) {
        if (wifiConfig == null) {
            return null;
        }
        WifiConfig wifiConfig2 = new WifiConfig();
        wifiConfig2.a = wifiConfig.getSsid();
        wifiConfig2.b = wifiConfig.getPwd();
        wifiConfig2.c = wifiConfig.getUse5GBand();
        wifiConfig2.d = wifiConfig.getChannel();
        wifiConfig2.e = wifiConfig.getMacAddr();
        wifiConfig2.f = wifiConfig.getRemoteIp();
        wifiConfig2.g = wifiConfig.getLocalIp();
        return wifiConfig2;
    }

    @Override // com.xiaomi.idm.api.conn.ConnConfig
    public IDMServiceProto.WifiConfig toProto() {
        IDMServiceProto.WifiConfig.Builder newBuilder = IDMServiceProto.WifiConfig.newBuilder();
        String str = this.a;
        if (str != null) {
            newBuilder.setSsid(str);
        }
        String str2 = this.b;
        if (str2 != null) {
            newBuilder.setPwd(str2);
        }
        newBuilder.setUse5GBand(isUse5GBand());
        newBuilder.setChannel(this.d);
        String str3 = this.e;
        if (str3 != null) {
            newBuilder.setMacAddr(str3);
        }
        String str4 = this.f;
        if (str4 != null) {
            newBuilder.setRemoteIp(str4);
        }
        String str5 = this.g;
        if (str5 != null) {
            newBuilder.setLocalIp(str5);
        }
        return newBuilder.build();
    }

    public String toQCodeString() {
        return this.a + Constants.ACCEPT_TIME_SEPARATOR_SP + this.b + Constants.ACCEPT_TIME_SEPARATOR_SP + this.e + Constants.ACCEPT_TIME_SEPARATOR_SP + this.d;
    }

    public String getSsid() {
        return this.a;
    }

    public void setSsid(String str) {
        this.a = str;
    }

    public String getPwd() {
        return this.b;
    }

    public void setPwd(String str) {
        this.b = str;
    }

    public boolean isUse5GBand() {
        return this.c;
    }

    public void setUse5GBand(boolean z) {
        this.c = z;
    }

    public int getChannel() {
        return this.d;
    }

    public void setChannel(int i) {
        this.d = i;
    }

    public String getMacAddr() {
        return this.e;
    }

    public void setMacAddr(String str) {
        this.e = str;
    }

    public String getRemoteIp() {
        return this.f;
    }

    public void setRemoteIp(String str) {
        this.f = str;
    }

    public String getLocalIp() {
        return this.g;
    }

    public void setLocalIp(String str) {
        this.g = str;
    }

    public String toString() {
        return "WifiConfig{use5GBand=" + this.c + ", ssid='" + this.a + "', pwd='" + this.b + "', channel=" + this.d + ", macAddr='" + this.e + "', localIp='" + this.g + "', remoteIp='" + this.f + "'}";
    }
}
