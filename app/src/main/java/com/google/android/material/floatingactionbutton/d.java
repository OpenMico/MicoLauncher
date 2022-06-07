package com.google.android.material.floatingactionbutton;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.FloatEvaluator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.util.Preconditions;
import androidx.core.view.ViewCompat;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.animation.AnimatorSetCompat;
import com.google.android.material.animation.ImageMatrixProperty;
import com.google.android.material.animation.MatrixEvaluator;
import com.google.android.material.animation.MotionSpec;
import com.google.android.material.internal.StateListAnimator;
import com.google.android.material.ripple.RippleDrawableCompat;
import com.google.android.material.ripple.RippleUtils;
import com.google.android.material.shadow.ShadowViewDelegate;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.Shapeable;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: FloatingActionButtonImpl.java */
/* loaded from: classes2.dex */
public class d {
    static final TimeInterpolator a = AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR;
    static final int[] m = {16842919, 16842910};
    static final int[] n = {16843623, 16842908, 16842910};
    static final int[] o = {16842908, 16842910};
    static final int[] p = {16843623, 16842910};
    static final int[] q = {16842910};
    static final int[] r = new int[0];
    private float A;
    private int C;
    private ArrayList<Animator.AnimatorListener> E;
    private ArrayList<Animator.AnimatorListener> F;
    private ArrayList<AbstractC0076d> G;
    @Nullable
    private ViewTreeObserver.OnPreDrawListener L;
    @Nullable
    ShapeAppearanceModel b;
    @Nullable
    MaterialShapeDrawable c;
    @Nullable
    Drawable d;
    @Nullable
    c e;
    @Nullable
    Drawable f;
    boolean g;
    float i;
    float j;
    float k;
    int l;
    final FloatingActionButton s;
    final ShadowViewDelegate t;
    @Nullable
    private MotionSpec v;
    @Nullable
    private MotionSpec w;
    @Nullable
    private Animator x;
    @Nullable
    private MotionSpec y;
    @Nullable
    private MotionSpec z;
    boolean h = true;
    private float B = 1.0f;
    private int D = 0;
    private final Rect H = new Rect();
    private final RectF I = new RectF();
    private final RectF J = new RectF();
    private final Matrix K = new Matrix();
    @NonNull
    private final StateListAnimator u = new StateListAnimator();

    /* compiled from: FloatingActionButtonImpl.java */
    /* renamed from: com.google.android.material.floatingactionbutton.d$d  reason: collision with other inner class name */
    /* loaded from: classes2.dex */
    interface AbstractC0076d {
        void a();

        void b();
    }

    /* compiled from: FloatingActionButtonImpl.java */
    /* loaded from: classes2.dex */
    interface e {
        void a();

        void b();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void n() {
    }

    boolean p() {
        return true;
    }

