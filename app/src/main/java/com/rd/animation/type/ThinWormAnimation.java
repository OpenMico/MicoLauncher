package com.rd.animation.type;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import androidx.annotation.NonNull;
import com.rd.animation.controller.ValueController;
import com.rd.animation.data.type.ThinWormAnimationValue;
import com.rd.animation.type.WormAnimation;

/* loaded from: classes2.dex */
public class ThinWormAnimation extends WormAnimation {
    private ThinWormAnimationValue g = new ThinWormAnimationValue();

    public ThinWormAnimation(@NonNull ValueController.UpdateListener updateListener) {
        super(updateListener);
    }

    @Override // com.rd.animation.type.WormAnimation, com.rd.animation.type.BaseAnimation
    public ThinWormAnimation duration(long j) {
        super.duration(j);
        return this;
    }

    @Override // com.rd.animation.type.WormAnimation
    public WormAnimation with(int i, int i2, int i3, boolean z) {
        if (a(i, i2, i3, z)) {
            this.animator = createAnimator();
            this.a = i;
            this.b = i2;
            this.c = i3;
            this.d = z;
            int i4 = i3 * 2;
            this.e = i - i3;
            this.f = i + i3;
            this.g.setRectStart(this.e);
            this.g.setRectEnd(this.f);
            this.g.setHeight(i4);
            WormAnimation.a a = a(z);
            long j = (long) (this.animationDuration * 0.8d);
            long j2 = (long) (this.animationDuration * 0.2d);
            long j3 = (long) (this.animationDuration * 0.5d);
            long j4 = (long) (this.animationDuration * 0.5d);
            ValueAnimator a2 = a(a.a, a.b, j, false, this.g);
            ValueAnimator a3 = a(a.c, a.d, j, true, this.g);
            a3.setStartDelay(j2);
            ValueAnimator a4 = a(i4, i3, j3);
            ValueAnimator a5 = a(i3, i4, j3);
            a5.setStartDelay(j4);
            ((AnimatorSet) this.animator).playTogether(a2, a3, a4, a5);
        }
        return this;
    }

    private ValueAnimator a(int i, int i2, long j) {
        ValueAnimator ofInt = ValueAnimator.ofInt(i, i2);
        ofInt.setInterpolator(new AccelerateDecelerateInterpolator());
        ofInt.setDuration(j);
        ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.rd.animation.type.ThinWormAnimation.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                ThinWormAnimation.this.a(valueAnimator);
            }
        });
        return ofInt;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(@NonNull ValueAnimator valueAnimator) {
        this.g.setHeight(((Integer) valueAnimator.getAnimatedValue()).intValue());
        if (this.listener != null) {
            this.listener.onValueUpdated(this.g);
        }
    }

    @Override // com.rd.animation.type.WormAnimation, com.rd.animation.type.BaseAnimation
    public ThinWormAnimation progress(float f) {
        if (this.animator != null) {
            long j = f * ((float) this.animationDuration);
            int size = ((AnimatorSet) this.animator).getChildAnimations().size();
            for (int i = 0; i < size; i++) {
                ValueAnimator valueAnimator = (ValueAnimator) ((AnimatorSet) this.animator).getChildAnimations().get(i);
                long startDelay = j - valueAnimator.getStartDelay();
                long duration = valueAnimator.getDuration();
                if (startDelay > duration) {
                    startDelay = duration;
                } else if (startDelay < 0) {
                    startDelay = 0;
                }
                if ((i != size - 1 || startDelay > 0) && valueAnimator.getValues() != null && valueAnimator.getValues().length > 0) {
                    valueAnimator.setCurrentPlayTime(startDelay);
                }
            }
        }
        return this;
    }
}
