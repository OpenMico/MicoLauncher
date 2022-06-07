package com.google.android.material.ripple;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.util.StateSet;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.core.graphics.ColorUtils;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes2.dex */
public class RippleUtils {
    public static final boolean USE_FRAMEWORK_RIPPLE;
    @VisibleForTesting
    static final String a;
    private static final int[] b;
    private static final int[] c;
    private static final int[] d;
    private static final int[] e;
    private static final int[] f;
    private static final int[] g;
    private static final int[] h;
    private static final int[] i;
    private static final int[] j;
    private static final int[] k;

    static {
        USE_FRAMEWORK_RIPPLE = Build.VERSION.SDK_INT >= 21;
        b = new int[]{16842919};
        c = new int[]{16843623, 16842908};
        d = new int[]{16842908};
        e = new int[]{16843623};
        f = new int[]{16842913, 16842919};
        g = new int[]{16842913, 16843623, 16842908};
        h = new int[]{16842913, 16842908};
        i = new int[]{16842913, 16843623};
        j = new int[]{16842913};
        k = new int[]{16842910, 16842919};
        a = RippleUtils.class.getSimpleName();
    }

    private RippleUtils() {
    }

    @NonNull
    public static ColorStateList convertToRippleDrawableColor(@Nullable ColorStateList colorStateList) {
        if (USE_FRAMEWORK_RIPPLE) {
            return new ColorStateList(new int[][]{j, StateSet.NOTHING}, new int[]{a(colorStateList, f), a(colorStateList, b)});
        }
        int[] iArr = f;
        int[] iArr2 = g;
        int[] iArr3 = h;
        int[] iArr4 = i;
        int[] iArr5 = b;
        int[] iArr6 = c;
        int[] iArr7 = d;
        int[] iArr8 = e;
        return new ColorStateList(new int[][]{iArr, iArr2, iArr3, iArr4, j, iArr5, iArr6, iArr7, iArr8, StateSet.NOTHING}, new int[]{a(colorStateList, iArr), a(colorStateList, iArr2), a(colorStateList, iArr3), a(colorStateList, iArr4), 0, a(colorStateList, iArr5), a(colorStateList, iArr6), a(colorStateList, iArr7), a(colorStateList, iArr8), 0});
    }

    @NonNull
    public static ColorStateList sanitizeRippleDrawableColor(@Nullable ColorStateList colorStateList) {
        if (colorStateList == null) {
            return ColorStateList.valueOf(0);
        }
        if (Build.VERSION.SDK_INT >= 22 && Build.VERSION.SDK_INT <= 27 && Color.alpha(colorStateList.getDefaultColor()) == 0 && Color.alpha(colorStateList.getColorForState(k, 0)) != 0) {
            Log.w(a, "Use a non-transparent color for the default color as it will be used to finish ripple animations.");
        }
        return colorStateList;
    }

    public static boolean shouldDrawRippleCompat(@NonNull int[] iArr) {
        boolean z = false;
        boolean z2 = false;
        for (int i2 : iArr) {
            if (i2 == 16842910) {
                z = true;
            } else if (i2 == 16842908) {
                z2 = true;
            } else if (i2 == 16842919) {
                z2 = true;
            } else if (i2 == 16843623) {
                z2 = true;
            }
        }
        return z && z2;
    }

    @ColorInt
    private static int a(@Nullable ColorStateList colorStateList, int[] iArr) {
        int colorForState = colorStateList != null ? colorStateList.getColorForState(iArr, colorStateList.getDefaultColor()) : 0;
        return USE_FRAMEWORK_RIPPLE ? a(colorForState) : colorForState;
    }

    @ColorInt
    @TargetApi(21)
    private static int a(@ColorInt int i2) {
        return ColorUtils.setAlphaComponent(i2, Math.min(Color.alpha(i2) * 2, 255));
    }
}
