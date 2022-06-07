package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.TypedArrayUtils;
import androidx.core.view.ViewCompat;
import java.util.Map;

/* loaded from: classes.dex */
public class ChangeBounds extends Transition {
    private static final String[] a = {"android:changeBounds:bounds", "android:changeBounds:clip", "android:changeBounds:parent", "android:changeBounds:windowX", "android:changeBounds:windowY"};
    private static final Property<Drawable, PointF> i = new Property<Drawable, PointF>(PointF.class, "boundsOrigin") { // from class: androidx.transition.ChangeBounds.1
        private Rect a = new Rect();

        /* renamed from: a */
        public void set(Drawable drawable, PointF pointF) {
            drawable.copyBounds(this.a);
            this.a.offsetTo(Math.round(pointF.x), Math.round(pointF.y));
            drawable.setBounds(this.a);
        }

        /* renamed from: a */
        public PointF get(Drawable drawable) {
            drawable.copyBounds(this.a);
            return new PointF(this.a.left, this.a.top);
        }
    };
    private static final Property<a, PointF> j = new Property<a, PointF>(PointF.class, "topLeft") { // from class: androidx.transition.ChangeBounds.3
        /* renamed from: a */
        public PointF get(a aVar) {
            return null;
        }

        /* renamed from: a */
        public void set(a aVar, PointF pointF) {
            aVar.a(pointF);
        }
    };
    private static final Property<a, PointF> k = new Property<a, PointF>(PointF.class, "bottomRight") { // from class: androidx.transition.ChangeBounds.4
        /* renamed from: a */
        public PointF get(a aVar) {
            return null;
        }

        /* renamed from: a */
        public void set(a aVar, PointF pointF) {
            aVar.b(pointF);
        }
    };
    private static final Property<View, PointF> l = new Property<View, PointF>(PointF.class, "bottomRight") { // from class: androidx.transition.ChangeBounds.5
        /* renamed from: a */
        public PointF get(View view) {
            return null;
        }

        /* renamed from: a */
        public void set(View view, PointF pointF) {
            ab.a(view, view.getLeft(), view.getTop(), Math.round(pointF.x), Math.round(pointF.y));
        }
    };
    private static final Property<View, PointF> m = new Property<View, PointF>(PointF.class, "topLeft") { // from class: androidx.transition.ChangeBounds.6
        /* renamed from: a */
        public PointF get(View view) {
            return null;
        }

        /* renamed from: a */
        public void set(View view, PointF pointF) {
            ab.a(view, Math.round(pointF.x), Math.round(pointF.y), view.getRight(), view.getBottom());
        }
    };
    private static final Property<View, PointF> n = new Property<View, PointF>(PointF.class, "position") { // from class: androidx.transition.ChangeBounds.7
        /* renamed from: a */
        public PointF get(View view) {
            return null;
        }

        /* renamed from: a */
        public void set(View view, PointF pointF) {
            int round = Math.round(pointF.x);
            int round2 = Math.round(pointF.y);
            ab.a(view, round, round2, view.getWidth() + round, view.getHeight() + round2);
        }
    };
    private static n r = new n();
    private int[] o;
    private boolean p;
    private boolean q;

    public ChangeBounds() {
        this.o = new int[2];
        this.p = false;
        this.q = false;
    }

