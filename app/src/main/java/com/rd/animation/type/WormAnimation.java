package com.rd.animation.type;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import androidx.annotation.NonNull;
import com.rd.animation.controller.ValueController;
import com.rd.animation.data.type.WormAnimationValue;
import java.util.Iterator;

/* loaded from: classes2.dex */
public class WormAnimation extends BaseAnimation<AnimatorSet> {
    int a;
    int b;
    int c;
    boolean d;
    int e;
    int f;
    private WormAnimationValue g = new WormAnimationValue();

    public WormAnimation(@NonNull ValueController.UpdateListener updateListener) {
        super(updateListener);
    }

    @Override // com.rd.animation.type.BaseAnimation
    @NonNull
    public AnimatorSet createAnimator() {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        return animatorSet;
    }

    @Override // com.rd.animation.type.BaseAnimation
    public WormAnimation duration(long j) {
        super.duration(j);
        return this;
    }

    public WormAnimation with(int i, int i2, int i3, boolean z) {
        if (a(i, i2, i3, z)) {
            this.animator = createAnimator();
            this.a = i;
            this.b = i2;
            this.c = i3;
            this.d = z;
            this.e = i - i3;
            this.f = i + i3;
            this.g.setRectStart(this.e);
            this.g.setRectEnd(this.f);
            a a2 = a(z);
            long j = this.animationDuration / 2;
            ((AnimatorSet) this.animator).playSequentially(a(a2.a, a2.b, j, false, this.g), a(a2.c, a2.d, j, true, this.g));
        }
        return this;
    }

    @Override // com.rd.animation.type.BaseAnimation
    public WormAnimation progress(float f) {
        if (this.animator == null) {
            return this;
        }
        long j = f * ((float) this.animationDuration);
        Iterator<Animator> it = ((AnimatorSet) this.animator).getChildAnimations().iterator();
        while (it.hasNext()) {
            ValueAnimator valueAnimator = (ValueAnimator) it.next();
            long duration = valueAnimator.getDuration();
            if (j <= duration) {
                duration = j;
            }
            valueAnimator.setCurrentPlayTime(duration);
            j -= duration;
        }
        return this;
    }

    ValueAnimator a(int i, int i2, long j, final boolean z, final WormAnimationValue wormAnimationValue) {
        ValueAnimator ofInt = ValueAnimator.ofInt(i, i2);
        ofInt.setInterpolator(new AccelerateDecelerateInterpolator());
        ofInt.setDuration(j);
        ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.rd.animation.type.WormAnimation.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                WormAnimation.this.a(wormAnimationValue, valueAnimator, z);
            }
        });
        return ofInt;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(@NonNull WormAnimationValue wormAnimationValue, @NonNull ValueAnimator valueAnimator, boolean z) {
        int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
        if (this.d) {
            if (!z) {
                wormAnimationValue.setRectEnd(intValue);
            } else {
                wormAnimationValue.setRectStart(intValue);
            }
        } else if (!z) {
            wormAnimationValue.setRectStart(intValue);
        } else {
            wormAnimationValue.setRectEnd(intValue);
        }
        if (this.listener != null) {
            this.listener.onValueUpdated(wormAnimationValue);
        }
    }

    boolean a(int i, int i2, int i3, boolean z) {
        return (this.a == i && this.b == i2 && this.c == i3 && this.d == z) ? false : true;
    }

    @NonNull
    a a(boolean z) {
        int i;
        int i2;
        int i3;
        int i4;
        if (z) {
            int i5 = this.a;
            int i6 = this.c;
            i4 = i5 + i6;
            int i7 = this.b;
            i3 = i7 + i6;
            i2 = i5 - i6;
            i = i7 - i6;
        } else {
            int i8 = this.a;
            int i9 = this.c;
            i4 = i8 - i9;
            int i10 = this.b;
            i3 = i10 - i9;
            i2 = i8 + i9;
            i = i10 + i9;
        }
        return new a(i4, i3, i2, i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public class a {
        final int a;
        final int b;
        final int c;
        final int d;

        a(int i, int i2, int i3, int i4) {
            this.a = i;
            this.b = i2;
            this.c = i3;
            this.d = i4;
        }
    }
}
