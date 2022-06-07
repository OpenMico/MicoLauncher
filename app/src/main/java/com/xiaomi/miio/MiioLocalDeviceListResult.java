package com.xiaomi.miio;

import java.util.List;

/* loaded from: classes3.dex */
public class MiioLocalDeviceListResult {
    public MiioLocalErrorCode code;
    public List<MiioLocalRpcResult> list;

    public MiioLocalDeviceListResult(MiioLocalErrorCode miioLocalErrorCode) {
        this.code = miioLocalErrorCode;
    }

    public MiioLocalDeviceListResult(MiioLocalErrorCode miioLocalErrorCode, List<MiioLocalRpcResult> list) {
        this.code = miioLocalErrorCode;
        this.list = list;
    }
}
