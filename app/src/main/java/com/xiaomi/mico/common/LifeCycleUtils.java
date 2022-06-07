package com.xiaomi.mico.common;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.Nullable;

/* loaded from: classes3.dex */
public class LifeCycleUtils {
    public static boolean isInvalidActivity(@Nullable Context context) {
        if (context == null) {
            return true;
        }
        if (!(context instanceof Activity)) {
            return false;
        }
        return !isValidActivity((Activity) context);
    }

    public static boolean isValidActivity(@Nullable Activity activity) {
        return activity != null && !activity.isFinishing() && !activity.isDestroyed();
    }

    private LifeCycleUtils() {
    }
}
