package com.google.android.material.progressindicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.progressindicator.BaseProgressIndicatorSpec;

/* loaded from: classes2.dex */
public final class DeterminateDrawable<S extends BaseProgressIndicatorSpec> extends c {
    private static final FloatPropertyCompat<DeterminateDrawable> j = new FloatPropertyCompat<DeterminateDrawable>("indicatorLevel") { // from class: com.google.android.material.progressindicator.DeterminateDrawable.1
        /* renamed from: a */
        public float getValue(DeterminateDrawable determinateDrawable) {
            return determinateDrawable.c() * 10000.0f;
        }

        /* renamed from: a */
        public void setValue(DeterminateDrawable determinateDrawable, float f) {
            determinateDrawable.c(f / 10000.0f);
        }
    };
    private d<S> e;
    private float h;
    private boolean i = false;
    private final SpringForce f = new SpringForce();
    private final SpringAnimation g = new SpringAnimation(this, j);

    @Override // com.google.android.material.progressindicator.c, androidx.vectordrawable.graphics.drawable.Animatable2Compat
    public /* bridge */ /* synthetic */ void clearAnimationCallbacks() {
        super.clearAnimationCallbacks();
    }

    @Override // com.google.android.material.progressindicator.c, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ int getAlpha() {
        return super.getAlpha();
    }

    @Override // com.google.android.material.progressindicator.c, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ int getOpacity() {
        return super.getOpacity();
    }

    @Override // com.google.android.material.progressindicator.c
    public /* bridge */ /* synthetic */ boolean hideNow() {
        return super.hideNow();
    }

    @Override // com.google.android.material.progressindicator.c
    public /* bridge */ /* synthetic */ boolean isHiding() {
        return super.isHiding();
    }

    @Override // com.google.android.material.progressindicator.c, android.graphics.drawable.Animatable
    public /* bridge */ /* synthetic */ boolean isRunning() {
        return super.isRunning();
    }

    @Override // com.google.android.material.progressindicator.c
    public /* bridge */ /* synthetic */ boolean isShowing() {
        return super.isShowing();
    }

    @Override // com.google.android.material.progressindicator.c, androidx.vectordrawable.graphics.drawable.Animatable2Compat
    public /* bridge */ /* synthetic */ void registerAnimationCallback(@NonNull Animatable2Compat.AnimationCallback animationCallback) {
        super.registerAnimationCallback(animationCallback);
    }

    @Override // com.google.android.material.progressindicator.c, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ void setAlpha(int i) {
        super.setAlpha(i);
    }

    @Override // com.google.android.material.progressindicator.c, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ void setColorFilter(@Nullable ColorFilter colorFilter) {
        super.setColorFilter(colorFilter);
    }

    @Override // com.google.android.material.progressindicator.c, android.graphics.drawable.Drawable
    public /* bridge */ /* synthetic */ boolean setVisible(boolean z, boolean z2) {
        return super.setVisible(z, z2);
    }

    @Override // com.google.android.material.progressindicator.c
    public /* bridge */ /* synthetic */ boolean setVisible(boolean z, boolean z2, boolean z3) {
        return super.setVisible(z, z2, z3);
    }

    @Override // com.google.android.material.progressindicator.c, android.graphics.drawable.Animatable
    public /* bridge */ /* synthetic */ void start() {
        super.start();
    }

    @Override // com.google.android.material.progressindicator.c, android.graphics.drawable.Animatable
    public /* bridge */ /* synthetic */ void stop() {
        super.stop();
    }

    @Override // com.google.android.material.progressindicator.c, androidx.vectordrawable.graphics.drawable.Animatable2Compat
    public /* bridge */ /* synthetic */ boolean unregisterAnimationCallback(@NonNull Animatable2Compat.AnimationCallback animationCallback) {
        return super.unregisterAnimationCallback(animationCallback);
    }

    DeterminateDrawable(@NonNull Context context, @NonNull BaseProgressIndicatorSpec baseProgressIndicatorSpec, @NonNull d<S> dVar) {
        super(context, baseProgressIndicatorSpec);
        a(dVar);
        this.f.setDampingRatio(1.0f);
        this.f.setStiffness(50.0f);
        this.g.setSpring(this.f);
        b(1.0f);
    }

    @NonNull
    public static DeterminateDrawable<LinearProgressIndicatorSpec> createLinearDrawable(@NonNull Context context, @NonNull LinearProgressIndicatorSpec linearProgressIndicatorSpec) {
        return new DeterminateDrawable<>(context, linearProgressIndicatorSpec, new f(linearProgressIndicatorSpec));
    }

    @NonNull
    public static DeterminateDrawable<CircularProgressIndicatorSpec> createCircularDrawable(@NonNull Context context, @NonNull CircularProgressIndicatorSpec circularProgressIndicatorSpec) {
        return new DeterminateDrawable<>(context, circularProgressIndicatorSpec, new a(circularProgressIndicatorSpec));
    }

    @Override // com.google.android.material.progressindicator.c
    public boolean a(boolean z, boolean z2, boolean z3) {
        boolean a = super.a(z, z2, z3);
        float systemAnimatorDurationScale = this.c.getSystemAnimatorDurationScale(this.a.getContentResolver());
        if (systemAnimatorDurationScale == 0.0f) {
            this.i = true;
        } else {
            this.i = false;
            this.f.setStiffness(50.0f / systemAnimatorDurationScale);
        }
        return a;
    }

    @Override // android.graphics.drawable.Drawable
    public void jumpToCurrentState() {
        this.g.cancel();
        c(getLevel() / 10000.0f);
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onLevelChange(int i) {
        if (this.i) {
            this.g.cancel();
            c(i / 10000.0f);
            return true;
        }
        this.g.setStartValue(c() * 10000.0f);
        this.g.animateToFinalPosition(i);
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.e.a();
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.e.b();
    }

    public void a(float f) {
        setLevel((int) (f * 10000.0f));
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(@NonNull Canvas canvas) {
        Rect rect = new Rect();
        if (!getBounds().isEmpty() && isVisible() && canvas.getClipBounds(rect)) {
            canvas.save();
            this.e.b(canvas, b());
            this.e.a(canvas, this.d);
            this.e.a(canvas, this.d, 0.0f, c(), MaterialColors.compositeARGBWithAlpha(this.b.indicatorColors[0], getAlpha()));
            canvas.restore();
        }
    }

    public float c() {
        return this.h;
    }

    public void c(float f) {
        this.h = f;
        invalidateSelf();
    }

    @NonNull
    public d<S> a() {
        return this.e;
    }

    void a(@NonNull d<S> dVar) {
        this.e = dVar;
        dVar.a(this);
    }
}
