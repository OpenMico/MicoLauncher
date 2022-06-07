package com.google.android.material.progressindicator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.util.Property;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import com.google.android.material.color.MaterialColors;
import java.util.Arrays;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: LinearIndeterminateContiguousAnimatorDelegate.java */
/* loaded from: classes2.dex */
public final class g extends e<ObjectAnimator> {
    private static final Property<g, Float> j = new Property<g, Float>(Float.class, "animationFraction") { // from class: com.google.android.material.progressindicator.g.2
        /* renamed from: a */
        public Float get(g gVar) {
            return Float.valueOf(gVar.i());
        }

        /* renamed from: a */
        public void set(g gVar, Float f) {
            gVar.a(f.floatValue());
        }
    };
    private ObjectAnimator a;
    private final BaseProgressIndicatorSpec f;
    private boolean h;
    private float i;
    private int g = 1;
    private FastOutSlowInInterpolator e = new FastOutSlowInInterpolator();

    @Override // com.google.android.material.progressindicator.e
    public void a(@Nullable Animatable2Compat.AnimationCallback animationCallback) {
    }

    @Override // com.google.android.material.progressindicator.e
    public void c() {
    }

    @Override // com.google.android.material.progressindicator.e
    public void e() {
    }

    public g(@NonNull LinearProgressIndicatorSpec linearProgressIndicatorSpec) {
        super(3);
        this.f = linearProgressIndicatorSpec;
    }

    @Override // com.google.android.material.progressindicator.e
    public void a() {
        g();
        f();
        this.a.start();
    }

    private void g() {
        if (this.a == null) {
            this.a = ObjectAnimator.ofFloat(this, j, 0.0f, 1.0f);
            this.a.setDuration(333L);
            this.a.setInterpolator(null);
            this.a.setRepeatCount(-1);
            this.a.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.progressindicator.g.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationRepeat(Animator animator) {
                    super.onAnimationRepeat(animator);
                    g gVar = g.this;
                    gVar.g = (gVar.g + 1) % g.this.f.indicatorColors.length;
                    g.this.h = true;
                }
            });
        }
    }

    @Override // com.google.android.material.progressindicator.e
    public void b() {
        ObjectAnimator objectAnimator = this.a;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
    }

    @Override // com.google.android.material.progressindicator.e
    public void d() {
        f();
    }

    private void a(int i) {
        this.c[0] = 0.0f;
        float a = a(i, 0, 667);
        float[] fArr = this.c;
        float[] fArr2 = this.c;
        float interpolation = this.e.getInterpolation(a);
        fArr2[2] = interpolation;
        fArr[1] = interpolation;
        float[] fArr3 = this.c;
        float[] fArr4 = this.c;
        float interpolation2 = this.e.getInterpolation(a + 0.49925038f);
        fArr4[4] = interpolation2;
        fArr3[3] = interpolation2;
        this.c[5] = 1.0f;
    }

    private void h() {
        if (this.h && this.c[3] < 1.0f) {
            this.d[2] = this.d[1];
            this.d[1] = this.d[0];
            this.d[0] = MaterialColors.compositeARGBWithAlpha(this.f.indicatorColors[this.g], this.b.getAlpha());
            this.h = false;
        }
    }

    @VisibleForTesting
    void f() {
        this.h = true;
        this.g = 1;
        Arrays.fill(this.d, MaterialColors.compositeARGBWithAlpha(this.f.indicatorColors[0], this.b.getAlpha()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float i() {
        return this.i;
    }

    @VisibleForTesting
    void a(float f) {
        this.i = f;
        a((int) (this.i * 333.0f));
        h();
        this.b.invalidateSelf();
    }
}
