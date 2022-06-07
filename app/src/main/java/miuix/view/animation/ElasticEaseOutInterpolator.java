package miuix.view.animation;

import android.view.animation.Interpolator;

/* loaded from: classes5.dex */
public class ElasticEaseOutInterpolator implements Interpolator {
    private final float a;
    private final float b;

    public ElasticEaseOutInterpolator() {
        this(0.0f, 0.0f);
    }

    public ElasticEaseOutInterpolator(float f, float f2) {
        this.a = f;
        this.b = f2;
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        float f2;
        float f3 = this.b;
        float f4 = this.a;
        if (f == 0.0f) {
            return 0.0f;
        }
        if (f == 1.0f) {
            return 1.0f;
        }
        if (f3 == 0.0f) {
            f3 = 0.3f;
        }
        if (f4 == 0.0f || f4 < 1.0f) {
            f2 = f3 / 4.0f;
            f4 = 1.0f;
        } else {
            f2 = (float) ((f3 / 6.283185307179586d) * Math.asin(1.0f / f4));
        }
        return (float) ((f4 * Math.pow(2.0d, (-10.0f) * f) * Math.sin(((f - f2) * 6.283185307179586d) / f3)) + 1.0d);
    }
}
