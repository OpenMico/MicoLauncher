package com.rd.animation.type;

import android.animation.IntEvaluator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import androidx.annotation.NonNull;
import com.rd.animation.controller.ValueController;
import com.rd.animation.data.type.ScaleAnimationValue;

/* loaded from: classes2.dex */
public class ScaleAnimation extends ColorAnimation {
    public static final float DEFAULT_SCALE_FACTOR = 0.7f;
    public static final float MAX_SCALE_FACTOR = 1.0f;
    public static final float MIN_SCALE_FACTOR = 0.3f;
    int c;
    float d;
    private ScaleAnimationValue e = new ScaleAnimationValue();

    public ScaleAnimation(@NonNull ValueController.UpdateListener updateListener) {
        super(updateListener);
    }

    @Override // com.rd.animation.type.ColorAnimation, com.rd.animation.type.BaseAnimation
    @NonNull
    public ValueAnimator createAnimator() {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(350L);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.rd.animation.type.ScaleAnimation.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator2) {
                ScaleAnimation.this.a(valueAnimator2);
            }
        });
        return valueAnimator;
    }

    @NonNull
    public ScaleAnimation with(int i, int i2, int i3, float f) {
        if (this.animator != null && a(i, i2, i3, f)) {
            this.a = i;
            this.b = i2;
            this.c = i3;
            this.d = f;
            ((ValueAnimator) this.animator).setValues(a(false), a(true), createScalePropertyHolder(false), createScalePropertyHolder(true));
        }
        return this;
    }

    public void a(@NonNull ValueAnimator valueAnimator) {
        int intValue = ((Integer) valueAnimator.getAnimatedValue("ANIMATION_COLOR")).intValue();
        int intValue2 = ((Integer) valueAnimator.getAnimatedValue("ANIMATION_COLOR_REVERSE")).intValue();
        int intValue3 = ((Integer) valueAnimator.getAnimatedValue("ANIMATION_SCALE")).intValue();
        int intValue4 = ((Integer) valueAnimator.getAnimatedValue("ANIMATION_SCALE_REVERSE")).intValue();
        this.e.setColor(intValue);
        this.e.setColorReverse(intValue2);
        this.e.setRadius(intValue3);
        this.e.setRadiusReverse(intValue4);
        if (this.listener != null) {
            this.listener.onValueUpdated(this.e);
        }
    }

    @NonNull
    protected PropertyValuesHolder createScalePropertyHolder(boolean z) {
        String str;
        int i;
        int i2;
        if (z) {
            str = "ANIMATION_SCALE_REVERSE";
            i2 = this.c;
            i = (int) (i2 * this.d);
        } else {
            str = "ANIMATION_SCALE";
            i = this.c;
            i2 = (int) (i * this.d);
        }
        PropertyValuesHolder ofInt = PropertyValuesHolder.ofInt(str, i2, i);
        ofInt.setEvaluator(new IntEvaluator());
        return ofInt;
    }

    private boolean a(int i, int i2, int i3, float f) {
        return (this.a == i && this.b == i2 && this.c == i3 && this.d == f) ? false : true;
    }
}
