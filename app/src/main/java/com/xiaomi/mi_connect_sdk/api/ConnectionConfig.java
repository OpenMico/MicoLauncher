package com.xiaomi.mi_connect_sdk.api;

/* loaded from: classes3.dex */
public class ConnectionConfig {
    private byte[] commData;
    private int endPointId;
    private boolean endPointTrusted;
    private int roleType;

    public void setEndPointId(int i) {
        this.endPointId = i;
    }

    public int getEndPointId() {
        return this.endPointId;
    }

    public void setEndPointTrusted(boolean z) {
        this.endPointTrusted = z;
    }

    public boolean isEndPointTrusted() {
        return this.endPointTrusted;
    }

    public void setCommData(byte[] bArr) {
        this.commData = bArr;
    }

    public byte[] getCommData() {
        return this.commData;
    }

    public void setRoleType(int i) {
        this.roleType = i;
    }

    public int getRoleType() {
        return this.roleType;
    }
}
