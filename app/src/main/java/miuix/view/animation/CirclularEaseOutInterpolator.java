package miuix.view.animation;

import android.view.animation.Interpolator;

/* loaded from: classes5.dex */
public class CirclularEaseOutInterpolator implements Interpolator {
    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        float f2 = f - 1.0f;
        return (float) Math.sqrt(1.0f - (f2 * f2));
    }
}
