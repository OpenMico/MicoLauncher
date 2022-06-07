package miuix.view.animation;

import android.view.animation.Interpolator;

/* loaded from: classes5.dex */
public class CirclularEaseInInterpolator implements Interpolator {
    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        return -((float) (Math.sqrt(1.0f - (f * f)) - 1.0d));
    }
}
