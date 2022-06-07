package com.google.android.material.transition.platform;

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
import android.transition.ArcMotion;
import android.transition.PathMotion;
import android.transition.Transition;
import android.transition.TransitionValues;
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
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.StyleRes;
import androidx.core.util.Preconditions;
import androidx.core.view.InputDeviceCompat;
import androidx.core.view.ViewCompat;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.Shapeable;
import com.google.android.material.transition.platform.j;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@RequiresApi(21)
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
    private static final String[] b = {"materialContainerTransition:bounds", "materialContainerTransition:shapeAppearance"};
    private static final a c = new a(new ProgressThresholds(0.0f, 0.25f), new ProgressThresholds(0.0f, 1.0f), new ProgressThresholds(0.0f, 1.0f), new ProgressThresholds(0.0f, 0.75f));
    private static final a d = new a(new ProgressThresholds(0.6f, 0.9f), new ProgressThresholds(0.0f, 1.0f), new ProgressThresholds(0.0f, 0.9f), new ProgressThresholds(0.3f, 0.9f));
    private static final a e = new a(new ProgressThresholds(0.1f, 0.4f), new ProgressThresholds(0.1f, 1.0f), new ProgressThresholds(0.1f, 1.0f), new ProgressThresholds(0.1f, 0.9f));
    private static final a f = new a(new ProgressThresholds(0.6f, 0.9f), new ProgressThresholds(0.0f, 0.9f), new ProgressThresholds(0.0f, 0.9f), new ProgressThresholds(0.2f, 0.9f));
    @Nullable
    private ProgressThresholds A;
    @Nullable
    private ProgressThresholds B;
    private boolean C;
    private float D;
    private float E;
    private boolean g;
    private boolean h;
    private boolean i;
    private boolean j;
    @IdRes
    private int k;
    @IdRes
    private int l;
    @IdRes
    private int m;
    @ColorInt
    private int n;
    @ColorInt
    private int o;
    @ColorInt
    private int p;
    @ColorInt
    private int q;
    private int r;
    private int s;
    private int t;
    @Nullable
    private View u;
    @Nullable
    private View v;
    @Nullable
    private ShapeAppearanceModel w;
    @Nullable
    private ShapeAppearanceModel x;
    @Nullable
    private ProgressThresholds y;
    @Nullable
    private ProgressThresholds z;

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
        this.g = false;
        this.h = false;
        this.i = false;
        this.j = false;
        this.k = 16908290;
        this.l = -1;
        this.m = -1;
        this.n = 0;
        this.o = 0;
        this.p = 0;
        this.q = 1375731712;
        this.r = 0;
        this.s = 0;
        this.t = 0;
        this.C = Build.VERSION.SDK_INT >= 28 ? true : z;
        this.D = -1.0f;
        this.E = -1.0f;
    }

    public MaterialContainerTransform(@NonNull Context context, boolean z) {
        boolean z2 = false;
        this.g = false;
        this.h = false;
        this.i = false;
        this.j = false;
        this.k = 16908290;
        this.l = -1;
        this.m = -1;
        this.n = 0;
        this.o = 0;
        this.p = 0;
        this.q = 1375731712;
        this.r = 0;
        this.s = 0;
        this.t = 0;
        this.C = Build.VERSION.SDK_INT >= 28 ? true : z2;
        this.D = -1.0f;
        this.E = -1.0f;
        a(context, z);
        this.j = true;
    }

    @IdRes
    public int getStartViewId() {
        return this.l;
    }

    public void setStartViewId(@IdRes int i) {
        this.l = i;
    }

    @IdRes
    public int getEndViewId() {
        return this.m;
    }

    public void setEndViewId(@IdRes int i) {
        this.m = i;
    }

    @Nullable
    public View getStartView() {
        return this.u;
    }

    public void setStartView(@Nullable View view) {
        this.u = view;
    }

    @Nullable
    public View getEndView() {
        return this.v;
    }

    public void setEndView(@Nullable View view) {
        this.v = view;
    }

    @Nullable
    public ShapeAppearanceModel getStartShapeAppearanceModel() {
        return this.w;
    }

    public void setStartShapeAppearanceModel(@Nullable ShapeAppearanceModel shapeAppearanceModel) {
        this.w = shapeAppearanceModel;
    }

    @Nullable
    public ShapeAppearanceModel getEndShapeAppearanceModel() {
        return this.x;
    }

    public void setEndShapeAppearanceModel(@Nullable ShapeAppearanceModel shapeAppearanceModel) {
        this.x = shapeAppearanceModel;
    }

    public boolean isElevationShadowEnabled() {
        return this.C;
    }

    public void setElevationShadowEnabled(boolean z) {
        this.C = z;
    }

    public float getStartElevation() {
        return this.D;
    }

    public void setStartElevation(float f2) {
        this.D = f2;
    }

    public float getEndElevation() {
        return this.E;
    }

    public void setEndElevation(float f2) {
        this.E = f2;
    }

    @IdRes
    public int getDrawingViewId() {
        return this.k;
    }

    public void setDrawingViewId(@IdRes int i) {
        this.k = i;
    }

    @ColorInt
    public int getContainerColor() {
        return this.n;
    }

    public void setContainerColor(@ColorInt int i) {
        this.n = i;
    }

    @ColorInt
    public int getStartContainerColor() {
        return this.o;
    }

    public void setStartContainerColor(@ColorInt int i) {
        this.o = i;
    }

    @ColorInt
    public int getEndContainerColor() {
        return this.p;
    }

    public void setEndContainerColor(@ColorInt int i) {
        this.p = i;
    }

    public void setAllContainerColors(@ColorInt int i) {
        this.n = i;
        this.o = i;
        this.p = i;
    }

    @ColorInt
    public int getScrimColor() {
        return this.q;
    }

    public void setScrimColor(@ColorInt int i) {
        this.q = i;
    }

    public int getTransitionDirection() {
        return this.r;
    }

    public void setTransitionDirection(int i) {
        this.r = i;
    }

    public int getFadeMode() {
        return this.s;
    }

    public void setFadeMode(int i) {
        this.s = i;
    }

    public int getFitMode() {
        return this.t;
    }

    public void setFitMode(int i) {
        this.t = i;
    }

    @Nullable
    public ProgressThresholds getFadeProgressThresholds() {
        return this.y;
    }

    public void setFadeProgressThresholds(@Nullable ProgressThresholds progressThresholds) {
        this.y = progressThresholds;
    }

    @Nullable
    public ProgressThresholds getScaleProgressThresholds() {
        return this.z;
    }

    public void setScaleProgressThresholds(@Nullable ProgressThresholds progressThresholds) {
        this.z = progressThresholds;
    }

    @Nullable
    public ProgressThresholds getScaleMaskProgressThresholds() {
        return this.A;
    }

    public void setScaleMaskProgressThresholds(@Nullable ProgressThresholds progressThresholds) {
        this.A = progressThresholds;
    }

    @Nullable
    public ProgressThresholds getShapeMaskProgressThresholds() {
        return this.B;
    }

    public void setShapeMaskProgressThresholds(@Nullable ProgressThresholds progressThresholds) {
        this.B = progressThresholds;
    }

    public boolean isHoldAtEndEnabled() {
        return this.h;
    }

    public void setHoldAtEndEnabled(boolean z) {
        this.h = z;
    }

    public boolean isDrawDebugEnabled() {
        return this.g;
    }

    public void setDrawDebugEnabled(boolean z) {
        this.g = z;
    }

    @Override // android.transition.Transition
    public void setPathMotion(@Nullable PathMotion pathMotion) {
        super.setPathMotion(pathMotion);
        this.i = true;
    }

    @Override // android.transition.Transition
    @Nullable
    public String[] getTransitionProperties() {
        return b;
    }

    @Override // android.transition.Transition
    public void captureStartValues(@NonNull TransitionValues transitionValues) {
        a(transitionValues, this.u, this.l, this.w);
    }

    @Override // android.transition.Transition
    public void captureEndValues(@NonNull TransitionValues transitionValues) {
        a(transitionValues, this.v, this.m, this.x);
    }

    private static void a(@NonNull TransitionValues transitionValues, @Nullable View view, @IdRes int i, @Nullable ShapeAppearanceModel shapeAppearanceModel) {
        if (i != -1) {
            transitionValues.view = j.a(transitionValues.view, i);
        } else if (view != null) {
            transitionValues.view = view;
        } else if (transitionValues.view.getTag(R.id.mtrl_motion_snapshot_view) instanceof View) {
            transitionValues.view.setTag(R.id.mtrl_motion_snapshot_view, null);
            transitionValues.view = (View) transitionValues.view.getTag(R.id.mtrl_motion_snapshot_view);
        }
        View view2 = transitionValues.view;
        if (ViewCompat.isLaidOut(view2) || view2.getWidth() != 0 || view2.getHeight() != 0) {
            RectF a2 = view2.getParent() == null ? j.a(view2) : j.c(view2);
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

    @Override // android.transition.Transition
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
        if (this.k == view5.getId()) {
            view = (View) view5.getParent();
            view2 = view5;
        } else {
            view = j.b(view5, this.k);
            view2 = null;
        }
        RectF c2 = j.c(view);
        float f2 = -c2.left;
        float f3 = -c2.top;
        RectF a2 = a(view, view2, f2, f3);
        rectF.offset(f2, f3);
        rectF2.offset(f2, f3);
        boolean a3 = a(rectF, rectF2);
        if (!this.j) {
            a(view5.getContext(), a3);
        }
        final b bVar = new b(getPathMotion(), view3, rectF, shapeAppearanceModel, a(this.D, view3), view4, rectF2, shapeAppearanceModel2, a(this.E, view4), this.n, this.o, this.p, this.q, a3, this.C, b.a(this.s, a3), e.a(this.t, a3, rectF, rectF2), a(a3), this.g);
        bVar.setBounds(Math.round(a2.left), Math.round(a2.top), Math.round(a2.right), Math.round(a2.bottom));
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.transition.platform.MaterialContainerTransform.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                bVar.a(valueAnimator.getAnimatedFraction());
            }
        });
        addListener(new i() { // from class: com.google.android.material.transition.platform.MaterialContainerTransform.2
            @Override // com.google.android.material.transition.platform.i, android.transition.Transition.TransitionListener
            public void onTransitionStart(@NonNull Transition transition) {
                ViewUtils.getOverlay(view).add(bVar);
                view3.setAlpha(0.0f);
                view4.setAlpha(0.0f);
            }

            @Override // com.google.android.material.transition.platform.i, android.transition.Transition.TransitionListener
            public void onTransitionEnd(@NonNull Transition transition) {
                MaterialContainerTransform.this.removeListener(this);
                if (!MaterialContainerTransform.this.h) {
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
        if (!this.i) {
            j.b(this, context, R.attr.motionPath);
        }
    }

    private static float a(float f2, View view) {
        return f2 != -1.0f ? f2 : ViewCompat.getElevation(view);
    }

    private static RectF a(View view, @Nullable View view2, float f2, float f3) {
        if (view2 == null) {
            return new RectF(0.0f, 0.0f, view.getWidth(), view.getHeight());
        }
        RectF c2 = j.c(view2);
        c2.offset(f2, f3);
        return c2;
    }

    private boolean a(@NonNull RectF rectF, @NonNull RectF rectF2) {
        switch (this.r) {
            case 0:
                return j.a(rectF2) > j.a(rectF);
            case 1:
                return true;
            case 2:
                return false;
            default:
                throw new IllegalArgumentException("Invalid transition direction: " + this.r);
        }
    }

    private a a(boolean z) {
        PathMotion pathMotion = getPathMotion();
        if ((pathMotion instanceof ArcMotion) || (pathMotion instanceof MaterialArcMotion)) {
            return a(z, e, f);
        }
        return a(z, c, d);
    }

    private a a(boolean z, a aVar, a aVar2) {
        if (!z) {
            aVar = aVar2;
        }
        return new a((ProgressThresholds) j.a(this.y, aVar.a), (ProgressThresholds) j.a(this.z, aVar.b), (ProgressThresholds) j.a(this.A, aVar.c), (ProgressThresholds) j.a(this.B, aVar.d));
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
            j.a(canvas, getBounds(), this.w.left, this.w.top, this.H.a, this.G.a, new j.a() { // from class: com.google.android.material.transition.platform.MaterialContainerTransform.b.1
                @Override // com.google.android.material.transition.platform.j.a
                public void a(Canvas canvas2) {
                    b.this.a.draw(canvas2);
                }
            });
        }

        private void e(Canvas canvas) {
            a(canvas, this.k);
            j.a(canvas, getBounds(), this.y.left, this.y.top, this.H.b, this.G.b, new j.a() { // from class: com.google.android.material.transition.platform.MaterialContainerTransform.b.2
                @Override // com.google.android.material.transition.platform.j.a
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
