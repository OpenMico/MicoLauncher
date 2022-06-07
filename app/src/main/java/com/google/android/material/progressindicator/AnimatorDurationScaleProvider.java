package com.google.android.material.progressindicator;

import android.content.ContentResolver;
import android.os.Build;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes2.dex */
public class AnimatorDurationScaleProvider {
    private static float a = 1.0f;

    public float getSystemAnimatorDurationScale(@NonNull ContentResolver contentResolver) {
        if (Build.VERSION.SDK_INT >= 17) {
            return Settings.Global.getFloat(contentResolver, "animator_duration_scale", 1.0f);
        }
        if (Build.VERSION.SDK_INT == 16) {
            return Settings.System.getFloat(contentResolver, "animator_duration_scale", 1.0f);
        }
        return a;
    }

    @VisibleForTesting
    public static void setDefaultSystemAnimatorDurationScale(float f) {
        a = f;
    }
}
