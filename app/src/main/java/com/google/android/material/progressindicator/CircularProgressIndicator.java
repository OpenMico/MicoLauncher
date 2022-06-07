package com.google.android.material.progressindicator;

import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.annotation.RestrictTo;
import com.google.android.material.R;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes2.dex */
public final class CircularProgressIndicator extends BaseProgressIndicator<CircularProgressIndicatorSpec> {
    public static final int DEF_STYLE_RES = R.style.Widget_MaterialComponents_CircularProgressIndicator;
    public static final int INDICATOR_DIRECTION_CLOCKWISE = 0;
    public static final int INDICATOR_DIRECTION_COUNTERCLOCKWISE = 1;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    /* loaded from: classes2.dex */
    public @interface IndicatorDirection {
    }

    public CircularProgressIndicator(@NonNull Context context) {
        this(context, null);
    }

    public CircularProgressIndicator(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.circularProgressIndicatorStyle);
    }

    public CircularProgressIndicator(@NonNull Context context, @Nullable AttributeSet attributeSet, @AttrRes int i) {
        super(context, attributeSet, i, DEF_STYLE_RES);
        c();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: b */
    public CircularProgressIndicatorSpec a(@NonNull Context context, @NonNull AttributeSet attributeSet) {
        return new CircularProgressIndicatorSpec(context, attributeSet);
    }

    private void c() {
        setIndeterminateDrawable(IndeterminateDrawable.createCircularDrawable(getContext(), (CircularProgressIndicatorSpec) this.b));
        setProgressDrawable(DeterminateDrawable.createCircularDrawable(getContext(), (CircularProgressIndicatorSpec) this.b));
    }

    @Override // com.google.android.material.progressindicator.BaseProgressIndicator
    public void setTrackThickness(int i) {
        super.setTrackThickness(i);
        ((CircularProgressIndicatorSpec) this.b).a();
    }

    @Px
    public int getIndicatorInset() {
        return ((CircularProgressIndicatorSpec) this.b).indicatorInset;
    }

    public void setIndicatorInset(@Px int i) {
        if (((CircularProgressIndicatorSpec) this.b).indicatorInset != i) {
            ((CircularProgressIndicatorSpec) this.b).indicatorInset = i;
            invalidate();
        }
    }

    @Px
    public int getIndicatorSize() {
        return ((CircularProgressIndicatorSpec) this.b).indicatorSize;
    }

    public void setIndicatorSize(@Px int i) {
        int max = Math.max(i, getTrackThickness() * 2);
        if (((CircularProgressIndicatorSpec) this.b).indicatorSize != max) {
            ((CircularProgressIndicatorSpec) this.b).indicatorSize = max;
            ((CircularProgressIndicatorSpec) this.b).a();
            invalidate();
        }
    }

    public int getIndicatorDirection() {
        return ((CircularProgressIndicatorSpec) this.b).indicatorDirection;
    }

    public void setIndicatorDirection(int i) {
        ((CircularProgressIndicatorSpec) this.b).indicatorDirection = i;
        invalidate();
    }
}
