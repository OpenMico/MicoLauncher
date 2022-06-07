package com.google.android.material.transformation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Pair;
import android.util.Property;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.animation.AnimatorSetCompat;
import com.google.android.material.animation.ArgbEvaluatorCompat;
import com.google.android.material.animation.ChildrenAlphaProperty;
import com.google.android.material.animation.DrawableAlphaProperty;
import com.google.android.material.animation.MotionSpec;
import com.google.android.material.animation.MotionTiming;
import com.google.android.material.animation.Positioning;
import com.google.android.material.circularreveal.CircularRevealCompat;
import com.google.android.material.circularreveal.CircularRevealHelper;
import com.google.android.material.circularreveal.CircularRevealWidget;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.math.MathUtils;
import com.xiaomi.idm.service.iot.LightService;
import java.util.ArrayList;
import java.util.List;

@Deprecated
/* loaded from: classes2.dex */
public abstract class FabTransformationBehavior extends ExpandableTransformationBehavior {
    private final Rect a;
    private final RectF b;
    private final RectF c;
    private final int[] d;
    private float e;
    private float f;

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: classes2.dex */
    public static class FabTransformationSpec {
        public Positioning positioning;
        @Nullable
        public MotionSpec timings;
    }

    protected abstract FabTransformationSpec onCreateMotionSpec(Context context, boolean z);

    public FabTransformationBehavior() {
        this.a = new Rect();
        this.b = new RectF();
        this.c = new RectF();
        this.d = new int[2];
    }

