package com.xiaomi.mi_connect_sdk.api;

/* loaded from: classes3.dex */
public class PayloadConfig {
    private int endPointId;
    private byte[] payload;
    private int roleType;

    public void setEndPointId(int i) {
        this.endPointId = i;
    }

    public int getEndPointId() {
        return this.endPointId;
    }

    public void setPayload(byte[] bArr) {
        this.payload = bArr;
    }

    public byte[] getPayload() {
        return this.payload;
    }

    public int getRoleType() {
        return this.roleType;
    }

    public void setRoleType(int i) {
        this.roleType = i;
    }
}
