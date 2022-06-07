package com.rd.animation.type;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import androidx.annotation.NonNull;
import com.rd.animation.controller.ValueController;
import com.rd.animation.data.type.DropAnimationValue;
import java.util.Iterator;

/* loaded from: classes2.dex */
public class DropAnimation extends BaseAnimation<AnimatorSet> {
    private int a;
    private int b;
    private int c;
    private int d;
    private int e;
    private DropAnimationValue f = new DropAnimationValue();

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public enum a {
        Width,
        Height,
        Radius
    }

    public DropAnimation(@NonNull ValueController.UpdateListener updateListener) {
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
    public DropAnimation progress(float f) {
        if (this.animator != null) {
            long j = f * ((float) this.animationDuration);
            boolean z = false;
            Iterator<Animator> it = ((AnimatorSet) this.animator).getChildAnimations().iterator();
            while (it.hasNext()) {
                ValueAnimator valueAnimator = (ValueAnimator) it.next();
                long duration = valueAnimator.getDuration();
                long j2 = z ? j - duration : j;
                if (j2 >= 0) {
                    if (j2 >= duration) {
                        j2 = duration;
                    }
                    if (valueAnimator.getValues() != null && valueAnimator.getValues().length > 0) {
                        valueAnimator.setCurrentPlayTime(j2);
                    }
                    if (!z && duration >= this.animationDuration) {
                        z = true;
                    }
                }
            }
        }
        return this;
    }

    @Override // com.rd.animation.type.BaseAnimation
    public DropAnimation duration(long j) {
        super.duration(j);
        return this;
    }

    public DropAnimation with(int i, int i2, int i3, int i4, int i5) {
        if (a(i, i2, i3, i4, i5)) {
            this.animator = createAnimator();
            this.a = i;
            this.b = i2;
            this.c = i3;
            this.d = i4;
            this.e = i5;
            int i6 = (int) (i5 / 1.5d);
            long j = this.animationDuration / 2;
            ValueAnimator a2 = a(i, i2, this.animationDuration, a.Width);
            ValueAnimator a3 = a(i3, i4, j, a.Height);
            ValueAnimator a4 = a(i5, i6, j, a.Radius);
            ValueAnimator a5 = a(i4, i3, j, a.Height);
            ((AnimatorSet) this.animator).play(a3).with(a4).with(a2).before(a5).before(a(i6, i5, j, a.Radius));
        }
        return this;
    }

    private ValueAnimator a(int i, int i2, long j, final a aVar) {
        ValueAnimator ofInt = ValueAnimator.ofInt(i, i2);
        ofInt.setInterpolator(new AccelerateDecelerateInterpolator());
        ofInt.setDuration(j);
        ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.rd.animation.type.DropAnimation.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                DropAnimation.this.a(valueAnimator, aVar);
            }
        });
        return ofInt;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(@NonNull ValueAnimator valueAnimator, @NonNull a aVar) {
        int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
        switch (aVar) {
            case Width:
                this.f.setWidth(intValue);
                break;
            case Height:
                this.f.setHeight(intValue);
                break;
            case Radius:
                this.f.setRadius(intValue);
                break;
        }
        if (this.listener != null) {
            this.listener.onValueUpdated(this.f);
        }
    }

    private boolean a(int i, int i2, int i3, int i4, int i5) {
        return (this.a == i && this.b == i2 && this.c == i3 && this.d == i4 && this.e == i5) ? false : true;
    }
}
