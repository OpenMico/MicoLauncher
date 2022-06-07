package com.xiaomi.miio;

/* loaded from: classes3.dex */
public class MiioLocalDeviceResult extends MiioLocalRpcResult {
    public MiioLocalDeviceResult(MiioLocalErrorCode miioLocalErrorCode) {
        super(miioLocalErrorCode);
    }

    public MiioLocalDeviceResult(MiioLocalErrorCode miioLocalErrorCode, String str, long j, int i, String str2, String str3) {
        super(miioLocalErrorCode, str, j, i, str2, str3);
    }
}
