package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.content.res.TypedArrayUtils;
import androidx.core.view.ViewCompat;
import org.xmlpull.v1.XmlPullParser;

/* loaded from: classes.dex */
public class ChangeTransform extends Transition {
    private static final String[] i = {"android:changeTransform:matrix", "android:changeTransform:transforms", "android:changeTransform:parentMatrix"};
    private static final Property<b, float[]> j = new Property<b, float[]>(float[].class, "nonTranslations") { // from class: androidx.transition.ChangeTransform.1
        /* renamed from: a */
        public float[] get(b bVar) {
            return null;
        }

        /* renamed from: a */
        public void set(b bVar, float[] fArr) {
            bVar.a(fArr);
        }
    };
    private static final Property<b, PointF> k = new Property<b, PointF>(PointF.class, "translations") { // from class: androidx.transition.ChangeTransform.2
        /* renamed from: a */
        public PointF get(b bVar) {
            return null;
        }

        /* renamed from: a */
        public void set(b bVar, PointF pointF) {
            bVar.a(pointF);
        }
    };
    private static final boolean l;
    boolean a;
    private boolean m;
    private Matrix n;

    static {
        l = Build.VERSION.SDK_INT >= 21;
    }

    public ChangeTransform() {
        this.a = true;
        this.m = true;
        this.n = new Matrix();
    }

