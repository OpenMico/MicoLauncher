package com.xiaomi.idm.api.conn;

import com.google.protobuf.InvalidProtocolBufferException;
import com.xiaomi.idm.api.proto.IDMServiceProto;
import com.xiaomi.mi_connect_sdk.util.LogUtil;

/* loaded from: classes3.dex */
public class BTConfig extends ConnConfig {
    private String a;
    private int b;

    public static BTConfig buildFromProto(byte[] bArr) {
        IDMServiceProto.BTConfig bTConfig = null;
        if (bArr == null) {
            return null;
        }
        try {
            bTConfig = IDMServiceProto.BTConfig.parseFrom(bArr);
        } catch (InvalidProtocolBufferException e) {
            LogUtil.e("BTConfig", e.getMessage(), e);
        }
        return buildFromProto(bTConfig);
    }

    public static BTConfig buildFromProto(IDMServiceProto.BTConfig bTConfig) {
        if (bTConfig == null) {
            return null;
        }
        BTConfig bTConfig2 = new BTConfig();
        bTConfig2.a = bTConfig.getStaticBTAddress();
        bTConfig2.b = bTConfig.getRssi();
        return bTConfig2;
    }

    @Override // com.xiaomi.idm.api.conn.ConnConfig
    public IDMServiceProto.BTConfig toProto() {
        IDMServiceProto.BTConfig.Builder newBuilder = IDMServiceProto.BTConfig.newBuilder();
        newBuilder.setRssi(this.b);
        String str = this.a;
        if (str != null) {
            newBuilder.setStaticBTAddress(str);
        }
        return newBuilder.build();
    }
}
