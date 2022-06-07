package com.rd.animation.type;

import android.animation.IntEvaluator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import androidx.annotation.NonNull;
import com.rd.animation.controller.ValueController;
import com.rd.animation.data.type.SlideAnimationValue;

/* loaded from: classes2.dex */
public class SlideAnimation extends BaseAnimation<ValueAnimator> {
    private int b = -1;
    private int c = -1;
    private SlideAnimationValue a = new SlideAnimationValue();

    public SlideAnimation(@NonNull ValueController.UpdateListener updateListener) {
        super(updateListener);
    }

    @Override // com.rd.animation.type.BaseAnimation
    @NonNull
    public ValueAnimator createAnimator() {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(350L);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.rd.animation.type.SlideAnimation.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator2) {
                SlideAnimation.this.a(valueAnimator2);
            }
        });
        return valueAnimator;
    }

    @Override // com.rd.animation.type.BaseAnimation
    public SlideAnimation progress(float f) {
        if (this.animator != null) {
            long j = f * ((float) this.animationDuration);
            if (((ValueAnimator) this.animator).getValues() != null && ((ValueAnimator) this.animator).getValues().length > 0) {
                ((ValueAnimator) this.animator).setCurrentPlayTime(j);
            }
        }
        return this;
    }

    @NonNull
    public SlideAnimation with(int i, int i2) {
        if (this.animator != null && a(i, i2)) {
            this.b = i;
            this.c = i2;
            ((ValueAnimator) this.animator).setValues(a());
        }
        return this;
    }

    private PropertyValuesHolder a() {
        PropertyValuesHolder ofInt = PropertyValuesHolder.ofInt("ANIMATION_COORDINATE", this.b, this.c);
        ofInt.setEvaluator(new IntEvaluator());
        return ofInt;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(@NonNull ValueAnimator valueAnimator) {
        this.a.setCoordinate(((Integer) valueAnimator.getAnimatedValue("ANIMATION_COORDINATE")).intValue());
        if (this.listener != null) {
            this.listener.onValueUpdated(this.a);
        }
    }

    private boolean a(int i, int i2) {
        return (this.b == i && this.c == i2) ? false : true;
    }
}
