package com.google.android.material.transition;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.StyleRes;
import androidx.core.util.Preconditions;
import androidx.core.view.InputDeviceCompat;
import androidx.core.view.ViewCompat;
import androidx.transition.ArcMotion;
import androidx.transition.PathMotion;
import androidx.transition.Transition;
import androidx.transition.TransitionValues;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.Shapeable;
import com.google.android.material.transition.j;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes2.dex */
public final class MaterialContainerTransform extends Transition {
    public static final int FADE_MODE_CROSS = 2;
    public static final int FADE_MODE_IN = 0;
    public static final int FADE_MODE_OUT = 1;
    public static final int FADE_MODE_THROUGH = 3;
    public static final int FIT_MODE_AUTO = 0;
    public static final int FIT_MODE_HEIGHT = 2;
    public static final int FIT_MODE_WIDTH = 1;
    public static final int TRANSITION_DIRECTION_AUTO = 0;
    public static final int TRANSITION_DIRECTION_ENTER = 1;
    public static final int TRANSITION_DIRECTION_RETURN = 2;
    private static final String a = "MaterialContainerTransform";
    private static final String[] i = {"materialContainerTransition:bounds", "materialContainerTransition:shapeAppearance"};
    private static final a j = new a(new ProgressThresholds(0.0f, 0.25f), new ProgressThresholds(0.0f, 1.0f), new ProgressThresholds(0.0f, 1.0f), new ProgressThresholds(0.0f, 0.75f));
    private static final a k = new a(new ProgressThresholds(0.6f, 0.9f), new ProgressThresholds(0.0f, 1.0f), new ProgressThresholds(0.0f, 0.9f), new ProgressThresholds(0.3f, 0.9f));
    private static final a l = new a(new ProgressThresholds(0.1f, 0.4f), new ProgressThresholds(0.1f, 1.0f), new ProgressThresholds(0.1f, 1.0f), new ProgressThresholds(0.1f, 0.9f));
    private static final a m = new a(new ProgressThresholds(0.6f, 0.9f), new ProgressThresholds(0.0f, 0.9f), new ProgressThresholds(0.0f, 0.9f), new ProgressThresholds(0.2f, 0.9f));
    private int A;
    @Nullable
    private View B;
    @Nullable
    private View C;
    @Nullable
    private ShapeAppearanceModel D;
    @Nullable
    private ShapeAppearanceModel E;
    @Nullable
    private ProgressThresholds F;
    @Nullable
    private ProgressThresholds G;
    @Nullable
    private ProgressThresholds H;
    @Nullable
    private ProgressThresholds I;
    private boolean J;
    private float K;
    private float L;
    private boolean n;
    private boolean o;
    private boolean p;
    private boolean q;
    @IdRes
    private int r;
    @IdRes
    private int s;
    @IdRes
    private int t;
    @ColorInt
    private int u;
    @ColorInt
    private int v;
    @ColorInt
    private int w;
    @ColorInt
    private int x;
    private int y;
    private int z;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    /* loaded from: classes2.dex */
    public @interface FadeMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    /* loaded from: classes2.dex */
    public @interface FitMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    /* loaded from: classes2.dex */
    public @interface TransitionDirection {
    }

    public MaterialContainerTransform() {
        boolean z = false;
        this.n = false;
        this.o = false;
        this.p = false;
        this.q = false;
        this.r = 16908290;
        this.s = -1;
        this.t = -1;
        this.u = 0;
        this.v = 0;
        this.w = 0;
        this.x = 1375731712;
        this.y = 0;
        this.z = 0;
        this.A = 0;
        this.J = Build.VERSION.SDK_INT >= 28 ? true : z;
        this.K = -1.0f;
        this.L = -1.0f;
    }

    public MaterialContainerTransform(@NonNull Context context, boolean z) {
        boolean z2 = false;
        this.n = false;
        this.o = false;
        this.p = false;
        this.q = false;
        this.r = 16908290;
        this.s = -1;
        this.t = -1;
        this.u = 0;
        this.v = 0;
        this.w = 0;
        this.x = 1375731712;
        this.y = 0;
        this.z = 0;
        this.A = 0;
        this.J = Build.VERSION.SDK_INT >= 28 ? true : z2;
        this.K = -1.0f;
        this.L = -1.0f;
        a(context, z);
        this.q = true;
    }

