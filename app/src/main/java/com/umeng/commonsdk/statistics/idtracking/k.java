package com.umeng.commonsdk.statistics.idtracking;

import com.umeng.commonsdk.statistics.common.DeviceConfig;

/* compiled from: SerialTracker.java */
/* loaded from: classes2.dex */
public class k extends a {
    private static final String a = "serial";

    public k() {
        super(a);
    }

    @Override // com.umeng.commonsdk.statistics.idtracking.a
    public String f() {
        return DeviceConfig.getSerial();
    }
}
