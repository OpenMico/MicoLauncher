package com.google.android.material.progressindicator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Property;
import android.view.animation.Interpolator;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import androidx.vectordrawable.graphics.drawable.AnimationUtilsCompat;
import com.google.android.material.R;
import com.google.android.material.color.MaterialColors;
import com.xiaomi.mico.base.ui.RxViewHelp;
import java.util.Arrays;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: LinearIndeterminateDisjointAnimatorDelegate.java */
/* loaded from: classes2.dex */
public final class h extends e<ObjectAnimator> {
    private static final int[] e = {533, 567, 850, 750};
    private static final int[] f = {1267, 1000, 333, 0};
    private static final Property<h, Float> n = new Property<h, Float>(Float.class, "animationFraction") { // from class: com.google.android.material.progressindicator.h.2
        /* renamed from: a */
        public Float get(h hVar) {
            return Float.valueOf(hVar.i());
        }

        /* renamed from: a */
        public void set(h hVar, Float f2) {
            hVar.a(f2.floatValue());
        }
    };
    private ObjectAnimator g;
    private final Interpolator[] h;
    private final BaseProgressIndicatorSpec i;
    private boolean k;
    private float l;
    private boolean m;
    private int j = 0;
    Animatable2Compat.AnimationCallback a = null;

    public h(@NonNull Context context, @NonNull LinearProgressIndicatorSpec linearProgressIndicatorSpec) {
        super(2);
        this.i = linearProgressIndicatorSpec;
        this.h = new Interpolator[]{AnimationUtilsCompat.loadInterpolator(context, R.animator.linear_indeterminate_line1_head_interpolator), AnimationUtilsCompat.loadInterpolator(context, R.animator.linear_indeterminate_line1_tail_interpolator), AnimationUtilsCompat.loadInterpolator(context, R.animator.linear_indeterminate_line2_head_interpolator), AnimationUtilsCompat.loadInterpolator(context, R.animator.linear_indeterminate_line2_tail_interpolator)};
    }

    @Override // com.google.android.material.progressindicator.e
    public void a() {
        g();
        f();
        this.g.start();
    }

    private void g() {
        if (this.g == null) {
            this.g = ObjectAnimator.ofFloat(this, n, 0.0f, 1.0f);
            this.g.setDuration(RxViewHelp.CLICK_DURATION);
            this.g.setInterpolator(null);
            this.g.setRepeatCount(-1);
            this.g.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.progressindicator.h.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationRepeat(Animator animator) {
                    super.onAnimationRepeat(animator);
                    h hVar = h.this;
                    hVar.j = (hVar.j + 1) % h.this.i.indicatorColors.length;
                    h.this.k = true;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    if (h.this.m) {
                        h.this.g.setRepeatCount(-1);
                        h.this.a.onAnimationEnd(h.this.b);
                        h.this.m = false;
                    }
                }
            });
        }
    }

    @Override // com.google.android.material.progressindicator.e
    public void b() {
        ObjectAnimator objectAnimator = this.g;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
    }

    @Override // com.google.android.material.progressindicator.e
    public void c() {
        if (this.b.isVisible()) {
            this.m = true;
            this.g.setRepeatCount(0);
            return;
        }
        b();
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
        for (int i2 = 0; i2 < 4; i2++) {
            this.c[i2] = Math.max(0.0f, Math.min(1.0f, this.h[i2].getInterpolation(a(i, f[i2], e[i2]))));
        }
    }

    private void h() {
        if (this.k) {
            Arrays.fill(this.d, MaterialColors.compositeARGBWithAlpha(this.i.indicatorColors[this.j], this.b.getAlpha()));
            this.k = false;
        }
    }

    @VisibleForTesting
    void f() {
        this.j = 0;
        int compositeARGBWithAlpha = MaterialColors.compositeARGBWithAlpha(this.i.indicatorColors[0], this.b.getAlpha());
        this.d[0] = compositeARGBWithAlpha;
        this.d[1] = compositeARGBWithAlpha;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float i() {
        return this.l;
    }

    @VisibleForTesting
    void a(float f2) {
        this.l = f2;
        a((int) (this.l * 1800.0f));
        h();
        this.b.invalidateSelf();
    }
}
