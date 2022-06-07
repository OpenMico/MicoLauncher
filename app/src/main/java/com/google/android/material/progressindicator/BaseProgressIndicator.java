package com.google.android.material.progressindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import android.widget.ProgressBar;
import androidx.annotation.AttrRes;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.annotation.RestrictTo;
import androidx.annotation.StyleRes;
import androidx.annotation.VisibleForTesting;
import androidx.core.view.ViewCompat;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import com.google.android.material.R;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.progressindicator.BaseProgressIndicatorSpec;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;

/* loaded from: classes2.dex */
public abstract class BaseProgressIndicator<S extends BaseProgressIndicatorSpec> extends ProgressBar {
    public static final int HIDE_INWARD = 2;
    public static final int HIDE_NONE = 0;
    public static final int HIDE_OUTWARD = 1;
    public static final int SHOW_INWARD = 2;
    public static final int SHOW_NONE = 0;
    public static final int SHOW_OUTWARD = 1;
    static final int a = R.style.Widget_MaterialComponents_ProgressIndicator;
    S b;
    private int d;
    private boolean e;
    private final int g;
    private final int h;
    private long i = -1;
    private boolean j = false;
    private int k = 4;
    private final Runnable l = new Runnable() { // from class: com.google.android.material.progressindicator.BaseProgressIndicator.1
        @Override // java.lang.Runnable
        public void run() {
            BaseProgressIndicator.this.e();
        }
    };
    private final Runnable m = new Runnable() { // from class: com.google.android.material.progressindicator.BaseProgressIndicator.2
        @Override // java.lang.Runnable
        public void run() {
            BaseProgressIndicator.this.f();
            BaseProgressIndicator.this.i = -1L;
        }
    };
    private final Animatable2Compat.AnimationCallback n = new Animatable2Compat.AnimationCallback() { // from class: com.google.android.material.progressindicator.BaseProgressIndicator.3
        @Override // androidx.vectordrawable.graphics.drawable.Animatable2Compat.AnimationCallback
        public void onAnimationEnd(Drawable drawable) {
            BaseProgressIndicator.this.setIndeterminate(false);
            BaseProgressIndicator.this.setProgressCompat(0, false);
            BaseProgressIndicator baseProgressIndicator = BaseProgressIndicator.this;
            baseProgressIndicator.setProgressCompat(baseProgressIndicator.d, BaseProgressIndicator.this.e);
        }
    };
    private final Animatable2Compat.AnimationCallback o = new Animatable2Compat.AnimationCallback() { // from class: com.google.android.material.progressindicator.BaseProgressIndicator.4
        @Override // androidx.vectordrawable.graphics.drawable.Animatable2Compat.AnimationCallback
        public void onAnimationEnd(Drawable drawable) {
            super.onAnimationEnd(drawable);
            if (!BaseProgressIndicator.this.j) {
                BaseProgressIndicator baseProgressIndicator = BaseProgressIndicator.this;
                baseProgressIndicator.setVisibility(baseProgressIndicator.k);
            }
        }
    };
    AnimatorDurationScaleProvider c = new AnimatorDurationScaleProvider();
    private boolean f = true;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    /* loaded from: classes2.dex */
    public @interface HideAnimationBehavior {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    /* loaded from: classes2.dex */
    public @interface ShowAnimationBehavior {
    }

    abstract S a(@NonNull Context context, @NonNull AttributeSet attributeSet);

