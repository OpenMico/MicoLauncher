package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.core.content.res.TypedArrayUtils;
import androidx.transition.Transition;
import androidx.transition.a;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public abstract class Visibility extends Transition {
    public static final int MODE_IN = 1;
    public static final int MODE_OUT = 2;
    private static final String[] a = {"android:visibility:visibility", "android:visibility:parent"};
    private int i = 3;

    @SuppressLint({"UniqueConstants"})
    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    /* loaded from: classes.dex */
    public @interface Mode {
    }

    public Animator onAppear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        return null;
    }

    public Animator onDisappear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class b {
        boolean a;
        boolean b;
        int c;
        int d;
        ViewGroup e;
        ViewGroup f;

        b() {
        }
    }

    public Visibility() {
    }

    @SuppressLint({"RestrictedApi"})
    public Visibility(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, o.e);
        int namedInt = TypedArrayUtils.getNamedInt(obtainStyledAttributes, (XmlResourceParser) attributeSet, "transitionVisibilityMode", 0, 0);
        obtainStyledAttributes.recycle();
        if (namedInt != 0) {
            setMode(namedInt);
        }
    }

    public void setMode(int i) {
        if ((i & (-4)) == 0) {
            this.i = i;
            return;
        }
        throw new IllegalArgumentException("Only MODE_IN and MODE_OUT flags are allowed");
    }

    public int getMode() {
        return this.i;
    }

    @Override // androidx.transition.Transition
    @Nullable
    public String[] getTransitionProperties() {
        return a;
    }

    private void b(TransitionValues transitionValues) {
        transitionValues.values.put("android:visibility:visibility", Integer.valueOf(transitionValues.view.getVisibility()));
        transitionValues.values.put("android:visibility:parent", transitionValues.view.getParent());
        int[] iArr = new int[2];
        transitionValues.view.getLocationOnScreen(iArr);
        transitionValues.values.put("android:visibility:screenLocation", iArr);
    }

    @Override // androidx.transition.Transition
    public void captureStartValues(@NonNull TransitionValues transitionValues) {
        b(transitionValues);
    }

    @Override // androidx.transition.Transition
    public void captureEndValues(@NonNull TransitionValues transitionValues) {
        b(transitionValues);
    }

    public boolean isVisible(TransitionValues transitionValues) {
        if (transitionValues == null) {
            return false;
        }
        return ((Integer) transitionValues.values.get("android:visibility:visibility")).intValue() == 0 && ((View) transitionValues.values.get("android:visibility:parent")) != null;
    }

    private b a(TransitionValues transitionValues, TransitionValues transitionValues2) {
        b bVar = new b();
        bVar.a = false;
        bVar.b = false;
        if (transitionValues == null || !transitionValues.values.containsKey("android:visibility:visibility")) {
            bVar.c = -1;
            bVar.e = null;
        } else {
            bVar.c = ((Integer) transitionValues.values.get("android:visibility:visibility")).intValue();
            bVar.e = (ViewGroup) transitionValues.values.get("android:visibility:parent");
        }
        if (transitionValues2 == null || !transitionValues2.values.containsKey("android:visibility:visibility")) {
            bVar.d = -1;
            bVar.f = null;
        } else {
            bVar.d = ((Integer) transitionValues2.values.get("android:visibility:visibility")).intValue();
            bVar.f = (ViewGroup) transitionValues2.values.get("android:visibility:parent");
        }
        if (transitionValues == null || transitionValues2 == null) {
            if (transitionValues == null && bVar.d == 0) {
                bVar.b = true;
                bVar.a = true;
            } else if (transitionValues2 == null && bVar.c == 0) {
                bVar.b = false;
                bVar.a = true;
            }
        } else if (bVar.c == bVar.d && bVar.e == bVar.f) {
            return bVar;
        } else {
            if (bVar.c != bVar.d) {
                if (bVar.c == 0) {
                    bVar.b = false;
                    bVar.a = true;
                } else if (bVar.d == 0) {
                    bVar.b = true;
                    bVar.a = true;
                }
            } else if (bVar.f == null) {
                bVar.b = false;
                bVar.a = true;
            } else if (bVar.e == null) {
                bVar.b = true;
                bVar.a = true;
            }
        }
        return bVar;
    }

    @Override // androidx.transition.Transition
    @Nullable
    public Animator createAnimator(@NonNull ViewGroup viewGroup, @Nullable TransitionValues transitionValues, @Nullable TransitionValues transitionValues2) {
        b a2 = a(transitionValues, transitionValues2);
        if (!a2.a) {
            return null;
        }
        if (a2.e == null && a2.f == null) {
            return null;
        }
        if (a2.b) {
            return onAppear(viewGroup, transitionValues, a2.c, transitionValues2, a2.d);
        }
        return onDisappear(viewGroup, transitionValues, a2.c, transitionValues2, a2.d);
    }

    public Animator onAppear(ViewGroup viewGroup, TransitionValues transitionValues, int i, TransitionValues transitionValues2, int i2) {
        if ((this.i & 1) != 1 || transitionValues2 == null) {
            return null;
        }
        if (transitionValues == null) {
            View view = (View) transitionValues2.view.getParent();
            if (a(a(view, false), getTransitionValues(view, false)).a) {
                return null;
            }
        }
        return onAppear(viewGroup, transitionValues2.view, transitionValues, transitionValues2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x0086, code lost:
        if (r9.f != false) goto L_0x0088;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.animation.Animator onDisappear(final android.view.ViewGroup r10, androidx.transition.TransitionValues r11, int r12, androidx.transition.TransitionValues r13, int r14) {
        /*
            Method dump skipped, instructions count: 255
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.transition.Visibility.onDisappear(android.view.ViewGroup, androidx.transition.TransitionValues, int, androidx.transition.TransitionValues, int):android.animation.Animator");
    }

    @Override // androidx.transition.Transition
    public boolean isTransitionRequired(TransitionValues transitionValues, TransitionValues transitionValues2) {
        if (transitionValues == null && transitionValues2 == null) {
            return false;
        }
        if (transitionValues != null && transitionValues2 != null && transitionValues2.values.containsKey("android:visibility:visibility") != transitionValues.values.containsKey("android:visibility:visibility")) {
            return false;
        }
        b a2 = a(transitionValues, transitionValues2);
        if (a2.a) {
            return a2.c == 0 || a2.d == 0;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class a extends AnimatorListenerAdapter implements Transition.TransitionListener, a.AbstractC0030a {
        boolean a = false;
        private final View b;
        private final int c;
        private final ViewGroup d;
        private final boolean e;
        private boolean f;

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationRepeat(Animator animator) {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animator) {
        }

        @Override // androidx.transition.Transition.TransitionListener
        public void onTransitionCancel(@NonNull Transition transition) {
        }

        @Override // androidx.transition.Transition.TransitionListener
        public void onTransitionStart(@NonNull Transition transition) {
        }

        a(View view, int i, boolean z) {
            this.b = view;
            this.c = i;
            this.d = (ViewGroup) view.getParent();
            this.e = z;
            a(true);
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorPauseListener, androidx.transition.a.AbstractC0030a
        public void onAnimationPause(Animator animator) {
            if (!this.a) {
                ab.a(this.b, this.c);
            }
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorPauseListener, androidx.transition.a.AbstractC0030a
        public void onAnimationResume(Animator animator) {
            if (!this.a) {
                ab.a(this.b, 0);
            }
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animator) {
            this.a = true;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            a();
        }

        @Override // androidx.transition.Transition.TransitionListener
        public void onTransitionEnd(@NonNull Transition transition) {
            a();
            transition.removeListener(this);
        }

        @Override // androidx.transition.Transition.TransitionListener
        public void onTransitionPause(@NonNull Transition transition) {
            a(false);
        }

        @Override // androidx.transition.Transition.TransitionListener
        public void onTransitionResume(@NonNull Transition transition) {
            a(true);
        }

        private void a() {
            if (!this.a) {
                ab.a(this.b, this.c);
                ViewGroup viewGroup = this.d;
                if (viewGroup != null) {
                    viewGroup.invalidate();
                }
            }
            a(false);
        }

        private void a(boolean z) {
            ViewGroup viewGroup;
            if (this.e && this.f != z && (viewGroup = this.d) != null) {
                this.f = z;
                w.a(viewGroup, z);
            }
        }
    }
}
