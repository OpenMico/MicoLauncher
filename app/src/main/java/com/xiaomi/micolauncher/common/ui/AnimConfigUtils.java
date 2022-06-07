package com.xiaomi.micolauncher.common.ui;

import miuix.animation.base.AnimConfig;
import miuix.animation.utils.EaseManager;

/* loaded from: classes3.dex */
public class AnimConfigUtils {
    private static final AnimConfig a = new AnimConfig();

    static {
        a.setEase(new EaseManager.InterpolateEaseStyle(20, new float[0]).setDuration(150L));
    }

    public static AnimConfig getAnimConfig() {
        return a;
    }
}
