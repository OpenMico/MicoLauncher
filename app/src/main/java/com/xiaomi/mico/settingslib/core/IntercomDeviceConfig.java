package com.xiaomi.mico.settingslib.core;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes3.dex */
public class IntercomDeviceConfig {
    public static final int ACTION_GET = 0;
    public static final int ACTION_NONE = -1;
    public static final int ACTION_UPLOAD = 1;
    public int action;
    public boolean night_mode_no_disturbing;
    public long refreshTime;
    public boolean support_intercom;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes3.dex */
    public @interface INTERCOM_CONFIG_ACTION {
    }

    public IntercomDeviceConfig(boolean z, boolean z2, int i, long j) {
        this.support_intercom = z;
        this.night_mode_no_disturbing = z2;
        this.action = i;
        this.refreshTime = j;
    }
}
