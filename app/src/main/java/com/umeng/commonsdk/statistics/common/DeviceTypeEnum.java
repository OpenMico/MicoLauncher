package com.umeng.commonsdk.statistics.common;

import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.onetrack.api.b;

/* loaded from: classes2.dex */
public enum DeviceTypeEnum {
    IMEI(OneTrack.Param.IMEI_MD5, OneTrack.Param.IMEI_MD5),
    OAID(OneTrack.Param.OAID, OneTrack.Param.OAID),
    ANDROIDID("android_id", "android_id"),
    MAC(b.B, b.B),
    SERIALNO("serial_no", "serial_no"),
    IDFA("idfa", "idfa"),
    DEFAULT("null", "null");
    
    private String description;
    private String deviceIdType;

    DeviceTypeEnum(String str, String str2) {
        this.deviceIdType = str;
        this.description = str2;
    }

    public String getDeviceIdType() {
        return this.deviceIdType;
    }

    public String getDescription() {
        return this.description;
    }
}