    boolean s() {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public d(FloatingActionButton floatingActionButton, ShadowViewDelegate shadowViewDelegate) {
        this.s = floatingActionButton;
        this.t = shadowViewDelegate;
        this.u.addState(m, a((g) new c()));
        this.u.addState(n, a((g) new b()));
        this.u.addState(o, a((g) new b()));
        this.u.addState(p, a((g) new b()));
        this.u.addState(q, a((g) new f()));
        this.u.addState(r, a((g) new a()));
        this.A = this.s.getRotation();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(ColorStateList colorStateList, @Nullable PorterDuff.Mode mode, ColorStateList colorStateList2, int i) {
        this.c = u();
        this.c.setTintList(colorStateList);
        if (mode != null) {
            this.c.setTintMode(mode);
        }
        this.c.setShadowColor(-12303292);
        this.c.initializeElevationOverlay(this.s.getContext());
        RippleDrawableCompat rippleDrawableCompat = new RippleDrawableCompat(this.c.getShapeAppearanceModel());
        rippleDrawableCompat.setTintList(RippleUtils.sanitizeRippleDrawableColor(colorStateList2));
        this.d = rippleDrawableCompat;
        this.f = new LayerDrawable(new Drawable[]{(Drawable) Preconditions.checkNotNull(this.c), rippleDrawableCompat});
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(@Nullable ColorStateList colorStateList) {
        MaterialShapeDrawable materialShapeDrawable = this.c;
        if (materialShapeDrawable != null) {
            materialShapeDrawable.setTintList(colorStateList);
        }
        c cVar = this.e;
        if (cVar != null) {
            cVar.a(colorStateList);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(@Nullable PorterDuff.Mode mode) {
        MaterialShapeDrawable materialShapeDrawable = this.c;
        if (materialShapeDrawable != null) {
            materialShapeDrawable.setTintMode(mode);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(int i) {
        this.l = i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b(@Nullable ColorStateList colorStateList) {
        Drawable drawable = this.d;
        if (drawable != null) {
            DrawableCompat.setTintList(drawable, RippleUtils.sanitizeRippleDrawableColor(colorStateList));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void a(float f2) {
        if (this.i != f2) {
            this.i = f2;
            a(this.i, this.j, this.k);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public float a() {
        return this.i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public float b() {
        return this.j;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public float c() {
        return this.k;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void b(float f2) {
        if (this.j != f2) {
            this.j = f2;
            a(this.i, this.j, this.k);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void c(float f2) {
        if (this.k != f2) {
            this.k = f2;
            a(this.i, this.j, this.k);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void b(int i) {
        if (this.C != i) {
            this.C = i;
            d();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void d() {
        d(this.B);
    }

    final void d(float f2) {
        this.B = f2;
        Matrix matrix = this.K;
        a(f2, matrix);
        this.s.setImageMatrix(matrix);
    }

    private void a(float f2, @NonNull Matrix matrix) {
        matrix.reset();
        Drawable drawable = this.s.getDrawable();
        if (drawable != null && this.C != 0) {
            RectF rectF = this.I;
            RectF rectF2 = this.J;
            rectF.set(0.0f, 0.0f, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            int i = this.C;
            rectF2.set(0.0f, 0.0f, i, i);
            matrix.setRectToRect(rectF, rectF2, Matrix.ScaleToFit.CENTER);
            int i2 = this.C;
            matrix.postScale(f2, f2, i2 / 2.0f, i2 / 2.0f);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void a(@NonNull ShapeAppearanceModel shapeAppearanceModel) {
        this.b = shapeAppearanceModel;
        MaterialShapeDrawable materialShapeDrawable = this.c;
        if (materialShapeDrawable != null) {
            materialShapeDrawable.setShapeAppearanceModel(shapeAppearanceModel);
        }
        Drawable drawable = this.d;
        if (drawable instanceof Shapeable) {
            ((Shapeable) drawable).setShapeAppearanceModel(shapeAppearanceModel);
        }
        c cVar = this.e;
        if (cVar != null) {
            cVar.a(shapeAppearanceModel);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public final ShapeAppearanceModel e() {
        return this.b;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public final MotionSpec f() {
        return this.y;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void a(@Nullable MotionSpec motionSpec) {
        this.y = motionSpec;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public final MotionSpec g() {
        return this.z;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void b(@Nullable MotionSpec motionSpec) {
        this.z = motionSpec;
    }

    final boolean h() {
        return !this.g || this.s.getSizeDimension() >= this.l;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean i() {
        return this.g;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(boolean z) {
        this.g = z;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b(boolean z) {
        this.h = z;
        o();
    }

    void a(float f2, float f3, float f4) {
        o();
        e(f2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void e(float f2) {
        MaterialShapeDrawable materialShapeDrawable = this.c;
        if (materialShapeDrawable != null) {
            materialShapeDrawable.setElevation(f2);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(int[] iArr) {
        this.u.setState(iArr);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void j() {
        this.u.jumpToCurrentState();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(@NonNull Animator.AnimatorListener animatorListener) {
        if (this.E == null) {
            this.E = new ArrayList<>();
        }
        this.E.add(animatorListener);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b(@NonNull Animator.AnimatorListener animatorListener) {
        ArrayList<Animator.AnimatorListener> arrayList = this.E;
        if (arrayList != null) {
            arrayList.remove(animatorListener);
        }
    }

    public void c(@NonNull Animator.AnimatorListener animatorListener) {
        if (this.F == null) {
            this.F = new ArrayList<>();
        }
        this.F.add(animatorListener);
    }

    public void d(@NonNull Animator.AnimatorListener animatorListener) {
        ArrayList<Animator.AnimatorListener> arrayList = this.F;
        if (arrayList != null) {
            arrayList.remove(animatorListener);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(@Nullable final e eVar, final boolean z) {
        if (!w()) {
            Animator animator = this.x;
            if (animator != null) {
                animator.cancel();
            }
            if (B()) {
                MotionSpec motionSpec = this.z;
                if (motionSpec == null) {
                    motionSpec = z();
                }
                AnimatorSet a2 = a(motionSpec, 0.0f, 0.0f, 0.0f);
                a2.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.floatingactionbutton.d.1
                    private boolean d;

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationStart(Animator animator2) {
                        d.this.s.internalSetVisibility(0, z);
                        d.this.D = 1;
                        d.this.x = animator2;
                        this.d = false;
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationCancel(Animator animator2) {
                        this.d = true;
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator2) {
                        d.this.D = 0;
                        d.this.x = null;
                        if (!this.d) {
                            d.this.s.internalSetVisibility(z ? 8 : 4, z);
                            e eVar2 = eVar;
                            if (eVar2 != null) {
                                eVar2.b();
                            }
                        }
                    }
                });
                ArrayList<Animator.AnimatorListener> arrayList = this.F;
                if (arrayList != null) {
                    Iterator<Animator.AnimatorListener> it = arrayList.iterator();
                    while (it.hasNext()) {
                        a2.addListener(it.next());
                    }
                }
                a2.start();
                return;
            }
            this.s.internalSetVisibility(z ? 8 : 4, z);
            if (eVar != null) {
                eVar.b();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b(@Nullable final e eVar, final boolean z) {
        if (!v()) {
            Animator animator = this.x;
            if (animator != null) {
                animator.cancel();
            }
            if (B()) {
                if (this.s.getVisibility() != 0) {
                    this.s.setAlpha(0.0f);
                    this.s.setScaleY(0.0f);
                    this.s.setScaleX(0.0f);
                    d(0.0f);
                }
                MotionSpec motionSpec = this.y;
                if (motionSpec == null) {
                    motionSpec = y();
                }
                AnimatorSet a2 = a(motionSpec, 1.0f, 1.0f, 1.0f);
                a2.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.floatingactionbutton.d.2
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationStart(Animator animator2) {
                        d.this.s.internalSetVisibility(0, z);
                        d.this.D = 2;
                        d.this.x = animator2;
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator2) {
                        d.this.D = 0;
                        d.this.x = null;
                        e eVar2 = eVar;
                        if (eVar2 != null) {
                            eVar2.a();
                        }
                    }
                });
                ArrayList<Animator.AnimatorListener> arrayList = this.E;
                if (arrayList != null) {
                    Iterator<Animator.AnimatorListener> it = arrayList.iterator();
                    while (it.hasNext()) {
                        a2.addListener(it.next());
                    }
                }
                a2.start();
                return;
            }
            this.s.internalSetVisibility(0, z);
            this.s.setAlpha(1.0f);
            this.s.setScaleY(1.0f);
            this.s.setScaleX(1.0f);
            d(1.0f);
            if (eVar != null) {
                eVar.a();
            }
        }
    }

    private MotionSpec y() {
        if (this.v == null) {
            this.v = MotionSpec.createFromResource(this.s.getContext(), R.animator.design_fab_show_motion_spec);
        }
        return (MotionSpec) Preconditions.checkNotNull(this.v);
    }

    private MotionSpec z() {
        if (this.w == null) {
            this.w = MotionSpec.createFromResource(this.s.getContext(), R.animator.design_fab_hide_motion_spec);
        }
        return (MotionSpec) Preconditions.checkNotNull(this.w);
    }

    @NonNull
    private AnimatorSet a(@NonNull MotionSpec motionSpec, float f2, float f3, float f4) {
        ArrayList arrayList = new ArrayList();
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.s, View.ALPHA, f2);
        motionSpec.getTiming("opacity").apply(ofFloat);
        arrayList.add(ofFloat);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.s, View.SCALE_X, f3);
        motionSpec.getTiming("scale").apply(ofFloat2);
        a(ofFloat2);
        arrayList.add(ofFloat2);
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(this.s, View.SCALE_Y, f3);
        motionSpec.getTiming("scale").apply(ofFloat3);
        a(ofFloat3);
        arrayList.add(ofFloat3);
        a(f4, this.K);
        ObjectAnimator ofObject = ObjectAnimator.ofObject(this.s, new ImageMatrixProperty(), new MatrixEvaluator() { // from class: com.google.android.material.floatingactionbutton.d.3
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.google.android.material.animation.MatrixEvaluator
            public Matrix evaluate(float f5, @NonNull Matrix matrix, @NonNull Matrix matrix2) {
                d.this.B = f5;
                return super.evaluate(f5, matrix, matrix2);
            }
        }, new Matrix(this.K));
        motionSpec.getTiming("iconScale").apply(ofObject);
        arrayList.add(ofObject);
        AnimatorSet animatorSet = new AnimatorSet();
        AnimatorSetCompat.playTogether(animatorSet, arrayList);
        return animatorSet;
    }

    private void a(ObjectAnimator objectAnimator) {
        if (Build.VERSION.SDK_INT == 26) {
            objectAnimator.setEvaluator(new TypeEvaluator<Float>() { // from class: com.google.android.material.floatingactionbutton.d.4
                FloatEvaluator a = new FloatEvaluator();

                /* renamed from: a */
                public Float evaluate(float f2, Float f3, Float f4) {
                    float floatValue = this.a.evaluate(f2, (Number) f3, (Number) f4).floatValue();
                    if (floatValue < 0.1f) {
                        floatValue = 0.0f;
                    }
                    return Float.valueOf(floatValue);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(@NonNull AbstractC0076d dVar) {
        if (this.G == null) {
            this.G = new ArrayList<>();
        }
        this.G.add(dVar);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b(@NonNull AbstractC0076d dVar) {
        ArrayList<AbstractC0076d> arrayList = this.G;
        if (arrayList != null) {
            arrayList.remove(dVar);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void k() {
        ArrayList<AbstractC0076d> arrayList = this.G;
        if (arrayList != null) {
            Iterator<AbstractC0076d> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().a();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void l() {
        ArrayList<AbstractC0076d> arrayList = this.G;
        if (arrayList != null) {
            Iterator<AbstractC0076d> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().b();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public final Drawable m() {
        return this.f;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void o() {
        Rect rect = this.H;
        a(rect);
        b(rect);
        this.t.setShadowPadding(rect.left, rect.top, rect.right, rect.bottom);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(@NonNull Rect rect) {
        int sizeDimension = this.g ? (this.l - this.s.getSizeDimension()) / 2 : 0;
        float a2 = this.h ? a() + this.k : 0.0f;
        int max = Math.max(sizeDimension, (int) Math.ceil(a2));
        int max2 = Math.max(sizeDimension, (int) Math.ceil(a2 * 1.5f));
        rect.set(max, max2, max, max2);
    }

    void b(@NonNull Rect rect) {
        Preconditions.checkNotNull(this.f, "Didn't initialize content background");
        if (p()) {
            this.t.setBackgroundDrawable(new InsetDrawable(this.f, rect.left, rect.top, rect.right, rect.bottom));
            return;
        }
        this.t.setBackgroundDrawable(this.f);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void q() {
        MaterialShapeDrawable materialShapeDrawable = this.c;
        if (materialShapeDrawable != null) {
            MaterialShapeUtils.setParentAbsoluteElevation(this.s, materialShapeDrawable);
        }
        if (s()) {
            this.s.getViewTreeObserver().addOnPreDrawListener(A());
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void r() {
        ViewTreeObserver viewTreeObserver = this.s.getViewTreeObserver();
        ViewTreeObserver.OnPreDrawListener onPreDrawListener = this.L;
        if (onPreDrawListener != null) {
            viewTreeObserver.removeOnPreDrawListener(onPreDrawListener);
            this.L = null;
        }
    }

    void t() {
        float rotation = this.s.getRotation();
        if (this.A != rotation) {
            this.A = rotation;
            x();
        }
    }

    @NonNull
    private ViewTreeObserver.OnPreDrawListener A() {
        if (this.L == null) {
            this.L = new ViewTreeObserver.OnPreDrawListener() { // from class: com.google.android.material.floatingactionbutton.d.5
                @Override // android.view.ViewTreeObserver.OnPreDrawListener
                public boolean onPreDraw() {
                    d.this.t();
                    return true;
                }
            };
        }
        return this.L;
    }

    MaterialShapeDrawable u() {
        return new MaterialShapeDrawable((ShapeAppearanceModel) Preconditions.checkNotNull(this.b));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean v() {
        return this.s.getVisibility() != 0 ? this.D == 2 : this.D != 1;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean w() {
        return this.s.getVisibility() == 0 ? this.D == 1 : this.D != 2;
    }

    @NonNull
    private ValueAnimator a(@NonNull g gVar) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setInterpolator(a);
        valueAnimator.setDuration(100L);
        valueAnimator.addListener(gVar);
        valueAnimator.addUpdateListener(gVar);
        valueAnimator.setFloatValues(0.0f, 1.0f);
        return valueAnimator;
    }

    /* compiled from: FloatingActionButtonImpl.java */
    /* loaded from: classes2.dex */
    private abstract class g extends AnimatorListenerAdapter implements ValueAnimator.AnimatorUpdateListener {
        private boolean a;
        private float c;
        private float d;

        protected abstract float a();

        private g() {
        }

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator) {
            if (!this.a) {
                this.c = d.this.c == null ? 0.0f : d.this.c.getElevation();
                this.d = a();
                this.a = true;
            }
            d dVar = d.this;
            float f = this.c;
            dVar.e((int) (f + ((this.d - f) * valueAnimator.getAnimatedFraction())));
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            d.this.e((int) this.d);
            this.a = false;
        }
    }

    /* compiled from: FloatingActionButtonImpl.java */
    /* loaded from: classes2.dex */
    private class f extends g {
        f() {
            super();
        }

        @Override // com.google.android.material.floatingactionbutton.d.g
        protected float a() {
            return d.this.i;
        }
    }

    /* compiled from: FloatingActionButtonImpl.java */
    /* loaded from: classes2.dex */
    private class b extends g {
        b() {
            super();
        }

        @Override // com.google.android.material.floatingactionbutton.d.g
        protected float a() {
            return d.this.i + d.this.j;
        }
    }

    /* compiled from: FloatingActionButtonImpl.java */
    /* loaded from: classes2.dex */
    private class c extends g {
        c() {
            super();
        }

        @Override // com.google.android.material.floatingactionbutton.d.g
        protected float a() {
            return d.this.i + d.this.k;
        }
    }

    /* compiled from: FloatingActionButtonImpl.java */
    /* loaded from: classes2.dex */
    private class a extends g {
        @Override // com.google.android.material.floatingactionbutton.d.g
        protected float a() {
            return 0.0f;
        }

        a() {
            super();
        }
    }

    private boolean B() {
        return ViewCompat.isLaidOut(this.s) && !this.s.isInEditMode();
    }

    void x() {
        if (Build.VERSION.SDK_INT == 19) {
            if (this.A % 90.0f != 0.0f) {
                if (this.s.getLayerType() != 1) {
                    this.s.setLayerType(1, null);
                }
            } else if (this.s.getLayerType() != 0) {
                this.s.setLayerType(0, null);
            }
        }
        MaterialShapeDrawable materialShapeDrawable = this.c;
        if (materialShapeDrawable != null) {
            materialShapeDrawable.setShadowCompatRotation((int) this.A);
        }
    }
}
