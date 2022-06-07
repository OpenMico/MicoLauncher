package com.rd.animation.type;

import android.animation.IntEvaluator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import androidx.annotation.NonNull;
import com.rd.animation.controller.ValueController;
import com.rd.animation.data.type.FillAnimationValue;

/* loaded from: classes2.dex */
public class FillAnimation extends ColorAnimation {
    public static final int DEFAULT_STROKE_DP = 1;
    private FillAnimationValue c = new FillAnimationValue();
    private int d;
    private int e;

    public FillAnimation(@NonNull ValueController.UpdateListener updateListener) {
        super(updateListener);
    }

    @Override // com.rd.animation.type.ColorAnimation, com.rd.animation.type.BaseAnimation
    @NonNull
    public ValueAnimator createAnimator() {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(350L);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.rd.animation.type.FillAnimation.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator2) {
                FillAnimation.this.a(valueAnimator2);
            }
        });
        return valueAnimator;
    }

    @NonNull
    public FillAnimation with(int i, int i2, int i3, int i4) {
        if (this.animator != null && a(i, i2, i3, i4)) {
            this.a = i;
            this.b = i2;
            this.d = i3;
            this.e = i4;
            ((ValueAnimator) this.animator).setValues(a(false), a(true), b(false), b(true), c(false), c(true));
        }
        return this;
    }

    @NonNull
    private PropertyValuesHolder b(boolean z) {
        String str;
        int i;
        int i2;
        if (z) {
            str = "ANIMATION_RADIUS_REVERSE";
            i2 = this.d;
            i = i2 / 2;
        } else {
            str = "ANIMATION_RADIUS";
            i = this.d;
            i2 = i / 2;
        }
        PropertyValuesHolder ofInt = PropertyValuesHolder.ofInt(str, i, i2);
        ofInt.setEvaluator(new IntEvaluator());
        return ofInt;
    }

    @NonNull
    private PropertyValuesHolder c(boolean z) {
        String str;
        int i;
        int i2;
        if (z) {
            str = "ANIMATION_STROKE_REVERSE";
            i2 = this.d;
            i = 0;
        } else {
            str = "ANIMATION_STROKE";
            i = this.d;
            i2 = 0;
        }
        PropertyValuesHolder ofInt = PropertyValuesHolder.ofInt(str, i2, i);
        ofInt.setEvaluator(new IntEvaluator());
        return ofInt;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(@NonNull ValueAnimator valueAnimator) {
        int intValue = ((Integer) valueAnimator.getAnimatedValue("ANIMATION_COLOR")).intValue();
        int intValue2 = ((Integer) valueAnimator.getAnimatedValue("ANIMATION_COLOR_REVERSE")).intValue();
        int intValue3 = ((Integer) valueAnimator.getAnimatedValue("ANIMATION_RADIUS")).intValue();
        int intValue4 = ((Integer) valueAnimator.getAnimatedValue("ANIMATION_RADIUS_REVERSE")).intValue();
        int intValue5 = ((Integer) valueAnimator.getAnimatedValue("ANIMATION_STROKE")).intValue();
        int intValue6 = ((Integer) valueAnimator.getAnimatedValue("ANIMATION_STROKE_REVERSE")).intValue();
        this.c.setColor(intValue);
        this.c.setColorReverse(intValue2);
        this.c.setRadius(intValue3);
        this.c.setRadiusReverse(intValue4);
        this.c.setStroke(intValue5);
        this.c.setStrokeReverse(intValue6);
        if (this.listener != null) {
            this.listener.onValueUpdated(this.c);
        }
    }

    private boolean a(int i, int i2, int i3, int i4) {
        return (this.a == i && this.b == i2 && this.d == i3 && this.e == i4) ? false : true;
    }
}
