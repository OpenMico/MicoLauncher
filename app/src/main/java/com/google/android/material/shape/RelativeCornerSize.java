package com.google.android.material.shape;

import android.graphics.RectF;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import java.util.Arrays;

/* loaded from: classes2.dex */
public final class RelativeCornerSize implements CornerSize {
    private final float a;

    public RelativeCornerSize(@FloatRange(from = 0.0d, to = 1.0d) float f) {
        this.a = f;
    }

    @FloatRange(from = 0.0d, to = 1.0d)
    public float getRelativePercent() {
        return this.a;
    }

    @Override // com.google.android.material.shape.CornerSize
    public float getCornerSize(@NonNull RectF rectF) {
        return this.a * rectF.height();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof RelativeCornerSize) && this.a == ((RelativeCornerSize) obj).a;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Float.valueOf(this.a)});
    }
}