    @IdRes
    public int getStartViewId() {
        return this.s;
    }

    public void setStartViewId(@IdRes int i2) {
        this.s = i2;
    }

    @IdRes
    public int getEndViewId() {
        return this.t;
    }

    public void setEndViewId(@IdRes int i2) {
        this.t = i2;
    }

    @Nullable
    public View getStartView() {
        return this.B;
    }

    public void setStartView(@Nullable View view) {
        this.B = view;
    }

    @Nullable
    public View getEndView() {
        return this.C;
    }

    public void setEndView(@Nullable View view) {
        this.C = view;
    }

    @Nullable
    public ShapeAppearanceModel getStartShapeAppearanceModel() {
        return this.D;
    }

    public void setStartShapeAppearanceModel(@Nullable ShapeAppearanceModel shapeAppearanceModel) {
        this.D = shapeAppearanceModel;
    }

    @Nullable
    public ShapeAppearanceModel getEndShapeAppearanceModel() {
        return this.E;
    }

    public void setEndShapeAppearanceModel(@Nullable ShapeAppearanceModel shapeAppearanceModel) {
        this.E = shapeAppearanceModel;
    }

    public boolean isElevationShadowEnabled() {
        return this.J;
    }

    public void setElevationShadowEnabled(boolean z) {
        this.J = z;
    }

    public float getStartElevation() {
        return this.K;
    }

    public void setStartElevation(float f) {
        this.K = f;
    }

    public float getEndElevation() {
        return this.L;
    }

    public void setEndElevation(float f) {
        this.L = f;
    }

    @IdRes
    public int getDrawingViewId() {
        return this.r;
    }

    public void setDrawingViewId(@IdRes int i2) {
        this.r = i2;
    }

    @ColorInt
    public int getContainerColor() {
        return this.u;
    }

    public void setContainerColor(@ColorInt int i2) {
        this.u = i2;
    }

    @ColorInt
    public int getStartContainerColor() {
        return this.v;
    }

    public void setStartContainerColor(@ColorInt int i2) {
        this.v = i2;
    }

    @ColorInt
    public int getEndContainerColor() {
        return this.w;
    }

    public void setEndContainerColor(@ColorInt int i2) {
        this.w = i2;
    }

    public void setAllContainerColors(@ColorInt int i2) {
        this.u = i2;
        this.v = i2;
        this.w = i2;
    }

    @ColorInt
    public int getScrimColor() {
        return this.x;
    }

    public void setScrimColor(@ColorInt int i2) {
        this.x = i2;
    }

    public int getTransitionDirection() {
        return this.y;
    }

    public void setTransitionDirection(int i2) {
        this.y = i2;
    }

    public int getFadeMode() {
        return this.z;
    }

    public void setFadeMode(int i2) {
        this.z = i2;
    }

    public int getFitMode() {
        return this.A;
    }

    public void setFitMode(int i2) {
        this.A = i2;
    }

    @Nullable
    public ProgressThresholds getFadeProgressThresholds() {
        return this.F;
    }

    public void setFadeProgressThresholds(@Nullable ProgressThresholds progressThresholds) {
        this.F = progressThresholds;
    }

    @Nullable
    public ProgressThresholds getScaleProgressThresholds() {
        return this.G;
    }

    public void setScaleProgressThresholds(@Nullable ProgressThresholds progressThresholds) {
        this.G = progressThresholds;
    }

    @Nullable
    public ProgressThresholds getScaleMaskProgressThresholds() {
        return this.H;
    }

    public void setScaleMaskProgressThresholds(@Nullable ProgressThresholds progressThresholds) {
        this.H = progressThresholds;
    }

    @Nullable
    public ProgressThresholds getShapeMaskProgressThresholds() {
        return this.I;
    }

    public void setShapeMaskProgressThresholds(@Nullable ProgressThresholds progressThresholds) {
        this.I = progressThresholds;
    }

