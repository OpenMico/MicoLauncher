package miuix.view.animation;

import android.view.animation.Interpolator;

/* loaded from: classes5.dex */
public class BackEaseOutInterpolator implements Interpolator {
    private final float a;

    public BackEaseOutInterpolator() {
        this(0.0f);
    }

    public BackEaseOutInterpolator(float f) {
        this.a = f;
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        float f2 = this.a;
        if (f2 == 0.0f) {
            f2 = 1.70158f;
        }
        float f3 = f - 1.0f;
        return (f3 * f3 * (((f2 + 1.0f) * f3) + f2)) + 1.0f;
    }
}
