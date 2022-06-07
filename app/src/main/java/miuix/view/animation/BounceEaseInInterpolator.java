package miuix.view.animation;

import android.view.animation.Interpolator;

/* loaded from: classes5.dex */
public class BounceEaseInInterpolator implements Interpolator {
    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        return 1.0f - new BounceEaseOutInterpolator().getInterpolation(1.0f - f);
    }
}
