package androidx.transition;

import android.view.View;

/* loaded from: classes.dex */
public abstract class VisibilityPropagation extends TransitionPropagation {
    private static final String[] a = {"android:visibilityPropagation:visibility", "android:visibilityPropagation:center"};

    @Override // androidx.transition.TransitionPropagation
    public void captureValues(TransitionValues transitionValues) {
        View view = transitionValues.view;
        Integer num = (Integer) transitionValues.values.get("android:visibility:visibility");
        if (num == null) {
            num = Integer.valueOf(view.getVisibility());
        }
        transitionValues.values.put("android:visibilityPropagation:visibility", num);
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        iArr[0] = iArr[0] + Math.round(view.getTranslationX());
        iArr[0] = iArr[0] + (view.getWidth() / 2);
        iArr[1] = iArr[1] + Math.round(view.getTranslationY());
        iArr[1] = iArr[1] + (view.getHeight() / 2);
        transitionValues.values.put("android:visibilityPropagation:center", iArr);
    }

    @Override // androidx.transition.TransitionPropagation
    public String[] getPropagationProperties() {
        return a;
    }

    public int getViewVisibility(TransitionValues transitionValues) {
        Integer num;
        if (transitionValues == null || (num = (Integer) transitionValues.values.get("android:visibilityPropagation:visibility")) == null) {
            return 8;
        }
        return num.intValue();
    }

    public int getViewX(TransitionValues transitionValues) {
        return a(transitionValues, 0);
    }

    public int getViewY(TransitionValues transitionValues) {
        return a(transitionValues, 1);
    }

    private static int a(TransitionValues transitionValues, int i) {
        int[] iArr;
        if (transitionValues == null || (iArr = (int[]) transitionValues.values.get("android:visibilityPropagation:center")) == null) {
            return -1;
        }
        return iArr[i];
    }
}
