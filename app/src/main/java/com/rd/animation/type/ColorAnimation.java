package com.rd.animation.type;

import android.animation.ArgbEvaluator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.rd.animation.controller.ValueController;
import com.rd.animation.data.type.ColorAnimationValue;

/* loaded from: classes2.dex */
public class ColorAnimation extends BaseAnimation<ValueAnimator> {
    public static final String DEFAULT_SELECTED_COLOR = "#ffffff";
    public static final String DEFAULT_UNSELECTED_COLOR = "#33ffffff";
    int a;
    int b;
    private ColorAnimationValue c = new ColorAnimationValue();

    public ColorAnimation(@Nullable ValueController.UpdateListener updateListener) {
        super(updateListener);
    }

    @Override // com.rd.animation.type.BaseAnimation
    @NonNull
    public ValueAnimator createAnimator() {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(350L);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.rd.animation.type.ColorAnimation.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator2) {
                ColorAnimation.this.a(valueAnimator2);
            }
        });
        return valueAnimator;
    }

    @Override // com.rd.animation.type.BaseAnimation
    public ColorAnimation progress(float f) {
        if (this.animator != null) {
            long j = f * ((float) this.animationDuration);
            if (((ValueAnimator) this.animator).getValues() != null && ((ValueAnimator) this.animator).getValues().length > 0) {
                ((ValueAnimator) this.animator).setCurrentPlayTime(j);
            }
        }
        return this;
    }

    @NonNull
    public ColorAnimation with(int i, int i2) {
        if (this.animator != null && a(i, i2)) {
            this.a = i;
            this.b = i2;
            ((ValueAnimator) this.animator).setValues(a(false), a(true));
        }
        return this;
    }

    PropertyValuesHolder a(boolean z) {
        String str;
        int i;
        int i2;
        if (z) {
            str = "ANIMATION_COLOR_REVERSE";
            i2 = this.b;
            i = this.a;
        } else {
            str = "ANIMATION_COLOR";
            i2 = this.a;
            i = this.b;
        }
        PropertyValuesHolder ofInt = PropertyValuesHolder.ofInt(str, i2, i);
        ofInt.setEvaluator(new ArgbEvaluator());
        return ofInt;
    }

    private boolean a(int i, int i2) {
        return (this.a == i && this.b == i2) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(@NonNull ValueAnimator valueAnimator) {
        int intValue = ((Integer) valueAnimator.getAnimatedValue("ANIMATION_COLOR")).intValue();
        int intValue2 = ((Integer) valueAnimator.getAnimatedValue("ANIMATION_COLOR_REVERSE")).intValue();
        this.c.setColor(intValue);
        this.c.setColorReverse(intValue2);
        if (this.listener != null) {
            this.listener.onValueUpdated(this.c);
        }
    }
}
