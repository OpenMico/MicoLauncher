package com.google.android.material.progressindicator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Property;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import com.google.android.material.animation.AnimationUtils;
import java.util.ArrayList;
import java.util.List;

/* compiled from: DrawableWithAnimatedVisibilityChange.java */
/* loaded from: classes2.dex */
public abstract class c extends Drawable implements Animatable2Compat {
    private static final Property<c, Float> o = new Property<c, Float>(Float.class, "growFraction") { // from class: com.google.android.material.progressindicator.c.3
        /* renamed from: a */
        public Float get(c cVar) {
            return Float.valueOf(cVar.b());
        }

        /* renamed from: a */
        public void set(c cVar, Float f) {
            cVar.b(f.floatValue());
        }
    };
    final Context a;
    final BaseProgressIndicatorSpec b;
    private ValueAnimator e;
    private ValueAnimator f;
    private boolean g;
    private boolean h;
    private float i;
    private List<Animatable2Compat.AnimationCallback> j;
    private Animatable2Compat.AnimationCallback k;
    private boolean l;
    private float m;
    private int n;
    final Paint d = new Paint();
    AnimatorDurationScaleProvider c = new AnimatorDurationScaleProvider();

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    public c(@NonNull Context context, @NonNull BaseProgressIndicatorSpec baseProgressIndicatorSpec) {
        this.a = context;
        this.b = baseProgressIndicatorSpec;
        setAlpha(255);
    }

    private void a() {
        if (this.e == null) {
            this.e = ObjectAnimator.ofFloat(this, o, 0.0f, 1.0f);
            this.e.setDuration(500L);
            this.e.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
            a(this.e);
        }
        if (this.f == null) {
            this.f = ObjectAnimator.ofFloat(this, o, 1.0f, 0.0f);
            this.f.setDuration(500L);
            this.f.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
            b(this.f);
        }
    }

    public void registerAnimationCallback(@NonNull Animatable2Compat.AnimationCallback animationCallback) {
        if (this.j == null) {
            this.j = new ArrayList();
        }
        if (!this.j.contains(animationCallback)) {
            this.j.add(animationCallback);
        }
    }

    public boolean unregisterAnimationCallback(@NonNull Animatable2Compat.AnimationCallback animationCallback) {
        List<Animatable2Compat.AnimationCallback> list = this.j;
        if (list == null || !list.contains(animationCallback)) {
            return false;
        }
        this.j.remove(animationCallback);
        if (!this.j.isEmpty()) {
            return true;
        }
        this.j = null;
        return true;
    }

    public void clearAnimationCallbacks() {
        this.j.clear();
        this.j = null;
    }

    public void c() {
        Animatable2Compat.AnimationCallback animationCallback = this.k;
        if (animationCallback != null) {
            animationCallback.onAnimationStart(this);
        }
        List<Animatable2Compat.AnimationCallback> list = this.j;
        if (!(list == null || this.l)) {
            for (Animatable2Compat.AnimationCallback animationCallback2 : list) {
                animationCallback2.onAnimationStart(this);
            }
        }
    }

    public void d() {
        Animatable2Compat.AnimationCallback animationCallback = this.k;
        if (animationCallback != null) {
            animationCallback.onAnimationEnd(this);
        }
        List<Animatable2Compat.AnimationCallback> list = this.j;
        if (!(list == null || this.l)) {
            for (Animatable2Compat.AnimationCallback animationCallback2 : list) {
                animationCallback2.onAnimationEnd(this);
            }
        }
    }

    public void start() {
        a(true, true, false);
    }

    public void stop() {
        a(false, true, false);
    }

    public boolean isRunning() {
        return isShowing() || isHiding();
    }

    public boolean isShowing() {
        ValueAnimator valueAnimator = this.e;
        return (valueAnimator != null && valueAnimator.isRunning()) || this.g;
    }

    public boolean isHiding() {
        ValueAnimator valueAnimator = this.f;
        return (valueAnimator != null && valueAnimator.isRunning()) || this.h;
    }

    public boolean hideNow() {
        return setVisible(false, false, false);
    }

    @Override // android.graphics.drawable.Drawable
    public boolean setVisible(boolean z, boolean z2) {
        return setVisible(z, z2, true);
    }

    public boolean setVisible(boolean z, boolean z2, boolean z3) {
        return a(z, z2, z3 && this.c.getSystemAnimatorDurationScale(this.a.getContentResolver()) > 0.0f);
    }

    public boolean a(boolean z, boolean z2, boolean z3) {
        a();
        if (!isVisible() && !z) {
            return false;
        }
        ValueAnimator valueAnimator = z ? this.e : this.f;
        if (!z3) {
            if (valueAnimator.isRunning()) {
                valueAnimator.end();
            } else {
                a(valueAnimator);
            }
            return super.setVisible(z, false);
        } else if (z3 && valueAnimator.isRunning()) {
            return false;
        } else {
            boolean z4 = !z || super.setVisible(z, false);
            if (!(z ? this.b.isShowAnimationEnabled() : this.b.isHideAnimationEnabled())) {
                a(valueAnimator);
                return z4;
            }
            if (z2 || Build.VERSION.SDK_INT < 19 || !valueAnimator.isPaused()) {
                valueAnimator.start();
            } else {
                valueAnimator.resume();
            }
            return z4;
        }
    }

    private void a(@NonNull ValueAnimator... valueAnimatorArr) {
        boolean z = this.l;
        this.l = true;
        for (ValueAnimator valueAnimator : valueAnimatorArr) {
            valueAnimator.end();
        }
        this.l = z;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.n = i;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.n;
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        this.d.setColorFilter(colorFilter);
        invalidateSelf();
    }

    private void a(@NonNull ValueAnimator valueAnimator) {
        ValueAnimator valueAnimator2 = this.e;
        if (valueAnimator2 == null || !valueAnimator2.isRunning()) {
            this.e = valueAnimator;
            valueAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.progressindicator.c.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                    super.onAnimationStart(animator);
                    c.this.c();
                }
            });
            return;
        }
        throw new IllegalArgumentException("Cannot set showAnimator while the current showAnimator is running.");
    }

    private void b(@NonNull ValueAnimator valueAnimator) {
        ValueAnimator valueAnimator2 = this.f;
        if (valueAnimator2 == null || !valueAnimator2.isRunning()) {
            this.f = valueAnimator;
            valueAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.progressindicator.c.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    c.super.setVisible(false, false);
                    c.this.d();
                }
            });
            return;
        }
        throw new IllegalArgumentException("Cannot set hideAnimator while the current hideAnimator is running.");
    }

    float b() {
        if (!this.b.isShowAnimationEnabled() && !this.b.isHideAnimationEnabled()) {
            return 1.0f;
        }
        if (this.h || this.g) {
            return this.i;
        }
        return this.m;
    }

    void b(@FloatRange(from = 0.0d, to = 1.0d) float f) {
        if (this.m != f) {
            this.m = f;
            invalidateSelf();
        }
    }
}
