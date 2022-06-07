package com.rd.draw.controller;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.rd.animation.type.AnimationType;
import com.rd.animation.type.BaseAnimation;
import com.rd.animation.type.ColorAnimation;
import com.rd.draw.data.Indicator;
import com.rd.draw.data.Orientation;
import com.rd.draw.data.RtlMode;
import com.rd.pageindicatorview.R;
import com.rd.utils.DensityUtils;

/* loaded from: classes2.dex */
public class AttributeController {
    private Indicator a;

    public AttributeController(@NonNull Indicator indicator) {
        this.a = indicator;
    }

    public void init(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.PageIndicatorView, 0, 0);
        a(obtainStyledAttributes);
        b(obtainStyledAttributes);
        c(obtainStyledAttributes);
        d(obtainStyledAttributes);
        obtainStyledAttributes.recycle();
    }

    private void a(@NonNull TypedArray typedArray) {
        int resourceId = typedArray.getResourceId(R.styleable.PageIndicatorView_piv_viewPager, -1);
        boolean z = typedArray.getBoolean(R.styleable.PageIndicatorView_piv_autoVisibility, true);
        int i = 0;
        boolean z2 = typedArray.getBoolean(R.styleable.PageIndicatorView_piv_dynamicCount, false);
        int i2 = typedArray.getInt(R.styleable.PageIndicatorView_piv_count, -1);
        if (i2 == -1) {
            i2 = 3;
        }
        int i3 = typedArray.getInt(R.styleable.PageIndicatorView_piv_select, 0);
        if (i3 >= 0 && (i2 <= 0 || i3 <= i2 - 1)) {
            i = i3;
        }
        this.a.setViewPagerId(resourceId);
        this.a.setAutoVisibility(z);
        this.a.setDynamicCount(z2);
        this.a.setCount(i2);
        this.a.setSelectedPosition(i);
        this.a.setSelectingPosition(i);
        this.a.setLastSelectedPosition(i);
    }

    private void b(@NonNull TypedArray typedArray) {
        int color = typedArray.getColor(R.styleable.PageIndicatorView_piv_unselectedColor, Color.parseColor(ColorAnimation.DEFAULT_UNSELECTED_COLOR));
        int color2 = typedArray.getColor(R.styleable.PageIndicatorView_piv_selectedColor, Color.parseColor(ColorAnimation.DEFAULT_SELECTED_COLOR));
        this.a.setUnselectedColor(color);
        this.a.setSelectedColor(color2);
    }

    private void c(@NonNull TypedArray typedArray) {
        boolean z = typedArray.getBoolean(R.styleable.PageIndicatorView_piv_interactiveAnimation, false);
        long j = typedArray.getInt(R.styleable.PageIndicatorView_piv_animationDuration, BaseAnimation.DEFAULT_ANIMATION_TIME);
        if (j < 0) {
            j = 0;
        }
        AnimationType a = a(typedArray.getInt(R.styleable.PageIndicatorView_piv_animationType, AnimationType.NONE.ordinal()));
        RtlMode b = b(typedArray.getInt(R.styleable.PageIndicatorView_piv_rtl_mode, RtlMode.Off.ordinal()));
        boolean z2 = typedArray.getBoolean(R.styleable.PageIndicatorView_piv_fadeOnIdle, false);
        long j2 = typedArray.getInt(R.styleable.PageIndicatorView_piv_idleDuration, 3000);
        this.a.setAnimationDuration(j);
        this.a.setInteractiveAnimation(z);
        this.a.setAnimationType(a);
        this.a.setRtlMode(b);
        this.a.setFadeOnIdle(z2);
        this.a.setIdleDuration(j2);
    }

    private void d(@NonNull TypedArray typedArray) {
        Orientation orientation;
        if (typedArray.getInt(R.styleable.PageIndicatorView_piv_orientation, Orientation.HORIZONTAL.ordinal()) == 0) {
            orientation = Orientation.HORIZONTAL;
        } else {
            orientation = Orientation.VERTICAL;
        }
        int dimension = (int) typedArray.getDimension(R.styleable.PageIndicatorView_piv_radius, DensityUtils.dpToPx(6));
        if (dimension < 0) {
            dimension = 0;
        }
        int dimension2 = (int) typedArray.getDimension(R.styleable.PageIndicatorView_piv_padding, DensityUtils.dpToPx(8));
        if (dimension2 < 0) {
            dimension2 = 0;
        }
        float f = typedArray.getFloat(R.styleable.PageIndicatorView_piv_scaleFactor, 0.7f);
        if (f < 0.3f) {
            f = 0.3f;
        } else if (f > 1.0f) {
            f = 1.0f;
        }
        int dimension3 = (int) typedArray.getDimension(R.styleable.PageIndicatorView_piv_strokeWidth, DensityUtils.dpToPx(1));
        if (dimension3 > dimension) {
            dimension3 = dimension;
        }
        if (this.a.getAnimationType() != AnimationType.FILL) {
            dimension3 = 0;
        }
        this.a.setRadius(dimension);
        this.a.setOrientation(orientation);
        this.a.setPadding(dimension2);
        this.a.setScaleFactor(f);
        this.a.setStroke(dimension3);
    }

    private AnimationType a(int i) {
        switch (i) {
            case 0:
                return AnimationType.NONE;
            case 1:
                return AnimationType.COLOR;
            case 2:
                return AnimationType.SCALE;
            case 3:
                return AnimationType.WORM;
            case 4:
                return AnimationType.SLIDE;
            case 5:
                return AnimationType.FILL;
            case 6:
                return AnimationType.THIN_WORM;
            case 7:
                return AnimationType.DROP;
            case 8:
                return AnimationType.SWAP;
            case 9:
                return AnimationType.SCALE_DOWN;
            default:
                return AnimationType.NONE;
        }
    }

    private RtlMode b(int i) {
        switch (i) {
            case 0:
                return RtlMode.On;
            case 1:
                return RtlMode.Off;
            case 2:
                return RtlMode.Auto;
            default:
                return RtlMode.Auto;
        }
    }
}
