package com.xiaomi.idm.api.conn;

import com.google.protobuf.InvalidProtocolBufferException;
import com.xiaomi.idm.api.proto.IDMServiceProto;
import com.xiaomi.mi_connect_sdk.util.LogUtil;

/* loaded from: classes3.dex */
public class BLEConfig extends ConnConfig {
    private int a;
    private String b;
    private int c;

    public static BLEConfig buildFromProto(byte[] bArr) {
        IDMServiceProto.BLEConfig bLEConfig = null;
        if (bArr == null) {
            return null;
        }
        try {
            bLEConfig = IDMServiceProto.BLEConfig.parseFrom(bArr);
        } catch (InvalidProtocolBufferException e) {
            LogUtil.e("BLEConfig", e.getMessage(), e);
        }
        return buildFromProto(bLEConfig);
    }

    public static BLEConfig buildFromProto(IDMServiceProto.BLEConfig bLEConfig) {
        if (bLEConfig == null) {
            return null;
        }
        BLEConfig bLEConfig2 = new BLEConfig();
        bLEConfig2.a = bLEConfig.getBleRole();
        bLEConfig2.b = bLEConfig.getBleAddress();
        bLEConfig2.c = bLEConfig.getRssi();
        return bLEConfig2;
    }

    @Override // com.xiaomi.idm.api.conn.ConnConfig
    public IDMServiceProto.BLEConfig toProto() {
        IDMServiceProto.BLEConfig.Builder newBuilder = IDMServiceProto.BLEConfig.newBuilder();
        newBuilder.setBleRole(this.a);
        String str = this.b;
        if (str != null) {
            newBuilder.setBleAddress(str);
        }
        newBuilder.setRssi(this.c);
        return newBuilder.build();
    }
}
