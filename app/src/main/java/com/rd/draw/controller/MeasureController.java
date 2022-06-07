package com.rd.draw.controller;

import android.util.Pair;
import android.view.View;
import androidx.annotation.NonNull;
import com.rd.animation.type.AnimationType;
import com.rd.draw.data.Indicator;
import com.rd.draw.data.Orientation;

/* loaded from: classes2.dex */
public class MeasureController {
    public Pair<Integer, Integer> measureViewSize(@NonNull Indicator indicator, int i, int i2) {
        int i3;
        int i4;
        int i5;
        int i6;
        int mode = View.MeasureSpec.getMode(i);
        int size = View.MeasureSpec.getSize(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        int size2 = View.MeasureSpec.getSize(i2);
        int count = indicator.getCount();
        int radius = indicator.getRadius();
        int stroke = indicator.getStroke();
        int padding = indicator.getPadding();
        int paddingLeft = indicator.getPaddingLeft();
        int paddingTop = indicator.getPaddingTop();
        int paddingRight = indicator.getPaddingRight();
        int paddingBottom = indicator.getPaddingBottom();
        int i7 = radius * 2;
        Orientation orientation = indicator.getOrientation();
        int i8 = 0;
        if (count != 0) {
            i4 = (i7 * count) + (stroke * 2 * count) + (padding * (count - 1));
            i3 = i7 + stroke;
            if (orientation != Orientation.HORIZONTAL) {
                i3 = i4;
                i4 = i3;
            }
        } else {
            i4 = 0;
            i3 = 0;
        }
        if (indicator.getAnimationType() == AnimationType.DROP) {
            if (orientation == Orientation.HORIZONTAL) {
                i3 *= 2;
            } else {
                i4 *= 2;
            }
        }
        int i9 = paddingLeft + paddingRight;
        int i10 = paddingTop + paddingBottom;
        if (orientation == Orientation.HORIZONTAL) {
            i6 = i4 + i9;
            i5 = i3 + i10;
        } else {
            i6 = i4 + i9;
            i5 = i3 + i10;
        }
        if (mode != 1073741824) {
            size = mode == Integer.MIN_VALUE ? Math.min(i6, size) : i6;
        }
        if (mode2 != 1073741824) {
            size2 = mode2 == Integer.MIN_VALUE ? Math.min(i5, size2) : i5;
        }
        if (size < 0) {
            size = 0;
        }
        if (size2 >= 0) {
            i8 = size2;
        }
        indicator.setWidth(size);
        indicator.setHeight(i8);
        return new Pair<>(Integer.valueOf(size), Integer.valueOf(i8));
    }
}