    public boolean isHoldAtEndEnabled() {
        return this.o;
    }

    public void setHoldAtEndEnabled(boolean z) {
        this.o = z;
    }

    public boolean isDrawDebugEnabled() {
        return this.n;
    }

    public void setDrawDebugEnabled(boolean z) {
        this.n = z;
    }

    @Override // androidx.transition.Transition
    public void setPathMotion(@Nullable PathMotion pathMotion) {
        super.setPathMotion(pathMotion);
        this.p = true;
    }

    @Override // androidx.transition.Transition
    @Nullable
    public String[] getTransitionProperties() {
        return i;
    }

    @Override // androidx.transition.Transition
    public void captureStartValues(@NonNull TransitionValues transitionValues) {
        a(transitionValues, this.B, this.s, this.D);
    }

    @Override // androidx.transition.Transition
    public void captureEndValues(@NonNull TransitionValues transitionValues) {
        a(transitionValues, this.C, this.t, this.E);
    }

    private static void a(@NonNull TransitionValues transitionValues, @Nullable View view, @IdRes int i2, @Nullable ShapeAppearanceModel shapeAppearanceModel) {
        if (i2 != -1) {
            transitionValues.view = j.a(transitionValues.view, i2);
        } else if (view != null) {
            transitionValues.view = view;
        } else if (transitionValues.view.getTag(R.id.mtrl_motion_snapshot_view) instanceof View) {
            transitionValues.view.setTag(R.id.mtrl_motion_snapshot_view, null);
            transitionValues.view = (View) transitionValues.view.getTag(R.id.mtrl_motion_snapshot_view);
        }
        View view2 = transitionValues.view;
        if (ViewCompat.isLaidOut(view2) || view2.getWidth() != 0 || view2.getHeight() != 0) {
            RectF a2 = view2.getParent() == null ? j.a(view2) : j.b(view2);
            transitionValues.values.put("materialContainerTransition:bounds", a2);
            transitionValues.values.put("materialContainerTransition:shapeAppearance", a(view2, a2, shapeAppearanceModel));
        }
    }

    private static ShapeAppearanceModel a(@NonNull View view, @NonNull RectF rectF, @Nullable ShapeAppearanceModel shapeAppearanceModel) {
        return j.a(a(view, shapeAppearanceModel), rectF);
    }

    private static ShapeAppearanceModel a(@NonNull View view, @Nullable ShapeAppearanceModel shapeAppearanceModel) {
        if (shapeAppearanceModel != null) {
            return shapeAppearanceModel;
        }
        if (view.getTag(R.id.mtrl_motion_snapshot_view) instanceof ShapeAppearanceModel) {
            return (ShapeAppearanceModel) view.getTag(R.id.mtrl_motion_snapshot_view);
        }
        Context context = view.getContext();
        int a2 = a(context);
        if (a2 != -1) {
            return ShapeAppearanceModel.builder(context, a2, 0).build();
        }
        if (view instanceof Shapeable) {
            return ((Shapeable) view).getShapeAppearanceModel();
        }
        return ShapeAppearanceModel.builder().build();
    }

