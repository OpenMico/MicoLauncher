package com.efs.sdk.pa.config;

/* loaded from: classes.dex */
public enum PackageLevel {
    DAILY_DEVELOP(1),
    INTERNAL_TEST(2),
    TRIAL(3),
    RELEASE(4);
    
    private int mLevel;

    PackageLevel(int i) {
        this.mLevel = i;
    }

    public final int getLevel() {
        return this.mLevel;
    }
}