    @SuppressLint({"RestrictedApi"})
    public ChangeTransform(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.a = true;
        this.m = true;
        this.n = new Matrix();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, o.g);
        XmlPullParser xmlPullParser = (XmlPullParser) attributeSet;
        this.a = TypedArrayUtils.getNamedBoolean(obtainStyledAttributes, xmlPullParser, "reparentWithOverlay", 1, true);
        this.m = TypedArrayUtils.getNamedBoolean(obtainStyledAttributes, xmlPullParser, "reparent", 0, true);
        obtainStyledAttributes.recycle();
    }

    public boolean getReparentWithOverlay() {
        return this.a;
    }

    public void setReparentWithOverlay(boolean z) {
        this.a = z;
    }

    public boolean getReparent() {
        return this.m;
    }

    public void setReparent(boolean z) {
        this.m = z;
    }

    @Override // androidx.transition.Transition
    public String[] getTransitionProperties() {
        return i;
    }

    private void b(TransitionValues transitionValues) {
        View view = transitionValues.view;
        if (view.getVisibility() != 8) {
            transitionValues.values.put("android:changeTransform:parent", view.getParent());
            transitionValues.values.put("android:changeTransform:transforms", new c(view));
            Matrix matrix = view.getMatrix();
            transitionValues.values.put("android:changeTransform:matrix", (matrix == null || matrix.isIdentity()) ? null : new Matrix(matrix));
            if (this.m) {
                Matrix matrix2 = new Matrix();
                ViewGroup viewGroup = (ViewGroup) view.getParent();
                ab.a(viewGroup, matrix2);
                matrix2.preTranslate(-viewGroup.getScrollX(), -viewGroup.getScrollY());
                transitionValues.values.put("android:changeTransform:parentMatrix", matrix2);
                transitionValues.values.put("android:changeTransform:intermediateMatrix", view.getTag(R.id.transition_transform));
                transitionValues.values.put("android:changeTransform:intermediateParentMatrix", view.getTag(R.id.parent_matrix));
            }
        }
    }

    @Override // androidx.transition.Transition
    public void captureStartValues(@NonNull TransitionValues transitionValues) {
        b(transitionValues);
        if (!l) {
            ((ViewGroup) transitionValues.view.getParent()).startViewTransition(transitionValues.view);
        }
    }

    @Override // androidx.transition.Transition
    public void captureEndValues(@NonNull TransitionValues transitionValues) {
        b(transitionValues);
    }

    @Override // androidx.transition.Transition
    public Animator createAnimator(@NonNull ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
        if (transitionValues == null || transitionValues2 == null || !transitionValues.values.containsKey("android:changeTransform:parent") || !transitionValues2.values.containsKey("android:changeTransform:parent")) {
            return null;
        }
        ViewGroup viewGroup2 = (ViewGroup) transitionValues.values.get("android:changeTransform:parent");
        boolean z = this.m && !a(viewGroup2, (ViewGroup) transitionValues2.values.get("android:changeTransform:parent"));
        Matrix matrix = (Matrix) transitionValues.values.get("android:changeTransform:intermediateMatrix");
        if (matrix != null) {
            transitionValues.values.put("android:changeTransform:matrix", matrix);
        }
        Matrix matrix2 = (Matrix) transitionValues.values.get("android:changeTransform:intermediateParentMatrix");
        if (matrix2 != null) {
            transitionValues.values.put("android:changeTransform:parentMatrix", matrix2);
        }
        if (z) {
            a(transitionValues, transitionValues2);
        }
        ObjectAnimator a2 = a(transitionValues, transitionValues2, z);
        if (z && a2 != null && this.a) {
            a(viewGroup, transitionValues, transitionValues2);
        } else if (!l) {
            viewGroup2.endViewTransition(transitionValues.view);
        }
        return a2;
    }

    private ObjectAnimator a(TransitionValues transitionValues, TransitionValues transitionValues2, final boolean z) {
        Matrix matrix = (Matrix) transitionValues.values.get("android:changeTransform:matrix");
        Matrix matrix2 = (Matrix) transitionValues2.values.get("android:changeTransform:matrix");
        if (matrix == null) {
            matrix = j.a;
        }
        final Matrix matrix3 = matrix2 == null ? j.a : matrix2;
        if (matrix.equals(matrix3)) {
            return null;
        }
        final c cVar = (c) transitionValues2.values.get("android:changeTransform:transforms");
        final View view = transitionValues2.view;
        a(view);
        float[] fArr = new float[9];
        matrix.getValues(fArr);
        float[] fArr2 = new float[9];
        matrix3.getValues(fArr2);
        final b bVar = new b(view, fArr);
        ObjectAnimator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(bVar, PropertyValuesHolder.ofObject(j, new c(new float[9]), fArr, fArr2), m.a(k, getPathMotion().getPath(fArr[2], fArr[5], fArr2[2], fArr2[5])));
        AnimatorListenerAdapter animatorListenerAdapter = new AnimatorListenerAdapter() { // from class: androidx.transition.ChangeTransform.3
            private boolean g;
            private Matrix h = new Matrix();

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                this.g = true;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                if (!this.g) {
                    if (!z || !ChangeTransform.this.a) {
                        view.setTag(R.id.transition_transform, null);
                        view.setTag(R.id.parent_matrix, null);
                    } else {
                        a(matrix3);
                    }
                }
                ab.c(view, null);
                cVar.a(view);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorPauseListener
            public void onAnimationPause(Animator animator) {
                a(bVar.a());
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorPauseListener
            public void onAnimationResume(Animator animator) {
                ChangeTransform.a(view);
            }

            private void a(Matrix matrix4) {
                this.h.set(matrix4);
                view.setTag(R.id.transition_transform, this.h);
                cVar.a(view);
            }
        };
        ofPropertyValuesHolder.addListener(animatorListenerAdapter);
        a.a(ofPropertyValuesHolder, animatorListenerAdapter);
        return ofPropertyValuesHolder;
    }

    private boolean a(ViewGroup viewGroup, ViewGroup viewGroup2) {
        if (!b((View) viewGroup) || !b((View) viewGroup2)) {
            return viewGroup == viewGroup2;
        }
        TransitionValues a2 = a((View) viewGroup, true);
        return a2 != null && viewGroup2 == a2.view;
    }

    private void a(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
        View view = transitionValues2.view;
        Matrix matrix = new Matrix((Matrix) transitionValues2.values.get("android:changeTransform:parentMatrix"));
        ab.b(viewGroup, matrix);
        d a2 = h.a(view, viewGroup, matrix);
        if (a2 != null) {
            a2.a((ViewGroup) transitionValues.values.get("android:changeTransform:parent"), transitionValues.view);
            Transition transition = this;
            while (transition.e != null) {
                transition = transition.e;
            }
            transition.addListener(new a(view, a2));
            if (l) {
                if (transitionValues.view != transitionValues2.view) {
                    ab.a(transitionValues.view, 0.0f);
                }
                ab.a(view, 1.0f);
            }
        }
    }

    private void a(TransitionValues transitionValues, TransitionValues transitionValues2) {
        Matrix matrix = (Matrix) transitionValues2.values.get("android:changeTransform:parentMatrix");
        transitionValues2.view.setTag(R.id.parent_matrix, matrix);
        Matrix matrix2 = this.n;
        matrix2.reset();
        matrix.invert(matrix2);
        Matrix matrix3 = (Matrix) transitionValues.values.get("android:changeTransform:matrix");
        if (matrix3 == null) {
            matrix3 = new Matrix();
            transitionValues.values.put("android:changeTransform:matrix", matrix3);
        }
        matrix3.postConcat((Matrix) transitionValues.values.get("android:changeTransform:parentMatrix"));
        matrix3.postConcat(matrix2);
    }

    static void a(View view) {
        a(view, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f);
    }

    static void a(View view, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
        view.setTranslationX(f);
        view.setTranslationY(f2);
        ViewCompat.setTranslationZ(view, f3);
        view.setScaleX(f4);
        view.setScaleY(f5);
        view.setRotationX(f6);
        view.setRotationY(f7);
        view.setRotation(f8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class c {
        final float a;
        final float b;
        final float c;
        final float d;
        final float e;
        final float f;
        final float g;
        final float h;

        c(View view) {
            this.a = view.getTranslationX();
            this.b = view.getTranslationY();
            this.c = ViewCompat.getTranslationZ(view);
            this.d = view.getScaleX();
            this.e = view.getScaleY();
            this.f = view.getRotationX();
            this.g = view.getRotationY();
            this.h = view.getRotation();
        }

        public void a(View view) {
            ChangeTransform.a(view, this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h);
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof c)) {
                return false;
            }
            c cVar = (c) obj;
            return cVar.a == this.a && cVar.b == this.b && cVar.c == this.c && cVar.d == this.d && cVar.e == this.e && cVar.f == this.f && cVar.g == this.g && cVar.h == this.h;
        }

        public int hashCode() {
            float f = this.a;
            int i = 0;
            int floatToIntBits = (f != 0.0f ? Float.floatToIntBits(f) : 0) * 31;
            float f2 = this.b;
            int floatToIntBits2 = (floatToIntBits + (f2 != 0.0f ? Float.floatToIntBits(f2) : 0)) * 31;
            float f3 = this.c;
            int floatToIntBits3 = (floatToIntBits2 + (f3 != 0.0f ? Float.floatToIntBits(f3) : 0)) * 31;
            float f4 = this.d;
            int floatToIntBits4 = (floatToIntBits3 + (f4 != 0.0f ? Float.floatToIntBits(f4) : 0)) * 31;
            float f5 = this.e;
            int floatToIntBits5 = (floatToIntBits4 + (f5 != 0.0f ? Float.floatToIntBits(f5) : 0)) * 31;
            float f6 = this.f;
            int floatToIntBits6 = (floatToIntBits5 + (f6 != 0.0f ? Float.floatToIntBits(f6) : 0)) * 31;
            float f7 = this.g;
            int floatToIntBits7 = (floatToIntBits6 + (f7 != 0.0f ? Float.floatToIntBits(f7) : 0)) * 31;
            float f8 = this.h;
            if (f8 != 0.0f) {
                i = Float.floatToIntBits(f8);
            }
            return floatToIntBits7 + i;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class a extends TransitionListenerAdapter {
        private View a;
        private d b;

        a(View view, d dVar) {
            this.a = view;
            this.b = dVar;
        }

        @Override // androidx.transition.TransitionListenerAdapter, androidx.transition.Transition.TransitionListener
        public void onTransitionEnd(@NonNull Transition transition) {
            transition.removeListener(this);
            h.a(this.a);
            this.a.setTag(R.id.transition_transform, null);
            this.a.setTag(R.id.parent_matrix, null);
        }

        @Override // androidx.transition.TransitionListenerAdapter, androidx.transition.Transition.TransitionListener
        public void onTransitionPause(@NonNull Transition transition) {
            this.b.setVisibility(4);
        }

        @Override // androidx.transition.TransitionListenerAdapter, androidx.transition.Transition.TransitionListener
        public void onTransitionResume(@NonNull Transition transition) {
            this.b.setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class b {
        private final Matrix a = new Matrix();
        private final View b;
        private final float[] c;
        private float d;
        private float e;

        b(View view, float[] fArr) {
            this.b = view;
            this.c = (float[]) fArr.clone();
            float[] fArr2 = this.c;
            this.d = fArr2[2];
            this.e = fArr2[5];
            b();
        }

        void a(float[] fArr) {
            System.arraycopy(fArr, 0, this.c, 0, fArr.length);
            b();
        }

        void a(PointF pointF) {
            this.d = pointF.x;
            this.e = pointF.y;
            b();
        }

        private void b() {
            float[] fArr = this.c;
            fArr[2] = this.d;
            fArr[5] = this.e;
            this.a.setValues(fArr);
            ab.c(this.b, this.a);
        }

        Matrix a() {
            return this.a;
        }
    }
}
