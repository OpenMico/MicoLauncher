package com.rd.animation.type;

import android.animation.Animator;
import android.animation.ValueAnimator;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.rd.animation.controller.ValueController;

/* loaded from: classes2.dex */
public abstract class BaseAnimation<T extends Animator> {
    public static final int DEFAULT_ANIMATION_TIME = 350;
    protected long animationDuration = 350;
    protected T animator = createAnimator();
    protected ValueController.UpdateListener listener;

    @NonNull
    public abstract T createAnimator();

    public abstract BaseAnimation progress(float f);

    public BaseAnimation(@Nullable ValueController.UpdateListener updateListener) {
        this.listener = updateListener;
    }

    public BaseAnimation duration(long j) {
        this.animationDuration = j;
        T t = this.animator;
        if (t instanceof ValueAnimator) {
            t.setDuration(this.animationDuration);
        }
        return this;
    }

    public void start() {
        T t = this.animator;
        if (t != null && !t.isRunning()) {
            this.animator.start();
        }
    }

    public void end() {
        T t = this.animator;
        if (t != null && t.isStarted()) {
            this.animator.end();
        }
    }
}
