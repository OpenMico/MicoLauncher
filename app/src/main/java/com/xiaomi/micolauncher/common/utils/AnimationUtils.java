package com.xiaomi.micolauncher.common.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import androidx.annotation.NonNull;

/* loaded from: classes3.dex */
public class AnimationUtils {
    private AnimationUtils() {
    }

    public static void titleIconTouchDown(@NonNull View view) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 1.1f), ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 1.1f), ObjectAnimator.ofFloat(view, "alpha", 0.0f, 0.2f));
        animatorSet.setDuration(150L);
        animatorSet.start();
    }

    public static void titleIconTouchUp(@NonNull View view) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ObjectAnimator.ofFloat(view, "scaleX", 1.1f, 1.0f), ObjectAnimator.ofFloat(view, "scaleY", 1.1f, 1.0f), ObjectAnimator.ofFloat(view, "alpha", 0.2f, 0.0f));
        animatorSet.setDuration(150L);
        animatorSet.start();
    }

    public static void titleTouchDown(@NonNull View view) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "alpha", 1.0f, 0.5f);
        ofFloat.setDuration(150L);
        ofFloat.start();
    }

    public static void titleTouchUp(@NonNull View view) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "alpha", 0.5f, 1.0f);
        ofFloat.setDuration(150L);
        ofFloat.start();
    }
}
