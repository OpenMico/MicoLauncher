package com.xiaomi.miot.typedef.property;

/* loaded from: classes3.dex */
public enum AllowedValueType {
    ANY,
    LIST,
    RANGE;

    public static AllowedValueType retrieveType(int i) {
        switch (i) {
            case 0:
                return ANY;
            case 1:
                return LIST;
            case 2:
                return RANGE;
            default:
                return ANY;
        }
    }

    public int toInt() {
        switch (this) {
            case ANY:
                return 0;
            case LIST:
                return 1;
            case RANGE:
                return 2;
            default:
                return 0;
        }
    }
}