    @SuppressLint({"RestrictedApi"})
    public ChangeBounds(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.o = new int[2];
        this.p = false;
        this.q = false;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, o.d);
        boolean namedBoolean = TypedArrayUtils.getNamedBoolean(obtainStyledAttributes, (XmlResourceParser) attributeSet, "resizeClip", 0, false);
        obtainStyledAttributes.recycle();
        setResizeClip(namedBoolean);
    }

    @Override // androidx.transition.Transition
    @Nullable
    public String[] getTransitionProperties() {
        return a;
    }

    public void setResizeClip(boolean z) {
        this.p = z;
    }

    public boolean getResizeClip() {
        return this.p;
    }

    private void b(TransitionValues transitionValues) {
        View view = transitionValues.view;
        if (ViewCompat.isLaidOut(view) || view.getWidth() != 0 || view.getHeight() != 0) {
            transitionValues.values.put("android:changeBounds:bounds", new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom()));
            transitionValues.values.put("android:changeBounds:parent", transitionValues.view.getParent());
            if (this.q) {
                transitionValues.view.getLocationInWindow(this.o);
                transitionValues.values.put("android:changeBounds:windowX", Integer.valueOf(this.o[0]));
                transitionValues.values.put("android:changeBounds:windowY", Integer.valueOf(this.o[1]));
            }
            if (this.p) {
                transitionValues.values.put("android:changeBounds:clip", ViewCompat.getClipBounds(view));
            }
        }
    }

    @Override // androidx.transition.Transition
    public void captureStartValues(@NonNull TransitionValues transitionValues) {
        b(transitionValues);
    }

    @Override // androidx.transition.Transition
    public void captureEndValues(@NonNull TransitionValues transitionValues) {
        b(transitionValues);
    }

    private boolean a(View view, View view2) {
        if (!this.q) {
            return true;
        }
        TransitionValues a2 = a(view, true);
        return a2 == null ? view == view2 : view2 == a2.view;
    }

    @Override // androidx.transition.Transition
    @Nullable
    public Animator createAnimator(@NonNull final ViewGroup viewGroup, @Nullable TransitionValues transitionValues, @Nullable TransitionValues transitionValues2) {
        int i2;
        final View view;
        Animator animator;
        int i3;
        Rect rect;
        ObjectAnimator objectAnimator;
        if (transitionValues == null || transitionValues2 == null) {
            return null;
        }
        Map<String, Object> map = transitionValues.values;
        Map<String, Object> map2 = transitionValues2.values;
        ViewGroup viewGroup2 = (ViewGroup) map.get("android:changeBounds:parent");
        ViewGroup viewGroup3 = (ViewGroup) map2.get("android:changeBounds:parent");
        if (viewGroup2 == null || viewGroup3 == null) {
            return null;
        }
        final View view2 = transitionValues2.view;
        if (a(viewGroup2, viewGroup3)) {
            Rect rect2 = (Rect) transitionValues.values.get("android:changeBounds:bounds");
            Rect rect3 = (Rect) transitionValues2.values.get("android:changeBounds:bounds");
            int i4 = rect2.left;
            final int i5 = rect3.left;
            int i6 = rect2.top;
            final int i7 = rect3.top;
            int i8 = rect2.right;
            final int i9 = rect3.right;
            int i10 = rect2.bottom;
            final int i11 = rect3.bottom;
            int i12 = i8 - i4;
            int i13 = i10 - i6;
            int i14 = i9 - i5;
            int i15 = i11 - i7;
            Rect rect4 = (Rect) transitionValues.values.get("android:changeBounds:clip");
            final Rect rect5 = (Rect) transitionValues2.values.get("android:changeBounds:clip");
            if ((i12 == 0 || i13 == 0) && (i14 == 0 || i15 == 0)) {
                i2 = 0;
            } else {
                i2 = (i4 == i5 && i6 == i7) ? 0 : 1;
                if (!(i8 == i9 && i10 == i11)) {
                    i2++;
                }
            }
            if ((rect4 != null && !rect4.equals(rect5)) || (rect4 == null && rect5 != null)) {
                i2++;
            }
            if (i2 <= 0) {
                return null;
            }
            if (!this.p) {
                view = view2;
                ab.a(view, i4, i6, i8, i10);
                if (i2 == 2) {
                    if (i12 == i14 && i13 == i15) {
                        animator = k.a(view, n, getPathMotion().getPath(i4, i6, i5, i7));
                    } else {
                        final a aVar = new a(view);
                        ObjectAnimator a2 = k.a(aVar, j, getPathMotion().getPath(i4, i6, i5, i7));
                        ObjectAnimator a3 = k.a(aVar, k, getPathMotion().getPath(i8, i10, i9, i11));
                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.playTogether(a2, a3);
                        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: androidx.transition.ChangeBounds.8
                            private a mViewBounds;

                            {
                                this.mViewBounds = aVar;
                            }
                        });
                        animator = animatorSet;
                    }
                } else if (i4 == i5 && i6 == i7) {
                    animator = k.a(view, l, getPathMotion().getPath(i8, i10, i9, i11));
                } else {
                    animator = k.a(view, m, getPathMotion().getPath(i4, i6, i5, i7));
                }
            } else {
                view = view2;
                ab.a(view, i4, i6, Math.max(i12, i14) + i4, Math.max(i13, i15) + i6);
                ObjectAnimator a4 = (i4 == i5 && i6 == i7) ? null : k.a(view, n, getPathMotion().getPath(i4, i6, i5, i7));
                if (rect4 == null) {
                    i3 = 0;
                    rect = new Rect(0, 0, i12, i13);
                } else {
                    i3 = 0;
                    rect = rect4;
                }
                Rect rect6 = rect5 == null ? new Rect(i3, i3, i14, i15) : rect5;
                if (!rect.equals(rect6)) {
                    ViewCompat.setClipBounds(view, rect);
                    n nVar = r;
                    Object[] objArr = new Object[2];
                    objArr[i3] = rect;
                    objArr[1] = rect6;
                    objectAnimator = ObjectAnimator.ofObject(view, "clipBounds", nVar, objArr);
                    objectAnimator.addListener(new AnimatorListenerAdapter() { // from class: androidx.transition.ChangeBounds.9
                        private boolean h;

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationCancel(Animator animator2) {
                            this.h = true;
                        }

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animator2) {
                            if (!this.h) {
                                ViewCompat.setClipBounds(view, rect5);
                                ab.a(view, i5, i7, i9, i11);
                            }
                        }
                    });
                } else {
                    objectAnimator = null;
                }
                animator = p.a(a4, objectAnimator);
            }
            if (view.getParent() instanceof ViewGroup) {
                final ViewGroup viewGroup4 = (ViewGroup) view.getParent();
                w.a(viewGroup4, true);
                addListener(new TransitionListenerAdapter() { // from class: androidx.transition.ChangeBounds.10
                    boolean a = false;

                    @Override // androidx.transition.TransitionListenerAdapter, androidx.transition.Transition.TransitionListener
                    public void onTransitionCancel(@NonNull Transition transition) {
                        w.a(viewGroup4, false);
                        this.a = true;
                    }

                    @Override // androidx.transition.TransitionListenerAdapter, androidx.transition.Transition.TransitionListener
                    public void onTransitionEnd(@NonNull Transition transition) {
                        if (!this.a) {
                            w.a(viewGroup4, false);
                        }
                        transition.removeListener(this);
                    }

                    @Override // androidx.transition.TransitionListenerAdapter, androidx.transition.Transition.TransitionListener
                    public void onTransitionPause(@NonNull Transition transition) {
                        w.a(viewGroup4, false);
                    }

                    @Override // androidx.transition.TransitionListenerAdapter, androidx.transition.Transition.TransitionListener
                    public void onTransitionResume(@NonNull Transition transition) {
                        w.a(viewGroup4, true);
                    }
                });
            }
            return animator;
        }
        int intValue = ((Integer) transitionValues.values.get("android:changeBounds:windowX")).intValue();
        int intValue2 = ((Integer) transitionValues.values.get("android:changeBounds:windowY")).intValue();
        int intValue3 = ((Integer) transitionValues2.values.get("android:changeBounds:windowX")).intValue();
        int intValue4 = ((Integer) transitionValues2.values.get("android:changeBounds:windowY")).intValue();
        if (intValue == intValue3 && intValue2 == intValue4) {
            return null;
        }
        viewGroup.getLocationInWindow(this.o);
        Bitmap createBitmap = Bitmap.createBitmap(view2.getWidth(), view2.getHeight(), Bitmap.Config.ARGB_8888);
        view2.draw(new Canvas(createBitmap));
        final BitmapDrawable bitmapDrawable = new BitmapDrawable(createBitmap);
        final float c = ab.c(view2);
        ab.a(view2, 0.0f);
        ab.a(viewGroup).a(bitmapDrawable);
        PathMotion pathMotion = getPathMotion();
        int[] iArr = this.o;
        ObjectAnimator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(bitmapDrawable, m.a(i, pathMotion.getPath(intValue - iArr[0], intValue2 - iArr[1], intValue3 - iArr[0], intValue4 - iArr[1])));
        ofPropertyValuesHolder.addListener(new AnimatorListenerAdapter() { // from class: androidx.transition.ChangeBounds.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator2) {
                ab.a(viewGroup).b(bitmapDrawable);
                ab.a(view2, c);
            }
        });
        return ofPropertyValuesHolder;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class a {
        private int a;
        private int b;
        private int c;
        private int d;
        private View e;
        private int f;
        private int g;

        a(View view) {
            this.e = view;
        }

        void a(PointF pointF) {
            this.a = Math.round(pointF.x);
            this.b = Math.round(pointF.y);
            this.f++;
            if (this.f == this.g) {
                a();
            }
        }

        void b(PointF pointF) {
            this.c = Math.round(pointF.x);
            this.d = Math.round(pointF.y);
            this.g++;
            if (this.f == this.g) {
                a();
            }
        }

        private void a() {
            ab.a(this.e, this.a, this.b, this.c, this.d);
            this.f = 0;
            this.g = 0;
        }
    }
}
