package com.rd.animation;

import androidx.annotation.NonNull;
import com.rd.animation.controller.AnimationController;
import com.rd.animation.controller.ValueController;
import com.rd.draw.data.Indicator;

/* loaded from: classes2.dex */
public class AnimationManager {
    private AnimationController a;

    public AnimationManager(@NonNull Indicator indicator, @NonNull ValueController.UpdateListener updateListener) {
        this.a = new AnimationController(indicator, updateListener);
    }

    public void basic() {
        AnimationController animationController = this.a;
        if (animationController != null) {
            animationController.end();
            this.a.basic();
        }
    }

    public void interactive(float f) {
        AnimationController animationController = this.a;
        if (animationController != null) {
            animationController.interactive(f);
        }
    }

    public void end() {
        AnimationController animationController = this.a;
        if (animationController != null) {
            animationController.end();
        }
    }
}
