package com.rd.animation.type;

import android.animation.IntEvaluator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import androidx.annotation.NonNull;
import com.rd.animation.controller.ValueController;
import com.rd.animation.data.type.SwapAnimationValue;

/* loaded from: classes2.dex */
public class SwapAnimation extends BaseAnimation<ValueAnimator> {
    private int a = -1;
    private int b = -1;
    private SwapAnimationValue c = new SwapAnimationValue();

    public SwapAnimation(@NonNull ValueController.UpdateListener updateListener) {
        super(updateListener);
    }

    @Override // com.rd.animation.type.BaseAnimation
    @NonNull
    public ValueAnimator createAnimator() {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(350L);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.rd.animation.type.SwapAnimation.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator2) {
                SwapAnimation.this.a(valueAnimator2);
            }
        });
        return valueAnimator;
    }

    @Override // com.rd.animation.type.BaseAnimation
    public SwapAnimation progress(float f) {
        if (this.animator != null) {
            long j = f * ((float) this.animationDuration);
            if (((ValueAnimator) this.animator).getValues() != null && ((ValueAnimator) this.animator).getValues().length > 0) {
                ((ValueAnimator) this.animator).setCurrentPlayTime(j);
            }
        }
        return this;
    }

    @NonNull
    public SwapAnimation with(int i, int i2) {
        if (this.animator != null && a(i, i2)) {
            this.a = i;
            this.b = i2;
            ((ValueAnimator) this.animator).setValues(a("ANIMATION_COORDINATE", i, i2), a("ANIMATION_COORDINATE_REVERSE", i2, i));
        }
        return this;
    }

    private PropertyValuesHolder a(String str, int i, int i2) {
        PropertyValuesHolder ofInt = PropertyValuesHolder.ofInt(str, i, i2);
        ofInt.setEvaluator(new IntEvaluator());
        return ofInt;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(@NonNull ValueAnimator valueAnimator) {
        int intValue = ((Integer) valueAnimator.getAnimatedValue("ANIMATION_COORDINATE")).intValue();
        int intValue2 = ((Integer) valueAnimator.getAnimatedValue("ANIMATION_COORDINATE_REVERSE")).intValue();
        this.c.setCoordinate(intValue);
        this.c.setCoordinateReverse(intValue2);
        if (this.listener != null) {
            this.listener.onValueUpdated(this.c);
        }
    }

    private boolean a(int i, int i2) {
        return (this.a == i && this.b == i2) ? false : true;
    }
}
