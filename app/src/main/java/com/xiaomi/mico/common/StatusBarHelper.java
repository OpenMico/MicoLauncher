package com.xiaomi.mico.common;

import android.annotation.TargetApi;
import android.app.Activity;
import android.view.Window;

/* loaded from: classes3.dex */
public class StatusBarHelper {
    public static void setStatusBarMode(Activity activity, boolean z) {
        Window window = activity.getWindow();
        if (window != null) {
            a(window, 1280, true);
            a(window, z);
        }
    }

    @TargetApi(23)
    private static void a(Window window, boolean z) {
        a(window, 8192, z);
    }

    private static void a(Window window, int i, boolean z) {
        int systemUiVisibility = window.getDecorView().getSystemUiVisibility();
        window.getDecorView().setSystemUiVisibility(z ? i | systemUiVisibility : (~i) & systemUiVisibility);
    }
}
