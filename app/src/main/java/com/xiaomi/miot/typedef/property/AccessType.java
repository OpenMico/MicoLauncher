package com.xiaomi.miot.typedef.property;

/* loaded from: classes3.dex */
public class AccessType {
    public static final int GET = 2;
    public static final int NOTIFY = 4;
    public static final int SET = 1;
    private int value;

    public boolean isSettable() {
        return (this.value & 1) == 1;
    }

    public boolean isGettable() {
        return (this.value & 2) == 2;
    }

    public boolean isNotifiable() {
        return (this.value & 4) == 4;
    }

    public int setSettable(boolean z) {
        if (z) {
            this.value |= 1;
        } else {
            this.value &= -2;
        }
        return this.value;
    }

    public int setGettable(boolean z) {
        if (z) {
            this.value |= 2;
        } else {
            this.value &= -3;
        }
        return this.value;
    }

    public int setNotifiable(boolean z) {
        if (z) {
            this.value |= 4;
        } else {
            this.value &= -5;
        }
        return this.value;
    }

    public static AccessType valueOf(int i) {
        AccessType accessType = new AccessType();
        accessType.value = i;
        return accessType;
    }

    public int value() {
        return this.value;
    }
}
