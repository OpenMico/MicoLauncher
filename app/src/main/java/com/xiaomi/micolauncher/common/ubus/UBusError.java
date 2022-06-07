package com.xiaomi.micolauncher.common.ubus;

/* loaded from: classes3.dex */
public enum UBusError {
    REMOTE_UBUS_ERROR(100),
    PARAMETER_ERROR(10400),
    METHOD_NONSUPPORT(10404);
    
    private final int code;

    UBusError(int i) {
        this.code = i;
    }

    public int getCode() {
        return this.code;
    }
}