    public BaseProgressIndicator(@NonNull Context context, @Nullable AttributeSet attributeSet, @AttrRes int i, @StyleRes int i2) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, a), attributeSet, i);
        Context context2 = getContext();
        this.b = a(context2, attributeSet);
        TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(context2, attributeSet, R.styleable.BaseProgressIndicator, i, i2, new int[0]);
        this.g = obtainStyledAttributes.getInt(R.styleable.BaseProgressIndicator_showDelay, -1);
        this.h = Math.min(obtainStyledAttributes.getInt(R.styleable.BaseProgressIndicator_minHideDelay, -1), 1000);
        obtainStyledAttributes.recycle();
    }

    private void c() {
        if (!(getProgressDrawable() == null || getIndeterminateDrawable() == null)) {
            getIndeterminateDrawable().a().a(this.n);
        }
        if (getProgressDrawable() != null) {
            getProgressDrawable().registerAnimationCallback(this.o);
        }
        if (getIndeterminateDrawable() != null) {
            getIndeterminateDrawable().registerAnimationCallback(this.o);
        }
    }

    private void d() {
        if (getIndeterminateDrawable() != null) {
            getIndeterminateDrawable().unregisterAnimationCallback(this.o);
            getIndeterminateDrawable().a().e();
        }
        if (getProgressDrawable() != null) {
            getProgressDrawable().unregisterAnimationCallback(this.o);
        }
    }

    public void show() {
        if (this.g > 0) {
            removeCallbacks(this.l);
            postDelayed(this.l, this.g);
            return;
        }
        this.l.run();
    }

    public void e() {
        if (this.h > 0) {
            this.i = SystemClock.uptimeMillis();
        }
        setVisibility(0);
    }

    public void hide() {
        if (getVisibility() != 0) {
            removeCallbacks(this.l);
            return;
        }
        removeCallbacks(this.m);
        long uptimeMillis = SystemClock.uptimeMillis() - this.i;
        if (uptimeMillis >= ((long) this.h)) {
            this.m.run();
        } else {
            postDelayed(this.m, this.h - uptimeMillis);
        }
    }

    public void f() {
        ((c) getCurrentDrawable()).setVisible(false, false, true);
        if (g()) {
            setVisibility(4);
        }
    }

    @Override // android.view.View
    protected void onVisibilityChanged(@NonNull View view, int i) {
        super.onVisibilityChanged(view, i);
        applyNewVisibility(i == 0);
    }

    @Override // android.view.View
    protected void onWindowVisibilityChanged(int i) {
        super.onWindowVisibilityChanged(i);
        applyNewVisibility(false);
    }

    protected void applyNewVisibility(boolean z) {
        if (this.f) {
            ((c) getCurrentDrawable()).setVisible(a(), false, z);
        }
    }

    @Override // android.widget.ProgressBar, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        c();
        if (a()) {
            e();
        }
    }

    @Override // android.widget.ProgressBar, android.view.View
    protected void onDetachedFromWindow() {
        removeCallbacks(this.m);
        removeCallbacks(this.l);
        ((c) getCurrentDrawable()).hideNow();
        d();
        super.onDetachedFromWindow();
    }

    @Override // android.widget.ProgressBar, android.view.View
    protected synchronized void onDraw(@NonNull Canvas canvas) {
        int save = canvas.save();
        if (!(getPaddingLeft() == 0 && getPaddingTop() == 0)) {
            canvas.translate(getPaddingLeft(), getPaddingTop());
        }
        if (!(getPaddingRight() == 0 && getPaddingBottom() == 0)) {
            canvas.clipRect(0, 0, getWidth() - (getPaddingLeft() + getPaddingRight()), getHeight() - (getPaddingTop() + getPaddingBottom()));
        }
        getCurrentDrawable().draw(canvas);
        canvas.restoreToCount(save);
    }

    @Override // android.widget.ProgressBar, android.view.View
    protected synchronized void onMeasure(int i, int i2) {
        int i3;
        int i4;
        super.onMeasure(i, i2);
        d<S> currentDrawingDelegate = getCurrentDrawingDelegate();
        if (currentDrawingDelegate != null) {
            int a2 = currentDrawingDelegate.a();
            int b = currentDrawingDelegate.b();
            if (a2 < 0) {
                i3 = getMeasuredWidth();
            } else {
                i3 = a2 + getPaddingLeft() + getPaddingRight();
            }
            if (b < 0) {
                i4 = getMeasuredHeight();
            } else {
                i4 = b + getPaddingTop() + getPaddingBottom();
            }
            setMeasuredDimension(i3, i4);
        }
    }

    @Override // android.view.View
    public void invalidate() {
        super.invalidate();
        if (getCurrentDrawable() != null) {
            getCurrentDrawable().invalidateSelf();
        }
    }

    @Override // android.widget.ProgressBar
    @Nullable
    public Drawable getCurrentDrawable() {
        return isIndeterminate() ? getIndeterminateDrawable() : getProgressDrawable();
    }

    @Nullable
    private d<S> getCurrentDrawingDelegate() {
        if (isIndeterminate()) {
            if (getIndeterminateDrawable() == null) {
                return null;
            }
            return getIndeterminateDrawable().c();
        } else if (getProgressDrawable() == null) {
            return null;
        } else {
            return getProgressDrawable().a();
        }
    }

    @Override // android.widget.ProgressBar
    public void setProgressDrawable(@Nullable Drawable drawable) {
        if (drawable == null) {
            super.setProgressDrawable(null);
        } else if (drawable instanceof DeterminateDrawable) {
            DeterminateDrawable determinateDrawable = (DeterminateDrawable) drawable;
            determinateDrawable.hideNow();
            super.setProgressDrawable(determinateDrawable);
            determinateDrawable.a(getProgress() / getMax());
        } else {
            throw new IllegalArgumentException("Cannot set framework drawable as progress drawable.");
        }
    }

    @Override // android.widget.ProgressBar
    public void setIndeterminateDrawable(@Nullable Drawable drawable) {
        if (drawable == null) {
            super.setIndeterminateDrawable(null);
        } else if (drawable instanceof IndeterminateDrawable) {
            ((c) drawable).hideNow();
            super.setIndeterminateDrawable(drawable);
        } else {
            throw new IllegalArgumentException("Cannot set framework drawable as indeterminate drawable.");
        }
    }

    @Override // android.widget.ProgressBar
    @Nullable
    public DeterminateDrawable<S> getProgressDrawable() {
        return (DeterminateDrawable) super.getProgressDrawable();
    }

    @Override // android.widget.ProgressBar
    @Nullable
    public IndeterminateDrawable<S> getIndeterminateDrawable() {
        return (IndeterminateDrawable) super.getIndeterminateDrawable();
    }

    boolean a() {
        return ViewCompat.isAttachedToWindow(this) && getWindowVisibility() == 0 && b();
    }

    boolean b() {
        View view = this;
        while (view.getVisibility() == 0) {
            ViewParent parent = view.getParent();
            if (parent == null) {
                return getWindowVisibility() == 0;
            }
            if (!(parent instanceof View)) {
                return true;
            }
            view = (View) parent;
        }
        return false;
    }

    private boolean g() {
        return (getProgressDrawable() == null || !getProgressDrawable().isVisible()) && (getIndeterminateDrawable() == null || !getIndeterminateDrawable().isVisible());
    }

    @Override // android.widget.ProgressBar
    public synchronized void setIndeterminate(boolean z) {
        if (z != isIndeterminate()) {
            if (a() && z) {
                throw new IllegalStateException("Cannot switch to indeterminate mode while the progress indicator is visible.");
            }
            c cVar = (c) getCurrentDrawable();
            if (cVar != null) {
                cVar.hideNow();
            }
            super.setIndeterminate(z);
            c cVar2 = (c) getCurrentDrawable();
            if (cVar2 != null) {
                cVar2.setVisible(a(), false, false);
            }
            this.j = false;
        }
    }

    @Px
    public int getTrackThickness() {
        return this.b.trackThickness;
    }

    public void setTrackThickness(@Px int i) {
        if (this.b.trackThickness != i) {
            this.b.trackThickness = i;
            requestLayout();
        }
    }

    @NonNull
    public int[] getIndicatorColor() {
        return this.b.indicatorColors;
    }

    public void setIndicatorColor(@ColorInt int... iArr) {
        if (iArr.length == 0) {
            iArr = new int[]{MaterialColors.getColor(getContext(), R.attr.colorPrimary, -1)};
        }
        if (!Arrays.equals(getIndicatorColor(), iArr)) {
            this.b.indicatorColors = iArr;
            getIndeterminateDrawable().a().d();
            invalidate();
        }
    }

    @ColorInt
    public int getTrackColor() {
        return this.b.trackColor;
    }

    public void setTrackColor(@ColorInt int i) {
        if (this.b.trackColor != i) {
            this.b.trackColor = i;
            invalidate();
        }
    }

    @Px
    public int getTrackCornerRadius() {
        return this.b.trackCornerRadius;
    }

    public void setTrackCornerRadius(@Px int i) {
        if (this.b.trackCornerRadius != i) {
            S s = this.b;
            s.trackCornerRadius = Math.min(i, s.trackThickness / 2);
        }
    }

    public int getShowAnimationBehavior() {
        return this.b.showAnimationBehavior;
    }

    public void setShowAnimationBehavior(int i) {
        this.b.showAnimationBehavior = i;
        invalidate();
    }

    public int getHideAnimationBehavior() {
        return this.b.hideAnimationBehavior;
    }

    public void setHideAnimationBehavior(int i) {
        this.b.hideAnimationBehavior = i;
        invalidate();
    }

    @Override // android.widget.ProgressBar
    public synchronized void setProgress(int i) {
        if (!isIndeterminate()) {
            setProgressCompat(i, false);
        }
    }

    public void setProgressCompat(int i, boolean z) {
        if (!isIndeterminate()) {
            super.setProgress(i);
            if (getProgressDrawable() != null && !z) {
                getProgressDrawable().jumpToCurrentState();
            }
        } else if (getProgressDrawable() != null) {
            this.d = i;
            this.e = z;
            this.j = true;
            if (!getIndeterminateDrawable().isVisible() || this.c.getSystemAnimatorDurationScale(getContext().getContentResolver()) == 0.0f) {
                this.n.onAnimationEnd(getIndeterminateDrawable());
            } else {
                getIndeterminateDrawable().a().c();
            }
        }
    }

    public void setVisibilityAfterHide(int i) {
        if (i == 0 || i == 4 || i == 8) {
            this.k = i;
            return;
        }
        throw new IllegalArgumentException("The component's visibility must be one of VISIBLE, INVISIBLE, and GONE defined in View.");
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    @VisibleForTesting
    public void setAnimatorDurationScaleProvider(@NonNull AnimatorDurationScaleProvider animatorDurationScaleProvider) {
        this.c = animatorDurationScaleProvider;
        if (getProgressDrawable() != null) {
            getProgressDrawable().c = animatorDurationScaleProvider;
        }
        if (getIndeterminateDrawable() != null) {
            getIndeterminateDrawable().c = animatorDurationScaleProvider;
        }
    }
}