    public FabTransformationBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.a = new Rect();
        this.b = new RectF();
        this.c = new RectF();
        this.d = new int[2];
    }

    @Override // com.google.android.material.transformation.ExpandableBehavior, androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    @CallSuper
    public boolean layoutDependsOn(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View view, @NonNull View view2) {
        if (view.getVisibility() == 8) {
            throw new IllegalStateException("This behavior cannot be attached to a GONE view. Set the view to INVISIBLE instead.");
        } else if (!(view2 instanceof FloatingActionButton)) {
            return false;
        } else {
            int expandedComponentIdHint = ((FloatingActionButton) view2).getExpandedComponentIdHint();
            return expandedComponentIdHint == 0 || expandedComponentIdHint == view.getId();
        }
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    @CallSuper
    public void onAttachedToLayoutParams(@NonNull CoordinatorLayout.LayoutParams layoutParams) {
        if (layoutParams.dodgeInsetEdges == 0) {
            layoutParams.dodgeInsetEdges = 80;
        }
    }

    @Override // com.google.android.material.transformation.ExpandableTransformationBehavior
    @NonNull
    protected AnimatorSet onCreateExpandedStateChangeAnimation(@NonNull final View view, @NonNull final View view2, final boolean z, boolean z2) {
        FabTransformationSpec onCreateMotionSpec = onCreateMotionSpec(view2.getContext(), z);
        if (z) {
            this.e = view.getTranslationX();
            this.f = view.getTranslationY();
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        if (Build.VERSION.SDK_INT >= 21) {
            a(view, view2, z, z2, onCreateMotionSpec, arrayList, arrayList2);
        }
        RectF rectF = this.b;
        a(view, view2, z, z2, onCreateMotionSpec, arrayList, arrayList2, rectF);
        float width = rectF.width();
        float height = rectF.height();
        a(view, view2, z, onCreateMotionSpec, arrayList);
        b(view, view2, z, z2, onCreateMotionSpec, arrayList, arrayList2);
        a(view, view2, z, z2, onCreateMotionSpec, width, height, arrayList, arrayList2);
        c(view, view2, z, z2, onCreateMotionSpec, arrayList, arrayList2);
        d(view, view2, z, z2, onCreateMotionSpec, arrayList, arrayList2);
        AnimatorSet animatorSet = new AnimatorSet();
        AnimatorSetCompat.playTogether(animatorSet, arrayList);
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.transformation.FabTransformationBehavior.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                if (z) {
                    view2.setVisibility(0);
                    view.setAlpha(0.0f);
                    view.setVisibility(4);
                }
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                if (!z) {
                    view2.setVisibility(4);
                    view.setAlpha(1.0f);
                    view.setVisibility(0);
                }
            }
        });
        int size = arrayList2.size();
        for (int i = 0; i < size; i++) {
            animatorSet.addListener(arrayList2.get(i));
        }
        return animatorSet;
    }

    @TargetApi(21)
    private void a(View view, @NonNull View view2, boolean z, boolean z2, @NonNull FabTransformationSpec fabTransformationSpec, @NonNull List<Animator> list, List<Animator.AnimatorListener> list2) {
        ObjectAnimator objectAnimator;
        float elevation = ViewCompat.getElevation(view2) - ViewCompat.getElevation(view);
        if (z) {
            if (!z2) {
                view2.setTranslationZ(-elevation);
            }
            objectAnimator = ObjectAnimator.ofFloat(view2, View.TRANSLATION_Z, 0.0f);
        } else {
            objectAnimator = ObjectAnimator.ofFloat(view2, View.TRANSLATION_Z, -elevation);
        }
        fabTransformationSpec.timings.getTiming("elevation").apply(objectAnimator);
        list.add(objectAnimator);
    }

    private void a(@NonNull View view, @NonNull View view2, boolean z, @NonNull FabTransformationSpec fabTransformationSpec, @NonNull List<Animator> list) {
        float a = a(view, view2, fabTransformationSpec.positioning);
        float b = b(view, view2, fabTransformationSpec.positioning);
        Pair<MotionTiming, MotionTiming> a2 = a(a, b, z, fabTransformationSpec);
        MotionTiming motionTiming = (MotionTiming) a2.first;
        MotionTiming motionTiming2 = (MotionTiming) a2.second;
        Property property = View.TRANSLATION_X;
        float[] fArr = new float[1];
        if (!z) {
            a = this.e;
        }
        fArr[0] = a;
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, property, fArr);
        Property property2 = View.TRANSLATION_Y;
        float[] fArr2 = new float[1];
        if (!z) {
            b = this.f;
        }
        fArr2[0] = b;
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view, property2, fArr2);
        motionTiming.apply(ofFloat);
        motionTiming2.apply(ofFloat2);
        list.add(ofFloat);
        list.add(ofFloat2);
    }

    private void a(@NonNull View view, @NonNull View view2, boolean z, boolean z2, @NonNull FabTransformationSpec fabTransformationSpec, @NonNull List<Animator> list, List<Animator.AnimatorListener> list2, @NonNull RectF rectF) {
        ObjectAnimator objectAnimator;
        ObjectAnimator objectAnimator2;
        float a = a(view, view2, fabTransformationSpec.positioning);
        float b = b(view, view2, fabTransformationSpec.positioning);
        Pair<MotionTiming, MotionTiming> a2 = a(a, b, z, fabTransformationSpec);
        MotionTiming motionTiming = (MotionTiming) a2.first;
        MotionTiming motionTiming2 = (MotionTiming) a2.second;
        if (z) {
            if (!z2) {
                view2.setTranslationX(-a);
                view2.setTranslationY(-b);
            }
            objectAnimator2 = ObjectAnimator.ofFloat(view2, View.TRANSLATION_X, 0.0f);
            objectAnimator = ObjectAnimator.ofFloat(view2, View.TRANSLATION_Y, 0.0f);
            a(view2, fabTransformationSpec, motionTiming, motionTiming2, -a, -b, 0.0f, 0.0f, rectF);
        } else {
            objectAnimator2 = ObjectAnimator.ofFloat(view2, View.TRANSLATION_X, -a);
            objectAnimator = ObjectAnimator.ofFloat(view2, View.TRANSLATION_Y, -b);
        }
        motionTiming.apply(objectAnimator2);
        motionTiming2.apply(objectAnimator);
        list.add(objectAnimator2);
        list.add(objectAnimator);
    }

    private void b(View view, final View view2, boolean z, boolean z2, @NonNull FabTransformationSpec fabTransformationSpec, @NonNull List<Animator> list, @NonNull List<Animator.AnimatorListener> list2) {
        ObjectAnimator objectAnimator;
        if ((view2 instanceof CircularRevealWidget) && (view instanceof ImageView)) {
            final CircularRevealWidget circularRevealWidget = (CircularRevealWidget) view2;
            final Drawable drawable = ((ImageView) view).getDrawable();
            if (drawable != null) {
                drawable.mutate();
                if (z) {
                    if (!z2) {
                        drawable.setAlpha(255);
                    }
                    objectAnimator = ObjectAnimator.ofInt(drawable, DrawableAlphaProperty.DRAWABLE_ALPHA_COMPAT, 0);
                } else {
                    objectAnimator = ObjectAnimator.ofInt(drawable, DrawableAlphaProperty.DRAWABLE_ALPHA_COMPAT, 255);
                }
                objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.transformation.FabTransformationBehavior.2
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        view2.invalidate();
                    }
                });
                fabTransformationSpec.timings.getTiming("iconFade").apply(objectAnimator);
                list.add(objectAnimator);
                list2.add(new AnimatorListenerAdapter() { // from class: com.google.android.material.transformation.FabTransformationBehavior.3
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationStart(Animator animator) {
                        circularRevealWidget.setCircularRevealOverlayDrawable(drawable);
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        circularRevealWidget.setCircularRevealOverlayDrawable(null);
                    }
                });
            }
        }
    }

    private void a(@NonNull View view, View view2, boolean z, boolean z2, @NonNull FabTransformationSpec fabTransformationSpec, float f, float f2, @NonNull List<Animator> list, @NonNull List<Animator.AnimatorListener> list2) {
        Animator animator;
        if (view2 instanceof CircularRevealWidget) {
            final CircularRevealWidget circularRevealWidget = (CircularRevealWidget) view2;
            float c = c(view, view2, fabTransformationSpec.positioning);
            float d = d(view, view2, fabTransformationSpec.positioning);
            ((FloatingActionButton) view).getContentRect(this.a);
            float width = this.a.width() / 2.0f;
            MotionTiming timing = fabTransformationSpec.timings.getTiming("expansion");
            if (z) {
                if (!z2) {
                    circularRevealWidget.setRevealInfo(new CircularRevealWidget.RevealInfo(c, d, width));
                }
                if (z2) {
                    width = circularRevealWidget.getRevealInfo().radius;
                }
                animator = CircularRevealCompat.createCircularReveal(circularRevealWidget, c, d, MathUtils.distanceToFurthestCorner(c, d, 0.0f, 0.0f, f, f2));
                animator.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.transformation.FabTransformationBehavior.4
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator2) {
                        CircularRevealWidget.RevealInfo revealInfo = circularRevealWidget.getRevealInfo();
                        revealInfo.radius = Float.MAX_VALUE;
                        circularRevealWidget.setRevealInfo(revealInfo);
                    }
                });
                a(view2, timing.getDelay(), (int) c, (int) d, width, list);
            } else {
                float f3 = circularRevealWidget.getRevealInfo().radius;
                Animator createCircularReveal = CircularRevealCompat.createCircularReveal(circularRevealWidget, c, d, width);
                int i = (int) c;
                int i2 = (int) d;
                a(view2, timing.getDelay(), i, i2, f3, list);
                a(view2, timing.getDelay(), timing.getDuration(), fabTransformationSpec.timings.getTotalDuration(), i, i2, width, list);
                animator = createCircularReveal;
            }
            timing.apply(animator);
            list.add(animator);
            list2.add(CircularRevealCompat.createCircularRevealListener(circularRevealWidget));
        }
    }

    private void c(@NonNull View view, View view2, boolean z, boolean z2, @NonNull FabTransformationSpec fabTransformationSpec, @NonNull List<Animator> list, List<Animator.AnimatorListener> list2) {
        ObjectAnimator objectAnimator;
        if (view2 instanceof CircularRevealWidget) {
            CircularRevealWidget circularRevealWidget = (CircularRevealWidget) view2;
            int c = c(view);
            int i = 16777215 & c;
            if (z) {
                if (!z2) {
                    circularRevealWidget.setCircularRevealScrimColor(c);
                }
                objectAnimator = ObjectAnimator.ofInt(circularRevealWidget, CircularRevealWidget.CircularRevealScrimColorProperty.CIRCULAR_REVEAL_SCRIM_COLOR, i);
            } else {
                objectAnimator = ObjectAnimator.ofInt(circularRevealWidget, CircularRevealWidget.CircularRevealScrimColorProperty.CIRCULAR_REVEAL_SCRIM_COLOR, c);
            }
            objectAnimator.setEvaluator(ArgbEvaluatorCompat.getInstance());
            fabTransformationSpec.timings.getTiming(LightService.LightPropertyCommand.COLOR).apply(objectAnimator);
            list.add(objectAnimator);
        }
    }

    private void d(View view, View view2, boolean z, boolean z2, @NonNull FabTransformationSpec fabTransformationSpec, @NonNull List<Animator> list, List<Animator.AnimatorListener> list2) {
        ViewGroup a;
        ObjectAnimator objectAnimator;
        if (view2 instanceof ViewGroup) {
            if ((!(view2 instanceof CircularRevealWidget) || CircularRevealHelper.STRATEGY != 0) && (a = a(view2)) != null) {
                if (z) {
                    if (!z2) {
                        ChildrenAlphaProperty.CHILDREN_ALPHA.set(a, Float.valueOf(0.0f));
                    }
                    objectAnimator = ObjectAnimator.ofFloat(a, ChildrenAlphaProperty.CHILDREN_ALPHA, 1.0f);
                } else {
                    objectAnimator = ObjectAnimator.ofFloat(a, ChildrenAlphaProperty.CHILDREN_ALPHA, 0.0f);
                }
                fabTransformationSpec.timings.getTiming("contentFade").apply(objectAnimator);
                list.add(objectAnimator);
            }
        }
    }

    @NonNull
    private Pair<MotionTiming, MotionTiming> a(float f, float f2, boolean z, @NonNull FabTransformationSpec fabTransformationSpec) {
        MotionTiming motionTiming;
        MotionTiming motionTiming2;
        int i;
        if (f == 0.0f || f2 == 0.0f) {
            motionTiming2 = fabTransformationSpec.timings.getTiming("translationXLinear");
            motionTiming = fabTransformationSpec.timings.getTiming("translationYLinear");
        } else if ((!z || f2 >= 0.0f) && (z || i <= 0)) {
            motionTiming2 = fabTransformationSpec.timings.getTiming("translationXCurveDownwards");
            motionTiming = fabTransformationSpec.timings.getTiming("translationYCurveDownwards");
        } else {
            motionTiming2 = fabTransformationSpec.timings.getTiming("translationXCurveUpwards");
            motionTiming = fabTransformationSpec.timings.getTiming("translationYCurveUpwards");
        }
        return new Pair<>(motionTiming2, motionTiming);
    }

    private float a(@NonNull View view, @NonNull View view2, @NonNull Positioning positioning) {
        float f;
        RectF rectF = this.b;
        RectF rectF2 = this.c;
        b(view, rectF);
        a(view2, rectF2);
        int i = positioning.gravity & 7;
        if (i == 1) {
            f = rectF2.centerX() - rectF.centerX();
        } else if (i != 3) {
            f = i != 5 ? 0.0f : rectF2.right - rectF.right;
        } else {
            f = rectF2.left - rectF.left;
        }
        return f + positioning.xAdjustment;
    }

    private float b(@NonNull View view, @NonNull View view2, @NonNull Positioning positioning) {
        float f;
        RectF rectF = this.b;
        RectF rectF2 = this.c;
        b(view, rectF);
        a(view2, rectF2);
        int i = positioning.gravity & 112;
        if (i == 16) {
            f = rectF2.centerY() - rectF.centerY();
        } else if (i != 48) {
            f = i != 80 ? 0.0f : rectF2.bottom - rectF.bottom;
        } else {
            f = rectF2.top - rectF.top;
        }
        return f + positioning.yAdjustment;
    }

    private void a(@NonNull View view, RectF rectF) {
        rectF.set(0.0f, 0.0f, view.getWidth(), view.getHeight());
        int[] iArr = this.d;
        view.getLocationInWindow(iArr);
        rectF.offsetTo(iArr[0], iArr[1]);
        rectF.offset((int) (-view.getTranslationX()), (int) (-view.getTranslationY()));
    }

    private void b(@NonNull View view, @NonNull RectF rectF) {
        a(view, rectF);
        rectF.offset(this.e, this.f);
    }

    private float c(@NonNull View view, @NonNull View view2, @NonNull Positioning positioning) {
        RectF rectF = this.b;
        RectF rectF2 = this.c;
        b(view, rectF);
        a(view2, rectF2);
        rectF2.offset(-a(view, view2, positioning), 0.0f);
        return rectF.centerX() - rectF2.left;
    }

    private float d(@NonNull View view, @NonNull View view2, @NonNull Positioning positioning) {
        RectF rectF = this.b;
        RectF rectF2 = this.c;
        b(view, rectF);
        a(view2, rectF2);
        rectF2.offset(0.0f, -b(view, view2, positioning));
        return rectF.centerY() - rectF2.top;
    }

    private void a(@NonNull View view, @NonNull FabTransformationSpec fabTransformationSpec, @NonNull MotionTiming motionTiming, @NonNull MotionTiming motionTiming2, float f, float f2, float f3, float f4, @NonNull RectF rectF) {
        float a = a(fabTransformationSpec, motionTiming, f, f3);
        float a2 = a(fabTransformationSpec, motionTiming2, f2, f4);
        Rect rect = this.a;
        view.getWindowVisibleDisplayFrame(rect);
        RectF rectF2 = this.b;
        rectF2.set(rect);
        RectF rectF3 = this.c;
        a(view, rectF3);
        rectF3.offset(a, a2);
        rectF3.intersect(rectF2);
        rectF.set(rectF3);
    }

    private float a(@NonNull FabTransformationSpec fabTransformationSpec, @NonNull MotionTiming motionTiming, float f, float f2) {
        long delay = motionTiming.getDelay();
        long duration = motionTiming.getDuration();
        MotionTiming timing = fabTransformationSpec.timings.getTiming("expansion");
        return AnimationUtils.lerp(f, f2, motionTiming.getInterpolator().getInterpolation(((float) (((timing.getDelay() + timing.getDuration()) + 17) - delay)) / ((float) duration)));
    }

    @Nullable
    private ViewGroup a(@NonNull View view) {
        View findViewById = view.findViewById(R.id.mtrl_child_content_container);
        if (findViewById != null) {
            return b(findViewById);
        }
        if ((view instanceof TransformationChildLayout) || (view instanceof TransformationChildCard)) {
            return b(((ViewGroup) view).getChildAt(0));
        }
        return b(view);
    }

    @Nullable
    private ViewGroup b(View view) {
        if (view instanceof ViewGroup) {
            return (ViewGroup) view;
        }
        return null;
    }

    private int c(@NonNull View view) {
        ColorStateList backgroundTintList = ViewCompat.getBackgroundTintList(view);
        if (backgroundTintList != null) {
            return backgroundTintList.getColorForState(view.getDrawableState(), backgroundTintList.getDefaultColor());
        }
        return 0;
    }

    private void a(View view, long j, int i, int i2, float f, @NonNull List<Animator> list) {
        if (Build.VERSION.SDK_INT >= 21 && j > 0) {
            Animator createCircularReveal = ViewAnimationUtils.createCircularReveal(view, i, i2, f, f);
            createCircularReveal.setStartDelay(0L);
            createCircularReveal.setDuration(j);
            list.add(createCircularReveal);
        }
    }

    private void a(View view, long j, long j2, long j3, int i, int i2, float f, @NonNull List<Animator> list) {
        if (Build.VERSION.SDK_INT >= 21) {
            long j4 = j + j2;
            if (j4 < j3) {
                Animator createCircularReveal = ViewAnimationUtils.createCircularReveal(view, i, i2, f, f);
                createCircularReveal.setStartDelay(j4);
                createCircularReveal.setDuration(j3 - j4);
                list.add(createCircularReveal);
            }
        }
    }
}
