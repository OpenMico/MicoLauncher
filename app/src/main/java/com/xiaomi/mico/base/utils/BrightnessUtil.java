package com.xiaomi.mico.base.utils;

import android.view.Window;
import android.view.WindowManager;

/* loaded from: classes3.dex */
public class BrightnessUtil {
    public static void setBrightness(Window window, float f) {
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.screenBrightness = f;
        window.setAttributes(attributes);
    }
}
