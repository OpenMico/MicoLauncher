package com.xiaomi.micolauncher.module.setting.utils;

/* loaded from: classes3.dex */
public class MiotProvisionSessionId {
    public static int generateSessionId() {
        return (int) (((Math.random() * 9.0d) + 1.0d) * 100000.0d);
    }
}
