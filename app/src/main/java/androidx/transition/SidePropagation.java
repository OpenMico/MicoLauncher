package androidx.transition;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.view.ViewCompat;

/* loaded from: classes.dex */
public class SidePropagation extends VisibilityPropagation {
    private float a = 3.0f;
    private int b = 80;

    public void setSide(int i) {
        this.b = i;
    }

    public void setPropagationSpeed(float f) {
        if (f != 0.0f) {
            this.a = f;
            return;
        }
        throw new IllegalArgumentException("propagationSpeed may not be 0");
    }

    @Override // androidx.transition.TransitionPropagation
    public long getStartDelay(ViewGroup viewGroup, Transition transition, TransitionValues transitionValues, TransitionValues transitionValues2) {
        int i;
        int i2;
        int i3;
        TransitionValues transitionValues3 = transitionValues;
        if (transitionValues3 == null && transitionValues2 == null) {
            return 0L;
        }
        Rect epicenter = transition.getEpicenter();
        if (transitionValues2 == null || getViewVisibility(transitionValues3) == 0) {
            i = -1;
        } else {
            transitionValues3 = transitionValues2;
            i = 1;
        }
        int viewX = getViewX(transitionValues3);
        int viewY = getViewY(transitionValues3);
        int[] iArr = new int[2];
        viewGroup.getLocationOnScreen(iArr);
        int round = iArr[0] + Math.round(viewGroup.getTranslationX());
        int round2 = iArr[1] + Math.round(viewGroup.getTranslationY());
        int width = round + viewGroup.getWidth();
        int height = round2 + viewGroup.getHeight();
        if (epicenter != null) {
            i3 = epicenter.centerX();
            i2 = epicenter.centerY();
        } else {
            i3 = (round + width) / 2;
            i2 = (round2 + height) / 2;
        }
        float a = a(viewGroup, viewX, viewY, i3, i2, round, round2, width, height) / a(viewGroup);
        long duration = transition.getDuration();
        if (duration < 0) {
            duration = 300;
        }
        return Math.round((((float) (duration * i)) / this.a) * a);
    }

    private int a(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        int i9 = this.b;
        boolean z = true;
        if (i9 == 8388611) {
            if (ViewCompat.getLayoutDirection(view) != 1) {
                z = false;
            }
            i9 = z ? 5 : 3;
        } else if (i9 == 8388613) {
            if (ViewCompat.getLayoutDirection(view) != 1) {
                z = false;
            }
            i9 = z ? 3 : 5;
        }
        if (i9 == 3) {
            return (i7 - i) + Math.abs(i4 - i2);
        }
        if (i9 == 5) {
            return (i - i5) + Math.abs(i4 - i2);
        }
        if (i9 == 48) {
            return (i8 - i2) + Math.abs(i3 - i);
        }
        if (i9 != 80) {
            return 0;
        }
        return (i2 - i6) + Math.abs(i3 - i);
    }

    private int a(ViewGroup viewGroup) {
        int i = this.b;
        if (i == 3 || i == 5 || i == 8388611 || i == 8388613) {
            return viewGroup.getWidth();
        }
        return viewGroup.getHeight();
    }
}
