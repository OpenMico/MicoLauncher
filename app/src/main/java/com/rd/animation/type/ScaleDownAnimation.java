package com.rd.animation.type;

import android.animation.IntEvaluator;
import android.animation.PropertyValuesHolder;
import androidx.annotation.NonNull;
import com.rd.animation.controller.ValueController;

/* loaded from: classes2.dex */
public class ScaleDownAnimation extends ScaleAnimation {
    public ScaleDownAnimation(@NonNull ValueController.UpdateListener updateListener) {
        super(updateListener);
    }

    @Override // com.rd.animation.type.ScaleAnimation
    @NonNull
    protected PropertyValuesHolder createScalePropertyHolder(boolean z) {
        String str;
        int i;
        int i2;
        if (z) {
            str = "ANIMATION_SCALE_REVERSE";
            i2 = (int) (this.c * this.d);
            i = this.c;
        } else {
            str = "ANIMATION_SCALE";
            i2 = this.c;
            i = (int) (this.c * this.d);
        }
        PropertyValuesHolder ofInt = PropertyValuesHolder.ofInt(str, i2, i);
        ofInt.setEvaluator(new IntEvaluator());
        return ofInt;
    }
}
