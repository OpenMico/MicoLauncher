package com.milink.base.contract;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.CLASS)
/* loaded from: classes2.dex */
public @interface DeviceCategory {
    public static final int IOT = 6;
    public static final int NOTEBOOK = 3;
    public static final int PAD = 18;
    public static final int PHONE = 1;
    public static final int ROUTER = 5;
    public static final int SMART_SPEAKER = 4;
    public static final int SOUND_BOX = 16;
    public static final int TV = 2;
    public static final int UNKNOWN = 0;
    public static final int WATCH = 17;
}
