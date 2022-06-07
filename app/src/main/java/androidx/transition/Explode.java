package androidx.transition;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import androidx.annotation.NonNull;

/* loaded from: classes.dex */
public class Explode extends Visibility {
    private static final TimeInterpolator a = new DecelerateInterpolator();
    private static final TimeInterpolator i = new AccelerateInterpolator();
    private int[] j;

    public Explode() {
        this.j = new int[2];
        setPropagation(new CircularPropagation());
    }

    public Explode(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.j = new int[2];
        setPropagation(new CircularPropagation());
    }

    private void b(TransitionValues transitionValues) {
        View view = transitionValues.view;
        view.getLocationOnScreen(this.j);
        int[] iArr = this.j;
        int i2 = iArr[0];
        int i3 = iArr[1];
        transitionValues.values.put("android:explode:screenBounds", new Rect(i2, i3, view.getWidth() + i2, view.getHeight() + i3));
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

    @Override // androidx.transition.Visibility
    public Animator onAppear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        if (transitionValues2 == null) {
            return null;
        }
        Rect rect = (Rect) transitionValues2.values.get("android:explode:screenBounds");
        float translationX = view.getTranslationX();
        float translationY = view.getTranslationY();
        a(viewGroup, rect, this.j);
        int[] iArr = this.j;
        return s.a(view, transitionValues2, rect.left, rect.top, translationX + iArr[0], translationY + iArr[1], translationX, translationY, a, this);
    }

    @Override // androidx.transition.Visibility
    public Animator onDisappear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        float f;
        float f2;
        if (transitionValues == null) {
            return null;
        }
        Rect rect = (Rect) transitionValues.values.get("android:explode:screenBounds");
        int i2 = rect.left;
        int i3 = rect.top;
        float translationX = view.getTranslationX();
        float translationY = view.getTranslationY();
        int[] iArr = (int[]) transitionValues.view.getTag(R.id.transition_position);
        if (iArr != null) {
            f2 = (iArr[0] - rect.left) + translationX;
            f = (iArr[1] - rect.top) + translationY;
            rect.offsetTo(iArr[0], iArr[1]);
        } else {
            f2 = translationX;
            f = translationY;
        }
        a(viewGroup, rect, this.j);
        int[] iArr2 = this.j;
        return s.a(view, transitionValues, i2, i3, translationX, translationY, f2 + iArr2[0], f + iArr2[1], i, this);
    }

    private void a(View view, Rect rect, int[] iArr) {
        int i2;
        int i3;
        view.getLocationOnScreen(this.j);
        int[] iArr2 = this.j;
        int i4 = iArr2[0];
        int i5 = iArr2[1];
        Rect epicenter = getEpicenter();
        if (epicenter == null) {
            i3 = (view.getWidth() / 2) + i4 + Math.round(view.getTranslationX());
            i2 = (view.getHeight() / 2) + i5 + Math.round(view.getTranslationY());
        } else {
            int centerX = epicenter.centerX();
            i2 = epicenter.centerY();
            i3 = centerX;
        }
        float centerX2 = rect.centerX() - i3;
        float centerY = rect.centerY() - i2;
        if (centerX2 == 0.0f && centerY == 0.0f) {
            centerX2 = ((float) (Math.random() * 2.0d)) - 1.0f;
            centerY = ((float) (Math.random() * 2.0d)) - 1.0f;
        }
        float a2 = a(centerX2, centerY);
        float a3 = a(view, i3 - i4, i2 - i5);
        iArr[0] = Math.round((centerX2 / a2) * a3);
        iArr[1] = Math.round(a3 * (centerY / a2));
    }

    private static float a(View view, int i2, int i3) {
        return a(Math.max(i2, view.getWidth() - i2), Math.max(i3, view.getHeight() - i3));
    }

    private static float a(float f, float f2) {
        return (float) Math.sqrt((f * f) + (f2 * f2));
    }
}
