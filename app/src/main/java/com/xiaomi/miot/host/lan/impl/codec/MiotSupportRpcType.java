package com.xiaomi.miot.host.lan.impl.codec;

import com.xiaomi.miot.host.lan.impl.MiotHost;

/* loaded from: classes2.dex */
public class MiotSupportRpcType {
    public static final int DEFAULT = 0;
    public static final int MIOT_SPEC_V2 = 2;
    public static final int MITV = 1;
    public static final int YUNMI = 3;

    public static boolean isMitvType() {
        return MiotHost.sSupportRpcType == 1;
    }

    public static boolean isMiotSpecV2() {
        return MiotHost.sSupportRpcType == 2;
    }

    public static boolean isYunmiType() {
        return MiotHost.sSupportRpcType == 3;
    }
}
