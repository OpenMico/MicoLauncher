package miuix.view.animation;

import android.view.animation.Interpolator;

/* loaded from: classes5.dex */
public class ExponentialEaseInInterpolator implements Interpolator {
    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        if (f == 0.0f) {
            return 0.0f;
        }
        return (float) Math.pow(2.0d, (f - 1.0f) * 10.0f);
    }
}
