package com.xiaomi.passport.ui.settings;

import android.content.Context;
import android.view.accessibility.AccessibilityManager;

/* loaded from: classes4.dex */
public class AccessibilityUtil {
    public static boolean isTallBackActive(Context context) {
        AccessibilityManager accessibilityManager = (AccessibilityManager) context.getSystemService("accessibility");
        return accessibilityManager.isEnabled() && accessibilityManager.isTouchExplorationEnabled();
    }
}
