package com.xiaomi.micolauncher.module.setting.utils;

import android.util.MathUtils;

/* loaded from: classes3.dex */
public class BrightnessUtils {
    public static final int GAMMA_SPACE_MAX = 255;

    public static final int convertGammaToLinear(int i, int i2, int i3) {
        float f;
        float norm = MathUtils.norm(0.0f, 255.0f, i);
        if (norm <= 0.5f) {
            f = MathUtils.sq(norm / 0.5f);
        } else {
            f = MathUtils.exp((norm - 0.5599107f) / 0.17883277f) + 0.28466892f;
        }
        return Math.round(MathUtils.lerp(i2, i3, f / 12.0f));
    }

    public static final int convertLinearToGamma(int i, int i2, int i3) {
        float f;
        float norm = MathUtils.norm(i2, i3, i) * 12.0f;
        if (norm <= 1.0f) {
            f = ((float) Math.sqrt(norm)) * 0.5f;
        } else {
            f = (MathUtils.log(norm - 0.28466892f) * 0.17883277f) + 0.5599107f;
        }
        return Math.round(MathUtils.lerp(0.0f, 255.0f, f));
    }
}
