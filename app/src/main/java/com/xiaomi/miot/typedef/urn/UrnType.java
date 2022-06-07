package com.xiaomi.miot.typedef.urn;

import com.xiaomi.idm.service.iot.PropertyService;

/* loaded from: classes3.dex */
public enum UrnType {
    UNDEFINED("undefined"),
    DEVICE("device"),
    SERVICE("service"),
    ACTION("action"),
    PROPERTY(PropertyService.SUPERTYPE),
    EVENT("event");
    
    private String string;

    UrnType(String str) {
        this.string = str;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.string;
    }

    public static UrnType retrieveType(String str) {
        UrnType[] values = values();
        for (UrnType urnType : values) {
            if (urnType.toString().equals(str)) {
                return urnType;
            }
        }
        return UNDEFINED;
    }
}
