package miuix.view.animation;

import android.view.animation.Interpolator;

/* loaded from: classes5.dex */
public class QuadraticEaseOutInterpolator implements Interpolator {
    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        return (-f) * (f - 2.0f);
    }
}
