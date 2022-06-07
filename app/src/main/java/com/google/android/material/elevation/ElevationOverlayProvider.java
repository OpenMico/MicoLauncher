package com.google.android.material.elevation;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.core.graphics.ColorUtils;
import com.google.android.material.R;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialAttributes;

/* loaded from: classes2.dex */
public class ElevationOverlayProvider {
    private final boolean a;
    private final int b;
    private final int c;
    private final float d;

    public ElevationOverlayProvider(@NonNull Context context) {
        this.a = MaterialAttributes.resolveBoolean(context, R.attr.elevationOverlayEnabled, false);
        this.b = MaterialColors.getColor(context, R.attr.elevationOverlayColor, 0);
        this.c = MaterialColors.getColor(context, R.attr.colorSurface, 0);
        this.d = context.getResources().getDisplayMetrics().density;
    }

    @ColorInt
    public int compositeOverlayWithThemeSurfaceColorIfNeeded(float f, @NonNull View view) {
        return compositeOverlayWithThemeSurfaceColorIfNeeded(f + getParentAbsoluteElevation(view));
    }

    @ColorInt
    public int compositeOverlayWithThemeSurfaceColorIfNeeded(float f) {
        return compositeOverlayIfNeeded(this.c, f);
    }

    @ColorInt
    public int compositeOverlayIfNeeded(@ColorInt int i, float f, @NonNull View view) {
        return compositeOverlayIfNeeded(i, f + getParentAbsoluteElevation(view));
    }

    @ColorInt
    public int compositeOverlayIfNeeded(@ColorInt int i, float f) {
        return (!this.a || !a(i)) ? i : compositeOverlay(i, f);
    }

    @ColorInt
    public int compositeOverlay(@ColorInt int i, float f, @NonNull View view) {
        return compositeOverlay(i, f + getParentAbsoluteElevation(view));
    }

    @ColorInt
    public int compositeOverlay(@ColorInt int i, float f) {
        float calculateOverlayAlphaFraction = calculateOverlayAlphaFraction(f);
        return ColorUtils.setAlphaComponent(MaterialColors.layer(ColorUtils.setAlphaComponent(i, 255), this.b, calculateOverlayAlphaFraction), Color.alpha(i));
    }

    public int calculateOverlayAlpha(float f) {
        return Math.round(calculateOverlayAlphaFraction(f) * 255.0f);
    }

    public float calculateOverlayAlphaFraction(float f) {
        float f2 = this.d;
        if (f2 <= 0.0f || f <= 0.0f) {
            return 0.0f;
        }
        return Math.min(((((float) Math.log1p(f / f2)) * 4.5f) + 2.0f) / 100.0f, 1.0f);
    }

    public boolean isThemeElevationOverlayEnabled() {
        return this.a;
    }

    @ColorInt
    public int getThemeElevationOverlayColor() {
        return this.b;
    }

    @ColorInt
    public int getThemeSurfaceColor() {
        return this.c;
    }

    public float getParentAbsoluteElevation(@NonNull View view) {
        return ViewUtils.getParentAbsoluteElevation(view);
    }

    private boolean a(@ColorInt int i) {
        return ColorUtils.setAlphaComponent(i, 255) == this.c;
    }
}
