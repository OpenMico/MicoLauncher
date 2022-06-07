package miuix.view.animation;

import android.view.animation.Interpolator;

/* loaded from: classes5.dex */
public class BackEaseInInterpolator implements Interpolator {
    private final float a;

    public BackEaseInInterpolator() {
        this(0.0f);
    }

    public BackEaseInInterpolator(float f) {
        this.a = f;
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        float f2 = this.a;
        if (f2 == 0.0f) {
            f2 = 1.70158f;
        }
        return f * f * (((1.0f + f2) * f) - f2);
    }
}
