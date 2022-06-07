package com.xiaomi.micolauncher.module.skill.manager;

import android.animation.ValueAnimator;
import android.widget.ImageView;

/* loaded from: classes3.dex */
public class SkillAnimationUtils {
    public static void startClickAnimation(ImageView imageView, int i) {
        a(imageView, i, 0L);
        a(imageView, i, 680L);
    }

    private static void a(final ImageView imageView, final int i, long j) {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat.setDuration(600L);
        ofFloat.setStartDelay(j);
        ofFloat.start();
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.xiaomi.micolauncher.module.skill.manager.-$$Lambda$SkillAnimationUtils$BJdYpRZ_Y5Tb1yRPp3F3TzahvIs
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                SkillAnimationUtils.a(imageView, i, valueAnimator);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(ImageView imageView, int i, ValueAnimator valueAnimator) {
        imageView.setImageAlpha((int) ((1.0f - ((Float) valueAnimator.getAnimatedValue()).floatValue()) * 255.0f));
        imageView.getLayoutParams().width = (int) (i * ((Float) valueAnimator.getAnimatedValue()).floatValue());
        imageView.requestLayout();
    }
}
