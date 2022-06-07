package androidx.transition;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.core.content.res.TypedArrayUtils;
import androidx.core.view.ViewCompat;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.xmlpull.v1.XmlPullParser;

/* loaded from: classes.dex */
public class Slide extends Visibility {
    private static final TimeInterpolator a = new DecelerateInterpolator();
    private static final TimeInterpolator i = new AccelerateInterpolator();
    private static final a l = new b() { // from class: androidx.transition.Slide.1
        @Override // androidx.transition.Slide.a
        public float a(ViewGroup viewGroup, View view) {
            return view.getTranslationX() - viewGroup.getWidth();
        }
    };
    private static final a m = new b() { // from class: androidx.transition.Slide.2
        @Override // androidx.transition.Slide.a
        public float a(ViewGroup viewGroup, View view) {
            boolean z = true;
            if (ViewCompat.getLayoutDirection(viewGroup) != 1) {
                z = false;
            }
            if (z) {
                return view.getTranslationX() + viewGroup.getWidth();
            }
            return view.getTranslationX() - viewGroup.getWidth();
        }
    };
    private static final a n = new c() { // from class: androidx.transition.Slide.3
        @Override // androidx.transition.Slide.a
        public float b(ViewGroup viewGroup, View view) {
            return view.getTranslationY() - viewGroup.getHeight();
        }
    };
    private static final a o = new b() { // from class: androidx.transition.Slide.4
        @Override // androidx.transition.Slide.a
        public float a(ViewGroup viewGroup, View view) {
            return view.getTranslationX() + viewGroup.getWidth();
        }
    };
    private static final a p = new b() { // from class: androidx.transition.Slide.5
        @Override // androidx.transition.Slide.a
        public float a(ViewGroup viewGroup, View view) {
            boolean z = true;
            if (ViewCompat.getLayoutDirection(viewGroup) != 1) {
                z = false;
            }
            if (z) {
                return view.getTranslationX() - viewGroup.getWidth();
            }
            return view.getTranslationX() + viewGroup.getWidth();
        }
    };
    private static final a q = new c() { // from class: androidx.transition.Slide.6
        @Override // androidx.transition.Slide.a
        public float b(ViewGroup viewGroup, View view) {
            return view.getTranslationY() + viewGroup.getHeight();
        }
    };
    private a j = q;
    private int k = 80;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    /* loaded from: classes.dex */
    public @interface GravityFlag {
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public interface a {
        float a(ViewGroup viewGroup, View view);

        float b(ViewGroup viewGroup, View view);
    }

    /* loaded from: classes.dex */
    private static abstract class b implements a {
        private b() {
        }

        @Override // androidx.transition.Slide.a
        public float b(ViewGroup viewGroup, View view) {
            return view.getTranslationY();
        }
    }

    /* loaded from: classes.dex */
    private static abstract class c implements a {
        private c() {
        }

        @Override // androidx.transition.Slide.a
        public float a(ViewGroup viewGroup, View view) {
            return view.getTranslationX();
        }
    }

    public Slide() {
        setSlideEdge(80);
    }

    public Slide(int i2) {
        setSlideEdge(i2);
    }

    @SuppressLint({"RestrictedApi"})
    public Slide(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, o.h);
        int namedInt = TypedArrayUtils.getNamedInt(obtainStyledAttributes, (XmlPullParser) attributeSet, "slideEdge", 0, 80);
        obtainStyledAttributes.recycle();
        setSlideEdge(namedInt);
    }

    private void b(TransitionValues transitionValues) {
        int[] iArr = new int[2];
        transitionValues.view.getLocationOnScreen(iArr);
        transitionValues.values.put("android:slide:screenPosition", iArr);
    }

    @Override // androidx.transition.Visibility, androidx.transition.Transition
    public void captureStartValues(@NonNull TransitionValues transitionValues) {
        super.captureStartValues(transitionValues);
        b(transitionValues);
    }

    @Override // androidx.transition.Visibility, androidx.transition.Transition
    public void captureEndValues(@NonNull TransitionValues transitionValues) {
        super.captureEndValues(transitionValues);
        b(transitionValues);
    }

    public void setSlideEdge(int i2) {
        if (i2 == 3) {
            this.j = l;
        } else if (i2 == 5) {
            this.j = o;
        } else if (i2 == 48) {
            this.j = n;
        } else if (i2 == 80) {
            this.j = q;
        } else if (i2 == 8388611) {
            this.j = m;
        } else if (i2 == 8388613) {
            this.j = p;
        } else {
            throw new IllegalArgumentException("Invalid slide direction");
        }
        this.k = i2;
        SidePropagation sidePropagation = new SidePropagation();
        sidePropagation.setSide(i2);
        setPropagation(sidePropagation);
    }

    public int getSlideEdge() {
        return this.k;
    }

    @Override // androidx.transition.Visibility
    public Animator onAppear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        if (transitionValues2 == null) {
            return null;
        }
        int[] iArr = (int[]) transitionValues2.values.get("android:slide:screenPosition");
        float translationX = view.getTranslationX();
        float translationY = view.getTranslationY();
        return s.a(view, transitionValues2, iArr[0], iArr[1], this.j.a(viewGroup, view), this.j.b(viewGroup, view), translationX, translationY, a, this);
    }

    @Override // androidx.transition.Visibility
    public Animator onDisappear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        if (transitionValues == null) {
            return null;
        }
        int[] iArr = (int[]) transitionValues.values.get("android:slide:screenPosition");
        return s.a(view, transitionValues, iArr[0], iArr[1], view.getTranslationX(), view.getTranslationY(), this.j.a(viewGroup, view), this.j.b(viewGroup, view), i, this);
    }
}
