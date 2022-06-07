package com.google.android.material.progressindicator;

import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.core.view.ViewCompat;
import com.google.android.material.R;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes2.dex */
public final class LinearProgressIndicator extends BaseProgressIndicator<LinearProgressIndicatorSpec> {
    public static final int DEF_STYLE_RES = R.style.Widget_MaterialComponents_LinearProgressIndicator;
    public static final int INDETERMINATE_ANIMATION_TYPE_CONTIGUOUS = 0;
    public static final int INDETERMINATE_ANIMATION_TYPE_DISJOINT = 1;
    public static final int INDICATOR_DIRECTION_END_TO_START = 3;
    public static final int INDICATOR_DIRECTION_LEFT_TO_RIGHT = 0;
    public static final int INDICATOR_DIRECTION_RIGHT_TO_LEFT = 1;
    public static final int INDICATOR_DIRECTION_START_TO_END = 2;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    /* loaded from: classes2.dex */
    public @interface IndeterminateAnimationType {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    /* loaded from: classes2.dex */
    public @interface IndicatorDirection {
    }

    public LinearProgressIndicator(@NonNull Context context) {
        this(context, null);
    }

    public LinearProgressIndicator(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.linearProgressIndicatorStyle);
    }

    public LinearProgressIndicator(@NonNull Context context, @Nullable AttributeSet attributeSet, @AttrRes int i) {
        super(context, attributeSet, i, DEF_STYLE_RES);
        c();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: b */
    public LinearProgressIndicatorSpec a(@NonNull Context context, @NonNull AttributeSet attributeSet) {
        return new LinearProgressIndicatorSpec(context, attributeSet);
    }

    @Override // android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        LinearProgressIndicatorSpec linearProgressIndicatorSpec = (LinearProgressIndicatorSpec) this.b;
        boolean z2 = true;
        if (!(((LinearProgressIndicatorSpec) this.b).indicatorDirection == 1 || ((ViewCompat.getLayoutDirection(this) == 1 && ((LinearProgressIndicatorSpec) this.b).indicatorDirection == 2) || (ViewCompat.getLayoutDirection(this) == 0 && ((LinearProgressIndicatorSpec) this.b).indicatorDirection == 3)))) {
            z2 = false;
        }
        linearProgressIndicatorSpec.a = z2;
    }

    @Override // android.widget.ProgressBar, android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        int paddingLeft = i - (getPaddingLeft() + getPaddingRight());
        int paddingTop = i2 - (getPaddingTop() + getPaddingBottom());
        IndeterminateDrawable indeterminateDrawable = getIndeterminateDrawable();
        if (indeterminateDrawable != null) {
            indeterminateDrawable.setBounds(0, 0, paddingLeft, paddingTop);
        }
        DeterminateDrawable progressDrawable = getProgressDrawable();
        if (progressDrawable != null) {
            progressDrawable.setBounds(0, 0, paddingLeft, paddingTop);
        }
    }

    private void c() {
        setIndeterminateDrawable(IndeterminateDrawable.createLinearDrawable(getContext(), (LinearProgressIndicatorSpec) this.b));
        setProgressDrawable(DeterminateDrawable.createLinearDrawable(getContext(), (LinearProgressIndicatorSpec) this.b));
    }

    @Override // com.google.android.material.progressindicator.BaseProgressIndicator
    public void setIndicatorColor(@NonNull int... iArr) {
        super.setIndicatorColor(iArr);
        ((LinearProgressIndicatorSpec) this.b).a();
    }

    @Override // com.google.android.material.progressindicator.BaseProgressIndicator
    public void setTrackCornerRadius(int i) {
        super.setTrackCornerRadius(i);
        ((LinearProgressIndicatorSpec) this.b).a();
        invalidate();
    }

    public int getIndeterminateAnimationType() {
        return ((LinearProgressIndicatorSpec) this.b).indeterminateAnimationType;
    }

    public void setIndeterminateAnimationType(int i) {
        if (((LinearProgressIndicatorSpec) this.b).indeterminateAnimationType != i) {
            if (!a() || !isIndeterminate()) {
                ((LinearProgressIndicatorSpec) this.b).indeterminateAnimationType = i;
                ((LinearProgressIndicatorSpec) this.b).a();
                if (i == 0) {
                    getIndeterminateDrawable().a(new g((LinearProgressIndicatorSpec) this.b));
                } else {
                    getIndeterminateDrawable().a(new h(getContext(), (LinearProgressIndicatorSpec) this.b));
                }
                invalidate();
                return;
            }
            throw new IllegalStateException("Cannot change indeterminate animation type while the progress indicator is show in indeterminate mode.");
        }
    }

    public int getIndicatorDirection() {
        return ((LinearProgressIndicatorSpec) this.b).indicatorDirection;
    }

    public void setIndicatorDirection(int i) {
        ((LinearProgressIndicatorSpec) this.b).indicatorDirection = i;
        LinearProgressIndicatorSpec linearProgressIndicatorSpec = (LinearProgressIndicatorSpec) this.b;
        boolean z = true;
        if (!(i == 1 || ((ViewCompat.getLayoutDirection(this) == 1 && ((LinearProgressIndicatorSpec) this.b).indicatorDirection == 2) || (ViewCompat.getLayoutDirection(this) == 0 && i == 3)))) {
            z = false;
        }
        linearProgressIndicatorSpec.a = z;
        invalidate();
    }

    @Override // com.google.android.material.progressindicator.BaseProgressIndicator
    public void setProgressCompat(int i, boolean z) {
        if (this.b == null || ((LinearProgressIndicatorSpec) this.b).indeterminateAnimationType != 0 || !isIndeterminate()) {
            super.setProgressCompat(i, z);
        }
    }
}
