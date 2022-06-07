package com.xiaomi.onetrack.api;

import com.xiaomi.onetrack.Configuration;

/* loaded from: classes4.dex */
public class a {
    private static final int a = 1;
    private static final int b = 2;
    private static final int c = 4;
    private static final int d = 8;
    private static final int e = 16;

    public static int a(Configuration configuration) {
        int i = 0;
        if (configuration == null) {
            return 0;
        }
        if (configuration.isGAIDEnable()) {
            i = 1;
        }
        if (configuration.isIMSIEnable()) {
            i |= 2;
        }
        if (configuration.isIMEIEnable()) {
            i |= 4;
        }
        if (configuration.isExceptionCatcherEnable()) {
            i |= 8;
        }
        return configuration.isOverrideMiuiRegionSetting() ? i | 16 : i;
    }
}
