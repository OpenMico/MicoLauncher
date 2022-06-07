package com.xiaomi.mi_connect_sdk.api;

/* loaded from: classes3.dex */
public final class AppConfig {
    private final byte[] advData;
    private final int appCommDataType;
    private final int appCommType;
    private final int appRoleType;
    private final byte[] commData;
    private final int[] discAppIds;
    private final int discType;

    public AppConfig(int i, int i2, int i3, byte[] bArr, byte[] bArr2, int i4, int[] iArr) {
        this.appRoleType = i;
        this.discType = i2;
        this.appCommType = i3;
        if (bArr != null) {
            this.advData = (byte[]) bArr.clone();
        } else {
            this.advData = new byte[0];
        }
        if (bArr2 != null) {
            this.commData = (byte[]) bArr2.clone();
        } else {
            this.commData = new byte[0];
        }
        this.appCommDataType = i4;
        if (iArr != null) {
            this.discAppIds = (int[]) iArr.clone();
        } else {
            this.discAppIds = new int[0];
        }
    }

    public int[] getDiscAppIds() {
        return (int[]) this.discAppIds.clone();
    }

    public int getAppRoleType() {
        return this.appRoleType;
    }

    public int getDiscType() {
        return this.discType;
    }

    public int getAppCommType() {
        return this.appCommType;
    }

    public byte[] getAdvData() {
        return (byte[]) this.advData.clone();
    }

    public byte[] getCommData() {
        return (byte[]) this.commData.clone();
    }

    public int getAppCommDataType() {
        return this.appCommDataType;
    }

    /* loaded from: classes3.dex */
    public static class Builder {
        private byte[] advData;
        private int appCommDataType;
        private int appCommType;
        private int appRoleType;
        private byte[] commData;
        private int[] discAppIds;
        private int discType;

        public Builder roleType(int i) {
            this.appRoleType = i;
            return this;
        }

        public Builder discType(int i) {
            this.discType = i;
            return this;
        }

        public Builder commType(int i) {
            this.appCommType = i;
            return this;
        }

        public Builder advData(byte[] bArr) {
            this.advData = bArr;
            return this;
        }

        public Builder commData(byte[] bArr) {
            this.commData = bArr;
            return this;
        }

        public Builder commDataType(int i) {
            this.appCommDataType = i;
            return this;
        }

        public Builder discAppIds(int[] iArr) {
            this.discAppIds = iArr;
            return this;
        }

        public AppConfig build() {
            return new AppConfig(this.appRoleType, this.discType, this.appCommType, this.advData, this.commData, this.appCommDataType, this.discAppIds);
        }
    }
}
