package com.google.android.material.switchmaterial;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import com.google.android.material.R;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.elevation.ElevationOverlayProvider;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

/* loaded from: classes2.dex */
public class SwitchMaterial extends SwitchCompat {
    private static final int c = R.style.Widget_MaterialComponents_CompoundButton_Switch;
    private static final int[][] d = {new int[]{16842910, 16842912}, new int[]{16842910, -16842912}, new int[]{-16842910, 16842912}, new int[]{-16842910, -16842912}};
    @NonNull
    private final ElevationOverlayProvider e;
    @Nullable
    private ColorStateList f;
    @Nullable
    private ColorStateList g;
    private boolean h;

    public SwitchMaterial(@NonNull Context context) {
        this(context, null);
    }

    public SwitchMaterial(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.switchStyle);
    }

    public SwitchMaterial(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, c), attributeSet, i);
        Context context2 = getContext();
        this.e = new ElevationOverlayProvider(context2);
        TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(context2, attributeSet, R.styleable.SwitchMaterial, i, c, new int[0]);
        this.h = obtainStyledAttributes.getBoolean(R.styleable.SwitchMaterial_useMaterialThemeColors, false);
        obtainStyledAttributes.recycle();
    }

    @Override // android.widget.TextView, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.h && getThumbTintList() == null) {
            setThumbTintList(getMaterialThemeColorsThumbTintList());
        }
        if (this.h && getTrackTintList() == null) {
            setTrackTintList(getMaterialThemeColorsTrackTintList());
        }
    }

    public void setUseMaterialThemeColors(boolean z) {
        this.h = z;
        if (z) {
            setThumbTintList(getMaterialThemeColorsThumbTintList());
            setTrackTintList(getMaterialThemeColorsTrackTintList());
            return;
        }
        setThumbTintList(null);
        setTrackTintList(null);
    }

    public boolean isUseMaterialThemeColors() {
        return this.h;
    }

    private ColorStateList getMaterialThemeColorsThumbTintList() {
        if (this.f == null) {
            int color = MaterialColors.getColor(this, R.attr.colorSurface);
            int color2 = MaterialColors.getColor(this, R.attr.colorControlActivated);
            float dimension = getResources().getDimension(R.dimen.mtrl_switch_thumb_elevation);
            if (this.e.isThemeElevationOverlayEnabled()) {
                dimension += ViewUtils.getParentAbsoluteElevation(this);
            }
            int compositeOverlayIfNeeded = this.e.compositeOverlayIfNeeded(color, dimension);
            int[] iArr = new int[d.length];
            iArr[0] = MaterialColors.layer(color, color2, 1.0f);
            iArr[1] = compositeOverlayIfNeeded;
            iArr[2] = MaterialColors.layer(color, color2, 0.38f);
            iArr[3] = compositeOverlayIfNeeded;
            this.f = new ColorStateList(d, iArr);
        }
        return this.f;
    }

    private ColorStateList getMaterialThemeColorsTrackTintList() {
        if (this.g == null) {
            int[] iArr = new int[d.length];
            int color = MaterialColors.getColor(this, R.attr.colorSurface);
            int color2 = MaterialColors.getColor(this, R.attr.colorControlActivated);
            int color3 = MaterialColors.getColor(this, R.attr.colorOnSurface);
            iArr[0] = MaterialColors.layer(color, color2, 0.54f);
            iArr[1] = MaterialColors.layer(color, color3, 0.32f);
            iArr[2] = MaterialColors.layer(color, color2, 0.12f);
            iArr[3] = MaterialColors.layer(color, color3, 0.12f);
            this.g = new ColorStateList(d, iArr);
        }
        return this.g;
    }
}
