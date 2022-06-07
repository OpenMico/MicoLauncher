package miuix.view.animation;

import android.view.animation.Interpolator;

/* loaded from: classes5.dex */
public class SineEaseOutInterpolator implements Interpolator {
    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        return (float) Math.sin(f * 1.5707963267948966d);
    }
}