    @StyleRes
    private static int a(Context context) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(new int[]{R.attr.transitionShapeAppearance});
        int resourceId = obtainStyledAttributes.getResourceId(0, -1);
        obtainStyledAttributes.recycle();
        return resourceId;
    }

    @Override // androidx.transition.Transition
    @Nullable
    public Animator createAnimator(@NonNull ViewGroup viewGroup, @Nullable TransitionValues transitionValues, @Nullable TransitionValues transitionValues2) {
        final View view;
        View view2;
        if (transitionValues == null || transitionValues2 == null) {
            return null;
        }
        RectF rectF = (RectF) transitionValues.values.get("materialContainerTransition:bounds");
        ShapeAppearanceModel shapeAppearanceModel = (ShapeAppearanceModel) transitionValues.values.get("materialContainerTransition:shapeAppearance");
        if (rectF == null || shapeAppearanceModel == null) {
            Log.w(a, "Skipping due to null start bounds. Ensure start view is laid out and measured.");
            return null;
        }
        RectF rectF2 = (RectF) transitionValues2.values.get("materialContainerTransition:bounds");
        ShapeAppearanceModel shapeAppearanceModel2 = (ShapeAppearanceModel) transitionValues2.values.get("materialContainerTransition:shapeAppearance");
        if (rectF2 == null || shapeAppearanceModel2 == null) {
            Log.w(a, "Skipping due to null end bounds. Ensure end view is laid out and measured.");
            return null;
        }
        final View view3 = transitionValues.view;
        final View view4 = transitionValues2.view;
        View view5 = view4.getParent() != null ? view4 : view3;
        if (this.r == view5.getId()) {
            view = (View) view5.getParent();
            view2 = view5;
        } else {
            view = j.b(view5, this.r);
            view2 = null;
        }
        RectF b2 = j.b(view);
        float f = -b2.left;
        float f2 = -b2.top;
        RectF a2 = a(view, view2, f, f2);
        rectF.offset(f, f2);
        rectF2.offset(f, f2);
        boolean a3 = a(rectF, rectF2);
        if (!this.q) {
            a(view5.getContext(), a3);
        }
        final b bVar = new b(getPathMotion(), view3, rectF, shapeAppearanceModel, a(this.K, view3), view4, rectF2, shapeAppearanceModel2, a(this.L, view4), this.u, this.v, this.w, this.x, a3, this.J, b.a(this.z, a3), e.a(this.A, a3, rectF, rectF2), c(a3), this.n);
        bVar.setBounds(Math.round(a2.left), Math.round(a2.top), Math.round(a2.right), Math.round(a2.bottom));
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.transition.MaterialContainerTransform.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                bVar.a(valueAnimator.getAnimatedFraction());
            }
        });
        addListener(new i() { // from class: com.google.android.material.transition.MaterialContainerTransform.2
            @Override // com.google.android.material.transition.i, androidx.transition.Transition.TransitionListener
            public void onTransitionStart(@NonNull Transition transition) {
                ViewUtils.getOverlay(view).add(bVar);
                view3.setAlpha(0.0f);
                view4.setAlpha(0.0f);
            }

            @Override // com.google.android.material.transition.i, androidx.transition.Transition.TransitionListener
            public void onTransitionEnd(@NonNull Transition transition) {
                MaterialContainerTransform.this.removeListener(this);
                if (!MaterialContainerTransform.this.o) {
                    view3.setAlpha(1.0f);
                    view4.setAlpha(1.0f);
                    ViewUtils.getOverlay(view).remove(bVar);
                }
            }
        });
        return ofFloat;
    }

    private void a(Context context, boolean z) {
        j.a(this, context, R.attr.motionEasingStandard, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
        j.a(this, context, z ? R.attr.motionDurationLong1 : R.attr.motionDurationMedium2);
        if (!this.p) {
            j.b(this, context, R.attr.motionPath);
        }
    }

    private static float a(float f, View view) {
        return f != -1.0f ? f : ViewCompat.getElevation(view);
    }

    private static RectF a(View view, @Nullable View view2, float f, float f2) {
        if (view2 == null) {
            return new RectF(0.0f, 0.0f, view.getWidth(), view.getHeight());
        }
        RectF b2 = j.b(view2);
        b2.offset(f, f2);
        return b2;
    }

    private boolean a(@NonNull RectF rectF, @NonNull RectF rectF2) {
        switch (this.y) {
            case 0:
                return j.a(rectF2) > j.a(rectF);
            case 1:
                return true;
            case 2:
                return false;
            default:
                throw new IllegalArgumentException("Invalid transition direction: " + this.y);
        }
    }

    private a c(boolean z) {
        PathMotion pathMotion = getPathMotion();
        if ((pathMotion instanceof ArcMotion) || (pathMotion instanceof MaterialArcMotion)) {
            return a(z, l, m);
        }
        return a(z, j, k);
    }

    private a a(boolean z, a aVar, a aVar2) {
        if (!z) {
            aVar = aVar2;
        }
        return new a((ProgressThresholds) j.a(this.F, aVar.a), (ProgressThresholds) j.a(this.G, aVar.b), (ProgressThresholds) j.a(this.H, aVar.c), (ProgressThresholds) j.a(this.I, aVar.d));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class b extends Drawable {
        private final a A;
        private final a B;
        private final d C;
        private final boolean D;
        private final Paint E;
        private final Path F;
        private c G;
        private f H;
        private RectF I;
        private float J;
        private float K;
        private float L;
        private final View a;
        private final RectF b;
        private final ShapeAppearanceModel c;
        private final float d;
        private final View e;
        private final RectF f;
        private final ShapeAppearanceModel g;
        private final float h;
        private final Paint i;
        private final Paint j;
        private final Paint k;
        private final Paint l;
        private final Paint m;
        private final g n;
        private final PathMeasure o;
        private final float p;
        private final float[] q;
        private final boolean r;
        private final float s;
        private final float t;
        private final boolean u;
        private final MaterialShapeDrawable v;
        private final RectF w;
        private final RectF x;
        private final RectF y;
        private final RectF z;

        @Override // android.graphics.drawable.Drawable
        public int getOpacity() {
            return -3;
        }

        private b(PathMotion pathMotion, View view, RectF rectF, ShapeAppearanceModel shapeAppearanceModel, float f, View view2, RectF rectF2, ShapeAppearanceModel shapeAppearanceModel2, float f2, @ColorInt int i, @ColorInt int i2, @ColorInt int i3, int i4, boolean z, boolean z2, a aVar, d dVar, a aVar2, boolean z3) {
            this.i = new Paint();
            this.j = new Paint();
            this.k = new Paint();
            this.l = new Paint();
            this.m = new Paint();
            this.n = new g();
            this.q = new float[2];
            this.v = new MaterialShapeDrawable();
            this.E = new Paint();
            this.F = new Path();
            this.a = view;
            this.b = rectF;
            this.c = shapeAppearanceModel;
            this.d = f;
            this.e = view2;
            this.f = rectF2;
            this.g = shapeAppearanceModel2;
            this.h = f2;
            this.r = z;
            this.u = z2;
            this.B = aVar;
            this.C = dVar;
            this.A = aVar2;
            this.D = z3;
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((WindowManager) view.getContext().getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
            this.s = displayMetrics.widthPixels;
            this.t = displayMetrics.heightPixels;
            this.i.setColor(i);
            this.j.setColor(i2);
            this.k.setColor(i3);
            this.v.setFillColor(ColorStateList.valueOf(0));
            this.v.setShadowCompatibilityMode(2);
            this.v.setShadowBitmapDrawingEnable(false);
            this.v.setShadowColor(-7829368);
            this.w = new RectF(rectF);
            this.x = new RectF(this.w);
            this.y = new RectF(this.w);
            this.z = new RectF(this.y);
            PointF a = a(rectF);
            PointF a2 = a(rectF2);
            this.o = new PathMeasure(pathMotion.getPath(a.x, a.y, a2.x, a2.y), false);
            this.p = this.o.getLength();
            this.q[0] = rectF.centerX();
            this.q[1] = rectF.top;
            this.m.setStyle(Paint.Style.FILL);
            this.m.setShader(j.a(i4));
            this.E.setStyle(Paint.Style.STROKE);
            this.E.setStrokeWidth(10.0f);
            b(0.0f);
        }

        @Override // android.graphics.drawable.Drawable
        public void draw(@NonNull Canvas canvas) {
            if (this.m.getAlpha() > 0) {
                canvas.drawRect(getBounds(), this.m);
            }
            int save = this.D ? canvas.save() : -1;
            if (this.u && this.J > 0.0f) {
                a(canvas);
            }
            this.n.a(canvas);
            a(canvas, this.i);
            if (this.G.c) {
                d(canvas);
                e(canvas);
            } else {
                e(canvas);
                d(canvas);
            }
            if (this.D) {
                canvas.restoreToCount(save);
                a(canvas, this.w, this.F, -65281);
                a(canvas, this.x, InputDeviceCompat.SOURCE_ANY);
                a(canvas, this.w, -16711936);
                a(canvas, this.z, -16711681);
                a(canvas, this.y, -16776961);
            }
        }

        private void a(Canvas canvas) {
            canvas.save();
            canvas.clipPath(this.n.a(), Region.Op.DIFFERENCE);
            if (Build.VERSION.SDK_INT > 28) {
                b(canvas);
            } else {
                c(canvas);
            }
            canvas.restore();
        }

        private void b(Canvas canvas) {
            ShapeAppearanceModel b = this.n.b();
            if (b.isRoundRect(this.I)) {
                float cornerSize = b.getTopLeftCornerSize().getCornerSize(this.I);
                canvas.drawRoundRect(this.I, cornerSize, cornerSize, this.l);
                return;
            }
            canvas.drawPath(this.n.a(), this.l);
        }

        private void c(Canvas canvas) {
            this.v.setBounds((int) this.I.left, (int) this.I.top, (int) this.I.right, (int) this.I.bottom);
            this.v.setElevation(this.J);
            this.v.setShadowVerticalOffset((int) this.K);
            this.v.setShapeAppearanceModel(this.n.b());
            this.v.draw(canvas);
        }

        private void d(Canvas canvas) {
            a(canvas, this.j);
            j.a(canvas, getBounds(), this.w.left, this.w.top, this.H.a, this.G.a, new j.a() { // from class: com.google.android.material.transition.MaterialContainerTransform.b.1
                @Override // com.google.android.material.transition.j.a
                public void a(Canvas canvas2) {
                    b.this.a.draw(canvas2);
                }
            });
        }

        private void e(Canvas canvas) {
            a(canvas, this.k);
            j.a(canvas, getBounds(), this.y.left, this.y.top, this.H.b, this.G.b, new j.a() { // from class: com.google.android.material.transition.MaterialContainerTransform.b.2
                @Override // com.google.android.material.transition.j.a
                public void a(Canvas canvas2) {
                    b.this.e.draw(canvas2);
                }
            });
        }

        private void a(Canvas canvas, Paint paint) {
            if (paint.getColor() != 0 && paint.getAlpha() > 0) {
                canvas.drawRect(getBounds(), paint);
            }
        }

        @Override // android.graphics.drawable.Drawable
        public void setAlpha(int i) {
            throw new UnsupportedOperationException("Setting alpha on is not supported");
        }

        @Override // android.graphics.drawable.Drawable
        public void setColorFilter(@Nullable ColorFilter colorFilter) {
            throw new UnsupportedOperationException("Setting a color filter is not supported");
        }

        public void a(float f) {
            if (this.L != f) {
                b(f);
            }
        }

        private void b(float f) {
            float f2;
            float f3;
            float f4;
            float f5;
            this.L = f;
            this.m.setAlpha((int) (this.r ? j.a(0.0f, 255.0f, f) : j.a(255.0f, 0.0f, f)));
            this.o.getPosTan(this.p * f, this.q, null);
            float[] fArr = this.q;
            float f6 = fArr[0];
            float f7 = fArr[1];
            int i = (f > 1.0f ? 1 : (f == 1.0f ? 0 : -1));
            if (i > 0 || f < 0.0f) {
                if (i > 0) {
                    f5 = 0.99f;
                    f4 = (f - 1.0f) / 0.00999999f;
                } else {
                    f5 = 0.01f;
                    f4 = (f / 0.01f) * (-1.0f);
                }
                this.o.getPosTan(this.p * f5, this.q, null);
                float[] fArr2 = this.q;
                f3 = f6 + ((f6 - fArr2[0]) * f4);
                f2 = f7 + ((f7 - fArr2[1]) * f4);
            } else {
                f2 = f7;
                f3 = f6;
            }
            this.H = this.C.a(f, ((Float) Preconditions.checkNotNull(Float.valueOf(this.A.b.a))).floatValue(), ((Float) Preconditions.checkNotNull(Float.valueOf(this.A.b.b))).floatValue(), this.b.width(), this.b.height(), this.f.width(), this.f.height());
            this.w.set(f3 - (this.H.c / 2.0f), f2, (this.H.c / 2.0f) + f3, this.H.d + f2);
            this.y.set(f3 - (this.H.e / 2.0f), f2, f3 + (this.H.e / 2.0f), this.H.f + f2);
            this.x.set(this.w);
            this.z.set(this.y);
            float floatValue = ((Float) Preconditions.checkNotNull(Float.valueOf(this.A.c.a))).floatValue();
            float floatValue2 = ((Float) Preconditions.checkNotNull(Float.valueOf(this.A.c.b))).floatValue();
            boolean a = this.C.a(this.H);
            RectF rectF = a ? this.x : this.z;
            float a2 = j.a(0.0f, 1.0f, floatValue, floatValue2, f);
            if (!a) {
                a2 = 1.0f - a2;
            }
            this.C.a(rectF, a2, this.H);
            this.I = new RectF(Math.min(this.x.left, this.z.left), Math.min(this.x.top, this.z.top), Math.max(this.x.right, this.z.right), Math.max(this.x.bottom, this.z.bottom));
            this.n.a(f, this.c, this.g, this.w, this.x, this.z, this.A.d);
            this.J = j.a(this.d, this.h, f);
            float a3 = a(this.I, this.s);
            float b = b(this.I, this.t);
            float f8 = this.J;
            this.K = (int) (b * f8);
            this.l.setShadowLayer(f8, (int) (a3 * f8), this.K, 754974720);
            this.G = this.B.a(f, ((Float) Preconditions.checkNotNull(Float.valueOf(this.A.a.a))).floatValue(), ((Float) Preconditions.checkNotNull(Float.valueOf(this.A.a.b))).floatValue(), 0.35f);
            if (this.j.getColor() != 0) {
                this.j.setAlpha(this.G.a);
            }
            if (this.k.getColor() != 0) {
                this.k.setAlpha(this.G.b);
            }
            invalidateSelf();
        }

        private static PointF a(RectF rectF) {
            return new PointF(rectF.centerX(), rectF.top);
        }

        private static float a(RectF rectF, float f) {
            return ((rectF.centerX() / (f / 2.0f)) - 1.0f) * 0.3f;
        }

        private static float b(RectF rectF, float f) {
            return (rectF.centerY() / f) * 1.5f;
        }

        private void a(Canvas canvas, RectF rectF, Path path, @ColorInt int i) {
            PointF a = a(rectF);
            if (this.L == 0.0f) {
                path.reset();
                path.moveTo(a.x, a.y);
                return;
            }
            path.lineTo(a.x, a.y);
            this.E.setColor(i);
            canvas.drawPath(path, this.E);
        }

        private void a(Canvas canvas, RectF rectF, @ColorInt int i) {
            this.E.setColor(i);
            canvas.drawRect(rectF, this.E);
        }
    }

    /* loaded from: classes2.dex */
    public static class ProgressThresholds {
        @FloatRange(from = 0.0d, to = 1.0d)
        private final float a;
        @FloatRange(from = 0.0d, to = 1.0d)
        private final float b;

        public ProgressThresholds(@FloatRange(from = 0.0d, to = 1.0d) float f, @FloatRange(from = 0.0d, to = 1.0d) float f2) {
            this.a = f;
            this.b = f2;
        }

        @FloatRange(from = 0.0d, to = 1.0d)
        public float getStart() {
            return this.a;
        }

        @FloatRange(from = 0.0d, to = 1.0d)
        public float getEnd() {
            return this.b;
        }
    }

    /* loaded from: classes2.dex */
    public static class a {
        @NonNull
        private final ProgressThresholds a;
        @NonNull
        private final ProgressThresholds b;
        @NonNull
        private final ProgressThresholds c;
        @NonNull
        private final ProgressThresholds d;

        private a(@NonNull ProgressThresholds progressThresholds, @NonNull ProgressThresholds progressThresholds2, @NonNull ProgressThresholds progressThresholds3, @NonNull ProgressThresholds progressThresholds4) {
            this.a = progressThresholds;
            this.b = progressThresholds2;
            this.c = progressThresholds3;
            this.d = progressThresholds4;
        }
    }
}
