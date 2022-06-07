package com.google.android.material.progressindicator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.util.Property;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import com.google.android.material.animation.ArgbEvaluatorCompat;
import com.google.android.material.color.MaterialColors;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: CircularIndeterminateAnimatorDelegate.java */
/* loaded from: classes2.dex */
public final class b extends e<ObjectAnimator> {
    private static final int[] e = {0, 1350, 2700, 4050};
    private static final int[] f = {667, 2017, 3367, 4717};
    private static final int[] g = {1000, 2350, 3700, 5050};
    private static final Property<b, Float> o = new Property<b, Float>(Float.class, "animationFraction") { // from class: com.google.android.material.progressindicator.b.3
        /* renamed from: a */
        public Float get(b bVar) {
            return Float.valueOf(bVar.h());
        }

        /* renamed from: a */
        public void set(b bVar, Float f2) {
            bVar.a(f2.floatValue());
        }
    };
    private static final Property<b, Float> p = new Property<b, Float>(Float.class, "completeEndFraction") { // from class: com.google.android.material.progressindicator.b.4
        /* renamed from: a */
        public Float get(b bVar) {
            return Float.valueOf(bVar.i());
        }

        /* renamed from: a */
        public void set(b bVar, Float f2) {
            bVar.b(f2.floatValue());
        }
    };
    private ObjectAnimator h;
    private ObjectAnimator i;
    private final BaseProgressIndicatorSpec k;
    private float m;
    private float n;
    private int l = 0;
    Animatable2Compat.AnimationCallback a = null;
    private final FastOutSlowInInterpolator j = new FastOutSlowInInterpolator();

    public b(@NonNull CircularProgressIndicatorSpec circularProgressIndicatorSpec) {
        super(1);
        this.k = circularProgressIndicatorSpec;
    }

    @Override // com.google.android.material.progressindicator.e
    void a() {
        g();
        f();
        this.h.start();
    }

    private void g() {
        if (this.h == null) {
            this.h = ObjectAnimator.ofFloat(this, o, 0.0f, 1.0f);
            this.h.setDuration(5400L);
            this.h.setInterpolator(null);
            this.h.setRepeatCount(-1);
            this.h.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.progressindicator.b.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationRepeat(Animator animator) {
                    super.onAnimationRepeat(animator);
                    b bVar = b.this;
                    bVar.l = (bVar.l + 4) % b.this.k.indicatorColors.length;
                }
            });
        }
        if (this.i == null) {
            this.i = ObjectAnimator.ofFloat(this, p, 0.0f, 1.0f);
            this.i.setDuration(333L);
            this.i.setInterpolator(this.j);
            this.i.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.progressindicator.b.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    b.this.b();
                    b.this.a.onAnimationEnd(b.this.b);
                }
            });
        }
    }

    @Override // com.google.android.material.progressindicator.e
    void b() {
        ObjectAnimator objectAnimator = this.h;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
    }

    @Override // com.google.android.material.progressindicator.e
    void c() {
        if (!this.i.isRunning()) {
            if (this.b.isVisible()) {
                this.i.start();
            } else {
                b();
            }
        }
    }

    @Override // com.google.android.material.progressindicator.e
    public void d() {
        f();
    }

    @Override // com.google.android.material.progressindicator.e
    public void a(@NonNull Animatable2Compat.AnimationCallback animationCallback) {
        this.a = animationCallback;
    }

    @Override // com.google.android.material.progressindicator.e
    public void e() {
        this.a = null;
    }

    private void a(int i) {
        this.c[0] = (this.m * 1520.0f) - 20.0f;
        this.c[1] = this.m * 1520.0f;
        for (int i2 = 0; i2 < 4; i2++) {
            float a = a(i, e[i2], 667);
            float[] fArr = this.c;
            fArr[1] = fArr[1] + (this.j.getInterpolation(a) * 250.0f);
            float a2 = a(i, f[i2], 667);
            float[] fArr2 = this.c;
            fArr2[0] = fArr2[0] + (this.j.getInterpolation(a2) * 250.0f);
        }
        float[] fArr3 = this.c;
        fArr3[0] = fArr3[0] + ((this.c[1] - this.c[0]) * this.n);
        float[] fArr4 = this.c;
        fArr4[0] = fArr4[0] / 360.0f;
        float[] fArr5 = this.c;
        fArr5[1] = fArr5[1] / 360.0f;
    }

    private void b(int i) {
        for (int i2 = 0; i2 < 4; i2++) {
            float a = a(i, g[i2], 333);
            if (a >= 0.0f && a <= 1.0f) {
                int length = (i2 + this.l) % this.k.indicatorColors.length;
                int length2 = (length + 1) % this.k.indicatorColors.length;
                this.d[0] = ArgbEvaluatorCompat.getInstance().evaluate(this.j.getInterpolation(a), Integer.valueOf(MaterialColors.compositeARGBWithAlpha(this.k.indicatorColors[length], this.b.getAlpha())), Integer.valueOf(MaterialColors.compositeARGBWithAlpha(this.k.indicatorColors[length2], this.b.getAlpha()))).intValue();
                return;
            }
        }
    }

    @VisibleForTesting
    void f() {
        this.l = 0;
        this.d[0] = MaterialColors.compositeARGBWithAlpha(this.k.indicatorColors[0], this.b.getAlpha());
        this.n = 0.0f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float h() {
        return this.m;
    }

    @VisibleForTesting
    void a(float f2) {
        this.m = f2;
        int i = (int) (this.m * 5400.0f);
        a(i);
        b(i);
        this.b.invalidateSelf();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float i() {
        return this.n;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(float f2) {
        this.n = f2;
    }
}
