package com.umeng.commonsdk.statistics.proto;

import com.umeng.analytics.pro.au;

/* loaded from: classes2.dex */
public enum Gender implements au {
    MALE(0),
    FEMALE(1),
    UNKNOWN(2);
    
    private final int value;

    Gender(int i) {
        this.value = i;
    }

    @Override // com.umeng.analytics.pro.au
    public int getValue() {
        return this.value;
    }

    public static Gender findByValue(int i) {
        switch (i) {
            case 0:
                return MALE;
            case 1:
                return FEMALE;
            case 2:
                return UNKNOWN;
            default:
                return null;
        }
    }
}
